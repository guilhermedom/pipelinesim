package mips;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Classe que simula a mem�ria da m�quina, dividida por palavras.
 * Aqui est�o localizadas tanto a mem�ria de instru��o quanto a mem�ria de dados.
 */
public class MemoriaCPU {
	/**
	 * Estrutura de dados que armazena a mem�ria
	 */
	private ArrayList<String> memoria = new ArrayList<String>();
	
	/**
	 * Cria uma mem�ria a partir do arquivo passado.
	 * 
	 * @param arq Nome do arquivo a partir do qual se cria a mem�ria.
	 * @throws IOException Exce��o de erro na abertura do arquivo.
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
	 * Busca o valor em uma dada posi��o de mem�ria.
	 * @param pos A posi��o na mem�ria em byte da palavra desejada.
	 * @return A palavra buscada.
	 */
	public String getValue(long pos) {
		// Obt�m o endere�o da palavra pelo endere�o "pos".
		long enderecoMemoria = pos;
		
		// Verifica se a posi��o da palavra existe na mem�ria.
		if (enderecoMemoria >= memoria.size()) {
			throw new IllegalArgumentException("Stack Overflow");
		}
		
		// Retorna a palavra desejada.
		return memoria.get((int) enderecoMemoria);
	}
	
	/**
	 * Armazena na mem�ria o valor passado como par�metro em uma dada posi��o da mem�ria
	 * @param pos Posi��o do byte pelo qual ser� obtida o endere�o da palavra de mem�ria
	 * correspondente a se usar no armazenamento.
	 * @param valor O valor a ser inserido na mem�ria.
	 */
	public void storeValue(long pos, String valor) {
		// Obt�m o endere�o da palavra pelo endere�o do byte com shift de 2 a direita.
		long enderecoMemoria = pos;
		
		// Verifica se o endere�o existe na mem�ria.
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
