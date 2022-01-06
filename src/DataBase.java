import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DataBase {
    public String DBName;
    public String filePath;

    DataBase(String DBName, String filePath) {
        this.DBName = DBName;
        this.filePath = filePath;
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
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath+"/"+tableName+".txt", true));
            for(int i=0;i<colNames.length;i++) {
                writer.write(colNames[i]+" ");
            }
            writer.write("\n");
            for(int i=0;i<colTypes.length;i++) {
                writer.write(colTypes[i]+" ");
            }
            writer.write("\n");
        }catch (IOException e ) {
            System.out.println(e);
        }
    }
    public void dropTable(String tableName) {
        File table = new File(filePath+"/"+tableName+".txt");
        if(table.delete()) {
            System.out.println("Table " + tableName + "Dropped successfully");
        }
    }
    public void generateReport() {

    }
}
