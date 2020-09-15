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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SetupActivity extends AppCompatActivity {

    EditText email;
    EditText password;
    ProgressBar progressBar;
    Button cancel;
    Button accept;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.pass);
        cancel = (Button) findViewById(R.id.cancel);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        accept = (Button) findViewById(R.id.accept);
        setTitle("Setup");
        email.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgress();
                if(!email.getText().toString().isEmpty()){

                    FirebaseAuth.getInstance().getCurrentUser().updatePassword(email.getText().toString());

                    if(!password.getText().toString().isEmpty()){
                        FirebaseAuth.getInstance().getCurrentUser().updatePassword(password.getText().toString());
                    }

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
                    showAlert("Please, fill email field.", "Alert!.").show();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
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
        password.setEnabled(false);
        accept.setEnabled(false);
        cancel.setEnabled(false);

    }
    private void hideProgress(){
        progressBar.setVisibility(View.INVISIBLE);
        email.setEnabled(true);
        password.setEnabled(true);
        accept.setEnabled(true);
        cancel.setEnabled(true);
    }
}
