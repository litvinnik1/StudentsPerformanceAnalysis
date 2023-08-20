package com.example.kursova_oop;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;

import static java.sql.DriverManager.getConnection;

public class AnalysisController extends DatabaseConfigs {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button analysis_button2;

    @FXML
    private Button back_button2;

    @FXML
    private TableColumn<Student, Integer> compmath_column2;

    @FXML
    private TableColumn<Student, Integer> cyberwars_column2;

    @FXML
    private TableColumn<Student, Integer> english_column2;

    @FXML
    private TableColumn<Student, String> firstname_column2;

    @FXML
    private TableColumn<Student, String> group_column2;

    @FXML
    private TableColumn<Student, Integer> id_column2;

    @FXML
    private TableColumn<Student, String> lastname_column2;

    @FXML
    private TableColumn<Student, Integer> oop_column2;

    @FXML
    private TableColumn<Student, Integer> result_column;
    @FXML
    private TableView<Student> analysis_table;

    @FXML
    private Button update_button2;


    @FXML
    void initialize() {
        showStudents();
        update_button2.setOnAction(event ->{
            showStudents();
        });
        back_button2.setOnAction(event -> {
            openNewScene("/com/example/kursova_oop/dashboard.fxml");
        });
//        analysis_button2.setOnAction(event -> {
//            if(Integer.parseInt(result_column.getText())<60 || Integer.parseInt(result_column.getText())>100){
//                Dialog<ButtonType> dialog = new Dialog<>();
//                dialog.setTitle("Помилка !!!");
//                dialog.setHeaderText("Число має бути >=60 та <=100!");
//                ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
//                dialog.getDialogPane().getButtonTypes().addAll(okButton);
//                dialog.show();
//            }else{
//                showStudents();
//            }
//        });
    }
    public void showStudents(){
        ObservableList<Student> list = getStudentsList();
        PreparedStatement ps = null;

        id_column2.setCellValueFactory(new PropertyValueFactory<Student,Integer>("id"));
        group_column2.setCellValueFactory(new PropertyValueFactory<Student, String>("group"));
        firstname_column2.setCellValueFactory(new PropertyValueFactory<Student, String>("firstName"));
        lastname_column2.setCellValueFactory(new PropertyValueFactory<Student, String>("lastName"));
        compmath_column2.setCellValueFactory(new PropertyValueFactory<Student, Integer>("compmath"));
        english_column2.setCellValueFactory(new PropertyValueFactory<Student, Integer>("english"));
        cyberwars_column2.setCellValueFactory(new PropertyValueFactory<Student, Integer>("cyberwars"));
        oop_column2.setCellValueFactory(new PropertyValueFactory<Student, Integer>("oop"));
        result_column.setCellValueFactory(new PropertyValueFactory<Student,Integer>("mean"));




        analysis_table.setItems(list);
        int markSum = 0;
        for (Student k:list
             ) {
            markSum += k.getCompmath();
            markSum += k.getEnglish();
            markSum += k.getCyberwars();
            markSum += k.getOop();

            int mean = (int) markSum/4;
            Connection conn;
            try{
                conn = DriverManager.getConnection("jdbc:mysql://"+dbHost+":"+dbPort+"/"+dbName, dbUser, dbPass);
                String query = "UPDATE students_table SET mean =?  WHERE id =? ";
                ps = conn.prepareStatement(query);
                ps.setInt(1,mean);
                ps.setInt(2,k.getId());
                ps.executeUpdate();

            }catch (Exception ex){
                System.out.println("Error: "+ex.getMessage());
            }
//            String query = "UPDATE students_table SET mean = '"+mean+" WHERE id = '" +k.getId();
//            ps = getConnection().prepareStatement(query);
            markSum = 0;
            mean = 0;


        }
    }

    public ObservableList<Student> getStudentsList(){
//        String discipline = null;
//        if (analisMath.isSelected()) {
//            discipline = ConstStudent.STUDENTS_MATH;
//        } else if (analisEnglish.isSelected()) {
//            discipline = ConstStudent.STUDENTS_ENGLISH;
//        } else if (analisOOP.isSelected()) {
//            discipline = ConstStudent.STUDENTS_OOP;
//        }else if (analisMeneg.isSelected()) {
//            discipline = ConstStudent.STUDENTS_MANAG;
//        }
        ObservableList<Student> studentsList = FXCollections.observableArrayList();
        Connection conn = getConnection();
        String query = "SELECT * FROM students_table";
//        String query = "SELECT * FROM students WHERE " +
//                discipline +">="+ Integer.parseInt(analisFrom.getText()) + " AND " + discipline +"<="+ Integer.parseInt(analisTo.getText()) + "";
        Statement st;
        ResultSet rs;
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Student student;
            while(rs.next()){
                student = new Student(rs.getInt("id"),rs.getString("groupname"), rs.getString("firstname"), rs.getString("lastname"), rs.getInt("compmath"),
                        rs.getInt("english"), rs.getInt("cyberwars"), rs.getInt("oop"),rs.getInt("mean"));
//                student = new Student(rs.getInt("id"), rs.getString("firstname"), rs.getString("lastname"), rs.getInt("math"),
//                        rs.getInt("oop"), rs.getInt("english"), rs.getInt("manag"));
                studentsList.add(student);
                System.out.println(student);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return studentsList;
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
    public void getStudentMarks (){
        Connection conn = getConnection();
        try {
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery("SELECT compmath, english, cyberwars, oop FROM students_table");
            while (rs.next()){
                String str1 = rs.getString(1);
                String str2 = rs.getString(2);
                String str3 = rs.getString(3);
                String str4 = rs.getString(4);


            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

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
    //  Відкриває нове вікно
    public void openNewScene(String window) {
        back_button2.getScene().getWindow().hide();
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
}

