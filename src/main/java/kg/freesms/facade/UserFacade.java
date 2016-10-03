package kg.freesms.facade;

import java.security.NoSuchAlgorithmException;

import kg.freesms.dao.UserDAO;
import kg.freesms.entity.User;
import kg.freesms.util.DigestUtil;

public class UserFacade {
	
	private UserDAO userDAO = new UserDAO();
	
	public void createUser(User user) {
		userDAO.beginTransaction();
		userDAO.save(user);
		userDAO.commitAndCloseTransaction();
	}
	
	public void updateUser(User user) {
		userDAO.beginTransaction();
		User persistedUser = userDAO.find(user.getId());
		persistedUser.setUserInfo(user.getUserInfo());
		userDAO.update(persistedUser);
		userDAO.commitAndCloseTransaction();
	}
	
	public void deleteUser(User user) {
		userDAO.beginTransaction();
		userDAO.delete(user);
		userDAO.commitAndCloseTransaction();
	}
	
	public User isValidLogin(String username, String password) throws NoSuchAlgorithmException {
		userDAO.beginTransaction();
		User user = userDAO.findUserByUsername(username);
		System.out.println("===username====" + username);
		System.out.println("===password====" + password);
		System.out.println("===password====" + new DigestUtil("SHA-512").doEncypt(password));
		if (user == null || !user.getPassword().equals(new DigestUtil("SHA-512").doEncypt(password))) {
			return null;
		}
		
		return user;
	}
	
	public User findUserWithInfo(int userId) {
		userDAO.beginTransaction();
		User user = userDAO.findUserWithInfo(userId);
		userDAO.closeTransaction();
		return user;
	}
	
}
