package ict.test;
import ict.db.UserDB;

public class TestCreateDB {
    public static void main(String[] args){
        String url = "jdbc:mysql://localhost:3306/";
        String username = "root";
        String password = "root";
        UserDB db = new UserDB(url, username, password);
        db.createDB();
    }
}
