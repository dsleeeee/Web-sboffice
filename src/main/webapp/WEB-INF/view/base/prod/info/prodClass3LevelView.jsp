<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}" />
<c:set var="storeCd" value="${sessionScope.sessionInfo.storeCd}" />

<div id="prodClass3LevelView" name="prodClass3LevelView" class="subCon">

    <div ng-controller="prodClassLevel1Ctrl">
    <%-- title --%>
    <div class="searchBar">
        <a href="#" class="open fl"><s:message code="info.prodClassView" /></a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" id="btnSearch" ng-click="btnSearch()">
                <s:message code="cmm.search" />
            </button>
        </div>
    </div>
    <%-- title --%>

    <%-- 선택한 분류 경로 안내 --%>
    <table class="searchTbl mt10">
        <colgroup>
            <col class="w100" />
        </colgroup>
        <tbody>
            <tr class="brt">
                <th class="oh gr">
                <p class="s13 bk pdt5 pdb5" style="height: 25px;">
                    <label id="lblLevel1"></label>
                    <label id="lblLevel2"></label>
                </p>
              </th>
            </tr>
        </tbody>
    </table>
    <%-- 선택한 분류 경로 안내 --%>

    <%-- left(대분류) --%>
    <div class="wj-TblWrap mt20 mb20 w33 fl">
        <div class="wj-TblWrapBr mr10 pd20" style="height:600px;">
            <span class="bk lh30" id="spLevel1"><s:message code='info.prodClassLevel1' /></span>
            <div class="updownSet oh mb10 pd5" id="divBtnLevel1">
                <span class="fl bk lh30"><s:message code='info.prodClassLevel1' /></span>
                <button class="btn_skyblue" id="btnAddLevel1" ng-click="addLevel1()">
                    <s:message code="cmm.add" />
                </button>
                <button class="btn_skyblue" id="btnDelLevel1" ng-click="delLevel1()">
                    <s:message code="cmm.delete" />
                </button>
                <button class="btn_skyblue" id="btnSaveLevel1" ng-click="saveLevel1()">
                    <s:message code="cmm.save" />
                </button>
            </div>
            <div class="w100 mt10 mb20">
                <div class="wj-gridWrap" style="height:422px; overflow-x: hidden; overflow-y: hidden;">
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            control="flex"
                            initialized="initGrid(s,e)"
                            sticky-headers="true"
                            selection-mode="Row"
                            items-source="data"
                            item-formatter="_itemFormatter">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="35"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="info.prodClassCd"/>" binding="prodClassCd" width="65" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="info.prodClassName"/>" binding="prodClassNm" width="140"></wj-flex-grid-column>
                        <wj-flex-grid-column header="" binding="hqChildClassCnt" width="70" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="" binding="hqProdCnt" width="70" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="" binding="msChildClassCnt" width="70" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="" binding="msProdCnt" width="70" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="" binding="newRowYn" width="70" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>

                    </wj-flex-grid>
                </div>
            </div>
            <input type="hidden" id="hdLevel1" />
        </div>
    </div>
    <%-- left(대분류) --%>
    </div>

    <%-- mid(중분류) --%>
    <div class="wj-TblWrap mt20 mb20 w33 fl" ng-controller="prodClassLevel2Ctrl">
        <div class="wj-TblWrapBr mr10 pd20" style="height:600px;">
            <span class="bk lh30" id="spLevel2"><s:message code='info.prodClassLevel2' /></span>
            <div class="updownSet oh mb10 pd5" id="divBtnLevel2">
                <span class="fl bk lh30"><s:message code='info.prodClassLevel2' /></span>
                <button class="btn_skyblue" id="btnAddLevel2" ng-click="addLevel2()">
                    <s:message code="cmm.add" />
                </button>
                <button class="btn_skyblue" id="btnDelLevel2" ng-click="delLevel2()">
                    <s:message code="cmm.delete" />
                </button>
                <button class="btn_skyblue" id="btnSaveLevel2" ng-click="saveLevel2()">
                    <s:message code="cmm.save" />
                </button>
            </div>
            <div class="w100 mt10 mb20">
                <div class="wj-gridWrap" style="height:422px; overflow-x: hidden; overflow-y: hidden;">
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            control="flex"
                            initialized="initGrid(s,e)"
                            sticky-headers="true"
                            selection-mode="Row"
                            items-source="data"
                            item-formatter="_itemFormatter">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="35"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="info.prodClassCd"/>" binding="prodClassCd" width="65" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="info.prodClassName"/>" binding="prodClassNm" width="140"></wj-flex-grid-column>
                        <wj-flex-grid-column header="" binding="hqChildClassCnt" width="70" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="" binding="hqProdCnt" width="70" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="" binding="msChildClassCnt" width="70" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="" binding="msProdCnt" width="70" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="" binding="newRowYn" width="70" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>

                    </wj-flex-grid>
                </div>
            </div>
            <input type="hidden" id="hdLevel2" />
        </div>
    </div>
    <%-- mid(중분류) --%>

    <%-- right(소분류) --%>
    <div class="wj-TblWrap mt20 mb20 w33 fl" ng-controller="prodClassLevel3Ctrl">
        <div class="wj-TblWrapBr mr10 pd20" style="height:600px;">
            <span class="bk lh30" id="spLevel3"><s:message code='info.prodClassLevel3' /></span>
            <div class="updownSet oh mb10 pd5" id="divBtnLevel3">
                <span class="fl bk lh30"><s:message code='info.prodClassLevel3' /></span>
                <button class="btn_skyblue" id="btnAddLevel3" ng-click="addLevel3()">
                    <s:message code="cmm.add" />
                </button>
                <button class="btn_skyblue" id="btnDelLevel3" ng-click="delLevel3()">
                    <s:message code="cmm.delete" />
                </button>
                <button class="btn_skyblue" id="btnSaveLevel3" ng-click="saveLevel3()">
                    <s:message code="cmm.save" />
                </button>
            </div>
            <div class="w100 mt10 mb20">
                <div class="wj-gridWrap" style="height:422px; overflow-x: hidden; overflow-y: hidden;">
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            control="flex"
                            initialized="initGrid(s,e)"
                            sticky-headers="true"
                            selection-mode="Row"
                            items-source="data"
                            item-formatter="_itemFormatter">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="35"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="info.prodClassCd"/>" binding="prodClassCd" width="65" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="info.prodClassName"/>" binding="prodClassNm" width="140"></wj-flex-grid-column>
                        <wj-flex-grid-column header="" binding="hqChildClassCnt" width="70" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="" binding="hqProdCnt" width="70" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="" binding="msChildClassCnt" width="70" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="" binding="msProdCnt" width="70" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="" binding="newRowYn" width="70" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>

                    </wj-flex-grid>
                </div>
            </div>
        </div>
    </div>
    <%-- right(소분류) --%>

</div>

<script type="text/javascript">
    var prodAuthEnvstVal = "${prodAuthEnvstVal}";
    var prodClassCdInputType = "${prodClassCdInputType}";
    var orgnFg = "${orgnFg}";
    var hqOfficeCd = "${hqOfficeCd}";
    var storeCd = "${storeCd}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/info/prodClass3LevelView.js?ver=20211101.01" charset="utf-8"></script>