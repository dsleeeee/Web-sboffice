package kr.co.solbipos.application.com.bizno.web;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.Result;
import kr.co.solbipos.application.com.bizno.service.BizNoService;
import kr.co.solbipos.application.com.bizno.service.BizNoVO;
import lombok.extern.slf4j.Slf4j;

/**
* @Class Name : BizNoController.java
* @Description : 어플리케이션 > 공통 > 사업자번호 유효성검사
* @Modification Information
* @
* @  수정일      수정자              수정내용
* @ ----------  ---------   -------------------------------
* @ 2018.06.01  노현수      최초생성
*
* @author 솔비포스 차세대개발실 노현수
* @since 2018. 05.01
* @version 1.0
* @see
*
*  Copyright (C) by SOLBIPOS CORP. All right reserved.
*/
@Slf4j
@Controller
@RequestMapping(value = "/com/bizno")
public class BizNoController {

    @Autowired
    BizNoService bizNoService;

    /**
     * 사업자번호 유효성검사
     *
     * @param menuType
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "vrify.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result bizNoVrify(BizNoVO bizNoVO, HttpServletRequest request, HttpServletResponse response) {

        boolean result = bizNoService.bizNoVerify(bizNoVO);

        if ( result ) {
            return returnJson(Status.OK);
        } else {
            return returnJson(Status.FAIL);
        }

    }


}
