package xin.pxyu.util;

import java.util.UUID;

public class ID {
    public static String createId(){
        UUID uuid= UUID.randomUUID();
        String id=uuid.toString();//36bits
        return id;
    }
}