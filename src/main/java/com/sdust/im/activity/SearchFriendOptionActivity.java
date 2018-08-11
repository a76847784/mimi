package com.sdust.im.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.sdust.im.BaseActivity;
import com.sdust.im.R;
import com.sdust.im.view.TitleBarView;

public class SearchFriendOptionActivity extends BaseActivity implements
		OnClickListener {

	private TitleBarView mTitleBarView;
	private Button mBtnSearchByName;
	private Button mBtnSearchBySimilarity;

	private static boolean mIsReceived;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_searchfriendoption);

		initViews();
		initEvents();
	}

	@Override
	protected void initViews() {
		mTitleBarView = (TitleBarView) findViewById(R.id.title_bar);
		mTitleBarView.setCommonTitle(View.GONE, View.VISIBLE, View.GONE);
		mTitleBarView.setTitleText("选择查找朋友方式");
		mBtnSearchByName = (Button) findViewById(R.id.search_friend_by_name_search);
		mBtnSearchBySimilarity = (Button) findViewById(R.id.search_friend_by_similarity_search);
	}

	@Override
	protected void initEvents() {
		// TODO Auto-generated method stub
		mIsReceived = false;
		mBtnSearchByName.setOnClickListener(this);
		mBtnSearchBySimilarity.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		Intent intent;
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.search_friend_by_name_search:

			intent = new Intent(SearchFriendOptionActivity.this, SearchFriendActivity.class);
			startActivity(intent);
			break;
		case R.id.search_friend_by_similarity_search:

			intent = new Intent(SearchFriendOptionActivity.this, SearchFriendBySimilarityActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}

}
