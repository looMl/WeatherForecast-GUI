package projecteaster;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import javafx.util.Duration;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.json.JSONException;

/**
 * Controller of the Main window
 *
 * @author Luca Landolfo
 */
public class FXMLDocumentController implements Initializable, Serializable {

    //Attributes
    private final WeatherData wd = new WeatherData();

    //FXML GUI variables
    @FXML
    private Label tempLabel, tempMinLabel, tempMaxLabel, descriptionLabel,
            pressureLabel, windSpeedLabel, humidityLabel, cityLabel, textTemperature,
            textHumidity, textPressure, textWindSpeed, textConditions, textCurrent,
            textSerialize;
    @FXML
    private ImageView img, tempMinImg, tempMaxImg;
    @FXML
    private ListView<String> cityListView;

    /**
     * Method that when clicked opens the second view.
     *
     * @param event
     */
    @FXML
    void addCityButtonAction(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SecondWindow.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException e) {
            System.out.println("Can't load the window.");
        }
    }

    /**
     * Method that gets which item is selected in the listview and deletes it
     *
     * @param event
     */
    @FXML
    void removeCityButtonAction(MouseEvent event) {
        String item = cityListView.getSelectionModel().getSelectedItem();
        Main.dataList.remove(item);
        hideLabels();
    }

    /**
     * When the button is clicked, the serialize method in the Main class is
     * called
     *
     * @param event
     */
    @FXML
    void serializeItems(MouseEvent event) {
        try {
            Main.serialize();
            textSerialize.setText("Data saved successfully!");
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        timer();
    }

    /**
     * Method that after 3 seconds makes the textSerialize label disappear
     */
    private void timer() {
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(3000),
                ae -> textSerialize.setText("")));
        timeline.play();
    }

    /**
     * When the list view is clicked it gets which item has been selected and,
     * after getting the name and the country code, makes an API request
     * everytime the item is clicked
     */
    private void getValuesOnMouseClick() {
        cityListView.setOnMouseClicked((MouseEvent e) -> {
            try {
                String item = cityListView.getSelectionModel().getSelectedItem();
                if (item != null) {
                    String[] stringArray = item.split(", ");
                    wd.getWeather(stringArray[0], stringArray[1]);
                    cityLabel.setText(stringArray[0].toUpperCase());
                    fillLabels(wd);
                } else {
                    System.out.println("Nothing has been clicked.");
                }
            } catch (JSONException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    /**
     * When called fills all the labels on the main window with data gotten from
     * the API request
     *
     * @param wd
     */
    private void fillLabels(WeatherData wd) {
        showLabels();
        tempLabel.setText(wd.getTemperature() + " °C");
        tempMinLabel.setText(wd.getTempMin() + "°");
        tempMinImg.setImage(new Image("file:img/downArrow.png"));
        tempMaxLabel.setText(wd.getTempMax() + "°");
        tempMaxImg.setImage(new Image("file:img/upArrow.png"));
        descriptionLabel.setText(wd.getDescription());
        pressureLabel.setText(wd.getPressure() + " hPa");
        windSpeedLabel.setText(wd.getWindSpeed() + " Km/h");
        humidityLabel.setText(wd.getHumidity() + "%");
        img.setImage(new Image(ImageHandler.getImage(wd.getIcon())));
    }

    /**
     * Everytime this method is called it hides all the labels on the main
     * window
     */
    private void hideLabels() {
        cityLabel.setVisible(false);
        textHumidity.setVisible(false);
        textPressure.setVisible(false);
        textWindSpeed.setVisible(false);
        textConditions.setVisible(false);
        textCurrent.setVisible(false);
        textTemperature.setVisible(false);
        tempLabel.setVisible(false);
        tempMinLabel.setVisible(false);
        tempMinImg.setVisible(false);
        tempMaxLabel.setVisible(false);
        tempMaxImg.setVisible(false);
        descriptionLabel.setVisible(false);
        pressureLabel.setVisible(false);
        windSpeedLabel.setVisible(false);
        humidityLabel.setVisible(false);
        img.setVisible(false);
    }

    /**
     * Everytime this method is called it shows all the labels on the main
     * window
     */
    private void showLabels() {
        cityLabel.setVisible(true);
        textHumidity.setVisible(true);
        textPressure.setVisible(true);
        textWindSpeed.setVisible(true);
        textConditions.setVisible(true);
        textCurrent.setVisible(true);
        textTemperature.setVisible(true);
        tempLabel.setVisible(true);
        tempMinLabel.setVisible(true);
        tempMinImg.setVisible(true);
        tempMaxLabel.setVisible(true);
        tempMaxImg.setVisible(true);
        descriptionLabel.setVisible(true);
        pressureLabel.setVisible(true);
        windSpeedLabel.setVisible(true);
        humidityLabel.setVisible(true);
        img.setVisible(true);
    }

    /**
     * Initilize method which is called everytime the app is started
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO 
        hideLabels();
        Main.deserialize();
        cityListView.setItems(Main.dataList);
        getValuesOnMouseClick();
    }

}
