package tu.sofia.shop.black.friday.exceptions;

public class NewPriceLowerThanMinimumException extends Exception {
    @Override
    public String getMessage (){
        return "The new price of the product cannot be lower than the minimum price!";
    }
}
