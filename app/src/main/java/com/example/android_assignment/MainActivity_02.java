package com.example.android_assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity_02 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main02);

        // step_02: Receive the number from Activity_01 and display in UI:

        // variable declaration to  fetch UI elements
        TextView receivedNo = findViewById(R.id.receiveNo_id);

        // create the get Intent object : to fetch data.
        Intent intent = getIntent();

        // receive the value by getDoubleExtra() method and
        // key must be same which is send by first activity.
        double number = intent.getDoubleExtra("input_msg",0.0);

        // edge_case_03: check : if the number has decimal value then pass the same double value
        // else convert the double to int.  e.g -> 12.0 converts to 12 but 12.02 remains same.
        if(number%1 != 0){
            receivedNo.setText("Received Number 🤩 "+number); // display the number into textView
        }
        else{
            receivedNo.setText("Received Number 🤩 "+(int)number);// display the number into textView
        }

        // step_03: Multiply the received number with 2 and send back to Activity_01:

        // Back button is declared to fetch button element.
        Button backBtn = findViewById(R.id.backBtn_id);

        //OnClickListener is added to Back button, to send back resultant no from Activity_02 to Activity_01:
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // multiply received number with 2 to get the resultant number.
                double res = number*2;

                // Intent object is created of this class Context() to MainActivity_01 class
                Intent intent = new Intent(MainActivity_02.this, MainActivity_01.class);

                // now by putExtra method put the value in key-value pair key.
                // 'output_msg' is the key and 'res' is its value. By 'output_msg' we receive our value.
                intent.putExtra("output_msg", res);

                // Set the result of the activity as 'OK' and attach the intent containing data
                setResult(RESULT_OK,intent);
                // Finish the current activity and return to the calling activity
                finish();
            }
        });
    }
}