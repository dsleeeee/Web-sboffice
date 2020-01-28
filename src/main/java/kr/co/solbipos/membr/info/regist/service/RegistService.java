package kr.co.solbipos.membr.info.regist.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.membr.anals.postpaid.service.PostpaidStoreVO;

import java.util.List;

/**
 * @Class Name : RegistService.java
 * @Description : 회원관리 > 회원정보 > 회원정보관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.05.01  정용길      최초생성
 * @ 2018.11.09  김지은      회원정보관리 수정
 *
 * @author NHN한국사이버결제 KCP 정용길
 * @since 2018.05.01
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface RegistService {

    /**
     * 등록 매장 리스트 조회
     *
     * @return
     */
    List<DefaultMap<String>> getRegistStore(SessionInfoVO sessionInfoVO);

    /**
     * 회원 등급 리스트 조회
     * @param sessionInfoVO
     * @return
     */
    List<DefaultMap<String>> getMembrClassList(SessionInfoVO sessionInfoVO);

    /**
     * 회원정보 리스트 조회
     *
     * @param registVO
     * @param sessionInfoVO
     * @return
     */
    List<DefaultMap<String>> getMemberList(RegistVO registVO, SessionInfoVO sessionInfoVO);

    /**
     * 회원정보 조회
     *
     * @param registVO
     * @return
     */
    DefaultMap<String> getMemberInfo(RegistVO registVO, SessionInfoVO sessionInfoVO);

    /**
     * 회원정보 등록
     *
     * @param registVO
     * @return
     */
    int registMemberInfo(RegistVO registVO, SessionInfoVO sessionInfoVO);

    /**
     * 회원정보 수정
     *
     * @param registVO
     * @return
     */
    int updateMemberInfo(RegistVO registVO, SessionInfoVO sessionInfoVO );

    /**
     * 회원정보 삭제
     *
     * @param registVOs
     * @return
     */
    int deleteMemberInfo(RegistVO[] registVOs , SessionInfoVO sessionInfoVO );


    /**
     * 후불 회원 등록 매장 조회
     * @param postpaidStoreVO
     * @return
     */
    List<DefaultMap<String>> getPostpaidStoreLists(PostpaidStoreVO postpaidStoreVO, SessionInfoVO sessionInfoVO);

    /**
     * 후불회원 매장등록
     * @param postpaidStoreVOs
     * @return
     */
    int registPostpaidStore(PostpaidStoreVO[] postpaidStoreVOs, SessionInfoVO sessionInfoVO);

    /**
     * 후불회원 매장삭제
     * @param postpaidStoreVOs
     * @return
     */
    int deletePostpaidStore(PostpaidStoreVO[] postpaidStoreVOs, SessionInfoVO sessionInfoVO);

    /**
     * 회원 거래처 매핑코드
     * @param memberMappingVO
     * @return
     */
    List<DefaultMap<String>> getMappingCompany(MemberMappingVO memberMappingVO, SessionInfoVO sessionInfoVO);

    /** 회원 거래처 매핑 팝업 - 회원 거래처 매핑 조회 */
    List<DefaultMap<String>> getMemberVendorMappingList(RegistVO registVO, SessionInfoVO sessionInfoVO);
}
