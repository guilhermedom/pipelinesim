package mips;


/**
 * Classe que simula um program counter. Armazena a posição na memória atual.
 */
public class ProgramCounter extends Registrador {
	
	/**
	 * Cria o program counter e o deixa apontado para a instrução inicial.
	 */
	public ProgramCounter() {
		super(0);
	}
	
	/**
	 * Avança o program counter na memória, palavra a palavra/linha a linha
	 * /posição a posição do hashmap de memória.
	 */
	public void avança() {
		//pc = getValue() + 1;
		pc++;
		System.out.println("Novo PC: " + pc);
	}
}
