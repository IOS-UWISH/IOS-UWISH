package user;
import java.util.List;


public interface UserDao {
	int insert(User user,byte[] image);

	int update(User user, byte[] image);

	int delete(int uuid);

	User findById(int uuid);

	List<User> getAll();

	byte[] getImage(int uuid);
	
	int userLogin (String account, String password);
	
	int userUpdatePoint(int uuid,int point);

//	User getLogin(String email,String password);
	
/*-----------------------------------------------------------*/	
//0527新加入的
	List<User> getAllUser();
	int updateStatus(User user);
	
	

}
