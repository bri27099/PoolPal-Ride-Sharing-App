package com.example.poolpal;

import android.app.DatePickerDialog;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class OfferRide extends AppCompatActivity {

    private TextView sourceTextView;
    private EditText sourceEditText;
    private TextView destinationTextView;
    private EditText destinationEditText;
    private TextView dateTextView;
    private EditText dateEditText;
    private Button genderToggleButton;
    private Button createRideButton;
    private TextView estimatedTimeTextView;
    private TextView estimatedCostTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_ride);

        sourceTextView = findViewById(R.id.sourceTextView);
        sourceEditText = findViewById(R.id.sourceEditText);
        destinationTextView = findViewById(R.id.destinationTextView);
        destinationEditText = findViewById(R.id.destinationEditText);
        dateTextView = findViewById(R.id.dateTextView);
      //  dateEditText = findViewById(R.id.dateEditText);
        genderToggleButton = findViewById(R.id.genderToggleButton);
        createRideButton = findViewById(R.id.createRideButton);
        estimatedTimeTextView = findViewById(R.id.estimatedTimeTextView);
        estimatedCostTextView = findViewById(R.id.estimatedCostTextView);
        //TextView dateTextView = findViewById(R.id.dateTextView);


        dateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get current date
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                // create DatePickerDialog and show it
                DatePickerDialog datePickerDialog = new DatePickerDialog(OfferRide.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // set the selected date to dateTextView
                        String date = dayOfMonth + "/" + (month + 1) + "/" + year;
                        dateTextView.setText("Date: " + date);
                    }
                }, year, month, dayOfMonth);
                datePickerDialog.show();
            }
        });




        genderToggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toggle same gender option
                // Implement your logic here
            }
        });

        createRideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create ride button clicked
                // Implement your logic here
            }
        });
    }
}