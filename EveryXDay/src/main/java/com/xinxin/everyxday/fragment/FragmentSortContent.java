package com.xinxin.everyxday.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.xinxin.everyxday.R;
import com.xinxin.everyxday.base.imgloader.ImgLoadUtil;
import com.xinxin.everyxday.bean.ShowOrderFeaturedBean;
import com.xinxin.everyxday.bean.SortBean;
import com.xinxin.everyxday.global.InterfaceUrlDefine;
import com.xinxin.everyxday.util.TimeUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xinxin on 15/7/18.
 */
public class FragmentSortContent extends Fragment {

    private ArrayList<SortBean> voList = new ArrayList<SortBean>();

    private View sortView;
    private GridView mGridView;
    private SortAdapter mSortAdapter;
    List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();

    // 图片封装为一个数组
    private int[] icon = { R.mipmap.chuanyi, R.mipmap.chihuo,
            R.mipmap.jiaju, R.mipmap.lvxing, R.mipmap.baobao,
            R.mipmap.meizhuang, R.mipmap.muying, R.mipmap.shouzhang,
            R.mipmap.sheying, R.mipmap.wanju, R.mipmap.shouji, R.mipmap.kechuandai,
            R.mipmap.diannao, R.mipmap.wurenji, R.mipmap.wuyinliangpin,
            R.mipmap.zhongguozhizao
    };
    private String[] iconName = { "#穿衣", "#吃货", "#家居", "#旅行", "#包包", "#美妆", "#母婴",
            "#手帐" , "#摄影", "#玩具", "#手机", "#可穿戴", "#电脑", "#无人机", "#无印良品", "#中国制造"};

    public static FragmentSortContent newInstance(Bundle args) {
        FragmentSortContent myFragment = new FragmentSortContent();
        myFragment.setArguments(args);
        return myFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getData();
        sortView = inflater.inflate(R.layout.sort, null);
        mGridView = (GridView)sortView.findViewById(R.id.gview);
        initViews();
        return sortView;
    }

    private void getData() {
        // 将上述资源转化为list集合
        for (int i = 0; i < iconName.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", icon[i]);
            map.put("title", iconName[i]);

            dataList.add(map);
        }
    }

    private void initViews() {
        if(sortView != null){
            initActionBar();
            initGridViewAdapter();
        }
    }

    private void initGridViewAdapter() {
        if(mSortAdapter == null){
            mSortAdapter = new SortAdapter(this.getActivity());
            mGridView.setAdapter(mSortAdapter);
        }
    }

    private void initActionBar() {

    }

    class SortAdapter extends BaseAdapter{
        private LayoutInflater mInflater;//得到一个LayoutInfalter对象用来导入布局

        public SortAdapter(Context context) {
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return dataList.size();
        }

        @Override
        public Map getItem(int position) {
            return dataList.get(position);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.sort_item,null);
                holder = new ViewHolder();
                /**得到各个控件的对象*/
                holder.sortName = (TextView) convertView.findViewById(R.id.sort_left_name);
                holder.sortImageView = (ImageView) convertView.findViewById(R.id.sort_left);
                convertView.setTag(holder);//绑定ViewHolder对象
            }else{
                holder = (ViewHolder)convertView.getTag();//取出ViewHolder对象
            }

            holder.sortName.setText(dataList.get(position).get("title").toString());
            holder.sortImageView.setImageResource((Integer) dataList.get(position).get("image"));

            return convertView;
        }
    }

    /**存放控件*/
    public final class ViewHolder{
        public ImageView sortImageView;
        public TextView sortName;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


//    @Override
//    public void initCovertView(View convertView, final int position) {
//
//        final SortBean vo = voList.get(position);
//
//        TextView userName = (TextView)convertView.findViewById(R.id.showorder_list_user_name);
//        userName.setText(vo.getTitle().replace("今日最佳：", ""));
//
//        ImageView orderImg = (ImageView)convertView.findViewById(R.id.showorder_list_img);
//        ImgLoadUtil.displayImageWithAnimationAndNoCorner(vo.getCover(), orderImg);
//
////		ImageView aboveImg = (ImageView)convertView.findViewById(R.id.showorder_list_img_above);
//        convertView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (!isActivityAttached()) {
//                    return;
//                }
//
////				Intent intent = new Intent();
////				intent.setClass(getAttachActivity(), ShowOrderFeaturedDetailContentActivity.class);
////				intent.putExtra(CommonWebViewActivity.KILL_HELP_ACTIVITY_VIEW_TITLE, "晒单精选");
////				intent.putExtra(CommonWebViewActivity.KILL_HELP_ACTIVITY_LOAD_URL, vo.getDetail());
////
////				intent.putExtra(ShowOrderFeaturedDetailContentActivity.SHOR_ORDER_FEATURED_CONTENT_ACTIVITY_ID, vo.getId());
////				intent.putExtra(ShowOrderFeaturedDetailContentActivity.SHOR_ORDER_FEATURED_CONTENT_ACTIVITY_URL, vo.getBuyurl());
////
////				startActivity(intent);
//            }
//        });
//    }


}
