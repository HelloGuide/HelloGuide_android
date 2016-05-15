package com.example.androidchoi.helloguide;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidchoi.helloguide.Manager.MyApplication;
import com.example.androidchoi.helloguide.Manager.NetworkManager;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {
    public static final String MESSAGE_SUCCESS = "login success";
    public static final String MESSAGE_FAIL = "login fail";
    public static final String MESSAGE_NO_USER = "사용자가 없습니다.";
    public static final String MESSAGE_DIFF_PW = "비밀번호가 다릅니다.";
    public static final String MESSAGE_MISSING = "Missing credentials";
    public static final String MESSAGE_DUPLICATION = "중복된 id 입니다.";

    TextView mTextSignUp;
    TextView mTextFailMessage;
    EditText mEditEmail;
    EditText mEditPassWord;
    RelativeLayout mLayoutLogin;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        mLayoutLogin = (RelativeLayout) view.findViewById(R.id.layout_login);
        mTextFailMessage = (TextView) view.findViewById(R.id.text_login_fail_message);
        Button btn = (Button) view.findViewById(R.id.btn_sign_in);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard();
                logIn();
            }
        });

        mTextSignUp = (TextView) view.findViewById(R.id.text_sign_up);
        mTextSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard();
                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.login_container, new InputUserInfoFragment()).addToBackStack(null).commit();
            }
        });
        mEditEmail = (EditText) view.findViewById(R.id.editText_login_email);
        mEditPassWord = (EditText) view.findViewById(R.id.editText_login_password);
        return view;
    }

    public void logIn() {
        final String email = mEditEmail.getText().toString();
        final String password = mEditPassWord.getText().toString();
        NetworkManager.getInstance().login(email, password, new NetworkManager.OnResultListener<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("result", result);
                if(result.equals(MESSAGE_SUCCESS)){
                    startActivity(new Intent(getContext(), MainActivity.class));
                    getActivity().finish();
                }else{
                    Toast.makeText(MyApplication.getContext(), "입력 정보가 잘못 되었습니다.", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFail(String error) {
                Log.i("error : ", error);
                Toast.makeText(MyApplication.getContext(), "로그인 요청에 실패하였습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void hideSoftKeyboard(){
        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
    }


}
