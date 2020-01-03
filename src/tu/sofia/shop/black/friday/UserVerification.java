package tu.sofia.shop.black.friday;

public interface UserVerification {

    public boolean userVerification(String userdata, String pattern);
    // In interface all methods are public and abstract by default, so that any of these modifiers is redundant

    //public boolean passwordVerification(String password);
}
