package kr.co.solbipos.base.prod.prodExcelUpload.service.impl;

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
import kr.co.solbipos.base.prod.prodExcelUpload.service.ProdExcelUploadService;
import kr.co.solbipos.base.prod.prodExcelUpload.service.ProdExcelUploadVO;
import kr.co.solbipos.base.prod.prod.service.ProdVO;
import kr.co.solbipos.base.prod.prod.service.impl.ProdMapper;
import kr.co.solbipos.base.prod.simpleProd.service.SimpleProdVO;
import kr.co.solbipos.base.prod.simpleProd.service.impl.SimpleProdMapper;
import kr.co.solbipos.base.prod.prod.service.enums.WorkModeFg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : ProdExcelUploadServiceImpl.java
 * @Description : 기초관리 > 상품관리 > 상품엑셀업로드
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.09.09  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2020.09.09
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("prodExcelUploadService")
@Transactional
public class ProdExcelUploadServiceImpl implements ProdExcelUploadService {
    private final ProdExcelUploadMapper prodExcelUploadMapper; // 상품엑셀업로드
    private final SimpleProdMapper simpleProdMapper; // 간편상품등록
    private final ProdMapper prodMapper; // 상품등록
    private final MessageService messageService;
    private final CmmEnvUtil cmmEnvUtil;

    /**
     * Constructor Injection
     */
    @Autowired
    public ProdExcelUploadServiceImpl(ProdExcelUploadMapper prodExcelUploadMapper, SimpleProdMapper simpleProdMapper, ProdMapper prodMapper, MessageService messageService, CmmEnvUtil cmmEnvUtil) {
        this.prodExcelUploadMapper = prodExcelUploadMapper;
        this.simpleProdMapper = simpleProdMapper;
        this.prodMapper = prodMapper;
        this.messageService = messageService;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    /** 상품분류 콤보 조회 */
    @Override
    public List<DefaultMap<String>> prodClassComboList(SessionInfoVO sessionInfoVO) {

        ProdExcelUploadVO prodExcelUploadVO = new ProdExcelUploadVO();

        prodExcelUploadVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        prodExcelUploadVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            prodExcelUploadVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return prodExcelUploadMapper.prodClassComboList(prodExcelUploadVO);
    }

    /** 검증결과 전체 삭제 */
    @Override
    public int getProdExcelUploadCheckDeleteAll(ProdExcelUploadVO prodExcelUploadVO, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;

        prodExcelUploadVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        prodExcelUploadVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            prodExcelUploadVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        prodExcelUploadVO.setSessionId(sessionInfoVO.getUserId());

        // 검증결과 삭제
        procCnt = prodExcelUploadMapper.getProdExcelUploadCheckDeleteAll(prodExcelUploadVO);

        return procCnt;
    }

    /** 검증결과 조회 */
    @Override
    public List<DefaultMap<Object>> getProdExcelUploadCheckList(ProdExcelUploadVO prodExcelUploadVO, SessionInfoVO sessionInfoVO) {

        prodExcelUploadVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        prodExcelUploadVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            prodExcelUploadVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        prodExcelUploadVO.setSessionId(sessionInfoVO.getUserId());

        return prodExcelUploadMapper.getProdExcelUploadCheckList(prodExcelUploadVO);
    }

    /** 업로드시 임시테이블 저장 */
    @Override
    public int getProdExcelUploadCheckSave(ProdExcelUploadVO[] prodExcelUploadVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        int i = 1;
        String currentDt = currentDateTimeString();

        for(ProdExcelUploadVO prodExcelUploadVO : prodExcelUploadVOs) {

            prodExcelUploadVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            prodExcelUploadVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
            prodExcelUploadVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
                prodExcelUploadVO.setStoreCd(sessionInfoVO.getStoreCd());
            }

            prodExcelUploadVO.setRegDt(currentDt);
            prodExcelUploadVO.setRegId(sessionInfoVO.getUserId());
            prodExcelUploadVO.setModDt(currentDt);
            prodExcelUploadVO.setModId(sessionInfoVO.getUserId());

            prodExcelUploadVO.setSessionId(sessionInfoVO.getUserId());
            prodExcelUploadVO.setSeq(i);

            prodExcelUploadVO.setResult("검증전");

            // <-- 업로할때는 전부 명칭으로 들어간다 -->
            // 브랜드명
            if (prodExcelUploadVO.getHqBrandCd() != null && !"".equals(prodExcelUploadVO.getHqBrandCd())) {
                String hqBrandCd = prodExcelUploadMapper.getHqBrandCdCheck(prodExcelUploadVO);
                prodExcelUploadVO.setHqBrandCd(hqBrandCd);
            }

            // 상품유형
            if (prodExcelUploadVO.getProdTypeFg() != null && !"".equals(prodExcelUploadVO.getProdTypeFg())) {
                String prodTypeFg = prodExcelUploadMapper.getProdTypeFgCheck(prodExcelUploadVO);
                prodExcelUploadVO.setProdTypeFg(prodTypeFg);
            }

            // 판매상품여부
            if (prodExcelUploadVO.getSaleProdYn() != null && !"".equals(prodExcelUploadVO.getSaleProdYn())) {
                String saleProdYn = prodExcelUploadMapper.getSaleProdYnCheck(prodExcelUploadVO);
                prodExcelUploadVO.setSaleProdYn(saleProdYn);
            }

            // 발주상품구분
            if (prodExcelUploadVO.getPoProdFg() != null && !"".equals(prodExcelUploadVO.getPoProdFg())) {
                String poProdFg = prodExcelUploadMapper.getPoProdFgCheck(prodExcelUploadVO);
                prodExcelUploadVO.setPoProdFg(poProdFg);
            }

            // 발주단위
            if (prodExcelUploadVO.getPoUnitFg() != null && !"".equals(prodExcelUploadVO.getPoUnitFg())) {
                String poUnitFg = prodExcelUploadMapper.getPoUnitFgCheck(prodExcelUploadVO);
                prodExcelUploadVO.setPoUnitFg(poUnitFg);
            }

            // 과세여부
            if (prodExcelUploadVO.getVatFg() != null && !"".equals(prodExcelUploadVO.getVatFg())) {
                String vatFg = prodExcelUploadMapper.getVatFgCheck(prodExcelUploadVO);
                prodExcelUploadVO.setVatFg(vatFg);
            }

            // 재고관리여부
            if (prodExcelUploadVO.getStockProdYn() != null && !"".equals(prodExcelUploadVO.getStockProdYn())) {
                String stockProdYn = prodExcelUploadMapper.getStockProdYnCheck(prodExcelUploadVO);
                prodExcelUploadVO.setStockProdYn(stockProdYn);
            }

            // 거래처
            if (prodExcelUploadVO.getVendrCd() != null && !"".equals(prodExcelUploadVO.getVendrCd())) {
                String vendrCd = prodExcelUploadMapper.getVendrCdCheck(prodExcelUploadVO);
                prodExcelUploadVO.setVendrCd(vendrCd);
            }

            // 상품분류
            if (prodExcelUploadVO.getProdClassCd() != null && !"".equals(prodExcelUploadVO.getProdClassCd())) {
                String prodClassCd = prodExcelUploadMapper.getProdClassCdCheck(prodExcelUploadVO);
                prodExcelUploadVO.setProdClassCd(prodClassCd);
            }

            // 가격관리구분
            if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ ) {
                // 가격관리구분
                if (prodExcelUploadVO.getPrcCtrlFg() != null && !"".equals(prodExcelUploadVO.getPrcCtrlFg())) {
                    String prcCtrlFg = prodExcelUploadMapper.getPrcCtrlFgCheck(prodExcelUploadVO);
                    prodExcelUploadVO.setPrcCtrlFg(prcCtrlFg);
                }
            } else if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ) {
                prodExcelUploadVO.setPrcCtrlFg("S");
            }
            // <-- //업로할때는 전부 명칭으로 들어간다 -->

            // 상품코드
            if (prodExcelUploadVO.getProdCd() != null && !"".equals(prodExcelUploadVO.getProdCd())) {
                if(prodExcelUploadVO.getProdCd().contains("'")) {
                    prodExcelUploadVO.setProdCd(prodExcelUploadVO.getProdCd().replaceAll("'",""));
                }
            }

            // 검증결과 저장
            procCnt = prodExcelUploadMapper.getProdExcelUploadCheckSave(prodExcelUploadVO);
            i++;
        }

        return procCnt;
    }

    /** 검증결과 저장 */
    @Override
    public int getProdExcelUploadCheckSaveAdd(ProdExcelUploadVO[] prodExcelUploadVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        int i = 1;
        String currentDt = currentDateTimeString();

        for(ProdExcelUploadVO prodExcelUploadVO : prodExcelUploadVOs) {

            prodExcelUploadVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            prodExcelUploadVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
            if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
                prodExcelUploadVO.setStoreCd(sessionInfoVO.getStoreCd());
            }

            prodExcelUploadVO.setRegDt(currentDt);
            prodExcelUploadVO.setRegId(sessionInfoVO.getUserId());
            prodExcelUploadVO.setModDt(currentDt);
            prodExcelUploadVO.setModId(sessionInfoVO.getUserId());

            prodExcelUploadVO.setSessionId(sessionInfoVO.getUserId());
            prodExcelUploadVO.setSeq(i);

            // 초기재고
            if (prodExcelUploadVO.getStartStockQty() != null && !"".equals(prodExcelUploadVO.getStartStockQty())) {
                if (prodExcelUploadVO.getStartStockQty() < 0) {
                    prodExcelUploadVO.setResult("초기재고는 0이상 입력해주세요.");
                }
                if (prodExcelUploadVO.getStartStockQty() > 99999999) {
                    prodExcelUploadVO.setStartStockQty(0);
                    prodExcelUploadVO.setResult("초기재고 길이가 너무 깁니다.");
                }
            } else {
                prodExcelUploadVO.setResult("초기재고를 입력해주세요.");
            }

            // 안전재고
            if (prodExcelUploadVO.getSafeStockQty() != null && !"".equals(prodExcelUploadVO.getSafeStockQty())) {
                if (prodExcelUploadVO.getSafeStockQty() < 0) {
                    prodExcelUploadVO.setResult("안전재고는 0이상 입력해주세요.");
                }
                if (prodExcelUploadVO.getSafeStockQty() > 999999999) {
                    prodExcelUploadVO.setSafeStockQty(0);
                    prodExcelUploadVO.setResult("안전재고 길이가 너무 깁니다.");
                }
            } else {
                prodExcelUploadVO.setResult("안전재고를 입력해주세요.");
            }

            // 원가단가 길이체크
            // 값이 있을때만
            if (prodExcelUploadVO.getCostUprc() != null && !"".equals(prodExcelUploadVO.getCostUprc())) {
                if(prodExcelUploadVO.getCostUprc() > 9999999999999999.0) {
                    prodExcelUploadVO.setCostUprc(0.0);
                    prodExcelUploadVO.setResult("원가단가 길이가 너무 깁니다.");
                }
            }

            // 재고관리여부
            if (prodExcelUploadVO.getStockProdYn() != null && !"".equals(prodExcelUploadVO.getStockProdYn())) {
            } else {
                prodExcelUploadVO.setResult("재고관리여부를 선택해주세요.");
            }

            // SimpleProdVO
            SimpleProdVO simpleProdVO = new SimpleProdVO();
            simpleProdVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            simpleProdVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
            if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
                simpleProdVO.setStoreCd(sessionInfoVO.getStoreCd());
            }
            simpleProdVO.setSessionId(sessionInfoVO.getUserId());

            // 바코드 중복체크
            // 값이 있을때만
            if (prodExcelUploadVO.getBarCd() != null && !"".equals(prodExcelUploadVO.getBarCd())) {
                // 바코드
                simpleProdVO.setBarCd(prodExcelUploadVO.getBarCd());

                int barCdCnt = simpleProdMapper.getBarCdCnt(simpleProdVO);
                if(barCdCnt > 0) {
                    prodExcelUploadVO.setResult("저장된 동일한 바코드가 존재합니다.");
                }
            }

            // 가격관리구분
            if (prodExcelUploadVO.getPrcCtrlFg() != null && !"".equals(prodExcelUploadVO.getPrcCtrlFg())) {
            } else {
                prodExcelUploadVO.setResult("가격관리구분을 선택해주세요.");
            }

            // 과세여부
            if (prodExcelUploadVO.getVatFg() != null && !"".equals(prodExcelUploadVO.getVatFg())) {
            } else {
                prodExcelUploadVO.setResult("과세여부를 선택해주세요.");
            }

            // 최소발주수량
            if (prodExcelUploadVO.getPoMinQty() != null && !"".equals(prodExcelUploadVO.getPoMinQty())) {
                if (prodExcelUploadVO.getPoMinQty() < 1) {
                    prodExcelUploadVO.setResult("최소발주수량은 1이상 입력해주세요.");
                }
                if (prodExcelUploadVO.getPoMinQty() > 999999999) {
                    prodExcelUploadVO.setPoMinQty(0);
                    prodExcelUploadVO.setResult("최소발주수량 길이가 너무 깁니다.");
                }
            } else {
                prodExcelUploadVO.setResult("최소발주수량을 입력해주세요.");
            }

            // 발주단위수량
            if (prodExcelUploadVO.getPoUnitQty() != null && !"".equals(prodExcelUploadVO.getPoUnitQty())) {
                if (prodExcelUploadVO.getPoUnitQty() < 1) {
                    prodExcelUploadVO.setResult("발주단위수량은 1이상 입력해주세요.");
                }
                if (prodExcelUploadVO.getPoUnitQty() > 999999999) {
                    prodExcelUploadVO.setPoUnitQty(0);
                    prodExcelUploadVO.setResult("발주단위수량 길이가 너무 깁니다.");
                }
            } else {
                prodExcelUploadVO.setResult("발주단위수량를 입력해주세요.");
            }

            // 발주단위
            if (prodExcelUploadVO.getPoUnitFg() != null && !"".equals(prodExcelUploadVO.getPoUnitFg())) {
                if(String.valueOf(1).equals(prodExcelUploadVO.getPoUnitFg())) {
                    // 발주단위수량
                    if (!prodExcelUploadVO.getPoUnitQty().equals(1)) {
                        prodExcelUploadVO.setResult("발주단위가 낱개인 경우 발주단위수량은 1만 입력가능합니다.");
                    }
                }
            } else {
                prodExcelUploadVO.setResult("발주단위를 입력해주세요.");
            }

            // 발주상품구분
            if (prodExcelUploadVO.getPoProdFg() != null && !"".equals(prodExcelUploadVO.getPoProdFg())) {
            } else {
                prodExcelUploadVO.setResult("발주상품구분를 선택해주세요.");
            }

            // 공급단가 길이체크
            // 값이 있을때만
            if (prodExcelUploadVO.getSplyUprc() != null && !"".equals(prodExcelUploadVO.getSplyUprc())) {
                if(prodExcelUploadVO.getSplyUprc() > 9999999999999999.0) {
                    prodExcelUploadVO.setSplyUprc(0.0);
                    prodExcelUploadVO.setResult("공급단가 길이가 너무 깁니다.");
                }
            }

            // 판매단가 길이체크
            // 값이 있을때만
            if (prodExcelUploadVO.getSaleUprc() != null && !"".equals(prodExcelUploadVO.getSaleUprc())) {
                if (prodExcelUploadVO.getSaleUprc().length() > 16) {
                    prodExcelUploadVO.setSaleUprc("");
                    prodExcelUploadVO.setResult("판매단가 길이가 너무 깁니다.");
                }
            }

            // 내점가 길이체크
            // 값이 있을때만
            if (prodExcelUploadVO.getStinSaleUprc() != null && !"".equals(prodExcelUploadVO.getStinSaleUprc())) {
                if (prodExcelUploadVO.getStinSaleUprc().length() > 16) {
                    prodExcelUploadVO.setStinSaleUprc("");
                    prodExcelUploadVO.setResult("내점가 길이가 너무 깁니다.");
                }
            }

            // 배달가 길이체크
            // 값이 있을때만
            if (prodExcelUploadVO.getDlvrSaleUprc() != null && !"".equals(prodExcelUploadVO.getDlvrSaleUprc())) {
                if (prodExcelUploadVO.getDlvrSaleUprc().length() > 16) {
                    prodExcelUploadVO.setDlvrSaleUprc("");
                    prodExcelUploadVO.setResult("배달가 길이가 너무 깁니다.");
                }
            }

            // 포장가 길이체크
            // 값이 있을때만
            if (prodExcelUploadVO.getPackSaleUprc() != null && !"".equals(prodExcelUploadVO.getPackSaleUprc())) {
                if (prodExcelUploadVO.getPackSaleUprc().length() > 16) {
                    prodExcelUploadVO.setPackSaleUprc("");
                    prodExcelUploadVO.setResult("포장가 길이가 너무 깁니다.");
                }
            }

            // 판매상품여부
            if (prodExcelUploadVO.getSaleProdYn() != null && !"".equals(prodExcelUploadVO.getSaleProdYn())) {
            } else {
                prodExcelUploadVO.setResult("판매상품여부를 선택해주세요.");
            }

            // 상품유형
            if (prodExcelUploadVO.getProdTypeFg() != null && !"".equals(prodExcelUploadVO.getProdTypeFg())) {
            } else {
                prodExcelUploadVO.setResult("상품유형를 선택해주세요.");
            }

            // 상품분류
            if (prodExcelUploadVO.getProdClassCd() != null && !"".equals(prodExcelUploadVO.getProdClassCd())) {
            } else {
                prodExcelUploadVO.setResult("상품분류를 입력해주세요.");
            }

            // 상품명 중복체크
            if(String.valueOf(true).equals(prodExcelUploadVO.getChkProdNm())) {
                // 값이 있을때만
                if (prodExcelUploadVO.getProdNm() != null && !"".equals(prodExcelUploadVO.getProdNm())) {
                    // 상품명
                    simpleProdVO.setProdNm(prodExcelUploadVO.getProdNm());

                    int prodNmCnt = simpleProdMapper.getProdNmCnt(simpleProdVO);
                    if (prodNmCnt > 0) {
                        prodExcelUploadVO.setResult("저장된 동일한 상품명이 존재합니다.");
                    }
                }
            }

            // 브랜드 등록여부 체크
            if (prodExcelUploadVO.getHqBrandCd() != null && !"".equals(prodExcelUploadVO.getHqBrandCd())) {
                simpleProdVO.setUserId(sessionInfoVO.getUserId());
                simpleProdVO.setHqBrandCd(prodExcelUploadVO.getHqBrandCd());

                int hqBrandCdChk = simpleProdMapper.getHqBrandCdChk(simpleProdVO);
                if (hqBrandCdChk < 1) {
                    prodExcelUploadVO.setResult("브랜드를 다시 선택해주세요.");
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
            if(prodExcelUploadVO.getProdNoEnv() == ProdNoEnvFg.AUTO) {
                prodExcelUploadVO.setProdCd("자동채번");
            // 수동채번인 경우 중복체크
            } else if(prodExcelUploadVO.getProdNoEnv() == ProdNoEnvFg.MANUAL) {
                // 값이 있을때만
                if (prodExcelUploadVO.getProdCd() != null && !"".equals(prodExcelUploadVO.getProdCd())) {
                    prodVO.setProdCd(prodExcelUploadVO.getProdCd());
                    int prodCdCnt = prodMapper.getProdCdCnt(prodVO);
                    if (prodCdCnt > 0) {
                        prodExcelUploadVO.setResult("저장된 동일한 상품코드가 존재합니다.");
                    }
                }
            }

            if(prodExcelUploadVO.getStoreCd() == null) { prodExcelUploadVO.setStoreCd(""); }
            if(prodExcelUploadVO.getProdCd() == null) { prodExcelUploadVO.setProdCd(""); }
            if(prodExcelUploadVO.getProdNm() == null) { prodExcelUploadVO.setProdNm(""); }
            if(prodExcelUploadVO.getSaleUprc() == null) { prodExcelUploadVO.setSaleUprc(""); }
            if(prodExcelUploadVO.getStinSaleUprc() == null) { prodExcelUploadVO.setStinSaleUprc(""); }
            if(prodExcelUploadVO.getDlvrSaleUprc() == null) { prodExcelUploadVO.setDlvrSaleUprc(""); }
            if(prodExcelUploadVO.getPackSaleUprc() == null) { prodExcelUploadVO.setPackSaleUprc(""); }
            if(prodExcelUploadVO.getSplyUprc() == null) { prodExcelUploadVO.setSplyUprc(0.0); }
            if(prodExcelUploadVO.getCostUprc() == null) { prodExcelUploadVO.setCostUprc(0.0); }
            if(prodExcelUploadVO.getBarCd() == null) { prodExcelUploadVO.setBarCd(""); }

            if (prodExcelUploadVO.getResult() == null || prodExcelUploadVO.getResult() == "") {
                prodExcelUploadVO.setResult("검증성공");
            }

            // 검증결과 저장
            procCnt = prodExcelUploadMapper.getProdExcelUploadCheckSave(prodExcelUploadVO);
            i++;
        }

        return procCnt;
    }

    /** 검증결과 삭제 */
    @Override
    public int getProdExcelUploadCheckDelete(ProdExcelUploadVO[] prodExcelUploadVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;

        for(ProdExcelUploadVO prodExcelUploadVO : prodExcelUploadVOs) {

            if (prodExcelUploadVO.getStatus() == GridDataFg.DELETE) {

                prodExcelUploadVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
                prodExcelUploadVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
                if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
                    prodExcelUploadVO.setStoreCd(sessionInfoVO.getStoreCd());
                }
                prodExcelUploadVO.setSessionId(sessionInfoVO.getUserId());

                // 검증결과 삭제
                procCnt = prodExcelUploadMapper.getProdExcelUploadCheckDelete(prodExcelUploadVO);
            }
        }

        return procCnt;
    }
}