package com.example.tirefinder;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tirefinder.databinding.TireBinding;

public class Adapter  extends RecyclerView.Adapter<Adapter.viewHolder> {

    private TireModel tireModel;
    TireSearch search;
    TireBinding binding;

    public Adapter(TireModel tireModel, TireSearch search) {
        this.tireModel = tireModel;
        this.search = search;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @NonNull
    @Override
    public Adapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        TireBinding tireBinding = TireBinding.inflate(search.getLayoutInflater());
        return new viewHolder(tireBinding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.viewHolder holder, int position) {
        TireInfo tireToDisplay = tireModel.tiresToDisplay.getValue().get(position);
        try{
            holder.nameView.setText(tireToDisplay.name);
            holder.carView.setText(tireToDisplay.vehicle);
            holder.imageView.setImageBitmap(tireToDisplay.picture);
            holder.treadView.setText(tireToDisplay.treadType);
        }catch (Exception e){}


    }

    @Override
    public int getItemCount() {
        int itemCount = tireModel.tiresToDisplay.getValue().size();
        return itemCount;
    }

    public class viewHolder  extends RecyclerView.ViewHolder {
        TextView nameView;
        TextView carView;
        ImageView imageView;
        TextView treadView;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.nameView);
            carView = itemView.findViewById(R.id.carView);
            imageView = itemView.findViewById(R.id.imageView);
            treadView = itemView.findViewById(R.id.tireTreadView);

            itemView.setOnClickListener(click ->{
                tireModel.updatingTire.setValue(true);
                search.updateTire(getAdapterPosition());
            });


        }
    }
}
