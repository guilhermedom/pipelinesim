package estagios;

import mips.MemoriaCPU;
import mips.ProgramCounter;
import mips.RegInterno;
import mips.TipoRegistrador;

/**
 * Classe que representa o estágio de busca do pipeline.
 */

public class Busca {
	
	// Conjunto de registradores necessários para o estágio, memória da máquina e pc.
	private final RegInterno if_id;
	private final MemoriaCPU memoria;
	private final ProgramCounter pc;
	
	/**
	 * Preenche os atributos necessários para o estágio de busca.
	 * @param if_id Conteúdo do conjunto de registradores internos if_id.
	 * @param memoria Memória da máquina (dados + instrução por enquanto).
	 * @param pc Program Counter.
	 */
	public Busca(RegInterno if_id, MemoriaCPU memoria, ProgramCounter pc) {
		this.if_id = if_id;
		this.memoria = memoria;
		this.pc = pc;
	}
	
	/**
	 * Executa o estágio de busca.
	 */
	public void run() {
		String instrucao = memoria.getValue(pc.getValue());		
		
		// Pega uma instrução na memória onde o pc se encontra, passa para o conjunto interno
		// de registradores if_id que segue para o estágio decodifica.
		if_id.setValueIns(instrucao);
		
		// Zera OP_CODE pois não se sabe OP_CODE da instrução.
		if_id.setValue(TipoRegistrador.OP_CODE, 0);
		
		// Coloca o valor do pc no if_id.
		if_id.setValue(TipoRegistrador.PC, pc.getValue());
		
		// Avança o pc para a próxima linha da memória, para a próxima vez que o estágio busca executar.
		pc.avança();
	}
}
