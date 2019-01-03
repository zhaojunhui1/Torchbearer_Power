package com.zjh.administrat.torchbearer_power.bag.apps;

import android.app.Application;
import android.graphics.Bitmap;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class Apps extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //Fresco显示图片
        Fresco.initialize(this);
        //ImageLoader
        ImageLoader.getInstance().init(new ImageLoaderConfiguration.Builder(this)
                .memoryCacheSizePercentage(10)
                .diskCacheSize(50 * 1024 * 1024)
                .defaultDisplayImageOptions(new DisplayImageOptions.Builder()
                        .cacheOnDisk(true)
                        .cacheInMemory(true)
                        .bitmapConfig(Bitmap.Config.RGB_565)
                        .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                        .displayer(new RoundedBitmapDisplayer(8))
                        .build()
                )
                .build()
        );


    }
}
