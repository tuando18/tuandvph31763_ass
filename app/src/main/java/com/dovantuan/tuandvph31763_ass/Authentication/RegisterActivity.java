package com.dovantuan.tuandvph31763_ass.Authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dovantuan.tuandvph31763_ass.DAO.DaoNguoiDung;
import com.dovantuan.tuandvph31763_ass.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    EditText edt_fullname, edt_email, edt_username, edt_password, edt_password2;
    TextView btn_log;
    Button btn_register;
    Pattern checkname, checkemail, checkusername, checkpassword;
    DaoNguoiDung daoNguoiDung;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        edt_fullname = findViewById(R.id.edt_fullname_register);
        edt_email = findViewById(R.id.edt_email_register);
        edt_username = findViewById(R.id.edt_username_register);
        edt_password = findViewById(R.id.edt_password_register);
        edt_password2 = findViewById(R.id.edt_password2_register);
        btn_register = findViewById(R.id.btn_register);
        btn_log = findViewById(R.id.tvdangNhap);

        daoNguoiDung = new DaoNguoiDung(this);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String full_name = edt_fullname.getText().toString();
                String email = edt_email.getText().toString();
                String username = edt_username.getText().toString();
                String password = edt_password.getText().toString();
                String password2 = edt_password2.getText().toString();

                checkname = Pattern.compile("^[a-zA-Z\\p{L} ]+$");
                checkemail = Pattern.compile("^[a-zA-Z0-9]+@gmail.com$");
                checkusername = Pattern.compile("^[a-zA-Z0-9]{6,}$");
                checkpassword = Pattern.compile("^[a-zA-Z0-9]{8,}$");

                //check full name
                if (full_name.equals("")) {
                    Snackbar.make(v, "Không Được Để Trống Họ Và Tên!", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    return;
                } else if (checkname.matcher(full_name).find()) {
                } else {
                    Snackbar.make(v, "Họ Và Tên Không Được Có Kí Tự Đặc Biệt Và Số!", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    return;
                }

                //check email
                if (email.equals("")) {
                    Toast.makeText(RegisterActivity.this, "Không Được Để Trống Email!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (checkemail.matcher(email).find()) {
                } else {
                    Toast.makeText(RegisterActivity.this, "Email Không Hợp Lệ!", Toast.LENGTH_SHORT).show();
                    return;
                }

                //check username
                if (username.equals("")) {
                    Toast.makeText(RegisterActivity.this, "Không Được Để Trống Tên Đăng Nhập!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (checkusername.matcher(username).find()) {
                } else {
                    Toast.makeText(RegisterActivity.this, "Tên Đăng Nhập Quá Ngắn!", Toast.LENGTH_SHORT).show();
                    return;
                }

                //check password
                if (password.equals("")) {
                    Toast.makeText(RegisterActivity.this, "Không Được Để Trống Tên Đăng Nhập!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (checkpassword.matcher(password).find()) {
                } else {
                    Toast.makeText(RegisterActivity.this, "Mật Khẩu phải Trên 8 ký Tự", Toast.LENGTH_SHORT).show();
                    return;
                }

                // check password 2
                if (!password.equals(password2)) {
                    Toast.makeText(RegisterActivity.this, "Mật Khẩu Nhập Lại Không Khớp", Toast.LENGTH_SHORT).show();
                } else {
                    boolean check = daoNguoiDung.register(full_name, email, username, password);
                    if (check) {
                        Toast.makeText(RegisterActivity.this, "Đăng Kí Thành Công", Toast.LENGTH_SHORT).show();
                        Intent loginIntent = new Intent(RegisterActivity.this, LogActivity.class);
                        loginIntent.putExtra("username", username); // Gửi username về để điền vào trường đăng nhập
                        loginIntent.putExtra("password", password); // Gửi mật khẩu mới về để điền vào trường đăng nhập
                        startActivity(loginIntent);
                        finish();
                    } else {
                        Snackbar.make(v, "Đăng Kí Thất Bại,Vui Lòng Kiểm Tra Lại!", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    }
                }

            }
        });
        btn_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LogActivity.class));
            }
        });
    }
}