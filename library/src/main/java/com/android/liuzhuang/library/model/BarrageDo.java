package com.android.liuzhuang.library.model;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.android.liuzhuang.library.Constants;

/**
 * The base data Object of barrage
 * Created by liuzhuang on 16/4/7.
 */
public class BarrageDo {

    private String text;
    private int textColor;
    private int textSize;
    private Bitmap image;
    private ImageSize imageSize;
    private Arrange arrange;
    private int velocity;
    private int acceleration;
    private int direction = Constants.RIGHT_LEFT;
    /**The during millisecond when this item shows since starting. If -1, it will show immediately.*/
    private long millisecondFromStart;
    /**The offset from margin of this item. If horizontal margin top, if vertical margin left*/
    private int offsetFromMargin;

    private int shownTime;

    private BarrageDo(Builder builder) {
        text = builder.text;
        textColor = builder.textColor;
        textSize = builder.textSize;
        image = builder.image;
        imageSize = builder.imageSize;
        arrange = builder.arrange;
        velocity = builder.velocity;
        millisecondFromStart = builder.millisecondFromStart;
        offsetFromMargin = builder.offsetFromMargin;
        direction = builder.direction;
        acceleration = builder.acceleration;
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

    public ImageSize getImageSize() {
        return imageSize;
    }

    public Arrange getArrange() {
        return arrange;
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
        private String text;
        private int textColor = Color.BLACK;
        private int textSize = 50;
        private Bitmap image;
        private ImageSize imageSize;
        private Arrange arrange;
        private int velocity = 10;
        private int acceleration;
        /**when will the item show, if -1, it will show immediately.*/
        private long millisecondFromStart = -1;
        private int offsetFromMargin = 100;
        private int direction = Constants.RIGHT_LEFT;

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

        public Builder setImageSize(ImageSize imageSize) {
            this.imageSize = imageSize;
            return this;
        }

        public Builder setArrange(Arrange arrange) {
            this.arrange = arrange;
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

    public static class ImageSize {
        public int width;
        public int height;
    }

    public enum Arrange {
        IMAGE_TEXT, TEXT_IMAGE
    }
}
