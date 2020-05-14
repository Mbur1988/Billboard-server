package Clients.ControlPanel.ControlPanelTools;

public class UserAccess {

    /**
     * converts a decimal integer to a binary array. if the input is >15 or <0 returns null
     * @param decimal integer between 0-15
     * @return integer array of the input param in binary form or null if input out of bounds
     */
    public static int[] dec2bin(int decimal) {
        if (decimal > 15 || decimal < 0) { return null; }
        int binary[] = new int[4];
        int index = 0;
        while (decimal > 0) {
            binary[index++] = decimal % 2;
            decimal = decimal/2;
        }
        for(int i = index-1;i >= 0;i--){
            System.out.print(binary[i]);
        }
        return binary;
    }

    /**
     * generates the access level as an integer based on the permissions
     * @param createBillboards
     * @param editBillboards
     * @param scheduleBillboards
     * @param editUsers
     * @return the access level as an integer
     */
    public static int bool2dec(boolean createBillboards,
                              boolean editBillboards,
                              boolean scheduleBillboards,
                              boolean editUsers) {
        int decimal = 0;
        if (createBillboards) { decimal = decimal + 1; }
        if (editBillboards) { decimal = decimal + 2; }
        if (scheduleBillboards) { decimal = decimal + 4; }
        if (editUsers) { decimal = decimal + 8; }
        return decimal;
    }
}
