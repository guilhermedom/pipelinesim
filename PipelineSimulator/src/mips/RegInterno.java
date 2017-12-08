package mips;
import java.util.Map.Entry;

/**
 * Classe que simula um conjunto de registradores intenos, do tipo id_ex, ex_mem, etc.
 */

public class RegInterno extends BancoRegistradores {
	/**
	 * Variável para guardar erros diversos e cancelar o writeback da instrução com erro.
	 */
	int erro = 0;
	
	/**
	 * Construtor que cria um grupo de registradores internos e coloca no banco "registradores".
	 */
	public RegInterno() {
		super();
		Registrador reg = new Registrador();
		registradores.put(TipoRegistrador.OP_CODE, reg);
	}
	
	/**
	 * Registradores internos são sempre válidos para operações.
	 */
	public boolean regValido(TipoRegistrador tipoReg) {
		return true;
	}
	
	/**
	 * Usado para enviar dados de um grupo de registradores interno para outro.
	 * @param Grupo de registradores do estágio seguinte que recebe os dados.
	 */
	public void enviaDados(RegInterno destino) {
		for (Entry<TipoRegistrador, Registrador> entry : registradores.entrySet()) {
			destino.setValue(entry.getKey(), entry.getValue().getValue());
		}
	}
	
	/**
	 * Constrói uma String que contém a informação de todos os registradores do grupo.
	 */
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (Entry<TipoRegistrador, Registrador> entry : registradores.entrySet()) {
			builder.append(entry.getKey());
			builder.append('\t');
			builder.append(entry.getValue().getValue());
			builder.append('\n');
		}
		
		return builder.toString();
	}
	
	public void setErro() {
		erro = 1;
	}
	
	public int getErro() {
		return erro;
	}
}
