package com.example.linearlayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity {

    private TextView display;

    private double firstOperand = 0;
    private String pendingOperator = "";
    private boolean startNewNumber = true;
    private boolean hasError = false;

    private final View.OnClickListener sharedListener = view -> {
        Button button = (Button) view;
        appendSymbol(button.getText().toString());
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.main), (v, insets) -> {
                    Insets systemBars = insets.getInsets(
                            WindowInsetsCompat.Type.systemBars());
                    v.setPadding(
                            systemBars.left,
                            systemBars.top,
                            systemBars.right,
                            systemBars.bottom);
                    return insets;
                });

        display = findViewById(R.id.display);
        display.setText("0");

        int[] digitIds = {
                R.id.btn0, R.id.btn1, R.id.btn2,
                R.id.btn3, R.id.btn4, R.id.btn5,
                R.id.btn6, R.id.btn7, R.id.btn8,
                R.id.btn9
        };

        for (int id : digitIds) {
            findViewById(id).setOnClickListener(sharedListener);
        }

        findViewById(R.id.btnDot).setOnClickListener(v -> appendSymbol("."));

        findViewById(R.id.btnPlus).setOnClickListener(v -> chooseOperator("+"));
        findViewById(R.id.btnMinus).setOnClickListener(v -> chooseOperator("-"));
        findViewById(R.id.btnMultiply).setOnClickListener(v -> chooseOperator("*"));
        findViewById(R.id.btnDivide).setOnClickListener(v -> chooseOperator("/"));

        findViewById(R.id.btnEquals).setOnClickListener(v -> calculate());
        findViewById(R.id.btnClear).setOnClickListener(v -> clear());
    }

    private void appendSymbol(String symbol) {
        if (hasError) {
            clear();
        }

        String current = display.getText().toString();

        if (startNewNumber) {
            display.setText(symbol.equals(".") ? "0." : symbol);
            startNewNumber = false;
            return;
        }

        if (symbol.equals(".")) {
            if (current.contains(".") || current.length() >= 10) {
                return;
            }

            display.setText(current + ".");
            return;
        }

        if (current.length() >= 10) {
            return;
        }

        // Replace a leading zero instead of appending to it.
        if (current.equals("0")) {
            display.setText(symbol);
        } else {
            display.setText(current + symbol);
        }
    }

    private void chooseOperator(String operator) {
        if (hasError) {
            return;
        }

        try {
            double current = Double.parseDouble(
                    display.getText().toString());

            if (!pendingOperator.isEmpty() && !startNewNumber) {
                firstOperand = performCalculation(
                        firstOperand, current, pendingOperator);

                display.setText(formatResult(firstOperand));
            } else {
                firstOperand = current;
            }

            pendingOperator = operator;
            startNewNumber = true;

        } catch (ArithmeticException | NumberFormatException e) {
            showError();
        }
    }

    private void calculate() {
        if (hasError || pendingOperator.isEmpty() || startNewNumber) {
            return;
        }

        try {
            double secondOperand = Double.parseDouble(
                    display.getText().toString());

            double result = performCalculation(
                    firstOperand, secondOperand, pendingOperator);

            display.setText(formatResult(result));

            firstOperand = result;
            pendingOperator = "";
            startNewNumber = true;

        } catch (ArithmeticException | NumberFormatException e) {
            showError();
        }
    }

    private double performCalculation(
            double first, double second, String operator) {

        double result;

        switch (operator) {
            case "+":
                result = first + second;
                break;

            case "-":
                result = first - second;
                break;

            case "*":
                result = first * second;
                break;

            case "/":
                if (second == 0) {
                    throw new ArithmeticException("Division by zero");
                }
                result = first / second;
                break;

            default:
                throw new IllegalArgumentException("Unknown operator");
        }

        if (Double.isNaN(result) || Double.isInfinite(result)) {
            throw new ArithmeticException("Invalid result");
        }

        return result;
    }

    private String formatResult(double result) {
        String text = BigDecimal.valueOf(result)
                .stripTrailingZeros()
                .toPlainString();

        if (text.length() <= 10) {
            return text;
        }

        text = String.format(java.util.Locale.US, "%.3E", result);

        if (text.length() > 10) {
            text = String.format(java.util.Locale.US, "%.2E", result);
        }

        if (text.length() > 10) {
            return "Error";
        }

        return text;
    }

    private void showError() {
        display.setText("Error");
        firstOperand = 0;
        pendingOperator = "";
        startNewNumber = true;
        hasError = true;
    }

    private void clear() {
        display.setText("0");
        firstOperand = 0;
        pendingOperator = "";
        startNewNumber = true;
        hasError = false;
    }
}