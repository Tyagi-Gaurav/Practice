package gt.practice.hack;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Test {
    public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        final String API_KEY = "1biibclk4qfpb59ognph3ldtov";
        final String SECRET_KEY = "8vvepaj00dkd5";

        long unixTime = System.currentTimeMillis() / 1000L;

        String[] values = {API_KEY, SECRET_KEY, ""+unixTime};
        char[] HEXCHARS = "0123456789abcdef".toCharArray();

        MessageDigest md5digest = null;
        String signature = null;
        try {
            md5digest = MessageDigest.getInstance("MD5");
            for (String val : values) {
                md5digest.update(val.getBytes("UTF-8"));
            }
            final byte[] digest = md5digest.digest();
            final char[] chars = new char[digest.length * 2];
            int c = 0;
            for (final byte b : digest) {
                chars[c++] = HEXCHARS[(b >>> 4) & 0x0f];
                chars[c++] = HEXCHARS[(b      ) & 0x0f];
            }

            signature = new String(chars);
        } finally {
            md5digest.reset();
        }

        System.out.println(signature);
    }
}
