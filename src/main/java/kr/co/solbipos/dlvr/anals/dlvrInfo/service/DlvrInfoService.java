package kr.co.solbipos.dlvr.anals.dlvrInfo.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : DlvrInfoService.java
 * @Description : 배달관리 > 배달분석 > 배달내역
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.07.09  Joshua      최초생성
 *
 * @author
 * @since 2020.07.09
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface DlvrInfoService {

    /** 배달내역조회 */
    public List<DefaultMap<Object>> getDlvrInfoList(DlvrInfoVO dlvrInfoVO, SessionInfoVO sessionInfoVO);

    /** 영수증 상세 조회 */
    DefaultMap<String> getBillInfo(DlvrInfoVO dlvrInfoVO, SessionInfoVO sessionInfoVO);

    /** 영수증 상세 조회 */
    List<DefaultMap<Object>> getBillInfoList(DlvrInfoVO dlvrInfoVO, SessionInfoVO sessionInfoVO);

    /** 영수번호 상세팝업 - 영수증상세 결제내역 조회 */
    DefaultMap<String> getBillPayInfo(DlvrInfoVO dlvrInfoVO, SessionInfoVO sessionInfoVO);

    /** 수번호 상세팝업 - 영수증상세 방문인원 조회 */
    DefaultMap<String> getBillGuestInfo(DlvrInfoVO dlvrInfoVO, SessionInfoVO sessionInfoVO);

    /** 영수번호 상세팝업 - 매출종합 리스트 조회 */
    List<DefaultMap<String>> getBillProdList(DlvrInfoVO dlvrInfoVO, SessionInfoVO sessionInfoVO);
}
