package kr.co.solbipos.sys.etc.vancard.service;

import java.util.List;
import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

/**
 * @Class Name : VanCardService.java
 * @Description : 시스템관리 > VAN/CARD사 관리
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
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface VanCardService {
    
    /** VAN사 목록 조회 */
    List<DefaultMap<String>> getVanCmpnyList(VanCmpnyVO vanCmpnyVO);
    
    /** VAN사 목록 저장 */
    int saveVanCmpnyList(VanCmpnyVO[] vanCmpnyVOs, SessionInfoVO sessionInfoVO);
    
    /** CARD사 목록 조회 */
    List<DefaultMap<String>> getCardCmpnyList(CardCmpnyVO cardCmpnyVO);
    
    /** CARD사 목록 저장 */
    int saveCardCmpnyList(CardCmpnyVO[] cardCmpnyVOs, SessionInfoVO sessionInfoVO);
    
    /** VAN/CARD사 매핑 목록 조회 */
    List<DefaultMap<String>> getMapngList(VanCardVO vanCardVO);
    
    /** VAN/CARD사 매핑 목록 조회 */
    int saveMapngList(VanCardVO[] vanCardVOs, SessionInfoVO sessionInfoVO);
    
}
