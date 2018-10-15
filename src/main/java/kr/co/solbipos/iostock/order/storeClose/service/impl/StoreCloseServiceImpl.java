package kr.co.solbipos.iostock.order.storeClose.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.order.storeClose.service.StoreCloseService;
import kr.co.solbipos.iostock.order.storeClose.service.StoreCloseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

@Service("StoreCloseService")
public class StoreCloseServiceImpl implements StoreCloseService {
    @Autowired StoreCloseMapper storeCloseMapper;

    @Autowired MessageService messageService;

    /** 매장요청마감 마감월 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getStoreCloseList(StoreCloseVO storeCloseVO) {
        return storeCloseMapper.getStoreCloseList(storeCloseVO);
    }

    /** 매장요청마감 마감월 상세 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getStoreCloseDtlList(StoreCloseVO storeCloseVO) {
        return storeCloseMapper.getStoreCloseDtlList(storeCloseVO);
    }

    /** 매장요청마감 마감일 마감여부 저장 */
    @Override
    public int saveStoreClose(StoreCloseVO[] storeCloseVOs, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;
        int result = 0;
        String currentDt = currentDateTimeString();

        for (StoreCloseVO storeCloseVO : storeCloseVOs) {
            storeCloseVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            storeCloseVO.setOrderCloseId(sessionInfoVO.getUserId());
            storeCloseVO.setOrderCloseDt(currentDt);
            storeCloseVO.setRegId(sessionInfoVO.getUserId());
            storeCloseVO.setRegDt(currentDt);
            storeCloseVO.setModId(sessionInfoVO.getUserId());
            storeCloseVO.setModDt(currentDt);

            // 이전마감여부와 마감여부가 같지 않은 경우 수정
            if(storeCloseVO.getOrderCloseFg() != storeCloseVO.getPrevOrderCloseFg()) {
                // 마감여부가 체크 되어있는 경우 isnert, 아닌 경우 delete
                if (storeCloseVO.getOrderCloseFg()) {
                    // 등록
                    result = storeCloseMapper.insertStoreClose(storeCloseVO);
                    if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                }
                else {
                    // 삭제
                    result = storeCloseMapper.deleteStoreClose(storeCloseVO);
                    if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                }

                returnResult += result;
            }
        }

        return returnResult;
    }
}
