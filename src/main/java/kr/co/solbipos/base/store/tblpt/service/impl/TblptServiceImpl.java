package kr.co.solbipos.base.store.tblpt.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.store.tblpt.service.TblptService;
import kr.co.solbipos.base.store.tblpt.service.TblptVO;
import kr.co.solbipos.base.store.tblpt.service.impl.TblptMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;
import kr.co.common.utils.security.EncUtil;
import static kr.co.common.utils.spring.StringUtil.getRandomNumber;

/**
* @Class Name : TblptServiceImpl.java
* @Description : 기초관리 > 매장관리 > 매장정보조회
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
@Service("tblptService")
public class TblptServiceImpl implements TblptService {

    private final TblptMapper tblptMapper;
    private final MessageService messageService;


    /** Constructor Injection */
    @Autowired
    public TblptServiceImpl(TblptMapper tblptMapper, MessageService messageService) {
        this.tblptMapper = tblptMapper;
        this.messageService = messageService;
    }

    /** 창고정보 리스트조회 */
    @Override
    public List<DefaultMap<String>> getTblptList(TblptVO tblptVO, SessionInfoVO sessionInfoVO){

    	tblptVO.setStoreCd(sessionInfoVO.getStoreCd());

    	List<DefaultMap<String>> list = null;

        return list;
    }

    /** 임시패스워드 등록 */
    @Override
    public String tblptOpn(TblptVO tblptVO, SessionInfoVO sessionInfoVO) {

        String newPassword = "";
        String dt = currentDateTimeString();
        String randomNumber = getRandomNumber(5);

        newPassword = EncUtil.setEncSHA256(sessionInfoVO.getUserId() + dt + randomNumber);

        tblptVO.setStoreCd(sessionInfoVO.getStoreCd());
        tblptVO.setTblptTempPw(newPassword);
        tblptVO.setTblptLoginFg("0");
        tblptVO.setTblptTempPwDt(dt);
        tblptVO.setTblptTempPwRegId(sessionInfoVO.getUserId());

        int newPasswordCnt = tblptMapper.tblptOpn(tblptVO);

        return newPassword;
    }

























}
