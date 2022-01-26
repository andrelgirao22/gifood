package br.com.alg.giraofoodapi.util;

import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

public class ResourceUtils {

    public static String getContentFromResource(String resourceName) {
        try {
            InputStream inputStream = ResourceUtils.class.getResourceAsStream(resourceName);
            return StreamUtils.copyToString(inputStream, Charset.forName("UTF-8"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
