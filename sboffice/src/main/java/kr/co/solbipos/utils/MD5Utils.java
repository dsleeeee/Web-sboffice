package kr.co.solbipos.utils;

import org.springframework.util.DigestUtils;

/**
 * @author 정용길
 *
 */
public class MD5Utils {

    private MD5Utils() {}

    public static String md5toHex(Object o) {
        return (o == null) ? null : DigestUtils.md5DigestAsHex(o.toString().getBytes());
    }
}
