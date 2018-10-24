package kr.co.common.service.message.impl;

import kr.co.common.service.message.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

/**
 * @author 정용길
 *
 */
@Service("messageService")
public class MessageServiceImpl implements MessageService {

    private final MessageSource messageSource;

    /** Constructor Injection */
    @Autowired
    public MessageServiceImpl(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * 가져오기
     * 
     * @param code {@code String} 메세지 코드
     * @return {@code String} 내용
     * @see MessageService#get( String code, Object[] args, String defaultMessage )
     */
    public String get(String code) {
        return get(code, null, "");
    }

    /**
     * 가져오기
     * 
     * @param code {@code String} 메세지 코드
     * @param args {@code String} 내용에 추가 될 값
     * @return {@code String} 내용
     * @see MessageService#get( String code, Object[] args, String defaultMessage )
     */
    public String get(String code, Object[] args) {
        return get(code, args, "");
    }

    /**
     * 가져오기
     * 
     * @param code {@code String} 메세지 코드
     * @param args {@code Object[]} 내용에 추가 될 값
     * @param defaultMessage {@code String} 대체할 내용
     * @return {@code String} 내용
     */
    public String get(String code, Object[] args, String defaultMessage) {
        return messageSource.getMessage(code, args, defaultMessage, LocaleContextHolder.getLocale());
    }
}
