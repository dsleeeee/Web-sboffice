package kr.co.solbipos.application.controller.com;

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
import kr.co.solbipos.application.domain.com.BizNoVO;
import kr.co.solbipos.application.service.com.BizNoService;
import lombok.extern.slf4j.Slf4j;

/**
 * 사업자번호 유효성검사 <br>
 *
 * @author 노현수
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
