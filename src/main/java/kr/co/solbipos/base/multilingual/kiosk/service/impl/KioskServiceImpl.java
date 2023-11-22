package kr.co.solbipos.base.multilingual.kiosk.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.multilingual.kiosk.service.KioskService;
import kr.co.solbipos.base.multilingual.kiosk.service.KioskVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : KioskServiceImpl.java
 * @Description : 기초관리 - 다국어관리 - 다국어관리(키오스크(카테고리)/사이드/옵션)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.11.20  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2023.11.20
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("KioskService")
public class KioskServiceImpl  implements KioskService {

    private final KioskMapper kioskMapper;
    private final MessageService messageService;

    @Autowired
    public KioskServiceImpl(KioskMapper kioskMapper, MessageService messageService) {

        this.kioskMapper = kioskMapper;
        this.messageService = messageService;
    }

    /** 키오스크(카테고리) 탭 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getKioskCategoryList(KioskVO kioskVO, SessionInfoVO sessionInfoVO){

        kioskVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return kioskMapper.getKioskCategoryList(kioskVO);
    }

    /** 키오스크(카테고리) 영문, 중문, 일문 저장 */
    @Override
    public int saveKioskCategory(KioskVO[] kioskVOs, SessionInfoVO sessionInfoVO){
        int result = 0;
        String dt = currentDateTimeString();

        for (KioskVO kioskVo : kioskVOs) {

            kioskVo.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            kioskVo.setRegDt(dt);
            kioskVo.setRegId(sessionInfoVO.getUserId());
            kioskVo.setModDt(dt);
            kioskVo.setModId(sessionInfoVO.getUserId());

            result += kioskMapper.saveKioskCategory(kioskVo);
        }

        if (result == kioskVOs.length) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }


}
