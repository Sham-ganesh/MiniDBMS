import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class DataBase {
    public String DBName;
    public String filePath;
    public String tableDetailsPath;
    public HashMap<String, Table> tables = new HashMap<>();

    DataBase(String DBName, String filePath) {
        this.DBName = DBName;
        this.filePath = filePath;
        tableDetailsPath = filePath+"/tableDetails.txt";
        checkForTables();
    }

    public void createTable(String tableName, String[] colNames, String[] colTypes) {
        File table = new File(filePath+"/"+tableName+".txt");
        if( table.exists() ) {
            System.out.println("Table already exists");
            return;
        }
        else {
            try {
                table.createNewFile();
                File tableDetailFile = new File(tableDetailsPath);
                if(!tableDetailFile.exists())
                    tableDetailFile.createNewFile();
                BufferedWriter writer = new BufferedWriter(new FileWriter(tableDetailFile, true));
                writer.write(tableName+"\n");
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath+"/"+tableName+".txt", true));
            for(int i=0;i<colNames.length;i++) {
                writer.write(colNames[i]+",");
            }
            writer.write("\n");
            for(int i=0;i<colTypes.length;i++) {
                writer.write(colTypes[i]+",");
            }
            writer.write("\n");
            writer.close();
        }catch (IOException e ) {
            System.out.println(e);
        }
    }
    public void dropTable(String tableName) {
        File table = new File(filePath+"/"+tableName+".txt");
        if(table.delete()) {
            System.out.println("Table " + tableName + " Dropped successfully");
        }

        ArrayList<String> nameList = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(tableDetailsPath));
            String str;
            while ((str = reader.readLine()) != null) {
                if (str.equals(tableName))
                    continue;
                nameList.add(str);
            }
            reader.close();

            BufferedWriter writer = new BufferedWriter(new FileWriter(tableDetailsPath));
            writer.write("");
            writer.close();

            writer = new BufferedWriter(new FileWriter(tableDetailsPath, true));
            for (int i = 0; i < nameList.size(); i++) {
                System.out.println(nameList.get(i));
                writer.write(nameList.get(i) + "\n");
            }
            writer.close();
        }catch (IOException e) {
            System.out.println(e);
        }
    }
    private void checkForTables() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(tableDetailsPath));
            String str;
            while((str = reader.readLine()) != null) {
                tables.put(str, new Table(str,filePath+"/"+str+".txt"));
            }
        }catch (IOException e) {
            System.out.println(e);
        }
    }
    public void generateReport() {

    }
}