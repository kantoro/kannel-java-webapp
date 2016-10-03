package kg.freesms.controllers.data;

import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import kg.freesms.dao.PhoneDAO;
import kg.freesms.entity.Phone;
import kg.freesms.entity.User;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

public class LazyPhoneDataModel extends LazyDataModel<Phone> {
	
	private static final long serialVersionUID = -7392470819416394256L;
	
	private List<Phone> phoneDataSource;
	private PhoneDAO phoneDAO;
	
	@Override
    public Phone getRowData(String rowKey) {
        for(Phone phone : phoneDataSource) {
            if(phone.getId().equals(rowKey))
                return phone;
        }
        
        return null;
    }
	
	@Override
    public Object getRowKey(Phone phone) {
        return phone.getId();
    }
	
	@Override
	public List<Phone> load(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, String> filters) {
		
		User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
		phoneDAO = new PhoneDAO();
		
		phoneDAO.beginTransaction();
		phoneDataSource = phoneDAO.loadLazilyByProperty(first, pageSize, "user", user);
		phoneDAO.closeTransaction();
		System.out.println("====phoneDataSource.size()====" + phoneDataSource.size());
		
		if(getRowCount() <= 0) {
			phoneDAO.beginTransaction();
			int size = phoneDAO.countByProperty("user", user).intValue();
			phoneDAO.closeTransaction();
			setRowCount( size );
		}
        setPageSize(pageSize);
		
		return phoneDataSource;
	}
	
	@Override
	public void setRowIndex(int rowIndex) {
		if (rowIndex == -1 || getPageSize() == 0) {
            super.setRowIndex(-1);
        }
        else
            super.setRowIndex(rowIndex % getPageSize());
	}
	
	public List<Phone> getPhoneDataSource() {
		return phoneDataSource;
	}
	
	public void setPhoneDataSource(List<Phone> phoneDataSource) {
		this.phoneDataSource = phoneDataSource;
	}
	
}
