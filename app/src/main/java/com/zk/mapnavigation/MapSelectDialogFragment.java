package com.zk.mapnavigation;

import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;
import java.util.regex.Pattern;

import static com.zk.mapnavigation.MapTranslateUtils.map_bd2hx;

/**
 * author: ZK.
 * date:   On 2017/11/17.
 */
public class MapSelectDialogFragment extends DialogFragment implements View.OnClickListener {


    private android.widget.TextView tvbaidu;
    private android.widget.TextView tvgaode;
    private android.widget.TextView tvtencent;
    private android.widget.RelativeLayout rlroot;

    private double mStartLat;
    private double mStartLng;
    private double mEndLat;
    private double mEndLng;
    private String mStartAddress;
    private String mEndAddress;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.TranslucentDialogFragment);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_select_map, null);
        getDialog().setCanceledOnTouchOutside(false);
        this.rlroot = (RelativeLayout) view.findViewById(R.id.rl_root);
        this.tvtencent = (TextView) view.findViewById(R.id.tv_tencent);
        this.tvgaode = (TextView) view.findViewById(R.id.tv_gaode);
        this.tvbaidu = (TextView) view.findViewById(R.id.tv_baidu);

        tvtencent.setOnClickListener(this);
        tvgaode.setOnClickListener(this);
        tvbaidu.setOnClickListener(this);

        return view;
    }


    public void setLatLng(double startLat, double startLng, double endLat, double endLng, String startAddress, String endAddress) {
        mStartLat = startLat;
        mStartLng = startLng;
        mEndLat = endLat;
        mEndLng = endLng;
        mStartAddress = startAddress;
        mEndAddress = endAddress;
        if (mStartAddress != null)
            mStartAddress = Pattern.compile(".*省").matcher(mStartAddress).replaceAll("");
        if (mEndAddress != null)
            mEndAddress = Pattern.compile(".*省").matcher(mEndAddress).replaceAll("");
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_baidu:
                selectBaidu();
                break;
            case R.id.tv_gaode:
                selectGaode();
                break;
            case R.id.tv_tencent:
                selectTencent();
                break;

        }
    }

    private void selectBaidu() {

        //调起App
        if (isInstallByread(getString(R.string.baidu_map_package))) {

            Intent intent = new Intent();
            intent.setData(Uri.parse("baidumap://map/direction?origin=name:" + mStartAddress + "|latlng:" + mStartLat + "," +
                    mStartLng + "&destination=:" + mEndAddress + "&mode=driving"));
            getActivity().startActivity(intent);

        } else {

           /* NaviParaOption para = new NaviParaOption().startPoint(new LatLng(mStartLat
                    , mStartLng)).endPoint(new LatLng(mEndLat, mEndLng));
            BaiduMapNavigation.openBaiduMapNavi(para, getBaseActivity());
            BaiduMapNavigation.setSupportWebNavi(true);*/

            String url = "http://api.map.baidu.com/direction?origin=latlng:" + mEndLat + ","
                    + mEndLng + "|name:&destination=" + mEndAddress + "&mode=driving&output=html&src=我的项目名称";
            WebViewActivity.launch(getActivity(), url);

        }
    }


    public void selectGaode() {

        double[] txNowLatLng1 = map_bd2hx(mStartLat, mStartLng);
        double[] txDesLatLng1 = map_bd2hx(mEndLat, mEndLng);


        if (isInstallByread(getString(R.string.gaode_map_package))) {
            Intent intentOther = new Intent("android.intent.action.VIEW",
                    Uri.parse("androidamap://navi?sourceApplication=amap&lat="
                            + txDesLatLng1[0] + "&lon=" + txDesLatLng1[1] + "&dev=0&stype=0"));
            intentOther.setPackage("com.autonavi.minimap");
            intentOther.addCategory(Intent.CATEGORY_DEFAULT);
            startActivity(intentOther);
        } else {
            final String url = "http://m.amap.com/?from=" + txNowLatLng1[0] +
                    "," + txNowLatLng1[1] +
                    "(" + mStartAddress + ")&to=" + txDesLatLng1[0] + "," + txDesLatLng1[1] + "(" + mEndAddress + ")&type=0&opt=1&dev=0";
            WebViewActivity.launch(getActivity(), url);
        }


    }

    public void selectTencent() {
        double[] txNowLatLng1 = map_bd2hx(mStartLat, mStartLng);
        double[] txDesLatLng1 = map_bd2hx(mEndLat, mEndLng);

        final String url = "http://apis.map.qq.com/uri/v1/routeplan?type=drive&from=" +
                mStartAddress + "&fromcoord=" +
                +txNowLatLng1[0] + "," + txNowLatLng1[1] +
                "&to=" + mEndAddress +
                "&tocoord=" + txDesLatLng1[0] + "," + txDesLatLng1[1] + "&policy=0&referer=myapp";

        WebViewActivity.launch(getActivity(), url);

    }


    private boolean isInstallByread(String packageName) {
        PackageManager packageManager = getActivity().getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals(packageName)) {
                    return true;
                }
            }
        }
        return false;
        //return new File("/data/data/" + packageName).exists();
    }

    @Override
    public int show(FragmentTransaction transaction, String tag) {
        if (isAdded())
            return super.show(transaction, tag);
        return -1;
    }
}
