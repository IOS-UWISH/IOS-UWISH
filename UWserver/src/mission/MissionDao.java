package mission;

import java.util.List;

import user.User;

public interface MissionDao {

	List<Mission> getAll();

	byte[] getPhoto(int userId);

	int insertTaskReport(Mission mission);// 任務檢舉

	int insertCommentReport(Mission mission);// 留言檢舉

	int insertComment(Mission mission);// user輸入留言

	List<Mission> getAllms(int taskId);//取得所有留言
	
	Mission getCurrentPoints(String email);//取得目前點數的物件
	
	List<Mission> getPointsRecord(String email);//取得點數紀錄的物件

	// -----------------------------------
		
	int insert(Mission mission, byte[] image1, byte[] image2);
	
	int getPoints(int userId);
	
	int pointChanges(Mission mission, String request);

	Mission findById(int id);
	
	int checkTaken (int taskId, int userId);
	
	String getPhonePost (int taskId);
	
	User whoTakes(int taskId);
	
	int takeMission(int taskId, int userId);

	List<Mission> getMissionTaken(int userId); // 接案紀錄
	
	List<Mission> getMissionPosted(int userId); // 發案紀錄
	
	List<Applicant> checkApply(int taskId);

	byte[] getPhotoFromPost(int taskId); // 傳入案件id尋找發案者頭像
	
	public byte[] getPhotoFromUser(int userId); // 傳入使用者id尋找使用者頭像
	
	byte[] getImageFromTask(int taskId, String search); // 接案紀錄詳細頁面，request選擇要查詢Image_1或是Image_2
	
	List<byte[]> getImages(int taskId);  // 這個不用了，暫時先放著
	
	int changeStatusApply(int taskId, int userId, int toStatus);
	
	int changeStatusPost(Mission mission, int toStatus);
	
	int starForApply(int stars, int taskId, int userIdApply);
	
	int starForPost(int stars, int taskId, int userIdApply, int userIdPost);
	
	int updateStarApply(int userIdApply);
	
	public int checkLevel(int userIdApply);
	
	public int sendNotification(int userId, int type, String reason, int nextPage);
	
	int updateStarPost(int userIdPost);
	
	List<Record> getStars(int userId);
	
	//0528新增項目：
	List<Mission> getMissionReport();
	int updateReportStatus(int reportId,int reportStatus,boolean isMission,int userId);
	List<Mission> getCommentReport();




}
