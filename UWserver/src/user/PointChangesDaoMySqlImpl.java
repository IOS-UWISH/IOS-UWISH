package user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import main.ServiceLocator;

public class PointChangesDaoMySqlImpl implements PointChangesDao {
	
	DataSource dataSource;
	
public PointChangesDaoMySqlImpl() {
		
		dataSource = ServiceLocator.getInstance().getDataSource();
}
		
	
	@Override
	public int insert(PointChanges pointChanges) {
		int count = 0;
		String sql ="INSERT INTO PointChanges" + 
				"(User_id, Reason, Add_or_subtract) " + 
				"VALUES(?, ?, ?);";
		try(Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);){
			ps.setInt(1, pointChanges.getUser_id());
			ps.setString(2, pointChanges.getReason());
			ps.setInt(3, pointChanges.getAdd_or_subtract());
			
			
			count = ps.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}


	@Override
	public List<PointChanges> getAll() {
		String sql = "SELECT UUID, User_id, Reason, Add_or_subtract " 
				+ "FROM PointChanges ORDER BY Update_time DESC;";
		List<PointChanges> pointChangesList = new ArrayList<PointChanges>();
		try (Connection connection = dataSource.getConnection();
			PreparedStatement ps = connection.prepareStatement(sql);){
			ResultSet rs =ps.executeQuery();
			while (rs.next()) {
				int uuid =rs.getInt(1);
				int user_id =rs.getInt(2);
				String reason = rs.getString(3);
				int add_or_subtract =rs.getInt(4);
				
				PointChanges pointChanges = new PointChanges(uuid, user_id, reason, add_or_subtract);
				pointChangesList.add(pointChanges);
				}
			return pointChangesList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pointChangesList;
		
	}

}
