package com.chen.react.amap;

import android.content.Context;
import android.widget.FrameLayout;

import com.amap.api.maps2d.AMap;

/**
 * Created by chen on 2017/6/13.
 */

public abstract class RCTAMapFeature extends FrameLayout{
    public RCTAMapFeature(Context context) {
        super(context);
    }

    public abstract void addToMap(AMap map);

    public abstract void removeFromMap(AMap map);

    public abstract Object getFeature();
}
