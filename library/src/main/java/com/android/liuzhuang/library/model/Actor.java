package com.android.liuzhuang.library.model;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;

import com.android.liuzhuang.library.Constants;

/**
 * The actor of each item, it stores the current position and validation of item.
 * Created by liuzhuang on 16/4/8.
 */
public final class Actor {
    private BarrageDo barrageDo;
    private boolean isValid;
    private int currentPosition;
    /** The direction of barrage. */
    private int direction;
    private Rect bound;
    private int realVelocity;

    public Actor(BarrageDo barrageDo, Rect bound, int direction) {
        if (barrageDo == null) {
            throw new NullPointerException("BarrageDo can not be null");
        }
        if (bound == null) {
            throw new NullPointerException("Bound can not be null");
        }
        this.barrageDo = barrageDo;
        this.direction = direction;
        this.bound = bound;
        initPositionByBoundDirection();
    }

    private void initPositionByBoundDirection() {
        switch (direction) {
            case Constants.RIGHT_LEFT:
            default:
                currentPosition = bound.right;
                realVelocity = -barrageDo.getVelocity();
                break;
            case Constants.TOP_DOWN:
                currentPosition = bound.top - getLength();
                realVelocity = barrageDo.getVelocity();
                break;
            case Constants.LEFT_RIGHT:
                currentPosition = bound.left - getLength();
                realVelocity = barrageDo.getVelocity();
                break;
            case Constants.DOWN_TOP:
                currentPosition = bound.bottom;
                realVelocity = -barrageDo.getVelocity();
                break;
        }
    }

    private boolean isOutOfBound() {
        boolean isOutOfBound = true;
        switch (direction) {
            case Constants.RIGHT_LEFT:
            default:
                if (currentPosition < bound.left - getLength()) {
                    isOutOfBound = true;
                } else {
                    isOutOfBound = false;
                }
                break;
            case Constants.TOP_DOWN:
                if (currentPosition > bound.bottom + getLength()) {
                    isOutOfBound = true;
                } else {
                    isOutOfBound = false;
                }
                break;
            case Constants.LEFT_RIGHT:
                if (currentPosition > bound.right + getLength()) {
                    isOutOfBound = true;
                } else {
                    isOutOfBound = false;
                }
                break;
            case Constants.DOWN_TOP:
                if (currentPosition < bound.top - getLength()) {
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
        } else if (duringMilliseconds >= barrageDo.getMillisecondFromStart() && !isOutOfBound()) {
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
            if (direction == Constants.RIGHT_LEFT) {
                canvas.drawText(barrageDo.getText(), currentPosition, barrageDo.getOffsetFromMargin(), paint);
            } else if (direction == Constants.TOP_DOWN) {
                canvas.save();
                canvas.rotate(90, barrageDo.getOffsetFromMargin(), currentPosition);
                canvas.drawText(barrageDo.getText(), barrageDo.getOffsetFromMargin(), currentPosition, paint);
                canvas.restore();
            } else if (direction == Constants.LEFT_RIGHT) {
                canvas.drawText(barrageDo.getText(), currentPosition, barrageDo.getOffsetFromMargin(), paint);
            } else if (direction == Constants.DOWN_TOP) {
                canvas.save();
                canvas.rotate(-90, barrageDo.getOffsetFromMargin(), currentPosition);
                canvas.drawText(barrageDo.getText(), barrageDo.getOffsetFromMargin(), currentPosition, paint);
                canvas.restore();
            }
            // actor moves
            currentPosition += realVelocity;
        }
    }
}
