package com.dovantuan.tuandvph31763_ass.Authentication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dovantuan.tuandvph31763_ass.DAO.DaoNguoiDung;
import com.dovantuan.tuandvph31763_ass.MainActivity;
import com.dovantuan.tuandvph31763_ass.R;
import com.google.android.material.snackbar.Snackbar;

public class LogActivity extends AppCompatActivity {
    TextView btn_dangki,btn_forgot_pass;
    Button btn_login;
    EditText username_log, password_log;
    DaoNguoiDung daoNguoiDung;
    CheckBox cbo_luu;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username_log = findViewById(R.id.edt_username_log);
        password_log = findViewById(R.id.edt_password_log);

        cbo_luu = findViewById(R.id.cbo_luu);

        btn_login = findViewById(R.id.btn_login);
        btn_dangki = findViewById(R.id.btn_dangki);
        btn_forgot_pass = findViewById(R.id.btn_forgot_pass);

        daoNguoiDung = new DaoNguoiDung(this);

        // Khởi tạo SharedPreferences
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        String password = intent.getStringExtra("password");

        username_log.setText(username); // Điền username vào trường đăng nhập
        password_log.setText(password); // Điền pass vào trường đăng nhập

        btn_dangki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = username_log.getText().toString();
                String password = password_log.getText().toString();

                boolean check = daoNguoiDung.CheckLogin1(username, password);

                if (username.equals("")) {
                    Snackbar.make(v, "Tên Đăng Nhập Không Được Để Trống !", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                } else if (password.equals("")) {
                    Snackbar.make(v, "Mật Khẩu Không Được Để Trống !", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                } else {
                    if (check) {
                        Toast.makeText(LogActivity.this, "Đăng Nhập Thành Công", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LogActivity.this, MainActivity.class));

                    } else {
                        Snackbar.make(v, "Đăng Nhập Thất Bại Kiểm Tra Lại!", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    }
                }
            }
        });
        btn_forgot_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogActivity.this, Forgot_password_activity.class));
            }
        });
    }
}