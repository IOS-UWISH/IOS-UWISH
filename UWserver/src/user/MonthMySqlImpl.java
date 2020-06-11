package user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import main.ServiceLocator;

public class MonthMySqlImpl implements MonthDao{
	DataSource dataSource;
	
	public MonthMySqlImpl() {
		dataSource = ServiceLocator.getInstance().getDataSource();
	}

	@Override
	public Month getAll2020() {
		String sql = "select " + 
				"(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-01-01 00:00:00' and '2020-02-01 00:00:00' )as Jan, " + 
				"(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-02-01 00:00:00' and '2020-03-01 00:00:00' )as Feb, " + 
				"(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-03-01 00:00:00' and '2020-04-01 00:00:00' )as Mar, " + 
				"(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-04-01 00:00:00' and '2020-05-01 00:00:00' )as Apr, " + 
				"(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-05-01 00:00:00' and '2020-06-01 00:00:00' )as May, " + 
				"(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-06-01 00:00:00' and '2020-07-01 00:00:00' )as Jun, " + 
				"(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-07-01 00:00:00' and '2020-08-01 00:00:00' )as Jul, " + 
				"(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-08-01 00:00:00' and '2020-09-01 00:00:00' )as Aug, " + 
				"(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-09-01 00:00:00' and '2020-10-01 00:00:00' )as Sep, " + 
				"(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-10-01 00:00:00' and '2020-11-01 00:00:00' )as Oct, " + 
				"(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-11-01 00:00:00' and '2020-12-01 00:00:00' )as Nov, " + 
				"(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-12-01 00:00:00' and '2021-01-01 00:00:00' )as December;";
		Month month = null;
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);){
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				int Jan = rs.getInt(1);
				int Feb = rs.getInt(2);
				int Mar = rs.getInt(3);
				int Apr = rs.getInt(4);
				int May = rs.getInt(5);
				int Jun = rs.getInt(6);
				int Jul = rs.getInt(7);
				int Aug = rs.getInt(8);
				int Sep = rs.getInt(9);
				int Oct = rs.getInt(10);
				int Nov = rs.getInt(11);
				int December = rs.getInt(12);
				month = new Month(Jan,Feb,Mar,Apr,May,Jun,Jul,Aug,Sep,Oct,Nov,December);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return month;
	}

	@Override
	public Month getAll2019() {
		String sql = "select " + 
				"(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2019-01-01 00:00:00' and '2019-02-01 00:00:00' )as Jan, " + 
				"(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2019-02-01 00:00:00' and '2019-03-01 00:00:00' )as Feb, " + 
				"(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2019-03-01 00:00:00' and '2019-04-01 00:00:00' )as Mar, " + 
				"(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2019-04-01 00:00:00' and '2019-05-01 00:00:00' )as Apr, " + 
				"(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2019-05-01 00:00:00' and '2019-06-01 00:00:00' )as May, " + 
				"(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2019-06-01 00:00:00' and '2019-07-01 00:00:00' )as Jun, " + 
				"(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2019-07-01 00:00:00' and '2019-08-01 00:00:00' )as Jul, " + 
				"(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2019-08-01 00:00:00' and '2019-09-01 00:00:00' )as Aug, " + 
				"(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2019-09-01 00:00:00' and '2019-10-01 00:00:00' )as Sep, " + 
				"(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2019-10-01 00:00:00' and '2019-11-01 00:00:00' )as Oct, " + 
				"(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2019-11-01 00:00:00' and '2019-12-01 00:00:00' )as Nov, " + 
				"(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2019-12-01 00:00:00' and '2020-01-01 00:00:00' )as December;";
		Month month = null;
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);){
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				int Jan = rs.getInt(1);
				int Feb = rs.getInt(2);
				int Mar = rs.getInt(3);
				int Apr = rs.getInt(4);
				int May = rs.getInt(5);
				int Jun = rs.getInt(6);
				int Jul = rs.getInt(7);
				int Aug = rs.getInt(8);
				int Sep = rs.getInt(9);
				int Oct = rs.getInt(10);
				int Nov = rs.getInt(11);
				int December = rs.getInt(12);
				month = new Month(Jan,Feb,Mar,Apr,May,Jun,Jul,Aug,Sep,Oct,Nov,December);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return month;
	}

	@Override
	public Month getAll2021() {
		String sql = "select " + 
				"(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2021-01-01 00:00:00' and '2021-02-01 00:00:00' )as Jan, " + 
				"(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2021-02-01 00:00:00' and '2021-03-01 00:00:00' )as Feb, " + 
				"(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2021-03-01 00:00:00' and '2021-04-01 00:00:00' )as Mar, " + 
				"(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2021-04-01 00:00:00' and '2021-05-01 00:00:00' )as Apr, " + 
				"(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2021-05-01 00:00:00' and '2021-06-01 00:00:00' )as May, " + 
				"(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2021-06-01 00:00:00' and '2021-07-01 00:00:00' )as Jun, " + 
				"(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2021-07-01 00:00:00' and '2021-08-01 00:00:00' )as Jul, " + 
				"(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2021-08-01 00:00:00' and '2021-09-01 00:00:00' )as Aug, " + 
				"(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2021-09-01 00:00:00' and '2021-10-01 00:00:00' )as Sep, " + 
				"(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2021-10-01 00:00:00' and '2021-11-01 00:00:00' )as Oct, " + 
				"(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2021-11-01 00:00:00' and '2021-12-01 00:00:00' )as Nov, " + 
				"(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2021-12-01 00:00:00' and '2022-01-01 00:00:00' )as December;";
		Month month = null;
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);){
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				int Jan = rs.getInt(1);
				int Feb = rs.getInt(2);
				int Mar = rs.getInt(3);
				int Apr = rs.getInt(4);
				int May = rs.getInt(5);
				int Jun = rs.getInt(6);
				int Jul = rs.getInt(7);
				int Aug = rs.getInt(8);
				int Sep = rs.getInt(9);
				int Oct = rs.getInt(10);
				int Nov = rs.getInt(11);
				int December = rs.getInt(12);
				month = new Month(Jan,Feb,Mar,Apr,May,Jun,Jul,Aug,Sep,Oct,Nov,December);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return month;
	}

}
