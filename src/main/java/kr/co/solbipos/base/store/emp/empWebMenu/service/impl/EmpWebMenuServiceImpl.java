package kr.co.solbipos.base.store.emp.empWebMenu.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.store.emp.empWebMenu.service.EmpWebMenuService;
import kr.co.solbipos.base.store.emp.empWebMenu.service.EmpWebMenuVO;
import kr.co.solbipos.sys.auth.authgroup.enums.IncldExcldFg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : EmpWebMenuServiceImpl.java
 * @Description : 기초관리 > 사원관리 > 메뉴별권한복사
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.07.24  권지현      최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2023.07.24
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Service
public class EmpWebMenuServiceImpl implements EmpWebMenuService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final EmpWebMenuMapper webMenuMapper;
    private final MessageService messageService;

    /** Constructor Injection */
    @Autowired
    public EmpWebMenuServiceImpl(EmpWebMenuMapper webMenuMapper, MessageService messageService) {
        this.webMenuMapper = webMenuMapper;
        this.messageService = messageService;
    }

    /** 메뉴목록 조회 */
    @Override
    public List<DefaultMap<String>> getMenuList(EmpWebMenuVO empWebMenuVO, SessionInfoVO sessionInfoVO) {

        empWebMenuVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return webMenuMapper.getMenuList(empWebMenuVO);
    }

    /** 사용사원목록 조회 */
    @Override
    public List<DefaultMap<String>> getUseEmp(EmpWebMenuVO empWebMenuVO, SessionInfoVO sessionInfoVO) {

        empWebMenuVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return webMenuMapper.getUseEmp(empWebMenuVO);
    }

    /** 미사용사원목록 조회 */
    @Override
    public List<DefaultMap<String>> getUnuesdEmp(EmpWebMenuVO empWebMenuVO, SessionInfoVO sessionInfoVO) {

        empWebMenuVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return webMenuMapper.getUnuesdEmp(empWebMenuVO);
    }

    /** 사원정보 저장 */
    @Override
    public int getEmpWebMenuSave(EmpWebMenuVO[] empWebMenuVOs, SessionInfoVO sessionInfoVO) {

        int storeCnt = 0;
        String currentDt = currentDateTimeString();

        for(EmpWebMenuVO empWebMenuVO : empWebMenuVOs) {

            empWebMenuVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

            empWebMenuVO.setRegDt(currentDt);
            empWebMenuVO.setRegId(sessionInfoVO.getUserId());
            empWebMenuVO.setModDt(currentDt);
            empWebMenuVO.setModId(sessionInfoVO.getUserId());


            if(empWebMenuVO.getStatus() == GridDataFg.INSERT) {
                // 저장
                storeCnt += webMenuMapper.getEmpWebMenuSaveInsert(empWebMenuVO);
            } else if(empWebMenuVO.getStatus() == GridDataFg.DELETE) {
                // 저장
                storeCnt += webMenuMapper.getEmpWebMenuSaveDelete(empWebMenuVO);
            }
        }

        return storeCnt;
    }

}
