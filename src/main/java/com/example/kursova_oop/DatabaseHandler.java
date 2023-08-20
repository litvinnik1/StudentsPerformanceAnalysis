package com.example.kursova_oop;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class DatabaseHandler extends DatabaseConfigs {
    Connection dbConnection;
    public Connection getDbConnectionStudent() throws ClassNotFoundException, SQLException{
        String connectionString = "jdbc:mysql://"+dbHost+":"+dbPort+"/"+dbName;
        Class.forName("com.mysql.cj.jdbc.Driver");
        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);
        return dbConnection;
    }
    public void signUpStudent(Student student){
//        String insert = "INSERT INTO "+ConstStudent.STUDENT_TABLE+"("+ConstStudent.STUDENT_ID+","+ConstStudent.STUDENT_GROUP+","+ConstStudent.STUDENT_FIRSTNAME+","
//                +ConstStudent.STUDENT_LASTNAME+","+ConstStudent.STUDENT_COMPMATH+","+ConstStudent.STUDENT_ENGLISH+","+ConstStudent.STUDENT_CYBERWARS+","
//                +ConstStudent.STUDENT_OOP+")"+"VALUES(?,?,?,?,?,?,?,?)";
        String insert = "INSERT INTO students_table (id,groupname,firstname,lastname,compmath,english,cyberwars,oop) VALUES (?,?,?,?,?,?,?,?)";

        try {
            PreparedStatement prSt = getDbConnectionStudent().prepareStatement(insert);
            prSt.setInt(1,student.getId());
            prSt.setString(2, student.getGroup());
            prSt.setString(3, student.getFirstName());
            prSt.setString(4, student.getLastName());
            prSt.setInt(5, student.getCompmath());
            prSt.setInt(6, student.getOop());
            prSt.setInt(7, student.getEnglish());
            prSt.setInt(8, student.getCyberwars());
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getDbConnectionUserLogin()
            throws ClassNotFoundException, SQLException{
//        Class.forName("com.mysql.cj.jdbc.Driver");
//        Connection con=DriverManager.getConnection(
//                "jdbc:mysql://localhost:3306/kursova_bd","root","12345");
        String connectionString = "jdbc:mysql://"+ dbHost + ":"
                + dbPort + "/" + dbName;
        Class.forName("com.mysql.cj.jdbc.Driver");

        dbConnection = DriverManager.getConnection(connectionString,
                dbUser, dbPass);
        return dbConnection;
    }


    public ResultSet getUser(User user){
        ResultSet resultSet = null;

        String selectFrom = "SELECT * FROM " + ConstUserLogin.USER_TABLE + " WHERE " +
                ConstUserLogin.USER_LOGIN + "=? AND " + ConstUserLogin.USER_PASSWORD + "=?";
        try {

            PreparedStatement prSt = getDbConnectionUserLogin().prepareStatement(selectFrom);
            prSt.setString(1, user.getLogin());
            prSt.setString(2, user.getPassword());


            resultSet = prSt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
//            e.printStackTrace();
        }
        return resultSet;
    }
}
