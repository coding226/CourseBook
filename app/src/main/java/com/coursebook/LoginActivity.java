package com.coursebook;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.utils.UserModel;
import com.utils.PrefrenceManager;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private TextInputLayout outlinedTextFieldUserID;
    private TextInputEditText et_userlogin_email;
    private TextInputLayout outlinedTextFieldPassword;
    private TextInputEditText et_userlogin_password;
    private Button btn_userlogin_login;
    private Button btn_userlogin_register;
    private FirebaseAuth mAuth;

    private String TAG = LoginActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        mAuth = FirebaseAuth.getInstance();

        findViews();
    }

    @Override
    public void onClick(View v) {
        if (v == btn_userlogin_login) {
            //hideKeyBoard(et_userlogin_password);
            if (isValidated())
                checkEmailisExist();
        } else if (v == btn_userlogin_register) {
            Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
            startActivity(intent);
        }
    }

    private void checkEmailisExist() {
        if (isInternetAvailable()) {
            mAuth.signInWithEmailAndPassword(et_userlogin_email.getText().toString(), et_userlogin_password.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();

                                UserModel userModel = new UserModel();
                                userModel.setUid(user.getUid());
                                userModel.setEmail(user.getEmail());
                                userModel.setName(user.getDisplayName());

                                PrefrenceManager.setUserModel(LoginActivity.this, userModel);
                                PrefrenceManager.setIsLogedin(LoginActivity.this, true);


                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);

                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private boolean isValidated() {
        boolean validated = true;
        if (et_userlogin_email.getText().toString().isEmpty()) {
            Toast.makeText(LoginActivity.this, "Please enter email address", Toast.LENGTH_LONG).show();
            validated = false;

        } else if (et_userlogin_password.getText().toString().isEmpty()) {
            Toast.makeText(LoginActivity.this, "Please enter password", Toast.LENGTH_LONG).show();
            validated = false;
        }
        return validated;
    }

    private void findViews() {
        outlinedTextFieldUserID = (TextInputLayout) findViewById(R.id.outlinedTextFieldUserID);
        et_userlogin_email = (TextInputEditText) findViewById(R.id.et_userlogin_email);
        outlinedTextFieldPassword = (TextInputLayout) findViewById(R.id.outlinedTextFieldPassword);
        et_userlogin_password = (TextInputEditText) findViewById(R.id.et_userlogin_password);
        btn_userlogin_login = (Button) findViewById(R.id.btn_userlogin_login);
        btn_userlogin_register = (Button) findViewById(R.id.btn_userlogin_register);

        btn_userlogin_login.setOnClickListener(this);
        btn_userlogin_register.setOnClickListener(this);
    }
}


