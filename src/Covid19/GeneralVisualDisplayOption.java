package Covid19;

import java.util.*;

class GeneralVisualDisplayOption {
    public static int GeneralVisualDisplayOption() {
        //Choose way of displaying
        System.out.println("\nChoose one way to display");
        System.out.println("1. Tabular display. ");
        System.out.println("2. Chart display. ");
        Scanner input = new Scanner(System.in);
        int DisplayOption;
        do {
            System.out.print("\nPlease enter only the number in those 3 options to choose: ");
            while (true) {
                try {
                    DisplayOption = input.nextInt();
                    return DisplayOption;
                } catch (Exception e) {
                    input.next();
                }
            }
        } while (DisplayOption != 1 && DisplayOption != 2);
    }
}


