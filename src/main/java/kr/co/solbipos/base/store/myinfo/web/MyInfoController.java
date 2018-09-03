package kr.co.solbipos.base.store.myinfo.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping( MyInfoController.PREFIX )
public class MyInfoController{
    static final String PREFIX = "/base/store/myinfo";

    @GetMapping( "/myinfo/list.sb" )
    String myInfoList( Model model ){
        model.addAttribute( "variable", "Hello Myinfo" );
        return PREFIX + "/myinfo";
    }
}