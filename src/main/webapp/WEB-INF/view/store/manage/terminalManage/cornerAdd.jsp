<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup id="cornerAddLayer" control="cornerAddLayer" show-trigger="Click" hide-trigger="Click" style="display: none;width:650px;height:500px;">
    <div class="wj-dialog wj-dialog-columns title" ng-controller="cornerAddCtrl">
        <form name="myform">
            <div class="wj-dialog-header wj-dialog-header-font">
                <s:message code="terminalManage.cornrAdd" />
                <span id="storePosAddTitle" class="ml20"></span>
                <a href="#" class="wj-hide btn_close"></a>
            </div>

            <div class="wj-dialog-body sc2" style="overflow-y: hidden;">
                <%--매장정보--%>
                <h2 class="h2_tit mt5">
                    [<label id="cnr_storeCd"></label>]
                    <label id="cnr_storeNm"></label>
                </h2>

                <%-- 상세 --%>
                <div id="dtlArea" style="height: 266px; overflow-y: auto;">

                    <table class="tblType01">
                        <colgroup>
                            <col class="w20" />
                            <col class="w30" />
                            <col class="w20" />
                            <col class="w30" />
                        </colgroup>
                        <tbody>
                        <tr>
                            <%-- 코너명 --%>
                            <th>
                                <div class="impWrap" align="center"><s:message code="terminalManage.cornrNm" /></div>
                            </th>
                            <td align="center">
                                <input type="text" id="cornrNm" name="cornrNm" class="sb-input w100" maxlength="20"
                                       ng-model="cornrNm"
                                       required
                                       popover-enable="myform.cornrNm.$invalid"
                                       popover-placement="bottom-left"
                                       popover-trigger="'mouseenter'"
                                       uib-popover="<s:message code="terminalManage.cornrNm" />은(는) 필수 입력항목 입니다."/>
                            </td>
                            <%-- 대표자 --%>
                            <th>
                                <div class="impWrap" align="center"><s:message code="terminalManage.owner" /></div>
                            </th>
                            <td align="center">
                                <input type="text" id="ownerNm" name="ownerNm" ng-model="ownerNm" class="sb-input w100" maxlength="20">
                            </td>
                        </tr>
                        <tr>
                            <%-- 사업자번호 --%>
                            <th>
                                <div class="impWrap" align="center"><s:message code="terminalManage.bizNo" /></div>
                            </th>
                            <td align="center">
                                <input type="text" id="bizNo" name="bizNo" class="sb-input w100" maxlength="10"
                                       ng-model="bizNo"
                                       required
                                       popover-enable="myform.bizNo.$invalid"
                                       popover-placement="bottom-left"
                                       popover-trigger="'mouseenter'"
                                       uib-popover="<s:message code="terminalManage.bizNo" />은(는) 필수 입력항목 입니다."/>
                            </td>
                            <%-- 전화번호 --%>
                            <th>
                                <div class="impWrap" align="center"><s:message code="terminalManage.telNo" /></div>
                            </th>
                            <td align="center">
                                <input type="text" id="telNo" name="telNo" ng-model="telNo" class="sb-input w100" maxlength="15">
                            </td>
                        </tr>
                        <tr>
                            <%-- VAN사 코드 --%>
                            <th>
                                <div class="impWrap" align="center"><s:message code="terminalManage.vanCd" /></div>
                            </th>
                            <td align="center">
                                <input type="text" id="vanCd" name="vanCd" ng-model="vanCd" class="sb-input w100" maxlength="3">
                            </td>
                            <%-- VAN 터미널 번호--%>
                            <th>
                                <div class="impWrap" align="center"><s:message code="terminalManage.vanTermnlNo" /></div>
                            </th>
                            <td align="center">
                                <input type="text" id="vanTermnlNo" name="vanTermnlNo" ng-model="vanTermnlNo" class="sb-input w100" maxlength="20">
                            </td>
                        </tr>
                        <tr>
                            <%-- VAN 일련번호--%>
                            <th>
                                <div class="impWrap" align="center"><s:message code="terminalManage.vanSerNo" /></div>
                            </th>
                            <td align="center">
                                <input type="text" id="vanSerNo" name="vanSerNo" ng-model="vanSerNo" class="sb-input w100" maxlength="20">
                            </td>
                            <th></th>
                            <td></td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>

            <div class="wj-dialog-footer">
                <%-- 저장--%>
                <button class="btn btn_blue" ng-click="myform.$valid && saveCorner()"><s:message code="cmm.save"/></button>
                <%-- 닫기 --%>
                <button class="btn btn_blue" ng-click="close()"><s:message code="cmm.close"/></button>
            </div>
        </form>
    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/store/manage/terminalManage/cornerAdd.js?ver=20200303" charset="utf-8"></script>