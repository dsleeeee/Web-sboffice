package kr.co.solbipos.base.prod.prodExcelUpload.service.impl;

import kr.co.common.data.enums.UseYn;
import kr.co.common.service.message.MessageService;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.info.service.ProductClassVO;
import kr.co.solbipos.base.prod.info.service.impl.InfoMapper;
import kr.co.solbipos.base.prod.prod.service.enums.ProdNoEnvFg;
import kr.co.solbipos.base.prod.prodExcelUpload.service.ProdExcelUploadService;
import kr.co.solbipos.base.prod.prodExcelUpload.service.ProdExcelUploadVO;
import kr.co.solbipos.base.prod.prod.service.ProdVO;
import kr.co.solbipos.base.prod.prod.service.impl.ProdMapper;
import kr.co.solbipos.base.prod.simpleProd.service.SimpleProdVO;
import kr.co.solbipos.base.prod.simpleProd.service.impl.SimpleProdMapper;
import kr.co.solbipos.base.prod.vendr.service.VendrVO;
import kr.co.solbipos.base.prod.vendr.service.impl.VendrMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;

import java.util.List;
import java.util.regex.Pattern;

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
    private final InfoMapper infoMapper; // 상품분류등록
    private final VendrMapper vendrMapper; // 거래처등록
    private final MessageService messageService;
    private final CmmEnvUtil cmmEnvUtil;

    /**
     * Constructor Injection
     */
    @Autowired
    public ProdExcelUploadServiceImpl(ProdExcelUploadMapper prodExcelUploadMapper, SimpleProdMapper simpleProdMapper, ProdMapper prodMapper, InfoMapper infoMapper, VendrMapper vendrMapper, MessageService messageService, CmmEnvUtil cmmEnvUtil) {
        this.prodExcelUploadMapper = prodExcelUploadMapper;
        this.simpleProdMapper = simpleProdMapper;
        this.prodMapper = prodMapper;
        this.infoMapper = infoMapper;
        this.vendrMapper = vendrMapper;
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
        String pattern = "^[0-9]*$"; //숫자만

        for(ProdExcelUploadVO prodExcelUploadVO : prodExcelUploadVOs) {

            // 숫자가 입력되어야 하는 컬럼에 문자가 입력되면 null처리
            // 포장금액         prodPackAmt
            // 배달금액         prodDlvrAmt
            // 원가단가         costUprc
            // 최종원가단가     lastCostUprc
            if(prodExcelUploadVO.getCostUprc() != null && !"".equals(prodExcelUploadVO.getCostUprc())){
                if(Pattern.matches(pattern, prodExcelUploadVO.getCostUprc())){
                    prodExcelUploadVO.setCostUprcD(Double.parseDouble(prodExcelUploadVO.getCostUprc()));
                }
            }
            // 공급단가         splyUprc
            if(prodExcelUploadVO.getSplyUprc() != null && !"".equals(prodExcelUploadVO.getSplyUprc())){
                if (Pattern.matches(pattern, prodExcelUploadVO.getSplyUprc())) {
                    prodExcelUploadVO.setSplyUprcD(Double.parseDouble(prodExcelUploadVO.getSplyUprc()));
                }
            }
            // 발주단위수량       poUnitQty
            if(prodExcelUploadVO.getPoUnitQty() != null && !"".equals(prodExcelUploadVO.getPoUnitQty())){
                if (Pattern.matches(pattern, prodExcelUploadVO.getPoUnitQty())) {
                    prodExcelUploadVO.setPoUnitQtyI(Integer.parseInt(prodExcelUploadVO.getPoUnitQty()));
                }
            }
            // 발주최소수량       poMinQty
            if(prodExcelUploadVO.getPoMinQty() != null && !"".equals(prodExcelUploadVO.getPoMinQty())){
                if (Pattern.matches(pattern, prodExcelUploadVO.getPoMinQty())) {
                    prodExcelUploadVO.setPoMinQtyI(Integer.parseInt(prodExcelUploadVO.getPoMinQty()));
                }
            }
            // 안전재고수량       safeStockQty
            if(prodExcelUploadVO.getSafeStockQty() != null && !"".equals(prodExcelUploadVO.getSafeStockQty())){
                if (Pattern.matches(pattern, prodExcelUploadVO.getSafeStockQty())) {
                    prodExcelUploadVO.setSafeStockQtyI(Integer.parseInt(prodExcelUploadVO.getSafeStockQty()));
                }
            }
            // 판매단가         saleUprc - 자료형이 String이라 VO에서 에러는 안 나지만 문자가 포함 된 경우 0으로 치환
            if(prodExcelUploadVO.getSaleUprc() != null && !"".equals(prodExcelUploadVO.getSaleUprc())){
                if (!Pattern.matches(pattern, prodExcelUploadVO.getSaleUprc())) {
                    prodExcelUploadVO.setSaleUprc("0");
                }
            }
            // 초기재고수량     startStockQty
            if(prodExcelUploadVO.getStartStockQty() != null && !"".equals(prodExcelUploadVO.getStartStockQty())){
                if (Pattern.matches(pattern, prodExcelUploadVO.getStartStockQty())) {
                    prodExcelUploadVO.setStartStockQtyI(Integer.parseInt(prodExcelUploadVO.getStartStockQty()));
                }
            }
            // 판매단가-내점      stinSaleUprc
            if(prodExcelUploadVO.getStinSaleUprc() != null && !"".equals(prodExcelUploadVO.getStinSaleUprc())){
                if (!Pattern.matches(pattern, prodExcelUploadVO.getStinSaleUprc())) {
                    prodExcelUploadVO.setStinSaleUprc(null);
                }
            }
            // 판매단가-배달      dlvrSaleUprc
            if(prodExcelUploadVO.getDlvrSaleUprc() != null && !"".equals(prodExcelUploadVO.getDlvrSaleUprc())){
                if (!Pattern.matches(pattern, prodExcelUploadVO.getDlvrSaleUprc())) {
                    prodExcelUploadVO.setDlvrSaleUprc(null);
                }
            }
            // 판매단가-포장      packSaleUprc
            if(prodExcelUploadVO.getPackSaleUprc() != null && !"".equals(prodExcelUploadVO.getPackSaleUprc())){
                if (!Pattern.matches(pattern, prodExcelUploadVO.getPackSaleUprc())) {
                    prodExcelUploadVO.setPackSaleUprc(null);
                }
            }

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

            // 보증금상품유형
            if(prodExcelUploadVO.getDepositCupFg() != null && !"".equals(prodExcelUploadVO.getDepositCupFg())){

                DefaultMap<String> depositCupFgMap = new DefaultMap<>();
                depositCupFgMap.put("선택", "");
                depositCupFgMap.put("종이", "1");
                depositCupFgMap.put("플라스틱", "2");
                depositCupFgMap.put("다회용", "3");
                depositCupFgMap.put("보증컵기타", "4");

                String depositCupFg = (String)depositCupFgMap.get(prodExcelUploadVO.getDepositCupFg());

                prodExcelUploadVO.setDepositCupFg(depositCupFg);
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
                String startStockQty = prodExcelUploadVO.getStartStockQty().replace(",", "");
                if (startStockQty.length() > 8) { //99999999
                    startStockQty = "0";
                    prodExcelUploadVO.setResult("초기재고 길이가 너무 깁니다.");
                } else if (Integer.parseInt(startStockQty) < 0) {
                    prodExcelUploadVO.setResult("초기재고는 0이상 입력해주세요.");
                }
                prodExcelUploadVO.setStartStockQtyI(Integer.parseInt(startStockQty));
            } else {
                prodExcelUploadVO.setResult("초기재고를 입력해주세요.");
            }

            // 안전재고
            if (prodExcelUploadVO.getSafeStockQty() != null && !"".equals(prodExcelUploadVO.getSafeStockQty())) {
                String safeStockQty = prodExcelUploadVO.getSafeStockQty().replace(",", "");
                if (safeStockQty.length() > 9) {    //999999999
                    safeStockQty = "0";
                    prodExcelUploadVO.setResult("안전재고 길이가 너무 깁니다.");
                } else if (Integer.parseInt(safeStockQty) < 0) {
                    prodExcelUploadVO.setResult("안전재고는 0이상 입력해주세요.");
                }
                prodExcelUploadVO.setSafeStockQtyI(Integer.parseInt(safeStockQty));
            } else {
                prodExcelUploadVO.setResult("안전재고를 입력해주세요.");
            }

            // 원가단가 길이체크
            // 값이 있을때만
            if (prodExcelUploadVO.getCostUprc() != null && !"".equals(prodExcelUploadVO.getCostUprc())) {
                if(Double.parseDouble(prodExcelUploadVO.getCostUprc()) > 9999999999999999.0) {
                    prodExcelUploadVO.setCostUprc("0.0");
                    prodExcelUploadVO.setResult("원가단가 길이가 너무 깁니다.");
                }
                prodExcelUploadVO.setCostUprcD(Double.parseDouble(prodExcelUploadVO.getCostUprc()));
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
                String poMinQty = prodExcelUploadVO.getPoMinQty().replace(",", "");
                if (poMinQty.length() > 9) { //999999999
                    poMinQty = "0";
                    prodExcelUploadVO.setResult("최소발주수량 길이가 너무 깁니다.");
                } else if (Integer.parseInt(poMinQty) < 1) {
                    prodExcelUploadVO.setResult("최소발주수량은 1이상 입력해주세요.");
                }
                prodExcelUploadVO.setPoMinQtyI(Integer.parseInt(poMinQty));
            } else {
                prodExcelUploadVO.setResult("최소발주수량을 입력해주세요.");
            }

            // 발주단위수량
            if (prodExcelUploadVO.getPoUnitQty() != null && !"".equals(prodExcelUploadVO.getPoUnitQty())) {
                String poUnitQty = prodExcelUploadVO.getPoUnitQty().replace(",", "");
                if (poUnitQty.length() > 9) { //999999999
                    poUnitQty = "0";
                    prodExcelUploadVO.setResult("발주단위수량 길이가 너무 깁니다.");
                } else if (Integer.parseInt(poUnitQty) < 1) {
                    prodExcelUploadVO.setResult("발주단위수량은 1이상 입력해주세요.");
                }
                prodExcelUploadVO.setPoUnitQtyI(Integer.parseInt(poUnitQty));
            } else {
                prodExcelUploadVO.setResult("발주단위수량를 입력해주세요.");
            }

            // 발주단위
            if (prodExcelUploadVO.getPoUnitFg() != null && !"".equals(prodExcelUploadVO.getPoUnitFg())) {
                if(String.valueOf(1).equals(prodExcelUploadVO.getPoUnitFg())) {
                    // 발주단위수량
                    if (prodExcelUploadVO.getPoUnitQtyI() != null && !"".equals(prodExcelUploadVO.getPoUnitQtyI())) {
                        // 발주단위수량
                        if (prodExcelUploadVO.getPoUnitQtyI() != 1) {
                            prodExcelUploadVO.setResult("발주단위가 낱개인 경우 발주단위수량은 1만 입력가능합니다.");
                        }
                    } else {
                        prodExcelUploadVO.setResult("발주단위수량를 입력해주세요.");
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
                if(Double.parseDouble(prodExcelUploadVO.getSplyUprc()) > 9999999999999999.0) {
                    prodExcelUploadVO.setSplyUprc("0.0");
                    prodExcelUploadVO.setResult("공급단가 길이가 너무 깁니다.");
                }
                prodExcelUploadVO.setSplyUprcD(Double.parseDouble(prodExcelUploadVO.getSplyUprc()));
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
                // 상품유형이 [보증금상품]이면 강제로 면세처리
                if(prodExcelUploadVO.getProdTypeFg().equals("4")){
                    prodExcelUploadVO.setVatFg("2");
                }
            } else {
                prodExcelUploadVO.setResult("상품유형를 선택해주세요.");
            }

            // 상품분류
            if (prodExcelUploadVO.getProdClassCd() != null && !"".equals(prodExcelUploadVO.getProdClassCd()) && !"선택".equals(prodExcelUploadVO.getProdClassCd())) {
                String prodClassList = prodExcelUploadMapper.prodClassComboList(prodExcelUploadVO).toString();

                if(!prodClassList.contains(prodExcelUploadVO.getProdClassCd())){
                    prodExcelUploadVO.setResult("존재하지 않는 상품분류입니다.");
                } else {
                    String prodClassCd = prodExcelUploadMapper.getProdClassCdCheck(prodExcelUploadVO);
                    prodExcelUploadVO.setProdClassCd(prodClassCd);
                }
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

            if (prodExcelUploadVO.getBrandUseFg() == "1") {
                // 브랜드 등록여부 체크
                if (prodExcelUploadVO.getHqBrandCd() != null && !"".equals(prodExcelUploadVO.getHqBrandCd())) {
                    simpleProdVO.setUserId(sessionInfoVO.getUserId());
                    simpleProdVO.setHqBrandCd(prodExcelUploadVO.getHqBrandCd());

                    int hqBrandCdChk = simpleProdMapper.getHqBrandCdChk(simpleProdVO);
                    if (hqBrandCdChk < 1) {
                        prodExcelUploadVO.setResult("브랜드를 다시 선택해주세요.");
                    }
                }
            }

            // 거래처
            if (prodExcelUploadVO.getVendrCd() != null && !"".equals(prodExcelUploadVO.getVendrCd()) && !"선택".equals(prodExcelUploadVO.getVendrCd())) {
                String vendrList = simpleProdMapper.vendrComboList(simpleProdVO).toString();
                if(!vendrList.contains(prodExcelUploadVO.getVendrCd())){
                    prodExcelUploadVO.setResult("존재하지 않는 거래처입니다.");
                } else {
                    String vendrCd = prodExcelUploadMapper.getVendrCdCheck(prodExcelUploadVO);
                    prodExcelUploadVO.setVendrCd(vendrCd);
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
            if(prodExcelUploadVO.getSplyUprc() == null) { prodExcelUploadVO.setSplyUprcD(0.0); }
            if(prodExcelUploadVO.getCostUprc() == null) { prodExcelUploadVO.setCostUprcD(0.0); }
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

    /**  기초 마스터 체크  */
    @Override
    public DefaultMap<Object> getMasterChk(ProdExcelUploadVO prodExcelUploadVO, SessionInfoVO sessionInfoVO) {

        prodExcelUploadVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        prodExcelUploadVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE){
            prodExcelUploadVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        prodExcelUploadVO.setSessionId(sessionInfoVO.getUserId());

        return prodExcelUploadMapper.getMasterChk(prodExcelUploadVO);
    }

    /**  기초 마스터 등록 - 상품분류  */
    @Override
    public List<DefaultMap<String>> getProdClassCdInsertList(ProdExcelUploadVO prodExcelUploadVO, SessionInfoVO sessionInfoVO) {

        prodExcelUploadVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE){
            prodExcelUploadVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        prodExcelUploadVO.setSessionId(sessionInfoVO.getUserId());

        return prodExcelUploadMapper.getProdClassCdInsertList(prodExcelUploadVO);
    }

    @Override
    public int prodClassCdSave(ProductClassVO[] productClassVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;
        String dt = currentDateTimeString();

        for( ProductClassVO productClassVO : productClassVOs) {

            if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {   // 본사
                productClassVO.setHqOfficeCd(sessionInfoVO.getOrgnCd());
            }
            else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {    // 매장
                productClassVO.setStoreCd(sessionInfoVO.getOrgnCd());
            }

            productClassVO.setRegDt(dt);
            productClassVO.setRegId(sessionInfoVO.getUserId());
            productClassVO.setModDt(dt);
            productClassVO.setModId(sessionInfoVO.getUserId());
            productClassVO.setOrgnFg(sessionInfoVO.getOrgnFg());

            String prodClassCd = "";
            prodClassCd = infoMapper.getClsCd(productClassVO);
            productClassVO.setProdClassCd(prodClassCd);

            if(!productClassVO.getClsLevelCd().equals("1")){
                String pProdClassCd = "";
                pProdClassCd = infoMapper.getPProdClsCd2(productClassVO);
                productClassVO.setpProdClassCd(pProdClassCd);
            }

            result += infoMapper.insertCls(productClassVO);
            // 본사에서 접속시
            if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
                infoMapper.insertClsToStore(productClassVO);
            }
        }
        return result;
    }

    /**  기초 마스터 등록 - 거래처  */
    @Override
    public List<DefaultMap<String>> getVendrCdInsertList(ProdExcelUploadVO prodExcelUploadVO, SessionInfoVO sessionInfoVO) {

        prodExcelUploadVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE){
            prodExcelUploadVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        prodExcelUploadVO.setSessionId(sessionInfoVO.getUserId());

        return prodExcelUploadMapper.getVendrCdInsertList(prodExcelUploadVO);
    }

    /**  기초 마스터 등록 - 거래처 저장  */
    @Override
    public int vendrCdSave(VendrVO[] vendrVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;
        String dt = currentDateTimeString();

        for( VendrVO vendrVO : vendrVOs) {

            vendrVO.setRegDt(dt);
            vendrVO.setRegId(sessionInfoVO.getUserId());
            vendrVO.setModDt(dt);
            vendrVO.setModId(sessionInfoVO.getUserId());

            vendrVO.setUseYn(UseYn.Y);
            vendrVO.setShipFg("Y");

            if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
                vendrVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

                // 신규등록일때 거래처코드(본사 자동채번)
                String vendrCd = vendrMapper.getHqVendrCd(vendrVO);
                vendrVO.setVendrCd(vendrCd);

                result = vendrMapper.insertHqVendr(vendrVO);

            } else if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
                //매장코드
                vendrVO.setStoreCd(sessionInfoVO.getStoreCd());

                // 신규등록일때 거래처코드(매장 자동채번)
                String vendrCd = vendrMapper.getMsVendrCd(vendrVO);
                vendrVO.setVendrCd(vendrCd);

                result = vendrMapper.insertMsVendr(vendrVO);
            }
        }
        return result;
    }
}