package com.example.techware.Activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.techware.Model.ComputerComponent;
import com.example.techware.databinding.ActivityInspectComponentBinding;

public class InspectComponentActivity extends AppCompatActivity {
    private ActivityInspectComponentBinding binding;
    private ComputerComponent component;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInspectComponentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        component = getIntent().getParcelableExtra("COMPONENT");

        if (component != null)
            fillFields();

        binding.returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void fillFields() {
        System.out.println(component.toString());
        binding.idField.setText("Product ID: " + String.valueOf(component.getId()));
        binding.nameField.getEditText().setText(component.getName());
        binding.categoryField.getEditText().setText(component.getCategory());
        binding.manufacturerField.getEditText().setText(component.getManufacturer());
        binding.priceField.getEditText().setText(String.valueOf(component.getPrice()));
        binding.quantityField.getEditText().setText(String.valueOf(component.getQuantity()));
        binding.releaseDateField.getEditText().setText(component.getReleaseDate().toString());
    }
}
