package com.tul.nti.antos.binarycalc;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.tul.nti.antos.binarycalc.Model.BinaryConverter;
import com.tul.nti.antos.binarycalc.Model.CalcEngine;

import java.util.EmptyStackException;


public class MainActivity extends ActionBarActivity {

    TextView display;

    /**
     * Pokud je true, znamená to, že se začínají zadávat čísla - vymaže se display.
     */
    boolean displayIsEmpty = true;

    /**
     * Text, který se zobrazuje na displeji ve výchozím stavu
     */
    private static String DEFAULT_VALUE = "0";

    private CalcEngine calculator = new CalcEngine(new BinaryConverter());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView v = new TextView(this);
        v.setId(R.id.textView);
        v.setBackgroundColor(Color.GREEN);
        display = (TextView) findViewById(R.id.textView);
        initDisplay();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startSettingsActivity();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Přidá na displej nulu.
     * @param view
     */
    public void addNullClicked(View view) {
        if(displayIsEmpty) {
            displayIsEmpty = false;
            clearDisplay();
        }
        calculator.getOperand1().push(false);
        show();
    }

    /**
     * Přidá na displej jedničku.
     * @param view
     */
    public void addOneClicked(View view) {
        if(displayIsEmpty) {
            displayIsEmpty = false;
            clearDisplay();
        }
        calculator.getOperand1().push(true);
        show();
    }

    /**
     * Vyčistí displej.
     * @param view
     */
    public void clearButtonClicked(View view) {
        initDisplay();
    }

    /**
     * Negace zadaného čísla.
     * @param view
     */
    public void negateButtonClicked(View view) {
        try {
            calculator.negate();
            show();
            displayIsEmpty = true;
        } catch(EmptyStackException e) {
            message(R.string.empty_error);
        }
    }

    /**
     * Převede číslo na desítkové.
     * @param view
     */
    public void decButtonClicked(View view) {
        try {
            Integer result = calculator.toDecimal();
            show(result);
            displayIsEmpty = true;
        }catch(EmptyStackException e) {
            message(R.string.empty_error);
        }
    }

    /**
     * Převede číslo do hexadecimální soustavy.
     * @param view
     */
    public void hexButtonClicked(View view) {
        try{
            String str = calculator.toHexadecimal();
            show(str);
            displayIsEmpty = true;
        } catch(EmptyStackException e) {
            message(R.string.empty_error);
        }
    }

    public void binButtonClicked(View view) {
        try {
            show();
            displayIsEmpty = true;
        } catch(EmptyStackException e) {
            message(R.string.empty_error);
        }
    }

    /**
     * Nastavuje logickou operaci AND.
     * @param view
     */
    public void andButtonClicked(View view) {
        try {
            calculator.setOperation(CalcEngine.BinaryOperation.AND).swap();
            displayIsEmpty = true;
        } catch(EmptyStackException e) {
            message(R.string.empty_error);
        }
    }

    /**
     * Nastavuje logickou operaci OR.
     * @param view
     */
    public void orButtonClicked(View view) {
        try {
            calculator.setOperation(CalcEngine.BinaryOperation.OR).swap();
            displayIsEmpty = true;
        } catch(EmptyStackException e) {
            message(R.string.empty_error);
        }
    }

    /**
     * Nastavuje logickou operaci XOR.
     * @param view
     */
    public void xorButtonClicked(View view) {
        try {
            calculator.setOperation(CalcEngine.BinaryOperation.XOR).swap();
            displayIsEmpty = true;
        } catch(EmptyStackException e) {
            message(R.string.empty_error);
        }
    }

    /**
     * Obsluha tlačítka rovná se.
     * @param view
     */
    public void okButtonClicked(View view) {
        try {
            calculator.executeOperation();
            show();
            calculator.getOperand2().clear();
            displayIsEmpty = true;
        }catch (IllegalStateException e) {
            message(R.string.empty_error2);
        }
    }

    /**
     * Vymaže displej a nastaví ho do výchozího stavu.
     */
    private void initDisplay() {
        clearDisplay();
        display.setText(DEFAULT_VALUE);
        displayIsEmpty = true;
    }

    /**
     * Odstraní vše z displeje a vymaže zásobníky.
     */
    private void clearDisplay() {
        calculator.getOperand1().clear();
        display.setText(null);
    }

    private void show() {
        display.setText(calculator.show());
    }

    private void show(int number) {
        display.setText(String.valueOf(number));
    }

    private void show(String s) {
        display.setText(s);
    }

    private void message(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    private void message(int resId) {
        Toast.makeText(this, resId, Toast.LENGTH_SHORT).show();
    }

    protected void startSettingsActivity() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}
