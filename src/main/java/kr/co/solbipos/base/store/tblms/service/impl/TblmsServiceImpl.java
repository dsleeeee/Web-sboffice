package kr.co.solbipos.base.store.tblms.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.security.EncUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.store.tblms.service.TblmsService;
import kr.co.solbipos.base.store.tblms.service.TblmsVO;
import kr.co.solbipos.base.store.tblms.service.impl.TblmsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;
import static kr.co.common.utils.spring.StringUtil.getRandomNumber;

/**
* @Class Name : TblmsServiceImpl.java
* @Description :
* @Modification Information
* @
* @  수정일      수정자              수정내용
* @ ----------  ---------   -------------------------------
 * @
 * @
*
* @author
* @since
* @version 1.0
* @see
*
* @Copyright (C) by SOLBIPOS CORP. All right reserved.
*/
@Service("tblmsService")
public class TblmsServiceImpl implements TblmsService {

    private final TblmsMapper tblmsMapper;
    private final MessageService messageService;


    /** Constructor Injection */
    @Autowired
    public TblmsServiceImpl(TblmsMapper tblmsMapper, MessageService messageService) {
        this.tblmsMapper = tblmsMapper;
        this.messageService = messageService;
    }

    /** 창고정보 리스트조회 */
    @Override
    public List<DefaultMap<String>> getTblmsList(TblmsVO tblmsVO, SessionInfoVO sessionInfoVO){

    	tblmsVO.setStoreCd(sessionInfoVO.getStoreCd());

    	List<DefaultMap<String>> list = null;

        return list;
    }

    /** 임시패스워드 등록 */
    @Override
    public String tblmsOpn(TblmsVO tblmsVO, SessionInfoVO sessionInfoVO) {

        String newPassword = "";
        String dt = currentDateTimeString();
        String randomNumber = getRandomNumber(5);

        newPassword = EncUtil.setEncSHA256(sessionInfoVO.getUserId() + dt + randomNumber);

        tblmsVO.setStoreCd(sessionInfoVO.getStoreCd());
        tblmsVO.setTblmsTempPw(newPassword);
        tblmsVO.setTblmsLoginFg("0");
        tblmsVO.setTblmsTempPwDt(dt);
        tblmsVO.setTblmsTempPwRegId(sessionInfoVO.getUserId());

        int newPasswordCnt = tblmsMapper.tblmsOpn(tblmsVO);

        return newPassword;
    }

























}
