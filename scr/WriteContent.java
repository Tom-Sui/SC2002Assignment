import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class WriteContent {
    //This function will only add the new content at the end of the file
    //If modified any of the txt files (e.g. ApplicantList.txt)
    //Do run initializing function again to load new contents
    public void LoadOfficerInfo(String filePath, String addContent){
        int size;
        int count = 0;
        String fileContent = "";

        File officerFile = new File(filePath);

        try{
            Scanner scanner = new Scanner(officerFile);
            size = Integer.parseInt(scanner.nextLine());

            while(scanner.hasNextLine()){
                fileContent = fileContent + scanner.nextLine() + "\n";
            }
            fileContent += addContent;

            scanner.close();
        }catch (FileNotFoundException e){
            System.out.println("Error occured while reading OfficerList.txt");
            e.printStackTrace();
        }
    }
}
