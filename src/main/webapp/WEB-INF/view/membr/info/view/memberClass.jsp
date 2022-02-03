<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd">${sessionScope.sessionInfo.currentMenu.resrceCd}</c:set>
<c:set var="menuNm">${sessionScope.sessionInfo.currentMenu.resrceNm}</c:set>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}"/>
<c:set var="storeCd" value="${sessionScope.sessionInfo.storeCd}"/>
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}"/>

<div class="subCon">
    <div ng-controller="memberClassCtrl">
        <div class="searchBar flddUnfld">
            <a href="#" class="open fl">${menuNm}</a>
            <%-- 조회 --%>
            <!-- 저장-->
            <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                <button class="btn_blue fr" id="btnSearch" ng-if="userUseYn" ng-click="classSave()">
                    <s:message code="cmm.save"/>
                </button>
            </div>
            <!-- 신규등록-->
            <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                <button class="btn_blue fr" id="btnSave" ng-if="userUseYn" ng-click="newAdd()">
                    <s:message code="cmm.new.add"/>
                </button>
            </div>
        </div>
        <table class="searchTbl">
            <colgroup>
                <col class="w15"/>
                <col class="w35"/>
                <col class="w15"/>
                <col class="w35"/>
            </colgroup>
            <tbody>
            <tr>
                <%-- 등급코드/명/기본여부 --%>
                <th><s:message code="grade.membr.class.cd.nm"/><em class="imp">*</em></th>
                <td colspan="3">
                    <input type="text" id="membrClassCd" class="sb-input w10 fl mr10" ng-model="detailData.membrClassCd" maxlength="3" ng-readonly="detailData.regDt !== undefined" />
                    <input type="text" id="membrClassNm" class="sb-input w30 fl mr10" ng-model="detailData.membrClassNm" maxlength="15" />
                    <div class="sb-select fl w20 mr10">
                        <wj-combo-box
                                id="membrClass"
                                ng-model="detailData.membrClass"
                                control="membrClassCombo"
                                items-source="_getComboData('membrClass')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)">
                        </wj-combo-box>
                    </div>
                </td>
            </tr>
            <tr>
                <%-- 할인율 --%>
                <th><s:message code="grade.membr.dc.rate"/><em class="imp">*</em></th>
                <td>
                    <input type="text" id="dcRate" class="sb-input w80" ng-model="detailData.dcRate"
                           maxlength="3" placeholder="<s:message code="grade.membr.dc.rate"/><s:message code='cmm.regexp' arguments='3'/>"/>
                    <s:message code="grade.membr.per"/> <s:message code="grade.membr.discnt"/>
                </td>
                <%-- 신규가입시 부여 Point --%>
                <th><s:message code="grade.membr.new.join.save.point"/><em class="imp">*</em></th>
                <td>
                    <input type="text" id="newJoinSavePoint" class="sb-input w80" ng-model="detailData.newJoinSavePoint"
                           maxlength="8" placeholder="<s:message code="grade.membr.new.join.save.point"/><s:message code='cmm.regexp' arguments='8'/>"/>
                    <s:message code="grade.membr.amt"/>
                </td>
            </tr>
            <tr>
                <%-- 최소 사용 Point --%>
                <th><s:message code="grade.membr.min.use.point"/><em class="imp">*</em></th>
                <td>
                    <input type="text" id="minUsePoint" class="sb-input w50" ng-model="detailData.minUsePoint" maxlength="8"/>
                    <s:message code="grade.membr.min.use.point.message"/>
                </td>
                <%-- 첫거래 적립 Point --%>
                <th><s:message code="grade.membr.first.sale.save.point"/><em class="imp">*</em></th>
                <td>
                    <input type="text" id="firstSaleSavePoint" class="sb-input w80" ng-model="detailData.firstSaleSavePoint"
                           maxlength="3" placeholder="<s:message code="grade.membr.first.sale.save.point"/><s:message code='cmm.regexp' arguments='3'/>"/>
                    <s:message code="grade.membr.per"/>
                </td>
            </tr>
            <tr>
                <%-- 최대 사용 포인트 --%>
                <th><s:message code="grade.membr.max.use.point"/><em class="imp">*</em></th>
                <td>
                    <input type="text" id="maxUsePoint" class="sb-input w50" ng-model="detailData.maxUsePoint" maxlength="8"/>
                    <s:message code="grade.membr.max.use.point.message"/>
                </td>
                <%-- 포인트 사용시 적립여부 --%>
                <th><s:message code="grade.membr.point.yn"/></th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                                id="useAccPointYn"
                                ng-model="detailData.useAccPointYn"
                                control="membrPointYnCombo"
                                items-source="_getComboData('membrDcYn')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)">
                        </wj-combo-box>
                    </div>
                </td>
            </tr>
            <tr>
                <%-- 할인시 적립 여부 --%>
                <th><s:message code="grade.membr.dc.yn"/></th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                                id="dcAccPointYn"
                                ng-model="detailData.dcAccPointYn"
                                control="membrDcYnCombo"
                                items-source="_getComboData('membrDcYn')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)">
                        </wj-combo-box>
                    </div>
                </td>
                <%-- 할인한도액 --%>
                <th><s:message code="grade.membr.dc.max"/><em class="imp">*</em></th>
                <td>
                    <input type="text" id="dcLimitAmt" class="sb-input w100" ng-model="detailData.dcLimitAmt" maxlength="8"/>
                </td>
            </tr>
            <tr>
                <%-- 기념일 적립 여부 --%>
                <th><s:message code="grade.membr.anvsr.point.save.fg"/></th>
                <td>
                    <div class="sb-select w50 fl mr10">
                        <wj-combo-box
                                id="anvsrPointSaveFg"
                                ng-model="detailData.anvsrPointSaveFg"
                                control="membrAnvsrYnCombo"
                                items-source="_getComboData('membrAnvsrYn')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)">
                        </wj-combo-box>
                    </div>
                    <input type="text" id="anvsrSavePoint" ng-readonly="detailData.anvsrPointSaveFg === ''" class="sb-input w10 fl " ng-model="detailData.anvsrSavePoint" maxlength="3"/>
                    <span class="txtIn mt10"><s:message code="grade.membr.per"/> <s:message code="grade.membr.plus"/></span>
                </td>
                <%-- 포인트적립구분 --%>
                <th><s:message code="grade.membr.point.save.fg"/></th>
                <td ng-switch on="detailData.pointSaveFg">
                    <div class="sb-select w50 fl">
                        <wj-combo-box
                                id="pointSaveFg"
                                ng-model="detailData.pointSaveFg"
                                control="pointSaveFgCombo"
                                items-source="_getComboData('pointSaveFg')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)">
                        </wj-combo-box>
                        <div style="display: none">
                            {{detailData.prePointSaveFg}}
                        </div>
                    </div>
                    <span class="txtIn mt10" ng-switch-when="1"> % 적립</span>
                    <span class="txtIn mt10" ng-switch-when="2"> 원당 1Point</span>
                </td>
            </tr>
            </tbody>
        </table>

        <div class="wj-TblWrap mt20 mb20 w70 fl">
            <%--위즈모 테이블--%>
            <div class="wj-TblWrapBr mr10 pd20" style="height: 470px; overflow-y: hidden;">
                <div class="updownSet oh mb10">
                    <span class="fl bk lh30 mr10"><s:message code='grade.membr.orgn.list'/></span>
                    <%--삭제--%>
                    <button class="btn_skyblue" id="btnDel"  ng-if="userUseYn" ng-click="classDel()"><s:message code='cmm.del' /></button>
                </div>
                <%-- 회원목록 그리드 --%>
                <div class="wj-gridWrap" style="height:370px; overflow-y: hidden;">
                    <div class="row">
                        <wj-flex-grid
                                control="flex"
                                autoGenerateColumns="false"
                                selection-mode="Row"
                                initialized="initGrid(s,e)"
                                items-source="data"
                                item-formatter="_itemFormatter"
                                sticky-headers="true"
                                frozen-columns="2"
                                sorted-column="toggleFreeze(false)">

                            <!-- define columns -->
                            <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="grade.membr.class.cd"/>" binding="membrClassType" align="left" width="100" is-read-only="true"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="grade.membr.deflt.yn"/>" binding="defltYn" align="center" width="63" is-read-only="true"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="grade.membr.dc.rate"/>" binding="dcRate" align="center" is-read-only="true" visible="false" width="80"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="grade.membr.new.join.save.point"/>" binding="newJoinSavePoint" align="right" width="120" is-read-only="true"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="grade.membr.first.sale.save.point"/>" binding="firstSaleSavePoint" width="108" align="right" is-read-only="true"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="grade.membr.min.use.point"/>" binding="minUsePoint" width="93" align="right" is-read-only="true"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="grade.membr.max.use.point"/>" binding="maxUsePoint" is-read-only="true" width="93"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="grade.membr.point.save.fg"/>" binding="pointSaveFgNm" width="97" align="center" is-read-only="true"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="grade.membr.dc.yn"/>" binding="dcAccPointYn" data-map="useYn" width="97" align="center" is-read-only="true"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="grade.membr.point.yn"/>" binding="useAccPointYn" data-map="useAccPointYn" width="135" align="center" is-read-only="true"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="grade.membr.dc.max"/>" binding="dcLimitAmt" data-map="dcLimitAmt" width="73" align="right" is-read-only="true"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="grade.membr.anvsr.point.save.fg"/>" binding="anvsrPointSaveFgNm" data-map="anvsrPointSaveFg" width="133" align="center" is-read-only="true"></wj-flex-grid-column>
                        </wj-flex-grid>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="wj-TblWrap mt20 mb20 w30 fr" ng-controller="memberClassDetailCtrl">
        <%--위즈모 테이블--%>
        <div class="wj-TblWrapBr ml10 pd20" style="height: 470px; overflow-y: hidden;">
            <div class="updownSet oh mb10" ng-switch="classData.pointSaveFg">
                <span class="fl bk lh30 mr10" ng-switch-default><s:message code='grade.membr.point.list.rate'/></span>
                <span class="fl bk lh30 mr10" ng-switch-when="1"><s:message code='grade.membr.point.list.rate'/></span>
                <span class="fl bk lh30 mr10" ng-switch-when="2"><s:message code='grade.membr.point.list.amt'/></span>
                <%--추가--%>
                <button class="btn_skyblue" id="membrPointAdd" ng-if="userUseYn" ng-click="pointAdd()"><s:message code="cmm.add"/></button>
                <%--저장--%>
                <button class="btn_skyblue" id="membrPointSave" ng-if="userUseYn" ng-click="pointSave()"><s:message code="cmm.save"/></button>
                <%--삭제--%>
                <button class="btn_skyblue" id="membrPointDel" ng-if="userUseYn" ng-click="pointDel()"><s:message code="cmm.del"/></button>

                <span class="fl bk lh30 mr10" ng-if="userUseYn === false && classData.pointSaveFg === '2'">원당 1포인트</span>

                <input type="text" class="sb-input fl w15"
                       ng-attr-placeholder="{{classData.pointSaveFg === '2' ? '원당1Point' : '%적립'}}"
                       ng-style="userUseYn ? { 'font-size': '0.8rem'} : {'display': 'none' }"
                       ng-attr-maxlength="{{classData.pointSaveFg === '2' ? '5' : '3'}}"
                       ng-model="membrTotal"/>

                <%--일괄등록--%>
                <button class="btn_skyblue fl ml10" id="membrTotalbtn" ng-if="userUseYn" ng-click="pointTotal()"><s:message code="grade.membr.total.button"/></button>
            </div>

            <%-- 개발시 높이 조절해서 사용--%>
            <%-- tbody영역의 셀 배경이 들어가는 부분은 .bdBg를 넣어주세요. --%>
            <div class="wj-gridWrap" style="height:370px; overflow-y: hidden;">
                <div class="row" ng-switch="classData.pointSaveFg">
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            control="flex"
                            beginning-edit="beginningEdit(s,e)"
<%--                            sorting-column="sortingColumn(s,e)"--%>
                            cell-edit-ended="cellEditEnded(s,e)"
                            initialized="initGrid(s,e)"
                            sticky-headers="true"
                            selection-mode="Row"
                            items-source="data"
                            item-formatter="_itemFormatter"
                            ime-enabled="true">
                    <%--                            frozen-columns="2"--%>
                    <%--                            sorted-column="toggleFreeze(false)"--%>
                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="systemCd.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="grade.membr.class.cd"/>" binding="membrOrgnCd" is-read-only="true" width="*" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="grade.membr.class.cd"/>" binding="initPayCd" is-read-only="true" width="*" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="grade.membr.class.cd"/>" binding="membrClassType" is-read-only="true" width="*"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="grade.membr.pay.code"/>" binding="payCd" data-map="payCdDataMap" width="*"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="grade.membr.acc_rate"/>" ng-switch-default  max-length="3" binding="accRate" width="*"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="grade.membr.acc_rate"/>" ng-switch-when="1" max-length="3" binding="accRate" width="*"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="grade.membr.point.list.amt"/>" ng-switch-when="2" binding="accRate" width="*" max-length="5"></wj-flex-grid-column>
                    </wj-flex-grid>
                    <%-- ColumnPicker 사용시 include --%>
                    <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
                        <jsp:param name="pickerTarget" value="detailCtrl"/>
                    </jsp:include>
                    <%--// ColumnPicker 사용시 include --%>
                </div>
            </div>
            <%--//위즈모 테이블--%>
        </div>
    </div>
    <%-- 페이지 리스트 --%>
    <div class="pageNum mt20">
        <%-- id --%>
        <ul id="memberClassCtrlPager" data-size="10">
        </ul>
    </div>
    <%--//페이지 리스트--%>
</div>

<script>
    <%--var detailData = ${detailData};--%>
    var recvDataMap = ${ccu.getCommCode("072")};
    <%--수신, 미수신--%>
    var recvDataMapEx = ${ccu.getCommCodeExcpAll("072")};
    <%--수신, 미수신--%>
    var genderDataMap = ${ccu.getCommCode("055")};
    <%--여자, 남자, 사용안함--%>
    var genderDataMapEx = ${ccu.getCommCodeExcpAll("055")};
    <%--여자, 남자, 사용안함--%>
    var useDataMap = ${ccu.getCommCodeExcpAll("067")};
    <%--사용, 미사용--%>
    var periodDataMap = ${ccu.getCommCodeExcpAll("077")};
    <%--조회기간--%>
    var weddingDataMap = ${ccu.getCommCodeExcpAll("076")};
    <%--결혼유무--%>

    <%--var payCdDataMap = ${ccu.getCommCodeExcpAll("024")};--%>
    var payCd = ${ccu.getCommCodeExcpAll("024")};
    var result = ${result};
    var membrClassList = ${membrClassList};
</script>

<script type="text/javascript" src="/resource/solbipos/js/membr/info/view/memberClass.js?ver=20210225.02" charset="utf-8"></script>