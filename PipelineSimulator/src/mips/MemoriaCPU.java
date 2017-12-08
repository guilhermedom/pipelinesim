package mips;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Classe que simula a memória da máquina, dividida por palavras.
 * Aqui estão localizadas tanto a memória de instrução quanto a memória de dados.
 */
public class MemoriaCPU {
	/**
	 * Estrutura de dados que armazena a memória
	 */
	private ArrayList<String> memoria = new ArrayList<String>();
	
	/**
	 * Cria uma memória a partir do arquivo passado.
	 * 
	 * @param arq Nome do arquivo a partir do qual se cria a memória.
	 * @throws IOException Exceção de erro na abertura do arquivo.
	 */
	public MemoriaCPU(String arq) throws IOException {
		
		File input = new File(arq);
		
		try {
			if (!input.exists()) {
				throw new IOException("Arquivo inexistente!");
			}
		
			FileReader fr = new FileReader(arq);
			BufferedReader br = new BufferedReader(fr);
		
			while (br.ready()) {
				String palavra = br.readLine();
				memoria.add(palavra);
			}
		
			br.close();
			fr.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		//memoria.add(arq);
	}
	
	public MemoriaCPU(ArrayList<String> entrada) throws IOException {
		
		memoria = entrada;
		
	}
	
	/**
	 * Busca o valor em uma dada posição de memória.
	 * @param pos A posição na memória em byte da palavra desejada.
	 * @return A palavra buscada.
	 */
	public String getValue(long pos) {
		// Obtém o endereço da palavra pelo endereço "pos".
		long enderecoMemoria = pos;
		
		// Verifica se a posição da palavra existe na memória.
		if (enderecoMemoria >= memoria.size()) {
			throw new IllegalArgumentException("Stack Overflow");
		}
		
		// Retorna a palavra desejada.
		return memoria.get((int) enderecoMemoria);
	}
	
	/**
	 * Armazena na memória o valor passado como parâmetro em uma dada posição da memória
	 * @param pos Posição do byte pelo qual será obtida o endereço da palavra de memória
	 * correspondente a se usar no armazenamento.
	 * @param valor O valor a ser inserido na memória.
	 */
	public void storeValue(long pos, String valor) {
		// Obtém o endereço da palavra pelo endereço do byte com shift de 2 a direita.
		long enderecoMemoria = pos;
		
		// Verifica se o endereço existe na memória.
		if (enderecoMemoria >= memoria.size()) {
			throw new IllegalArgumentException("Stack Overflow");
		}
		
		memoria.set((int) enderecoMemoria, valor);
	}
	
	public int getLimite() {
		int posicao = memoria.indexOf("|");
		return posicao;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < memoria.size(); i++) {
			builder.append("Palavra: ");
			builder.append(i);
			builder.append('\t');
			builder.append(memoria.get(i));
			builder.append('\n');
		}
		
		return builder.toString();
	}
}
