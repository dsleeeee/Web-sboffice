<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head><title></title>
    <script type="text/javascript" src="/resource/vendor/jquery/jquery-2.2.4.min.js"></script>
</head>
</html>

<script type="text/javascript">

    // 이전 화면(연동 단계 파악)
    var prePage = "${prePage}";

    if (prePage == "login") {
        /*goAgreePop();*/

        if (window.opener && !window.opener.closed) {
            window.opener.location.reload();
        }
        window.close();

    } /*else if (prePage == "agree") {
        if (window.opener && !window.opener.closed) {
            window.opener.location.reload();
        }
        window.close();
    }

    // 약관동의 팝업 이동
    function goAgreePop() {

        // 약관동의 팝업 후
        //var redirectURL = encodeURIComponent("https://neo.lynk.co.kr" + "/naverPlace/naverPlace/naverOrderLink/naverOrderPop.sb");
        var redirectURL = encodeURIComponent("http://" + window.location.host + "/naverPlace/naverPlace/naverOrderLink/naverOrderPop.sb");
        var popupUrl = sessionStorage.getItem("popUrl") + "/embed/terms?service=lynk_pos,mybiz,booking&to=" + redirectURL;
        var popup = window.open(popupUrl, "popup", "width=750, height=1000");
    }*/

</script>