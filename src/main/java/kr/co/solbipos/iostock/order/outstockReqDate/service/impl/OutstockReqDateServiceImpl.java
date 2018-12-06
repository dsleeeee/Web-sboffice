package kr.co.solbipos.iostock.order.outstockReqDate.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.order.outstockReqDate.service.OutstockReqDateService;
import kr.co.solbipos.iostock.order.outstockReqDate.service.OutstockReqDateVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

@Service("outstockReqDateService")
public class OutstockReqDateServiceImpl implements OutstockReqDateService {
    @Autowired
    OutstockReqDateMapper outstockReqDateMapper;

    @Autowired
    MessageService messageService;

    /** 출고요청일관리 요일별 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getDaysList(OutstockReqDateVO outstockReqDateVO) {
        if(!StringUtil.getOrBlank(outstockReqDateVO.getStoreCd()).equals("")) {
            outstockReqDateVO.setArrStoreCd(outstockReqDateVO.getStoreCd().split(","));
        }

        return outstockReqDateMapper.getDaysList(outstockReqDateVO);
    }

    /** 출고요청일관리 요일별 저장 */
    @Override
    public int saveReqDateDays(OutstockReqDateVO[] outstockReqDateVOs, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;
        int result = 0;
        String currentDt = currentDateTimeString();

        for (OutstockReqDateVO outstockReqDateVO : outstockReqDateVOs) {
            outstockReqDateVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            outstockReqDateVO.setRegId(sessionInfoVO.getUserId());
            outstockReqDateVO.setRegDt(currentDt);
            outstockReqDateVO.setModId(sessionInfoVO.getUserId());
            outstockReqDateVO.setModDt(currentDt);

            String reqNoDay = "";
            if(!outstockReqDateVO.getSun()) reqNoDay += "1"; //일
            if(!outstockReqDateVO.getMon()) reqNoDay += "2"; //월
            if(!outstockReqDateVO.getTue()) reqNoDay += "3"; //화
            if(!outstockReqDateVO.getWed()) reqNoDay += "4"; //수
            if(!outstockReqDateVO.getThu()) reqNoDay += "5"; //목
            if(!outstockReqDateVO.getFri()) reqNoDay += "6"; //금
            if(!outstockReqDateVO.getSat()) reqNoDay += "7"; //토
            outstockReqDateVO.setReqNoDay(reqNoDay);

            // 수정
            result = outstockReqDateMapper.updateReqDateDays(outstockReqDateVO);
            if(result == 0) {
                // 추가
                result = outstockReqDateMapper.insertReqDateDays(outstockReqDateVO);
            }
            returnResult += result;
        }

        if ( returnResult == outstockReqDateVOs.length) {
            return returnResult;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }


    /** 출고요청일관리 특정일 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getSpecificDateList(OutstockReqDateVO outstockReqDateVO) {
        if(!StringUtil.getOrBlank(outstockReqDateVO.getStoreCd()).equals("")) {
            outstockReqDateVO.setArrStoreCd(outstockReqDateVO.getStoreCd().split(","));
        }

        return outstockReqDateMapper.getSpecificDateList(outstockReqDateVO);
    }


    /** 출고요청일관리 특정일 신규 등록 */
    @Override
    public int saveNewSpecificDate(OutstockReqDateVO outstockReqDateVO, SessionInfoVO sessionInfoVO) {
        int result = 0;
        String currentDt = currentDateTimeString();

        outstockReqDateVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        outstockReqDateVO.setRegId(sessionInfoVO.getUserId());
        outstockReqDateVO.setRegDt(currentDt);
        outstockReqDateVO.setModId(sessionInfoVO.getUserId());
        outstockReqDateVO.setModDt(currentDt);

        // 등록
        result = outstockReqDateMapper.insertSpecificDate(outstockReqDateVO);

        if (result > 0) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }


    /** 출고요청일관리 특정일 수정 */
    @Override
    public int saveSpecificDate(OutstockReqDateVO[] outstockReqDateVOs, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;
        int result = 0;
        String currentDt = currentDateTimeString();

        for (OutstockReqDateVO outstockReqDateVO : outstockReqDateVOs) {
            outstockReqDateVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            outstockReqDateVO.setRegId(sessionInfoVO.getUserId());
            outstockReqDateVO.setRegDt(currentDt);
            outstockReqDateVO.setModId(sessionInfoVO.getUserId());
            outstockReqDateVO.setModDt(currentDt);

            // 수정
            result = outstockReqDateMapper.updateSpecificDate(outstockReqDateVO);
            returnResult += result;
        }

        if ( returnResult == outstockReqDateVOs.length) {
            return returnResult;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }


    /** 출고요청일관리 특정일 삭제 */
    @Override
    public int deleteSpecificDate(OutstockReqDateVO[] outstockReqDateVOs, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;
        int result = 0;
        String currentDt = currentDateTimeString();

        for (OutstockReqDateVO outstockReqDateVO : outstockReqDateVOs) {
            outstockReqDateVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            outstockReqDateVO.setRegId(sessionInfoVO.getUserId());
            outstockReqDateVO.setRegDt(currentDt);
            outstockReqDateVO.setModId(sessionInfoVO.getUserId());
            outstockReqDateVO.setModDt(currentDt);

            // 삭제
            result = outstockReqDateMapper.deleteSpecificDate(outstockReqDateVO);
            returnResult += result;
        }

        if ( returnResult == outstockReqDateVOs.length) {
            return returnResult;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }


    /** 출고요청일관리 특정일 복사 */
    @Override
    public int copySpecificDate(OutstockReqDateVO[] outstockReqDateVOs, SessionInfoVO sessionInfoVO) {
        int returnResult = 0;
        int result = 0;
        String currentDt = currentDateTimeString();

        for (OutstockReqDateVO outstockReqDateVO : outstockReqDateVOs) {
            outstockReqDateVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            outstockReqDateVO.setRegId(sessionInfoVO.getUserId());
            outstockReqDateVO.setRegDt(currentDt);
            outstockReqDateVO.setModId(sessionInfoVO.getUserId());
            outstockReqDateVO.setModDt(currentDt);

            // 복사할 매장을 여러개 선택한 경우
            String storeCds[] = outstockReqDateVO.getCopyStoreCd().split(",");
            for(String storeCd : storeCds) {
                if(!outstockReqDateVO.getStoreCd().equals(storeCd)) {
                    //                    OutstockReqDateVO paramVO = outstockReqDateVO;

                    outstockReqDateVO.setCopyStoreCd(storeCd);

                    // 복사할 매장의 이전 정보 삭제
                    outstockReqDateMapper.deleteCopySpecificDate(outstockReqDateVO);

                    // 등록
                    result = outstockReqDateMapper.insertCopySpecificDate(outstockReqDateVO);
                    if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                }
            }

            returnResult += result;
        }

        if ( returnResult == outstockReqDateVOs.length) {
            return returnResult;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }


    /** 출고요청일관리 요일 복사 */
    @Override
    public int copyDays(OutstockReqDateVO outstockReqDateVO, SessionInfoVO sessionInfoVO) {
        int result = 0;
        String currentDt = currentDateTimeString();

        outstockReqDateVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        outstockReqDateVO.setRegId(sessionInfoVO.getUserId());
        outstockReqDateVO.setRegDt(currentDt);
        outstockReqDateVO.setModId(sessionInfoVO.getUserId());
        outstockReqDateVO.setModDt(currentDt);

        // 복사할 매장을 여러개 선택한 경우
        String storeCds[] = outstockReqDateVO.getCopyStoreCd().split(",");
        for(String storeCd : storeCds) {
            if(!outstockReqDateVO.getStoreCd().equals(storeCd)) {
                //                OutstockReqDateVO paramVO = outstockReqDateVO;

                outstockReqDateVO.setCopyStoreCd(storeCd);

                // 복사할 매장의 이전 정보 삭제
                outstockReqDateMapper.deleteAllCopyDays(outstockReqDateVO);

                // 등록
                result = outstockReqDateMapper.insertCopyDays(outstockReqDateVO);
                if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }
        }

        if ( result >= 0) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }






    /** 출고요청일관리 매장선택 리스트 조회 */
    @Override
    public List<DefaultMap<String>> selectStoreList(OutstockReqDateVO outstockReqDateVO) {
        return outstockReqDateMapper.selectStoreList(outstockReqDateVO);
    }

}
