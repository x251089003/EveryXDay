package com.xinxin.everyxday.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.andexert.library.RippleView;
import com.xinxin.everyxday.R;
import com.xinxin.everyxday.util.ResultInterface;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GlobalMenuAdapter extends ArrayAdapter<GlobalMenuAdapter.GlobalMenuItem> {
    private static final int TYPE_MENU_ITEM = 0;
    private static final int TYPE_DIVIDER = 1;

    private final LayoutInflater inflater;
    private final List<GlobalMenuItem> menuItems = new ArrayList<GlobalMenuItem>();
    private ResultInterface listener;

    public GlobalMenuAdapter(Context context,ResultInterface listener) {
        super(context, 0);
        this.inflater = LayoutInflater.from(context);
        this.listener = listener;
        setupMenuItems();
    }

    private void setupMenuItems() {
        menuItems.add(new GlobalMenuItem(R.mipmap.home, "NEW"));
        menuItems.add(new GlobalMenuItem(R.mipmap.sort, "分类"));
        menuItems.add(new GlobalMenuItem(R.mipmap.like, "喜欢过的"));
        menuItems.add(GlobalMenuItem.dividerMenuItem());
        menuItems.add(new GlobalMenuItem(R.mipmap.send, "分享新品"));
        menuItems.add(new GlobalMenuItem(R.mipmap.give, "支持NEW"));
        menuItems.add(GlobalMenuItem.dividerMenuItem());
        menuItems.add(new GlobalMenuItem(R.mipmap.setting, "设置"));
        menuItems.add(new GlobalMenuItem(R.mipmap.about, "关于"));
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return menuItems.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public GlobalMenuItem getItem(int position) {
        return menuItems.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        return menuItems.get(position).isDivider ? TYPE_DIVIDER : TYPE_MENU_ITEM;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (getItemViewType(position) == TYPE_MENU_ITEM) {
            MenuItemViewHolder holder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_global_menu, parent, false);
                holder = new MenuItemViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (MenuItemViewHolder) convertView.getTag();
            }

            GlobalMenuItem item = getItem(position);
            holder.tvLabel.setText(item.label);
            holder.ivIcon.setImageResource(item.iconResId);
            holder.ivIcon.setVisibility(item.iconResId == 0 ? View.GONE : View.VISIBLE);

            holder.mRippleView.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {

                @Override
                public void onComplete(RippleView rippleView) {
                    System.out.println("完成！！！！！！！！！！！！！！！");
                    listener.OnComplete(1, position);
                }

            });

            return convertView;
        } else {
            return inflater.inflate(R.layout.item_menu_divider, parent, false);
        }
    }

    @Override
    public boolean isEnabled(int position) {
        return !menuItems.get(position).isDivider;
    }

    public static class MenuItemViewHolder {
        @Bind(R.id.ivIcon)
        ImageView ivIcon;
        @Bind(R.id.tvLabel)
        TextView tvLabel;
        @Bind(R.id.myripple)
        RippleView mRippleView;

        public MenuItemViewHolder(View view) {
            ButterKnife.bind(this,view);
        }
    }

    public static class GlobalMenuItem {
        public int iconResId;
        public String label;
        public boolean isDivider;

        private GlobalMenuItem() {

        }

        public GlobalMenuItem(int iconResId, String label) {
            this.iconResId = iconResId;
            this.label = label;
            this.isDivider = false;
        }

        public static GlobalMenuItem dividerMenuItem() {
            GlobalMenuItem globalMenuItem = new GlobalMenuItem();
            globalMenuItem.isDivider = true;
            return globalMenuItem;
        }
    }
}
