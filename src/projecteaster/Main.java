package projecteaster;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Main class that starts the app
 * 
 * @author Luca Landolfo
 */
public class Main extends Application implements Serializable {
    
    //static ObservableList to be used everywhere
    static ObservableList<String> dataList = FXCollections.observableArrayList();

    
    /**
     * Getter dataList
     * @return
     */
    public static ObservableList<String> getDataList() {
        return dataList;
    }

    /**
     * Setter dataList
     * @param dataList
     */
    public static void setDataList(ObservableList<String> dataList) {
        Main.dataList = dataList;
    }
    
    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));

        Scene scene = new Scene(root);
        stage.getIcons().add(new Image("file:img/icon.png"));
        stage.setTitle("Weather");
        stage.setResizable(true);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Serialize ArrayList items into a file.
     * @throws IOException 
     */
    static void serialize() throws IOException {
        try (FileOutputStream fos = new FileOutputStream("./data/cities.bin")) {
            ObjectOutputStream out = new ObjectOutputStream(fos);
            out.writeObject(new ArrayList<String>(getDataList()));
            out.close();
        }
    }
    
    /**
     * Deserialize ArrayList items from file.
     */
    static void deserialize() {
        try {
            FileInputStream fileIn = new FileInputStream("./data/cities.bin");
            ObjectInputStream ois = new ObjectInputStream(fileIn);
            List<String> list = (List<String>) ois.readObject();
            setDataList(FXCollections.observableList(list));
            ois.close();
            fileIn.close();
        } catch (FileNotFoundException ex) {
            try {
                serialize();
            } catch (IOException e) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
            }
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}