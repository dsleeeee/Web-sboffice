<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/loan/storeLoanManage/storeLoanManage/"/>

<div class="subCon">

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
            <%-- 매장코드 --%>
            <th><s:message code="loan.storeCd"/></th>
            <td>
                <div class="sb-select">
                    <div id="srchStoreCd"></div>
                </div>
            </td>
            <%-- 매장명 --%>
            <th><s:message code="loan.storeNm"/></th>
            <td>
                <div class="sb-select">
                    <div id="srchStoreNm"></div>
                </div>
            </td>
        </tr>
        </tbody>
    </table>

    <%-- 조회 --%>
    <div class="mt10 pdb20 oh bb">
        <button class="btn_blue fr" id="btnSearch"><s:message code="cmm.search"/></button>
    </div>

    <div class="mt20 oh sb-select dkbr">
        <%-- 페이지 스케일  --%>
        <div id="listScaleBox" class="w150 fl"></div>
        <%-- 엑셀 다운로드 --%>
        <button id="btnExcel" class="btn_skyblue fr"><s:message code="cmm.excel.down"/></button>
        <%-- 저장 --%>
        <button id="btnSave" class="btn_skyblue fr"><s:message code="cmm.save"/></button>
    </div>

    <%--위즈모 테이블--%>
    <div class="wj-TblWrapBr mt10" style="height: 400px;">
        <%-- 개발시 높이 조절해서 사용--%>
        <%-- tbody영역의 셀 배경이 들어가는 부분은 .bdBg를 넣어주세요. --%>
        <div id="gridStoreLoan" style="width:100%;height:393px;"></div>
    </div>
    <%--//위즈모 테이블--%>

    <%-- 페이지 리스트 --%>
    <div class="pageNum mt20">
        <%-- id --%>
        <ul id="page" data-size="10">
        </ul>
    </div>
    <%--//페이지 리스트--%>

</div>

<script type="text/javascript">
    //그리드 전역 변수 선언
    var gridStoreLoan;
    // 조회 변수 선언
    var srchStoreCd  = wcombo.genInputText("#srchStoreCd", 7, "");
    var srchStoreNm  = wcombo.genInputText("#srchStoreNm", 50, "");
    var listScaleBox = wcombo.genCommonBox("#listScaleBox", listScaleBoxData); //listScaleBoxData 는 공통으로 빼둠. (commonVariables.jsp)

    $(document).ready(function () {
        gridInit();

        // 조회버튼 클릭 --%>
        $("#btnSearch").click(function (e) {
            search(1);
        });

        // 저장버튼 클릭 --%>
        $("#btnSave").click(function (e) {
            save();
        });

        <%-- 엑셀 다운로드 버튼 클릭 --%>
        $("#btnExcel").click(function(){
            var name = "${menuNm}";
            name = name+" 테스트";
            wexcel.down(gridStoreLoan, name, name + ".xlsx");
        });

        // 페이징
        $(document).on("click", ".page", function () {
            search($(this).data("value"));
        });
    });

    function gridInit() {
        var orderFg  = new wijmo.grid.DataMap([
            {id: "1", name: "<s:message code='loan.orderFg1'/>"},
            {id: "2", name: "<s:message code='loan.orderFg2'/>"},
            {id: "3", name: "<s:message code='loan.orderFg3'/>"}
        ], 'id', 'name');
        var noOutstockAmtFg  = new wijmo.grid.DataMap([
            {id: "N", name: "<s:message code='loan.noOutstockAmtFgN'/>"},
            {id: "Y", name: "<s:message code='loan.noOutstockAmtFgY'/>"},
        ], 'id', 'name');
        var gridDataStoreLoan =
            [
                {binding: "storeCd", header: messages["loan.storeCd"], width:  70, align: "center", isReadOnly: true},
                {binding: "storeNm", header: messages["loan.storeNm"], width: 120, align: "left", isReadOnly: true},
                {binding: "limitLoanAmt", header: messages["loan.limitLoanAmt"], width: 100, align: "right", dataType: "Number", format: "n0", maxLength: 10, aggregate: 'Sum'},
                {binding: "useLoanAmt", header: messages["loan.useLoanAmt"], width: 100, align: "right", dataType: "Number", format: "n0", isReadOnly: true, aggregate: 'Sum'},
                {binding: "currLoanAmt", header: messages["loan.currLoanAmt"], width: 100, align: "right", dataType: "Number", format: "n0", isReadOnly: true, aggregate: 'Sum'},
                {binding: "maxOrderAmt", header: messages["loan.maxOrderAmt"], width: 100, align: "right", dataType: "Number", format: "n0", aggregate: 'Sum'},
                {binding: "orderFg", header: messages["loan.orderFg"], width: 100, align: "center", dataMap:orderFg},
                {binding: "availableOrderAmt", header: messages["loan.availableOrderAmt"], width: 100, align: "right", dataType: "Number", format: "n0", isReadOnly: true, aggregate: 'Sum'},
                {binding: "noOutstockAmtFg", header: messages["loan.noOutstockAmtFg"], width: 120, align: "center", dataMap:noOutstockAmtFg},
                {binding: "remark", header: messages["loan.remark"], width: "*", align: "left"}
            ];
        gridStoreLoan = wgrid.genGrid("#gridStoreLoan", gridDataStoreLoan);
        gridStoreLoan.isReadOnly = false;

        // footer에 합계 생성
        gridStoreLoan.columnFooters.rows.push(new wijmo.grid.GroupRow());
        gridStoreLoan.bottomLeftCells.setCellData(0, 0, '합계');

    }

    // 매장여신 목록 조회
    function search(index) {
        // validation 추가
        var param = {};
        param.storeCd = srchStoreCd.value;
        param.storeNm = srchStoreNm.value;
        param.listScale = listScaleBox.selectedValue;
        param.curr = index;

        $.postJSON("/iostock/loan/storeLoanManage/storeLoanManage/list.sb", param,
            function (result) {
                var list = result.data.list;
                if (list.length === undefined || list.length === 0) {
                    gridStoreLoan.itemsSource = new wijmo.collections.CollectionView([]);
                    s_alert.pop(result.message);
                    return;
                }

                gridStoreLoan.itemsSource = new wijmo.collections.CollectionView(list, {trackChanges: true});

                page.make("#page", result.data.page.curr, result.data.page.totalPage);
            },
            function (result) {
                s_alert.pop(result.message);
                return;
            }
        );
    }

    function save() {
        var paramArr = new Array();

        for (var i = 0; i < gridStoreLoan.collectionView.itemsEdited.length; i++) {
            gridStoreLoan.collectionView.itemsEdited[i].status = "U";
            paramArr.push(gridStoreLoan.collectionView.itemsEdited[i]);
        }
        for (var i = 0; i < gridStoreLoan.collectionView.itemsAdded.length; i++) {
            gridStoreLoan.collectionView.itemsAdded[i].status = "I";
            paramArr.push(gridStoreLoan.collectionView.itemsAdded[i]);
        }

        if (paramArr.length <= 0) {
            s_alert.pop(messages["cmm.not.modify"]);
            return;
        }

        $.postJSONArray("/iostock/loan/storeLoanManage/storeLoanManage/save.sb", paramArr, function (result) {
                s_alert.pop(messages["cmm.saveSucc"]);
                gridStoreLoan.collectionView.clearChanges();
            },
            function (result) {
                s_alert.pop(result.message);
            }
        );
    }
</script>
<%--<script type="text/javascript" src="/resource/solbipos/js/iostock/loan/storeLoan.js?ver=2018082101" charset="utf-8"></script>--%>
