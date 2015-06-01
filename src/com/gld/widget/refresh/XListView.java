/**
 * @file XListView.java
 * @package me.maxwin.view
 * @create Mar 18, 2012 6:28:41 PM
 * @author Maxwin
 * @description An ListView support (a) Pull down to refresh, (b) Pull up to load more.
 * 		Implement IXListViewListener, and see stopRefresh() / stopLoadMore().
 */
package com.gld.widget.refresh;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.example.logisticsShop.R;



public class XListView extends ListView implements OnScrollListener {

    private int mId = -1;
    /**
     * 鐢ㄤ簬瀛樺偍涓婃鏇存柊鏃堕棿
     */
    private SharedPreferences preferences;
    /**
     * 涓婃鏇存柊鏃堕棿鐨勬绉掑�
     */
    private long lastUpdateTime;
    /**
     * 涓�垎閽熺殑姣鍊硷紝鐢ㄤ簬鍒ゆ柇涓婃鐨勬洿鏂版椂闂�
     */
    public static final long ONE_MINUTE = 60 * 1000;

    /**
     * 涓�皬鏃剁殑姣鍊硷紝鐢ㄤ簬鍒ゆ柇涓婃鐨勬洿鏂版椂闂�
     */
    public static final long ONE_HOUR = 60 * ONE_MINUTE;

    /**
     * 涓�ぉ鐨勬绉掑�锛岀敤浜庡垽鏂笂娆＄殑鏇存柊鏃堕棿
     */
    public static final long ONE_DAY = 24 * ONE_HOUR;

    /**
     * 涓�湀鐨勬绉掑�锛岀敤浜庡垽鏂笂娆＄殑鏇存柊鏃堕棿
     */
    public static final long ONE_MONTH = 30 * ONE_DAY;

    /**
     * 涓�勾鐨勬绉掑�锛岀敤浜庡垽鏂笂娆＄殑鏇存柊鏃堕棿
     */
    public static final long ONE_YEAR = 12 * ONE_MONTH;

    /**
     * 涓婃鏇存柊鏃堕棿鐨勫瓧绗︿覆甯搁噺锛岀敤浜庝綔涓篠haredPreferences鐨勯敭鍊�
     */
    private static final String UPDATED_AT = "updated_at";

    private final String TAG = "XListView";
    private float mLastY = -1; // save event y
    private Scroller mScroller; // used for scroll back
    private OnScrollListener mScrollListener; // user's scroll listener

    // the interface to trigger refresh and load more.
    private IXListViewListener mListViewListener;

    // -- header view
    private XListViewHeader mHeaderView;
    // header view content, use it to calculate the Header's height. And hide it
    // when disable pull refresh.
    private RelativeLayout mHeaderViewContent;
    private TextView mHeaderTimeView;
    private TextView mHeaderTextView;
    private int mHeaderViewHeight; // header view's height
    private boolean mEnablePullRefresh = true;
    private boolean mPullRefreshing = false; // is refreashing.

    // -- footer view
    private XListViewFooter mFooterView;
    private TextView mXlistviewFooterTextview;
    private boolean mEnablePullLoad;
    private boolean mPullLoading;
    private boolean mIsFooterReady = false;

    // total list items, used to detect is at the bottom of listview.
    private int mTotalItemCount;

    // for mScroller, scroll back from header or footer.
    private int mScrollBack;
    private final static int SCROLLBACK_HEADER = 0;
    private final static int SCROLLBACK_FOOTER = 1;

    private final static int SCROLL_DURATION = 400; // scroll back duration
    private final static int PULL_LOAD_MORE_DELTA = 50; // when pull up >= 50px
    // at bottom, trigger
    // load more.
    private final static float OFFSET_RADIO = 1.8f; // support iOS like pull
    // feature.

    private String footerText;

    /**
     * @param context
     */
    public XListView(Context context) {
        super(context);
        initWithContext(context);
    }

    public XListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initWithContext(context);
    }

    public XListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initWithContext(context);
    }

    private void initWithContext(Context context) {
        mScroller = new Scroller(context, new DecelerateInterpolator());
        // XListView need the scroll event, and it will dispatch the event to
        // user's listener (as a proxy).
        super.setOnScrollListener(this);
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        // init header view
        mHeaderView = new XListViewHeader(context);
        mHeaderViewContent = (RelativeLayout) mHeaderView
                .findViewById(R.id.xlistview_header_content);
        mHeaderTimeView = (TextView) mHeaderView
                .findViewById(R.id.xlistview_header_time);
        mHeaderTextView = (TextView) mHeaderView
                .findViewById(R.id.xlistview_header_hint_textview);
        // addHeaderView(mHeaderView);
        addHeaderView(mHeaderView, null, false);

        // init footer view
        mFooterView = new XListViewFooter(context);
        mXlistviewFooterTextview = (TextView) mFooterView
                .findViewById(R.id.xlistview_footer_hint_textview);
        // init header height
        mHeaderView.getViewTreeObserver().addOnGlobalLayoutListener(
                new OnGlobalLayoutListener() {

                    @Override
                    public void onGlobalLayout() {
                        mHeaderViewHeight = mHeaderViewContent.getHeight();
                        getViewTreeObserver()
                                .removeGlobalOnLayoutListener(this);
                    }
                });
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        // make sure XListViewFooter is the last footer view, and only add once.
        if (mIsFooterReady == false) {
            if (mEnablePullLoad) {
                mIsFooterReady = true;
                // addFooterView(mFooterView);
                addFooterView(mFooterView, null, false);
            }
        }
        super.setAdapter(adapter);
    }

    /**
     * enable or disable pull down refresh feature.
     *
     * @param enable
     */
    public void setPullRefreshEnable(boolean enable) {
        mEnablePullRefresh = enable;
        if (!mEnablePullRefresh) { // disable, hide the content
            mHeaderViewContent.setVisibility(View.INVISIBLE);
        } else {
            mHeaderViewContent.setVisibility(View.VISIBLE);
        }
    }

    /**
     * enable or disable pull up load more feature.
     *
     * @param enable
     */
    public void setPullLoadEnable(boolean enable) {
        mEnablePullLoad = enable;
        if (!mEnablePullLoad) {
            mFooterView.hide();
            // removeFooterView(mFooterView);
            // mFooterView.setClickable(false);
        } else {
            mPullLoading = false;
            mFooterView.show();
            mFooterView.setState(XListViewFooter.STATE_NORMAL);
            // both "pull up" and "click" will invoke load more.
            /*
             * mFooterView.setOnClickListener(new OnClickListener() {
			 * 
			 * @Override public void onClick(View v) { startLoadMore(); } });
			 */
        }
    }

    /**
     * stop refresh, reset header view.
     */
    public void stopRefresh() {
        if (mPullRefreshing == true) {
            mPullRefreshing = false;
            resetHeaderHeight();
            preferences.edit()
                    .putLong(UPDATED_AT + mId, System.currentTimeMillis())
                    .commit();
        }
    }

    // 鍙娇杩涘叆Activity鏃朵究鎵ц涓嬫媺鍒锋柊
    public void startRefresh() {
        if (mPullRefreshing == false) {
            mPullRefreshing = true;
            mHeaderView.setState(XListViewHeader.STATE_REFRESHING);
            mHeaderView.setVisiableHeight(90);
            if (mListViewListener != null) {
                mListViewListener.onRefresh();
            }
        }
    }

    /**
     * stop load more, reset footer view.
     */
    public void stopLoadMore() {
        if (mPullLoading == true) {
            mPullLoading = false;
            mFooterView.setState(XListViewFooter.STATE_NORMAL);
        }
    }

    /**
     * 璁剧疆涓婃媺鍔犺浇鏄惁鍙
     *
     * @param enable
     */
    @SuppressLint("ResourceAsColor")
    public void setPullIsEnable(boolean enable) {
        if (enable) {
            if (footerText == null) {
                // mXlistviewFooterTextview.setText("鏌ョ湅鏇村");
                mXlistviewFooterTextview.setText("");
            } else {
                mXlistviewFooterTextview.setText(footerText);
            }
        } else {
            mXlistviewFooterTextview.setText("");
        }
    }

    public void setFooterText(String footerNormal, String footerReady) {
        this.footerText = footerNormal;
        mFooterView.setFooterText(footerNormal, footerReady);
    }

    public void setHeaderText(String headNormal, String headerReady) {
        mHeaderView.setHeaderText(headNormal, headerReady);
    }

    /**
     * set last refresh time
     *
     * @param
     */
    public void setRefreshTime() {
        // mHeaderTimeView.setText(time);
        refreshUpdatedAtValue();
    }


    private void invokeOnScrolling() {
        if (mScrollListener instanceof OnXScrollListener) {
            OnXScrollListener l = (OnXScrollListener) mScrollListener;
            l.onXScrolling(this);
        }
    }

    private void refreshUpdatedAtValue() {
        lastUpdateTime = preferences.getLong(UPDATED_AT + mId, -1);
        long currentTime = System.currentTimeMillis();
        long timePassed = currentTime - lastUpdateTime;
        long timeIntoFormat;
        String updateAtValue;
        if (lastUpdateTime == -1) {
            updateAtValue = getResources().getString(R.string.not_updated_yet);
        } else if (timePassed < 0) {
            updateAtValue = getResources().getString(R.string.time_error);
        } else if (timePassed < ONE_MINUTE) {
            updateAtValue = getResources().getString(R.string.updated_just_now);
        } else if (timePassed < ONE_HOUR) {
            timeIntoFormat = timePassed / ONE_MINUTE;
            String value = timeIntoFormat + "分钟";
            updateAtValue = String.format(getResources().getString(R.string.updated_at), value);
        } else if (timePassed < ONE_DAY) {
            timeIntoFormat = timePassed / ONE_HOUR;
            String value = timeIntoFormat + "小时";
            updateAtValue = String.format(getResources().getString(R.string.updated_at), value);
        } else if (timePassed < ONE_MONTH) {
            timeIntoFormat = timePassed / ONE_DAY;
            String value = timeIntoFormat + "天";
            updateAtValue = String.format(getResources().getString(R.string.updated_at), value);
        } else if (timePassed < ONE_YEAR) {
            timeIntoFormat = timePassed / ONE_MONTH;
            String value = timeIntoFormat + "个月";
            updateAtValue = String.format(getResources().getString(R.string.updated_at), value);
        } else {
            timeIntoFormat = timePassed / ONE_YEAR;
            String value = timeIntoFormat + "年";
            updateAtValue = String.format(getResources().getString(R.string.updated_at), value);
        }
//        mHeaderTimeView.setText(updateAtValue);
    }

    private void updateHeaderHeight(float delta) {
        mHeaderView.setVisiableHeight((int) delta
                + mHeaderView.getVisiableHeight());
        if (mEnablePullRefresh && !mPullRefreshing) { // 鏈浜庡埛鏂扮姸鎬侊紝鏇存柊绠ご
            if (mHeaderView.getVisiableHeight() > mHeaderViewHeight) {
                mHeaderView.setState(XListViewHeader.STATE_READY);
            } else {
                mHeaderView.setState(XListViewHeader.STATE_NORMAL);
            }
            if (mPullLoading) { // disable, hide the content
                mHeaderViewContent.setVisibility(View.INVISIBLE);
            } else {
                mHeaderViewContent.setVisibility(View.VISIBLE);
            }
        }
        setRefreshTime();
        setSelection(0); // scroll to top each time
    }

    /**
     * reset header view's height.
     */
    private void resetHeaderHeight() {
        int height = mHeaderView.getVisiableHeight();
        if (height == 0) // not visible.
            return;
        // refreshing and header isn't shown fully. do nothing.
        if (mPullRefreshing && height <= mHeaderViewHeight) {
            return;
        }
        int finalHeight = 0; // default: scroll back to dismiss header.
        // is refreshing, just scroll back to show all the header.
        if (mPullRefreshing && height > mHeaderViewHeight) {
            finalHeight = mHeaderViewHeight;
        }
        mScrollBack = SCROLLBACK_HEADER;
        mScroller.startScroll(0, height, 0, finalHeight - height,
                SCROLL_DURATION);
        // trigger computeScroll
        invalidate();
    }

    private void updateFooterHeight(float delta) {
        int height = mFooterView.getBottomMargin() + (int) delta;
        if (mEnablePullLoad && !mPullLoading) {
            if (height > PULL_LOAD_MORE_DELTA) { // height enough to invoke load
                // more.
                mFooterView.setState(XListViewFooter.STATE_READY);
            } else {
                mFooterView.setState(XListViewFooter.STATE_NORMAL);
            }
            if (mPullRefreshing) {
                mFooterView.hide();
            } else {
                mFooterView.show();
            }
        }
        mFooterView.setBottomMargin(height);
        // DLog.i(TAG, "mFooterView.setBottomMargin(height) with " + height);

        // setSelection(mTotalItemCount - 1); // scroll to bottom
    }

    private void resetFooterHeight() {
        int bottomMargin = mFooterView.getBottomMargin();
        if (bottomMargin > 0) {
            mScrollBack = SCROLLBACK_FOOTER;
            mScroller.startScroll(0, bottomMargin, 0, -bottomMargin,
                    SCROLL_DURATION);
            invalidate();
        }
    }

    private void startLoadMore() {
        mPullLoading = true;
        mFooterView.setState(XListViewFooter.STATE_LOADING);
        if (mListViewListener != null) {
            mListViewListener.onLoadMore();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mLastY == -1) {
            mLastY = ev.getRawY();
        }

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                final float deltaY = ev.getRawY() - mLastY;
                // DLog.i(TAG, "deltaY is " + deltaY);
                mLastY = ev.getRawY();
                // DLog.i(TAG, "FirstVisiblePosition is " +
                // getFirstVisiblePosition());
                // DLog.i(TAG, "LastVisiblePosition is " +
                // getLastVisiblePosition());
                if (getFirstVisiblePosition() == 0
                        && (mHeaderView.getVisiableHeight() > 0 || deltaY > 0)) {
                    // the first item is showing, header has shown or pull down.
                    updateHeaderHeight(deltaY / OFFSET_RADIO);
                    invokeOnScrolling();
                } else if (getLastVisiblePosition() == mTotalItemCount - 1
                        && (mFooterView.getBottomMargin() > 0 || deltaY < 0)) {
                    // last item, already pulled up or want to pull up.
                    updateFooterHeight(-deltaY / OFFSET_RADIO);
                }
                break;
            default:
                mLastY = -1; // reset
                if (getFirstVisiblePosition() == 0) {
                    // invoke refresh
                    if (!mPullRefreshing && mEnablePullRefresh && !mPullLoading
                            && mHeaderView.getVisiableHeight() > mHeaderViewHeight) {
                        mPullRefreshing = true;
                        mHeaderView.setState(XListViewHeader.STATE_REFRESHING);
                        // DLog.i(TAG, "invoke refresh");
                        if (mListViewListener != null) {
                            mListViewListener.onRefresh();
                        }
                    }
                    resetHeaderHeight();
                } else if (getLastVisiblePosition() == mTotalItemCount - 1) {
                    // invoke load more.
                    if (!mPullLoading && mEnablePullLoad && !mPullRefreshing
                            && mFooterView.getBottomMargin() > PULL_LOAD_MORE_DELTA) {
                        // DLog.i(TAG, "invoke load more");
                        startLoadMore();
                    }
                    resetFooterHeight();
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            if (mScrollBack == SCROLLBACK_HEADER) {
                mHeaderView.setVisiableHeight(mScroller.getCurrY());
            } else {
                mFooterView.setBottomMargin(mScroller.getCurrY());
            }
            postInvalidate();
            invokeOnScrolling();
        }
        super.computeScroll();
    }

    @Override
    public void setOnScrollListener(OnScrollListener l) {
        mScrollListener = l;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (mScrollListener != null) {
            mScrollListener.onScrollStateChanged(view, scrollState);
        }
        // 婊戝埌搴曢儴鏃讹紝鑷姩鍔犺浇鏇村銆�涔熷彲浠ョ鐢ㄦ閫昏緫
        if (getLastVisiblePosition() == mTotalItemCount - 1
                && scrollState == SCROLL_STATE_IDLE) {
            if (!mPullLoading && mEnablePullLoad && !mPullRefreshing) {
                // DLog.i(TAG, "invoke load more");
                startLoadMore();
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        // send to user's listener
        mTotalItemCount = totalItemCount;
        if (mScrollListener != null) {
            mScrollListener.onScroll(view, firstVisibleItem, visibleItemCount,
                    totalItemCount);
        }
    }

    public void setXListViewListener(IXListViewListener l) {
        mListViewListener = l;
    }

    /**
     * you can listen ListView.OnScrollListener or this one. it will invoke
     * onXScrolling when header/footer scroll back.
     */
    public interface OnXScrollListener extends OnScrollListener {

        public void onXScrolling(View view);
    }

    /**
     * implements this interface to get refresh/load more event.
     */
    public interface IXListViewListener {

        public void onRefresh();

        public void onLoadMore();
    }
}
