package mx.edu.utez.server;

import mx.edu.utez.client.Person;
import mx.edu.utez.database.ConnectionMysql;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class Handler {

    Connection con;
    PreparedStatement pstm;
    ResultSet rs;
    Statement statement;

    public int suma(int x, int y){
        return x + y;
    }

    public boolean createUser(String name, String lastname, String email, String password){
        boolean flag = false;
        try{
            con = ConnectionMysql.getConnection();
            pstm = con.prepareCall("INSERT INTO `persons`(name, lastname, email, password, date_registered, statusP)VALUES(?,?,?,?,NOW(),1);");
            pstm.setString(1, name);
            pstm.setString(2, lastname);
            pstm.setString(3, email);
            pstm.setString(4, password);

            flag = pstm.executeUpdate() == 1;
        }catch(SQLException e){
            System.out.println("Error" + e.getMessage());
        }finally{
            ConnectionMysql.closeConnections(con, pstm);
        }

        return flag;
    }



    public List<Person> consultaRegistros(){
        List<Person> listPer = new ArrayList<>();
        try{
            con = ConnectionMysql.getConnection();
            String query = "select id, name, lastname, email, date_registered, statusP from persons;";
            statement = con.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next()){
                Person per = new Person();
                per.setId(rs.getInt("id"));
                per.setName(rs.getString("name"));
                per.setLastname(rs.getString("lastname"));
                per.setEmail(rs.getString("email"));
                per.setDate_registered(rs.getString("date_registered"));
                per.setStatus(rs.getInt("statusP"));
                listPer.add(per);
            }
        }catch(SQLException e){
            System.out.println("Error" + e.getMessage());
        }finally{
            ConnectionMysql.closeConnections(con, pstm);
        }
        return listPer;
    }

    public boolean updateUser(int id, String name, String lastname, String email, String password){
        boolean flag = false;
        try{
            con = ConnectionMysql.getConnection();
            pstm = con.prepareCall("UPDATE `persons` SET name = ?, lastname = ?, email = ?, password = ? WHERE id = ?;");
            pstm.setString(1, name);
            pstm.setString(2, lastname);
            pstm.setString(3, email);
            pstm.setString(4, password);
            pstm.setInt(5, id);

            flag = pstm.executeUpdate() == 1;
        }catch(SQLException e){
            System.out.println("Error" + e.getMessage());
        }finally{
            ConnectionMysql.closeConnections(con, pstm);
        }

        return flag;
    }

    public boolean deleteUser(int id){
        boolean flag = false;
        try{
            con = ConnectionMysql.getConnection();
            pstm = con.prepareCall("DELETE FROM `persons` WHERE id = ?;");
            pstm.setInt(1, id);

            flag = pstm.executeUpdate() == 1;
        }catch(SQLException e){
            System.out.println("Error" + e.getMessage());
        }finally{
            ConnectionMysql.closeConnections(con, pstm);
        }

        return flag;
    }
}
