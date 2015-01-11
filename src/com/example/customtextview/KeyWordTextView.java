package com.example.customtextview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.text.Layout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * һ��TextView�а���һ�����Ե����KeyWord(�ؼ���),��ͨ������ؼ��ʣ��ڶ�Ӧ�ؼ���λ�����Ϸ�չʾ�ؼ��ʶ�Ӧ�Ľ��͵��߼�
 * 
 * @author DuGuang
 * @date 2015.1.11
 * 
 */
public class KeyWordTextView extends TextView {

	protected static final String TAG = KeyWordTextView.class.getSimpleName();
	private Context mContext;
	private String mHide; // �ؼ��ʵ���ʾ����
	private ShowHideView mShowHideView;	//show�ؼ��ʽ���
	private DotView mDotView;	//��һ��Բ�㣬Ч��mShowHideViewչʾ��λ���Ƿ���ȷ

	private String mStartStr, mKeyWord, mEndStr;

	int mYStartTop;// �ؼ��ʵ�һ���ַ�����y����
	int mYStartBottom;// �ؼ��ʵ�һ���ַ��ײ�y����
	float mXStartLeft;// �ؼ��ʵ�һ���ַ����x����
	float mXStartRight;// �ؼ��ʵ�һ���ַ��ұ�x����

	int mYEndTop;// �ؼ������һ���ַ�����y����
	int mYEndBottom;// �ؼ������һ���ַ��ײ�y����
	float mXEndLeft;// �ؼ������һ���ַ����x����
	float mXEndRight;// �ؼ������һ���ַ��ұ�x����

	private Layout mLayout;
	private int mStartPosition; // �ؼ�����ʼ��λ��
	private int mEndPosition; // �ؼ��ʽ�����λ��
	
	private int mStatusBarHeight; //�ֻ�״̬���߶�
	private int mTitleBarHeight;	//�ֻ��������ĸ߶�
	private SpannableString mSpStr;	//���ڸ�KeyWord��������ɫ���»��ߵ���

	@SuppressLint("NewApi")
	public KeyWordTextView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.mContext = context;
	}

	public KeyWordTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
	}

	public KeyWordTextView(Context context) {
		super(context);
		this.mContext = context;
	}
	
	/**
	 * ����������
	 * 
	 * @param startStr
	 *            
	 * @param keyWord
	 *           
	 * @param endStr
	 *            
	 */
	/**
	 * @param startStr	
	 * @param keyWord	 
	 * @param endStr	
	 * @param hide	
	 * @param mDotView 
	 * @param mTitleBarHeight 
	 * @param mShowHideView 
	 */
	/**
	 * @param startStr	�ؼ���֮ǰ��ʾ�����ݣ����û����null
	 * @param keyWord	�ؼ��ʣ����Ե���Ĵ�
	 * @param endStr	�ؼ���֮����ʾ�����ݣ����û����null
	 * @param hide		չʾ�ؼ��ʵĽ����ֶ�
	 * @param statusBarHeight	״̬���ĸ߶�
	 * @param titleBarHeight	�������ĸ߶�
	 * @param showHideView	չʾ�ؼ��ʵ�View
	 * @param dotView	��һ��Բ�㣬Ч��mShowHideViewչʾ��λ���Ƿ���ȷ
	 */
	public void setAllString(String startStr, String keyWord, String endStr, String hide, int statusBarHeight, int titleBarHeight,
			ShowHideView showHideView, DotView dotView) {
		this.mStartStr = startStr;
		this.mKeyWord = keyWord;
		this.mEndStr = endStr;
		
		this.mHide = hide;
		this.mStatusBarHeight = statusBarHeight;
		this.mShowHideView = showHideView;
		this.mTitleBarHeight = titleBarHeight;
		this.mDotView = dotView;
		
		setKeyWordClick();
		initData();
		getTextLayout();
	}

	/**
	 * ����������
	 */
	private void initData() {
		// ����ؼ��ʵ���ʼλ��
		mStartPosition = StringUtil.isNotEmpty(mStartStr) ? mStartStr.length() + 1 : 1;
		
		//����ؼ��ʽ�������λ��
		mEndPosition = StringUtil.isNotEmpty(mEndStr) ?mStartPosition + mKeyWord.length()+1 : mStartPosition + mKeyWord.length() -1 ;
	
		setText(mStartStr);
		append(mSpStr);
		append(StringUtil.isNotEmpty(mEndStr) ? mEndStr : "");
		setMovementMethod(LinkMovementMethod.getInstance());// �൱��ע��ؼ��ʵĵ���¼�����ʼ��Ӧ����¼���
	}

	/**
	 * ��ȡ��ӦTextView��layout����
	 */
	private void getTextLayout() {
		// view�仯������ViewTreeObserver����
		ViewTreeObserver vto = getViewTreeObserver();
		vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				mLayout = getLayout();
				Rect bound = new Rect();
				int line = mLayout.getLineForOffset(mStartPosition);

				mLayout.getLineBounds(line, bound);

				mYStartTop = bound.top;
				mYStartBottom = bound.bottom;

				mXStartLeft = mLayout.getPrimaryHorizontal(mStartPosition);
				mXStartRight = mLayout.getSecondaryHorizontal(mStartPosition);

			}
		});
	}
	
	/**
	 * ���ùؼ��ʵĵ���¼�
	 */
	private void setKeyWordClick() {
		mSpStr = new SpannableString(mKeyWord);
		mSpStr.setSpan(new ClickableSpan() {
			@Override
			public void updateDrawState(TextPaint ds) {
				super.updateDrawState(ds);
				 ds.setColor(Color.BLUE); //����������ɫ
				ds.setUnderlineText(true); // �����»���
			}

			@Override
			public void onClick(View widget) {

				Log.d("", "onTextClick........�������");
				Log.i(TAG, "mYStartTop >>> " + mYStartTop);
				Log.i(TAG, "mYStartBottom >>> " + mYStartBottom);
				Log.i(TAG, "mXStartLeft >>> " + mXStartLeft);
				Log.i(TAG, "mXStartRight >>> " + mXStartRight);
				
				int[] location1 = new int[2];
//				getLocationInWindow(location1);
				getLocationOnScreen(location1);
				Log.i(TAG, "location1[0] >>> " + location1[0]);
				Log.i(TAG, "location1[1] >>> " + location1[1]);
				
				getKeyWordEndInfo();
//				RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//				params.leftMargin = 100;
//				params.topMargin = 100;
//				mdot.setLayoutParams(params);
				
				RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
				params2.leftMargin = (int)(mXStartLeft+(mXEndRight - mXStartLeft)/2)- mShowHideView.getWidth()/2;
				params2.topMargin = (int)location1[1] - mStatusBarHeight - mTitleBarHeight + mYStartTop - mShowHideView.getHeight();
				mShowHideView.setLayoutParams(params2);
				mShowHideView.setPosition(mHide);
				
				mDotView.setPosition((int)(mXStartLeft+(mXEndRight - mXStartLeft)/2), (int)location1[1] - mStatusBarHeight - mTitleBarHeight + mYStartTop);
				
			}
		}, 0, mKeyWord.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		
	}
	
	/**
	 * ��ȡ�ؼ������һ���ַ���X,Y�������Ϣ
	 */
	public void getKeyWordEndInfo(){
		mLayout = getLayout();
		Rect bound = new Rect();
		int line = mLayout.getLineForOffset(mEndPosition);

		mLayout.getLineBounds(line, bound);

		mYEndTop = bound.top;
		mYEndBottom = bound.bottom;

		mXEndLeft = mLayout.getPrimaryHorizontal(mEndPosition);
		mXEndRight = mLayout.getSecondaryHorizontal(mEndPosition);
		
		Log.i(TAG, "mYEndTop >>> " + mYEndTop);
		Log.i(TAG, "mYEndBottom >>> " + mYEndBottom);
		Log.i(TAG, "mXEndLeft >>> " + mXEndLeft);
		Log.i(TAG, "mXEndRight >>> " + mXEndRight);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

	}

}
