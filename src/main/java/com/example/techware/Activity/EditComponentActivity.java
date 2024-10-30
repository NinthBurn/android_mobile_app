package com.example.techware.Activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.example.techware.Model.ComputerComponent;
import com.example.techware.Model.ComputerComponentValidator;
import com.example.techware.databinding.ActivityAddComponentBinding;

import java.time.LocalDate;

public class EditComponentActivity extends AppCompatActivity {
    private ActivityAddComponentBinding binding;
    private final static ComputerComponentValidator validator = new ComputerComponentValidator();
    String name, manufacturer, category;
    Float price;
    Integer id, position, quantity;
    LocalDate releaseDate = LocalDate.now();
    ComputerComponent originalComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddComponentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initData();
        setupDatePicker();
        setupEditButton();
        setupReturnButton();
    }

    void setupDatePicker() {
        DatePickerDialog dialog = new DatePickerDialog(EditComponentActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                releaseDate = LocalDate.of(year, month + 1, dayOfMonth);
                binding.releaseDateField.getEditText().setText(releaseDate.toString());
            }
        }, releaseDate.getYear(), releaseDate.getMonthValue() - 1, releaseDate.getDayOfMonth());

        binding.releaseDateField.getEditText().setOnKeyListener(null);
        binding.releaseDateField.getEditText().setOnClickListener(v -> { dialog.show(); });

        binding.releaseDateField.getEditText().setOnFocusChangeListener((v, hasFocus) -> {
            if(!hasFocus)
                return;

            dialog.show();
        });
    }

    void setupEditButton() {
        binding.addButton.setText("Modify");
        binding.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    extractData();
                    ComputerComponent component = new ComputerComponent(id, name, manufacturer, category, price, quantity, releaseDate);
                    validator.validate(component);

                    Intent intent = getIntent();
                    intent.putExtra("UPDATED_COMPONENT", component);
                    intent.putExtra("POSITION", position);
                    setResult(MainActivity.RESULT_OK, intent);

                    finish();
                }catch(Exception ex){
                    binding.errorTextView.setText(ex.getMessage());
                }
            }
        });
    }

    void initData() {
        originalComponent = getIntent().getParcelableExtra("COMPONENT");
        position = getIntent().getIntExtra("POSITION", -1);

        if (originalComponent == null || position == -1)
            finish();

        id = originalComponent.getId();
        binding.titlePanels.setText("Editing " + originalComponent.getName());
        binding.nameField.getEditText().setText(originalComponent.getName());
        binding.categoryField.getEditText().setText(originalComponent.getCategory());
        binding.manufacturerField.getEditText().setText(originalComponent.getManufacturer());
        binding.priceField.getEditText().setText(String.valueOf(originalComponent.getPrice()));
        binding.quantityField.getEditText().setText(String.valueOf(originalComponent.getQuantity()));
        binding.releaseDateField.getEditText().setText(originalComponent.getReleaseDate().toString());
    }
    void extractData() throws Exception{
        name = binding.nameField.getEditText().getText().toString();
        category = binding.categoryField.getEditText().getText().toString();
        manufacturer = binding.manufacturerField.getEditText().getText().toString();

        try{
            price = Float.valueOf(binding.priceField.getEditText().getText().toString());

        }catch(Exception ex) {
            throw new Exception("Invalid price; only positive numbers are accepted");
        }

        try{
            quantity = Integer.valueOf(binding.quantityField.getEditText().getText().toString());

        }catch(Exception ex) {
            throw new Exception("Invalid quantity; only positive integers are accepted");
        }
    }

    void setupReturnButton() {
        binding.returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
