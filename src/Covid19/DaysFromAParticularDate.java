package Covid19;

import java.io.*;
import java.util.*;
import java.text.ParseException;

import static Covid19.ReadWriteCsvData.readCsvRow;
import static Covid19.Metrics.*;

class DaysFromAParticularDate {
    public static void runIndividualProgram(int DataChoice) throws ParseException, IOException {
        UserInitialInput initialInput = UserInitialInput.userInputRequest(DataChoice);
        int option = initialInput.option;

        GroupingDisplayDataPrint groupDisplayPrint = new GroupingDisplayDataPrint();
        groupDisplayPrint.setInitialInput(initialInput);

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


