package kr.co.solbipos.base.store.myinfo.web;

import kr.co.common.data.structure.Result;
import kr.co.solbipos.base.store.myinfo.service.MyInfoService;
import kr.co.solbipos.base.store.myinfo.service.MyInfoVO;
import kr.co.solbipos.base.store.myinfo.service.WijmoGridVO;
import kr.co.solbipos.store.hq.hqmanage.service.HqNmcodeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private MyInfoService service;

    @GetMapping( "/myInfo/list.sb" )
    public String myInfoList( Model model ){
        model.addAttribute( "myInfo", service.getMyInfo() );
        model.addAttribute( "areaCds", service.nmcodeList(AREA) );
        return PREFIX.toLowerCase() + "/myinfo";
    }

    @PostMapping( "/myInfo/save.sb" )
    public ResponseEntity< Result > modifyMyInfo( @RequestBody MyInfoVO myInfo ){
        return new ResponseEntity<>( new Result(service.modifyMyInfo(myInfo)), OK );
    }

    @GetMapping( "/storeType/list.sb" )
    public ResponseEntity< WijmoGridVO<HqNmcodeVO> > storeTypeList(){
        return new ResponseEntity<>( service.getGridInfo(STORE_TYPE), OK );
    }

    @PostMapping( "/storeType/save.sb" )
    public ResponseEntity< Result > saveStoreType( @RequestBody Map<String, List<HqNmcodeVO>> params ){
        List< HqNmcodeVO > emptyList = Collections.emptyList(),
                addList = params.getOrDefault("addItems", emptyList ),
                modList = params.getOrDefault("modItems", emptyList ),
                delList = params.getOrDefault("delItems", emptyList );

        return new ResponseEntity<>( new Result(service.saveMultipleNmcode(addList, modList, delList, STORE_TYPE)), OK );
    }

    @GetMapping( "/grp/list.sb" )
    public ResponseEntity< WijmoGridVO<HqNmcodeVO> > grpList(){
        return new ResponseEntity<>( service.getGridInfo(GRP), OK );
    }

    @PostMapping( "/grp/save.sb" )
    public ResponseEntity< Result > saveGrp( @RequestBody Map<String, List<HqNmcodeVO>> params ){
        List< HqNmcodeVO > emptyList = Collections.emptyList(),
                addList = params.getOrDefault("addItems", emptyList ),
                modList = params.getOrDefault("modItems", emptyList ),
                delList = params.getOrDefault("delItems", emptyList );

        return new ResponseEntity<>( new Result(service.saveMultipleNmcode(addList, modList, delList, GRP)), OK );
    }

    @GetMapping( "/hour/list.sb" )
    public ResponseEntity< WijmoGridVO<HqNmcodeVO> > hourList(){
        return new ResponseEntity<>( service.getGridInfo(HOUR), OK );
    }

    @PostMapping( "/hour/save.sb" )
    public ResponseEntity< Result > saveHour( @RequestBody Map<String, List<HqNmcodeVO>> params ){
        List< HqNmcodeVO > emptyList = Collections.emptyList(),
                addList = params.getOrDefault("addItems", emptyList ),
                modList = params.getOrDefault("modItems", emptyList ),
                delList = params.getOrDefault("delItems", emptyList );

        return new ResponseEntity<>( new Result(service.saveMultipleNmcode(addList, modList, delList, HOUR)), OK );
    }

    @GetMapping( "/guest/list.sb" )
    public ResponseEntity< WijmoGridVO<HqNmcodeVO> > guestList(){
        return new ResponseEntity<>( service.getGridInfo(GUEST), OK );
    }

    @PostMapping( "/guest/save.sb" )
    public ResponseEntity< Result > saveGuest( @RequestBody Map<String, List<HqNmcodeVO>> params ){
        List< HqNmcodeVO > emptyList = Collections.emptyList(),
                addList = params.getOrDefault("addItems", emptyList ),
                modList = params.getOrDefault("modItems", emptyList ),
                delList = params.getOrDefault("delItems", emptyList );

        return new ResponseEntity<>( new Result(service.saveMultipleNmcode(addList, modList, delList, GUEST)), OK );
    }
}