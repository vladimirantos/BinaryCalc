package com.tul.nti.antos.binarycalc.Model;

import java.util.Stack;

/**
 * @author Vladimír Antoš
 * @version 1.0
 */
public interface IBinaryConverter {

    public IBinaryConverter setOperand(Stack<Boolean> operand);

    public String toHexadecimal();

    public int toDecimal();
}
