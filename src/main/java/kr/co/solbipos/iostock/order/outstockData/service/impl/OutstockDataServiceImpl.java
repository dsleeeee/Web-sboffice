package kr.co.solbipos.iostock.order.outstockData.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.DateUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.order.outstockData.service.OutstockDataService;
import kr.co.solbipos.iostock.order.outstockData.service.OutstockDataVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

@Service("outstockDataService")
@Transactional
public class OutstockDataServiceImpl implements OutstockDataService {
    private final OutstockDataMapper outstockDataMapper;
    private final MessageService messageService;

    public OutstockDataServiceImpl(OutstockDataMapper outstockDataMapper, MessageService messageService) {
        this.outstockDataMapper = outstockDataMapper;
        this.messageService = messageService;
    }

    /** 출고자료생성 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getOutstockDataList(OutstockDataVO outstockDataVO) {
        return outstockDataMapper.getOutstockDataList(outstockDataVO);
    }

    /** 출고자료생성 - 출고자료생성 */
    @Override
    public int saveDataCreate(OutstockDataVO[] outstockDataVOs, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;
        int result = 0;
        String currentDt = currentDateTimeString();

        // 전표번호 조회
        String yymm = DateUtil.currentDateString().substring(2,6); // 새로운 전표번호 생성을 위한 년월(YYMM)
        OutstockDataVO maxSlipNoVO = new OutstockDataVO();
        maxSlipNoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        maxSlipNoVO.setYymm(yymm);
        String maxSlipNo = outstockDataMapper.getMaxSlipNo(maxSlipNoVO);
        Long maxSlipNoIdx = Long.valueOf(maxSlipNo.substring(4));
        int slipNoIdx = 0;

        for (OutstockDataVO outstockDataVO : outstockDataVOs) {
            outstockDataVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            outstockDataVO.setRegId(sessionInfoVO.getUserId());
            outstockDataVO.setRegDt(currentDt);
            outstockDataVO.setModId(sessionInfoVO.getUserId());
            outstockDataVO.setModDt(currentDt);

            // 직배송거래처 및 배송기사 조회
            //List<DefaultMap<String>> storeVendrDlvrList = outstockDataMapper.getStoreVendrDlvr(outstockDataVO);

            //for(int i=0; i < storeVendrDlvrList.size(); i++) {
                slipNoIdx++;
                String slipNo    = yymm + StringUtil.lpad(String.valueOf(maxSlipNoIdx+slipNoIdx), 6, "0");
                //String vendrCd   = StringUtil.getOrBlank(storeVendrDlvrList.get(i).get("vendrCd"));
                //String dlvrCd    = StringUtil.getOrBlank(storeVendrDlvrList.get(i).get("dlvrCd"));

                // TB_PO_HQ_STORE_DISTRIBUTE 수정
                outstockDataVO.setProcFg("20");
                outstockDataVO.setUpdateProcFg("30");
                outstockDataVO.setSlipNo(slipNo);
                //outstockDataVO.setVendrCd(vendrCd);
                result = outstockDataMapper.updateDstbDataCreate(outstockDataVO);
                //if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                // TB_PO_HQ_STORE_OUTSTOCK_DTL 자료입력
                result = outstockDataMapper.insertOutstockDtlDataCreate(outstockDataVO);
                //if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                // TB_PO_HQ_STORE_OUTSTOCK 자료입력
                //outstockDataVO.setDlvrCd(dlvrCd);
                outstockDataVO.setSlipKind("0"); // 전표종류 TB_CM_NMCODE(NMCODE_GRP_CD=') 0:일반 1:물량오류 2:이동
                result = outstockDataMapper.insertOutstockDataCreate(outstockDataVO);
                //if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            //}

            returnResult += result;
        }

        return returnResult;
    }

    /** 출고자료생성 상세 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getOutstockDataDtlList(OutstockDataVO outstockDataVO) {
        return outstockDataMapper.getOutstockDataDtlList(outstockDataVO);
    }


}
