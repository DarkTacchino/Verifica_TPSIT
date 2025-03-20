import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText budgetEditText, descriptionEditText, amountEditText;
    private Button foodButton, transportButton, otherButton, sendButton;
    private CardView infoCardView;
    private ScrollView scrollView;
    private TextView infoTextView;
    private String selectedCategory = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        budgetEditText = findViewById(R.id.budget);
        descriptionEditText = findViewById(R.id.des);
        amountEditText = findViewById(R.id.imp);
        foodButton = findViewById(R.id.cibo);
        transportButton = findViewById(R.id.trasporto);
        otherButton = findViewById(R.id.button11);
        sendButton = findViewById(R.id.invia);
        infoCardView = findViewById(R.id.infoCardView);
        scrollView = findViewById(R.id.scrollView);
        infoTextView = findViewById(R.id.infoTextView);

        checkBudget();
    }

    private void checkBudget() {
        if (budgetEditText.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Inserisci il budget iniziale!", Toast.LENGTH_SHORT).show();
            disableButtons();
            hideInputFields();
        } else {
            enableButtons();
            hideInputFields();
        }
    }

    private void disableButtons() {
        foodButton.setEnabled(false);
        transportButton.setEnabled(false);
        otherButton.setEnabled(false);
        sendButton.setEnabled(false);
    }

    private void enableButtons() {
        foodButton.setEnabled(true);
        transportButton.setEnabled(true);
        otherButton.setEnabled(true);
    }

    private void hideInputFields() {
        descriptionEditText.setVisibility(View.INVISIBLE);
        amountEditText.setVisibility(View.INVISIBLE);
        sendButton.setVisibility(View.INVISIBLE);
    }

    private void showInputFields() {
        descriptionEditText.setVisibility(View.VISIBLE);
        amountEditText.setVisibility(View.VISIBLE);
        sendButton.setVisibility(View.VISIBLE);
    }

    public void foodButtonClick(View view) {
        selectedCategory = "Cibo";
        showInputFields();
    }

    public void transportButtonClick(View view) {
        selectedCategory = "Trasporto";
        showInputFields();
    }

    public void otherButtonClick(View view) {
        selectedCategory = "Altro";
        showInputFields();
    }

    public void sendButtonClick(View view) {
        String description = descriptionEditText.getText().toString();
        String amount = amountEditText.getText().toString();

        if (!description.isEmpty() && !amount.isEmpty()) {

            String info = "Categoria: " + selectedCategory + "\nDescrizione: " + description + "\nImporto: " + amount;
            infoTextView.setText(info);
            descriptionEditText.getText().clear();
            amountEditText.getText().clear();
            hideInputFields();
        } else {
            Toast.makeText(this, "Inserisci descrizione e importo!", Toast.LENGTH_SHORT).show();
        }

    }

}