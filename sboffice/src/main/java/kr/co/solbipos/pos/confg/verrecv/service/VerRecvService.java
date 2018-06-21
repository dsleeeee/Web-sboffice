package kr.co.solbipos.pos.confg.verrecv.service;

import java.util.List;
import kr.co.common.data.structure.DefaultMap;

/**
* @Class Name : VerRecvService.java
* @Description : 포스관리 > POS 설정관리 > POS 버전 수신현황
* @Modification Information
* @
* @  수정일      수정자              수정내용
* @ ----------  ---------   -------------------------------
* @ 2015.06.01  김지은      최초생성
*
* @author 솔비포스 차세대개발실 김지은
* @since 2018. 05.01
* @version 1.0
* @see
*
*  Copyright (C) by SOLBIPOS CORP. All right reserved.
*/
public interface VerRecvService {
    
    /**
     * 버전별수신현황 - 버전 목록 조회
     * 
     * @param verRecv
     * @return
     */
    List<DefaultMap<String>> selectVerList(VerRecvVO verRecv);

    /**
     * 버전별수신현황 - 매장 목록 조회
     * 
     * @param verRecv
     * @return
     */
    List<DefaultMap<String>> selectStoreList(VerRecvVO verRecv);

    /**
     * 매장별수신현황 - 수신매장 목록 조회
     * 
     * @param verRecv
     * @return
     */
    List<DefaultMap<String>> selectStoreRecvList(VerRecvVO verRecv);

    /**
     * 매장별수신현황 - 매장 리스트 조회 - 매장상세
     * 
     * @param verRecv
     * @return
     */
    List<DefaultMap<String>> selectStoreDtl(VerRecvVO verRecv);

    /**
     * 버전별매장현황 - 버전 리스트 조회
     * 
     * @param verRecv
     * @return
     */
    List<DefaultMap<String>> selectVerStoreList(VerRecvVO verRecv);

    /**
     * 버전별매장현황 - 매장 리스트 조회
     * 
     * @param verRecv
     * @return
     */
    List<DefaultMap<String>> selectVerStoreDtlList(VerRecvVO verRecv);

}
