package kg.freesms.dao;
 
import java.util.HashMap;
import java.util.Map;

import kg.freesms.entity.UserInfo;
 
public class UserInfoDAO extends GenericDAO<UserInfo> {
	
	private static final long serialVersionUID = 9008597851660966588L;
	
	public UserInfoDAO() {
        super(UserInfo.class);
    }
 
	public void delete(UserInfo userInfo) {
    	super.delete(userInfo.getId(), UserInfo.class);
	}
	
	public UserInfo findByUserId(int userId) {
		String querry = "select u from UserInfo u where u.user = :user";
		Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("user", userId);
    	return super.findOneResult(querry, parameters);
	}
}
