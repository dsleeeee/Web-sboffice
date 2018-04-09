package kr.co.solbipos.utils;

import java.util.List;

public class AppUtil {

    /**
      * 
      * @param list
      * @param tartget
      * @return
      */
    public static boolean listIndexOf(List<String> list, String target) {
        if(list.indexOf(target) > -1) {
            return true;
        }
        return false;
    }

    /**
      * 
      * @param list
      * @param target
      * @return
      */
    public static boolean listIndexOf(List<String> list, String[] target) {
        for(int i=0; i<target.length; i++) {
            if(list.indexOf(target[i]) == -1) {
                return false;
            }
        }
        return true;
    }
}
