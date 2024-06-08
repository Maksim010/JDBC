import java.sql.*;

public class HomeWorkMySql {
    private static Statement statement = null;

    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/homework", "root",
                "12345");
        statement = connection.createStatement();
        statement.setQueryTimeout(30);
        allNames();
        allMountains();
        alpinistOnKonber();
        nameStart();
        mountainEndClimbDesc();
        idFirstStart();
        avgHeieMountains();
        timeIn();


        connection.close();
    }

    public static void allNames() throws SQLException {//Имена всех альпинистов
        ResultSet rs = statement.executeQuery("select name from alpinists order by name");
        while (rs.next()) {
            System.out.printf("Name:%s%n", rs.getString("name"));
        }
        System.out.println(" ");
    }

    public static void allMountains() throws SQLException {//Все горы
        ResultSet res = statement.executeQuery("select nameMoutain from mountains ");
        while (res.next()) {
            System.out.printf("Mountain:%s%n", res.getString("nameMoutain"));
        }
        System.out.println(" ");
    }

    public static void alpinistOnKonber() throws SQLException {//Люди которые были на горе Конбер
        ResultSet res = statement.executeQuery("select name from alpinists join dates on alpinists.id=dates.idalp " +
                "join mountains on dates.idmountain=mountains.id " +
                "where nameMoutain like('Конбер')");
        while (res.next()) {
            System.out.printf("Name from Konber:%s%n", res.getString("name"));
        }
        System.out.println(" ");
    }

    public static void nameStart() throws SQLException {//Имя альпиниста и начало экспедиции
        ResultSet res = statement.executeQuery("select name,start from alpinists " +
                "join dates on alpinists.id=idalp ");
        while (res.next()) {
            System.out.printf("Name and start:%s%n%s%n", res.getString("name"), res.getString("start"));
        }
        System.out.println(" ");

    }

    public static void mountainEndClimbDesc() throws SQLException {//название горы,конец экспедиции по дате в порядке возрастания
        ResultSet res = statement.executeQuery("select nameMoutain,end from mountains " +
                "join dates on dates.idmountain=mountains.id " +
                "order by end desc ");
        while (res.next()) {
            System.out.printf("Mountain and end:%s%n%s%n", res.getString("nameMoutain"), res.getString("end"));
        }
        System.out.println(" ");
    }

    public static void idFirstStart() throws SQLException {// начало первых 2 восхождений на Эверест
        ResultSet res = statement.executeQuery("SELECT start FROM dates join mountains on mountains.id=dates.idmountain" +
                " where mountains.id =1 order by start desc limit 2");
        while (res.next()) {
            System.out.printf("Start:%s%n", res.getString("start"));
        }
        System.out.println(" ");
    }

    public static void avgHeieMountains() throws SQLException {// Средняя высота гор округленная до меньшего
        ResultSet res = statement.executeQuery("SELECT avg(mountains.hieghst)as hieghst FROM mountains group by hieghst  ");
        while (res.next()) {
            System.out.printf("Avg :%s%n", res.getString("hieghst"));
        }
        System.out.println(" ");
    }
    public static void timeIn() throws SQLException {// Время похода первых 3 групп
        ResultSet res = statement.executeQuery("SELECT idalp,(end-start) as a FROM dates order by idalp limit 3");
        while (res.next()) {
            System.out.printf("Group and days in :%s%n(days)%s%n", res.getString("idalp"),res.getString("a"));
        }
        System.out.println(" ");
    }
}