package estagios;

import mips.BancoRegistradores;
import mips.RegInterno;
import mips.TipoRegistrador;

/**
 * Classe que simula o estágio de writeback do pipeline.
 */
public class Writeback {
	
	private final RegInterno mem_wb;
	private final BancoRegistradores bReg;
	
	
	/**
	 * Construtor preenche os conjuntos de registradores os quais o estágio acessa.
	 * @param mem_wb Banco de registradores interno do estágio anterior.
	 * @param bReg Banco de registradores de CPU do estágio anterior.
	 */
	public Writeback(RegInterno mem_wb, BancoRegistradores bReg) {
		this.mem_wb = mem_wb;
		this.bReg = bReg;
	}
	
	
	/**
	 * Executa o estágio de writeback, escrevendo de acordo com os sinais de controle.
	 */
	public void run() {
		if (mem_wb.getValue(TipoRegistrador.REG_WRITE) == 1) {
			
			// Writeback escreve de volta no banco, ou em RD ou em RT.
			// (RD só se usa em instruções que envolvem 3 registradores, como add.
			// Caso contrário, RT é o destino, como em lw)
			long destino = mem_wb.getValue(TipoRegistrador.R_DST) == 1 ?
					mem_wb.getValue(TipoRegistrador.RD) :
						mem_wb.getValue(TipoRegistrador.RT);

			// O dado a ser escrito ou é um valor da memória, ou a resposta da ULA.
			long dado = mem_wb.getValue(TipoRegistrador.MEM_TO_REG) == 1 ?
					mem_wb.getValue(TipoRegistrador.MEM_RESULT) :
						mem_wb.getValue(TipoRegistrador.ULA_RESULT);
			
			// Armazena o dado no registrador de destino
			// Diferente de 0 pois r0 em mips é um registrador estático de valor 0,
			// que não pode ser sobrescrito e não faz sentido armazenar 0 em um registrador
			// sendo que o r0 é sempre 0.
			if (destino != 0) {
				bReg.setValue(TipoRegistrador.valueOf(destino), dado);
				bReg.clock();
			}
		}
	}
}
