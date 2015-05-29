package main;

import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 *
 * @author D.Tsogtbayar
 */

public class Tsogtbayar extends Application {
    
    private final String TITLE = "D.Tsogtbayar";
    private final double WIDTH = 800;
    private final double HEIGHT = 400;
    
    private static ObservableList<User> datas = FXCollections.observableArrayList();
    private TableView table;
    private TableColumn code = new TableColumn("Код"),
            name = new TableColumn("Нэр"),
            age = new TableColumn("Нас"),
            gender = new TableColumn("Хүйс"),
            career = new TableColumn("Мэргэжил"),
            phone = new TableColumn("Утас"),
            address = new TableColumn("Гэрийн хаяг");
    
    
    private Button add = new Button("Нэмэх"),
            edit = new Button("Засах"),
            delete = new Button("Устгах"),
            exit = new Button("Гарах");
    private VBox control = new VBox(add, new Label(" "), edit, new Label(" "), delete, new Label(" "), exit);
    
    private BorderPane root = new BorderPane();
    private Scene scene = new Scene(root, WIDTH, HEIGHT);
    
    private static Label error = new Label();
    
    @Override
    public void start(Stage stage) {
        root.setStyle("-fx-background-color: white");
        
        table = new TableView(datas);
        
        root.setPadding(new Insets(25, 10, 25, 10));
        code.setCellValueFactory(new PropertyValueFactory<>("code"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        age.setCellValueFactory(new PropertyValueFactory<>("age"));
        gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        career.setCellValueFactory(new PropertyValueFactory<>("career"));
        phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        
        refreshTable();
        
        code.setPrefWidth(100);
        name.setPrefWidth(100);
        age.setPrefWidth(50);
        gender.setPrefWidth(70);
        career.setPrefWidth(100);
        phone.setPrefWidth(100);
        address.setPrefWidth(150);
        
        table.getColumns().addAll(code, name, age, gender, career, phone, address);
            
        control.setPadding(new Insets(0, 15, 0, 15));
        
        error.setPadding(new Insets(15, 15, 15, 15));
        error.setStyle("-fx-text-fill: #BA1C1C; -fx-font-weight: bold; -fx-font-family: Berlin Sans FB; -fx-font-size: 18px;");
        root.setTop(error);
        root.setCenter(table);
        root.setRight(control);
        
        add.setPrefSize(150, 30);
        edit.setPrefSize(150, 30);
        delete.setPrefSize(150, 30);
        exit.setPrefSize(150, 30);
        
        add.setOnAction(ae -> {
            new AddWindow(new Stage());
        });
        
        edit.setOnAction(ae -> {
            if (table.getSelectionModel().getSelectedItem() != null) {
                setError("");
                new EditWindow(new Stage(), (User) table.getSelectionModel().getSelectedItem());
            } else {
                setError("Та 1 мөр мэдээлэл сонгоно уу!");
            }
        });
        
        delete.setOnAction(ae -> {
            if (table.getSelectionModel().getSelectedItem() != null) {
                int answer = JOptionPane.showConfirmDialog(null, "Үнэхээр устгамаар байна уу?");
                if (answer == JOptionPane.YES_OPTION) {
                    User user = (User) table.getSelectionModel().getSelectedItem();
                    String query = "delete from users where id = "+user.getId();
                    DB.delete(query);
                    refreshTable();
                }
            } else {
                setError("Та 1 мөр мэдээлэл сонгоно уу!");
            }
        });
        
        exit.setOnAction(ae -> {
            stage.close();
            Login login = new Login();
            login.start(new Stage()); 
        });
        
        stage.setTitle(TITLE);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setMaximized(false);
        stage.show();
    }
    
    public static void refreshTable() {
        ResultSet res = DB.select("select * from users");
        datas.clear();
        try {
            while (res.next()) {
                User user = new User(
                        res.getObject("id").toString(),
                        res.getObject("code").toString(),
                        res.getObject("name").toString(),
                        res.getObject("age").toString(),
                        res.getObject("gender").toString(),
                        res.getObject("career").toString(),
                        res.getObject("phone").toString(),
                        res.getObject("address").toString()
                );
                datas.add(user);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public static void setError(String errorMessage) {
        error.setText(errorMessage);
        Thread t = new Thread(() -> {
            int i = 0;
            while (i != 10) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                i++;
                error.setVisible(true);
            }
            error.setVisible(false);
        });
    }
    
}
