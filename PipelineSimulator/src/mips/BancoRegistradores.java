package mips;
import java.util.HashMap;

/**
 * Banco de registradores da m�quina.
 */
public class BancoRegistradores {
	/**
	 * Estrutura de dados que armazena o banco de registradores,
	 * relacionando cada grupo de registradores com seu TipoRegistrador que tem seu conte�do num HashMap.
	 */
	protected HashMap<TipoRegistrador, Registrador> registradores = new HashMap<TipoRegistrador, Registrador>();
	
	protected String ins;
	
	Registrador reg = new Registrador();
	
	/**
	 * Controla a escrita no banco de registradores para a fun��o clock().
	 */
	private boolean disableWrite = false;
	
	/**
	 * Seta um novo valor para um registrador do banco. Apenas quando a fun��o clock roda.
	 * 
	 * @param tipoRegistrador Nome do registrador a se alterar.
	 * @param valor Novo valor para o registrador.
	 */
	public void setValue(TipoRegistrador tipoRegistrador, long valor) {
		Registrador registrador = getRegistrador(tipoRegistrador);
		registrador.setValue(valor);
	}
	
	/**
	 * Obt�m o valor de um registrador espec�fico dentro do banco de registradores,
	 * vasculhando dentro do hash que relaciona tipoRegistrador com seu valor.
	 * 
	 * @param tipoRegistrador O registrador do qual deseja-se o conte�do.
	 * @return O conte�do do registrador.
	 */
	public long getValue(TipoRegistrador tipoRegistrador) {
		return getRegistrador(tipoRegistrador).getValue();
	}
	
	/**
	 * Faz um set na instru��o que est� sendo executada no momento.
	 * @param instrucao Instru��o sendo executada no momento
	 */
	public void setValueIns(String instrucao) {
		ins = instrucao;
	}
	
	/**
	 * Faz um get na instru��o que est� sendo executada no momento.
	 * @return Instru��o sendo executada no momento.
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
	 * Avan�a o ciclo de clock em todos os registradores do banco.
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
	 * para se usar seu conte�do posteriormente.
	 * @param tipoRegistrador
	 * @return
	 */
	private Registrador getRegistrador(TipoRegistrador tipoRegistrador) {
		/*if (!regValido(tipoRegistrador)) {
			throw new IllegalArgumentException("Registrador inv�lido: " + tipoRegistrador.name());
		}*/
		
		// Procura no HashMap o registrador "tipoRegistrador".
		Registrador registrador = registradores.get(tipoRegistrador);
		
		// Se n�o o registrador buscado n�o existir no banco, ent�o cria um e o coloca
		// no HashMap para fazer sua fun��o.
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
	 * Constr�i String para ser exibida com as informa��es de cada registrador do banco.
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
