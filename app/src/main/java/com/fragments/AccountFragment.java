package com.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.coursebook.R;
import com.coursebook.SplashActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.utils.PrefrenceManager;

public class AccountFragment extends Fragment implements View.OnClickListener {
    private ImageView imageView2;
    private TextInputLayout outlinedTextFieldUserName;
    private TextInputEditText et_userreg_email;
    private Button btn_userprof_update;
    private Button btn_userprof_signout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_account, container, false);

        findViews(rootView);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            et_userreg_email.setText("" + mAuth.getCurrentUser().getEmail());
        }

        return rootView;
    }

    @Override
    public void onClick(View v) {
        if (v == btn_userprof_update) {

        } else if (v == btn_userprof_signout) {
            FirebaseAuth.getInstance().signOut();
            PrefrenceManager.clear(getActivity());
            Intent intent = new Intent(getActivity(), SplashActivity.class);
            startActivity(intent);
            getActivity().finish();
        }
    }


    private void findViews(View rootView) {
        imageView2 = (ImageView) rootView.findViewById(R.id.imageView2);
        outlinedTextFieldUserName = (TextInputLayout) rootView.findViewById(R.id.outlinedTextFieldUserName);
        et_userreg_email = (TextInputEditText) rootView.findViewById(R.id.et_userreg_email);
        btn_userprof_update = (Button) rootView.findViewById(R.id.btn_userprof_update);
        btn_userprof_signout = (Button) rootView.findViewById(R.id.btn_userprof_signout);

        btn_userprof_update.setOnClickListener(this);
        btn_userprof_signout.setOnClickListener(this);
    }
}