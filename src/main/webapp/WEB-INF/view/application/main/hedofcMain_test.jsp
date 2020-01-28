<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}" />

<div class="contents">
    <div class="mainCon" id="mainCon">
        <%--<img src="/resource/solbipos/css/img/test/testimg5.PNG" style="width:100%" alt="artisee" />--%>
    </div>
</div>

<script>
    // 본사 별 지정된 메인이미지 셋팅
    var mainImg = "${hqOfficeCd}".toLowerCase() + ".png";
    $("#mainCon").html("<img src=\"/resource/solbipos/css/img/test/hqImg/" + mainImg + "\" style=\"width:100%\" onerror=\"this.src='/resource/solbipos/css/img/test/testimg5.PNG'\" />");
</script>
