package com.android.liuzhuang.library;

import android.content.Context;

import com.android.liuzhuang.library.controller.Director;
import com.android.liuzhuang.library.model.BarrageDo;
import com.android.liuzhuang.library.ui.BarrageView;
import com.android.liuzhuang.library.util.ContextUtil;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * The face of whole system.
 * Created by liuzhuang on 16/4/7.
 */
public final class Barrage {
    private WeakReference<Context> contextWeakReference;
    private Director director;
    private DrawRunnable drawRunnable;

    private ThreadPoolExecutor executor = new ThreadPoolExecutor(0, 2, 120, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>());


    public Barrage(Context context, BarrageView barrageView) {
        if (context == null) {
            throw new NullPointerException("Context can not be null!");
        }
        if (barrageView == null) {
            throw new NullPointerException("BarrageView can not be null!");
        }
        contextWeakReference = new WeakReference<Context>(context);
        init(barrageView);
        ContextUtil.init(context);
    }

    private void init(BarrageView barrageView) {
        this.director = new Director();
        this.director.setBarrageView(barrageView);
    }

    public void start() {
        if (drawRunnable == null) {
            drawRunnable = new DrawRunnable(director);
        }
        drawRunnable.startRunning();
        director.onStart();
        executor.execute(drawRunnable);
    }

    public void stop() {
        if (drawRunnable != null) {
            director.onStop();
            drawRunnable.stopRunning();
            if (executor != null) {
                executor.remove(drawRunnable);
            }
        }
    }

    public void addDataList(List<BarrageDo> dataList) {
        director.addDataList(dataList);
    }

    public void addData(BarrageDo barrageDo) {
        director.addData(barrageDo);
    }

    private static class DrawRunnable implements Runnable {
        Director director;
        boolean running = true;

        public DrawRunnable(Director director) {
            this.director = director;
        }

        public void startRunning() {
            running = true;
        }

        public void stopRunning() {
            running = false;
        }

        @Override
        public void run() {
            if (director != null) {
                while (running) {
                    // if failed, stop all.
                    if (!director.drawOnce()) {
                        stopRunning();
                    }
                }
            }
        }
    }
}
