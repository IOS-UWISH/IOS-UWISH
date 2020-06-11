package user;

public class User {
	
	private int uuid;
    private String name;
    private String nickname;
    private String email;
    private String phoneNo;
    private String password;
    private float star_apply;
    private float star_post;
    private int level;
    private int points;
    private boolean status;
    private String Register_time;
    
    public User() {}

    public User(int uuid, String name, String nickname, String email, String phoneNo, String password, float star_apply, float star_post, int level, int points, boolean status, String register_time) {
        this.uuid = uuid;
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.phoneNo = phoneNo;
        this.password = password;
        this.star_apply = star_apply;
        this.star_post = star_post;
        this.level = level;
        this.points = points;
        this.status = status;
        this.Register_time = register_time;
    }
    
    public User(int uuid, String nickname, String phoneNo) {
    	this.uuid = uuid;
    	this.nickname = nickname;
    	this.phoneNo = phoneNo;
    }
    
    //0527新加入的
    public User(int uuid,String name,boolean status) {
    	this.uuid = uuid;
    	this.name = name;
    	this.status = status;    	    	
    }
    
    @Override
    public boolean equals(Object obj) {
        return this.uuid == ((User) obj).uuid;
    }


    public int getUuid() {
        return uuid;
    }

    public void setUuid(int uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public float getStar_apply() {
        return star_apply;
    }

    public void setStar_apply(float star_apply) {
        this.star_apply = star_apply;
    }

    public float getStar_post() {
        return star_post;
    }

    public void setStar_post(float star_post) {
        this.star_post = star_post;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getRegister_time() {
        return Register_time;
    }

    public void setRegister_time(String register_time) {
        Register_time = register_time;
    }

}