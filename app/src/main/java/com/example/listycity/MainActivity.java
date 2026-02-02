package com.example.listycity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.app.AlertDialog;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;
    int selectedCityIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        cityList = findViewById(R.id.city_list);
        Button addButton = findViewById(R.id.add_button);
        Button deleteButton = findViewById(R.id.delete_button);

        String[] cities = {"Edmonton", "Vancouver", "Moscow", "Sydney", "Vienna", "Tokyo", "Beijing", "Osaka", "New Delhi"};
        dataList = new ArrayList<>(Arrays.asList(cities));

        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);
        cityList.setAdapter(cityAdapter);

        // Track selection
        cityList.setOnItemClickListener((parent, view, position, id) -> {
            selectedCityIndex = position;
        });

        // Add Button Logic
        addButton.setOnClickListener(v -> {
            EditText input = new EditText(this);
            input.setHint("Enter city name");

            new AlertDialog.Builder(this)
                    .setTitle("Add City")
                    .setMessage("Enter the name of the city:")
                    .setView(input)
                    .setPositiveButton("Add", (dialog, which) -> {
                        String cityName = input.getText().toString().trim();
                        if (!cityName.isEmpty()) {
                            dataList.add(cityName);
                            cityAdapter.notifyDataSetChanged();
                        }
                    })
//                    .setNegativeButton("Cancel", null)
                    .show();
        });

        // Delete Button Logic
        deleteButton.setOnClickListener(v -> {
            if (selectedCityIndex != -1) {
                dataList.remove(selectedCityIndex);
                cityAdapter.notifyDataSetChanged();
                selectedCityIndex = -1; // Reset selection
            }
        });
    }
}