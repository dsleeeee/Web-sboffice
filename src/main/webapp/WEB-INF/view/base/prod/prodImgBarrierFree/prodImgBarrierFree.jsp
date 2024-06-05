<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd">${sessionScope.sessionInfo.currentMenu.resrceCd}</c:set>
<c:set var="menuNm">${sessionScope.sessionInfo.currentMenu.resrceNm}</c:set>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg.getCode()}" />
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}" />
<c:set var="storeCd" value="${sessionScope.sessionInfo.storeCd}" />
<c:set var="userId" value="${sessionScope.sessionInfo.userId}"/>

<div class="subCon" ng-controller="prodImgBarrierFreeCtrl">

    <%-- 조회조건 --%>
    <div class="searchBar flddUnfld">
        <a href="#" class="open fl">${menuNm}</a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" id="btnSearch" ng-click="_pageView('prodImgBarrierFreeCtrl',1)">
                <s:message code="cmm.search" />
            </button>
        </div>
    </div>
    <table class="searchTbl">
        <colgroup>
            <col class="w15" />
            <col class="w35" />
            <col class="w15" />
            <col class="w35" />
        </colgroup>
        <tbody>
        <tr>
            <%-- 등록일자 --%>
            <th><s:message code="prodImgBarrierFree.regDate" /></th>
            <td colspan="3" style="text-align: left;">
                <div class="sb-select">
                    <span class="txtIn"><input id="srchTimeStartDate" class="w120px"></span>
                    <span class="rg">~</span>
                    <span class="txtIn"><input id="srchTimeEndDate" class="w120px"></span>
                    <span class="chk ml10">
                       <input type="checkbox" id="chkDt" ng-model="isChecked" ng-change="isChkDt()" />
                        <label for="chkDt">
                            <s:message code="cmm.all.day" />
                        </label>
                        <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                            <%-- 이미지매장적용 --%>
                            <button class="btn_skyblue ml10" id="btnImgToStoreReg" ng-click="imgToStoreReg()"><s:message code="prodImgBarrierFree.storeReg"/></button>
                        </c:if>
		            </span>
                </div>
            </td>
        </tr>
        <tr>
            <%-- 상품코드 --%>
            <th><s:message code="prodImgBarrierFree.prodCd" /></th>
            <td>
                <input type="text" class="sb-input w100" id="srchProdCd" ng-model="prodCd" onkeyup="fnNxBtnSearch();"/>
            </td>
            <%-- 상품명 --%>
            <th><s:message code="prodImgBarrierFree.prodNm" /></th>
            <td>
                <input type="text" class="sb-input w100" id="srchProdNm" ng-model="prodNm" onkeyup="fnNxBtnSearch();"/>
            </td>
        </tr>
        <tr>
            <%-- 분류조회 --%>
            <th><s:message code="cmm.prodClass" /></th>
            <td>
                <input type="text" class="sb-input w80" id="srchProdClassCd" ng-model="prodClassCdNm" ng-click="popUpProdClass()" style="float: left;"
                       placeholder="<s:message code="cmm.prodClass" /> 선택" readonly/>
                <input type="hidden" id="_prodClassCd" name="prodClassCd" ng-model="prodClassCd" disabled />
                <button type="button" class="btn_skyblue fl mr5" id="btnCancelProdClassCd" style="margin-left: 5px;" ng-click="delProdClass()"><s:message code="cmm.selectCancel"/></button>
            </td>
            <%-- 바코드 --%>
            <th><s:message code="prodImgBarrierFree.barCd" /></th>
            <td>
                <input type="text" class="sb-input w100" id="srchBarCd" ng-model="barCd" onkeyup="fnNxBtnSearch();"/>
            </td>
        </tr>
        <tr>
            <%-- 사용여부 --%>
            <th><s:message code="prodImgBarrierFree.useYn" /></th>
            <td>
                <div class="sb-select">
                    <wj-combo-box
                            id="srchUseYn"
                            ng-model="useYn"
                            control="useYnAllCombo"
                            items-source="_getComboData('useYnAllComboData')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)"
                            selected-index="1">
                    </wj-combo-box>
                </div>
            </td>
            <c:if test="${brandUseFg != '1' or sessionInfo.orgnFg != 'HQ'}">
                <th></th>
                <td></td>
            </c:if>
            <c:if test="${brandUseFg == '1'}">
                <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                    <%-- 상품브랜드 --%>
                    <th><s:message code="prodImgBarrierFree.prodHqBrand" /></th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                    id="srchProdHqBrandCd"
                                    ng-model="prodHqBrandCd"
                                    items-source="_getComboData('srchProdHqBrandCd')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    control="srchProdHqBrandCdCombo">
                            </wj-combo-box>
                        </div>
                    </td>
                </c:if>
            </c:if>
        </tr>
        </tbody>
    </table>

    <div class="mt10 oh sb-select dkbr">
        <button class="btn_skyblue fr mr5" ng-click="deleteAll()" style="display: none;"><s:message code="prodImgBarrierFree.deleteAll"/></button>
        <button class="btn_skyblue fr mr5" ng-click="copy('A')" ><s:message code="prodImgBarrierFree.copyAll"/></button>
        <p class="s12 fr pd5">등록된 이미지만 복사됩니다.</p>
    </div>

    <div class="wj-TblWrap mt10 mb20 w40 fl">
        <div class="wj-TblWrapBr mr10 pd10" style="height:480px;">
            <div class="sb-select dkbr mb10 oh">
            </div>
            <div class="w100 mt10 mb20">
                <div class="wj-gridWrap" style="height:388px; overflow-x: hidden; overflow-y: hidden;">
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            control="flex"
                            initialized="initGrid(s,e)"
                            sticky-headers="true"
                            selection-mode="Row"
                            items-source="data"
                            item-formatter="_itemFormatter">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="prodImgBarrierFree.prodClassCd"/>" binding="prodClassCd" width="85" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="prodImgBarrierFree.prodClassNm"/>" binding="prodClassNm" width="100" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="prodImgBarrierFree.prodCd"/>" binding="prodCd" width="100" is-read-only="true" ></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="prodImgBarrierFree.prodNm"/>" binding="prodNm" width="150" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="prodImgBarrierFree.saleUprc"/>" binding="saleUprc" width="70" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="prodImgBarrierFree.useYn"/>" binding="useYn" width="60" data-map="useYnComboDataMap" is-read-only="true" align="center"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
            </div>
            <%-- 페이지 리스트 --%>
            <div class="pageNum2 mt10">
                <%-- id --%>
                <ul id="prodImgBarrierFreeCtrlPager" data-size="10">
                </ul>
            </div>
            <%--//페이지 리스트--%>
        </div>
    </div>

    <div class="wj-TblWrap mt10 mb20 w60 fr">
        <div class="wj-TblWrapBr ml10 pd10" style="height:480px; overflow-y: hidden;">

            <label id="prodInfo"></label>
            <input type="hidden" id="hdProdCd"/>
            <input type="hidden" id="hdProdNm"/>

            <button id="btnCopy" style="display: none;" class="btn_skyblue ml5 fr" ng-click="copy('I')" ><s:message code="prodImgBarrierFree.copy"/></button>

            <table class='tblType01 mt20' id="imgTbl" style="display: none;">
                <colgroup>
                    <col class="w33" />
                    <col class="w33" />
                    <col class="w33" />
                </colgroup>
                <tbody id="imgBorder">
                <tr>
                    <th><s:message code="prodImgBarrierFree.kioskGreenImg" /></th>
                    <th><s:message code="prodImgBarrierFree.kioskYellowImg" /></th>
                    <th><s:message code="prodImgBarrierFree.kioskWhiteImg" /></th>
                </tr>
                <tr>
                    <td><div class="imgCell" id="imgKioskGreen"></div></td>
                    <td><div class="imgCell" id="imgKioskYellow"></div></td>
                    <td><div class="imgCell" id="imgKioskWhite"></div></td>
                </tr>
                <f:form id="regForm" name="regForm" method="post" enctype="multipart/form-data">
                    <tr>
                        <td>
                            <input type="file" id="fileKioskGreen" name="fileKioskGreen" class="form-control" accept="image/x-png, .jpg" onchange="imagePreview(this, '006')"/>
                            <input type="hidden" id="hdKioskGreenFileNm" />
                        </td>
                        <td>
                            <input type="file" id="fileKioskYellow" name="fileKioskYellow" class="form-control" accept="image/x-png, .jpg" onchange="imagePreview(this, '007')"/>
                            <input type="hidden" id="hdKioskYellowFileNm" />
                        </td>
                        <td>
                            <input type="file" id="fileKioskWhite" name="fileKioskWhite" class="form-control" accept="image/x-png, .jpg" onchange="imagePreview(this, '008')"/>
                            <input type="hidden" id="hdKioskWhiteFileNm" />
                        </td>
                    </tr>
                </f:form>
                <tr>
                    <td>
                        <c:if test="${momsEnvstVal == '0'}">
                            <p class="s12 red mb10"><s:message code="prodImgBarrierFree.fileSize.max" /></p>
                        </c:if>
                        <c:if test="${momsEnvstVal == '1'}">
                            <p class="s12 red mb10"><s:message code="prodImgBarrierFree.fileSize.max2" /></p>
                        </c:if>
                        <button type="button" class="btn_skyblue" ng-click="imgCancel('006', 'F')"><s:message code="cmm.selectCancel" /></button>
                        <button type="button" class="btn_skyblue" ng-click="regImg('006')"><s:message code="prodImgBarrierFree.regImg" /></button>
                        <button type="button" class="btn_skyblue" ng-click="delImg('006')"><s:message code="cmm.del" /></button>
                    </td>
                    <td>
                        <c:if test="${momsEnvstVal == '0'}">
                            <p class="s12 red mb10"><s:message code="prodImgBarrierFree.fileSize.max" /></p>
                        </c:if>
                        <c:if test="${momsEnvstVal == '1'}">
                            <p class="s12 red mb10"><s:message code="prodImgBarrierFree.fileSize.max2" /></p>
                        </c:if>
                        <button type="button" class="btn_skyblue" ng-click="imgCancel('007', 'F')"><s:message code="cmm.selectCancel" /></button>
                        <button type="button" class="btn_skyblue" ng-click="regImg('007')"><s:message code="prodImgBarrierFree.regImg" /></button>
                        <button type="button" class="btn_skyblue" ng-click="delImg('007')"><s:message code="cmm.del" /></button>
                    </td>
                    <td>
                        <c:if test="${momsEnvstVal == '0'}">
                            <p class="s12 red mb10"><s:message code="prodImgBarrierFree.fileSize.max" /></p>
                        </c:if>
                        <c:if test="${momsEnvstVal == '1'}">
                            <p class="s12 red mb10"><s:message code="prodImgBarrierFree.fileSize.max2" /></p>
                        </c:if>
                        <button type="button" class="btn_skyblue" ng-click="imgCancel('008', 'F')"><s:message code="cmm.selectCancel" /></button>
                        <button type="button" class="btn_skyblue" ng-click="regImg('008')"><s:message code="prodImgBarrierFree.regImg" /></button>
                        <button type="button" class="btn_skyblue" ng-click="delImg('008')"><s:message code="cmm.del" /></button>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>

</div>

<style>
    #imgBorder tr th,td{
        border:1px solid #e8e8e8;
        text-align: center;
        vertical-align: middle;
    }
    .imgCell{
        height:200px;
        border:1px solid #e8e8e8;
        vertical-align: middle;
        line-height: 200px;
        width:200px;
    }
    .imgPic{
        max-width:100%;
        max-height:100%;
        width:auto;
        height:auto;
        vertical-align: middle;
    }
    .btnSet{
        margin-top:7px;
    }
</style>

<script>
    var orgnFg = "${orgnFg}";
    var hqOfficeCd = "${hqOfficeCd}";
    var storeCd = "${storeCd}";
    var userId = "${userId}";
    // 브랜드 사용여부
    var brandUseFg = "${brandUseFg}";
    // 사용자 브랜드
    var userHqBrandCdComboList = ${userHqBrandCdComboList};

    // [1250 맘스터치] 환경설정값
    var momsEnvstVal = "${momsEnvstVal}";

    // POS에서 해당 WEB 화면 최초 접속한 경우(접속하면서 session 생성), 왼쪽 메뉴영역은 접어두기.
    // 최초 접속시에는 이전 URL 인자값으로 판별가능
    var referrer = document.referrer;
    if(referrer.indexOf("userId") > 0 && referrer.indexOf("resrceCd") > 0 && referrer.indexOf("accessCd") > 30 ){
        $(".menuControl").trigger("click");
    }

    // POS에서 해당 WEB 화면 재접속한 경우(이전 접속 session 그대로 존재), 'posLoginReconnect'값 판단하여 왼쪽 메뉴영역은 접어두기.
    // 재접속시에는 이전 URL 인자값이 없어, 로그인 여부 판별시에 특정 parameter 값을 보내 처리.
    if("${posLoginReconnect}" === "Y"){ // 직접입력한경우
        $(".menuControl").trigger("click");
    }
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/prodImgBarrierFree/prodImgBarrierFree.js?ver=20240517.01" charset="utf-8"></script>

<%-- 상품분류 팝업 --%>
<c:import url="/WEB-INF/view/application/layer/searchProdClassCd.jsp">
</c:import>

<%-- 베리어프리-이미지관리 매장적용 팝업 --%>
<c:import url="/WEB-INF/view/base/prod/prodImgBarrierFree/prodImgBarrierFreeStoreRegist.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 베리어프리-이미지관리 이미지복사 팝업 --%>
<c:import url="/WEB-INF/view/base/prod/prodImgBarrierFree/prodImgBarrierFreeCopy.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 베리어프리-이미지관리 이미지전체삭제 팝업 --%>
<c:import url="/WEB-INF/view/base/prod/prodImgBarrierFree/prodImgBarrierFreeDelete.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>