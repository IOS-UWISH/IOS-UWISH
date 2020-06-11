package mission;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import main.ServiceLocator;
import user.User;

public class MissionDaoMySqlImpl implements MissionDao {
	DataSource dataSource;

	public MissionDaoMySqlImpl() {
		dataSource = ServiceLocator.getInstance().getDataSource();
	}

	@Override
	public List<Mission> getAll() {
		String sql = "SELECT t.UUID, Title, User_id_post, Nickname, Category, Due_date, City, District,Address, Latitude, Longitude, Budget, Detail, t.UUID as tid,"
				+ "(select count(Task_id) from Task_apply where Task_id = tid) as applycount,"
				+ "(select count(Task_id) from CommentInfo where Task_id = tid) as commentcount,"
				+ "t.Status FROM TaskInfo t " + "LEFT JOIN UserInfo u ON t.User_id_post = u.UUID "
				+ "LEFT JOIN CityInfo c ON t.District_id = c.UUID WHERE t.Status = 0;";

		List<Mission> missionList = new ArrayList<Mission>();

		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int taskId = rs.getInt(1);
				String title = rs.getString(2);
				int userId = rs.getInt(3);
				String userName = rs.getString(4);
				int category = rs.getInt(5);
				Date dueDate = rs.getDate(6);
				String city = rs.getString(7);
				String district = rs.getString(8);
				String address = rs.getString(9);
				double latitude = rs.getDouble(10);
				double longitude = rs.getDouble(11);
				int budget = rs.getInt(12);
				String detail = rs.getString(13);
				int tid = rs.getInt(14);
				int applyCount = rs.getInt(15);
				int messageCount = rs.getInt(16);
				int status = rs.getInt(17);
				Mission mission = new Mission(taskId, title, userId, userName, category, dueDate, city, district,
						address, latitude, longitude, budget, detail, tid, applyCount, messageCount, status);
				missionList.add(mission);
			}
			return missionList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return missionList;
	}

	@Override
	public byte[] getPhoto(int userId) {
		String sql = "SELECT u.Photo from UserInfo u left join TaskInfo t on u.UUID = t.User_id_post WHERE u.UUID = ?;";
		byte[] photo = null;
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				photo = rs.getBytes(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return photo;
	}

	@Override
	public int insertTaskReport(Mission mission) {
		int count = 0;
		String sql = "INSERT INTO TaskReport(Task_id,Reporter_id,Reason)" + "VALUES(?,?,?);";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, mission.getTaskId());
			ps.setInt(2, mission.getUserId());
			ps.setString(3, mission.getTaskReport());
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public int insertCommentReport(Mission mission) {
		int count = 0;
		String sql = "INSERT INTO CommentReport(Comment_id,Reporter_id,Reason)" + "VALUES(?,?,?);";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, mission.getCommentId());
			ps.setInt(2, mission.getUserId());
			ps.setInt(3, mission.getCommentReason());
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public int insertComment(Mission mission) {
		int count = 0;
		String sql = "INSERT INTO CommentInfo(User_id_comment,Detail,Task_id)" + "VALUES(?,?,?);";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, mission.getUserId());
			ps.setString(2, mission.getCommentDetail());
			ps.setInt(3, mission.getTaskId());
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public List<Mission> getAllms(int taskId) {
		String sql = "SELECT u.UUID, Nickname, c.UUID, c.Detail,Comment_time from CommentInfo c "
				+ "LEFT JOIN UserInfo u ON c.User_id_comment = u.UUID "
				+ "LEFT JOIN TaskInfo t ON c.User_id_comment = t.User_id_post WHERE c.Task_id = ? GROUP BY c.UUID ORDER BY Comment_time;";
		List<Mission> missions = new ArrayList<Mission>();
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, taskId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int userId = rs.getInt(1);
				String username = rs.getString(2);
				int commentId = rs.getInt(3);
				String message = rs.getString(4);
				String time = rs.getString(5);
				Mission mission = new Mission(userId, username, commentId, message, time);
				missions.add(mission);
			}
			return missions;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return missions;
	}

	@Override // 新增
	public Mission getCurrentPoints(String email) {
		String sql = "SELECT Points FROM UserInfo WHERE Email = ?;";
		Mission mission = null;
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				int points = rs.getInt(1);
				mission = new Mission(email, points);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return mission;
	}

	@Override // 新增
	public List<Mission> getPointsRecord(String email) {
		String sql = "SELECT p.Update_time,Reason,Add_or_subtract FROM PointChanges p "
				+ "JOIN UserInfo u ON p.User_id = u.UUID WHERE Email = ?;";
		List<Mission> missions = new ArrayList<Mission>();
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				String time = rs.getString(1);
//				Date time = rs.getDate(1);
				String reason = rs.getString(2);
				int add_or_subtract = rs.getInt(3);
				Mission mission = new Mission(time, reason, add_or_subtract);
				missions.add(mission);
			}
			return missions;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return missions;
	}

	// ---------------------------------------------

	@Override
	public int insert(Mission mission, byte[] image1, byte[] image2) {
		int count = 0, ptResult = 0;
		String sql = "INSERT INTO TaskInfo (Title, User_id_post, Category, Due_date, District_id, "
				+ "Address, Latitude, Longitude, Budget, Detail, Image_1, Image_2) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setString(1, mission.getTitle());
			ps.setInt(2, mission.getUserId());
			ps.setInt(3, mission.getCategory());
			ps.setDate(4, mission.getDueDate());
			ps.setInt(5, mission.getDistrictId());
			ps.setString(6, mission.getAddress());
			ps.setDouble(7, mission.getLatitude());
			ps.setDouble(8, mission.getLongitude());
			ps.setInt(9, mission.getBudget());
			ps.setString(10, mission.getDetail());
			ps.setBytes(11, image1);
			ps.setBytes(12, image2);
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (count != 0) {
			ptResult = pointChanges(mission, "post");
		}
		return ptResult;
	}

	@Override
	public int pointChanges(Mission mission, String request) { // 處理點數增減，更新使用者剩餘點數
		String reason = "";
		int count1 = 0, count2 = 0, ptResult = 0;
		int ptHave = getPoints(mission.getUserId()); // 取得原有點數
		int ptChange = (mission.getBudget() + 49) / 50; // 換算案件點數
		if (request.equals("post")) { // 如果需求為發案，扣除案件點數
			reason = "發案 - " + mission.showCategory(mission.getCategory());
			ptChange *= -1;
		} else if (request.equals("giveBack")) { // 如果需求為撤回，歸還案件點數
			reason = "案件撤回 - " + mission.showCategory(mission.getCategory());
		}
		// 點數增減紀錄表
		String sql1 = "INSERT INTO PointChanges (User_id, Reason, Add_or_subtract) VALUES (?, ?, ?);";
		// 更新使用者剩餘點數
		String sql2 = "UPDATE UserInfo SET Points = ? WHERE UUID = ?;";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps1 = connection.prepareStatement(sql1);
				PreparedStatement ps2 = connection.prepareStatement(sql2);) {
			ps1.setInt(1, mission.getUserId());
			ps1.setString(2, reason);
			ps1.setInt(3, ptChange);
			count1 = ps1.executeUpdate();

			ps2.setInt(1, ptHave + ptChange);
			ps2.setInt(2, mission.getUserId());
			count2 = ps2.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (count1 != 0 && count2 != 0) {
			ptResult = getPoints(mission.getUserId()); // 取得剩餘點數
		}
		return ptResult;
	}

	@Override
	public int getPoints(int userId) {
		String sql = "SELECT Points FROM UserInfo WHERE UUID = ?;";
		int points = 0;
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				points = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return points;
	}

	@Override
	public Mission findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int checkTaken(int taskId, int userId) { // 從接案者角度檢視自己是否曾申請該案件（return 0表示未曾申請）
		int count = 0;
		String sql = "SELECT COUNT(UUID) FROM Task_apply WHERE Task_id = ? AND User_id_apply = ?;";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, taskId);
			ps.setInt(2, userId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public String getPhonePost(int taskId) {
		String phone = "";
		String sql = "SELECT Phone FROM UserInfo u JOIN TaskInfo t ON t.User_id_post = u.UUID WHERE t.UUID = ?;";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, taskId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				phone = rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return phone;
	}

	@Override
	public User whoTakes(int taskId) { // 從發案者角度檢視誰已經接了這個案件
		User user = null;
		String sql = "SELECT ui.UUID, Nickname, Phone FROM Task_apply ta "
				+ "JOIN UserInfo ui ON ta.User_id_apply = ui.UUID " + "WHERE ta.Task_id = ? AND ta.Status != 5;";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, taskId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				int userId = rs.getInt(1);
				String name = rs.getString(2);
				String phone = rs.getString(3);
				user = new User(userId, name, phone);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public int takeMission(int taskId, int userId) {
		int count = 0;
		String sql = "INSERT INTO Task_apply (Task_id, User_id_apply) VALUES (?, ?);";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, taskId);
			ps.setInt(2, userId);
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public List<Mission> getMissionTaken(int userId) { // 接案紀錄，傳入接案者id
		String sql = "SELECT t.UUID, Title, User_id_post, Nickname, Category, Due_date, City, District, Address, Latitude, Longitude, Budget, Detail, "
				+ "(select count(Task_id) from CommentInfo where Task_id = t.UUID) as commentcount, "
				+ "(select count(Task_id) from Task_apply where Task_id = t.UUID) as applycount, "
				+ "a.Status FROM TaskInfo t " + "LEFT JOIN UserInfo u ON t.User_id_post = u.UUID "
				+ "LEFT JOIN CityInfo c ON t.District_id = c.UUID "
				+ "LEFT JOIN Task_apply a ON t.UUID = a.Task_id WHERE a.User_id_apply = ?;";
		List<Mission> missionTaken = new ArrayList<Mission>();
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int taskId = rs.getInt(1);
				String title = rs.getString(2);
				int userIdPost = rs.getInt(3);
				String userName = rs.getString(4);
				int category = rs.getInt(5);
				Date dueDate = rs.getDate(6);
				String city = rs.getString(7);
				String district = rs.getString(8);
				String address = rs.getString(9);
				double latitude = rs.getDouble(10);
				double longitude = rs.getDouble(11);
				int budget = rs.getInt(12);
				String detail = rs.getString(13);
				int commentCount = rs.getInt(14);
				int applyCount = rs.getInt(15);
				int status = rs.getInt(16);
				Mission mission = new Mission(taskId, title, userIdPost, userName, category, dueDate, city, district,
						address, latitude, longitude, budget, detail, commentCount, applyCount, status);
				missionTaken.add(mission);
			}
			return missionTaken;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return missionTaken;
	}

	@Override
	public List<Mission> getMissionPosted(int userId) {
		String sql = "SELECT t.UUID, Title, User_id_post, Nickname, Category, Due_date, City, District, Address, Latitude, Longitude, Budget, Detail, "
				+ "(select count(Task_id) from CommentInfo where Task_id = t.UUID) as commentcount, "
				+ "(select count(Task_id) from Task_apply where Task_id = t.UUID) as applycount, t.Status FROM TaskInfo t "
				+ "LEFT JOIN UserInfo u ON t.User_id_post = u.UUID " + "LEFT JOIN CityInfo c ON t.District_id = c.UUID "
				+ "WHERE User_id_post = ?;";
		List<Mission> missionPosted = new ArrayList<Mission>();
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int taskId = rs.getInt(1);
				String title = rs.getString(2);
				int userIdPost = rs.getInt(3);
				String userName = rs.getString(4);
				int category = rs.getInt(5);
				Date dueDate = rs.getDate(6);
				String city = rs.getString(7);
				String district = rs.getString(8);
				String address = rs.getString(9);
				double latitude = rs.getDouble(10);
				double longitude = rs.getDouble(11);
				int budget = rs.getInt(12);
				String detail = rs.getString(13);
				int commentCount = rs.getInt(14);
				int applyCount = rs.getInt(15);
				int status = rs.getInt(16);
				Mission mission = new Mission(taskId, title, userIdPost, userName, category, dueDate, city, district,
						address, latitude, longitude, budget, detail, commentCount, applyCount, status);
				missionPosted.add(mission);
			}
			return missionPosted;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return missionPosted;
	}

	@Override
	public List<Applicant> checkApply(int taskId) {
		String sql = "SELECT ui.UUID, Nickname, Level, Phone, Apply_time, ta.Status "
				+ "FROM Task_apply ta LEFT JOIN UserInfo ui ON ta.User_id_apply = ui.UUID " + "WHERE Task_id = ?;";
		List<Applicant> applicants = new ArrayList<Applicant>();
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, taskId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int userId = rs.getInt(1);
				String userName = rs.getString(2);
				int level = rs.getInt(3);
				String phone = rs.getString(4);
				Date applyDate = rs.getDate(5);
				Time applyTime = rs.getTime(5);
				int status = rs.getInt(6);
				Applicant applicant = new Applicant(userId, userName, level, phone, applyDate, applyTime, status);
				applicants.add(applicant);
			}
			return applicants;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return applicants;
	}

	@Override
	public byte[] getPhotoFromPost(int taskId) { // 傳入案件id尋找發案者頭像
		String sql = "SELECT Photo FROM TaskInfo ti " + "LEFT JOIN UserInfo ui ON ti.User_id_post = ui.UUID "
				+ "WHERE ti.UUID= ?;";
		byte[] photo = null;
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, taskId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				photo = rs.getBytes(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return photo;
	}

	@Override
	public byte[] getPhotoFromUser(int userId) { // 傳入使用者id尋找使用者頭像
		String sql = "SELECT Photo FROM UserInfo WHERE UUID= ?;";
		byte[] photo = null;
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				photo = rs.getBytes(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return photo;
	}

	@Override
	public byte[] getImageFromTask(int taskId, String search) {
		String sql = "";
		if (search.equals("Image_1")) {
			sql = "SELECT Image_1 FROM TaskInfo WHERE UUID= ?;";
		} else if (search.equals("Image_2")) {
			sql = "SELECT Image_2 FROM TaskInfo WHERE UUID= ?;";
		}
		byte[] image = null;
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, taskId);
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
	public List<byte[]> getImages(int taskId) { // 這個不用了，暫時先放著
		String sql = "SELECT image_1, image_2 FROM TaskInfo WHERE UUID = ?;";
		List<byte[]> images = new ArrayList<byte[]>();
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, taskId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				byte[] image1 = rs.getBytes(1);
				byte[] image2 = rs.getBytes(2);
				images.add(image1);
				images.add(image2);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return images;
	}

	@Override
	public int changeStatusApply(int taskId, int userId, int toStatus) {
		int count = 0, count2 = 0;
		String sql = "UPDATE Task_apply SET Status = ? WHERE Task_id = ? AND User_id_apply = ?;";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, toStatus);
			ps.setInt(2, taskId);
			ps.setInt(3, userId);
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (toStatus == 1) { // 如果有人被指派為接案者，將其他人設定為應徵失敗
			String sql2 = "UPDATE Task_apply SET Status = 5 WHERE Task_id = ? AND User_id_apply != ?;";
			try (Connection connection = dataSource.getConnection();
					PreparedStatement ps = connection.prepareStatement(sql2);) {
				ps.setInt(1, taskId);
				ps.setInt(2, userId);
				count2 = ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
//			sendNotification(userId, 1, "成功接案！請至接案紀錄查看詳情", 1);
		}
		return count + count2;
	}

	@Override
	public int changeStatusPost(Mission mission, int toStatus) {
		String sql = "UPDATE TaskInfo SET Status = ? WHERE UUID = ?;";
		int count = 0, ptResult = 0;
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, toStatus);
			ps.setInt(2, mission.getTaskId());
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (mission.getStatus() == 0 && toStatus == 4) {
			// 把點數還給userId
			pointChanges(mission, "giveBack");
		}
		return count;
	}

	@Override
	public int starForApply(int stars, int taskId, int userIdApply) {
		int count = 0;
		String sql = "UPDATE Task_apply SET Star_employee = ? WHERE Task_id = ? AND User_id_apply = ?;";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, stars);
			ps.setInt(2, taskId);
			ps.setInt(3, userIdApply);
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (count != 0) {
			updateStarApply(userIdApply);
		}
		return count;
	}

	@Override
	public int starForPost(int stars, int taskId, int userIdApply, int userIdPost) {
		int count = 0;
		String sql = "UPDATE Task_apply SET Star_employer = ? WHERE Task_id = ? AND User_id_apply = ?;";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, stars);
			ps.setInt(2, taskId);
			ps.setInt(3, userIdApply);
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (count != 0) {
			updateStarPost(userIdPost);
		}
		return count;
	}

	@Override
	public int updateStarApply(int userIdApply) {
		int count = 0;
		String sql = "UPDATE UserInfo SET Star_apply = (SELECT AVG(Star_employee) "
				+ "FROM Task_apply WHERE User_id_apply = ?) WHERE UUID = ?;";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, userIdApply);
			ps.setInt(2, userIdApply);
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		int newLevel = checkLevel(userIdApply);
		System.out.println("被傳回來的newLevel: " + newLevel);
		if (newLevel != 0) {
			System.out.println("必須通知！");
			sendNotification(userIdApply, 0, "恭喜升等！您已升至第"+ newLevel +"等", 0);
		}
		return count;
	}
	
	@Override
	public int checkLevel(int userIdApply) {
		System.out.println("checkLevel is called..");
		int newLevel = 0;
		int level = 0, star = 0;
		String sql = "SELECT Level, SUM(Star_employee) FROM UserInfo ui JOIN Task_apply ta "
				+ "ON ui.UUID = ta.User_id_apply WHERE ui.UUID = ?;";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, userIdApply);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				level = rs.getInt(1);
				star = rs.getInt(2);
				System.out.println("原本的level: " + level + "\nstar: " + star);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		int i = star / 100 + 1;
		System.out.println("應該要有的level: " + i);

		if (level != i) {
			System.out.println("必須升級！");
			String sqlChangeLv = "UPDATE UserInfo SET Level = ? WHERE UUID = ?;";
			try (Connection connection = dataSource.getConnection();
					PreparedStatement ps = connection.prepareStatement(sqlChangeLv);) {
				ps.setInt(1, i);
				ps.setInt(2, userIdApply);
				int count = ps.executeUpdate();
				System.out.println("升級資料的count: " + count);
				if (count != 0) newLevel = i;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return newLevel;  // 回傳0:等級沒變   其他:等級升至第幾級
	}
	
	@Override
	public int sendNotification(int userId, int type, String reason, int nextPage) {
		int count = 0;
		System.out.println("sendNotification is called...");
		String sql = "INSERT INTO Notification (User_id, Type, Reason, Next_page) VALUES(?, ?, ?, ?);";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, userId);
			ps.setInt(2, type);
			ps.setString(3, reason);
			ps.setInt(4, nextPage);
			count = ps.executeUpdate();
			System.out.println("count: " + count);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public int updateStarPost(int userIdPost) {
		int count = 0;
		String sql = "UPDATE UserInfo SET Star_post = "
				+ "(SELECT AVG(Star_employer) FROM Task_apply ta JOIN TaskInfo ti ON ta.Task_id = ti.UUID WHERE ti.User_id_post = ?) "
				+ "WHERE UUID = ?;";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, userIdPost);
			ps.setInt(2, userIdPost);
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public List<Record> getStars(int userId) {
		List<Record> records = new ArrayList<Record>();
		String sqlTake = "SELECT ti.UUID, ti.Title, ui.Nickname, ta.Star_employee, ta.Star_employer " + 
				"FROM Task_apply ta LEFT JOIN TaskInfo ti ON ta.Task_id = ti.UUID " + 
				"LEFT JOIN UserInfo ui ON ti.User_id_post = ui.UUID " + 
				"WHERE ta.User_id_apply = ? AND ti.Status = 3;";
		String sqlPost = "SELECT ti.UUID, ti.Title, ui.Nickname, ta.Star_employee, ta.Star_employer " + 
				"FROM Task_apply ta LEFT JOIN TaskInfo ti ON ta.Task_id = ti.UUID " + 
				"LEFT JOIN UserInfo ui ON ta.User_id_apply = ui.UUID " + 
				"WHERE ti.User_id_post = ? AND ta.Status = 3;";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement psTake = connection.prepareStatement(sqlTake);
				PreparedStatement psPost = connection.prepareStatement(sqlPost);) {
			psTake.setInt(1, userId);
			ResultSet rsTake = psTake.executeQuery();
			while(rsTake.next()) {
				int taskId = rsTake.getInt(1);
				String title = rsTake.getString(2);
				String name = rsTake.getString(3);
				int starTakeMission = rsTake.getInt(4);
				int starPostMission = rsTake.getInt(5);
				Record record = new Record(taskId, title, name, starTakeMission, starPostMission, true);
				records.add(record);
			}
			psPost.setInt(1, userId);
			ResultSet rsPost = psPost.executeQuery();
			while(rsPost.next()) {
				int taskId = rsPost.getInt(1);
				String title = rsPost.getString(2);
				String name = rsPost.getString(3);
				int starTakeMission = rsPost.getInt(4);
				int starPostMission = rsPost.getInt(5);
				Record record = new Record(taskId, title, name, starTakeMission, starPostMission, false);
				records.add(record);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return records;
	}

	//0528新增項目：
	@Override
	public List<Mission> getMissionReport() {
		List<Mission> missions = new ArrayList<Mission>();
		String sql = "select u.UUID as userId,u.Name,ti.Title,t.UUID AS taskReportId from TaskReport t " + 
				"left join TaskInfo ti on t.Task_id = ti.UUID " +  // "left join TaskInfo ti on t.Task_id = ti.UUID " + 
				"left join UserInfo u on ti.User_id_post = u.UUID " + 
				"WHERE t.Status = 0;";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);){
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int userId = rs.getInt(1);
				String userName = rs.getString(2);
				String title = rs.getString(3);
				int taskId = rs.getInt(4);
				Mission mission = new Mission(userId,userName,title,taskId);
				missions.add(mission);				
			}	
			return missions;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return missions;
	}
	
	@Override
	public List<Mission> getCommentReport() {
		List<Mission> missions = new ArrayList<Mission>();
		String sql = "select u.UUID as userId,u.Name,ci.Detail,c.UUID AS commentReportId from CommentReport c " + 
				"left join CommentInfo ci on c.Comment_id = ci.UUID " +    //  "left join CommentInfo ci on c.Comment_id = ci.UUID " +
				"left join UserInfo u on ci.User_id_comment = u.UUID " +   //  "left join UserInfo u on ci.User_id_comment = u.UUID " +
				"WHERE c.Status = 0;";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);){
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int userId = rs.getInt(1);
				String userName = rs.getString(2);
				String title = rs.getString(3);
				int taskId = rs.getInt(4);
				Mission mission = new Mission(taskId,userId,userName,title);
				missions.add(mission);				
			}
			return missions;
		}catch(SQLException e) {
			e.printStackTrace();
		}	
		return missions;
	}

	@Override
	public int updateReportStatus(int reportId, int reportStatus ,boolean isMission,int userId) { //ci.User_id_comment-留言的人;ti.User_id_post-發案的人
		int count = 0;
		String sql = "";
		if(isMission == true) {
			 sql = "update TaskReport set Status = ? where UUID = ?;";
		}else {
			 sql = "update CommentReport set Status = ? where UUID = ?;";
		}
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);){
			ps.setInt(1,reportStatus );
			ps.setInt(2,reportId);
			count = ps.executeUpdate();			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		if (reportStatus == 3) {
			inValidUser(userId);
		}
	
		return count;
	}
	
	public int inValidUser(int userId) {
		int count = 0;
		String sql = "UPDATE UserInfo SET Status = 0 WHERE UUID = ?;";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);){
			ps.setInt(1,userId );
			count = ps.executeUpdate();			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	

}
