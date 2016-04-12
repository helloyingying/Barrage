package com.android.liuzhuang.barrage;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.liuzhuang.library.Barrage;
import com.android.liuzhuang.library.model.BarrageDo;
import com.android.liuzhuang.library.ui.BarrageView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    Button switchBtn;
    Barrage barrage;
    int[] colors = new int[]{
            Color.parseColor("#F44336"),
            Color.parseColor("#E91E63"),
            Color.parseColor("#9C27B0"),
            Color.parseColor("#EA80FC"),
            Color.parseColor("#FF80AB"),
            Color.parseColor("#673AB7"),
            Color.parseColor("#311B92"),
            Color.parseColor("#00BCD4"),
            Color.parseColor("#009688"),
            Color.parseColor("#4CAF50")
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        switchBtn = (Button) findViewById(R.id.start);
        barrage = new Barrage(this, (BarrageView) findViewById(R.id.barrage));
        switchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                barrage.stop();
                Random random = new Random();
                List<BarrageDo> data = new ArrayList<>();
                for (int i = 0; i < 50; i++) {
                    data.add(new BarrageDo.Builder()
                            .setText("text" + i)
                            .setTextColor(colors[random.nextInt(10)])
                            .setTime(1000*random.nextInt(5))
                            .setTextSize(50 + random.nextInt(10))
                            .setOffsetFromMargin(100 + random.nextInt(1000))
                            .setVelocity(10+random.nextInt(5))
                            .build());
                }
                barrage.addDataList(data);
                barrage.start();
            }
        });
    }
}
