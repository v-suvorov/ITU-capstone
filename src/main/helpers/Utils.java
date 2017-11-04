package helpers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

public class Utils {
    /**
     * Get current date and time in specific format
     * @param: "dd.MM.yyyy" or "MM.dd.yyyy hh:mm"
     */
    public static String getDateAndTime(String timeFormat){
        DateFormat dateFormat = new SimpleDateFormat(timeFormat);
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());
    }

    /**
    * Generates random int value in [minValue, maxValue] range
    * Example: generateRandomIntInRange(1, 100) returns x, where 1 <= x <= 100
    */
    public static int generateRandomIntInRange(int minValue, int maxValue) {
        return (int)(Math.round(Math.random() * (maxValue - minValue))) + minValue;
    }

    /**
     * Generates random string with the length less or equal to set value
     * @param maxStringLength - length of generated string is less or equal to this value
     */
    public static String randomString(int maxStringLength) {
        char[] chars = "   abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        int length = 0;
        boolean isRandomStringValid;
        String resultString;

        do {
            length = random.nextInt(maxStringLength) + 1;       //length should not be equal to 0
            for (int i = 0; i < length; i++) {
                char c = chars[random.nextInt(chars.length)];
                sb.append(c);
            }
            resultString = sb.toString();

            int counter = 0;
            isRandomStringValid = true;                         //consider that the string is valid by default
            for (int i = 0; i < resultString.length(); i++) {
                if (resultString.charAt(i) == ' ') {
                    counter++;
                }
            }
            if (resultString.length() == counter) {             //but if the generated string contains only spaces then regenerate it
                isRandomStringValid = false;
            }
        } while (!isRandomStringValid);
        return resultString;
    }

}
