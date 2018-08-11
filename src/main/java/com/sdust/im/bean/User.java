/**
 * 文件名：User.java
 * 时间：2015年5月9日上午10:23:19
 * 作者：修维康
 */
package com.sdust.im.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * 类名：User 说明：用户对象
 */
public class User implements Serializable, Comparable<User> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String account;
//	private String idCard;
	private String userName;
	private String password;
	private Date birthday;
	private int gender; // 0代表女生 1代表男生
	private boolean isOnline;
	private String location;
	private byte[] photo;
	private int age;
	private String userBriefIntro;
	//add 身份证号
	private String identityNo;
	//相似度
	private String similarity;

	public String getSimilarity() {
		return similarity;
	}

	public void setSimilarity(String similarity) {
		this.similarity = similarity;
	}

	public String getUserBriefIntro() {
		return userBriefIntro;
	}

	public void setUserBriefIntro(String userBriefIntro) {
		this.userBriefIntro = userBriefIntro;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	private ArrayList<User> friendList;

	public ArrayList<User> getFriendList() {
		return friendList;
	}

	public void setFriendList(ArrayList<User> friendList) {
		this.friendList = friendList;
	}

	public User(String account, String username, String identityNo,  String password,
			Date birthday, int gender, byte[] photo) {
		this.account = account;
		this.userName = username;
		this.identityNo = identityNo;
		this.password = password;
		this.birthday = birthday;
		this.gender = gender;
		this.photo = photo;
	}

	public User(String account, String username, String identityNo,  String password,
				byte[] photo) {
		this.account = account;
		this.userName = username;
		this.identityNo = identityNo;
		this.password = password;
//		this.birthday = birthday;
//		this.gender = gender;
		this.photo = photo;
	}

	public User() {

	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getIdentityNo() {
		return identityNo;
	}

	public void setIdentityNo(String identityNo) {
		this.identityNo = identityNo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public boolean isOnline() {
		return isOnline;
	}

	public void setIsOnline(boolean isOnline) {
		this.isOnline = isOnline;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Override
	public boolean equals(Object o) {
		User user = (User) o;
		if (this.id == user.getId())
			return true;
		return false;
	}

	@Override
	public String toString() {
		return "User{" +
				"account='" + account + '\'' +
				", userName='" + userName + '\'' +
				", photo=" + Arrays.toString(photo) +
				", identityNo='" + identityNo + '\'' +
				'}';
	}

	@Override
	public int compareTo(User o) {
		int i;
		if (Double.parseDouble(o.getSimilarity())>Double.parseDouble(this.getSimilarity())) {
			i=1;
		} else if (Double.parseDouble(o.getSimilarity())==Double.parseDouble(this.getSimilarity())) {
			i=0;
		} else {
			i=-1;
		}
		return i;
	}
}
