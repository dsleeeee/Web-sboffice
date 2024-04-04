package kr.co.solbipos.iostock.order.outstockData.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.code.CmmEnvService;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.DateUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.order.outstockData.service.OutstockDataService;
import kr.co.solbipos.iostock.order.outstockData.service.OutstockDataVO;
import kr.co.solbipos.store.hq.brand.service.HqEnvstVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

@Service("outstockDataService")
@Transactional
public class OutstockDataServiceImpl implements OutstockDataService {
    private final OutstockDataMapper outstockDataMapper;
    private final MessageService messageService;
    private final CmmEnvService cmmEnvService;

    public OutstockDataServiceImpl(OutstockDataMapper outstockDataMapper, MessageService messageService, CmmEnvService cmmEnvService) {
        this.outstockDataMapper = outstockDataMapper;
        this.messageService = messageService;
        this.cmmEnvService = cmmEnvService;
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

        // 본사 환경설정 1042(수발주옵션) 조회
        HqEnvstVO hqEnvstVO = new HqEnvstVO();
        hqEnvstVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        hqEnvstVO.setEnvstCd("1042");
        String envst1042 = CmmUtil.nvl(cmmEnvService.getHqEnvst(hqEnvstVO), "0");

        // 본사 환경설정 1242(거래처출고구분) 조회
        hqEnvstVO.setEnvstCd("1242");
        String envst1242 = CmmUtil.nvl(cmmEnvService.getHqEnvst(hqEnvstVO), "0");

        for (OutstockDataVO outstockDataVO : outstockDataVOs) {
            outstockDataVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            outstockDataVO.setRegId(sessionInfoVO.getUserId());
            outstockDataVO.setRegDt(currentDt);
            outstockDataVO.setModId(sessionInfoVO.getUserId());
            outstockDataVO.setModDt(currentDt);

            if ("2".equals(envst1042) && "2".equals(envst1242)) { // 1042(수발주옵션) - [2] 매장확정(출고자료생성) && 1242(거래처출고구분) - [2] 거래처별출고전표자동생성 인 경우, 거래처별 출고전표 자동생성

                // 직배송거래처 조회
                List<DefaultMap<String>> storeVendrList = outstockDataMapper.getStoreVendr(outstockDataVO);

                for (int i = 0; i < storeVendrList.size(); i++) {

                    slipNoIdx++;
                    String slipNo = yymm + StringUtil.lpad(String.valueOf(maxSlipNoIdx + slipNoIdx), 6, "0");
                    String vendrCd = StringUtil.getOrBlank(storeVendrList.get(i).get("vendrCd"));

                    // TB_PO_HQ_STORE_DISTRIBUTE 수정
                    outstockDataVO.setProcFg("20");
                    outstockDataVO.setUpdateProcFg("30");
                    outstockDataVO.setSlipNo(slipNo);
                    outstockDataVO.setVendrCd(vendrCd);
                    result = outstockDataMapper.updateDstbDataCreate(outstockDataVO);
                    //if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                    // TB_PO_HQ_STORE_OUTSTOCK_DTL 자료입력
                    result = outstockDataMapper.insertOutstockDtlDataCreate(outstockDataVO);
                    //if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                    // TB_PO_HQ_STORE_OUTSTOCK 자료입력
                    outstockDataVO.setSlipKind("0"); // 전표종류 TB_CM_NMCODE(NMCODE_GRP_CD=') 0:일반 1:물량오류 2:이동
                    result = outstockDataMapper.insertOutstockDataCreate(outstockDataVO);
                    //if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                }
            } else {

                slipNoIdx++;
                String slipNo = yymm + StringUtil.lpad(String.valueOf(maxSlipNoIdx + slipNoIdx), 6, "0");

                // TB_PO_HQ_STORE_DISTRIBUTE 수정
                outstockDataVO.setProcFg("20");
                outstockDataVO.setUpdateProcFg("30");
                outstockDataVO.setSlipNo(slipNo);
                result = outstockDataMapper.updateDstbDataCreate(outstockDataVO);
                //if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                // TB_PO_HQ_STORE_OUTSTOCK_DTL 자료입력
                result = outstockDataMapper.insertOutstockDtlDataCreate(outstockDataVO);
                //if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                // TB_PO_HQ_STORE_OUTSTOCK 자료입력
                outstockDataVO.setSlipKind("0"); // 전표종류 TB_CM_NMCODE(NMCODE_GRP_CD=') 0:일반 1:물량오류 2:이동
                result = outstockDataMapper.insertOutstockDataCreate(outstockDataVO);
                //if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }

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
