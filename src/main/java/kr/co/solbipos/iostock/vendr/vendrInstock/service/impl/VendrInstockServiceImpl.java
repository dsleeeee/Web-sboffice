package kr.co.solbipos.iostock.vendr.vendrInstock.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.DateUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.iostock.cmmExcelUpload.excelUpload.service.ExcelUploadVO;
import kr.co.solbipos.iostock.order.instockConfm.service.InstockConfmProdVO;
import kr.co.solbipos.iostock.vendr.vendrInstock.service.VendrInstockService;
import kr.co.solbipos.iostock.vendr.vendrInstock.service.VendrInstockVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

@Service("vendrInstockService")
@Transactional
public class VendrInstockServiceImpl implements VendrInstockService {
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final VendrInstockHqMapper vendrInstockHqMapper;
    private final VendrInstockStoreMapper vendrInstockStoreMapper;
    private final MessageService messageService;

    @Autowired
    public VendrInstockServiceImpl(VendrInstockHqMapper vendrInstockHqMapper, VendrInstockStoreMapper vendrInstockStoreMapper, MessageService messageService) {
        this.vendrInstockHqMapper = vendrInstockHqMapper;
        this.vendrInstockStoreMapper = vendrInstockStoreMapper;
        this.messageService = messageService;
    }


    /** 거래처 입고/반출등록 - 거래처 입고/반출 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getVendrInstockList(VendrInstockVO vendrInstockVO, SessionInfoVO sessionInfoVO) {
        List<DefaultMap<String>> result = new ArrayList<DefaultMap<String>>();

        // regId, regDt, modId, modDt, hqOfficd, storeCd 세팅
        vendrInstockVO = setSessionValue(vendrInstockVO, sessionInfoVO, null);

        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
            result = vendrInstockHqMapper.getVendrInstockList(vendrInstockVO);
        }
        else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
            result = vendrInstockStoreMapper.getVendrInstockList(vendrInstockVO);
        }
        return result;
    }


    /** 거래처 입고/반출등록 - 거래처 입고/반출 등록시 발주번호 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getVendrInstockOrderSlipList(VendrInstockVO vendrInstockVO, SessionInfoVO sessionInfoVO) {
        List<DefaultMap<String>> result = new ArrayList<DefaultMap<String>>();

        // regId, regDt, modId, modDt, hqOfficd, storeCd 세팅
        vendrInstockVO = setSessionValue(vendrInstockVO, sessionInfoVO, null);

        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
            result = vendrInstockHqMapper.getVendrInstockOrderSlipList(vendrInstockVO);
        }
        else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
            result = vendrInstockStoreMapper.getVendrInstockOrderSlipList(vendrInstockVO);
        }
        return result;
    }


    /** 거래처 입고/반출등록 - 입고/반출정보 상세 조회 */
    @Override
    public DefaultMap<String> getSlipInfo(VendrInstockVO vendrInstockVO, SessionInfoVO sessionInfoVO) {
        DefaultMap<String> result = new DefaultMap<String>();

        // regId, regDt, modId, modDt, hqOfficd, storeCd 세팅
        vendrInstockVO = setSessionValue(vendrInstockVO, sessionInfoVO, null);

        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
            result = vendrInstockHqMapper.getSlipInfo(vendrInstockVO);
        }
        else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
            result = vendrInstockStoreMapper.getSlipInfo(vendrInstockVO);
        }
        return result;
    }


    /** 거래처 입고/반출등록 - 입고/반출정보 저장 */
    @Override
    public DefaultMap<String> saveVendrInstockDtl(VendrInstockVO vendrInstockVO, SessionInfoVO sessionInfoVO) {
        String slipNo = "";
        int result = 0;

        // regId, regDt, modId, modDt, hqOfficd, storeCd 세팅
        vendrInstockVO = setSessionValue(vendrInstockVO, sessionInfoVO, null);

        // 신규등록
        if(StringUtil.getOrBlank(vendrInstockVO.getSlipNo()).equals("")) {
            // 전표번호 조회
            String yymm = DateUtil.currentDateString().substring(2, 6); // 새로운 전표번호 생성을 위한 년월(YYMM)
            VendrInstockVO maxSlipNoVO = new VendrInstockVO();
            maxSlipNoVO.setYymm(yymm);

            // regId, regDt, modId, modDt, hqOfficd, storeCd 세팅
            maxSlipNoVO = setSessionValue(maxSlipNoVO, sessionInfoVO, null);

            if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
                slipNo = vendrInstockHqMapper.getNewSlipNo(maxSlipNoVO);

                vendrInstockVO.setSlipNo(slipNo);
                vendrInstockVO.setProcFg("0");
                if(StringUtil.getOrBlank(vendrInstockVO.getInstockType()).equals("N")) {
                    vendrInstockVO.setOrderSlipNo("");
                }
                result = vendrInstockHqMapper.insertVendrInstockHd(vendrInstockVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            } else if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
                slipNo = vendrInstockStoreMapper.getNewSlipNo(maxSlipNoVO);

                vendrInstockVO.setSlipNo(slipNo);
                vendrInstockVO.setProcFg("0");
                result = vendrInstockStoreMapper.insertVendrInstockHd(vendrInstockVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }
        }
        // 신규등록이 아닌 경우
        else {
            slipNo = vendrInstockVO.getSlipNo();
            if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
                result = vendrInstockHqMapper.updateVendrInstockHd(vendrInstockVO);
                if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }
            else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
                result = vendrInstockStoreMapper.updateVendrInstockHd(vendrInstockVO);
                if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }
        }

        DefaultMap<String> resultMap = new DefaultMap<String>();
        resultMap.put("slipNo", slipNo);
        resultMap.put("slipFg", String.valueOf(vendrInstockVO.getSlipFg()));
        resultMap.put("vendrCd", String.valueOf(vendrInstockVO.getVendrCd()));
        return resultMap;
    }


    /** 거래처 입고/반출등록 - 입고/반출정보 삭제 */
    @Override
    public int deleteVendrInstockDtl(VendrInstockVO vendrInstockVO, SessionInfoVO sessionInfoVO) {
        int result = 0;

        // regId, regDt, modId, modDt, hqOfficd, storeCd 세팅
        vendrInstockVO = setSessionValue(vendrInstockVO, sessionInfoVO, null);

        String dtlProdExist = "N";
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
            dtlProdExist = vendrInstockHqMapper.getDtlProdExist(vendrInstockVO);
            if(dtlProdExist.equals("Y")) {
                throw new JsonException(Status.FAIL, messageService.get("vendrInstock.dtl.prodExist"));
            }

            result = vendrInstockHqMapper.deleteVendrInstockHd(vendrInstockVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
        else if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
            dtlProdExist = vendrInstockStoreMapper.getDtlProdExist(vendrInstockVO);
            if(dtlProdExist.equals("Y")) {
                throw new JsonException(Status.FAIL, messageService.get("vendrInstock.dtl.prodExist"));
            }

            result = vendrInstockStoreMapper.deleteVendrInstockHd(vendrInstockVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }

        return result;
    }


    /** 거래처 입고/반출등록 - 입고/반출정보 진행상태 변경 */
    @Override
    public int saveProcFg(VendrInstockVO vendrInstockVO, SessionInfoVO sessionInfoVO) {
        int result = 0;
        String procFg = StringUtil.getOrBlank(vendrInstockVO.getProcFg());

        // regId, regDt, modId, modDt, hqOfficd, storeCd 세팅
        vendrInstockVO = setSessionValue(vendrInstockVO, sessionInfoVO, null);

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
            result = vendrInstockHqMapper.updateProcFg(vendrInstockVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

            // 발주번호가 있는 경우
            if(!StringUtil.getOrBlank(vendrInstockVO.getOrderSlipNo()).equals("")) {
                // 발주 DT 내역의 입고관련 정보 초기화
                result = vendrInstockHqMapper.updateDefaultVendrOrderDtl(vendrInstockVO);

                // 발주 DT 내역의 입고관련 정보 갱신
                result = vendrInstockHqMapper.updateVendrInstockToOrderDtl(vendrInstockVO);

                // 입고DT에 있으면서 발주 DT 내역에 없는 내역은 신규로 생성
                result = vendrInstockHqMapper.insertVendrInstockToOrderDtl(vendrInstockVO);

                // 발주 DT 내역의 집계정보 HD에 수정
                result = vendrInstockHqMapper.updateVendrOrderDtlSumHd(vendrInstockVO);

                // 발주 테이블의 진행구분 입고중으로 수정
                vendrInstockVO.setProcFg("4");
                result = vendrInstockHqMapper.updateVendrOrderProcFg(vendrInstockVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }

            // 확정
            if(procFg.equals("1")) {
                // 거래처정산 입력
                result = vendrInstockHqMapper.insertVendrExact(vendrInstockVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                
                // TB_PO_HQ_VENDR_INSTOCK_PROD 확정여부 'Y'
                result = vendrInstockHqMapper.saveVendrInstockProd(vendrInstockVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }
            // 확정취소
            else if(procFg.equals("0")) {
                // 거래처정산 삭제
                result = vendrInstockHqMapper.deleteVendrExact(vendrInstockVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }
        }
        else if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
            result = vendrInstockStoreMapper.updateProcFg(vendrInstockVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

            // 발주번호가 있는 경우
            if(!StringUtil.getOrBlank(vendrInstockVO.getOrderSlipNo()).equals("")) {

                // 발주 DT 내역의 입고관련 정보 초기화
                result = vendrInstockStoreMapper.updateDefaultVendrOrderDtl(vendrInstockVO);

                // 발주 DT 내역의 입고관련 정보 갱신
                result = vendrInstockStoreMapper.updateVendrInstockToOrderDtl(vendrInstockVO);

                // 입고DT에 있으면서 발주 DT 내역에 없는 내역은 신규로 생성
                result = vendrInstockStoreMapper.insertVendrInstockToOrderDtl(vendrInstockVO);

                // 발주 DT 내역의 집계정보 HD에 수정
                result = vendrInstockStoreMapper.updateVendrOrderDtlSumHd(vendrInstockVO);

                // 발주 테이블의 진행구분 입고중으로 수정
                vendrInstockVO.setProcFg("4");
                result = vendrInstockStoreMapper.updateVendrOrderProcFg(vendrInstockVO);
                if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }
            // 확정
            if(procFg.equals("1")) {
                // 거래처정산 입력
                result = vendrInstockStoreMapper.insertVendrExact(vendrInstockVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                
                // TB_PO_STORE_VENDR_INSTOCK_PROD 확정여부 'Y'
                result = vendrInstockStoreMapper.saveVendrInstockProd(vendrInstockVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }
            // 확정취소
            else if(procFg.equals("0")) {
                // 거래처정산 삭제
                result = vendrInstockStoreMapper.deleteVendrExact(vendrInstockVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }
        }

        return result;
    }


    /** 거래처 입고/반출등록 - 입고/반출상품 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getVendrInstockProdList(VendrInstockVO vendrInstockVO, SessionInfoVO sessionInfoVO) {
        List<DefaultMap<String>> result = new ArrayList<DefaultMap<String>>();

        // regId, regDt, modId, modDt, hqOfficd, storeCd 세팅
        vendrInstockVO = setSessionValue(vendrInstockVO, sessionInfoVO, null);

        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
            result = vendrInstockHqMapper.getVendrInstockProdList(vendrInstockVO);
        }
        else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
            result = vendrInstockStoreMapper.getVendrInstockProdList(vendrInstockVO);
        }
        return result;
    }


    /** 거래처 입고/반출등록 - 진행구분 조회 */
    @Override
    public DefaultMap<String> getProcFgCheck(VendrInstockVO vendrInstockVO, SessionInfoVO sessionInfoVO) {
        DefaultMap<String> result = new DefaultMap<String>();

        // regId, regDt, modId, modDt, hqOfficd, storeCd 세팅
        vendrInstockVO = setSessionValue(vendrInstockVO, sessionInfoVO, null);

        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
            result = vendrInstockHqMapper.getProcFgCheck(vendrInstockVO);
        }
        else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
            result = vendrInstockStoreMapper.getProcFgCheck(vendrInstockVO);
        }
        return result;
    }


    /** 거래처 입고/반출등록 - 입고/반출상품 등록 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getVendrInstockProdRegList(VendrInstockVO vendrInstockVO, SessionInfoVO sessionInfoVO) {
        List<DefaultMap<String>> result = new ArrayList<DefaultMap<String>>();

        // regId, regDt, modId, modDt, hqOfficd, storeCd 세팅
        vendrInstockVO = setSessionValue(vendrInstockVO, sessionInfoVO, null);

        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
            result = vendrInstockHqMapper.getVendrInstockProdRegList(vendrInstockVO);
        }
        else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
            result = vendrInstockStoreMapper.getVendrInstockProdRegList(vendrInstockVO);
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
        
        //TB_PO_HQ_VENDR_INSTOCK_DTL Insert or Update에 사용
        String[] storageCd;
        String[] storageNm;
        String[] storageInUnitQty;
        String[] storageInEtcQty;
        String[] storageInTotQty;
        String[] storageInAmt;
        String[] storageInVat;
        String[] storageInTot;
        
        // regId, regDt, modId, modDt, hqOfficd, storeCd 세팅
        vendrInstockHdVO = setSessionValue(vendrInstockHdVO, sessionInfoVO, currentDt);

        int i = 0;
        for (VendrInstockVO vendrInstockVO : vendrInstockVOs) {
            // regId, regDt, modId, modDt, hqOfficd, storeCd 세팅
            vendrInstockVO = setSessionValue(vendrInstockVO, sessionInfoVO, currentDt);

            // HD 저장을 위한 파라미터 세팅
            if (i == 0) {
                vendrInstockHdVO.setSlipNo(vendrInstockVO.getSlipNo());
            }

            String insFg = "";
            if(!StringUtil.getOrBlank(vendrInstockVO.getInTotQty()).equals("")) {
                // 기주문수량이 있는 경우 수정
                if(vendrInstockVO.getPrevInTotQty() != null) {
                    insFg = "U";
                    // 기주문수량이 있으면서 주문수량이 0 이나 null 인 경우 삭제
                    if(vendrInstockVO.getInTotQty() == 0 || vendrInstockVO.getInTotQty() == null) {
                        insFg = "D";
                    }
                }
                else if(vendrInstockVO.getInTotQty() != null) {
                    insFg = "I";
                }

                if(!insFg.equals("D")) {
                    int slipFg       = vendrInstockVO.getSlipFg();
                    int poUnitQty    = Math.abs(vendrInstockVO.getPoUnitQty());
                    int prevUnitQty  = (vendrInstockVO.getPrevInUnitQty() == null ? 0 : Math.abs(vendrInstockVO.getPrevInUnitQty()));
                    int prevEtcQty   = (vendrInstockVO.getPrevInEtcQty()  == null ? 0 : Math.abs(vendrInstockVO.getPrevInEtcQty()));
                    int unitQty      = (vendrInstockVO.getInUnitQty()     == null ? 0 : Math.abs(vendrInstockVO.getInUnitQty()));
                    int etcQty       = (vendrInstockVO.getInEtcQty()      == null ? 0 : Math.abs(vendrInstockVO.getInEtcQty()));
                                        
                    int orderUnitQty = ((prevUnitQty + unitQty) + Integer.valueOf((prevEtcQty + etcQty) / poUnitQty)) * slipFg;
                    int orderEtcQty  = Integer.valueOf((prevEtcQty + etcQty) % poUnitQty) * slipFg;
                    int orderTotQty  = (vendrInstockVO.getInTotQty()   == null ? 0 : Math.abs(vendrInstockVO.getInTotQty())) * slipFg;
                    Long orderAmt    = (vendrInstockVO.getInAmt()      == null ? 0 : Math.abs(vendrInstockVO.getInAmt()))    * slipFg;
                    Long orderVat    = (vendrInstockVO.getInVat()      == null ? 0 : Math.abs(vendrInstockVO.getInVat()))    * slipFg;
                    Long orderTot    = (vendrInstockVO.getInTot()      == null ? 0 : Math.abs(vendrInstockVO.getInTot()))    * slipFg;
                    
                    vendrInstockVO.setInUnitQty(orderUnitQty);
                    vendrInstockVO.setInEtcQty(orderEtcQty);
                    vendrInstockVO.setInTotQty(orderTotQty);
                    vendrInstockVO.setInAmt(orderAmt);
                    vendrInstockVO.setInVat(orderVat);
                    vendrInstockVO.setInTot(orderTot);
                }

                // 추가
                if(insFg.equals("I")) {
                    if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
                        result = vendrInstockHqMapper.insertVendrInstockDtl(vendrInstockVO);
                        if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                        
                        //창고별 상품저장
                        //TB_PO_HQ_VENDR_INSTOCK_PROD - START
                    	// ^ 로 사용하는  구분자를 별도의 constant로 구현하지 않았음. (추후 굳이 변경할 필요가 없다고 생각되기에)
        	            storageCd           = vendrInstockVO.getArrStorageCd().split("\\^");	//split의 인자로 들어가는 String Token이 regex 정규식이기 때문에, 특수문자임을 명시적으로 알려주어야 함.
        	            storageNm           = vendrInstockVO.getArrStorageNm().split("\\^");
        	            storageInUnitQty    = vendrInstockVO.getArrInUnitQty().split("\\^");
        	            storageInEtcQty     = vendrInstockVO.getArrInEtcQty	().split("\\^");
        	            storageInTotQty     = vendrInstockVO.getArrInTotQty	().split("\\^");
        	            storageInAmt        = vendrInstockVO.getArrInAmt	().split("\\^");
        	            storageInVat        = vendrInstockVO.getArrInVat	().split("\\^");
        	            storageInTot        = vendrInstockVO.getArrInTot	().split("\\^");

        	            for(int k=0; k<storageCd.length; k++) {
        		            LOGGER.debug("### storageInUnitQty: " + storageInUnitQty[k]	);
        		            LOGGER.debug("### storageInEtcQty : " + storageInEtcQty	[k]	);
        		            LOGGER.debug("### storageInTotQty : " + storageInTotQty	[k]	);
        		            LOGGER.debug("### storageInAmt    : " + storageInAmt	[k]	);
        		            LOGGER.debug("### storageInVat    : " + storageInVat	[k]	);
        		            LOGGER.debug("### storageInTot    : " + storageInTot	[k]	);

        		            vendrInstockVO.setStorageCd				(storageCd[k]								);	//창고코드
        		            vendrInstockVO.setOccrFg				(vendrInstockVO.getSlipFg()	== 1 ? "01" : "16"	);	//발생구분(03:매장입고)
        		            vendrInstockVO.setSlipFg		        (vendrInstockVO.getSlipFg()					);	//전표구분 1:주문 -1:반품

        		            vendrInstockVO.setInUnitQty		        (Integer.parseInt	(storageInUnitQty	[k]));	//입고수량 주문단위
        		            vendrInstockVO.setInEtcQty		        (Integer.parseInt	(storageInEtcQty	[k]));	//입고수량 나머지
        		            vendrInstockVO.setInTotQty		        (Integer.parseInt	(storageInTotQty	[k]));	//입고수량합계 낱개
        		            vendrInstockVO.setInAmt			        (Long.parseLong		(storageInAmt		[k]));	//입고금액
        		            vendrInstockVO.setInVat			        (Long.parseLong		(storageInVat		[k]));	//입고금액VAT
        		            vendrInstockVO.setInTot			        (Long.parseLong		(storageInTot		[k]));	//입고금액합계

        		            vendrInstockVO.setRegId			        (sessionInfoVO.getUserId());
        		            vendrInstockVO.setRegDt			        (currentDt	);
        		            vendrInstockVO.setModId			        (sessionInfoVO.getUserId());
        		            vendrInstockVO.setModDt			        (currentDt	);

        	            	LOGGER.debug("### getProperties: " + vendrInstockVO.getProperties() );
        	            	
    	            		result = vendrInstockHqMapper.insertVendrInstockProd(vendrInstockVO);
    	            		if(result <= 0) throw new JsonException(Status.SERVER_ERROR, messageService.get("cmm.saveFail"));
        	            }
        	        //TB_PO_HQ_VENDR_INSTOCK_PROD - END
                    }
                    else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
                        result = vendrInstockStoreMapper.insertVendrInstockDtl(vendrInstockVO);
                        if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                        
                        //창고별 상품저장
                        //TB_PO_STORE_VENDR_INSTOCK_PROD - START
                    	// ^ 로 사용하는  구분자를 별도의 constant로 구현하지 않았음. (추후 굳이 변경할 필요가 없다고 생각되기에)
        	            storageCd           = vendrInstockVO.getArrStorageCd().split("\\^");	//split의 인자로 들어가는 String Token이 regex 정규식이기 때문에, 특수문자임을 명시적으로 알려주어야 함.
        	            storageNm           = vendrInstockVO.getArrStorageNm().split("\\^");
        	            storageInUnitQty    = vendrInstockVO.getArrInUnitQty().split("\\^");
        	            storageInEtcQty     = vendrInstockVO.getArrInEtcQty	().split("\\^");
        	            storageInTotQty     = vendrInstockVO.getArrInTotQty	().split("\\^");
        	            storageInAmt        = vendrInstockVO.getArrInAmt	().split("\\^");
        	            storageInVat        = vendrInstockVO.getArrInVat	().split("\\^");
        	            storageInTot        = vendrInstockVO.getArrInTot	().split("\\^");

        	            for(int k=0; k<storageCd.length; k++) {
        		            LOGGER.debug("### storageInUnitQty: " + storageInUnitQty[k]	);
        		            LOGGER.debug("### storageInEtcQty : " + storageInEtcQty	[k]	);
        		            LOGGER.debug("### storageInTotQty : " + storageInTotQty	[k]	);
        		            LOGGER.debug("### storageInAmt    : " + storageInAmt	[k]	);
        		            LOGGER.debug("### storageInVat    : " + storageInVat	[k]	);
        		            LOGGER.debug("### storageInTot    : " + storageInTot	[k]	);

        		            vendrInstockVO.setStorageCd				(storageCd[k]								);	//창고코드
        		            vendrInstockVO.setOccrFg				(vendrInstockVO.getSlipFg()	== 1 ? "06" : "18"	);	//발생구분(03:매장입고)
        		            vendrInstockVO.setSlipFg		        (vendrInstockVO.getSlipFg()					);	//전표구분 1:주문 -1:반품

        		            vendrInstockVO.setInUnitQty		        (Integer.parseInt	(storageInUnitQty	[k]));	//입고수량 주문단위
        		            vendrInstockVO.setInEtcQty		        (Integer.parseInt	(storageInEtcQty	[k]));	//입고수량 나머지
        		            vendrInstockVO.setInTotQty		        (Integer.parseInt	(storageInTotQty	[k]));	//입고수량합계 낱개
        		            vendrInstockVO.setInAmt			        (Long.parseLong		(storageInAmt		[k]));	//입고금액
        		            vendrInstockVO.setInVat			        (Long.parseLong		(storageInVat		[k]));	//입고금액VAT
        		            vendrInstockVO.setInTot			        (Long.parseLong		(storageInTot		[k]));	//입고금액합계

        		            vendrInstockVO.setRegId			        (sessionInfoVO.getUserId());
        		            vendrInstockVO.setRegDt			        (currentDt	);
        		            vendrInstockVO.setModId			        (sessionInfoVO.getUserId());
        		            vendrInstockVO.setModDt			        (currentDt	);

        	            	LOGGER.debug("### getProperties: " + vendrInstockVO.getProperties() );
        	            	
    	            		result = vendrInstockStoreMapper.insertVendrInstockProd(vendrInstockVO);
    	            		if(result <= 0) throw new JsonException(Status.SERVER_ERROR, messageService.get("cmm.saveFail"));
        	            }
        	        //TB_PO_STORE_VENDR_INSTOCK_PROD - END
                    }
                }
                // 수정
                else if(insFg.equals("U")) {
                    if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
                        result = vendrInstockHqMapper.updateVendrInstockDtl(vendrInstockVO);
                        if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                        
                        //창고별 상품저장
                        //TB_PO_HQ_VENDR_INSTOCK_PROD - START
                    	// ^ 로 사용하는  구분자를 별도의 constant로 구현하지 않았음. (추후 굳이 변경할 필요가 없다고 생각되기에)
        	            storageCd           = vendrInstockVO.getArrStorageCd().split("\\^");	//split의 인자로 들어가는 String Token이 regex 정규식이기 때문에, 특수문자임을 명시적으로 알려주어야 함.
        	            storageNm           = vendrInstockVO.getArrStorageNm().split("\\^");
        	            storageInUnitQty    = vendrInstockVO.getArrInUnitQty().split("\\^");
        	            storageInEtcQty     = vendrInstockVO.getArrInEtcQty	().split("\\^");
        	            storageInTotQty     = vendrInstockVO.getArrInTotQty	().split("\\^");
        	            storageInAmt        = vendrInstockVO.getArrInAmt	().split("\\^");
        	            storageInVat        = vendrInstockVO.getArrInVat	().split("\\^");
        	            storageInTot        = vendrInstockVO.getArrInTot	().split("\\^");
        	                    	            
        	            for(int k=0; k<storageCd.length; k++) {
        		            LOGGER.debug("### storageInUnitQty: " + storageInUnitQty[k]	);
        		            LOGGER.debug("### storageInEtcQty : " + storageInEtcQty	[k]	);
        		            LOGGER.debug("### storageInTotQty : " + storageInTotQty	[k]	);
        		            LOGGER.debug("### storageInAmt    : " + storageInAmt	[k]	);
        		            LOGGER.debug("### storageInVat    : " + storageInVat	[k]	);
        		            LOGGER.debug("### storageInTot    : " + storageInTot	[k]	);

        		            vendrInstockVO.setStorageCd				(storageCd[k]								);	//창고코드
        		            vendrInstockVO.setOccrFg				("01"										);	//발생구분(03:매장입고)
        		            vendrInstockVO.setSlipFg		        (1											);	//전표구분 1:주문 -1:반품

        		            vendrInstockVO.setInUnitQty		        (Integer.parseInt	(storageInUnitQty	[k]));	//입고수량 주문단위
        		            vendrInstockVO.setInEtcQty		        (Integer.parseInt	(storageInEtcQty	[k]));	//입고수량 나머지
        		            vendrInstockVO.setInTotQty		        (Integer.parseInt	(storageInTotQty	[k]));	//입고수량합계 낱개
        		            vendrInstockVO.setInAmt			        (Long.parseLong		(storageInAmt		[k]));	//입고금액
        		            vendrInstockVO.setInVat			        (Long.parseLong		(storageInVat		[k]));	//입고금액VAT
        		            vendrInstockVO.setInTot			        (Long.parseLong		(storageInTot		[k]));	//입고금액합계

        		            vendrInstockVO.setRegId			        (sessionInfoVO.getUserId());
        		            vendrInstockVO.setRegDt			        (currentDt	);
        		            vendrInstockVO.setModId			        (sessionInfoVO.getUserId());
        		            vendrInstockVO.setModDt			        (currentDt	);

        	            	LOGGER.debug("### getProperties: " + vendrInstockVO.getProperties() );

    	            		result = vendrInstockHqMapper.updateVendrInstockProd(vendrInstockVO);
    	            		if(result <= 0) throw new JsonException(Status.SERVER_ERROR, messageService.get("cmm.saveFail"));
        	            }
        	        //TB_PO_HQ_VENDR_INSTOCK_PROD - END
                    }
                    else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
                        result = vendrInstockStoreMapper.updateVendrInstockDtl(vendrInstockVO);
                        if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                        
                        //창고별 상품저장
                        //TB_PO_STORE_VENDR_INSTOCK_PROD - START
                    	// ^ 로 사용하는  구분자를 별도의 constant로 구현하지 않았음. (추후 굳이 변경할 필요가 없다고 생각되기에)
        	            storageCd           = vendrInstockVO.getArrStorageCd().split("\\^");	//split의 인자로 들어가는 String Token이 regex 정규식이기 때문에, 특수문자임을 명시적으로 알려주어야 함.
        	            storageNm           = vendrInstockVO.getArrStorageNm().split("\\^");
        	            storageInUnitQty    = vendrInstockVO.getArrInUnitQty().split("\\^");
        	            storageInEtcQty     = vendrInstockVO.getArrInEtcQty	().split("\\^");
        	            storageInTotQty     = vendrInstockVO.getArrInTotQty	().split("\\^");
        	            storageInAmt        = vendrInstockVO.getArrInAmt	().split("\\^");
        	            storageInVat        = vendrInstockVO.getArrInVat	().split("\\^");
        	            storageInTot        = vendrInstockVO.getArrInTot	().split("\\^");
        	                   	            
        	            for(int k=0; k<storageCd.length; k++) {
        		            LOGGER.debug("### storageInUnitQty: " + storageInUnitQty[k]	);
        		            LOGGER.debug("### storageInEtcQty : " + storageInEtcQty	[k]	);
        		            LOGGER.debug("### storageInTotQty : " + storageInTotQty	[k]	);
        		            LOGGER.debug("### storageInAmt    : " + storageInAmt	[k]	);
        		            LOGGER.debug("### storageInVat    : " + storageInVat	[k]	);
        		            LOGGER.debug("### storageInTot    : " + storageInTot	[k]	);

        		            vendrInstockVO.setStorageCd				(storageCd[k]								);	//창고코드
        		            vendrInstockVO.setOccrFg				("06"										);	//발생구분(03:매장입고)
        		            vendrInstockVO.setSlipFg		        (1											);	//전표구분 1:주문 -1:반품

        		            vendrInstockVO.setInUnitQty		        (Integer.parseInt	(storageInUnitQty	[k]));	//입고수량 주문단위
        		            vendrInstockVO.setInEtcQty		        (Integer.parseInt	(storageInEtcQty	[k]));	//입고수량 나머지
        		            vendrInstockVO.setInTotQty		        (Integer.parseInt	(storageInTotQty	[k]));	//입고수량합계 낱개
        		            vendrInstockVO.setInAmt			        (Long.parseLong		(storageInAmt		[k]));	//입고금액
        		            vendrInstockVO.setInVat			        (Long.parseLong		(storageInVat		[k]));	//입고금액VAT
        		            vendrInstockVO.setInTot			        (Long.parseLong		(storageInTot		[k]));	//입고금액합계

        		            vendrInstockVO.setRegId			        (sessionInfoVO.getUserId());
        		            vendrInstockVO.setRegDt			        (currentDt	);
        		            vendrInstockVO.setModId			        (sessionInfoVO.getUserId());
        		            vendrInstockVO.setModDt			        (currentDt	);

        	            	LOGGER.debug("### getProperties: " + vendrInstockVO.getProperties() );

    	            		result = vendrInstockStoreMapper.updateVendrInstockProd(vendrInstockVO);
    	            		if(result <= 0) throw new JsonException(Status.SERVER_ERROR, messageService.get("cmm.saveFail"));
        	            }
        	        //TB_PO_STORE_VENDR_INSTOCK_PROD - END
                    }
                }
                // 삭제
                else if(insFg.equals("D")) {
                    if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
                        result = vendrInstockHqMapper.deleteVendrInstockDtl(vendrInstockVO);
                        if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                    }
                    else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
                        result = vendrInstockStoreMapper.deleteVendrInstockDtl(vendrInstockVO);
                        if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                    }
                }
            }

            if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
                if(!StringUtil.getOrBlank(vendrInstockVO.getSplyUprc()).equals("")) {
                    // 상품 공급가 수정(기존 공급가와 다른 경우만 수정 된다.)
                    result = vendrInstockHqMapper.updateProdSplyUprc(vendrInstockVO);

                    // 매장공급가 동시저장 저장인 경우
                    if (StringUtil.getOrBlank(vendrInstockVO.getStoreSplyFg()).equals("Y")) {
                        // 매장상품 공급가 수정(기존 공급가와 다른 경우만 수정 된다.)
                        result = vendrInstockHqMapper.updateStProdSplyUprc(vendrInstockVO);
                    }
                }
            }

            returnResult += result;
            i++;
        }

        // 입고/반출정보 DTL의 집계정보 HD에 수정
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
            result = vendrInstockHqMapper.updateVendrInstockDtlSumHd(vendrInstockHdVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
        else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
            result = vendrInstockStoreMapper.updateVendrInstockDtlSumHd(vendrInstockHdVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }

        return returnResult;
    }


    /** 거래처 입고/반출등록 - 입고/반출상품 발주내역으로 세팅 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getVendrInstockOrderInfoRegList(VendrInstockVO vendrInstockVO, SessionInfoVO sessionInfoVO) {
        List<DefaultMap<String>> result = new ArrayList<DefaultMap<String>>();

        // regId, regDt, modId, modDt, hqOfficd, storeCd 세팅
        vendrInstockVO = setSessionValue(vendrInstockVO, sessionInfoVO, null);

        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
            result = vendrInstockHqMapper.getVendrInstockOrderInfoRegList(vendrInstockVO);
        }
        else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
            result = vendrInstockStoreMapper.getVendrInstockOrderInfoRegList(vendrInstockVO);
        }
        return result;
    }


    /** 거래처 입고/반출등록 - 반출서 반출정보 조회(반출처, 공급자 정보) */
    @Override
    public DefaultMap<String> getVendrInstockReportInfo(VendrInstockVO vendrInstockVO, SessionInfoVO sessionInfoVO) {
        DefaultMap<String> result = new DefaultMap<String>();
        // regId, regDt, modId, modDt, hqOfficd, storeCd, orgnCd 세팅
        vendrInstockVO = setSessionValue(vendrInstockVO, sessionInfoVO, null);

        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
            result = vendrInstockHqMapper.getVendrInstockReportInfo(vendrInstockVO);
        }
        else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
            result = vendrInstockStoreMapper.getVendrInstockReportInfo(vendrInstockVO);
        }
        return result;
    }


    /** 거래처 발주등록 - 엑셀업로드 */
    @Override
    public int excelUpload(ExcelUploadVO excelUploadVO, SessionInfoVO sessionInfoVO) {
        int result = 0;

        String currentDt = currentDateTimeString();

        excelUploadVO.setSessionId(sessionInfoVO.getSessionId());
        excelUploadVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        excelUploadVO.setStoreCd(sessionInfoVO.getStoreCd());
        excelUploadVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        excelUploadVO.setRegId(sessionInfoVO.getUserId());
        excelUploadVO.setRegDt(currentDt);
        excelUploadVO.setModId(sessionInfoVO.getUserId());
        excelUploadVO.setModDt(currentDt);

        // 수량추가인 경우
        if(StringUtil.getOrBlank(excelUploadVO.getAddQtyFg()).equals("add")) {
            if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
                result = vendrInstockHqMapper.insertExcelUploadAddQty(excelUploadVO);
            }
            else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
                result = vendrInstockStoreMapper.insertExcelUploadAddQty(excelUploadVO);
            }
        }

        // 기존 데이터중 엑셀업로드 한 데이터와 같은 상품은 삭제
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
            result = vendrInstockHqMapper.deleteVendrInstockToExcelUploadData(excelUploadVO);
        }
        else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
            result = vendrInstockStoreMapper.deleteVendrInstockToExcelUploadData(excelUploadVO);
        }

        // 엑셀업로드 한 수량을 입고수량으로 입력
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
            result = vendrInstockHqMapper.insertVendrInstockToExcelUploadData(excelUploadVO);
        }
        else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
            result = vendrInstockStoreMapper.insertVendrInstockToExcelUploadData(excelUploadVO);
        }

        // 정상 입력된 데이터 TEMP 테이블에서 삭제
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
            result = vendrInstockHqMapper.deleteExcelUploadCompleteData(excelUploadVO);
        }
        else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
            result = vendrInstockStoreMapper.deleteExcelUploadCompleteData(excelUploadVO);
        }

        VendrInstockVO vendrInstockHdVO = new VendrInstockVO();
        vendrInstockHdVO.setSlipNo(excelUploadVO.getSlipNo());

        /** regId, regDt, modId, modDt, hqOfficd, storeCd, orgnFg 세팅  */
        vendrInstockHdVO = setSessionValue(vendrInstockHdVO, sessionInfoVO, currentDt);

        // 입고/반출정보 DTL의 집계정보 HD에 수정
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
            result = vendrInstockHqMapper.updateVendrInstockDtlSumHd(vendrInstockHdVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
        else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
            result = vendrInstockStoreMapper.updateVendrInstockDtlSumHd(vendrInstockHdVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }

        return result;
    }


    /** regId, regDt, modId, modDt, hqOfficd, storeCd 세팅  */
    public VendrInstockVO setSessionValue(VendrInstockVO vendrInstockVO, SessionInfoVO sessionInfoVO, String currentDt) {
        if(StringUtil.getOrBlank(currentDt).equals("")) {
            currentDt = currentDateTimeString();
        }

        vendrInstockVO.setRegId(sessionInfoVO.getUserId());
        vendrInstockVO.setRegDt(currentDt);
        vendrInstockVO.setModId(sessionInfoVO.getUserId());
        vendrInstockVO.setModDt(currentDt);

        vendrInstockVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        vendrInstockVO.setStoreCd(sessionInfoVO.getStoreCd());

        return vendrInstockVO;
    }
}
