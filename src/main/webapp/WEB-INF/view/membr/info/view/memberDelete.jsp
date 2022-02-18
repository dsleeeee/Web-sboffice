<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="sessionId" value="${param.sid}" />

<wj-popup control="wjMemberDeleteLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:450px;height:300px;" fade-in="false" fade-out="false">

    <div ng-controller="memberDeleteCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="regist.membr.delete"/>
            <a href="#" class="wj-hide btn_close" ng-click="closeMemberDelete()"></a>
        </div>

        <div style="padding:10px 30px 0 30px;">
            <p class="s13 bk lh20">
                <s:message code="regist.membr.delete.msg"/>
            </p>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body">
            <%-- 강제삭제(가상로그인을 통해 접속한 경우만 삭제가능) --%>
            <c:if test="${sessionId ne null}">
                <table class="tblType01" id="tbForcedDelete">
                    <colgroup>
                        <col class="w30"/>
                        <col class="w70"/>
                    </colgroup>
                    <tbody>
                    <tr>
                        <th colspan="2">
                            <div style="float: left;"><input type="checkbox" id="chkForcedDelete" ng-model="isCheckedForcedDelete" ng-change="isChkForcedDelete()" /></div>
                            <div style="padding-top: 3px; padding-left: 25px;"><s:message code="regist.membr.forcedDelete"/></div>
                        </th>
                    </tr>
                    <tr id="trForcedDeletePwd">
                        <th><s:message code="regist.membr.pwd"/></th>
                        <td>
                            <input type="password" class="sb-input" style="width: 150px;" id="forcedDeletePwd" ng-model="forcedDeletePwd" />
                        </td>
                    </tr>
                    </tbody>
                </table>
            </c:if>
            <div class="btnSet tc mt20">
                <button class="btn_blue" id="btnSelectMembrDelete" ng-click="selectMembrDelete()"><s:message code="regist.membr.selectDelete"/></button>
                <button class="btn_blue" id="btnAllMembrDelete" ng-click="allMembrDelete()"><s:message code="regist.membr.allDelete"/></button>
            </div>
        </div>
    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/membr/info/view/memberDelete.js?ver=20220216.02"
        charset="utf-8"></script>