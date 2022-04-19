package com.internship.retail_management.entities.enums;

public enum TransactionType {

	/**
	 * Enumeration for the transaction's types.
	 * 
	 * @author Bruno Soares
	 * @author João Osório
	 * @version 1.0
	 */
	// Numeros para que sejam íntegros ao valor na tabela, caso haja alguma
	// alteração na enumeração
	DEBIT(0), CREDIT(1);

	private int code;

	/**
	 * Sets the type of transaction.
	 * @param code Transaction's type code
	 */
	private TransactionType(int code) {
			this.code = code;
		}

	/**
	 * Retrieves the transaction type code.
	 * @return
	 */
	public int getCode() {
		return code;
	}

	/**
	 * Gets transaction's code and returns type of said transaction.
	 * @param code transaction's type code
	 * @return value of the transaction
	 * @throws IllegalArgumentException
	 */
	public static TransactionType valueOf(int code) {
		for (TransactionType value : TransactionType.values()) {
			if (value.getCode() == code) {
				return value;
			}
		}

		throw new IllegalArgumentException("Invalid transaction type value!");
	}

}
