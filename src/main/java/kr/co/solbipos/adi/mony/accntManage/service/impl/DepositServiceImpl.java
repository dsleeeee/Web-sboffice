package kr.co.solbipos.adi.mony.accntManage.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.adi.mony.accntManage.service.DepositService;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;


/**
 * @Class Name : DepositServiceImpl.java
 * @Description : 부가서비스 > 금전처리 > 계정관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.10.12  김지은      최초생성
 * @ 2019.08.14  이다솜      본사에서 계정 등록 시 매장에도 적용되도록 추가
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018. 10.12
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service
public class DepositServiceImpl implements DepositService{

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final DepositMapper depositMapper;
    private final MessageService messageService;

    /** Constructor Injection */
    @Autowired
    public DepositServiceImpl(DepositMapper depositMapper, MessageService messageService) {
        this.depositMapper = depositMapper;
        this.messageService = messageService;
    }

    /** 계정 조회 */
    @Override
    public List<DefaultMap<String>> getDepositAccntList(AccntVO accntVO, SessionInfoVO sessionInfoVO) {

        OrgnFg userOrgnFg = sessionInfoVO.getOrgnFg();

        accntVO.setOrgnFg(userOrgnFg);

        if(userOrgnFg == OrgnFg.HQ) { // 본사
            accntVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        } else if(userOrgnFg == OrgnFg.STORE) { // 매장
            accntVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return depositMapper.getDepositAccntList(accntVO);
    }

    /** 계정 정보 저장 */
    @Override
    public int saveDepositAccntList(AccntVO[] accntVOs, SessionInfoVO sessionInfoVO) {

        int resultCnt = 0;
        String procResult;
        String dt = currentDateTimeString();

        for(AccntVO accntVO : accntVOs) {

            accntVO.setOrgnFg(sessionInfoVO.getOrgnFg());

            OrgnFg userOrgnFg = sessionInfoVO.getOrgnFg();

            if(userOrgnFg == OrgnFg.HQ) {
                accntVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            } else if(userOrgnFg == OrgnFg.STORE) {
                accntVO.setStoreCd(sessionInfoVO.getStoreCd());
            }

            accntVO.setRegDt(dt);
            accntVO.setRegId(sessionInfoVO.getUserId());
            accntVO.setModDt(dt);
            accntVO.setModId(sessionInfoVO.getUserId());

            if(accntVO.getStatus() == GridDataFg.INSERT) {
                // 계정코드 생성
                accntVO.setAccntCd(depositMapper.getAccntCode(accntVO));

                resultCnt += depositMapper.insertDepositAccntList(accntVO);
                // 본사에서 접속시
                if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
                    procResult = depositMapper.insertAccntToStore(accntVO);
                }
            } else if (accntVO.getStatus() == GridDataFg.UPDATE) {
                resultCnt += depositMapper.updateDepositAccntList(accntVO);
                // 본사에서 접속시
                if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
                    procResult = depositMapper.updateAccntToStore(accntVO);
                }
            } else if (accntVO.getStatus() == GridDataFg.DELETE) {
                resultCnt += depositMapper.deleteDepositAccntList(accntVO);
                // 본사에서 접속시
                if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
                    procResult = depositMapper.deleteAccntToStore(accntVO);
                }
            }
        }

        if ( resultCnt == accntVOs.length) {
            return resultCnt;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }

    /** 계정 정보 일괄저장 */
    @Override
    public int batchDepositAccntList(AccntVO accntVO, SessionInfoVO sessionInfoVO) {

        int resultCnt = 0;
        String dt = currentDateTimeString();

        accntVO.setOrgnFg(sessionInfoVO.getOrgnFg());
        accntVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        accntVO.setRegDt(dt);
        accntVO.setRegId(sessionInfoVO.getUserId());

        // 본사의 계정 리스트 조회
        List<DefaultMap<String>> accntList = depositMapper.getDepositAccntList(accntVO);

        //본사의 계정리스트를 전 매장에 동일적용
        for (int i = 0; i < accntList.size(); i++) {
            accntVO.setAccntCd(accntList.get(i).getStr("accntCd"));
            depositMapper.insertAccntToStore(accntVO);
            resultCnt++;
        }

        if (resultCnt == accntList.size()) {
            return resultCnt;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }

}
