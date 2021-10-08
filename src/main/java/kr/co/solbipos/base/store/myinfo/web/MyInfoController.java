package kr.co.solbipos.base.store.myinfo.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.Result;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.session.SessionService;
import kr.co.solbipos.base.store.myinfo.service.MyInfoService;
import kr.co.solbipos.base.store.myinfo.service.MyInfoVO;
import kr.co.solbipos.base.store.myinfo.service.WijmoGridVO;
import kr.co.solbipos.store.hq.hqmanage.service.HqNmcodeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.solbipos.base.store.myinfo.enums.NmcodeGrpCd.*;
import static org.springframework.http.HttpStatus.OK;

/**
 * @Class Name : MyInfoController.java
 * @Description : 기초관리 > 매장관리 > 내정보관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.07.27  이호원      최초생성
 * @ 2019.07.24  이다솜      코드 값 가져오는 부분 POST 방식으로 수정(가상로그인의 session을 사용하기 위함)
 *
 * @author NHN한국사이버결제 이호원
 * @since 2018.07.27
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping( "/" + MyInfoController.PREFIX )
public class MyInfoController{
    static final String PREFIX = "base/store/myInfo";

    private final MyInfoService myInfoService;
    private final SessionService sessionService;
    private final MessageService messageService;

    /** Constructor Injection */
    @Autowired
    public MyInfoController(MyInfoService myInfoService, SessionService sessionService, MessageService messageService) {
        this.myInfoService = myInfoService;
        this.sessionService = sessionService;
        this.messageService = messageService;
    }

    @GetMapping( "/myInfo/list.sb" )
    public String myInfoList( Model model ){
        model.addAttribute( "myInfo", myInfoService.getMyInfo() );
        model.addAttribute( "areaCds", myInfoService.nmcodeList(AREA) );
        return PREFIX.toLowerCase() + "/myinfo";
    }

    @PostMapping( "/myInfo/save.sb" )
    public ResponseEntity< Result > modifyMyInfo( @RequestBody MyInfoVO myInfo ){
        return new ResponseEntity<>( new Result(myInfoService.modifyMyInfo(myInfo)), OK );
    }

    @PostMapping( "/storeType/list.sb" )
    public ResponseEntity< WijmoGridVO<HqNmcodeVO> > storeTypeList(){
        return new ResponseEntity<>( myInfoService.getGridInfo(STORE_TYPE), OK );
    }

    @PostMapping( "/storeType/save.sb" )
    public ResponseEntity< Result > saveStoreType( @RequestBody Map<String, List<HqNmcodeVO>> params ){
        List< HqNmcodeVO > emptyList = Collections.emptyList(),
                addList = params.getOrDefault("addItems", emptyList ),
                modList = params.getOrDefault("modItems", emptyList ),
                delList = params.getOrDefault("delItems", emptyList );

        return new ResponseEntity<>( new Result(myInfoService.saveMultipleNmcode(addList, modList, delList, STORE_TYPE)), OK );
    }

    @PostMapping( "/grp/list.sb" )
    public ResponseEntity< WijmoGridVO<HqNmcodeVO> > grpList(){
        return new ResponseEntity<>( myInfoService.getGridInfo(GRP), OK );
    }

    @PostMapping( "/grp/save.sb" )
    public ResponseEntity< Result > saveGrp( @RequestBody Map<String, List<HqNmcodeVO>> params ){
        List< HqNmcodeVO > emptyList = Collections.emptyList(),
                addList = params.getOrDefault("addItems", emptyList ),
                modList = params.getOrDefault("modItems", emptyList ),
                delList = params.getOrDefault("delItems", emptyList );

        return new ResponseEntity<>( new Result(myInfoService.saveMultipleNmcode(addList, modList, delList, GRP)), OK );
    }

    @PostMapping( "/hour/list.sb" )
    public ResponseEntity< WijmoGridVO<HqNmcodeVO> > hourList(){
        return new ResponseEntity<>( myInfoService.getGridInfo(HOUR), OK );
    }

    @PostMapping( "/hour/save.sb" )
    public ResponseEntity< Result > saveHour( @RequestBody Map<String, List<HqNmcodeVO>> params ){
        List< HqNmcodeVO > emptyList = Collections.emptyList(),
                addList = params.getOrDefault("addItems", emptyList ),
                modList = params.getOrDefault("modItems", emptyList ),
                delList = params.getOrDefault("delItems", emptyList );

        return new ResponseEntity<>( new Result(myInfoService.saveMultipleNmcode(addList, modList, delList, HOUR)), OK );
    }

    @PostMapping( "/guest/list.sb" )
    public ResponseEntity< WijmoGridVO<HqNmcodeVO> > guestList(){
        return new ResponseEntity<>( myInfoService.getGridInfo(GUEST), OK );
    }

    @PostMapping( "/guest/save.sb" )
    public ResponseEntity< Result > saveGuest( @RequestBody Map<String, List<HqNmcodeVO>> params ){
        List< HqNmcodeVO > emptyList = Collections.emptyList(),
                addList = params.getOrDefault("addItems", emptyList ),
                modList = params.getOrDefault("modItems", emptyList ),
                delList = params.getOrDefault("delItems", emptyList );

        return new ResponseEntity<>( new Result(myInfoService.saveMultipleNmcode(addList, modList, delList, GUEST)), OK );
    }

    /**
     *  상단로고이미지 - 이미지저장
     *
     * @param request MultipartHttpServletRequest
     * @return
     */
    @RequestMapping(value = "/titleImg/saveTitleImg.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveTitleImg(MultipartHttpServletRequest request) {
        String result = myInfoService.saveTitleImg(request);

        if(result.equals("0")) {
            return returnJson(Status.OK);
        } else if(result.equals("1")) {
            return returnJson(Status.FAIL, "msg", "이미지 저장에 실패했습니다.");
        } else if(result.equals("3")) {
            return returnJson(Status.FAIL, "msg", messageService.get("myInfo.fileExtensionChk.msg"));
        } else{
            return returnJson(Status.FAIL, "msg", "이미지 저장에 실패했습니다.");
        }
    }
}
