package com.example.android_assignment;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity_01 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main01);

        // variable declaration to  fetch UI elements
        EditText inputNumber = findViewById(R.id.inputNo_id);
        Button submitButton = findViewById(R.id.submitBtn_id);

        // step_01:Pass one number from the first Activity to the second Activity

        //OnClickListener is added to submit button, to send number from Activity_01 to Activity_02:
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // edge_case_01: if the user doesn't enter any value or if only enter (.) then give the warning:
                if(inputNumber.getText().toString().equals("") || inputNumber.getText().toString().equals(".")){
                    Toast.makeText(MainActivity_01.this,"Please enter your number ⚠️",Toast.LENGTH_SHORT).show();
                }
                // edge_case_02 : If user enter more than 10 digit number , then warning is shown.
                // handle the decimal part also i.e 12.12222222 -> is a 2 digit no.
                else if(isValid(inputNumber.getText().toString())){
                    Toast.makeText(MainActivity_01.this,"Max 10 digit number is allowed ⚠️",Toast.LENGTH_SHORT).show();
                }
                else{
                    // get the user input from EditText and convert it to double.
                    double number =  Double.parseDouble(inputNumber.getText().toString());

                    // Intent object is created of this class Context() to MainActivity_02 class
                    Intent intent = new Intent(MainActivity_01.this, MainActivity_02.class);

                    // now by putExtra method put the value in key-value pair key.
                    // 'input_msg' is the key and 'number' is its value. By 'input_msg' we receive our value.
                    intent.putExtra("input_msg",number);

                    // start the Intent with requestCode: 1
                    startActivityForResult(intent,1);
                }
            }
            // function to check the size of the string.
            public boolean isValid(String s){
                int len = 0;
                for(int i= 0;i<s.length();i++){
                    if(s.charAt(i) == '.'){
                        break;
                    }
                    len++;
                }
                if(len>10){
                    return true;
                }
                return  false;
            }
        });
    }

    // step_04: Receive the resultant number from second Activity to the first Activity and Display in UI.

    // Override the method to handle the result from a started activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data); // Call the superclass implementation of the same method

        // variable declaration to  fetch UI elements
        TextView resultNo = findViewById(R.id.result_id);
        EditText inputNumber = findViewById(R.id.inputNo_id);

        // Check if the result is from the specified activity
        if(requestCode == 1){
            if(resultCode == RESULT_OK){ // Check if the result indicates success (OK)
                assert data != null; // data should not be null.

                // receive the value by getDoubleExtra() method and
                // key must be same which is send by second activity.
                double res = data.getDoubleExtra("output_msg",0.0);


                // edge_case_04: check : if the number has decimal value then pass the same double value
                // else convert the double to int.  e.g ->6.0*2 = 12.0 converts to 12 but 6.01*2 = 12.02 .
                if(res%1 != 0){
                    resultNo.setText("Result 🥳 "+res);// display the number into textView
                    inputNumber.getText().clear(); // clearing the EditText field to input another number.
                }
                else{
                    resultNo.setText("Result 🥳 "+(int)res);
                    inputNumber.getText().clear();
                }
            }
            // edge_case_05: if the user doesn't press the back button of second Activity but press the back button of phone screen
            // then result is cancelled.
            if(resultCode == RESULT_CANCELED){
                resultNo.setText("No Action Taken ⚠️");
                inputNumber.getText().clear();
            }
        }
    }
}
