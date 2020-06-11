package user;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import main.ServiceLocator;



public class UserDaoMySqlImpl implements UserDao {
	
	DataSource dataSource;
	
public UserDaoMySqlImpl() {
		
		dataSource = ServiceLocator.getInstance().getDataSource();
		
	}

@Override
public int insert(User user, byte[] image) {
	int count = 0;
	String sql ="INSERT INTO UserInfo" + 
			"(Name, Nickname, Email, Phone, Password, Photo) " + 
			"VALUES(?, ?, ?, ?, ?, ?);";
	try(Connection connection = dataSource.getConnection();
			PreparedStatement ps = connection.prepareStatement(sql);){
		ps.setString(1, user.getName());
		ps.setString(2, user.getNickname());
		ps.setString(3, user.getEmail());
		ps.setString(4, user.getPhoneNo());
		ps.setString(5, user.getPassword());
		ps.setBytes(6, image);
		
		count = ps.executeUpdate();
	}catch (SQLException e) {
		e.printStackTrace();
	}
	return count;
}

@Override
public int update(User user, byte[] image) {
	// TODO Auto-generated method stub
	int count = 0;
	String sql = "";
	if (image != null) {
		sql = "UPDATE UserInfo SET Nickname = ?, Phone = ?, "
				+ "Password = ?, Photo = ? WHERE UUID = ?;";
	} else {
		sql = "UPDATE UserInfo SET Nickname = ?, Phone = ?, "
				+ "Password = ? WHERE UUID = ?;";
	}
	try(Connection connection = dataSource.getConnection();
			PreparedStatement ps = connection.prepareStatement(sql);){
		ps.setString(1,user.getNickname());
		ps.setString(2,user.getPhoneNo());
		ps.setString(3,user.getPassword());
		if (image != null) {
			ps.setBytes(4, image);
			ps.setInt(5, user.getUuid());
		} else {
			ps.setInt(4, user.getUuid());
		}
		count = ps.executeUpdate();
	}catch (SQLException e) {
		e.printStackTrace();
	}
	return count;
}


@Override
public int delete(int uuid) {
	// TODO Auto-generated method stub
	return 0;
}

@Override
public User findById(int uuid) {
	// TODO Auto-generated method stub
	String sql = "SELECT Name, Nickname, Email, Phone, Password, Star_apply, Star_post, Level, Points, Status, Register_time " 
			+ "FROM UserInfo WHERE UUID = ?;";
	    User user = null;
	try (Connection connection = dataSource.getConnection();
		PreparedStatement ps = connection.prepareStatement(sql);){
		ps.setInt(1, uuid);
		ResultSet rs =ps.executeQuery();
		if (rs.next()) {
			String name = rs.getString(1);
			String nickname = rs.getString(2);
			String email = rs.getString(3);
			String phoneNo = rs.getString(4);
			String password = rs.getString(5);
			float star_apply = rs.getFloat(6);
			float star_post	= rs.getFloat(7);
			int level = rs.getInt(8);
			int points = rs.getInt(9);
			boolean status = rs.getBoolean(10);
			String register_time = rs.getString(11);
			user = new User(uuid, name, nickname, email, phoneNo, password, star_apply, star_post, level, points, status, register_time);
			
			}
		
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return user;
	
}

@Override
public List<User> getAll() {
	// TODO Auto-generated method stub
	String sql = "SELECT UUID, Name, Nickname, Email, Phone, Password, Star_apply, Star_post, Level, Points, Status, Register_time " 
			+ "FROM UserInfo ORDER BY Update_time DESC;";
	List<User> userList = new ArrayList<User>();
	try (Connection connection = dataSource.getConnection();
		PreparedStatement ps = connection.prepareStatement(sql);){
		ResultSet rs =ps.executeQuery();
		while (rs.next()) {
			int uuid =rs.getInt(1);
			String name = rs.getString(2);
			String nickname = rs.getString(3);
			String email = rs.getString(4);
			String phoneNo = rs.getString(5);
			String password = rs.getString(6);
			float star_apply = rs.getFloat(7);
			float star_post	= rs.getFloat(8);
			int level = rs.getInt(9);
			int points = rs.getInt(10);
			boolean status = rs.getBoolean(11);
			String register_time = rs.getString(12);
			User user = new User (uuid,  name,  nickname, email,  phoneNo, password, star_apply, star_post,  level,  points, status, register_time);
			userList.add(user);
			}
		return userList;
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return userList;
	
}


public byte[] getImage(int uuid) {
	String sql = "SELECT Photo FROM UserInfo WHERE UUID = ?;";
	byte[] image = null;
	try (Connection connection = dataSource.getConnection();
			PreparedStatement ps = connection.prepareStatement(sql);) {
		ps.setInt(1, uuid);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			image = rs.getBytes(1);
		}
	} catch (SQLException e) {
		e.printStackTrace();
	} 
	return image;
}


@Override
public int userLogin(String account, String password) {
	int uuid = 0;
	String sql = "SELECT UUID FROM UserInfo WHERE Email = ? AND password = ?;";
	try (Connection connection = dataSource.getConnection();
			PreparedStatement ps = connection.prepareStatement(sql);) {
		ps.setString(1, account);
		ps.setString(2, password);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			uuid = rs.getInt(1);
		}
		
	} catch (SQLException e) {
		e.printStackTrace();
	}
	
	return uuid;
}

public int userUpdatePoint(int uuid,int point) {
	int count = 0;	
	String sql = "UPDATE UserInfo SET Points = ? WHERE UUID = ?;";
	
	try (Connection connection = dataSource.getConnection();
		PreparedStatement ps = connection.prepareStatement(sql);){
		ps.setInt(1, point);
		ps.setInt(2, uuid);
		count = ps.executeUpdate();		
	}catch(SQLException e) {
		e.printStackTrace();
	}
	return count;
	
}

//0527新加入的
@Override
public List<User> getAllUser() {
	List<User> userList = new ArrayList<User>();
	String sql = "SELECT UUID,Name,Status FROM UserInfo;";
	try (Connection connection = dataSource.getConnection();
			PreparedStatement ps = connection.prepareStatement(sql);){
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			int uuid = rs.getInt(1);
			String name = rs.getString(2);
			boolean status = rs.getBoolean(3);
			User user = new User(uuid,name,status);
			userList.add(user);			
		}
		return userList;
	}catch(SQLException e) {
		e.printStackTrace();
	}
	return userList;
}

@Override
public int updateStatus(User user) {
	int count = 0;
	boolean status = user.isStatus();	
	String sql = "";
	if(status == true) {
		sql = "UPDATE UserInfo SET Status = 0 WHERE UUID = ?;";
	}else {
		sql = "UPDATE UserInfo SET Status = 1 WHERE UUID = ?;";
	}
	try (Connection connection = dataSource.getConnection();
			PreparedStatement ps = connection.prepareStatement(sql);){
		ps.setInt(1, user.getUuid());
		count = ps.executeUpdate();		
	}catch(SQLException e) {
		e.printStackTrace();
	}
	return count;
}

//@Override
//public User getLogin(String email,String password) {
//	return null;
//}

}
