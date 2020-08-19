package kr.co.solbipos.iostock.orderReturn.rtnOutstockData.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.DateUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.order.outstockData.service.OutstockDataVO;
import kr.co.solbipos.iostock.orderReturn.rtnOutstockData.service.RtnOutstockDataService;
import kr.co.solbipos.iostock.orderReturn.rtnOutstockData.service.RtnOutstockDataVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

@Service("rtnOutstockData")
@Transactional
public class RtnOutstockDataServiceImpl implements RtnOutstockDataService {
    private final RtnOutstockDataMapper rtnOutstockDataMapper;
    private final MessageService messageService;

    public RtnOutstockDataServiceImpl(RtnOutstockDataMapper rtnOutstockDataMapper, MessageService messageService) {
        this.rtnOutstockDataMapper = rtnOutstockDataMapper;
        this.messageService = messageService;
    }

    /** 반품자료생성 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getRtnOutstockDataList(RtnOutstockDataVO rtnOutstockDataVO) {
        return rtnOutstockDataMapper.getRtnOutstockDataList(rtnOutstockDataVO);
    }

    /** 반품자료생성 - 반품자료생성 */
    @Override
    public int saveDataCreate(RtnOutstockDataVO[] rtnOutstockDataVOs, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;
        int result = 0;
        String currentDt = currentDateTimeString();

        // 전표번호 조회
        String yymm = DateUtil.currentDateString().substring(2,6); // 새로운 전표번호 생성을 위한 년월(YYMM)
        RtnOutstockDataVO maxSlipNoVO = new RtnOutstockDataVO();
        maxSlipNoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        maxSlipNoVO.setYymm(yymm);
        String maxSlipNo = rtnOutstockDataMapper.getMaxSlipNo(maxSlipNoVO);
        Long maxSlipNoIdx = Long.valueOf(maxSlipNo.substring(4));
        int slipNoIdx = 0;

        for (RtnOutstockDataVO rtnOutstockDataVO : rtnOutstockDataVOs) {
            rtnOutstockDataVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            rtnOutstockDataVO.setRegId(sessionInfoVO.getUserId());
            rtnOutstockDataVO.setRegDt(currentDt);
            rtnOutstockDataVO.setModId(sessionInfoVO.getUserId());
            rtnOutstockDataVO.setModDt(currentDt);

            // 직배송거래처 및 배송기사 조회
            List<DefaultMap<String>> storeVendrDlvrList = rtnOutstockDataMapper.getStoreVendrDlvr(rtnOutstockDataVO);

            for(int i=0; i < storeVendrDlvrList.size(); i++) {
                slipNoIdx++;
                String slipNo    = yymm + StringUtil.lpad(String.valueOf(maxSlipNoIdx+slipNoIdx), 6, "0");
                String vendrCd   = StringUtil.getOrBlank(storeVendrDlvrList.get(i).get("vendrCd"));
                String dlvrCd    = StringUtil.getOrBlank(storeVendrDlvrList.get(i).get("dlvrCd"));

                // TB_PO_HQ_STORE_DISTRIBUTE 수정
                rtnOutstockDataVO.setProcFg("20");
                rtnOutstockDataVO.setUpdateProcFg("30");
                rtnOutstockDataVO.setSlipNo(slipNo);
                rtnOutstockDataVO.setVendrCd(vendrCd);
                result = rtnOutstockDataMapper.updateDstbDataCreate(rtnOutstockDataVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                // TB_PO_HQ_STORE_OUTSTOCK_DTL 자료입력
                result = rtnOutstockDataMapper.insertOutstockDtlDataCreate(rtnOutstockDataVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                // TB_PO_HQ_STORE_OUTSTOCK 자료입력
                rtnOutstockDataVO.setDlvrCd(dlvrCd);
                rtnOutstockDataVO.setSlipKind("0"); // 전표종류 TB_CM_NMCODE(NMCODE_GRP_CD=') 0:일반 1:물량오류 2:이동
                result = rtnOutstockDataMapper.insertRtnOutstockDataCreate(rtnOutstockDataVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                
                result = rtnOutstockDataMapper.insertRtnStoreOutStockProd(rtnOutstockDataVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }

            returnResult += result;
        }       
        return returnResult;
    }

    /** 반품자료생성 상세 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getRtnOutstockDataDtlList(RtnOutstockDataVO rtnOutstockDataVO) {
        return rtnOutstockDataMapper.getRtnOutstockDataDtlList(rtnOutstockDataVO);
    }
}
