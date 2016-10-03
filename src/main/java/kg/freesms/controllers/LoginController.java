package kg.freesms.controllers;

import java.security.NoSuchAlgorithmException;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import kg.freesms.entity.User;
import kg.freesms.facade.UserFacade;

@RequestScoped
@Named
public class LoginController extends AbstractController {
	
	private String username;
	private String password;
	
	@Inject
	private UserController userController;
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String login() {
		UserFacade userFacade = new UserFacade();
		
		User user = null;
		try {
			user = userFacade.isValidLogin(username, password);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			displayErrorMessageToUser("Error occured !");
		}
		
		if (user != null) {
			user  = userFacade.findUserWithInfo(user.getId());
			userController.setUser(user);
			FacesContext context = FacesContext.getCurrentInstance();
			HttpServletRequest request = (HttpServletRequest) context
					.getExternalContext().getRequest();
			request.getSession().setAttribute("user", user);
			return "/pages/protected/index.xhtml?faces-redirect=true";
		}
		displayErrorMessageToUser("Check your username/password");
		
		return null;
	}
	
	public void setUserController(UserController userController) {
		this.userController = userController;
	}
	
}
