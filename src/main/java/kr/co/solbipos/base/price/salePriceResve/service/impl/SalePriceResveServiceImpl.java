package kr.co.solbipos.base.price.salePriceResve.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.price.salePrice.service.impl.SalePriceMapper;
import kr.co.solbipos.base.price.salePriceResve.service.SalePriceResveService;
import kr.co.solbipos.base.price.salePriceResve.service.SalePriceResveVO;
import kr.co.solbipos.base.price.salePrice.service.SalePriceVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateString;
import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : SalePriceResveServiceImpl.java
 * @Description : 기초관리 - 가격관리 - 가격예약
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.04.05  이다솜       최초생성
 *
 * @author 솔비포스 WEB개발팀 이다솜
 * @since 2022.04.05
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("salePriceResveService")
public class SalePriceResveServiceImpl implements SalePriceResveService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final MessageService messageService;
    private final SalePriceResveMapper salePriceResveMapper;
    private final CmmEnvUtil cmmEnvUtil;
    private final SalePriceMapper salePriceMapper; // 본사판매가관리

    /** Constructor Injection */
    @Autowired
    public SalePriceResveServiceImpl(SalePriceResveMapper salePriceResveMapper, CmmEnvUtil cmmEnvUtil, MessageService messageService, SalePriceMapper salePriceMapper) {
        this.salePriceResveMapper = salePriceResveMapper;
        this.cmmEnvUtil = cmmEnvUtil;
        this.messageService = messageService;
        this.salePriceMapper = salePriceMapper;
    }

    /** 가격예약(본사판매가) 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getHqSalePriceResveList(SalePriceResveVO salePriceResveVO, SessionInfoVO sessionInfoVO) {

        salePriceResveVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return salePriceResveMapper.getHqSalePriceResveList(salePriceResveVO);
    }

    /** 가격예약(본사판매가) 추가 */
    @Override
    public int saveHqSalePriceResve(SalePriceResveVO[] salePriceResveVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;
        String currentDt = currentDateTimeString();

        for(SalePriceResveVO salePriceResveVO : salePriceResveVOs) {

            salePriceResveVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            salePriceResveVO.setSaleResveFg("1"); // 가격예약구분 0:일반, 1:예약
            salePriceResveVO.setRegDt(currentDt);
            salePriceResveVO.setRegId(sessionInfoVO.getUserId());
            salePriceResveVO.setModDt(currentDt);
            salePriceResveVO.setModId(sessionInfoVO.getUserId());

            // 해당 시작날짜에 등록된 가격이 있는지 조회(판매가 히스토리 등록을 위해)
            int prodCnt = salePriceResveMapper.getHqSalePriceCnt(salePriceResveVO);

            if(prodCnt > 0){ // 기존 판매가 히스토리에 저장
                result = salePriceResveMapper.insertHqSalePriceHistory(salePriceResveVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }
            // todo 추후 최초 판매가도 히스토리 등록할 경우에 이 주석 해제하여 사용

            // 새 예약 판매가 등록
            result = salePriceResveMapper.insertHqSalePrice(salePriceResveVO);

            // 전매장적용 체크시, 본사 예약 판매가 매장적용
            if(salePriceResveVO.getApplyFg().equals("true")){
                salePriceResveMapper.insertHqSalePriceToStore(salePriceResveVO);
            }
        }
        return result;
    }

    /** 가격예약(본사판매가) 삭제 */
    @Override
    public int delHqSalePriceResve(SalePriceResveVO[] salePriceResveVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;

        for(SalePriceResveVO salePriceResveVO : salePriceResveVOs) {
            salePriceResveVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            // 기존 예약 판매가 삭제
            salePriceResveMapper.deleteHqSalePriceResve(salePriceResveVO);

        }
        return result;
    }

    /** 가격예약(본사판매가) 수정 */
    @Override
    public int modHqSalePriceResve(SalePriceResveVO[] salePriceResveVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;
        String currentDt = currentDateTimeString();

        for(SalePriceResveVO salePriceResveVO : salePriceResveVOs) {

            salePriceResveVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            salePriceResveVO.setSaleResveFg("1"); // 가격예약구분 0:일반, 1:예약
            salePriceResveVO.setRegDt(currentDt);
            salePriceResveVO.setRegId(sessionInfoVO.getUserId());
            salePriceResveVO.setModDt(currentDt);
            salePriceResveVO.setModId(sessionInfoVO.getUserId());

            // 기존 예약 판매가 삭제
            salePriceResveMapper.deleteHqSalePriceResve(salePriceResveVO);

            // 해당 시작날짜에 등록된 가격이 있는지 조회(판매가 히스토리 등록을 위해)
            int prodCnt = salePriceResveMapper.getHqSalePriceCnt(salePriceResveVO);

            if(prodCnt > 0){ // 기존 판매가 히스토리에 저장
                result = salePriceResveMapper.insertHqSalePriceHistory(salePriceResveVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }
            // todo 추후 최초 판매가도 히스토리 등록할 경우에 이 주석 해제하여 사용

            // 새 예약 판매가 등록
            result = salePriceResveMapper.insertHqSalePrice(salePriceResveVO);

            // 전매장적용 체크시, 본사 예약 판매가 매장적용
            if(salePriceResveVO.getApplyFg().equals("true")){
                salePriceResveMapper.insertHqSalePriceToStore(salePriceResveVO);
            }
        }
        return result;
    }

    /** 가격예약(본사판매가) 상품가격정보 조회 */
    @Override
    public List<DefaultMap<String>> getHqSalePriceInfo(SalePriceResveVO salePriceResveVO, SessionInfoVO sessionInfoVO) {

        salePriceResveVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return salePriceResveMapper.getHqSalePriceInfo(salePriceResveVO);
    }

    /** 가격예약(매장판매가) [상품별 판매가관리] 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getStoreProdSalePriceResveList(SalePriceResveVO salePriceResveVO, SessionInfoVO sessionInfoVO) {

        salePriceResveVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(!StringUtil.getOrBlank(salePriceResveVO.getStoreCd()).equals("")) {
            salePriceResveVO.setArrStoreCd(salePriceResveVO.getStoreCd().split(","));

        }

        return salePriceResveMapper.getStoreProdSalePriceResveList(salePriceResveVO);
    }

    /** 가격예약(매장판매가) [매장별 판매가관리] 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getStoreStoreSalePriceResveList(SalePriceResveVO salePriceResveVO, SessionInfoVO sessionInfoVO) {

        salePriceResveVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return salePriceResveMapper.getStoreStoreSalePriceResveList(salePriceResveVO);
    }

    /** 가격예약(매장판매가) 추가 */
    @Override
    public int saveStoreProdSalePriceResve(SalePriceResveVO[] salePriceResveVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;
        String currentDt = currentDateTimeString();

        for(SalePriceResveVO salePriceResveVO : salePriceResveVOs) {

            salePriceResveVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            salePriceResveVO.setSaleResveFg("1"); // 가격예약구분 0:일반, 1:예약
            salePriceResveVO.setRegDt(currentDt);
            salePriceResveVO.setRegId(sessionInfoVO.getUserId());
            salePriceResveVO.setModDt(currentDt);
            salePriceResveVO.setModId(sessionInfoVO.getUserId());

            // 해당 시작날짜에 등록된 가격이 있는지 조회(판매가 히스토리 등록을 위해)
            int prodCnt = salePriceResveMapper.getStoreSalePriceCnt(salePriceResveVO);

            if(prodCnt > 0){ // 기존 판매가 히스토리에 저장
                result = salePriceResveMapper.insertStoreSalePriceHistory(salePriceResveVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }
            // todo 추후 최초 판매가도 히스토리 등록할 경우에 이 주석 해제하여 사용

            // 새 예약 판매가 등록
            result = salePriceResveMapper.insertStoreSalePrice(salePriceResveVO);

        }
        return result;
    }

    /** 가격예약(매장판매가) 삭제 */
    @Override
    public int delStoreProdSalePriceResve(SalePriceResveVO[] salePriceResveVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;

        for(SalePriceResveVO salePriceResveVO : salePriceResveVOs) {

            salePriceResveVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

            // 기존 예약 판매가 삭제
            salePriceResveMapper.deleteStoreSalePriceResve(salePriceResveVO);

        }
        return result;
    }

    /** 가격예약(매장판매가) 수정 */
    @Override
    public int modStoreProdSalePriceResve(SalePriceResveVO[] salePriceResveVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;
        String currentDt = currentDateTimeString();

        for(SalePriceResveVO salePriceResveVO : salePriceResveVOs) {

            salePriceResveVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            salePriceResveVO.setSaleResveFg("1"); // 가격예약구분 0:일반, 1:예약
            salePriceResveVO.setRegDt(currentDt);
            salePriceResveVO.setRegId(sessionInfoVO.getUserId());
            salePriceResveVO.setModDt(currentDt);
            salePriceResveVO.setModId(sessionInfoVO.getUserId());

            // 기존 예약 판매가 삭제
            salePriceResveMapper.deleteStoreSalePriceResve(salePriceResveVO);

            // 해당 시작날짜에 등록된 가격이 있는지 조회(판매가 히스토리 등록을 위해)
            int prodCnt = salePriceResveMapper.getStoreSalePriceCnt(salePriceResveVO);

            if(prodCnt > 0){ // 기존 판매가 히스토리에 저장
                result = salePriceResveMapper.insertStoreSalePriceHistory(salePriceResveVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }
            // todo 추후 최초 판매가도 히스토리 등록할 경우에 이 주석 해제하여 사용

            // 새 예약 판매가 등록
            result = salePriceResveMapper.insertStoreSalePrice(salePriceResveVO);

        }
        return result;
    }

    /** 가격예약(매장판매가) 상품가격정보 조회 */
    public List<DefaultMap<String>> getStoreSalePriceInfo(SalePriceResveVO salePriceResveVO, SessionInfoVO sessionInfoVO){

        salePriceResveVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return salePriceResveMapper.getStoreSalePriceInfo(salePriceResveVO);
    }

    /** 가격예약(판매가관리) 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getSalePriceResveList(SalePriceResveVO salePriceResveVO, SessionInfoVO sessionInfoVO) {

        salePriceResveVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        salePriceResveVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        salePriceResveVO.setStoreCd(sessionInfoVO.getStoreCd());

        return salePriceResveMapper.getSalePriceResveList(salePriceResveVO);
    }

    /** 가격예약(판매가관리) 추가 */
    @Override
    public int saveSalePriceResve(SalePriceResveVO[] salePriceResveVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;
        String currentDt = currentDateTimeString();

        for(SalePriceResveVO salePriceResveVO : salePriceResveVOs) {

            salePriceResveVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            salePriceResveVO.setStoreCd(sessionInfoVO.getStoreCd());
            salePriceResveVO.setSaleResveFg("1"); // 가격예약구분 0:일반, 1:예약
            salePriceResveVO.setRegDt(currentDt);
            salePriceResveVO.setRegId(sessionInfoVO.getUserId());
            salePriceResveVO.setModDt(currentDt);
            salePriceResveVO.setModId(sessionInfoVO.getUserId());

            // 해당 시작날짜에 등록된 가격이 있는지 조회(판매가 히스토리 등록을 위해)
            int prodCnt = salePriceResveMapper.getStoreSalePriceCnt(salePriceResveVO);

            if(prodCnt > 0){ // 기존 판매가 히스토리에 저장
                result = salePriceResveMapper.insertStoreSalePriceHistory(salePriceResveVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }
            // todo 추후 최초 판매가도 히스토리 등록할 경우에 이 주석 해제하여 사용

            // 새 예약 판매가 등록
            result = salePriceResveMapper.insertStoreSalePrice(salePriceResveVO);

        }
        return result;
    }

    /** 가격예약(판매가관리) 삭제 */
    @Override
    public int delSalePriceResve(SalePriceResveVO[] salePriceResveVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;

        for(SalePriceResveVO salePriceResveVO : salePriceResveVOs) {

            salePriceResveVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            salePriceResveVO.setStoreCd(sessionInfoVO.getStoreCd());

            // 기존 예약 판매가 삭제
            salePriceResveMapper.deleteStoreSalePriceResve(salePriceResveVO);

        }
        return result;
    }

    /** 가격예약(판매가관리) 수정 */
    @Override
    public int modSalePriceResve(SalePriceResveVO[] salePriceResveVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;
        String currentDt = currentDateTimeString();

        for(SalePriceResveVO salePriceResveVO : salePriceResveVOs) {

            salePriceResveVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            salePriceResveVO.setStoreCd(sessionInfoVO.getStoreCd());
            salePriceResveVO.setSaleResveFg("1"); // 가격예약구분 0:일반, 1:예약
            salePriceResveVO.setRegDt(currentDt);
            salePriceResveVO.setRegId(sessionInfoVO.getUserId());
            salePriceResveVO.setModDt(currentDt);
            salePriceResveVO.setModId(sessionInfoVO.getUserId());

            // 기존 예약 판매가 삭제
            salePriceResveMapper.deleteStoreSalePriceResve(salePriceResveVO);

            // 해당 시작날짜에 등록된 가격이 있는지 조회(판매가 히스토리 등록을 위해)
            int prodCnt = salePriceResveMapper.getStoreSalePriceCnt(salePriceResveVO);

            if(prodCnt > 0){ // 기존 판매가 히스토리에 저장
                result = salePriceResveMapper.insertStoreSalePriceHistory(salePriceResveVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }
            // todo 추후 최초 판매가도 히스토리 등록할 경우에 이 주석 해제하여 사용

            // 새 예약 판매가 등록
            result = salePriceResveMapper.insertStoreSalePrice(salePriceResveVO);

        }
        return result;
    }

    /** 가격예약(판매가관리) 상품가격정보 조회 */
    public List<DefaultMap<String>> getSalePriceInfo(SalePriceResveVO salePriceResveVO, SessionInfoVO sessionInfoVO){

        salePriceResveVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        salePriceResveVO.setStoreCd(sessionInfoVO.getStoreCd());

        return salePriceResveMapper.getStoreSalePriceInfo(salePriceResveVO);
    }

    /** 가격예약(본사판매가) 엑셀업로드 탭 - 판매가 저장 */
    @Override
    public int getHqSalePriceResveExcelUploadSave(SalePriceResveVO[] salePriceResveVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;
        String currentDt = currentDateTimeString();

        for(SalePriceResveVO salePriceResveVO : salePriceResveVOs) {
            salePriceResveVO.setRegDt(currentDt);
            salePriceResveVO.setRegId(sessionInfoVO.getUserId());
            salePriceResveVO.setModDt(currentDt);
            salePriceResveVO.setModId(sessionInfoVO.getUserId());

            salePriceResveVO.setSessionId(sessionInfoVO.getSessionId());
            salePriceResveVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            salePriceResveVO.setSaleResveFg("1"); // 가격예약구분 0:일반, 1:예약

            if(("검증성공").equals(salePriceResveVO.getResult())) {
                // 가격관리구분이 본사인 경우만 수정
                if(("H").equals(salePriceResveVO.getPrcCtrlFg())) {

                    // 해당 시작날짜에 등록된 가격이 있는지 조회(판매가 히스토리 등록을 위해)
                    int prodCnt = salePriceResveMapper.getHqSalePriceCnt(salePriceResveVO);

                    if(prodCnt > 0){ // 기존 판매가 히스토리에 저장
                        result = salePriceResveMapper.insertHqSalePriceHistory(salePriceResveVO);
                        if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                    }

                    // 새 예약 판매가 등록
                    result = salePriceResveMapper.insertHqSalePrice(salePriceResveVO);

                    // 전매장적용 체크시, 본사 예약 판매가 매장적용
                    if(salePriceResveVO.getApplyFg().equals("true")){
                        salePriceResveMapper.insertHqSalePriceToStore(salePriceResveVO);
                    }

                    // 본사판매가관리
                    SalePriceVO salePriceVO = new SalePriceVO();
                    salePriceVO.setSessionId(salePriceResveVO.getSessionId());
                    salePriceVO.setHqOfficeCd(salePriceResveVO.getHqOfficeCd());
                    salePriceVO.setStoreCd(salePriceResveVO.getStoreCd());
                    salePriceVO.setProdCd(salePriceResveVO.getProdCd());
                    salePriceVO.setSeq(salePriceResveVO.getSeq());
                    salePriceVO.setSalePriceOrgnFg(salePriceResveVO.getSalePriceOrgnFg());

                    // 저장완료된 검증결과만 삭제
                    result += salePriceMapper.getSalePriceExcelUploadCheckDelete(salePriceVO);
                }
            }
        }

        return result;
    }

    /** 가격예약(매장판매가) 엑셀업로드 탭 - 판매가 저장 */
    @Override
    public int getStoreSalePriceResveExcelUploadSave(SalePriceResveVO[] salePriceResveVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;
        String currentDt = currentDateTimeString();

        for(SalePriceResveVO salePriceResveVO : salePriceResveVOs) {
            salePriceResveVO.setRegDt(currentDt);
            salePriceResveVO.setRegId(sessionInfoVO.getUserId());
            salePriceResveVO.setModDt(currentDt);
            salePriceResveVO.setModId(sessionInfoVO.getUserId());

            salePriceResveVO.setSessionId(sessionInfoVO.getSessionId());
            salePriceResveVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            salePriceResveVO.setSaleResveFg("1"); // 가격예약구분 0:일반, 1:예약

            if(("검증성공").equals(salePriceResveVO.getResult())) {
                // 가격관리구분이 매장인 경우만 수정
                if(("S").equals(salePriceResveVO.getPrcCtrlFg())) {

                    // 해당 시작날짜에 등록된 가격이 있는지 조회(판매가 히스토리 등록을 위해)
                    int prodCnt = salePriceResveMapper.getStoreSalePriceCnt(salePriceResveVO);

                    if(prodCnt > 0){ // 기존 판매가 히스토리에 저장
                        result = salePriceResveMapper.insertStoreSalePriceHistory(salePriceResveVO);
                        if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                    }

                    // 새 예약 판매가 등록
                    result = salePriceResveMapper.insertStoreSalePrice(salePriceResveVO);

                    // 본사판매가관리
                    SalePriceVO salePriceVO = new SalePriceVO();
                    salePriceVO.setSessionId(salePriceResveVO.getSessionId());
                    salePriceVO.setHqOfficeCd(salePriceResveVO.getHqOfficeCd());
                    salePriceVO.setStoreCd(salePriceResveVO.getStoreCd());
                    salePriceVO.setProdCd(salePriceResveVO.getProdCd());
                    salePriceVO.setSeq(salePriceResveVO.getSeq());
                    salePriceVO.setSalePriceOrgnFg(salePriceResveVO.getSalePriceOrgnFg());

                    // 저장완료된 검증결과만 삭제
                    result += salePriceMapper.getSalePriceExcelUploadCheckDelete(salePriceVO);
                }
            }
        }

        return result;
    }

}