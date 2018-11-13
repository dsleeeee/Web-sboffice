<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- 팝업 부분 설정 - width 는 강제 해주어야함.. 해결방법? 확인 필요 : 20180829 노현수 --%>
<wj-popup control="prodModifyLayer" show-trigger="Click" hide-trigger="Click" fade-in="false" fade-out="false" style="display: none;width:800px;">
  <form name="myForm" novalidate>
  <div class="wj-dialog wj-dialog-columns" ng-controller="prodModifyCtrl">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="prod.layer.info"/>
      <a href="#" class="wj-hide btn_close"></a>
    </div>
    <div class="wj-dialog-body sc2" style="height: 600px;">
      <p class="s14 bk mb5"><s:message code="prod.basicInfo"/></p>
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
            <th rowspan="3"><s:message code="prod"/><br/><s:message code="image"/>
            </th>
            <td rowspan="3">
              <%--등록한 상품이 없는 경우--%>
              <span class="goodsNo">IMAGE</span>
              <!--
                            <span class="goodsYes"><img src="img/sample.jpg" alt="" /></span> <%--등록한 상품이 있는 경우--%>
                            -->
            </td>
            <%--단가구분 //TODO --%>
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
              <input type="text" name="prodCd" class="sb-input w100" ng-model="prodModifyInfo.prodCd" placeholder="상품코드는 자동생성 됩니다." disabled />

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
            <%--상품분류--%>
            <th><s:message code="prod.prodClass"/></th>
            <td>
              <input type="text" id="_prodClassCdNm" name="prodClassCdNm" class="sb-input w100"
                     ng-model="prodModifyInfo.prodClassCdNm"
                     required
                     popover-enable="myForm.prodClassCdNm.$invalid"
                     popover-placement="bottom-left"
                     popover-trigger="'mouseenter'"
                     uib-popover="<s:message code="prod.prodClass" />은(는) 필수 입력항목 입니다."
                     placeholder="선택" readonly
                     ng-click="popUpProdClass()" />
              <input type="hidden" id="_prodClassCd" name="prodClassCd" class="sb-input w100" ng-model="prodModifyInfo.prodClassCd" disabled />
            </td>
            <%--거래처 //TODO --%>
            <th><s:message code="prod.vendr"/></th>
            <td id="_vendr">

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
                     ng-model="prodModifyInfo.orgplceCd" placeholder="선택" disabled />
            </td>
          </tr>
          <tr>
            <%--판매단가--%>
            <th><s:message code="prod.saleUprc"/></th>
            <td>
              <input type="text" id="_saleUprc" name="saleUprc" class="sb-input w100"
                     ng-model="prodModifyInfo.saleUprc"
                     required
                     popover-enable="myForm.saleUprc.$invalid"
                     popover-placement="bottom-left"
                     popover-trigger="'mouseenter'"
                     uib-popover="<s:message code="prod.saleUprc" />은(는) 필수 입력항목 입니다."/>
            </td>
            <th></th>
            <td></td>
          </tr>
          <tr>
            <%--공급단가--%>
            <th><s:message code="prod.splyUprc"/></th>
            <td>
              <input type="text" id="_splyUprc" name="splyUprc" class="sb-input w100"
                     ng-model="prodModifyInfo.splyUprc"
                     required
                     popover-enable="myForm.splyUprc.$invalid"
                     popover-placement="bottom-left"
                     popover-trigger="'mouseenter'"
                     uib-popover="<s:message code="prod.splyUprc" />은(는) 필수 입력항목 입니다."/>
            <%--공급단가사용여부--%>
            <th><s:message code="prod.splyUprc"/><br><s:message code="cmm.useYn"/></th>
            <td>
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
          </tr>
          <tr>
            <%--원가단가--%>
            <th><s:message code="prod.costUprc"/></th>
            <td>
              <input type="text" id="_costUprc" name="costUprc" class="sb-input w100"
                     ng-model="prodModifyInfo.costUprc"
                     required
                     popover-enable="myForm.costUprc.$invalid"
                     popover-placement="bottom-left"
                     popover-trigger="'mouseenter'"
                     uib-popover="<s:message code="prod.costUprc" />은(는) 필수 입력항목 입니다."/>
            </td>
            <%--최종판매단가--%>
            <th><s:message code="prod.lastCostUprc"/></th>
            <td>
              <input type="text" id="_lastCostUprc" name="lastCostUprc" class="sb-input w100"
                     ng-model="prodModifyInfo.lastCostUprc"
                     required
                     popover-enable="myForm.lastCostUprc.$invalid"
                     popover-placement="bottom-left"
                     popover-trigger="'mouseenter'"
                     uib-popover="<s:message code="prod.lastCostUprc" />은(는) 필수 입력항목 입니다."/>
            </td>
          </tr>
          <tr>
            <%--주문상품구분--%>
            <th><s:message code="prod.poProdFg"/></th>
            <td>
              <div class="sb-select">
                <wj-combo-box id="_poProdFg" name="poProdFg"
                  ng-model="prodModifyInfo.poProdFg"
                  items-source="_getComboData('poProdFgComboData')"
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
            <%--주문단위--%>
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
              <input type="text" id="_poUnitQty" name="poUnitQty" class="sb-input w100"
                     ng-model="prodModifyInfo.poUnitQty"
                     required
                     popover-enable="myForm.poUnitQty.$invalid"
                     popover-placement="bottom-left"
                     popover-trigger="'mouseenter'"
                     uib-popover="<s:message code="prod.poUnitQty" />은(는) 필수 입력항목 입니다."/>
            </td>
            <th></th>
            <td></td>
          </tr>
          <tr>
            <%--최소주문--%>
            <th><s:message code="prod.poMinQty"/></th>
            <td>
              <input type="text" id="_poMinQty" name="poMinQty" class="sb-input w100"
                     ng-model="prodModifyInfo.poMinQty"
                     required
                     popover-enable="myForm.poMinQty.$invalid"
                     popover-placement="bottom-left"
                     popover-trigger="'mouseenter'"
                     uib-popover="<s:message code="prod.poMinQty" />은(는) 필수 입력항목 입니다."/>
            </td>
            <%--안전재고--%>
            <th><s:message code="prod.safeStockQty"/></th>
            <td>
              <input type="text" id="_safeStockQty" name="safeStockQty" class="sb-input w100"
                     ng-model="prodModifyInfo.safeStockQty"
                     required
                     popover-enable="myForm.safeStockQty.$invalid"
                     popover-placement="bottom-left"
                     popover-trigger="'mouseenter'"
                     uib-popover="<s:message code="prod.safeStockQty" />은(는) 필수 입력항목 입니다."/>
            </td>
          </tr>
          </tbody>
        </table>
      <p class="s14 bk mt20 mb5"><s:message code="prod.addInfo"/></p>
      <table class="tblType01">
        <colgroup>
          <col class="w15"/>
          <col class="w35"/>
          <col class="w15"/>
          <col class="w35"/>
        </colgroup>
        <tbody>
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
        </tr>
        <tr>
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
          <th></th>
          <td></td>
        </tr>
        <tr>
          <%--품절여부 --%>
          <th><s:message code="prod.soldOutYn"/></th>
          <td>
            <div class="sb-select">
              <%--<wj-combo-box id="_soldOutYn" name="soldOutYn"
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
              </wj-combo-box>--%>
            </div>
          </td>
          <%--초기재고 //TODO --%>
          <th><s:message code="prod.defaultStock"/></th>
          <td>
            <input type="text" id="_defaultStock" name="defaultStock" class="sb-input w100"
                   ng-model="prodModifyInfo.defaultStock"
                   required
                   popover-enable="myForm.defaultStock.$invalid"
                   popover-placement="bottom-left"
                   popover-trigger="'mouseenter'"
                   uib-popover="<s:message code="prod.defaultStock" />은(는) 필수 입력항목 입니다."/>
          </td>
        </tr>
        <tr>
          <%--저장품코드 //TODO --%>
          <th><s:message code="prod.saveProdCd"/></th>
          <td>
            <a href="#" class="link" id="_saveProdCd"></a>
          </td>
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
                uib-popover="<s:message code="prod.setProdFg" />은(는) 필수 입력항목 입니다.">
              </wj-combo-box>
            </div>
          </td>
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
          <%--사이드상품여부--%>
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
                uib-popover="<s:message code="prod.sideProdYn" />은(는) 필수 입력항목 입니다.">
              </wj-combo-box>
            </div>
          </td>
          <th></th>
          <td></td>
        </tr>
        </tbody>
      </table>
      <%--비고--%>
      <p class="s14 bk mt20 mb5"><s:message code="remark"/></p>
      <table class="tblType01">
        <colgroup>
          <col class="w100"/>
        </colgroup>
        <tbody>
        <tr>
          <th class="gr lh20">
            <input type="text" id="_remark" name="remark" class="sb-input w100"
                   ng-model="prodModifyInfo.remark"/>
          </th>
        </tr>
        </tbody>
      </table>

      <%-- 할인 / 적립 --%>
      <p class="s14 bk mt20 mb5"><s:message code="prod.dcAndSaveInfo"/></p>
      <table class="tblType01">
        <colgroup>
          <col width="15%"/>
          <col width="85%"/>
        </colgroup>
        <tbody>
        <tr>
          <%--할인 //TODO --%>
          <th><s:message code="prod.dc"/></th>
          <td id="_dc"></td>
        </tr>
        <tr>
          <%--적립 //TODO --%>
          <th><s:message code="prod.save"/></th>
          <td id="_save"></td>
        </tr>
        </tbody>
      </table>
      <p class="s14 bk mt20 mb5"><s:message code="prod.unitstInfo"/></p>
      <table class="tblType01 mb20">
        <colgroup>
          <col class="w15"/>
          <col class="w85"/>
        </colgroup>
        <tbody id="_linkedProdInfo">
        </tbody>
      </table>
    </div>
    <div class="wj-dialog-footer">
      <button class="btn wj-hide-apply btn_blue"><s:message code="cmm.save"/></button>
      <button class="btn wj-hide btn_blue"><s:message code="cmm.close"/></button>
    </div>
  </div>
  </form>
</wj-popup>
<script type="text/javascript" src="/resource/solbipos/js/base/prod/prod/popUpProdModify.js?ver=20181113.01" charset="utf-8"></script>

<%-- 레이어 팝업 --%>
<c:import url="/WEB-INF/view/base/prod/prod/popUpProdClass.jsp">
</c:import>
