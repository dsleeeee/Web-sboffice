package kr.co.solbipos.iostock.vendr.vendrExact.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.vendr.vendrExact.service.VendrExactService;
import kr.co.solbipos.iostock.vendr.vendrExact.service.VendrExactVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

@Service("vendrExactService")
public class VendrExactServiceImpl implements VendrExactService {
    private final VendrExactMapper vendrExactMapper;
    private final MessageService messageService;

    @Autowired
    public VendrExactServiceImpl(VendrExactMapper vendrExactMapper, MessageService messageService) {
        this.vendrExactMapper = vendrExactMapper;
        this.messageService = messageService;
    }

    /** 거래처 정산관리 - 거래처별 정산 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getVendrExactList(VendrExactVO vendrExactVO, SessionInfoVO sessionInfoVO) {
        // regId, regDt, modId, modDt, hqOfficd, storeCd, orgnCd 세팅
        vendrExactVO = setSessionValue(vendrExactVO, sessionInfoVO, null);

        // 거래처 멀티 선택
        if(!StringUtil.getOrBlank(vendrExactVO.getVendrCd()).equals("")) {
            vendrExactVO.setArrVendrCd(vendrExactVO.getVendrCd().split(","));
        }

        return vendrExactMapper.getVendrExactList(vendrExactVO);
    }


    /** 거래처 정산관리 - 거래처 정산 상세 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getVendrExactDtlList(VendrExactVO vendrExactVO, SessionInfoVO sessionInfoVO) {
        // regId, regDt, modId, modDt, hqOfficd, storeCd, orgnCd 세팅
        vendrExactVO = setSessionValue(vendrExactVO, sessionInfoVO, null);

        return vendrExactMapper.getVendrExactDtlList(vendrExactVO);
    }


    /** 거래처 정산관리 - 지급액 상세 조회 */
    @Override
    public DefaultMap<String> getExactInfo(VendrExactVO vendrExactVO, SessionInfoVO sessionInfoVO) {
        // regId, regDt, modId, modDt, hqOfficd, storeCd, orgnFg 세팅
        vendrExactVO = setSessionValue(vendrExactVO, sessionInfoVO, null);
        return vendrExactMapper.getExactInfo(vendrExactVO);
    }


    /** 거래처 정산관리 - 지급액 저장 */
    @Override
    public int saveVendrExactRegist(VendrExactVO vendrExactVO, SessionInfoVO sessionInfoVO) {
        int result = 0;

        // regId, regDt, modId, modDt, hqOfficd, storeCd, orgnCd 세팅
        vendrExactVO = setSessionValue(vendrExactVO, sessionInfoVO, null);

        // 신규등록
        if(StringUtil.getOrBlank(vendrExactVO.getSeqNo()).equals("")) {
            result = vendrExactMapper.insertVendrExact(vendrExactVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
        // 신규등록이 아닌 경우
        else {
            result = vendrExactMapper.updateVendrExact(vendrExactVO);
            if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }

        return result;
    }


    /** 거래처 정산관리 - 지급액 삭제 */
    @Override
    public int deleteVendrExactRegist(VendrExactVO vendrExactVO, SessionInfoVO sessionInfoVO) {
        int result = 0;

        // regId, regDt, modId, modDt, hqOfficd, storeCd, orgnCd 세팅
        vendrExactVO = setSessionValue(vendrExactVO, sessionInfoVO, null);

        result = vendrExactMapper.deleteVendrExact(vendrExactVO);
        if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        return result;
    }


    /** regId, regDt, modId, modDt, hqOfficd, storeCd, orgnFg 세팅  */
    public VendrExactVO setSessionValue(VendrExactVO vendrExactVO, SessionInfoVO sessionInfoVO, String currentDt) {
        if(StringUtil.getOrBlank(currentDt).equals("")) {
            currentDt = currentDateTimeString();
        }

        vendrExactVO.setRegId(sessionInfoVO.getUserId());
        vendrExactVO.setRegDt(currentDt);
        vendrExactVO.setModId(sessionInfoVO.getUserId());
        vendrExactVO.setModDt(currentDt);

        vendrExactVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        vendrExactVO.setStoreCd(sessionInfoVO.getStoreCd());
        vendrExactVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

        return vendrExactVO;
    }
}
