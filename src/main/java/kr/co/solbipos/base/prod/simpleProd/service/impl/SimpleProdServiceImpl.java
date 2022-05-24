package kr.co.solbipos.base.prod.simpleProd.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.prod.service.enums.PriceEnvFg;
import kr.co.solbipos.base.prod.prod.service.enums.ProdEnvFg;
import kr.co.solbipos.base.prod.prod.service.enums.ProdNoEnvFg;
import kr.co.solbipos.base.prod.prod.service.enums.HqProdEnvFg;
import kr.co.solbipos.base.prod.simpleProd.service.SimpleProdService;
import kr.co.solbipos.base.prod.simpleProd.service.SimpleProdVO;
import kr.co.solbipos.base.prod.prod.service.ProdVO;
import kr.co.solbipos.base.prod.prod.service.impl.ProdMapper;
import kr.co.solbipos.base.prod.prodExcelUpload.service.ProdExcelUploadVO;
import kr.co.solbipos.base.prod.prodExcelUpload.service.impl.ProdExcelUploadMapper;
import kr.co.solbipos.stock.adj.adj.service.AdjVO;
import kr.co.solbipos.stock.adj.adj.service.impl.AdjMapper;
import kr.co.solbipos.base.prod.prod.service.enums.WorkModeFg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateString;
import static kr.co.common.utils.DateUtil.currentDateTimeString;
import static kr.co.common.utils.spring.StringUtil.convertToJson;

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
    private final SimpleProdMapper simpleProdMapper; // 간편상품등록
    private final ProdMapper prodMapper; // 상품등록
    private final ProdExcelUploadMapper prodExcelUploadMapper; // 상품엑셀업로드
    private final AdjMapper adjMapper; // 조정관리
    private final MessageService messageService;
    private final CmmEnvUtil cmmEnvUtil;

    /**
     * Constructor Injection
     */
    @Autowired
    public SimpleProdServiceImpl(SimpleProdMapper simpleProdMapper, ProdMapper prodMapper, ProdExcelUploadMapper prodExcelUploadMapper, AdjMapper adjMapper, MessageService messageService, CmmEnvUtil cmmEnvUtil) {
        this.simpleProdMapper = simpleProdMapper;
        this.prodMapper = prodMapper;
        this.prodExcelUploadMapper = prodExcelUploadMapper;
        this.adjMapper = adjMapper;
        this.messageService = messageService;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    /** 거래처 콤보 조회 */
    @Override
    public List<DefaultMap<String>> vendrComboList(SessionInfoVO sessionInfoVO) {

        SimpleProdVO simpleProdVO = new SimpleProdVO();

        simpleProdVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        simpleProdVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            simpleProdVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return simpleProdMapper.vendrComboList(simpleProdVO);
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

            // 가격관리구분
            if (simpleProdVO.getPrcCtrlFg() != null && !"".equals(simpleProdVO.getPrcCtrlFg())) {
            } else {
                simpleProdVO.setResult("가격관리구분을 선택해주세요.");
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

            // 발주상품구분
            if (simpleProdVO.getPoProdFg() != null && !"".equals(simpleProdVO.getPoProdFg())) {
            } else {
                simpleProdVO.setResult("발주상품구분를 선택해주세요.");
            }

            // 상품유형
            if (simpleProdVO.getProdTypeFg() != null && !"".equals(simpleProdVO.getProdTypeFg())) {
            } else {
                simpleProdVO.setResult("상품유형를 선택해주세요.");
            }

            // 판매단가 길이체크
            // 값이 있을때만
            if (simpleProdVO.getSaleUprc() != null && !"".equals(simpleProdVO.getSaleUprc())) {
                if (simpleProdVO.getSaleUprc().length() > 16) {
                    simpleProdVO.setSaleUprc("");
                    simpleProdVO.setResult("판매단가 길이가 너무 깁니다.");
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

            if (simpleProdVO.getBrandUseFg() == "1") {
                // 브랜드 등록여부 체크
                if (simpleProdVO.getHqBrandCd() != null && !"".equals(simpleProdVO.getHqBrandCd())) {
                    simpleProdVO.setUserId(sessionInfoVO.getUserId());

                    int hqBrandCdChk = simpleProdMapper.getHqBrandCdChk(simpleProdVO);
                    if (hqBrandCdChk < 1) {
                        simpleProdVO.setResult("브랜드를 다시 선택해주세요.");
                    }
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
                simpleProdVO.setProdCd("자동채번");
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
            if(simpleProdVO.getVendrCd() == null) { simpleProdVO.setVendrCd(""); }
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
        String currentDate = currentDateString();

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

                // 본사신규상품매장생성
//                HqProdEnvFg hqProdEnvstVal = HqProdEnvFg.getEnum(cmmEnvUtil.getHqEnvst(sessionInfoVO, "0043"));

                prodVO.setRegDt(currentDt);
                prodVO.setRegId(sessionInfoVO.getUserId());
                prodVO.setModDt(currentDt);
                prodVO.setModId(sessionInfoVO.getUserId());

//                prodVO.setProdCd(simpleProdVO.getProdCd());
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
                prodVO.setPrcCtrlFg(simpleProdVO.getPrcCtrlFg());
                prodVO.setStinSaleUprc(simpleProdVO.getStinSaleUprc());
                prodVO.setDlvrSaleUprc(simpleProdVO.getDlvrSaleUprc());
                prodVO.setPackSaleUprc(simpleProdVO.getPackSaleUprc());
                prodVO.setCornrCd("00");
                prodVO.setHqBrandCd(simpleProdVO.getHqBrandCd());
                prodVO.setRemark(simpleProdVO.getRemark());
                if(simpleProdVO.getDepositCupFg() != null && !simpleProdVO.getDepositCupFg().equals("선택")){
                    prodVO.setDepositCupFg(simpleProdVO.getDepositCupFg());
                }

                // 자동채번인 경우 상품코드 조회
                if(simpleProdVO.getProdNoEnv() == ProdNoEnvFg.AUTO) {
                    // 자동채번 Start
                    String prodCd = prodMapper.getProdCd(prodVO);
                    prodVO.setProdCd(prodCd);
                    // 2022.03.26 - 거래처코드 있으면 엑셀업로드 후 삭제가 안 되어서 주석처리
                    if(simpleProdVO.getVendrCd() != null && !"".equals(simpleProdVO.getVendrCd())){
                        simpleProdVO.setProdCd(prodCd);
                    }
                // 수동채번인 경우 중복체크
                } else if(simpleProdVO.getProdNoEnv() == ProdNoEnvFg.MANUAL) {
                    // 값이 있을때만
                    if (simpleProdVO.getProdCd() != null && !"".equals(simpleProdVO.getProdCd())) {
                        prodVO.setProdCd(simpleProdVO.getProdCd());
                        int prodCdCnt = prodMapper.getProdCdCnt(prodVO);
                        if (prodCdCnt > 0) throw new JsonException(Status.FAIL, "저장된 동일한 상품코드가 존재합니다.");
                    }
                }

               // 상품정보 저장
                long result = prodMapper.saveProductInfo(prodVO);
                if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                //상품 바코드 저장(바코드정보가 있을 경우만)
                if (prodVO.getBarCd() != null && prodVO.getBarCd().length() > 0) {
                    int barCdResult = prodMapper.saveProdBarcd(prodVO);
                    if (barCdResult <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                }

                // 본사신규상품매장생성 [0 전매장]일 경우 매장에 수정정보 내려줌
//                if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ  && hqProdEnvstVal == HqProdEnvFg.ALL) {
                // 본사인 경우 매장에 수정정보 내려줌
                if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
                    //[본사신규상품매장생성]이 [0 자동생성]일 경우 매장에 수정정보 내려줌
                    if(CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "0043") , "0").equals("0")) {
                        // 상품정보 매장에 INSERT
                        String procResult = prodMapper.insertHqProdToStoreProd(prodVO);

                        // 상품분류 매장에 INSERT
                        prodMapper.insertClsHqToStore(prodVO);

                        // 매장 상품 바코드 저장(바코드정보가 있을 경우만)
                        if (prodVO.getBarCd() != null && prodVO.getBarCd().length() > 0) {
                            prodMapper.saveProdBarcdStore(prodVO);
                        }
                    }
                }

                int salePriceReeulst = prodMapper.saveSalePrice(prodVO);
                if(salePriceReeulst <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                // 상품 판매가 변경 히스토리 저장
                int hqSalePriceHistResult = prodMapper.saveSalePriceHistory(prodVO);
                if(hqSalePriceHistResult <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                // 판매가 - 가격관리구분 PRC_CTRL_FG 상관없이 매장에 수정정보 내려줌
                if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
                    String storeSalePriceReeulst = prodMapper.saveStoreSalePrice(prodVO);
                }

                // 거래처
                if (simpleProdVO.getVendrCd() != null && !"".equals(simpleProdVO.getVendrCd())) {
                    // 거래처 저장
                    simpleProdMapper.getVendorProdSaveInsert(simpleProdVO);
                }

                // 초기재고 저장
                // 상품엑셀업로드
                if (("prodExcelUpload").equals(simpleProdVO.getGubun())) {
                    if (simpleProdVO.getStartStockQty() != null && !"".equals(simpleProdVO.getStartStockQty())) {
                        if (simpleProdVO.getStartStockQty() > 0) {
                            AdjVO adjVO = new AdjVO();

                            adjVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
                            adjVO.setStoreCd(sessionInfoVO.getStoreCd());
                            adjVO.setAdjDate(currentDate);

                            String seqNo = "";
                            // 신규 seq 조회
                            if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
                                seqNo = adjMapper.getHqNewSeqNo(adjVO);
                            }
                            else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
                                seqNo = adjMapper.getStNewSeqNo(adjVO);
                            }
                            adjVO.setSeqNo(adjVO.getSeqNo());
                            adjVO.setSeqNo(Integer.parseInt(seqNo));

                            adjVO.setRegId(sessionInfoVO.getUserId());
                            adjVO.setRegDt(currentDt);
                            adjVO.setModId(sessionInfoVO.getUserId());
                            adjVO.setModDt(currentDt);

                            adjVO.setHqBrandCd("00");
                            adjVO.setProdCd(simpleProdVO.getProdCd());
                            adjVO.setCostUprc(Integer.parseInt(String.valueOf(Math.round(simpleProdVO.getCostUprc()))));
                            adjVO.setPoUnitQty(simpleProdVO.getPoUnitQty()); //주문단위-입수량
                            adjVO.setCurrQty(0); //현재고수량
                            adjVO.setAdjQty(simpleProdVO.getStartStockQty()); //조정수량
                            adjVO.setAdjAmt(0); //조정금액

                            // DTL 등록
                            if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
                                result = adjMapper.insertHqAdjDtl(adjVO);
                            }
                            else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
                                result = adjMapper.insertStAdjDtl(adjVO);
                            }
                            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                            adjVO.setAdjTitle("상품등록 후 초기재고 생성");
                            adjVO.setProcFg("0"); // 0:등록, 1:확정
                            adjVO.setAdjStorageCd("001");
                            adjVO.setStorageCd("999");

                            // HD 등록
                            if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
                                result = adjMapper.insertHqAdjHd(adjVO);
                            }
                            else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
                                result = adjMapper.insertStAdjHd(adjVO);
                            }
                            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

                            adjVO.setProcFg("1"); // 0:등록, 1:확정

                            // 확정시 확정일자,확정자 등록하려고(ProcFg 1일때만)
                           if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
                                // DTL 수정
                                result = adjMapper.updateHqAdjDtl(adjVO);
                                // HD 수정
                                result = adjMapper.updateHqAdjHd(adjVO);
                            }
                            else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
                                // DTL 수정
                                result = adjMapper.updateStAdjDtl(adjVO);
                                // HD 수정
                                result = adjMapper.updateStAdjHd(adjVO);
                            }
                            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                        }
                    }
                }

                // 저장완료된 검증결과만 삭제
                // 간편상품등록
                if(("simpleProd").equals(simpleProdVO.getGubun())) {
                    procCnt = simpleProdMapper.getSimpleProdCheckDelete(simpleProdVO);

                // 상품엑셀업로드
                } else if (("prodExcelUpload").equals(simpleProdVO.getGubun())) {
                    ProdExcelUploadVO prodExcelUploadVO = new ProdExcelUploadVO();

                    prodExcelUploadVO.setSessionId(simpleProdVO.getSessionId());
                    prodExcelUploadVO.setMembrOrgnCd(simpleProdVO.getMembrOrgnCd());
                    prodExcelUploadVO.setStoreCd(simpleProdVO.getStoreCd());

                    if(simpleProdVO.getProdNoEnv() == ProdNoEnvFg.AUTO) {
                        prodExcelUploadVO.setProdCd("자동채번");
                    } else if(simpleProdVO.getProdNoEnv() == ProdNoEnvFg.MANUAL) {
                        prodExcelUploadVO.setProdCd(simpleProdVO.getProdCd());
                    }
                    prodExcelUploadVO.setDeleteFg("검증성공");

                    procCnt = prodExcelUploadMapper.getProdExcelUploadCheckDelete(prodExcelUploadVO);
                }
            }
        }

        return procCnt;
    }
}