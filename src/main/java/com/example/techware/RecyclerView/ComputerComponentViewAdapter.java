package com.example.techware.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.techware.Model.ComputerComponent;
import com.example.techware.R;

import java.util.ArrayList;

public class ComputerComponentViewAdapter extends RecyclerView.Adapter<ComputerComponentViewAdapter.MyViewHolder> {
    private final RecyclerViewInterface recyclerViewInterface;

    private final Context context;
    private final ArrayList<ComputerComponent> components;

    public ComputerComponentViewAdapter(Context context, ArrayList<ComputerComponent> components, RecyclerViewInterface recyclerViewInterface) {
        this.recyclerViewInterface = recyclerViewInterface;
        this.context = context;
        this.components = components;
    }

    @NonNull
    @Override
    public ComputerComponentViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_items, parent, false);
        return new ComputerComponentViewAdapter.MyViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ComputerComponentViewAdapter.MyViewHolder holder, int position) {
        ComputerComponent currrentComponent = components.get(position);
        holder.productNameField.setText(currrentComponent.getName());
        holder.categoryField.setText(currrentComponent.getCategory());
        String details = currrentComponent.getPrice()
                + ", " + currrentComponent.getQuantity() + " units in stock";
        holder.otherDetailsField.setText(details);
    }

    @Override
    public int getItemCount() {
        return components.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView productNameField, categoryField, otherDetailsField;
        ImageButton editButton, deleteButton;

        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            productNameField = itemView.findViewById(R.id.productNameField);
            categoryField = itemView.findViewById(R.id.categoryField);
            otherDetailsField = itemView.findViewById(R.id.otherDetailsField);

            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);

            deleteButton.setOnClickListener(v -> {
                if(recyclerViewInterface != null) {
                    int position = getAdapterPosition();

                    if(position != RecyclerView.NO_POSITION) {
                        recyclerViewInterface.onDeleteItemClick(position);
                    }
                }
            });

            editButton.setOnClickListener(v -> {
                if(recyclerViewInterface != null) {
                    int position = getAdapterPosition();

                    if(position != RecyclerView.NO_POSITION) {
                        recyclerViewInterface.onEditItemClick(position);
                    }
                }
            });

            itemView.setOnClickListener(v -> {
                if(recyclerViewInterface != null) {
                    int position = getAdapterPosition();

                    if(position != RecyclerView.NO_POSITION) {
                        recyclerViewInterface.onItemClick(position);
                    }
                }
            });
        }
    }
}
