package user;



public class Manager  {
    private String Uuid;
    private String Password;

    public Manager(String uuid, String password) {
        this.Uuid = uuid;
        this.Password = password;
    }

    public Manager(String password){
        this.Password = password;
    }

    public void setFields(String password) {
        this.Password = password;
    }

    @Override
    public boolean equals(Object obj) {
        return this.Uuid == ((Manager) obj).Uuid;
    }

    public String getUuid() {
        return Uuid;
    }

    public void setUuid(String uuid) {
        Uuid = uuid;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}