package com.example.customtextview;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;

/**
 * һ��TextView�а���һ�����Ե����KeyWord(�ؼ���),��ͨ������ؼ��ʣ��ڶ�Ӧ�ؼ���λ�����Ϸ�չʾ�ؼ��ʶ�Ӧ�Ľ��͵��߼�
 * @author DuGuang
 *
 */
public class MainActivity extends Activity {

	private KeyWordTextView mTvKeyWord;
	private ShowHideView mShowHideView;
	private DotView mDotView;
	private int mStatusBarHeight; // �ֻ�״̬���߶�
	private int mTitleBarHeight;	//�ֻ��������ĸ߶�

	private RelativeLayout mLlMain;
	private String mHide = "��";
	private String mStartStr;	//�ؼ���ǰ����ֶ�
//	private String mStartStr = null;
	private String mKeyWord = "love"; //�ؼ���
	private String mEndStr ;	//�ؼ��ʺ�����ֶ�
//	private String mEndStr = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		initData();
	}

	/**
	 * ��ʼ��View
	 */
	private void initView() {
		mTvKeyWord = (KeyWordTextView) findViewById(R.id.mTvKeyWord);
		mLlMain = (RelativeLayout) findViewById(R.id.mLlMain);
		
		mShowHideView = new ShowHideView(this);
		mDotView = new DotView(this);
		
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		mShowHideView.setLayoutParams(params);
		mDotView.setLayoutParams(params);
		mShowHideView.setVisibility(View.INVISIBLE);
		
		mLlMain.addView(mShowHideView);
		mLlMain.addView(mDotView);
	}

	/**
	 * �������
	 */
	private void initData() {
		mStartStr = "Whatever is worth doing is worth haha zhen de ke yi me, hao xi fan! Whatever is worth doing is worth haha zhen de ke yi me, hao xi fan! Whatever is worth doing is worth haha zhen de ke yi me, hao xi fan! Whatever is worth doing is worth haha zhen de ke yi me, hao xi fan! Whatever is worth doing is worth haha zhen de ke yi me, hao xi fan! Whatever is worth doing is worth haha zhen de ke yi me, hao xi fan! ";
		mEndStr = " Whatever is worth doing is worth doing well.";
		
		//���￪�����߳���Ϊ��ȡ״̬���ͱ������ĸ߶�
		mTvKeyWord.post(new Runnable() {
			
			@Override
			public void run() {
				getBarHeight();
				mTvKeyWord.setAllString(mStartStr, mKeyWord, mEndStr, mHide,
						mStatusBarHeight, mTitleBarHeight, mShowHideView,  mDotView);
			}
		});
		
	

	}

	/**
	 * ��ȡ�ֻ�״̬���ͱ������ĸ߶�
	 */
	private void getBarHeight() {
		Rect frame = new Rect();
		getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		Window window = getWindow();
		// ״̬���ĸ߶�
		mStatusBarHeight = frame.top;
		// ��������״̬��������߶�
		int contentViewTop = window.findViewById(Window.ID_ANDROID_CONTENT).getTop();
		// �������ĸ߶ȣ��������ֵ��ȥ״̬���ĸ߶ȼ�Ϊ�������߶�
		mTitleBarHeight = contentViewTop - mStatusBarHeight;
		Log.i("dg", mStatusBarHeight + "..." + contentViewTop + "..."
				+ mTitleBarHeight);

	}

}
