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

import static kr.co.common.utils.DateUtil.currentDateString;
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
    private final ProdExcelUploadMapper prodExcelUploadMapper;
    private final SimpleProdMapper simpleProdMapper;
    private final ProdMapper prodMapper;
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

    /** 검증결과 전체 삭제 */
    @Override
    public int getProdExcelUploadCheckDeleteAll(ProdExcelUploadVO prodExcelUploadVO, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();

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
    public List<DefaultMap<Object>> getProdExcelUploadList(ProdExcelUploadVO prodExcelUploadVO, SessionInfoVO sessionInfoVO) {

        prodExcelUploadVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        prodExcelUploadVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            prodExcelUploadVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        prodExcelUploadVO.setSessionId(sessionInfoVO.getUserId());

        return prodExcelUploadMapper.getProdExcelUploadList(prodExcelUploadVO);
    }

    /** 업로드시 임시테이블 저장 */
    @Override
    public int getProdExcelUploadAddSave(ProdExcelUploadVO[] prodExcelUploadVOs, SessionInfoVO sessionInfoVO) {

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

            prodExcelUploadVO.setResult("검증전");

            // 검증결과 저장
            procCnt = prodExcelUploadMapper.getProdExcelUploadAddSave(prodExcelUploadVO);
            i++;
        }

        return procCnt;
    }

    /** 검증결과 저장 */
    @Override
    public int getProdExcelUploadCheckSave(ProdExcelUploadVO[] prodExcelUploadVOs, SessionInfoVO sessionInfoVO) {

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
            } else {
                prodExcelUploadVO.setResult("초기재고를 입력해주세요.");
            }

            // 안전재고
            if (prodExcelUploadVO.getSafeStockQty() != null && !"".equals(prodExcelUploadVO.getSafeStockQty())) {
                if (prodExcelUploadVO.getSafeStockQty() < 0) {
                    prodExcelUploadVO.setResult("안전재고는 0이상 입력해주세요.");
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
                // 재고관리여부 조회
                int stockProdYn = prodExcelUploadMapper.getStockProdYn(prodExcelUploadVO);
                if (stockProdYn < 1) {
                    prodExcelUploadVO.setResult("재고관리여부 값을 확인해주세요.");
                }
            } else {
                prodExcelUploadVO.setResult("재고관리여부를 입력해주세요.");
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

            // 과세여부
            if (prodExcelUploadVO.getVatFg() != null && !"".equals(prodExcelUploadVO.getVatFg())) {
                // 과세여부 조회
                int vatFg = prodExcelUploadMapper.getVatFg(prodExcelUploadVO);
                if (vatFg < 1) {
                    prodExcelUploadVO.setResult("과세여부 값을 확인해주세요.");
                }
            } else {
                prodExcelUploadVO.setResult("과세여부를 입력해주세요.");
            }

            // 최소발주수량
            if (prodExcelUploadVO.getPoMinQty() != null && !"".equals(prodExcelUploadVO.getPoMinQty())) {
                if (prodExcelUploadVO.getPoMinQty() < 1) {
                    prodExcelUploadVO.setResult("최소발주수량은 1이상 입력해주세요.");
                }
            } else {
                prodExcelUploadVO.setResult("최소발주수량을 입력해주세요.");
            }

            // 발주단위
            if (prodExcelUploadVO.getPoUnitFg() != null && !"".equals(prodExcelUploadVO.getPoUnitFg())) {
                if (prodExcelUploadVO.getPoUnitFg() < 1) {
                    prodExcelUploadVO.setResult("발주단위는 1이상 입력해주세요.");
                }
            } else {
                prodExcelUploadVO.setResult("발주단위를 입력해주세요.");
            }

            // 발주상품구분
            if (prodExcelUploadVO.getPoProdFg() != null && !"".equals(prodExcelUploadVO.getPoProdFg())) {
                // 발주상품구분 조회
                int poProdFg = prodExcelUploadMapper.getPoProdFg(prodExcelUploadVO);
                if (poProdFg < 1) {
                    prodExcelUploadVO.setResult("발주상품구분 값을 확인해주세요.");
                }
            } else {
                prodExcelUploadVO.setResult("발주상품구분를 입력해주세요.");
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

            // 판매상품여부
            if (prodExcelUploadVO.getSaleProdYn() != null && !"".equals(prodExcelUploadVO.getSaleProdYn())) {
                // 판매상품여부 조회
                int saleProdYn = prodExcelUploadMapper.getSaleProdYn(prodExcelUploadVO);
                if (saleProdYn < 1) {
                    prodExcelUploadVO.setResult("판매상품여부 값을 확인해주세요.");
                }
            } else {
                prodExcelUploadVO.setResult("판매상품여부를 입력해주세요.");
            }

            // 상품유형
            if (prodExcelUploadVO.getProdTypeFg() != null && !"".equals(prodExcelUploadVO.getProdTypeFg())) {
                // 상품유형 조회
                int prodTypeFg = prodExcelUploadMapper.getProdTypeFg(prodExcelUploadVO);
                if (prodTypeFg < 1) {
                    prodExcelUploadVO.setResult("상품유형 값을 확인해주세요.");
                }
            } else {
                prodExcelUploadVO.setResult("상품유형를 입력해주세요.");
            }

            // 상품분류
            if (prodExcelUploadVO.getProdClassCd() != null && !"".equals(prodExcelUploadVO.getProdClassCd())) {
//                int checkLevel = 0;
//                String checkProdClassNm = "";
//                String checkProdClassCd = "";
//                String fullProdClassCd = "/";
//
//                String arrProdClassCd[] = prodExcelUploadVO.getProdClassCd().split("▶");
//                for(int j=1; j < arrProdClassCd.length+1; j++) {
//                    checkLevel = j;
//                    checkProdClassNm = arrProdClassCd[j];
//
//                    // 상품분류코드 조회
//                    checkProdClassCd = prodExcelUploadMapper.getCheckProdClassCd(prodExcelUploadVO);
//                    fullProdClassCd = checkProdClassCd + "/";
//                }
//
//                // 상품분류 조회
//                int prodClassCd = prodExcelUploadMapper.getProdClassCd(prodExcelUploadVO);
//                if (prodClassCd < 1) {
//                    prodExcelUploadVO.setResult("상품분류 값을 확인해주세요.");
//                }
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


            // ProdVO
            ProdVO prodVO = new ProdVO();
            prodVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            prodVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
                prodVO.setStoreCd(sessionInfoVO.getStoreCd());
            }

            // 자동채번인 경우 상품코드 조회
            if(prodExcelUploadVO.getProdNoEnv() == ProdNoEnvFg.AUTO) {
                // 자동채번 Start
                String prodCd = prodMapper.getProdCd(prodVO);

                // 순차적으로
                if(prodExcelUploadVO.getSeq() == 1) {
                    prodExcelUploadVO.setProdCd(prodCd);
                } else {
                    // 상품코드 자동채번
                    prodCd = simpleProdMapper.getProdCd(simpleProdVO);
                    prodExcelUploadVO.setProdCd(prodCd);
                }
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
            if(prodExcelUploadVO.getSplyUprc() == null) { prodExcelUploadVO.setSplyUprc(0.0); }
            if(prodExcelUploadVO.getCostUprc() == null) { prodExcelUploadVO.setCostUprc(0.0); }
            if(prodExcelUploadVO.getBarCd() == null) { prodExcelUploadVO.setBarCd(""); }

            if (prodExcelUploadVO.getResult() == null || prodExcelUploadVO.getResult() == "") {
                prodExcelUploadVO.setResult("검증성공");
            }

            // 검증결과 저장
            procCnt = prodExcelUploadMapper.getProdExcelUploadAddSave(prodExcelUploadVO);
            i++;
        }

        return procCnt;
    }
}