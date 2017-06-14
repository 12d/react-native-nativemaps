package com.chen.react.amap;

import android.content.Context;
import android.util.Log;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdate;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.TileOverlay;
import com.amap.api.maps2d.model.TileOverlayOptions;
import com.amap.api.maps2d.model.UrlTileProvider;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by chen on 2017/6/13.
 */

public class RCTAMapUrlTile extends RCTAMapFeature {
    private TileOverlay tileOverlay;
    private TileOverlayOptions tileOverlayOptions;
    private String urlTemplate;
    private float zIndex;
    private  RCTMapUrlTileProvider tileProvider;

    class RCTMapUrlTileProvider extends UrlTileProvider
    {
        private String urlTemplate;
        public RCTMapUrlTileProvider(int width, int height, String urlTemplate) {
            super(width, height);
            this.urlTemplate = urlTemplate;
        }
        @Override
        public synchronized URL getTileUrl(int x, int y, int zoom) {

            String s = this.urlTemplate
                    .replace("{x}", Integer.toString(x))
                    .replace("{y}", Integer.toString(y))
                    .replace("{z}", Integer.toString(zoom));
            URL url = null;
            Log.i("xxxxx",s);
            try {
                url = new URL(s);
            } catch (MalformedURLException e) {
                throw new AssertionError(e);
            }
            return url;
        }

        public void setUrlTemplate(String urlTemplate) {
            this.urlTemplate = urlTemplate;
        }
    }
    private TileOverlayOptions createTileOverlayOptions() {
        TileOverlayOptions options = new TileOverlayOptions();
        options.zIndex(zIndex);
        this.tileProvider = new RCTMapUrlTileProvider(256, 256, this.urlTemplate);
        options.tileProvider(this.tileProvider);

        return options;
    }
    public TileOverlayOptions getTileOverlayOptions() {
        if (tileOverlayOptions == null) {
            tileOverlayOptions = createTileOverlayOptions();
        }
        return tileOverlayOptions;
    }
    public RCTAMapUrlTile(Context context) {
        super(context);

    }
    public void setUrlTemplate(String urlTemplate){
        this.urlTemplate = urlTemplate;
        if (tileProvider != null) {
            tileProvider.setUrlTemplate(urlTemplate);
        }
        if (tileOverlay != null) {
            tileOverlay.clearTileCache();
        }
    }
    public void setZIndex(float zIndex) {
        this.zIndex = zIndex;
        if (tileOverlay != null) {
            tileOverlay.setZIndex(zIndex);
        }
    }
    @Override
    public void addToMap(AMap amap){
        Log.i("xxxxx","add to map");
        this.tileOverlay = amap.addTileOverlay(getTileOverlayOptions());


        //hack to 初始化的时候覆盖物不显示
        CameraPosition cameraPosition = amap.getCameraPosition();
        CameraUpdate cameraUpdate = CameraUpdateFactory.zoomBy(+1);
        amap.moveCamera(cameraUpdate);
        CameraUpdate cameraUpdate2 = CameraUpdateFactory.zoomBy(-1);
        amap.moveCamera(cameraUpdate2);

    }
    @Override
    public void removeFromMap(AMap amap){

    }
    @Override
    public TileOverlay getFeature(){
        return tileOverlay;
    }
}
