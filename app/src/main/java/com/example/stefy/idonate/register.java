package com.example.stefy.idonate;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class register extends AppCompatActivity {

    private EditText editTextUsername, editTextPassword, editTextPassword2,editTextFirstName, editTextLastName, editTextPhoneNumber, editTextEmail;
    private TextView matchPasswordsText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle("Εγγραφή");
        editTextUsername=(EditText)findViewById(R.id.editTextUsername);
        editTextPassword=(EditText)findViewById(R.id.editTextPassword);
        editTextPassword2=(EditText)findViewById(R.id.editTextPassword2);
        editTextFirstName=(EditText)findViewById(R.id.editTextFirstName);
        editTextLastName=(EditText)findViewById(R.id.editTextLastName);
        editTextPhoneNumber=(EditText)findViewById(R.id.editTextPhoneNumber);
        editTextEmail=(EditText)findViewById(R.id.editTextEmail);
        matchPasswordsText=(TextView)findViewById(R.id.matchPasswordsText);
        editTextPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String pw1=editTextPassword.getText().toString();
                String pw2=editTextPassword2.getText().toString();
                if(pw1.equals(pw2)){
                    matchPasswordsText.setText("Οι κωδικοί ταιριάζουν!");
                }
                else {
                    matchPasswordsText.setText("Οι κωδικοί δεν ταιριάζουν");
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editTextPassword2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String pw1=editTextPassword.getText().toString();
                String pw2=editTextPassword2.getText().toString();
                if(pw1.equals(pw2)){
                    matchPasswordsText.setText("Οι κωδικοί ταιριάζουν!");
                }
                else {
                    matchPasswordsText.setText("Οι κωδικοί δεν ταιριάζουν");
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    public void OnRegister(View view){
        String username = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();
        String firstname = editTextFirstName.getText().toString();
        String lastname = editTextLastName.getText().toString();
        String phonenumber = editTextPhoneNumber.getText().toString();
        String email = editTextEmail.getText().toString();
        AlertDialog a = new AlertDialog.Builder(register.this).create();

        if((username.equals(""))||(password.equals(""))||(firstname.equals(""))||
                (lastname.equals(""))||(phonenumber.equals(""))||(email.equals(""))) {
            a.setTitle("Σφάλμα");
            a.setMessage("Παρακαλώ συμπληρώστε όλα τα πεδία");
            a.show();
        }
        else{
            if (matchPasswordsText.getText().toString().equals("Οι κωδικοί δεν ταιριάζουν")) {

                a.setTitle("Σφάλμα");
                a.setMessage("Οι κωδικοί δεν ταιριάζουν");
                a.show();
            }
            else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                a.setTitle("Σφάλμα");
                a.setMessage("Μη έγκυρο e-mail");
                a.show();
            }
            else
            {   String type = "signup";
                BackgroundWorker bgw = new BackgroundWorker(this);
                bgw.execute(type, username, password, firstname, lastname, phonenumber, email);
            }
        }
    }
}