package mips;

/**
 * Classe enum responsável por enumerar todos os sinais de controle
 * da MC e registradores que serão usados pela CPU.
 */
public enum TipoRegistrador {
	R0, R1, R2, R3, R4, R5, R6, R7, R8, R9, R10, R11, R12, R13, R14,
	R15, R16, R17, R18, R19, R20, R21, R22, R23, R24, R25, R26, R27,
	R28, R29, R30, R31,
	
	// Registradores com parâmetro false indicam registradores que não podem ser usados
	// em operações da ULA.
	INSTRUCAO, 
	
	RS, RT, RD,

	IMEDIATO, SHIFT_AMT,  ENDERECO, R_DST,
	
	ULA_SRC,
	
	MEM_TO_REG, REG_WRITE,
	
	MEM_READ, MEM_WRITE,
	
	BRANCH, BRANCH_NE,
	
	ULA_OP,
	
	JUMP, JUMP_SRC,
	
	READ_DATA1, READ_DATA2,
	
	ULA_RESULT, MEM_RESULT, 
	
	WRITE_DATA,
	
	HALT,
	
	OP_CODE, 
	
	PC;
	
	/**
	 *  Retorna o nome do TipoRegistrador de acordo com o valor de ordinal.
	 *  Como esta classe é um enum, o índice de cada item é a ordem em que foi declarado.
	 * 	R0 = 0, R1 = 1... INSTRUCAO = 32...
	 * @param ordinal Posição do TipoRegistrador na classe enum.
	 * @return TipoRegistrador da posição ordinal.
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
	
	public static long getPosition(TipoRegistrador tipoRegistrador) {
		for (TipoRegistrador tR : TipoRegistrador.values()) {
			if (tipoRegistrador.name().equals(tR.name()))
				return tR.ordinal();
		}
		return 0;
	}
	
	public static long getPositionNome(String nome) {
		for (TipoRegistrador tR : TipoRegistrador.values()) {
			if (nome.equals(tR.name()))
				return tR.ordinal();
		}
		return 0;
	}
}
