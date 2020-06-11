package user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import main.ServiceLocator;

public class DayMySqlImpl implements DayDao {
	DataSource dataSource;

	public DayMySqlImpl() {
		dataSource = ServiceLocator.getInstance().getDataSource();
	}

	@Override
	public Day getAll202001() {
		String sql = "select "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-01-01 00:00:00' and '2020-01-02 00:00:00' )as ONE, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-01-02 00:00:00' and '2020-01-03 00:00:00' )as TWO, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-01-03 00:00:00' and '2020-01-04 00:00:00' )as THREE, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-01-04 00:00:00' and '2020-01-05 00:00:00' )as FOUR, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-01-05 00:00:00' and '2020-01-06 00:00:00' )as FIVE, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-01-06 00:00:00' and '2020-01-07 00:00:00' )as SIX, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-01-07 00:00:00' and '2020-01-08 00:00:00' )as SEVEN, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-01-08 00:00:00' and '2020-01-09 00:00:00' )as EIGHT, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-01-09 00:00:00' and '2020-01-10 00:00:00' )as NINE, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-01-10 00:00:00' and '2020-01-11 00:00:00' )as TEN, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-01-11 00:00:00' and '2020-01-12 00:00:00' )as TEN0NE, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-01-12 00:00:00' and '2020-01-13 00:00:00' )as TENTWO, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-01-13 00:00:00' and '2020-01-14 00:00:00' )as TENTHREE, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-01-14 00:00:00' and '2020-01-15 00:00:00' )as TENFOUR, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-01-15 00:00:00' and '2020-01-16 00:00:00' )as TENFIVE, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-01-16 00:00:00' and '2020-01-17 00:00:00' )as TENSIX, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-01-17 00:00:00' and '2020-01-18 00:00:00' )as TENSEVEN, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-01-18 00:00:00' and '2020-01-19 00:00:00' )as TENEIGHT, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-01-19 00:00:00' and '2020-01-20 00:00:00' )as TENNINE, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-01-20 00:00:00' and '2020-01-21 00:00:00' )as TWENTY, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-01-21 00:00:00' and '2020-01-22 00:00:00' )as TWENTYONE, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-01-22 00:00:00' and '2020-01-23 00:00:00' )as TWENTYTWO, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-01-23 00:00:00' and '2020-01-24 00:00:00' )as TWENTYTHREE, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-01-24 00:00:00' and '2020-01-25 00:00:00' )as TWENTYFOUR, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-01-25 00:00:00' and '2020-01-26 00:00:00' )as TWENTYFIVE, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-01-26 00:00:00' and '2020-01-27 00:00:00' )as TWENTYSIX, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-01-27 00:00:00' and '2020-01-28 00:00:00' )as TWENTYSEVEN, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-01-28 00:00:00' and '2020-01-29 00:00:00' )as TWENTYEIGHT, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-01-29 00:00:00' and '2020-01-30 00:00:00' )as TWENTYNINE, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-01-30 00:00:00' and '2020-01-31 00:00:00' )as THIRTY, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-01-31 00:00:00' and '2020-02-01 00:00:00' )as THIRTYONE;";
		Day day = null;
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				int one = rs.getInt(1);
				int two = rs.getInt(2);
				int three = rs.getInt(3);
				int four = rs.getInt(4);
				int five = rs.getInt(5);
				int six = rs.getInt(6);
				int seven = rs.getInt(7);
				int eight = rs.getInt(8);
				int nine = rs.getInt(9);
				int ten = rs.getInt(10);
				int tenone = rs.getInt(11);
				int tentwo = rs.getInt(12);
				int tenthree = rs.getInt(13);
				int tenfour = rs.getInt(14);
				int tenfive = rs.getInt(15);
				int tensix = rs.getInt(16);
				int tenseven = rs.getInt(17);
				int teneight = rs.getInt(18);
				int tennine = rs.getInt(19);
				int twenty = rs.getInt(20);
				int twentyone = rs.getInt(21);
				int twentytwo = rs.getInt(22);
				int twentythree = rs.getInt(23);
				int twentyfour = rs.getInt(24);
				int twentyfive = rs.getInt(25);
				int twentysix = rs.getInt(26);
				int twentyseven = rs.getInt(27);
				int twentyeight = rs.getInt(28);
				int twentynine = rs.getInt(29);
				int thirty = rs.getInt(30);
				int thirtyone = rs.getInt(31);
				day = new Day(one, two, three, four, five, six, seven, eight, nine, ten, tenone, tentwo, tenthree,
						tenfour, tenfive, tensix, tenseven, teneight, tennine, twenty, twentyone, twentytwo,
						twentythree, twentyfour, twentyfive, twentysix, twentyseven, twentyeight, twentynine, thirty,
						thirtyone);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return day;
	}

	@Override
	public Day getAll202002() {
		String sql = "select "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-02-01 00:00:00' and '2020-02-02 00:00:00' )as ONE, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-02-02 00:00:00' and '2020-02-03 00:00:00' )as TWO, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-02-03 00:00:00' and '2020-02-04 00:00:00' )as THREE, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-02-04 00:00:00' and '2020-02-05 00:00:00' )as FOUR, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-02-05 00:00:00' and '2020-02-06 00:00:00' )as FIVE, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-02-06 00:00:00' and '2020-02-07 00:00:00' )as SIX, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-02-07 00:00:00' and '2020-02-08 00:00:00' )as SEVEN, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-02-08 00:00:00' and '2020-02-09 00:00:00' )as EIGHT, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-02-09 00:00:00' and '2020-02-10 00:00:00' )as NINE, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-02-10 00:00:00' and '2020-02-11 00:00:00' )as TEN, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-02-11 00:00:00' and '2020-02-12 00:00:00' )as TEN0NE, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-02-12 00:00:00' and '2020-02-13 00:00:00' )as TENTWO, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-02-13 00:00:00' and '2020-02-14 00:00:00' )as TENTHREE, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-02-14 00:00:00' and '2020-02-15 00:00:00' )as TENFOUR, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-02-15 00:00:00' and '2020-02-16 00:00:00' )as TENFIVE, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-02-16 00:00:00' and '2020-02-17 00:00:00' )as TENSIX, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-02-17 00:00:00' and '2020-02-18 00:00:00' )as TENSEVEN, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-02-18 00:00:00' and '2020-02-19 00:00:00' )as TENEIGHT, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-02-19 00:00:00' and '2020-02-20 00:00:00' )as TENNINE, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-02-20 00:00:00' and '2020-02-21 00:00:00' )as TWENTY, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-02-21 00:00:00' and '2020-02-22 00:00:00' )as TWENTYONE, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-02-22 00:00:00' and '2020-02-23 00:00:00' )as TWENTYTWO, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-02-23 00:00:00' and '2020-02-24 00:00:00' )as TWENTYTHREE, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-02-24 00:00:00' and '2020-02-25 00:00:00' )as TWENTYFOUR, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-02-25 00:00:00' and '2020-02-26 00:00:00' )as TWENTYFIVE, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-02-26 00:00:00' and '2020-02-27 00:00:00' )as TWENTYSIX, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-02-27 00:00:00' and '2020-02-28 00:00:00' )as TWENTYSEVEN, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-02-28 00:00:00' and '2020-02-29 00:00:00' )as TWENTYEIGHT, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-02-29 00:00:00' and '2020-03-01 00:00:00' )as TWENTYNINE;";
		Day day = null;
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				int one = rs.getInt(1);
				int two = rs.getInt(2);
				int three = rs.getInt(3);
				int four = rs.getInt(4);
				int five = rs.getInt(5);
				int six = rs.getInt(6);
				int seven = rs.getInt(7);
				int eight = rs.getInt(8);
				int nine = rs.getInt(9);
				int ten = rs.getInt(10);
				int tenone = rs.getInt(11);
				int tentwo = rs.getInt(12);
				int tenthree = rs.getInt(13);
				int tenfour = rs.getInt(14);
				int tenfive = rs.getInt(15);
				int tensix = rs.getInt(16);
				int tenseven = rs.getInt(17);
				int teneight = rs.getInt(18);
				int tennine = rs.getInt(19);
				int twenty = rs.getInt(20);
				int twentyone = rs.getInt(21);
				int twentytwo = rs.getInt(22);
				int twentythree = rs.getInt(23);
				int twentyfour = rs.getInt(24);
				int twentyfive = rs.getInt(25);
				int twentysix = rs.getInt(26);
				int twentyseven = rs.getInt(27);
				int twentyeight = rs.getInt(28);
				int twentynine = rs.getInt(29);
				day = new Day(one, two, three, four, five, six, seven, eight, nine, ten, tenone, tentwo, tenthree,
						tenfour, tenfive, tensix, tenseven, teneight, tennine, twenty, twentyone, twentytwo,
						twentythree, twentyfour, twentyfive, twentysix, twentyseven, twentyeight, twentynine);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return day;
	}

	@Override
	public Day getAll202003() {
		String sql = "select "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-03-01 00:00:00' and '2020-03-02 00:00:00' )as ONE, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-03-02 00:00:00' and '2020-03-03 00:00:00' )as TWO, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-03-03 00:00:00' and '2020-03-04 00:00:00' )as THREE, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-03-04 00:00:00' and '2020-03-05 00:00:00' )as FOUR, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-03-05 00:00:00' and '2020-03-06 00:00:00' )as FIVE, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-03-06 00:00:00' and '2020-03-07 00:00:00' )as SIX, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-03-07 00:00:00' and '2020-03-08 00:00:00' )as SEVEN, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-03-08 00:00:00' and '2020-03-09 00:00:00' )as EIGHT, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-03-09 00:00:00' and '2020-03-10 00:00:00' )as NINE, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-03-10 00:00:00' and '2020-03-11 00:00:00' )as TEN, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-03-11 00:00:00' and '2020-03-12 00:00:00' )as TEN0NE, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-03-12 00:00:00' and '2020-03-13 00:00:00' )as TENTWO, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-03-13 00:00:00' and '2020-03-14 00:00:00' )as TENTHREE, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-03-14 00:00:00' and '2020-03-15 00:00:00' )as TENFOUR, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-03-15 00:00:00' and '2020-03-16 00:00:00' )as TENFIVE, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-03-16 00:00:00' and '2020-03-17 00:00:00' )as TENSIX, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-03-17 00:00:00' and '2020-03-18 00:00:00' )as TENSEVEN, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-03-18 00:00:00' and '2020-03-19 00:00:00' )as TENEIGHT, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-03-19 00:00:00' and '2020-03-20 00:00:00' )as TENNINE, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-03-20 00:00:00' and '2020-03-21 00:00:00' )as TWENTY, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-03-21 00:00:00' and '2020-03-22 00:00:00' )as TWENTYONE, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-03-22 00:00:00' and '2020-03-23 00:00:00' )as TWENTYTWO, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-03-23 00:00:00' and '2020-03-24 00:00:00' )as TWENTYTHREE, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-03-24 00:00:00' and '2020-03-25 00:00:00' )as TWENTYFOUR, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-03-25 00:00:00' and '2020-03-26 00:00:00' )as TWENTYFIVE, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-03-26 00:00:00' and '2020-03-27 00:00:00' )as TWENTYSIX, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-03-27 00:00:00' and '2020-03-28 00:00:00' )as TWENTYSEVEN, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-03-28 00:00:00' and '2020-03-29 00:00:00' )as TWENTYEIGHT, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-03-29 00:00:00' and '2020-03-30 00:00:00' )as TWENTYNINE, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-03-30 00:00:00' and '2020-03-31 00:00:00' )as THIRTY, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-03-31 00:00:00' and '2020-04-01 00:00:00' )as THIRTYONE;";
		Day day = null;
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				int one = rs.getInt(1);
				int two = rs.getInt(2);
				int three = rs.getInt(3);
				int four = rs.getInt(4);
				int five = rs.getInt(5);
				int six = rs.getInt(6);
				int seven = rs.getInt(7);
				int eight = rs.getInt(8);
				int nine = rs.getInt(9);
				int ten = rs.getInt(10);
				int tenone = rs.getInt(11);
				int tentwo = rs.getInt(12);
				int tenthree = rs.getInt(13);
				int tenfour = rs.getInt(14);
				int tenfive = rs.getInt(15);
				int tensix = rs.getInt(16);
				int tenseven = rs.getInt(17);
				int teneight = rs.getInt(18);
				int tennine = rs.getInt(19);
				int twenty = rs.getInt(20);
				int twentyone = rs.getInt(21);
				int twentytwo = rs.getInt(22);
				int twentythree = rs.getInt(23);
				int twentyfour = rs.getInt(24);
				int twentyfive = rs.getInt(25);
				int twentysix = rs.getInt(26);
				int twentyseven = rs.getInt(27);
				int twentyeight = rs.getInt(28);
				int twentynine = rs.getInt(29);
				int thirty = rs.getInt(30);
				int thirtyone = rs.getInt(31);
				day = new Day(one, two, three, four, five, six, seven, eight, nine, ten, tenone, tentwo, tenthree,
						tenfour, tenfive, tensix, tenseven, teneight, tennine, twenty, twentyone, twentytwo,
						twentythree, twentyfour, twentyfive, twentysix, twentyseven, twentyeight, twentynine, thirty,
						thirtyone);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return day;
	}

	@Override
	public Day getAll202004() {
		String sql = "select "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-04-01 00:00:00' and '2020-04-02 00:00:00' )as ONE, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-04-02 00:00:00' and '2020-04-03 00:00:00' )as TWO, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-04-03 00:00:00' and '2020-04-04 00:00:00' )as THREE, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-04-04 00:00:00' and '2020-04-05 00:00:00' )as FOUR, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-04-05 00:00:00' and '2020-04-06 00:00:00' )as FIVE, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-04-06 00:00:00' and '2020-04-07 00:00:00' )as SIX, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-04-07 00:00:00' and '2020-04-08 00:00:00' )as SEVEN, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-04-08 00:00:00' and '2020-04-09 00:00:00' )as EIGHT, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-04-09 00:00:00' and '2020-04-10 00:00:00' )as NINE, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-04-10 00:00:00' and '2020-04-11 00:00:00' )as TEN, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-04-11 00:00:00' and '2020-04-12 00:00:00' )as TEN0NE, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-04-12 00:00:00' and '2020-04-13 00:00:00' )as TENTWO, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-04-13 00:00:00' and '2020-04-14 00:00:00' )as TENTHREE, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-04-14 00:00:00' and '2020-04-15 00:00:00' )as TENFOUR, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-04-15 00:00:00' and '2020-04-16 00:00:00' )as TENFIVE, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-04-16 00:00:00' and '2020-04-17 00:00:00' )as TENSIX, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-04-17 00:00:00' and '2020-04-18 00:00:00' )as TENSEVEN, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-04-18 00:00:00' and '2020-04-19 00:00:00' )as TENEIGHT, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-04-19 00:00:00' and '2020-04-20 00:00:00' )as TENNINE, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-04-20 00:00:00' and '2020-04-21 00:00:00' )as TWENTY, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-04-21 00:00:00' and '2020-04-22 00:00:00' )as TWENTYONE, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-04-22 00:00:00' and '2020-04-23 00:00:00' )as TWENTYTWO, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-04-23 00:00:00' and '2020-04-24 00:00:00' )as TWENTYTHREE, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-04-24 00:00:00' and '2020-04-25 00:00:00' )as TWENTYFOUR, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-04-25 00:00:00' and '2020-04-26 00:00:00' )as TWENTYFIVE, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-04-26 00:00:00' and '2020-04-27 00:00:00' )as TWENTYSIX, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-04-27 00:00:00' and '2020-04-28 00:00:00' )as TWENTYSEVEN, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-04-28 00:00:00' and '2020-04-29 00:00:00' )as TWENTYEIGHT, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-04-29 00:00:00' and '2020-04-30 00:00:00' )as TWENTYNINE, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-04-30 00:00:00' and '2020-05-01 00:00:00' )as THIRTY;";
		Day day = null;
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				int one = rs.getInt(1);
				int two = rs.getInt(2);
				int three = rs.getInt(3);
				int four = rs.getInt(4);
				int five = rs.getInt(5);
				int six = rs.getInt(6);
				int seven = rs.getInt(7);
				int eight = rs.getInt(8);
				int nine = rs.getInt(9);
				int ten = rs.getInt(10);
				int tenone = rs.getInt(11);
				int tentwo = rs.getInt(12);
				int tenthree = rs.getInt(13);
				int tenfour = rs.getInt(14);
				int tenfive = rs.getInt(15);
				int tensix = rs.getInt(16);
				int tenseven = rs.getInt(17);
				int teneight = rs.getInt(18);
				int tennine = rs.getInt(19);
				int twenty = rs.getInt(20);
				int twentyone = rs.getInt(21);
				int twentytwo = rs.getInt(22);
				int twentythree = rs.getInt(23);
				int twentyfour = rs.getInt(24);
				int twentyfive = rs.getInt(25);
				int twentysix = rs.getInt(26);
				int twentyseven = rs.getInt(27);
				int twentyeight = rs.getInt(28);
				int twentynine = rs.getInt(29);
				int thirty = rs.getInt(30);
				day = new Day(one, two, three, four, five, six, seven, eight, nine, ten, tenone, tentwo, tenthree,
						tenfour, tenfive, tensix, tenseven, teneight, tennine, twenty, twentyone, twentytwo,
						twentythree, twentyfour, twentyfive, twentysix, twentyseven, twentyeight, twentynine, thirty);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return day;
	}

	@Override
	public Day getAll202005() {
		String sql = "select "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-01-01 00:00:00' and '2020-01-02 00:00:00' )as ONE, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-01-02 00:00:00' and '2020-01-03 00:00:00' )as TWO, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-01-03 00:00:00' and '2020-01-04 00:00:00' )as THREE, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-01-04 00:00:00' and '2020-01-05 00:00:00' )as FOUR, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-01-05 00:00:00' and '2020-01-06 00:00:00' )as FIVE, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-01-06 00:00:00' and '2020-01-07 00:00:00' )as SIX, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-01-07 00:00:00' and '2020-01-08 00:00:00' )as SEVEN, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-01-08 00:00:00' and '2020-01-09 00:00:00' )as EIGHT, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-01-09 00:00:00' and '2020-01-10 00:00:00' )as NINE, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-01-10 00:00:00' and '2020-01-11 00:00:00' )as TEN, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-01-11 00:00:00' and '2020-01-12 00:00:00' )as TEN0NE, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-01-12 00:00:00' and '2020-01-13 00:00:00' )as TENTWO, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-01-13 00:00:00' and '2020-01-14 00:00:00' )as TENTHREE, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-01-14 00:00:00' and '2020-01-15 00:00:00' )as TENFOUR, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-01-15 00:00:00' and '2020-01-16 00:00:00' )as TENFIVE, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-01-16 00:00:00' and '2020-01-17 00:00:00' )as TENSIX, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-01-17 00:00:00' and '2020-01-18 00:00:00' )as TENSEVEN, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-01-18 00:00:00' and '2020-01-19 00:00:00' )as TENEIGHT, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-01-19 00:00:00' and '2020-01-20 00:00:00' )as TENNINE, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-01-20 00:00:00' and '2020-01-21 00:00:00' )as TWENTY, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-01-21 00:00:00' and '2020-01-22 00:00:00' )as TWENTYONE, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-01-22 00:00:00' and '2020-01-23 00:00:00' )as TWENTYTWO, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-01-23 00:00:00' and '2020-01-24 00:00:00' )as TWENTYTHREE, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-01-24 00:00:00' and '2020-01-25 00:00:00' )as TWENTYFOUR, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-01-25 00:00:00' and '2020-01-26 00:00:00' )as TWENTYFIVE, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-01-26 00:00:00' and '2020-01-27 00:00:00' )as TWENTYSIX, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-01-27 00:00:00' and '2020-01-28 00:00:00' )as TWENTYSEVEN, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-01-28 00:00:00' and '2020-01-29 00:00:00' )as TWENTYEIGHT, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-01-29 00:00:00' and '2020-01-30 00:00:00' )as TWENTYNINE, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-01-30 00:00:00' and '2020-01-31 00:00:00' )as THIRTY, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-01-31 00:00:00' and '2020-02-01 00:00:00' )as THIRTYONE;";
		Day day = null;
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				int one = rs.getInt(1);
				int two = rs.getInt(2);
				int three = rs.getInt(3);
				int four = rs.getInt(4);
				int five = rs.getInt(5);
				int six = rs.getInt(6);
				int seven = rs.getInt(7);
				int eight = rs.getInt(8);
				int nine = rs.getInt(9);
				int ten = rs.getInt(10);
				int tenone = rs.getInt(11);
				int tentwo = rs.getInt(12);
				int tenthree = rs.getInt(13);
				int tenfour = rs.getInt(14);
				int tenfive = rs.getInt(15);
				int tensix = rs.getInt(16);
				int tenseven = rs.getInt(17);
				int teneight = rs.getInt(18);
				int tennine = rs.getInt(19);
				int twenty = rs.getInt(20);
				int twentyone = rs.getInt(21);
				int twentytwo = rs.getInt(22);
				int twentythree = rs.getInt(23);
				int twentyfour = rs.getInt(24);
				int twentyfive = rs.getInt(25);
				int twentysix = rs.getInt(26);
				int twentyseven = rs.getInt(27);
				int twentyeight = rs.getInt(28);
				int twentynine = rs.getInt(29);
				int thirty = rs.getInt(30);
				int thirtyone = rs.getInt(31);
				day = new Day(one, two, three, four, five, six, seven, eight, nine, ten, tenone, tentwo, tenthree,
						tenfour, tenfive, tensix, tenseven, teneight, tennine, twenty, twentyone, twentytwo,
						twentythree, twentyfour, twentyfive, twentysix, twentyseven, twentyeight, twentynine, thirty,
						thirtyone);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return day;
	}

	@Override
	public Day getAll202006() {
		String sql = "select "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-06-01 00:00:00' and '2020-06-02 00:00:00' )as ONE, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-06-02 00:00:00' and '2020-06-03 00:00:00' )as TWO, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-06-03 00:00:00' and '2020-06-04 00:00:00' )as THREE, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-06-04 00:00:00' and '2020-06-05 00:00:00' )as FOUR, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-06-05 00:00:00' and '2020-06-06 00:00:00' )as FIVE, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-06-06 00:00:00' and '2020-06-07 00:00:00' )as SIX, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-06-07 00:00:00' and '2020-06-08 00:00:00' )as SEVEN, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-06-08 00:00:00' and '2020-06-09 00:00:00' )as EIGHT, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-06-09 00:00:00' and '2020-06-10 00:00:00' )as NINE, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-06-10 00:00:00' and '2020-06-11 00:00:00' )as TEN, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-06-11 00:00:00' and '2020-06-12 00:00:00' )as TEN0NE, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-06-12 00:00:00' and '2020-06-13 00:00:00' )as TENTWO, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-06-13 00:00:00' and '2020-06-14 00:00:00' )as TENTHREE, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-06-14 00:00:00' and '2020-06-15 00:00:00' )as TENFOUR, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-06-15 00:00:00' and '2020-06-16 00:00:00' )as TENFIVE, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-06-16 00:00:00' and '2020-06-17 00:00:00' )as TENSIX, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-06-17 00:00:00' and '2020-06-18 00:00:00' )as TENSEVEN, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-06-18 00:00:00' and '2020-06-19 00:00:00' )as TENEIGHT, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-06-19 00:00:00' and '2020-06-20 00:00:00' )as TENNINE, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-06-20 00:00:00' and '2020-06-21 00:00:00' )as TWENTY, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-06-21 00:00:00' and '2020-06-22 00:00:00' )as TWENTYONE, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-06-22 00:00:00' and '2020-06-23 00:00:00' )as TWENTYTWO, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-06-23 00:00:00' and '2020-06-24 00:00:00' )as TWENTYTHREE, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-06-24 00:00:00' and '2020-06-25 00:00:00' )as TWENTYFOUR, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-06-25 00:00:00' and '2020-06-26 00:00:00' )as TWENTYFIVE, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-06-26 00:00:00' and '2020-06-27 00:00:00' )as TWENTYSIX, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-06-27 00:00:00' and '2020-06-28 00:00:00' )as TWENTYSEVEN, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-06-28 00:00:00' and '2020-06-29 00:00:00' )as TWENTYEIGHT, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-06-29 00:00:00' and '2020-06-30 00:00:00' )as TWENTYNINE, "
				+ "(select sum(Point_added) as totalincome from OrderInfo where Purchase_time between '2020-06-30 00:00:00' and '2020-07-01 00:00:00' )as THIRTY;";
		Day day = null;
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				int one = rs.getInt(1);
				int two = rs.getInt(2);
				int three = rs.getInt(3);
				int four = rs.getInt(4);
				int five = rs.getInt(5);
				int six = rs.getInt(6);
				int seven = rs.getInt(7);
				int eight = rs.getInt(8);
				int nine = rs.getInt(9);
				int ten = rs.getInt(10);
				int tenone = rs.getInt(11);
				int tentwo = rs.getInt(12);
				int tenthree = rs.getInt(13);
				int tenfour = rs.getInt(14);
				int tenfive = rs.getInt(15);
				int tensix = rs.getInt(16);
				int tenseven = rs.getInt(17);
				int teneight = rs.getInt(18);
				int tennine = rs.getInt(19);
				int twenty = rs.getInt(20);
				int twentyone = rs.getInt(21);
				int twentytwo = rs.getInt(22);
				int twentythree = rs.getInt(23);
				int twentyfour = rs.getInt(24);
				int twentyfive = rs.getInt(25);
				int twentysix = rs.getInt(26);
				int twentyseven = rs.getInt(27);
				int twentyeight = rs.getInt(28);
				int twentynine = rs.getInt(29);
				int thirty = rs.getInt(30);
				day = new Day(one, two, three, four, five, six, seven, eight, nine, ten, tenone, tentwo, tenthree,
						tenfour, tenfive, tensix, tenseven, teneight, tennine, twenty, twentyone, twentytwo,
						twentythree, twentyfour, twentyfive, twentysix, twentyseven, twentyeight, twentynine, thirty);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return day;
	}

}
