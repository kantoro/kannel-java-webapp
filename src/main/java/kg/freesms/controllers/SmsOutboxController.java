package kg.freesms.controllers;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import kg.freesms.entity.SmsOutbox;
import kg.freesms.facade.SmsOutboxFacade;

@ViewScoped
@ManagedBean
public class SmsOutboxController extends AbstractController implements Serializable {
	
	private static final long serialVersionUID = -4375814824521184688L;
	
	private SmsOutbox smsOutbox;
	private List<SmsOutbox> smsOutboxes;
	private SmsOutboxFacade smsOutboxFacade;

	public SmsOutboxFacade getSmsOutboxFacade() {
		if (smsOutboxFacade == null) {
			smsOutboxFacade = new SmsOutboxFacade();
		}

		return smsOutboxFacade;
	}

	public SmsOutbox getSmsOutbox() {
		if (smsOutbox == null) {
			smsOutbox = new SmsOutbox();
		}

		return smsOutbox;
	}

	public void setSmsOutbox(SmsOutbox smsOutbox) {
		this.smsOutbox = smsOutbox;
	}

	public void createSmsOutbox() {
		try {
			getSmsOutboxFacade().createSmsOutbox(smsOutbox);
			closeDialog();
			displayInfoMessageToUser("Created With Sucess");
			//loadSmsOutboxs();
			resetSmsOutbox();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Ops, we could not create. Try again later");
			e.printStackTrace();
		}
	}

	public void updateSmsOutbox() {
		try {
			getSmsOutboxFacade().updateSmsOutbox(smsOutbox);
			closeDialog();
			displayInfoMessageToUser("Updated With Sucess");
			//loadSmsOutboxs();
			resetSmsOutbox();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Ops, we could not create. Try again later");
			e.printStackTrace();
		}
	}

	public void deleteSmsOutbox() {
		try {
			getSmsOutboxFacade().deleteSmsOutbox(smsOutbox);
			closeDialog();
			displayInfoMessageToUser("Deleted With Sucess");
			//loadSmsOutboxs();
			resetSmsOutbox();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Ops, we could not create. Try again later");
			e.printStackTrace();
		}
	}

	public List<SmsOutbox> getAllSmsOutboxs() {
		if (smsOutboxes == null) {
			loadSmsOutboxs();
		}

		return smsOutboxes;
	}

	private void loadSmsOutboxs() {
		smsOutboxes = getSmsOutboxFacade().listAll();
	}

	public void resetSmsOutbox() {
		smsOutbox = new SmsOutbox();
	}
}
