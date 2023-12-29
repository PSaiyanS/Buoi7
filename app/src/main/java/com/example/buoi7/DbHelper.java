package com.example.buoi7;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    public DbHelper(Context context) {
            super(context, "TodoDatabase", null, 5);
        }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Tạo câu lệnh tạo bảng
        String sql = "CREATE TABLE TODO (ID INTEGER PRIMARY KEY AUTOINCREMENT, "+
                "TITLE TEXT, CONTENT TEXT, DATE TEXT)";
        sqLiteDatabase.execSQL(sql);
        // Tạo câu lệnh thêm dữ liệu vào database
        String data = "INSERT INTO TODO VALUES(1, 'Học Java', 'Học Java cơ bản', '27/2/2023'), " + "(2, 'Học React Native', 'Học React Native cơ bản', '24/3/2023')," +
                "(3, 'Học KotLin', 'Học kotlin cơ bản', '1/4/2023'  )";
        sqLiteDatabase.execSQL(data);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Kiểm tra phiên bản Nếu trùng thì xoá bảng và tạo lại
        if(i!= 1)
        {
            // Xóa bảng nếu tồn tại
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS TODO");
            // Gọi Lại hàm onCreate
            onCreate(sqLiteDatabase);
        }

    }
}
