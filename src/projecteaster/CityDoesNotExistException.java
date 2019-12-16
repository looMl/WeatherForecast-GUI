package projecteaster;

/**
 * Class that defines custom exception "City does not exists".
 * @author Landolfo Luca
 */
public class CityDoesNotExistException extends Exception{
    
    /**
     * Constructor
     * @param s
     */
    public CityDoesNotExistException(String s) {
        super(s);
    }

}