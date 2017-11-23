package estagios;

import mips.MemoriaCPU;
import mips.RegInterno;
import mips.TipoRegistrador;

/**
 * Classe que simula o est�gio de acesso a mem�ria do pipeline.
 */

public class Memoria {

	private final RegInterno ex_mem;
	private final RegInterno mem_wb;
	private final MemoriaCPU memoria;
	
	/**
	 * Construtor que preenche os registradores internos.
	 * @param ex_mem Banco de registradores.
	 * @param mem_wb Banco de registradores.
	 * @param memoria Banco de registradores.
	 */
	public Memoria(RegInterno ex_mem, RegInterno mem_wb, MemoriaCPU memoria) {
		this.ex_mem = ex_mem;
		this.mem_wb = mem_wb;
		this.memoria = memoria;
	}
	
	
	/**
	 * Executa o est�gio de acessoa a mem�ria.
	 * Verifica os sinais de controle para saber se � necess�rio ler ou escrever na
	 * mem�ria e assim o faz.
	 * Por fim passa os dados do conjunto de registradores ex_mem para o conjunto mem_wb.
	 */
	public void run() {
		// Verifica o sinal de controle de leitura da mem�ria, caso 1, envia para o registrador
		// MEM_RESULT do mem_wb a resposta da ULA.
		if (ex_mem.getValue(TipoRegistrador.MEM_READ) == 1) {
			
			String palavra = memoria.getValue(ex_mem.getValue(TipoRegistrador.ULA_RESULT));
					
			long palavraEmLong = Long.parseLong(palavra);
			
			mem_wb.setValue(TipoRegistrador.MEM_RESULT, palavraEmLong);
		}

		if (ex_mem.getValue(TipoRegistrador.MEM_WRITE) == 1) {
			// Coloca a sa�da da ULA como String para armazenar na mem�ria, caso MEM_WRITE seja 1.
			// � necess�ria uma convers�o da palavra de mem�ria de String para long, tipo aceito
			// pelos registradores.
			String dado = Long.toString(ex_mem.getValue(TipoRegistrador.WRITE_DATA));
			
			memoria.storeValue(ex_mem.getValue(TipoRegistrador.ULA_RESULT),
					dado);
		}
		
		// Envia os dados nos registradores do conjunto ex_mem para o conjunto mem_wb;
		ex_mem.enviaDados(mem_wb);
	}
}
