package com.chen.react.amap;

import android.os.StrictMode;
import android.view.View;

import com.airbnb.android.react.maps.SizeReportingShadowNode;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.MapsInitializer;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.LatLngBounds;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.uimanager.LayoutShadowNode;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.events.RCTEventEmitter;

import java.util.Map;

import javax.annotation.Nullable;

/**
 * Created by chen on 2017/6/13.
 */

public class RCTAMapManager extends ViewGroupManager<RCTAMap> {

    public static final String REACT_CLASS = "RCTAMap";

    public static final int ANIMATE_TO_REGION = 1;
    public static final int ANIMATE_TO_COORDINATE = 2;
    public static final int FIT_TO_ELEMENTS = 3;
    public static final int ANIMATE_TO_ZOOM_LEVEL = 4;

    private final Map<String, Integer> MAP_TYPES = MapBuilder.of(
            "standard", AMap.MAP_TYPE_NORMAL,
            "satellite", AMap.MAP_TYPE_SATELLITE
    );

    private ReactContext reactContext;


    public RCTAMapManager(ReactContext reactContext) {

    }

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Override
    protected RCTAMap createViewInstance(ThemedReactContext context) {
        reactContext = context;
        RCTAMap view = new RCTAMap(context, this);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            MapsInitializer.initialize(context.getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
            emitMapError("AMap initialize error", "amap_init_error");
        }

        return view;
    }

    @Override
    public void onDropViewInstance(RCTAMap view) {
        view.doDestroy();
        super.onDropViewInstance(view);
    }

    private void emitMapError(String message, String type) {
        WritableMap error = Arguments.createMap();
        error.putString("message", message);
        error.putString("type", type);

        reactContext
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit("onError", error);
    }

    @ReactProp(name = "region")
    public void setRegion(RCTAMap view, ReadableMap region) {
        view.setRegion(region);
    }

    @ReactProp(name = "mapType", defaultInt = AMap.MAP_TYPE_NORMAL)
    public void setMapType(RCTAMap view, @Nullable String mapType) {
        int typeId = MAP_TYPES.get(mapType);
        view.map.setMapType(typeId);
    }

    @ReactProp(name = "showsUserLocation", defaultBoolean = false)
    public void setShowsUserLocation(RCTAMap view, boolean showUserLocation) {
        view.setShowsUserLocation(showUserLocation);
    }

    @ReactProp(name = "showsTraffic", defaultBoolean = false)
    public void setShowTraffic(RCTAMap view, boolean showTraffic) {
        view.map.setTrafficEnabled(showTraffic);
    }

//    @ReactProp(name = "showsBuildings", defaultBoolean = false)
//    public void setShowBuildings(RCTAMap view, boolean showBuildings) {
//        view.map.setBuildingsEnabled(showBuildings);
//    }

    //// TODO: 16/5/19
//    @ReactProp(name = "showsIndoors", defaultBoolean = false)
//    public void setShowIndoors(RCTAMap view, boolean showIndoors) {
//        view.map.setIndoorEnabled(showIndoors);
//    }

    @ReactProp(name = "showsCompass", defaultBoolean = false)
    public void setShowsCompass(RCTAMap view, boolean showsCompass) {
        view.map.getUiSettings().setCompassEnabled(showsCompass);
    }

    @ReactProp(name = "showsScale", defaultBoolean = false)
    public void setShowsScale(RCTAMap view, boolean showsScale) {
        view.map.getUiSettings().setScaleControlsEnabled(showsScale);
    }

    @ReactProp(name = "scrollEnabled", defaultBoolean = false)
    public void setScrollEnabled(RCTAMap view, boolean scrollEnabled) {
        view.map.getUiSettings().setScrollGesturesEnabled(scrollEnabled);
    }

    @ReactProp(name = "zoomEnabled", defaultBoolean = false)
    public void setZoomEnabled(RCTAMap view, boolean zoomEnabled) {
        view.map.getUiSettings().setZoomGesturesEnabled(zoomEnabled);
        view.map.getUiSettings().setZoomControlsEnabled(zoomEnabled);
    }

//    @ReactProp(name = "zoomLevel", defaultDouble = 10.0)
//    public void  setZoomLevel(RCTAMap view, double zoomLevel) {
//        view.setZoomLevel((float) zoomLevel);
//    }
//    @ReactProp(name = "rotateEnabled", defaultBoolean = false)
//    public void setRotateEnabled(RCTAMap view, boolean rotateEnabled) {
//        view.map.getUiSettings().setRotateGesturesEnabled(rotateEnabled);
//    }

//    @ReactProp(name = "pitchEnabled", defaultBoolean = false)
//    public void setPitchEnabled(RCTAMap view, boolean pitchEnabled) {
//        view.map.getUiSettings().setTiltGesturesEnabled(pitchEnabled);
//    }

    @Override
    public void receiveCommand(RCTAMap view, int commandId, @Nullable ReadableArray args) {
        Integer duration;
        Double lat;
        Double lng;
        Double lngDelta;
        Double latDelta;
        ReadableMap region;
        Double zoomLevel;

        switch (commandId) {
            case ANIMATE_TO_REGION:
                region = args.getMap(0);
                duration = args.getInt(1);
                lng = region.getDouble("longitude");
                lat = region.getDouble("latitude");
                lngDelta = region.hasKey("longitudeDelta")?region.getDouble("longitudeDelta"):0.0;
                latDelta = region.hasKey("latitudeDelta")?region.getDouble("latitudeDelta"):0.0;
                try{
                    LatLngBounds bounds = new LatLngBounds(
                            new LatLng(lat - latDelta / 2, lng - lngDelta / 2), // southwest
                            new LatLng(lat + latDelta / 2, lng + lngDelta / 2)  // northeast
                    );
                    view.animateToRegion(bounds, duration);
                }catch(Exception e){

                }
                break;

            case ANIMATE_TO_COORDINATE:
                region = args.getMap(0);
                duration = args.getInt(1);
                lng = region.getDouble("longitude");
                lat = region.getDouble("latitude");
                view.animateToCoordinate(new LatLng(lat, lng), duration);
                break;

            case ANIMATE_TO_ZOOM_LEVEL:
                zoomLevel = args.getDouble(0);
                view.setZoomLevel(zoomLevel.floatValue());
                break;
            case FIT_TO_ELEMENTS:
                view.fitToElements(args.getBoolean(0));
                break;
        }
    }

    @Override
    @Nullable
    public Map getExportedCustomDirectEventTypeConstants() {
        Map map = MapBuilder.of(
                "onMapReady", MapBuilder.of("registrationName", "onMapReady"),
                "onPress", MapBuilder.of("registrationName", "onPress"),
                "onLongPress", MapBuilder.of("registrationName", "onLongPress"),
                "onMarkerPress", MapBuilder.of("registrationName", "onMarkerPress"),
                "onMarkerSelect", MapBuilder.of("registrationName", "onMarkerSelect"),
                "onMarkerDeselect", MapBuilder.of("registrationName", "onMarkerDeselect"),
                "onCalloutPress", MapBuilder.of("registrationName", "onCalloutPress")
        );

        map.putAll(MapBuilder.of(
                "onMarkerDragStart", MapBuilder.of("registrationName", "onMarkerDragStart"),
                "onMarkerDrag", MapBuilder.of("registrationName", "onMarkerDrag"),
                "onMarkerDragEnd", MapBuilder.of("registrationName", "onMarkerDragEnd")
        ));

        return map;
    }

    @Override
    @Nullable
    public Map<String, Integer> getCommandsMap() {
        return MapBuilder.of(
                "animateToRegion", ANIMATE_TO_REGION,
                "animateToCoordinate", ANIMATE_TO_COORDINATE,
                "fitToElements", FIT_TO_ELEMENTS,
                "animateToZoomLevel", ANIMATE_TO_ZOOM_LEVEL
        );
    }

    @Override
    public LayoutShadowNode createShadowNodeInstance() {
        // A custom shadow node is needed in order to pass back the width/height of the map to the
        // view manager so that it can start applying camera moves with bounds.
        return new SizeReportingShadowNode();
    }

    @Override
    public void addView(RCTAMap parent, View child, int index) {
        parent.addFeature(child, index);
    }

    @Override
    public int getChildCount(RCTAMap view) {
        return view.getFeatureCount();
    }

    @Override
    public View getChildAt(RCTAMap view, int index) {
        return view.getFeatureAt(index);
    }

    @Override
    public void removeViewAt(RCTAMap parent, int index) {
        parent.removeFeatureAt(index);
    }

    @Override
    public void updateExtraData(RCTAMap view, Object extraData) {
        view.updateExtraData(extraData);
    }

    public void pushEvent(View view, String name, WritableMap data) {
        ReactContext reactContext = (ReactContext) view.getContext();
        reactContext.getJSModule(RCTEventEmitter.class)
                .receiveEvent(view.getId(), name, data);
    }

}
