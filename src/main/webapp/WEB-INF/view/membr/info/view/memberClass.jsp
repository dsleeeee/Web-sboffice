<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd">${sessionScope.sessionInfo.currentMenu.resrceCd}</c:set>
<c:set var="menuNm">${sessionScope.sessionInfo.currentMenu.resrceNm}</c:set>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}"/>


<div class="subCon">
    <div ng-controller="memberClassCtrl">
        <div class="searchBar flddUnfld">
            <a href="#" class="open fl">${menuNm}</a>
            <%-- 조회 --%>
            <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                <button class="btn_blue fr" id="btnDel" ng-click="classDel()">
                    <s:message code="cmm.del"/>
                </button>
            </div>
            <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                <button class="btn_blue fr" id="btnSearch" ng-click="classSave()">
                    <s:message code="cmm.save"/>
                </button>
            </div>
            <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                <button class="btn_blue fr" id="btnSave" ng-click="newAdd()">
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
                <%-- 회원번호 --%>
                <th><s:message code="grade.membr.class.cd.nm"/></th>
                <td colspan="3">
                    <input type="text" id="membrCd" class="sb-input w10 fl mr10" ng-model="detailData.membrClassCd"
                           maxlength="10"/>
                    <input type="text" id="membrCdNm" class="sb-input w30 fl mr10" ng-model="detailData.membrOrgnCd"
                           maxlength="15"/>
                    <div class="sb-select fl w20 mr10">
                        <wj-combo-box
                                id="defaultYn"
                                ng-model="detailData.defltYn"
                                control="memberClassCombo"
                                items-source="_getComboData('defaultYn')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)">
                        </wj-combo-box>
                    </div>
                    <%-- 회원명 --%>
                    <%--            <th><s:message code="grade.membr.class.nm"/></th>--%>
                    <%--            <td>--%>
                    <%--                <input type="text" id="" class="sb-input w100" ng-model="membrNm" maxlength="15"/>--%>
                    <%--            </td>--%>
                </td>
            </tr>
            <tr>
                <%-- 할인율 --%>
                <th><s:message code="grade.membr.dc.rate"/></th>
                <td>
                    <input type="text" id="membrDc" class="sb-input w80" ng-model="detailData.dcRate" maxlength="15"/>% 할인
                </td>
                <%-- 신규가입시 부여 Point --%>
                <th><s:message code="grade.membr.new.join.save.point"/></th>
                <td>
                    <input type="text" id="membrNewPoint" class="sb-input w100" ng-model="detailData.newJoinSavePoint"
                           maxlength="15"/>
                </td>
            </tr>
            <tr>
                <%-- 최소 사용 Point --%>
                <th><s:message code="grade.membr.min.use.point"/></th>
                <td>
                    <input type="text" id="membrMinPoint" class="sb-input w100" ng-model="detailData.minUsePoint"
                           maxlength="15"/>
                </td>
                <%-- 첫거래 적립 Point --%>
                <th><s:message code="grade.membr.first.sale.save.point"/></th>
                <td>
                    <input type="text" id="membrFirstPoint" class="sb-input w100"
                           ng-model="detailData.firstSaleSavePoint"
                           maxlength="15"/>
                </td>
            </tr>
            <tr>
                <%-- 최대 사용 포인트 --%>
                <th><s:message code="grade.membr.max.use.point"/></th>
                <td>
                    <input type="text" id="membrMaxPoint" class="sb-input w100" ng-model="detailData.membrMaxPoint"
                           maxlength="15"/>
                </td>
                <%-- 포인트 사용시 적립여부 --%>
                <th><s:message code="grade.membr.point.yn"/></th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                                id="membrPointYn"
                                ng-model="detailData.pointSaveFg"
                                control="membrPointYnCombo"
                                items-source="_getComboData('membrPointYn')"
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
                                id="membrDcYn"
                                ng-model="membrDcYn"
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
                <th><s:message code="grade.membr.dc.max"/></th>
                <td>
                    <input type="text" id="membrDcMax" class="sb-input w100" ng-model="membrDcMax" maxlength="15"/>
                </td>
            </tr>
            <tr>
                <%-- 기념일 적립 여부 --%>
                <th><s:message code="grade.membr.anvsr.point.save.fg"/></th>
                <td colspan="3">
                    <div class="sb-select w20 fl mr10">
                        <wj-combo-box
                                id="membrAnvsrYn"
                                ng-model="membrAnvsrYn"
                                control="membrAnvsrYnCombo"
                                items-source="_getComboData('membrAnvsrYn')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)">
                        </wj-combo-box>
                    </div>
                    <input type="text" id="membrAnvsr" class="sb-input w10 fl " ng-model="membrAnvsr"
                           maxlength="15"/><span class="txtIn mt10">% 적립</span>
                </td>
            </tr>
            </tbody>
        </table>

        <div class="w50 fl mt40" style="width: 70%">
            <%--위즈모 테이블--%>
            <div class="wj-TblWrapBr mr10 pd20" style="height: 480px;">
                <div class="updownSet oh mb10">
                    <span class="fl bk lh30 mr10"><s:message code='grade.membr.orgn.list'/></span>
                    <%--                    <div class="sb-select dkbr">--%>
                    <%--                        &lt;%&ndash; 페이지 스케일  &ndash;%&gt;--%>
                    <%--                        <wj-combo-box--%>
                    <%--                                class="w100px fl"--%>
                    <%--                                id="listScaleBox"--%>
                    <%--                                ng-model="listScale"--%>
                    <%--                                items-source="_getComboData('listScaleBox')"--%>
                    <%--                                display-member-path="name"--%>
                    <%--                                selected-value-path="value"--%>
                    <%--                                is-editable="false"--%>
                    <%--                                initialized="initComboBox(s)">--%>
                    <%--                        </wj-combo-box>--%>
                    <%--                    </div>--%>
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
                            <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk"
                                                 width="40"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="grade.membr.class.cd"/>"
                                                 binding="membrClassType"
                                                 align="center"
                                                 is-read-only="true"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="grade.membr.dc.rate"/>" binding="dcRate"
                                                 align="center"
                                                 is-read-only="true" visible="false" width="100"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="grade.membr.new.join.save.point"/>"
                                                 binding="newJoinSavePoint"
                                                 align="center"
                                                 width="100" is-read-only="true"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="grade.membr.first.sale.save.point"/>"
                                                 binding="firstSaleSavePoint"
                                                 width="100" align="center"
                                                 is-read-only="true"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="grade.membr.min.use.point"/>"
                                                 binding="minUsePoint"
                                                 width="100"
                                                 align="center" is-read-only="true"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="grade.membr.max.use.point"/>"
                                                 binding="maxUsePoint" is-read-only="true"
                                                 width="100"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="grade.membr.point.save.fg"/>"
                                                 binding="pointSaveFgNm"
                                                 width="100"
                                                 align="center" is-read-only="true"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="grade.membr.dc.yn"/>" binding="dcAccPointYn"
                                                 data-map="useYn" width="100" align="center"
                                                 is-read-only="true"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="grade.membr.point.yn"/>"
                                                 binding="useAccPointYn"
                                                 data-map="useAccPointYn" width="100" align="center"
                                                 is-read-only="true"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="grade.membr.dc.max"/>" binding="dcLimitAmt"
                                                 data-map="dcLimitAmt" width="100" align="center"
                                                 is-read-only="true"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="grade.membr.anvsr.point.save.fg"/>"
                                                 binding="anvsrPointSaveFgNm"
                                                 data-map="anvsrPointSaveFg" width="100" align="center"
                                                 is-read-only="true"></wj-flex-grid-column>
                        </wj-flex-grid>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="w50 fr mt40 mb20" style="width: 30%" ng-controller="memberClassDetailCtrl">
        <%--위즈모 테이블--%>
        <div class="wj-TblWrapBr ml10 pd20" style="height: 480px;">
            <div class="updownSet oh mb10">
                <span class="fl bk lh30 mr10"><s:message code='grade.membr.point.list'/></span>
                <button class="btn_skyblue fr ml10" id="membrTotalbtn" ng-click="_pageView('memberClassCtrl', 1)">
                    <s:message code="grade.membr.total.button"/>
                </button>
                <input type="text" id="membrTotal" class="sb-input fr w10" ng-model="membrTotal" maxlength="15"/>
            </div>
            <%-- 개발시 높이 조절해서 사용--%>
            <%-- tbody영역의 셀 배경이 들어가는 부분은 .bdBg를 넣어주세요. --%>
            <div class="wj-gridWrap" style="height:370px; overflow-y: hidden;">
                <div class="row">
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            control="flex"
                            initialized="initGrid(s,e)"
                            sticky-headers="true"
                            selection-mode="Row"
                            items-source="data"
                            item-formatter="_itemFormatter"
                            frozen-columns="2"
                            sorted-column="toggleFreeze(false)">
                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="systemCd.chk"/>" binding="gChk"
                                             width="40"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="grade.membr.class.cd"/>"
                                             binding="membrClassCd" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="grade.membr.pay.code"/>"
                                             binding="payCd" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="grade.membr.acc_rate"/>"
                                             binding="accRate"></wj-flex-grid-column>
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
    var result = ${result};
    var memberClassList = ${memberClassList};

</script>
<script type="text/javascript" src="/resource/solbipos/js/membr/info/view/memberClass.js?ver=20200609.01"
        charset="utf-8"></script>
