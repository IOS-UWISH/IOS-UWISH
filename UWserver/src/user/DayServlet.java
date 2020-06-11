package user;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

@WebServlet("/DayServlet")
public class DayServlet extends HttpServlet{
	private final static String CONTENT_TYPE = "text/html; charset=utf-8";
	DayDao dayDao = null;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
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
		if (dayDao == null) {
			dayDao = new DayMySqlImpl();
		}
		
		String action = jsonObject.get("action").getAsString();
	
		if(action.equals("getAll202001")) {
			Day day = dayDao.getAll202001();
			writeText(response,gson.toJson(day));
		}else if(action.equals("getAll202002")) {
			Day day = dayDao.getAll202002();
			writeText(response,gson.toJson(day));			
		}else if(action.equals("getAll202003")) {
			Day day = dayDao.getAll202003();
			writeText(response,gson.toJson(day));			
		}else if(action.equals("getAll202004")) {
			Day day = dayDao.getAll202004();
			writeText(response,gson.toJson(day));			
		}else if(action.equals("getAll202005")) {
			Day day = dayDao.getAll202005();
			writeText(response,gson.toJson(day));			
		}else if(action.equals("getAll202006")) {
			Day day = dayDao.getAll202006();
			writeText(response,gson.toJson(day));			
		}
	
		
	}
	
	private void writeText(HttpServletResponse response, String outText) throws IOException{
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		out.print(outText);
		System.out.println("output: " + outText);
	}
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (dayDao == null) {
			dayDao = new DayMySqlImpl();
		}
		Day day = dayDao.getAll202001();
		writeText(response, new Gson().toJson(day));
	}
	
	

}
