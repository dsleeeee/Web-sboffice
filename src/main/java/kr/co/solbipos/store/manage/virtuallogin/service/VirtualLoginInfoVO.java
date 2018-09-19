package kr.co.solbipos.store.manage.virtuallogin.service;

import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

public class VirtualLoginInfoVO implements Serializable {

    private static final long serialVersionUID = 1277796477653660819L;
    private String sessionId;
    private SessionInfoVO sessionInfoVO;

    /**
     * @return the sessionId
     */
    public String getSessionId() {
        return sessionId;
    }

    /**
     * @param sessionId the sessionId to set
     */
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    /**
     * @return the sessionInfoVO
     */
    public SessionInfoVO getSessionInfoVO() {
        return sessionInfoVO;
    }

    /**
     * @param sessionInfoVO the sessionInfoVO to set
     */
    public void setSessionInfoVO(SessionInfoVO sessionInfoVO) {
        this.sessionInfoVO = sessionInfoVO;
    }

    /** VO내의 모든 값 출력 */
    public String getProperties() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
