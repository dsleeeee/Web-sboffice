<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}"/>
<c:set var="storeCd" value="${sessionScope.sessionInfo.storeCd}"/>

<%-- 팝업 부분 설정 - width 는 강제 해주어야함.. 해결방법? 확인 필요 : 20180829 노현수 --%>
<wj-popup control="prodModifyLayer" show-trigger="Click" hide-trigger="Click" style="display: none;width:800px;">
  <div class="wj-dialog wj-dialog-columns" ng-controller="prodModifyCtrl">
  <form id="myForm" name="myForm">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="prod.layer.info"/>
      <a href="#" class="wj-hide btn_close"></a>
    </div>
    <div class="wj-dialog-body sc2" style="height: 600px;">
      <h3 class="h3_tbl brt"><s:message code="prod.title.basicInfo"/></h3>
        <div class="tblBr">
          <table class="tblType01">
            <colgroup>
              <col class="w15"/>
              <col class="w35"/>
              <col class="w15"/>
              <col class="w35"/>
            </colgroup>
            <tbody>
            <tr>
              <%-- 상품이미지 //TODO --%>
              <th rowspan="4"><s:message code="prod"/><br/><s:message code="image"/>
                <br/>
              </th>
              <td rowspan="4">
                <%--등록한 상품이 없는 경우--%>
                <span class="goodsNo" id="goodsNo"><s:message code="image"/> 등록 준비중 입니다</span>
                <%--등록한 상품이 있는 경우--%>
                <%--<span class="goodsYes"><img src="img/sample.jpg" alt="" /></span>--%>
                <span class="goodsYes" id="goodsYes"><img id="imgProdImage" /></span>
                <%--첨부파일--%>
                <input type="file" id="file" name="file" accept="image/x-png, .jpg" onchange="angular.element(this).scope().changeProdImage(this)"/>
                <%--삭제--%>
                <a id="btnDelProdImage" href="#" class="btn_grayS mt5 fr" ng-click="delProdImage()"><s:message code="cmm.delete" /></a>
               </td>
               <%-- 브랜드명 --%>
               <th <c:if test="${brandUseFg == '0'}"> style="display: none;" </c:if> ><s:message code="prod.brandNm"/></th>
               <td <c:if test="${brandUseFg == '0'}"> style="display: none;" </c:if>>
                  <div class="sb-select">
                    <wj-combo-box
                          id="hqBrandCd"
                          ng-model="prodModifyInfo.hqBrandCd"
                          items-source="_getComboData('hqBrandCd')"
                          display-member-path="name"
                          selected-value-path="value"
                          is-editable="false"
                          control="hqBrandCdCombo">
                    </wj-combo-box>
                  </div>
               </td>
            </tr>
            <tr>
              <%--상품유형 //TODO --%>
              <th><s:message code="prod.prodTypeFg"/></th>
              <td>
                <div class="sb-select">
                  <wj-combo-box id="_prodTypeFg" name="prodTypeFg"
                                ng-model="prodModifyInfo.prodTypeFg"
                                items-source="_getComboData('prodTypeFgComboData')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)"
                                required
                                popover-enable="myForm.prodTypeFg.$invalid"
                                popover-placement="bottom-left"
                                popover-trigger="'mouseenter'"
                                uib-popover="<s:message code="prod.prodTypeFg" />은(는) 필수 입력항목 입니다.">
                  </wj-combo-box>
                </div>
              </td>
            </tr>
            <tr>
              <%--상품코드--%>
              <th><s:message code="prod.prodCd"/></th>
              <td>
                <input type="text" id="prodCd" numberAlphabet name="prodCd" class="sb-input w100"
                       ng-model="prodModifyInfo.prodCd"
                       readonly="readonly"
                        <c:if test="${prodNoEnvFg == 'MANUAL'}">
                         required
                         popover-enable="myForm.prodCd.$invalid"
                         popover-placement="bottom-left"
                         popover-trigger="'mouseenter'"
                         uib-popover="<s:message code="prod.prodCd" />은(는) 필수 입력항목 입니다."
                        </c:if>
                /><%--disabled--%>
                <input type="hidden" id="prodCdChkFg" name="prodCdChkFg" ng-model="prodModifyInfo.prodCdChkFg"/>
                <input type="hidden" id="prodCdInputType" name="prodCdInputType" ng-model="prodModifyInfo.prodCdInputType"/>
                <a id="btnChkProdCd" href="#" class="btn_grayS ml5" ng-click="chkProdCd()" style="display: none;"><s:message code="prod.chk.duplicate" /></a><Br />

                <%-- TODO 코너코드 어떻게 할것인가? --%>
                <input type="hidden" name="cornrCd" ng-model="prodModifyInfo.cornrCd"   >
              </td>
            </tr>
            <tr>
              <%--상품명--%>
              <th><s:message code="prod.prodNm"/></th>
              <td>
                <input type="text" id="_prodNm" name="prodNm" class="sb-input w100"
                       ng-model="prodModifyInfo.prodNm"
                       required
                       popover-enable="myForm.prodNm.$invalid"
                       popover-placement="bottom-left"
                       popover-trigger="'mouseenter'"
                       uib-popover="<s:message code="prod.prodNm" />은(는) 필수 입력항목 입니다."/>
              </td>
            </tr>
            <tr>
              <%--분류조회--%>
              <th><s:message code="prod.prodClass"/></th>
              <td>
                <input type="text" id="_prodClassCdNm" name="prodClassCdNm" class="sb-input w100"
                       style="background-color: #f0f0f0"
                       ng-model="prodModifyInfo.prodClassCdNm"
                       required
                       popover-enable="myForm.prodClassCdNm.$invalid"
                       popover-placement="bottom-left"
                       popover-trigger="'mouseenter'"
                       uib-popover="<s:message code="prod.prodClass" />은(는) 필수 입력항목 입니다."
                       placeholder="<s:message code="prod.prodClass" /> 선택" <%--readonly--%>
                       readonly
                       ng-click="popUpProdClass()"  />
                <input type="hidden" id="_prodClassCd" name="prodClassCd" class="sb-input w100" ng-model="prodModifyInfo.prodClassCd" disabled />
              </td>
              <%--거래처 --%>
              <th><s:message code="prod.vendr"/></th>
              <td>
                <input type="text" class="sb-input w70" id="srchVendrNm" ng-model="prodModifyInfo.vendrNm" ng-click="popUpVendrCd()" style="float: left;"
                       placeholder="<s:message code="prod.vendr" /> 선택" readonly/>
                <input type="hidden" id="_vendrCd" name="vendrCd" ng-model="prodModifyInfo.vendrCd" disabled />
              </td>
            </tr>
            <tr>
              <%--판매상품여부--%>
              <th><s:message code="prod.saleProdYn"/></th>
              <td>
                <div class="sb-select">
                  <wj-combo-box id="_saleProdYn" name="saleProdYn"
                    ng-model="prodModifyInfo.saleProdYn"
                    items-source="_getComboData('saleProdYnComboData')"
                    display-member-path="name"
                    selected-value-path="value"
                    is-editable="false"
                    initialized="_initComboBox(s)"
                    required
                    popover-enable="myForm.saleProdYn.$invalid"
                    popover-placement="bottom-left"
                    popover-trigger="'mouseenter'"
                    uib-popover="<s:message code="prod.saleProdYn" />은(는) 필수 입력항목 입니다.">
                  </wj-combo-box>
                </div>
              </td>
              <%--원산지--%>
              <th><s:message code="prod.orgplceCd"/></th>
              <td>
                <input type="text" id="_orgplceCd" name="orgplceCd" class="sb-input w100"
                       ng-model="prodModifyInfo.orgplceCd" placeholder="<s:message code="prod.orgplceCd"/> 등록은 준비중입니다." disabled />
              </td>
            </tr>
            <tr>
              <%--판매단가--%>
              <th><s:message code="prod.saleUprc"/></th>
              <td>
                <input type="text" maxlength="10" numberOnly id="prodModifySaleUprc" name="saleUprc" class="sb-input w100"
                       ng-model="prodModifyInfo.saleUprc"
                       required
                       popover-enable="myForm.saleUprc.$invalid"
                       popover-placement="bottom-left"
                       popover-trigger="'mouseenter'"
                       uib-popover="<s:message code="prod.saleUprc" />은(는) 필수 입력항목 입니다."/>
              </td>
                <%-- 봉사료 포함 여부 --%>
                <th><s:message code="prod.prodTipYn"/></th>
                <td>
                  <div class="sb-select">
                    <wj-combo-box id="_prodTipYn" name="prodTipYn"
                                  ng-model="prodModifyInfo.prodTipYn"
                                  items-source="_getComboData('prodTipYnComboData')"
                                  display-member-path="name"
                                  selected-value-path="value"
                                  is-editable="false"
                                  initialized="_initComboBox(s)"
                                  required
                                  popover-enable="myForm.prodTipYn.$invalid"
                                  popover-placement="bottom-left"
                                  popover-trigger="'mouseenter'"
                                  uib-popover="<s:message code="prod.prodTipYn" />은(는) 필수 입력항목 입니다.">
                    </wj-combo-box>
                  </div>
                </td>
            </tr>
            <tr>
              <%--과세여부--%>
              <th><s:message code="prod.vatFg"/></th>
              <td>
                <div class="sb-select">
                  <wj-combo-box id="_vatFg" name="vatFg"
                                ng-model="prodModifyInfo.vatFg"
                                items-source="_getComboData('vatFgComboData')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)"
                                required
                                popover-enable="myForm.vatFg.$invalid"
                                popover-placement="bottom-left"
                                popover-trigger="'mouseenter'"
                                uib-popover="<s:message code="prod.vatFg" />은(는) 필수 입력항목 입니다.">
                  </wj-combo-box>
                </div>
              </td>
                <%--사용여부--%>
                <th><s:message code="cmm.useYn"/></th>
                <td>
                  <div class="sb-select">
                    <wj-combo-box id="_useYn" name="useYn"
                                  ng-model="prodModifyInfo.useYn"
                                  items-source="_getComboData('useYnComboData')"
                                  display-member-path="name"
                                  selected-value-path="value"
                                  is-editable="false"
                                  initialized="_initComboBox(s)"
                                  required
                                  popover-enable="myForm.useYn.$invalid"
                                  popover-placement="bottom-left"
                                  popover-trigger="'mouseenter'"
                                  uib-popover="<s:message code="cmm.useYn" />은(는) 필수 입력항목 입니다.">
                    </wj-combo-box>
                  </div>
                </td>
            </tr>
            <tr>
                <%-- 바코드 --%>
                <th>
                    <s:message code="prod.barCd"/>
                </th>
                <td>
                    <input type="text" id="_barCd" numberAlphabet name="barCd" class="sb-input" style="width: 150px" ng-model="prodModifyInfo.barCd"/>
                    <button type="button"  class="btn_skyblue fr w30" ng-click="barCdAutoSet()"><s:message code="prod.autoSet"/></button>
                </td>
                <%-- 가격관리구분 --%>
                <th>
                    <s:message code="prod.prcCtrlFg"/>
                </th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box id="_prcCtrlFg" name="prcCtrlFg"
                                      ng-model="prodModifyInfo.prcCtrlFg"
                                      items-source="_getComboData('prcCtrlFgComboData')"
                                      display-member-path="name"
                                      selected-value-path="value"
                                      is-editable="false"
                                      initialized="_initComboBox(s)"
                                      required
                                      popover-enable="myForm.prcCtrlFg.$invalid"
                                      popover-placement="bottom-left"
                                      popover-trigger="'mouseenter'"
                                      uib-popover="<s:message code="prod.prcCtrlFg" />은(는) 필수 입력항목 입니다.">
                        </wj-combo-box>
                    </div>
                </td>
            </tr>
          </table>
        </div>
      <%-- 내점/배달/포장 가격 --%>
      <c:if test="${subPriceFg == '1'}">
        <h3 class="h3_tbl"><s:message code="prod.title.saleUprc"/>&nbsp;&nbsp;<input type="checkbox" id="saleUprcApply" ng-model="saleUprcApply" />&nbsp;<s:message code="prod.saleUprcApply"/></h3>
        <div class="tblBr">
          <table class="tblType01">
            <colgroup>
              <col class="w15"/>
              <col class="w35"/>
              <col class="w15"/>
              <col class="w35"/>
            </colgroup>
            <tbody>
            <tr>
                <%--내점가--%>
              <th><s:message code="prod.stinSaleUprc"/></th>
              <td>
                <input type="text" maxlength="10" numberOnly id="stinSaleUprc" name="stinSaleUprc" ng-model="prodModifyInfo.stinSaleUprc" class="sb-input w100">
              </td>
                <%--포장가--%>
              <th><s:message code="prod.packSaleUprc"/></th>
              <td>
                <input type="text" maxlength="10" numberOnly id="packSaleUprc" name="packSaleUprc" ng-model="prodModifyInfo.packSaleUprc" class="sb-input w100">
              </td>
            </tr>
            <tr>
                <%--배달가--%>
              <th><s:message code="prod.dlvrSaleUprc"/></th>
              <td>
                <input type="text" maxlength="10" numberOnly id="dlvrSaleUprc" name="dlvrSaleUprc" ng-model="prodModifyInfo.dlvrSaleUprc" class="sb-input w100">
              </td>
              <th></th>
              <td></td>
            </tr>
            </tbody>
          </table>
        </div>
      </c:if>
      <%-- 상품부가정보 --%>
      <h3 class="h3_tbl"><s:message code="prod.title.addInfo"/></h3>
      <div class="tblBr">
        <table class="tblType01">
          <colgroup>
            <col class="w15"/>
            <col class="w35"/>
            <col class="w15"/>
            <col class="w35"/>
          </colgroup>
          <tbody>
            <tr>
              <%--재고관리여부--%>
              <th><s:message code="prod.stockProdYn"/></th>
              <td>
                <div class="sb-select">
                  <wj-combo-box id="_stockProdYn" name="stockProdYn"
                                ng-model="prodModifyInfo.stockProdYn"
                                items-source="_getComboData('useYnComboData')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)"
                                required
                                popover-enable="myForm.stockProdYn.$invalid"
                                popover-placement="bottom-left"
                                popover-trigger="'mouseenter'"
                                uib-popover="<s:message code="prod.stockProdYn" />은(는) 필수 입력항목 입니다.">
                  </wj-combo-box>
                </div>
              </td>
              <%--저장품코드 //TODO --%>
              <th><s:message code="prod.saveProdCd"/></th>
              <td>
                <%--<a href="#" class="link" id="_saveProdCd"></a>--%>
                <s:message code="prod.saveProdCd"/> 준비중 입니다.
              </td>
            </tr>
            <tr>
              <%--세트상품구분//TODO --%>
              <th><s:message code="prod.setProdFg"/></th>
              <td>
                <div class="sb-select">
                  <wj-combo-box id="_setProdFg" name="setProdFg"
                                ng-model="prodModifyInfo.setProdFg"
                                items-source="_getComboData('setProdFgComboData')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)"
                                required
                                popover-enable="myForm.setProdFg.$invalid"
                                popover-placement="bottom-left"
                                popover-trigger="'mouseenter'"
                                uib-popover="<s:message code="prod.setProdFg" />은(는) 필수 입력항목 입니다."
                                selected-index-changed="setProdFgSelected(s)">
                  </wj-combo-box>
                </div>
              </td>
              <td>
                <a href="#" id="btnSetConfigProd" class="btn_grayS" ng-click="setConfigProd()" style="display: none;"><s:message code="prod.configProd"/></a>
              </td>
              <td></td>
            </tr>
            <tr>
              <%--포인트적립여부--%>
              <th><s:message code="prod.pointSaveYn"/></th>
              <td>
                <div class="sb-select">
                  <wj-combo-box id="_pointSaveYn" name="pointSaveYn"
                                ng-model="prodModifyInfo.pointSaveYn"
                                items-source="_getComboData('useYnComboData')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)"
                                required
                                popover-enable="myForm.pointSaveYn.$invalid"
                                popover-placement="bottom-left"
                                popover-trigger="'mouseenter'"
                                uib-popover="<s:message code="prod.pointSaveYn" />은(는) 필수 입력항목 입니다.">
                  </wj-combo-box>
                </div>
              </td>
              <%--사이드사용여부--%>
              <th><s:message code="prod.sideProdYn"/></th>
              <td>
                <div class="sb-select">
                  <wj-combo-box id="_sideProdYn" name="sideProdYn"
                                ng-model="prodModifyInfo.sideProdYn"
                                items-source="_getComboData('useYnComboData')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)"
                                required
                                popover-enable="myForm.sideProdYn.$invalid"
                                popover-placement="bottom-left"
                                popover-trigger="'mouseenter'"
                                uib-popover="<s:message code="prod.sideProdYn" />은(는) 필수 입력항목 입니다."
                                selected-index="1">
                  </wj-combo-box>
                </div>
              </td>
              <%--품절여부 //TODO --%>
              <th style="display: none">
                <s:message code="prod.soldOutYn"/>
              </th>
              <td style="display: none">
                <div class="sb-select">
                  <wj-combo-box id="_soldOutYn" name="soldOutYn"
                                ng-model="prodModifyInfo.soldOutYn"
                                items-source="_getComboData('soldOutYnComboData')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)"
                                required
                                popover-enable="myForm.soldOutYn.$invalid"
                                popover-placement="bottom-left"
                                popover-trigger="'mouseenter'"
                                uib-popover="<s:message code="prod.soldOutYn" />은(는) 필수 입력항목 입니다.">
                  </wj-combo-box>
                </div>
              </td>
              <td></td>
              <td></td>
            </tr>
            <tr ng-if="prodModifyInfo.sideProdYn === 'Y'">
              <%--사이드속성분류코드(속성) --%>
              <th><s:message code="prod.sdattrClassCd"/></th>
              <td>
                <div class="sb-select">
                  <wj-combo-box id="_sdattrClassCd" name="sdattrClassCd"
                                ng-model="prodModifyInfo.sdattrClassCd"
                                items-source="_getComboData('sdattrClassCdComboData')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)">
                  </wj-combo-box>
                </div>
              </td>
              <%--사이드선택그룹코드(선택메뉴) --%>
              <th><s:message code="prod.sdselGrpCd"/></th>
              <td>
                <%--<div class="sb-select">
                  <wj-combo-box id="_sdselGrpCd" name="sdselGrpCd"
                                ng-model="prodModifyInfo.sdselGrpCd"
                                items-source="_getComboData('sdselGrpCdComboData')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)">
                  </wj-combo-box>
                </div>--%>
                  <input type="text" class="sb-input w70" id="srchSdselGrpNm" ng-model="prodModifyInfo.sdselGrpNm" ng-click="popUpSdselGrp()" style="float: left;"
                         placeholder="<s:message code="prod.sdselGrpCd" /> 선택" readonly/>
                  <input type="hidden" id="_sdselGrpCd" name="sdselGrpCd" ng-model="prodModifyInfo.sdselGrpCd" disabled />
              </td>
            </tr>
            <tr>
              <!-- 보증금상품유형 -->
              <th><s:message code="prod.depositCupFg"/></th>
              <td>
                <div class="sb-select">
                  <wj-combo-box id="_depositCupFg" name="depositCupFg"
                                ng-model="prodModifyInfo.depositCupFg"
                                items-source="_getComboData('depositCupFgComboData')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)">
                  </wj-combo-box>
                </div>
              </td>
              <!-- 포인트사용여부 -->
              <th><s:message code="prod.pointUseYn"/></th>
              <td>
                <div class="sb-select">
                  <wj-combo-box id="_depositCupFg" name="pointUseYn"
                                ng-model="prodModifyInfo.pointUseYn"
                                items-source="_getComboData('useYnComboData')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)">
                  </wj-combo-box>
                </div>
              </td>
            </tr>
            <tr>
              <!-- 할인여부 -->
              <th><s:message code="prod.dcYn"/></th>
              <td>
                <div class="sb-select">
                  <wj-combo-box id="_depositCupFg" name="dcYn"
                                ng-model="prodModifyInfo.dcYn"
                                items-source="_getComboData('useYnComboData')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)">
                  </wj-combo-box>
                </div>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
      <%-- 발주정보 --%>
      <h3 class="h3_tbl"><s:message code="prod.title.orderInfo"/></h3>
      <div class="tblBr">
        <table class="tblType01">
          <colgroup>
            <col class="w15"/>
            <col class="w35"/>
            <col class="w15"/>
            <col class="w35"/>
          </colgroup>
          <tbody>
            <tr <c:if test="${momsEnvstVal == '1' && orgnFg == 'STORE'}">style="display: none"</c:if>> <%-- 맘스터치 매장은 공금단가와 원가단가를 hidden 처리 --%>
              <%--공급단가--%>
              <th><s:message code="prod.splyUprc"/></th>
              <td>
                <input type="text" maxlength="9" numberOnly id="prodModifySplyUprc" name="splyUprc" class="sb-input w100"
                       ng-model="prodModifyInfo.splyUprc"
                       required
                       popover-enable="myForm.splyUprc.$invalid"
                       popover-placement="bottom-left"
                       popover-trigger="'mouseenter'"
                       uib-popover="<s:message code="prod.splyUprc" />은(는) 필수 입력항목 입니다."/>
              <%--공급단가사용여부--%>
              <th style="display: none">
                <s:message code="prod.splyUprc"/><br><s:message code="cmm.useYn"/>
              </th>
              <td style="display: none">
                <div class="sb-select">
                  <wj-combo-box id="_splyUprcUseYn" name="splyUprcUseYn"
                    ng-model="prodModifyInfo.splyUprcUseYn"
                    items-source="_getComboData('useYnComboData')"
                    display-member-path="name"
                    selected-value-path="value"
                    is-editable="false"
                    initialized="_initComboBox(s)"
                    required
                    popover-enable="myForm.splyUprcUseYn.$invalid"
                    popover-placement="bottom-left"
                    popover-trigger="'mouseenter'"
                    uib-popover="<s:message code="prod.splyUprcUseYn" />은(는) 필수 입력항목 입니다.">
                  </wj-combo-box>
                </div>
              </td>
              <%--원가단가--%>
              <th><s:message code="prod.costUprc"/></th>
              <td>
                <input type="text" maxlength="9" numberOnly id="prodModifyCostUprc" name="costUprc" class="sb-input w100"
                       ng-model="prodModifyInfo.costUprc"
                       required
                       popover-enable="myForm.costUprc.$invalid"
                       popover-placement="bottom-left"
                       popover-trigger="'mouseenter'"
                       uib-popover="<s:message code="prod.costUprc" />은(는) 필수 입력항목 입니다."/>
              </td>
              <%--최종원가단가--%>
              <th style="display: none">
                <s:message code="prod.lastCostUprc"/>
              </th>
              <td style="display: none">
                <input type="text" id="prodModifyLastCostUprc" name="lastCostUprc" class="sb-input w100"
                       ng-model="prodModifyInfo.lastCostUprc"
                       required
                       popover-enable="myForm.lastCostUprc.$invalid"
                       popover-placement="bottom-left"
                       popover-trigger="'mouseenter'"
                       uib-popover="<s:message code="prod.lastCostUprc" />은(는) 필수 입력항목 입니다."/>
              </td>
            </tr>
            <tr>
              <%--발주상품구분--%>
              <th><s:message code="prod.poProdFg"/></th>
              <td>
                <div class="sb-select">
                  <wj-combo-box id="_poProdFg" name="poProdFg"
                    ng-model="prodModifyInfo.poProdFg"
                    items-source="_getComboData('_poProdFg')"
                    display-member-path="name"
                    selected-value-path="value"
                    is-editable="false"
                    initialized="_initComboBox(s)"
                    required
                    popover-enable="myForm.poProdFg.$invalid"
                    popover-placement="bottom-left"
                    popover-trigger="'mouseenter'"
                    uib-popover="<s:message code="prod.poProdFg" />은(는) 필수 입력항목 입니다.">
                  </wj-combo-box>
                </div>
              </td>
              <%--발주단위--%>
              <th><s:message code="prod.poUnitFg"/></th>
              <td>
                <div class="sb-select">
                  <wj-combo-box id="_poUnitFg" name="poUnitFg"
                                ng-model="prodModifyInfo.poUnitFg"
                                items-source="_getComboData('poUnitFgComboData')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)"
                                required
                                popover-enable="myForm.poUnitFg.$invalid"
                                popover-placement="bottom-left"
                                popover-trigger="'mouseenter'"
                                uib-popover="<s:message code="prod.poUnitFg" />은(는) 필수 입력항목 입니다.">
                  </wj-combo-box>
                </div>
              </td>
            </tr>
            <tr>
              <%--발주단위수량--%>
              <th><s:message code="prod.poUnitQty"/></th>
              <td>
                <input type="text" numberOnly id="prodModifyPoUnitQty" name="poUnitQty" class="sb-input w100"
                       ng-model="prodModifyInfo.poUnitQty"
                       required
                       popover-enable="myForm.poUnitQty.$invalid"
                       popover-placement="bottom-left"
                       popover-trigger="'mouseenter'"
                       uib-popover="<s:message code="prod.poUnitQty" />은(는) 필수 입력항목 입니다."/>
              </td>
              <%--최소발주수량--%>
              <th><s:message code="prod.poMinQty"/></th>
              <td>
                <input type="text" numberOnly id="prodModifyPoMinQty" name="poMinQty" class="sb-input w100"
                       ng-model="prodModifyInfo.poMinQty"
                       required
                       popover-enable="myForm.poMinQty.$invalid"
                       popover-placement="bottom-left"
                       popover-trigger="'mouseenter'"
                       uib-popover="<s:message code="prod.poMinQty" />은(는) 필수 입력항목 입니다."/>
              </td>
            </tr>
            <tr>
              <%--안전재고--%>
              <th><s:message code="prod.safeStockQty"/></th>
              <td>
                <input type="text" numberOnly id="prodModifySafeStockQty" name="safeStockQty" class="sb-input w100"
                       ng-model="prodModifyInfo.safeStockQty"
                       required
                       popover-enable="myForm.safeStockQty.$invalid"
                       popover-placement="bottom-left"
                       popover-trigger="'mouseenter'"
                       uib-popover="<s:message code="prod.safeStockQty" />은(는) 필수 입력항목 입니다."/>
              </td>
              <%--초기재고--%>
              <th id="thStartStockQty">
                <s:message code="prod.startStockQty"/>
              </th>
              <td id="tdStartStockQty">
                <%--defaultStock--%>
                <input type="text" numberOnly id="prodModifyStartStockQty" name="startStockQty" class="sb-input w100"
                       ng-model="prodModifyInfo.startStockQty"
                       required
                       popover-enable="myForm.defaultStock.$invalid"
                       popover-placement="bottom-left"
                       popover-trigger="'mouseenter'"
                       uib-popover="<s:message code="prod.startStockQty" />은(는) 필수 입력항목 입니다."/>
              </td>
              <td id="thStartStockQtyNo"></td>
              <td id="tdStartStockQtyNo"></td>
            </tr>
          </tbody>
        </table>
      </div>
      <%-- [1250 맘스터치] --%>
      <c:if test="${momsEnvstVal == '1'}">
        <%-- 영양정보 --%>
        <h3 class="h3_tbl"><s:message code="prod.title.nuInfo"/></h3>
        <div class="tblBr">
          <table class="tblType01">
            <colgroup>
              <col class="w15" />
              <col class="w35" />
              <col class="w15" />
              <col class="w35" />
            </colgroup>
            <tbody>
            <tr>
              <%-- 총중량 --%>
              <th><s:message code="prod.nuTotWt"/></th>
              <td>
                <div class="sb-select w80 fl">
                  <input type="text" id="_nuTotWt" name="nuTotWt" class="sb-input w100" ng-model="prodModifyInfo.nuTotWt"/>
                </div>
                <div class="sb-select w20 fr">
                    (g)
                </div>
              </td>
              <%-- 총열량 --%>
              <th><s:message code="prod.nuKcal"/></th>
              <td>
                <div class="sb-select w80 fl">
                  <input type="text" id="_nuKcal" name="nuKcal" class="sb-input w100" ng-model="prodModifyInfo.nuKcal"/>
                </div>
                <div class="sb-select w20 fr">
                  (kcal)
                </div>
              </td>
            </tr>
            <tr>
              <%-- 단백질 --%>
              <th><s:message code="prod.nuProtein"/></th>
              <td>
                <div class="sb-select w80 fl">
                  <input type="text" id="_nuProtein" name="nuProtein" class="sb-input w100" ng-model="prodModifyInfo.nuProtein"/>
                </div>
                <div class="sb-select w20 fr">
                  (g)
                </div>
              </td>
              <%-- 나트륨 --%>
              <th><s:message code="prod.nuSodium"/></th>
              <td>
                <div class="sb-select w80 fl">
                  <input type="text" id="_nuSodium" name="nuSodium" class="sb-input w100" ng-model="prodModifyInfo.nuSodium"/>
                </div>
                <div class="sb-select w20 fr">
                  (mg)
                </div>
              </td>
            </tr>
            <tr>
              <%-- 당류 --%>
              <th><s:message code="prod.nuSugars"/></th>
              <td>
                <div class="sb-select w80 fl">
                  <input type="text" id="_nuSugars" name="nuSugars" class="sb-input w100" ng-model="prodModifyInfo.nuSugars"/>
                </div>
                <div class="sb-select w20 fr">
                  (g)
                </div>
              </td>
              <%-- 포화지방 --%>
              <th><s:message code="prod.nuSatFat"/></th>
              <td>
                <div class="sb-select w80 fl">
                  <input type="text" id="_nuSatFat" name="nuSatFat" class="sb-input w100" ng-model="prodModifyInfo.nuSatFat"/>
                </div>
                <div class="sb-select w20 fr">
                  (g)
                </div>
              </td>
            </tr>
            <tr>
              <%-- 카페인 --%>
              <th><s:message code="prod.nuCaffeine"/></th>
              <td>
                <div class="sb-select w80 fl">
                  <input type="text" id="_nuCaffeine" name="nuCaffeine" class="sb-input w100" ng-model="prodModifyInfo.nuCaffeine"/>
                </div>
              </td>
              <td></td>
              <td></td>
            </tr>
            </tbody>
          </table>
        </div>
      </c:if>
      <%-- 기타정보 --%>
      <h3 class="h3_tbl"><s:message code="prod.title.etcInfo"/></h3>
      <div class="tblBr">
        <table class="tblType01">
          <colgroup>
            <col class="w15" />
            <col class="w35" />
            <col class="w15" />
            <col class="w35" />
          </colgroup>
          <tbody>
          <tr>
            <%-- 매핑상품코드 --%>
            <th><s:message code="prod.mapProdCd"/></th>
            <td colspan="3">
              <input type="text" id="_mapProdCd" name="mapProdCd" class="sb-input w100" ng-model="prodModifyInfo.mapProdCd"/>
            </td>
          </tr>
          <%-- [1250 맘스터치] --%>
          <c:if test="${momsEnvstVal == '1'}">
            <tr>
              <%-- KIOSK 판매시간 --%>
              <th><s:message code="prod.kioskSaleTime"/></th>
              <td>
                  <div class="sb-select">
                    <wj-combo-box
                          id="_saleTimeFg"
                          name="saleTimeFg"
                          ng-model="prodModifyInfo.saleTimeFg"
                          items-source="_getComboData('useYnComboData')"
                          display-member-path="name"
                          selected-value-path="value"
                          is-editable="false"
                          initialized="_initComboBox(s)"
                          control="saleTimeFgCombo"
                          selected-index="1">
                    </wj-combo-box>
                  </div>
              </td>
              <%-- KIOSK 시간설정 --%>
              <th><s:message code="prod.kioskSaleTimeSetting"/></th>
              <td id="dataKioskTime">
                 <%--<input id="P_KIOSK_TIME" name="P_KIOSK_TIME" type="hidden">--%>
                 <div class="divDataKioskTime">
                   <input class="inputKioskTimeStart" type="text" style="width: 55px;" onfocus="this.style.background='#FFE08C';" onblur="this.style.background='';" onkeyup="onKeyupKioskTime(this)"> ~
                   <input class="inputKioskTimeEnd" type="text" style="width: 55px;" onfocus="this.style.background='#FFE08C';" onblur="this.style.background='';" onkeyup="onKeyupKioskTime(this)">
                   <button input type="button" class="btn_skyblue" id="btnKioskTimeAdd" onclick="addNewKioskTimeHtml()">＋</button>
                 </div>
              </td>
            </tr>
            <tr>
              <%-- KIOSK 엣지 --%>
              <th><s:message code="prod.momsKioskEdge"/></th>
              <td>
                  <div class="sb-select">
                      <wj-combo-box
                              id="_momsKioskEdge"
                              name="momsKioskEdge"
                              ng-model="prodModifyInfo.momsKioskEdge"
                              items-source="_getComboData('momsKioskEdgeComboData')"
                              display-member-path="name"
                              selected-value-path="value"
                              is-editable="false"
                              initialized="_initComboBox(s)"
                              control="momsKioskEdgeCombo">
                      </wj-combo-box>
                  </div>
              </td>
              <%-- 상품옵션그룹 --%>
              <th><s:message code="prod.optionGrp"/></th>
              <td>
                <input type="text" class="sb-input w70" id="_optionGrpNm" ng-model="prodModifyInfo.optionGrpNm" ng-click="popUpOptionGrp()" style="float: left; width: 69%;"
                       placeholder="<s:message code="prod.optionGrp" /> 선택" readonly/>
                <input type="hidden" id="_optionGrpCd" name="optionGrpCd" ng-model="prodModifyInfo.optionGrpCd" disabled />
                <button type="button" class="btn_skyblue fl mr5" id="btnCancelOptionGrp" style="margin-left: 5px;" ng-click="delOptionGrp()"><s:message code="cmm.selectCancel"/></button>
              </td>
            </tr>
            <tr>
              <%-- 출시일 --%>
              <th><s:message code="prod.releaseDate"/></th>
              <td>
                <div class="sb-select w200px">
                  <span class="txtIn"><input id="releaseDate" class="w110px"></span>
                </div>
              </td>
              <%-- 단종 --%>
              <th>
                  <div style="float: left;"><input type="checkbox" id="chkDisconYn" ng-model="isCheckedDisconYn" ng-change="isChkDisconYn()"/></div>
                  <div style="padding-top: 3px; padding-left: 20px;"><s:message code="prod.discon" /></div>
              </th>
              <td>
                <div class="sb-select w200px" id="divChkDiscon">
                   <span class="txtIn"><input id="disconDate" class="w110px"></span>
                </div>
              </td>
            </tr>
            <tr>
              <%-- 판매방식 --%>
              <th><s:message code="prod.saleType"/></th>
              <td>
                 <div class="fl pd5" style="padding-right: 15px;">
                   <div style="float: left;"><input type="checkbox" id="chkSaleTypeYnSin" ng-model="prodModifyInfo.saleTypeYnSin"/></div>
                   <div style="float: left; padding-top: 3px; padding-left:5px; padding-right:10px;"><label><s:message code="prod.inStore" /></label></div>
                   <div style="float: left;"><input type="checkbox" id="chkSaleTypeYnDlv" ng-model="prodModifyInfo.saleTypeYnDlv"/></div>
                   <div style="float: left; padding-top: 3px; padding-left:5px; padding-right:10px;"><label><s:message code="prod.delivery" /> </label></div>
                   <div style="float: left;"><input type="checkbox" id="chkSaleTypeYnPkg" ng-model="prodModifyInfo.saleTypeYnPkg"/></div>
                   <div style="float: left; padding-top: 3px; padding-left:5px; padding-right:10px;"><label><s:message code="prod.packing" /> </label></div>
                 </div>
              </td>
              <th></th>
              <td></td>
            </tr>
            <tr>
              <%-- 판매채널 --%>
              <th><s:message code="prod.saleChannel"/></th>
              <td colspan="3" style="height: 35px;">
                <div class="fl pd5" style="padding-right: 15px;">
                   <div style="float: left;"><input type="checkbox" id="chkSaleChnYnPos" ng-model="prodModifyInfo.saleChnYnPos"/></div>
                   <div style="float: left; padding-top: 3px; padding-left:5px; padding-right:10px;"><label><s:message code="prod.pos" /></label></div>
                   <div style="float: left;"><input type="checkbox" id="chkSaleChnYnKsk" ng-model="prodModifyInfo.saleChnYnKsk"/></div>
                   <div style="float: left; padding-top: 3px; padding-left:5px; padding-right:10px;"><label><s:message code="prod.kiosk" /></label></div>
                   <div style="float: left;"><input type="checkbox" id="chkSaleChnYnCmp" ng-model="prodModifyInfo.saleChnYnCmp"/></div>
                   <div style="float: left; padding-top: 3px; padding-left:5px; padding-right:10px;"><label><s:message code="prod.app" /></label></div>
                   <div style="float: left;"><input type="checkbox" id="chkSaleChnYnBae" ng-model="prodModifyInfo.saleChnYnBae"/></div>
                   <div style="float: left; padding-top: 3px; padding-left:5px; padding-right:10px;"><label><s:message code="prod.baemin" /></label></div>
                   <div style="float: left;"><input type="checkbox" id="chkSaleChnYnBao" ng-model="prodModifyInfo.saleChnYnBao"/></div>
                   <div style="float: left; padding-top: 3px; padding-left:5px; padding-right:10px;"><label><s:message code="prod.baemin1" /></label></div>
                   <div style="float: left;"><input type="checkbox" id="chkSaleChnYnYgy" ng-model="prodModifyInfo.saleChnYnYgy"/></div>
                   <div style="float: left; padding-top: 3px; padding-left:5px; padding-right:10px;"><label><s:message code="prod.yogiyo" /></label></div>
                   <div style="float: left;"><input type="checkbox" id="chkSaleChnYnYge" ng-model="prodModifyInfo.saleChnYnYge"/></div>
                   <div style="float: left; padding-top: 3px; padding-left:5px; padding-right:10px;"><label><s:message code="prod.yogiyoExp" /></label></div>
                </div>
                <div class="fl pd5" style="padding-right: 15px;">
                   <div style="float: left;"><input type="checkbox" id="chkSaleChnYnCpn" ng-model="prodModifyInfo.saleChnYnCpn"/></div>
                   <div style="float: left; padding-top: 3px; padding-left:5px; padding-right:10px;"><label><s:message code="prod.coupang" /></label></div>
                   <div style="float: left;"><input type="checkbox" id="chkSaleChnYnTng" ng-model="prodModifyInfo.saleChnYnTng"/></div>
                   <div style="float: left; padding-top: 3px; padding-left:5px; padding-right:10px;"><label><s:message code="prod.baedaltong" /></label></div>
                   <div style="float: left;"><input type="checkbox" id="chkSaleChnYnDdn" ng-model="prodModifyInfo.saleChnYnDdn"/></div>
                   <div style="float: left; padding-top: 3px; padding-left:5px; padding-right:10px;"><label><s:message code="prod.ddangyo" /></label></div>
                </div>
              </td>
            </tr>
          </c:if>
          </tbody>
        </table>
      </div>
      <%-- 비고 --%>
      <h3 class="h3_tbl"><s:message code="prod.title.remark"/></h3>
      <div class="tblBr">
        <table class="tblType01">
          <colgroup>
            <col class="w100"/>
          </colgroup>
          <tbody>
          <tr>
            <th class="gr lh20">
              <input type="text" id="_remark" name="remark" class="sb-input w100" ng-model="prodModifyInfo.remark"/>
            </th>
          </tr>
          </tbody>
        </table>
      </div>
      <%-- 상품 상세 설명 --%>
      <h3 class="h3_tbl"><s:message code="prod.title.info"/></h3>
      <div class="tblBr">
        <table class="tblType01">
          <colgroup>
            <col class="w100"/>
          </colgroup>
          <tbody>
          <tr>
            <th class="gr lh30">
<%--              <input type="text" id="_info" name="info" style="height:50px" class="sb-input w100" ng-model="prodModifyInfo.info"/>--%>
                  <textarea id="_info"  class="w100" cols="42" style="height:30px;resize: none;" ng-model="prodModifyInfo.prodInfo"></textarea>
            </th>
          </tr>
          </tbody>
        </table>
      </div>
      <%-- 할인/적립정보 --%>
      <h3 class="h3_tbl" ng-if="prodModifyInfo.pointSaveYn === 'Y'"><s:message code="prod.title.dcAndSaveInfo"/></h3>
      <div class="tblBr" ng-if="prodModifyInfo.pointSaveYn === 'Y'">
        <table class="tblType01">
          <colgroup>
            <col width="15%"/>
            <col width="85%"/>
          </colgroup>
          <tbody>
          <tr>
            <%--할인 //TODO --%>
            <th><s:message code="prod.dc"/></th>
            <td id="_dc">
              할인정보 준비중 입니다.
            </td>
          </tr>
          <tr>
            <%--적립 //TODO --%>
            <th><s:message code="prod.save"/></th>
            <td id="_save">
              적립정보 준비중 입니다.
            </td>
          </tr>
          </tbody>
        </table>
      </div>
      <%-- 연결상품정보 --%>
      <h3 class="h3_tbl"><s:message code="prod.title.unitstInfo"/></h3>
      <div class="tblBr">
        <table class="tblType01">
          <colgroup>
            <col class="w15"/>
            <col class="w85"/>
          </colgroup>
          <tbody id="_linkedProdInfo">
          <tr>
            <th class="gr lh20">
              연결상품정보 준비중 입니다.
            </th>
          </tr>
          </tbody>
        </table>
      </div>
    </div>
    <div class="wj-dialog-footer">
       <%--<button class="btn btn_blue" ng-click="myForm.$valid && saveProd()"><s:message code="cmm.save"/></button>--%>
       <button class="btn btn_blue" ng-click="sideCheck()"><s:message code="cmm.save"/></button>
      <button class="btn wj-hide btn_blue"><s:message code="cmm.close"/></button>
      <input type="hidden" id="saveMode" name="saveMode"/>
    </div>
    </form>
  </div>
</wj-popup>

<script type="text/javascript">
  var orgnFg = "${orgnFg}";
  var hqOfficeCd = "${hqOfficeCd}";
  var storeCd = "${storeCd}";
  var gubun = "${param.gubun}";
  var kitchenprintLink = "${param.kitchenprintLink}";

  var prodFgComboData2 = ${ccu.getCommCodeExcpAll("092")}; <%-- 주문상품구분 --%>
  if(orgnFg === "STORE"){
    prodFgComboData2.shift();
    prodFgComboData2.shift();
    prodFgComboData2.shift();
  }

  $(function(){
    $("input:text[numberOnly]").on("keyup", function() {
      $(this).val($(this).val().replace(/[^-|^0-9]/g,""));
    });
    $("input:text[numberAlphabet]").on("keyup", function() {
      $(this).val($(this).val().replace(/[^A-za-z0-9]/g,""));
    });
  });


  // KIOSK 시간설정 추가
  function addNewKioskTimeHtml() {
    var html = '';
    html += '<div class="divDataKioskTime">';
    html += '   <input class="inputKioskTimeStart" type=text style="width:55px" onfocus="this.style.background=\'#FFE08C\';" onblur="this.style.background=\'\';" onkeyup="onKeyupKioskTime(this)" > ~ ';
    html += '   <input class="inputKioskTimeEnd" type=text style="width:55px" onfocus="this.style.background=\'#FFE08C\';" onblur="this.style.background=\'\';" onkeyup="onKeyupKioskTime(this)" >';
    html += '   <button input type="button" class="btn_skyblue" id="btnKioskTimeRemove" onclick="removeTimeDiv(this)">－</button> ';
    html += '</div>';

    $("#dataKioskTime").append(html);
  }

  // KIOSK 시간설정 숫자만 입력되도록
  function onKeyupKioskTime(e) {
    if (!(event.keyCode >=37 && event.keyCode<=40)) {
       var inputVal = $(e).val();
       var tempVal = inputVal.replace(/[^0-9]/gi,'');
       $(e).val(getFormatKioskTime(tempVal));
    }
  }

  // KIOSK 시간설정 시 분 사이 ':' 추가
  function getFormatKioskTime(tempVal) {
    if (tempVal.length < 2) {
       return tempVal;
    } else if (tempVal.length == 2) {
       return tempVal + ":";
    } else {
       return tempVal.substring(0, 2) + ":" + tempVal.substring(2, 4);
    }
  }

  // KIOSK 시간설정 제거
  function removeTimeDiv(e) {
    $(e).parent().remove();
  }

  // KIOSK 시간설정 값 변수에 담기
  function getKioskTimeValue() {
    var returnStr = "";

    var timeDivs = $("#dataKioskTime").children(".divDataKioskTime");
    var cnt = 0;
    for (var i=0; i<timeDivs.length; i++) {
       var startTime = $($(timeDivs[i]).children(".inputKioskTimeStart")[0]).val().replace(':', '');
       var endTime = $($(timeDivs[i]).children(".inputKioskTimeEnd")[0]).val().replace(':', '');

       if (startTime.length == 4 && endTime.length == 4) {
          if (cnt != 0) returnStr += ",";
          returnStr += startTime + "-" + endTime;
          cnt++;
       }
    }

    return returnStr;
  }

  // KIOSK 시간설정 View에 셋팅
  function setKioskTimeValue(timeVal) {
    if (timeVal == null || timeVal == "") return;
    try {
       var timeArr = timeVal.split(",");
       for (var i=0; i<timeArr.length; i++) {
          var timeArrDetail = timeArr[i].split("-");
          var startTime = timeArrDetail[0];
          var endTime = timeArrDetail[1];

          if (i != 0) {
             addNewKioskTimeHtml();
          }
          var timeDivs = $("#dataKioskTime").children(".divDataKioskTime");
          $($(timeDivs[timeDivs.length-1]).children(".inputKioskTimeStart")[0]).val(getFormatKioskTime(startTime));
          $($(timeDivs[timeDivs.length-1]).children(".inputKioskTimeEnd")[0]).val(getFormatKioskTime(endTime));
       }
    } catch (e) {
       console.log(e);
    }
 }

  // KIOSK 시간설정 초기화
  function resetKioskTimeHtml() {
    var html = '';
    html += '<div class="divDataKioskTime">';
    html += '   <input class="inputKioskTimeStart" type=text style="width:55px" onfocus="this.style.background=\'#FFE08C\';" onblur="this.style.background=\'\';" onkeyup="onKeyupKioskTime(this)" > ~ ';
    html += '   <input class="inputKioskTimeEnd" type=text style="width:55px" onfocus="this.style.background=\'#FFE08C\';" onblur="this.style.background=\'\';" onkeyup="onKeyupKioskTime(this)" >';
    html += '   <button input type="button" class="btn_skyblue" id="btnKioskTimeAdd" onclick="addNewKioskTimeHtml()">＋</button> ';
    html += '</div>';

    $("#dataKioskTime").html(html);
  }

</script>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/prod/prodModifyView.js?ver=20221223.01" charset="utf-8"></script>

<%-- 상품분류 팝업 --%>
<c:import url="/WEB-INF/view/application/layer/searchProdClassCd.jsp">
</c:import>

<%-- 상품 거래처 조회 팝업 --%>
<c:import url="/WEB-INF/view/base/prod/prod/searchProdVendr.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 프린터연결 팝업 --%>
<c:import url="/WEB-INF/view/base/prod/prod/kitchenprintLink.jsp">
</c:import>

<%-- 상품그룹-프린터연결 팝업 --%>
<c:import url="/WEB-INF/view/base/prod/prod/printerGroupPopup.jsp">
</c:import>

<%-- 세트구성상품 팝업 --%>
<c:import url="/WEB-INF/view/base/prod/prod/setConfigProdRegist.jsp">
</c:import>

<%-- 선택메뉴 팝업 --%>
<c:import url="/WEB-INF/view/base/prod/prod/searchSdselGrp.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 상품옵션그룹 팝업--%>
<c:import url="/WEB-INF/view/base/prod/prod/searchOptionGrp.jsp">
</c:import>