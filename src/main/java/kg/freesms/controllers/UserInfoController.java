package kg.freesms.controllers;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import kg.freesms.entity.UserInfo;
import kg.freesms.facade.UserInfoFacade;

@ViewScoped
@ManagedBean
public class UserInfoController extends AbstractController implements Serializable {
	
	private static final long serialVersionUID = -4828734242279294513L;
	
	private UserInfo userInfo;
	private List<UserInfo> userInfos;
	private UserInfoFacade userInfoFacade;

	public UserInfoFacade getUserInfoFacade() {
		if (userInfoFacade == null) {
			userInfoFacade = new UserInfoFacade();
		}

		return userInfoFacade;
	}

	public UserInfo getUserInfo() {
		if (userInfo == null) {
			userInfo = new UserInfo();
		}

		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public void createUserInfo() {
		try {
			getUserInfoFacade().createUserInfo(userInfo);
			closeDialog();
			displayInfoMessageToUser("Created With Sucess");
			//loadUserInfos();
			resetUserInfo();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Ops, we could not create. Try again later");
			e.printStackTrace();
		}
	}

	public void updateUserInfo() {
		try {
			getUserInfoFacade().updateUserInfo(userInfo);
			closeDialog();
			displayInfoMessageToUser("Updated With Sucess");
			//loadUserInfos();
			resetUserInfo();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Ops, we could not create. Try again later");
			e.printStackTrace();
		}
	}

	public void deleteUserInfo() {
		try {
			getUserInfoFacade().deleteUserInfo(userInfo);
			closeDialog();
			displayInfoMessageToUser("Deleted With Sucess");
			//loadUserInfos();
			resetUserInfo();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Ops, we could not create. Try again later");
			e.printStackTrace();
		}
	}
	
	public List<UserInfo> getAllUserInfos() {
		if (userInfos == null) {
			loadUserInfos();
		}
		
		return userInfos;
	}
	
	private void loadUserInfos() {
		userInfos = getUserInfoFacade().listAll();
	}
	
	public void resetUserInfo() {
		userInfo = new UserInfo();
	}
	
}
