package user;

public class Notification {
    private int uuid;
    private int type;
    private String reason;
    private boolean next_page;
    private int user_id;

    public Notification(int uuid, int type, String reason, boolean next_page, int user_id) {
        this.uuid = uuid;
        this.type = type;
        this.reason = reason;
        this.next_page = next_page;
        this.user_id = user_id;
    }

    @Override
    public boolean equals(Object obj) {
        return this.uuid == ((Notification) obj).uuid;
    }

    public int getUuid() {
        return uuid;
    }

    public void setUuid(int uuid) {
        this.uuid = uuid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public boolean isNext_page() {
        return next_page;
    }

    public void setNext_page(boolean next_page) {
        this.next_page = next_page;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
