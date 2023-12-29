package com.example.buoi7;

import static java.util.Collections.addAll;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ToDoAdapter extends BaseAdapter {

    private final ArrayList<ToDo> toDoList;
    private final LayoutInflater inflater;

    public ToDoAdapter(Context context, ArrayList<ToDo> list) {
        toDoList = list;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return toDoList.size();
    }

    @Override
    public Object getItem(int position) {
        return toDoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.todo_list_item, parent, false);
            holder = new ViewHolder();
            holder.txtTitle = convertView.findViewById(R.id.txtTitle);
            holder.txtContent = convertView.findViewById(R.id.txtContent);
            holder.txtDate = convertView.findViewById(R.id.txtDate);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ToDo currentToDo = toDoList.get(position);

        holder.txtTitle.setText(currentToDo.getTitle());
        holder.txtContent.setText(currentToDo.getContent());
        holder.txtDate.setText(currentToDo.getDate());

        return convertView;
    }

    static class ViewHolder {
        TextView txtTitle;
        TextView txtContent;
        TextView txtDate;
    }
}
