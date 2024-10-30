package com.example.cw1_b;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.TimePicker;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
public class MainActivity extends AppCompatActivity {

    private EditText editTextName, editTextPhone, editTextDescription;
    private TimePicker timePickerStartTime, timePickerEndTime;
    private Button buttonAdd, buttonEdit, buttonDelete;
    private ListView listViewTasks;

    private ArrayList<String> taskList;
    private ArrayAdapter<String> adapter;
    private int selectedIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextName = findViewById(R.id.editTextName);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextDescription = findViewById(R.id.editTextDescription);
        timePickerStartTime = findViewById(R.id.timePickerStartTime);
        timePickerEndTime = findViewById(R.id.timePickerEndTime);
        buttonAdd = findViewById(R.id.buttonAdd);
        buttonEdit = findViewById(R.id.buttonEdit);
        buttonDelete = findViewById(R.id.buttonDelete);
        listViewTasks = findViewById(R.id.listViewTasks);

        taskList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, taskList);
        listViewTasks.setAdapter(adapter);


        buttonAdd.setOnClickListener(v -> addTask());

        buttonEdit.setOnClickListener(v -> editTask());

        buttonDelete.setOnClickListener(v -> deleteTask());

        listViewTasks.setOnItemClickListener((parent, view, position, id) -> {
            selectedIndex = position;
            loadSelectedTask();
        });
    }

    private void addTask() {
        String name = editTextName.getText().toString();
        String phone = editTextPhone.getText().toString();
        String description = editTextDescription.getText().toString();
        String startTime = getTimeFromPicker(timePickerStartTime);
        String endTime = getTimeFromPicker(timePickerEndTime);

        if (name.isEmpty() || phone.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        String task = "Name: " + name + "\n" +
                "Phone: " + phone + "\n" +
                "Description: " + description + "\n" +
                "Start Time: " + startTime + "\n" +
                "End Time: " + endTime ;
        taskList.add(task);
        adapter.notifyDataSetChanged();
        clearFields();
    }

    private void editTask() {
        if (selectedIndex != -1) {
            String name = editTextName.getText().toString();
            String phone = editTextPhone.getText().toString();
            String description = editTextDescription.getText().toString();
            String startTime = getTimeFromPicker(timePickerStartTime);
            String endTime = getTimeFromPicker(timePickerEndTime);

            if (name.isEmpty() || phone.isEmpty() || description.isEmpty()) {
                Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            String task = "Name: " + name + "\n" +
                    "Phone: " + phone + "\n" +
                    "Description: " + description + "\n" +
                    "Start Time: " + startTime + "\n" +
                    "End Time: " + endTime ;

            taskList.set(selectedIndex, task);
            adapter.notifyDataSetChanged();
            clearFields();
        } else {
            Toast.makeText(this, "Select a task to edit", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteTask() {
        if (selectedIndex != -1) {
            taskList.remove(selectedIndex);
            adapter.notifyDataSetChanged();
            clearFields();
        } else {
            Toast.makeText(this, "Select a task to delete", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadSelectedTask() {
        String[] taskDetails = taskList.get(selectedIndex).split("\n");
        editTextName.setText(taskDetails[0].substring("Name: ".length()));
        editTextPhone.setText(taskDetails[1].substring("Phone: ".length()));
        editTextDescription.setText(taskDetails[2].substring("Description: ".length()));

        String startTime = taskDetails[3].substring("Start Time: ".length());
        String endTime = taskDetails[4].substring("End Time: ".length());

        setTimePicker(timePickerStartTime, startTime);
        setTimePicker(timePickerEndTime, endTime);
    }


    private void clearFields() {
        editTextName.setText("");
        editTextPhone.setText("");
        editTextDescription.setText("");
        timePickerStartTime.setHour(0);
        timePickerStartTime.setMinute(0);
        timePickerEndTime.setHour(0);
        timePickerEndTime.setMinute(0);
        selectedIndex = -1;
    }

    private String getTimeFromPicker(TimePicker picker) {
        int hour = picker.getHour();
        int minute = picker.getMinute();
        return String.format("%02d:%02d", hour, minute);
    }

    private void setTimePicker(TimePicker picker, String time) {
        String[] timeParts = time.split(":");
        int hour = Integer.parseInt(timeParts[0]);
        int minute = Integer.parseInt(timeParts[1]);
        picker.setHour(hour);
        picker.setMinute(minute);
    }
}
