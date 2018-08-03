package kr.co.common.service.message;

import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Map;

public interface MessageResolveService extends MessageSourceAware {
    String getMessage(String key, Object[] argumentsForKey, Locale locale);
    Map<String,String> getMessages(Locale locale);
}
