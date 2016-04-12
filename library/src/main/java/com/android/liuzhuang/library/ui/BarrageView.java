package com.android.liuzhuang.library.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.SurfaceView;

import com.android.liuzhuang.library.model.Actor;
import com.android.liuzhuang.library.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * BarrageView rendered by SurfaceView
 * Created by liuzhuang on 16/4/6.
 */
public class BarrageView extends SurfaceView {
    private static final String TAG = "barrage_view";

    private List<Actor> actors;

    public BarrageView(Context context) {
        super(context);
        init();
    }

    public BarrageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BarrageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        actors = new ArrayList<Actor>();
        setZOrderOnTop(true);
        getHolder().setFormat(PixelFormat.TRANSPARENT);
    }

    public void addActor(Actor actor) {
        if (!actors.contains(actor)) {
            actors.add(actor);
        }
    }

    public void removeActor(Actor actor) {
        actors.remove(actor);
    }

    public void clearActors() {
        actors.clear();
        Canvas canvas = getHolder().lockCanvas();
        if (canvas != null) {
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    public boolean drawOnce() {
        if (actors.isEmpty()) { // if no actors, then wait.
            return true;
        }
        Canvas canvas = getHolder().lockCanvas();
        if (canvas != null) {
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            if (!actors.isEmpty()) {
                synchronized (getHolder()) {
                    for (int i = 0; i < actors.size(); i++) {
                        actors.get(i).drawSelf(canvas);
                    }
                }
            }
            getHolder().unlockCanvasAndPost(canvas);
            return true;
        }
        return false;
    }
}
