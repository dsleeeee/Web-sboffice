package kr.co.solbipos.mobile.adi.dclz.dclz.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.adi.dclz.dclzmanage.service.DclzManageService;
import kr.co.solbipos.adi.dclz.dclzmanage.service.DclzManageVO;
import kr.co.solbipos.adi.dclz.dclzmanage.service.impl.DclzManageMapper;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.mobile.adi.dclz.dclz.service.MobileDclzManageService;
import kr.co.solbipos.mobile.adi.dclz.dclz.service.MobileDclzManageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : MobileDclzManageServiceImpl.java
 * @Description : 부가서비스 > 근태 관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.07.09  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2021.07.09
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("mobileDManageService")
@Transactional
public class MobileDclzManageServiceImpl implements MobileDclzManageService {

    private final MobileDclzManageMapper mobileDclzManageMapper;
    private final MessageService messageService;

    /** Constructor Injection */
    @Autowired
    public MobileDclzManageServiceImpl(MobileDclzManageMapper mobileDclzManageMapper, MessageService messageService) {
        this.mobileDclzManageMapper = mobileDclzManageMapper;
        this.messageService = messageService;
    }

    /** 근태관리 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getDclzManage(MobileDclzManageVO mobileDclzManageVO, SessionInfoVO sessionInfoVO) {
        mobileDclzManageVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        mobileDclzManageVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(!StringUtil.getOrBlank(mobileDclzManageVO.getSrchStoreCd()).equals("")) {
            mobileDclzManageVO.setArrStoreCd(mobileDclzManageVO.getSrchStoreCd().split(","));
        }

        return mobileDclzManageMapper.getDclzManage(mobileDclzManageVO);
    }
}

