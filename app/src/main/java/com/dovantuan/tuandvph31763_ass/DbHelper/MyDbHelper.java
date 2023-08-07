package com.dovantuan.tuandvph31763_ass.DbHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDbHelper extends SQLiteOpenHelper {
    static String DB_NAME = "Quanly_CV.db";
    static  int DB_VERSION = 1;

    public MyDbHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql_ND = "CREATE TABLE tb_nguoidung(name TEXT (30),"+
                " email TEXT (50)," +
                " username TEXT(50) PRIMARY KEY," +
                " password TEXT(50))";
        db.execSQL(sql_ND);


        String sql_CV = "CREATE TABLE tb_congviec ( id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " name TEXT (50) UNIQUE NOT NULL," +
                " content TEXT(50)," +
                " status INTEGER," +
                " start TEXT(15)," +
                " ends TEXT(15))";
        db.execSQL(sql_CV);


        String sql_cv = "INSERT INTO tb_congviec VALUES(1,'LAP TRINH JAVA','hov java that de','moi tao','12/2/2023','11/2/2023')";
        db.execSQL(sql_cv);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
         if(oldVersion != newVersion){
            db.execSQL("DROP TABLE IF EXISTS tb_nguoidung");
            db.execSQL("DROP TABLE IF EXISTS tb_congviec");
            onCreate(db);
         }
    }
}
