package projecteaster;

import java.io.Serializable;

/**
 * Class that handles icons gotten from the API request.
 *
 * @author Landolfo Luca
 */
public class ImageHandler implements Serializable {

    /**
     * Method that selects which icon to show
     *
     * @param icon
     * @return
     */
    static String getImage(String icon) {
        switch (icon) {
            case "01d":
                return "file:img/clearOffice.png";
            case "01n":
                return "file:img/moonOffice.png";
            case "02d":
                return "file:img/fewOffice.png";
            case "02n":
                return "file:img/fewNightOffice.png";
            case "03d":
            case "03n":
                return "file:img/cloudsOffice.png";
            case "04d":
            case "04n":
                return "file:img/brokenOffice.png";
            case "09d":
            case "09n":
                return "file:img/showerOffice.png";
            case "10d":
            case "10n":
                return "file:img/rainOffice.png";
            case "11n":
            case "11d":
                return "file:img/thunderstormOffice.png";
            case "13d":
            case "13n":
                return "file:img/snowOffice.png";
            case "50d":
            case "50n":
                return "file:img/hazeOffice.png";
        }
        return "file:img/clearOffice.png";
    }
}
