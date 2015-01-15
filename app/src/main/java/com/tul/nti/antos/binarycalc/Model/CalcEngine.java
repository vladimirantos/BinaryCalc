package com.tul.nti.antos.binarycalc.Model;

import android.util.Log;

import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.Stack;

/**
 * Hlavní třída kalkulačky. Stará se o binární výpočty. Jejím úkolem je udržovat v paměti dvě čísla a provádět s nimi operace
 * AND, OR, XOR a NOT. Také provádí převody mezi soustavami.
 * @author Vladimír Antoš
 * @version 1.0
 */
public class CalcEngine {

    private Stack<Boolean> operand1;

    private Stack<Boolean> operand2;

    private IBinaryConverter converter;

    public enum BinaryOperation {AND, OR, XOR};

    private BinaryOperation operation = null;

    public CalcEngine(IBinaryConverter binCon) {
        operand1 = new Stack<Boolean>();
        operand2 = new Stack<Boolean>();
        converter = binCon;
    }

    public CalcEngine setOperation(BinaryOperation b) {
        operation = b;
        return this;
    }

    public Stack<Boolean> getOperand1() {
        return operand1;
    }

    public Stack<Boolean> getOperand2() {
        return operand2;
    }

    /**
     * Neguje hodnotu prvního operandu.
     */
    public void negate() {
        if(operand1.empty())
            throw new EmptyStackException();

        Stack<Boolean> tmp = new Stack<Boolean>();
        Iterator iter = operand1.iterator();
        while(iter.hasNext())
            tmp.push(!(Boolean)iter.next());
        operand1 = tmp;
    }

    /**
     * @return Vrací desítkové číslo prvního operandu.
     */
    public int toDecimal() {
        return converter.setOperand(operand1).toDecimal();
    }

    /**
     * @return Vrací řetězec reprezentující hexadecimální číslo. Převádí první operand.
     */
    public String toHexadecimal() {
        return converter.setOperand(operand1).toHexadecimal();
    }

    /**
     * Provádí logické operace se dvěmi čísly a výsledek uloží do operandu1.
     */
    public void executeOperation() {
        Log.d("empty: ", String.valueOf(operand1.empty()) + String.valueOf(operand2.empty()) + String.valueOf(operation == null));
        if(operand1.empty() || operand2.empty() || operation == null)
            throw new IllegalStateException();

        if(operation.equals(BinaryOperation.AND))
            operand1 = BinaryOperationHelper.And(operand2, operand1);

        else if(operation.equals(BinaryOperation.OR))
            operand1 = BinaryOperationHelper.Or(operand2, operand1);
        else if(operation.equals(BinaryOperation.XOR))
            operand1 = BinaryOperationHelper.Xor(operand2, operand1);
    }

    /**
     * Vymění zásobníky. Použije se u operací se dvěmi čísly. Protože kalkulačka ukládá a čte data z operandu1,
     * musí se před vkládáním druhého čísla zálohovat do operandu2.
     */
    public CalcEngine swap() {
        if(operand1.empty())
            throw new EmptyStackException();

        operand2.addAll(operand1);
        operand1.clear();
        return this;
    }

    /**
     * @return Vrací řetězec prvního operandu.
     */
    public String show() {
        String s = new String();
        Iterator iter = operand1.iterator();
        while(iter.hasNext()) {
            s += ((Boolean)iter.next()) ? "1" : "0";
        }
        return s;
    }

    /**
     * Vymaže operand1
     */
    public void clear() {
        operand1.clear();
        operand2.clear();
    }
}
