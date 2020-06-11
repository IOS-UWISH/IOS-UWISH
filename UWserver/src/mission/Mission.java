package mission;

import java.sql.Date;

public class Mission {
	//0528新增項目：
    private int reportStatus;//檢舉狀態：0：未處理、1：忽略、2：下架、3：停權
    private int reportId;//檢舉id
    private boolean isMission;//0:任務、1:留言

	
    //
	
	// 需要新增的屬性：
	private int tid;// MySql參照用的id
	private int messageCount;// 留言筆數
	private int applyCount;// 應徵人數

	private String taskReport;// 檢舉任務的原因

	private int CommentId;// 留言的UUID
	private int CommentReason;// 檢舉留言的選項(0：罵人、說髒話、1：恐嚇、言語暴力、2：色情、騷擾、3：張貼廣告訊息、4：其他)

	private String CommentDetail;// user的留言
	private String time;// 留言的時間
	
	//5/13新增的項目： sorry，有點亂...
	private int currentPoints;//目前點數
	private String email;//登入帳號
	private int add_or_subtract;//點數增減
	private String reason;//購買 or 發案
	

	// 下列為原本有的屬性：
	private int taskId;
	private String title;
	private int userId; // 發文者ID
	private String userName; // 發文者暱稱
	private int category; // 0買東西 1遛狗 2清潔 3搬家 4快遞 5煮飯
	private Date dueDate; // 任務時間 = 刊登截止日（預設）
	private String city, district; // 縣市、區域
	private int districtId;
	private String address; // 詳細地址（地點）
	private double latitude, longitude;
	private int budget; // 發案金額
	private String detail;
	private int status; // 0待指派 1已成立 2進行中 3已完成 4未完成 5應徵失敗
	
	//0528新增項目：
    public  Mission(int userId,String userName,String title,int reportId){ //任務檢舉狀態的變更
        this.userId = userId;
        this.userName = userName;
        this.title = title;
        this.reportId = reportId;
    }
    
    public  Mission(int reportId,int reportStatus,boolean isMission){ 
        this.reportId = reportId;
        this.reportStatus = reportStatus;
        this.isMission = isMission;
    }
    
    public Mission(int reportId,int userId,String userName,String CommentDetail) { //留言檢舉狀態的變更
    	this.reportId = reportId;
    	this.userId = userId;
    	this.userName = userName;
    	this.CommentDetail = CommentDetail;    	
    }
    
  
    
    
    //

	// 已更新的getAll，有新增tid,applyCount,messageCount;servlet資料也有更新
	public Mission(int taskId, String title, int userId, String userName, int category, Date dueDate, String city,
			String district, String address, double latitude, double longitude, int budget, String detail, int tid,
			int applyCount, int messageCount, int status) {
		this.taskId = taskId;
		this.title = title;
		this.userId = userId;
		this.userName = userName;
		this.category = category;
		this.dueDate = dueDate;
		this.city = city;
		this.district = district;
		this.address = address;
		this.latitude = latitude;
		this.longitude = longitude;
		this.budget = budget;
		this.detail = detail;
		this.status = status;
		this.messageCount = messageCount;
		this.applyCount = applyCount;
		this.tid = tid;
	}

	// 接案紀錄用的
	public Mission(int taskId, String title, int userId, String userName, int category, Date dueDate, 
			String city, String district, String address, double latitude, double longitude, int budget, 
			String detail, int messageCount, int applyCount, int status) {
		this.taskId = taskId;
		this.title = title;
		this.userId = userId;
		this.userName = userName;
		this.category = category;
		this.dueDate = dueDate;
		this.city = city;
		this.district = district;
		this.address = address;
		this.latitude = latitude;
		this.longitude = longitude;
		this.budget = budget;
		this.detail = detail;
		this.messageCount = messageCount;
		this.applyCount = applyCount;
		this.status = status;
	}

	public Mission(int taskId, int userId, String taskReport) { // 檢舉任務用的物件;Q.這邊是拿取偏好設定的user_id,應該不會發生衝突吧?！
		this.taskId = taskId;
		this.userId = userId;
		this.taskReport = taskReport;
	}

	public Mission(int CommentId, int userId, int CommentReason) { // 檢舉留言用的物件;Q.這邊是拿取偏好設定的user_id,應該不會發生衝突吧?！
		this.CommentId = CommentId;
		this.userId = userId;
		this.CommentReason = CommentReason;
	}

	public Mission(int userId, String commentDetail, int taskId) { // 送留言用的物件;
		this.taskId = taskId;
		this.userId = userId;
		this.CommentDetail = commentDetail;
	}

	public Mission(int userId, String userName, int commentId, String CommentDetail, String time) { // 取留言用的物件;
		this.userName = userName;
		this.CommentDetail = CommentDetail;
		this.CommentId = commentId;
		this.time = time;
		this.userId = userId;
	}
	
	public  Mission(String email,int currentPoints){ //取得目前點數的物件;用email帳號當查詢條件
        this.email = email;
        this.currentPoints =currentPoints;
    }

	public Mission(String time,String reason,int add_or_subtract){ //取得點數紀錄的物件
        this.time = time;
        this.reason = reason;
        this.add_or_subtract = add_or_subtract;
    }

	public static String showStatus(int i) {
		String status = "";
		switch (i) {
		case 0: status = "待指派"; break;
		case 1: status = "已成立"; break;
		case 2: status = "進行中"; break;
		case 3: status = "已完成"; break;
		case 4: status = "未完成"; break;
		case 5: status = "應徵失敗"; break;
		}
		return status;
	}

	public static String showCategory(int i) {
		String type = "";
		switch (i) {
		case 0: type = "買東西"; break;
		case 1: type = "遛狗"; break;
		case 2: type = "清潔"; break;
		case 3:	type = "搬家"; break;
		case 4: type = "快遞"; break;
		case 5: type = "煮飯"; break;
		}
		return type;
	}

	public int getTid() {
		return tid;
	}

	public void setTid(int tid) {
		this.tid = tid;
	}

	public int getApplyCount() {
		return applyCount;
	}

	public void setApplyCount(int applyCount) {
		this.applyCount = applyCount;
	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}
	
    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public int getBudget() {
		return budget;
	}

	public void setBudget(int budget) {
		this.budget = budget;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getMessageCount() {
		return messageCount;
	}

	public void setMessageCount(int messageCount) {
		this.messageCount = messageCount;
	}

	public String getTaskReport() {
		return taskReport;
	}

	public void setTaskReport(String taskReport) {
		this.taskReport = taskReport;
	}

	public int getCommentId() {
		return CommentId;
	}

	public void setCommentId(int commentId) {
		CommentId = commentId;
	}

	public int getCommentReason() {
		return CommentReason;
	}

	public void setCommentReason(int commentReason) {
		CommentReason = commentReason;
	}

	public String getCommentDetail() {
		return CommentDetail;
	}

	public void setCommentDetail(String commentDetail) {
		CommentDetail = commentDetail;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getCurrentPoints() {
		return currentPoints;
	}

	public void setCurrentPoints(int currentPoints) {
		this.currentPoints = currentPoints;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getAdd_or_subtract() {
		return add_or_subtract;
	}

	public void setAdd_or_subtract(int add_or_subtract) {
		this.add_or_subtract = add_or_subtract;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public int getReportStatus() {
		return reportStatus;
	}

	public void setReportStatus(int reportStatus) {
		this.reportStatus = reportStatus;
	}

	public int getReportId() {
		return reportId;
	}

	public void setReportId(int reportId) {
		this.reportId = reportId;
	}


	
	
}
