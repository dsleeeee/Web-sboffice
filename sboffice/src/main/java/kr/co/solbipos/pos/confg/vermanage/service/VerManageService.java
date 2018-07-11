package kr.co.solbipos.pos.confg.vermanage.service;

import java.util.List;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

/**
* @Class Name : VerManageService.java
* @Description : 포스관리 > POS 설정관리 > POS 버전 관리
* @Modification Information
* @
* @  수정일      수정자              수정내용
* @ ----------  ---------   -------------------------------
* @ 2018.06.01  김지은      최초생성
*
* @author 솔비포스 차세대개발실 김지은
* @since 2018. 05.01
* @version 1.0
* @see
*
*  Copyright (C) by SOLBIPOS CORP. All right reserved.
*/
public interface VerManageService {

    /**
     * 포스버전 목록 조회
     * 
     * @param verInfo
     * @return
     */
    List<DefaultMap<String>> list(VerInfoVO verInfo);

    /**
     * 포스버전정보 상세 조회
     * 
     * @param verInfo
     * @return
     */
    DefaultMap<String> dtlInfo(VerInfoVO verInfo);

    /**
     * 매장목록 조회
     * 
     * @param verInfo
     * @return
     */
    List<DefaultMap<String>> storeList(VerInfoVO verInfo);
    
    /**
     * 버전 삭제
     * 
     * @param verInfo
     * @return
     */
    int verDelete(VerInfoVO verInfo);
    
    /**
     * 버전 중복 체크
     * 
     * @param verInfo
     * @return
     */
    int chkVerSerNo(VerInfoVO verInfo);
    
    /**
     * 버전 등록
     * 
     * @param request
     * @param verInfo
     * @return
     */
    boolean regist(MultipartHttpServletRequest multi, SessionInfoVO sessionInfo);
    
    /**
     * 버전 수정
     * 
     * @param request
     * @param verInfo
     * @return
     */
    boolean modify(MultipartHttpServletRequest request, SessionInfoVO sessionInfo);

    /**
     * 매장추가 매장검색
     * 
     * @param applcStore
     * @return
     */
    List<DefaultMap<String>> srchStoreList(ApplcStoreVO applcStore);

    /**
     * 버전 적용 매장 등록
     * 
     * @param applcStore
     * @param sessionInfo
     * @return
     */
    int registStore(ApplcStoreVO[] applcStore, SessionInfoVO sessionInfo);

}
