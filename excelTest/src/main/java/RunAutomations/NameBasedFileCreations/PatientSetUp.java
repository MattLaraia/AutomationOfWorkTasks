//package nameBasedFileCreation;
package RunAutomations.NameBasedFileCreations;
import java.io.File;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.Scanner;
//import nameBasedFileCreation.ExcelPatientAutomation.ExcelPatientWorkbook;

public class PatientSetUp {


    public static void setUpNewPatientsIntoTheSystem()throws IOException,NoSuchFileException{
        String lineOfText ="\n---------------------------------------------------------------------------------------------------------------------------\n";
        System.out.println(lineOfText + "Note: If you want to put files somewhere other than the Patient Payment Files Folder, just type 'Change root directory'" + lineOfText);
        System.out.println(lineOfText + "Note: Type 'stop' if you want to stop at any point!" + lineOfText);
        String defaultRootFolderHoldingPatientFiles = "Patient Payment Files";
        //^^ Could use scanner and ask for it as input BUT, Grandpa who is using this program only puts patients in that folder
        //this root folder MUST be visible after a certain amount of times of doing '../'
        //Note: the root can be as specific as necessary such as: 'Patient Payment File/Back Pain' and so on
        String patientDirectory = defaultRootFolderHoldingPatientFiles;
        patientDirectory = obtainProperDirectory(patientDirectory);
        String mainExcelFilePathName = "Main Patient Table.xlsx";
        mainExcelFilePathName = obtainProperDirectory(mainExcelFilePathName);
        //System.out.println(patientDirectory);
        String tempDirectory = patientDirectory;
        boolean addAnotherPatient=true;
        while(addAnotherPatient){
            patientDirectory = tempDirectory;
            boolean nonExistingDirectory = false;
            String patientName;
            Scanner nameOrDirectoryInput = new Scanner(System.in);
            System.out.println("Enter patient's name: (Press enter after typing name)");
            patientName = nameOrDirectoryInput.nextLine();
            if(patientName.equals("stop")){
                addAnotherPatient = false;
            }
            //elseif changing root folder... 
            //--need to do loop w new path until changed again!!
            else if(patientName.equals("Change root directory")){
                 
                //String newRootDirectory;
                //use scanner for input to newRootDirectory
                //then do a string remove of patient direcory? 
                //how to modify patientDirectory w/o a ton of work? use some 
            }
            else{
                System.out.println("\nNow specify the folder this patient belongs to: (Then press enter)");
                patientDirectory = tempDirectory;
                patientDirectory += "/" + nameOrDirectoryInput.nextLine();
                File designatedDirectory = new File(patientDirectory);
                if(designatedDirectory.exists()){
                    createPatientFolderFileAndAddToMainFile(designatedDirectory, patientName, mainExcelFilePathName);
                    
                }
                else{
                    nonExistingDirectory=true;
                    while(nonExistingDirectory){
                        patientDirectory = tempDirectory;
                        System.out.print(lineOfText + "ERROR: Folder you entered does not exist, please Re-enter the folder name:" + lineOfText);
                        patientDirectory += "/" + nameOrDirectoryInput.nextLine();
                        if(patientDirectory.contains("stop")){
                            nonExistingDirectory=false;
                            addAnotherPatient=false;
                        }
                        else{
                            designatedDirectory = new File(patientDirectory);
                            if(designatedDirectory.exists()){
                                nonExistingDirectory=false;
                                createPatientFolderFileAndAddToMainFile(designatedDirectory, patientName, mainExcelFilePathName);
                            }   
                        }
                    }  
                }
            }
            //nameOrDirectoryInput.close();
        }
        
    }

    public static void createPatientFolderFileAndAddToMainFile(File designatedDirectory, String patientName, String mainExcelFilePathName) throws IOException,NoSuchFileException{
        String patientFolderName = designatedDirectory +"/" + patientName;
        File directory = new File(patientFolderName);
        String lineOfText = "\n---------------------------------------------------------------------------------------------------------------------------\n";
        if(!directory.exists()){
            if(directory.mkdir()){
                String patientFileName = patientFolderName + "/" + patientName+ "'s Patient Table.xlsx";
                File newPatientFile = new File(patientFileName);
                if(newPatientFile.createNewFile()){
                    PersonalAndMainXLSXOps.addPersonalExcelFormat(newPatientFile);
                    PersonalAndMainXLSXOps.updateMainExcel(patientName, mainExcelFilePathName);
                    System.out.println("\nPersonal Folder and File have been created successfully!\nName added to the main file sucessfully!\n");
                }
                else{
                    System.out.println(lineOfText + "ERROR: Folder was created but not excel file!" + lineOfText);
                }
            }
            else{
                System.out.println(lineOfText + "ERROR: Folder and excel file failed to be created" + lineOfText);
            }
        }
        else{
            System.out.println(lineOfText + "ERROR: Patient already exists!" + lineOfText);
        }
        return;
    }

    public static String obtainProperDirectory(String folderOrFilePathWanted){
        File tempDirectory = new File(folderOrFilePathWanted);
        while(!tempDirectory.exists()){
            String goBackAFolder = "../";
            folderOrFilePathWanted=goBackAFolder.concat(folderOrFilePathWanted);
            tempDirectory = new File(folderOrFilePathWanted);
        }
        return folderOrFilePathWanted;
    }

}
