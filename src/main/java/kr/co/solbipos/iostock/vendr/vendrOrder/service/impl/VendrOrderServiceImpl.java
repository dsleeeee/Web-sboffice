package kr.co.solbipos.iostock.vendr.vendrOrder.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.DateUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.iostock.vendr.vendrOrder.service.VendrOrderService;
import kr.co.solbipos.iostock.vendr.vendrOrder.service.VendrOrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

@Service("vendrOrderService")
public class VendrOrderServiceImpl implements VendrOrderService {
    private final VendrOrderMapper vendrOrderMapper;
    private final MessageService messageService;

    @Autowired
    public VendrOrderServiceImpl(VendrOrderMapper vendrOrderMapper, MessageService messageService) {
        this.vendrOrderMapper = vendrOrderMapper;
        this.messageService = messageService;
    }

    /** 거래처 발주등록 - 거래처 발주등록 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getVendrOrderList(VendrOrderVO vendrOrderVO, SessionInfoVO sessionInfoVO) {
        List<DefaultMap<String>> result = new ArrayList<DefaultMap<String>>();
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
            vendrOrderVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            result = vendrOrderMapper.getHqVendrOrderList(vendrOrderVO);
        }
        else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
            vendrOrderVO.setStoreCd(sessionInfoVO.getStoreCd());
//            result = vendrOrderMapper.getStVendrOrderList(vendrOrderVO);
        }
        return result;
    }


    /** 거래처 발주등록 - 발주정보 상세 조회 */
    @Override
    public DefaultMap<String> getSlipInfo(VendrOrderVO vendrOrderVO, SessionInfoVO sessionInfoVO) {
        DefaultMap<String> result = new DefaultMap<String>();
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
            vendrOrderVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            result = vendrOrderMapper.getHqSlipInfo(vendrOrderVO);
        }
        else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
            vendrOrderVO.setStoreCd(sessionInfoVO.getStoreCd());
            //            result = vendrOrderMapper.getStSlipInfo(vendrOrderVO);
        }
        return result;
    }


    /** 거래처 발주등록 - 발주정보 저장 */
    @Override
    public DefaultMap<String> saveVendrOrderDtl(VendrOrderVO vendrOrderVO, SessionInfoVO sessionInfoVO) {
        String slipNo = "";
        int result = 0;

        String currentDt = currentDateTimeString();
        vendrOrderVO.setRegId(sessionInfoVO.getUserId());
        vendrOrderVO.setRegDt(currentDt);
        vendrOrderVO.setModId(sessionInfoVO.getUserId());
        vendrOrderVO.setModDt(currentDt);

        // 신규등록
        if(StringUtil.getOrBlank(vendrOrderVO.getSlipNo()).equals("")) {
            // 전표번호 조회
            String yymm = DateUtil.currentDateString().substring(2, 6); // 새로운 전표번호 생성을 위한 년월(YYMM)
            VendrOrderVO maxSlipNoVO = new VendrOrderVO();
            maxSlipNoVO.setYymm(yymm);

            if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
                maxSlipNoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
                slipNo = vendrOrderMapper.getHqNewSlipNo(maxSlipNoVO);

                vendrOrderVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
                vendrOrderVO.setSlipNo(slipNo);
                vendrOrderVO.setProcFg("0");
                result = vendrOrderMapper.insertHqVendrOrderHd(vendrOrderVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            } else if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
//                maxSlipNoVO.setStoreCd(sessionInfoVO.getStoreCd());
//                slipNo = vendrOrderMapper.getStMaxSlipNo(maxSlipNoVO);

//                vendrOrderVO.setStoreCd(sessionInfoVO.getStoreCd());
//                vendrOrderVO.setSlipNo(slipNo);
//                result = vendrOrderMapper.insertHqVendrOrderHd(vendrOrderVO);
//                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }
        }
        // 신규등록이 아닌 경우
        else {
            slipNo = vendrOrderVO.getSlipNo();
            if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
                vendrOrderVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
                result = vendrOrderMapper.updateHqVendrOrderHd(vendrOrderVO);
                if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }
            else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
//                vendrOrderVO.setStoreCd(sessionInfoVO.getStoreCd());
//                result = vendrOrderMapper.updateStVendrOrderHd(vendrOrderVO);
//                if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }
        }

        DefaultMap<String> resultMap = new DefaultMap<String>();
        resultMap.put("slipNo", slipNo);
        resultMap.put("slipFg", String.valueOf(vendrOrderVO.getSlipFg()));
        return resultMap;
    }


    /** 거래처 발주등록 - 발주정보 삭제 */
    @Override
    public int deleteVendrOrderDtl(VendrOrderVO vendrOrderVO, SessionInfoVO sessionInfoVO) {
        int result = 0;

        String currentDt = currentDateTimeString();
        vendrOrderVO.setRegId(sessionInfoVO.getUserId());
        vendrOrderVO.setRegDt(currentDt);
        vendrOrderVO.setModId(sessionInfoVO.getUserId());
        vendrOrderVO.setModDt(currentDt);

        String dtlProdExist = "N";
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
            vendrOrderVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            dtlProdExist = vendrOrderMapper.getHqDtlProdExist(vendrOrderVO);
            if(dtlProdExist.equals("Y")) {
                throw new JsonException(Status.FAIL, messageService.get("vendrOrder.dtl.prodExist"));
            }

            result = vendrOrderMapper.deleteHqVendrOrderHd(vendrOrderVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
        else if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
//            vendrOrderVO.setStoreCd(sessionInfoVO.getStoreCd());
//            dtlProdExist = vendrOrderMapper.getStDtlProdExist(vendrOrderVO);
//            if(dtlProdExist.equals("Y")) {
//                throw new JsonException(Status.FAIL, messageService.get("vendrOrder.dtl.prodExist"));
//            }

            //            result = vendrOrderMapper.deleteStVendrOrderHd(vendrOrderVO);
//            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }

        return result;
    }


    /** 거래처 발주등록 - 발주정보 진행상태 변경 */
    @Override
    public int saveProcFg(VendrOrderVO vendrOrderVO, SessionInfoVO sessionInfoVO) {
        int result = 0;

        String currentDt = currentDateTimeString();
        vendrOrderVO.setRegId(sessionInfoVO.getUserId());
        vendrOrderVO.setRegDt(currentDt);
        vendrOrderVO.setModId(sessionInfoVO.getUserId());
        vendrOrderVO.setModDt(currentDt);

        String vendrInstockExist = "N";
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
            vendrOrderVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            vendrInstockExist = vendrOrderMapper.getHqVendrInstockExist(vendrOrderVO);
            if(vendrInstockExist.equals("Y")) {
                String errMsg = (vendrOrderVO.getProcFg().equals("5") ? messageService.get("vendrOrder.dtl.noConfmExist") : messageService.get("vendrOrder.dtl.regExist"));
                throw new JsonException(Status.FAIL, errMsg);
            }

            result = vendrOrderMapper.updateHqProcFg(vendrOrderVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
        else if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
            // vendrOrderVO.setStoreCd(sessionInfoVO.getStoreCd());
//            vendrInstockExist = vendrOrderMapper.getStVendrInstockExist(vendrOrderVO);
//            if(vendrInstockExist.equals("Y")) {
//            String errMsg = (vendrOrderVO.getProcFg().equals("5") ? messageService.get("vendrOrder.dtl.noConfmExist") : messageService.get("vendrOrder.dtl.regExist"));
//                throw new JsonException(Status.FAIL, messageService.get("vendrOrder.dtl.prodExist"));
//            }
//            result = vendrOrderMapper.updateStProcFg(vendrOrderVO);
//            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }

        return result;
    }


    /** 거래처 발주등록 - 발주상품 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getVendrOrderProdList(VendrOrderVO vendrOrderVO, SessionInfoVO sessionInfoVO) {
        List<DefaultMap<String>> result = new ArrayList<DefaultMap<String>>();
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
            vendrOrderVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            result = vendrOrderMapper.getHqVendrOrderProdList(vendrOrderVO);
        }
        else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
            vendrOrderVO.setStoreCd(sessionInfoVO.getStoreCd());
            //            result = vendrOrderMapper.getStVendrOrderProdList(vendrOrderVO);
        }
        return result;
    }


    /** 거래처 발주등록 - 진행구분 조회 */
    @Override
    public DefaultMap<String> getProcFgCheck(VendrOrderVO vendrOrderVO, SessionInfoVO sessionInfoVO) {
        DefaultMap<String> result = new DefaultMap<String>();
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
            vendrOrderVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            result = vendrOrderMapper.getHqProcFgCheck(vendrOrderVO);
        }
        else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
            vendrOrderVO.setStoreCd(sessionInfoVO.getStoreCd());
            //            result = vendrOrderMapper.getStProcFgCheck(vendrOrderVO);
        }
        return result;
    }


    /** 거래처 발주등록 - 발주상품 추가/변경 등록 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getVendrOrderProdRegList(VendrOrderVO vendrOrderVO, SessionInfoVO sessionInfoVO) {
        List<DefaultMap<String>> result = new ArrayList<DefaultMap<String>>();
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
            vendrOrderVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            result = vendrOrderMapper.getHqVendrOrderProdRegList(vendrOrderVO);
        }
        else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
            vendrOrderVO.setStoreCd(sessionInfoVO.getStoreCd());
            //            result = vendrOrderMapper.getStVendrOrderProdRegList(vendrOrderVO);
        }
        return result;
    }


    /** 거래처 발주등록 - 발주상품 추가/변경 등록 리스트 저장 */
    @Override
    public int saveVendrOrderProdReg(VendrOrderVO[] vendrOrderVOs, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;
        int result = 0;
        String currentDt = currentDateTimeString();
        VendrOrderVO vendrOrderHdVO = new VendrOrderVO();

        int i = 0;
        for (VendrOrderVO vendrOrderVO : vendrOrderVOs) {
            // HD 저장을 위한 파라미터 세팅
            if (i == 0) {
                vendrOrderHdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
                vendrOrderHdVO.setStoreCd(sessionInfoVO.getStoreCd());
                vendrOrderHdVO.setSlipNo(vendrOrderVO.getSlipNo());
                vendrOrderHdVO.setRegId(sessionInfoVO.getUserId());
                vendrOrderHdVO.setRegDt(currentDt);
                vendrOrderHdVO.setModId(sessionInfoVO.getUserId());
                vendrOrderHdVO.setModDt(currentDt);
            }

            String insFg = "";
            // 기주문수량이 있는 경우 수정
            if(vendrOrderVO.getPrevOrderTotQty() != null) {
                insFg = "U";
                // 기주문수량이 있으면서 주문수량이 0 이나 null 인 경우 삭제
                if(vendrOrderVO.getOrderTotQty() == 0 || vendrOrderVO.getOrderTotQty() == null) {
                    insFg = "D";
                }
            }
            else {
                insFg = "I";
            }

            if(!insFg.equals("D")) {
                int slipFg       = vendrOrderVO.getSlipFg();
                int poUnitQty    = vendrOrderVO.getPoUnitQty();
                int prevUnitQty  = (vendrOrderVO.getPrevOrderUnitQty() == null ? 0 : vendrOrderVO.getPrevOrderUnitQty());
                int prevEtcQty   = (vendrOrderVO.getPrevOrderEtcQty()  == null ? 0 : vendrOrderVO.getPrevOrderEtcQty());
                int unitQty      = (vendrOrderVO.getOrderUnitQty()     == null ? 0 : vendrOrderVO.getOrderUnitQty());
                int etcQty       = (vendrOrderVO.getOrderEtcQty()      == null ? 0 : vendrOrderVO.getOrderEtcQty());
                int orderUnitQty = ((prevUnitQty + unitQty) + Integer.valueOf((prevEtcQty + etcQty) / poUnitQty)) * slipFg;
                int orderEtcQty  = Integer.valueOf((prevEtcQty + etcQty) % poUnitQty) * slipFg;
                int orderTotQty  = (vendrOrderVO.getOrderTotQty()   == null ? 0 : vendrOrderVO.getOrderTotQty()) * slipFg;
                Long orderAmt    = (vendrOrderVO.getOrderAmt()      == null ? 0 : vendrOrderVO.getOrderAmt())    * slipFg;
                Long orderVat    = (vendrOrderVO.getOrderVat()      == null ? 0 : vendrOrderVO.getOrderVat())    * slipFg;
                Long orderTot    = (vendrOrderVO.getOrderTot()      == null ? 0 : vendrOrderVO.getOrderTot())    * slipFg;

                vendrOrderVO.setOrderUnitQty(orderUnitQty);
                vendrOrderVO.setOrderEtcQty(orderEtcQty);
                vendrOrderVO.setOrderTotQty(orderTotQty);
                vendrOrderVO.setOrderAmt(orderAmt);
                vendrOrderVO.setOrderVat(orderVat);
                vendrOrderVO.setOrderTot(orderTot);
                vendrOrderVO.setRegId(sessionInfoVO.getUserId());
                vendrOrderVO.setRegDt(currentDt);
                vendrOrderVO.setModId(sessionInfoVO.getUserId());
                vendrOrderVO.setModDt(currentDt);
            }
            vendrOrderVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            vendrOrderVO.setStoreCd(sessionInfoVO.getStoreCd());

            // 추가
            if(insFg.equals("I")) {
                if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
                    result = vendrOrderMapper.insertHqVendrOrderDtl(vendrOrderVO);
                    if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                }
                else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
//                    result = vendrOrderMapper.insertStVendrOrderDtl(vendrOrderVO);
//                    if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                }
            }
            // 수정
            else if(insFg.equals("U")) {
                if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
                    result = vendrOrderMapper.updateHqVendrOrderDtl(vendrOrderVO);
                    if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                }
                else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
//                    result = vendrOrderMapper.updateStVendrOrderDtl(vendrOrderVO);
//                    if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                }
            }
            // 삭제
            else if(insFg.equals("D")) {
                if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
                    result = vendrOrderMapper.deleteHqVendrOrderDtl(vendrOrderVO);
                    if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                }
                else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
//                    result = vendrOrderMapper.deleteStVendrOrderDtl(vendrOrderVO);
//                    if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                }
            }

            returnResult += result;
            i++;
        }

        // 발주정보 DTL의 집계정보 HD에 수정
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
            result = vendrOrderMapper.updateHqVendrOrderDtlSumHd(vendrOrderHdVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
        else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
//            result = vendrOrderMapper.updateStVendrOrderDtlSumHd(vendrOrderVO);
//            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }

        return returnResult;
    }








    /** 거래처 발주등록 - 거래처 선택모듈 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getVendrList(VendrOrderVO vendrOrderVO, SessionInfoVO sessionInfoVO) {
        List<DefaultMap<String>> result = new ArrayList<DefaultMap<String>>();
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
            vendrOrderVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            result = vendrOrderMapper.getHqVendrList(vendrOrderVO);
        }
        else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
            vendrOrderVO.setStoreCd(sessionInfoVO.getStoreCd());
            //            result = vendrOrderMapper.getStVendrList(vendrOrderVO);
        }
        return result;
    }
}
