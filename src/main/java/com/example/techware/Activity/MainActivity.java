package com.example.techware.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.techware.Model.ComputerComponent;
import com.example.techware.R;
import com.example.techware.RecyclerView.ComputerComponentViewAdapter;
import com.example.techware.RecyclerView.RecyclerViewInterface;
import com.example.techware.Repository.ComputerComponentRepository;
import com.example.techware.databinding.ActivityMainBinding;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements RecyclerViewInterface {
    ComputerComponentRepository repository = new ComputerComponentRepository();
    ArrayList<ComputerComponent> products;
    RecyclerView recyclerView;
    ComputerComponentViewAdapter adapter;
    private ActivityMainBinding binding;

    private static final int RC_ADD_COMP_RESULT = 10001;

    private ActivityResultLauncher<Intent> addPageLauncher;
    private ActivityResultLauncher<Intent> updatePageLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recyclerViewSetup();
        addButtonSetup();

        updatePageLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        assert data != null;
                        ComputerComponent newComponent = data.getParcelableExtra("UPDATED_COMPONENT");
                        int position = data.getIntExtra("POSITION", -1);

                        if(newComponent == null || position == -1) {
                            System.out.println("Null component or invalid position");
                            return;
                        }

                        this.repository.update(newComponent);
                        this.products.set(position, newComponent);
                        adapter.notifyItemChanged(position);
                    }
                }
        );
    }

    @Override
    protected void onResume() {
        super.onResume();

//        int i = 25;
//        ComputerComponent test = new ComputerComponent("Product " + String.valueOf(i), "Manufacturer", "Category", (float)110.99 * i, i * 10, LocalDate.now());
//        this.repository.add(test);
//        this.products.add(test);
//        adapter.notifyItemInserted(this.products.size() - 1);
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if(requestCode == RC_ADD_COMP_RESULT) {
//            if(resultCode == RESULT_OK) {
//                ComputerComponent newComponent = data.getParcelableExtra("ADDED_COMPONENT");
//
//                if(newComponent == null)
//                    throw new RuntimeException();
//                this.repository.add(newComponent);
//                this.products.add(newComponent);
//                adapter.notifyItemInserted(this.products.size());
//            }
//        }
//    }

    private void addButtonSetup() {
        addPageLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        assert data != null;
                        ComputerComponent newComponent = data.getParcelableExtra("ADDED_COMPONENT");

                        if(newComponent == null)
                            throw new RuntimeException();

                        this.repository.add(newComponent);
                        this.products.add(newComponent);
                        adapter.notifyItemInserted(this.products.size() - 1);
                    }
                }
        );

        binding.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddComponentActivity.class);

                addPageLauncher.launch(intent);
//                startActivityForResult(intent, RC_ADD_COMP_RESULT);
            }
        });
    }
    private void recyclerViewSetup() {
        recyclerView = findViewById(R.id.itemRecyclerView);
        setUpEntities();

        adapter = new ComputerComponentViewAdapter(this, products, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setUpEntities() {
        for(int i = 1; i < 15; ++i) {
            this.repository.add(new ComputerComponent("Product " + String.valueOf(i), "Manufacturer", "Category", (float)110.99 * i, i * 10, LocalDate.now()));
        }

        products = this.repository.getAll();
    }

    @Override
    public void onItemClick(int position) {
        Log.d("d","Clicked " + position);
        Intent intent = new Intent(MainActivity.this, InspectComponentActivity.class);

        ComputerComponent selected = this.products.get(position);
        intent.putExtra("COMPONENT", selected);
        startActivity(intent);
    }

    @Override
    public void onDeleteItemClick(int position) {
        Log.d("d","Deleted " + position);

        ComputerComponent selected = this.products.get(position);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        String message = "Delete " + selected.getName() + "?";
        builder.setTitle("Delete component")
                .setMessage(message)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.this.products.remove(position);
                        MainActivity.this.repository.remove(selected.getId());
                        adapter.notifyItemRemoved(position);
                    }
                })
                .setNegativeButton("No", (dialog, which) -> {}).show();
    }

    @Override
    public void onEditItemClick(int position) {
        Log.d("d","Clicked " + position);

        Intent intent = new Intent(MainActivity.this, EditComponentActivity.class);
        ComputerComponent selected = this.products.get(position);
        intent.putExtra("COMPONENT", selected);
        intent.putExtra("POSITION", position);

        updatePageLauncher.launch(intent);
    }
}
