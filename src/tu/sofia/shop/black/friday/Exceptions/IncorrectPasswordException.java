package tu.sofia.shop.black.friday.Exceptions;

public class IncorrectPasswordException extends Exception {
    @Override
    public String getMessage (){
        return "Incorrect password!";
    }
}
