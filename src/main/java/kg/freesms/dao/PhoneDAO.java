package kg.freesms.dao;
 
import java.util.List;

import kg.freesms.entity.Phone;
import kg.freesms.entity.User;
 
public class PhoneDAO extends GenericDAO<Phone> {
	
	private static final long serialVersionUID = -6777016805752030345L;
	
	public PhoneDAO() {
        super(Phone.class);
    }
	
	public void delete(Phone phone) {
    	super.delete(phone.getId(), Phone.class);
	}
	
	public List<Phone> findByUser(User user) {
		//String hql = "select p from Phone p where p.user = :user";
		//Query query = em.createQuery(hql);
		//query.setParameter("user", user);
		
		return super.findByProperty("user", user);
		
		//return query.getResultList();
	}
	
}
