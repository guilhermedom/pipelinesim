package mips;


/**
 * 
 * Classe feita para representar um registrador.
 * Os valores alterados por meio de métodos set só serão atualizados
 * após a chamada do método clock, que representa a ativação de um 
 * ciclo de clock.
 * 
 */
public class Registrador {
	
	/**
	 * Conteúdo do registrador.
	 */
	protected long valor = 0;
	protected long pc = 0;
	
	/**
	 * Controla quando o conteúdo do registrador pode ser sobrescrito.
	 */
	private boolean disableWrite = false;
	
	/**
	 * Novo conteúdo do registrador que será atualizado quando clock()
	 * for chamado.
	 */
	protected long novoValor = -1;
	
	/**
	 * Construtor que inicia o registrador com conteúdo 0.
	 */
	public Registrador() {
		this(0);
	}
	
	/**
	 * Construtor que inicia o registrador com conteúdo do parâmetro.
	 * @param valor é o valor que será inserido inicialmente no registrador.
	 */
	public Registrador(long valor) {
		this.valor = valor;
	}
	
	public long getValue() {
		return valor;
	}
	
	public void setValue(long novoValor) {
		this.valor = novoValor;
	}
	
	/**
	 * Desabilita a escrita no registrador.
	 */
	public void disableWrite() {
		this.disableWrite = true;
	}
	
	/**
	 * Habilita a escrita no registrador.
	 */
	public void enableWrite() {
		this.disableWrite = false;
	}
	
	/**
	 * Atualiza o valor do registrador com o novo valor da variável novoValor.
	 * Apenas funciona quando a escrita está habilitada.
	 */
	public void clock() {
		/*if (!disableWrite) {
			valor = novoValor;
			novoValor = -1;
		} else
			disableWrite = false;*/
	}
}
