package com.txx.androidpaginglibrary.listwrap.loaderrorview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;
import com.nispok.snackbar.listeners.ActionClickListener;
import com.txx.androidpaginglibrary.R;

/**
 * 1.该列表本来无数据 
 * 2.该列表有数据，但是第一页就加载失败
 * 3.该列表有数据，加载下一页数据时，加载失败
 */

public class LoadListDataErrorViewWrap {
	
	public interface LoadListDataErrorViewWrapDelegate{
		
		public void addLoadFirstPageDataFailureView(View errorView);
		
		public void addFirstPageReloadingView(View loadingView);
		
		public void addEmptyView(View emptyView);
		
	}
	
	private LoadListDataErrorViewWrapDelegate loadListDataErrorViewWrapDelegate;
	
	public LoadListDataErrorViewWrap(LoadListDataErrorViewWrapDelegate loadListDataErrorViewWrapDelegate){
		this.loadListDataErrorViewWrapDelegate = loadListDataErrorViewWrapDelegate;
	}
	
	private View netErroView;
	private View loadingView;
	private View emptyView;
	
	public void setLoadFirstPageDataFailureView(Context context, LayoutInflater inflater) {
		
		if(netErroView == null){
			netErroView = inflater.inflate(R.layout.common_paging_load_error_view, null);
		}
		
		if(loadingView == null){
			loadingView = inflater.inflate(R.layout.common_paging_load_view, null);
		}
		
//		RelativeLayout reloadBtn = (RelativeLayout) netErroView.findViewById(R.id.reload_btn);
//		reloadBtn.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				loadListDataErrorViewWrapDelegate.addFirstPageReloadingView(loadingView);
//			}
//		});

		loadListDataErrorViewWrapDelegate.addLoadFirstPageDataFailureView(netErroView);
		SnackbarManager.show(
				Snackbar.with(context) // context
						.text("网络连接失败") // text to display
						.colorResource(R.color.app_black_color_transparent)
						.actionLabel("重新加载") // action button label
						.duration(Snackbar.SnackbarDuration.LENGTH_INDEFINITE)
						.dismissOnActionClicked(true)
						.actionListener(new ActionClickListener() {
							@Override
							public void onActionClicked(Snackbar snackbar) {
								loadListDataErrorViewWrapDelegate.addFirstPageReloadingView(loadingView);
							}
						})
		);// action button's ActionClickListener


//		Toast.makeText(context, context.getResources().getString(R.string.common_paging_network_failure_tip), Toast.LENGTH_SHORT).show();
		
	}

	public void setLoadDataResponseEmptyView(LayoutInflater inflater, String tip) {
		
		if(emptyView == null){
			emptyView = inflater.inflate(R.layout.common_paging_load_empty_view, null);
		}
		
		TextView textview = (TextView) emptyView.findViewById(R.id.empty_tip_text);
		textview.setText(tip);
		
		loadListDataErrorViewWrapDelegate.addEmptyView(emptyView);

	}
	
}

