package estagios;

import mips.BancoRegistradores;
import mips.RegInterno;
import mips.TipoRegistrador;

/**
 * Classe que simula o est�gio de writeback do pipeline.
 */
public class Writeback {
	
	private final RegInterno mem_wb;
	private final BancoRegistradores bReg;
	
	
	/**
	 * Construtor preenche os conjuntos de registradores os quais o est�gio acessa.
	 * @param mem_wb Banco de registradores interno do est�gio anterior.
	 * @param bReg Banco de registradores de CPU do est�gio anterior.
	 */
	public Writeback(RegInterno mem_wb, BancoRegistradores bReg) {
		this.mem_wb = mem_wb;
		this.bReg = bReg;
	}
	
	
	/**
	 * Executa o est�gio de writeback, escrevendo de acordo com os sinais de controle.
	 */
	public void run() {
		if (mem_wb.getValue(TipoRegistrador.REG_WRITE) == 1) {
			
			// Writeback escreve de volta no banco, ou em RD ou em RT.
			// (RD s� se usa em instru��es que envolvem 3 registradores, como add.
			// Caso contr�rio, RT � o destino, como em lw)
			long destino = mem_wb.getValue(TipoRegistrador.R_DST) == 1 ?
					mem_wb.getValue(TipoRegistrador.RD) :
						mem_wb.getValue(TipoRegistrador.RT);

			// O dado a ser escrito ou � um valor da mem�ria, ou a resposta da ULA.
			long dado = mem_wb.getValue(TipoRegistrador.MEM_TO_REG) == 1 ?
					mem_wb.getValue(TipoRegistrador.MEM_RESULT) :
						mem_wb.getValue(TipoRegistrador.ULA_RESULT);
			
			// Armazena o dado no registrador de destino
			// Diferente de 0 pois r0 em mips � um registrador est�tico de valor 0,
			// que n�o pode ser sobrescrito e n�o faz sentido armazenar 0 em um registrador
			// sendo que o r0 � sempre 0.
			if (destino != 0) {
				bReg.setValue(TipoRegistrador.valueOf(destino), dado);
				bReg.clock();
			}
		}
	}
}
