package com.android.liuzhuang.library.model;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.android.liuzhuang.library.Constants;
import com.android.liuzhuang.library.util.ContextUtil;
import com.android.liuzhuang.library.util.DeviceUtil;

/**
 * The base data Object of barrage
 * Created by liuzhuang on 16/4/7.
 */
public class BarrageDo {

    private String text = "";
    private int textColor = Color.BLACK;
    private int textSize = DeviceUtil.dp2px(24, ContextUtil.getApplicationContext());
    private Bitmap image;
    private ImageConfig imageConfig;
    private int velocity;
    private int acceleration;
    private int direction = Constants.RIGHT_LEFT;

    /**The during millisecond when this item shows since starting. If -1, it will show immediately.*/
    private long millisecondFromStart;

    /**The offset from margin of this item. If horizontal margin top, if vertical margin left*/
    private int offsetFromMargin;

    /**how many times has be shown*/
    private int shownTime;

    /**rotate the image or not when direction is TOP_DOWN or DOWN_TOP*/
    private boolean rotateImage = true;

    private BarrageDo(Builder builder) {
        text = builder.text;
        textColor = builder.textColor;
        textSize = builder.textSize;
        image = builder.image;
        imageConfig = builder.imageConfig;
        velocity = builder.velocity;
        millisecondFromStart = builder.millisecondFromStart;
        offsetFromMargin = builder.offsetFromMargin;
        direction = builder.direction;
        acceleration = builder.acceleration;
        rotateImage = builder.rotateImage;
    }

    public boolean isRotateImage() {
        return rotateImage;
    }

    public int getShownTime() {
        return shownTime;
    }

    public void showOnce() {
        shownTime ++;
    }

    public String getText() {
        return text;
    }

    public int getTextColor() {
        return textColor;
    }

    public int getTextSize() {
        return textSize;
    }

    public Bitmap getImage() {
        return image;
    }

    public ImageConfig getImageConfig() {
        return imageConfig;
    }

    public int getVelocity() {
        return velocity;
    }

    public long getMillisecondFromStart() {
        return millisecondFromStart;
    }

    public int getOffsetFromMargin() {
        return offsetFromMargin;
    }

    public int getDirection() {
        return direction;
    }

    public int getAcceleration() {
        return acceleration;
    }

    public static class Builder {
        private String text = "";
        private int textColor = Color.BLACK;
        private int textSize = DeviceUtil.dp2px(32, ContextUtil.getApplicationContext());
        private Bitmap image;
        private ImageConfig imageConfig = new ImageConfig(DeviceUtil.dp2px(64, ContextUtil.getApplicationContext()),
                DeviceUtil.dp2px(64, ContextUtil.getApplicationContext()), 0, DeviceUtil.dp2px(2, ContextUtil.getApplicationContext()));
        private int velocity = DeviceUtil.dp2px(3, ContextUtil.getApplicationContext());
        private int acceleration = 0;
        /**when will the item show, if -1, it will show immediately.*/
        private long millisecondFromStart = -1;
        private int offsetFromMargin = DeviceUtil.dp2px(50, ContextUtil.getApplicationContext());
        private int direction = Constants.RIGHT_LEFT;
        /**rotate the image or not when direction is TOP_DOWN or DOWN_TOP*/
        private boolean rotateImage = true;

        public Builder setText(String text) {
            this.text = text;
            return this;
        }

        public Builder setTextColor(int textColor) {
            this.textColor = textColor;
            return this;
        }

        public Builder setTextSize(int textSize) {
            this.textSize = textSize;
            return this;
        }

        public Builder setImage(Bitmap image) {
            this.image = image;
            return this;
        }

        public Builder setImageConfig(ImageConfig imageConfig) {
            this.imageConfig = imageConfig;
            return this;
        }

        public Builder setVelocity(int velocity) {
            this.velocity = velocity;
            return this;
        }

        public Builder setMillisecondFromStart(long millisecondFromStart) {
            this.millisecondFromStart = millisecondFromStart;
            return this;
        }

        public Builder setAcceleration(int acceleration) {
            this.acceleration = acceleration;
            return this;
        }

        public Builder setRotateImage(boolean rotateImage) {
            this.rotateImage = rotateImage;
            return this;
        }

        public Builder setDirection(int direction) {
            if (direction == Constants.RIGHT_LEFT ||
                    direction == Constants.LEFT_RIGHT ||
                    direction == Constants.TOP_DOWN ||
                    direction == Constants.DOWN_TOP) {
                this.direction = direction;
            } else {
                throw new IllegalArgumentException("direction is illegal!");
            }
            return this;
        }

        public Builder setOffsetFromMargin(int offsetFromMargin) {
            this.offsetFromMargin = offsetFromMargin;
            return this;
        }

        public BarrageDo build() {
            return new BarrageDo(this);
        }
    }

    public static class ImageConfig {
        /**The width of ImageView*/
        public int width;
        /**The width of ImageView*/
        public int height;
        /**The position of ImageView relative to text, if 0, the first position, if text.length the last position*/
        public int position;

        public int margin2text;

        public ImageConfig(int width, int height, int position, int margin2text) {
            this.width = width;
            this.height = height;
            this.position = position;
            this.margin2text = margin2text;
        }
    }
}
