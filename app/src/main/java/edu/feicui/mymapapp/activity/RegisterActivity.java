package edu.feicui.mymapapp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.feicui.mymapapp.R;

/**
 * 注册界面
 * Created by Administrator on 2016/11/7.
 */
public class RegisterActivity extends MyBaseActivity implements View.OnClickListener {
    private EditText mETUserName; //用户名
    private EditText mETPassword;//用户密码
    private EditText mETAffirmPassword;//确认密码
    private Button mBtnLogin; //注册按钮
    private String mUserName;//接收的户名
    private String mPassword; //接受的密码
    private String mAffirmPassword; //接受的确认密码
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    @Override
    void initView() {
        //改变头条目
        mIvBaseLift.setImageResource(R.mipmap.abc_ic_ab_back_mtrl_am_alpha); //左面返回图片
        mIvBaseLift.setOnClickListener(this);
        mTvBaseTitle.setText("注册"); //中间文字
        mIvBaseRight.setVisibility(View.GONE); //右边图片

        mETUserName= (EditText) findViewById(R.id.et_register_name);
        mETPassword= (EditText) findViewById(R.id.et_register_password);
        mETAffirmPassword= (EditText) findViewById(R.id.et_register_affirm_password);
        mBtnLogin= (Button) findViewById(R.id.btn_pass_register);
        mBtnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_pass_register: //注册按钮
                mUserName=mETPassword.getText().toString();
                mPassword=mETUserName.getText().toString();
                mAffirmPassword=mETAffirmPassword.getText().toString();
                if (mUserName.equals("")||mPassword.equals("")||mAffirmPassword.equals("")){
                    Toast.makeText(this,"用户名、密码或确认密码不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }else if (!(mETAffirmPassword.equals(mAffirmPassword))){
                    Toast.makeText(this,"确认密码与设定的密码不相同，请确认后再输入",Toast.LENGTH_SHORT).show();
                    return;

                }else if((mUserName.length()>3&&mUserName.length()<10)&&(mPassword.length()>5&&mPassword.length()<16)
                        &&mETAffirmPassword.equals(mAffirmPassword)){
                    //在此调接口
                }
                break;
            case R.id.iv_base_lift: //返回
                finish();
                break;
        }
    }
}

