package kr.co.common.service.alert;

import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * 사내 메신저(두레이 인커밍 웹훅) 알림 ( Interface )
 *
 * 업무별 차단/이상 상황 알림을 한 곳에서 관리한다.
 * 알림은 비동기(단일 데몬 스레드)로 전송되며, 실패해도 경고 로그만 남기고
 * 호출한 업무 흐름에는 영향을 주지 않는다.
 *
 * @author 김유승
 */
public interface DoorayAlertService {

    /**
     * SMS 차단 알림 - 금칙어 탐지
     *
     * @param keywords      {@code String} 탐지된 금칙어(조합)
     * @param msgContent    {@code String} 발송 시도 본문 (앞 50자만 미리보기로 전송)
     * @param sessionInfoVO {@code SessionInfoVO} 세션정보 (조직코드/사용자ID 표기용)
     */
    void notifySmsBadwordBlock(String keywords, String msgContent, SessionInfoVO sessionInfoVO);

    /**
     * SMS 차단 알림 - URL 탐지
     *
     * @param urlSummaries  {@code List<String>} 탐지된 URL 요약 목록
     * @param msgContent    {@code String} 발송 시도 본문 (앞 50자만 미리보기로 전송)
     * @param sessionInfoVO {@code SessionInfoVO} 세션정보 (조직코드/사용자ID 표기용)
     */
    void notifySmsUrlBlock(List<String> urlSummaries, String msgContent, SessionInfoVO sessionInfoVO);

    /**
     * 사이드메뉴 복사저장 자동 재전송 차단 알림
     *
     * @param reqType       {@code String} CLASS_COPY(분류복사) / PROD_COPY(상품복사)
     * @param reqNonce      {@code String} 차단된 요청의 nonce
     * @param reqCnt        {@code int} 요청 건수
     * @param sessionInfoVO {@code SessionInfoVO} 세션정보 (조직코드/사용자ID 표기용)
     */
    void notifySideMenuResendBlock(String reqType, String reqNonce, int reqCnt, SessionInfoVO sessionInfoVO);
}
