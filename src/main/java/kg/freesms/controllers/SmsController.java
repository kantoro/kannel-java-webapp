package kg.freesms.controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import kg.freesms.entity.Group;
import kg.freesms.entity.Phone;
import kg.freesms.facade.GroupFacade;
import kg.freesms.facade.PhoneFacade;
import kg.freesms.util.KannelSmsSender;

@ViewScoped
@ManagedBean
public class SmsController extends AbstractController implements Serializable {
	
	private static final long serialVersionUID = -6024099516213492241L;
	
	private String phoneNumber;
	private String smsContent;
	private Phone phone;
	private Group group;
	private List<Phone> phones;
	private List<Phone> userPhones;
	private List<Group> userGroups;
	@Inject
	private UserController userController;  

	@PostConstruct
	public void init() {
		if (phone == null) {
			phone = new Phone();
		}
		if (group == null) {
			group = new Group();
		}
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
	
	public Group getGroup() {
		return group;
	}
	
	public void setGroup(Group group) {
		this.group = group;
	}

	public void addPhone() {
		try {
			phone.setPhoneNumber(phoneNumber);
			phones.add(phone);
			displayInfoMessageToUser("Phone Number added to Recievers list");
			getPhones();
			resetPhone();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Ops, we could not add. Try again later");
			e.printStackTrace();
		}
	}
	
	public void addPhoneFromDB() {
		try {
			phones.add(phone);
			displayInfoMessageToUser("Phone From DB added to Recievers list");
			getPhones();
			resetPhone();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Ops, we could not add. Try again later");
			e.printStackTrace();
		}
	}
	
	public void addPhonesFromGroup() {
		try {
			phones.addAll(group.getPhones());
			displayInfoMessageToUser("Phones From Group were added to Recievers list");
			getPhones();
			resetPhone();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Ops, we could not add. Try again later");
			e.printStackTrace();
		}
	}
	
	public void removePhone() {
		try {
			phones.remove(phone);
			displayInfoMessageToUser("Phone Number was removed to Recievers list");
			getPhones();
			resetPhone();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Ops, we could not remove. Try again later");
			e.printStackTrace();
		}
	}
	
	public List<Phone> getPhones() {
		if (phones == null) {
			phones = new ArrayList<Phone>();
		}
		return phones;
	}
	
	public void setPhones(List<Phone> phones) {
		this.phones = phones;
	}

	
	public void resetPhone() {
		phone = new Phone();
		phoneNumber = null;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String getSmsContent() {
		return smsContent;
	}
	
	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}

	public List<Phone> complete(String name) {
		List<Phone> queryResult = new ArrayList<Phone>();
		
		if (userPhones == null) {
			PhoneFacade phoneFacade = new PhoneFacade();
			userPhones = phoneFacade.findPhonesByUser(userController.getUser());
		}

		userPhones.removeAll(phones);

		for (Phone phone : userPhones) {
			if (phone.getPhoneNumber().toLowerCase().contains(name.toLowerCase())) {
				queryResult.add(phone);
			}
		}

		return queryResult;
	}
	
	public List<Group> completeGroup(String name) {
		List<Group> queryResult = new ArrayList<Group>();
		
		if (userGroups == null) {
			GroupFacade groupFacade = new GroupFacade();
			userGroups = groupFacade.findGroupsByUser(userController.getUser());
		}
		
		userGroups.remove(group);
		
		for (Group group : userGroups) {
			if (group.getGroupName().toLowerCase().contains(name.toLowerCase())) {
				queryResult.add(group);
			}
		}

		return queryResult;
	}
	
	public String sendSMS() {
		KannelSmsSender kannelSmsSender = new KannelSmsSender();
		kannelSmsSender.sendSMS(phones, smsContent);
		
		return "/pages/protected/index.xhtml?faces-redirect=true;";
	}
	
}
