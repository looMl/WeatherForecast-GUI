package projecteaster;

/**
 * Class that defines custom exception "City already exists".
 * @author Landolfo Luca
 */
public class CityAlreadyExistsException extends Exception{
    
    /**
     * Constructor
     * @param s
     */
    public CityAlreadyExistsException(String s) {
        super(s);
    }

}