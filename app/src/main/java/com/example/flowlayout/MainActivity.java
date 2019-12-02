package com.example.flowlayout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private FlowView flowView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        flowView = findViewById(R.id.flowView);
        final List<String> texts = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            if (i == 3 || i == 9 || i == 9) {
                texts.add("name is bluecat");
            } else {
                if (i % 2 == 0) {
                    texts.add("flow");
                } else {
                    texts.add("flow layout");
                }
            }

        }
        flowView.setOnFlowItemClick(new FlowView.OnFlowItemClick() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(MainActivity.this, texts.get(position), Toast.LENGTH_LONG).show();
            }
        });
        flowView.setDatas(texts);
    }
}
