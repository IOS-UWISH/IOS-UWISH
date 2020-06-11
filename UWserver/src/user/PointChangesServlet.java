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
@WebServlet("/PointChangesServlet")
public class PointChangesServlet extends HttpServlet {
	
	private final static String CONTENT_TYPE = "text/html; charset=utf-8";
	PointChangesDao pointChangesDao = null;
	
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
		if (pointChangesDao == null) {
			pointChangesDao = new PointChangesDaoMySqlImpl(); }
			String action = jsonObject.get("action").getAsString();
			if (action.equals("PointChangesInsert")) {
				String pointChangesJson = jsonObject.get("PointChanges").getAsString();
				System.out.println("PointChangesJson = " + pointChangesJson);
				PointChanges pointChanges = gson.fromJson(pointChangesJson, PointChanges.class);
				int count = 0;
				if (action.equals("PointChangesInsert")) {
					count = pointChangesDao.insert(pointChanges);
				}
				
				writeText(rp,String.valueOf(count));
			}else if(action.equals("getAll")) {
				
				List<PointChanges> pointChanges = pointChangesDao.getAll();
				writeText(rp,gson.toJson(pointChanges));
				
			}else {
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
		if(pointChangesDao == null) {
			pointChangesDao = new PointChangesDaoMySqlImpl();
		}
		List<PointChanges> pointChanges = pointChangesDao.getAll();
//		Gson gson = new Gson();
//		String Book = gson.toJson(book);
//		writeText(rp,Book);
		writeText(rp, new Gson().toJson(pointChanges));
	}
		
		
		
	}
	
	

