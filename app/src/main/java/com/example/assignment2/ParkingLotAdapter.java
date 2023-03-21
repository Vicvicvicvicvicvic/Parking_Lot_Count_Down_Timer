package com.example.assignment2;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ParkingLotAdapter extends RecyclerView.Adapter<ParkingLotAdapter.ViewHolder> {

    private List<String> parkingLots;
    private Context context;

    public ParkingLotAdapter(Context context, List<String> parkingLots) {
        this.context = context;
        this.parkingLots = parkingLots;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.parking_lot_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String parkingLotName = parkingLots.get(position); //get the data from arrayList by index
        holder.parkingLotName.setText(parkingLotName); //match String value with index

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ParkingDetailsActivity.class);//listen when user clicks, it go from ParkingLotAdapter to Parking DetailsActivity
                intent.putExtra("parking_lot_name", parkingLotName); //sending key with an item in the arrayList which user just clicked
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return parkingLots.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView parkingLotName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parkingLotName = itemView.findViewById(R.id.parking_lot_name); //where should the value display. locate with id
        }
    }
}
