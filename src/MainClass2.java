import java.util.*;

import static java.lang.Math.*;
import static java.lang.Math.abs;

public class MainClass2 {
    private static final int MINI_CYCLE_MAX_SIZE = 4;
    private static final int MINI_CYCLES_PER_LINE = 3;
    private static int cursorX = 0;
    private static int cursorY = 1;
    public enum CharSet {
        LETTERS("abcdefghijklmnopqrstuvwxyz"), //26
        NUMBERS("0123456789"), //10
        SPECIAL_CHARACTERS("!?@$%^&*"), //8
        ALPHANUMERIC(LETTERS.charSet+NUMBERS.charSet),
        ALPHANUMERIC_SPECIAL(ALPHANUMERIC.charSet + SPECIAL_CHARACTERS.charSet);

        private String charSet;
        CharSet(String charSet) {
            this.charSet = charSet;
        }

        public String getCharSet() {
            return charSet;
        }
    }
    private static String f;
    public static void main(String[] args) {
        String bam = "test";
        System.out.println(bam.substring(0,bam.length()-1));
    }
}
