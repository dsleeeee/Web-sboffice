package kr.co.solbipos.application.controller.user;

import static kr.co.solbipos.utils.DateUtil.*;
import static kr.co.solbipos.utils.HttpUtils.*;
import static kr.co.solbipos.utils.spring.StringUtil.*;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import kr.co.solbipos.application.domain.user.OtpAuth;
import kr.co.solbipos.application.domain.user.User;
import kr.co.solbipos.application.service.user.UserService;
import kr.co.solbipos.application.validate.user.AuthNumber;
import kr.co.solbipos.application.validate.user.IdFind;
import kr.co.solbipos.application.validate.user.PwFind;
import kr.co.solbipos.service.message.MessageService;
import kr.co.solbipos.structure.JsonResult;
import kr.co.solbipos.structure.Result.Status;
import kr.co.solbipos.system.Prop;
import kr.co.solbipos.utils.grid.ReturnUtil;
import kr.co.solbipos.utils.spring.ObjectUtil;
import kr.co.solbipos.utils.spring.StringUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author 정용길
 */

@Slf4j
@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    Prop prop;

    @Autowired
    UserService userService;

    @Autowired
    MessageService messageService;

    /**
     * 인증번호 발사!!
     * 
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "sendNum.sb", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult sendNum(@Validated(AuthNumber.class) User user, BindingResult bindingResult,
            HttpServletRequest request, HttpServletResponse response, Model model) {

        // 입력값 에러 처리
        if (bindingResult.hasErrors()) {
            List<ObjectError> errros = bindingResult.getAllErrors();
            HashMap<String, String> map = new HashMap<>();
            for (Object object : errros) {
                if (object instanceof FieldError) {
                    FieldError fieldError = (FieldError) object;
                    map.put(fieldError.getField(), fieldError.getDefaultMessage());
                }
            }
            return ReturnUtil.returnJson(Status.FAIL, map);
        }

        // 유져 정보 조회
        User u = userService.selectUserByNmAndId(user);

        // 조회된 유져가 없으면 에러 메세지 전송
        if (ObjectUtil.isEmpty(u)) {
            HashMap<String, String> map = new HashMap<>();
            String msg = messageService.get("msg.pw.find.error1")
                    + messageService.get("msg.pw.find.error2");
            map.put("authNmerrors", msg);
            return ReturnUtil.returnJson(Status.FAIL, map);
        } else {

            OtpAuth otp = new OtpAuth();
            otp.setUserId(u.getUserId());
            otp.setAuthFg("001");
            otp.setRecvMpNo(u.getMpNo());
            otp.setReqIp(getClientIp(request));
            otp.setReqDate(currentDateString());
            otp.setOtpLimit(prop.otpLimit);

            // limit 에 걸렸는지 확인
            if (checkOtpLimit(otp)) {
                HashMap<String, String> map = new HashMap<>();
                String msg = String.valueOf(otp.getOtpLimit())
                        + messageService.get("msg.pw.find.otp.limit");
                map.put("authNmerrors", msg);
                return ReturnUtil.returnJson(Status.FAIL, map);
            }

            // 인증 번호 생성 후 전송
            // 신규 OTP 생성 리턴
            userService.insertOtpAuth(otp);

            HashMap<String, String> map = new HashMap<>();
            String msg = messageService.get("msg.pw.find.send.ok");
            map.put("msg", msg);

            return ReturnUtil.returnJson(Status.OK, map);
        }
    }


    /**
     * otp limit 해당되는지 확인
     * 
     * @param otp
     * @return 10:23(limit) >= 10:20(now) ? true : false 조회 못한 경우에도 false return
     */
    public boolean checkOtpLimit(OtpAuth otp) {
        OtpAuth o = userService.selectOtpTopOne(otp);

        if (ObjectUtil.isEmpty(o)) {
            return false;
        }

        String plusReqDt = o.getPlusReqDt();
        if (!StringUtil.isEmpty(plusReqDt)) {

            int req = Integer.valueOf(plusReqDt);
            int now = Integer.valueOf(currentTimeString());

            // 현재 시간이 otp 입력 허용 시간 범위 안이다.
            if (req >= now) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }


    /**
     * 아이디 찾기 성공 페이지 이동
     * 
     * @param request
     * @param response
     * @param model
     * @return
     */
    /*
     * @RequestMapping(value = "idFindOk.sb", method = RequestMethod.GET) public String
     * idFindOk(HttpServletRequest request, HttpServletResponse response, Model model) { return
     * "user/login:idFindOk"; }
     */

    /**
     * 아이디 찾기 페이지 이동
     * 
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "idFind.sb", method = RequestMethod.GET)
    public String idFind(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "user/login:idFind";
    }

    /**
     * 아이디 찾기
     * 
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "idFind.sb", method = RequestMethod.POST)
    public String idFindProcess(@Validated(IdFind.class) User user, BindingResult bindingResult,
            HttpServletRequest request, HttpServletResponse response, Model model) {

        if (bindingResult.hasErrors()) {
            return "user/login:idFind";
        }

        String findUserNm = userService.selectUserCheck(user);

        // 아이디가 없으면 에러 메시지 리턴
        if (StringUtil.isEmpty(findUserNm)) {
            String msg = messageService.get("label.login.userId")
                    + messageService.get("msg.cmm.not.find");
            model.addAttribute("msg", msg);
            return "user/login:idFind";
        }

        // 찾은 아이디를 마스킹해서 보여줌
        model.addAttribute("findUserNm", strMaskingHalf(findUserNm));

        return "user/login:idFindOk";
    }

    /**
     * 패스워드 찾기 페이지 이동
     * 
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "pwdFind.sb", method = RequestMethod.GET)
    public String passwordFind(HttpServletRequest request, HttpServletResponse response,
            Model model) {
        return "user/login:pwdFind";
    }

    /**
     * 패스워드 찾기 > 비밀번호 변경 페이지로 이동
     * 
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "pwdFind.sb", method = RequestMethod.POST)
    public String passwordFindProcess(@Validated(PwFind.class) User user,
            BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response,
            Model model) {

        if (bindingResult.hasErrors()) {
            return "user/login:pwdFind";
        }

        User u = userService.selectUserByNmAndId(user);
        // id, 이름이 틀림
        if (ObjectUtil.isEmpty(u)) {
            return "";
        }

        OtpAuth otp = new OtpAuth();
        otp.setUserId(user.getUserId());
        otp.setAuthFg("001");
        otp.setReqDate(currentDateString());
        otp.setOtpLimit(prop.otpLimit);

        OtpAuth o = userService.selectOtpTopOne(otp);

        // limit 시간안에 전송함 통과
        if (checkOtpLimit(o)) {
            // 
        } else {
            // 인증번호 통과 못함
            return "";
        }


        return "redirect:/user/pwdChg.sb";
    }

    /**
     * 패스워드 변경 페이지 이동
     * 
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "pwdChg.sb", method = RequestMethod.GET)
    public String passwordChange(HttpServletRequest request, HttpServletResponse response,
            Model model) {
        return "user/login:pwdChg";
    }

    /**
     * 패스워드 변경
     * 
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "pwdChg.sb", method = RequestMethod.POST)
    public String passwordChangeProcess(HttpServletRequest request, HttpServletResponse response,
            Model model) {
        return "redirect:/user/pwdChgOk.sb";
    }

    /**
     * 패스워드 변경 완료 페이지 이동
     * 
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "pwdChgOk.sb", method = RequestMethod.GET)
    public String pwdChgOk(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "user/login:pwdChgOk";
    }
}


