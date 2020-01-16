package tu.sofia.shop.black.friday.exceptions;

public class IncorrectUsernameException extends RuntimeException {
    @Override
    public String getMessage (){
        return "Incorrect username!";
    }
}
