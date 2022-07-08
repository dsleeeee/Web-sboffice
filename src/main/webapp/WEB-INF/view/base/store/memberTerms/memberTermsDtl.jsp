<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>

<wj-popup control="wjMemberTermsDtlLayer" show-trigger="Click" hide-trigger="Click" style="display: none; width:750px;height:340px;">
    <div class="wj-dialog wj-dialog-columns title">
        <div ng-controller="memberTermsDtlCtrl">

            <%-- header --%>
            <div class="wj-dialog-header wj-dialog-header-font">
                <s:message code="memberTermsDtl.info"/>
                <a href="#" class="wj-hide btn_close"></a>
            </div>

            <%-- body --%>
            <div class="wj-dialog-body">
                <c:if test="${orgnFg == 'HQ'}">
                    <%-- 탭 --%>
                    <ul class="subTab">
                        <%-- 버전정보 --%>
                        <li><a id="storeInfo" href="#" class="on"><s:message code="memberTermsDtl.verInfo" /></a></li>
                        <%-- 적용매장 --%>
                        <li style="display: none;"><a id="storeEnv" href="#" ng-click="changeTab();"><s:message code="memberTermsDtl.store.registed" /></a></li>
                    </ul>
                </c:if>
                <div>
                    <div style="height:170px; overflow-y: auto;">
                        <f:form id="viewForm" name="viewForm" >
                            <h3 class="h3_tbl"><s:message code="memberTermsDtl.basicInfo" /></h3>
                            <table class="searchTbl">
                                <colgroup>
                                    <col class="w15" />
                                    <col class="w35" />
                                    <col class="w15" />
                                    <col class="w35" />
                                </colgroup>
                                <tbody>
                                <tr>
                                    <%-- 파일번호 --%>
                                    <th>
                                        <s:message code="memberTermsDtl.verSerNo" />
                                    </th>
                                    <td>
                                        {{version.verSerNo}}
                                    </td>
                                    <%-- 파일명 --%>
                                    <th>
                                        <s:message code="memberTermsDtl.verSerNm" />
                                    </th>
                                    <td>
                                        {{version.verSerNm}}
                                    </td>
                                </tr>
                                <tr>
                                    <%-- 사용일자 --%>
                                    <th>
                                        <s:message code="memberTermsDtl.useDate" />
                                    </th>
                                    <td>
                                        {{version.useDate}}
                                    </td>
                                    <%-- 파일사이즈 --%>
                                    <th>
                                        <s:message code="memberTermsDtl.fileSize" />
                                    </th>
                                    <td>
                                        {{version.fileSize}}
                                    </td>
                                </tr>
                                <tr>
                                    <%-- 파일타입 --%>
                                    <th>
                                        <s:message code="memberTermsDtl.fileType" />
                                    </th>
                                    <td>
                                        <wj-combo-box
                                                ng-model="version.fileType"
                                                ng-hide="true"
                                                text="_fileType"
                                                items-source="_getComboData('fileTypeCombo')"
                                                display-member-path="name"
                                                selected-value-path="value"
                                                is-editable="false">
                                        </wj-combo-box>
                                        {{_fileType}}
                                    </td>
                                    <%-- 사용여부 --%>
                                    <th>
                                        <s:message code="memberTermsDtl.useYn" />
                                    </th>
                                    <td>
                                        <wj-combo-box
                                                ng-model="version.useYn"
                                                ng-hide="true"
                                                text="_useYn"
                                                items-source="_getComboData('useYnCombo')"
                                                display-member-path="name"
                                                selected-value-path="value"
                                                is-editable="false">
                                        </wj-combo-box>
                                        {{_useYn}}
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </f:form>
                    </div>
                    <div class="btnSet2">
                        <%-- 닫기 --%>
                        <span><a href="#" class="btn_blue pd20" ng-click="close()"><s:message code="cmm.close" /></a></span>
                    </div>
                </div>
            </div>
            <%-- //body --%>

        </div>
    </div>
</wj-popup>

<script type="text/javascript">
    <%-- 사용여부 --%>
    var useYnComboData = ${ccu.getCommCode("067")};
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/store/memberTerms/memberTermsDtl.js?ver=20220708.01" charset="utf-8"></script>