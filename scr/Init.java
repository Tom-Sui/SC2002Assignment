import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Init {
    public Applicant[] LoadUserInfo(){
        int size;
        int count = 0;

        File applicantFile = new File("./scr/Data/ApplicantList.txt");
        Applicant[] applicant;

        try{
            Scanner scanner = new Scanner(applicantFile);
            size = Integer.parseInt(scanner.nextLine());

            applicant = new Applicant[size];

            PasswordHashing passwordHashing = new PasswordHashing();

            while(scanner.hasNextLine()){
                applicant[count] = new Applicant();
                String[] data = scanner.nextLine().split(",");
                applicant[count].setName(data[0]);
                applicant[count].setNRIC(data[1]);
                applicant[count].setage(Integer.parseInt(data[2]));
                if(data[3].equals("Single")){
                    applicant[count].setMatritialSatus(false);
                }else{
                    applicant[count].setMatritialSatus(true);
                }
                

                //Hash the password and store back
                applicant[count].setPassword(passwordHashing.hashingPassword(data[4]));

                count += 1;
            }

            scanner.close();
        }catch (FileNotFoundException e){
            System.out.println("Error occured while reading ApplicantList.txt");
            e.printStackTrace();
            applicant = new Applicant[0];
        }
        return applicant;
    }

    public HDBManager[] LoadManagerInfo(){
        int size;
        int count = 0;

        File managerFile = new File("./scr/Data/ManagerList.txt");
        HDBManager[] hdbManagers;

        try{
            Scanner scanner = new Scanner(managerFile);
            size = Integer.parseInt(scanner.nextLine());

            hdbManagers = new HDBManager[size];

            while(scanner.hasNextLine()){
                hdbManagers[count] = new HDBManager();
                String[] data = scanner.nextLine().split(",");
                hdbManagers[count].setName(data[0]);
                hdbManagers[count].setNRIC(data[1]);
                hdbManagers[count].setage(Integer.parseInt(data[2]));
                if(data[3].equals("Single")){
                    hdbManagers[count].setMatritialSatus(false);
                }else{
                    hdbManagers[count].setMatritialSatus(true);
                }
                hdbManagers[count].setPassword(data[4]);

                count += 1;
            }

            scanner.close();
        }catch (FileNotFoundException e){
            System.out.println("Error occured while reading ApplicantList.txt");
            e.printStackTrace();
            hdbManagers = new HDBManager[0];
        }
        return hdbManagers;
    }

    public HDBOfficer[] LoadOfficerInfo(){
        int size;
        int count = 0;

        File officerFile = new File("./scr/Data/OfficerList.txt");
        HDBOfficer[] hdbOfficer;

        try{
            Scanner scanner = new Scanner(officerFile);
            size = Integer.parseInt(scanner.nextLine());

            hdbOfficer = new HDBOfficer[size];

            while(scanner.hasNextLine()){
                hdbOfficer[count] = new HDBOfficer();
                String[] data = scanner.nextLine().split(",");
                hdbOfficer[count].setName(data[0]);
                hdbOfficer[count].setNRIC(data[1]);
                hdbOfficer[count].setage(Integer.parseInt(data[2]));
                if(data[3].equals("Single")){
                    hdbOfficer[count].setMatritialSatus(false);
                }else{
                    hdbOfficer[count].setMatritialSatus(true);
                }
                hdbOfficer[count].setPassword(data[4]);
                count += 1;
            }
            scanner.close();
        }catch (FileNotFoundException e){
            System.out.println("Error occured while reading ApplicantList.txt");
            e.printStackTrace();
            hdbOfficer = new HDBOfficer[0];
        }
        return hdbOfficer;
    }
}
