package com.example.finaltest.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.finaltest.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AuthActivity extends AppCompatActivity {

    Button loginButton;
    Button registerButton;
    EditText editTextEmail;
    EditText editTextPass;
    ProgressBar progressBar;
    TextView forgotPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        loginButton = (Button) findViewById(R.id.buttonLogin);
        registerButton = (Button) findViewById(R.id.buttonRegister);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPass = (EditText) findViewById(R.id.editTextPass);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        forgotPass = (TextView) findViewById(R.id.forgotPass);
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        forgotPass.setPaintFlags(paint.getColor());
        forgotPass.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        setTitle("Authentication");

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgress();
                if(!editTextEmail.getText().toString().isEmpty() && !editTextPass.getText().toString().isEmpty()){
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                            editTextEmail.getText().toString(), editTextPass.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            hideProgress();
                            if(task.isSuccessful()){
                                AlertDialog alert = showAlert("User registered successfully", "Done!");
                                        alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                            @Override
                                            public void onDismiss(DialogInterface dialogInterface) {
                                                showHome();
                                            }
                                        });
                                        alert.show();
                            }else{
                                showAlert(null, null).show();
                            }
                        }
                    });
                }
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgress();
                if(!editTextEmail.getText().toString().isEmpty() && !editTextPass.getText().toString().isEmpty()){
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(
                            editTextEmail.getText().toString(), editTextPass.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            hideProgress();
                            if(task.isSuccessful()){
                                AlertDialog alert = showAlert("User login successfully", "Done!");
                                        alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                    @Override
                                    public void onDismiss(DialogInterface dialogInterface) {
                                        showHome();
                                    }
                                });
                                        alert.show();
                            }else{
                                showAlert(null, null).show();
                            }
                        }
                    });
                }
            }
        });

    }

    private AlertDialog showAlert(String message, String title){
        return new AlertDialog.Builder(this).setTitle(title.isEmpty()?"Error!.":title)
                .setPositiveButton("Accept", null)
                .setMessage(message.isEmpty()?"An authentication error has occurred.":message).create();
    }

    private void showHome(){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    private void showProgress(){
        progressBar.setVisibility(View.VISIBLE);
        loginButton.setEnabled(false);
        registerButton.setEnabled(false);
        editTextEmail.setEnabled(false);
        editTextPass.setEnabled(false);
        forgotPass.setEnabled(false);
    }
    private void hideProgress(){
        progressBar.setVisibility(View.INVISIBLE);
        loginButton.setEnabled(true);
        registerButton.setEnabled(true);
        editTextEmail.setEnabled(true);
        editTextPass.setEnabled(true);
        forgotPass.setEnabled(true);
    }
}
