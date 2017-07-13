package commaciejprogramuje.facebook.edu_calculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CalculatorActivity extends AppCompatActivity {
    public static final String DISPLAY_KEY = "display";
    public static final String ACCUMULATOR_KEY = "accumulator";
    public static final String CURRENT_OPERATION_KEY = "currentOperation";
    public static final String IS_OPERATION_KEY = "isOperation";
    public static final String DISPLAY_VALUE_KEY = "displayValue";
    public static final String UPPER_VALUE_KEY = "upperValue";
    private String display = "0";
    private double accumulator = 0.0;
    private Operation currentOperation = Operation.NONE;
    boolean isOperation;
    TextView upperTextView;
    TextView displayTextView;
    double displayValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        displayTextView = (TextView) findViewById(R.id.resultTextView);
        upperTextView = (TextView) findViewById(R.id.upperTextView);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(DISPLAY_KEY, display);
        outState.putDouble(ACCUMULATOR_KEY, accumulator);
        outState.putSerializable(CURRENT_OPERATION_KEY, currentOperation);
        outState.putBoolean(IS_OPERATION_KEY, isOperation);
        outState.putDouble(DISPLAY_VALUE_KEY, displayValue);
        outState.putString(UPPER_VALUE_KEY, upperTextView.getText().toString());
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        display = savedInstanceState.getString(DISPLAY_KEY);
        accumulator = savedInstanceState.getDouble(ACCUMULATOR_KEY);
        currentOperation = (Operation) savedInstanceState.getSerializable(CURRENT_OPERATION_KEY);
        isOperation = savedInstanceState.getBoolean(IS_OPERATION_KEY);
        displayValue = savedInstanceState.getDouble(DISPLAY_VALUE_KEY);
        String tempUpperValue = savedInstanceState.getString(UPPER_VALUE_KEY);

        updateDisplay();
        upperTextView.setText(tempUpperValue);
    }

    public void onClicked(View view) {
        Button button = (Button) view;
        String key = button.getText().toString();

        switch (key) {
            case "0":
            case "1":
            case "2":
            case "3":
            case "4":
            case "5":
            case "6":
            case "7":
            case "8":
            case "9":
                if (display.equals("0")) {
                    display = "";
                }
                display += key;
                break;
            case ".":
                if (!display.contains(".")) {
                    display += key;
                }
                break;
            case "+":
            case "-":
            case "*":
            case "/":
            case "POW":
                if(isOperation) {
                    calculateResultTwoOperands();
                }
                setOperationAndAccumulator(key);
                break;
            case "=":
                calculateResultTwoOperands();
                break;
            case "CE":
                clearOne();
                break;
            case "C":
                clearAll();
                break;
            case "%":
                calculateResultPercentOperation();
                break;
            case "SQRT":
            case "SIN":
            case "COS":
            case "TAN":
                calculateResultOneOperand(key);
                break;
        }
        updateDisplay();
    }

    private void calculateResultOneOperand(String key) {
        setDisplayValueIfNull();

        switch(key) {
            case "SQRT":
                displayResultAsLongOrDouble(Math.sqrt(displayValue));
                break;
            case "SIN":
                displayResultAsLongOrDouble(Math.sin(displayValue));
                break;
            case "COS":
                displayResultAsLongOrDouble(Math.cos(displayValue));
                break;
            case "TAN":
                displayResultAsLongOrDouble(Math.tan(displayValue));
                break;
        }

        upperTextView.setText(key);
        isOperation = false;
    }

    private void calculateResultTwoOperands() {
        setDisplayValueIfNull();

        switch (currentOperation) {
            case ADD:
                displayResultAsLongOrDouble(accumulator + displayValue);
                break;
            case SUBSTRACT:
                displayResultAsLongOrDouble(accumulator - displayValue);
                break;
            case MULTIPLY:
                displayResultAsLongOrDouble(accumulator * displayValue);
                break;
            case DIVIDE:
                if (displayValue != 0) {
                    displayResultAsLongOrDouble(accumulator / displayValue);
                } else {
                    Toast.makeText(getApplicationContext(), R.string.alert0, Toast.LENGTH_SHORT).show();
                }
                break;
            case POW:
                displayResultAsLongOrDouble(Math.pow(accumulator, displayValue));
                break;
        }
        upperTextView.setText("");
        isOperation = false;
    }

    private void calculateResultPercentOperation() {
        setDisplayValueIfNull();
        displayResultAsLongOrDouble(accumulator * displayValue / 100);
        upperTextView.setText("%");
        isOperation = false;
    }

    private void displayResultAsLongOrDouble(double result) {
        if (result == (long) result) {
            display = String.valueOf((long) result);
        } else {
            display = String.valueOf(result);
        }
    }

    private void setOperationAndAccumulator(String key) {
        currentOperation = Operation.operationFromKey(key);
        accumulator = Double.valueOf(display);

        String temp = display + key;
        upperTextView.setText(temp);

        display = "";
        isOperation = true;
    }

    private void clearAll() {
        display = "0";
        upperTextView.setText("");
        accumulator = 0.0;
        currentOperation = Operation.NONE;
        isOperation = false;
    }

    private void clearOne() {
        if (display.length() > 1) {
            display = display.substring(0, display.length() - 1);
        } else {
            display = "0";
        }
    }

    private void updateDisplay() {
        displayTextView.setText(display);
    }

    private void setDisplayValueIfNull() {
        if(display.equals("")) {
            displayValue = 0.0;
        } else {
            displayValue = Double.valueOf(display);
        }
    }
}
