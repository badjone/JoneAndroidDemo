package newstudiowork.newtest.UI.Utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import Decoder.BASE64Encoder;

/**
 * md5加密
 *
 * @author badjone
 * @Date 2016/8/1 17:04
 */
public class Md5Utils {

    public static String md5(String str) {
        String s = str;
        if (s == null) {
            return "";
        } else {
            String value = null;
            MessageDigest md5 = null;
            try {
                md5 = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException ex) {
                L.e("exception>>" + ex);
            }
            BASE64Encoder baseEncoder = new BASE64Encoder();
            try {
                value = baseEncoder.encode(md5.digest(s.getBytes("utf-8")));
            } catch (Exception ex) {
            }
            return value;
        }
    }
}
