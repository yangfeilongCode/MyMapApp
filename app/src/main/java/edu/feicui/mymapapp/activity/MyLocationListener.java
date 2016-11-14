package edu.feicui.mymapapp.activity;

import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.Poi;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MyLocationData;

import java.util.List;

/**
 * 位置监听
 */
public class MyLocationListener implements BDLocationListener {

    LocationClient mLocationClient;
    BaiduMap mBaiduMap;

    public MyLocationListener(LocationClient mLocationClient, BaiduMap mBaiduMap) {
        this.mLocationClient=mLocationClient;
        this.mBaiduMap=mBaiduMap;
    }


    /**
     * 接收的位置信息
     * @param location
     */
    @Override
    public void onReceiveLocation(BDLocation location) {

        if (location!=null){
            double lat=location.getLatitude();  //纬度
            double log=location.getLongitude(); //经度

            MyLocationData locationData=new MyLocationData.Builder()
                    .latitude(lat)
                    .longitude(log)
                    .accuracy(100f)
                    .build();
            mBaiduMap.setMyLocationData(locationData);
        }

        //Receive Location
        StringBuffer sb = new StringBuffer(256);
        sb.append("time : ");
        sb.append(location.getTime()); //时间
        sb.append("\nerror code : ");
        sb.append(location.getLocType()); //loc 类型
        sb.append("\nlatitude : ");
        sb.append(location.getLatitude()); //纬度
        sb.append("\nlontitude : ");
        sb.append(location.getLongitude()); //经度
        sb.append("\nradius : ");
        sb.append(location.getRadius()); //半径 范围
        if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
            sb.append("\nspeed : ");
            sb.append(location.getSpeed());// 速度 单位：公里每小时
            sb.append("\nsatellite : ");
            sb.append(location.getSatelliteNumber()); //卫星数量
            sb.append("\nheight : ");
            sb.append(location.getAltitude());// 高度  单位：米
            sb.append("\ndirection : ");
            sb.append(location.getDirection());// 方向  单位度
            sb.append("\naddr : ");
            sb.append(location.getAddrStr());
            sb.append("\ndescribe : ");
            sb.append("gps定位成功");

        } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
            sb.append("\naddr : ");
            sb.append(location.getAddrStr());
            //运营商信息
            sb.append("\noperationers : ");
            sb.append(location.getOperators());//运营商信息
            sb.append("\ndescribe : ");
            sb.append("网络定位成功");
        } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
            sb.append("\ndescribe : ");
            sb.append("离线定位成功，离线定位结果也是有效的");
        } else if (location.getLocType() == BDLocation.TypeServerError) { //服务端网络定位失败
            sb.append("\ndescribe : ");
            sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
        } else if (location.getLocType() == BDLocation.TypeNetWorkException) { //类型网络异常
            sb.append("\ndescribe : ");
            sb.append("网络不同导致定位失败，请检查网络是否通畅");
        } else if (location.getLocType() == BDLocation.TypeCriteriaException) { //标准异常
            sb.append("\ndescribe : ");
            sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
        }
        sb.append("\nlocationdescribe : ");
        sb.append(location.getLocationDescribe());// 位置语义化信息
        List<Poi> list = location.getPoiList();// POI数据
        if (list != null) {
            sb.append("\npoilist size = : ");
            sb.append(list.size());
            for (Poi p : list) {
                sb.append("\npoi= : ");
                sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
            }
        }
        Log.i("BaiduLocationApiDem", sb.toString());
    }
}