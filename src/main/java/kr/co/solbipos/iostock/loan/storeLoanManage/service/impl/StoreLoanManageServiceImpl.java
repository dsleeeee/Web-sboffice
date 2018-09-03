package kr.co.solbipos.iostock.loan.storeLoanManage.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.loan.storeLoanManage.service.StoreLoanManageService;
import kr.co.solbipos.iostock.loan.storeLoanManage.service.StoreLoanManageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

@Service("StoreLoanManageService")
public class StoreLoanManageServiceImpl implements StoreLoanManageService {

    @Autowired
    StoreLoanManageMapper storeLoanManageMapper;

    @Autowired
    MessageService messageService;

    /** 매장여신관리 목록 조회 */
    @Override
    public List<DefaultMap<String>> getStoreLoanManageList(StoreLoanManageVO storeLoanManageVO) {
        return storeLoanManageMapper.getStoreLoanManageList(storeLoanManageVO);
    }

    /** 매장여신 저장 */
    @Override
    public int saveLoanManageList(StoreLoanManageVO[] storeLoanManageVOs, SessionInfoVO sessionInfoVO) {

        int returnResult = 0;
        int result = 0;
        String currentDt = currentDateTimeString();

        for ( StoreLoanManageVO storeLoanManageVO : storeLoanManageVOs ) {

            storeLoanManageVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            storeLoanManageVO.setRegDt(currentDt);
            storeLoanManageVO.setRegId(sessionInfoVO.getUserId());
            storeLoanManageVO.setModDt(currentDt);
            storeLoanManageVO.setModId(sessionInfoVO.getUserId());

            // 수정
            if ( storeLoanManageVO.getStatus() == GridDataFg.UPDATE ) {
                result = storeLoanManageMapper.updateStoreLoanManage(storeLoanManageVO);
                if(result == 0) {
                    // 추가
                    storeLoanManageVO.setUseLoanAmt(0);
                    storeLoanManageVO.setCurrLoanAmt(0);
                    result = storeLoanManageMapper.insertStoreLoanManage(storeLoanManageVO);
                }
            }
            returnResult += result;
        }

        if ( returnResult == storeLoanManageVOs.length) {
            return returnResult;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }

    }
}
