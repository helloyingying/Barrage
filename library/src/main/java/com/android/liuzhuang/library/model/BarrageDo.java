package com.android.liuzhuang.library.model;

import android.graphics.Bitmap;

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
    /**The during millisecond when this item shows since starting.*/
    private long millisecondFromStart;
    /**The offset from margin of this item.*/
    private int offsetFromMargin;

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

    public static class Builder {
        private String text;
        private int textColor;
        private int textSize;
        private Bitmap image;
        private ImageSize imageSize;
        private Arrange arrange;
        private int velocity;
        private long millisecondFromStart;
        private int offsetFromMargin;

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

        public Builder setTime(long millisecondFromStart) {
            this.millisecondFromStart = millisecondFromStart;
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
