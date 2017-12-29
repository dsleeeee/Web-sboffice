package kr.co.solbipos.security.lucy;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class CustomXssEscapeServletFilterWrapper extends HttpServletRequestWrapper {
    private CustomXssEscapeFilter xssEscapeFilter;
    private String path = null;

    public CustomXssEscapeServletFilterWrapper(ServletRequest request,
            CustomXssEscapeFilter xssEscapeFilter) {
        super((HttpServletRequest) request);
        this.xssEscapeFilter = xssEscapeFilter;
        this.path = ((HttpServletRequest) request).getRequestURI();
    }

    @Override
    public String getParameter(String paramName) {
        String value = super.getParameter(paramName);
        return doFilter(paramName, value);
    }

    @Override
    public String[] getParameterValues(String paramName) {
        String values[] = super.getParameterValues(paramName);

        if (values == null)
            return values;

        for (int index = 0; index < values.length; index++)
            values[index] = doFilter(paramName, values[index]);

        return values;
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        Map<String, String[]> paramMap = super.getParameterMap();
        Map<String, String[]> newFilteredParamMap = new HashMap<String, String[]>();

        Set<Map.Entry<String, String[]>> entries = paramMap.entrySet();
        for (Map.Entry<String, String[]> entry : entries) {
            String paramName = entry.getKey();
            Object[] valueObj = (Object[]) entry.getValue();
            String[] filteredValue = new String[valueObj.length];

            for (int index = 0; index < valueObj.length; index++)
                filteredValue[index] = doFilter(paramName, String.valueOf(valueObj[index]));

            newFilteredParamMap.put(entry.getKey(), filteredValue);
        }

        return newFilteredParamMap;
    }

    /**
     * @param paramName String
     * @param value String
     * @return String
     */
    private String doFilter(String paramName, String value) {
        return xssEscapeFilter.doFilter(path, paramName, value);
    }
}
