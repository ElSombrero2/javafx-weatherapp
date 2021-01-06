package com.weather.app;

import com.weather.models.Date;
import com.weather.models.Weather;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    public static Weather weather;
    public static final Date date=new Date();
    public static final String KEY="&appid=e0eefe934ad399573091c64a2cd5f4c8&lang=fr";
    public static final String URL="http://api.openweathermap.org/data/2.5/weather?";
    public static String query="q=Antananarivo";
    public static AnchorPane index;
    public static AnchorPane loading;
    private static final Delta delta=new Delta();
    public static Stage Stage;


    public static void main(String[] args) { launch(args);}

    @Override
    public void start(Stage stage) throws Exception {
        Main.Stage=stage;
        loading=FXMLLoader.load(getClass().getResource("..\\views\\loading.fxml"));
        Group root=new Group();
        root.getChildren().add(loading);
        Scene scene=new Scene(root,600,400);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
        root.setOnMousePressed ((e)-> {
                delta.x= stage.getX ()-e.getScreenX ();
                delta.y= stage.getY ()-e.getScreenY ();
        });
        root.setOnMouseDragged ((e)-> {
                stage.setX (e.getScreenX ()+delta.x);
                stage.setY (e.getScreenY ()+delta.y);
        });
        new Thread(() -> {
            try {
                Thread.sleep(1800);
                var res = Utils.getData(URL + query + KEY);
                weather = new Weather(res);
                index = FXMLLoader.load(getClass().getResource("..\\views\\index.fxml"));
                Thread.sleep(1800);
                Platform.runLater(() -> {
                    root.getChildren().clear();
                    root.getChildren().add(index);
                });
            } catch (Exception e) {
               Platform.runLater(()->Utils.showAlert("Il semblerai  que votre connexion internet soit coupée", stage));
            }
        }).start();
    }
}
class Delta{double x,y;}
