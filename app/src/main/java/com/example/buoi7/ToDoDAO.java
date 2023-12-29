package com.example.buoi7;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.EditText;

import java.util.ArrayList;

public class ToDoDAO {
    private final DbHelper dbHelper;

    public ToDoDAO(DbHelper dbHelper) {
        this.dbHelper = dbHelper;
    }
    public ArrayList<ToDo> getListTodo() {
        //Tạo một danh sách để add dữ liệu Todo vào
        ArrayList<ToDo> list = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        //Database bắt đầu chạy
        database.beginTransaction();
        try {
            //Tạo câu lệnh truy vấn
            Cursor cursor = database.rawQuery( "SELECT * FROM TODO", null);
            if (cursor.getCount() > 0) {

                //Nếu cursor lớn hơn không, di chuyển con trỏ lên đầu
                cursor.moveToFirst();
                //Khởi tạo vòng lặp do..while để lấy toàn bộ dữ liệu truy vấn được thêm vào danh sách
                do {
                    list.add(new ToDo(cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3)));
                } while (cursor.moveToNext());
                //Database đã chạy thành công
                database.setTransactionSuccessful();
            }
        } catch (Exception e) {
            Log.e(TAG,"getListTodo: " + e);
        } finally {
            //Kết thúc lệnh chạy
            database.endTransaction();
        }
        return list;
    }
    public boolean addToDo (ToDo toDo) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        long check = -1; // Khởi tạo giá trị mặc định cho biến check
        try {
            database.beginTransaction(); // Bắt đầu giao dịch
            //Kiểm tra tồn tại
            Cursor cursor = database.rawQuery("SELECT * FROM TODO WHERE TITLE = ? AND CONTENT = ? AND DATE = ?", new String[]{toDo.getTitle(), toDo.getContent(), toDo.getDate()});
            if (cursor.getCount() > 0) {
                // Đã tồn tại dữ liệu với tiêu đề tương tự, không thêm mới
                return false;
            }
            ContentValues values = new ContentValues();
            values.put("TITLE", toDo.getTitle());
            values.put("CONTENT", toDo.getContent());
            values.put("DATE", toDo.getDate());
            check = database.insert("TODO", null, values);
            database.setTransactionSuccessful(); // Đánh dấu giao dịch thành công
        } catch (Exception e) {
            // Xử lý ngoại lệ trong quá trình thêm dữ liệu
            Log.e(TAG, "Error while adding ToDo: " + e.getMessage());
        } finally {
            // Kết thúc giao dịch và đóng kết nối cơ sở dữ liệu
            if (database != null) {
                database.endTransaction();
                database.close();
            }
        }
        return check != -1;
    }
    public boolean deleteToDo (int todoID) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        int deletedRows = 0; // Số hàng bị xóa
        try {
            database.beginTransaction(); // Bắt đầu giao dịch
            // Xóa To do
            deletedRows = database.delete("TODO", "ID = ?", new String[]{String.valueOf(todoID)});
            database.setTransactionSuccessful(); // Đánh dấu giao dịch thành công
        } catch (Exception e) {
            // Xử lý ngoại lệ trong quá trình xóa dữ liệu
            Log.e(TAG, "Error while deleting ToDo: " + e.getMessage());
        } finally {
            // Kết thúc giao dịch và đóng kết nối cơ sở dữ liệu
            if (database != null) {
                database.endTransaction();
                database.close();
            }
        }
        return deletedRows > 0; // Trả về true nếu có ít nhất một hàng đã bị xóa
    }
    public boolean updateToDo (ToDo toDo) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        int updateRows = 0; // Số hàng sửa
        try {
            database.beginTransaction(); // Bắt đầu giao dịch
            // UPDATE To do
            ContentValues values = new ContentValues();
            values.put("TITLE", toDo.getTitle());
            values.put("CONTENT", toDo.getContent());
            values.put("DATE", toDo.getDate());
            updateRows = database.update("TODO", values, "ID= ? ", new String[]{String.valueOf(toDo.getID())});
            database.setTransactionSuccessful(); // Đánh dấu giao dịch thành công
        } catch (Exception e) {
            // Xử lý ngoại lệ trong quá trình xóa dữ liệu
            Log.e(TAG, "Error while deleting ToDo: " + e.getMessage());
        } finally {
            // Kết thúc giao dịch và đóng kết nối cơ sở dữ liệu
            if (database != null) {
                database.endTransaction();
                database.close();
            }
        }
        return updateRows > 0; // Trả về true nếu có ít nhất một hàng đã bị xóa
    }



}
