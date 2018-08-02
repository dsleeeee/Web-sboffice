package kr.co.common.system;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Class Name : BaseEnv.java
 * @Description : 공통변수 저장소 Class
 *                  경로 지정시에는 맨뒤의 / 는 포함하지 않는다.
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.06.15  노현수      최초생성
 *
 * @author 솔비포스 차세대개발실 노현수
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Component("baseEnv")
public class BaseEnv {
    
    public static String PROFILE;

    public static String DOMAIN;
    
    public static String ENCODING;
    
    public static Integer SESSION_TIMEOUT_MIN;
    
    public static String REDIS_WEB_PREFIX;
    
    public static String REDIS_CODE_PREFIX;
    
    public static int SESSION_HIST_MENU_SIZE;
    
    public static int LOGIN_PWD_CHG_DAYS;
    
    public static String LOGIN_CHECK_ID_SAVE;
    
    public static int OTP_LIMIT_MINUTE;
    
    public static int OTP_SIZE;
    
    /*
     * 아래는 기존에 treepay 에서 쓰던것
     */
    public static String EMAIL_HOST;
    
    public static int EMAIL_PORT;
    
    public static String EXCEL_TEMP_DIR;
    
    public static String EXCEL_FONT;
    
    public static String FILE_UPLOAD_DIR;
    
    public static String DELI = String.valueOf((char) 31);
    
    /**
     * 공통변수 저장 관련
     */
    public static String PROP_ENV;
    
    public static String VIRTUAL_LOGIN_ID;
    
    
    @Value("#{config['profile']}")
    public void setProfile(String profile) {
        PROFILE = profile;
    }

    @Value("#{config['domain']}")
    public void setDomain(String domain) {
        DOMAIN = domain;
    }
    
    @Value("#{config['encoding']}")
    public void setEncoding(String encoding) {
        ENCODING = encoding;
    }
    
    @Value("#{config['session.timeout.min']}")
    public void setSessionTimeOutMin(Integer sessionTimeOutMin) {
        SESSION_TIMEOUT_MIN = sessionTimeOutMin;
    }
    
    @Value("#{config['redis.web.prefix']}")
    public void setRedisWebPrefix(String redisWebPrefix) {
        REDIS_WEB_PREFIX = redisWebPrefix;
    }
    
    @Value("#{config['redis.code.prefix']}")
    public void setRedisCodePrefix(String redisCodePrefix) {
        REDIS_CODE_PREFIX = redisCodePrefix;
    }
    
    @Value("#{config['session.hist.menu.size']}")
    public void setSessionHistMenuSize(int sessionHistMenuSize) {
        SESSION_HIST_MENU_SIZE = sessionHistMenuSize;
    }
    
    @Value("#{config['login.pwd.chg.days']}")
    public void setLoginPwdChgDays(int loginPwdChgDays) {
        LOGIN_PWD_CHG_DAYS = loginPwdChgDays;
    }
    
    @Value("#{config['login.check.id.save']}")
    public void setLoginCheckIdSave(String loginCheckIdSave) {
        LOGIN_CHECK_ID_SAVE = loginCheckIdSave;
    }
    
    @Value("#{config['login.otp.limit.minute']}")
    public void setOtpLimitMinute(int otpLimitMinute) {
        OTP_LIMIT_MINUTE = otpLimitMinute;
    }
    
    @Value("#{config['login.otp.size']}")
    public void setOtpSize(int otpSize) {
        OTP_SIZE = otpSize;
    }
    
    /*
     * 아래는 기존에 treepay 에서 쓰던것
     */
    @Value("#{config['email.host']}")
    public void setEmailHost(String emailHost) {
        EMAIL_HOST = emailHost;
    }
    
    @Value("#{config['email.port']}")
    public void setEmailPort(int emailPort) {
        EMAIL_PORT = emailPort;
    }
    
    @Value("#{config['excel.tempDir']}")
    public void setExcelTempDir(String excelTempDir) {
        EXCEL_TEMP_DIR = excelTempDir;
    }
    
    @Value("#{config['excel.font']}")
    public void setExcelDefaultFont(String excelFont) {
        EXCEL_FONT = excelFont;
    }
    
    @Value("#{config['file.upload.dir']}")
    public void setFileUploadDir(String fileUploadDir) {
        FILE_UPLOAD_DIR = fileUploadDir;
    }

    public void setDeli(String deli) {
        DELI = deli;
    }
    
    /**
     * 공통변수 저장 관련
     */
    @Value("#{config['baseEnv.prop.env']}")
    public void setPropEnv(String propEnv) {
        PROP_ENV = propEnv;
    }
    
}
