package com.example.kursova_oop;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;

import javafx.fxml.FXML;
import javafx.stage.Stage;

public class DashboardController extends DatabaseConfigs {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<Student, Integer> compmath_column;

    @FXML
    private TableColumn<Student, Integer> cyberwars_column;

    @FXML
    private TableColumn<Student, Integer> english_column;

    @FXML
    private TableColumn<Student, String> firstname_column;

    @FXML
    private TableColumn<Student, String> group_column;
    @FXML
    private TableColumn<Student, Integer> id_column;

    @FXML
    private TableColumn<Student, String> lastname_column;

    @FXML
    private TableColumn<Student, Integer> oop_column;

    @FXML
    private TableView<Student> signup_table;
    @FXML
    private Button signup_update;

    @FXML
    private Button signup_analysis;
    @FXML
    private TextField signup_id;

    @FXML
    private Button signup_back;

    @FXML
    private TextField signup_compmath;

    @FXML
    private TextField signup_cyberwars;

    @FXML
    private Button signup_delete;

    @FXML
    private TextField signup_english;

    @FXML
    private TextField signup_group;

    @FXML
    private TextField signup_lastname;

    @FXML
    private TextField signup_oop;

    @FXML
    private Button signup_save;

    @FXML
    private TextField singup_firstname;

    @FXML
    void handleMouseAction(MouseEvent event) {
        Student student = signup_table.getSelectionModel().getSelectedItem();
        signup_id.setText("" + student.getId());
        singup_firstname.setText(student.getFirstName());
        signup_lastname.setText(student.getLastName());
        signup_compmath.setText("" + student.getCompmath());
        signup_english.setText("" + student.getEnglish());
        signup_cyberwars.setText("" + student.getCyberwars());
        signup_oop.setText("" + student.getOop());
    }

    @FXML
    void initialize() {
        showStudents();
        signup_save.setOnAction(event -> {
            if (signup_lastname.getText().matches("\\d+") || signup_lastname.getText().equals("")){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Помилка!");
                alert.setHeaderText(null);
                alert.setContentText("Поле з прізвищем не заповнено!");
                alert.showAndWait();
            } else if(singup_firstname.getText().matches("\\d+") || singup_firstname.getText().equals("")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Помилка!");
                alert.setHeaderText(null);
                alert.setContentText("Поле з ім'ям не заповнено!");
                alert.showAndWait();
//            }else if(signup_id.getText().matches("\\d+") || signup_id.getText().equals("")) {
//                Alert alert = new Alert(Alert.AlertType.ERROR);
//                alert.setTitle("Помилка!");
//                alert.setHeaderText(null);
//                alert.setContentText("Поле з ID не заповнено!");
//                alert.showAndWait();
            }else if(signup_group.getText().matches("\\d+") || signup_group.getText().equals("")){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Помилка!");
                    alert.setHeaderText(null);
                    alert.setContentText("Поле з групою не заповнено!");
                    alert.showAndWait();
            }else if(Integer.parseInt(signup_compmath.getText())<60 || Integer.parseInt(signup_compmath.getText())>100 || signup_compmath.getText().equals("")){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Помилка");
                alert.setHeaderText(null);
                alert.setContentText("Оцінки мають бути від 60 до 100 балів!");
                alert.showAndWait();
            }else if(Integer.parseInt(signup_english.getText())<60 || Integer.parseInt(signup_english.getText())>100 || signup_english.getText().equals("")){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Помилка");
                alert.setHeaderText(null);
                alert.setContentText("Оцінки мають бути від 60 до 100 балів!");
                alert.showAndWait();
            }else if(Integer.parseInt(signup_cyberwars.getText())<60 || Integer.parseInt(signup_cyberwars.getText())>100 || signup_cyberwars.getText().equals("")){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Помилка");
                alert.setHeaderText(null);
                alert.setContentText("Оцінки мають бути від 60 до 100 балів!");
                alert.showAndWait();
            }else if(Integer.parseInt(signup_oop.getText())<60 || Integer.parseInt(signup_oop.getText())>100 || signup_oop.getText().equals("")){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Помилка");
                alert.setHeaderText(null);
                alert.setContentText("Оцінки мають бути від 60 до 100 балів!");
                alert.showAndWait();
            }else {
                signUpNewStudent();
            }
        });
        signup_update.setOnAction(event -> {
            showStudents();
        });
        signup_delete.setOnAction(event -> {
            deleteStudent();
        });
        signup_back.setOnAction(event -> {
            openNewScene("/com/example/kursova_oop/hello-view.fxml");
        });
        signup_analysis.setOnAction(event -> {
            openNewScene("/com/example/kursova_oop/analysis.fxml");
        });
    }
    private void signUpNewStudent() {
        table();
    }
    public void openNewScene(String window) {
        signup_back.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(LoginController.class.getResource(window));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }
    public void table(){
        DatabaseHandler dbHandler = new DatabaseHandler();

        int id = Integer.parseInt(signup_id.getText());
        String group = signup_group.getText();
        String firstName = singup_firstname.getText();
        String lastName = signup_lastname.getText();
        int compmath = Integer.parseInt(signup_compmath.getText());
        int cyberwars = Integer.parseInt(signup_cyberwars.getText());
        int english = Integer.parseInt(signup_english.getText());
        int oop = Integer.parseInt(signup_oop.getText());

        int mean = Integer.parseInt("0");
        Student student = new Student(id, group, firstName, lastName, compmath, oop, english, cyberwars,mean);
        dbHandler.signUpStudent(student);
        showStudents();
    }
    public Connection getConnection(){
        Connection conn;
        try{
            conn = DriverManager.getConnection("jdbc:mysql://"+dbHost+":"+dbPort+"/"+dbName, dbUser, dbPass);
            return conn;
        }catch (Exception ex){
            System.out.println("Error: "+ex.getMessage());
            return null;
        }
    }
    public ObservableList<Student> getStudentsList(){
        ObservableList<Student> studentsList = FXCollections.observableArrayList();
        Connection conn = getConnection();
        String query = "SELECT * FROM students_table";
        Statement st;
        ResultSet rs;
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Student student;
            while(rs.next()){
                student = new Student(rs.getInt("id"),rs.getString("groupname"), rs.getString("firstname"), rs.getString("lastname"), rs.getInt("compmath"),
                        rs.getInt("english"), rs.getInt("cyberwars"), rs.getInt("oop"),rs.getInt("mean"));
                studentsList.add(student);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return studentsList;
    }
    public void showStudents(){
        ObservableList<Student> list = getStudentsList();

        id_column.setCellValueFactory(new PropertyValueFactory<Student,Integer>("id"));
        group_column.setCellValueFactory(new PropertyValueFactory<Student, String>("group"));
        firstname_column.setCellValueFactory(new PropertyValueFactory<Student, String>("firstName"));
        lastname_column.setCellValueFactory(new PropertyValueFactory<Student, String>("lastName"));
        compmath_column.setCellValueFactory(new PropertyValueFactory<Student, Integer>("compmath"));
        english_column.setCellValueFactory(new PropertyValueFactory<Student, Integer>("english"));
        cyberwars_column.setCellValueFactory(new PropertyValueFactory<Student, Integer>("cyberwars"));
        oop_column.setCellValueFactory(new PropertyValueFactory<Student, Integer>("oop"));



        signup_table.setItems(list);
    }
    private void updateStudent(){
//        String query = "UPDATE students_table SET groupname = '"+signup_group.getText()+", firstname = "+singup_firstname.getText()+", lastname = "+signup_lastname.getText()+", " +
//                "math = "+signup_compmath.getText()+", english = "+signup_english.getText()+", cyberwars = "+signup_cyberwars.getText()+
//                ", oop = "+signup_oop.getText()+" WHERE id = '"+ signup_id.getText()+"";
        String query = "UPDATE students_table SET groupname = '"+signup_group.getText()+", firstname = "+singup_firstname.getText()+", lastname = "+signup_lastname.getText()+", " +
                "math = "+signup_compmath.getText()+", english = "+signup_english.getText()+", cyberwars = "+signup_cyberwars.getText()+
                ", oop = "+signup_oop.getText()+" WHERE id = '"+ signup_id.getText()+"";
        executeQuery(query);
        showStudents();
    }
    private void deleteStudent(){
        String query = "DELETE FROM students_table WHERE id = "+signup_id.getText()+"";
        executeQuery(query);
        showStudents();
    }
    private void executeQuery(String query){
        Connection conn = getConnection();
        Statement st;
        try{
            st = conn.createStatement();
            st.executeUpdate(query);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}





