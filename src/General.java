import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.print.event.PrintJobAdapter;

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
            while(scanner.hasNextLine()){
                buffeString = scanner.nextLine();
                if (buffeString.contains(targetRow)){
                    System.out.println("write into file");
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

    public void editProjectFile(Project project){
        String buffeString;
        String fileContent = "";
        File projectFile = new File(DataFilePath +  "/ProjectList.txt");
        try{

            Scanner scanner = new Scanner(projectFile);
            while(scanner.hasNextLine()){
                buffeString = scanner.nextLine();
                if (buffeString.contains(project.getProjectName())){
                    System.out.println("write into file");
                    buffeString = project.toStore();
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
            System.out.println("Error occured when writing " + DataFilePath + "/ProjectList.txt");
            e.printStackTrace();
        }
    }

    public HDBManager findManager(ArrayList<HDBManager> hdbManager,String name){
        for(HDBManager i : hdbManager){
            if(name.equals(i.getName())){
                return i;
            }
        }
        return null;
    }
    public HDBOfficer findOfficer(ArrayList<HDBOfficer> hdbOfficer,String name){
        for(HDBOfficer i : hdbOfficer){
            if(name.equals(i.getName())){
                return i;
            }
        }
        return null;
    }
    public Project findProject(ArrayList<Project> project,String projectName){
        for(Project i : project){
            if(projectName.equals(i.getProjectName())){
                return i;
            }
        }
        return null;
    }
}
