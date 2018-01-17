package kr.co.solbipos.system;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import lombok.Data;

/**
 * 환경변수
 * 
 * @author 이호원
 */
@Data
@Component
public class Prop {
    
    @Value("#{config['profile']}")
    public String profile;
    
    @Value("#{config['domain']}")
    public String domain;
    
    @Value("#{config['encoding']}")
    public String encoding;

    @Value("#{config['session.timeout.min']}")
    public Integer sessionTimeout;

    @Value("#{config['redis.web.prefix']}")
    public String redisWebPrefix;

    @Value("#{config['redis.code.prefix']}")
    public String redisCodePrefix;
    
    @Value("#{config['session.hist.menu.size']}")
    public int sessionHistMenuSize;
    
    @Value("#{config['login.pwd.chg.days']}")
    public int loginPwdChgDays;
    
    @Value("#{config['login.check.id.save']}")
    public String loginSaveId;

    @Value("#{config['login.otp.limit']}")
    public int otpLimit;
    
    /*
     * 아래는 기존에 treepay 에서 쓰던것
     */
    @Value("#{config['email.host']}")
    public String emailHost;
    @Value("#{config['email.port']}")
    public int emailPort;
    @Value("#{config['excel.tempDir']}")
    public String excelTempDir;
    @Value("#{config['excel.font']}")
    public String defaultFont;

    public String DELI = String.valueOf((char) 31);
}
