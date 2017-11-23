package mips;
import java.util.Map.Entry;

public class RegInterno extends BancoRegistradores {
	
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
	
	/**
	 * Usado para enviar dados de um grupo de registradores interno para outro.
	 * @param Grupo de registradores do estágio seguinte que recebe os dados.
	 */
	public void enviaDados(RegInterno destino) {
		for (Entry<TipoRegistrador, Registrador> entry : registradores.entrySet()) {
			destino.setValue(entry.getKey(), entry.getValue().getValue());
		}
	}
}
