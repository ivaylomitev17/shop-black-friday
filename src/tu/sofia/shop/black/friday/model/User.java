package tu.sofia.shop.black.friday.model;

public class User {

    private String username;
    private String passwordHash;
    private boolean isEmployee;


    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setPasswordHash (String passwordHash){
        this.passwordHash = passwordHash;
    }
}
