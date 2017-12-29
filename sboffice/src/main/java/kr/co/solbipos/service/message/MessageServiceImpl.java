package kr.co.solbipos.service.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {
    /**
     * 메세지 처리
     */
    @Autowired
    MessageSource message;

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
        return message.getMessage(code, args, defaultMessage, LocaleContextHolder.getLocale());
    }
}
