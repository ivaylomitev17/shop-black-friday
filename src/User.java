public class User {
    private String username;

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    private String passwordHash;

    public User(){

    }
    public void setUsername(String username){
        this.username = username;
    }
    public void setPasswordHash (String passwordHash){
        this.passwordHash = passwordHash;
    }

}
