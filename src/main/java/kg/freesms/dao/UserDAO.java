package kg.freesms.dao;
 
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Query;

import kg.freesms.entity.User;
 
public class UserDAO extends GenericDAO<User> {
	
	private static final long serialVersionUID = 722290919908605920L;
	
	public UserDAO() {
        super(User.class);
    }
	
	public void delete(User user) {
    	super.delete(user.getId(), User.class);
	}
	
    public User findUserByUsername(String email){
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("email", email);
        
        return super.findOneResult(User.FIND_BY_USERNAME, parameters);
    }
    
    public User findUserWithInfo(int userId) {
		String hql = "select u from User u left join fetch u.userInfo where u.id = :userId";
		Query query = em.createQuery(hql);
		query.setParameter("userId", userId);
		
		return (User) query.getSingleResult();
	}
    
}
