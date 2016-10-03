package kg.freesms.facade;

import java.io.Serializable;
import java.util.List;

import kg.freesms.dao.GroupDAO;
import kg.freesms.dao.PhoneDAO;
import kg.freesms.entity.Group;
import kg.freesms.entity.Phone;
import kg.freesms.entity.User;

public class GroupFacade implements Serializable {

	private static final long serialVersionUID = -873730830107255136L;

	private GroupDAO groupDAO = new GroupDAO();
	private PhoneDAO phoneDAO = new PhoneDAO();

	public void createGroup(Group group) {
		groupDAO.beginTransaction();
		groupDAO.save(group);
		groupDAO.commitAndCloseTransaction();
	}

	public void updateGroup(Group group) {
		groupDAO.beginTransaction();
		Group persistedGroup = groupDAO.find(group.getId());
		persistedGroup.setDescription(group.getDescription());
		persistedGroup.setGroupName(group.getGroupName());
		groupDAO.update(persistedGroup);
		groupDAO.commitAndCloseTransaction();
	}

	public Group findGroup(int groupId) {
		groupDAO.beginTransaction();
		Group group = groupDAO.find(groupId);
		groupDAO.closeTransaction();
		return group;
	}
	
	public List<Group> findGroupsByUser(User user) {
		groupDAO.beginTransaction();
		List<Group> result = groupDAO.findByUser(user);
		groupDAO.closeTransaction();
		return result;
	}
	
//	public List<Group> listAll() {
//		groupDAO.beginTransaction();
//		List<Group> result = groupDAO.findAll();
//		groupDAO.closeTransaction();
//		return result;
//	}

	public void deleteGroup(Group group) {
		groupDAO.beginTransaction();
		Group persistedGroup = groupDAO.findReferenceOnly(group.getId());
		groupDAO.delete(persistedGroup);
		groupDAO.commitAndCloseTransaction();
	}

	public Group findGroupWithAllPhones(int groupId) {
		groupDAO.beginTransaction();
		Group group = groupDAO.findGroupWithAllPhones(groupId);
		groupDAO.closeTransaction();
		return group;
	}

	public void addPhoneToGroup(int phoneId, int groupId) {
		groupDAO.beginTransaction();
		phoneDAO.joinTransaction();
		Phone phone = phoneDAO.find(phoneId);
		Group group = groupDAO.find(groupId);
		group.getPhones().add(phone);
		phone.getGroups().add(group);
		groupDAO.commitAndCloseTransaction();
	}

	public void removePhoneFromGroup(int phoneId, int groupId) {
		groupDAO.beginTransaction();
		phoneDAO.joinTransaction();
		Phone phone = phoneDAO.find(phoneId);
		Group group = groupDAO.find(groupId);
		group.getPhones().remove(phone);
		phone.getGroups().remove(group);
		groupDAO.commitAndCloseTransaction();
	}
}
