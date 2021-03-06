package tu.sofia.shop.black.friday.model;

public class User {

    /*
    Better example of java class structure is the following:
        1. constants
        2. fields
        3. constructors
        4. getters & setters
        5. hash(), equals(), toString() methods
        6. other methods (if any)

        NOTE: Each of the above sections is organized by the access modifiers in the following order:
            1. public
            2. protected
            3. default
            4. private
     */

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
