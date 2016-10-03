package kg.freesms.facade;

import java.io.Serializable;
import java.util.List;

import kg.freesms.dao.PhoneDAO;
import kg.freesms.entity.Phone;
import kg.freesms.entity.User;

public class PhoneFacade implements Serializable {
	
	private static final long serialVersionUID = 1664088051585074390L;
	
	private PhoneDAO phoneDAO = new PhoneDAO();

	public void createPhone(Phone phone) {
		phoneDAO.beginTransaction();
		phoneDAO.save(phone);
		phoneDAO.commitAndCloseTransaction();
	}

	public void updatePhone(Phone phone) {
		phoneDAO.beginTransaction();
		Phone persistedPhone = phoneDAO.find(phone.getId());
		persistedPhone.setPhoneNumber(phone.getPhoneNumber());
		phoneDAO.update(persistedPhone);
		phoneDAO.commitAndCloseTransaction();
	}

	public Phone findPhone(int phoneId) {
		phoneDAO.beginTransaction();
		Phone phone = phoneDAO.find(phoneId);
		phoneDAO.closeTransaction();
		return phone;
	}
	
	public List<Phone> findPhonesByUser(User user) {
		phoneDAO.beginTransaction();
		List<Phone> result = phoneDAO.findByUser(user);
		phoneDAO.closeTransaction();
		return result;
	}
	
//	public List<Phone> listAll() {
//		phoneDAO.beginTransaction();
//		List<Phone> result = phoneDAO.findAll();
//		phoneDAO.closeTransaction();
//		return result;
//	}
//	
	public void deletePhone(Phone phone) {
		phoneDAO.beginTransaction();
		Phone persistedPhone = phoneDAO.findReferenceOnly(phone.getId());
		phoneDAO.delete(persistedPhone);
		phoneDAO.commitAndCloseTransaction();
	}
	
}
