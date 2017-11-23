package estagios;

import java.util.HashMap;

import mips.BancoRegistradores;
import mips.ProgramCounter;
import mips.RegInterno;
import mips.TipoRegistrador;

/**
 * Classe que simula o est�gio de decodifica��o do pipeline.
 */
public class Decodifica {
	
	private final RegInterno if_id;
	private final RegInterno id_ex;
	private final BancoRegistradores bReg;
	private final ProgramCounter pc;
	
	// C�digo da opera��o da instru��o atual.
	private long opCode;
	
	// Vari�veis que indicam a posi��o para onde ser� feito o branch 
	// (para onde o pc vai apontar em caso de branch).
	private long imediatoBranchOffset;
	private long imediatoBranchSW;
	
	// Entradas da ula no pr�ximo est�gio.
	private long readData1;
	private long readData2;
	
	/**
	 * Construtor que preenche os grupos de registradores que ser�o usados no est�gio.
	 * @param if_id Grupo de registradores.
	 * @param id_ex Grupo de registradores.
	 * @param bReg Banco de registradores.
	 * @param pc ProgramCounter.
	 */
	public Decodifica(RegInterno if_id, RegInterno id_ex, BancoRegistradores bReg,
			ProgramCounter pc) {
		this.if_id = if_id;
		this.id_ex = id_ex;
		this.bReg = bReg;
		this.pc = pc;
	}
	
	/**
	 * Executa o est�gio de decodifica.
	 */
	public void run() {
		String instrucao = if_id.getValueIns();
		
		// opCode: 1 = add. 2 = sub. 3 = and. 4 = or. 5 = lw. 6 = sw. 7 = bne.
		if (instrucao.contains("ADD") || instrucao.contains("add")) {
			 opCode = 1;
			 
			 // Quebra a string em substrings para fazer o parsing.
			 String[] pedacos = instrucao.split(" ");
			 String operacao = pedacos[0]; // add
			 String destino = pedacos[1]; // RD
			 String arg1 = pedacos[2]; // RS
			 String arg2 = pedacos[3]; // RT
			 
			 // Armazena os valores dos argumentos no conjunto de registradores.
			 id_ex.setValue(TipoRegistrador.RS, bReg.getValue(TipoRegistrador.valueOf(arg1)));
			 id_ex.setValue(TipoRegistrador.RT, bReg.getValue(TipoRegistrador.valueOf(arg2)));
			 id_ex.setValue(TipoRegistrador.RD, bReg.getValue(TipoRegistrador.valueOf(destino)));
			 
			 // Armazena a opera��o no conjunto de registradores.
			 id_ex.setValueIns(operacao);	
			 
			 // Pega o valor dos argumentos para colocar nos readData que entrar�o na ULA no pr�ximo est�gio.
			 readData1 = bReg.getValue(TipoRegistrador.valueOf(arg1));
			 readData2 = bReg.getValue(TipoRegistrador.valueOf(arg2));
		}
		if (instrucao.contains("SUB") || instrucao.contains("sub")) {
			 opCode = 2;
			 String[] pedacos = instrucao.split(" ");
			 String operacao = pedacos[0]; // sub
			 String destino = pedacos[1]; // RD
			 String arg1 = pedacos[2]; // RS
			 String arg2 = pedacos[3]; // RT
			 
			 id_ex.setValue(TipoRegistrador.RS, bReg.getValue(TipoRegistrador.valueOf(arg1)));
			 id_ex.setValue(TipoRegistrador.RT, bReg.getValue(TipoRegistrador.valueOf(arg2)));
			 id_ex.setValue(TipoRegistrador.RD, bReg.getValue(TipoRegistrador.valueOf(destino)));
			 
			 id_ex.setValueIns(operacao);		
			 
			 readData1 = bReg.getValue(TipoRegistrador.valueOf(arg1));
			 readData2 = bReg.getValue(TipoRegistrador.valueOf(arg2));
		}
		if (instrucao.contains("AND") || instrucao.contains("and")) {
			 opCode = 3;
			 String[] pedacos = instrucao.split(" ");
			 String operacao = pedacos[0]; // and
			 String destino = pedacos[1]; // RD
			 String arg1 = pedacos[2]; // RS
			 String arg2 = pedacos[3]; // RT
			 
			 id_ex.setValue(TipoRegistrador.RS, bReg.getValue(TipoRegistrador.valueOf(arg1)));
			 id_ex.setValue(TipoRegistrador.RT, bReg.getValue(TipoRegistrador.valueOf(arg2)));
			 id_ex.setValue(TipoRegistrador.RD, bReg.getValue(TipoRegistrador.valueOf(destino)));
			 
			 id_ex.setValueIns(operacao);
			 
			 readData1 = bReg.getValue(TipoRegistrador.valueOf(arg1));
			 readData2 = bReg.getValue(TipoRegistrador.valueOf(arg2));
		}
		if (instrucao.contains("OR") || instrucao.contains("or")) {
			 opCode = 4;
			 String[] pedacos = instrucao.split(" ");
			 String operacao = pedacos[0]; // or
			 String destino = pedacos[1]; // RD
			 String arg1 = pedacos[2]; // RS
			 String arg2 = pedacos[3]; // RT
			 
			 id_ex.setValue(TipoRegistrador.RS, bReg.getValue(TipoRegistrador.valueOf(arg1)));
			 id_ex.setValue(TipoRegistrador.RT, bReg.getValue(TipoRegistrador.valueOf(arg2)));
			 id_ex.setValue(TipoRegistrador.RD, bReg.getValue(TipoRegistrador.valueOf(destino)));
			 
			 id_ex.setValueIns(operacao);
			 
			 readData1 = bReg.getValue(TipoRegistrador.valueOf(arg1));
			 readData2 = bReg.getValue(TipoRegistrador.valueOf(arg2));
		}
		if (instrucao.contains("LW") || instrucao.contains("lw")) {
			
			 opCode = 5;
			 String[] pedacos = instrucao.split(" ");
			 String operacao = pedacos[0]; // lw
			 String destino = pedacos[1]; // RT
			 String offset = pedacos[2]; // offset
			 String origem = pedacos[3]; // RS
			 
			 // Coloca em RS o valor da posi��o da mem�ria ao qual ser� aplicado o offset para saber
			 // onde est� o dado requerido na mem�ria.
			 id_ex.setValue(TipoRegistrador.RS, if_id.getValue(TipoRegistrador.valueOfName(origem)));
			 
			 // Registrador RT receber� o valor da posi��o dada por RS.
			 // id_ex recebe em RT a posi��o do registrador destino(RT escrito na instrucao) dentro do grupo de
			 // registradores.
			 id_ex.setValue(TipoRegistrador.RT, TipoRegistrador.getPosition(TipoRegistrador.valueOfName(destino)));
			 
			 // Coloca no registrador IMEDIATO o valor do offset.
			 id_ex.setValue(TipoRegistrador.IMEDIATO, Long.parseLong(offset));
			 
			 // Coloca na vari�vel de id_ex a opera��o sendo executada.
			 id_ex.setValueIns(operacao);
			 
			 readData1 = bReg.getValue(TipoRegistrador.valueOfName(origem));
			 readData2 = bReg.getValue(TipoRegistrador.valueOfName(destino));
		}
		if (instrucao.contains("SW") || instrucao.contains("sw")) {
			 opCode = 6;
			 String[] pedacos = instrucao.split(" ");
			 String operacao = pedacos[0]; // sw
			 String destino = pedacos[1]; // RT
			 String offset = pedacos[2]; // offset
			 String origem = pedacos[3]; // RS
			 
			 // Recebe a posi��o da mem�ria onde ser� colocado o dado.
			 id_ex.setValue(TipoRegistrador.RS, if_id.getValue(TipoRegistrador.valueOfName(origem)));

			 // O Registrador RT cont�m o dado que ser� colocado na mem�ria.
			 id_ex.setValue(TipoRegistrador.RT, TipoRegistrador.getPosition(TipoRegistrador.valueOfName(destino)));
			 
			 // Offset que ser� agregado ao RS para saber a posi��o exata onde o dado ser� colocado.
			 id_ex.setValue(TipoRegistrador.IMEDIATO, Long.parseLong(offset));

			 id_ex.setValueIns(operacao);
			 
			 readData1 = bReg.getValue(TipoRegistrador.valueOf(origem));
			 readData2 = bReg.getValue(TipoRegistrador.valueOf(destino));
		}
		if (instrucao.contains("BNE") || instrucao.contains("bne")) {
			 opCode = 7;
			 String[] pedacos = instrucao.split(" ");
			 String operacao = pedacos[0]; // bne
			 String arg1 = pedacos[1]; // RS
			 String arg2 = pedacos[2]; // RT
			 String offset = pedacos[3]; // offset
			 
			 // Argumentos que ser�o comparados para verificar se o branch ser� feito.
			 id_ex.setValue(TipoRegistrador.RS, bReg.getValue(TipoRegistrador.valueOf(arg1)));		 
			 id_ex.setValue(TipoRegistrador.RT, bReg.getValue(TipoRegistrador.valueOf(arg2)));
			 
			 id_ex.setValue(TipoRegistrador.IMEDIATO, Long.parseLong(offset));
			 
			 // Vari�vel para ser usada no m�todo fazBranch com o valor do offset que afetar� o pc.
			 imediatoBranchOffset = Long.parseLong(offset);
			 imediatoBranchSW = bReg.getValue(TipoRegistrador.valueOf(arg1));
			 
			 id_ex.setValueIns(operacao);
			 
			 readData1 = bReg.getValue(TipoRegistrador.valueOf(arg1));
			 readData2 = bReg.getValue(TipoRegistrador.valueOf(arg2));
		}
		
		// Registradores read data que entrar�o como argumentos da ULA no est�gio Executa.
		id_ex.setValue(TipoRegistrador.READ_DATA1, readData1);
		id_ex.setValue(TipoRegistrador.READ_DATA2, readData2);
		
		// Guarda todos os sinais de controle setados durante esse est�gio.
		// Pode ser �til para exibir informa��es no output.
		HashMap<TipoRegistrador, Long> sinaisControle = setSinaisControle(opCode);
		
		// Condi��es para que o branch ocorra.
		if (sinaisControle.get(TipoRegistrador.BRANCH) == 1
				&& sinaisControle.get(TipoRegistrador.BRANCH_NE) == 1
				&& readData1 != readData2) {
			// A soma dos valores indica para onde o pc ser� encaminhado.
			fazBranch(imediatoBranchOffset + imediatoBranchSW);
		}
	}
	
	/**
	 * Realiza o branch no c�digo zerando os sinais de controle e mudando o valor
	 * do pc.
	 * @param pos Posi��o para onde o branch redireciona o pc.
	 */
	private void fazBranch(long pos) {
		zerarSinaisControle(if_id);
		
		pc.setValue(pos);
		pc.enableWrite();
	}
	
	/**
	 * M�todo que zera todos os sinais de controle quando ocorre um branch.
	 * @param reg Grupo de registradores que ter� o sinal de controle zerado.
	 */
	private void zerarSinaisControle(RegInterno reg) {
		reg.setValue(TipoRegistrador.OP_CODE, 0);
		reg.setValue(TipoRegistrador.MEM_TO_REG, 0);
		reg.setValue(TipoRegistrador.REG_WRITE, 0);
		reg.setValue(TipoRegistrador.R_DST, 0);
		reg.setValue(TipoRegistrador.ULA_SRC, 0);
		reg.setValue(TipoRegistrador.ULA_OP, 0);
		reg.setValue(TipoRegistrador.MEM_READ, 0);
		reg.setValue(TipoRegistrador.MEM_WRITE, 0);
		reg.setValue(TipoRegistrador.BRANCH, 0);
		reg.setValue(TipoRegistrador.BRANCH_NE, 0);
		reg.setValue(TipoRegistrador.JUMP, 0);
		reg.setValue(TipoRegistrador.JUMP_SRC, 0);
	}
	
	/**
	 * Aloca todos os sinais de controle de acordo com a opera��o da instru��o que ser� processada
	 * no est�gio executa.
	 * @param opCode C�digo da opera��o da instru��o atual.
	 * @return Todos os sinais de controle para exibir na tela.
	 */
	private HashMap<TipoRegistrador, Long> setSinaisControle(long opCode) {
		HashMap<TipoRegistrador, Long> sinais = new HashMap<TipoRegistrador, Long>();
		
		// Inicialmente zera todos os sinais de controle do Hash e do id_ex,
		// pois ainda n�o se sabe qual opera��o ser� executada.
		sinais.put(TipoRegistrador.R_DST, 0l);
		sinais.put(TipoRegistrador.ULA_SRC, 0l);
		sinais.put(TipoRegistrador.MEM_TO_REG, 0l);
		sinais.put(TipoRegistrador.REG_WRITE, 0l);
		sinais.put(TipoRegistrador.MEM_READ, 0l);
		sinais.put(TipoRegistrador.MEM_WRITE, 0l);
		sinais.put(TipoRegistrador.BRANCH, 0l);
		sinais.put(TipoRegistrador.BRANCH_NE, 0l);
		sinais.put(TipoRegistrador.JUMP, 0l);
		sinais.put(TipoRegistrador.JUMP_SRC, 0l);
		sinais.put(TipoRegistrador.ULA_OP, 0l);
		sinais.put(TipoRegistrador.HALT, 0l);
		
		id_ex.setValue(TipoRegistrador.R_DST, 0l);
		id_ex.setValue(TipoRegistrador.ULA_SRC, 0l);
		id_ex.setValue(TipoRegistrador.MEM_TO_REG, 0l);
		id_ex.setValue(TipoRegistrador.REG_WRITE, 0l);
		id_ex.setValue(TipoRegistrador.MEM_READ, 0l);
		id_ex.setValue(TipoRegistrador.MEM_WRITE, 0l);
		id_ex.setValue(TipoRegistrador.BRANCH, 0l);
		id_ex.setValue(TipoRegistrador.BRANCH_NE, 0l);
		id_ex.setValue(TipoRegistrador.JUMP, 0l);	
		id_ex.setValue(TipoRegistrador.JUMP_SRC, 0l);
		id_ex.setValue(TipoRegistrador.ULA_OP, 0l);		
		id_ex.setValue(TipoRegistrador.HALT, 0l);
		
		switch((int) opCode) {
		// add
		case 1:
			sinais.put(TipoRegistrador.R_DST, 1l);
			sinais.put(TipoRegistrador.REG_WRITE, 1l);
			sinais.put(TipoRegistrador.ULA_OP, 1l);
			
			id_ex.setValue(TipoRegistrador.R_DST, 1l);
			id_ex.setValue(TipoRegistrador.REG_WRITE, 1l);
			id_ex.setValue(TipoRegistrador.ULA_OP, 1l);
			
			break;
		// sub
		case 2:
			sinais.put(TipoRegistrador.R_DST, 1l);
			sinais.put(TipoRegistrador.REG_WRITE, 1l);
			sinais.put(TipoRegistrador.ULA_OP, 2l);
			
			id_ex.setValue(TipoRegistrador.R_DST, 1l);
			id_ex.setValue(TipoRegistrador.REG_WRITE, 1l);
			id_ex.setValue(TipoRegistrador.ULA_OP, 2l);
			
			break;
		// and
		case 3:
			sinais.put(TipoRegistrador.R_DST, 1l);
			sinais.put(TipoRegistrador.REG_WRITE, 1l);
			sinais.put(TipoRegistrador.ULA_OP, 3l);
			
			id_ex.setValue(TipoRegistrador.R_DST, 1l);
			id_ex.setValue(TipoRegistrador.REG_WRITE, 1l);
			id_ex.setValue(TipoRegistrador.ULA_OP, 3l);
			
			break;
		// or
		case 4:
			sinais.put(TipoRegistrador.R_DST, 1l);
			sinais.put(TipoRegistrador.REG_WRITE, 1l);
			sinais.put(TipoRegistrador.ULA_OP, 4l);
			
			id_ex.setValue(TipoRegistrador.R_DST, 1l);
			id_ex.setValue(TipoRegistrador.REG_WRITE, 1l);
			id_ex.setValue(TipoRegistrador.ULA_OP, 4l);
			
			break;
		// lw
		case 5:
			sinais.put(TipoRegistrador.MEM_TO_REG, 1l);
			sinais.put(TipoRegistrador.MEM_READ, 1l);
			sinais.put(TipoRegistrador.REG_WRITE, 1l);
			sinais.put(TipoRegistrador.ULA_SRC, 1l);
			sinais.put(TipoRegistrador.ULA_OP, 1l);
			
			id_ex.setValue(TipoRegistrador.ULA_OP, 1l);
			id_ex.setValue(TipoRegistrador.MEM_TO_REG, 1l);
			id_ex.setValue(TipoRegistrador.MEM_READ, 1l);
			id_ex.setValue(TipoRegistrador.REG_WRITE, 1l);
			id_ex.setValue(TipoRegistrador.ULA_SRC, 1l);
			
			break;
		// sw
		case 6:
			sinais.put(TipoRegistrador.MEM_WRITE, 1l);
			sinais.put(TipoRegistrador.ULA_SRC, 1l);
			sinais.put(TipoRegistrador.ULA_OP, 1l);

			id_ex.setValue(TipoRegistrador.MEM_WRITE, 1l);
			id_ex.setValue(TipoRegistrador.ULA_SRC, 1l);
			id_ex.setValue(TipoRegistrador.ULA_OP, 1l);
			
			break;
		// bne
		case 7:
			sinais.put(TipoRegistrador.BRANCH, 1l);
			sinais.put(TipoRegistrador.BRANCH_NE, 1l);
			sinais.put(TipoRegistrador.ULA_OP, 2l);
			
			id_ex.setValue(TipoRegistrador.BRANCH, 1l);
			id_ex.setValue(TipoRegistrador.BRANCH_NE, 1l);
			id_ex.setValue(TipoRegistrador.ULA_OP, 2l);
			
			break;
		}
		return sinais;
	}
}
