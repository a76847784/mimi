package com.sdust.im.activity.register;

import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.sdust.im.BaseDialog;
import com.sdust.im.R;
import com.sdust.im.util.VerifyUtils;

public class StepBaseInfo extends RegisterStep implements TextWatcher
		{
//			0616取消实现接口
//OnCheckedChangeListener
	private EditText mEtName;
	private EditText mIDCard;
//	0616 取消性别
//	private RadioGroup mRgGender;
//	private RadioButton mRbMale;
//	private RadioButton mRbFemale;
//	private int mGender = 0;//0代表女 1代表男
//	private boolean mIsGenderAlert;
//	0616
	private String mName;
	private String mID;
	private boolean mIsChange = true;
	private BaseDialog mBaseDialog;

	public StepBaseInfo(RegisterActivity activity, View contentRootView) {
		super(activity, contentRootView);
	}

	@Override
	public void initViews() {
		mEtName = (EditText) findViewById(R.id.reg_baseinfo_et_name);
		mIDCard = (EditText) findViewById(R.id.reg_baseinfo_et_id);
//		0616 取消性别
//		mRgGender = (RadioGroup) findViewById(R.id.reg_baseinfo_rg_gender);
//		mRbMale = (RadioButton) findViewById(R.id.reg_baseinfo_rb_male);
//		mRbFemale = (RadioButton) findViewById(R.id.reg_baseinfo_rb_female);
//		0616
	}

	@Override
	public void initEvents() {
		mEtName.addTextChangedListener(this);
		mIDCard.addTextChangedListener(this);
//		0616取消性别
//		mRgGender.setOnCheckedChangeListener(this);
//		0616
	}

	@Override
	public void doNext() {
		mOnNextActionListener.next();
	}

	@Override
	public boolean validate() {
		mName = mEtName.getText().toString().trim();
		if (VerifyUtils.isNull(mEtName)) {
			showCustomToast("请输入用户名");
			mEtName.requestFocus();
			return false;
		}

		String ID = mIDCard.getText().toString().trim();
		if (VerifyUtils.isNull(mIDCard)) {
			showCustomToast("请输入身份证号");
			mIDCard.requestFocus();
			return false;
		}

		if (VerifyUtils.matchAccount1(ID)) {
			mID = ID;
		} else {
			showCustomToast("身份证号格式不正确");
			mIDCard.requestFocus();
			return false;
		}
//		0616 取消性别
//		if (mRgGender.getCheckedRadioButtonId() < 0) {
//			showCustomToast("请选择性别");
//			return false;
//		}
//		0616
		return true;
	}

	@Override
	public boolean isChange() {
		return mIsChange;
	}

//	0616取消性别
//	@Override
//	public void onCheckedChanged(RadioGroup group, int checkedId) {
//		mIsChange = true;
//		0616取消性别
//		if (!mIsGenderAlert) {
//			mIsGenderAlert = true;
//			mBaseDialog = BaseDialog.getDialog(mContext, "提示", "注册成功后性别将不可更改",
//					"确认", new DialogInterface.OnClickListener() {
//
//						@Override
//						public void onClick(DialogInterface dialog, int which) {
//							dialog.dismiss();
//						}
//					});
//			mBaseDialog.show();
//		}
//		switch (checkedId) {
//		case R.id.reg_baseinfo_rb_male:
//			mRbMale.setChecked(true);
//			mGender = 1;
//			break;
//
//		case R.id.reg_baseinfo_rb_female:
//			mRbFemale.setChecked(true);
//			mGender = 0;
//			break;
//		}
//	}
//	0616

	@Override
	public void afterTextChanged(Editable s) {

	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		mIsChange = true;
	}
	public String getName() {
		return mName;
	}
	public String getID() { return  mID;}
//	0616取消性别
//	public int getGender(){
//		return mGender;
//	}
//	0616
}
