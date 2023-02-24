package kr.co.solbipos.base.price.salePrice.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.price.salePrice.service.SalePriceService;
import kr.co.solbipos.base.price.salePrice.service.SalePriceVO;
import kr.co.solbipos.base.prod.prod.service.enums.WorkModeFg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

import static kr.co.common.utils.DateUtil.currentDateString;
import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : SalePriceServiceImpl.java
 * @Description : 기초관리 - 가격관리 - 판매가격관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.12.20  김지은       최초생성
 *
 * @author 솔비포스 김지은
 * @since 2018. 12.20
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("salePriceService")
public class SalePriceServiceImpl implements SalePriceService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final MessageService messageService;
    private final SalePriceMapper salePriceMapper;
    private final CmmEnvUtil cmmEnvUtil;

    /** Constructor Injection */
    @Autowired
    public SalePriceServiceImpl(SalePriceMapper salePriceMapper, CmmEnvUtil cmmEnvUtil, MessageService messageService) {
        this.salePriceMapper = salePriceMapper;
        this.cmmEnvUtil = cmmEnvUtil;
        this.messageService = messageService;
    }

    /** 상품별 가격정보 조회 */
    @Override
    public DefaultMap<String> getProdInfo(SalePriceVO salePriceVO, SessionInfoVO sessionInfoVO) {

        salePriceVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return salePriceMapper.getProdInfo(salePriceVO);
    }

    /** 상품별 매장 판매가 조회 */
    @Override
    public List<DefaultMap<String>> getProdSalePriceList(SalePriceVO salePriceVO, SessionInfoVO sessionInfoVO) {

        salePriceVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(!StringUtil.getOrBlank(salePriceVO.getStoreCd()).equals("")) {
            salePriceVO.setArrStoreCd(salePriceVO.getStoreCd().split(","));
        }

        return salePriceMapper.getProdSalePriceList(salePriceVO);
    }

    /** 상품별 매장 판매가 저장 */
    @Override
    public int saveProdSalePrice(SalePriceVO[] salePriceVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;
        String currentDate = currentDateString();
        String currentDt = currentDateTimeString();

        // 판매가 본사 통제여부 체크 - 본사통제 여부가 '매장'일 경우 저장로직 수행하지 않음.
//        PriceEnvFg priceEnvstVal = PriceEnvFg.getEnum(cmmEnvUtil.getHqEnvst(sessionInfoVO, "0022"));

//        if(priceEnvstVal == PriceEnvFg.STORE){
//            return result;
//        }

        for(SalePriceVO salePriceVO : salePriceVOs) {

            salePriceVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
//            salePriceVO.setPrcCtrlFg("1"); // 본사에서 등록
            salePriceVO.setStartDate(currentDate);
            salePriceVO.setEndDate("99991231");
            salePriceVO.setRegDt(currentDt);
            salePriceVO.setRegId(sessionInfoVO.getUserId());
            salePriceVO.setModDt(currentDt);
            salePriceVO.setModId(sessionInfoVO.getUserId());

            // 판매가 변경 히스토리 등록
            int prodCnt = salePriceMapper.getRegistProdCount(salePriceVO);

            if(prodCnt > 0){
                result = salePriceMapper.updateStoreProdSalePriceHistory(salePriceVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }
            // todo 추후 최초 판매가도 히스토리 등록할 경우에 이 주석 해제하여 사용
//            else {
//                result = salePriceMapper.insertStoreProdSalePriceHistory(salePriceVO);
//                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
//            }

            // 매장 판매가 변경
            result = salePriceMapper.modifyStoreProdSalePrice(salePriceVO);
//            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        }
        return result;
    }

    /** 매장별 판매가 목록 조회 */
    @Override
    public List<DefaultMap<String>> getStoreSalePriceList(SalePriceVO salePriceVO, SessionInfoVO sessionInfoVO) {

        salePriceVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        salePriceVO.setUserId(sessionInfoVO.getUserId());

        return salePriceMapper.getStoreSalePriceList(salePriceVO);
    }

    /** 본사 가격정보 조회 */
    @Override
    public List<DefaultMap<String>> getHqSalePriceList(SalePriceVO salePriceVO, SessionInfoVO sessionInfoVO) {

        salePriceVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        salePriceVO.setUserId(sessionInfoVO.getUserId());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 선택한 상품브랜드가 없을 때 (상품브랜드가 '전체' 일때)
            if (salePriceVO.getProdHqBrandCd() == "" || salePriceVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                if (salePriceVO.getUserProdBrands() != null && !"".equals(salePriceVO.getUserProdBrands())) {
                    String[] userBrandList = salePriceVO.getUserProdBrands().split(",");
                    if (userBrandList.length > 0) {
                        salePriceVO.setUserProdBrandList(userBrandList);
                    }
                }
            }
        }

        return salePriceMapper.getHqSalePriceList(salePriceVO);
    }

    /** 본사 판매가 저장 */
    @Override
    public int saveHqProdSalePrice(SalePriceVO[] salePriceVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;
        int result2 = 0;
        String currentDate = currentDateString();
        String currentDt = currentDateTimeString();

        for(SalePriceVO salePriceVO : salePriceVOs) {

            salePriceVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            salePriceVO.setStartDate(currentDate);
            salePriceVO.setEndDate("99991231");
            salePriceVO.setRegDt(currentDt);
            salePriceVO.setRegId(sessionInfoVO.getUserId());
            salePriceVO.setModDt(currentDt);
            salePriceVO.setModId(sessionInfoVO.getUserId());

            // 판매가 변경 히스토리 등록
            int prodCnt = salePriceMapper.getRegistHqProdCount(salePriceVO);

            if(prodCnt > 0){
                result = salePriceMapper.updateHqProdSalePriceHistory(salePriceVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }
            // todo 추후 최초 판매가도 히스토리 등록할 경우에 이 주석 해제하여 사용

            // 본사 판매가 변경
            result = salePriceMapper.modifyHqProdSalePrice(salePriceVO);

            if(salePriceVO.getApplyFg().equals("true")){
                salePriceVO.setWorkMode(WorkModeFg.MOD_PROD);
                //전매장 적용, 상품이 있으면 머지 업데이트 처리
                //String storeSalePriceReulst = salePriceMapper.saveStoreSalePrice(salePriceVO);
                result2 = salePriceMapper.modifyMsProdSalePrice(salePriceVO);
            }
        }
        return result;
    }

    /** 본사판매가관리 엑셀업로드 탭 - 엑셀 양식다운로드 조회 */
    @Override
    public List<DefaultMap<String>> getHqSalePriceExcelUploadSampleList(SalePriceVO salePriceVO, SessionInfoVO sessionInfoVO) {

        salePriceVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        salePriceVO.setUserId(sessionInfoVO.getUserId());

        return salePriceMapper.getHqSalePriceExcelUploadSampleList(salePriceVO);
    }

    /** 검증결과 조회 */
    @Override
    public List<DefaultMap<String>> getHqSalePriceExcelUploadCheckList(SalePriceVO salePriceVO, SessionInfoVO sessionInfoVO) {

        salePriceVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        salePriceVO.setSessionId(sessionInfoVO.getUserId());

        return salePriceMapper.getHqSalePriceExcelUploadCheckList(salePriceVO);
    }

    /** 검증결과 전체 삭제 */
    @Override
    public int getSalePriceExcelUploadCheckDeleteAll(SalePriceVO salePriceVO, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        salePriceVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        salePriceVO.setSessionId(sessionInfoVO.getUserId());

        procCnt += salePriceMapper.getSalePriceExcelUploadCheckDeleteAll(salePriceVO);

        return procCnt;
    }

    /** 검증결과 삭제 */
    @Override
    public int getSalePriceExcelUploadCheckDelete(SalePriceVO[] salePriceVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;

        for(SalePriceVO salePriceVO : salePriceVOs) {
            salePriceVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            salePriceVO.setSessionId(sessionInfoVO.getUserId());

            procCnt += salePriceMapper.getSalePriceExcelUploadCheckDelete(salePriceVO);
        }

        return procCnt;
    }

    /** 업로드시 임시테이블 저장 */
    @Override
    public int getSalePriceExcelUploadCheckSave(SalePriceVO[] salePriceVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        int i = 1;
        String currentDt = currentDateTimeString();

        for(SalePriceVO salePriceVO : salePriceVOs) {
            salePriceVO.setRegDt(currentDt);
            salePriceVO.setRegId(sessionInfoVO.getUserId());
            salePriceVO.setModDt(currentDt);
            salePriceVO.setModId(sessionInfoVO.getUserId());

            salePriceVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            salePriceVO.setSessionId(sessionInfoVO.getUserId());
            salePriceVO.setSeq(i);

            salePriceVO.setResult("검증전");

            procCnt += salePriceMapper.getSalePriceExcelUploadCheckSave(salePriceVO);
            i++;
        }

        return procCnt;
    }

    /** 검증결과 저장 */
    @Override
    public int getSalePriceExcelUploadCheckSaveAdd(SalePriceVO[] salePriceVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        int i = 1;
        String currentDt = currentDateTimeString();
        String pattern = "^[0-9]*$"; //숫자만

        for(SalePriceVO salePriceVO : salePriceVOs) {
            salePriceVO.setRegDt(currentDt);
            salePriceVO.setRegId(sessionInfoVO.getUserId());
            salePriceVO.setModDt(currentDt);
            salePriceVO.setModId(sessionInfoVO.getUserId());

            salePriceVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            salePriceVO.setSessionId(sessionInfoVO.getUserId());
            salePriceVO.setSeq(i);

            // 상품코드
            if(salePriceMapper.getProdCdChk(salePriceVO) > 0) {
                // 가격관리구분이 본사인 경우만 수정
                if(salePriceVO.getPrcCtrlFg() != null && !"".equals(salePriceVO.getPrcCtrlFg())) {
                    if(("H").equals(salePriceVO.getPrcCtrlFg())) {
                    } else {
                        salePriceVO.setResult("가격관리구분이 본사인 상품만 수정 가능합니다.");
                    }
                }
            } else {
                salePriceVO.setResult("존재하지 않는 상품코드입니다");
            }

            // 숫자가 입력되어야 하는 컬럼에 문자가 입력되면 null처리
            if(salePriceVO.getSaleUprc() != null && !"".equals(salePriceVO.getSaleUprc())) {
                if(Pattern.matches(pattern, salePriceVO.getSaleUprc())) {
                    salePriceVO.setSaleUprc(salePriceVO.getSaleUprc());
                } else {
                    salePriceVO.setResult("판매가는 숫자만 입력가능합니다.");
                }
            } else {
                salePriceVO.setResult("판매가를 입력해주세요.");
            }

            if (salePriceVO.getResult() == null || salePriceVO.getResult() == "") {
                salePriceVO.setResult("검증성공");
            }

            procCnt += salePriceMapper.getSalePriceExcelUploadCheckSave(salePriceVO);
            i++;
        }

        return procCnt;
    }

    /** 본사판매가관리 엑셀업로드 탭 - 판매가 저장 */
    @Override
    public int getHqSalePriceExcelUploadSave(SalePriceVO[] salePriceVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;
        int result2 = 0;
        String currentDt = currentDateTimeString();
        String currentDate = currentDateString();

        for(SalePriceVO salePriceVO : salePriceVOs) {
            salePriceVO.setRegDt(currentDt);
            salePriceVO.setRegId(sessionInfoVO.getUserId());
            salePriceVO.setModDt(currentDt);
            salePriceVO.setModId(sessionInfoVO.getUserId());

            salePriceVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            salePriceVO.setSessionId(sessionInfoVO.getUserId());
            salePriceVO.setStartDate(currentDate);
            salePriceVO.setEndDate("99991231");

            if(("검증성공").equals(salePriceVO.getResult())) {
                if(("H").equals(salePriceVO.getPrcCtrlFg())) {

                    // 판매가 변경 히스토리 등록
                    int prodCnt = salePriceMapper.getRegistHqProdCount(salePriceVO);

                    if(prodCnt > 0){
                        result = salePriceMapper.updateHqProdSalePriceHistory(salePriceVO);
                        if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                    }

                    // 본사 판매가 변경
                    result = salePriceMapper.modifyHqProdSalePrice(salePriceVO);

                    // 전매장적용
                    if(salePriceVO.getApplyFg().equals("true")){
                        salePriceVO.setWorkMode(WorkModeFg.MOD_PROD);
                        //전매장 적용, 상품이 있으면 머지 업데이트 처리
                        //String storeSalePriceReulst = salePriceMapper.saveStoreSalePrice(salePriceVO);
                        result2 = salePriceMapper.modifyMsProdSalePrice(salePriceVO);
                    }

                    // 저장완료된 검증결과만 삭제
                    result += salePriceMapper.getSalePriceExcelUploadCheckDelete(salePriceVO);
                }
            }
        }

        return result;
    }

}