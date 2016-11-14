//package com.being.base.ui.widget.ptr;
//
//import android.annotation.TargetApi;
//import android.content.Context;
//import android.os.Build;
//import android.util.AttributeSet;
//import android.view.LayoutInflater;
//import android.widget.FrameLayout;
//import android.widget.ListView;
//
//import com.being.base.R;
//import com.being.base.presenter.ListPresenter;
//import com.being.base.ui.widget.ptr.loadmore.LoadMoreListViewContainer;
//
///**
// * 包括下拉刷新和加载更多的listview
// * Created by zhangpeng on 16/5/11.
// */
//public class PtrListView extends FrameLayout {
//    ListView mListView;
//    LoadMoreListViewContainer mLayoutLoadmore;
//    FPtrFrameLayout mLayoutPtr;
//    ListPresenter mListPresenter;
//
//    public PtrListView(Context context) {
//        super(context);
//        init();
//    }
//
//    public PtrListView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        init();
//    }
//
//    public PtrListView(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        init();
//    }
//
//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//    public PtrListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//        init();
//    }
//
//    private void init() {
//        LayoutInflater.from(getContext()).inflate(R.layout.layout_ptr_listview, this);
//        mListView = (ListView) findViewById(R.id.listView);
//        mLayoutLoadmore = (LoadMoreListViewContainer) findViewById(R.id.layout_loadmore);
//        mLayoutPtr = (FPtrFrameLayout) findViewById(R.id.layout_ptr);
//        mListPresenter = new ListPresenter(mListView, mLayoutPtr, mLayoutLoadmore);
//    }
//
//    public void setPtrInterFace(ListPresenter.PtrInterFace interFace) {
//        mListPresenter.setPtrInterFace(interFace);
//    }
//
//    public void requestComplete(String start) {
//        mListPresenter.refreshComplete(start);
//    }
//}
