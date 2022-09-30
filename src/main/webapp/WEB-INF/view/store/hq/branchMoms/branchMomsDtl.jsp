<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd">${sessionScope.sessionInfo.currentMenu.resrceCd}</c:set>
<c:set var="menuNm">${sessionScope.sessionInfo.currentMenu.resrceNm}</c:set>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}"/>
<c:set var="orgnNm" value="${sessionScope.sessionInfo.orgnNm}"/>
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}"/>

<%@ include file="/WEB-INF/view/application/layer/searchAddr.jsp" %>

<wj-popup id="wjBranchMomsDtlLayer" control="wjBranchMomsDtlLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:600px;height:700px;">  <div id="branchMomsDtlLayer" class="wj-dialog wj-dialog-columns" ng-controller="branchMomsDtlCtrl">
    <div class="wj-dialog-header wj-dialog-header-font">
        <span id="branchMomsDtlTitle"></span>
        <a href="#" class="wj-hide btn_close"></a>
    </div>
    <div class="wj-dialog-body" class="subCon">
        <%-- body --%>
        <f:form id="regForm" name="regForm" ng-submit="submit()">
            <table class="searchTbl">
                <colgroup>
                    <col class="w15"/>
                    <col class="w35"/>
                    <col class="w15"/>
                    <col class="w35"/>
                </colgroup>
                <tbody>
                <tr style="border-top: 1px solid #ccc;">
                    <%-- 지사코드 --%>
                    <th><s:message code="branchMoms.branchCd"/></th>
                    <td>
                        <input type="text" id="branchCd" name="branchCd" ng-model="branchCd" class="sb-input w100"readonly="readonly">
                    </td>
                    <%-- 지사명 --%>
                    <th><s:message code="branchMoms.branchNm"/><em class="imp">*</em></th>
                    <td>
                        <input type="text" id="branchNm" name="branchNm" ng-model="branchNm" class="sb-input w100" maxlength="30" required/>
                    </td>
                </tr>
                <tr>
                    <%-- 지사장명 --%>
                    <th><s:message code="branchMoms.branchOwnerNm"/><em class="imp">*</em></th>
                    <td>
                        <input type="text" id="branchOwnerNm" name="branchOwnerNm" ng-model="branchOwnerNm" class="sb-input w100" maxlength="100" required/>
                    </td>
                    <%-- 전화번호 --%>
                    <th><s:message code="branchMoms.telNo"/></th>
                    <td>
                        <input type="text" id="telNo" name="telNo" ng-model="telNo" class="sb-input w100" maxlength="30">
                    </td>
                </tr>
                <tr>
                    <%-- 휴대번호 --%>
                    <th><s:message code="branchMoms.phoneNo"/></th>
                    <td>
                        <input type="text" id="phoneNo" name="phoneNo" ng-model="phoneNo" class="sb-input w100" maxlength="30">
                    </td>
                    <%-- 이메일 --%>
                    <th><s:message code="branchMoms.email"/></th>
                    <td>
                        <input type="text" id="email" name="email" ng-model="email" class="sb-input w100" maxlength="50"/>
                    </td>
                </tr>
                <tr>
                    <%-- 사용여부 --%>
                    <th><s:message code="branchMoms.useYn"/></th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                    id="useYn"
                                    ng-model="useYn"
                                    control="useYnCombo"
                                    items-source="_getComboData('useYn')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)">
                            </wj-combo-box>
                        </div>
                    </td>
                </tr>
                <tr>
                    <%-- 주소 //TODO 주소검색 추가 필요 --%>
                    <th><s:message code="branchMoms.addr"/></th>
                    <td colspan="3">
                        <input type="text" id="postNo" ng-model="postNo" class="sb-input" placeholder="우편번호" maxlength="5" style="width: 80px;" readonly/>
                        <a id="btnSrchAddr" href="#" class="btn_grayS ml5" onclick="searchAddr()">
                            <s:message code="storeManage.srchAddr" />
                        </a>
                        <input type="text" id="addr" ng-model="addr" class="sb-input w100" placeholder="주소1" maxlength="100" style="margin:4px 0px;" readonly/>
                        <input type="text" id="addrDtl" ng-model="addrDtl" class="sb-input w100" placeholder="주소2" maxlength="100"/>
                    </td>
                </tr>
                <tr style="height: 330px">
                    <%-- 비고 --%>
                    <th><s:message code="branchMoms.orgplceInfo"/></th>
                    <td colspan="3">
                        <textarea id="orgplceInfo" class="w100 tArea1" style="height:320px" ng-model="orgplceInfo"></textarea>
                    </td>
                </tr>
                </tbody>
            </table>
        </f:form>
        <div class="btnSet tc mt20 mb10">
            <%-- 저장 --%>
            <a href="#" class="btn_blue pd20" ng-click="save()"><s:message code="cmm.save"/></a>
            <a href="#" class="btn_gray" ng-click="close()"><s:message code="cmm.close" /></a>
        </div>
    </div>
    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/store/hq/branchMoms/branchMomsDtl.js?ver=20220926.01" charset="utf-8"></script>