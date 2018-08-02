package kr.co.common.utils.spring;

import java.io.UnsupportedEncodingException;
import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.HtmlUtils;

public class HtmlUtil extends HtmlUtils {
    
    private final static Logger LOGGER = LoggerFactory.getLogger(HtmlUtil.class);
    
    public static String unescape(String hexDecimal) {
        return unescape(hexDecimal, Charset.UTF_8);
    }

    public static String unescape(String hexDecimal, Charset toCharset) {
        return unescape(hexDecimal, Charset.ISO_8859_1, toCharset);
    }

    public static String unescape(String hexDecimal, Charset fromCharset, Charset toCharset) {
        if (StringUtil.isEmpty(hexDecimal))
            return "";

        String str = StringEscapeUtils.unescapeJava(hexDecimal.replaceAll("(?i)\\\\x", "\\\\u00"));

        try {
            return new String(str.getBytes(fromCharset.get()), toCharset.get());
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    public static void unescapePrint(String hexDecimal) {
        Charset[] charsets = Charset.values();

        for (Charset li : charsets)
            for (Charset lj : charsets) {
                if (li == lj)
                    continue;

                if (LOGGER.isDebugEnabled())
                    LOGGER.debug("{} -> {} : {}", li, lj, unescape(hexDecimal, li, lj));
            }
    }

    public enum Charset {
        MS949("MS949"), UTF_8("UTF-8"), EUC_KR("EUC-KR"), CP949("CP949"), ISO_8859_1("ISO-8859-1");

        private String charset;

        Charset(String charset) {
            this.charset = charset;
        }

        public String get() {
            return charset;
        }

        public static int size() {
            return Charset.values().length;
        }
    }
}
