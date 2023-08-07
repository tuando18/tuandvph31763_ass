package com.dovantuan.tuandvph31763_ass.DAO;

import static android.content.ContentValues.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.dovantuan.tuandvph31763_ass.DTO.DtoCV;
import com.dovantuan.tuandvph31763_ass.DbHelper.MyDbHelper;

import java.util.ArrayList;

public class DaoCV {
    private final MyDbHelper dbHelper;

    public DaoCV(Context context) {
        this.dbHelper = new MyDbHelper(context);

    }

    public ArrayList<DtoCV> getAll(){
        ArrayList<DtoCV>list =new ArrayList<>();
        SQLiteDatabase db =dbHelper.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery("select * from tb_congviec", null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    DtoCV cv = new DtoCV();
                    cv.setId(cursor.getInt(0));
                    cv.setName(cursor.getString(1));
                    cv.setContent(cursor.getString(2));
                    cv.setStatus(cursor.getString(3));
                    cv.setStart(cursor.getString(4));
                    cv.setEnd(cursor.getString(5));
                    list.add(cv);
                    cursor.moveToNext();
                }
            }
        } catch (Exception e) {
            Log.i(TAG, "Lỗi", e);
        }
        return list;
    }
    public boolean addRow(DtoCV dtoCV) {
        Log.d("zzzz", "addToDo: chưa add" + getAll().size());

        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.beginTransaction();

        ContentValues values = new ContentValues();
        values.put("name", dtoCV.getName());
        values.put("content", dtoCV.getContent());
        values.put("status", dtoCV.getStatus());
        values.put("start", dtoCV.getStart());
        values.put("ends",dtoCV.getEnd());
        database.setTransactionSuccessful();
        database.endTransaction();

        long check = database.insert("tb_congviec", null,values);

        return check != -1;
    }

    public boolean removeRow(int id){
        SQLiteDatabase sqLiteDatabase =dbHelper.getWritableDatabase();
        int row = sqLiteDatabase.delete("tb_congviec", "id = ?", new String[]{String.valueOf(id)});
        return  row!=-1;
    }
    public  boolean updateRow( DtoCV dtoCV ){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", dtoCV.getName());
        values.put("content", dtoCV.getContent());
        values.put("status", dtoCV.getStatus());
        values.put("start", dtoCV.getStart());
        values.put("ends",dtoCV.getEnd());
        int check = database.update("tb_congviec", values, "id = ?", new String[]{String.valueOf(dtoCV.getId())});
        return  check!=-1;
    }
}
