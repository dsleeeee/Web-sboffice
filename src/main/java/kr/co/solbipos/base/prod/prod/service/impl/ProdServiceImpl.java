package kr.co.solbipos.base.prod.prod.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.system.BaseEnv;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.prod.service.ProdService;
import kr.co.solbipos.base.prod.prod.service.ProdVO;
import kr.co.solbipos.base.prod.prod.service.enums.ProdNoEnvFg;
import kr.co.solbipos.base.prod.prod.service.enums.WorkModeFg;
import kr.co.solbipos.base.prod.prodBarcd.service.ProdBarcdVO;
import kr.co.solbipos.base.prod.prodBarcd.service.impl.ProdBarcdMapper;
import kr.co.solbipos.stock.adj.adj.service.AdjVO;
import kr.co.solbipos.stock.adj.adj.service.impl.AdjMapper;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateString;
import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : ProdServiceImpl.java
 * @Description : 기초관리 - 상품관리 - 상품조회
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.08.06  장혁수       최초생성
 * @ 2018.10.19  노현수       생성자 주입, 상품조회 관련 변경
 * @ 2019.06.03  이다솜       saveProductInfo 수정 (WorkMode 추가)
 * @ 2020.08.10  김설아       saveProductInfo 수정 (return : 상품코드 / int -> long -> String)
 *                            getProdImageFileSave 추가 (상품 이미지 업로드)
 * @ 2020.12.16  김설아       saveProductInfo 수정 (초기재고, 거래처 추가)
 *
 * @author NHN한국사이버결제 KCP 장혁수
 * @since 2018. 08.06
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("prodService")
public class ProdServiceImpl implements ProdService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final MessageService messageService;
    private final ProdMapper prodMapper;
    private final CmmEnvUtil cmmEnvUtil;
    private final AdjMapper adjMapper; // 조정관리

    /** Constructor Injection */
    @Autowired
    public ProdServiceImpl(ProdMapper prodMapper, CmmEnvUtil cmmEnvUtil, MessageService messageService, AdjMapper adjMapper) {
        this.prodMapper = prodMapper;
        this.cmmEnvUtil = cmmEnvUtil;
        this.messageService = messageService;
        this.adjMapper = adjMapper;
    }

    /** 상품목록 조회 */
    @Override
    public List<DefaultMap<String>> getProdList(@RequestBody ProdVO prodVO, SessionInfoVO sessionInfoVO) {

        String orgnFg = sessionInfoVO.getOrgnFg().getCode();
        String hqOfficeCd = sessionInfoVO.getHqOfficeCd();
        String storeCd = sessionInfoVO.getStoreCd();

        // 소속구분 설정
        prodVO.setOrgnFg(orgnFg);
        prodVO.setHqOfficeCd(hqOfficeCd);
        prodVO.setStoreCd(storeCd);

        /*
          단독매장의 경우 SALE_PRC_FG = '2'
          프랜차이즈의 경우, 상품 판매가 본사통제여부 조회하여
          본사통제구분이 '본사'일때, SALE_PRC_FG = '1'
          본사통제구분이 '매장'일때, SALE_PRC_FG = '2'
        */
//        if("00000".equals(hqOfficeCd)) { // 단독매장
//            prodVO.setSalePrcFg("2");
//        } else {
//
//            String envstVal = StringUtil.getOrBlank(cmmEnvUtil.getHqEnvst(sessionInfoVO, "0022"));
//
//            if( StringUtil.isEmpties(storeCd)) { // 본사일때
//                prodVO.setSalePrcFg("1");
//            } else {                             // 매장일때
//                if("1".equals(envstVal)) prodVO.setSalePrcFg("1");
//                else                     prodVO.setSalePrcFg("2");
//            }
//        }

        prodVO.setUserId(sessionInfoVO.getUserId());

        return prodMapper.getProdList(prodVO);
    }

    /** 상품목록 조회(엑셀다운로드용) */
    @Override
    public List<DefaultMap<String>> getProdExcelList(@RequestBody ProdVO prodVO, SessionInfoVO sessionInfoVO) {

        String orgnFg = sessionInfoVO.getOrgnFg().getCode();
        String hqOfficeCd = sessionInfoVO.getHqOfficeCd();
        String storeCd = sessionInfoVO.getStoreCd();

        // 소속구분 설정
        prodVO.setOrgnFg(orgnFg);
        prodVO.setHqOfficeCd(hqOfficeCd);
        prodVO.setStoreCd(storeCd);

        if (prodVO.getExcelGubun().equals("T")) { // 전체 엑셀다운로드시(T) 조회조건 날림
            prodVO.setProdCd(null);
            prodVO.setProdNm(null);
            prodVO.setProdClassCd(null);
            prodVO.setBarCd(null);
            prodVO.setUseYn(null);
            prodVO.setHqBrandNm(null);
        }

        /*
          단독매장의 경우 SALE_PRC_FG = '2'
          프랜차이즈의 경우, 상품 판매가 본사통제여부 조회하여
          본사통제구분이 '본사'일때, SALE_PRC_FG = '1'
          본사통제구분이 '매장'일때, SALE_PRC_FG = '2'
        */
//        if("00000".equals(hqOfficeCd)) { // 단독매장
//            prodVO.setSalePrcFg("2");
//        } else {
//
//            String envstVal = StringUtil.getOrBlank(cmmEnvUtil.getHqEnvst(sessionInfoVO, "0022"));
//
//            if( StringUtil.isEmpties(storeCd)) { // 본사일때
//                prodVO.setSalePrcFg("1");
//            } else {                             // 매장일때
//                if("1".equals(envstVal)) prodVO.setSalePrcFg("1");
//                else                     prodVO.setSalePrcFg("2");
//            }
//        }

        prodVO.setUserId(sessionInfoVO.getUserId());

        return prodMapper.getProdExcelList(prodVO);
    }


    /** 상품 상세정보 조회 */
    @Override
    public DefaultMap<String> getProdDetail(ProdVO prodVO, SessionInfoVO sessionInfoVO) {

        DefaultMap result = new DefaultMap<>();

        String orgnFg = sessionInfoVO.getOrgnFg().getCode();
        String hqOfficeCd = sessionInfoVO.getHqOfficeCd();
        String storeCd = sessionInfoVO.getStoreCd();

        // 소속구분 설정
        prodVO.setOrgnFg(orgnFg);
        prodVO.setHqOfficeCd(hqOfficeCd);
        prodVO.setStoreCd(storeCd);

        /*
          단독매장의 경우 SALE_PRC_FG = '2'
          프랜차이즈의 경우, 상품 판매가 본사통제여부 조회하여
          본사통제구분이 '본사'일때, SALE_PRC_FG = '1'
          본사통제구분이 '매장'일때, SALE_PRC_FG = '2'
        */
//        if("00000".equals(hqOfficeCd)) { // 단독매장
//            prodVO.setSalePrcFg("2");
//        } else {
//
//            String envstVal = StringUtil.getOrBlank(cmmEnvUtil.getHqEnvst(sessionInfoVO, "0022"));
//
//            if( StringUtil.isEmpties(storeCd)) { // 본사일때
//                prodVO.setSalePrcFg("1");
//            } else {                             // 매장일때
//                if("1".equals(envstVal)) prodVO.setSalePrcFg("1");
//                else                     prodVO.setSalePrcFg("2");
//            }
//        }

        // 상품상세정보 조회
        result = prodMapper.getProdDetail(prodVO);

        // 연결상품목록 조회
        List<DefaultMap<String>> linkedProdList = prodMapper.getLinkedProdList(prodVO);
        result.put("linkedProdList", linkedProdList);

        return result;
    }

    /** 상품정보 저장 */
    @Override
//    public int saveProductInfo(ProdVO prodVO, SessionInfoVO sessionInfoVO) {
//    public long saveProductInfo(ProdVO prodVO, SessionInfoVO sessionInfoVO) {
    public String saveProductInfo(ProdVO prodVO, SessionInfoVO sessionInfoVO) {

        String currentDt = currentDateTimeString();
        String currentDate = currentDateString();

        // 소속구분 설정
        String orgnFg = sessionInfoVO.getOrgnFg().getCode();
        prodVO.setOrgnFg(orgnFg);
        prodVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        prodVO.setStoreCd(sessionInfoVO.getStoreCd());

        prodVO.setRegDt(currentDt);
        prodVO.setModDt(currentDt);
        prodVO.setRegId(sessionInfoVO.getUserId());
        prodVO.setModId(sessionInfoVO.getUserId());

        // 상품등록 본사 통제여부
//        ProdEnvFg prodEnvstVal = ProdEnvFg.getEnum(cmmEnvUtil.getHqEnvst(sessionInfoVO, "0020"));
        // 판매가 본사 통제여부
//        PriceEnvFg priceEnvstVal = PriceEnvFg.getEnum(cmmEnvUtil.getHqEnvst(sessionInfoVO, "0022"));
        // 본사신규상품매장생성
//        HqProdEnvFg hqProdEnvstVal = HqProdEnvFg.getEnum(cmmEnvUtil.getHqEnvst(sessionInfoVO, "0043"));

        // 상품 신규등록일때 상품코드 조회
        /*if( StringUtil.isEmpties( prodVO.getProdCd())) {
            String prodCd = prodMapper.getProdCd(prodVO);
            prodVO.setProdCd(prodCd);
            // 신규상품등록 인 경우 WorkMode Flag 변경_2019.06.06
            prodVO.setWorkMode(WorkModeFg.REG_PROD);
        }*/

        // 상품 신규등록이면서 자동채번인 경우 상품코드 조회
        if(prodVO.getProdNoEnv() == ProdNoEnvFg.AUTO && prodVO.getSaveMode().equals("REG")){

            String prodCd = "";

            // 프랜차이즈 매장의 경우, 본사 ([0002] 매장상품 prefix) 사용여부를 파악하여 상품코드를 생성
            if(prodVO.getOrgnFg() == "S" && prodVO.getHqOfficeCd() != "00000"){

                String sPrefix = CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "0002"), "");

                // prefix 미사용시
                if("*".equals(sPrefix) || "".equals(sPrefix)){
                    prodCd = prodMapper.getProdCd(prodVO);
                }else{
                    // prefix 사용시
                    prodVO.setPrefix(sPrefix);
                    prodCd = prodMapper.getPrefixProdCd(prodVO);
                }

            }else{
                prodCd = prodMapper.getProdCd(prodVO);
            }

            prodVO.setProdCd(prodCd);
        }

        // WorkMode Flag 상품정보수정으로 기본 셋팅_2019.06.06
        prodVO.setWorkMode(WorkModeFg.MOD_PROD);

        // 신규상품등록 인 경우 WorkMode Flag 변경
        if(prodVO.getSaveMode().equals("REG")){
            // 신규상품등록 인 경우 WorkMode Flag 변경_2019.06.06
            prodVO.setWorkMode(WorkModeFg.REG_PROD);
        }
        // 상품코드 체크
        if(prodVO.getProdCd() == "" || prodVO.getProdCd() == null){
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }

        int prodExist = 0;

        // 본사일경우, 상품정보 존재여부를 체크하여 프로시져 호출에 사용
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ ) {
            prodExist = prodMapper.getProdExistInfo(prodVO);
        }

        // 매장에서 매장상품 등록시에 가격관리 구분 등록 -> 콤보박스 처리
//        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ)  prodVO.setPrcCtrlFg("H"); //본사
//        else                                        prodVO.setPrcCtrlFg("S"); //매장

        // 상품정보 저장
//        int result = prodMapper.saveProductInfo(prodVO);
        long result = prodMapper.saveProductInfo(prodVO);
        if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        //상품 바코드 저장(바코드정보가 있을 경우만)
        if(prodVO.getBarCd() != null && prodVO.getBarCd().length() > 0){
            if(prodVO.getBarCd().equals("자동생성")){
                prodVO.setBarCd(prodVO.getProdCd() + String.format("%02d", (int)(Math.random()*100)));
            }

            int barCdResult = prodMapper.saveProdBarcd(prodVO);
            if(barCdResult <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
        else {
            prodMapper.deleteProdBarcd(prodVO);
        }

        // 상품정보 추가 테이블 조회
        String prodInfoRowCount = prodMapper.getProdInfoRowCount(prodVO);
        if(prodInfoRowCount.equals("0")) {
            if( (prodVO.getProdInfo() != null && prodVO.getProdInfo().length() > 0)
                    || (prodVO.getNuTotWt() != null && prodVO.getNuTotWt().length() > 0)
                    || (prodVO.getNuKcal() != null && prodVO.getNuKcal().length() > 0)
                    || (prodVO.getNuProtein() != null && prodVO.getNuProtein().length() > 0)
                    || (prodVO.getNuSodium() != null && prodVO.getNuSodium().length() > 0)
                    || (prodVO.getNuSugars() != null && prodVO.getNuSugars().length() > 0)
                    || (prodVO.getNuSatFat() != null && prodVO.getNuSatFat().length() > 0)
                    || (prodVO.getNuCaffeine() != null && prodVO.getNuCaffeine().length() > 0)
                    || (prodVO.getMomsKioskEdge() != null && prodVO.getMomsKioskEdge().length() > 0 && Integer.parseInt(prodVO.getMomsKioskEdge()) > 0) )
            {
                // 상품 설명 상세 + 영양정보
                prodMapper.saveProdInfo(prodVO);
            }
        } else {
            // 상품 설명 상세 + 영양정보
            prodMapper.saveProdInfo(prodVO);
        }

        // KIOSK 판매여부
        if(prodVO.getSaleTimeFg() != null && "Y".equals(prodVO.getSaleTimeFg())){
            if(prodVO.getSaleTime() != null && prodVO.getSaleTime().length() > 0){

                String[] arrSaleTime = prodVO.getSaleTime().split(",");

                if (arrSaleTime.length > 0) {

                    // 기존 KIOSK 판매시간 시간설정 삭제
                    prodMapper.deleteProdSaleTime(prodVO);

                    for(int i=0; i < arrSaleTime.length; i++) {

                        int iSeq = i+1;
                        ProdVO stProdVo = new ProdVO();
                        stProdVo.setOrgnFg(orgnFg);
                        stProdVo.setHqOfficeCd(prodVO.getHqOfficeCd());
                        stProdVo.setStoreCd(prodVO.getStoreCd());
                        stProdVo.setProdCd(prodVO.getProdCd());
                        stProdVo.setTimeSeq(iSeq);
                        stProdVo.setIncludeFg("1");
                        stProdVo.setsSaleTime(arrSaleTime[i].split("-")[0]);
                        stProdVo.seteSaleTime(arrSaleTime[i].split("-")[1]);
                        stProdVo.setRegDt(currentDt);
                        stProdVo.setModDt(currentDt);
                        stProdVo.setRegId(sessionInfoVO.getUserId());
                        stProdVo.setModId(sessionInfoVO.getUserId());

                        // KIOSK 판매시간 시간설정 등록
                        prodMapper.insertProdSaleTime(stProdVo);
                    }
                }
            }
        }else{

            // KIOSK 판매시간 시간설정 삭제
            prodMapper.deleteProdSaleTime(prodVO);
        }

        // [상품등록 - 본사통제시] 본사에서 상품정보 수정시 매장에 수정정보 내려줌
//        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ  && prodEnvstVal == ProdEnvFg.HQ) {
        // 본사신규상품매장생성 [0 전매장]일 경우 매장에 수정정보 내려줌
//        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ  && hqProdEnvstVal == HqProdEnvFg.ALL) {
        // 본사인 경우 매장에 수정정보 내려줌
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ ) {
            //[본사신규상품매장생성]이 [0 자동생성]일 경우 매장에 수정정보 내려줌 -> 오류 2022.04.08 신규상품생성여부임, 수정정보는 내려줌
            String procResult;

            if (prodExist == 0 && CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "0043") , "0").equals("0")) {
                // 상품정보 매장에 INSERT
                procResult = prodMapper.insertHqProdToStoreProd(prodVO);
                // 상품분류 매장에 INSERT
                prodMapper.insertClsHqToStore(prodVO);
            } else {
                // 상품정보 매장에 UPDATE
                procResult = prodMapper.updateHqProdToStoreProd(prodVO);
                // 상품분류 매장에 UPDATE
                prodMapper.updateClsHqToStore(prodVO);
            }

            //매장 상품 바코드 저장(바코드정보가 있을 경우만)
            if (prodVO.getBarCd() != null && prodVO.getBarCd().length() > 0) {
                prodMapper.saveProdBarcdStore(prodVO);
            } else {
                prodMapper.deleteProdBarcdStore(prodVO);
            }

//            if(prodVO.getProdInfo() != null && prodVO.getProdInfo().length() > 0){
//                prodMapper.saveProdInfo(prodVO);
//            }

            //매장 KIOSK 판매시간 시간설정 저장(KIOSK 판매시간을 사용하는 경우만)
            if(prodVO.getSaleTimeFg() != null && "Y".equals(prodVO.getSaleTimeFg())){
                prodMapper.deleteStoreProdSaleTime(prodVO);
                prodMapper.insertStoreProdSaleTime(prodVO);
            }else{
                prodMapper.deleteStoreProdSaleTime(prodVO);
            }

            // 매장 사이드 선택메뉴 그룹/분류/상품 저장(사이드 선택메뉴를 사용하는 경우만)
            if ("Y".equals(prodVO.getSideProdYn()) && prodVO.getSdselGrpCd() != null && prodVO.getSdselGrpCd().length() > 0) {
                //그룹(sdselGrp)
                prodMapper.insertSdselGrpToStore(prodVO);
                //분류(sdselClass)
                prodMapper.insertSdselClassToStore(prodVO);
                //상품(sdselProd)
                prodMapper.insertSdselProdToStore(prodVO);
            }
        }

        // 상품 판매가 저장
//        if(priceEnvstVal == PriceEnvFg.HQ)  prodVO.setSalePrcFg("1");
//        else                                prodVO.setSalePrcFg("2");

        prodVO.setStartDate(currentDate);
        prodVO.setEndDate("99991231");

        int salePriceReeulst = prodMapper.saveSalePrice(prodVO);
        if(salePriceReeulst <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        // 상품 판매가 변경 히스토리 저장
        int hqSalePriceHistResult = prodMapper.saveSalePriceHistory(prodVO);
        if(hqSalePriceHistResult <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        // [판매가 - 본사통제시] 본사에서 상품정보 수정시 매장에 수정정보 내려줌
//        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ  && priceEnvstVal == PriceEnvFg.HQ) {
        // 판매가 - 본사이면서 가격관리구분 PRC_CTRL_FG이 본사인경우 매장에 수정정보 내려줌
//        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ  && prodVO.getPrcCtrlFg().equals("H")) {
//            String storeSalePriceReeulst = prodMapper.saveStoreSalePrice(prodVO);
//        }

        // 본사일때
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 신규
            if(prodVO.getSaveMode().equals("REG")) {
                // 판매가 - 가격관리구분 PRC_CTRL_FG 상관없이 매장에 수정정보 내려줌
                String storeSalePriceReeulst = prodMapper.saveStoreSalePrice(prodVO);
            // 수정
            } else {
                // 본사
                if(prodVO.getPrcCtrlFg().equals("H")) {
                    // 판매가 - 가격관리구분 PRC_CTRL_FG이 본사인경우 매장에 수정정보 내려줌
                    String storeSalePriceReeulst = prodMapper.saveStoreSalePrice(prodVO);
                }
            }
        }

        // 초기재고
        if (prodVO.getStartStockQty() != null && !"".equals(prodVO.getStartStockQty())) {
            if (prodVO.getStartStockQty() > 0) {
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
                adjVO.setProdCd(prodVO.getProdCd());
                adjVO.setCostUprc(Integer.parseInt(String.valueOf(Math.round(prodVO.getCostUprc()))));
                adjVO.setPoUnitQty(prodVO.getPoUnitQty()); //주문단위-입수량
                adjVO.setCurrQty(0); //현재고수량
                adjVO.setAdjQty(prodVO.getStartStockQty()); //조정수량
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

        // 거래처 삭제
        prodMapper.getVendorProdSaveUpdate(prodVO);
        // 거래처코드
        if (prodVO.getChkVendrCd() != null && !"".equals(prodVO.getChkVendrCd())) {
            // 거래처코드 array 값 세팅
            String chkVendrCd[] = prodVO.getChkVendrCd().split(",");
            for(int i=0; i < chkVendrCd.length; i++) {
                prodVO.setVendrCd(chkVendrCd[i]);

                // 거래처 저장
                prodMapper.getVendorProdSaveInsert(prodVO);
            }
        }

        // 신규상품 이미지 등록시
//        result = Long.parseLong(prodVO.getProdCd());
        String resultProdCd = prodVO.getProdCd();


        // 본사 상품등록시 선택한 사이드메뉴에 걸린 상품 매장에 저장
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // [1111 사이드상품자동생성]이 [1 사용]일 경우
            String hqEnvCodeSide = prodMapper.getHqEnvCodeSide(prodVO);
            if(hqEnvCodeSide.equals("1")) {

                // 사이드사용여부가 Y인 경우에는 선택한 사이드메뉴관리의 선택상품도 매장에 저장
                if ("Y".equals(prodVO.getSideProdYn()) && prodVO.getSdselGrpCd() != null && prodVO.getSdselGrpCd().length() > 0) {

                    // 상품코드 조회
                    String sideProd = prodMapper.getHqSdselProd(prodVO);

                    String arrProdCol[] = sideProd.split(",");
                    for(int i=0; i < arrProdCol.length; i++) {

                        prodVO.setProdCd(arrProdCol[i]);

                        // 상품상세정보 조회
                        DefaultMap prodDtl = prodMapper.getProdDetail(prodVO);
                        prodVO.setBarCd(prodDtl.getStr("barCd"));
                        prodVO.setProdClassCd(prodDtl.getStr("prodClassCd"));
                        prodVO.setSdselGrpCd(prodDtl.getStr("sdselGrpCd"));

                        // 적용 매장 등록
                        // 매장 상품저장시 등록매장 테이블에도 저장 TB_HQ_PRODUCT_STORE
                        prodMapper.insertHqProdStoreTotal(prodVO);

                        // 해당 매장에 본사상품 상품분류 등록
                        // 본사 상품등록시 선택한 사이드메뉴에 걸린 상품 분류 매장에 저장
                        prodMapper.insertHqSdselProdStoreClassTotal(prodVO);

                        // 해당 매장에 본사 상품 등록
                        // 본사 상품등록시 선택한 사이드메뉴에 걸린 상품 매장에 저장
                        prodMapper.insertHqSdselProdStoreTotal(prodVO);

                        // 본사 상품이 바코드를 사용하는 경우, 매장에도 바코드를 넣어준다.
                        if(prodVO.getBarCd() != null && prodVO.getBarCd().length() > 0){
                            prodMapper.insertHqSdselProdStoreBarcdTotal(prodVO);
                        }

                        // [판매가 - 본사통제시] 본사에서 상품정보 수정시 매장에 수정정보 내려줌
                        String storeSalePriceReulst = prodMapper.saveStoreSalePrice(prodVO);

                        // 본사상품이 사이드 선택메뉴를 사용하는 경우, 매장에도 사이드 선택메뉴를 넣어준다.
                        if(prodVO.getSdselGrpCd() != null && prodVO.getSdselGrpCd().length() > 0){
                            // 매장 사이드 선택메뉴 그룹/분류/상품 저장
                            //그룹(sdselGrp)
                            prodMapper.insertSdselGrpToStore(prodVO);
                            //분류(sdselClass)
                            prodMapper.insertSdselClassToStore(prodVO);
                            //상품(sdselProd)
                            prodMapper.insertSdselProdToStore(prodVO);
                        }
                    }
                }
            }
        }

//        return result;
        return resultProdCd;
    }

    /** 상품 적용/미적용 매장 조회 */
    @Override
    public List<DefaultMap<String>> getStoreList(ProdVO prodVO, SessionInfoVO sessionInfoVO) {

        prodVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return prodMapper.getStoreList(prodVO);
    }

    /** 상품적용매장 등록 */
    @Override
    public int insertProdStore(ProdVO[] prodVOs, SessionInfoVO sessionInfoVO) {

        String currentDate = currentDateTimeString();

        int procCnt = 0;

        // 해당 상품이 바코드를 사용하는지 파악
        int prodBarCdYn = prodMapper.getProdBarCdCnt(prodVOs[0]);

        // 해당 상품의 KIOSK 판매시간 사용여부 파악
        String prodSaleTimeFg = prodMapper.getProdSaleTimeFg(prodVOs[0]);

        // 해당 상품이 사이드 선택메뉴를 사용하는지 파악
        String sideSelYn = "N";
        String SideGrpCd = "";
        if("Y".equals(prodVOs[0].getSideProdYn()) && prodVOs[0].getSdselGrpCd() != null && prodVOs[0].getSdselGrpCd().length() > 0){
            sideSelYn = "Y";
            SideGrpCd = prodVOs[0].getSdselGrpCd();
        }

        for(ProdVO prodVO : prodVOs) {
            prodVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            prodVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            prodVO.setRegDt(currentDate);
            prodVO.setRegId(sessionInfoVO.getUserId());
            prodVO.setModDt(currentDate);
            prodVO.setModId(sessionInfoVO.getUserId());
            // WorkMode Flag 매장등록으로 셋팅_2019.06.06
            prodVO.setWorkMode(WorkModeFg.REG_STORE);


            // 적용 매장 등록
            int result = prodMapper.insertProdStore(prodVO);
            if(result <= 0){
                throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            } else {
                procCnt += result;
            }

            // 해당 매장에 본사상품 상품분류 등록
            prodMapper.updateClsHqToStore(prodVO);

            // 해당 매장에 본사 상품 등록
            int hqProdResult = prodMapper.insertProdStoreDetail(prodVO);
            if (hqProdResult <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

            // 해당 매장에 본사 상품 바코드 등록
            // 본사 상품이 바코드를 사용하는 경우, 매장에도 바코드를 넣어준다.
            if(prodBarCdYn > 0){
                int hqProdBarcdResult = prodMapper.insertProdBarcdStoreDetail(prodVO);
                if (hqProdBarcdResult <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }

            // 해당 매장에 본사 KIOSK 판매시간 시간설정 등록
            // 본사 상품이 KIOSK 판매시간을 사용하는 경우, 매장에도 KIOSK 판매시간 시간설정을 넣어준다.
            if("Y".equals(prodSaleTimeFg)){
                prodMapper.deleteProdStoreProdSaleTime(prodVO);
                prodMapper.insertProdStoreProdSaleTime(prodVO);
            }else{
                prodMapper.deleteProdStoreProdSaleTime(prodVO);
            }

            // [판매가 - 본사통제시] 본사에서 상품정보 수정시 매장에 수정정보 내려줌
            String storeSalePriceReulst = prodMapper.saveStoreSalePrice(prodVO);

            // 판매가가 기존 판매가와 다른 경우
            if(!prodVO.getSaleUprc().equals(prodVO.getSaleUprcB())) {

//                prodVO.setSalePrcFg("1"); // 본사에서 변경
                prodVO.setStartDate(currentDateString());
                prodVO.setEndDate("99991231");

                // 판매가 변경 히스토리 등록 count 조회
                int prodCnt = prodMapper.getRegistProdCount(prodVO);

                if(prodCnt > 0) {
                    // 매장 상품 판매가 변경 히스토리 등록
                    result = prodMapper.updateStoreSaleUprcHistory(prodVO);
                    if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                }

                // 매장 상품 판매가 변경
                result = prodMapper.updateStoreSaleUprc(prodVO);
                if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }

            // 본사 상품등록시 선택한 사이드메뉴에 걸린 상품 매장에 저장
            // [1111 사이드상품자동생성]이 [1 사용]일 경우
            String hqEnvCodeSide = prodMapper.getHqEnvCodeSide(prodVO);
            if(hqEnvCodeSide.equals("1")) {

                // 사이드사용여부가 Y인 경우에는 선택한 사이드메뉴관리의 선택상품도 매장에 저장
                if ("Y".equals(sideSelYn) && SideGrpCd != null) {
                    // 상품코드 조회
                    String sideProd = prodMapper.getHqSdselProd(prodVO);
                    String arrProdCol[] = sideProd.split(",");
                    for (int i = 0; i < arrProdCol.length; i++) {

                        prodVO.setProdCd(arrProdCol[i]);

                        // 상품상세정보 조회
                        DefaultMap prodDtl = prodMapper.getProdDetail(prodVO);
                        prodVO.setBarCd(prodDtl.getStr("barCd"));
                        prodVO.setProdClassCd(prodDtl.getStr("prodClassCd"));
                        prodVO.setSdselGrpCd(prodDtl.getStr("sdselGrpCd"));

                        // 적용 매장 등록
                        // 매장 상품저장시 등록매장 테이블에도 저장 TB_HQ_PRODUCT_STORE
                        prodMapper.insertHqProdStore(prodVO);

                        // 해당 매장에 본사상품 상품분류 등록
                        // 본사 상품등록시 선택한 사이드메뉴에 걸린 상품 매장에 저장
                        prodMapper.insertHqSdselProdStoreClass(prodVO);

                        // 해당 매장에 본사 상품 등록
                        // 본사 상품등록시 선택한 사이드메뉴에 걸린 상품 매장에 저장
                        prodMapper.insertProdStoreDetail(prodVO);

                        // 본사 상품이 바코드를 사용하는 경우, 매장에도 바코드를 넣어준다.
                        if (prodVO.getBarCd() != null && prodVO.getBarCd().length() > 0) {
                            prodMapper.insertProdBarcdStoreDetail(prodVO);
                        }

                        // [판매가 - 본사통제시] 본사에서 상품정보 수정시 매장에 수정정보 내려줌
                        storeSalePriceReulst = prodMapper.saveStoreSalePrice(prodVO);

                        // 본사상품이 사이드 선택메뉴를 사용하는 경우, 매장에도 사이드 선택메뉴를 넣어준다.
                        if (prodVO.getSdselGrpCd() != null && prodVO.getSdselGrpCd().length() > 0) {
                            // 매장 사이드 선택메뉴 그룹/분류/상품 저장
                            //그룹(sdselGrp)
                            prodMapper.insertSdselGrpToStore(prodVO);
                            //분류(sdselClass)
                            prodMapper.insertSdselClassToStore(prodVO);
                            //상품(sdselProd)
                            prodMapper.insertSdselProdToStore(prodVO);
                        }
                    }
                }
            }
        }


        // 선택한 매장에 본사 사이드 선택메뉴 등록
        // 본사상품이 사이드 선택메뉴를 사용하는 경우, 매장에도 사이드 선택메뉴를 넣어준다.
        if("Y".equals(sideSelYn)){

            // 값 셋팅
            ProdVO prodVO = new ProdVO();

            prodVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            prodVO.setSdselGrpCd(prodVOs[0].getSdselGrpCd());
            prodVO.setProdCd(prodVOs[0].getProdCd());
            prodVO.setRegDt(currentDate);
            prodVO.setRegId(sessionInfoVO.getUserId());
            prodVO.setModDt(currentDate);
            prodVO.setModId(sessionInfoVO.getUserId());

            // 매장 사이드 선택메뉴 그룹/분류/상품 저장
            //그룹(sdselGrp)
            prodMapper.insertSdselGrpToStore(prodVO);
            //분류(sdselClass)
            prodMapper.insertSdselClassToStore(prodVO);
            //상품(sdselProd)
            prodMapper.insertSdselProdToStore(prodVO);
        }

        return procCnt;
    }

    /** 상품적용매장 삭제 */
    @Override
    public int deleteProdStore(ProdVO[] prodVOs, SessionInfoVO sessionInfoVO) {

        String currentDate = currentDateTimeString();

        int procCnt = 0;

        for(ProdVO prodVO : prodVOs) {
            prodVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            prodVO.setModDt(currentDate);
            prodVO.setModId(sessionInfoVO.getUserId());

            int result = prodMapper.deleteProdStore(prodVO);
            if(result <= 0){
                throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            } else {
                procCnt += result;
            }

            // 해당 상품 삭제
            int hqProdResult = prodMapper.deleteProdStoreDetail(prodVO);
            if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
        return procCnt;
    }

    /** 상품 등록매장의 판매가 변경 */
    @Override
    public int updateStoreSaleUprc(ProdVO[] prodVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;
        String currentDate = currentDateTimeString();

        for(ProdVO prodVO : prodVOs) {
            prodVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
//            prodVO.setSalePrcFg("1"); // 본사에서 변경
            prodVO.setStartDate(currentDateString());
            prodVO.setEndDate("99991231");
            prodVO.setRegDt(currentDate);
            prodVO.setRegId(sessionInfoVO.getUserId());
            prodVO.setModDt(currentDate);
            prodVO.setModId(sessionInfoVO.getUserId());


            // 매장 상품 판매가 변경
            result = prodMapper.updateStoreSaleUprc(prodVO);
            if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

            if(!prodVO.getSaleUprc().equals(prodVO.getSaleUprcB()) ||
               (!CmmUtil.nvl(prodVO.getStinSaleUprc(),0).equals(0) && !prodVO.getStinSaleUprc().equals(CmmUtil.nvl(prodVO.getStinSaleUprcB(),0))) ||
               (!CmmUtil.nvl(prodVO.getDlvrSaleUprc(),0).equals(0) && !prodVO.getDlvrSaleUprc().equals(CmmUtil.nvl(prodVO.getDlvrSaleUprcB(),0))) ||
               (!CmmUtil.nvl(prodVO.getPackSaleUprc(),0).equals(0) && !prodVO.getPackSaleUprc().equals(CmmUtil.nvl(prodVO.getPackSaleUprcB(),0)))){
                // 판매가 변경 히스토리 등록 count 조회
                int prodCnt = prodMapper.getRegistProdCount(prodVO);

                if(prodCnt > 0) {
                    // 매장 상품 판매가 변경 히스토리 등록
                    result = prodMapper.updateStoreSaleUprcHistory(prodVO);
                    if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                }
            }

        }

        return result;
    }

    /** 상품코드 중복체크 */
    public int getProdCdCnt(ProdVO prodVO, SessionInfoVO sessionInfoVO) {

        prodVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        prodVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            prodVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return prodMapper.getProdCdCnt(prodVO);
    }

    /** 바코드 중복체크 */
    public List<DefaultMap<String>> chkBarCd(ProdVO prodVO, SessionInfoVO sessionInfoVO) {

        List<DefaultMap<String>> result = new ArrayList<DefaultMap<String>>();

        prodVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        prodVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            prodVO.setStoreCd(sessionInfoVO.getStoreCd());
            result = prodMapper.chkBarCd(prodVO);
        } else if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ ){

            List<DefaultMap<String>> list = null;
            // 본사 바코드 등록 시 중복된 바코드 체크
            list = prodMapper.chkBarCdHq(prodVO);
            if (list.size() != 0) {
                // 중복된 바코드가 있음
                if(StringUtil.getOrBlank(list.get(0).get("hqCnt")).equals("0") && StringUtil.getOrBlank(list.get(0).get("msCnt")).equals("1")){
                    // 중복된 바코드가 매장테이블에만 있을 경우 매장 데이터 삭제 후 저장
                    prodVO.setModDt(currentDateTimeString());
                    prodVO.setModId(sessionInfoVO.getUserId());
                    prodMapper.deleteProdBarcdStoreHq(prodVO);
                } else {
                    // 그외 바코드 중복 메시지 창 띄움
                    result = prodMapper.chkBarCd(prodVO);
                }
            }
        }
        return prodMapper.chkBarCd(prodVO);
    }

    /** 매장 적용/미적용 상품 조회 */
    @Override
    public List<DefaultMap<String>> getStoreProdBatchList(ProdVO prodVO, SessionInfoVO sessionInfoVO) {

        prodVO.setUserId(sessionInfoVO.getUserId());

        if(prodVO.getProdRegFg() == UseYn.Y){
            return prodMapper.getStoreProdRegList(prodVO);
        }else{
            return prodMapper.getStoreProdNoRegList(prodVO);
        }
    }

    /** 매장 적용상품 등록 */
    @Override
    public int insertStoreProdBatch(ProdVO[] prodVOs, SessionInfoVO sessionInfoVO) {

        String currentDate = currentDateTimeString();

        int procCnt = 0;

        for(ProdVO prodVO : prodVOs) {

            // 본사권한만 등록 가능
            prodVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

            // WorkMode Flag 매장등록으로 셋팅_2019.06.06
            prodVO.setWorkMode(WorkModeFg.REG_STORE);

            prodVO.setRegDt(currentDate);
            prodVO.setRegId(sessionInfoVO.getUserId());
            prodVO.setModDt(currentDate);
            prodVO.setModId(sessionInfoVO.getUserId());

            // 상품상세정보 조회
            DefaultMap prodDtl = prodMapper.getProdDetail(prodVO);
            prodVO.setBarCd(prodDtl.getStr("barCd"));
            prodVO.setProdClassCd(prodDtl.getStr("prodClassCd"));
            prodVO.setSdselGrpCd(prodDtl.getStr("sdselGrpCd"));
            prodVO.setSideProdYn(prodDtl.getStr("sideProdYn"));
            prodVO.setSaleTimeFg(prodDtl.getStr("saleTimeFg"));

            // 적용 매장 등록(혹시 남아있을지 모르기 때문에(중복키 입력 방지) DELETE - INSERT 실행)
            prodMapper.deleteProdStore(prodVO);
            int result = prodMapper.insertProdStore(prodVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

            // 해당 매장에 본사상품 상품분류 등록
            prodMapper.updateClsHqToStore(prodVO);

            // 해당 매장에 본사 상품 등록
            int hqProdResult = prodMapper.insertProdStoreDetail(prodVO);
            if (hqProdResult <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

            // 본사 상품이 바코드를 사용하는 경우, 매장에도 바코드를 넣어준다.
            if(prodVO.getBarCd() != null && prodVO.getBarCd().length() > 0){
                int hqProdBarcdResult = prodMapper.insertProdBarcdStoreDetail(prodVO);
                if (hqProdBarcdResult <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }

            // 본사 상품이 KIOSK 판매시간을 사용하는 경우, 매장에도 KIOSK 판매시간 시간설정을 넣어준다.
            if(prodVO.getSaleTimeFg() != null && "Y".equals(prodVO.getSaleTimeFg())){
                prodMapper.deleteProdStoreProdSaleTime(prodVO);
                prodMapper.insertProdStoreProdSaleTime(prodVO);
            }else{
                prodMapper.deleteProdStoreProdSaleTime(prodVO);
            }

            // [판매가 - 본사통제시] 본사에서 상품정보 수정시 매장에 수정정보 내려줌
            String storeSalePriceReulst = prodMapper.saveStoreSalePrice(prodVO);

            // 판매가가 기존 판매가와 다른 경우
            if(!prodVO.getSaleUprc().equals(prodVO.getSaleUprcB())) {

//                prodVO.setSalePrcFg("1"); // 본사에서 변경
                prodVO.setStartDate(currentDateString());
                prodVO.setEndDate("99991231");

                // 판매가 변경 히스토리 등록 count 조회
                int prodCnt = prodMapper.getRegistProdCount(prodVO);

                if(prodCnt > 0) {
                    // 매장 상품 판매가 변경 히스토리 등록
                    result = prodMapper.updateStoreSaleUprcHistory(prodVO);
                    if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                }

                // 매장 상품 판매가 변경
                result = prodMapper.updateStoreSaleUprc(prodVO);
                if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }

            // 본사상품이 사이드 선택메뉴를 사용하는 경우, 매장에도 사이드 선택메뉴를 넣어준다.
            if(prodVO.getSdselGrpCd() != null && prodVO.getSdselGrpCd().length() > 0){

                // 매장 사이드 선택메뉴 그룹/분류/상품 저장
                //그룹(sdselGrp)
                prodMapper.insertSdselGrpToStore(prodVO);
                //분류(sdselClass)
                prodMapper.insertSdselClassToStore(prodVO);
                //상품(sdselProd)
                prodMapper.insertSdselProdToStore(prodVO);
            }


            // 본사 상품등록시 선택한 사이드메뉴에 걸린 상품 매장에 저장
            // [1111 사이드상품자동생성]이 [1 사용]일 경우
            String hqEnvCodeSide = prodMapper.getHqEnvCodeSide(prodVO);
            if(hqEnvCodeSide.equals("1")) {

                // 사이드사용여부가 Y인 경우에는 선택한 사이드메뉴관리의 선택상품도 매장에 저장
                if ("Y".equals(prodVO.getSideProdYn()) && prodVO.getSdselGrpCd() != null && prodVO.getSdselGrpCd().length() > 0) {

                    // 상품코드 조회
                    String sideProd = prodMapper.getHqSdselProd(prodVO);

                    String arrProdCol[] = sideProd.split(",");
                    for (int i = 0; i < arrProdCol.length; i++) {

                        prodVO.setProdCd(arrProdCol[i]);

                        // 상품상세정보 조회
                        DefaultMap prodDtl2 = prodMapper.getProdDetail(prodVO);
                        prodVO.setBarCd(prodDtl2.getStr("barCd"));
                        prodVO.setProdClassCd(prodDtl2.getStr("prodClassCd"));
                        prodVO.setSdselGrpCd(prodDtl2.getStr("sdselGrpCd"));

                        // 적용 매장 등록
                        // 매장 상품저장시 등록매장 테이블에도 저장 TB_HQ_PRODUCT_STORE
                        prodMapper.insertHqProdStore(prodVO);

                        // 해당 매장에 본사상품 상품분류 등록
                        // 본사 상품등록시 선택한 사이드메뉴에 걸린 상품 매장에 저장
                        prodMapper.insertHqSdselProdStoreClass(prodVO);

                        // 해당 매장에 본사 상품 등록
                        // 본사 상품등록시 선택한 사이드메뉴에 걸린 상품 매장에 저장
                        prodMapper.insertHqSdselProdStoreTotal(prodVO);

                        // 본사 상품이 바코드를 사용하는 경우, 매장에도 바코드를 넣어준다.
                        if (prodVO.getBarCd() != null && prodVO.getBarCd().length() > 0) {
                            prodMapper.insertProdBarcdStoreDetail(prodVO);
                        }

                        // [판매가 - 본사통제시] 본사에서 상품정보 수정시 매장에 수정정보 내려줌
                        storeSalePriceReulst = prodMapper.saveStoreSalePrice(prodVO);

                        // 본사상품이 사이드 선택메뉴를 사용하는 경우, 매장에도 사이드 선택메뉴를 넣어준다.
                        if (prodVO.getSdselGrpCd() != null && prodVO.getSdselGrpCd().length() > 0) {
                            // 매장 사이드 선택메뉴 그룹/분류/상품 저장
                            //그룹(sdselGrp)
                            prodMapper.insertSdselGrpToStore(prodVO);
                            //분류(sdselClass)
                            prodMapper.insertSdselClassToStore(prodVO);
                            //상품(sdselProd)
                            prodMapper.insertSdselProdToStore(prodVO);
                        }
                    }
                }
            }


        }

        return procCnt;
    }

    /** 상품 신규등록,수정 팝업 - 상품 이미지 저장 */
    @Override
    public boolean getProdImageFileSave(MultipartHttpServletRequest multi, SessionInfoVO sessionInfo) {

//        System.out.println("test1111");
//        boolean isSuccess = false;
        boolean isSuccess = true;

        try{

            // 업로드 파일 읽기
            ProdVO prodInfo = new ProdVO();

            // 현재 일자
            String currentDt = currentDateTimeString();

            prodInfo.setModDt(currentDt);
            prodInfo.setModId(sessionInfo.getUserId());
            prodInfo.setRegDt(currentDt);
            prodInfo.setRegId(sessionInfo.getUserId());

            prodInfo.setOrgnFg((String)multi.getParameter("orgnFg"));
            prodInfo.setProdCd((String)multi.getParameter("ImageProdCd"));
            prodInfo.setProdImageDelFg((String)multi.getParameter("prodImageDelFg"));

            // 저장경로 폴더
            String path_folder = "";

            // 본사
            if(String.valueOf(prodInfo.getOrgnFg()).equals("HQ")) {
                prodInfo.setHqOfficeCd((String)multi.getParameter("hqOfficeCd"));
                path_folder = prodInfo.getHqOfficeCd();

                // 매장
            } else if(String.valueOf(prodInfo.getOrgnFg()).equals("STORE")) {
                prodInfo.setStoreCd((String)multi.getParameter("storeCd"));
                path_folder = prodInfo.getStoreCd();
            }

            // 저장 경로 설정 (개발시 로컬) (파일 저장용)
//            String pre_path = "D:\\Workspace\\javaWeb\\testProdImg\\" + path_folder;
//            String path = "D:\\Workspace\\javaWeb\\testProdImg\\" + path_folder + "\\001\\";

            // 파일서버 대응 경로 지정 (운영) (파일 저장용)
            // 001: 기본이미지, 002: KIOSK이미지, 003: DID이미지
            String pre_path = BaseEnv.FILE_UPLOAD_DIR + "prod_img/" + path_folder + "/";
            String path = BaseEnv.FILE_UPLOAD_DIR + "prod_img/" + path_folder + "/001/";
            // FileRoot/prod_img/A0001/001/1597125734220.jpg
            // C:\Users\김설아\.IntelliJIdea2018.3\system\tomcat\Unnamed_sboffice\work\Catalina\localhost\ROOT\FileRoot\prod_img\A0001\001\1597125734220.jpg

            // 저장 경로 설정 (디비 저장용)
            String path_table = multi.getRequestURL().toString().replace(multi.getRequestURI(),"") + "/ProdImg/" + path_folder + "/001/";
            // http://192.168.0.85:10001/ProdImg/A0001/001

            // 업로드 되는 파일명
            String newFileName = "";
            // 원본 파일명
            String orgFileName = "";

            // 경로에 폴도가 있는지 체크
            File pre_dir = new File(pre_path);
            if(!pre_dir.isDirectory()){
                pre_dir.mkdir();
            }
            File dir = new File(path);
            if(!dir.isDirectory()){
                dir.mkdir();
            }

            List<MultipartFile> fileList = multi.getFiles("file");
            // 선택한 파일이 있으면
            for(MultipartFile mFile : fileList)
            {
                newFileName = String.valueOf(System.currentTimeMillis()); // 파일명 (물리적으로 저장되는 파일명)
                orgFileName = mFile.getOriginalFilename(); // 원본 파일명
                String fileExt = FilenameUtils.getExtension(orgFileName); // 파일확장자

                // orgFileName
                if ( orgFileName.contains(".") ) {
                    orgFileName = orgFileName.substring(0, orgFileName.lastIndexOf("."));
                }
                // IE에선 C:\Users\김설아\Desktop\123\new2.txt
                // 크롬에선 new2.txt
                if ( orgFileName.contains("\\") ) {
                    orgFileName = orgFileName.substring(orgFileName.lastIndexOf("\\"));
                    orgFileName = orgFileName.substring(1);
                }

                if(mFile.getOriginalFilename().lastIndexOf('.') > 0) { // 파일명 최소 한글자 이상은 되어야함.
                    // 파일경로
                    prodInfo.setFilePath(path_table);
                    // 파일명 (물리적으로 저장되는 파일명)
                    prodInfo.setFileNm(newFileName);
                    // 원본 파일명
//                    prodInfo.setOrginlFileNm(orgFileName);
                    // 파일확장자
                    prodInfo.setFileExt(fileExt);

                    // 파일 저장하는 부분
                    try {
                        mFile.transferTo(new File(path+newFileName+"."+fileExt));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    // 상품 이미지 저장시 파일여부 체크
                    String check = prodMapper.getProdImageFileSaveCheck(prodInfo);
                    if(String.valueOf(check).equals("0")) {
                        // 상품 이미지 저장 insert
                        if(prodMapper.getProdImageFileSaveInsert(prodInfo) > 0) {
                            isSuccess = true;
                        } else {
                            isSuccess = false;
                        }
                    } else {
                        // 상품 이미지 저장 update
                        if(prodMapper.getProdImageFileSaveUpdate(prodInfo) > 0) {
                            isSuccess = true;
                        } else {
                            isSuccess = false;
                        }
                    }
                }
            }

            // 선택한 파일이 없으면 // 크롬시 안먹음
//            if(fileList.size() == 0) {
            // 삭제시
            if(String.valueOf(prodInfo.getProdImageDelFg()).equals("DEL")) {
                // 상품 이미지 삭제시 파일명 가져오기
                String pathFull = prodMapper.getProdImageFileSaveImgFileNm(prodInfo);

                // 상품 이미지 저장 delete
                if(prodMapper.getProdImageFileSaveDelete(prodInfo) > 0) {
                    // 파일 삭제
//                        File delFile = new File("D:\\Workspace\\javaWeb\\testProdImg\\" + pathFull);
                    File delFile = new File(BaseEnv.FILE_UPLOAD_DIR + pathFull);
                    if(delFile.exists()) {
                        delFile.delete();
                    }
                    isSuccess = true;
                } else {
                    isSuccess = false;
                }
            }
//            }

        }catch(Exception e){

            isSuccess = false;
        }
        return isSuccess;
    }

    /** 미적용 상품 거래처 조회 팝업 - 조회 */
    @Override
    public List<DefaultMap<String>> getSearchNoProdVendrList(ProdVO prodVO, SessionInfoVO sessionInfoVO) {

        prodVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        prodVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            prodVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        // 거래처코드
        if (prodVO.getChkVendrCd() != null && !"".equals(prodVO.getChkVendrCd())) {
            // 거래처코드 array 값 세팅
            String[] chkVendrCd = prodVO.getChkVendrCd().split(",");
            prodVO.setVendrCdList(chkVendrCd);
        }

        return prodMapper.getSearchNoProdVendrList(prodVO);
    }

    /** 브랜드 콤보박스 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getBrandComboList(ProdVO prodVO, SessionInfoVO sessionInfoVO) {
        return prodMapper.getBrandComboList(prodVO);
    }

    /** 사이드메뉴관리의 선택상품에 등록된 상품인지 조회 */
    @Override
    public List<DefaultMap<Object>> getSideProdChk(ProdVO prodVO, SessionInfoVO sessionInfoVO) {

        prodVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        prodVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            prodVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return prodMapper.getSideProdChk(prodVO);
    }

    /** 프린트 조회 */
    @Override
    public List<DefaultMap<String>> getKitchenprintList(ProdVO prodVO, SessionInfoVO sessionInfoVO) {
        prodVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        prodVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            prodVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        return prodMapper.getKitchenprintList(prodVO);
    }

    /** 프린트 연결 */
    @Override
    public int kitchenprintLink(ProdVO[] prodVOs, SessionInfoVO sessionInfoVO) {
        int result = 0;
        String dt = currentDateTimeString();
        for(ProdVO prodVO : prodVOs){
            prodVO.setRegDt(dt);
            prodVO.setRegId(sessionInfoVO.getUserId());
            prodVO.setModDt(dt);
            prodVO.setModId(sessionInfoVO.getUserId());

            result += prodMapper.kitchenprintLink(prodVO);
        }
        return result;
    }

    /** 브랜드 리스트 조회(선택 콤보박스용) */
    @Override
    public List<DefaultMap<Object>> getBrandList(ProdVO prodVO, SessionInfoVO sessionInfoVO) {

        prodVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        prodVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            prodVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        prodVO.setUserId(sessionInfoVO.getUserId());

        return prodMapper.getBrandList(prodVO);
    }

    /** 브랜드 리스트 조회(선택 콤보박스용, 선택한 상품에서 현재 사용중인 브랜드 + 사용여부 'Y'인 브랜드) */
    @Override
    public List<DefaultMap<Object>> getBrandList2(ProdVO prodVO, SessionInfoVO sessionInfoVO) {

        prodVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        prodVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            prodVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return prodMapper.getBrandList2(prodVO);
    }

    /** 세트구성상품 팝업 - 구성내역 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getSetConfigProdList(ProdVO prodVO, SessionInfoVO sessionInfoVO) {

        prodVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        prodVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            prodVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return prodMapper.getSetConfigProdList(prodVO);
    }

    /** 세트구성상품 팝업 - 상품 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getSrchSetConfigProdList(ProdVO prodVO, SessionInfoVO sessionInfoVO) {

        prodVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        prodVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            prodVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return prodMapper.getSrchSetConfigProdList(prodVO);
    }

    /** 세트구성상품 팝업 - 상품 등록/수정/삭제 */
    @Override
    public int saveSetConfigProd(ProdVO[] prodVOs, SessionInfoVO sessionInfoVO) {

        String dt = currentDateTimeString();

        int result = 0;
        int procResult = 0;

        for(ProdVO prodVO : prodVOs) {

            prodVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            prodVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
                prodVO.setStoreCd(sessionInfoVO.getStoreCd());
            }

            prodVO.setRegDt(dt);
            prodVO.setRegId(sessionInfoVO.getUserId());
            prodVO.setModDt(dt);
            prodVO.setModId(sessionInfoVO.getUserId());

            if ( prodVO.getStatus() == GridDataFg.INSERT ) { // 등록

                // 상품 새 표기순번
                prodVO.setDispSeq(prodMapper.getSetConfigProdDispSeq(prodVO));
                result += prodMapper.insertSetConfigProd(prodVO);

            } else if ( prodVO.getStatus() == GridDataFg.UPDATE ) { // 수정
                result += prodMapper.updateSetConfigProd(prodVO);

            } else if ( prodVO.getStatus() == GridDataFg.DELETE ) { // 삭제
                result += prodMapper.deleteSetConfigProd(prodVO);
            }
        }

        if (result == prodVOs.length) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }

    /** 선택메뉴 조회 팝업 */
    @Override
    public List<DefaultMap<String>> getSearchSdselGrpList(ProdVO prodVO, SessionInfoVO sessionInfoVO) {

        prodVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        prodVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            prodVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return prodMapper.getSearchSdselGrpList(prodVO);
    }

    /** 발주 단위 구분 조회 */
    @Override
    public List<DefaultMap<String>> getPoUnitFgData(ProdVO prodVO, SessionInfoVO sessionInfoVO) {
        prodVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        prodVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            prodVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        return prodMapper.getPoUnitFgData(prodVO);
    }

    /** 선택상품삭제 */
    @Override
    public int selectProdDelete(ProdVO[] prodVOs, SessionInfoVO sessionInfoVO){

        int result = 0;
        String dt = currentDateTimeString();

        int delYn = 0;                              // 삭제가능여부 파악
        int iSeq = 0;                               // 상품삭제 임시테이블 상품 seq 번호
        String delProdSeq = "";                     // 영구삭제할 상품 seq 번호(불필요한 로직 실행안하기 위한 판별용)
        String modProdSeq = "";                     // '미사용'처리할 상품 seq 번호(불필요한 로직 실행안하기 위한 판별용)

        ProdVO prodVO1 = new ProdVO();
        prodVO1.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        prodVO1.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            prodVO1.setStoreCd(sessionInfoVO.getStoreCd());
        }
        prodVO1.setSessionId(sessionInfoVO.getUserId());

        // 임시 상품삭제테이블 초기화
        prodMapper.deleteAllTmpDelProduct(prodVO1);

        for (ProdVO prodVO : prodVOs) {

            delYn = 0; // 초기화
            prodVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            prodVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
                prodVO.setStoreCd(sessionInfoVO.getStoreCd());
            }
            prodVO.setRegDt(dt);
            prodVO.setRegId(sessionInfoVO.getUserId());
            prodVO.setModDt(dt);
            prodVO.setModId(sessionInfoVO.getUserId());
            prodVO.setSessionId(sessionInfoVO.getUserId());
            prodVO.setSeq(++iSeq);

            // 1. 삭제 전 데이터 확인
            if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ ){
                delYn += prodMapper.getHqEventMsgProdCnt(prodVO);              // 본사 - 이벤트 상품
                delYn += prodMapper.getHqProductSdselProdCnt(prodVO);          // 본사 - 상품 사이드선택 상품
                delYn += prodMapper.getHqProductUnitstProdCnt(prodVO);         // 본사 - 상품 세트구성 상품
                delYn += prodMapper.getHqPromoBeneProdCnt(prodVO);             // 본사 - 프로모션 혜택품목
                delYn += prodMapper.getHqPromoCondiProdCnt(prodVO);            // 본사 - 프로모션 적용상품
                delYn += prodMapper.getHqStoreProdGroupDtlCnt(prodVO);         // 본사 - 메뉴그룹-상품설정
                delYn += prodMapper.getStHqActualInspectionDtlCnt(prodVO);     // 본사 - 실사재고 상세
                delYn += prodMapper.getStHqAdjustDtlCnt(prodVO);               // 본사 - 재고조정 상세
                delYn += prodMapper.getStHqDisuseDtlCnt(prodVO);               // 본사 - 재고폐기 상세
                delYn += prodMapper.getStHqSetprodCompositionCnt(prodVO);      // 본사 - 수불내역 세트구성/해체내역
                delYn += prodMapper.getStHqSetprodCompositionDtlCnt(prodVO);   // 본사 - 수불내역 세트구성/해체내역 상세
                delYn += prodMapper.getStHqStockCurCnt(prodVO);                // 본사 - 현재고
                delYn += prodMapper.getPoHqMoveDtlCnt(prodVO);                 // 본사수불 - 이동전표 본사간이동내역 DT
                delYn += prodMapper.getPoHqDistributeCnt(prodVO);              // 본사수불 - 분배출고_매장별분배출고내역
                delYn += prodMapper.getPoHqOutstockDtlCnt(prodVO);             // 본사수불 - 출고전표_매장출고내역_상세
                delYn += prodMapper.getPoHqVendrInstockDtlCnt(prodVO);         // 본사수불 - 거래처_업체입고반출전표_상세
                delYn += prodMapper.getPoHqVendrOrderDtlCnt(prodVO);           // 본사수불 - 거래처_업체발주전표 상세
                delYn += prodMapper.getPoStoreInstockErrorDtlCnt(prodVO);      // 본사수불 - 물량오류_매장입고오류내역 상세
                delYn += prodMapper.getPoStoreMoveDtlCnt(prodVO);              // 본사수불 - 이동전표_매장간이동내역-상세

                if(delYn == 0){

                    // 삭제 전 본사에 속한 매장 데이터 확인
                    delYn += prodMapper.getMsEventMsgProdCnt(prodVO);              // 매장 - 이벤트 상품
                    delYn += prodMapper.getMsProductUnitstProdCnt(prodVO);         // 매장 - 상품 세트구성 상품
                    delYn += prodMapper.getMsPromoBeneProdCnt(prodVO);             // 매장 - 프로모션 혜택품목
                    delYn += prodMapper.getMsPromoCondiProdCnt(prodVO);            // 매장 - 프로모션 적용상품
                    delYn += prodMapper.getStStoreActualInspectionDtlCnt(prodVO);  // 매장 - 실사재고 상세
                    delYn += prodMapper.getStStoreAdjustDtlCnt(prodVO);            // 매장 - 재고조정 상세
                    delYn += prodMapper.getStStoreDisuseDtlCnt(prodVO);            // 매장 - 재고폐기 상세
                    delYn += prodMapper.getStStoreStockCurCnt(prodVO);             // 매장 - 현재고
                    delYn += prodMapper.getPoHqStoreDistributeCnt(prodVO);         // 매장수불 - 분배출고_매장별분배출고내역
                    delYn += prodMapper.getPoHqStoreOrderDtlCnt(prodVO);           // 매장수불 - 주문전표_본사주문내역-상세
                    delYn += prodMapper.getPoHqStoreOutstockDtlCnt(prodVO);        // 매장수불 - 출고전표_매장출고내역_상세
                    delYn += prodMapper.getPoStoreStandMoveDtlCnt(prodVO);         // 매장수불 - 매대창고간이동내역 상세
                    delYn += prodMapper.getPoStoreVendrInstockDtlCnt(prodVO);      // 매장수불 - 거래처 업체입고반출전표 상세
                    delYn += prodMapper.getPoStoreVendrOrderDtlCnt(prodVO);        // 매장수불 - 거래처 업체발주전표 상세

                    if(delYn == 0){
                        prodVO.setDelTypeFg("0"); // 영구삭제가능
                        delProdSeq += (delProdSeq.equals("") ? "" : ",") + prodVO.getProdCd();
                    }else{
                        prodVO.setDelTypeFg("1"); // 영구삭제불가('미사용' 처리)
                        modProdSeq += (modProdSeq.equals("") ? "" : ",") + prodVO.getProdCd();
                    }
                }else{
                    prodVO.setDelTypeFg("1"); // 영구삭제불가('미사용' 처리)
                    modProdSeq += (modProdSeq.equals("") ? "" : ",") + prodVO.getProdCd();
                }
            }else{
                delYn += prodMapper.getMsEventMsgProdCnt(prodVO);              // 매장 - 이벤트 상품
                delYn += prodMapper.getMsProductUnitstProdCnt(prodVO);         // 매장 - 상품 세트구성 상품
                delYn += prodMapper.getMsPromoBeneProdCnt(prodVO);             // 매장 - 프로모션 혜택품목
                delYn += prodMapper.getMsPromoCondiProdCnt(prodVO);            // 매장 - 프로모션 적용상품
                delYn += prodMapper.getStStoreActualInspectionDtlCnt(prodVO);  // 매장 - 실사재고 상세
                delYn += prodMapper.getStStoreAdjustDtlCnt(prodVO);            // 매장 - 재고조정 상세
                delYn += prodMapper.getStStoreDisuseDtlCnt(prodVO);            // 매장 - 재고폐기 상세
                delYn += prodMapper.getStStoreStockCurCnt(prodVO);             // 매장 - 현재고
                delYn += prodMapper.getPoHqStoreDistributeCnt(prodVO);         // 매장수불 - 분배출고_매장별분배출고내역
                delYn += prodMapper.getPoHqStoreOrderDtlCnt(prodVO);           // 매장수불 - 주문전표_본사주문내역-상세
                delYn += prodMapper.getPoHqStoreOutstockDtlCnt(prodVO);        // 매장수불 - 출고전표_매장출고내역_상세
                delYn += prodMapper.getPoStoreStandMoveDtlCnt(prodVO);         // 매장수불 - 매대창고간이동내역 상세
                delYn += prodMapper.getPoStoreVendrInstockDtlCnt(prodVO);      // 매장수불 - 거래처 업체입고반출전표 상세
                delYn += prodMapper.getPoStoreVendrOrderDtlCnt(prodVO);        // 매장수불 - 거래처 업체발주전표 상세

                if(delYn == 0){
                    prodVO.setDelTypeFg("0"); // 영구삭제가능
                    delProdSeq += (delProdSeq.equals("") ? "" : ",") + prodVO.getProdCd();
                }else{
                    prodVO.setDelTypeFg("1"); // 영구삭제불가('미사용' 처리)
                    modProdSeq += (modProdSeq.equals("") ? "" : ",") + prodVO.getProdCd();
                }
            }

            // 상품삭제 임시테이블에 Insert
            prodMapper.insertTmpDelProduct(prodVO);
        }

        LOGGER.info("선택_delProdSeq : " + delProdSeq);
        LOGGER.info("선택_modProdSeq : " + modProdSeq);





        //
        prodVO1.setRegDt(dt);
        prodVO1.setRegId(sessionInfoVO.getUserId());
        prodVO1.setModDt(dt);
        prodVO1.setModId(sessionInfoVO.getUserId());

        // 선택상품 영구삭제
        if(!StringUtil.getOrBlank(delProdSeq).equals("")){

            prodVO1.setDelTypeFg("0"); // 영구삭제가능

            prodMapper.deleteProdInfoProdInfo(prodVO1);            // 상품추가정보
            prodMapper.deleteProdInfoSaleTime(prodVO1);            // 키오스크판매시간대정보
            prodMapper.deleteProdInfoCouponProd(prodVO1);          // 쿠폰적용상품
            prodMapper.deleteProdInfoKioskKey(prodVO1);            // 키오스크 키맵설정
            prodMapper.deleteProdInfoKioskRecmd(prodVO1);          // 키오스크 추천메뉴정보
            prodMapper.deleteProdInfoKioskRecmdProd(prodVO1);      // 키오스크 메뉴추천리스트
            prodMapper.deleteProdInfoAlgiProd(prodVO1);            // 재료/알러지-상품맵핑정보
            prodMapper.deleteProdInfoBarcd(prodVO1);               // 상품-바코드
            prodMapper.deleteProdInfoDlvrProdNm(prodVO1);          // 배달앱 상품명-맵핑정보
            prodMapper.deleteProdInfoOption(prodVO1);              // 마스터-키오스크옵션상품
            prodMapper.deleteProdInfoRecpProd(prodVO1);            // 재료-상품맵핑정보
            prodMapper.deleteProdInfoSalePrice(prodVO1);           // 상품 판매금액
            prodMapper.deleteProdInfoVendorProd(prodVO1);          // 거래처별_취급상품
            prodMapper.deleteProdInfoUnitstProd(prodVO1);          // 상품_세트구성_상품
            prodMapper.deleteProdInfoTouchKey(prodVO1);            // 상품_판매터치키

            if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ ){
                prodMapper.deleteProdInfoHqProductStore(prodVO1);  // 본사) 상품별_취급매장
            }else{
                prodMapper.deleteProdInfoPrintProd(prodVO1);       // 매장) 주방프린터_출력상품
                prodMapper.deleteProdInfoSdselProd(prodVO1);       // 매장) 상품_사이드선택_상품
            }

            LOGGER.info("선택_이미지삭제_시작");

            // 상품이미지 삭제
            try{
                // 저장 경로 설정
                String path_folder = "";

                // 접속권한에 따른 경로 셋팅
                if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ ){
                    path_folder = prodVO1.getHqOfficeCd();
                }else{
                    path_folder = prodVO1.getStoreCd();
                }

                // 서버 저장 경로 설정 (imgFg -> 001: 기본이미지, 002: KIOSK이미지, 003: DID이미지)
                //String path = "D:\\prod_img\\" + path_folder + "/";
                String path = BaseEnv.FILE_UPLOAD_DIR + "prod_img/" + path_folder + "/";

                // 삭제할 상품이미지 파일명 가져오기
                List<DefaultMap<String>> imgList = prodMapper.getProdImgInfo(prodVO1);

                if(imgList.size() > 0){
                    for(DefaultMap<String> list : imgList){
                        // 서버 파일 삭제
                        File delFile = new File(path + list.getStr("imgFg") + "/" + list.getStr("imgFileNm"));
                        if(delFile.exists()) {
                            delFile.delete();
                            LOGGER.info("선택_이미지삭제 : " + path + list.getStr("imgFg") + "/" + list.getStr("imgFileNm"));
                        }
                    }
                }

            }catch(Exception e) {
                LOGGER.info("선택_이미지삭제_오류 : " + e.getMessage());
            }

            // 본사인경우, 매장 상품이미지도 삭제
            // 여기서 삭제하는 이유는, 본사 상품이미지정보 삭제시 트리거로 인해 매장상품이미지 정보도 같이 삭제되어 버려서 서버의 매장상품이미지를 삭제할 수 없게됨.
            if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ ){

                LOGGER.info("선택_본사상품_매장삭제_이미지삭제_시작");

                // 매장상품이미지 삭제
                try{

                    // 서버 저장 경로 설정 (imgFg -> 001: 기본이미지, 002: KIOSK이미지, 003: DID이미지)
                    //String path = "D:\\prod_img\\";
                    String path = BaseEnv.FILE_UPLOAD_DIR + "prod_img/";

                    // 삭제할 상품이미지 파일명 가져오기
                    List<DefaultMap<String>> imgList = prodMapper.getProdImgInfoAllStore(prodVO1);

                    if(imgList.size() > 0){
                        for(DefaultMap<String> list : imgList){
                            // 서버 파일 삭제
                            File delFile = new File(path + list.getStr("storeCd") + "/" + list.getStr("imgFg") + "/" + list.getStr("imgFileNm"));
                            if(delFile.exists()) {
                                delFile.delete();
                                LOGGER.info("선택_본사상품_매장삭제_이미지삭제 : " + path + list.getStr("storeCd") + "/" + list.getStr("imgFg") + "/" + list.getStr("imgFileNm"));
                            }
                        }
                    }

                }catch(Exception e) {
                    LOGGER.info("선택_본사상품_매장삭제_이미지삭제_오류 : " + e.getMessage());
                }
            }

            prodMapper.deleteProdInfoImage(prodVO1);               // 상품 이미지
            prodMapper.deleteProdInfo(prodVO1);                    // 상품정보

            // 본사인경우, 본사에 속한 매장상품정보도 영구삭제
            if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ ){

                prodMapper.deleteProdInfoProdInfoAllStore(prodVO1);            // 상품추가정보
                prodMapper.deleteProdInfoSaleTimeAllStore(prodVO1);            // 키오스크판매시간대정보
                prodMapper.deleteProdInfoCouponProdAllStore(prodVO1);          // 쿠폰적용상품
                prodMapper.deleteProdInfoKioskKeyAllStore(prodVO1);            // 키오스크 키맵설정
                prodMapper.deleteProdInfoKioskRecmdAllStore(prodVO1);          // 키오스크 추천메뉴정보
                prodMapper.deleteProdInfoKioskRecmdProdAllStore(prodVO1);      // 키오스크 메뉴추천리스트
                prodMapper.deleteProdInfoAlgiProdAllStore(prodVO1);            // 재료/알러지-상품맵핑정보
                prodMapper.deleteProdInfoBarcdAllStore(prodVO1);               // 상품-바코드
                prodMapper.deleteProdInfoDlvrProdNmAllStore(prodVO1);          // 배달앱 상품명-맵핑정보
                prodMapper.deleteProdInfoOptionAllStore(prodVO1);              // 마스터-키오스크옵션상품
                prodMapper.deleteProdInfoRecpProdAllStore(prodVO1);            // 재료-상품맵핑정보
                prodMapper.deleteProdInfoSalePriceAllStore(prodVO1);           // 상품 판매금액
                prodMapper.deleteProdInfoVendorProdAllStore(prodVO1);          // 거래처별_취급상품
                prodMapper.deleteProdInfoUnitstProdAllStore(prodVO1);          // 상품_세트구성_상품
                prodMapper.deleteProdInfoTouchKeyAllStore(prodVO1);            // 상품_판매터치키
                prodMapper.deleteProdInfoPrintProdAllStore(prodVO1);           // 매장) 주방프린터_출력상품
                prodMapper.deleteProdInfoSdselProdAllStore(prodVO1);           // 매장) 상품_사이드선택_상품


                LOGGER.info("선택_본사상품_매장삭제_이미지삭제2_시작");

                // 매장상품이미지 삭제
                try{

                    // 서버 저장 경로 설정 (imgFg -> 001: 기본이미지, 002: KIOSK이미지, 003: DID이미지)
                    //String path = "D:\\prod_img\\";
                    String path = BaseEnv.FILE_UPLOAD_DIR + "prod_img/";

                    // 삭제할 상품이미지 파일명 가져오기
                    List<DefaultMap<String>> imgList = prodMapper.getProdImgInfoAllStore(prodVO1);

                    if(imgList.size() > 0){
                        for(DefaultMap<String> list : imgList){
                            // 서버 파일 삭제
                            File delFile = new File(path + list.getStr("storeCd") + "/" + list.getStr("imgFg") + "/" + list.getStr("imgFileNm"));
                            if(delFile.exists()) {
                                delFile.delete();
                                LOGGER.info("선택_본사상품_매장삭제_이미지삭제2 : " + path + list.getStr("storeCd") + "/" + list.getStr("imgFg") + "/" + list.getStr("imgFileNm"));
                            }
                        }
                    }

                }catch(Exception e) {
                    LOGGER.info("선택_본사상품_매장삭제_이미지삭제_오류2 : " + e.getMessage());
                }

                prodMapper.deleteProdInfoImageAllStore(prodVO1);               // 상품 이미지
                prodMapper.deleteProdInfoAllStore(prodVO1);                    // 상품정보
            }
        }

        // 선택상품 '미사용' 처리
        if(!StringUtil.getOrBlank(modProdSeq).equals("")){

            prodVO1.setDelTypeFg("1"); // 영구삭제불가('미사용' 처리)
            prodMapper.updateProdUseYn(prodVO1);

            // 본사인경우, 매장의 상품정보도 '미사용' 처리
            if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ ) {
                prodMapper.updateStoreProdUseYn(prodVO1);
            }
        }

        // 완료 본사/매장의 임시테이블 초기화
        prodMapper.deleteAllTmpDelProduct(prodVO1);

        return result;
    }

    /** 전체상품삭제 */
    @Override
    public int allProdDelete(ProdVO prodVO, SessionInfoVO sessionInfoVO){

        int result = 0;
        String dt = currentDateTimeString();

        int delYn = 0;                              // 삭제가능여부 파악
        int iSeq = 0;                               // 상품삭제 임시테이블 상품 seq 번호
        String delProdSeq = "";                     // 영구삭제할 상품 seq 번호(불필요한 로직 실행안하기 위한 판별용)
        String modProdSeq = "";                     // '미사용'처리할 상품 seq 번호(불필요한 로직 실행안하기 위한 판별용)

        prodVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        prodVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            prodVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        prodVO.setRegDt(dt);
        prodVO.setRegId(sessionInfoVO.getUserId());
        prodVO.setModDt(dt);
        prodVO.setModId(sessionInfoVO.getUserId());
        prodVO.setSessionId(sessionInfoVO.getUserId());
        prodVO.setUserId(sessionInfoVO.getUserId());

        // 임시 상품삭제테이블 초기화
        prodMapper.deleteAllTmpDelProduct(prodVO);

        // 전체 상품 조회
        List<DefaultMap<String>> prodList = prodMapper.getAllProdList(prodVO);

        if(prodList.size() > 0) {
            for (DefaultMap<String> prod : prodList) {

                delYn = 0; // 초기화
                prodVO.setProdCd(prod.getStr("prodCd"));
                prodVO.setSeq(++iSeq);

                // 1. 삭제 전 데이터 확인
                if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
                    delYn += prodMapper.getHqEventMsgProdCnt(prodVO);              // 본사 - 이벤트 상품
                    delYn += prodMapper.getHqProductSdselProdCnt(prodVO);          // 본사 - 상품 사이드선택 상품
                    delYn += prodMapper.getHqProductUnitstProdCnt(prodVO);         // 본사 - 상품 세트구성 상품
                    delYn += prodMapper.getHqPromoBeneProdCnt(prodVO);             // 본사 - 프로모션 혜택품목
                    delYn += prodMapper.getHqPromoCondiProdCnt(prodVO);            // 본사 - 프로모션 적용상품
                    delYn += prodMapper.getHqStoreProdGroupDtlCnt(prodVO);         // 본사 - 메뉴그룹-상품설정
                    delYn += prodMapper.getStHqActualInspectionDtlCnt(prodVO);     // 본사 - 실사재고 상세
                    delYn += prodMapper.getStHqAdjustDtlCnt(prodVO);               // 본사 - 재고조정 상세
                    delYn += prodMapper.getStHqDisuseDtlCnt(prodVO);               // 본사 - 재고폐기 상세
                    delYn += prodMapper.getStHqSetprodCompositionCnt(prodVO);      // 본사 - 수불내역 세트구성/해체내역
                    delYn += prodMapper.getStHqSetprodCompositionDtlCnt(prodVO);   // 본사 - 수불내역 세트구성/해체내역 상세
                    delYn += prodMapper.getStHqStockCurCnt(prodVO);                // 본사 - 현재고
                    delYn += prodMapper.getPoHqMoveDtlCnt(prodVO);                 // 본사수불 - 이동전표 본사간이동내역 DT
                    delYn += prodMapper.getPoHqDistributeCnt(prodVO);              // 본사수불 - 분배출고_매장별분배출고내역
                    delYn += prodMapper.getPoHqOutstockDtlCnt(prodVO);             // 본사수불 - 출고전표_매장출고내역_상세
                    delYn += prodMapper.getPoHqVendrInstockDtlCnt(prodVO);         // 본사수불 - 거래처_업체입고반출전표_상세
                    delYn += prodMapper.getPoHqVendrOrderDtlCnt(prodVO);           // 본사수불 - 거래처_업체발주전표 상세
                    delYn += prodMapper.getPoStoreInstockErrorDtlCnt(prodVO);      // 본사수불 - 물량오류_매장입고오류내역 상세
                    delYn += prodMapper.getPoStoreMoveDtlCnt(prodVO);              // 본사수불 - 이동전표_매장간이동내역-상세

                    if (delYn == 0) {

                        // 삭제 전 본사에 속한 매장 데이터 확인
                        delYn += prodMapper.getMsEventMsgProdCnt(prodVO);              // 매장 - 이벤트 상품
                        delYn += prodMapper.getMsProductUnitstProdCnt(prodVO);         // 매장 - 상품 세트구성 상품
                        delYn += prodMapper.getMsPromoBeneProdCnt(prodVO);             // 매장 - 프로모션 혜택품목
                        delYn += prodMapper.getMsPromoCondiProdCnt(prodVO);            // 매장 - 프로모션 적용상품
                        delYn += prodMapper.getStStoreActualInspectionDtlCnt(prodVO);  // 매장 - 실사재고 상세
                        delYn += prodMapper.getStStoreAdjustDtlCnt(prodVO);            // 매장 - 재고조정 상세
                        delYn += prodMapper.getStStoreDisuseDtlCnt(prodVO);            // 매장 - 재고폐기 상세
                        delYn += prodMapper.getStStoreStockCurCnt(prodVO);             // 매장 - 현재고
                        delYn += prodMapper.getPoHqStoreDistributeCnt(prodVO);         // 매장수불 - 분배출고_매장별분배출고내역
                        delYn += prodMapper.getPoHqStoreOrderDtlCnt(prodVO);           // 매장수불 - 주문전표_본사주문내역-상세
                        delYn += prodMapper.getPoHqStoreOutstockDtlCnt(prodVO);        // 매장수불 - 출고전표_매장출고내역_상세
                        delYn += prodMapper.getPoStoreStandMoveDtlCnt(prodVO);         // 매장수불 - 매대창고간이동내역 상세
                        delYn += prodMapper.getPoStoreVendrInstockDtlCnt(prodVO);      // 매장수불 - 거래처 업체입고반출전표 상세
                        delYn += prodMapper.getPoStoreVendrOrderDtlCnt(prodVO);        // 매장수불 - 거래처 업체발주전표 상세

                        if (delYn == 0) {
                            prodVO.setDelTypeFg("0"); // 영구삭제가능
                            delProdSeq += (delProdSeq.equals("") ? "" : ",") + prodVO.getProdCd();
                        } else {
                            prodVO.setDelTypeFg("1"); // 영구삭제불가('미사용' 처리)
                            modProdSeq += (modProdSeq.equals("") ? "" : ",") + prodVO.getProdCd();
                        }
                    } else {
                        prodVO.setDelTypeFg("1"); // 영구삭제불가('미사용' 처리)
                        modProdSeq += (modProdSeq.equals("") ? "" : ",") + prodVO.getProdCd();
                    }
                } else {
                    delYn += prodMapper.getMsEventMsgProdCnt(prodVO);              // 매장 - 이벤트 상품
                    delYn += prodMapper.getMsProductUnitstProdCnt(prodVO);         // 매장 - 상품 세트구성 상품
                    delYn += prodMapper.getMsPromoBeneProdCnt(prodVO);             // 매장 - 프로모션 혜택품목
                    delYn += prodMapper.getMsPromoCondiProdCnt(prodVO);            // 매장 - 프로모션 적용상품
                    delYn += prodMapper.getStStoreActualInspectionDtlCnt(prodVO);  // 매장 - 실사재고 상세
                    delYn += prodMapper.getStStoreAdjustDtlCnt(prodVO);            // 매장 - 재고조정 상세
                    delYn += prodMapper.getStStoreDisuseDtlCnt(prodVO);            // 매장 - 재고폐기 상세
                    delYn += prodMapper.getStStoreStockCurCnt(prodVO);             // 매장 - 현재고
                    delYn += prodMapper.getPoHqStoreDistributeCnt(prodVO);         // 매장수불 - 분배출고_매장별분배출고내역
                    delYn += prodMapper.getPoHqStoreOrderDtlCnt(prodVO);           // 매장수불 - 주문전표_본사주문내역-상세
                    delYn += prodMapper.getPoHqStoreOutstockDtlCnt(prodVO);        // 매장수불 - 출고전표_매장출고내역_상세
                    delYn += prodMapper.getPoStoreStandMoveDtlCnt(prodVO);         // 매장수불 - 매대창고간이동내역 상세
                    delYn += prodMapper.getPoStoreVendrInstockDtlCnt(prodVO);      // 매장수불 - 거래처 업체입고반출전표 상세
                    delYn += prodMapper.getPoStoreVendrOrderDtlCnt(prodVO);        // 매장수불 - 거래처 업체발주전표 상세

                    if (delYn == 0) {
                        prodVO.setDelTypeFg("0"); // 영구삭제가능
                        delProdSeq += (delProdSeq.equals("") ? "" : ",") + prodVO.getProdCd();
                    } else {
                        prodVO.setDelTypeFg("1"); // 영구삭제불가('미사용' 처리)
                        modProdSeq += (modProdSeq.equals("") ? "" : ",") + prodVO.getProdCd();
                    }
                }

                // 상품삭제 임시테이블에 Insert
                prodMapper.insertTmpDelProduct(prodVO);
            }

            LOGGER.info("전체_delProdSeq : " + delProdSeq);
            LOGGER.info("전체_modProdSeq : " + modProdSeq);
        }




        // 선택상품 영구삭제
        if(!StringUtil.getOrBlank(delProdSeq).equals("")){

            prodVO.setDelTypeFg("0"); // 영구삭제가능

            prodMapper.deleteProdInfoProdInfo(prodVO);            // 상품추가정보
            prodMapper.deleteProdInfoSaleTime(prodVO);            // 키오스크판매시간대정보
            prodMapper.deleteProdInfoCouponProd(prodVO);          // 쿠폰적용상품
            prodMapper.deleteProdInfoKioskKey(prodVO);            // 키오스크 키맵설정
            prodMapper.deleteProdInfoKioskRecmd(prodVO);          // 키오스크 추천메뉴정보
            prodMapper.deleteProdInfoKioskRecmdProd(prodVO);      // 키오스크 메뉴추천리스트
            prodMapper.deleteProdInfoAlgiProd(prodVO);            // 재료/알러지-상품맵핑정보
            prodMapper.deleteProdInfoBarcd(prodVO);               // 상품-바코드
            prodMapper.deleteProdInfoDlvrProdNm(prodVO);          // 배달앱 상품명-맵핑정보
            prodMapper.deleteProdInfoOption(prodVO);              // 마스터-키오스크옵션상품
            prodMapper.deleteProdInfoRecpProd(prodVO);            // 재료-상품맵핑정보
            prodMapper.deleteProdInfoSalePrice(prodVO);           // 상품 판매금액
            prodMapper.deleteProdInfoVendorProd(prodVO);          // 거래처별_취급상품
            prodMapper.deleteProdInfoUnitstProd(prodVO);          // 상품_세트구성_상품
            prodMapper.deleteProdInfoTouchKey(prodVO);            // 상품_판매터치키 영구삭제

            if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ ){
                prodMapper.deleteProdInfoHqProductStore(prodVO);  // 본사) 상품별_취급매장
            }else{
                prodMapper.deleteProdInfoPrintProd(prodVO);       // 매장) 주방프린터_출력상품
                prodMapper.deleteProdInfoSdselProd(prodVO);       // 매장) 상품_사이드선택_상품
            }

            LOGGER.info("전체_이미지삭제_시작");

            // 상품이미지 삭제
            try{
                // 저장 경로 설정
                String path_folder = "";

                // 접속권한에 따른 경로 셋팅
                if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ ){
                    path_folder = prodVO.getHqOfficeCd();
                }else{
                    path_folder = prodVO.getStoreCd();
                }

                // 서버 저장 경로 설정 (imgFg -> 001: 기본이미지, 002: KIOSK이미지, 003: DID이미지)
                //String path = "D:\\prod_img\\" + path_folder + "/";
                String path = BaseEnv.FILE_UPLOAD_DIR + "prod_img/" + path_folder + "/";

                // 삭제할 상품이미지 파일명 가져오기
                List<DefaultMap<String>> imgList = prodMapper.getProdImgInfo(prodVO);

                if(imgList.size() > 0){
                    for(DefaultMap<String> list : imgList){
                        // 서버 파일 삭제
                        File delFile = new File(path + list.getStr("imgFg") + "/" + list.getStr("imgFileNm"));
                        if(delFile.exists()) {
                            delFile.delete();
                            LOGGER.info("전체_이미지삭제 : " + path + list.getStr("imgFg") + "/" + list.getStr("imgFileNm"));
                        }
                    }
                }

            }catch(Exception e) {
                LOGGER.info("전체_이미지삭제_오류 : " + e.getMessage());
            }

            // 본사인경우, 매장 상품이미지도 삭제
            // 여기서 삭제하는 이유는, 본사 상품이미지정보 삭제시 트리거로 인해 매장상품이미지 정보도 같이 삭제되어 버려서 서버의 매장상품이미지를 삭제할 수 없게됨.
            if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ ){

                LOGGER.info("전체_본사상품_매장삭제_이미지삭제_시작");

                // 매장상품이미지 삭제
                try{

                    // 서버 저장 경로 설정 (imgFg -> 001: 기본이미지, 002: KIOSK이미지, 003: DID이미지)
                    //String path = "D:\\prod_img\\";
                    String path = BaseEnv.FILE_UPLOAD_DIR + "prod_img/";

                    // 삭제할 상품이미지 파일명 가져오기
                    List<DefaultMap<String>> imgList = prodMapper.getProdImgInfoAllStore(prodVO);

                    if(imgList.size() > 0){
                        for(DefaultMap<String> list : imgList){
                            // 서버 파일 삭제
                            File delFile = new File(path + list.getStr("storeCd") + "/" + list.getStr("imgFg") + "/" + list.getStr("imgFileNm"));
                            if(delFile.exists()) {
                                delFile.delete();
                                LOGGER.info("전체_본사상품_매장삭제_이미지삭제 : " + path + list.getStr("storeCd") + "/" + list.getStr("imgFg") + "/" + list.getStr("imgFileNm"));
                            }
                        }
                    }

                }catch(Exception e) {
                    LOGGER.info("전체_본사상품_매장삭제_이미지삭제_오류 : " + e.getMessage());
                }
            }

            prodMapper.deleteProdInfoImage(prodVO);               // 상품 이미지
            prodMapper.deleteProdInfo(prodVO);                    // 상품정보

            // 본사인경우, 본사에 속한 매장상품정보도 영구삭제
            if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ ){

                prodMapper.deleteProdInfoProdInfoAllStore(prodVO);            // 상품추가정보
                prodMapper.deleteProdInfoSaleTimeAllStore(prodVO);            // 키오스크판매시간대정보
                prodMapper.deleteProdInfoCouponProdAllStore(prodVO);          // 쿠폰적용상품
                prodMapper.deleteProdInfoKioskKeyAllStore(prodVO);            // 키오스크 키맵설정
                prodMapper.deleteProdInfoKioskRecmdAllStore(prodVO);          // 키오스크 추천메뉴정보
                prodMapper.deleteProdInfoKioskRecmdProdAllStore(prodVO);      // 키오스크 메뉴추천리스트
                prodMapper.deleteProdInfoAlgiProdAllStore(prodVO);            // 재료/알러지-상품맵핑정보
                prodMapper.deleteProdInfoBarcdAllStore(prodVO);               // 상품-바코드
                prodMapper.deleteProdInfoDlvrProdNmAllStore(prodVO);          // 배달앱 상품명-맵핑정보
                prodMapper.deleteProdInfoOptionAllStore(prodVO);              // 마스터-키오스크옵션상품
                prodMapper.deleteProdInfoRecpProdAllStore(prodVO);            // 재료-상품맵핑정보
                prodMapper.deleteProdInfoSalePriceAllStore(prodVO);           // 상품 판매금액
                prodMapper.deleteProdInfoVendorProdAllStore(prodVO);          // 거래처별_취급상품
                prodMapper.deleteProdInfoUnitstProdAllStore(prodVO);          // 상품_세트구성_상품
                prodMapper.deleteProdInfoTouchKeyAllStore(prodVO);            // 상품_판매터치키
                prodMapper.deleteProdInfoPrintProdAllStore(prodVO);           // 매장) 주방프린터_출력상품
                prodMapper.deleteProdInfoSdselProdAllStore(prodVO);           // 매장) 상품_사이드선택_상품


                LOGGER.info("전체_본사상품_매장삭제_이미지삭제2_시작");

                // 매장상품이미지 삭제
                try{

                    // 서버 저장 경로 설정 (imgFg -> 001: 기본이미지, 002: KIOSK이미지, 003: DID이미지)
                    //String path = "D:\\prod_img\\";
                    String path = BaseEnv.FILE_UPLOAD_DIR + "prod_img/";

                    // 삭제할 상품이미지 파일명 가져오기
                    List<DefaultMap<String>> imgList = prodMapper.getProdImgInfoAllStore(prodVO);

                    if(imgList.size() > 0){
                        for(DefaultMap<String> list : imgList){
                            // 서버 파일 삭제
                            File delFile = new File(path + list.getStr("storeCd") + "/" + list.getStr("imgFg") + "/" + list.getStr("imgFileNm"));
                            if(delFile.exists()) {
                                delFile.delete();
                                LOGGER.info("전체_본사상품_매장삭제_이미지삭제2 : " + path + list.getStr("storeCd") + "/" + list.getStr("imgFg") + "/" + list.getStr("imgFileNm"));
                            }
                        }
                    }

                }catch(Exception e) {
                    LOGGER.info("전체_본사상품_매장삭제_이미지삭제_오류2 : " + e.getMessage());
                }

                prodMapper.deleteProdInfoImageAllStore(prodVO);               // 상품 이미지
                prodMapper.deleteProdInfoAllStore(prodVO);                    // 상품정보
            }
        }

        // 선택상품 '미사용' 처리
        if(!StringUtil.getOrBlank(modProdSeq).equals("")){

            prodVO.setDelTypeFg("1"); // 영구삭제불가('미사용' 처리)
            prodMapper.updateProdUseYn(prodVO);

            // 본사인경우, 매장의 상품정보도 '미사용' 처리
            if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ ) {
                prodMapper.updateStoreProdUseYn(prodVO);
            }
        }

        // 완료 본사/매장의 임시테이블 초기화
        prodMapper.deleteAllTmpDelProduct(prodVO);

        return result;
    }

    /** KIOSK 판매시간 시간설정 조회 */
    @Override
    public List<DefaultMap<String>> getProdSaleTime(ProdVO prodVO, SessionInfoVO sessionInfoVO) {

        prodVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        prodVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        prodVO.setStoreCd(sessionInfoVO.getStoreCd());

        return prodMapper.getProdSaleTime(prodVO);
    }

}