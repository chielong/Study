package higher.webmvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HigherView {
    public static final String DEFAULT_CONTENT_TYPE = "text/html;charset=utf-8";

    private final File viewFile;

    public HigherView(File viewFile) {
        this.viewFile = viewFile;
    }

    public void render(Map<String , ?> model , HttpServletRequest request , HttpServletResponse response) throws IOException {
        StringBuffer stringBuffer = new StringBuffer();

        RandomAccessFile file = new RandomAccessFile(this.viewFile , "r");
        String line = null;
        while(null != (line = file.readLine())) {
            line = new String(line.getBytes("ISO-8859-1") , "utf-8");
            Pattern pattern = Pattern.compile("chileong\\{\\}" , Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(line);
            while(matcher.find()) {
                String paramName = matcher.group();
                paramName = paramName.replaceAll("chielong\\{\\}" , "");
                Object paramValue = model.get(paramName);
                if(null == paramName) {
                    continue;
                }
                line = matcher.replaceFirst(makeStringForRegExp(paramValue.toString()));
                matcher = pattern.matcher(line);
            }
            stringBuffer.append(line);
        }
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(stringBuffer.toString());
    }

    private String makeStringForRegExp(String str) {
        return str.replace("\\", "\\\\").replace("*", "\\*")
                .replace("+", "\\+").replace("|", "\\|")
                .replace("{", "\\{").replace("}", "\\}")
                .replace("(", "\\(").replace(")", "\\)")
                .replace("^", "\\^").replace("$", "\\$")
                .replace("[", "\\[").replace("]", "\\]")
                .replace("?", "\\?").replace(",", "\\,")
                .replace(".", "\\.").replace("&", "\\&");
    }
}
