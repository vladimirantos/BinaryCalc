package com.tul.nti.antos.binarycalc.Model;

import android.util.Log;

import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.Stack;

/**
 * Převádí čísla z binární soustavy do soustav definovaných v rozhraní IBinaryConverter.
 * @author Vladimír Antoš
 * @version 1.0
 */
public class BinaryConverter implements IBinaryConverter{

    private Stack<Boolean> operand;

    public IBinaryConverter setOperand(Stack<Boolean> operand) {
        this.operand = operand;
        return this;
    }

    @Override
    public String toHexadecimal() {
        if(operand.empty())
            throw new EmptyStackException();
        int size = operand.size();
        if(size % 4 != 0) {
            operand = padding(operand);
        }

        String str = new String();
        int pointer = - 1;
        Stack<Boolean> tmp = new Stack();
        for(int i = 0; i < operand.size(); i++) {
            pointer++;
            if(pointer < 4) {
                tmp.push(operand.get(i));
            } else {
                pointer = -1;
                i--;
            }
            if(tmp.size() == 4) {
                str += toHex(tmp);
                tmp.clear();
            }
        }
        return str;
    }

    /**
     * @return Vrací hodnotu zásobníku v desítkové soustavě
     */
    @Override
    public int toDecimal() {
        return toDecimal(operand);
    }

    private int toDecimal(Stack<Boolean> stack) {
        if(stack.empty())
            throw new EmptyStackException();
        int result = 0;
        int i = 0;
        int size = stack.size() - 1;
        for(boolean b : stack) {
            if(b == true)
                result += Math.pow(2, size - i); //počítáno od nejvyššího bitu
            i++;
        }
        return result;
    }

    /**
     * Doplní zásobník tak aby byl dělitelný osmi (na bajty).
     * @param stack
     * @return
     */
    private Stack<Boolean> padding(Stack<Boolean> stack) {
        int padding = 4 - stack.size() % 4;

        if(padding > 0)
            for(int i = 0; i < padding; i++)
                stack.add(0, false);
        return stack;
    }

    private String toHex(Stack<Boolean> stack) {
        int dec = toDecimal(stack);
        String hex = new String();
        if(dec >= 0 && dec <= 9)
            hex += (char)(48 + dec);
        else if(dec >= 10 && dec <= 15)
            hex += (char)(55 + dec);
        return hex;
    }
}
