import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Provides general utility methods for file operations and object searching.
 * <p>
 * This class handles file editing operations for various data files and provides
 * search functionality for different system entities.
 * </p>
 * 
 * @see Project
 * @see HDBManager
 * @see HDBOfficer
 * @see Applicant
 */
public class General {
    /** Default path for data files */
    private String DataFilePath = "./Data";

    /**
     * Edits a file by replacing specific content in a target row.
     * <p>
     * Note: After modifying any text files, the initialization function should be
     * run again to load the new contents.
     * </p>
     * 
     * @param filePath path to the file to edit
     * @param newContent new content to insert
     * @param target text to be replaced
     * @param targetRow row containing the target text
     */
    // public void editFile(String filePath, String newContent, String target, String targetRow) {
    //     String buffeString;
    //     String fileContent = "";
    //     File projectFile = new File(filePath);

    //     try {
    //         Scanner scanner = new Scanner(projectFile);
    //         while(scanner.hasNextLine()) {
    //             buffeString = scanner.nextLine();
    //             if (buffeString.contains(targetRow)) {
    //                 System.out.println("write into file");
    //                 buffeString = buffeString.replace(target, newContent);
    //             }
    //             fileContent = fileContent + buffeString + "\n";
    //         }
    //         FileWriter writer = new FileWriter(DataFilePath + "/ProjectList.txt");
    //         writer.write(fileContent);
    //         writer.close();
    //         scanner.close();
    //     } catch (FileNotFoundException e) {
    //         System.out.println("Error occurred while reading OfficerList.txt");
    //         e.printStackTrace();
    //     } catch (IOException e) {
    //         System.out.println("Error occurred when writing " + filePath);
    //         e.printStackTrace();
    //     }
    // }

    /**
     * Updates project information in the ProjectList.txt file.
     * 
     * @param project the Project object containing updated information
     */
    public static void editProjectFile(Project project) {
        String buffeString;
        String fileContent = "";
        File projectFile = new File("./Data/ProjectList.txt");
        
        try {
            Scanner scanner = new Scanner(projectFile);
            while(scanner.hasNextLine()) {
                buffeString = scanner.nextLine();
                    if (buffeString.contains(project.getProjectName())) {
                        System.out.println("Processing...");
                        buffeString = project.toStore();
                    }
                fileContent = fileContent + buffeString + "\n";
            }

            FileWriter writer = new FileWriter("./Data/ProjectList.txt");
            writer.write(fileContent);
            writer.close();
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error occurred while reading ./Data/ProjectList.txt");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error occurred when writing ./Data/ProjectList.txt");
            e.printStackTrace();
        }
    }
    /**
     * Updates project information in the ProjectList.txt file.
     * Specific used when updating the project name
     * @param project the Project object containing updated information
     */
    public static void editProjectFile(Project project,String projectName) {
        String buffeString;
        String fileContent = "";
        File projectFile = new File("./Data/ProjectList.txt");
        
        try {
            Scanner scanner = new Scanner(projectFile);
            while(scanner.hasNextLine()) {
                buffeString = scanner.nextLine();
                    if (buffeString.contains(project.getProjectName())) {
                        System.out.println("Processing...");
                        project.setProjectName(projectName);
                        buffeString = project.toStore();
                    }
                fileContent = fileContent + buffeString + "\n";
            }
            
            FileWriter writer = new FileWriter("./Data/ProjectList.txt");
            writer.write(fileContent);
            writer.close();
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error occurred while reading ./Data/ProjectList.txt");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error occurred when writing ./Data/ProjectList.txt");
            e.printStackTrace();
        }
    }
    /**
     * Generic file editing method for various file types.
     * 
     * @param filePath path to the file to edit
     * @param newContent new content to insert
     * @param target text to be replaced
     * @param targetRow row containing the target text
     * editOtherFile
     */
    public static void editFile(String filePath, String newContent, String target, String targetRow) {
        String buffeString;
        String fileContent = "";
        File projectFile = new File(filePath);

        try {
            Scanner scanner = new Scanner(projectFile);
            while(scanner.hasNextLine()) {
                buffeString = scanner.nextLine();
                if (buffeString.contains(targetRow)) {
                    buffeString = buffeString.replace(target, newContent);
                }
                fileContent = fileContent + buffeString + "\n";
            }
            FileWriter writer = new FileWriter(filePath);
            writer.write(fileContent);
            writer.close();
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error occurred while reading OfficerList.txt");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error occurred when writing " + filePath);
            e.printStackTrace();
        }
    }

    /**
     * Generic file editing method for various file types.
     * 
     * @param filePath path to the file to edit
     * @param content new content to insert
     * editOtherFile
     */
    public static void editFile(String filePath, String content) {
        String fileContent = "";
        File projectFile = new File(filePath);

        try {
            Scanner scanner = new Scanner(projectFile);
            FileWriter writer = new FileWriter(filePath);

            while(scanner.hasNextLine()) {
                fileContent = fileContent + scanner.nextLine() + "\n";
            }
            fileContent = fileContent + content + "\n";
            writer.write(fileContent);

            writer.close();
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error occurred while reading " + filePath);
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error occurred when writing " + filePath);
            e.printStackTrace();
        }
    }
    // ========== SEARCH METHODS ==========
    /**
     * Finds an HDBManager by name.
     * 
     * @param hdbManager list of HDBManagers to search
     * @param checker identity to search for
     * @return the matching HDBManager or null if not found
     */
    public static HDBManager findManager(ArrayList<HDBManager> hdbManager, String checker) {
        for(HDBManager i : hdbManager) {
            if(checker.equals(i.getNRIC())) {
                return i;
            }
        }
        return null;
    }

    /**
     * Finds an HDBOfficer by name.
     * 
     * @param hdbOfficer list of HDBOfficers to search
     * @param checker identity to search for
     * @return the matching HDBOfficer or null if not found
     */
    public static HDBOfficer findOfficer(ArrayList<HDBOfficer> hdbOfficer, String checker) {
        for(HDBOfficer i : hdbOfficer) {
            if(checker.equals(i.getNRIC())) {
                return i;
            }
        }
        return null;
    }

    /**
     * Finds a Project by name.
     * 
     * @param project list of Projects to search
     * @param projectName project name to search for
     * @return the matching Project or null if not found
     */
    public static Project findProject(ArrayList<Project> project, String projectName) {
        for(Project i : project) {
            if(projectName.equals(i.getProjectName())) {
                return i;
            }
        }
        return null;
    }
    
    /**
     * Finds an Applicant by NRIC.
     * 
     * @param applicant list of Applicants to search
     * @param NRIC NRIC to search for
     * @return the matching Applicant or null if not found
     */
    public static Applicant findApplicant(ArrayList<Applicant> applicant, String NRIC) {
        for(Applicant i : applicant) {
            if(NRIC.equals(i.getNRIC())) {
                return i;
            }
        }
        return null;
    }
}