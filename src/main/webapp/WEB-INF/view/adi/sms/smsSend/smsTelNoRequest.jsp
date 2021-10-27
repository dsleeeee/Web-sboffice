<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="result" value="${result}" />

<wj-popup control="smsTelNoRequestLayer" show-trigger="Click" hide-trigger="Click" style="width:500px">
    <div class="wj-dialog wj-dialog-columns">
        <div class="wj-dialog-header wj-dialog-header-font">
            <a href="#" class="wj-hide btn_close"></a>
        </div>
        <div class="wj-dialog-body" ng-controller="registCtrl">
            <div>
                <c:if test="${result == '-2'}">
                    변조 위험 있음
                </c:if>
                <c:if test="${result == '-1'}">
                    이미 인증된 발신번호입니다
                </c:if>
                <c:if test="${result == '0'}">
                    정상 등록
                </c:if>
                <c:if test="${result != '0' and result != '-1'}">
                    발신번호 인증을 요청하는데 오류가 발생하였습니다.\n다시 시도해주십시오.\n계속 오류가 발생할 경우 관리자에게 문의해주십시오.
                </c:if>
            </div>

            <%-- 버튼 영역 --%>
            <div class="btnSet">
                <%-- 닫기 --%>
                <span><a href="#" class="btn_gray" id="btnClose" ng-click="close()"><s:message code="cmm.close" /></a></span>
            </div>
        </div>
    </div>
</wj-popup>
<script>
    // 닫기
    $scope.close = function () {
        $scope.wjVendrInfoLayer.hide();

        // 발신번호 재조회
        var scope = agrid.getScope('marketingSmsSendCtrl');
        scope.tellNumChk();
    };
</script>