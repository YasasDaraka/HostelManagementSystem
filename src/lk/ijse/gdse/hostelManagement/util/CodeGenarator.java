package lk.ijse.gdse.hostelManagement.util;

import java.util.Random;

public class CodeGenarator {
    public static int verifyCode(){
        Random rand = new Random();
        int verify = rand.nextInt(10000);
        return verify;
    }
}
