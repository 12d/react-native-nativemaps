package com.chen.react.amap;
import android.content.Context;

import com.facebook.react.views.view.ReactViewGroup;


public class RCTAMapCallout extends ReactViewGroup {
    private boolean tooltip = false;
    public int width;
    public int height;

    public RCTAMapCallout(Context context) {
        super(context);
    }

    public void setTooltip(boolean tooltip) {
        this.tooltip = tooltip;
    }

    public boolean getTooltip() {
        return this.tooltip;
    }
}