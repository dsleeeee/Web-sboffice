package kr.co.solbipos.iostock.orderReturn.rtnOutstockData.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.code.CmmEnvService;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.DateUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.orderReturn.rtnOutstockData.service.RtnOutstockDataService;
import kr.co.solbipos.iostock.orderReturn.rtnOutstockData.service.RtnOutstockDataVO;
import kr.co.solbipos.store.hq.brand.service.HqEnvstVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

@Service("rtnOutstockData")
@Transactional
public class RtnOutstockDataServiceImpl implements RtnOutstockDataService {
    private final RtnOutstockDataMapper rtnOutstockDataMapper;
    private final MessageService messageService;
    private final CmmEnvService cmmEnvService;

    public RtnOutstockDataServiceImpl(RtnOutstockDataMapper rtnOutstockDataMapper, MessageService messageService, CmmEnvService cmmEnvService) {
        this.rtnOutstockDataMapper = rtnOutstockDataMapper;
        this.messageService = messageService;
        this.cmmEnvService = cmmEnvService;
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

        // 본사 환경설정 1242(거래처출고구분) 조회
        HqEnvstVO hqEnvstVO = new HqEnvstVO();
        hqEnvstVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        hqEnvstVO.setEnvstCd("1242");
        String envst1242 = CmmUtil.nvl(cmmEnvService.getHqEnvst(hqEnvstVO), "0");

        for (RtnOutstockDataVO rtnOutstockDataVO : rtnOutstockDataVOs) {
            rtnOutstockDataVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            rtnOutstockDataVO.setRegId(sessionInfoVO.getUserId());
            rtnOutstockDataVO.setRegDt(currentDt);
            rtnOutstockDataVO.setModId(sessionInfoVO.getUserId());
            rtnOutstockDataVO.setModDt(currentDt);
            if(rtnOutstockDataVO.getOrderSlipNo() != null && !"".equals(rtnOutstockDataVO.getOrderSlipNo())) {
                String[] orderSlipNoList = rtnOutstockDataVO.getOrderSlipNo().split(",");
                rtnOutstockDataVO.setOrderSlipNoList(orderSlipNoList);
            }

            if ("2".equals(envst1242)) { // 1042(수발주옵션) - [2] 매장확정(출고자료생성) && 1242(거래처출고구분)(로직과 무관 - 제외) - [2] 거래처별출고전표자동생성 인 경우, 거래처별 출고전표 자동생성
                // 직배송거래처 조회
                List<DefaultMap<String>> storeVendrList = rtnOutstockDataMapper.getStoreVendr(rtnOutstockDataVO);

                for (int i = 0; i < storeVendrList.size(); i++) {
                    slipNoIdx++;
                    String slipNo = yymm + StringUtil.lpad(String.valueOf(maxSlipNoIdx + slipNoIdx), 6, "0");
                    String vendrCd   = StringUtil.getOrBlank(storeVendrList.get(i).get("vendrCd"));

                    // TB_PO_HQ_STORE_DISTRIBUTE 수정
                    rtnOutstockDataVO.setProcFg("20");
                    rtnOutstockDataVO.setUpdateProcFg("30");
                    rtnOutstockDataVO.setSlipNo(slipNo);
                    rtnOutstockDataVO.setVendrCd(vendrCd);
                    result = rtnOutstockDataMapper.updateDstbDataCreate(rtnOutstockDataVO);
                    //if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                    // TB_PO_HQ_STORE_OUTSTOCK_DTL 자료입력
                    result = rtnOutstockDataMapper.insertOutstockDtlDataCreate(rtnOutstockDataVO);
                    //if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                    // TB_PO_HQ_STORE_OUTSTOCK 자료입력
                    //rtnOutstockDataVO.setDlvrCd(dlvrCd);
                    rtnOutstockDataVO.setSlipKind("0"); // 전표종류 TB_CM_NMCODE(NMCODE_GRP_CD=') 0:일반 1:물량오류 2:이동
                    result = rtnOutstockDataMapper.insertRtnOutstockDataCreate(rtnOutstockDataVO);
                    //if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                    result = rtnOutstockDataMapper.insertRtnStoreOutStockProd(rtnOutstockDataVO);
                    //if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                }
            }else {

                // 직배송거래처 및 배송기사 조회
                //List<DefaultMap<String>> storeVendrDlvrList = rtnOutstockDataMapper.getStoreVendrDlvr(rtnOutstockDataVO);

                //for(int i=0; i < storeVendrDlvrList.size(); i++) {
                slipNoIdx++;
                String slipNo = yymm + StringUtil.lpad(String.valueOf(maxSlipNoIdx + slipNoIdx), 6, "0");
                //String vendrCd   = StringUtil.getOrBlank(storeVendrDlvrList.get(i).get("vendrCd"));
                //String dlvrCd    = StringUtil.getOrBlank(storeVendrDlvrList.get(i).get("dlvrCd"));

                // TB_PO_HQ_STORE_DISTRIBUTE 수정
                rtnOutstockDataVO.setProcFg("20");
                rtnOutstockDataVO.setUpdateProcFg("30");
                rtnOutstockDataVO.setSlipNo(slipNo);
                //rtnOutstockDataVO.setVendrCd(vendrCd);
                result = rtnOutstockDataMapper.updateDstbDataCreate(rtnOutstockDataVO);
                //if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                // TB_PO_HQ_STORE_OUTSTOCK_DTL 자료입력
                result = rtnOutstockDataMapper.insertOutstockDtlDataCreate(rtnOutstockDataVO);
                //if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                // TB_PO_HQ_STORE_OUTSTOCK 자료입력
                //rtnOutstockDataVO.setDlvrCd(dlvrCd);
                rtnOutstockDataVO.setSlipKind("0"); // 전표종류 TB_CM_NMCODE(NMCODE_GRP_CD=') 0:일반 1:물량오류 2:이동
                result = rtnOutstockDataMapper.insertRtnOutstockDataCreate(rtnOutstockDataVO);
                //if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                result = rtnOutstockDataMapper.insertRtnStoreOutStockProd(rtnOutstockDataVO);
                //if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                //}
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
