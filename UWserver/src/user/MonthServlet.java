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

import mission.Mission;
import mission.MissionDaoMySqlImpl;

@WebServlet("/MonthServlet")
public class MonthServlet extends HttpServlet{
	private final static String CONTENT_TYPE = "text/html; charset=utf-8";
	MonthDao monthDao = null;
	
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
		if (monthDao == null) {
			monthDao = new MonthMySqlImpl();
		}
		
		String action = jsonObject.get("action").getAsString();

		if(action.equals("getAll2020")) {
			Month month = monthDao.getAll2020();
			writeText(response, gson.toJson(month));
		}else if(action.equals("getAll2019")) {
			Month month = monthDao.getAll2019();
			writeText(response, gson.toJson(month));
		}else if(action.equals("getAll2021")) {
			Month month = monthDao.getAll2021();
			writeText(response, gson.toJson(month));
		}
		
	}
	
	private void writeText(HttpServletResponse response, String outText) throws IOException{
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		out.print(outText);
		System.out.println("output: " + outText);
	}
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (monthDao == null) {
			monthDao = new MonthMySqlImpl();
		}
		Month month = monthDao.getAll2020();
		writeText(response, new Gson().toJson(month));
	}

	

}
