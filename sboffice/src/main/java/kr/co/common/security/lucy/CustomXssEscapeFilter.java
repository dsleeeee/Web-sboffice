package kr.co.common.security.lucy;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.navercorp.lucy.security.xss.servletfilter.XssEscapeFilterRule;

public final class CustomXssEscapeFilter {
    private static final Log LOG = LogFactory.getLog(CustomXssEscapeFilter.class);

    private static CustomXssEscapeFilter xssEscapeFilter;
    private static CustomXssEscapeFilterConfig config;

    static {
        try {
            xssEscapeFilter = new CustomXssEscapeFilter();
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    /**
     * Default Constructor
     */
    private CustomXssEscapeFilter() {
        // config = new CustomXssEscapeFilterConfig();
    }

    /**
     * @param url String
     * @param paramName String
     * @param value String
     * @return void
     */
    private void log(String url, String paramName, String value) {
        if (LOG.isDebugEnabled())
            LOG.debug("Do not filtered Parameter. Request url: " + url + ", Parameter name: "
                    + paramName + ", Parameter value: " + value);
    }

    public static void setCustomXssEscapeFilterConfig(CustomXssEscapeFilterConfig config2) {
        if (config == null)
            config = config2;
    }

    /**
     * @return XssEscapeFilter
     */
    public static CustomXssEscapeFilter getInstance() {
        return xssEscapeFilter;
    }

    /**
     * @param url String
     * @param paramName String
     * @param value String
     * @return String
     */
    public String doFilter(String url, String paramName, String value) {
        if (StringUtils.isBlank(value))
            return value;

        XssEscapeFilterRule urlRule = config.getUrlParamRule(url, paramName);

        if (urlRule == null)
            return config.getDefaultDefender().doFilter(value);

        if (!urlRule.isUseDefender()) {
            log(url, paramName, value);
            return value;
        }

        return urlRule.getDefender().doFilter(value);
    }
}
