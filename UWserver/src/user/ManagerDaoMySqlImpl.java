package user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import main.ServiceLocator;

public class ManagerDaoMySqlImpl implements ManagerDao{

	DataSource dataSource;
	
	public ManagerDaoMySqlImpl() {
			
			dataSource = ServiceLocator.getInstance().getDataSource();
			
		}
	
	
	
	@Override
	public int insert(Manager manager) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(Manager manager) {
		int count = 0;
		String sql = "";
		sql = "UPDATE ManagerInfo SET Password = ? "+ "WHERE UUID = ?;";
		
		try(Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);){
			ps.setString(1,manager.getPassword());
			ps.setString(2, manager.getUuid());
			
			count = ps.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public Manager findById(String uuid) {
		String sql = "SELECT Password " 
				+ "FROM ManagerInfo WHERE UUID = ?;";
		    Manager manager = null;
		try (Connection connection = dataSource.getConnection();
			PreparedStatement ps = connection.prepareStatement(sql);){
			ps.setString(1, uuid);
			ResultSet rs =ps.executeQuery();
			if (rs.next()) {
				String password = rs.getString(1);
				manager = new Manager(uuid, password);
				
				}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return manager;
	}

	@Override
	public List<Manager> getAll() {
		String sql = "SELECT UUID, Password " 
				+ "FROM ManagerInfo ORDER BY Update_time DESC;";
		List<Manager> managerList= new ArrayList<Manager>();
		try (Connection connection = dataSource.getConnection();
			PreparedStatement ps = connection.prepareStatement(sql);){
			ResultSet rs =ps.executeQuery();
			while (rs.next()) {
				String uuid =rs.getString(1);
				String password =rs.getString(2);
				
				
				Manager manager = new Manager(uuid, password);
				managerList.add(manager);
				}
			return managerList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return managerList;
	}

	
	@Override
	public int userLogin(String account, String password) {
		int uuid = 0;
		String sql = "SELECT UUID FROM ManagerInfo WHERE password = ?;";
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

}
