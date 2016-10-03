package kg.freesms.controllers.data;

import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import kg.freesms.dao.GroupDAO;
import kg.freesms.entity.Group;
import kg.freesms.entity.User;

public class LazyGroupDataModel extends LazyDataModel<Group> {
	
	private static final long serialVersionUID = 7015086129936322160L;
	
	private List<Group> groupDataSource;
	private GroupDAO groupDAO;
	
	@Override
    public Group getRowData(String rowKey) {
        for(Group group : groupDataSource) {
            if(group.getId().equals(rowKey))
                return group;
        }
        
        return null;
    }
	
	@Override
    public Object getRowKey(Group group) {
        return group.getId();
    }

	@Override
	public List<Group> load(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, String> filters) {
		
		User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
		
		groupDAO = new GroupDAO();
		
		return super.load(first, pageSize, sortField, sortOrder, filters);
	}
	
}
