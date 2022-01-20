import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

public class MailService {
    public static void sendEmail(String to, String dbName, HashMap<String, Table> tables) {
        String from = "yogeeswartestmail@gmail.com";
        String host = "smtp.gmail.com";

        Properties properties = System.getProperties();

        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("yogeeswartestmail@gmail.com", "pwd@1234");
            }
        });

        session.setDebug(true);

        ArrayList<String> CSVFilePaths = new ArrayList<>();

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(dbName + " DB TABLES EXPORTED");

            Multipart multipart = new MimeMultipart();
            MimeBodyPart attachmentPart = new MimeBodyPart();
            MimeBodyPart textPart = new MimeBodyPart();

            String textString = dbName + " DB TABLES\n";
            int count=1;
            for(String tableName : tables.keySet())
                textString += Integer.toString(count++) + ". " + tableName + "\n";
            textString += "All the above tables are exported.";

            textPart.setText(textString);
            multipart.addBodyPart(textPart);

            for(Table table : tables.values()) {
                String data = MailService.getCSVData(table.filePath);

                File attachmentFile = new File(table.tableName+".csv");
                CSVFilePaths.add(table.tableName+".csv");
                attachmentFile.createNewFile();
                BufferedWriter writer = new BufferedWriter(new FileWriter(table.tableName+".csv",true));
                writer.write(data);
                writer.close();

                attachmentPart = new MimeBodyPart();
                attachmentPart.attachFile(attachmentFile);
                multipart.addBodyPart(attachmentPart);
            }

            message.setContent(multipart);
            System.out.println("sending mail.....");
            Transport.send(message);
            System.out.println("mail sent");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        MailService.deleteCSVFiles(CSVFilePaths);
    }
    public static String getCSVData(String fileName) {
        String data = "";
        int count=1;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String str;
            while((str = reader.readLine()) != null) {
                if(count != 2)
                    data += str + "\n";
                count++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
    public static void deleteCSVFiles(ArrayList<String> paths) {
        for(int i=0;i<paths.size();i++) {
            File file = new File(paths.get(i));
            file.delete();
        }
    }
}