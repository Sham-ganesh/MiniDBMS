import java.util.HashMap;

public class Main {
    public static DataBase currentDB;
    public static HashMap<String, DataBase> DataBases;

    public static void main(String[] args) {
        Main DB = new Main();
    }
    Main() {
        DataBases = new HashMap<>();
        DBUtils.checkForDataBases(DataBases);

        currentDB = DataBases.get("EmployeeDB");
        Table table = currentDB.tables.get("emp");
        try {
            System.out.println(table.filePath);
            //table.query_processing("table Insert values (102,sham,2132)");
            //table.query_processing("table delete record where rno=101");
            table.query_processing("table update name=yogi where rno=102");
        }catch (Exception e) {
            System.out.println(e);
        }
//        String[] colNames = new String[3];
//        colNames[0]="rno";colNames[1]="name";colNames[2]="code";
//        String[] colTypes = new String[3];
//        colTypes[0]="\\d+";colTypes[1]="String";colTypes[2]="\\d+";
//        currentDB.createTable("emp",colNames,colTypes);

        //currentDB.dropTable("emp");

//        for(String name : DataBases.keySet())
//            System.out.println(name);
//        Scanner s=new Scanner(System.in);
//        String query;
//        //Tablename Select all;
//        //Tablename Insert values ( )
//        //Tablename update values;
//        query = s.nextLine();
        //currentDB.query_processing(query);
    }
}