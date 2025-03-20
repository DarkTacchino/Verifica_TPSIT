package com.example.verifica_tpsit_luciano_arsene;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TableRow descTableRew, impTableRew, invioTableRew;
    private Button foodButton, transportButton, otherButton, sendButton;
    private ScrollView scrollView;
    private TextView infoTextView;
    private CardView infoCardView;
    private List<String> history = new ArrayList<>();
    private EditText budgetEditText, descriptionEditText, amountEditText;
    private double budget = 0;
    private boolean budgetSet = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        budgetEditText = findViewById(R.id.budget);
        foodButton = findViewById(R.id.cibo);
        transportButton = findViewById(R.id.trasporto);
        otherButton = findViewById(R.id.button11);
        sendButton = findViewById(R.id.invia);
        descriptionEditText = findViewById(R.id.des);
        amountEditText = findViewById(R.id.imp);
        infoTextView = findViewById(R.id.infoTextView);
        descTableRew = findViewById(R.id.descTableRew);
        impTableRew = findViewById(R.id.impTableRew);
        invioTableRew = findViewById(R.id.invioTableRew);

        foodButton.setOnClickListener(v -> showInputFields());
        transportButton.setOnClickListener(v -> showInputFields());
        otherButton.setOnClickListener(v -> showInputFields());
        sendButton.setOnClickListener(v -> sendButtonClick());

        budgetEditText.setOnClickListener(v -> {
            if (!budgetSet) {
                budgetEditText.setText("");
            }
        });

        budgetEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus && !budgetSet) {
                try {
                    budget = Double.parseDouble(budgetEditText.getText().toString());
                    if (budget > 0) {
                        foodButton.setEnabled(true);
                        transportButton.setEnabled(true);
                        otherButton.setEnabled(true);
                        budgetEditText.setEnabled(false);
                        budgetSet = true;
                    } else {
                        Toast.makeText(this, "Il budget deve essere maggiore di zero.", Toast.LENGTH_SHORT).show();
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(this, "Inserisci un budget valido.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void showInputFields() {
        descTableRew.setVisibility(View.VISIBLE);
        impTableRew.setVisibility(View.VISIBLE);
        invioTableRew.setVisibility(View.VISIBLE);
    }

    private void sendButtonClick() {
        String description = descriptionEditText.getText().toString();
        double amount;
        try {
            amount = Double.parseDouble(amountEditText.getText().toString());
        } catch (NumberFormatException e) {
            infoTextView.setText("Inserisci un importo valido.");
            return;
        }

        if (amount <= 0 || amount > 250) {
            infoTextView.setText("L'importo deve essere compreso tra 1 e 250.");
            return;
        }

        if (budget - amount < 0) {
            infoTextView.setText("Budget insufficiente.");
            return;
        }

        budget -= amount;
        String category = "";
        if (foodButton.isPressed()) {
            category = "Cibo";
        } else if (transportButton.isPressed()) {
            category = "Trasporto";
        } else if (otherButton.isPressed()) {
            category = "Altro";
        }

        String entry = String.format(Locale.getDefault(), "%s: %.2f€ (%.2f%%) - %s",
                category, amount, (amount / (budget + amount)) * 100, description);
        history.add(entry);

        updateInfoTextView();
        clearInputFields();
    }

    private void updateInfoTextView() {
        StringBuilder infoText = new StringBuilder("Budget rimanente: " + String.format(Locale.getDefault(), "%.2f€\n", budget));
        for (String entry : history) {
            infoText.append(entry).append("\n");
        }
        infoTextView.setText(infoText.toString());
    }

    private void clearInputFields() {
        descriptionEditText.setText("");
        amountEditText.setText("");
        descTableRew.setVisibility(View.GONE);
        impTableRew.setVisibility(View.GONE);
        invioTableRew.setVisibility(View.GONE);
    }
}