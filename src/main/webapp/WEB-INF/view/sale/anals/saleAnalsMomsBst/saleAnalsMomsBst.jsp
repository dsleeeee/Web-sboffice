<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="subCon" ng-controller="saleAnalsMomsBstCtrl">
    <div class="searchBar">
      <a href="#" class="open fl"><s:message code="saleAnalsMomsBst.saleAnalsMomsBst"/></a>
      <%-- 조회 --%>
      <button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_broadcast('saleAnalsMomsBstCtrl')">
        <s:message code="cmm.search"/>
      </button>
      <c:if test="${sessionInfo.orgnFg == 'HQ'}">
        <%-- 확장조회 --%>
        <button class="btn_blue fr mt5 mr5" id="btnSearchAddShow" ng-click="searchAddShowChange()">
          <s:message code="cmm.search.addShow" />
        </button>
      </c:if>
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
              <%-- 화면출력 --%>
              <th><s:message code="saleAnalsMomsBst.viewType"/></th>
              <td>
                <div class="sb-select">
                    <wj-combo-box
                        id="srchViewType"
                        ng-model="viewType"
                        items-source="_getComboData('srchViewType')"
                        display-member-path="name"
                        selected-value-path="value"
                        is-editable="false"
                        control="srchViewTypeCombo">
                    </wj-combo-box>
                </div>
              </td>
              <th></th>
              <td></td>
          </tr>
          <tr>
              <%-- 조회옵션1 --%>
              <th><s:message code="saleAnalsMomsBst.srchOption1"/></th>
              <td>
                  <div class="fl pd5" style="padding-right: 15px;">
                      <div style="float: left;"><input type="checkbox" id="chkOpt1Store" ng-model="opt1Store"/></div>
                      <div style="float: left; padding-top: 3px; padding-left:5px; padding-right:10px;"><label><s:message code="saleAnalsMomsBst.store" /></label></div>
                      <div style="float: left;"><input type="checkbox" id="chkOpt1App" ng-model="opt1App"/></div>
                      <div style="float: left; padding-top: 3px; padding-left:5px; padding-right:10px;"><label><s:message code="saleAnalsMomsBst.app" /></label></div>
                      <div style="float: left;"><input type="checkbox" id="chkOpt1DlvrAppBaemin" ng-model="opt1DlvrAppBaemin"/></div>
                      <div style="float: left; padding-top: 3px; padding-left:5px; padding-right:10px;"><label><s:message code="saleAnalsMomsBst.dlvrApp(baemin)" /></label></div>
                      <div style="float: left;"><input type="checkbox" id="chkOpt1DlvrAppYogiyo" ng-model="opt1DlvrAppYogiyo"/></div>
                      <div style="float: left; padding-top: 3px; padding-left:5px; padding-right:10px;"><label><s:message code="saleAnalsMomsBst.dlvrApp(yogiyo)" /></label></div>
                      <div style="float: left;"><input type="checkbox" id="chkOpt1DlvrAppEtc" ng-model="opt1DlvrAppEtc"/></div>
                      <div style="float: left; padding-top: 3px; padding-left:5px; padding-right:10px;"><label><s:message code="saleAnalsMomsBst.dlvrApp(etc)" /></label></div>
                  </div>
              </td>
              <%-- 조회옵션2 --%>
              <th><s:message code="saleAnalsMomsBst.srchOption2"/></th>
              <td>
                  <div style="float: left;"><input type="checkbox" id="chkOpt2StoreIn" ng-model="opt2StoreIn"/></div>
                  <div style="float: left; padding-top: 3px; padding-left:5px; padding-right:10px;"><label><s:message code="saleAnalsMomsBst.storeIn" /></label></div>
                  <div style="float: left;"><input type="checkbox" id="chkOpt2Pack" ng-model="opt2Pack"/></div>
                  <div style="float: left; padding-top: 3px; padding-left:5px; padding-right:10px;"><label><s:message code="saleAnalsMomsBst.pack" /></label></div>
                  <div style="float: left;"><input type="checkbox" id="chkOpt2Dlvr" ng-model="opt2Dlvr"/></div>
                  <div style="float: left; padding-top: 3px; padding-left:5px; padding-right:10px;"><label><s:message code="saleAnalsMomsBst.dlvr" /></label></div>
              </td>
          </tr>
          <tr>
              <%-- 기간선택 --%>
              <th><s:message code="saleAnalsMomsBst.period"/></th>
              <td>
                <div class="sb-select">
                    <%-- 일/월/년 구분 --%>
                    <span class="txtIn w15">
                       <div class="sb-select">
                          <wj-combo-box
                              id="srchDayGubun"
                              ng-model="dayGubun"
                              items-source="_getComboData('srchDayGubun')"
                              display-member-path="name"
                              selected-value-path="value"
                              is-editable="false"
                              control="srchDayGubunCombo"
                              selected-index-changed="setDayGubunCombo(s)">
                          </wj-combo-box>
                      </div>
                    </span>
                    <%-- 일 --%>
                    <span id="spanDay">
                        <span class="txtIn"><input id="srchStartDate" class="w110px"></span>
                        <span class="rg">~</span>
                        <span class="txtIn"><input id="srchEndDate" class="w110px"></span>
                    </span>
                    <%-- 월 --%>
                    <span id="spanMonth" style="display: none;">
                        <span class="txtIn"><input id="startMonth" class="w110px"></span>
                        <span class="rg">~</span>
                        <span class="txtIn"><input id="endMonth" class="w110px"></span>
                    </span>
                    <%-- 년 --%>
                    <span id="spanYear" style="display: none;">
                        <span class="txtIn"><input id="startYear" class="w110px"></span>
                        <span class="rg">~</span>
                        <span class="txtIn"><input id="endYear" class="w110px"></span>
                    </span>
                </div>
              </td>
              <%-- 분류조회 --%>
              <th><s:message code="saleAnalsMomsBst.prodClass" /></th>
              <td>
                  <input type="text" class="sb-input w70" id="srchProdClassCd" ng-model="prodClassNm" ng-click="popUpProdClass()" style="float: left;"
                          placeholder="<s:message code="prod.prodClass" /> 선택" readonly/>
                  <input type="hidden" id="_prodClassCd" name="prodClassCd" ng-model="prodClassCd" disabled />
                  <button type="button" class="btn_skyblue fl mr5" id="btnCancelProdClassCd" style="margin-left: 5px;" ng-click="delProdClass()"><s:message code="cmm.selectCancel"/></button>
              </td>
          </tr>
          <tr>
              <%-- 일자표시옵션 --%>
              <%--<th><s:message code="saleAnalsMomsBst.dayOption"/></th>
              <td>
                  <div class="sb-select">
                      <wj-combo-box
                              id="srchDayOption"
                              ng-model="dayOption"
                              items-source="_getComboData('srchDayOption')"
                              display-member-path="name"
                              selected-value-path="value"
                              is-editable="false"
                              control="srchDayOptionCombo">
                      </wj-combo-box>
                  </div>
              </td>--%>
              <%-- 상품표시옵션 --%>
              <th><s:message code="saleAnalsMomsBst.prodOption"/></th>
              <td>
                  <div class="sb-select">
                      <wj-combo-box
                              id="srchProdOption"
                              ng-model="prodOption"
                              items-source="_getComboData('srchProdOption')"
                              display-member-path="name"
                              selected-value-path="value"
                              is-editable="false"
                              control="srchProdOptionCombo">
                      </wj-combo-box>
                  </div>
              </td>
              <th></th>
              <td></td>
          </tr>
          <tr>
              <%-- 상품코드 --%>
              <th><s:message code="saleAnalsMomsBst.prodCd" /></th>
              <td>
                  <input type="text" id="srchProdCd" ng-model="prodCd" class="sb-input w100" maxlength="13" onkeyup="fnNxBtnSearch('1');"/>
              </td>
              <%-- 상품명 --%>
              <th><s:message code="saleAnalsMomsBst.prodNm" /></th>
              <td>
                  <input type="text" id="srchProdNm" ng-model="prodNm" class="sb-input w100" maxlength="100" onkeyup="fnNxBtnSearch('1');"/>
              </td>
          </tr>
          <c:if test="${sessionInfo.orgnFg == 'HQ'}">
          <tr>
              <%-- 매장브랜드 --%>
              <th><s:message code="saleAnalsMomsBst.storeHqBrand"/></th>
              <td>
                  <div class="sb-select">
                      <wj-combo-box
                        id="srchStoreHqBrandCd"
                        items-source="_getComboData('srchStoreHqBrandCd')"
                        display-member-path="name"
                        selected-value-path="value"
                        is-editable="false"
                        control="srchStoreHqBrandCdCombo">
                      </wj-combo-box>
                  </div>
              </td>
              <%-- 매장코드 --%>
              <th><s:message code="cmm.store"/></th>
              <td>
                <%-- 매장선택 모듈 싱글 선택 사용시 include
                     param 정의 : targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
                                  displayNm - 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
                                  modiFg - 수정여부(변수 없을 경우 기본값으로 수정가능)
                                  closeFunc - 팝업 닫기시 호출할 함수
                --%>
                <jsp:include page="/WEB-INF/view/sale/com/popup/selectStoreMMoms.jsp" flush="true">
                    <jsp:param name="targetId" value="saleAnalsMomsBstStore"/>
                </jsp:include>
                <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
              </td>
          </tr>
          </c:if>
          <c:if test="${sessionInfo.orgnFg == 'STORE'}">
              <input type="hidden" id="saleAnalsMomsBstStoreCd" value="${sessionInfo.storeCd}"/>
          </c:if>
          <tr>
              <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                 <%-- 상품브랜드 --%>
                  <th><s:message code="saleAnalsMomsBst.prodHqBrand"/></th>
                  <td>
                      <div class="sb-select">
                          <wj-combo-box
                              id="srchProdHqBrand"
                              items-source="_getComboData('srchProdHqBrand')"
                              display-member-path="name"
                              selected-value-path="value"
                              is-editable="false"
                              control="srchProdHqBrandCombo">
                          </wj-combo-box>
                      </div>
                  </td>
              </c:if>
              <%-- 상품 --%>
              <th><s:message code="saleAnalsMomsBst.prod"/></th>
              <td>
                  <%-- 상품선택 모듈 싱글 선택 사용시 include param 정의 :
                       targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
                       displayNm - 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
                       modiFg - 수정여부(변수 없을 경우 기본값으로 수정가능)
                       closeFunc - 팝업 닫기시 호출할 함수--%>
                  <jsp:include page="/WEB-INF/view/sale/com/popup/selectProdMMoms.jsp" flush="true">
                      <jsp:param name="targetId" value="saleAnalsMomsBstSelect"/>
                  </jsp:include>
                  <%--// 상품선택 모듈 멀티 선택 사용시 include --%>
              </td>
              <c:if test="${sessionInfo.orgnFg == 'STORE'}">
                  <td></td>
                  <td></td>
              </c:if>
          </tr>
      </tbody>
    </table>
    <c:if test="${sessionInfo.orgnFg == 'HQ'}">
        <table class="searchTbl" id="tblSearchAddShow" style="display: none;">
          <colgroup>
            <col class="w15"/>
            <col class="w35"/>
            <col class="w15"/>
            <col class="w35"/>
          </colgroup>
          <tbody>
          <tr>
              <%-- 팀별 --%>
            <th><s:message code="saleAnalsMomsBst.momsTeam"/></th>
            <td>
              <div class="sb-select">
                <wj-combo-box
                  id="srchMomsTeam"
                  items-source="_getComboData('srchMomsTeam')"
                  display-member-path="name"
                  selected-value-path="value"
                  is-editable="false"
                  control="srchMomsTeamCombo">
                </wj-combo-box>
              </div>
            </td>
              <%-- AC점포별 --%>
            <th><s:message code="saleAnalsMomsBst.momsAcShop"/></th>
            <td>
              <div class="sb-select">
                <wj-combo-box
                  id="srchMomsAcShop"
                  items-source="_getComboData('srchMomsAcShop')"
                  display-member-path="name"
                  selected-value-path="value"
                  is-editable="false"
                  control="srchMomsAcShopCombo">
                </wj-combo-box>
              </div>
            </td>
          </tr>
          <tr>
              <%-- 지역구분 --%>
            <th><s:message code="saleAnalsMomsBst.momsAreaFg"/></th>
            <td>
              <div class="sb-select">
                <wj-combo-box
                  id="srchMomsAreaFg"
                  items-source="_getComboData('srchMomsAreaFg')"
                  display-member-path="name"
                  selected-value-path="value"
                  is-editable="false"
                  control="srchMomsAreaFgCombo">
                </wj-combo-box>
              </div>
            </td>
              <%-- 상권 --%>
            <th><s:message code="saleAnalsMomsBst.momsCommercial"/></th>
            <td>
              <div class="sb-select">
                <wj-combo-box
                  id="srchMomsCommercial"
                  items-source="_getComboData('srchMomsCommercial')"
                  display-member-path="name"
                  selected-value-path="value"
                  is-editable="false"
                  control="srchMomsCommercialCombo">
                </wj-combo-box>
              </div>
            </td>
          </tr>
          <tr>
              <%-- 점포유형 --%>
            <th><s:message code="saleAnalsMomsBst.momsShopType"/></th>
            <td>
              <div class="sb-select">
                <wj-combo-box
                  id="srchMomsShopType"
                  items-source="_getComboData('srchMomsShopType')"
                  display-member-path="name"
                  selected-value-path="value"
                  is-editable="false"
                  control="srchMomsShopTypeCombo">
                </wj-combo-box>
              </div>
            </td>
              <%-- 매장관리타입 --%>
            <th><s:message code="saleAnalsMomsBst.momsStoreManageType"/></th>
            <td>
              <div class="sb-select">
                <wj-combo-box
                  id="srchMomsStoreManageType"
                  items-source="_getComboData('srchMomsStoreManageType')"
                  display-member-path="name"
                  selected-value-path="value"
                  is-editable="false"
                  control="srchMomsStoreManageTypeCombo">
                </wj-combo-box>
              </div>
            </td>
          </tr>
          <tr>
            <%-- 지사 --%>
            <th><s:message code="saleAnalsMomsBst.branchCd"/></th>
            <td>
              <div class="sb-select">
                <wj-combo-box
                  id="srchBranchCd"
                  items-source="_getComboData('srchBranchCd')"
                  display-member-path="name"
                  selected-value-path="value"
                  is-editable="false"
                  control="srchBranchCdCombo">
                </wj-combo-box>
              </div>
            </td>
            <td></td>
            <td></td>
          </tr>
          </tbody>
        </table>
    </c:if>

    <div class="mt10 oh sb-select dkbr">
      <%-- 조회조건 엑셀다운로드 --%>
      <button class="btn_skyblue ml5 fr" ng-click="excelDownload()"><s:message code="cmm.excel.downCondition"/></button>
    </div>

    <div class="w100 mt10">
      <%--위즈모 테이블--%>
      <div class="wj-gridWrap" style="height: 380px; overflow-x: hidden; overflow-y: hidden;">
        <wj-flex-grid
          id="wjGridList"
          autoGenerateColumns="false"
          selection-mode="Row"
          items-source="data"
          control="flex"
          initialized="initGrid(s,e)"
          is-read-only="false"
          item-formatter="_itemFormatter">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="saleAnalsMomsBst.storeCd"/>" binding="storeCd" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="saleAnalsMomsBst.storeNm"/>" binding="storeNm" width="120" align="left" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="saleAnalsMomsBst.area"/>" binding="areaNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="saleAnalsMomsBst.lClassCd"/>" binding="lClassCd" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="saleAnalsMomsBst.lClassNm"/>" binding="lClassNm" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="saleAnalsMomsBst.mClassCd"/>" binding="mClassCd" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="saleAnalsMomsBst.mClassNm"/>" binding="mClassNm" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="saleAnalsMomsBst.sClassCd"/>" binding="sClassCd" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="saleAnalsMomsBst.sClassNm"/>" binding="sClassNm" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="saleAnalsMomsBst.prodCd"/>" binding="prodCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="saleAnalsMomsBst.prodNm"/>" binding="prodNm" width="120" align="left" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="saleAnalsMomsBst.storeCd"/>" binding="storeCd" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="saleAnalsMomsBst.storeNm"/>" binding="storeNm" width="120" align="left" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="saleAnalsMomsBst.area"/>" binding="areaNm" width="75" align="left" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="" binding="orderSeq" width="80" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="" binding="rowType" width="80" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>

        </wj-flex-grid>

      </div>
      <%--//위즈모 테이블--%>
  </div>

</div>

<style type="text/css">
    input[type=checkbox]  {
        width: 15px;
        height: 15px;
    }
</style>

<script type="text/javascript">

    // 콤보박스 데이터
    var momsHqBrandCdComboList = ${momsHqBrandCdComboList};
    var branchCdComboList = ${branchCdComboList};
    var momsTeamComboList = ${momsTeamComboList};
    var momsAcShopComboList = ${momsAcShopComboList};
    var momsAreaFgComboList = ${momsAreaFgComboList};
    var momsCommercialComboList = ${momsCommercialComboList};
    var momsShopTypeComboList = ${momsShopTypeComboList};
    var momsStoreManageTypeComboList = ${momsStoreManageTypeComboList};

</script>

<script type="text/javascript" src="/resource/solbipos/js/sale/anals/saleAnalsMomsBst/saleAnalsMomsBst.js?ver=20230208.01" charset="utf-8"></script>