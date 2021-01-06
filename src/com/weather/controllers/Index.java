package com.weather.controllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXSpinner;
import com.weather.app.Main;
import com.weather.app.Utils;
import com.weather.models.Weather;
import de.jensd.fx.glyphs.weathericons.WeatherIcon;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class Index implements Initializable {

    @FXML
    JFXComboBox<String> town_picker;
    @FXML
    Text name;
    @FXML
    Text date;
    @FXML
    Text jour;
    @FXML
    Text wind_speed;
    @FXML
    Text pressure;
    @FXML
    Text temp;
    @FXML
    Text temp_min;
    @FXML
    Text temp_max;
    @FXML
    Text hours;
    @FXML
    WeatherIcon icon;
    @FXML
    Text humidity;
    @FXML
    Text desc;
    @FXML
    JFXSpinner spinner;
    @FXML
    Pane pane;

    private final ObservableList<String> items= FXCollections.observableArrayList(
            "Antananarivo","Fianarantsoa","Tuléar","Toamasina","Mahajanga");


    private void update(){
        town_picker.setItems(items);
        town_picker.setPromptText("Antananarivo");
        name.setText(Main.weather.getName());
        icon.setIcon(Main.weather.getTimeIcon());
        wind_speed.setText(Main.weather.getWindSpeed()+" m/s");
        humidity.setText(Main.weather.getHumidity()+"%");
        pressure.setText(Main.weather.getPressure()+" hPa");
        desc.setText(Main.weather.getDesc());
    }

    private void updateDate(){
        date.setText(Main.date.getDate());
        jour.setText(Main.date.getDay().toUpperCase());
        hours.setText(Main.date.getHours());
        temp.setText(Main.weather.getTemp()+"°C");
        temp_max.setText(Main.weather.getTempMax()+"°C");
        temp_min.setText(Main.weather.getTempMin()+"°C");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        KeyFrame frame=new KeyFrame(Duration.millis(100),(e)->{
            Main.date.update();
            updateDate();
        });
        spinner.visibleProperty().bind(pane.visibleProperty().not());
        Timeline timeline=new Timeline(frame);
        timeline.setCycleCount(-1);
        timeline.play();
        update();
    }

    public void exit() {
        Stage stage=(Stage) town_picker.getScene().getWindow();
        stage.close();
    }

    public void minimize() {
        Stage stage=(Stage) town_picker.getScene().getWindow();
        stage.setIconified(true);
    }

    public void change() {
        pane.visibleProperty().setValue(false);
        new Thread(()->{

            try{
                Main.query="q="+town_picker.getValue();
                String res= Utils.getData(Main.URL+Main.query+Main.KEY);
                Main.weather=new Weather(res);
                Platform.runLater(()->{
                    pane.visibleProperty().setValue(true);
                    update();
                });
            }catch (Exception e)
            {
                Platform.runLater(()->Utils.showAlert("Erreur de connexion au server",Main.Stage));
            }

        }).start();
    }
}
