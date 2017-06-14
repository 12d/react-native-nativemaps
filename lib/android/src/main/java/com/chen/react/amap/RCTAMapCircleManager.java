package com.chen.react.amap;

/**
 * Created by chen on 2017/6/14.
 */

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.amap.api.maps2d.model.LatLng;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;

public class RCTAMapCircleManager extends ViewGroupManager<RCTAMapCircle> {
    private final DisplayMetrics metrics;

    public RCTAMapCircleManager(ReactApplicationContext reactContext) {
        super();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            metrics = new DisplayMetrics();
            ((WindowManager) reactContext.getSystemService(Context.WINDOW_SERVICE))
                    .getDefaultDisplay()
                    .getRealMetrics(metrics);
        } else {
            metrics = reactContext.getResources().getDisplayMetrics();
        }
    }

    @Override
    public String getName() {
        return "RCTAMapCircle";
    }

    @Override
    public RCTAMapCircle createViewInstance(ThemedReactContext context) {
        return new RCTAMapCircle(context);
    }

    @ReactProp(name = "center")
    public void setCenter(RCTAMapCircle view, ReadableMap center) {
        view.setCenter(new LatLng(center.getDouble("latitude"), center.getDouble("longitude")));
    }

    @ReactProp(name = "radius", defaultDouble = 0)
    public void setRadius(RCTAMapCircle view, double radius) {
        view.setRadius(radius);
    }

    @ReactProp(name = "strokeWidth", defaultFloat = 1f)
    public void setStrokeWidth(RCTAMapCircle view, float widthInPoints) {
        float widthInScreenPx = metrics.density * widthInPoints; // done for parity with iOS
        view.setStrokeWidth(widthInScreenPx);
    }

    @ReactProp(name = "fillColor", defaultInt = Color.RED, customType = "Color")
    public void setFillColor(RCTAMapCircle view, int color) {
        view.setFillColor(color);
    }

    @ReactProp(name = "strokeColor", defaultInt = Color.RED, customType = "Color")
    public void setStrokeColor(RCTAMapCircle view, int color) {
        view.setStrokeColor(color);
    }

    @ReactProp(name = "zIndex", defaultFloat = 1.0f)
    public void setZIndex(RCTAMapCircle view, float zIndex) {
        view.setZIndex(zIndex);
    }

}