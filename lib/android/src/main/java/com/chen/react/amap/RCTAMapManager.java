package com.chen.react.amap;

import com.airbnb.android.react.maps.AirMapUrlTile;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;

/**
 * Created by chen on 2017/6/13.
 */

public class RCTAMapManager extends ViewGroupManager<RCTAMap> {
    public RCTAMapManager (ReactApplicationContext reactContext){
        super();
    }
    @Override
    public String getName() {
        return "RCTAMap";
    }

    @Override
    public RCTAMap createViewInstance(ThemedReactContext context) {
        return new RCTAMap(context);
    }

    @ReactProp(name = "urlTemplate")
    public void setUrlTemplate(AirMapUrlTile view, String urlTemplate) {
        view.setUrlTemplate(urlTemplate);
    }
}
