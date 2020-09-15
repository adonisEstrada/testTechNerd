package com.example.finaltest.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.finaltest.R;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    Button buttonAdd;
    Button buttonList;
    Button buttonSetup;
    Button buttonLogOut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        buttonAdd = (Button) findViewById(R.id.buttonAdd);
        buttonList = (Button) findViewById(R.id.buttonList);
        buttonSetup = (Button) findViewById(R.id.buttonSetup);
        buttonLogOut = (Button) findViewById(R.id.buttonLogOut);
        setTitle("Home");

        buttonSetup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSetupActivity();
            }
        });
        buttonList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showListActivity();
            }
        });
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddActivity();
            }
        });
        buttonLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                onBackPressed();
            }
        });
    }

    public void showAddActivity(){
        Intent intent = new Intent(this, AddActivity.class);
        startActivity(intent);
    }
    public void showListActivity(){
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }
    public void showSetupActivity(){
        Intent intent = new Intent(this, SetupActivity.class);
        startActivity(intent);
    }
}
