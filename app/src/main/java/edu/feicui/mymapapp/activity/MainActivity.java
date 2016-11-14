package edu.feicui.mymapapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

import butterknife.Unbinder;
import edu.feicui.mymapapp.R;

/**
 * 我的地图应用
 */
public class MainActivity extends MyBaseActivity implements View.OnClickListener {
//    @BindView(R.id.bmapView)  MapView mMapView;  //地图视图
    private MapView mMapView;  //地图视图
    private Unbinder unbinder;  //解绑
    private BaiduMap mBaiduMap; //百度地图
    private LinearLayout mLLlocation; //定位
    private LinearLayout mLLsatellite; //卫星图
    private LinearLayout mLLcompass;  //指南
    private LocationClientOption option;
    private LatLng mMyLocation; //坐标
    private DrawerLayout mDrawerLt;//抽屉布局

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());  //进行初始化
        setContentView(R.layout.activity_home);

//       unbinder=ButterKnife.bind(this);  //绑定控件

    }


//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
////        super.onCreate(savedInstanceState);
//
//        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
//        //注意该方法要再setContentView方法之前实现
//        SDKInitializer.initialize(getApplicationContext());  //进行初始化
//
//        setContentView(R.layout.activity_main);
//     //   unbinder=ButterKnife.bind(this);  //绑定控件
//
//        init();
//    }


    /**
     * 1.找到MapView
     * 2。获取地图的控制器
     * 3.卫星视图和普通地图的切换
     */
    @Override
    void initView() {
        mIvBaseLift.setOnClickListener(this);
        mTvBaseTitle.setText("寻宝地图");
        mIvBaseRight.setOnClickListener(this);
        mDrawerLt= (DrawerLayout) findViewById(R.id.dl_home);

//        Log.e("aaaaa", "initView: -----"+mMapView.getMap() );
        mMapView= (MapView) findViewById(R.id.bmapView);//找到地图
        mBaiduMap = mMapView.getMap(); //获取地图

        mLLlocation = (LinearLayout) findViewById(R.id.ll_map_location);  //定位
        mLLsatellite = (LinearLayout) findViewById(R.id.ll_map_satellite); //卫星图
        mLLcompass = (LinearLayout) findViewById(R.id.ll_map_compass);  //指南
        mLLlocation.setOnClickListener(this); //定位
        mLLsatellite.setOnClickListener(this); //卫星图
        mLLcompass.setOnClickListener(this);  //路线


//-----------------------地图状态-------------------------------------------------------------------------------
        //地图的状态
        MapStatus mapStatus = new MapStatus.Builder()
                .overlook(0)//不显示缩放控件  -45---0
                .zoom(15)//缩放级别   3-21
                .build();//构建

        BaiduMapOptions options = new BaiduMapOptions()
                .zoomControlsEnabled(false)// 不显示缩放的控件
                .zoomGesturesEnabled(true)// 是否允许缩放的手势
                // 具体查看API
                .mapStatus(mapStatus);

        // 目前来说，设置只能通过MapView的构造方法来添加,所以Demo里面是在布局中添加MapView
        // 后面项目实施会动态创建
//        MapView mapView = new MapView(this,options);

        // 为地图设置状态监听
        mBaiduMap.setOnMapStatusChangeListener(mapStatusListener);
        // 为地图设置标记监听
        mBaiduMap.setOnMarkerClickListener(MarkerListener);

    }


       //  地图状态的监听
         BaiduMap.OnMapStatusChangeListener mapStatusListener = new BaiduMap.OnMapStatusChangeListener() {
            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus) {

            }

            @Override
            public void onMapStatusChange(MapStatus mapStatus) {

            }

            @Override
            public void onMapStatusChangeFinish(MapStatus mapStatus) {

    //            Toast.makeText(MainActivity.this, "状态变化：纬度：" + mapStatus.target.latitude + "经度：" + mapStatus.target.longitude, Toast.LENGTH_SHORT).show();

            }
        };

//----------------------------------------------------------------------------------------------------------------



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_map_location: //定位
                  location(); //调位置方法

                Toast.makeText(this,"定位",Toast.LENGTH_SHORT).show();
                break;

            case R.id.ll_map_satellite: //地图切换
                if(mBaiduMap.getMapType()==BaiduMap.MAP_TYPE_SATELLITE) {
                    mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL); //普通地图
                }else {//卫星地图
                    mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                }

                break;
            case R.id.ll_map_compass://指南
                //获取用户界面设置激活定位
                boolean isCompass=mBaiduMap.getUiSettings().isCompassEnabled();
                mBaiduMap.getUiSettings().setCompassEnabled(!isCompass);

                Toast.makeText(this,"指南调正！",Toast.LENGTH_SHORT).show();
                break;

            case R.id.iv_base_lift://（用户信息）左边抽屉
                mDrawerLt.openDrawer(Gravity.LEFT);
                Toast.makeText(this,"用户信息。。。。。",Toast.LENGTH_SHORT).show();
                break;

            case R.id.iv_base_right://宝物展示列表
              //  startActivity(new Intent(this,));
                break;
        }

    }


    /**
     * 位置定位
     */
    public void location() {
        /**
         *  1.开启定位图层操作
         2.初始化LocationClitent
         3.配置一些定位相关的参数LocationClitentOption
         4.设置监听，定位监听
         5.开启定位
         注意：不要使用模拟器，真机去测 ， 6.0其上需要运行时添加权限
         定位不准确：  默认是 gcj92 方式，位置有偏差 ，定位的时候设置
         */
        mBaiduMap.setMyLocationEnabled(true); //开启定位图层
        //初始化定位
        final LocationClient mLocationClient=new LocationClient(getApplicationContext());
        //初始化监听事件
  //        MyLocationListener locationListener=new MyLocationListener(mLocationClient,mBaiduMap);
        BDLocationListener locationListener=new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) { //重写方法
                /**
                 * 在定位监听里面，可以根据我们的结果来处理，显示定位的数据。。。。
                 */
                if (bdLocation==null){ //位置为null  重新请求定位
                    // 没有定位信息，重新定位，重新请求定位信息
                    mLocationClient.requestLocation();// 请求定位
                    return;
                }

                double lng = bdLocation.getLongitude();// 获取经度
                double lat = bdLocation.getLatitude();// 获取纬度

                /**
                 * 定位到之后弹吐丝改变成添加定位的标志，移动到我们的位置
                 */
            Toast.makeText(MainActivity.this, "经度："+lng+"纬度："+lat, Toast.LENGTH_SHORT).show();

                /**
                 * 添加定位的标志
                 *    1. 拿到定位的信息
                 *    2. 给地图设置上定位信息
                 */
                MyLocationData myLocationData = new MyLocationData.Builder()
                        .latitude(lat)// 纬度
                        .longitude(lng)// 经度
                        .accuracy(100f)// 定位的精度的大小
                        .build(); //构造
                mBaiduMap.setMyLocationData(myLocationData); //设置定位数据

                /**
                 * 移动到我们的位置
                 *     1. 有我们定位的位置
                 *     2. 移动的话，地图状态是不是发生变化了呢？要移动到定位的位置上去
                 *     3. 地图状态的位置设置我们的位置
                 *     4. 地图的状态变化了？我们需要对地图的状态进行更新
                 */
                mMyLocation = new LatLng(lat,lng); // 我们所定位的位置
                moveToMyLocation(); //移动方法  移动到我们的位置
                addMarker(new LatLng(lat+0.1,lng+0.1)); //添加标记的位置
                }
        };

        //设置监听
        mLocationClient.registerLocationListener(locationListener); //注册监听函数（注册位置监听）
        //配置参数初始化
        option= new LocationClientOption();
        ////可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy); //默认精确定位
        //bd0911 精确无偏差
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
//        //扫描周期
        option.setScanSpan(0);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        //需要地址
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        //打开gps
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        //设置位置通知
        option.setLocationNotify(true);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationDescribe(true);
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIsNeedLocationPoiList(true);
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.setIgnoreKillProcess(false);
        //可选，默认false，设置是否收集CRASH信息，默认收集
        option.SetIgnoreCacheException(false);
        //可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        option.setEnableSimulateGps(false);
        mLocationClient.setLocOption(option);

        mLocationClient.start(); //开启定位
        // 针对于某些机型，开启请求位置会失败
        mLocationClient.requestLocation();

    }

    /**
     * 移动到定位的位置
     */
    private void moveToMyLocation(){
        //初始化地图状态
        MapStatus mapStatus=new MapStatus.Builder()
                .target(mMyLocation)
                .rotate(0) //作用是摆正地图
                .zoom(19) //缩放级别
                .build();
        // 更新地图的状态
        MapStatusUpdate update = MapStatusUpdateFactory.newMapStatus(mapStatus);
        mBaiduMap.animateMapStatus(update); //更新地图
    }



//    Bitmap dot= BitmapFactory.decodeResource(MainActivity.this.getResources(),R.mipmap.ic_launcher);
//    BitmapDescriptor dot_click = BitmapDescriptorFactory.fromResource(R.mipmap.abc_btn_check_to_on_mtrl_015);


   //添加标记
    private void addMarker(LatLng latLng){
        //位置标记
         BitmapDescriptor dot = BitmapDescriptorFactory.fromResource(R.mipmap.mappin_traditional_collapsed);
        /**
         * 在某一位置，添加标志物
         *    1.目的：地图中添加一个标志
         *    2.实现步骤： 主要两个方面
         *      1.确定添加标志物的位置：经纬度
         *      2.确定你要添加的图片
         */
       MarkerOptions options=new MarkerOptions(); //标记选项
        options.position(latLng); //位置
        options.icon(dot); //图片
        mBaiduMap.addOverlay(options); //添加覆盖物

    }

    /**
     * 标记点击事件
     */
    private BaiduMap.OnMarkerClickListener MarkerListener = new BaiduMap.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(Marker marker) {
            //标记覆盖物
            BitmapDescriptor dot_click = BitmapDescriptorFactory.fromResource(R.mipmap.mappin_traditional_expanded);
            /**
             * marker点击的时候会触发这个方法
             * 展示一个信息窗口：文本、图片、。。。。
             *
             * 1. 目的：点击之后，展示出一个InfoWindow
             * 2. 实现：1. 创建一个Infowindow
             *          2. 设置你展示的是什么？
             *          3. 还是在地图上展示出来
             */
            //设置信息弹框
            InfoWindow infoWindow = new InfoWindow(dot_click, marker.getPosition(), 0, new InfoWindow.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick() {

                }
            });
            mBaiduMap.showInfoWindow(infoWindow); //展示窗口
            return false;
        }
    };




//------------生命周期------------------------------------------------------------------------------
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
        unbinder.unbind(); //解绑
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();

    }


}
