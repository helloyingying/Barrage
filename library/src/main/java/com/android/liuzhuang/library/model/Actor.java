package com.android.liuzhuang.library.model;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;

import com.android.liuzhuang.library.Constants;
import com.android.liuzhuang.library.util.LogUtil;

/**
 * The actor of each item, it stores the current position and validation of item.
 * Created by liuzhuang on 16/4/8.
 */
public final class Actor {
    private BarrageDo barrageDo;
    private boolean isValid;
    private int currentPosition;
    private Rect bound;
    private int realVelocity;
    private int realAcceleration;

    public Actor(BarrageDo barrageDo, Rect bound) {
        if (barrageDo == null) {
            throw new NullPointerException("BarrageDo can not be null");
        }
        if (bound == null) {
            throw new NullPointerException("Bound can not be null");
        }
        this.barrageDo = barrageDo;
        this.bound = bound;
        initPositionByBoundDirection();
    }

    private void initPositionByBoundDirection() {
        switch (barrageDo.getDirection()) {
            case Constants.RIGHT_LEFT:
            default:
                currentPosition = bound.right;
                realVelocity = -barrageDo.getVelocity();
                realAcceleration = -barrageDo.getAcceleration();
                break;
            case Constants.TOP_DOWN:
                currentPosition = bound.top - 2 * getLength();
                realVelocity = barrageDo.getVelocity();
                realAcceleration = barrageDo.getAcceleration();
                break;
            case Constants.LEFT_RIGHT:
                currentPosition = bound.left - getLength();
                realVelocity = barrageDo.getVelocity();
                realAcceleration = barrageDo.getAcceleration();
                break;
            case Constants.DOWN_TOP:
                currentPosition = bound.bottom + 2 * getLength();
                realVelocity = -barrageDo.getVelocity();
                realAcceleration = -barrageDo.getAcceleration();
                break;
        }
    }

    private boolean isOutOfBound() {
        boolean isOutOfBound = true;
        switch (barrageDo.getDirection()) {
            case Constants.RIGHT_LEFT:
            default:
                if (currentPosition < bound.left - 2 * getLength()) {
                    isOutOfBound = true;
                } else {
                    isOutOfBound = false;
                }
                break;
            case Constants.TOP_DOWN:
                if (currentPosition > bound.bottom + 2 * getLength()) {
                    isOutOfBound = true;
                } else {
                    isOutOfBound = false;
                }
                break;
            case Constants.LEFT_RIGHT:
                if (currentPosition > bound.right + 2 * getLength()) {
                    isOutOfBound = true;
                } else {
                    isOutOfBound = false;
                }
                break;
            case Constants.DOWN_TOP:
                if (currentPosition < bound.top - 2 * getLength()) {
                    isOutOfBound = true;
                } else {
                    isOutOfBound = false;
                }
                break;
        }
        return isOutOfBound;
    }

    private int getLength() {
        if (TextUtils.isEmpty(barrageDo.getText())) {
            return 0;
        }
        if (barrageDo.getTextSize() == 0) {
            return 0;
        }
        return barrageDo.getText().length() * barrageDo.getTextSize();
    }

    public boolean checkValid(long duringMilliseconds) {
        if (barrageDo == null) {
            isValid = false;
        } else if ((duringMilliseconds >= barrageDo.getMillisecondFromStart() || barrageDo.getMillisecondFromStart() == -1)
                && !isOutOfBound()) {
            isValid = true;
        } else {
            isValid = false;
        }
        return isValid;
    }

    public void drawSelf(Canvas canvas) {
        if (canvas != null && barrageDo != null) {
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setColor(barrageDo.getTextColor());
            paint.setTextSize(barrageDo.getTextSize());
            if (barrageDo.getDirection() == Constants.RIGHT_LEFT) {
                canvas.drawText(barrageDo.getText(), currentPosition, barrageDo.getOffsetFromMargin(), paint);
            } else if (barrageDo.getDirection() == Constants.TOP_DOWN) {
                canvas.save();
                canvas.rotate(90, barrageDo.getOffsetFromMargin(), currentPosition);
                canvas.drawText(barrageDo.getText(), barrageDo.getOffsetFromMargin(), currentPosition, paint);
                canvas.restore();
            } else if (barrageDo.getDirection() == Constants.LEFT_RIGHT) {
                canvas.drawText(barrageDo.getText(), currentPosition, barrageDo.getOffsetFromMargin(), paint);
            } else if (barrageDo.getDirection() == Constants.DOWN_TOP) {
                canvas.save();
                canvas.rotate(-90, barrageDo.getOffsetFromMargin(), currentPosition);
                canvas.drawText(barrageDo.getText(), barrageDo.getOffsetFromMargin(), currentPosition, paint);
                canvas.restore();
            }
            // actor moves
            currentPosition += realVelocity;
            if (barrageDo.getAcceleration() > 0) {
                realVelocity += realAcceleration;
            }
            if (LogUtil.isShow()) {
                StringBuilder builder = new StringBuilder();
                builder.append(" text: " + barrageDo.getText())
                        .append(" currentPos: "+currentPosition)
                        .append(" realVelocity: " + realVelocity)
                        .append(" acce: " + realAcceleration);
                LogUtil.d("actor", builder.toString());
            }
        }
    }
}
