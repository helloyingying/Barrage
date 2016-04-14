package com.android.liuzhuang.library.controller;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Rect;

import com.android.liuzhuang.library.model.Actor;
import com.android.liuzhuang.library.model.BarrageDo;
import com.android.liuzhuang.library.ui.BarrageView;
import com.android.liuzhuang.library.util.LogUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The director of Barrage. This class will direct witch barrage item will show at the time.
 * Created by liuzhuang on 16/4/8.
 */
public final class Director {
    private List<Actor> allActors;
    private List<Actor> showingActors;

    private BarrageView barrageView;
    private long duringMilliseconds;
    private long startTime;

    public Director() {
        allActors = Collections.synchronizedList(new ArrayList<Actor>());
        showingActors = Collections.synchronizedList(new ArrayList<Actor>());
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
                    new Rect(barrageView.getLeft(), barrageView.getTop(), barrageView.getRight(), barrageView.getBottom()));
            allActors.add(actor);
        }
    }

    public synchronized boolean drawOnce() {
        filterActors();
        return drawOnceInner();
    }

    /**
     * select actors need to show now.
     */
    // TODO: 16/4/14 不要每次都遍历
    private void filterActors() {
        duringMilliseconds = System.currentTimeMillis() - startTime;
        showingActors.clear();
        for (int i = 0; i < allActors.size(); i++) {
            if (allActors.get(i).checkValid(duringMilliseconds)) {
                showingActors.add(allActors.get(i));
            }
        }
        LogUtil.d("showing_actor_size", showingActors.size());
    }

    private boolean drawOnceInner() {
        if (showingActors.isEmpty()) { // if no actors, then wait for a while.
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return true;
        }
        Canvas canvas = barrageView.getHolder().lockCanvas();
        if (canvas != null) {
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            if (!showingActors.isEmpty()) {
                synchronized (barrageView.getHolder()) {
                    for (int i = 0; i < showingActors.size(); i++) {
                        showingActors.get(i).drawSelf(canvas);
                    }
                }
            }
            barrageView.getHolder().unlockCanvasAndPost(canvas);
            return true;
        }
        return false;
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
        allActors.clear();
        showingActors.clear();
        barrageView.clearStage();
    }
}
