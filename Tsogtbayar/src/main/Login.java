package main;

import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Login extends Application {

    @Override
    public void start(Stage primaryStage) {
        Label err = new Label();
        err.setPadding(new Insets(10, 10, 10, 10));
        err.setStyle("-fx-text-fill: #BA1C1C; -fx-font-weight: bold; -fx-font-family: Berlin Sans FB; -fx-font-size: 16px;");
        TextField username = new TextField();
        PasswordField password = new PasswordField();
        Button submit = new Button("Нэвтрэх");
        Button exit = new Button("Гарах");

        username.setPromptText("Нэвтрэх нэр");
        password.setPromptText("Нэвтрэх нууц үг");

        username.setMaxSize(175, 30);
        password.setMaxSize(175, 30);

        username.setOnKeyPressed(ae -> {
            if (ae.getCode() == KeyCode.ENTER) {
                if (!username.getText().isEmpty()) {
                    if (!password.getText().isEmpty()) {
                        err.setText("");
                        String user = username.getText();
                        String pass = password.getText();

                        String query = "select * from accounts where username = '" + user + "' && password = '" + pass + "'";
                        ResultSet res = DB.select(query);
                        boolean login = false;
                        try {
                            while (res.next()) {
                                login = true;
                            }
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                        if (login) {
                            Tsogtbayar tsogtbayar = new Tsogtbayar();
                            tsogtbayar.start(primaryStage);
                        } else {
                            err.setText("Нэр эсвэл нууц үг буруу байна.");
                        }
                    } else {
                        err.setText("Нууц үг хоосон байна");
                    }
                } else {
                    err.setText("Нэвтрэх нэр хоосон байна");
                }
            }
        });

        submit.setMaxSize(175, 30);
        exit.setMaxSize(175, 30);

        VBox cont = new VBox(err, username, new Label(" "), password, new Label(" "), submit, new Label(" "), exit);
        cont.setMaxSize(350, 100);
        cont.setAlignment(Pos.CENTER);
        StackPane root = new StackPane();
        root.getChildren().add(cont);

        Scene scene = new Scene(root, 800, 400);

        submit.setOnAction(ae -> {
            if (!username.getText().isEmpty()) {
                if (!password.getText().isEmpty()) {
                    err.setText("");
                    String user = username.getText();
                    String pass = password.getText();

                    String query = "select * from accounts where username = '" + user + "' && password = '" + pass + "'";
                    ResultSet res = DB.select(query);
                    boolean login = false;
                    try {
                        while (res.next()) {
                            login = true;
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                    if (login) {
                        Tsogtbayar tsogtbayar = new Tsogtbayar();
                        tsogtbayar.start(primaryStage);
                    } else {
                        err.setText("Нэр эсвэл нууц үг буруу байна.");
                    }
                } else {
                    err.setText("Нууц үг хоосон байна");
                }
            } else {
                err.setText("Нэвтрэх нэр хоосон байна");
            }
        });
        exit.setOnAction(ae -> {
            primaryStage.close();
        });

        primaryStage.setTitle("Tsogtbayar");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
