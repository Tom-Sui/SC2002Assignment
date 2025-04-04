import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class General {
    private String DataFilePath = "./Data";
    //This function will only add the new content at the end of the file
    //If modified any of the txt files (e.g. ApplicantList.txt)
    //Do run initializing function again to load new contents
    
    public void editFile(String filePath, String newContent, String target, String targetRow){
        String buffeString;
        String fileContent = "";
        File projectFile = new File(filePath);

        try{
            Scanner scanner = new Scanner(projectFile);
            fileContent = fileContent + scanner.nextLine() + "\n";
            //This may having issue if some
            while(scanner.hasNextLine()){
                buffeString = scanner.nextLine();
                //check if search target in the String
                if (buffeString.contains(targetRow)){
                    buffeString = buffeString.replace(target, newContent);
                }
                fileContent = fileContent + buffeString + "\n";

            }

            FileWriter writer = new FileWriter(DataFilePath + "/ProjectList.txt");
            writer.write(fileContent);
            writer.close();
            scanner.close();
        }catch (FileNotFoundException e){
            System.out.println("Error occured while reading OfficerList.txt");
            e.printStackTrace();
        }catch(IOException e){
            System.out.println("Error occured when writing " + filePath);
            e.printStackTrace();
        }
    }

    public HDBManager findPerson(HDBManager[] hdbManager,String name){
        for(HDBManager i : hdbManager){
            if(name.equals(i.getName())){
                return i;
            }
        }
        // HDBManager hdbManagerNone = new HDBManager();
        // hdbManagerNone.setName("none");
        // return hdbManagerNone;
        return null;
    }
    public HDBOfficer findPerson(HDBOfficer[] hdbOfficer,String name){
        for(HDBOfficer i : hdbOfficer){
            if(name.equals(i.getName())){
                return i;
            }
        }
        // HDBOfficer hdbOfficerReturn = new HDBOfficer();
        // hdbOfficerReturn.setName("none");
        // return hdbOfficerReturn;
        return null;
    }
    public Project findProject(Project[] project,String projectName){
        for(Project i : project){
            if(projectName.equals(i.getProjectName())){
                return i;
            }
        }
        // HDBOfficer hdbOfficerReturn = new HDBOfficer();
        // hdbOfficerReturn.setName("none");
        // return hdbOfficerReturn;
        return null;
    }
}
