package kg.freesms.controllers.data;

import java.util.List;
import java.util.Map;

import kg.freesms.dao.UserDAO;
import kg.freesms.entity.User;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

public class LazyUserDataModel extends LazyDataModel<User> {
	
	private static final long serialVersionUID = 2998698313366877524L;
	
	private List<User> userDataSource;
	private UserDAO userDAO;
	
	@Override
    public User getRowData(String rowKey) {
        for(User user : userDataSource) {
            if(user.getId().equals(rowKey))
                return user;
        }
        
        return null;
    }
	
	@Override
    public Object getRowKey(User user) {
        return user.getId();
    }
	
	@Override
	public List<User> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
		
		userDAO = new UserDAO();
		
		userDAO.beginTransaction();
		userDataSource = userDAO.findAll();
		userDAO.closeTransaction();
		System.out.println("====userDataSource.size()====" + userDataSource.size());
		
		if(getRowCount() <= 0) {
			userDAO.beginTransaction();
			int size = userDataSource.size();
			userDAO.closeTransaction();
			setRowCount( size );
		}
        setPageSize(pageSize);
		
		return userDataSource;
	}
	
	@Override
	public void setRowIndex(int rowIndex) {
		if (rowIndex == -1 || getPageSize() == 0) {
            super.setRowIndex(-1);
        }
        else
            super.setRowIndex(rowIndex % getPageSize());
	}
	
	public List<User> getUserDataSource() {
		return userDataSource;
	}
	
	public void setUserDataSource(List<User> userDataSource) {
		this.userDataSource = userDataSource;
	}
	
}
