package com.study.android.kswtest;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    //define view objects
    EditText editTextEmail;
    EditText editTextPassword;
    Button buttonSignin;
    TextView textviewSingin;
    TextView textviewMessage;
    TextView textviewFindPassword;
    ProgressDialog progressDialog;
    //define firebase object
    FirebaseAuth firebaseAuth;
    private BackPressCloseHandler backPressCloseHandler;

    CheckBox checkBox;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_login);

        //initializig firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        //initializing views

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        textviewSingin= findViewById(R.id.textViewSignin);
        textviewMessage = findViewById(R.id.textviewMessage);
        textviewFindPassword = findViewById(R.id.textViewFindpassword);
        buttonSignin = findViewById(R.id.buttonSignup);
        progressDialog = new ProgressDialog(this);
        backPressCloseHandler = new BackPressCloseHandler(this);

        //button click event
        buttonSignin.setOnClickListener(this);
        textviewSingin.setOnClickListener(this);
        textviewFindPassword.setOnClickListener(this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled( true );

        SharedPreferences pref = getSharedPreferences("login", Activity.MODE_PRIVATE);
        editor = pref.edit();

        String id = pref.getString("id", "");
        editTextEmail.setText(id);

        checkBox = findViewById(R.id.checkBox);
        if(editTextEmail.getText().toString().equals("")) {
            checkBox.setChecked(false);
        } else {
            checkBox.setChecked(true);
        }

        checkBox.setOnClickListener(new CheckBox.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (((CheckBox)v).isChecked()) {
                    String sId = editTextEmail.getText().toString();
                    String sPwd = editTextPassword.getText().toString();

                    editor.putString("id",sId);
                    editor.commit();
                } else {
                    editor.clear();
                    editor.commit();
                }
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home :
                finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //firebase userLogin method
    private void userLogin(){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "email을 입력해 주세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "password를 입력해 주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("로그인중입니다. 잠시 기다려 주세요...");
        progressDialog.show();

        //logging in the user
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()) {
                            finish();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else {
                            //Toast.makeText(getApplicationContext(), "로그인 실패!", Toast.LENGTH_LONG).show();

                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                            builder.setTitle("로그인 실패");
                            builder.setMessage("Email 또는 password가 맞지 않습니다.");
                            builder.setPositiveButton("확인",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    });
                            builder.show();
                            //textviewMessage.setText("로그인 실패 유형\n - password가 맞지 않습니다.\n -서버에러");
                            editTextPassword.setText("");
                        }
                    }
                });
    }
    @Override
    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }

    @Override
    public void onClick(View view) {
        if(view == buttonSignin) {
            userLogin();
        }
        if(view == textviewSingin) {
            finish();
            startActivity(new Intent(this, SignUpActivity.class));
        }
        if(view == textviewFindPassword) {
            finish();
            startActivity(new Intent(this, FindActivity.class));
        }
    }
}