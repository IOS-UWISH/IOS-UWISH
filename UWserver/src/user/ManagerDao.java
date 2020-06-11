package user;

import java.util.List;

public interface ManagerDao {
	
	
	int insert(Manager manager);

	int update(Manager manager);

	Manager findById(String uuid);

	List<Manager> getAll();

	int userLogin (String uuid, String password);

}
