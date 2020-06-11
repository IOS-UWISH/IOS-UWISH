package user;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;






@SuppressWarnings("serial")
@WebServlet("/NotificationServlet")
public class NotificationServlet extends HttpServlet{
	
	private final static String CONTENT_TYPE = "text/html; charset=utf-8";
    NotificationDao notificationDao = null;
	
	
	@Override
	protected void doPost(HttpServletRequest rq, HttpServletResponse rp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		BufferedReader br = rq.getReader();
		StringBuilder jsonIn = new StringBuilder();
		String line = null;
		while ((line = br.readLine()) != null) {
			jsonIn.append(line);
		}
		// 將輸入資料列印出來除錯用
		System.out.println("input: " + jsonIn);

		JsonObject jsonObject = gson.fromJson(jsonIn.toString(), JsonObject.class);
		if (notificationDao == null) {
			notificationDao = new NotificationDaoMySqlImpl();
		}

		String action = jsonObject.get("action").getAsString();
	
		
		if (action.equals("NotificationInsert")) {
			String notificationJson = jsonObject.get("Notification").getAsString();
			System.out.println("NotificationJson = " + notificationJson);
			Notification notification = gson.fromJson(notificationJson, Notification.class);
			int count = 0;
			if (action.equals("NotificationInsert")) {
				count = notificationDao.insert(notification);
			}
			writeText(rp,String.valueOf(count));}
		
		else if (action.equals("getAll")) {
			List<Notification> notifications = notificationDao.getAll();
			writeText(rp, gson.toJson(notifications));
		}else if (action.equals("findByUserId")) {
			int id = jsonObject.get("user_id").getAsInt();
			List<Notification> notifications = notificationDao.findByUserId(id);
			writeText(rp, gson.toJson(notifications));
		}
		else{
			writeText(rp, "");
		}
	
		}
	
	
	
	
	
	
	
	private void writeText(HttpServletResponse response, String outText) throws IOException{
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		out.print(outText);
		System.out.println("output: " + outText);
	}
	
	
	
	
	@Override
	protected void doGet(HttpServletRequest rq, HttpServletResponse rp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if (notificationDao == null) {
			notificationDao = new NotificationDaoMySqlImpl();
		}
		List<Notification> notifications = notificationDao.getAll();
		writeText(rp, new Gson().toJson(notifications));
	}
	
	
	
	

}
