package bankai.shizuku.ichigo.common.constant;

import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public class CommonConstant {
    private CommonConstant() {
    }


    public static final class REGEX {
        public static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        public static final String PHONE_REGEX = "^(\\+\\d{1,3}[- ]?)?1[3-9]\\d{9}$";
        public static final String ID_CARD_REGEX = "(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)";
        public static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);

        public static final Pattern PHONE_PATTERN = Pattern.compile(PHONE_REGEX);

        public static final Pattern ID_CARD_PATTERN = Pattern.compile(ID_CARD_REGEX);

        public static final Pattern IPV4_PATTERN = Pattern.compile("^((25[0-5]|2[0-4]\\d|[01]?\\d\\d?)\\.){3}(25[0-5]|2[0-4]\\d|[01]?\\d\\d?)$");

        public static final Pattern URL_PATTERN = Pattern.compile("^(https?|ftp|file)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]");

    }

    public static final class DATE_TIME {
        public static final String PATTERN_DATETIME = "yyyy-MM-dd HH:mm:ss";
        public static final String PATTERN_DATETIME_MINI = "yyyyMMddHHmmss";
        public static final String PATTERN_DATE = "yyyy-MM-dd";
        public static final String PATTERN_TIME = "HH:mm:ss";

        public static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern(PATTERN_DATETIME);
        public static final DateTimeFormatter DATETIME_MINI_FORMATTER = DateTimeFormatter.ofPattern(PATTERN_DATETIME_MINI);
        public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(PATTERN_DATE);
        public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(PATTERN_TIME);
    }

    public static final class PAGE {
        public static final int DEFAULT_PAGE_NUM = 1;
        public static final int DEFAULT_PAGE_SIZE = 10;
    }

    public static final class CHARSET {
        public static final String CHARSET_UTF8 = "UTF-8";
        public static final String CHARSET_GBK = "GBK";
    }

    public static final class CACHE {
        public static final long CACHE_EXPIRE_MINUTES = 30;
        public static final long TOKEN_EXPIRE_HOURS = 2;
    }

    public static final class HTTP {
        public static final String APPLICATION_JSON = "application/json";
        public static final String APPLICATION_OCTET_STREAM = "application/octet-stream";
        public static final String APPLICATION_FORM_URLENCODED = "application/x-www-form-urlencoded";
        public static final String APPLICATION_MULTIPART_FORM_DATA = "multipart/form-data";
        public static final String HEADER_AUTH = "Authorization";
        public static final String HEADER_CONTENT_DISPOSITION = "Content-Disposition";
        // 字符集常量
        public static final String SUFFIX_CHARSET_UTF8 = ";charset=" + CHARSET.CHARSET_UTF8;
        public static final String SUFFIX_CHARSET_GBK = ";charset=" + CHARSET.CHARSET_GBK;

        /**
         * 为指定Content-Type添加UTF-8字符集
         *
         * @param contentType 原始Content-Type
         * @return 带字符集的Content-Type
         */
        public static String withUtf8Charset(String contentType) {
            return contentType + SUFFIX_CHARSET_UTF8;
        }

        /**
         * 为指定Content-Type添加GBK字符集
         *
         * @param contentType 原始Content-Type
         * @return 带字符集的Content-Type
         */
        public static String withGbkCharset(String contentType) {
            return contentType + SUFFIX_CHARSET_GBK;
        }

    }


}
