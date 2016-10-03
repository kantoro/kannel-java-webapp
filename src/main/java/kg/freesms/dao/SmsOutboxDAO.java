package kg.freesms.dao;
 
import kg.freesms.entity.SmsOutbox;
 
public class SmsOutboxDAO extends GenericDAO<SmsOutbox> {
	
	private static final long serialVersionUID = -3174716035612160010L;
	
	public SmsOutboxDAO() {
        super(SmsOutbox.class);
    }
 
	public void delete(SmsOutbox smsOutbox) {
    	super.delete(smsOutbox.getId(), SmsOutbox.class);
	}
}
