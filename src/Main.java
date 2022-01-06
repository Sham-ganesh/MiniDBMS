import java.util.HashMap;

public class Main {
    public static DataBase currentDB;
    public static HashMap<String, DataBase> DataBases = new HashMap<>();

    public static void main(String[] args) {
        Main DB = new Main();

        DBUtils.createDB("StudentDB");
        DBUtils.createDB("EmployeeDB");
        DBUtils.checkForDataBases(DataBases);

        AppGUI app = new AppGUI();
    }
}