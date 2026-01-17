package io.github.p3sto.listycity;

import android.os.Bundle;
import android.text.InputType;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView cityList;
    Button addButton;
    Button deleteButton;

    final List<String> dataList = new ArrayList<>();
    ArrayAdapter<String> cityAdapter;
    int selected = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityList = findViewById(R.id.city_list);
        deleteButton = findViewById(R.id.delete_city);
        addButton = findViewById(R.id.add_city);

        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);
        cityList.setAdapter(cityAdapter);

        cityList.setOnItemClickListener((parent, view, position, id) ->
                selected = position
        );

        addButton.setOnClickListener(v -> {
            EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_TEXT);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Enter a city name")
                    .setView(input)
                    .setPositiveButton("OK", ((dialog, which) -> {
                        String raw = input.getText().toString();

                        if (raw.isBlank()) {
                            new AlertDialog.Builder(this)
                                    .setTitle("Error")
                                    .setMessage("Cannot enter a blank city name")
                                    .setPositiveButton("Ok", null)
                                    .show();
                            return;
                        }

                        String finalText = raw.toUpperCase().charAt(0) + raw.toLowerCase().substring(1);
                        if (dataList.contains(finalText)) {
                            new AlertDialog.Builder(this)
                                    .setTitle("Error")
                                    .setMessage("Cannot enter an existing city name")
                                    .setPositiveButton("Ok", null)
                                    .show();
                            return;
                        }
                        cityAdapter.add(finalText);
                    }))
                    .setNegativeButton("Cancel", ((dialog, which) -> dialog.cancel()))
                    .show();
        });

        deleteButton.setOnClickListener(v -> {
            if (selected != -1) {
                String city = cityAdapter.getItem(selected);
                cityAdapter.remove(city);
                selected = -1;
            }
        });
    }
}