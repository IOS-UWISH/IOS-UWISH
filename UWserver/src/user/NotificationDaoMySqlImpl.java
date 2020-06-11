package user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import main.ServiceLocator;

public class NotificationDaoMySqlImpl implements NotificationDao {
	DataSource dataSource;

	public NotificationDaoMySqlImpl() {
		dataSource = ServiceLocator.getInstance().getDataSource();
	}
	
	
	
	@Override
	public int insert(Notification notification) {
		int count = 0;
		String sql ="INSERT INTO Notification" + 
				"(Type, Reason, Next_page, User_id) " + 
				"VALUES(?, ?, ?, ?);";
		try(Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);){
			ps.setInt(1, notification.getType());
			ps.setString(2, notification.getReason());
			ps.setBoolean(3, notification.isNext_page());
			ps.setInt(4, notification.getUser_id());
			
			count = ps.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public List<Notification> findByUserId(int user_id) {
		
		String sql = "SELECT UUID, Type, Reason, Next_page " 
				+ "FROM Notification WHERE User_id = ?;";
		List<Notification> notificationsList = new ArrayList<Notification>();
		try (Connection connection = dataSource.getConnection();
			PreparedStatement ps = connection.prepareStatement(sql);){
			ps.setInt(1, user_id);
			ResultSet rs =ps.executeQuery();
			while (rs.next()) {
				int uuid = rs.getInt(1);
				int type =rs.getInt(2);
				String reason = rs.getString(3);
				boolean next_page =rs.getBoolean(4);
			
				Notification notification = new Notification(uuid, type, reason, next_page,user_id);
				notificationsList.add(notification);
				
				}
			return notificationsList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return notificationsList;
		
	}
		
	

	@Override
	public List<Notification> getAll() {
		
		String sql = "SELECT UUID, Type, Reason, Next_page, User_id " 
				+ "FROM Notification ORDER BY Time DESC;";
		List<Notification> notificationsList = new ArrayList<Notification>();
		try (Connection connection = dataSource.getConnection();
			PreparedStatement ps = connection.prepareStatement(sql);){
			ResultSet rs =ps.executeQuery();
			while (rs.next()) {
				int uuid =rs.getInt(1);
				int type =rs.getInt(2);
				String reason = rs.getString(3);
				boolean next_page =rs.getBoolean(4);
				int user_id = rs.getInt(5);
				
				Notification notification = new Notification(uuid, type, reason, next_page,user_id);
				notificationsList.add(notification);
				}
			return notificationsList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return notificationsList;
		
	}

}
