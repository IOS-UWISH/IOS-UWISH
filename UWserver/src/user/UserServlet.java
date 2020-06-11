package user;
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
import com.google.gson.JsonObject;

import main.ImageUtil;












@SuppressWarnings("serial")
@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
	
	private final static String CONTENT_TYPE = "text/html; charset=utf-8";
	UserDao userDao = null;
	
	@Override
	protected void doPost(HttpServletRequest rq, HttpServletResponse rp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		rq.setCharacterEncoding("utf-8");
		Gson gson = new Gson();
		BufferedReader br = rq.getReader();
		StringBuilder jsonIn = new StringBuilder();
		String line = null;
		while ((line = br.readLine()) != null) {
			jsonIn.append(line);
	}
		System.out.println("input: " + jsonIn);
		JsonObject jsonObject = gson.fromJson(jsonIn.toString(), JsonObject.class);
		if (userDao == null) {
			userDao = new UserDaoMySqlImpl();
		}
		String action = jsonObject.get("action").getAsString();
		
		if (action.equals("getAll")) {
			
//			List<User> userList = new UserDaoMySqlImpl().getAll();
//			
//			
//			String account = jsonObject.get("account").getAsString();
//			String password = jsonObject.get("password").getAsString();
//		
//		
//			String outstr="";
//			for(int i=0;i<userList.size();i++) {
//				if (account != null && password != null && account.equals(userList.get(i).getEmail()) && password.equals(userList.get(i).getPassword())){
//					outstr = userList.get(i).getName();
//					break;
//				 }
//		       }
//			rp.setContentType(CONTENT_TYPE);
//			PrintWriter out = rp.getWriter();
//			out.println(outstr);
//			System.out.println(outstr);
			
			List<User> user = userDao.getAll();
			writeText(rp,gson.toJson(user));
			
		}
		else if (action.equals("getPhoto")) {
			OutputStream os = rp.getOutputStream();
			int uuid = jsonObject.get("userId").getAsInt();
			int imageSize = jsonObject.get("imageSize").getAsInt();
			byte[] image = userDao.getImage(uuid);
			if(image != null) {
				image = ImageUtil.shrink(image,imageSize);
				rp.setContentType("image/jpeg");
				rp.setContentLength(image.length);
				os.write(image);
			}
		}
		
		else if (action.equals("userUpdatePoint")) {
//			String notificationJson = jsonObject.get("userId").getAsString();
			int userId = jsonObject.get("userId").getAsInt();
			int userPoint = jsonObject.get("userPoint").getAsInt();
//			System.out.println("NotificationJson = " + notificationJson);
//			Notification notification = gson.fromJson(notificationJson, Notification.class);
			int count = 0;
			if (action.equals("userUpdatePoint")) {
				count = userDao.userUpdatePoint(userId, userPoint);
			}
			writeText(rp,String.valueOf(count));}
		else if (action.equals("userInsert") || action.equals("userUpdate")) {
			String userjson = jsonObject.get("user").getAsString();
			System.out.println("userJson = " + userjson);
			User user = gson.fromJson(userjson, User.class);
			byte[] image = null;
			
			if (jsonObject.get("imageBase64") != null) {
				String imageBase64 = jsonObject.get("imageBase64").getAsString();
				if (imageBase64 != null && !imageBase64.isEmpty()) {
					image = Base64.getMimeDecoder().decode(imageBase64);
				}
			}
			int count = 0;
			if (action.equals("userInsert")) {
				count = userDao.insert(user,image);
			}else if (action.equals("userUpdate")) {
				count = userDao.update(user, image);
			}
			writeText(rp,String.valueOf(count));
		}else if (action.equals("userDelete")) {
			int userUuid = jsonObject.get("userUuid").getAsInt();
			int count = userDao.delete(userUuid);
			writeText(rp, String.valueOf(count));
		}else if (action.equals("findById")){
			int uuid = jsonObject.get("uuid").getAsInt();
			User user = userDao.findById(uuid);
			writeText(rp,gson.toJson(user));
		} else if (action.equals("userLogin")) {  // 那個...我是建議這樣寫啦QAQ
			
			String account = jsonObject.get("account").getAsString();
			String password = jsonObject.get("password").getAsString();
			int uuid = userDao.userLogin(account, password);
			writeText(rp, String.valueOf(uuid));
			
		} else if (action.equals("userLogin")) {
			
			
//			List<User> userList = new UserDaoMySqlImpl().getAll();
//			String account = jsonObject.get("account").getAsString();
//			String password = jsonObject.get("password").getAsString();
//		
//		
//			String outstr="";
//			for(int i=0;i<userList.size();i++) {
//				if (account != null && password != null && account.equals(userList.get(i).getEmail()) && password.equals(userList.get(i).getPassword())){
//					outstr = Integer.toString(userList.get(i).getUuid());
//					break;
//				 }
//		       }
//			rp.setContentType(CONTENT_TYPE);
//			PrintWriter out = rp.getWriter();
//			out.println(outstr);
//			System.out.println(outstr);
			
//			int uuid = jsonObject.get("uuid").getAsInt();
//			User user = userDao.findById(uuid);
//			writeText(rp,gson.toJson(user));
				
				
			
			
				List<User> userList = new UserDaoMySqlImpl().getAll();
				
				
				String account = jsonObject.get("account").getAsString();
				String password = jsonObject.get("password").getAsString();
//				User user = userDao.findById(0);
//				String outstr="";
				int outstr = 0;
				for(int i=0;i<userList.size();i++) {
					if (account != null && password != null && account.equals(userList.get(i).getEmail()) && password.equals(userList.get(i).getPassword())){
//						outstr = Integer.toString(userList.get(i).getUuid());
						outstr = userList.get(i).getUuid();
					 }
			       }
//				User user2 = userDao.findById(outstr);
//				writeText(rp, gson.toJson(user2));
				
				
				
				
				rp.setContentType(CONTENT_TYPE);
				PrintWriter out = rp.getWriter();
				out.println(Integer.valueOf(outstr));
				System.out.println(outstr);
				
				
//				writeText(rp,gson.toJson(user));
			
		}else if(action.equals("getAllUser")) { //0527新加入的
			List<User> userList = userDao.getAllUser();
			writeText(rp,gson.toJson(userList));		
		}
		else if(action.equals("userUpdateStatus")) { 
			String userJson = jsonObject.get("user").getAsString();
			System.out.println("userJson = " + userJson);
			User user = gson.fromJson(userJson, User.class);
			int count = 0; 
			count = userDao.updateStatus(user);
			writeText(rp,String.valueOf(count));
		}
	else {
			writeText(rp, "");
		}		
	}
		
		private void writeText(HttpServletResponse rp, String outText) throws IOException {
			rp.setContentType(CONTENT_TYPE);
			PrintWriter out = rp.getWriter();
			out.print(outText);
			System.out.println("output: " + outText);
		}	
	
	@Override
	protected void doGet(HttpServletRequest rq, HttpServletResponse rp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if(userDao == null) {
			userDao = new UserDaoMySqlImpl();
		}
		List<User> user = userDao.getAll();
//		Gson gson = new Gson();
//		String Book = gson.toJson(book);
//		writeText(rp,Book);
		writeText(rp, new Gson().toJson(user));
	}
	
	

}
