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
@WebServlet("/ManagerServlet")
public class ManagerServlet extends HttpServlet{
	
	private final static String CONTENT_TYPE = "text/html; charset=utf-8";
	ManagerDao managerDao = null;
	
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
		// 將輸入資料列印出來除錯用
		System.out.println("input: " + jsonIn);

		JsonObject jsonObject = gson.fromJson(jsonIn.toString(), JsonObject.class);
		if (managerDao == null) {
			managerDao = new ManagerDaoMySqlImpl();
		}

		String action = jsonObject.get("action").getAsString();
		
		if (action.equals("managerUpdate")){
			String managerjson = jsonObject.get("manager").getAsString();
			System.out.println("ManagerJson = " + managerjson);
			Manager manager = gson.fromJson(managerjson, Manager.class);
		    int count = 0;
			if (action.equals("managerUpdate")) {
				count = managerDao.update(manager);
			}
			writeText(rp,String.valueOf(count));
		}
		else if (action.equals("getAll")) {
			List<Manager> managers = managerDao.getAll();
			writeText(rp, gson.toJson(managers));
		}else if (action.equals("ManagerLogin")) {
			List<Manager> managerList = new ManagerDaoMySqlImpl().getAll();
			
			
			String account = jsonObject.get("account").getAsString();
			String password = jsonObject.get("password").getAsString();
			int outstr = 0;
			for(int i=0;i<managerList.size();i++) {
				if (account != null && password != null && account.equals(managerList.get(i).getUuid()) && password.equals(managerList.get(i).getPassword())){
					outstr = 1;
				 }
		       }
					
			rp.setContentType(CONTENT_TYPE);
			PrintWriter out = rp.getWriter();
			out.println(Integer.valueOf(outstr));
			System.out.println(outstr);
//			writeText(rp,gson.toJson(outstr));
		
		}else if (action.equals("findById")){
			String uuid = jsonObject.get("uuid").getAsString();
			Manager manager = managerDao.findById(uuid);
			writeText(rp,gson.toJson(manager));
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
		if (managerDao == null) {
			managerDao = new ManagerDaoMySqlImpl();
		}
		List<Manager> managers = managerDao.getAll();
		writeText(rp, new Gson().toJson(managers));
	}

}
