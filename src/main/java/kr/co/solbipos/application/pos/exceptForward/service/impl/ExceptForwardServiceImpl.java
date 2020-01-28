package kr.co.solbipos.application.pos.exceptForward.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.system.BaseEnv;
import kr.co.solbipos.application.pos.exceptForward.service.ExceptForwardService;
import kr.co.solbipos.application.pos.exceptForward.service.ExcpForwardProductVO;
import kr.co.solbipos.application.pos.exceptForward.service.enums.StatusFg;
import kr.co.solbipos.application.pos.exceptForward.service.enums.TpioFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.prod.info.service.ProductClassVO;
import kr.co.solbipos.base.prod.prod.service.ProdVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateString;
import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : ExceptForwardServiceImpl.java
 * @Description : POS 화면에서 예외출고 등록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.09.14  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018. 09.14
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Service("exceptForwardService")
public class ExceptForwardServiceImpl implements ExceptForwardService{

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final MessageService messageService;

    private final ExceptForwardMapper exceptForwardMapper;

    private final String NEOE_SEND_ID = "SOLBIPOS";

    /** Constructor Injection */
    @Autowired
    public ExceptForwardServiceImpl(MessageService messageService, ExceptForwardMapper exceptForwardMapper) {
        this.messageService = messageService;
        this.exceptForwardMapper = exceptForwardMapper;
    }

    /** 예외출고용 상품 분류 */
    @Override
    public List<ProductClassVO> getProdClassTree(ProdVO prodVO, SessionInfoVO sessionInfoVO) {

        // 소속구분 설정
        String orgnFg = sessionInfoVO.getOrgnFg().getCode();

        prodVO.setOrgnFg(orgnFg);
        prodVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        prodVO.setStoreCd(sessionInfoVO.getStoreCd());

        return exceptForwardMapper.getProdClassTree(prodVO);
    }

    /** 예외출고 대상상품 목록 조회*/
    @Override
    public List<DefaultMap<String>> getExcpForwardProduct(ExcpForwardProductVO productVO,
        SessionInfoVO sessionInfoVO) {

        productVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        productVO.setStoreCd(sessionInfoVO.getStoreCd());
        productVO.setSaleDate(currentDateString());
        productVO.setCdCompany(BaseEnv.NEOE_CD_COMPANY); // 회사코드
        productVO.setCdPlant(BaseEnv.NEOE_CD_PLANT); //공장코드
        productVO.setNoEgr(sessionInfoVO.getStoreCd() + productVO.getProdCd()); // 출고항번 (STORE_CD + PROD_CD)
        productVO.setDtIo(currentDateString()); // 출고일자
        productVO.setFgTpio(TpioFg.DISPOSAL); // 대체유형 : 폐기
        productVO.setCdQtiotp(BaseEnv.NEOE_CD_QTIOTP); // 수불유형

        // 목록 조회
        List<DefaultMap<String>> reultList = exceptForwardMapper.getExcpForwardProduct(productVO);

        return reultList;
    }

    /** 예외출고 저장 */
    @Override
    public int saveExcpForwardProduct(ExcpForwardProductVO productVO, SessionInfoVO sessionInfoVO) {

        String dt = currentDateTimeString();

        productVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        productVO.setCdCompany(BaseEnv.NEOE_CD_COMPANY); // 회사코드
        productVO.setCdPlant(BaseEnv.NEOE_CD_PLANT); //공장코드
        productVO.setNoEgr(sessionInfoVO.getStoreCd() + productVO.getProdCd()); // 출고항번 (STORE_CD + PROD_CD)
        productVO.setDtIo(currentDateString()); // 출고일자
        productVO.setFgTpio(TpioFg.DISPOSAL); // 대체유형 : 폐기
        productVO.setCdQtiotp(BaseEnv.NEOE_CD_QTIOTP); // 수불유형

        productVO.setDtsInsert(dt);
        productVO.setIdInsert(NEOE_SEND_ID);
        productVO.setDtsUpdate(dt);
        productVO.setIdUpdate(NEOE_SEND_ID);

        int result = 0;

        // 기존에 등록한 예외출고가 있으면 삭제 데이터 insert 후, 출고데이터 insert
        if(productVO.getPrevQtIo() > 0) {

            productVO.setFgStatus(StatusFg.DELETE);  // 삭제

            result = exceptForwardMapper.deleteExcpForwardProduct(productVO);
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }

        productVO.setFgStatus(StatusFg.INSERT);  // 예외출고 정상 등록

        result = exceptForwardMapper.saveExcpForwardProduct(productVO);
        if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        return result;
    }
}
