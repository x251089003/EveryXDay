package com.xinxin.everyxday.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.xinxin.everyxday.R;
import com.xinxin.everyxday.widget.AlignTextView;
import com.xinxin.everyxday.widget.CBAlignTextView;
import com.xinxin.everyxday.widget.swipeback.SwipeBackSherlockActivity;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by xinxin on 15/7/31.
 */
public class QuestionActivity extends SwipeBackSherlockActivity implements View.OnClickListener {

    private Toolbar mToolbar;

    private ExpandableListView mExpandableListView;
    private MyExpandableListAdapter myExpandableListAdapter;

    private LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha));
        mToolbar.setTitle("常见问题");// 标题的文字需在setSupportActionBar之前，不然会无效
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuestionActivity.this.finish();
            }
        });
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {

                }
                return true;
            }
        });

        inflater = LayoutInflater.from(this);
        mExpandableListView = (ExpandableListView)findViewById(R.id.expandableListView_problem);
        myExpandableListAdapter = new MyExpandableListAdapter();
        mExpandableListView.setAdapter(myExpandableListAdapter);
        for(int i = 0; i < myExpandableListAdapter.getGroupCount(); i++){

            mExpandableListView.expandGroup(i);

        }
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                this.finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    class MyExpandableListAdapter extends BaseExpandableListAdapter {

        public String[] groups = {
                "NEW是干什么的？",
                "NEW靠什么赚钱?",
                "怎样才能买到NEW中介绍的东东？？",
                "NEW的内容为什么更新很慢？",
                "为什么我分享的新品没有被NEW采纳？",
                "支持NEW后为什么没收到礼物？",
                "NEW送出的礼物值多少钱？",
                "为什么NEW的逼格如此之高？"
        };

        public String[][] children = {
                { "NEW是一个非盈利性的开源项目，意在为大家推荐生活中高品质的、美的东西，每天至少为大家介绍一款设计佳品，同时也欢迎大家投稿分享新品，我们会认真且细致地筛选每一份稿件。" },
                { "NEW的目的不是为了赚钱且目前也不赚钱，仅仅只是为大家提供一个高品质的内容分享平台，让大家知道每一份佳品背后的设计故事和细节，如果您希望NEW变得更好，那么请在支持我们栏目中支持我们。" },
                { "目前这一版本暂时不支持佳品的购买，因为在最初设计这款产品的时候，我们的目的不是售卖我们所介绍的产品，仅仅希望大家在这个信息爆炸的时代，每天在碎片时间关注NEW中的一点点细节，在后续的版本中我会根据大家的意愿选择是否添加购买功能。" },
                { "NEW中内容更新的速度取决于大家分享新品的数量和质量，以及作者的个人时间问题，如果您希望加入我们，为我们美好的生活出一份力，那么请添加QQ：251089003与我们联系。" },
                { "原因在于NEW对内容的筛选和采纳是极其苛刻严格的，我们希望呈现在大家眼前的东西是最好的、最有意义的、最有设计感的。" },
                { "原因可能为：1.您提供给我们的收货地址或联系方式无效；2.货物在运输途中丢失；3.由于快递方或不确定因素造成的货物运输问题。如出现以上问题，请添加QQ：251089003与我们联系。" },
                { "我想说NEW送出的礼物是无价的，她怀揣着我们满满的爱意。" },
                { "因为我们热爱生活，热爱设计，热爱编码。史蒂夫乔布斯曾经说过：只要想到你终将死去，就会觉得自己没什么好失去的。你没有理由不去准从自己的内心。" }
        };

        @Override
        public int getGroupCount() {
            return groups.length;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return children[groupPosition].length;
        }

        @Override
        public String getGroup(int groupPosition) {
            return groups[groupPosition];
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return children[groupPosition][childPosition];
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,View convertView, ViewGroup parent) {
            GroupHolder groupHolder = null;
            if (convertView == null) {
                groupHolder = new GroupHolder();
                convertView = inflater.inflate(R.layout.grouptitle, null);
                groupHolder.father_title_tv = (TextView) convertView.findViewById(R.id.textView_group_title);
//                groupHolder.arrow = (ImageView) convertView.findViewById(R.id.imageView_arrow);
                convertView.setTag(groupHolder);
            } else {
                groupHolder = (GroupHolder) convertView.getTag();
            }
            groupHolder.father_title_tv.setText(groups[groupPosition]);
//            if(isExpanded){
//                groupHolder.arrow.setBackgroundResource(R.drawable.btn_arrow);
//            }else{
//                groupHolder.arrow.setBackgroundResource(R.drawable.right_arrow);
//            }
            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition,boolean isLastChild, View convertView, ViewGroup parent) {
            ChildHolder childHolder = null;
            if (convertView == null) {
                childHolder = new ChildHolder();
                convertView = inflater.inflate(R.layout.childrencontent, null);
                childHolder.son_title_tv = (CBAlignTextView) convertView.findViewById(R.id.textView_problem_content);
                convertView.setTag(childHolder);
            } else {
                childHolder = (ChildHolder) convertView.getTag();
            }
            childHolder.son_title_tv.setText("\u3000\u3000"+children[groupPosition][childPosition]);
            return convertView;
        }

        class GroupHolder {
            TextView father_title_tv;
//            ImageView arrow;
        }

        class ChildHolder {
            CBAlignTextView son_title_tv;
        }

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
