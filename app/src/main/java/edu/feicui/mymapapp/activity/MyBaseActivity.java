package edu.feicui.mymapapp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import edu.feicui.mymapapp.R;

/**
 * Created by Administrator on 2016/11/7.
 */
public abstract class MyBaseActivity extends AppCompatActivity {
    public  @BindView(R.id.iv_base_lift)
            ImageView mIvBaseLift; //头条目左侧图标
    public @BindView(R.id.tv_base_title)
            TextView mTvBaseTitle; //头条目中心文字
    public @BindView(R.id.iv_base_right)
            ImageView mIvBaseRight;  //头条目右侧图标
    public @BindView(R.id.rl_base_content)
             RelativeLayout mRlBaseContent;  //子布局的内容容器
    private   Unbinder unbinder; //解绑接收
    private LayoutInflater mInflater; //布局填充器
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_base);
        unbinder=ButterKnife.bind(this);
    }

    public void setContentView(int id){
        mInflater=this.getLayoutInflater();
        mInflater.inflate(id,mRlBaseContent);
        initView();
    }

    abstract void initView();


    @Override
    protected void onStop() {
        super.onStop();
        unbinder.unbind(); //解除绑定

    }
}
