package kg.freesms.dao;

import java.util.List;

import javax.persistence.Query;

import kg.freesms.entity.Group;
import kg.freesms.entity.User;

public class GroupDAO extends GenericDAO<Group> {
	
	private static final long serialVersionUID = -3818538855614947575L;
	
	public GroupDAO() {
		super(Group.class);
	}
	
	public Group findGroupWithAllPhones(int groupId) {
		
		String hql = "select g from Group g left join fetch g.phones where g.id = :groupId";
		Query query = em.createQuery(hql);
		query.setParameter("groupId", groupId);
		
		return (Group) query.getSingleResult();
	}
	
	public void delete(Group group) {
		super.delete(group.getId(), Group.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<Group> findByUser(User user) {
		String hql = "select g from Group g where g.user = :user";
		Query query = em.createQuery(hql);
		query.setParameter("user", user);
		
		return query.getResultList();
	}
}
