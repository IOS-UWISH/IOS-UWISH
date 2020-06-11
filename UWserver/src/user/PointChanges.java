package user;

public class PointChanges {

    private int uuid;
    private int user_id;
    private String reason;
    private int add_or_subtract;

    public PointChanges(int uuid, int user_id, String reason, int add_or_subtract) {
        this.uuid = uuid;
        this.user_id = user_id;
        this.reason = reason;
        this.add_or_subtract = add_or_subtract;
    }
    
    @Override
    public boolean equals(Object obj) {
        return this.uuid == ((PointChanges) obj).uuid;
    }

    public int getUuid() {
        return uuid;
    }

    public void setUuid(int uuid) {
        this.uuid = uuid;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getAdd_or_subtract() {
        return add_or_subtract;
    }

    public void setAdd_or_subtract(int add_or_subtract) {
        this.add_or_subtract = add_or_subtract;
    }
}