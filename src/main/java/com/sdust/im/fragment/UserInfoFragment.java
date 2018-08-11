package com.sdust.im.fragment;

import com.sdust.im.R;
import com.sdust.im.bean.ApplicationData;
import com.sdust.im.bean.User;
import com.sdust.im.util.PhotoUtils;
import com.sdust.im.view.TitleBarView;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class UserInfoFragment extends Fragment{
	private Context mContext;
	private View mBaseView;
	private TitleBarView mTitleBarView;
	private ImageView mIvUserPhoto;
	private TextView mIvUserName;
	private TextView mIvUserAccount;
	private User user;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mContext = getActivity();
		mBaseView = inflater.inflate(R.layout.fragment_userinfo, null);
		findView();
		init();
		return mBaseView;
	}
	private void findView(){
		mTitleBarView=(TitleBarView) mBaseView.findViewById(R.id.title_bar);
		mIvUserPhoto = (ImageView) mBaseView.findViewById(R.id.reg_photo_iv_userphoto1);
		mIvUserName = (TextView) mBaseView.findViewById(R.id.user_name1);
		mIvUserAccount = (TextView) mBaseView.findViewById(R.id.user_account);
	}
	
	private void init(){
		mTitleBarView.setCommonTitle(View.GONE, View.VISIBLE, View.GONE);
		mTitleBarView.setTitleText("个人信息");
		user = ApplicationData.getInstance().getUserInfo();
		Bitmap photo = PhotoUtils.getBitmap(user.getPhoto());
		if (photo != null) {
			mIvUserPhoto.setImageBitmap(photo);
		}
		mIvUserName.setText("姓名:"+user.getUserName());
		mIvUserAccount.setText("帐号:"+user.getAccount());
	}
}

