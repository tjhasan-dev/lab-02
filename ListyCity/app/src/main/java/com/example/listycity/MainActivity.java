package com.example.listycity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import com.example.listycity.R;



public class MainActivity extends AppCompatActivity {

    private ListView cityList;
    private ArrayAdapter<String> cityAdapter;
    private ArrayList<String> dataList;

    /// NEW
    private Button btnAdd, btnDelete, btnConfirm;
    private EditText etCity;

    private int selectedIndex = -1; // -1 means “nothing selected”
/////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Hook UI
        cityList = findViewById(R.id.city_list);
        // NEW MY WORK
        btnAdd = findViewById(R.id.btn_add);
        btnDelete = findViewById(R.id.btn_delete);
        btnConfirm = findViewById(R.id.btn_confirm);
        etCity = findViewById(R.id.et_city);
        ////

        // Initial data
        String[] cities = {"Edmonton", "Vancouver", "Moscow", "Sydney", "Berlin", "Vienna", "Tokyo", "Beijing", "Osaka", "New Delhi"};
        dataList = new ArrayList<>(Arrays.asList(cities));

        // Adapter uses your content.xml (single TextView)
        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);
        cityList.setAdapter(cityAdapter);

        //// NEW REST OF IT
        // Select a city by tapping it
        cityList.setOnItemClickListener((parent, view, position, id) -> {
            selectedIndex = position;
            Toast.makeText(this, "Selected: " + dataList.get(position), Toast.LENGTH_SHORT).show();
        });

        // ADD CITY: show input + confirm
        btnAdd.setOnClickListener(v -> {
            etCity.setText("");
            etCity.setVisibility(View.VISIBLE);
            btnConfirm.setVisibility(View.VISIBLE);
            etCity.requestFocus();
        });

        // CONFIRM: add city to list
        btnConfirm.setOnClickListener(v -> {
            String newCity = etCity.getText().toString().trim();

            if (newCity.isEmpty()) {
                Toast.makeText(this, "Type a city name first.", Toast.LENGTH_SHORT).show();
                return;
            }

            dataList.add(newCity);
            cityAdapter.notifyDataSetChanged();

            // Hide input again (optional)
            etCity.setText("");
            etCity.setVisibility(View.GONE);
            btnConfirm.setVisibility(View.GONE);

            Toast.makeText(this, "Added: " + newCity, Toast.LENGTH_SHORT).show();
        });

        // DELETE CITY: delete selected city
        btnDelete.setOnClickListener(v -> {
            if (selectedIndex < 0 || selectedIndex >= dataList.size()) {
                Toast.makeText(this, "Tap a city first to select it.", Toast.LENGTH_SHORT).show();
                return;
            }

            String removed = dataList.remove(selectedIndex);
            selectedIndex = -1;

            cityAdapter.notifyDataSetChanged();
            Toast.makeText(this, "Deleted: " + removed, Toast.LENGTH_SHORT).show();
        });
    }
}
