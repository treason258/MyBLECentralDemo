package com.mjiayou.trecore.bean;

/**
 * Created by treason on 2018/3/8.
 */

public class FloatTagBean {

    private float percentX; // x轴百分比，圆点在上
    private float percentY; // y轴百分比，圆点在左
    private String text; // 文字描述
    private boolean toLeft = true; // 文字描述箭头方向是否朝左，默认朝左

    public float getPercentX() {
        return percentX;
    }

    public void setPercentX(float percentX) {
        this.percentX = percentX;
    }

    public float getPercentY() {
        return percentY;
    }

    public void setPercentY(float percentY) {
        this.percentY = percentY;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isToLeft() {
        return toLeft;
    }

    public void setToLeft(boolean toLeft) {
        this.toLeft = toLeft;
    }
}
