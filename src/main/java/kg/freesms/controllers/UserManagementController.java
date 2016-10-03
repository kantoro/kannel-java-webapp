package kg.freesms.controllers;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import kg.freesms.controllers.data.LazyUserDataModel;
import kg.freesms.entity.Role;
import kg.freesms.entity.User;
import kg.freesms.entity.UserInfo;
import kg.freesms.facade.UserFacade;
import kg.freesms.util.DigestUtil;

import org.primefaces.model.LazyDataModel;

@ViewScoped
@ManagedBean
public class UserManagementController extends AbstractController implements Serializable {

	private static final long serialVersionUID = -9035691449456728438L;

	private User user;
	private LazyDataModel<User> userModel;
	private UserFacade userFacade;
	
	@PostConstruct
	public void init() {
		if (user == null) {
			user = new User();
		}
	}

	public UserFacade getUserFacade() {
		if (userFacade == null) {
			userFacade = new UserFacade();
		}

		return userFacade;
	}
	
	public User getUser() {
		if (user == null) {
			user = new User();
		}

		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public void createUser() {
		try {
			UserInfo userInfo = new UserInfo();
			userInfo.setUser(user);
			user.setUserInfo(userInfo);
			user.setPassword(new DigestUtil("SHA-512").doEncypt(user.getPassword()));
			getUserFacade().createUser(user);
			closeDialog();
			displayInfoMessageToUser("Created With Sucess");
			loadUserModel();
			resetUser();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Ops, we could not create. Try again later");
			e.printStackTrace();
		}
	}

	public void updateUser() {
		try {
			getUserFacade().updateUser(user);
			closeDialog();
			displayInfoMessageToUser("Updated With Sucess");
			loadUserModel();
			resetUser();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Ops, we could not create. Try again later");
			e.printStackTrace();
		}
	}
	
	public void deleteUser() {
		try {
			getUserFacade().deleteUser(user);
			closeDialog();
			displayInfoMessageToUser("Deleted With Sucess");
			loadUserModel();
			resetUser();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Ops, we could not create. Try again later");
			e.printStackTrace();
		}
	}
	
	public LazyDataModel<User> getUserModel() {
		System.out.println("====getUserModel called====");
		System.out.println("====userModel====" + userModel);
		if (userModel == null) {
			loadUserModel();
		}
		
		return userModel;
	}
	
	public void setUserModel(LazyDataModel<User> userModel) {
		this.userModel = userModel;
	}
	
	private void loadUserModel() {
		userModel = new LazyUserDataModel();
	}
	
	public void resetUser() {
		user = new User();
	}
	
	public SelectItem[] getRoleValues() {
		SelectItem[] items = new SelectItem[Role.values().length];
		int i = 0;
		for(Role role: Role.values()) {
			items[i++] = new SelectItem(role, role.name());
		}
		return items;
	}
	
}
