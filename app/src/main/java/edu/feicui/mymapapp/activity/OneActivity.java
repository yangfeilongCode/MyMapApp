package edu.feicui.mymapapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import edu.feicui.mymapapp.R;

/**
 * 首次进入页面
 * Created by Administrator on 2016/11/8.
 */
public class OneActivity extends MyBaseActivity implements View.OnClickListener {

    private Button mBtnRegister; //注册按钮
    private Button mBtnLogin;//登录按钮
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);
    }

    @Override
    void initView() {
        mBtnRegister= (Button) findViewById(R.id.btn_register);
        mBtnLogin= (Button) findViewById(R.id.btn_login);
        mBtnRegister.setOnClickListener(this);
        mBtnLogin.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent();
        switch (v.getId()){
            case R.id.btn_register: //注册
                intent.setClass(this,RegisterActivity.class);
                break;
            case R.id.btn_login://登录
                intent.setClass(this,LoginActivity.class);
                break;
        }
        startActivity(intent);
    }


}
