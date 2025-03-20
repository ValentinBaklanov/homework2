package servlet.util;

import jakarta.servlet.http.HttpServletRequest;

import java.io.BufferedReader;
import java.io.IOException;

public final class ServletsUtil {

    private ServletsUtil() {
    }

    public static Long getId(HttpServletRequest req) {

        String servletPath = String.valueOf(req.getRequestURI());
        return Long.parseLong(servletPath.substring(servletPath.lastIndexOf('/') + 1));

    }

    public static String extractBody(HttpServletRequest req) throws IOException {
        BufferedReader reader = req.getReader();
        int intValueOfChar;
        StringBuilder result = new StringBuilder();
        while ((intValueOfChar = reader.read()) != -1) {
            result.append((char) intValueOfChar);
        }
        return result.toString();
    }

}
