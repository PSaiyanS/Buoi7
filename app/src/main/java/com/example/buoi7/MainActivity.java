package com.example.buoi7;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnAdd, btnDelete, btnUpdate;
    EditText edtTitle, edtContent, edtDate,edtID;
    ArrayList<ToDo> listToDo;
    ToDoAdapter listToDoAdapter;
    ListView listTodo;
    ToDo selectedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.btnAdd);
        btnDelete = findViewById(R.id.btnDelete);
        btnUpdate = findViewById(R.id.btnUpdate);
        edtTitle = findViewById(R.id.edtTitle);
        edtContent = findViewById(R.id.edtContent);
        edtDate = findViewById(R.id.edtDate);
        edtID = findViewById(R.id.edtID);
        DbHelper dbHelper = new DbHelper(this);
        ToDoDAO toDoDao = new ToDoDAO(dbHelper);
        listToDo = toDoDao.getListTodo();
        listToDoAdapter = new ToDoAdapter(this, listToDo);

        listTodo = findViewById(R.id.lstToDo);
        listTodo.setAdapter(listToDoAdapter);
        selectedItem = new ToDo();

        listTodo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ToDo selectedToDo = listToDo.get(position);
                edtTitle.setText(selectedToDo.getTitle());
                edtContent.setText(selectedToDo.getContent());
                edtDate.setText(selectedToDo.getDate());
                edtID.setText(String.valueOf(selectedToDo.getID()));
                selectedItem = selectedToDo;

            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = edtTitle.getText().toString().trim();
                String content = edtContent.getText().toString().trim();
                String date = edtDate.getText().toString().trim();

                if (title.isEmpty() || content.isEmpty() || date.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                } else {
                    ToDo newToDo = new ToDo();
                    newToDo.setTitle(title);
                    newToDo.setContent(content);
                    newToDo.setDate(date);

                    ToDoDAO toDoDAO = new ToDoDAO(new DbHelper(getApplicationContext()));
                    boolean isAdded = toDoDAO.addToDo(newToDo);

                    if (isAdded) {
                        Toast.makeText(getApplicationContext(), "Đã thêm công việc mới!", Toast.LENGTH_SHORT).show();
                        listToDo.add(newToDo);
                        edtTitle.setText("");
                        edtContent.setText("");
                        edtDate.setText("");
                        listToDoAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getApplicationContext(), "Thêm công việc thất bại!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToDoDAO toDoDAO = new ToDoDAO(new DbHelper(getApplicationContext()));
                boolean isDeleted = toDoDAO.deleteToDo(selectedItem.getID());
                if (isDeleted) {
                    Toast.makeText(getApplicationContext(), "Đã xoá công việc!", Toast.LENGTH_SHORT).show();
                    listToDo.remove(selectedItem);
                    listToDoAdapter.notifyDataSetChanged();
                    selectedItem = new ToDo();
                    edtTitle.setText("");
                    edtContent.setText("");
                    edtDate.setText("");

                } else {
                    Toast.makeText(getApplicationContext(), "Xoá công việc thất bại!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToDoDAO toDoDAO = new ToDoDAO(new DbHelper(getApplicationContext()));
                selectedItem.setTitle(edtTitle.getText().toString());
                selectedItem.setContent(edtContent.getText().toString());
                selectedItem.setDate(edtDate.getText().toString());
                boolean isUpdated = toDoDAO.updateToDo(selectedItem);
                if (isUpdated) {
                    Toast.makeText(getApplicationContext(), "Đã sửa công việc!", Toast.LENGTH_SHORT).show();
                    listToDoAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getApplicationContext(), "Sửa công việc thất bại!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
