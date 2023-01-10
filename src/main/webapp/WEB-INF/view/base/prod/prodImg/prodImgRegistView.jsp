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

<div class="subCon" ng-controller="prodImgCtrl">
    <%--searchTbl--%>
    <div class="searchBar">
        <a href="#" class="open fl">${menuNm}</a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" id="btnSearch" ng-click="_pageView('prodImgCtrl',1)">
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
        <%-- 등록 일자 --%>
        <tr>
            <th><s:message code="prodImg.regDate" /></th>
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
                            <button class="btn_skyblue ml10" id="btnImgToStoreReg" ng-click="imgToStoreReg()"><s:message code="prodImg.storeReg"/></button>
                        </c:if>
		            </span>
                </div>
            </td>
        </tr>
        <tr>
            <%-- 상품코드 --%>
            <th><s:message code="prodImg.prodCd" /></th>
            <td>
                <input type="text" class="sb-input w100" id="srchProdCd" ng-model="prodCd" onkeyup="fnNxBtnSearch();"/>
            </td>
            <%-- 상품명 --%>
            <th><s:message code="prodImg.prodNm" /></th>
            <td>
                <input type="text" class="sb-input w100" id="srchProdNm" ng-model="prodNm" onkeyup="fnNxBtnSearch();"/>
            </td>
        </tr>
        <tr>
            <%-- 분류조회 --%>
            <th><s:message code="prodImg.prodClass" /></th>
            <td>
                <input type="text" class="sb-input w80" id="srchProdClassCd" ng-model="prodClassCdNm" ng-click="popUpProdClass()" style="float: left;"
                       placeholder="<s:message code="prodImg.prodClass" /> 선택" readonly/>
                <input type="hidden" id="_prodClassCd" name="prodClassCd" ng-model="prodClassCd" disabled />
                <button type="button" class="btn_skyblue fl mr5" id="btnCancelProdClassCd" style="margin-left: 5px;" ng-click="delProdClass()"><s:message code="cmm.selectCancel"/></button>
            </td>
            <%-- 바코드 --%>
            <th><s:message code="prodImg.barCd" /></th>
            <td>
                <input type="text" class="sb-input w100" id="srchBarCd" ng-model="barCd" onkeyup="fnNxBtnSearch();"/>
            </td>
        </tr>
        <tr>
            <%-- 사용여부 --%>
            <th><s:message code="prodImg.useYn" /></th>
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
                            initialized="_initComboBox(s)">
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
                    <th><s:message code="prodImg.prodHqBrand" /></th>
                    <td>
                      <div class="sb-select">
                        <wj-combo-box
                          id="srchProdHqBrandCd"
                          ng-model="storeHqBrandCd"
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
    <%--//searchTbl--%>
    <div class="mt10 oh sb-select dkbr">
        <button class="btn_skyblue fr mr5" ng-click="deleteAll()" ><s:message code="prodImg.deleteAll"/></button>
        <button class="btn_skyblue fr mr5" ng-click="copy('A')" ><s:message code="prodImg.copyAll"/></button>
        <p class="s12 fr pd5">등록된 이미지만 복사됩니다.</p>
    </div>

    <div class="wj-TblWrap mt10 mb20 w40 fl">
        <div class="wj-TblWrapBr mr10 pd20" style="height:480px;">
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
                            <wj-flex-grid-column header="<s:message code="prodImg.prodClassCd"/>" binding="prodClassCd" width="85" align="center" is-read-only="true"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="prodImg.prodClassNm"/>" binding="prodClassNm" width="100" is-read-only="true"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="prodImg.prodCd"/>" binding="prodCd" width="100" is-read-only="true" ></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="prodImg.prodNm"/>" binding="prodNm" width="150" is-read-only="true"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="prodImg.saleUprc"/>" binding="saleUprc" width="70" is-read-only="true"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="prodImg.useYn"/>" binding="useYn" width="60" data-map="useYnComboDataMap" is-read-only="true" align="center"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
            </div>
            <%-- 페이지 리스트 --%>
            <div class="pageNum2 mt10">
                <%-- id --%>
                <ul id="prodImgCtrlPager" data-size="10">
                </ul>
            </div>
            <%--//페이지 리스트--%>
        </div>
    </div>

    <div class="wj-TblWrap mt10 mb20 w60 fr">
        <div class="wj-TblWrapBr ml10 pd20" style="height:480px; overflow-y: hidden;">

            <label id="prodInfo"></label>
            <input type="hidden" id="hdProdCd"/>
            <input type="hidden" id="hdProdNm"/>

            <button id="btnCopy" style="display: none;" class="btn_skyblue ml5 fr" ng-click="copy('I')" ><s:message code="prodImg.copy"/></button>

            <table class='tblType01 mt20' id="imgTbl" style="display: none;">
                <colgroup>
                    <col class="w33" />
                    <col class="w33" />
                    <col class="w33" />
                </colgroup>
                <tbody id="imgBorder">
                    <tr>
                        <th><s:message code="prodImg.prodImg" /></th>
                        <th><s:message code="prodImg.kioskImg" /></th>
                        <th><s:message code="prodImg.didImg" /></th>
                        <th><s:message code="prodImg.didHash" /></th>
                    </tr>
                    <tr>
                        <td><div class="imgCell" id="imgProd"></div></td>
                        <td><div class="imgCell" id="imgKiosk"></div></td>
                        <td><div class="imgCell" id="imgDid"></div></td>
                        <td><div class="imgCell" id="imgHash"></div></td>
                    </tr>
                    <f:form id="regForm" name="regForm" method="post" enctype="multipart/form-data">
                        <tr>
                            <td>
                                <input type="file" id="fileProd" name="fileProd" class="form-control" accept="image/x-png, .jpg" onchange="imagePreview(this, '001')"/>
                                <input type="hidden" id="hdProdFileNm" />
                            </td>
                            <td>
                                <input type="file" id="fileKiosk" name="fileKiosk" class="form-control" accept="image/x-png, .jpg" onchange="imagePreview(this, '002')"/>
                                <input type="hidden" id="hdKioskFileNm" />
                            </td>
                            <td>
                                <input type="file" id="fileDid" name="fileDid" class="form-control" accept="image/x-png, .jpg" onchange="imagePreview(this, '003')"/>
                                <input type="hidden" id="hdDidFileNm" />
                            </td>
                            <td>
                                <input type="file" id="fileHash" name="fileHash" class="form-control" accept="image/x-png, .jpg" onchange="imagePreview(this, '004')"/>
                                <input type="hidden" id="hdHashFileNm" />
                            </td>
                        </tr>
                    </f:form>
                    <tr>
                        <td align="center">
                            <p class="s12 red mb10"><s:message code="prodImg.fileSize.max" /></p>
                            <button type="button" class="btn_skyblue" ng-click="imgCancel('001', 'F')"><s:message code="cmm.selectCancel" /></button>
                            <button type="button" class="btn_skyblue" ng-click="regImg('001')"><s:message code="prodImg.regImg" /></button>
                            <button type="button" class="btn_skyblue" ng-click="delImg('001')"><s:message code="cmm.del" /></button>
                        </td>
                        <td>
                            <p class="s12 red mb10"><s:message code="prodImg.fileSize.max" /></p>
                            <button type="button" class="btn_skyblue" ng-click="imgCancel('002', 'F')"><s:message code="cmm.selectCancel" /></button>
                            <button type="button" class="btn_skyblue" ng-click="regImg('002')"><s:message code="prodImg.regImg" /></button>
                            <button type="button" class="btn_skyblue" ng-click="delImg('002')"><s:message code="cmm.del" /></button>
                        </td>
                        <td>
                            <p class="s12 red mb10"><s:message code="prodImg.fileSize.max" /></p>
                            <button type="button" class="btn_skyblue" ng-click="imgCancel('003', 'F')"><s:message code="cmm.selectCancel" /></button>
                            <button type="button" class="btn_skyblue" ng-click="regImg('003')"><s:message code="prodImg.regImg" /></button>
                            <button type="button" class="btn_skyblue" ng-click="delImg('003')"><s:message code="cmm.del" /></button>
                        </td>
                        <td>
                            <p class="s12 red mb10"><s:message code="prodImg.fileSize.max" /></p>
                            <button type="button" class="btn_skyblue" ng-click="imgCancel('004', 'F')"><s:message code="cmm.selectCancel" /></button>
                            <button type="button" class="btn_skyblue" ng-click="regImg('004')"><s:message code="prodImg.regImg" /></button>
                            <button type="button" class="btn_skyblue" ng-click="delImg('004')"><s:message code="cmm.del" /></button>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>
<script>
</script>
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
    // 사용자 매장브랜드
    var userHqBrandCdComboList = ${userHqBrandCdComboList};

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

<script type="text/javascript" src="/resource/solbipos/js/base/prod/prodImg/prodImgRegistView.js?ver=20210414.05" charset="utf-8"></script>

<%-- 상품분류 팝업 --%>
<c:import url="/WEB-INF/view/application/layer/searchProdClassCd.jsp">
</c:import>

<%-- 본사상품 이미지 매장적용 팝업 --%>
<c:import url="/WEB-INF/view/base/prod/prodImg/prodImgStoreRegist.jsp">
</c:import>

<%-- 이미지 복사 팝업 --%>
<c:import url="/WEB-INF/view/base/prod/prodImg/prodImgCopy.jsp">
</c:import>

<%-- 이미지 삭제 팝업 --%>
<c:import url="/WEB-INF/view/base/prod/prodImg/prodImgDelete.jsp">
</c:import>