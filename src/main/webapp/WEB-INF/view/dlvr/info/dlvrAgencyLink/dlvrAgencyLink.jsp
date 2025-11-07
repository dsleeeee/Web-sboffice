<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="subCon" ng-controller="dlvrAgencyLinkCtrl">

    <%-- 조회조건 --%>
    <div class="searchBar">
        <a href="#" class="open fl"><s:message code="dlvrAgencyLink.dlvrAgencyLink"/></a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" id="nxBtnSearch" ng-click="_pageView('dlvrAgencyLinkCtrl',1)">
                <s:message code="cmm.search" />
            </button>
        </div>
    </div>

    <%-- 안내 문구 --%>
    <div class="mt10 oh">
        <p class="tl s14 mt5 lh15">▶ '부릉'은 고객센터를 통해 연동하실 수 있습니다.</p>
        <p class="tl s14 mt5 lh15">▶ 링크포스 고객센터 1644-5195 문의해 주세요.</p>
    </div>

    <%-- left --%>
    <div class="wj-TblWrap mt10 mb20 w50 fl">
        <div class="wj-TblWrapBr mr10 pd10" style="height:550px;">
            <span class="bk lh30"><s:message code='dlvrAgencyLink.dlvrAgencyLink.status'/></span>
            <div class="updownSet oh mb10 pd5">
                <button class="btn_skyblue" id="btnAdd" ng-click="btnAdd()">
                    <s:message code="dlvrAgencyLink.dlvrAgency.reg"/>
                </button>
                <button class="btn_skyblue" id="btnClear" ng-click="btnClear()">
                    <s:message code="dlvrAgencyLink.clear"/>
                </button>
            </div>
            <div class="w100 mt10 mb20">
                <div class="wj-gridWrap" style="height:450px; overflow-x: hidden; overflow-y: hidden;">
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            control="flex"
                            initialized="initGrid(s,e)"
                            sticky-headers="true"
                            selection-mode="Row"
                            items-source="data"
                            item-formatter="_itemFormatter"
                            ime-enabled="true">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="35"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dlvrAgencyLink.dlvrAgency"/>" binding="prodClassCd" width="200" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dlvrAgencyLink.linkDate"/>" binding="prodClassNm" width="200"></wj-flex-grid-column>

                    </wj-flex-grid>
                </div>
            </div>
        </div>
    </div>
    <%-- left --%>

    <%-- right --%>
    <div class="wj-TblWrap mt10 mb20 w50 fl">
        <div class="wj-TblWrapBr pd10" style="height:550px;">

        </div>
    </div>
    <%-- right --%>

</div>


<script type="text/javascript">
</script>

<script type="text/javascript" src="/resource/solbipos/js/dlvr/info/dlvrAgencyLink/dlvrAgencyLink.js?ver=20251107.01" charset="utf-8"></script>

<%-- 배달대행사 추가 팝업 --%>
<c:import url="/WEB-INF/view/dlvr/info/dlvrAgencyLink/dlvrAgencyReg.jsp">
</c:import>