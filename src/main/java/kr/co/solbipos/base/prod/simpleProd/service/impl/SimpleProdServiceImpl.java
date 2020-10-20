package kr.co.solbipos.base.prod.simpleProd.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.prod.service.enums.PriceEnvFg;
import kr.co.solbipos.base.prod.prod.service.enums.ProdEnvFg;
import kr.co.solbipos.base.prod.prod.service.enums.ProdNoEnvFg;
import kr.co.solbipos.base.prod.simpleProd.service.SimpleProdService;
import kr.co.solbipos.base.prod.simpleProd.service.SimpleProdVO;
import kr.co.solbipos.base.prod.prod.service.ProdVO;
import kr.co.solbipos.base.prod.prod.service.impl.ProdMapper;
import kr.co.solbipos.base.prod.prod.service.enums.WorkModeFg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateString;
import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : SimpleProdServiceImpl.java
 * @Description : 기초관리 > 상품관리 > 간편상품등록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.08.26  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2020.08.26
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("simpleProdService")
@Transactional
public class SimpleProdServiceImpl implements SimpleProdService {
    private final SimpleProdMapper simpleProdMapper;
    private final ProdMapper prodMapper;
    private final MessageService messageService;
    private final CmmEnvUtil cmmEnvUtil;

    /**
     * Constructor Injection
     */
    @Autowired
    public SimpleProdServiceImpl(SimpleProdMapper simpleProdMapper, ProdMapper prodMapper, MessageService messageService, CmmEnvUtil cmmEnvUtil) {
        this.simpleProdMapper = simpleProdMapper;
        this.prodMapper = prodMapper;
        this.messageService = messageService;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    /** 검증결과 전체 삭제 */
    @Override
    public int getSimpleProdCheckDeleteAll(SimpleProdVO simpleProdVO, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();

        simpleProdVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        simpleProdVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            simpleProdVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        simpleProdVO.setSessionId(sessionInfoVO.getUserId());

        // 검증결과 삭제
        procCnt = simpleProdMapper.getSimpleProdCheckDeleteAll(simpleProdVO);

        return procCnt;
    }

    /** 검증결과 저장 */
    @Override
    public int getSimpleProdCheckSave(SimpleProdVO[] simpleProdVOs, SessionInfoVO sessionInfoVO) {

//        System.out.println("test1111");
        int procCnt = 0;
        int i = 1;
        String currentDt = currentDateTimeString();

        for(SimpleProdVO simpleProdVO : simpleProdVOs) {

            simpleProdVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            simpleProdVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
            if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
                simpleProdVO.setStoreCd(sessionInfoVO.getStoreCd());
            }

            simpleProdVO.setRegDt(currentDt);
            simpleProdVO.setRegId(sessionInfoVO.getUserId());
            simpleProdVO.setModDt(currentDt);
            simpleProdVO.setModId(sessionInfoVO.getUserId());

            simpleProdVO.setSessionId(sessionInfoVO.getUserId());
            simpleProdVO.setSeq(i);

            // 바코드 중복체크
            // 값이 있을때만
            if (simpleProdVO.getBarCd() != null && !"".equals(simpleProdVO.getBarCd())) {
                int barCdCnt = simpleProdMapper.getBarCdCnt(simpleProdVO);
                if(barCdCnt > 0) {
                    simpleProdVO.setResult("저장된 동일한 바코드가 존재합니다.");
                }
            }

            // 상품명 중복체크
            if(String.valueOf(true).equals(simpleProdVO.getChkProdNm())) {
                // 값이 있을때만
                if (simpleProdVO.getProdNm() != null && !"".equals(simpleProdVO.getProdNm())) {
                    int prodNmCnt = simpleProdMapper.getProdNmCnt(simpleProdVO);
                    if (prodNmCnt > 0) {
                        simpleProdVO.setResult("저장된 동일한 상품명이 존재합니다.");
                    }
                }
            }

            // 원가단가 길이체크
            // 값이 있을때만
            if (simpleProdVO.getCostUprc() != null && !"".equals(simpleProdVO.getCostUprc())) {
                if(simpleProdVO.getCostUprc() > 9999999999999999.0) {
                    simpleProdVO.setCostUprc(0.0);
                    simpleProdVO.setResult("원가단가 길이가 너무 깁니다.");
                }
            }

            // 공급단가 길이체크
            // 값이 있을때만
            if (simpleProdVO.getSplyUprc() != null && !"".equals(simpleProdVO.getSplyUprc())) {
                if(simpleProdVO.getSplyUprc() > 9999999999999999.0) {
                    simpleProdVO.setSplyUprc(0.0);
                    simpleProdVO.setResult("공급단가 길이가 너무 깁니다.");
                }
            }

            // 판매단가 길이체크
            // 값이 있을때만
            if (simpleProdVO.getSaleUprc() != null && !"".equals(simpleProdVO.getSaleUprc())) {
                if (simpleProdVO.getSaleUprc().length() > 16) {
                    simpleProdVO.setSaleUprc("");
                    simpleProdVO.setResult("판매단가 길이가 너무 깁니다.");
                }
            }


            // ProdVO
            ProdVO prodVO = new ProdVO();
            prodVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            prodVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
                prodVO.setStoreCd(sessionInfoVO.getStoreCd());
            }

            // 자동채번인 경우 상품코드 조회
            if(simpleProdVO.getProdNoEnv() == ProdNoEnvFg.AUTO) {
                // 자동채번 Start
                String prodCd = prodMapper.getProdCd(prodVO);

                // 순차적으로
                if(simpleProdVO.getSeq() == 1) {
                    simpleProdVO.setProdCd(prodCd);
                } else {
                    // 상품코드 자동채번
                    prodCd = simpleProdMapper.getProdCd(simpleProdVO);
                    simpleProdVO.setProdCd(prodCd);
                }
            // 수동채번인 경우 중복체크
            } else if(simpleProdVO.getProdNoEnv() == ProdNoEnvFg.MANUAL) {
                // 값이 있을때만
                if (simpleProdVO.getProdCd() != null && !"".equals(simpleProdVO.getProdCd())) {
                    prodVO.setProdCd(simpleProdVO.getProdCd());
                    int prodCdCnt = prodMapper.getProdCdCnt(prodVO);
                    if (prodCdCnt > 0) {
                        simpleProdVO.setResult("저장된 동일한 상품코드가 존재합니다.");
                    }
                }
            }

            if(simpleProdVO.getStoreCd() == null) { simpleProdVO.setStoreCd(""); }
            if(simpleProdVO.getProdCd() == null) { simpleProdVO.setProdCd(""); }
            if(simpleProdVO.getProdNm() == null) { simpleProdVO.setProdNm(""); }
            if(simpleProdVO.getSaleUprc() == null) { simpleProdVO.setSaleUprc(""); }
            if(simpleProdVO.getSplyUprc() == null) { simpleProdVO.setSplyUprc(0.0); }
            if(simpleProdVO.getCostUprc() == null) { simpleProdVO.setCostUprc(0.0); }
            if(simpleProdVO.getBarCd() == null) { simpleProdVO.setBarCd(""); }

            if (simpleProdVO.getResult() == null || simpleProdVO.getResult() == "") {
                simpleProdVO.setResult("검증성공");
            }

            // 검증결과 저장
            procCnt = simpleProdMapper.getSimpleProdCheckSave(simpleProdVO);
            i++;
        }

        return procCnt;
    }

    /** 검증결과 조회 */
    @Override
    public List<DefaultMap<Object>> getSimpleProdList(SimpleProdVO simpleProdVO, SessionInfoVO sessionInfoVO) {

        simpleProdVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        simpleProdVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            simpleProdVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        simpleProdVO.setSessionId(sessionInfoVO.getUserId());

        return simpleProdMapper.getSimpleProdList(simpleProdVO);
    }

    /** 상품 저장 */
    @Override
    public int getSimpleProdSave(SimpleProdVO[] simpleProdVOs, SessionInfoVO sessionInfoVO) {

//        System.out.println("test1111");
        int procCnt = 0;
        String currentDt = currentDateTimeString();

        for(SimpleProdVO simpleProdVO : simpleProdVOs) {

            simpleProdVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            simpleProdVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
            if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
                simpleProdVO.setStoreCd(sessionInfoVO.getStoreCd());
            }
            simpleProdVO.setSessionId(sessionInfoVO.getUserId());

            // 검증결과 성공인것만 저장
            if(("검증성공").equals(simpleProdVO.getResult())) {

                ProdVO prodVO = new ProdVO();
                prodVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
                prodVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
                if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
                    prodVO.setStoreCd(sessionInfoVO.getStoreCd());
                }

                // 신규상품등록 인 경우 WorkMode Flag 변경_2019.06.06
                prodVO.setWorkMode(WorkModeFg.REG_PROD);

                // 상품등록 본사 통제여부
                ProdEnvFg prodEnvstVal = ProdEnvFg.getEnum(cmmEnvUtil.getHqEnvst(sessionInfoVO, "0020"));

                // 판매가 본사 통제여부
                PriceEnvFg priceEnvstVal = PriceEnvFg.getEnum(cmmEnvUtil.getHqEnvst(sessionInfoVO, "0022"));

                prodVO.setRegDt(currentDt);
                prodVO.setRegId(sessionInfoVO.getUserId());
                prodVO.setModDt(currentDt);
                prodVO.setModId(sessionInfoVO.getUserId());

                prodVO.setProdCd(simpleProdVO.getProdCd());
                prodVO.setProdNm(simpleProdVO.getProdNm());
                prodVO.setSplyUprc(simpleProdVO.getSplyUprc());
                prodVO.setProdTypeFg(simpleProdVO.getProdTypeFg());
                prodVO.setPoProdFg(simpleProdVO.getPoProdFg());
                prodVO.setSplyUprc(simpleProdVO.getSplyUprc());
                prodVO.setCostUprc(simpleProdVO.getCostUprc());
                prodVO.setVatFg(simpleProdVO.getVatFg());
                prodVO.setBarCd(simpleProdVO.getBarCd());
                prodVO.setProdClassCd(simpleProdVO.getProdClassCd());

                prodVO.setPointSaveYn(simpleProdVO.getPointSaveYn());
                prodVO.setProdTipYn(simpleProdVO.getProdTipYn());
                prodVO.setSaleProdYn(simpleProdVO.getSaleProdYn());
                prodVO.setStockProdYn(simpleProdVO.getStockProdYn());
                prodVO.setSideProdYn(simpleProdVO.getSideProdYn());
                prodVO.setSetProdFg(simpleProdVO.getSetProdFg());
                prodVO.setLastCostUprc(simpleProdVO.getLastCostUprc());
                prodVO.setSplyUprcUseYn(simpleProdVO.getSplyUprcUseYn());
                prodVO.setPoUnitFg(simpleProdVO.getPoUnitFg());
                prodVO.setPoUnitQty(simpleProdVO.getPoUnitQty());
                prodVO.setPoMinQty(simpleProdVO.getPoMinQty());
                prodVO.setSafeStockQty(simpleProdVO.getSafeStockQty());
                prodVO.setUseYn(simpleProdVO.getUseYn());
                prodVO.setStartDate(currentDateString());
                prodVO.setEndDate("99991231");
                prodVO.setSaleUprc(simpleProdVO.getSaleUprc());

                // 매장에서 매장상품 등록시에 가격관리 구분 등록
                if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ)  prodVO.setPrcCtrlFg("H"); //본사
                else                                        prodVO.setPrcCtrlFg("S"); //매장

                // 상품정보 저장
                long result = prodMapper.saveProductInfo(prodVO);
                if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                //상품 바코드 저장(바코드정보가 있을 경우만)
                if (prodVO.getBarCd() != null && prodVO.getBarCd().length() > 0) {
                    int barCdResult = prodMapper.saveProdBarcd(prodVO);
                    if (barCdResult <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                }

                // [상품등록 - 본사통제시] 본사에서 상품정보 수정시 매장에 수정정보 내려줌
                if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ  && prodEnvstVal == ProdEnvFg.HQ) {

                    String procResult = prodMapper.insertHqProdToStoreProd(prodVO);

                    // 상품분류 매장에 INSERT
                    prodMapper.insertClsHqToStore(prodVO);

                    // 매장 상품 바코드 저장(바코드정보가 있을 경우만)
                    if(prodVO.getBarCd() != null && prodVO.getBarCd().length() > 0){
                        prodMapper.saveProdBarcdStore(prodVO);
                    }
                }

                // 상품 판매가 저장
                if(priceEnvstVal == PriceEnvFg.HQ)  prodVO.setSalePrcFg("1");
                else                                prodVO.setSalePrcFg("2");

                int salePriceReeulst = prodMapper.saveSalePrice(prodVO);
                if(salePriceReeulst <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                // 상품 판매가 변경 히스토리 저장
                int hqSalePriceHistResult = prodMapper.saveSalePriceHistory(prodVO);
                if(hqSalePriceHistResult <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                // [판매가 - 본사통제시] 본사에서 상품정보 수정시 매장에 수정정보 내려줌
                if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ  && priceEnvstVal == PriceEnvFg.HQ) {
                    String storeSalePriceReeulst = prodMapper.saveStoreSalePrice(prodVO);
                }


                // 저장완료된 검증결과만 삭제
                procCnt = simpleProdMapper.getSimpleProdCheckDelete(simpleProdVO);
            }
        }

        return procCnt;
    }
}