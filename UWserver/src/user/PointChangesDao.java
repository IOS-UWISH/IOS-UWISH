package user;

import java.util.List;

public interface PointChangesDao {
	
	int insert(PointChanges pointChanges);

	List<PointChanges> getAll();
	

}
