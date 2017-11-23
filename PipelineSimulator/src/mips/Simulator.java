package mips;
import java.io.IOException;

import estagios.Busca;
import estagios.Decodifica;
import estagios.Executa;
import estagios.Memoria;
import estagios.Writeback;

/**
 * Classe principal do simulador. Controla os estágios do pipeline e declara os
 * conjuntos de registradores.
 */

public class Simulator {	
	/**
	 * Conjunto de registradores entre cada estágio de pipeline.
	 */
	private RegInterno if_id = new RegInterno();
	private RegInterno id_ex = new RegInterno();
	private RegInterno ex_mem = new RegInterno();
	private RegInterno mem_wb = new RegInterno();
	
	private ProgramCounter pc = new ProgramCounter();
	
	/**
	 * Banco de registradores da CPU
	 */
	private BancoRegistradores bReg = new BancoRegistradores();
	
	/**
	 * Conjunto de estados do pipeline
	 */
	private Busca busca;
	private Decodifica decodifica;
	private Executa executa;
	private Memoria memoria;
	private Writeback writeback;
	
	/**
	 * Função main do programa.
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {		
		Simulator sim = new Simulator("teste.txt");
		
		// Executa o simulador criado acima.
		sim.run();
	}
	
	/**
	 * Cria o simulador como um todo, desde memória até estágios de pipeline.
	 */
	public Simulator(String arq) throws IOException {
		// Memória de instruções e de dados
		MemoriaCPU memoriaCPU = new MemoriaCPU(arq);
		
		// Estágios do pipeline
		busca = new Busca(if_id, memoriaCPU, pc);
		decodifica = new Decodifica(if_id, id_ex, bReg, pc);
		executa = new Executa(id_ex, ex_mem, mem_wb);
		memoria = new Memoria(ex_mem, mem_wb, memoriaCPU);
		writeback = new Writeback(mem_wb, bReg);
	}
	
	/**
	 * Executa o simulador
	 */
	public void run() {
		
		// Contador de número de ciclos.
		int numCiclos = 0;
		
		// Aqui precisa ter um laço para que o programa funcione como um pipeline
		//while () {
			
			busca.run();
			// Writeback executa antes de decodifica por causa do conceito
			// de divisão dos estágios em duas metades para cada ciclo de clock,
			// para evitar algumas dependências.
			writeback.run();
			decodifica.run();
			executa.run();
			memoria.run();
			writeback.run();
			
			busca.run();
			writeback.run();
			decodifica.run();
			executa.run();
			memoria.run();
			writeback.run();
			
			numCiclos++;
			
			// Avança o ciclo de clock.
			clock();
		//}
		
		System.out.println("Número de ciclos: " + numCiclos);
		System.out.println(bReg);
	}
	
	/**
	 *  Avança o ciclo de clock em todos os registradores.
	 */
	private void clock() {
		pc.clock();
		if_id.clock();
		id_ex.clock();
		ex_mem.clock();
		mem_wb.clock();
	}
}
