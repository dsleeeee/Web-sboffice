<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />

<wj-popup control="wjProductRegistLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:1000px;height:750px;">
    <div class="wj-dialog wj-dialog-columns title" ng-controller="productRegistCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <label id="popTitle"></label>&nbsp;&nbsp;<label id="lblPRProductDateTitle"></label> : <label id="lblPRProductDate"></label>
            <a href="" class="wj-hide btn_close" ng-click="close()"></a>
        </div>

        <div class="wj-dialog-body sc2" style="height: 700px;">

            <table class="tblType01" style="position: relative;">
              <colgroup>
                <col class="w15"/>
                <col class="w35"/>
                <col class="w15"/>
                <col class="w35"/>
              </colgroup>
              <tbody>
              <tr>
                <%-- 생산등록/폐기등록 제목 --%>
                <th><label id="lblPRTitleTitle"></label><em class="imp">*</em></th>
                <td colspan="3">
                  <input type="text" id="txtPRTitle" ng-model="txtPRTitle" class="sb-input w100" maxlength="33"/>
                </td>
              </tr>
              <tr>
                <%-- 상품코드 --%>
                <th><s:message code="product.prodCd"/></th>
                <td>
                  <input type="text" id="srchPRProdCd" name="srchPRProdCd" ng-model="prodCd" class="sb-input w100" maxlength="13"/>
                </td>
                <%-- 상품명 --%>
                <th><s:message code="product.prodNm"/></th>
                <td>
                  <input type="text" id="srchPRProdNm" name="srchPRProdNm" ng-model="prodNm" class="sb-input w100" maxlength="50"/>
                </td>
              </tr>
              <tr>
                <%-- 바코드 --%>
                <th><s:message code="product.barCd"/></th>
                <td>
                  <input type="text" id="srchPRBarcdCd" name="srchPRBarcdCd" ng-model="barcdCd" class="sb-input w100" maxlength="40"/>
                </td>
                <%-- 상품분류 --%>
                <th><s:message code="product.prodClass"/></th>
                <td>
                  <input type="text" class="sb-input w100" id="srchPRProdClassCd" ng-model="prodClassCdNm" ng-click="popUpPRProdClass()"
                         placeholder="<s:message code="cmm.all" />" readonly/>
                  <input type="hidden" id="_prodClassCd" name="prodClassCd" class="sb-input w100" ng-model="prodClassCd" disabled/>
                </td>
              </tr>
              <tr>
                <td colspan="4">
                  <%--<a href="#" class="btn_grayS" ng-click="readerFile()"><s:message code="product.readerFile"/></a>--%>
                  <a href="#" class="btn_grayS" ng-click="readerFormUpload('formDownload')"><s:message code="product.formDownload"/></a>
                  <span class="txtIn w120px" style="border:1px solid #e8e8e8;">
                    <wj-combo-box
                      id="PRAddQtyFg"
                      ng-model="PRAddQtyFg"
                      items-source="_getComboData('PRAddQtyFg')"
                      display-member-path="name"
                      selected-value-path="value"
                      is-editable="false"
                      control="PRAddQtyFgCombo"
                      initialized="_initComboBox(s)">
                    </wj-combo-box>
                  </span>
                  <input type="file" class="form-control" id="textUpFile" ng-model="textUpFile" onchange="angular.element(this).scope().textFileChanged()" accept=".txt" style="display: none;"/>
                  <a href="#" class="btn_grayS" ng-click="readerFormUpload('formUpload')"><s:message code="product.readerFile"/><s:message code="product.upload"/></a>
                  <a href="#" class="btn_grayS" ng-click="excelDownload()"><s:message code="product.excelDownload"/></a>
                  <a href="#" class="btn_grayS" ng-click="uploadErrInfo()"><s:message code="product.readerFile"/><s:message code="product.uploadErrorInfo"/></a>
                </td>
              </tr>
              </tbody>
            </table>

            <div class="mt10 oh">
                <%-- 조회 --%>
                <button type="button" class="btn_blue fr ml5" id="btnSearch" ng-click="fnSearch();">
                  <s:message code="cmm.search"/>
                </button>
                <%-- 상품찾기 --%>
                <button type="button" class="btn_blue fr ml5" id="btnProdFind" ng-click="prodFind();">
                  <s:message code="adj.reg.prodFind"/>
                </button>
            </div>

            <%--<ul class="txtSty3 mt10">
                <li class="red"><s:message code="product.comment.txt1"/></li>
                <li class="red"><s:message code="product.comment.txt2"/></li>
            </ul>--%>

            <table id="prodFind" class="tblType01 mt10 tc" style="position: relative; display: none;">
                <colgroup>
                  <col class="w70"/>
                  <col class="w30"/>
                </colgroup>
                <tbody>
                    <tr>
                      <%-- 상품코드/바코드 --%>
                      <th class="tc">
                        <s:message code="product.prodCd"/>/<s:message code="product.barCd"/></th>
                      <%-- 추가수량 --%>
                      <th class="tc"><s:message code="product.addQty"/></th>
                    </tr>
                    <tr>
                      <td>
                        <input type="text" id="prodBarcdCd" name="prodBarcdCd" ng-model="prodBarcdCd" class="sb-input tc" maxlength="40" style="width: 250px;" ng-keydown="searchProdKeyEvt($event)"/>
                        <%-- 찾기 --%>
                        <a href="#" class="btn_grayS" ng-click="prodFindPop()"><s:message code="product.prodFind"/></a>
                        <span class="chk txtIn lh30 ml5" style="top: -2px;">
                          <input type="checkbox" name="autoAddChk" id="autoAddChk" ng-model="autoAddChk"/>
                          <label for="autoAddChk"><s:message code="adj.reg.autoAdd"/></label>
                        </span>
                      </td>
                      <td>
                        <input type="text" id="addQty" name="addQty" ng-model="addQty" class="sb-input tc" maxlength="10" style="width: 100px;" ng-keydown="addQtyKeyEvt($event)"/>
                        <%-- 추가 --%>
                        <a href="#" class="btn_grayS" ng-click="fnAddQty()"><s:message code="cmm.add"/></a>
                      </td>
                </tr>
                </tbody>
            </table>

            <div class="mt10 tr">
                <div class="oh sb-select">
                  <%-- 페이지 스케일  --%>
                  <wj-combo-box
                      class="w100px fl"
                      id="PRListScaleBox"
                      ng-model="listScale"
                      control="listScaleCombo"
                      items-source="_getComboData('PRListScaleBox')"
                      display-member-path="name"
                      selected-value-path="value"
                      is-editable="false"
                      initialized="_initComboBox(s)">
                  </wj-combo-box>
                  <%--// 페이지 스케일  --%>

                  <ul class="txtSty3">
                    <li class="red fl"><s:message code="product.comment.txt3"/></li>
                  </ul>

                  <%-- 저장 --%>
                  <button type="button" class="btn_skyblue ml5" id="btnRegSave" ng-click="saveProductRegist()">
                    <s:message code="cmm.save"/>
                  </button>
                </div>
            </div>

            <div class="w100 mt10 mb20">
                <%--위즈모 테이블--%>
                <div class="wj-gridWrap" style="height: 400px; overflow-x: hidden; overflow-y: hidden;">
                  <wj-flex-grid
                    autoGenerateColumns="false"
                    selection-mode="Row"
                    items-source="data"
                    control="flex"
                    initialized="initGrid(s,e)"
                    is-read-only="false"
                    item-formatter="_itemFormatter"
                    ime-enabled="true">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="30" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="product.prodCd"/>" binding="prodCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="product.prodNm"/>" binding="prodNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="product.barcdCd"/>" binding="barcdCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="product.saleUprc"/>" binding="saleUprc" width="60" align="right" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="product.costUprc"/>" binding="costUprc" width="55" align="right" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="product.poUnitQty"/>" binding="poUnitQty" width="55" align="right" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="product.currQty"/>" binding="currQty" width="60" align="right" is-read-only="true"></wj-flex-grid-column>

                    <wj-flex-grid-column header="<s:message code="product.productWeight"/>" binding="productWeight" width="75" align="right" max-length=8 data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="product.productSaleUprc"/>" binding="productSaleUprc" width="75" align="right" max-length=8 data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="product.productQty"/>" binding="productQty" width="75" align="right" max-length=8 data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="product.productAmt"/>" binding="productAmt" width="75" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="product.remark"/>" binding="remark" width="200" align="left" max-length=300></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="product.prodStatus"/>" binding="productProdStatus" width="0" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>

                    <wj-flex-grid-column header="<s:message code="product.productWeight"/>" binding="orgProductWeight" width="75" align="right" max-length=8 data-type="Number" format="n0" aggregate="Sum" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="product.productSaleUprc"/>" binding="orgProductSaleUprc" width="75" align="right" max-length=8 data-type="Number" format="n0" aggregate="Sum" visible="false"></wj-flex-grid-column>

                  </wj-flex-grid>
                </div>
                <%--//위즈모 테이블--%>
            </div>

            <%-- 페이지 리스트 --%>
              <div class="pageNum mt10">
                <%-- id --%>
                <ul id="productRegistCtrlPager" data-size="10">
                </ul>
              </div>
            <%--//페이지 리스트--%>

        </div>

        <input type="hidden" id="hdProductDate" />
        <input type="hidden" id="hdProductFg" />
        <input type="hidden" id="hdSeqNo" />

    </div>
</wj-popup>

<script type="text/javascript">
    var orgnFg = "${orgnFg}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/stock/product/product/productRegist.js?ver=20220706.02" charset="utf-8"></script>

<%-- 상품분류 팝업 --%>
<c:import url="/WEB-INF/view/application/layer/searchProdClassCd.jsp">
</c:import>

<%-- 생산등록 업로드 실패내역 레이어 --%>
<c:import url="/WEB-INF/view/stock/product/product/productErrInfo.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>