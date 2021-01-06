package com.weather.app;

import javafx.scene.control.Alert;
import javafx.stage.Stage;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.io.*;
import java.util.Objects;

public class Utils {
    public static void showAlert(String msg, Stage stage){
        Alert alert=new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur de connexion");
        alert.setHeaderText("Connexion Echouée");
        alert.setContentText(msg);
        alert.showAndWait();
        stage.close();
    }

    public static String getData(String url)throws IOException
    {
        var client=new OkHttpClient();
        Request req=new  Request.Builder().url(url).get().build();
        try(var res=client.newCall(req).execute()){
            return Objects.requireNonNull(res.body()).string();
        }
    }
}
