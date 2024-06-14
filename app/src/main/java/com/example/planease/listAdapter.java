package com.example.planease;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.example.planease.taskData;

import java.util.ArrayList;

public class listAdapter extends ArrayAdapter<taskData> {

    private sharedPreferences sharedPrefManager;
    private ArrayList<taskData> dataArrayList;
    public listAdapter(@NonNull Context context, ArrayList<taskData> dataArrayList) {
        super(context, R.layout.list_item,dataArrayList);
        this.dataArrayList = dataArrayList;
        this.sharedPrefManager = new sharedPreferences(context);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        taskData taskData = getItem(position);

        if(view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }

        TextView todoItem = view.findViewById(R.id.txt_todoname);
        ImageView edit = view.findViewById(R.id.img_edit);
        ImageView delete = view.findViewById(R.id.img_delete);

        todoItem.setText(taskData.task);
        edit.setImageResource(taskData.edit);
        delete.setImageResource(taskData.delete);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditItemDialog(position, taskData);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdeletetemDialog(position, taskData);
            }
        });

        return view;
    }

    private void showEditItemDialog(int position, taskData todoData) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(),R.style.CustomAlertDialog);
        builder.setTitle("Edit "+todoData.task);

        // Inflate the custom layout/view
        final View customLayout = LayoutInflater.from(getContext()).inflate(R.layout.additemdialog, null);
        builder.setView(customLayout);

        // Set the current text to the EditText
        EditText editTextItem = customLayout.findViewById(R.id.edit_text_item);
        editTextItem.setText(todoData.task);

        builder.setPositiveButton("Update", (dialog, which) -> {
            String newItem = editTextItem.getText().toString();

            if (!newItem.isEmpty()) {
                // Update the item in SharedPreferences
                ArrayList<String> currentList = sharedPrefManager.getTodoList();
                currentList.set(currentList.indexOf(todoData.task), newItem);
                todoData.task = newItem;
                dataArrayList.set(position, todoData);
                sharedPrefManager.saveTodoList(currentList);

                // Notify the adapter that the data has changed
                notifyDataSetChanged();

                Toast.makeText(getContext().getApplicationContext(), "Item updated: " + newItem, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext().getApplicationContext(), "Item cannot be empty", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        // Create and show the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getContext().getResources().getColor(R.color.dialogTitleColor));
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getContext().getResources().getColor(R.color.dialogTitleColor));

    }

    private void showdeletetemDialog(int position, taskData taskData) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(),R.style.CustomAlertDialog);
        builder.setTitle("Delete "+taskData.task);
        builder.setMessage("Really want to delete "+taskData.task+" ?");

        builder.setPositiveButton("Delete", (dialog, which) -> {
            dataArrayList.remove(position);

            // Remove the item from SharedPreferences
            ArrayList<String> currentList = sharedPrefManager.getTodoList();
            currentList.remove(taskData.task);
            sharedPrefManager.saveTodoList(currentList);

            // Notify the adapter that the data has changed
            notifyDataSetChanged();

            Toast.makeText(getContext().getApplicationContext(), taskData.task + " deleted", Toast.LENGTH_SHORT).show();
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        // Create and show the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getContext().getResources().getColor(R.color.dialogTitleColor));
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getContext().getResources().getColor(R.color.dialogTitleColor));

    }
}
