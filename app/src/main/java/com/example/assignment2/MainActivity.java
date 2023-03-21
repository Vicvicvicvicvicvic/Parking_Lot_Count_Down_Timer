package com.example.assignment2;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ParkingLotAdapter parkingLotAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);

        List<String> parkingLots = new ArrayList<>();
        parkingLots.add(new String("Parking Lot A"));
        parkingLots.add(new String("Parking Lot B"));
        parkingLots.add(new String("Parking Lot C"));
        parkingLots.add(new String("Parking Lot H"));
        parkingLots.add(new String("Parking Lot N"));
        parkingLots.add(new String("Parking Lot ET"));

        parkingLotAdapter = new ParkingLotAdapter(this, parkingLots);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(parkingLotAdapter);
    }
}

