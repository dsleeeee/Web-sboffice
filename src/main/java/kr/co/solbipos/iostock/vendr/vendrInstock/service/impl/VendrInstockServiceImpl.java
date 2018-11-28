package kr.co.solbipos.iostock.vendr.vendrInstock.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.DateUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.iostock.vendr.vendrInstock.service.VendrInstockService;
import kr.co.solbipos.iostock.vendr.vendrInstock.service.VendrInstockVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

@Service("VendrInstockService")
public class VendrInstockServiceImpl implements VendrInstockService {
    private final VendrInstockMapper vendrInstockMapper;
    private final MessageService messageService;

    @Autowired
    public VendrInstockServiceImpl(VendrInstockMapper vendrInstockMapper, MessageService messageService) {
        this.vendrInstockMapper = vendrInstockMapper;
        this.messageService = messageService;
    }


    /** 거래처 입고/반출등록 - 거래처 입고/반출 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getVendrInstockList(VendrInstockVO vendrInstockVO, SessionInfoVO sessionInfoVO) {
        List<DefaultMap<String>> result = new ArrayList<DefaultMap<String>>();
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
            vendrInstockVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            result = vendrInstockMapper.getHqVendrInstockList(vendrInstockVO);
        }
        else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
            vendrInstockVO.setStoreCd(sessionInfoVO.getStoreCd());
            //            result = vendrInstockMapper.getStVendrInstockList(vendrInstockVO);
        }
        return result;
    }


    /** 거래처 입고/반출등록 - 거래처 입고/반출 등록시 발주번호 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getVendrInstockOrderSlipList(VendrInstockVO vendrInstockVO, SessionInfoVO sessionInfoVO) {
        List<DefaultMap<String>> result = new ArrayList<DefaultMap<String>>();
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
            vendrInstockVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            result = vendrInstockMapper.getHqVendrInstockOrderSlipList(vendrInstockVO);
        }
        else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
            vendrInstockVO.setStoreCd(sessionInfoVO.getStoreCd());
            //            result = vendrInstockMapper.getStVendrInstockOrderSlipList(vendrInstockVO);
        }
        return result;
    }


    /** 거래처 입고/반출등록 - 입고/반출정보 상세 조회 */
    @Override
    public DefaultMap<String> getSlipInfo(VendrInstockVO vendrInstockVO, SessionInfoVO sessionInfoVO) {
        DefaultMap<String> result = new DefaultMap<String>();
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
            vendrInstockVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            result = vendrInstockMapper.getHqSlipInfo(vendrInstockVO);
        }
        else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
            vendrInstockVO.setStoreCd(sessionInfoVO.getStoreCd());
            //            result = vendrInstockMapper.getStSlipInfo(vendrInstockVO);
        }
        return result;
    }


    /** 거래처 입고/반출등록 - 입고/반출정보 저장 */
    @Override
    public DefaultMap<String> saveVendrInstockDtl(VendrInstockVO vendrInstockVO, SessionInfoVO sessionInfoVO) {
        String slipNo = "";
        int result = 0;

        String currentDt = currentDateTimeString();
        vendrInstockVO.setRegId(sessionInfoVO.getUserId());
        vendrInstockVO.setRegDt(currentDt);
        vendrInstockVO.setModId(sessionInfoVO.getUserId());
        vendrInstockVO.setModDt(currentDt);

        // 신규등록
        if(StringUtil.getOrBlank(vendrInstockVO.getSlipNo()).equals("")) {
            // 전표번호 조회
            String yymm = DateUtil.currentDateString().substring(2, 6); // 새로운 전표번호 생성을 위한 년월(YYMM)
            VendrInstockVO maxSlipNoVO = new VendrInstockVO();
            maxSlipNoVO.setYymm(yymm);

            if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
                maxSlipNoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
                slipNo = vendrInstockMapper.getHqNewSlipNo(maxSlipNoVO);

                vendrInstockVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
                vendrInstockVO.setSlipNo(slipNo);
                vendrInstockVO.setProcFg("0");
                if(StringUtil.getOrBlank(vendrInstockVO.getInstockType()).equals("N")) {
                    vendrInstockVO.setOrderSlipNo("");
                }
                result = vendrInstockMapper.insertHqVendrInstockHd(vendrInstockVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            } else if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
//                maxSlipNoVO.setStoreCd(sessionInfoVO.getStoreCd());
//                slipNo = vendrInstockMapper.getStMaxSlipNo(maxSlipNoVO);

//                vendrInstockVO.setStoreCd(sessionInfoVO.getStoreCd());
//                vendrInstockVO.setSlipNo(slipNo);
//                result = vendrInstockMapper.insertHqVendrInstockHd(vendrInstockVO);
//                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }
        }
        // 신규등록이 아닌 경우
        else {
            slipNo = vendrInstockVO.getSlipNo();
            if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
                vendrInstockVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
                result = vendrInstockMapper.updateHqVendrInstockHd(vendrInstockVO);
                if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }
            else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
                //                vendrInstockVO.setStoreCd(sessionInfoVO.getStoreCd());
                //                result = vendrInstockMapper.updateStVendrInstockHd(vendrInstockVO);
                //                if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }
        }

        DefaultMap<String> resultMap = new DefaultMap<String>();
        resultMap.put("slipNo", slipNo);
        resultMap.put("slipFg", String.valueOf(vendrInstockVO.getSlipFg()));
        return resultMap;
    }


    /** 거래처 입고/반출등록 - 입고/반출정보 삭제 */
    @Override
    public int deleteVendrInstockDtl(VendrInstockVO vendrInstockVO, SessionInfoVO sessionInfoVO) {
        int result = 0;

        String currentDt = currentDateTimeString();
        vendrInstockVO.setRegId(sessionInfoVO.getUserId());
        vendrInstockVO.setRegDt(currentDt);
        vendrInstockVO.setModId(sessionInfoVO.getUserId());
        vendrInstockVO.setModDt(currentDt);

        String dtlProdExist = "N";
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
            vendrInstockVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            dtlProdExist = vendrInstockMapper.getHqDtlProdExist(vendrInstockVO);
            if(dtlProdExist.equals("Y")) {
                throw new JsonException(Status.FAIL, messageService.get("vendrInstock.dtl.prodExist"));
            }

            result = vendrInstockMapper.deleteHqVendrInstockHd(vendrInstockVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
        else if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
            //            vendrInstockVO.setStoreCd(sessionInfoVO.getStoreCd());
            //            dtlProdExist = vendrInstockMapper.getStDtlProdExist(vendrInstockVO);
            //            if(dtlProdExist.equals("Y")) {
            //                throw new JsonException(Status.FAIL, messageService.get("vendrInstock.dtl.prodExist"));
            //            }

            //            result = vendrInstockMapper.deleteStVendrInstockHd(vendrInstockVO);
            //            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }

        return result;
    }


    /** 거래처 입고/반출등록 - 입고/반출정보 진행상태 변경 */
    @Override
    public int saveProcFg(VendrInstockVO vendrInstockVO, SessionInfoVO sessionInfoVO) {
        int result = 0;

        String currentDt = currentDateTimeString();
        vendrInstockVO.setRegId(sessionInfoVO.getUserId());
        vendrInstockVO.setRegDt(currentDt);
        vendrInstockVO.setModId(sessionInfoVO.getUserId());
        vendrInstockVO.setModDt(currentDt);

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
            vendrInstockVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

            result = vendrInstockMapper.updateHqProcFg(vendrInstockVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

            // 발주번호가 있는 경우
            if(!StringUtil.getOrBlank(vendrInstockVO.getOrderSlipNo()).equals("")) {
                // 발주 DT 내역의 입고관련 정보 초기화
                result = vendrInstockMapper.updateHqDefaultVendrOrderDtl(vendrInstockVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                // 발주 DT 내역의 입고관련 정보 갱신
                result = vendrInstockMapper.updateHqVendrInstockToOrderDtl(vendrInstockVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                // 입고DT에 있으면서 발주 DT 내역에 없는 내역은 신규로 생성
                result = vendrInstockMapper.insertHqVendrInstockToOrderDtl(vendrInstockVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                // 발주 DT 내역의 집계정보 HD에 수정
                result = vendrInstockMapper.updateHqVendrOrderDtlSumHd(vendrInstockVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                // 발주 테이블의 진행구분 입고중으로 수정
                vendrInstockVO.setProcFg("4");
                result = vendrInstockMapper.updateHqVendrOrderProcFg(vendrInstockVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

            }

        }
        else if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
            // vendrInstockVO.setStoreCd(sessionInfoVO.getStoreCd());
            //            vendrInstockExist = vendrInstockMapper.getStVendrInstockExist(vendrInstockVO);
            //            if(vendrInstockExist.equals("Y")) {
            //            String errMsg = (vendrInstockVO.getProcFg().equals("5") ? messageService.get("vendrInstock.dtl.noConfmExist") : messageService.get("vendrInstock.dtl.regExist"));
            //                throw new JsonException(Status.FAIL, messageService.get("vendrInstock.dtl.prodExist"));
            //            }
            //            result = vendrInstockMapper.updateStProcFg(vendrInstockVO);
            //            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }

        return result;
    }


    /** 거래처 입고/반출등록 - 입고/반출상품 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getVendrInstockProdList(VendrInstockVO vendrInstockVO, SessionInfoVO sessionInfoVO) {
        List<DefaultMap<String>> result = new ArrayList<DefaultMap<String>>();
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
            vendrInstockVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            result = vendrInstockMapper.getHqVendrInstockProdList(vendrInstockVO);
        }
        else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
            vendrInstockVO.setStoreCd(sessionInfoVO.getStoreCd());
            //            result = vendrInstockMapper.getStVendrInstockProdList(vendrInstockVO);
        }
        return result;
    }


    /** 거래처 입고/반출등록 - 진행구분 조회 */
    @Override
    public DefaultMap<String> getProcFgCheck(VendrInstockVO vendrInstockVO, SessionInfoVO sessionInfoVO) {
        DefaultMap<String> result = new DefaultMap<String>();
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
            vendrInstockVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            result = vendrInstockMapper.getHqProcFgCheck(vendrInstockVO);
        }
        else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
            vendrInstockVO.setStoreCd(sessionInfoVO.getStoreCd());
            //            result = vendrInstockMapper.getStProcFgCheck(vendrInstockVO);
        }
        return result;
    }


    /** 거래처 입고/반출등록 - 입고/반출상품 등록 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getVendrInstockProdRegList(VendrInstockVO vendrInstockVO, SessionInfoVO sessionInfoVO) {
        List<DefaultMap<String>> result = new ArrayList<DefaultMap<String>>();
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
            vendrInstockVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            result = vendrInstockMapper.getHqVendrInstockProdRegList(vendrInstockVO);
        }
        else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
            vendrInstockVO.setStoreCd(sessionInfoVO.getStoreCd());
            //            result = vendrInstockMapper.getStVendrInstockProdRegList(vendrInstockVO);
        }
        return result;
    }


    /** 거래처 입고/반출등록 - 입고/반출상품 등록 리스트 저장 */
    @Override
    public int saveVendrInstockProdReg(VendrInstockVO[] vendrInstockVOs, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;
        int result = 0;
        String currentDt = currentDateTimeString();
        VendrInstockVO vendrInstockHdVO = new VendrInstockVO();

        int i = 0;
        for (VendrInstockVO vendrInstockVO : vendrInstockVOs) {
            // HD 저장을 위한 파라미터 세팅
            if (i == 0) {
                vendrInstockHdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
                vendrInstockHdVO.setStoreCd(sessionInfoVO.getStoreCd());
                vendrInstockHdVO.setSlipNo(vendrInstockVO.getSlipNo());
                vendrInstockHdVO.setRegId(sessionInfoVO.getUserId());
                vendrInstockHdVO.setRegDt(currentDt);
                vendrInstockHdVO.setModId(sessionInfoVO.getUserId());
                vendrInstockHdVO.setModDt(currentDt);
            }

            String insFg = "";
            // 기주문수량이 있는 경우 수정
            if(vendrInstockVO.getPrevInTotQty() != null) {
                insFg = "U";
                // 기주문수량이 있으면서 주문수량이 0 이나 null 인 경우 삭제
                if(vendrInstockVO.getInTotQty() == 0 || vendrInstockVO.getInTotQty() == null) {
                    insFg = "D";
                }
            }
            else {
                insFg = "I";
            }

            if(!insFg.equals("D")) {
                int slipFg       = vendrInstockVO.getSlipFg();
                int poUnitQty    = vendrInstockVO.getPoUnitQty();
                int prevUnitQty  = (vendrInstockVO.getPrevInUnitQty() == null ? 0 : vendrInstockVO.getPrevInUnitQty());
                int prevEtcQty   = (vendrInstockVO.getPrevInEtcQty()  == null ? 0 : vendrInstockVO.getPrevInEtcQty());
                int unitQty      = (vendrInstockVO.getInUnitQty()     == null ? 0 : vendrInstockVO.getInUnitQty());
                int etcQty       = (vendrInstockVO.getInEtcQty()      == null ? 0 : vendrInstockVO.getInEtcQty());
                int orderUnitQty = ((prevUnitQty + unitQty) + Integer.valueOf((prevEtcQty + etcQty) / poUnitQty)) * slipFg;
                int orderEtcQty  = Integer.valueOf((prevEtcQty + etcQty) % poUnitQty) * slipFg;
                int orderTotQty  = (vendrInstockVO.getInTotQty()   == null ? 0 : vendrInstockVO.getInTotQty()) * slipFg;
                Long orderAmt    = (vendrInstockVO.getInAmt()      == null ? 0 : vendrInstockVO.getInAmt())    * slipFg;
                Long orderVat    = (vendrInstockVO.getInVat()      == null ? 0 : vendrInstockVO.getInVat())    * slipFg;
                Long orderTot    = (vendrInstockVO.getInTot()      == null ? 0 : vendrInstockVO.getInTot())    * slipFg;

                vendrInstockVO.setInUnitQty(orderUnitQty);
                vendrInstockVO.setInEtcQty(orderEtcQty);
                vendrInstockVO.setInTotQty(orderTotQty);
                vendrInstockVO.setInAmt(orderAmt);
                vendrInstockVO.setInVat(orderVat);
                vendrInstockVO.setInTot(orderTot);
                vendrInstockVO.setRegId(sessionInfoVO.getUserId());
                vendrInstockVO.setRegDt(currentDt);
                vendrInstockVO.setModId(sessionInfoVO.getUserId());
                vendrInstockVO.setModDt(currentDt);
            }
            vendrInstockVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            vendrInstockVO.setStoreCd(sessionInfoVO.getStoreCd());

            // 추가
            if(insFg.equals("I")) {
                if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
                    result = vendrInstockMapper.insertHqVendrInstockDtl(vendrInstockVO);
                    if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                }
                else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
                    //                    result = vendrInstockMapper.insertStVendrInstockDtl(vendrInstockVO);
                    //                    if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                }
            }
            // 수정
            else if(insFg.equals("U")) {
                if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
                    result = vendrInstockMapper.updateHqVendrInstockDtl(vendrInstockVO);
                    if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                }
                else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
                    //                    result = vendrInstockMapper.updateStVendrInstockDtl(vendrInstockVO);
                    //                    if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                }
            }
            // 삭제
            else if(insFg.equals("D")) {
                if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
                    result = vendrInstockMapper.deleteHqVendrInstockDtl(vendrInstockVO);
                    if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                }
                else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
                    //                    result = vendrInstockMapper.deleteStVendrInstockDtl(vendrInstockVO);
                    //                    if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                }
            }

            returnResult += result;
            i++;
        }

        // 입고/반출정보 DTL의 집계정보 HD에 수정
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
            result = vendrInstockMapper.updateHqVendrInstockDtlSumHd(vendrInstockHdVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
        else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
            //            result = vendrInstockMapper.updateStVendrInstockDtlSumHd(vendrInstockVO);
            //            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }

        return returnResult;
    }


    /** 거래처 입고/반출등록 - 입고/반출상품 발주내역으로 세팅 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getVendrInstockOrderInfoRegList(VendrInstockVO vendrInstockVO, SessionInfoVO sessionInfoVO) {
        List<DefaultMap<String>> result = new ArrayList<DefaultMap<String>>();
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
            vendrInstockVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            result = vendrInstockMapper.getHqVendrInstockOrderInfoRegList(vendrInstockVO);
        }
        else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
            vendrInstockVO.setStoreCd(sessionInfoVO.getStoreCd());
            //            result = vendrInstockMapper.getStVendrInstockOrderInfoRegList(vendrInstockVO);
        }
        return result;
    }
}
