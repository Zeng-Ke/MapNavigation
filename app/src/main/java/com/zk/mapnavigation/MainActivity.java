package com.zk.mapnavigation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    // 起点坐标 113.40694,23.049772 百度坐标
    private double nowLat = 23.049772;
    private double nowLng = 113.40694;
    private String nowLocation = "广州大学城大学城南地铁站";

    // 目的坐标  113.32983,23.112109

    private double desLat = 23.112109;
    private double desLng = 113.32983;
    private String desAddress = "广州塔";
    private MapSelectDialogFragment mMapSelectDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void show(View view) {
        if (mMapSelectDialogFragment == null) {
            mMapSelectDialogFragment = new MapSelectDialogFragment();
            mMapSelectDialogFragment.setLatLng(nowLat, nowLng, desLat, desLng, nowLocation, desAddress);
        }
        mMapSelectDialogFragment.show(getFragmentManager(), "MapSelectDialogFragment");
    }
}
