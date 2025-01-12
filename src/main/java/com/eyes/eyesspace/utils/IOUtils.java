package com.eyes.eyesspace.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class IOUtils {

    public static byte[] inputStreamToBytes(InputStream ins) throws IOException {
        byte[] data = null;
        ByteArrayOutputStream baos = null;
        baos = new ByteArrayOutputStream();
        int i = -1;
        byte[] buf = new byte[1024];
        while ((i = ins.read(buf)) != -1) {
            baos.write(buf, 0, i);
        }
        return baos.toByteArray();
    }

}
