package com.jcertif.mdomotique.embedded;

import java.io.File;
import java.io.FileWriter;

public class GPIOControleurAPI {

    static final String GPIO_OUT = "out";
    static final String GPIO_ON = "1";
    static final String GPIO_OFF = "0";

    public static void main(String[] args) throws InterruptedException {

        try {
            // Open file handles to GPIO port unexport and export controls  
            FileWriter unexportFile = new FileWriter("/sys/class/gpio/unexport");
            FileWriter exportFile = new FileWriter("/sys/class/gpio/export");

            // Reset the port, if needed  
            File exportFileCheck = new File("/sys/class/gpio/gpio" + args[0]);
            if (exportFileCheck.exists()) {
                unexportFile.write(args[0]);
                unexportFile.flush();
            }

            // Set the port for use  
            exportFile.write(args[0]);
            exportFile.flush();

            // Open file handle to port input/output control  
            FileWriter directionFile = new FileWriter("/sys/class/gpio/gpio" + args[0] + "/direction");

            // Set port for output  
            directionFile.write(GPIO_OUT);
            directionFile.flush();

            // Set up a GPIO port as a command channel  
            FileWriter commandChannel = new FileWriter("/sys/class/gpio/gpio" + args[0] + "/value");
            
            if(args[1].equals("0")){
                commandChannel.write(GPIO_ON);
                commandChannel.flush();
            }else{
                commandChannel.write(GPIO_OFF);
                commandChannel.flush();
            }

        } catch (Exception exception) {
            
        }
    }
}
