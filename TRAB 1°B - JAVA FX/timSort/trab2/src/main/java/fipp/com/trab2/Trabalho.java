package fipp.com.trab2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Trabalho extends Application {
    AnchorPane pane;

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Pesquisa e Ordenacao");
        pane = new AnchorPane();
        pane.setStyle("-fx-background-color: #000000;");

        Button timSort = new Button("Tim Sort");
        timSort.setLayoutX(150);
        timSort.setLayoutY(84);
        timSort.setStyle("""
            -fx-background-color: #2196F3;
            -fx-text-fill: white;
            -fx-font-size: 14px;
            -fx-background-radius: 5;
            -fx-cursor: hand;
        """);
        timSort.setOnAction(event -> {
            TimSortApplication timSortApp = new TimSortApplication();
            try {
                stage.close();
                timSortApp.start(new Stage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        pane.getChildren().addAll(timSort);

        Scene scene = new Scene(pane, 350, 200);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}