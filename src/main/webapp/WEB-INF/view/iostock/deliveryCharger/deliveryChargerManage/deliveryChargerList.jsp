<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/deliveryCharger/deliveryChargerManage/deliveryChargerList/"/>

<script type="text/javascript">
    //그리드 생성
    var app = agrid.genGrid('mainApp', 'wjGridCtrl', true);
    var dlvrGrid;

    $(document).ready(function () {
        dlvrGrid = agrid.getGrid("dlvrGrid");

        dlvrGrid.useYnMap  = new wijmo.grid.DataMap([
            {id: "Y", name: "<s:message code='deliveryCharger.useYnY'/>"},
            {id: "N", name: "<s:message code='deliveryCharger.useYnN'/>"},
        ], 'id', 'name');

        // 조회 버튼 클릭 --%>
        $("#btnSearch").click(function (e) {
            search(1);
        });

        // 신규 버튼 클릭 --%>
        $("#btnNewRegist").click(function (e) {
            openRegist();
        });

        <%-- 엑셀 다운로드 버튼 클릭 --%>
        $("#btnExcel").click(function(){
            var name = "${menuNm}";
            name = name+" 테스트";
            // wexcel.down(gridStoreLoan, name, name + ".xlsx");
        });
    });

    // 목록 조회
    function search(index) {
        // validation 추가
        var param = {};
        param.dlvrCd = $("#searchDlvrCd").val();
        param.DlvrNm = $("#searchDlvrNm").val();
        param.listScale = 15;
        param.curr = index;

        dlvrGrid.search("/iostock/deliveryCharger/deliveryChargerManage/deliveryChargerList/list.sb", param, '');
    }
</script>

<div class="subCon" ng-app="mainApp">

    <div class="searchBar">
        <a href="javascript:;" class="open">${menuNm}</a>
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
            <%-- 기사코드 --%>
            <th><s:message code="deliveryCharger.dlvrCd"/></th>
            <td><input type="text" id="searchDlvrCd" name="searchDlvrCd" class="sb-input w100" maxlength="4" /></td>
            <%-- 기사명 --%>
            <th><s:message code="deliveryCharger.dlvrNm"/></th>
            <td><input type="text" id="searchDlvrNm" name="searchDlvrNm" class="sb-input w100" maxlength="12" /></td>
        </tr>
        <tr>
            <%-- 차량번호 --%>
            <th><s:message code="deliveryCharger.carNo"/></th>
            <td><input type="text" id="searchCarNo" name="searchCarNo" class="sb-input w100" maxlength="14" /></td>
            <th></th>
            <td></td>
        </tr>
        </tbody>
    </table>

    <div class="mt10 pdb20 oh bb">
        <%-- 조회 --%>
        <button class="btn_blue fr" id="btnSearch"><s:message code="cmm.search" /></button>
    </div>

    <div class="mt20 oh sb-select dkbr">
        <%--페이지 스케일 --%>
        <div id="listScaleBox" class="w130 fl"></div>
        <div class="tr">
            <%-- 신규등록 --%>
            <button class="btn_skyblue" id="btnNewRegist"><s:message code="deliveryCharger.new" /></button>
        </div>
    </div>

    <%--위즈모 테이블--%>
    <div id="dlvrGrid" class="wj-TblWrapBr mt10" style="height: 400px;" ng-controller="wjGridCtrl">
        <wj-flex-grid
            class="gridStyle"
            items-source="data"
            control="flex"
            initialized="initGrid(s,e)"
            is-read-only="true">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="deliveryCharger.dlvrCd"/>" binding="dlvrCd" width="70" ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="deliveryCharger.dlvrNm"/>" binding="dlvrNm" width="120"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="deliveryCharger.carNo"/>"  binding="carNo"  width="100"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="deliveryCharger.useYn"/>"  binding="useYn"  width="70" data-map="useYnMap"></wj-flex-grid-column>

            <!-- enable column filtering-->
            <wj-flex-grid-filter></wj-flex-grid-filter>
        </wj-flex-grid>
    </div>
    <%--//위즈모 테이블--%>

    <%-- 배송기사 등록 레이어 --%>
    <c:import url="/WEB-INF/view/iostock/deliveryCharger/deliveryChargerManage/deliveryChargerRegist.jsp">
        <c:param name="menuCd" value="${menuCd}"/>
        <c:param name="menuNm" value="${menuNm}"/>
    </c:import>
</div>


<%--<script type="text/javascript" src="/resource/solbipos/js/iostock/loan/storeLoan.js?ver=2018082101" charset="utf-8"></script>--%>
