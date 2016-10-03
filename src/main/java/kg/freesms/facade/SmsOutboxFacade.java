package kg.freesms.facade;

import java.io.Serializable;
import java.util.List;

import kg.freesms.dao.SmsOutboxDAO;
import kg.freesms.entity.SmsOutbox;

public class SmsOutboxFacade implements Serializable {
	
	private static final long serialVersionUID = -4006645516170427400L;
	
	private SmsOutboxDAO smsOutboxDAO = new SmsOutboxDAO();

	public void createSmsOutbox(SmsOutbox smsOutbox) {
		smsOutboxDAO.beginTransaction();
		smsOutboxDAO.save(smsOutbox);
		smsOutboxDAO.commitAndCloseTransaction();
	}

	public void updateSmsOutbox(SmsOutbox smsOutbox) {
		smsOutboxDAO.beginTransaction();
		SmsOutbox persistedSmsOutbox = smsOutboxDAO.find(smsOutbox.getId());
		persistedSmsOutbox.setSmsContent(smsOutbox.getSmsContent());
		persistedSmsOutbox.setStatus(smsOutbox.getStatus());
		smsOutboxDAO.update(persistedSmsOutbox);
		smsOutboxDAO.commitAndCloseTransaction();
	}

	public SmsOutbox findSmsOutbox(int smsOutboxId) {
		smsOutboxDAO.beginTransaction();
		SmsOutbox smsOutbox = smsOutboxDAO.find(smsOutboxId);
		smsOutboxDAO.closeTransaction();
		return smsOutbox;
	}

	public List<SmsOutbox> listAll() {
		smsOutboxDAO.beginTransaction();
		List<SmsOutbox> result = smsOutboxDAO.findAll();
		smsOutboxDAO.closeTransaction();
		return result;
	}

	public void deleteSmsOutbox(SmsOutbox smsOutbox) {
		smsOutboxDAO.beginTransaction();
		SmsOutbox persistedSmsOutbox = smsOutboxDAO.findReferenceOnly(smsOutbox.getId());
		smsOutboxDAO.delete(persistedSmsOutbox);
		smsOutboxDAO.commitAndCloseTransaction();
	}
}
