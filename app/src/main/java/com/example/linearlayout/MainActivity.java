package com.example.linearlayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private EditText inputGuess;
    private TextView txtResult;
    private TextView txtAttempts;

    private int secretNumber;
    private int attempts = 0;
    private boolean gameFinished = false;

    private final Random random = new Random();

    private final View.OnClickListener gameListener = view -> {
        if (view.getId() == R.id.btnNewGame) {
            startNewGame();
        } else if (view.getId() == R.id.btnGuess) {
            checkGuess();
        }
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

        inputGuess = findViewById(R.id.inputGuess);
        txtResult = findViewById(R.id.txtResult);
        txtAttempts = findViewById(R.id.txtAttempts);

        Button btnGuess = findViewById(R.id.btnGuess);
        Button btnNewGame = findViewById(R.id.btnNewGame);

        btnGuess.setOnClickListener(gameListener);
        btnNewGame.setOnClickListener(gameListener);

        startNewGame();
    }

    private void startNewGame() {
        secretNumber = random.nextInt(100) + 1;
        attempts = 0;
        gameFinished = false;

        inputGuess.setText("");
        txtResult.setText(R.string.initial_result);
        txtAttempts.setText(getString(R.string.attempts_zero));
    }

    private void checkGuess() {
        if (gameFinished) {
            Toast.makeText(
                    this,
                    R.string.game_finished,
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        String text = inputGuess.getText().toString().trim();

        if (text.isEmpty()) {
            Toast.makeText(
                    this,
                    R.string.error_empty,
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        int guess;

        try {
            guess = Integer.parseInt(text);
        } catch (NumberFormatException e) {
            Toast.makeText(
                    this,
                    R.string.error_range,
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        if (guess < 1 || guess > 100) {
            Toast.makeText(
                    this,
                    R.string.error_range,
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        attempts++;
        txtAttempts.setText(
                getString(R.string.attempts_format, attempts));

        if (guess < secretNumber) {
            txtResult.setText(R.string.guess_too_low);
        } else if (guess > secretNumber) {
            txtResult.setText(R.string.guess_too_high);
        } else {
            txtResult.setText(R.string.guess_correct);
            gameFinished = true;
        }

        inputGuess.setText("");
    }
}