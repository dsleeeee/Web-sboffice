<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd">${sessionScope.sessionInfo.currentMenu.resrceCd}</c:set>
<c:set var="menuNm">${sessionScope.sessionInfo.currentMenu.resrceNm}</c:set>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />


<div class="subCon" ng-controller="prodKitchenprintLinkCtrl">
  <%--searchTbl--%>
  <div class="searchBar flddUnfld">
    <a href="#" class="open fl">${menuNm}</a>
    <%-- 조회 --%>
    <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
      <button class="btn_blue fr" id="btnSearch" ng-click="_broadcast('prodKitchenprintLinkCtrl')">
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
      <th><s:message code="prod.regDate" /></th>
      <td colspan="3">
        <div class="sb-select">
            <span class="txtIn w110px">
              <wj-input-date
                      id="srchTimeStartDate"
                      value="startDate"
                      ng-model="startDate"
                      control="startDateCombo"
                      min="2000-01-01"
                      max="2099-12-31"
                      initialized="_initDateBox(s)">
              </wj-input-date>
            </span>
          <span class="rg">~</span>
          <span class="txtIn w110px">
              <wj-input-date
                      id="srchTimeEndDate"
                      value="endDate"
                      ng-model="endDate"
                      control="endDateCombo"
                      min="2000-01-01"
                      max="2099-12-31"
                      initialized="_initDateBox(s)">
              </wj-input-date>
            </span>
          <%--전체기간--%>
          <span class="chk ml10">
              <input type="checkbox" id="chkDt" ng-model="isChecked" ng-change="isChkDt()" />
              <label for="chkDt">
                <s:message code="cmm.all.day" />
              </label>
            </span>
        </div>
      </td>
    </tr>
    <tr>
      <%-- 상품코드 --%>
      <th><s:message code="prod.prodCd" /></th>
      <td>
        <input type="text" class="sb-input w100" id="srchProdCd" ng-model="prodCd" />
      </td>
      <%-- 상품명 --%>
      <th><s:message code="prod.prodNm" /></th>
      <td>
        <input type="text" class="sb-input w100" id="srchProdNm" ng-model="prodNm" />
      </td>
    </tr>
    <tr>
      <%-- 분류조회 --%>
      <th><s:message code="prod.prodClass" /></th>
      <td>
        <input type="text" class="sb-input w80" id="srchProdClassCd" ng-model="prodClassCdNm" ng-click="popUpProdClass()" style="float: left;"
               placeholder="<s:message code="prod.prodClass" /> 선택" readonly/>
        <input type="hidden" id="_prodClassCd" name="prodClassCd" ng-model="prodClassCd" disabled />
        <button type="button" class="btn_skyblue fl mr5" id="btnCancelProdClassCd" style="margin-left: 5px;" ng-click="delProdClass()"><s:message code="cmm.selectCancel"/></button>
      </td>
      <%-- 바코드 --%>
      <th><s:message code="prod.barCd" /></th>
      <td>
        <input type="text" class="sb-input w100" id="srchBarCd" ng-model="barCd" />
      </td>
    </tr>
    <tr>
      <%-- 사용여부 --%>
      <th><s:message code="prod.useYn" /></th>
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
      <th></th>
      <td></td>
    </tr>
    <tr>
      <%-- 매장상태 --%>
      <th><s:message code="prodKitchenprintLink.sysStatFg" /></th>
      <td>
        <div class="sb-select">
          <wj-combo-box
                  id="srchSysStatFg"
                  ng-model="sysStatFg"
                  items-source="_getComboData('srchSysStatFg')"
                  display-member-path="name"
                  selected-value-path="value"
                  is-editable="false"
                  initialized="_initComboBox(s)"
                  selected-index="1"
                  text-changed="sysStatFgChange()"
          >
          </wj-combo-box>
        </div>
      </td>

      <th><s:message code="prodKitchenprintLink.store"/></th>
      <td>
        <%-- 매장선택 모듈 멀티 선택 사용시 include --%>
          <jsp:include page="/WEB-INF/view/iostock/cmm/selectStoreM.jsp" flush="true">
            <jsp:param name="targetId" value="prodKitchenprintStore"/>
          </jsp:include>
        <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
      </td>
    </tr>
      <input type="hidden" id="prodKitchenprintStoreCd" value="${sessionInfo.storeCd}"/>
    </tbody>
  </table>
  <%--//searchTbl--%>
    <div class="w28 fl mt40">
      <div class="wj-TblWrapBr ml10 pd20 ng-scope" >
    <%-- 상품별 매출현황 --%>
      <div class="oh sb-select mb10">
        <span class="fl bk lh60"></span>
      </div>

      <%--위즈모 테이블--%>
      <div class="wj-gridWrap" style="height: 500px; overflow-x: hidden; overflow-y: hidden;">
        <wj-flex-grid
                autoGenerateColumns="false"
                selection-mode="Row"
                items-source="data"
                sticky-headers="true"
                control="flex"
                initialized="initGrid(s,e)"
                is-read-only="false"
                item-formatter="_itemFormatter">

          <!-- define columns -->
          <wj-flex-grid-column header="<s:message code="prodKitchenprintLink.prodCd"/>" binding="prodCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodKitchenprintLink.prodNm"/>" binding="prodNm" width="*" align="left" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodKitchenprintLink.saleUprc"/>" binding="saleUprc" width="100" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
        </wj-flex-grid>
      </div>
      <%--//위즈모 테이블--%>
    </div>
  </div>
</div>
<div class="w35 fl">
  <%-- 연결된 프린터 --%>
  <div class="wj-TblWrapBr ml10 pd20 ng-scope"  ng-controller="prodKitchenprintLinkedCtrl">

    <div id="srchSysStatFg2" style="display: none"></div>

    <div class="oh sb-select mb10">
      <span class="fl bk lh30" id="prodNm"></span>
      <span class="fl bk lh30" id="prodCd" style="display: none"></span>
      <%-- 삭제버튼 --%>
      <button class="btn_skyblue ml5 fr" ng-click="unlinkPrint()"><s:message code="cmm.delete"/></button>
    </div>

    <%--위즈모 테이블--%>
    <div class="wj-gridWrap" style="height: 480px; overflow-x: hidden; overflow-y: hidden;">
      <wj-flex-grid
              autoGenerateColumns="false"
              selection-mode="Row"
              items-source="data"
              control="flex"
              initialized="initGrid(s,e)"
              is-read-only="false"
              item-formatter="_itemFormatter">

        <!-- define columns -->
        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="prodKitchenprintLink.storeCd"/>" binding="storeCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="prodKitchenprintLink.storeNm"/>" binding="storeNm" width="*" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="prodKitchenprintLink.prterNo"/>" binding="prterNo" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="prodKitchenprintLink.prterNm"/>" binding="prterNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>

      </wj-flex-grid>
    </div>
    <%--//위즈모 테이블--%>
  </div>
</div>
<div class="w35 fl">
  <%-- 안연결된 프린터 --%>
  <div class="wj-TblWrapBr ml10 pd20 ng-scope" ng-controller="prodKitchenprintUnlinkCtrl">

    <div class="oh sb-select mb10">
      <span class="fl bk lh30"></span>
      <%-- 추가버튼 --%>
      <button class="btn_skyblue ml5 fr" ng-click="linkedPrint()"><s:message code="cmm.add"/></button>
    </div>

    <%--위즈모 테이블--%>
    <div class="wj-gridWrap" style="height: 480px; overflow-x: hidden; overflow-y: hidden;">
      <wj-flex-grid
              autoGenerateColumns="false"
              selection-mode="Row"
              items-source="data"
              control="flex"
              initialized="initGrid(s,e)"
              is-read-only="false"
              item-formatter="_itemFormatter">

        <!-- define columns -->
        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="prodKitchenprintLink.storeCd"/>" binding="storeCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="prodKitchenprintLink.storeNm"/>" binding="storeNm" width="*" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="prodKitchenprintLink.prterNo"/>" binding="prterNo" width="80" align="center" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="prodKitchenprintLink.prterNm"/>" binding="prterNm" width="100" align="left" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>

      </wj-flex-grid>
    </div>
    <%--//위즈모 테이블--%>
  </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/prodKitchenprintLink/prodKitchenprintLink.js?ver=20201224.02" charset="utf-8"></script>

<script type="text/javascript">
  var sysStatFg = ${ccu.getCommCode("005")};
</script>

<%-- 레이어 팝업 : 상품정보 입력/수정 --%>
<c:import url="/WEB-INF/view/base/prod/prod/prodModifyView.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 레이어 팝업 : 상품별 적용매장 선택 팝업 --%>
<c:import url="/WEB-INF/view/base/prod/prod/prodStoreRegistView.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 레이어 팝업 : 매장 리스트 팝업 --%>
<c:import url="/WEB-INF/view/base/prod/prod/storeProdBatchList.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 레이어 팝업 : 매장별 상품 일괄적용 팝업 --%>
<c:import url="/WEB-INF/view/base/prod/prod/storeProdBatchRegist.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>