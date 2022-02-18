package com.coursebook;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.utils.PrefrenceManager;
import com.utils.UserModel;

public class RegistrationActivity extends BaseActivity implements View.OnClickListener {
    private EditText et_userreg_name;
    private EditText et_userreg_email;
    private EditText et_userreg_password;
    private Button btn_userreg_register;
    private Button btn_userreg_back;

    private FirebaseAuth mAuth;

    private String TAG = RegistrationActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
        initContext(RegistrationActivity.this, RegistrationActivity.this);
        mAuth = FirebaseAuth.getInstance();

        findViews();
    }

    @Override
    public void onClick(View v) {
        if (v == btn_userreg_register) {
            if (isValidated()) {
                RegisterUser();
            }
        } else if (v == btn_userreg_back) {
            finishActivity();
        }
    }

    private void RegisterUser() {
        if (isInternetAvailable()) {
            mAuth.createUserWithEmailAndPassword(et_userreg_email.getText().toString(), et_userreg_password.getText().toString())
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

                                PrefrenceManager.setUserModel(RegistrationActivity.this, userModel);
                                PrefrenceManager.setIsLogedin(RegistrationActivity.this, true);


                                Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                                startActivity(intent);
                                RegistrationActivity.this.finish();
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(RegistrationActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private boolean isValidated() {
        boolean validated = true;
        if (et_userreg_name.getText().toString().isEmpty()) {
            Toast.makeText(RegistrationActivity.this, "Please Enter Name", Toast.LENGTH_LONG).show();
            validated = false;
        } else if (et_userreg_email.getText().toString().isEmpty()) {
            Toast.makeText(RegistrationActivity.this, "Please enter email", Toast.LENGTH_LONG).show();
            validated = false;
        } else if (et_userreg_password.getText().toString().isEmpty()) {
            Toast.makeText(RegistrationActivity.this, "PLease enter Password", Toast.LENGTH_LONG).show();
            validated = false;
        }
        return validated;
    }

    private void findViews() {
        et_userreg_name = findViewById(R.id.et_userreg_name);
        et_userreg_email = findViewById(R.id.et_userreg_email);
        et_userreg_password = findViewById(R.id.et_userreg_password);
        btn_userreg_register = (Button) findViewById(R.id.btn_userreg_register);
        btn_userreg_back = (Button) findViewById(R.id.btn_userreg_back);

        btn_userreg_register.setOnClickListener(this);
        btn_userreg_back.setOnClickListener(this);
    }

}





