package com.dovantuan.tuandvph31763_ass.Authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dovantuan.tuandvph31763_ass.DAO.DaoNguoiDung;
import com.dovantuan.tuandvph31763_ass.R;


public class Forgot_password_activity extends AppCompatActivity {
    Button btn_yes_forgotpass,btn_no_forgotpass;
    EditText edt_username_forgotpass,edt_email_forgot;
    DaoNguoiDung daoNguoiDung;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        btn_yes_forgotpass = findViewById(R.id.btn_yes_forgotpass);
        btn_no_forgotpass = findViewById(R.id.btn_no_forgotpass);
        edt_username_forgotpass = findViewById(R.id.edt_username_forgotpass);
        edt_email_forgot = findViewById(R.id.edt_email_forgotpass);
        daoNguoiDung = new DaoNguoiDung(this);


        btn_no_forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_yes_forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edt_email_forgot.getText().toString();
                String username = edt_username_forgotpass.getText().toString();

                // Kiểm tra email và username trong CSDL
                boolean isValidCombination = daoNguoiDung.CheckLogin(email, username);

                if (isValidCombination) {
                    // Nếu email và username đúng, chuyển sang hoạt động Đổi mật khẩu mới
                    Intent resetPasswordIntent = new Intent(Forgot_password_activity.this, ResetPasswordActivity.class);
                    resetPasswordIntent.putExtra("email", email);
                    resetPasswordIntent.putExtra("username", username);
                    startActivity(resetPasswordIntent);
                } else {
                    Toast.makeText(Forgot_password_activity.this, "Email hoặc username không đúng.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}