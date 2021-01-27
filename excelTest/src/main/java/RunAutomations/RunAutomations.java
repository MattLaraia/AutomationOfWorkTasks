package RunAutomations;
import RunAutomations.NameBasedFileCreations.PatientSetUp;
import java.io.IOException;

public class RunAutomations {
    public static void main(String[] args) throws IOException,Exception{
        PatientSetUp.setUpNewPatientsIntoTheSystem();
    }
}
