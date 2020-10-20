package com.company;

import java.util.Random;
import java.util.TimerTask;

/*
 * ManageTimer extends TimerTask to help manage the task of printing a message every 10 second.
 *
 * */
public class ManageTimer extends TimerTask {
    private int duplicateNumber;
    private int uniqueNumber;
    private int uniqueNumberTotal;

    @Override
    public void run() {
        Random random = new Random();
        Integer firstNumberToCompare = random.nextInt(10);
        Integer secondNumberToCompare = random.nextInt(10);

        int check = firstNumberToCompare.compareTo(secondNumberToCompare);

        if (check == 0) {
            duplicateNumber++;
        } else {
            uniqueNumber++;
            uniqueNumberTotal = uniqueNumber;
        }
        //prints the message every 10 seconds
        System.out.printf("Received %d unique numbers, %d duplicates. Unique total: %d\n",
                uniqueNumber, duplicateNumber, uniqueNumberTotal);
    }
}
