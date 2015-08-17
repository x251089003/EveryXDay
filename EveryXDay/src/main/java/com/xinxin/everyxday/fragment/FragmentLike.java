package com.xinxin.everyxday.fragment;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andexert.library.RippleView;
import com.xinxin.everyxday.R;
import com.xinxin.everyxday.activity.ToolbarControlDetailListViewActivity;
import com.xinxin.everyxday.base.imgloader.ImgLoadUtil;
import com.xinxin.everyxday.dao.model.Like;
import com.xinxin.everyxday.dao.util.DbService;
import com.xinxin.everyxday.util.DeviceInfoUtil;
import com.xinxin.everyxday.util.TimeUtil;
import com.xinxin.everyxday.widget.swipelistview.SwipeListView;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by mengxiao on 15/8/13.
 */
public class FragmentLike extends Fragment {

    protected static final String TAG = "FragmentLike";

    protected LayoutInflater inflater;

    private List<Like> likeList;

    private DbService mDbService;

    private View containerView;

    private RelativeLayout parentLayout;

    private LikeAdapter mLikeAdapter;

    private SwipeListView mListView;

    class MyHandler extends Handler {

        private final WeakReference<FragmentLike> mTarget;

        MyHandler(FragmentLike target) {
            mTarget = new WeakReference<FragmentLike>(target);
        }

        @Override
        public void handleMessage(Message msg) {
            FragmentLike target = mTarget.get();
            if (target != null) {
                switch (msg.what){
                    case 0 :
                        initListView();
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                }
            }
        }

    }

    private MyHandler mHandler = new MyHandler(this);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDbService = DbService.getInstance(getActivity());
        inflater = LayoutInflater.from(getActivity());
        new DbThread().start();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        containerView = inflater.inflate(R.layout.fragment_common_list, null);
        parentLayout = (RelativeLayout)containerView.findViewById(R.id.parentLayout);
        return containerView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void initListView(){
        DisplayMetrics dm = new DisplayMetrics();
        dm = getResources().getDisplayMetrics();
        int screenWidth  = dm.widthPixels;
        int distance = screenWidth - DeviceInfoUtil.dip2px(getActivity(),80);
        View view = inflater.inflate(R.layout.common_listview,null);
        mListView = (SwipeListView)view.findViewById(R.id.common_listview);
        mListView.setOffsetLeft(distance);
        if(mLikeAdapter == null){
            mLikeAdapter = new LikeAdapter();
        }
        mListView.setAdapter(mLikeAdapter);

        parentLayout.removeAllViews();
        parentLayout.addView(view);

    }

    class DbThread extends Thread{
        public void run(){
            likeList = mDbService.loadAllLike();
            if(null != likeList){
                if(likeList.size() > 0){
                    Message message = mHandler.obtainMessage();
                    message.what = 0;
                    mHandler.sendMessage(message);
                }else{
                    Message message = mHandler.obtainMessage();
                    message.what = 1;
                    mHandler.sendMessage(message);
                }
            }else{
                Message message = mHandler.obtainMessage();
                message.what = 2;
                mHandler.sendMessage(message);

            }
        }
    }

    class LikeAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return likeList.size();
        }

        @Override
        public Like getItem(int position) {
            return likeList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final Like vo = likeList.get(position);

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.like_item,null);
            }

            TextView userName = (TextView)convertView.findViewById(R.id.showorder_list_user_name);
            userName.setText(vo.getTitle().replace("今日最佳：", ""));

            ImageView orderImg = (ImageView)convertView.findViewById(R.id.showorder_list_img);
            ImgLoadUtil.displayImageWithAnimationAndNoCorner(vo.getCover(), orderImg);

            TextView publishTime = (TextView)convertView.findViewById(R.id.new_time);
            publishTime.setText(TimeUtil.getMonthAndDay(vo.getCreateTime()));

            Button delete = (Button)convertView.findViewById(R.id.id_remove);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDbService.deleteLike(vo.getId());
                    likeList.remove(position);
                    notifyDataSetChanged();
                    mListView.closeOpenedItems();
                }
            });

            RippleView mRippleView = (RippleView)convertView.findViewById(R.id.item_rippleview);
            mRippleView.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
                @Override
                public void onComplete(RippleView rippleView) {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), ToolbarControlDetailListViewActivity.class);
                    intent.putExtra("today_new_title", vo.getTitle().replace("今日最佳：", ""));
                    intent.putExtra("today_new_id", Integer.parseInt(vo.getNewid()));
                    intent.putExtra("today_detail_new_url", vo.getDetailNew());
                    intent.putExtra("today_new_cover",vo.getCover());
                    intent.putExtra("today_new_time",vo.getCreateTime());
                    intent.putExtra("today_new_avatar",vo.getAvatar());

                    startActivity(intent);
                }
            });
            return convertView;
        }
    }
}
