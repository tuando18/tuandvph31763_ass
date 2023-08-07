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


public class ResetPasswordActivity extends AppCompatActivity {

    EditText edt_new_password, edt_confirm_password;
    Button btn_reset_password;
    DaoNguoiDung daoNguoiDung;
    String email, username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        edt_new_password = findViewById(R.id.edt_new_password);
        edt_confirm_password = findViewById(R.id.edt_confirm_password);
        btn_reset_password = findViewById(R.id.btn_reset_password);

        daoNguoiDung = new DaoNguoiDung(this);

        // Nhận email và username từ hoạt động EmailVerificationActivity
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        username = intent.getStringExtra("username");

        btn_reset_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPassword = edt_new_password.getText().toString();
                String confirmPassword = edt_confirm_password.getText().toString();

                // Kiểm tra mật khẩu mới và mật khẩu xác nhận
                if (!newPassword.equals(confirmPassword)) {
                    Toast.makeText(ResetPasswordActivity.this, "Mật khẩu không khớp.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Cập nhật mật khẩu mới vào CSDL
                boolean isPasswordUpdated = daoNguoiDung.updatePassword(username, newPassword);

                if (isPasswordUpdated) {
//                    Toast.makeText(ResetPasswordActivity.this, "Đổi mật khẩu thành công!", Toast.LENGTH_SHORT).show();
//                    // Chuyển hướng về hoạt động đăng nhập (LogActivity)
//                    Intent loginIntent = new Intent(ResetPasswordActivity.this, LogActivity.class);
//                    startActivity(loginIntent);
//                    finish(); // Kết thúc hoạt động ResetPasswordActivity

                    Toast.makeText(ResetPasswordActivity.this, "Đổi mật khẩu thành công!", Toast.LENGTH_SHORT).show();
                    // Chuyển hướng về hoạt động đăng nhập (LogActivity) và gửi thông tin đăng nhập
                    Intent loginIntent = new Intent(ResetPasswordActivity.this, LogActivity.class);
                    loginIntent.putExtra("username", username); // Gửi username về để điền vào trường đăng nhập
                    loginIntent.putExtra("password", newPassword); // Gửi mật khẩu mới về để điền vào trường đăng nhập
                    startActivity(loginIntent);
                    finish(); // Kết thúc hoạt động ResetPasswordActivity

                } else {
                    Toast.makeText(ResetPasswordActivity.this, "Đổi mật khẩu thất bại. Vui lòng thử lại.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}