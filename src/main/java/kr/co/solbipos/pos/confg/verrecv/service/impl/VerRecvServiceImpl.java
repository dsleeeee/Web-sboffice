package kr.co.solbipos.pos.confg.verrecv.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.pos.confg.verrecv.service.VerRecvService;
import kr.co.solbipos.pos.confg.verrecv.service.VerRecvVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @Class Name : VerRecvServiceImpl.java
* @Description : 포스관리 > POS 설정관리 > POS 버전 수신현황
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
* @Copyright (C) by SOLBIPOS CORP. All right reserved.
*/
@Service("verRecvService")
public class VerRecvServiceImpl implements VerRecvService {

    private final VerRecvMapper verRecvMapper;

    /** Constructor Injection */
    @Autowired
    public VerRecvServiceImpl(VerRecvMapper verRecvMapper) {
        this.verRecvMapper = verRecvMapper;
    }

    /** 버전 목록 조회 */
    @Override
    public List<DefaultMap<String>> selectVerList(VerRecvVO verRecv) {
        return verRecvMapper.selectVerList(verRecv);
    }

    /** 매장 목록 조회 */
    @Override
    public List<DefaultMap<String>> selectStoreList(VerRecvVO verRecv) {
        return verRecvMapper.selectStoreList(verRecv);
    }

    /** 매장 목록 엑셀조회 */
    @Override
    public List<DefaultMap<String>> selectStoreExcelList(VerRecvVO verRecv) {
        return verRecvMapper.selectStoreExcelList(verRecv);
    }

    /** 수신매장 목록 조회 */
    @Override
    public List<DefaultMap<String>> selectStoreRecvList(VerRecvVO verRecv) {
        return verRecvMapper.selectStoreRecvList(verRecv);
    }

    /** 매장상세 */
    @Override
    public List<DefaultMap<String>> selectStoreDtl(VerRecvVO verRecv) {
        return verRecvMapper.selectStoreDtl(verRecv);
    }

    /** 버전 리스트 조회 */
    @Override
    public List<DefaultMap<String>> selectVerStoreList(VerRecvVO verRecv) {
        return verRecvMapper.selectVerStoreList(verRecv);
    }

    /** 매장 리스트 상세 조회 */
    @Override
    public List<DefaultMap<String>> selectVerStoreDtlList(VerRecvVO verRecv) {
        return verRecvMapper.selectVerStoreDtlList(verRecv);
    }
}
