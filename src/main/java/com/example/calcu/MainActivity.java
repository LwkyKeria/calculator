package com.example.calcu;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView tv;
    private String currentInput = "";
    private String operator = "";
    private double firstNumber = 0.0;
    private boolean isOperatorClicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.tv);

        setNumberButtonListeners();
        setOperatorButtonListeners();
    }

    private void setNumberButtonListeners() {
        int[] numberButtonIDs = {
                R.id.btn_1, R.id.btn_2, R.id.btn_3, R.id.btn_4,
                R.id.btn_5, R.id.btn_6, R.id.btn_7, R.id.btn_8,
                R.id.btn_9, R.id.btn_0
        };

        View.OnClickListener numberListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button clickedButton = (Button) v;
                currentInput += clickedButton.getText().toString();
                tv.setText(currentInput);
                isOperatorClicked = false;
            }
        };

        for (int id : numberButtonIDs) {
            findViewById(id).setOnClickListener(numberListener);
        }
    }

    private void setOperatorButtonListeners() {
        findViewById(R.id.btn_plus).setOnClickListener(operatorListener);
        findViewById(R.id.btn_sub).setOnClickListener(operatorListener);
        findViewById(R.id.btn_mul).setOnClickListener(operatorListener);
        findViewById(R.id.btn_div).setOnClickListener(operatorListener);
        findViewById(R.id.btn_eq).setOnClickListener(equalListener);
        findViewById(R.id.btn_del).setOnClickListener(deleteListener);
        findViewById(R.id.btn_clear).setOnClickListener(clearListener);
        findViewById(R.id.btn_decimal).setOnClickListener(decimalListener);
        findViewById(R.id.btn_0).setOnClickListener(zeroListener);
    }

    private final View.OnClickListener operatorListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!isOperatorClicked) {
                if (!currentInput.isEmpty()) {
                    firstNumber = Double.parseDouble(currentInput);
                    Button clickedButton = (Button) v;
                    operator = clickedButton.getText().toString();
                    currentInput = "";
                    isOperatorClicked = true;
                }
            }
        }
    };

    private final View.OnClickListener equalListener = new View.OnClickListener() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onClick(View v) {
            if (!currentInput.isEmpty() && !operator.isEmpty()) {
                double secondNumber = Double.parseDouble(currentInput);
                double result = 0.0;

                switch (operator) {
                    case "+":
                        result = firstNumber + secondNumber;
                        break;
                    case "-":
                        result = firstNumber - secondNumber;
                        break;
                    case "*":
                        result = firstNumber * secondNumber;
                        break;
                    case "/":
                        if (firstNumber == 0 && secondNumber == 0) {
                            tv.setText("Result is Undefined");
                            return;
                        } else if (secondNumber == 0) {
                            tv.setText("Cannot Divide by Zero");
                            return;
                        } else {
                            result = firstNumber / secondNumber;
                        }
                        break;
                }
                tv.setText(String.valueOf(result));
                currentInput = "";
                operator = "";
                isOperatorClicked = false;
            }
        }
    };


    private final View.OnClickListener clearListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            currentInput = "";
            operator = "";
            tv.setText("0"); // Reset to 0 instead of an empty string
            isOperatorClicked = false;
        }
    };

    private final View.OnClickListener deleteListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!currentInput.isEmpty()) {
                currentInput = currentInput.substring(0, currentInput.length() - 1);
                tv.setText(currentInput.isEmpty() ? "0" : currentInput);
            }
        }
    };

    private final View.OnClickListener decimalListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!currentInput.contains(".")) {
                currentInput += ".";
                tv.setText(currentInput);
            }
        }
    };

    private final View.OnClickListener zeroListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!currentInput.equals("0")) {
                currentInput += "0";
                tv.setText(currentInput);
            }
        }
    };
}
