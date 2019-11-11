<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>

<div id="agencyInfoView" class="subCon" ng-controller="agencyInfoCtrl" style="display:none;">

    <%--============================================= 탭 =============================================--%>
    <ul class="subTab">
        <%-- 설치업체관리 업체정보 --%>
        <li><a id="agencyInfoTab" href="#" class="on"><s:message code="instlAgency.agencyInfo" /></a></li>
        <%-- 설치업체관리 사원관리 --%>
        <li><a id="empManageTab"  href="#" ng-click="changeTabInstlAgency('emp');"><s:message code="instlAgency.empManage" /></a></li>
        <%-- 설치업체관리 인증관리 --%>
        <li><a id="authManageTab"  href="#" ng-click="changeTabInstlAgency('auth');"><s:message code="instlAgency.authManage" /></a></li>
    </ul>
    <div style="padding:10px; height:50px;">
        <%-- 조회 --%>
        <button class="btn_skyblue ml5 fr" id="btnNewRegAgency" ng-click="newRegAgency()">
            <s:message code="instlAgency.newReg"/>
        </button>
    </div>

    <form name="agencyForm">
        <table class="tblType01 mt10" style="border-top: 1px solid #CCCCCC;">
            <colgroup>
                <col class="w15" />
                <col class="w35" />
                <col class="w15" />
                <col class="w35" />
            </colgroup>
            <tbody>
                <%--<tr>
                    &lt;%&ndash; 총판구분 &ndash;%&gt;
                    <th></th>
                    <td></td>
                    &lt;%&ndash; 총판선택 &ndash;%&gt;
                    <th></th>
                    <td></td>
                </tr>--%>
                <tr>
                    <th><s:message code="instlAgency.agencyCd" />/<s:message code="instlAgency.agencyNm" /></th>
                    <td><input type="text" readonly ng-model="ai_agencyCd" class="sb-input" style="width:90px;"> /
                        <input type="text" id="ai_agencyNm" name="ai_agencyNm" ng-model="ai_agencyNm" class="sb-input" style="width:167px;" maxlength="50"
                               required
                               popover-enable="agencyForm.ai_agencyNm.$invalid"
                               popover-placement="bottom-left"
                               popover-trigger="'mouseenter'"
                               uib-popover="<s:message code="instlAgency.agencyNm" />은(는) 필수 입력항목 입니다."/>
                    </td>
                    <%-- 대표자명 --%>
                    <th><s:message code="instlAgency.ownerNm" /></th>
                    <td><input type="text" id="ai_ownerNm" name="ai_ownerNm" ng-model="ai_ownerNm" class="sb-input w100" maxlength="25"
                               required
                               popover-enable="agencyForm.ai_ownerNm.$invalid"
                               popover-placement="bottom-left"
                               popover-trigger="'mouseenter'"
                               uib-popover="<s:message code="instlAgency.ownerNm" />은(는) 필수 입력항목 입니다."/>
                    </td>
                </tr>
                <tr>
                    <%-- 사업자번호 --%>
                    <th><s:message code="instlAgency.bizNo" /></th>
                    <td><input type="text" id="ai_bizNo" name="ai_bizNo" ng-model="ai_bizNo" class="sb-input w100" maxlength="10"
                               required
                               popover-enable="agencyForm.ai_bizNo.$invalid"
                               popover-placement="bottom-left"
                               popover-trigger="'mouseenter'"
                               uib-popover="<s:message code="instlAgency.bizNo" />은(는) 필수 입력항목 입니다."/>
                    </td>
                    <%-- 상호명 --%>
                    <th><s:message code="instlAgency.bizStoreNm" /></th>
                    <td><input type="text" id="ai_bizStoreNm" name="ai_bizStoreNm" ng-model="ai_bizStoreNm" class="sb-input w100" maxlength="50"
                               required
                               popover-enable="agencyForm.ai_bizStoreNm.$invalid"
                               popover-placement="bottom-left"
                               popover-trigger="'mouseenter'"
                               uib-popover="<s:message code="instlAgency.bizStoreNm" />은(는) 필수 입력항목 입니다."/>
                    </td>
                </tr>
                <%--<tr>
                    &lt;%&ndash; 업태 &ndash;%&gt;
                    <th><s:message code="instlAgency.bizItem" /></th>
                    <td><input type="text" id="bizItem" name="bizItem" ng-model="bizItem" class="sb-input w100" maxlength="15"/></td>
                    &lt;%&ndash; 업체구분 &ndash;%&gt;
                    <th><s:message code="instlAgency.bizType" /></th>
                    <td><input type="text" id="bizType" name="bizType" ng-model="bizType" class="sb-input w100" maxlength="5"/></td>
                </tr>--%>
                <tr>
                    <%-- 전화번호 --%>
                    <th><s:message code="instlAgency.telNo" /></th>
                    <td><input type="text" id="ai_telNo" name="ai_telNo" ng-model="ai_telNo" class="sb-input w100" maxlength="15"
                               required
                               popover-enable="agencyForm.ai_telNo.$invalid"
                               popover-placement="bottom-left"
                               popover-trigger="'mouseenter'"
                               uib-popover="<s:message code="instlAgency.telNo" />은(는) 필수 입력항목 입니다."/>
                    </td>
                    <%-- 팩스번호 --%>
                    <th><s:message code="instlAgency.faxNo" /></th>
                    <td><input type="text" id="ai_faxno" name="ai_faxno" ng-model="ai_faxno" class="sb-input w100" maxlength="15" /></td>
                </tr>
                <tr>
                    <%-- 이메일주소 --%>
                    <th><s:message code="instlAgency.emailAddr" /></th>
                    <td><input type="text" id="ai_emailAddr" name="ai_emailAddr" ng-model="ai_emailAddr" class="sb-input w100" maxlength="100"/></td>
                    <%-- 홈페이지주소 --%>
                    <th><s:message code="instlAgency.hmpgAddr" /></th>
                    <td><input type="text" id="ai_hmpgAddr" name="ai_emailAddr" ng-model="ai_emailAddr" class="sb-input w100" maxlength="100"/></td>
                </tr>
                <tr>
                    <%-- 주소 --%>
                    <th rowspan="3"><s:message code="instlAgency.addr" /></th>
                    <td colspan="3"><input type="text" id="ai_postNo" name="ai_postNo" ng-model="ai_postNo" class="sb-input w20" maxlength="5" style="width:90px;"
                                           required
                                           popover-enable="agencyForm.ai_postNo.$invalid"
                                           popover-placement="bottom-left"
                                           popover-trigger="'mouseenter'"
                                           uib-popover="<s:message code="instlAgency.postNo" />은(는) 필수 입력항목 입니다."/>
                                      <a href="#" class="btn_grayS ml5"><s:message code="instlAgency.addrSearch" /></a>
                    </td>
                </tr>
                <tr>
                    <td colspan="3"><input type="text" id="ai_addr" name="ai_addr" ng-model="ai_addr" class="sb-input w100" maxlength="100"
                                           required
                                           popover-enable="agencyForm.ai_addr.$invalid"
                                           popover-placement="bottom-left"
                                           popover-trigger="'mouseenter'"
                                           uib-popover="<s:message code="instlAgency.addr" />은(는) 필수 입력항목 입니다."/>
                    </td>
                </tr>
                <tr>
                    <td colspan="3"><input type="text" id="ai_addrDtl" name="ai_addrDtl" ng-model="ai_addrDtl" class="sb-input w100" maxlength="100"
                                           required
                                           popover-enable="agencyForm.ai_addrDtl.$invalid"
                                           popover-placement="bottom-left"
                                           popover-trigger="'mouseenter'"
                                           uib-popover="<s:message code="instlAgency.addrDtl" />은(는) 필수 입력항목 입니다."/>
                    </td>
                </tr>
                <tr>
                    <%-- 비고 --%>
                    <th><s:message code="instlAgency.remark" /></th>
                    <td colspan="3"><input type="text" id="ai_remark" name="ai_remark" ng-model="ai_remark" class="sb-input w100" maxlength="250" /></td>
                    <%-- 저장타입 지정 --%>
                    <input type="hidden" id="ai_saveType" name="ai_saveType" ng-model="ai_saveType">
                </tr>
            </tbody>
        </table>
        <%-- 저장 --%>
        <div class="btnSet" class="mt10 mb10" align="center" style="padding:20px;">
            <button class="btn btn_blue" ng-click="agencyForm.$valid && saveAgency()"><s:message code="cmm.save"/></button>
        </div>
    </form>
</div>

<script>

</script>
<script type="text/javascript" src="/resource/solbipos/js/pos/license/instlAgency/agencyInfo.js?ver=20191023.02" charset="utf-8"></script>
