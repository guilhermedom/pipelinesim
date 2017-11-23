package mips;

/**
 * Classe enum respons�vel por enumerar todos os sinais de controle
 * da MC e registradores que ser�o usados pela CPU.
 */
public enum TipoRegistrador {
	R0, R1, R2, R3, R4, R5, R6, R7, R8, R9, R10, R11, R12, R13, R14,
	R15, R16, R17, R18, R19, R20, R21, R22, R23, R24, R25, R26, R27,
	R28, R29, R30, R31,
	
	INSTRUCAO(false), 
	
	RS(false), RT(false), RD(false),

	IMEDIATO(false), SHIFT_AMT(false),  ENDERECO(false), R_DST(false),
	
	ULA_SRC(false),
	
	MEM_TO_REG(false), REG_WRITE(false),
	
	MEM_READ(false), MEM_WRITE(false),
	
	BRANCH(false), BRANCH_NE(false),
	
	ULA_OP(false),
	
	JUMP(false), JUMP_SRC(false),
	
	READ_DATA1(false), READ_DATA2(false),
	
	ULA_RESULT(false), MEM_RESULT(false), 
	
	WRITE_DATA(false),
	
	HALT(false),
	
	OP_CODE(false), 
	
	PC(false);
	
	// Vari�vel para verificar validade do registrador.
	// S� se pode valores de opera��es e de dados nos registradores r1~r31.
	// Os outros s�o para sinais de controle.
	private final boolean valido;
	
	/**
	 *  Caso seja declarado sem par�metro ent�o pode-se escrever nele/� v�lido.
	 */
	private TipoRegistrador() {
		this(true);
	}
	
	/**
	 *  Declara��o com valor da validade.
	 * @param valido Validade do registrador para opera��es.
	 */
	private TipoRegistrador(boolean valido) {
		this.valido = valido;
	}
	
	/**
	 *  Retorna o nome do TipoRegistrador de acordo com o valor de ordinal.
	 *  Como esta classe � um enum, o �ndice de cada item � a ordem em que foi declarado.
	 * 	R0 = 0, R1 = 1... INSTRUCAO = 32...
	 * @param ordinal Posi��o do TipoRegistrador na classe enum.
	 * @return TipoRegistrador da posi��o ordinal.
	 */
	public static TipoRegistrador valueOf(long ordinal) {
		for (TipoRegistrador nome : TipoRegistrador.values()) {
			if (nome.ordinal() == ordinal)
				return nome;
		}
		return null;
	}
	
	public static TipoRegistrador valueOfName(String nome) {
		for (TipoRegistrador tR : TipoRegistrador.values()) {
			if (nome.equals(tR.name()))
				return tR;
		}
		return null;
	}
	
	public static int getPosition(TipoRegistrador tipoRegistrador) {
		for (TipoRegistrador tR : TipoRegistrador.values()) {
			if (tipoRegistrador.name().equals(tR.name()))
				return tR.ordinal();
		}
		return 0;
	}
	
	/**
	 * Verifica se � poss�vel usar o registrador nas opera��es.
	 * @return Validade do registrador em boolean.
	 */
	public boolean ehValido() {
		return valido;
	}
}
