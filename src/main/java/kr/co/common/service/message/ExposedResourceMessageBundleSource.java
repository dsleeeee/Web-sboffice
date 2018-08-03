package kr.co.common.service.message;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Locale;
import java.util.Properties;

public class ExposedResourceMessageBundleSource extends ReloadableResourceBundleMessageSource {

    @Override
    protected Properties loadProperties(Resource resource, String fileName) throws IOException {
        return super.loadProperties(resource, fileName);
    }

    /**
     * Gets all messages for presented Locale.
     * @param locale user request's locale
     * @return all messages
     */
    public Properties getMessages(Locale locale){
        return getMergedProperties(locale).getProperties();
    }
}
