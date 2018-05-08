package kr.co.common.security.lucy;

import java.io.IOException;
import java.io.InputStream;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class CustomXssServletFilter implements Filter {
    private static final String CLASSPATH_PREFIX = "classpath:";
    private static final String CONFIG_LOCATION_PARAM = "ruleConfigLocation";

    private InputStream getResource(FilterConfig filterConfig) {
        String ruleConfigLocation = filterConfig.getInitParameter(CONFIG_LOCATION_PARAM);

        if (ruleConfigLocation == null)
            ruleConfigLocation = "";

        if (ruleConfigLocation.indexOf(CLASSPATH_PREFIX) > -1)
            return Thread.currentThread().getContextClassLoader()
                    .getResourceAsStream(ruleConfigLocation.replaceFirst(CLASSPATH_PREFIX, ""));
        else
            return filterConfig.getServletContext().getResourceAsStream(ruleConfigLocation);
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        CustomXssEscapeFilter.setCustomXssEscapeFilterConfig(
                new CustomXssEscapeFilterConfig(getResource(filterConfig)));
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        chain.doFilter(new CustomXssEscapeServletFilterWrapper(request,
                CustomXssEscapeFilter.getInstance()), response);
    }

    public void destroy() {
        // TODO Auto-generated method stub
    }

}
