<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup control="wjMemberPointMoveLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:700px;height:210px;" fade-in="false" fade-out="false">

    <div ng-controller="memberPointMoveCtrl">
        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="regist.memberPointMove.info"/>
            <a href="#" class="wj-hide btn_close" ng-click="close()"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body">
            <table class="tblType01">
                <colgroup>
                    <col class="w15"/>
                    <col class="w35"/>
                    <col class="w15"/>
                    <col class="w35"/>
                </colgroup>
                <tbody>
                <tr>
                    <%-- 보내는 회원 --%>
                    <th>
                        <s:message code="regist.memberPointMove.memberSend"/>
                    </th>
                    <td>
                        <input type="text" class="sb-input w70" id="srchMemberNmSend" ng-model="memberNmSend" ng-click="popUpMemberSend()" style="float: left;"
                               placeholder="<s:message code="regist.memberPointMove.memberSearch" /> 선택" readonly/>
                        <input type="hidden" id="_memberNoSend" name="memberNoSend" ng-model="memberNoSend" disabled />
                    </td>
                    <%-- 가용포인트 --%>
                    <th>
                        <s:message code="regist.memberPointMove.pointSend"/>
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchPointSend" ng-model="pointSend" readonly />
                    </td>
                </tr>
                <tr>
                    <%-- 받는 회원 --%>
                    <th>
                        <s:message code="regist.memberPointMove.memberReceive"/>
                    </th>
                    <td>
                        <input type="text" class="sb-input w70" id="srchMemberNmReceive" ng-model="memberNmReceive" ng-click="popUpMemberReceive()" style="float: left;"
                               placeholder="<s:message code="regist.memberPointMove.memberSearch" /> 선택" readonly/>
                        <input type="hidden" id="_memberNoReceive" name="memberNoReceive" ng-model="memberNoReceive" disabled />
                    </td>
                    <%-- 이관포인트 --%>
                    <th>
                        <s:message code="regist.memberPointMove.pointReceive"/>
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchPointReceive" ng-model="pointReceive" />
                    </td>
                </tr>
                </tbody>
            </table>

            <%-- 저장 버튼 --%>
            <div class="tc mt20">
                <button id="funcSave" class="btn_blue">
                    <s:message code="cmm.save" />
                </button>
            </div>
        </div>
        <%-- //body --%>
    </div>

</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/membr/info/view/memberPointMove.js?ver=20210310.08" charset="utf-8"></script>

<%-- 회원 포인트 조회 팝업 --%>
<c:import url="/WEB-INF/view/membr/info/view/searchMemberPoint.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>