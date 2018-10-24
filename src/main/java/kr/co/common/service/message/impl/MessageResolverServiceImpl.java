package kr.co.common.service.message.impl;

import kr.co.common.service.message.ExposedResourceMessageBundleSource;
import kr.co.common.service.message.MessageResolveService;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.XmlWebApplicationContext;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

@Service("messageResolveService")
public class MessageResolverServiceImpl implements MessageResolveService {

    private MessageSource messageSource;

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getMessage(String key, Object[] arguments, Locale locale) {
        String message = "";
        try {
            message = messageSource.getMessage(key, arguments, locale);
        } catch (NoSuchMessageException e) {
            message = key;
        }
        return message;
    }

    public Map<String, String> getMessages(Locale locale) {
        Properties properties = ((XmlWebApplicationContext) messageSource)
            .getBean("messageSource", ExposedResourceMessageBundleSource.class).getMessages(locale);
        Map<String, String> messagesMap = new HashMap<String, String>();
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            if (entry.getKey() != null && entry.getValue() != null) {
                messagesMap.put(entry.getKey().toString(), entry.getValue().toString());
            }
        }
        return messagesMap;
    }
}
