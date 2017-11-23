package estagios;

import mips.RegInterno;
import mips.TipoRegistrador;

/**
 * Estágio executa do pipeline. Faz todas as operações da ULA.
 */
public class Executa {
	
	// Registradores internos com os quais o estágio executa tem contato.
	private final RegInterno id_ex;
	private final RegInterno ex_mem;
	//private final RegInterno mem_wb;
	
	/**
	 * Construtor que preenche os registradores internos.
	 * @param id_ex Banco de registradores.
	 * @param ex_mem Banco de registradores.
	 * @param mem_wb Banco de registradores.
	 */
	public Executa(RegInterno id_ex, RegInterno ex_mem, RegInterno mem_wb) {
		this.id_ex = id_ex;
		this.ex_mem = ex_mem;
		//this.mem_wb = mem_wb;
	}
	
	/**
	 * Roda o estágio de execução do pipeline.
	 * Faz as operações da ULA e transfere os resultados para a devida
	 * localização.
	 */
	public void run() {
		
		// ula1 e ula2 são os argumentos que entram na ULA.
		long ula1 = 0, ula2 = 0;
		
		// writeData é o dado que sai da ULA diretamente para um registrador.
		long writeData = 0;
		
		// Resultado da operação feita pela ULA.
		long resultado = 0;
		
		// Primeiro argumento da ULA vem do READ_DATA1.
		ula1 = id_ex.getValue(TipoRegistrador.READ_DATA1);
		
		// No MIPS, writeData sempre recebe o valor de READ_DATA2.
		writeData = id_ex.getValue(TipoRegistrador.READ_DATA2);
		
		// Verifica o sinal de ULA_SRC que atua no multiplex do segundo argumento da ULA,
		// para determinar qual será a origem do segundo argumento.
		if (id_ex.getValue(TipoRegistrador.ULA_SRC) == 0) {
			ula2 = id_ex.getValue(TipoRegistrador.READ_DATA2);
		} else {
			ula2 = id_ex.getValue(TipoRegistrador.IMEDIATO);
		}
		
		// Switch na operação a ser feita na ULA, de acordo com o necessário para o trabalho.
		// 1: add 2: sub 3: and 4: or
		switch((int) id_ex.getValue(TipoRegistrador.ULA_OP)) {
		case (1):
			resultado = ula1 + ula2;
			break;
		case (2):
			resultado = ula1 - ula2;
			break;
		case (3):
			resultado = ula1 & ula2;
			break;
		case (4):
			resultado = ula1 | ula2;
			break;
		}
		
		// Passa para o conjunto ex_mem o writeData e o resultado da ULA.
		ex_mem.setValue(TipoRegistrador.ULA_RESULT, resultado);
		ex_mem.setValue(TipoRegistrador.WRITE_DATA, writeData);
		
		// Envia os dados do banco de registradores id_ex para ex_mem.
		id_ex.enviaDados(ex_mem);
	}
}
