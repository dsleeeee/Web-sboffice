package kr.co.solbipos.membr.info.regist.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.membr.anals.credit.service.CreditStoreVO;

import java.util.List;
import java.util.Map;

/**
 * @Class Name : RegistService.java
 * @Description : 회원관리 > 회원정보 > 회원정보관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.05.01  정용길      최초생성
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
    List<DefaultMap<String>> selectRgstrStore(SessionInfoVO sessionInfoVO);

    /**
     * 회원 등급 리스트 조회
     * @param sessionInfoVO
     * @return
     */
    List<DefaultMap<String>> selectMembrClassList(SessionInfoVO sessionInfoVO);

    /**
     * 회원정보 리스트 조회
     *
     * @param registVO
     * @param <E>
     * @return
     */
    <E> List<E> selectMembers(RegistVO registVO, SessionInfoVO sessionInfoVO);

    /**
     * 회원정보 조회
     *
     * @param registVO
     * @return
     */
    RegistVO selectMember(RegistVO registVO, SessionInfoVO sessionInfoVO);

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
     * @param registVO
     * @return
     */
    int deleteMemberInfo(RegistVO registVO , SessionInfoVO sessionInfoVO );


    //    /**
//     * 회원정보 저장
//     *
//     * @param registVO
//     * @return
//     */
//    int insertRegistMember(RegistVO registVO);


//    /**
//     * 회원정보 수정
//     *
//     * @param registVO
//     * @return
//     */
//    int updateMember(RegistVO registVO);


//    /**
//     * 회원카드 등록
//     *
//     * @param registVO
//     * @return
//     */
//    int insertMembrCard(RegistVO registVO);

//    /**
//     * 회원카드 수정
//     *
//     * @param registVO
//     * @return
//     */
//    int updateMembrCard(RegistVO registVO);

    /**
     * 후불 회원 등록 매장 조회
     * @param creditStoreVO
     * @return
     */
    Map<String,Object> getCreditStoreLists(CreditStoreVO creditStoreVO, SessionInfoVO sessionInfoVO);

    /**
     * 후불회원 매장등록
     * @param creditStoreVOs
     * @return
     */
    int saveCreditStore(CreditStoreVO[] creditStoreVOs, SessionInfoVO sessionInfoVO);

}
