package mission;

import java.sql.Date;
import java.sql.Time;

public class Applicant {
    private int userId;
    private String userName;
    private int level;
    private String phone;
    private Date applyDate;
    private Time applyTime;
    private int status;

    public Applicant(int userId, String userName, int level, String phone, Date applyDate, Time applyTime, int status) {
        this.userId = userId;
        this.userName = userName;
        this.level = level;
        this.phone = phone;
        this.applyDate = applyDate;
        this.applyTime = applyTime;
        this.status = status;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }
    
    public Time getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Time applyTime) {
        this.applyTime = applyTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}