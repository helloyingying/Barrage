package com.android.liuzhuang.library.controller;

import android.graphics.Rect;

import com.android.liuzhuang.library.Constants;
import com.android.liuzhuang.library.model.Actor;
import com.android.liuzhuang.library.model.BarrageDo;
import com.android.liuzhuang.library.ui.BarrageView;

import java.util.ArrayList;
import java.util.List;

/**
 * The director of Barrage. This class will direct witch barrage item will show at the time.
 * Created by liuzhuang on 16/4/8.
 */
public final class Director {
    private List<Actor> rawDataSource;

    private BarrageView barrageView;
    private long duringMilliseconds;
    private long startTime;

    public Director() {
        rawDataSource = new ArrayList<Actor>();
    }

    public void setBarrageView(BarrageView barrageView) {
        this.barrageView = barrageView;
        if (barrageView == null) {
            throw new NullPointerException("BarrageView can not be null!");
        }
    }

    public void addDataList(List<BarrageDo> dataList) {
        if (dataList != null && !dataList.isEmpty()) {
            for (int i = 0; i < dataList.size(); i++) {
                addData(dataList.get(i));
            }
        }
    }

    public void addData(BarrageDo barrageDo) {
        if (barrageDo != null) {
            Actor actor = new Actor(barrageDo,
                    new Rect(barrageView.getLeft(), barrageView.getTop(), barrageView.getRight(), barrageView.getBottom()),
                    Constants.DOWN_TOP);
            rawDataSource.add(actor);
        }
    }

    public synchronized boolean drawOnce() {
        duringMilliseconds = System.currentTimeMillis() - startTime;
        // TODO: 16/4/12  性能有问题,多线程clear时有下面的crash
        /**
         java.lang.IndexOutOfBoundsException: Invalid index 18, size is 0
         at java.util.ArrayList.throwIndexOutOfBoundsException(ArrayList.java:255)
         at java.util.ArrayList.get(ArrayList.java:308)
         at com.android.liuzhuang.library.controller.Director.drawOnce(Director.java:59)
         at com.android.liuzhuang.library.Barrage$DrawRunnable.run(Barrage.java:91)
         at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1113)
         at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:588)
         at java.lang.Thread.run(Thread.java:818)
         * */
        for (int i = 0; i < rawDataSource.size(); i++) {
            if (rawDataSource.get(i).checkValid(duringMilliseconds)) {
                barrageView.addActor(rawDataSource.get(i));
            } else {
                barrageView.removeActor(rawDataSource.get(i));
            }
        }
        return barrageView.drawOnce();
    }

    public void onStop() {
        duringMilliseconds = 0;
        clearData();
    }

    public void onStart() {
        duringMilliseconds = 0;
        startTime = System.currentTimeMillis();
    }

    public void clearData() {
        rawDataSource.clear();
        barrageView.clearActors();
    }
}
