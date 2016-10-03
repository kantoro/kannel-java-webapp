package kg.freesms.controllers;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import kg.freesms.entity.User;

@SessionScoped
@Named
public class UserController implements Serializable {
	
	private static final long serialVersionUID = 5136487060239233604L;
	
	private User user;
 
    public boolean isAdmin() {
        return user.isAdmin();
    }
 
    public boolean isDefaultUser() {
        return user.isUser();
    }
 
    public String logOut() {
        getRequest().getSession().invalidate();
        return "/pages/public/login.xhtml?faces-redirect=true";
    }
 
    private HttpServletRequest getRequest() {
        return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
    }
 
    public User getUser() {
        return user;
    }
 
    public void setUser(User user) {
        this.user = user;
    }
}
