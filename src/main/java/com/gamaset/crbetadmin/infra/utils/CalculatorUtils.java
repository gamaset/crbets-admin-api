package com.gamaset.crbetadmin.infra.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculatorUtils {

	/**
	 * Defined centrally, to allow for easy changes to the rounding mode.
	 */
	private static RoundingMode ROUNDING_MODE = RoundingMode.HALF_EVEN;

	/**
	 * Number of decimals to retain. Also referred to as "scale".
	 */
	private static int DECIMALS = 2;
	private static int PERCENTAGE_DECIMALS = 3;
	private static int HUNDRED = 100;
	private static final BigDecimal TWO = new BigDecimal("2");

	public static BigDecimal getSum(BigDecimal amountOne, BigDecimal amountTwo) {
		return amountOne.add(amountTwo);
	}

	public static BigDecimal getDifference(BigDecimal amountOne, BigDecimal amountTwo) {
		return amountTwo.subtract(amountOne);
	}

	public static BigDecimal getAverage(BigDecimal amountOne, BigDecimal amountTwo) {
		return getSum(amountOne, amountTwo).divide(TWO, ROUNDING_MODE);
	}

	public static BigDecimal getMultiply(BigDecimal amount, BigDecimal percentage) {
		BigDecimal result = amount.multiply(percentage);
		return rounded(result);
	}

//	  private BigDecimal getPercentageChange(){
//	    BigDecimal fractionalChange = getDifference().divide(
//	      amountOne, EXTRA_DECIMALS, ROUNDING_MODE
//	    );
//	    return rounded(fractionalChange.multiply(HUNDRED));
//	  }

	private static BigDecimal rounded(BigDecimal number) {
		return number.setScale(DECIMALS, ROUNDING_MODE);
	}

}
