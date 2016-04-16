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
    private int realVelocity;
    private int realAcceleration;
    private Paint paint;
    private int width;
    private int height;
    private int SPACE = 100;

    public Actor(BarrageDo barrageDo, int width, int height) {
        if (barrageDo == null) {
            throw new NullPointerException("BarrageDo can not be null");
        }
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("width or height can not be 0");
        }
        this.barrageDo = barrageDo;
        this.width = width;
        this.height = height;
        initPositionByBoundDirection();
    }

    private void initPositionByBoundDirection() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(barrageDo.getTextColor());
        paint.setTextSize(barrageDo.getTextSize());
        int imageSize = 0;
        switch (barrageDo.getDirection()) {
            case Constants.RIGHT_LEFT:
            default:
                currentPosition = width;
                realVelocity = -barrageDo.getVelocity();
                realAcceleration = -barrageDo.getAcceleration();
                break;
            case Constants.TOP_DOWN:
                if (barrageDo.getImage() != null && barrageDo.getImageConfig() != null) {
                    imageSize = barrageDo.getImageConfig().height + 2 * barrageDo.getImageConfig().margin2text;
                }
                currentPosition = - 2 * (getLength(barrageDo.getText()) + imageSize);
                realVelocity = barrageDo.getVelocity();
                realAcceleration = barrageDo.getAcceleration();
                break;
            case Constants.LEFT_RIGHT:
                if (barrageDo.getImage() != null && barrageDo.getImageConfig() != null) {
                    imageSize = barrageDo.getImageConfig().width + 2 * barrageDo.getImageConfig().margin2text;
                }
                currentPosition = - 2 * (getLength(barrageDo.getText()) + imageSize);
                realVelocity = barrageDo.getVelocity();
                realAcceleration = barrageDo.getAcceleration();
                break;
            case Constants.DOWN_TOP:
                currentPosition = height;
                realVelocity = -barrageDo.getVelocity();
                realAcceleration = -barrageDo.getAcceleration();
                break;
        }
    }

    private boolean isOutOfBound() {
        boolean isOutOfBound = true;
        int imageSize = 0;
        switch (barrageDo.getDirection()) {
            case Constants.RIGHT_LEFT:
            default:
                if (barrageDo.getImage() != null && barrageDo.getImageConfig() != null) {
                    imageSize = barrageDo.getImageConfig().width + 2 * barrageDo.getImageConfig().margin2text;
                }
                if (currentPosition < - (getLength(barrageDo.getText()) + imageSize + SPACE)) {
                    isOutOfBound = true;
                } else {
                    isOutOfBound = false;
                }
                break;
            case Constants.TOP_DOWN:
                if (currentPosition > height + SPACE) {
                    isOutOfBound = true;
                } else {
                    isOutOfBound = false;
                }
                break;
            case Constants.LEFT_RIGHT:
                if (currentPosition > width + SPACE) {
                    isOutOfBound = true;
                } else {
                    isOutOfBound = false;
                }
                break;
            case Constants.DOWN_TOP:
                if (barrageDo.getImage() != null && barrageDo.getImageConfig() != null) {
                    imageSize = barrageDo.getImageConfig().height + 2 * barrageDo.getImageConfig().margin2text;
                }
                if (currentPosition < - (getLength(barrageDo.getText()) + imageSize + SPACE)) {
                    isOutOfBound = true;
                } else {
                    isOutOfBound = false;
                }
                break;
        }
        return isOutOfBound;
    }

    private int getLength(String text) {
        if (TextUtils.isEmpty(text)) {
            return 0;
        }
        return (int) paint.measureText(text);
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
            int dividePosition = 0;
            int nextPosition = currentPosition;

            if (barrageDo.getImage() != null && barrageDo.getImageConfig() != null &&
                    barrageDo.getImageConfig().position >= 0) {
                dividePosition = barrageDo.getImageConfig().position;
                if (barrageDo.getImageConfig().position > barrageDo.getText().length()) {
                    dividePosition = barrageDo.getText().length();
                }
                // draw first part
                nextPosition = drawText(canvas, barrageDo.getText().substring(0, dividePosition), barrageDo.getDirection(), nextPosition);
                // draw bitmap
                nextPosition = drawBitmap(canvas, nextPosition, barrageDo.getDirection());
            }

            if (dividePosition < barrageDo.getText().length()) {
                drawText(canvas, barrageDo.getText().substring(dividePosition), barrageDo.getDirection(), nextPosition);
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

    private int drawText(Canvas canvas, String text, int direction, int startPosition) {
        int result;
        switch (direction) {
            case Constants.RIGHT_LEFT:
            case Constants.LEFT_RIGHT:
            default:
                canvas.drawText(text, startPosition, barrageDo.getOffsetFromMargin() + barrageDo.getTextSize() / 2, paint);
                result = startPosition + getLength(text);
                break;
            case Constants.DOWN_TOP:
            case Constants.TOP_DOWN:
                canvas.save();
                canvas.rotate(90, barrageDo.getOffsetFromMargin() - barrageDo.getTextSize() / 2, startPosition);
                canvas.drawText(text, barrageDo.getOffsetFromMargin() - barrageDo.getTextSize() / 2, startPosition, paint);
                canvas.restore();
                result = startPosition + getLength(text);
                break;
        }
        return result;
    }

    private int drawBitmap(Canvas canvas, int startPosition, int direction) {
        BarrageDo.ImageConfig config = barrageDo.getImageConfig();
        Rect dest;
        startPosition += config.margin2text;
        int result;
        switch (direction) {
            case Constants.RIGHT_LEFT:
            case Constants.LEFT_RIGHT:
            default:
                dest = new Rect(startPosition, barrageDo.getOffsetFromMargin() - config.height / 2,
                        startPosition + config.width, barrageDo.getOffsetFromMargin() + config.height / 2);
                canvas.drawBitmap(barrageDo.getImage(), null, dest, null);
                result = startPosition + config.width + config.margin2text;
                break;
            case Constants.TOP_DOWN:
            case Constants.DOWN_TOP:
                dest = new Rect(barrageDo.getOffsetFromMargin() - config.width / 2, startPosition,
                        barrageDo.getOffsetFromMargin() + config.width / 2, startPosition + config.height);
                if (barrageDo.isRotateImage()) {
                    canvas.save();
                    canvas.rotate(90, barrageDo.getOffsetFromMargin(), startPosition + config.height / 2);
                    canvas.drawBitmap(barrageDo.getImage(), null, dest, null);
                    canvas.restore();
                } else {
                    canvas.drawBitmap(barrageDo.getImage(), null, dest, null);
                }
                result = startPosition + config.height + config.margin2text;
                break;
        }
        return result;
    }
}
