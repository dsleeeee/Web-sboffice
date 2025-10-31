<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="subCon" ng-controller="orderkitCtrl">
    <div class="searchBar">
        <a href="#" class="open fl">오더킷</a>
    </div>
</div>

<script type="text/javascript">

    // jwtToken
    var jwtToken = "${jwtToken}";

    // OMS웹뷰 새창 open
    var url = "https://kcp.onesell.co.kr/auth/pos?token=" + jwtToken;
    window.open(url, 'newWindow');

</script>

<script type="text/javascript" src="/resource/solbipos/js/orderkit/orderkit/orderkit/orderkit.js?ver=20251030.01" charset="utf-8"></script>