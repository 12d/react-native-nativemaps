package com.airbnb.android.react.maps;

import android.app.Activity;
import android.util.Log;

import com.chen.react.amap.RCTAMapCircleManager;
import com.chen.react.amap.RCTAMapManager;
import com.chen.react.amap.RCTAMapMarkerManager;
import com.chen.react.amap.RCTAMapUrlTileManager;
import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MapsPackage implements ReactPackage {
    public MapsPackage(Activity activity) {
    } // backwards compatibility

    public MapsPackage() {
    }

    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
        return Arrays.<NativeModule>asList(new AirMapModule(reactContext));
    }

    @Override
    public List<Class<? extends JavaScriptModule>> createJSModules() {
        return Collections.emptyList();
    }

    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
        AirMapCalloutManager calloutManager = new AirMapCalloutManager();
        AirMapMarkerManager annotationManager = new AirMapMarkerManager();
        AirMapPolylineManager polylineManager = new AirMapPolylineManager(reactContext);
        AirMapPolygonManager polygonManager = new AirMapPolygonManager(reactContext);
        AirMapCircleManager circleManager = new AirMapCircleManager(reactContext);
        AirMapManager mapManager = new AirMapManager(reactContext);
        AirMapLiteManager mapLiteManager = new AirMapLiteManager(reactContext);
        AirMapUrlTileManager tileManager = new AirMapUrlTileManager(reactContext);


        //高德地图
        RCTAMapManager amapManager = new RCTAMapManager(reactContext);
        RCTAMapCircleManager amapCircleManager = new RCTAMapCircleManager(reactContext);
        RCTAMapMarkerManager amapMarkerManager = new RCTAMapMarkerManager(reactContext);


        Log.d("xxxxx","asdfasdfasdf");
        RCTAMapUrlTileManager aMapUrlTitleManager = new RCTAMapUrlTileManager(reactContext);

        return Arrays.<ViewManager>asList(
                calloutManager,
                annotationManager,
                polylineManager,
                polygonManager,
                circleManager,
                mapManager,
                mapLiteManager,
                tileManager,
                amapManager,
                amapCircleManager,
                amapMarkerManager,
                aMapUrlTitleManager

        );

    }
}
