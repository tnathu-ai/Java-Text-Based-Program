package Covid19;

import java.io.*;
import java.util.*;
import java.text.ParseException;

import static Covid19.ReadWriteCsvData.readCsvRow;
import static Covid19.Metrics.*;

class DaysFromAParticularDate {
    UserInitialInput initialInput;
    GroupingDisplayDataPrint groupDisplayPrint;

    public DaysFromAParticularDate(UserInitialInput initialInput, GroupingDisplayDataPrint groupDisplayPrint) {
        this.initialInput = initialInput;
        this.groupDisplayPrint = groupDisplayPrint;
    }

    public void runIndividualProgram(int DataChoice) throws ParseException, IOException {
        UserInitialInput userInitialInput = UserInitialInput.userInputRequest(DataChoice);
        int option = initialInput.option;

        if (option == 1) {
            groupDisplayPrint.PrintOption1();

        } else if (option == 2) {
            groupDisplayPrint.PrintOption2();

        } else if (option == 3) {
            groupDisplayPrint.PrintOption3();

        } else {
            //Just in case sth else happens
            runIndividualProgram(DataChoice);
        }
    }
}


