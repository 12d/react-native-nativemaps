package com.chen.react.amap;


import android.content.Context;
import android.view.LayoutInflater;

import com.airbnb.android.react.maps.R;
import com.amap.api.maps2d.MapView;
import com.facebook.react.views.view.ReactViewGroup;

/**
 * Created by chen on 2017/6/13.
 */

public class RCTAMap extends ReactViewGroup {
    private MapView mapView;
    public RCTAMap(Context reactContext) {

        super(reactContext );
        mapView = (MapView) LayoutInflater.from(reactContext).inflate(R.layout.map, this);

    }
}
