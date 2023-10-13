package kr.co.solbipos.pos.confg.storeVerDel.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.pos.confg.storeVerDel.service.StoreVerDelService;
import kr.co.solbipos.pos.confg.storeVerDel.service.StoreVerDelVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @Class Name : StoreVerDelServiceImpl.java
* @Description : 포스관리 > POS 설정관리 > 매장별 POS 버전 삭제
* @Modification Information
* @
* @  수정일      수정자              수정내용
* @ ----------  ---------   -------------------------------
* @ 2023.10.12  이다솜      최초생성
*
* @author 솔비포스 개발본부 WEB개발팀 이다솜
* @since 2023.10.12
* @version 1.0
*
* @Copyright (C) by SOLBIPOS CORP. All right reserved.
*/
@Service("storeVerDelService")
public class StoreVerDelServiceImpl implements StoreVerDelService {

    private final StoreVerDelMapper storeVerDelMapper;
    private final MessageService messageService;

    /** Constructor Injection */
    @Autowired
    public StoreVerDelServiceImpl(StoreVerDelMapper storeVerDelMapper, MessageService messageService) {
        this.storeVerDelMapper = storeVerDelMapper;
        this.messageService = messageService;
    }

    /** 매장별 포스 버전 정보 조회 */
    @Override
    public List<DefaultMap<Object>> getStoreVerList(StoreVerDelVO storeVerDelVO, SessionInfoVO sessionInfoVO) {
        return storeVerDelMapper.getStoreVerList(storeVerDelVO);
    }

    /** 매장별 포스 버전 정보 조회 엑셀다운로드*/
    @Override
    public List<DefaultMap<Object>> getStoreVerExcelList(StoreVerDelVO storeVerDelVO, SessionInfoVO sessionInfoVO) {
        return storeVerDelMapper.getStoreVerExcelList(storeVerDelVO);
    }

    /** 매장별 포스 버전 삭제 */
    @Override
    public int deleteStoreVer(StoreVerDelVO[] storeVerDelVOs, SessionInfoVO sessionInfoVO) {

        int returnResult = 0;
        int result = 0;

        for (StoreVerDelVO storeVerDelVO : storeVerDelVOs) {
            result = storeVerDelMapper.deleteStoreVer(storeVerDelVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

            returnResult += result;
        }

        return returnResult;
    }
}
