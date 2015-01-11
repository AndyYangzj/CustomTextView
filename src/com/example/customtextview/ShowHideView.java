package com.example.customtextview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * ���KeyWord����ʾKeyWord�������Ϣ��View
 * @author DuGuang
 *
 */
public class ShowHideView extends LinearLayout{

	private Context mContext;
	private TextView mTvShowHide;
	
	@SuppressLint("NewApi")
	public ShowHideView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.mContext = context;
		initView();
	}

	public ShowHideView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		initView();
	}

	public ShowHideView(Context context) {
		super(context);
		this.mContext = context;
		initView();
	}

	public void setPosition(String hide){
		mTvShowHide.setText(hide);
		setVisibility(View.VISIBLE);
	}
	
	/**
	 * ����Զ����XML����
	 */
	public void initView() {
		View view = View.inflate(mContext, R.layout.item_popwin_start_hide,ShowHideView.this);
		mTvShowHide = (TextView) view.findViewById(R.id.tv_paly_hide);
	}
	
}
