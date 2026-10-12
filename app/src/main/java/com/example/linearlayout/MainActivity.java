package com.example.linearlayout;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private TextView display;

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

        findViewById(R.id.btn0).setOnClickListener(
                v -> appendSymbol("0"));

        findViewById(R.id.btn1).setOnClickListener(
                v -> appendSymbol("1"));

        findViewById(R.id.btn2).setOnClickListener(
                v -> appendSymbol("2"));
    }

    private void appendSymbol(String symbol) {
        String current = display.getText().toString();

        if (current.equals("0")) {
            display.setText(symbol);
        } else {
            display.setText(current + symbol);
        }
    }
}