package kr.co.common.service.message;

/**
 * 메세지 ( Interface )
 * 
 * @author 이호원
 */
public interface MessageService {
    /**
     * 가져오기
     * 
     * @param code {@code String} 메세지 코드
     * @return {@code String} 내용
     * @see MessageService#get( String code, Object[] args, String defaultMessage )
     */
    String get(String code);

    /**
     * 가져오기
     * 
     * @param code {@code String} 메세지 코드
     * @param args {@code String} 내용에 추가 될 값
     * @return {@code String} 내용
     * @see MessageService#get( String code, Object[] args, String defaultMessage )
     */
    String get(String code, Object[] args);

    /**
     * 가져오기
     * 
     * @param code {@code String} 메세지 코드
     * @param args {@code Object[]} 내용에 추가 될 값
     * @param defaultMessage {@code String} 대체할 내용
     * @return {@code String} 내용
     */
    String get(String code, Object[] args, String defaultMessage);
}
