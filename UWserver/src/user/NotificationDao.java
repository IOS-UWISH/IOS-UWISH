package user;

import java.util.List;

public interface NotificationDao {
	
	int insert(Notification notification);

	List<Notification> findByUserId(int user_id);

	List<Notification> getAll();

	

}
