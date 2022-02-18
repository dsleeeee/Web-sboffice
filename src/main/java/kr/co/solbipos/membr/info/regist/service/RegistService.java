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
     * 회원정보 리스트 조회(Excel 용)
     *
     * @param registVO
     * @param sessionInfoVO
     * @return
     */
    List<DefaultMap<String>> getMemberListExcel(RegistVO registVO, SessionInfoVO sessionInfoVO);

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
    String registMemberInfo(RegistVO registVO, SessionInfoVO sessionInfoVO);

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

    /** 카드정보 리스트 조회 */
    List<DefaultMap<String>> getCardList(RegistVO registVO, SessionInfoVO sessionInfoVO);

    /** 카드정보 중복체크 */
    int registCardInfo(RegistVO registVO, SessionInfoVO si);

    /** 배달정보 리스트 조회 */
    DefaultMap<Object> getDlvrList(RegistVO registVO, SessionInfoVO sessionInfoVO);

    /** 배달정보 등록 */
    int registDlvrInfo(RegistVO registVO, SessionInfoVO si);

    /** 배달전화번호정보 등록 */
    int registDlvrTelInfo(RegistVO registVO, SessionInfoVO si);

    /** 배달전화번호정보 리스트 조회 */
    List<DefaultMap<String>> getDlvrTelList(RegistVO registVO, SessionInfoVO sessionInfoVO);

    /** 중분류 리스트 조회 */
    DefaultMap<Object> getDlvrMzoneList(RegistVO registVO, SessionInfoVO sessionInfoVO);

    /** 대분류 리스트 조회 */
    List getLzoneList(RegistVO registVO, SessionInfoVO sessionInfoVO);

    /** 카드정보 등록 */
    int updateMembrCard(RegistVO registVO, SessionInfoVO si);

    /** 회원명 중복 체크 */
    int getMemberNmCount(RegistVO registVO, SessionInfoVO sessionInfoVO);

    /** 전화번호 중복 체크 */
    int getMemberTelNoCount(RegistVO registVO, SessionInfoVO sessionInfoVO);

    /** 카드정보 중복 체크 */
    int getMemberCardInfoCount(RegistVO registVO, SessionInfoVO sessionInfoVO);

    /** 카드 중복 체크( 카드번호 사용중인 회원번호 / X (해당 카드번호 미사용) ) */
    String getMemberCardInfoCountDetail(RegistVO registVO, SessionInfoVO sessionInfoVO);

    /** 배달전화번호정보 수정 */
    int updateDlvrTelInfo(RegistVO registVO, SessionInfoVO si);

    /** 배달전화번호정보 삭제 */
    int deleteDlvrTelInfo(RegistVO registVO, SessionInfoVO si);

    /** 배달주소지 수정 */
    int updateDlvrAddrInfo(RegistVO registVO, SessionInfoVO si);

    /** 배달주소지 삭제 */
    int deleteDlvrAddrInfo(RegistVO registVO, SessionInfoVO si);

    /** 회원정보 포인트변경내역 조회 */
    List<DefaultMap<String>> getMemberInfoPointList(RegistVO registVO, SessionInfoVO sessionInfoVO);

    /** 회원정보 구매내역 조회 */
    List<DefaultMap<String>> getMemberInfoBuyList(RegistVO registVO, SessionInfoVO sessionInfoVO);

    /** 회원 포인트 조회 팝업 - 조회 */
    List<DefaultMap<String>> getSearchMemberPointList(RegistVO registVO, SessionInfoVO sessionInfoVO);

    /** 회원 포인트 이관 팝업 - 저장 */
    int getMemberPointMoveSave(RegistVO registVO, SessionInfoVO sessionInfoVO);

    /** 회원 등급 조회 팝업 - 조회 */
    List<DefaultMap<String>> getSearchMemberClassList(RegistVO registVO, SessionInfoVO sessionInfoVO);

    /** 회원 포인트 조정 팝업 - 저장 */
    int getMemberPointAdjustSave(RegistVO registVO, SessionInfoVO sessionInfoVO);

    /** 회원 포인트 조정 팝업 - 조회 */
    DefaultMap<String> getMemberPointAdjustList(RegistVO registVO, SessionInfoVO sessionInfoVO);

    /** 회원 삭제 팝업 - 강제삭제 체크용 비밀번호 조회 */
    String getForcedDeleteChkPwd();

    /** 선택회원삭제 */
    int selectMemberDelete(RegistVO[] registVOs, SessionInfoVO sessionInfoVO);

    /** 전체회원삭제 */
    int allMemberDelete(RegistVO registVO, SessionInfoVO sessionInfoVO);
}
