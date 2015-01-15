package com.tul.nti.antos.binarycalc.Model;

import android.util.Log;

import java.util.Stack;

/**
 * @author Vladimír Antoš
 * @version 1.0
 */
public class BinaryOperationHelper {

    public static Stack<Boolean> And(Stack<Boolean> operand1, Stack<Boolean> operand2) {
        int x = toInt(operand1);
        int y = toInt(operand2);
        String str = Integer.toBinaryString(x * y);
        return toStack(str);
    }

    public static Stack<Boolean> Or(Stack<Boolean> operand1, Stack<Boolean> operand2) {
        int x = toInt(operand1);
        int y = toInt(operand2);
        String str = Integer.toBinaryString(x + y);
        return toStack(str);
    }

    public static Stack<Boolean> Xor(Stack<Boolean> operand1, Stack<Boolean> operand2) {
        int x = toInt(operand1);
        int y = toInt(operand2);
        String str = Integer.toBinaryString(x ^ y);
        return toStack(str);
    }

    private static int toInt(Stack<Boolean> stack) {
        String str = new String();
        for(boolean b : stack) {
            str += b ? "1" : "0";
        }
        return Integer.parseInt(str, 2);
    }

    private static Stack<Boolean> toStack(String str) {
        Stack<Boolean> s = new Stack();
        for(int i = 0; i < str.length(); i++)
            s.push(str.charAt(i) == '1');
        return s;
    }
}
