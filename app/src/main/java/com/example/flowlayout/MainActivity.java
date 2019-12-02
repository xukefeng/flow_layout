package com.example.flowlayout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private FlowView flowView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        flowView = findViewById(R.id.flowView);
        List<String> texts = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            if (i==3||i==9||i==9)
            {
                texts.add("name is bluecat");
            }else
            {
                if (i%2==0) {
                    texts.add("flow");
                }else
                {
                    texts.add("flow layout");
                }
            }

        }
        flowView.setDatas(texts);
    }
}
