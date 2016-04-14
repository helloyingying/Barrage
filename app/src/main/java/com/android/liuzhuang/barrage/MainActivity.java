package com.android.liuzhuang.barrage;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.android.liuzhuang.library.Barrage;
import com.android.liuzhuang.library.Constants;
import com.android.liuzhuang.library.model.BarrageDo;
import com.android.liuzhuang.library.ui.BarrageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
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
        barrage = new Barrage(this, (BarrageView) findViewById(R.id.barrage));
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.right_left) {
            createByDirection(Constants.RIGHT_LEFT);
        } else if (v.getId() == R.id.left_right) {
            createByDirection(Constants.LEFT_RIGHT);
        } else if (v.getId() == R.id.top_down) {
            createByDirection(Constants.TOP_DOWN);
        } else if (v.getId() == R.id.down_top) {
            createByDirection(Constants.DOWN_TOP);
        } else if (v.getId() == R.id.stop) {
            barrage.stop();
        } else if (v.getId() == R.id.create) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    barrage.addData(new BarrageDo.Builder()
                            .setText("I am a new text")
                            .setTextColor(Color.BLACK)
                            .setTextSize(70)
                            .setOffsetFromMargin(100)
                            .setAcceleration(0)
                            .setVelocity(10)
                            .setDirection(Constants.RIGHT_LEFT)
                            .setMillisecondFromStart(-1)
                            .build());
                }
            }).start();
        }

    }

    private void createByDirection(int direction) {
        barrage.stop();
        Random random = new Random();
        List<BarrageDo> data = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            data.add(new BarrageDo.Builder()
                    .setText("text" + i)
                    .setTextColor(colors[random.nextInt(10)])
                    .setMillisecondFromStart(1000*random.nextInt(5))
                    .setTextSize(50 + random.nextInt(10))
                    .setOffsetFromMargin(100 + random.nextInt(1000))
                    .setVelocity(random.nextInt(5))
                    .setAcceleration(random.nextInt(5) + 1)
                    .setDirection(direction)
                    .build());
        }
        barrage.addDataList(data);
        barrage.start();
    }
}