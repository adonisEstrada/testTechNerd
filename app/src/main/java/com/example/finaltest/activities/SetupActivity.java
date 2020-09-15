package com.example.finaltest.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.finaltest.R;

public class SetupActivity extends AppCompatActivity {

    EditText email;
    EditText password;
    EditText firstName;
    EditText lastName;
    EditText number;
    Button cancel;
    Button accept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.pass);
        firstName = (EditText) findViewById(R.id.firstName);
        lastName = (EditText) findViewById(R.id.lastName);
        number = (EditText) findViewById(R.id.number);
        cancel = (Button) findViewById(R.id.cancel);
        accept = (Button) findViewById(R.id.accept);
        setTitle("Setup");



        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
