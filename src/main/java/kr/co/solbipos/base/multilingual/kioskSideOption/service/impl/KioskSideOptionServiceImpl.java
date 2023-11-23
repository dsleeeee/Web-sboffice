package kr.co.solbipos.base.multilingual.kioskSideOption.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.multilingual.kioskSideOption.service.KioskSideOptionService;
import kr.co.solbipos.base.multilingual.kioskSideOption.service.KioskSideOptionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : KioskSideOptionServiceImpl.java
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
@Service("KioskSideOptionService")
public class KioskSideOptionServiceImpl  implements KioskSideOptionService {

    private final KioskSideOptionMapper kioskSideOptionMapper;
    private final MessageService messageService;

    @Autowired
    public KioskSideOptionServiceImpl(KioskSideOptionMapper kioskSideOptionMapper, MessageService messageService) {

        this.kioskSideOptionMapper = kioskSideOptionMapper;
        this.messageService = messageService;
    }

    /** 키오스크(카테고리) 탭 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getKioskCategoryList(KioskSideOptionVO kioskSideOptionVO, SessionInfoVO sessionInfoVO){

        kioskSideOptionVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return kioskSideOptionMapper.getKioskCategoryList(kioskSideOptionVO);
    }

    /** 키오스크(카테고리) 영문, 중문, 일문 저장 */
    @Override
    public int saveKioskCategory(KioskSideOptionVO[] kioskSideOptionVOs, SessionInfoVO sessionInfoVO){
        int result = 0;
        String dt = currentDateTimeString();

        for (KioskSideOptionVO kioskSideOptionVO : kioskSideOptionVOs) {

            kioskSideOptionVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            kioskSideOptionVO.setRegDt(dt);
            kioskSideOptionVO.setRegId(sessionInfoVO.getUserId());
            kioskSideOptionVO.setModDt(dt);
            kioskSideOptionVO.setModId(sessionInfoVO.getUserId());

            result += kioskSideOptionMapper.saveKioskCategory(kioskSideOptionVO);
        }

        if (result == kioskSideOptionVOs.length) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }


}
