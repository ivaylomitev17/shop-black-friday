package tu.sofia.shop.black.friday.Exceptions;

public class IncorrectUsernameException extends Exception {
    @Override
    public String getMessage (){
        return "Incorrect username!";
    }
}
