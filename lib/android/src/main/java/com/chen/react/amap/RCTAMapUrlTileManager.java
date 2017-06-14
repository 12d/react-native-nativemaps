package com.chen.react.amap;

import android.util.Log;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;

/**
 * Created by chen on 2017/6/13.
 */

public class RCTAMapUrlTileManager extends ViewGroupManager<RCTAMapUrlTile> {
    public RCTAMapUrlTileManager(ReactApplicationContext reactContext) {

        super();
//        Log.i("xxxxxx","RCTAMapUrlTileManager");
    }

    @Override
    public String getName() {
        return "RCTAMapUrlTile";
    }

    @Override
    public RCTAMapUrlTile createViewInstance(ThemedReactContext context) {
        Log.i("xxxxxx","createViewInstance RCTAMapUrlTileManager");
        return new RCTAMapUrlTile(context);
    }

    @ReactProp(name = "urlTemplate")
    public void setUrlTemplate(RCTAMapUrlTile view, String urlTemplate) {
        view.setUrlTemplate(urlTemplate);
    }

    @ReactProp(name = "zIndex", defaultFloat = -1.0f)
    public void setZIndex(RCTAMapUrlTile view, float zIndex) {
        view.setZIndex(zIndex);
    }
}
