package mission;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Base64;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import main.ImageUtil;
import user.User;


@WebServlet("/MissionServlet")
public class MissionServlet extends HttpServlet {
	private final static String CONTENT_TYPE = "text/html; charset=utf-8";
    MissionDao missionDao = null;
 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		Gson gson = new Gson();
		BufferedReader br = request.getReader();
		StringBuilder jsonIn = new StringBuilder();
		String line = null;
		while ((line = br.readLine()) != null) {
			jsonIn.append(line);
		}
		System.out.println("input: " + jsonIn);

		JsonObject jsonObject = gson.fromJson(jsonIn.toString(), JsonObject.class);
		if (missionDao == null) {
			missionDao = new MissionDaoMySqlImpl();
		}
		
		String action = jsonObject.get("action").getAsString();
		
		if (action.equals("getAll")) {
            Gson gson2 = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			List<Mission> missions = missionDao.getAll();
			writeText(response, gson2.toJson(missions));
		} else if (action.equals("getPhoto")) {
			OutputStream os = response.getOutputStream();
			int userId = jsonObject.get("userId").getAsInt();
			int imageSize = jsonObject.get("imageSize").getAsInt();
			byte[] photo = missionDao.getPhoto(userId);
			if(photo != null) {
				photo = ImageUtil.shrink(photo,imageSize);
				response.setContentType("photo/jpeg");
				response.setContentLength(photo.length);
				os.write(photo);
			}
		}else if(action.equals("InsertTaskReport")) { //任務檢舉
			String missionJson = jsonObject.get("mission").getAsString();
			System.out.println("missionJson" + missionJson);
			Mission mission = gson.fromJson(missionJson, Mission.class);
			int count = 0;
			if(action.equals("InsertTaskReport")) {
				count = missionDao.insertTaskReport(mission);
			}
			writeText(response,String.valueOf(count));
		}else if(action.equals("InsertCommentReport")) { //留言檢舉
			String missionJson = jsonObject.get("mission").getAsString();
			System.out.println("missionJson" + missionJson);
			Mission mission = gson.fromJson(missionJson, Mission.class);
			int count = 0;
			if(action.equals("InsertCommentReport")) {
				count = missionDao.insertCommentReport(mission);
			}
			writeText(response,String.valueOf(count));			
		}else if(action.equals("InsertComment")) { //user留言
			String missionJson = jsonObject.get("mission").getAsString();
			System.out.println("missionJson" + missionJson);
			Mission mission = gson.fromJson(missionJson, Mission.class);
			int count = 0;
			if(action.equals("InsertComment")) {
				count = missionDao.insertComment(mission);
			}
			writeText(response,String.valueOf(count));			
		}else if(action.equals("getAllMessages")) { //取得留言
			int taskId = jsonObject.get("taskId").getAsInt();
			List<Mission> missions = missionDao.getAllms(taskId);
			writeText(response,gson.toJson(missions));	
		}else if(action.equals("getCurrentPoints")) { //取得目前點數;新增的！！！
			String email = jsonObject.get("email").getAsString();
			Mission mission = missionDao.getCurrentPoints(email);
			writeText(response,gson.toJson(mission));
		}else if(action.equals("getPointsRecord")) { //取得點數紀錄;新增的！！！
//            Gson gson2 = new GsonBuilder().setDateFormat("yyyy/MM/dd HH:mm:ss").create(); Q.目前暫時用String，未改良成Date?
			String email = jsonObject.get("email").getAsString();
			List<Mission> missions = missionDao.getPointsRecord(email);
			writeText(response,gson.toJson(missions));			
		}
//		else if(action.equals("updateReportStatus")) {
//			int reportId = jsonObject.get("reportId").getAsInt();
//			int reportStatus = jsonObject.get("reportStatus").getAsInt();
//			int count = missionDao.updateReportStatus(reportId,reportStatus);
//			writeText(response, String.valueOf(count));			
//		}
 
		// --------------------------------------
		
		else if (action.equals("insert")) { // 我要發案
			String missionJson = jsonObject.get("mission").getAsString();
			System.out.println("missionJson = " + missionJson);
            Gson gson2 = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			Mission mission = gson2.fromJson(missionJson, Mission.class);
			byte[] image1 = null, image2 = null;
			if (jsonObject.get("image1") != null) {
				String imageBase64_1 = jsonObject.get("image1").getAsString();
				if (imageBase64_1 != null && !imageBase64_1.isEmpty()) {
					image1 = Base64.getMimeDecoder().decode(imageBase64_1);
				}
			}
			if (jsonObject.get("image2") != null) {
				String imageBase64_2 = jsonObject.get("image2").getAsString();
				if (imageBase64_2 != null && !imageBase64_2.isEmpty()) {
					image2 = Base64.getMimeDecoder().decode(imageBase64_2);
				}
			}
			int ptResult = 0;
			ptResult = missionDao.insert(mission, image1, image2);
			writeText(response, String.valueOf(ptResult));
			
		} else if (action.equals("getPoints")) {  // 取得發案者剩餘點數
			int userId = jsonObject.get("userId").getAsInt();
			int points = missionDao.getPoints(userId);
			writeText(response, String.valueOf(points));
			
		} else if (action.equals("takeMission")) {  // 我要接案
			int taskId = jsonObject.get("taskId").getAsInt();
			int userId = jsonObject.get("userId").getAsInt();
			int count = missionDao.takeMission(taskId, userId);
			writeText(response, String.valueOf(count));

		} else if (action.equals("checkTaken")) {  // 確認使用者狀態是否已接案
			int taskId = jsonObject.get("taskId").getAsInt();
			int userId = jsonObject.get("userId").getAsInt();
			int count = missionDao.checkTaken(taskId, userId);
			writeText(response, String.valueOf(count));
			
		} else if (action.equals("getPhonePost")) {  // 取得發案者手機號碼
			int taskId = jsonObject.get("taskId").getAsInt();
			String phone = missionDao.getPhonePost(taskId);
			writeText(response, phone);
			
		} else if (action.equals("whoTakes")) {  // 從發案者角度，查看誰已被指定為接案者
			int taskId = jsonObject.get("taskId").getAsInt();
			User whoTakes = missionDao.whoTakes(taskId);
			writeText(response, gson.toJson(whoTakes));
			
		} else if (action.equals("getMissionTaken")) { // 接案紀錄
			int userId = jsonObject.get("userId").getAsInt();
			List<Mission> missionTaken = missionDao.getMissionTaken(userId);
            Gson gson2 = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			writeText(response, gson2.toJson(missionTaken));
			
		} else if (action.equals("getMissionPosted")) { // 發案紀錄
			int userId = jsonObject.get("userId").getAsInt();
			List<Mission> missionTaken = missionDao.getMissionPosted(userId);
            Gson gson2 = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			writeText(response, gson2.toJson(missionTaken));
			
		} else if (action.equals("checkApply")) { // 查看應徵者
			int taskId = jsonObject.get("taskId").getAsInt();
			List<Applicant> applicants = missionDao.checkApply(taskId);
            Gson gson2 = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			writeText(response, gson2.toJson(applicants));
			
		} else if (action.equals("getPhotoFromPost")) {  // 透過案件id找到發案者頭像
			OutputStream os = response.getOutputStream();
			int taskId = jsonObject.get("id").getAsInt();
			int photoSize = jsonObject.get("imageSize").getAsInt();
			byte[] photo = missionDao.getPhotoFromPost(taskId);
			if (photo != null) {
				photo = ImageUtil.shrink(photo, photoSize);
				response.setContentType("image/jpeg");
				response.setContentLengthLong(photo.length);
				os.write(photo);
			}
			
		} else if (action.equals("getPhotoFromUser")) {  // 透過使用者id找到頭像
			OutputStream os = response.getOutputStream();
			int userId = jsonObject.get("id").getAsInt();
			int photoSize = jsonObject.get("imageSize").getAsInt();
			byte[] photo = missionDao.getPhotoFromUser(userId);
			if (photo != null) {
				photo = ImageUtil.shrink(photo, photoSize);
				response.setContentType("image/jpeg");
				response.setContentLengthLong(photo.length);
				os.write(photo);
			}
			
		}else if (action.equals("getImageFromTask")) {  // 取得案件圖片
			OutputStream os = response.getOutputStream(); 
			int taskId = jsonObject.get("id").getAsInt();
			int imageSize = jsonObject.get("imageSize").getAsInt();
			String search = jsonObject.get("request").getAsString();
			byte[] image = missionDao.getImageFromTask(taskId, search);
			if (image != null) {
				image = ImageUtil.shrink(image, imageSize);
				response.setContentType("image/jpeg");
				response.setContentLengthLong(image.length);
				os.write(image);
			}
			
		} else if (action.equals("changeStatusApply")) {  // 繼續新增下去
			int taskId = jsonObject.get("taskId").getAsInt();
			int userId = jsonObject.get("userId").getAsInt();
			int toStatus = jsonObject.get("toStatus").getAsInt();
			int count = missionDao.changeStatusApply(taskId, userId, toStatus);
			writeText(response, String.valueOf(count));
			
		} else if (action.equals("changeStatusPost")) {  // 更改發案狀態
			String missionJson = jsonObject.get("mission").getAsString();
			System.out.println("missionJson = " + missionJson);
			Mission mission = gson.fromJson(missionJson, Mission.class);			
			int toStatus = jsonObject.get("toStatus").getAsInt();
			int count = missionDao.changeStatusPost(mission, toStatus);
			writeText(response, String.valueOf(count));
			
		} else if (action.equals("starForApply") || action.equals("starForPost")) {  // 登錄接案、發案星星
			int stars = jsonObject.get("stars").getAsInt();
			int taskId = jsonObject.get("taskId").getAsInt();
			int userIdApply = jsonObject.get("userIdApply").getAsInt();
			int count = 0;
			if (action.equals("starForApply")) {
				count = missionDao.starForApply(stars, taskId, userIdApply);
			} else {
				int userIdPost = jsonObject.get("userIdPost").getAsInt();
				count = missionDao.starForPost(stars, taskId, userIdApply, userIdPost);
			}
			writeText(response, String.valueOf(count));
			
		} else if (action.equals("getStars")) {  // 取得星星紀錄
			int userId = jsonObject.get("userId").getAsInt();
			List<Record> records = missionDao.getStars(userId);
			writeText(response, gson.toJson(records));
			
		} else if (action.equals("sendNotification")) {  // 發送通知
			int userId = jsonObject.get("userId").getAsInt();
			int type = jsonObject.get("type").getAsInt();
			String reason = jsonObject.get("reason").getAsString();
			int nextPage = jsonObject.get("nextPage").getAsInt();
			int count = missionDao.sendNotification(userId, type, reason, nextPage);
			writeText(response, String.valueOf(count));
		}else if(action.equals("getMissionReport")) { //取得檢舉任務
			List<Mission> missions = missionDao.getMissionReport();
			writeText(response,gson.toJson(missions));	
		}else if(action.equals("updateReportStatus")) {
			int reportId = jsonObject.get("reportId").getAsInt();
			int reportStatus = jsonObject.get("reportStatus").getAsInt();
			boolean isMission = jsonObject.get("isMission").getAsBoolean();
			int userId = jsonObject.get("userId").getAsInt();
			int count = missionDao.updateReportStatus(reportId,reportStatus,isMission,userId); 
			writeText(response, String.valueOf(count));	
		}else if(action.equals("getCommentReport")) {
			List<Mission> missions = missionDao.getCommentReport();
			writeText(response,gson.toJson(missions));				
		}
		else if (action.equals("")) {  // 繼續新增下去
			
		}
	}
	
	private void writeText(HttpServletResponse response, String outText) throws IOException{
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		out.print(outText);
		System.out.println("output: " + outText);
	}
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (missionDao == null) {
			missionDao = new MissionDaoMySqlImpl();
		}
		List<Mission> missions = missionDao.getAll();
		writeText(response, new Gson().toJson(missions));
	}

	
	
}
