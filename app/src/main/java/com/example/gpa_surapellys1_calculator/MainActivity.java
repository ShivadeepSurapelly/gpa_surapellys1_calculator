package com.example.gpa_surapellys1_calculator;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText[] gradeInputs = new EditText[5];
    private TextView resultTextView;
    private Button computeButton;
    private boolean computedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize EditText fields
        gradeInputs[0] = findViewById(R.id.editText_course1);
        gradeInputs[1] = findViewById(R.id.editText_course2);
        gradeInputs[2] = findViewById(R.id.editText_course3);
        gradeInputs[3] = findViewById(R.id.editText_course4);
        gradeInputs[4] = findViewById(R.id.editText_course5);

        // Initialize Result TextView
        resultTextView = findViewById(R.id.textView_result);

        // Initialize Compute Button
        computeButton = findViewById(R.id.button_compute);
        computeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                computeGPA();
            }
        });

        // Add TextChangedListener to clear form if text is changed after computation
        for (final EditText editText : gradeInputs) {
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}

                @Override
                public void afterTextChanged(Editable s) {
                    // Change back the button text to "Compute GPA" if user starts typing after computation
                    if (computedOnce) {
                        computeButton.setText("Compute GPA");
                        computedOnce = false;
                    }
                }
            });
        }
    }

    // Method to compute GPA
    private void computeGPA() {
        float totalGrade = 0;
        int validInputs = 0;

        // Loop through all EditText fields to compute total grade and count valid inputs
        for (EditText editText : gradeInputs) {
            String input = editText.getText().toString().trim();
            if (!input.isEmpty()) {
                try {
                    float grade = Float.parseFloat(input);
                    if (grade >= 0 && grade <= 100) {
                        totalGrade += grade;
                        validInputs++;
                    } else {
                        editText.setError("Enter valid grade (0-100)");
                        return;
                    }
                } catch (NumberFormatException e) {
                    editText.setError("Enter valid grade (0-100)");
                    return;
                }
            } else {
                editText.setError("This field cannot be empty");
                return;
            }
        }

        // Calculate GPA and display result
        float gpa = totalGrade / validInputs;
        displayResult(gpa);
    }

    // Method to display result
    private void displayResult(float gpa) {
        String result;
        int backgroundColor;

        // Determine result and background color based on GPA range
        if (gpa < 60) {
            result = "GPA: " + gpa;
            backgroundColor = Color.RED;
        } else if (gpa >= 60 && gpa <= 79) {
            result = "GPA: " + gpa;
            backgroundColor = Color.YELLOW;
        } else {
            result = "GPA: " + gpa;
            backgroundColor = Color.GREEN;
        }

        // Display result in TextView and change background color
        resultTextView.setText(result);
        resultTextView.setBackgroundColor(backgroundColor);

        // Update flag to indicate computation has been done
        computedOnce = true;

        // Change button text to "Clear Form"
        computeButton.setText("Clear Form");
    }
}
