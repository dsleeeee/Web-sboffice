package kr.co.solbipos.pos.confg.verrecv.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kr.co.solbipos.pos.confg.verrecv.service.VerRecvService;
import kr.co.solbipos.pos.confg.verrecv.service.VerRecvVO;
import kr.co.common.data.structure.DefaultMap;

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
*  Copyright (C) by SOLBIPOS CORP. All right reserved.
*/
@Service("verRecvService")
public class VerRecvServiceImpl implements VerRecvService {

    @Autowired
    VerRecvMapper mapper;

    @Override
    public List<DefaultMap<String>> selectVerList(VerRecvVO verRecv) {
        return mapper.selectVerList(verRecv);
    }

    @Override
    public List<DefaultMap<String>> selectStoreList(VerRecvVO verRecv) {
        return mapper.selectStoreList(verRecv);
    }

    @Override
    public List<DefaultMap<String>> selectStoreRecvList(VerRecvVO verRecv) {
        return mapper.selectStoreRecvList(verRecv);
    }

    @Override
    public List<DefaultMap<String>> selectStoreDtl(VerRecvVO verRecv) {
        return mapper.selectStoreDtl(verRecv);
    }

    @Override
    public List<DefaultMap<String>> selectVerStoreList(VerRecvVO verRecv) {
        return mapper.selectVerStoreList(verRecv);
    }
    
    @Override
    public List<DefaultMap<String>> selectVerStoreDtlList(VerRecvVO verRecv) {
        return mapper.selectVerStoreDtlList(verRecv);
    }
}
