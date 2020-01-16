package tu.sofia.shop.black.friday.exceptions;

public class IncorrectPasswordException extends RuntimeException {
    @Override
    public String getMessage (){
        return "Incorrect password!";
    }
}
