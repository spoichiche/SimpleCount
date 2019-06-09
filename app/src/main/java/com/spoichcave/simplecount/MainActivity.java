package com.spoichcave.simplecount;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button btnAddCounter;
    Button btnLoad;
    Button btnReset;
    LinearLayout layoutList;
    DatabaseManager dbManager;
    Context thisContext = this;
    List<CounterView> counterList = new ArrayList<>();
    int counterNumber = 0;

    public void createCounter(){
        CounterView counterView = new CounterView(this, counterNumber);
        layoutList.addView(counterView.layoutCounter);
        counterNumber++;
        counterView.addDelOnClickListener(layoutList);
        counterView.addSaveOnClickListener(dbManager);
        counterList.add(counterView);
    }

    public void removeAllCounter(){
        for (CounterView counterView : counterList) {
            layoutList.removeView(counterView.layoutCounter);
        }
    }

    private View.OnClickListener addCounter = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            createCounter();
        }
    };

    private View.OnClickListener loadAllCounter = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            removeAllCounter();
            counterList.clear();
            counterNumber = 0;
            List<Counter> counters = dbManager.fetchAllCounter();
            for(Counter counter : counters){
                CounterView counterView = new CounterView(counter, thisContext, counterNumber);
                counterList.add(counterView);
                layoutList.addView(counterView.layoutCounter);
                counterNumber++;
                counterView.addDelOnClickListener(layoutList);
                counterView.addSaveOnClickListener(dbManager);
                counterList.add(counterView);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbManager = new DatabaseManager(this);

        btnAddCounter = findViewById(R.id.btnAddCounter);
        btnAddCounter.setOnClickListener(addCounter);
        layoutList = findViewById(R.id.layoutList);
        btnLoad = findViewById(R.id.btnLoad);
        btnLoad.setOnClickListener(loadAllCounter);
        btnReset = findViewById(R.id.btnReset);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeAllCounter();
                dbManager.resetCounters();
            }
        });
    }


}