<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd">${sessionScope.sessionInfo.currentMenu.resrceCd}</c:set>
<c:set var="menuNm">${sessionScope.sessionInfo.currentMenu.resrceNm}</c:set>

<div class="subCon" ng-controller="prodCtrl">
  <%--searchTbl--%>
  <div class="searchBar flddUnfld">
    <a href="#" class="open">${menuNm}</a>
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
                value="endDate"
                ng-model="endDate"
                control="endDateCombo"
                min="2000-01-01"
                max="2099-12-31"
                initialized="_initDateBox(s)">
              </wj-input-date>
            </span>
            <span class="chk ml10">
              <input type="checkbox" id="chkDt" checked/>
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
          <input type="text" class="sb-input w100" id="srchProdClassCd" ng-model="prodClassCd" />
        </td>
        <%-- 바코드 --%>
        <th><s:message code="prod.barCd" /></th>
        <td>
          <input type="text" class="sb-input w100" id="srchBarCd" ng-model="barCd" />
        </td>
      </tr>
    </tbody>
  </table>
  <%--//searchTbl--%>

  <div class="mt10 oh">
    <button class="btn_blue fr" id="btnSearch" ng-click="_pageView('prodCtrl', 1)">
      <s:message code="cmm.search" />
    </button>
  </div>

  <div class="mt40 oh sb-select dkbr">
    <%-- 페이지 스케일  --%>
    <wj-combo-box
      class="w100px fl"
      id="listScaleBox"
      ng-model="listScale"
      control="listScaleCombo"
      items-source="_getComboData('listScaleBox')"
      display-member-path="name"
      selected-value-path="value"
      is-editable="false"
      initialized="_initComboBox(s)">
    </wj-combo-box>


    <%-- 엑셀 다운로드 //TODO --%>
    <%--
    <button class="btn_skyblue fr" id="excelBtn">
      <s:message code="cmm.excel.down" />
    </button>
    --%>
  </div>

  <%--위즈모 테이블--%>
  <div class="wj-TblWrapBr mt10" style="height: 400px;">
    <%-- 개발시 높이 조절해서 사용--%>
    <%-- tbody영역의 셀 배경이 들어가는 부분은 .bdBg를 넣어주세요. --%>
    <div id="theGrid" style="height:393px;">
      <wj-flex-grid
        autoGenerateColumns="false"
        control="flex"
        initialized="initGrid(s,e)"
        sticky-headers="true"
        selection-mode="Row"
        items-source="data"
        item-formatter="_itemFormatter">

        <!-- define columns -->
        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="prod.prodClass"/>" binding="prodClassNm" width="150"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="prod.prodCd"/>" binding="prodCd" width="100"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="prod.prodNm"/>" binding="prodNm" width="*"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="prod.costUprc"/>" binding="costUprc" width="80"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="prod.splyUprc"/>" binding="splyUprc" width="80"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="prod.saleUprc"/>" binding="saleUprc" width="80"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="prod.orgplceCd"/>" binding="orgplceCd" width="80"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="prod.poUnitFg"/>" binding="poUnitFg" width="80"></wj-flex-grid-column>

      </wj-flex-grid>
      <%-- ColumnPicker 사용시 include --%>
      <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
        <jsp:param name="pickerTarget" value="prodCtrl"/>
      </jsp:include>
    </div>
  </div>
  <%--//위즈모 테이블--%>

  <%-- 페이지 리스트 --%>
  <div class="pageNum mt20">
    <%-- id --%>
    <ul id="prodCtrlPager" data-size="10">
    </ul>
  </div>
  <%--//페이지 리스트--%>

</div>

<%--//상품상세 정보--%>
<div id="prodFullDimmed" class="fullDimmed" style="display: none;"></div>
<div id="prodDetailLayer" class="layer" style="display: none;">
  <div class="layer_inner">
    <div class="title w600px">
      <p class="tit"><s:message code="prod.info" /></p>
      <a href="#" class="btn_close"></a>
      <div class="con sc2" style="height: 500px;">
        <p class="s14 bk mb5"><s:message code="prod.basicInfo" /></p>
        <table class="tblType01">
          <colgroup>
            <col class="w15" />
            <col class="w35" />
            <col class="w15" />
            <col class="w35" />
          </colgroup>
          <tbody>
            <tr>
              <%-- 상품이미지 //TODO --%>
              <th rowspan="3"><s:message code="prod" /><br /><s:message code="image" />
              </th>
              <td rowspan="3">
                <%--등록한 상품이 없는 경우--%>
                <span class="goodsNo">IMAGE</span>
                <!--
                <span class="goodsYes"><img src="img/sample.jpg" alt="" /></span> <%--등록한 상품이 있는 경우--%>
                -->
              </td>
              <%--단가구분 //TODO --%>
              <th>
                <div class="impWrap"><s:message code="prod.uprcFg" /></div>
              </th>
              <td id="_uprcFg"></td>
            </tr>
            <tr>
              <%--상품코드--%>
              <th>
                <div class="impWrap"><s:message code="prod.prodCd" /><em class="imp">*</em></div>
              </th>
              <td id="_prodCd"></td>
            </tr>
            <tr>
              <%--상품명--%>
              <th>
                <div class="impWrap"><s:message code="prod.prodNm" /></div>
              </th>
              <td id="_prodNm"></td>
            </tr>
            <tr>
              <%--상품분류--%>
              <th><s:message code="prod.prodClass" /></th>
              <td id="_prodClassNm"></td>
              <%--거래처 //TODO --%>
              <th>
                <div class="impWrap"><s:message code="prod.vendr" /></div>
              </th>
              <td id="_vendr"></td>
            </tr>
            <tr>
              <%--판매상품여부--%>
              <th><s:message code="prod.saleProdYn" /></th>
              <td id="_saleProdYn"></td>
              <%--원산지--%>
              <th>
                <div class="impWrap"><s:message code="prod.orgplceCd" /></div>
              </th>
              <td id="_orgplceCd"></td>
            </tr>
            <tr>
              <%--최초판매단가--%>
              <th>
                <div class="impWrap"><s:message code="prod.firstSaleUprc" /></div>
              </th>
              <td id="_saleUprc"></td>
              <%--원가단가--%>
              <th>
                <div class="impWrap"><s:message code="prod.costUprc" /></div>
              </th>
              <td id="_costUprc"></td>
            </tr>
            <tr>
              <%--주문상품구분--%>
              <th>
                <div class="impWrap"><s:message code="prod.poProdFg" /></div>
              </th>
              <td id="_poProdFg"></td>
              <%--주문단위--%>
              <th>
                <div class="impWrap"><s:message code="prod.poUnitFg" /></div>
              </th>
              <td id="_poUnitFg"></td>
            </tr>
            <tr>
              <%--최소주문--%>
              <th>
                <div class="impWrap"><s:message code="prod.poMinQty" /></div>
              </th>
              <td id="_poMinQty"></td>
              <th></th>
              <td></td>
            </tr>
          </tbody>
        </table>
        <p class="s14 bk mt20 mb5"><s:message code="prod.addInfo" /></p>
        <table class="tblType01">
          <colgroup>
            <col class="w15" />
            <col class="w35" />
            <col class="w15" />
            <col class="w35" />
          </colgroup>
          <tbody>
            <tr>
              <%--과세여부--%>
              <th>
                <div class="impWrap"><s:message code="prod.vatFg" /><em class="imp">*</em></div>
              </th>
              <td id="_vatFg"></td>
              <%--재고관리여부--%>
              <th>
                <div class="impWrap"><s:message code="prod.stockProdYn" /><em class="imp">*</em></div>
              </th>
              <td id="_stockProdYn"></td>
            </tr>
            <tr>
              <%--사용여부--%>
              <th>
                <div class="impWrap"><s:message code="useYn" /><em class="imp">*</em></div>
              </th>
              <td id="_useYn"></td>
              <%--안전재고--%>
              <th>
                <div class="impWrap"><s:message code="prod.safeStockQty" /><em class="imp">*</em></div>
              </th>
              <td id="_safeStockQty"></td>
            </tr>
            <tr>
              <%--품절여부 //TODO --%>
              <th>
                <div class="impWrap"><s:message code="prod.soldOutYn" /><em class="imp">*</em></div>
              </th>
              <td id="_soldOutYn"></td>
              <%--초기재고 //TODO --%>
              <th>
                <div class="impWrap"><s:message code="prod.defaultStock" /><em class="imp">*</em></div>
              </th>
              <td id="_defaultStock"></td>
            </tr>
            <tr>
              <%--저장품코드 //TODO --%>
              <th>
                <div class="impWrap"><s:message code="prod.saveProdCd" /><em class="imp">*</em></div>
              </th>
              <td><a href="#" class="link" id="_saveProdCd"></a></td>
              <%--세트상품구분//TODO --%>
              <th>
                <div class="impWrap"><s:message code="prod.setProdFg" /><em class="imp">*</em></div>
              </th>
              <td id="_setProdFg"></td>
            </tr>
            <tr>
              <%--환급적용여부--%>
              <th>
                <div class="impWrap"><s:message code="prod.refApplyYn" /><em class="imp">*</em></div>
              </th>
              <td id="_refApplyYn"></td>
              <th></th>
              <td></td>
            </tr>
          </tbody>
        </table>
        <%--비고--%>
        <p class="s14 bk mt20 mb5"><s:message code="remark" /></p>
        <table class="tblType01">
          <colgroup>
            <col class="w100" />
          </colgroup>
          <tbody>
            <tr>
              <th class="gr lh20" id="_remark">
              </th>
            </tr>
          </tbody>
        </table>

        <%-- 할인 / 적립 --%>
        <p class="s14 bk mt20 mb5"><s:message code="prod.dcAndSaveInfo" /></p>
        <table class="tblType01">
          <colgroup>
            <col width="15%" />
            <col width="85%" />
          </colgroup>
          <tbody>
            <tr>
              <%--할인 //TODO --%>
              <th><s:message code="prod.dc" /></th>
              <td id="_dc"></td>
            </tr>
            <tr>
              <%--적립 //TODO --%>
              <th><s:message code="prod.save" /></th>
              <td id="_save"></td>
            </tr>
          </tbody>
        </table>
        <p class="s14 bk mt20 mb5"><s:message code="prod.unitstInfo" /></p>
        <table class="tblType01 mb20">
          <colgroup>
            <col class="w15" />
            <col class="w85" />
          </colgroup>
          <tbody id="_unitstData">
          </tbody>
        </table>
      </div>
      <div class="btnSet">
        <span><a href="#" id="btnClose" class="btn_blue">닫기</a></span>
      </div>
    </div>
    <!--//layerContent-->
  </div>
</div>
<!--//layer:For Center screen-->

<script type="text/javascript" src="/resource/solbipos/js/base/prod/prod/prod.js?ver=20181027.01" charset="utf-8"></script>
