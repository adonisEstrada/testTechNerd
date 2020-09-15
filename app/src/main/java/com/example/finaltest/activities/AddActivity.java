package com.example.finaltest.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.finaltest.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddActivity extends AppCompatActivity {

    EditText email;
    EditText firstName;
    EditText lastName;
    EditText number;
    Button cancel;
    Button accept;
    ProgressBar progressBar;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        email = (EditText) findViewById(R.id.email);
        firstName = (EditText) findViewById(R.id.firstName);
        lastName = (EditText) findViewById(R.id.lastName);
        number = (EditText) findViewById(R.id.number);
        cancel = (Button) findViewById(R.id.cancel);
        accept = (Button) findViewById(R.id.accept);
        progressBar = (ProgressBar) findViewById(R.id.progressBarAdd);
        setTitle("Add Contact");

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgress();
                if(!firstName.getText().toString().isEmpty()
                        && !number.getText().toString().isEmpty()
                        && !email.getText().toString().isEmpty()){
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("number", number.getText().toString());
                    map.put("first_name", firstName.getText().toString());
                    map.put("last_name", lastName.getText().toString());
                    db.collection("contacts").document(email.getText().toString()).set(map);
                    hideProgress();
                    AlertDialog alert =showAlert("Contact is saved successfully.", "Done!");
                    alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {
                            onBackPressed();
                        }
                    });
                    alert.show();
                }else{
                    hideProgress();
                    showAlert("Please, fill fields.", "Alert!.").show();
                }
            }
        });
    }

    private AlertDialog showAlert(String message, String title){
        if(title!=null && message!=null){
            return new AlertDialog.Builder(this).setTitle(title.isEmpty()?"Error!.":title)
                    .setPositiveButton("Accept", null)
                    .setMessage(message.isEmpty()?"An authentication error has occurred.":message).create();
        }else{
            return new AlertDialog.Builder(this).setTitle("Error!.")
                    .setPositiveButton("Accept", null)
                    .setMessage("An authentication error has occurred.").create();
        }
    }

    private void showProgress(){
        progressBar.setVisibility(View.VISIBLE);
        email.setEnabled(false);
        firstName.setEnabled(false);
        lastName.setEnabled(false);
        number.setEnabled(false);
        cancel.setEnabled(false);
        accept.setEnabled(false);
    }
    private void hideProgress(){
        progressBar.setVisibility(View.INVISIBLE);
        email.setEnabled(true);
        firstName.setEnabled(true);
        lastName.setEnabled(true);
        number.setEnabled(true);
        cancel.setEnabled(true);
        accept.setEnabled(true);
    }
}
