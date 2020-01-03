package tu.sofia.shop.black.friday.util;

import java.security.MessageDigest;

public class CreateSHA256 {

    public CreateSHA256() {

    }
    // It is an utility class, so that any methods could be static and no instance is needed

    public String getSHA256(String data){
        StringBuffer sb = new StringBuffer();
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(data.getBytes());
            byte byteData[] = md.digest();

            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return sb.toString();
    }
}
