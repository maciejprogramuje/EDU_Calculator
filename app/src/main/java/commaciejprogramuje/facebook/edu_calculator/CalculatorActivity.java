package commaciejprogramuje.facebook.edu_calculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CalculatorActivity extends AppCompatActivity {
    private String display = "0";
    private double accumulator = 0.0;
    private Operation currentOperation = Operation.NONE;
    boolean isOperation;
    TextView upperTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        upperTextView = (TextView) findViewById(R.id.upperTextView);
    }

    public void onClicked(View view) {
        Button button = (Button) view;
        String key = button.getText().toString();
        TextView displayTextView = (TextView) findViewById(R.id.resultTextView);

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
            case "SQRT":
                if(isOperation) {
                    calculateResult();
                }
                calculateOperation(key);
                break;
            case "=":
            case "%":
                calculateResult();
                break;
            case "CE":
                clearOne();
                break;
            case "C":
                clearAll();
                break;
        }
        displayTextView.setText(display);
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

    private void calculateResult() {
        double displayValue;

        if(display.equals("")) {
            displayValue = 0.0;
        } else {
            displayValue = Double.valueOf(display);
        }

        switch (currentOperation) {
            case ADD:
                displayResult(accumulator + displayValue);
                break;
            case SUBSTRACT:
                displayResult(accumulator - displayValue);
                break;
            case MULTIPLY:
                displayResult(accumulator * displayValue);
                break;
            case DIVIDE:
                if (displayValue != 0) {
                    displayResult(accumulator / displayValue);
                } else {
                    Toast.makeText(getApplicationContext(), R.string.alert0, Toast.LENGTH_SHORT).show();
                }
                break;
            case PERCENT:
                displayResult(accumulator * displayValue / 100);
                break;
        }
        upperTextView.setText("");
        isOperation = false;
    }

    private void displayResult(double result) {
        if (result == (long) result) {
            display = String.valueOf((long) result);
        } else {
            display = String.valueOf(result);
        }
    }

    private void calculateOperation(String key) {
        currentOperation = Operation.operationFromKey(key);
        accumulator = Double.valueOf(display);

        String temp = display + key;
        upperTextView.setText(temp);

        display = "";
        isOperation = true;
    }
}
