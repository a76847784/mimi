package com.sdust.im.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.sdust.im.BaseActivity;
import com.sdust.im.R;
import com.sdust.im.action.UserAction;
import com.sdust.im.bean.ApplicationData;
import com.sdust.im.bean.TranObject;
import com.sdust.im.bean.User;
import com.sdust.im.activity.FriendSearchSimilarityResultActivity;
import com.sdust.im.util.PhotoUtils;
import com.sdust.im.view.TitleBarView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class SearchFriendBySimilarityActivity extends BaseActivity implements OnClickListener {
	private TitleBarView mTitleBarView;
	private ImageView mIvUserPhoto;
	private LinearLayout mLayoutSelectPhoto;
	private LinearLayout mLayoutTakePicture;
	private Button mBtnSearchBySimilarity;
	private String mTakePicturePath;
	private static boolean mIsReceived;
	private Bitmap mUserPhoto;
	private boolean flag = false;
	private Context mContext;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.include_similarity_photo);
		mContext = this;
		initViews();
		initEvents();
	}

	@Override
	public void initViews() {
		mTitleBarView = (TitleBarView) findViewById(R.id.title_bar_sim);
		mTitleBarView.setCommonTitle(View.GONE, View.VISIBLE, View.GONE);
		mTitleBarView.setTitleText("查找朋友");
		mIvUserPhoto = (ImageView) findViewById(R.id.sim_photo_iv_userphoto);
		mLayoutSelectPhoto = (LinearLayout) findViewById(R.id.sim_photo_layout_selectphoto);
		mLayoutTakePicture = (LinearLayout) findViewById(R.id.sim_photo_layout_takepicture);
		mBtnSearchBySimilarity = (Button) findViewById(R.id.search_friend_by_sim_btn_search);
	}

	@Override
	public void initEvents() {
		mIsReceived = false;
		mLayoutSelectPhoto.setOnClickListener(this);
		mLayoutTakePicture.setOnClickListener(this);
		mBtnSearchBySimilarity.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.sim_photo_layout_selectphoto:
				PhotoUtils.selectPhoto(this);
				break;

			case R.id.sim_photo_layout_takepicture:
				mTakePicturePath = PhotoUtils.takePicture(this);
				break;

			case R.id.search_friend_by_sim_btn_search:
				try {
					User user = ApplicationData.getInstance().getUserInfo();
					Log.i("user account",user.getAccount());
					byte[] photoByte = PhotoUtils.getBytes(mUserPhoto);
					flag = true;
					UserAction.searchFriendSimilarity(user,photoByte);

					//change 0616 add
					trySearch();
					//change 0616

				} catch (Exception e) {
					Log.d("searchFriend", "传输照片异常");

				}
			default:
				break;
		}
//change 0616 add注释
//		if (flag) {
//			mIsReceived = false;
//			showLoadingDialog("正在查找...");
//			while (!mIsReceived) {
//			}
//			System.out.println("准备跳转查找结果页面");
//			Intent intent = new Intent(this, FriendSearchSimilarityResultActivity.class);
//			//Bundle mBundle = new Bundle();
//			//mBundle.putSerializable("result", mReceivedMessage);
//			//intent.putExtras(mBundle);
//			startActivity(intent);
//			finish();
//			System.out.println("已跳转");
//		}
//change 0616
	}

	//change 0616 add trySearch()
	private void trySearch() {
		new AsyncTask<Void, Void, Integer>() {

			@Override
			protected Integer doInBackground(Void... params) {
				while (!mIsReceived) {
				}
				return 1;
			}

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				showLoadingDialog("正在查找,请稍后...");
			}

			@Override
			protected void onPostExecute(Integer result) {
				super.onPostExecute(result);
				dismissLoadingDialog();
				if (result == 1) {

					System.out.println("准备跳转查找结果页面");
					Intent intent = new Intent(mContext, FriendSearchSimilarityResultActivity.class);
					//Bundle mBundle = new Bundle();
					//mBundle.putSerializable("result", mReceivedMessage);
					//intent.putExtras(mBundle);
					startActivity(intent);
					finish();
					System.out.println("已跳转");
				}
			}
		}.execute();

	}
	//change 0616

	public static void messageArrived(TranObject mReceived){
		ArrayList<User> list = (ArrayList<User>)mReceived.getObject();
		ApplicationData.getInstance().setFriendSearched(list);
		mIsReceived = true;
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {

			case PhotoUtils.INTENT_REQUEST_CODE_ALBUM:
				try {
					if (data == null) {
						return;
					}
					if (resultCode == RESULT_OK) {

						if (data.getData() == null) {
							return;
						}
						Uri uri = data.getData();
						String[] proj = { MediaStore.MediaColumns.DATA };
						Cursor cursor = managedQuery(uri, proj, null, null, null);
						if (cursor != null) {
							int column_index = cursor
									.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
							if (cursor.getCount() > 0 && cursor.moveToFirst()) {
								String path = cursor.getString(column_index);
								Log.i("path",path);
								Bitmap bitmap = BitmapFactory.decodeFile(path);
								if (PhotoUtils.bitmapIsLarge(bitmap)) {
									PhotoUtils.cropPhoto(this, this, path);
								} else {
									setUserPhoto(PhotoUtils.compressImage(bitmap));
								}
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;

			case PhotoUtils.INTENT_REQUEST_CODE_CAMERA:
				if (resultCode == RESULT_OK) {
					String path = mTakePicturePath;

					Log.i("path",path);
					Bitmap bitmap = null;
					if (path != null) {
						File file = null ;
						try {
							file = new File(path);
							bitmap = BitmapFactory.decodeFile(path);
							if (bitmap == null) {
								//如果图片为null, 图片不完整则删除掉图片
								byte[] bytes = new byte[(int) file.length() + 1];
								FileInputStream inputStream = new FileInputStream(path);
								inputStream.read(bytes);
								bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
								if (bitmap == null) {
									file.delete();
								}
							}
						}catch (Exception e) {
							e.printStackTrace();
						}

						if (bitmap != null) {

							if (PhotoUtils.bitmapIsLarge(bitmap)) {
								PhotoUtils.cropPhoto(this, this, path);
							} else {
								//mStepPhoto.setUserPhoto(bitmap);
								setUserPhoto(PhotoUtils.compressImage(bitmap));
							}
						}
					}

				}
				break;

			case PhotoUtils.INTENT_REQUEST_CODE_CROP:
				if (resultCode == RESULT_OK) {
					String path = data.getStringExtra("path");
					if (path != null) {
						Bitmap bitmap = BitmapFactory.decodeFile(path);
						if (bitmap != null) {
							//mStepPhoto.setUserPhoto(bitmap);
							setUserPhoto(PhotoUtils.compressImage(bitmap));

						}
					}
				}
				break;
			default:
				break;
		}

	}

	public void setUserPhoto(Bitmap bitmap) {
		if (bitmap != null) {
			mUserPhoto = bitmap;
			mIvUserPhoto.setImageBitmap(mUserPhoto);
			return;
		}
		showCustomToast("未获取到图片");
		mUserPhoto = null;
		mIvUserPhoto.setImageResource(R.drawable.ic_common_def_header);
	}
	@Override
	protected void putAsyncTask(AsyncTask<Void, Void, Integer> asyncTask) {
		super.putAsyncTask(asyncTask);
	}

	@Override
	protected void showCustomToast(String text) {
		super.showCustomToast(text);
	}

	@Override
	protected void dismissLoadingDialog() {
		super.dismissLoadingDialog();
	}

	protected int getScreenWidth() {
		return mScreenWidth;
	}
}
