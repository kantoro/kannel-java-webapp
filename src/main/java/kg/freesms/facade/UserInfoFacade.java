package kg.freesms.facade;

import java.io.Serializable;
import java.util.List;

import kg.freesms.dao.UserInfoDAO;
import kg.freesms.entity.UserInfo;

public class UserInfoFacade implements Serializable {
	
	private static final long serialVersionUID = 6731293601768523416L;
	
	private UserInfoDAO userInfoDAO = new UserInfoDAO();

	public void createUserInfo(UserInfo userInfo) {
		userInfoDAO.beginTransaction();
		userInfoDAO.save(userInfo);
		userInfoDAO.commitAndCloseTransaction();
	}

	public void updateUserInfo(UserInfo userInfo) {
		userInfoDAO.beginTransaction();
		UserInfo persistedUserInfo = userInfoDAO.find(userInfo.getId());
		persistedUserInfo.setAddress(userInfo.getAddress());
		persistedUserInfo.setDateOfBirth(userInfo.getDateOfBirth());
		persistedUserInfo.setEmail(userInfo.getEmail());
		persistedUserInfo.setFirstName(userInfo.getFirstName());
		persistedUserInfo.setLastName(userInfo.getLastName());
		userInfoDAO.update(persistedUserInfo);
		userInfoDAO.commitAndCloseTransaction();
	}

	public UserInfo findUserInfo(int userInfoId) {
		userInfoDAO.beginTransaction();
		UserInfo userInfo = userInfoDAO.find(userInfoId);
		userInfoDAO.closeTransaction();
		return userInfo;
	}
	
	public UserInfo findUserInfoByUser(int userId) {
		userInfoDAO.beginTransaction();
		UserInfo userInfo = userInfoDAO.findByUserId(userId);
		userInfoDAO.closeTransaction();
		return userInfo;
	}

	public List<UserInfo> listAll() {
		userInfoDAO.beginTransaction();
		List<UserInfo> result = userInfoDAO.findAll();
		userInfoDAO.closeTransaction();
		return result;
	}

	public void deleteUserInfo(UserInfo userInfo) {
		userInfoDAO.beginTransaction();
		UserInfo persistedUserInfo = userInfoDAO.findReferenceOnly(userInfo.getId());
		userInfoDAO.delete(persistedUserInfo);
		userInfoDAO.commitAndCloseTransaction();
	}
}
