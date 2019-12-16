package projecteaster;

import java.io.Serializable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.json.JSONException;

/**
 * FXML Add a city Controller class
 *
 * @author Landolfo Luca
 */
public class SecondWindowController implements Initializable, Serializable {

    //attributes
    private final WeatherData wd = new WeatherData();
    private String result;

    //FXML GUI variables
    @FXML
    private TextField cityNameField;
    @FXML
    private TextField countryCodeField;
    @FXML
    private Button acceptButton;
    @FXML
    private Label errorLabel;

    /**
     * Getter result
     *
     * @return
     */
    public String getResult() {
        return result;
    }

    /**
     * It basically manages input errors and checks if the new city that the
     * user wants to add is already in the ListView, if so a custom exception is
     * called, else the input is being added to the ObservableList
     *
     * @param event
     */
    @FXML
    void acceptButtonAction(MouseEvent event) {
        Stage stage = (Stage) acceptButton.getScene().getWindow();
        String cityName = cityNameField.getText();
        String cityCode = countryCodeField.getText();

        try {
            if (cityName.equals("") && cityCode.equals("")) {
                result = "Verona, IT";
                addToList(result);
                stage.close();
                //check if at least one of the strings have a minimum of 1 number in it
            } else if (!cityName.matches("(.)*(\\d)(.)*") || !cityCode.matches("(.)*(\\d)(.)*")) {
                if (wd.getWeather(cityName, cityCode)) {
                    result = cityName + ", " + cityCode;
                    addToList(result);
                    stage.close();
                } else {
                    errorLabel.setText("The city doesn't exists.");
                    cityNameField.setText("");
                    countryCodeField.setText("");
                    throw new CityDoesNotExistException("The city doesn't exists.");
                }
            } else {
                errorLabel.setText("Input error.");
                cityNameField.setText("");
                countryCodeField.setText("");
            }
        } catch (JSONException | CityAlreadyExistsException | CityDoesNotExistException ex) {
            System.out.println("" + ex);
        }
    }

    /**
     * Adds to dataList checking first if the string is already in it
     *
     * @param s
     * @throws CityAlreadyExistsException
     */
    private void addToList(String s) throws CityAlreadyExistsException {
        if (Main.dataList.contains(s)) {
            errorLabel.setText("The city already exists.");
            cityNameField.setText("");
            countryCodeField.setText("");
            throw new CityAlreadyExistsException("The city already exists.");
        } else {
            Main.dataList.add(s);
        }
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
