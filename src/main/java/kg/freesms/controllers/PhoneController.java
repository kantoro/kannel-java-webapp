package kg.freesms.controllers;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import kg.freesms.controllers.data.LazyPhoneDataModel;
import kg.freesms.entity.Phone;
import kg.freesms.facade.PhoneFacade;

import org.primefaces.model.LazyDataModel;

@ViewScoped
@ManagedBean
public class PhoneController extends AbstractController implements Serializable {

	private static final long serialVersionUID = -9035691449456728438L;

	private Phone phone;
	private LazyDataModel<Phone> phoneModel;
	private PhoneFacade phoneFacade;
	@Inject
	private UserController userController;

	@PostConstruct
	public void init() {
		if (phone == null) {
			phone = new Phone();
		}
	}

	public PhoneFacade getPhoneFacade() {
		if (phoneFacade == null) {
			phoneFacade = new PhoneFacade();
		}

		return phoneFacade;
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

	public void createPhone() {
		try {
			phone.setUser(userController.getUser());
			getPhoneFacade().createPhone(phone);
			closeDialog();
			displayInfoMessageToUser("Created With Sucess");
			loadPhoneModel();
			resetPhone();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Ops, we could not create. Try again later");
			e.printStackTrace();
		}
	}

	public void updatePhone() {
		try {
			getPhoneFacade().updatePhone(phone);
			closeDialog();
			displayInfoMessageToUser("Updated With Sucess");
			loadPhoneModel();
			resetPhone();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Ops, we could not create. Try again later");
			e.printStackTrace();
		}
	}
	
	public void deletePhone() {
		try {
			getPhoneFacade().deletePhone(phone);
			closeDialog();
			displayInfoMessageToUser("Deleted With Sucess");
			loadPhoneModel();
			resetPhone();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Ops, we could not create. Try again later");
			e.printStackTrace();
		}
	}
	
	public LazyDataModel<Phone> getPhoneModel() {
		System.out.println("====getPhoneModel called====");
		System.out.println("====phoneModel====" + phoneModel);
		if (phoneModel == null) {
			loadPhoneModel();
		}
		
		return phoneModel;
	}
	
	public void setPhoneModel(LazyDataModel<Phone> phoneModel) {
		this.phoneModel = phoneModel;
	}
	
	private void loadPhoneModel() {
		phoneModel = new LazyPhoneDataModel();
	}
	
	public void resetPhone() {
		phone = new Phone();
	}
	
}
