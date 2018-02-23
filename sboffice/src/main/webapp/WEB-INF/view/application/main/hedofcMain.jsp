<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

본사 관리자 메인 hedofcMain
<br>
<input type="button" value="asdf" id="asdf" />
<br>

<script>
  $("#asdf").click(function() {
    // s_alert.pop("레이어 팝업 테스트");

    
//     s_alert.pop("zxcv");

    /* 
    s_alert.popOk("aaaa", function() {
     console.log("확인");
    });
     */
     
     
    s_alert.popConf("aaaa", function() {
      console.log("확인");
    }, function() {
      console.log("취소");
    });
     
     
  });
</script>



