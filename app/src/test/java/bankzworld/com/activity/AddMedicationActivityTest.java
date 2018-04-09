package bankzworld.com.activity;

import org.junit.Test;

public class AddMedicationActivityTest {

    @Test
    public void onCreate() {
    }

    @Test
    public void onClick() {
        AddMedicationActivity addMedicationActivity = new AddMedicationActivity();
        addMedicationActivity.dataValidation("Panadol", "pain killer", "2",
                "3", "09-04-2018", "3", "04");
    }

}