package com.dovantuan.tuandvph31763_ass.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.dovantuan.tuandvph31763_ass.DbHelper.MyDbHelper;


public class DaoNguoiDung {
    MyDbHelper dbHelper;
    SQLiteDatabase sqLiteDatabase;

    public DaoNguoiDung(Context context) {
        dbHelper = new MyDbHelper(context);
    }

    public boolean CheckLogin1(String username, String password) {
        sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery("SELECT * FROM tb_nguoidung WHERE username = ? AND password = ?", new String[]{username, password});
        if (c.getCount() > 0) {
            return true;
        }
        return false;
    }

    public boolean CheckLogin(String email, String username) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery("SELECT * FROM tb_nguoidung WHERE email = ? AND username = ?", new String[]{email, username});
        return c.getCount() > 0;
    }

    public boolean updatePassword(String username, String newPassword) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("password", newPassword);

        int rowsAffected = sqLiteDatabase.update("tb_nguoidung", contentValues, "username=?", new String[]{username});
        return rowsAffected > 0;
    }

    public boolean register(String name, String email, String username, String password) {
        sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("email", email);
        contentValues.put("username", username);
        contentValues.put("password", password);

        long check = sqLiteDatabase.insert("tb_nguoidung", null, contentValues);
        if (check != -1) {
            return true;
        }
        return false;
    }
}
