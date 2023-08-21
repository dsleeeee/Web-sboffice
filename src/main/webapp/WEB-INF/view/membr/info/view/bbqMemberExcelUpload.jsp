<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="storeCd" value="${sessionScope.sessionInfo.storeCd}" />
<c:set var="storeNm" value="${sessionScope.sessionInfo.storeNm}" />

<div class="subCon" ng-controller="bbqMemberExcelUploadCtrl">

    <div class="searchBar">
        <a href="#" class="open fl"><s:message code="bbq.member.excel.bbqMemberExcelUpload"/></a>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <%-- 조회 --%>
            <button class="btn_blue fr" id="nxBtnSearch" ng-click="_pageView('bbqMemberExcelUploadCtrl',1)">
                <s:message code="cmm.search"/>
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
            <%-- 검색기간 --%>
            <th>
                <div class="sb-select">
                    <wj-combo-box
                            id="periodType"
                            ng-model="periodType"
                            control="periodTypeCombo"
                            items-source="_getComboData('periodType')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)">
                    </wj-combo-box>
                </div>
            </th>
            <td>
                <div class="sb-select">
                    <span class="txtIn">
                        <div class="sb-select w110px">
                            <wj-input-date
                                    value="periodStartDate"
                                    ng-model="periodStartDate"
                                    control="periodStartDateCombo"
                                    format="yyyy/MM/dd"
                                    min="2000-01-01"
                                    max="2099-12-31"
                                    initialized="_initDateBox(s)">
                            </wj-input-date>
                        </div>
                    </span>
                    <span class="rg">~</span>
                    <span class="txtIn">
                        <div class="sb-select w110px">
                            <wj-input-date
                                    value="periodEndDate"
                                    ng-model="periodEndDate"
                                    control="periodEndDateCombo"
                                    format="yyyy/MM/dd"
                                    min="2000-01-01"
                                    max="2099-12-31"
                                    initialized="_initDateBox(s)">
                            </wj-input-date>
                        </div>
                    </span>
                </div>
            </td>
            <%-- 회원등급 --%>
            <th><s:message code="bbq.member.excel.memberClass"/></th>
            <td>
                <div class="sb-select">
                    <wj-combo-box
                            id="membrClassCd"
                            ng-model="membrClassCd"
                            control="membrClassCdCombo"
                            items-source="_getComboData('membrClassCd')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)">
                    </wj-combo-box>
                </div>
            </td>
        </tr>
        <tr>
            <%-- 회원번호 --%>
            <th><s:message code="bbq.member.excel.memberNo"/></th>
            <td>
                <input type="text" id="memberNo" class="sb-input w100" ng-model="memberNo" maxlength="10"
                       ng-disabled="newMemberYn === true" onkeyup="fnNxBtnSearch();"/>
            </td>
            <%-- 회원명 --%>
            <th><s:message code="bbq.member.excel.memberNm"/></th>
            <td>
                <input type="text" id="memberNm" class="sb-input w100" ng-model="memberNm" maxlength="15"
                       ng-disabled="newMemberYn === true" onkeyup="fnNxBtnSearch();"/>
            </td>
        </tr>
        </tbody>
    </table>

    <ul class="txtSty2 mt10 pdb20">
        <li class="red">
            <s:message code="bbq.member.excel.bbqMemberExcelUpload"/>&nbsp;설명<br>
            <p>
                <span>1. 등록매장을 반드시 선택합니다.(매장은 본인매장으로 자동셋팅)</span><br>
                <span>2. '회원엑셀업로드' 버튼을 클릭하여 회원정보 엑셀 파일을 업로드 합니다.</span><br>
                <span>3. 등록된 내역중 '전화번호'만 저장됩니다.('휴대폰','기타전화1','기타전화2' 제외됨)</span><br>
            </p>
        </li>
    </ul>

    <table class="searchTbl" style="border-top:1px solid #ddd">
        <colgroup>
            <col class="w15"/>
            <col class="w85"/>
        </colgroup>
        <tbody>
        <tr>
            <th>
                <s:message code="bbq.member.excel.regStore"/>
            </th>
            <td colspan="2">
                <c:if test="${orgnFg == 'HQ'}">
                    <%-- 등록매장 선택 --%>
                    <jsp:include page="/WEB-INF/view/application/layer/searchStoreS.jsp" flush="true">
                        <jsp:param name="targetId" value="bbqMemberExcelUploadStore"/>
                    </jsp:include>
                </c:if>
                <c:if test="${orgnFg == 'STORE'}">
                    <label>[${storeCd}]&nbsp;${storeNm}</label>
                    <input type="hidden" id="bbqMemberExcelUploadStoreCd" value="${storeCd}" />
                </c:if>
                <%-- 회원업로드(excel) --%>
                <button class="btn_skyblue ml2 " ng-click="excelUpload()"><s:message code="cmm.excel.excelUpload" />(BBQ)</button>
                <%-- 회원업로드(txt) --%>
                <button class="btn_skyblue ml2 " ng-click="txtUpload()"><s:message code="cmm.txt.txtUpload" />(BBQ)</button>
            </td>
        </tr>
        </tbody>
    </table>

    <%--<div class="mt10 oh sb-select dkbr">
        &lt;%&ndash; 페이지 스케일  &ndash;%&gt;
        <wj-combo-box
                class="w100px fl"
                id="listScaleBox"
                ng-model="listScale"
                items-source="_getComboData('listScaleBox')"
                display-member-path="name"
                selected-value-path="value"
                is-editable="false"
                initialized="initComboBox(s)">
        </wj-combo-box>
    </div>--%>

    <%-- 회원목록 그리드 --%>
    <div class="w100 mt10 mb20">
        <div class="wj-gridWrap" style="height:370px; overflow-x: hidden; overflow-y: hidden;">
            <wj-flex-grid
                    control="flex"
                    autoGenerateColumns="false"
                    selection-mode="Row"
                    initialized="initGrid(s,e)"
                    items-source="data"
                    frozen-columns="4"
                    item-formatter="_itemFormatter">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="bbq.member.excel.memberNo"/>" binding="membrNo" align="center" width="100" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="bbq.member.excel.memberNm"/>" binding="membrNm" align="left" width="150" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="bbq.member.excel.regStore"/>" binding="regStoreNm" align="left" width="150" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="bbq.member.excel.memberClass"/>" binding="membrClassCd" align="center"  width="80" is-read-only="true" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="bbq.member.excel.memberClass"/>" binding="membrClassNm" align="center" width="100" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="bbq.member.excel.telNo"/>" binding="telNo" align="center" width="120" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="bbq.member.excel.addr"/>" binding="addrDtl" align="left" width="200" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="bbq.member.excel.useYn"/>" binding="useYn" data-map="useYnDataMap" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="bbq.member.excel.remark"/>" binding="remark" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
            </wj-flex-grid>
        </div>
    </div>

    <%-- 페이지 리스트 --%>
    <div class="pageNum mt20">
        <%-- id --%>
        <ul id="bbqMemberExcelUploadCtrlPager" data-size="10">
        </ul>
    </div>
    <%--//페이지 리스트--%>
</div>

<script type="text/javascript">
    var orgnFg = "${orgnFg}";
    var storeCd = "${storeCd}";

    // 회원등급
    var memberClassList = ${memberClassList};

    // 회원등급 관리구분[1237]
    var membrClassManageFg = "${membrClassManageFg}";

    // 사용여부
    var useYn = ${ccu.getCommCodeExcpAll("067")};
</script>

<script type="text/javascript" src="/resource/solbipos/js/membr/info/view/bbqMemberExcelUpload.js?ver=20230821.01" charset="utf-8"></script>


<%-- 회원엑셀업로드(BBQ) 엑셀파일업로드--%>
<c:import url="/WEB-INF/view/membr/info/view/bbqExcelFileUpload.jsp">
</c:import>