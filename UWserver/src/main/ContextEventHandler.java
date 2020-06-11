// 自動更新任務狀態的功能
package main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

@WebListener
public class ContextEventHandler implements ServletContextListener {
	DataSource dataSource;
	
	public ContextEventHandler() {
		dataSource = ServiceLocator.getInstance().getDataSource();
	}

	@Override
	public void contextInitialized(ServletContextEvent contextEvent) {
		// 可以在ServletContext初始化完畢後存入一些必要資料讓頁面取用
		ServletContext context = contextEvent.getServletContext();
		context.setAttribute("message", "contextInitialized");
		context.log("contextInitialized");
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		int month = now.get(Calendar.MONTH);
		int day = now.get(Calendar.DAY_OF_MONTH);
//		int hour = now.get(Calendar.HOUR);
//		int minute = now.get(Calendar.MINUTE);
//		int second = now.get(Calendar.SECOND);
		Date rightNow = now.getTime();
		System.out.println("now: " + rightNow);

		Calendar start = Calendar.getInstance();
//		start.set(year, month, day, 0, 12, 0); // 測試用的啟動時間
		start.set(year, month, day+1, 0, 0, 0);

		Date startDate = start.getTime();
		System.out.println("startDate: " + startDate);

		new Timer().schedule(new TimerTask() {

			@Override
			public void run() {
				Calendar today = Calendar.getInstance();
				int tdy = today.get(Calendar.YEAR);
				int tdm = today.get(Calendar.MONTH);
				int tdd = today.get(Calendar.DAY_OF_MONTH);
				String todayStr = dateToString(tdy, tdm, tdd);
				String statement = "SELECT UUID FROM TaskInfo WHERE Due_date = " + todayStr + ";";
				System.out.println(statement);
				// 取得所有當日到期的TaskId
				List<Integer> tasksDueTodey = selectDueDate(statement);
				for (int i = 0; i < tasksDueTodey.size(); i++) {
					int taskId = tasksDueTodey.get(i);
					System.out.println("Task : " + taskId);
					int count = updateToday(taskId);
					System.out.println("count = " + count);
					if (count != 0) {
						System.out.println("TASK " + taskId + " is already updated.");
					} else {
						System.out.println("TASK " + taskId + " NOTHING UPDATED.");
					}
				}
				
				Calendar yesterday = Calendar.getInstance();
				yesterday.set(tdy, tdm, tdd-1);
				int ytdy = yesterday.get(Calendar.YEAR);
				int ytdm = yesterday.get(Calendar.MONTH);
				int ytdd = yesterday.get(Calendar.DAY_OF_MONTH);
				String yesterdayStr = dateToString(ytdy, ytdm, ytdd);
				statement = "SELECT UUID FROM TaskInfo WHERE Due_date = " + yesterdayStr + ";";
				System.out.println(statement);
				// 取得所有前一日到期的TaskId
				List<Integer> taskDueYesterday = selectDueDate(statement);
				for (int i = 0; i < taskDueYesterday.size(); i++) {
					int taskId = taskDueYesterday.get(i);
					System.out.println("Task : " + taskId);
					int count = updateYesterday(taskId);
					System.out.println("count = " + count);
					if (count != 0) {
						System.out.println("TASK " + taskId + " is already updated.");
					} else {
						System.out.println("TASK " + taskId + " NOTHING UPDATED.");
					}
				}				
 			}
		}, startDate, 24 * 60 * 60 * 1000);  // 24 * 60 * 60 * 1000  更新頻率週期
		
		System.out.println("ServletContextListener: contextInitialized");
	}
	
	public List<Integer> selectDueDate(String statement) {
		List<Integer> taskIds = new ArrayList<Integer>();
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(statement);) {
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int taskId = rs.getInt(1);
				taskIds.add(taskId);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return taskIds;
	}
	
	
	public int updateToday(int taskId) {
		int count1 = 0, count2 = 0;
		String sql1 = "UPDATE TaskInfo SET Status = 2 WHERE UUID = ? AND Status = 1;";
		String sql2 = "UPDATE Task_apply SET Status = 2 WHERE Task_id = ? AND Status = 1;";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps1 = connection.prepareStatement(sql1);
				PreparedStatement ps2 = connection.prepareStatement(sql2);) {
			ps1.setInt(1, taskId);
			ps2.setInt(1, taskId);
			count1 = ps1.executeUpdate();
			count2 = ps2.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count1 + count2;
	}
	
	public int updateYesterday(int taskId) {
		int count1 = 0, count2 = 0;
		String sql1 = "UPDATE TaskInfo SET Status = 4 WHERE UUID = ? AND Status = 0;";
		String sql2 = "UPDATE Task_apply SET Status = 5 WHERE Task_id = ? AND Status = 0;";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps1 = connection.prepareStatement(sql1);
				PreparedStatement ps2 = connection.prepareStatement(sql2);) {
			ps1.setInt(1, taskId);
			ps2.setInt(1, taskId);
			count1 = ps1.executeUpdate();
			count2 = ps2.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count1 + count2;
	}
	
	public String dateToString(int year, int month, int day) {
//		int year = date.get(Calendar.YEAR);
//		int month = date.get(Calendar.MONTH)+1;
//		int day = date.get(Calendar.DAY_OF_MONTH);
		month += 1;
		String mm = (month < 10) ? "0" + String.valueOf(month) : String.valueOf(month);
		String dateStr = String.valueOf(year) + mm +  String.valueOf(day);
		return dateStr;
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// ServletContext物件結束後想要執行的程式碼，例如關閉資料庫連線
		System.out.println("ServletContextListener: contextDestroyed");
	}

}
