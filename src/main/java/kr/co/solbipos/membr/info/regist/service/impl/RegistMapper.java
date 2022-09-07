package kr.co.solbipos.membr.info.regist.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.membr.anals.postpaid.service.PostpaidStoreVO;
import kr.co.solbipos.membr.info.grade.service.MembrClassVO;
import kr.co.solbipos.membr.info.regist.service.MemberMappingVO;
import kr.co.solbipos.membr.info.regist.service.RegistVO;
import kr.co.solbipos.store.hq.hqmanage.service.HqManageVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : RegistMapper.java
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
@Mapper
@Repository
public interface RegistMapper {

    /**
     * 등록 매장 리스트 조회
     *
     * @return
     */
    List<DefaultMap<String>> getRegistStore(HqManageVO hqManageVO);

    /**
     * 회원등급 리스트 조회
     * @param membrClassVO
     * @return
     */
    List<DefaultMap<String>> getMemberClassList(MembrClassVO membrClassVO);

    /**
     * 회원정보 리스트 조회
     *
     * @param registVO
     * @return
     */
    List<DefaultMap<String>> getMemberList(RegistVO registVO);

    /**
     * 회원정보 리스트 조회
     *
     * @param registVO
     * @return
     */
    List<DefaultMap<String>> getMemberListExcel(RegistVO registVO);

    /**
     * 회원정보 조회
     *
     * @param registVO
     * @return
     */
    DefaultMap<String> getMemberInfo(RegistVO registVO);


    /** 회원번호 채번 */
    String getNewMemberNo(RegistVO registVO);

    /**
     * 회원정보 저장
     *
     * @param registVO
     * @return
     */
    int registMemberInfo(RegistVO registVO);

    /** 선불회원 등록 */
    int registMemberPrepaid(RegistVO registVO);

    /**
     * 회원정보 수정
     *
     * @param registVO
     * @return
     */
    int updateMemberInfo(RegistVO registVO);

    /**
     * 회원정보 삭제
     *
     * @param registVO
     * @return
     */
    int deleteMemberInfo(RegistVO registVO);

    /**
     * 회원카드 등록
     *
     * @param registVO
     * @return
     */
    int insertMembrCard(RegistVO registVO);

    /**
     * 회원카드 수정
     *
     * @param registVO
     * @return
     */
    int updateMembrCard(RegistVO registVO);

    /**
     * 후불 회원 등록 매장 조회
     * @param postpaidStoreVO
     * @return
     */
    List<DefaultMap<String>> getRegStoreList(PostpaidStoreVO postpaidStoreVO);

    /**
     * 후불 회원 미등록 매장 조회
     * @param postpaidStoreVO
     * @return
     */
    List<DefaultMap<String>> getNoRegStoreList(PostpaidStoreVO postpaidStoreVO);

    /**
     * 후불회원 적용매장 삭제
     * @param postpaidStoreVO
     * @return
     */
    int deletePostpaidStore(PostpaidStoreVO postpaidStoreVO);

    /**
     * 후불회원 적용매장 등록
     * @param postpaidStoreVO
     * @return
     */
    int registPostpaidStore(PostpaidStoreVO postpaidStoreVO);

    /** 회원 거래처 매핑 코드 조회 */
    List<DefaultMap<String>> getMappingCompany(MemberMappingVO memberMappingVO);

    /** 회원 거래처 매핑코드 등록 및 저장 */
    int registMemberMappingCode(RegistVO registVO);

    /** 회원 거래처 매핑 팝업 - 회원 거래처 매핑 조회 */
    List<DefaultMap<String>> getMemberVendorMappingList(RegistVO registVO);

    /** 회원정보 등록,수정시 본사코드 A0007만 */
    String registPoslinkPtn(RegistVO registVO);

    /** 회원정보 등록,수정시 본사코드 A0007만 */
    String registSposBillPtn(RegistVO registVO);

    DefaultMap<String> getMemberSelect(RegistVO registVO);

    /** 카드정보 리스트 조회 */
    List<DefaultMap<String>> getCardList(RegistVO registVO);

    /** 회원배달 등록 */
    int insertMembrDlvr(RegistVO registVO);

    /** 배달전화정보 추가 */
    int insertMembrDlvrTel(RegistVO registVO);

    /** 배달지 조회 */
    List<DefaultMap<String>> getDlvrList(RegistVO registVO);

    /** 배달 전호번호 */
    List<DefaultMap<String>> getDlvrTelList(RegistVO registVO);

    /** 대분류 리스트 조회 */
    List getMembrLzoneList(RegistVO registVO);

    /** 배달구역 중분류 리스트 조회 */
    List getDlvrMzoneList(RegistVO registVO);

    int updateMemberCard(RegistVO registVO);

    /** 회원정보 수정 */
    int updateMembr(RegistVO registVO);

    /** 회원명 중복 체크 */
    int getMemberNmCount(RegistVO registVO);

    /** 전화번호 중복 체크 */
    int getMemberTelNoCount(RegistVO registVO);

    /** 카드정보 중복 체크 */
    int getMemberCardInfoCount(RegistVO registVO);

    /** 카드 중복 체크( 카드번호 사용중인 회원번호 / X (해당 카드번호 미사용) ) */
    String getMemberCardInfoCountDetail(RegistVO registVO);

    /** 신규가입 적립 POINT */
    int newJoinSavePointInfo(RegistVO registVO);

    /** 회원등급에 따른 신규가입 적립포인트 */
    void insertMembrPointHist(RegistVO registVO);

    /** 배달전화정보 수정 */
    int updateMembrDlvrTel(RegistVO registVO);

    /** 배달전화정보 삭제 */
    int deleteMembrDlvrTel(RegistVO registVO);

    /** 배달주소지 수정 */
    int updateDlvrAddrInfo(RegistVO registVO);

    /** 배달주소지 삭제 */
    int deleteDlvrAddrInfo(RegistVO registVO);

    /** 회원정보 포인트변경내역 - 조회 */
    List<DefaultMap<String>> getMemberInfoPointList(RegistVO registVO);

    /** 회원정보 구매내역 - 조회 */
    List<DefaultMap<String>> getMemberInfoBuyList(RegistVO registVO);

    /** 회원 포인트 조회 팝업 - 조회(보내는 회원) */
    List<DefaultMap<String>> getSearchMemberPointList(RegistVO registVO);

    /** 회원 포인트 조회 팝업 - 조회(받는 회원) */
    List<DefaultMap<String>> getSearchMemberPointReceiveList(RegistVO registVO);

    /** 회원 등급 조회 팝업 - 조회 */
    List<DefaultMap<String>> getSearchMemberClassList(RegistVO registVO);

    /** 회원 포인트 조정 팝업 - 저장 update */
    int getMemberPointAdjustSaveUpdate(RegistVO registVO);

    /** 회원 포인트 조정 팝업 - 조회 */
    DefaultMap<String> getMemberPointAdjustList(RegistVO registVO);

    /** 회원 삭제 팝업 - 강제삭제 체크용 비밀번호 조회 */
    String getForcedDeleteChkPwd();

    /** 회원 삭제 팝업 - 회원 삭제 가능여부 조회 */
   int getMemberDeleteYnChk(RegistVO registVO);

    /** 회원 삭제 팝업 - 회원정보 영구삭제 */
    int deleteMember(RegistVO registVO);

    /** 회원 삭제 팝업 - 회원카드정보 영구삭제 */
    int deleteMemberCard(RegistVO registVO);

    /** 회원 삭제 팝업 - 회원 포인트 변경내역 영구삭제 */
    int deleteMemberPointHist(RegistVO registVO);

    /** 회원 삭제 팝업 -  회원 포인트 정보 영구삭제 */
    int deleteMemberPoint(RegistVO registVO);

    /** 회원 삭제 팝업 -  회원 포인트 정보 영구삭제 */
    int deleteMemberPointStore(RegistVO registVO);

    /** 회원 삭제 팝업 - 회원 후불원장 영구삭제 */
    int deleteMemberPostpaid(RegistVO registVO);

    /** 회원 삭제 팝업 - 후불회원 등록매장 영구삭제 */
    int deleteMemberPostpaidStore(RegistVO registVO);

    /** 회원 삭제 팝업 - 회원 선불원장 영구삭제  */
    int deleteMemberPrepaid(RegistVO registVO);

    /** 회원 삭제 팝업 - 선불회원 등록매장 영구삭제 */
    int deleteMemberPrepaidStore(RegistVO registVO);

    /** 회원 삭제 팝업 - 선/후불 잔액 영구삭제 */
    int deleteMemberPaidBalance(RegistVO registVO);

    /** 회원 삭제 팝업 - 전체회원 중 삭제불가회원 '미사용'으로 수정 */
    int updateAllMemberUseYn(RegistVO registVO);

    /** 회원 삭제 팝업 - 전체회원 회원정보 영구삭제 */
    int deleteAllMember(RegistVO registVO);

    /** 회원 삭제 팝업 - 전체회원 회원카드정보 영구삭제 */
    int deleteAllMemberCard(RegistVO registVO);

    /** 회원 삭제 팝업 - 전체회원 회원 포인트 변경내역 영구삭제 */
    int deleteAllMemberPointHist(RegistVO registVO);

    /** 회원 삭제 팝업 - 전체회원 회원 포인트 정보 영구삭제 */
    int deleteAllMemberPoint(RegistVO registVO);

    /** 회원 삭제 팝업 - 전체회원 회원 포인트 정보 영구삭제 */
    int deleteAllMemberPointStore(RegistVO registVO);

    /** 회원 삭제 팝업 - 전체회원 회원 후불원장 영구삭제 */
    int deleteAllMemberPostpaid(RegistVO registVO);

    /** 회원 삭제 팝업 - 전체회원 후불회원 등록매장 영구삭제 */
    int deleteAllMemberPostpaidStore(RegistVO registVO);

    /** 회원 삭제 팝업 - 전체회원 회원 선불원장 영구삭제  */
    int deleteAllMemberPrepaid(RegistVO registVO);

    /** 회원 삭제 팝업 - 전체회원 선불회원 등록매장 영구삭제 */
    int deleteAllMemberPrepaidStore(RegistVO registVO);

    /** 회원 삭제 팝업 - 전체회원 선/후불 잔액 영구삭제 */
    int deleteAllMemberPaidBalance(RegistVO registVO);

    /** 코드별 본사 공통코드 콤보박스 조회 */
    List<DefaultMap<Object>> getHqNmcodeComboList(RegistVO registVO);

    /** 회원정보 저장 (광운대아이스링크 추가정보) */
    int mergeMemberInfoAddKWU(RegistVO registVO);

    /** 회원정보 조회 (광운대아이스링크 추가정보) */
    DefaultMap<String> getMemberInfoAddKWU(RegistVO registVO);
}
