package kr.co.solbipos.base.prod.prod.service.impl;

import kr.co.common.system.BaseEnv;
import kr.co.common.data.enums.Status;
import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.prod.service.ProdService;
import kr.co.solbipos.base.prod.prod.service.ProdVO;
import kr.co.solbipos.base.prod.prod.service.enums.PriceEnvFg;
import kr.co.solbipos.base.prod.prod.service.enums.ProdEnvFg;
import kr.co.solbipos.base.prod.prod.service.enums.ProdNoEnvFg;
import kr.co.solbipos.base.prod.prod.service.enums.WorkModeFg;
import kr.co.solbipos.stock.adj.adj.service.AdjVO;
import kr.co.solbipos.stock.adj.adj.service.impl.AdjMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
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
        if("00000".equals(hqOfficeCd)) { // 단독매장
            prodVO.setSalePrcFg("2");
        } else {

            String envstVal = StringUtil.getOrBlank(cmmEnvUtil.getHqEnvst(sessionInfoVO, "0022"));

            if( StringUtil.isEmpties(storeCd)) { // 본사일때
                prodVO.setSalePrcFg("1");
            } else {                             // 매장일때
                if("1".equals(envstVal)) prodVO.setSalePrcFg("1");
                else                     prodVO.setSalePrcFg("2");
            }
        }
        return prodMapper.getProdList(prodVO);
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
        if("00000".equals(hqOfficeCd)) { // 단독매장
            prodVO.setSalePrcFg("2");
        } else {

            String envstVal = StringUtil.getOrBlank(cmmEnvUtil.getHqEnvst(sessionInfoVO, "0022"));

            if( StringUtil.isEmpties(storeCd)) { // 본사일때
                prodVO.setSalePrcFg("1");
            } else {                             // 매장일때
                if("1".equals(envstVal)) prodVO.setSalePrcFg("1");
                else                     prodVO.setSalePrcFg("2");
            }
        }

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

        // WorkMode Flag 상품정보수정으로 기본 셋팅_2019.06.06
        prodVO.setWorkMode(WorkModeFg.MOD_PROD);

        // 상품등록 본사 통제여부
        ProdEnvFg prodEnvstVal = ProdEnvFg.getEnum(cmmEnvUtil.getHqEnvst(sessionInfoVO, "0020"));
        // 판매가 본사 통제여부
        PriceEnvFg priceEnvstVal = PriceEnvFg.getEnum(cmmEnvUtil.getHqEnvst(sessionInfoVO, "0022"));

        // 상품 신규등록일때 상품코드 조회
        /*if( StringUtil.isEmpties( prodVO.getProdCd())) {
            String prodCd = prodMapper.getProdCd(prodVO);
            prodVO.setProdCd(prodCd);
            // 신규상품등록 인 경우 WorkMode Flag 변경_2019.06.06
            prodVO.setWorkMode(WorkModeFg.REG_PROD);
        }*/

        // 상품 신규등록이면서 자동채번인 경우 상품코드 조회
        if(prodVO.getProdNoEnv() == ProdNoEnvFg.AUTO && prodVO.getSaveMode().equals("REG")){
            String prodCd = prodMapper.getProdCd(prodVO);
            prodVO.setProdCd(prodCd);
        }
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

        // 매장에서 매장상품 등록시에 가격관리 구분 등록
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ)  prodVO.setPrcCtrlFg("H"); //본사
        else                                        prodVO.setPrcCtrlFg("S"); //매장


        // 상품정보 저장
//        int result = prodMapper.saveProductInfo(prodVO);
        long result = prodMapper.saveProductInfo(prodVO);
        if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        //상품 바코드 저장(바코드정보가 있을 경우만)
        if(prodVO.getBarCd() != null && prodVO.getBarCd().length() > 0){
            int barCdResult = prodMapper.saveProdBarcd(prodVO);
            if(barCdResult <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }

        // [상품등록 - 본사통제시] 본사에서 상품정보 수정시 매장에 수정정보 내려줌
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ  && prodEnvstVal == ProdEnvFg.HQ) {

            String procResult;

            if(prodExist == 0) {
                procResult = prodMapper.insertHqProdToStoreProd(prodVO);

                // 상품분류 매장에 INSERT
                prodMapper.insertClsHqToStore(prodVO);

            } else {
                procResult = prodMapper.updateHqProdToStoreProd(prodVO);

                // 상품분류 매장에 UPDATE
                prodMapper.updateClsHqToStore(prodVO);
            }

            //매장 상품 바코드 저장(바코드정보가 있을 경우만)
            if(prodVO.getBarCd() != null && prodVO.getBarCd().length() > 0){
                prodMapper.saveProdBarcdStore(prodVO);
            }

            // 매장 사이드 선택메뉴 그룹/분류/상품 저장(사이드 선택메뉴를 사용하는 경우만)
            if("Y".equals(prodVO.getSideProdYn()) && prodVO.getSdselGrpCd().length() > 0){

                //그룹(sdselGrp)
                prodMapper.insertSdselGrpToStore(prodVO);
                //분류(sdselClass)
                prodMapper.insertSdselClassToStore(prodVO);
                //상품(sdselProd)
                prodMapper.insertSdselProdToStore(prodVO);
            }
        }

        // 상품 판매가 저장
        if(priceEnvstVal == PriceEnvFg.HQ)  prodVO.setSalePrcFg("1");
        else                                prodVO.setSalePrcFg("2");

        prodVO.setStartDate(currentDate);
        prodVO.setEndDate("99991231");

        int salePriceReeulst = prodMapper.saveSalePrice(prodVO);
        if(salePriceReeulst <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        // 상품 판매가 변경 히스토리 저장
        int hqSalePriceHistResult = prodMapper.saveSalePriceHistory(prodVO);
        if(hqSalePriceHistResult <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        // [판매가 - 본사통제시] 본사에서 상품정보 수정시 매장에 수정정보 내려줌
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ  && priceEnvstVal == PriceEnvFg.HQ) {
            String storeSalePriceReeulst = prodMapper.saveStoreSalePrice(prodVO);
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

        // 해당 상품이 사이드 선택메뉴를 사용하는지 파악
        String sideSelYn = "N";
        if("Y".equals(prodVOs[0].getSideProdYn()) && prodVOs[0].getSdselGrpCd() != null && prodVOs[0].getSdselGrpCd().length() > 0){
            sideSelYn = "Y";
        }

       for(ProdVO prodVO : prodVOs) {
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

            // [판매가 - 본사통제시] 본사에서 상품정보 수정시 매장에 수정정보 내려줌
            String storeSalePriceReulst = prodMapper.saveStoreSalePrice(prodVO);

            // 판매가가 기존 판매가와 다른 경우
            if(!prodVO.getSaleUprc().equals(prodVO.getSaleUprcB())) {

                prodVO.setSalePrcFg("1"); // 본사에서 변경
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
            prodVO.setSalePrcFg("1"); // 본사에서 변경
            prodVO.setStartDate(currentDateString());
            prodVO.setEndDate("99991231");
            prodVO.setRegDt(currentDate);
            prodVO.setRegId(sessionInfoVO.getUserId());
            prodVO.setModDt(currentDate);
            prodVO.setModId(sessionInfoVO.getUserId());

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

    /** 매장 적용/미적용 상품 조회 */
    @Override
    public List<DefaultMap<String>> getStoreProdBatchList(ProdVO prodVO, SessionInfoVO sessionInfoVO) {

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

            // [판매가 - 본사통제시] 본사에서 상품정보 수정시 매장에 수정정보 내려줌
            String storeSalePriceReulst = prodMapper.saveStoreSalePrice(prodVO);

            // 판매가가 기존 판매가와 다른 경우
            if(!prodVO.getSaleUprc().equals(prodVO.getSaleUprcB())) {

                prodVO.setSalePrcFg("1"); // 본사에서 변경
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

                if(mFile.getOriginalFilename().lastIndexOf('.') > 1) {
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

            // 선택한 파일이 없으면
            if(fileList.size() == 0) {
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
            }

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
}