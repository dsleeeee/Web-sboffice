<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="userId" value="${sessionScope.sessionInfo.userId}"/>

<wj-popup control="wjNaverPlaceStatusResetLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:450px;height:250px;" fade-in="false" fade-out="false">
    <div ng-controller="naverPlaceStatusResetCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="naverPlaceStatus.statusReset"/>
            <a href="#" class="wj-hide btn_close" ng-click="close()"></a>
        </div>

        <div style="padding:10px 30px 0 30px;">
            <p class="s13 bk lh20">
                <s:message code="naverPlaceStatus.reset.msg"/>
            </p>
            <table class="tblType01 mt20">
                <colgroup>
                    <col class="w30"/>
                    <col class="w70"/>
                </colgroup>
                <tbody>
                <tr>
                    <th><s:message code="naverPlaceStatus.pwd"/></th>
                    <td>
                        <input type="password" class="sb-input" style="width: 150px;" id="resetPwd" ng-model="resetPwd"/>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body">
            <div class="btnSet tc">
                <button class="btn_blue" id="btnReset" ng-click="statusReset()"><s:message code="naverPlaceStatus.reset"/></button>
            </div>
        </div>

    </div>
</wj-popup>

<script type="text/javascript">
    var menuCd = "${menuCd}";
    var userId = "${userId}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/sys/link/naverPlaceStatus/naverPlaceStatusReset.js?ver=20260410.02" charset="utf-8"></script>