package com.chen.react.amap;

import android.content.Context;
import android.util.Log;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.model.TileOverlay;
import com.amap.api.maps2d.model.TileOverlayOptions;
import com.amap.api.maps2d.model.TileProvider;
import com.amap.api.maps2d.model.UrlTileProvider;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by chen on 2017/6/13.
 */

public class RCTAMapUrlTile extends RCTAMapFeature {
    private TileOverlay tileOverlay;
    private TileOverlayOptions tileOverlayOptions;
    private RCTAMapUrlTileProvider tileProvider;
    private String urlTemplate;
    private float zIndex;
    class RCTAMapUrlTileProvider extends UrlTileProvider
    {
        private String urlTemplate;
        public RCTAMapUrlTileProvider(int width, int height, String urlTemplate) {
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

    public RCTAMapUrlTile(Context context) {
        super(context);
    }
    public void setUrlTemplate(String urlTemplate){
        Log.i("xxxxxx", urlTemplate);
        this.urlTemplate = urlTemplate;
        if (tileProvider != null) {
            tileProvider.setUrlTemplate(urlTemplate);
        }
        if (tileOverlay != null) {
            tileOverlay.clearTileCache();
        }
    }
    public void setZIndex(float zIndex){

    }
    @Override
    public void addToMap(AMap amap){
        Log.i("xxxxx","add to map");


          final String url = this.urlTemplate;
//        final String url="http://developer.baidu.com/map/jsdemo/demo/tiles/%d/tile/%d_%d.png";

        TileProvider tileProvider = new UrlTileProvider(256, 256) {
            @Override
            public synchronized URL getTileUrl(int x, int y, int zoom) {
                try {
                    Log.i("xxxxx",String.format(url, x, y, zoom));
//                    return new URL(String.format(url, 16,x, y));
                    return new URL(String.format(url, x, y, zoom));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        if (tileProvider != null) {
            tileOverlay = amap.addTileOverlay(new TileOverlayOptions()
                    .tileProvider(tileProvider)
                    .diskCacheEnabled(true)
                    .diskCacheDir("/storage/emulated/0/amap/cache")
                    .diskCacheSize(100000)
                    .memoryCacheEnabled(true)
                    .memCacheSize(100000)
                    );
            tileOverlay.setVisible(true);
        }
    }
    @Override
    public void removeFromMap(AMap amap){

    }
    @Override
    public TileOverlay getFeature(){
        return tileOverlay;
    }
}
