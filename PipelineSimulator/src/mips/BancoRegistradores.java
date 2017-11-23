package mips;
import java.util.HashMap;

/**
 * Banco de registradores da máquina.
 */
public class BancoRegistradores {
	/**
	 * Estrutura de dados que armazena o banco de registradores,
	 * relacionando cada grupo de registradores com seu TipoRegistrador que tem seu conteúdo num HashMap.
	 */
	protected HashMap<TipoRegistrador, Registrador> registradores = new HashMap<TipoRegistrador, Registrador>();
	
	protected String ins;
	
	Registrador reg = new Registrador();
	
	/**
	 * Controla a escrita no banco de registradores para a função clock().
	 */
	private boolean disableWrite = false;
	
	/**
	 * Seta um novo valor para um registrador do banco. Apenas quando a função clock roda.
	 * 
	 * @param tipoRegistrador Nome do registrador a se alterar.
	 * @param valor Novo valor para o registrador.
	 */
	public void setValue(TipoRegistrador tipoRegistrador, long valor) {
		Registrador registrador = getRegistrador(tipoRegistrador);
		registrador.setValue(valor);
	}
	
	/**
	 * Obtém o valor de um registrador específico dentro do banco de registradores,
	 * vasculhando dentro do hash que relaciona tipoRegistrador com seu valor.
	 * 
	 * @param tipoRegistrador O registrador do qual deseja-se o conteúdo.
	 * @return O conteúdo do registrador.
	 */
	public long getValue(TipoRegistrador tipoRegistrador) {
		return getRegistrador(tipoRegistrador).getValue();
	}
	
	/**
	 * Faz um set na instrução que está sendo executada no momento.
	 * @param instrucao Instrução sendo executada no momento
	 */
	public void setValueIns(String instrucao) {
		ins = instrucao;
	}
	
	/**
	 * Faz um get na instrução que está sendo executada no momento.
	 * @return Instrução sendo executada no momento.
	 */
	public String getValueIns() {
		return ins;
	}
	
	/**
	 * Desabilita a escrita no banco de registradores.
	 */
	public void disableWrite() {
		disableWrite = true;
	}
	
	/**
	 * Habilita a escrita no banco de registradores.
	 */
	public void enableWrite() {
		disableWrite = false;
	}
	
	/**
	 * Avança o ciclo de clock em todos os registradores do banco.
	 */
	public void clock() {
		if (!disableWrite) {
			for (Registrador registrador : registradores.values()) {
				registrador.clock();
			}
		} else {
			disableWrite = false;
		}
	}
	
	/**
	 * Busca no HashMap o registrador com nome "tipoRegistrador" e o retorna,
	 * para se usar seu conteúdo posteriormente.
	 * @param tipoRegistrador
	 * @return
	 */
	private Registrador getRegistrador(TipoRegistrador tipoRegistrador) {
		/*if (!regValido(tipoRegistrador)) {
			throw new IllegalArgumentException("Registrador inválido: " + tipoRegistrador.name());
		}*/
		
		// Procura no HashMap o registrador "tipoRegistrador".
		Registrador registrador = registradores.get(tipoRegistrador);
		
		// Se não o registrador buscado não existir no banco, então cria um e o coloca
		// no HashMap para fazer sua função.
		if (registrador == null) {
			registrador = new Registrador();
			registradores.put(tipoRegistrador, registrador);
		}
		
		return registrador;
	}
	
	public boolean regValido(TipoRegistrador tipoRegistrador) {
		return tipoRegistrador.ehValido();
	}
	
	/**
	 * Constrói String para ser exibida com as informações de cada registrador do banco.
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < 32; i++) {
			builder.append("$r");
			builder.append(i);
			builder.append('\t');
			builder.append(getRegistrador(TipoRegistrador.valueOf(i)).getValue());
			builder.append('\n');
		}
		
		return builder.toString();
	}
}
