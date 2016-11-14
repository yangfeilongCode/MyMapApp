package edu.feicui.mymapapp.activity;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import edu.feicui.mymapapp.R;

/**
 * Created by Administrator on 2016/11/8.
 */
public class UserHomeActivity extends MyBaseActivity implements View.OnClickListener {
    private ImageView mIvUserIcon;//用户头像
    private RelativeLayout mRLtop; //上部分布局
    private PopupWindow popupWindow; //弹框
//    private LayoutInflater mInflater;//布局填充器
    private View mView; //接收的popWindow（用于展示弹框）
    private Button mBtnCamera; //相机拍照按钮
    private Button mBtnPhoto; //相册按钮
    private Button mBtnAbolish; //弹框取消按钮

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
    }

    @Override
    void initView() {
        //改变头条目
        mIvBaseLift.setImageResource(R.mipmap.abc_ic_ab_back_mtrl_am_alpha); //左面返回图片
        mTvBaseTitle.setText("我的帐户"); //中间文字
        mIvBaseRight.setVisibility(View.GONE); //右边图片

        mView=getLayoutInflater().inflate(R.layout.user_home_pop,null);//布局填充填充
        //初始化弹框  设置宽高
        popupWindow=new PopupWindow(mView, ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        mRLtop= (RelativeLayout) findViewById(R.id.rl_user_home_top);
        mIvUserIcon= (ImageView) findViewById(R.id.iv_home_user_imag);
        mBtnCamera= (Button) mView.findViewById(R.id.btn_pop_camera);
        mBtnPhoto= (Button) mView.findViewById(R.id.btn_pop_photo);
        mBtnAbolish= (Button) mView.findViewById(R.id.btn_pop_abolish);
        mBtnCamera.setOnClickListener(this);
        mBtnPhoto.setOnClickListener(this);
        mBtnAbolish.setOnClickListener(this);
        mIvUserIcon.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_home_user_imag://用户头像
                popupWindow.setOutsideTouchable(true); //外部可点击
                popupWindow.setBackgroundDrawable(new BitmapDrawable()); //外部点击弹框隐藏
                popupWindow.showAtLocation(mRLtop, Gravity.BOTTOM,0,0); //展示位置
              //  popupWindow.showAsDropDown();

                break;
            case R.id.btn_pop_camera: //相机拍照
                //跳转相机
                Toast.makeText(this,"相机",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_pop_photo: //相册
                Toast.makeText(this,"相册",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_pop_abolish: //取消

                break;
        }
    }
}
