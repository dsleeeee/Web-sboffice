package kr.co.solbipos.base.prod.sidemenu.service;

/**
 * @Class Name : SideMenuCopyReqService.java
 * @Description : 사이드메뉴 복사요청 멱등처리 - 같은 nonce 자동 재전송을 차단한다.
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.09.22  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026. 09. 22.
 * @version 1.0
 * @See
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface SideMenuCopyReqService {

    /**
     * 복사요청 nonce 선점(멱등).
     * 최초 요청이면 정상 리턴, 같은 nonce 가 이미 있으면(=자동 재전송)
     * org.springframework.dao.DuplicateKeyException 을 던진다(호출측에서 잡아 차단 처리).
     *
     * ※ 본 저장 트랜잭션과 분리(REQUIRES_NEW)되어 즉시 커밋된다. 그래야 오래 걸리는
     *   저장(커밋 전) 도중 들어온 2차가 1차의 선점행을 보고 차단된다.
     * ※ 예외를 메서드 밖으로 던지므로 rollback-only 트랜잭션이 정상 롤백된다
     *   (@Transactional 안에서 catch 후 정상리턴하면 UnexpectedRollbackException 발생하므로 금지).
     */
    void claim(SideMenuCopyReqVO sideMenuCopyReqVO);

    /** 처리 완료/실패 상태 갱신 (DONE/ERR) */
    void finish(String reqNonce, String status);

    /** 자동 재전송(차단) 감지 시 원본 행의 재전송 횟수 누적 (기록용, 실패해도 무시) */
    void markResend(String reqNonce);

    /** 복사요청 처리상태 조회 (ING/DONE/ERR, 없으면 null) - 재전송 차단 후 화면 폴링용 */
    String getProcStatus(String reqNonce);
}
