package main;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class EditWindow {
    private final double WIDTH = 325;
    private final double HEIGHT = 390;
    
    private Stage stage;
    private GridPane root = new GridPane();
    private Scene scene = new Scene(root, WIDTH, HEIGHT);
    
    private Label[] labels = new Label[7];
    
    private Button add = new Button("Хадгалах"), exit = new Button("Гарах");
    
    private TextField code = new TextField();
    private TextField name = new TextField();
    private TextField age = new TextField();
    private ToggleGroup gender = new ToggleGroup();
    private RadioButton male = new RadioButton("Эрэгтэй");
    private RadioButton female = new RadioButton("Эмэгтэй");
    private TextField career = new TextField();
    private TextField phone = new TextField();
    private TextArea address = new TextArea();
    
    public EditWindow(Stage stage, User user) {
        this.stage = stage;
        root.setPadding(new Insets(15, 15, 15, 15));
        
        for (int i = 0; i < labels.length; i++) {
            labels[i] = new Label();
            switch (i) {
                case 0: labels[i].setText("Код: "); break;
                case 1: labels[i].setText("Нэр: "); break;
                case 2: labels[i].setText("Нас: "); break;
                case 3: labels[i].setText("Хүйс: "); break;
                case 4: labels[i].setText("Мэргэжил: "); break;
                case 5: labels[i].setText("Утас: "); break;
                case 6: labels[i].setText("Гэрийн хаяг: "); break;
            }
            labels[i].setMinSize(100, 25);
            labels[i].setAlignment(Pos.CENTER_LEFT);
            root.add(labels[i], 0, i);
        }
        
        
        code.setMaxSize(180, 25);
        name.setMaxSize(180, 25);
        age.setMaxSize(180, 25);
        male.setMaxSize(90, 25);
        female.setMaxSize(90, 25);
        career.setMaxSize(180, 25);
        phone.setMaxSize(180, 25);
        address.setMaxSize(180, 75);
        
        address.setWrapText(true);
        
        code.setText(user.getCode());
        name.setText(user.getName());
        age.setText(user.getAge());
        career.setText(user.getCareer());
        phone.setText(user.getPhone());
        address.setText(user.getAddress());
        
        if (user.getGender().equalsIgnoreCase("эрэгтэй")) {
            male.setSelected(true);
            female.setSelected(false);
        } else if (user.getGender().equalsIgnoreCase("эмэгтэй")) {
            male.setSelected(false);
            female.setSelected(true);
        }
        
        male.setToggleGroup(gender);
        female.setToggleGroup(gender);
        
        root.add(code, 1, 0);
        root.add(name, 1, 1);
        root.add(age, 1, 2);
        root.add(new FlowPane(male, new Label(" "), female), 1, 3);
        root.add(career, 1, 4);
        root.add(phone, 1, 5);
        root.add(address, 1, 6);
        
        add.setPrefSize(180, 25);
        exit.setPrefSize(180, 25);
        
        root.setVgap(10);
        root.setHgap(10);
        root.add(add, 1, 7);
        root.add(exit, 1, 8);
        
        add.setOnAction(ae -> {
            int errors = 0;
            if (code.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Код хоосон байна");
                errors++;
            } else if (name.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Нэр хоосон байна");
                errors++;
            } else if (age.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Нас хоосон байна");
                errors++;
            } else if (gender.getSelectedToggle() == null) {
                JOptionPane.showMessageDialog(null, "Хүйс сонгогдоогүй байна");
                errors++;
            } else if (career.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Мэргэжил хоосон байна");
                errors++;
            } else if (phone.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Утас хоосон байна");
                errors++;
            } else if (address.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Хаяг хоосон байна");
                errors++;
            }
            if (errors == 0) {
                String codeStr = "", nameStr = "", genderStr = "", careerStr = "", addressStr = "";
                int ageStr = 0, phoneStr = 0;
                try {
                    codeStr = code.getText();
                    nameStr = name.getText();
                    ageStr = Integer.parseInt(age.getText());
                    genderStr = ((RadioButton) gender.getSelectedToggle()).getText().toLowerCase();
                    careerStr = career.getText();
                    phoneStr = Integer.parseInt(phone.getText());
                    addressStr = address.getText();

                    String query = "update users set code = '" + codeStr + "', name = '" + nameStr + "', age = "+ageStr+", gender = '"+genderStr+"', career = '"+careerStr+"', phone = "+phoneStr+", address = '"+addressStr+"' where id = "+user.getId();

                    DB.update(query);
                    Tsogtbayar.refreshTable();
                    stage.close();
                    
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Өгөгдөл буруу орсон байна.");
                }
            }
        });
        exit.setOnAction(ae -> {
            stage.close();
        });
        
        this.stage.setScene(scene);
        this.stage.show();
        this.stage.setResizable(false);
        this.stage.setMaximized(false);
    }
}
