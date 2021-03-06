package com.example.jack.myapplication.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jack.myapplication.CommentActivity;
import com.example.jack.myapplication.MainActivity;
import com.example.jack.myapplication.Model.Item;
import com.example.jack.myapplication.Model.TwoTuple;
import com.example.jack.myapplication.R;
import com.example.jack.myapplication.ScanActivity;
import com.example.jack.myapplication.Util.Event.MessageEvent;
import com.example.jack.myapplication.Util.Palette.Palette;
import com.example.jack.myapplication.Util.Util;
import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;

/**
 * 展示物品的页面
 * 本来一开始是单例模式，但考虑它会经常刷新页面，没有必要考虑单例模式
 * 但是要注意资源的释放
 */
public class Fragment_item extends Fragment {

    private final String TAG = "Fragment_item";
    private static final String IMAGE = "IMAGE";
    public static Item item;  //当前展示的Item
    private Context mContext;
    private SimpleDraweeView sd_good;
    private TextView tv_name;
    private ImageView iv_star;
    private TextView tv_company;
    private TextView tv_source;
    private TextView tv_size;

    private ImageButton ib_add;
    private ImageButton ib_comment;
    private ImageButton ib_scan;
    private View mView;


    /**
     * Create a new Fragment_item
     * @param image
     */
    public static Fragment_item newInstance(String image) {
        Bundle args = new Bundle();
        args.putString(IMAGE, image);

        Fragment_item fragment = new Fragment_item();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onAttach(Context context){
        Log.i(TAG,"onAttach");
        super.onAttach(context);
        this.mContext = context;
    }
    @Override
    public void onResume() {

        super.onResume();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        mView = inflater.inflate(R.layout.fragment_item, container, false);

        Log.i(TAG,"onCreateView");
        sd_good = (SimpleDraweeView) mView.findViewById(R.id.sd_good);
        tv_name = (TextView) mView.findViewById(R.id.tv_name);
        iv_star = (ImageView) mView.findViewById(R.id.iv_star);
        tv_company = (TextView) mView.findViewById(R.id.tv_company);
        tv_source = (TextView) mView.findViewById(R.id.tv_source);
        tv_size = (TextView) mView.findViewById(R.id.tv_size);

        //刷新UI

        tv_name.setText(item.getName());
        tv_company.setText(item.getCompany());
        tv_size.setText(item.getSize());
        tv_source.setText(item.getSource());

        //新建一个菜单
        setHasOptionsMenu(true);

        ib_add = (ImageButton) mView.findViewById(R.id.item_add);
        ib_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickAdd();
            }
        });

        ib_comment=(ImageButton)mView.findViewById(R.id.item_comment);
        ib_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickComment();
            }
        });

        ib_scan=(ImageButton)mView.findViewById(R.id.item_scan);
        ib_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickScan();
            }
        });



        return mView;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Log.i(TAG, "onCreateOptionsMenu()");
        menu.clear();
        inflater.inflate(R.menu.menu_item, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public String toString(){
        return TAG;
    }


    public void clickAdd(){
        Fragment_plan.planItems.add(new TwoTuple(false,item));
        EventBus.getDefault().post(new MessageEvent(item.getName() + "已加入计划购买清单"));
    }

    public void clickScan(){
        startActivity(new Intent(getActivity(), ScanActivity.class));
    }

    public void clickComment(){
        startActivity(new Intent(getActivity(), CommentActivity.class));
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle args = getArguments();
        String ig = args.getString(IMAGE);

        sd_good.setImageURI(ig);

    }


}
