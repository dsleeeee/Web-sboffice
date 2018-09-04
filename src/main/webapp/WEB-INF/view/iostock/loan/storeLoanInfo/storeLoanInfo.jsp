<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/loan/storeLoanInfo/storeLoanInfo/"/>

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
            <%-- 조회일자 --%>
            <th><s:message code="loanDtl.srearchDate"/></th>
            <td colspan="3">
                <div class="sb-select">
                    <span class="txtIn"><input id="srchStartDate" class="w200"></span>
                    <span class="rg">~</span>
                    <span class="txtIn"><input id="srchEndDate" class="w200"></span>
                </div>
            </td>
        </tr>
        <tr>
            <%-- 매장코드 --%>
            <th><s:message code="loanDtl.storeCd"/></th>
            <td>
                <div class="sb-select">
                    <div id="srchStoreCd"></div>
                </div>
            </td>
            <%-- 매장명 --%>
            <th><s:message code="loanDtl.storeNm"/></th>
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
        <%-- 엑셀 다운로드 --%>
        <button id="btnExcel" class="btn_skyblue fr"><s:message code="cmm.excel.down"/></button>
    </div>

    <%--위즈모 테이블--%>
    <div class="wj-TblWrapBr mt10" style="height: 400px;">
        <%-- 개발시 높이 조절해서 사용--%>
        <%-- tbody영역의 셀 배경이 들어가는 부분은 .bdBg를 넣어주세요. --%>
        <div id="gridStoreLoanInfo" style="width:100%;height:393px;"></div>
    </div>
    <%--//위즈모 테이블--%>

</div>

<script type="text/javascript">
    //그리드 전역 변수 선언
    var gridStoreLoanInfo;
    // 조회 변수 선언
    var srchStartDate = wcombo.genDateVal("#srchStartDate", "${sessionScope.sessionInfo.startDt}");
    var srchEndDate   = wcombo.genDateVal("#srchEndDate", "${sessionScope.sessionInfo.endDt}");
    var srchStoreCd  = wcombo.genInputText("#srchStoreCd", 7, "");
    var srchStoreNm  = wcombo.genInputText("#srchStoreNm", 50, "");

    $(document).ready(function () {
        gridInit();

        // 조회버튼 클릭 --%>
        $("#btnSearch").click(function (e) {
            search(1);
        });

        <%-- 엑셀 다운로드 버튼 클릭 --%>
        $("#btnExcel").click(function(){
            var name = "${menuNm}";
            name = name+" 테스트";
            wexcel.down(gridStoreLoan, name, name + ".xlsx");
        });
    });

    function gridInit() {
        var gridDataStoreLoanInfo =
            [
                {binding: "storeCd", header: messages["loanDtl.storeCd"], width:  70, align: "center"},
                {binding: "storeNm", header: messages["loanDtl.storeNm"], width: 120, align: "left"},
                {binding: "loanDate", header: messages["loanDtl.loanDate"], width: 100, align: "center"},
                {binding: "outAmt", header: messages["loanDtl.outAmt"], width: 100, align: "right", dataType: "Number", format: "n0"},
                {binding: "inAmt", header: messages["loanDtl.inAmt"], width: 100, align: "right", dataType: "Number", format: "n0"},
                {binding: "currLoanAmt", header: messages["loanDtl.currLoanAmt"], width: 100, align: "right", dataType: "Number", format: "n0"},
                {binding: "remark", header: messages["loan.remark"], width: "*", align: "left"}
            ];
        gridStoreLoanInfo = wgrid.genGrid("#gridStoreLoanInfo", gridDataStoreLoanInfo);
    }

    // 목록 조회
    function search(index) {
        // validation 추가
        var param = {};
        //TODO wijmo calendar의 값을 원하는 포맷으로 변경하는 함수 필요
        param.startDate = wijmo.Globalize.format(srchStartDate.value, 'yyyyMMdd');
        param.endDate   = wijmo.Globalize.format(srchEndDate.value, 'yyyyMMdd');
        param.storeCd   = srchStoreCd.value;
        param.storeNm   = srchStoreNm.value;

        $.postJSON("/iostock/loan/storeLoanInfo/storeLoanInfo/list.sb", param,
            function (result) {
                var list = result.data.list;
                if (list.length === undefined || list.length === 0) {
                    gridStoreLoanInfo.itemsSource = new wijmo.collections.CollectionView([]);
                    s_alert.pop(result.message);
                    return;
                }

                gridStoreLoanInfo.itemsSource = new wijmo.collections.CollectionView(list, {trackChanges: true});
            },
            function (result) {
                s_alert.pop(result.message);
                return;
            }
        );
    }

</script>
<%--<script type="text/javascript" src="/resource/solbipos/js/iostock/loan/storeLoan.js?ver=2018082101" charset="utf-8"></script>--%>
