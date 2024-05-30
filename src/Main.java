import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        /*
        1- Driver register etme +
        2- Veritabanı bağlantısı açma +
        3- Sorgu çalıştırmak için statement yaratma +
                boolean execute = statement.execute();
                ResultSet resultSet = statement.executeQuery(); // SELECT
                int i = statement.executeUpdate(); // INSERT, UPDATE, DELETE
        4-Sorgu çalıştırma +
        5- Veritabanı bağlantısı kapatma +
         */

        Student student = new Student(1, "ahmet", 3.5);
        Student student2 = new Student(2, "asım", 4.0);

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/upod", "root", "ahmetgltkn");
            Statement statement = connection.createStatement();

            String ddl = "CREATE TABLE IF NOT EXISTS students " +
                    "(id int PRIMARY KEY AUTO_INCREMENT, name varchar(45), " +
                    "grade double)";

            statement.executeUpdate(ddl);

            String insert = "INSERT INTO students(name,grade) VALUES(?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insert);
            preparedStatement.setString(1, student.getName());
            preparedStatement.setDouble(2, student.getGrade());

            System.out.println(" Created student number " + preparedStatement.executeUpdate());

            preparedStatement.setString(1, student2.getName());
            preparedStatement.setDouble(2, student2.getGrade());

            System.out.println(" Created student number " + preparedStatement.executeUpdate());

            String select = "SELECT * FROM students ";


            ResultSet resultSet = statement.executeQuery(select);
            List<Student> studentList = new ArrayList<>();

            while (resultSet.next()) {
                Student setFromDb = new Student(resultSet.getInt("id")
                        , resultSet.getString("name")
                        , resultSet.getDouble("grade"));
                studentList.add(setFromDb);
            }

            studentList.forEach(System.out::println);

            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}