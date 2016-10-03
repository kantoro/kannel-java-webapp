package kg.freesms.controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import kg.freesms.entity.Group;
import kg.freesms.entity.Phone;
import kg.freesms.facade.GroupFacade;
import kg.freesms.facade.PhoneFacade;

import com.sun.faces.context.flash.ELFlash;

@ViewScoped
@ManagedBean
public class GroupController extends AbstractController implements Serializable {
	
	private static final long serialVersionUID = -911341964996842455L;
	
	private static final String SELECTED_GROUP = "selectedGroup";
	
	private Phone phone;
	private Group group;
	private Group groupWithPhones;
	private Group groupWithPhonesForDetail;
	
	private List<Phone> allPhones;
	private List<Group> groups;
	
	private PhoneFacade phoneFacade;
	private GroupFacade groupFacade;
	
	@Inject
	private UserController userController;
	
//	@PostConstruct
//	public void init() {
//		if (phone == null) {
//			phone = new Phone();
//		}
//		if (group == null) {
//			group = new Group();
//		}
//	}
	
	public void createGroup() {
		try {
			group.setUser(userController.getUser());
			group.setDateCreated(new Date());
			getGroupFacade().createGroup(group);
			closeDialog();
			displayInfoMessageToUser("Created With Sucess");
			loadUserGroups();
			resetGroup();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Ops, we could not create. Try again later");
			e.printStackTrace();
		}
	}

	public void updateGroup() {
		try {
			getGroupFacade().updateGroup(group);
			closeDialog();
			displayInfoMessageToUser("Updated With Sucess");
			loadUserGroups();
			resetGroup();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Ops, we could not create. Try again later");
			e.printStackTrace();
		}
	}

	public void deleteGroup() {
		try {
			getGroupFacade().deleteGroup(group);
			closeDialog();
			displayInfoMessageToUser("Deleted With Sucess");
			loadUserGroups();
			resetGroup();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Ops, we could not create. Try again later");
			e.printStackTrace();
		}
	}

	public void addPhoneToGroup() {
		try {
			getGroupFacade().addPhoneToGroup(phone.getId(),
					groupWithPhones.getId());
			closeDialog();
			displayInfoMessageToUser("Added With Sucess");
			reloadGroupWithPhones();
			resetPhone();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Ops, we could not create. Try again later");
			e.printStackTrace();
		}
	}

	public void removePhoneFromGroup() {
		try {
			getGroupFacade().removePhoneFromGroup(phone.getId(),
					groupWithPhones.getId());
			closeDialog();
			displayInfoMessageToUser("Removed With Sucess");
			reloadGroupWithPhones();
			resetPhone();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Ops, we could not create. Try again later");
			e.printStackTrace();
		}
	}

	public Group getGroupWithPhones() {
		if (groupWithPhones == null) {
			if (group == null) {
				group = (Group) ELFlash.getFlash().get(SELECTED_GROUP);
			}

			groupWithPhones = getGroupFacade().findGroupWithAllPhones(
					group.getId());
		}

		return groupWithPhones;
	}

	public void setGroupWithPhonesForDetail(Group group) {
		groupWithPhonesForDetail = getGroupFacade().findGroupWithAllPhones(
				group.getId());
	}

	public Group getGroupWithPhonesForDetail() {
		if (groupWithPhonesForDetail == null) {
			groupWithPhonesForDetail = new Group();
			groupWithPhonesForDetail.setPhones(new HashSet<Phone>());
		}

		return groupWithPhonesForDetail;
	}

	public void resetGroupWithPhonesForDetail() {
		groupWithPhonesForDetail = new Group();
	}

	public String editGroupPhones() {
		ELFlash.getFlash().put(SELECTED_GROUP, group);
		return "/pages/protected/group/groupPhones/groupPhones.xhtml?faces-redirect=true";
	}

	public List<Phone> complete(String name) {
		List<Phone> queryResult = new ArrayList<Phone>();
		
		if (allPhones == null) {
			phoneFacade = new PhoneFacade();
			allPhones = phoneFacade.findPhonesByUser(userController.getUser());
		}

		allPhones.removeAll(groupWithPhones.getPhones());

		for (Phone phone : allPhones) {
			if (phone.getPhoneNumber().toLowerCase().contains(name.toLowerCase())) {
				queryResult.add(phone);
			}
		}

		return queryResult;
	}

	public GroupFacade getGroupFacade() {
		if (groupFacade == null) {
			groupFacade = new GroupFacade();
		}

		return groupFacade;
	}

	public Group getGroup() {
		if (group == null) {
			group = new Group();
		}
		
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

//	public List<Group> getAllGroups() {
//		if (groups == null) {
//			loadGroups();
//		}
//
//		return groups;
//	}
//
//	private void loadGroups() {
//		groups = getGroupFacade().listAll();
//	}

	public List<Group> getUserGroups() {
		if (groups == null) {
			loadUserGroups();
		}

		return groups;
	}

	private void loadUserGroups() {
		groups = getGroupFacade().findGroupsByUser(userController.getUser());
	}

	public void resetGroup() {
		group = new Group();
	}

	public Phone getPhone() {
		if (phone == null) {
			phone = new Phone();
		}
		
		return phone;
	}

	public void setPhone(Phone phone) {
		this.phone = phone;
	}

	public void resetPhone() {
		phone = new Phone();
	}
	
	private void reloadGroupWithPhones() {
		groupWithPhones = getGroupFacade()
				.findGroupWithAllPhones(group.getId());
	}
	
}
