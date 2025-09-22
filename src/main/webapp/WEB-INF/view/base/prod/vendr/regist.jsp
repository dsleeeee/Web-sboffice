<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="menuCd">${sessionScope.sessionInfo.currentMenu.resrceCd}</c:set>
<c:set var="menuNm">${sessionScope.sessionInfo.currentMenu.resrceNm}</c:set>

<%-- 우편번호 찾기 팝업 --%>
<%-- 선택한 주소를 부모창에 바인딩 하기 위해, 각 화면마다 구분자를 지정하여 element id명을 파악한다. --%>
<%-- jsp:param 방식은 API 호출 시, 파라미터 사용을 불허하기 때문에 호출이 거부됨. --%>
<input type="hidden" id="pageNm" value="vendrRegist" />
<%@ include file="/WEB-INF/view/application/layer/searchAddr.jsp" %>

<wj-popup id="wjVendrRegistLayer" control="wjVendrRegistLayer"  show-trigger="Click" hide-trigger="Click" style="display: none;width:800px;">
    <div id="vendrRegistLayer" class="wj-dialog wj-dialog-columns" ng-controller="vendrRegistCtrl">
        <div class="layer_inner">
            <div class="title w800px">
                <%-- 타이틀 --%>
                <p class="tit" id="popTitleReg" style="display: none;"><s:message code='vendr.layer.regist.title' /></p>
                <p class="tit" id="popTitleMod" style="display: none;"><s:message code='vendr.layer.modify.title' /></p>
                <a href="#" class="btn_close" ng-click="close()"></a>
                <div class="con">
                    <%-- 거래처등록, 취급상품 탭 --%>
                    <div class="tabType1">
                        <ul>
                            <%-- 거래처등록 탭 --%>
                            <li><a href="#" id="vendrTab" class="on" ng-click="changeTab('1')"><s:message code="vendr.regst" /></a></li>
                            <c:if test="${urlVendorFg != '2'}">
                                <%-- 취급상품 탭 --%>
                                <li><a href="#" id="trtMntTab" ng-click="changeTab('2')"><s:message code="vendr.trtMnt" /></a></li>
                            </c:if>
                        </ul>
                    </div>

                    <table class="tblType01">
                        <colgroup>
                            <col class="w15" />
                            <col class="w35" />
                            <col class="w15" />
                            <col class="w35" />
                        </colgroup>
                        <tbody>
                        <tr class="brt">
                            <%-- 거래처코드 --%>
                            <th><s:message code="vendr.vendrCd" /> <em class="imp">*</em></th>
                            <td><input type="text" class="sb-input w100" id="rVendrCd" ng-model="rVendrCd" readonly/></td>
                            <%-- 거래처명 --%>
                            <th><s:message code="vendr.vendrNm" /> <em class="imp">*</em></th>
                            <td><input type="text" class="sb-input w100" id="rVendrNm" ng-model="rVendrNm" maxlength="50"/></td>
                        </tr>
                        <tr>
                            <%-- 대표자명 --%>
                            <th><s:message code="vendr.ownerNm" /> <em class="imp">*</em></th>
                            <td><input type="text" class="sb-input w100" id="rOwnerNm" ng-model="rOwnerNm" maxlength="50"/></td>
                            <%-- 거래처구분 --%>
                            <th><s:message code="vendr.vendorFg" /> <em class="imp">*</em></th>
                            <td>
                                <div class="sb-select">
                                    <wj-combo-box
                                            id="rVendorFg"
                                            ng-model="vendorFg"
                                            items-source="_getComboData('rVendorFg')"
                                            display-member-path="name"
                                            selected-value-path="value"
                                            is-editable="false"
                                            initialized="_initComboBox(s)"
                                            control="vendorFgCombo">
                                    </wj-combo-box>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <%-- 부가세 포함여부 --%>
                            <th><s:message code="vendr.vatIncldYn" /> <em class="imp">*</em></th>
                            <td>
                                <div class="sb-select">
                                    <wj-combo-box
                                            id="rVatIncldYn"
                                            ng-model="vatIncldYn"
                                            items-source="_getComboData('rVatIncldYn')"
                                            display-member-path="name"
                                            selected-value-path="value"
                                            is-editable="false"
                                            initialized="_initComboBox(s)"
                                            control="vatIncldYnCombo">
                                    </wj-combo-box>
                                </div>
                            </td>
                            <%-- 사용여부 --%>
                            <th><s:message code="vendr.useYn" /></th>
                            <td>
                                <div class="sb-select">
                                    <wj-combo-box
                                            id="rUseYn"
                                            ng-model="useYn"
                                            items-source="_getComboData('rUseYn')"
                                            display-member-path="name"
                                            selected-value-path="value"
                                            is-editable="false"
                                            initialized="_initComboBox(s)"
                                            control="useYnCombo">
                                    </wj-combo-box>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <%-- 사업자번호 --%>
                            <th><s:message code="vendr.bizNo" /></th>
                            <td>
                                <input type="text" id="rBizNo1" maxlength="3" style="width:50px;" maxlength="3" onkeyup="this.value=this.value.replace(/[^0-9]/g,'');" />
                                <input type="text" id="rBizNo2" maxlength="2" style="width:40px;" maxlength="2" onkeyup="this.value=this.value.replace(/[^0-9]/g,'');" />
                                <input type="text" id="rBizNo3" maxlength="5" style="width:110px;" maxlength="5" onkeyup="this.value=this.value.replace(/[^0-9]/g,'');" />
                            </td>
                            <th><s:message code="vendr.telNo" /></th>
                            <td><input type="text" class="sb-input w100" id="rTelNo" ng-model="rTelNo" maxlength="15" onkeyup="this.value=this.value.replace(/[^0-9]/g,'');" /></td>
                        </tr>
                        <tr>
                            <th><s:message code="vendr.emailAddr" /></th>
                            <td><input type="text" class="sb-input w100" id="rEmailAddr" ng-model="rEmailAddr" maxlength="200"/></td>
                            <th><s:message code="vendr.faxNo" /></th>
                            <td><input type="text" class="sb-input w100" id="rFaxNo" ng-model="rFaxNo" maxlength="15" onkeyup="this.value=this.value.replace(/[^0-9]/g,'');" /></td>
                        </tr>
                        <tr>
                            <th><s:message code="vendr.addr" /></th>
                            <td colspan="3">
                                <input type="text" id="rPostNo" ng-model="rPostNo" class="sb-input" placeholder="우편번호" maxlength="5" style="width: 80px;" readonly/>
                                <a id="btnSrchAddr" href="#" class="btn_grayS ml5" onclick="searchAddr()">
                                    <s:message code="storeManage.srchAddr" />
                                </a>
                                <input type="text" id="rAddr" ng-model="rAddr" class="sb-input w100" placeholder="주소1" maxlength="300" style="margin:4px 0px;" readonly/>
                                <input type="text" id="rAddrDtl" ng-model="rAddrDtl" class="sb-input w100" placeholder="주소2" maxlength="300"/>
                            </td>
                        </tr>
                        <tr <c:if test="${orgnFg != 'HQ' or (orgnFg =='HQ' and envst1242 != '2')}">style="display: none;"</c:if>>
                            <th><s:message code="vendr.shipFg" /><em class="imp">*</em></th>
                            <td colspan="3">
                                <div class="sb-select w30 pdr5" style="float:left;">
                                    <wj-combo-box
                                          id="rShipFg"
                                          ng-model="shipFg"
                                          items-source="_getComboData('rShipFg')"
                                          display-member-path="name"
                                          selected-value-path="value"
                                          is-editable="false"
                                          initialized="_initComboBox(s)"
                                          control="shipFgCombo">
                                    </wj-combo-box>
                                </div>
                                <label class="lh25">
                                    (직배송을 '사용'으로 설정하시면 출고자료 생성시 거래처별로 생성됩니다.)
                                </label>
                            </td>
                        </tr>
                        <tr>
                            <%-- 비고 --%>
                            <th><s:message code="vendr.remark" /></th>
                            <td colspan="3">
                                <input type="text" class="sb-input w100" id="rRemark" ng-model="rRemark" maxlength="500"/>
                            </td>
                        </tr>
                        <c:if test="${urlVendorFg == '2'}">
                            <tr>
                                <%-- 업체구분 --%>
                                <th><s:message code="vendr.companyFg" /></th>
                                <td>
                                    <div class="sb-select">
                                        <wj-combo-box
                                                id="rCompanyFg"
                                                ng-model="companyFg"
                                                items-source="_getComboData('rCompanyFg')"
                                                display-member-path="name"
                                                selected-value-path="value"
                                                is-editable="false"
                                                initialized="_initComboBox(s)"
                                                control="companyFgCombo">
                                        </wj-combo-box>
                                    </div>
                                </td>
                                <%-- 사업자구분 --%>
                                <th><s:message code="vendr.businessFg" /></th>
                                <td>
                                    <div class="sb-select">
                                        <wj-combo-box
                                                id="rBusinessFg"
                                                ng-model="businessFg"
                                                items-source="_getComboData('rBusinessFg')"
                                                display-member-path="name"
                                                selected-value-path="value"
                                                is-editable="false"
                                                initialized="_initComboBox(s)"
                                                control="businessFgCombo">
                                        </wj-combo-box>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <%-- 구매ID --%>
                                <th><s:message code="vendr.purchaseId" /></th>
                                <td><input type="text" class="sb-input w100" id="rPurchaseId" ng-model="rPurchaseId"/></td>
                                <%-- 법인번호 --%>
                                <th><s:message code="vendr.corporationNumber" /></th>
                                <td><input type="text" class="sb-input w100" id="rCorporationNumber" ng-model="rCorporationNumber"/></td>
                            </tr>
                            <tr>
                                <%-- 업태 --%>
                                <th><s:message code="vendr.businessStatus" /></th>
                                <td><input type="text" class="sb-input w100" id="rBusinessStatus" ng-model="rBusinessStatus"/></td>
                                <%-- 업종 --%>
                                <th><s:message code="vendr.industry" /></th>
                                <td><input type="text" class="sb-input w100" id="rIndustry" ng-model="rIndustry"/></td>
                            </tr>
                            <tr>
                                <%-- 홈페이지 --%>
                                <th><s:message code="vendr.homepage" /></th>
                                <td><input type="text" class="sb-input w100" id="rHomepage" ng-model="rHomepage"/></td>
                                <%-- 대표자전화 --%>
                                <th><s:message code="vendr.ownerTelNo" /></th>
                                <td><input type="text" class="sb-input w100" id="rOwnerTelNo" ng-model="rOwnerTelNo"/></td>
                            </tr>
                            <tr>
                                <%-- 대표자Email --%>
                                <th><s:message code="vendr.ownerEmail" /></th>
                                <td><input type="text" class="sb-input w100" id="rOwnerEmail" ng-model="rOwnerEmail"/></td>
                                <%-- 담당자명 --%>
                                <th><s:message code="vendr.managerNm" /></th>
                                <td><input type="text" class="sb-input w100" id="rManagerNm" ng-model="rManagerNm"/></td>
                            </tr>
                            <tr>
                                <%-- 담당자전화번호 --%>
                                <th><s:message code="vendr.managerTelNo" /></th>
                                <td><input type="text" class="sb-input w100" id="rManagerTelNo" ng-model="rManagerTelNo"/></td>
                                <%-- 담당자Email --%>
                                <th><s:message code="vendr.managerEmail" /></th>
                                <td><input type="text" class="sb-input w100" id="rManagerEmail" ng-model="rManagerEmail"/></td>
                            </tr>
                            <tr>
                                <%-- 담당자직위 --%>
                                <th><s:message code="vendr.managerSpot" /></th>
                                <td><input type="text" class="sb-input w100" id="rManagerSpot" ng-model="rManagerSpot"/></td>
                                <%-- 담당자휴대전화 --%>
                                <th><s:message code="vendr.managerPhoneNo" /></th>
                                <td><input type="text" class="sb-input w100" id="rManagerPhoneNo" ng-model="rManagerPhoneNo"/></td>
                            </tr>
                            <tr>
                                <%-- 은행코드 --%>
                                <th><s:message code="vendr.bankCd" /></th>
                                <td><input type="text" class="sb-input w100" id="rBankCd" ng-model="rBankCd"/></td>
                                <%-- 계좌번호 --%>
                                <th><s:message code="vendr.accountNo" /></th>
                                <td><input type="text" class="sb-input w100" id="rAccountNo" ng-model="rAccountNo"/></td>
                            </tr>
                            <tr>
                                <%-- 예금주 --%>
                                <th><s:message code="vendr.depositor" /></th>
                                <td><input type="text" class="sb-input w100" id="rDepositor" ng-model="rDepositor"/></td>
                                <%-- 수금 일자/주기 --%>
                                <th><s:message code="vendr.collectFg" /></th>
                                <td><input type="text" class="sb-input w100" id="rCollectFg" ng-model="rCollectFg"/></td>
                            </tr>
                            <tr>
                                <%-- 더존ERP --%>
                                <th><s:message code="vendr.douzoneErp" /></th>
                                <td><input type="text" class="sb-input w100" id="rDouzoneErp" ng-model="rDouzoneErp"/></td>
                                <%-- 외상한도액 --%>
                                <th><s:message code="vendr.creditLimit" /></th>
                                <td><input type="text" class="sb-input w100" id="rCreditLimit" ng-model="rCreditLimit"/></td>
                            </tr>
                            <tr>
                                <%-- 담보종류 --%>
                                <th><s:message code="vendr.collateralType" /></th>
                                <td><input type="text" class="sb-input w100" id="rCollateralType" ng-model="rCollateralType"/></td>
                                <%-- 담보금액 --%>
                                <th><s:message code="vendr.collateralAmt" /></th>
                                <td><input type="text" class="sb-input w100" id="rCollateralAmt" ng-model="rCollateralAmt"/></td>
                            </tr>
                            <tr>
                                <%-- 대조일 --%>
                                <th><s:message code="vendr.contrastDate" /></th>
                                <td><input type="text" class="sb-input w100" id="rContrastDate" ng-model="rContrastDate"/></td>
                                <%-- 대조자(점포) --%>
                                <th><s:message code="vendr.collatorStore" /></th>
                                <td><input type="text" class="sb-input w100" id="rCollatorStore" ng-model="rCollatorStore"/></td>
                            </tr>
                            <tr>
                                <%-- 대조자(업체) --%>
                                <th><s:message code="vendr.collatorCompany" /></th>
                                <td><input type="text" class="sb-input w100" id="rCollatorCompany" ng-model="rCollatorCompany"/></td>
                                <%-- 거래시작일 --%>
                                <th><s:message code="vendr.dealStartDate" /></th>
                                <td><input type="text" class="sb-input w100" id="rDealStartDate" ng-model="rDealStartDate"/></td>
                            </tr>
                            <tr>
                                <%-- 거래종료일 --%>
                                <th><s:message code="vendr.dealEndDate" /></th>
                                <td><input type="text" class="sb-input w100" id="rDealEndDate" ng-model="rDealEndDate"/></td>
                                <%-- 최종매출일 --%>
                                <th><s:message code="vendr.lastSaleDate" /></th>
                                <td><input type="text" class="sb-input w100" id="rLastSaleDate" ng-model="rLastSaleDate"/></td>
                            </tr>
                            <tr>
                                <%-- 최종입금일 --%>
                                <th><s:message code="vendr.lastDepositDate" /></th>
                                <td><input type="text" class="sb-input w100" id="rLastDepositDate" ng-model="rLastDepositDate"/></td>
                                <td></td>
                                <td></td>
                            </tr>
                            <tr id="trMembr">
                                <%-- 회원코드 --%>
                                <th><s:message code="vendr.membrNo" /></th>
                                <td><input type="text" class="sb-input w100" id="rMembrNo" ng-model="rMembrNo" readonly/></td>
                                <%-- 회원명 --%>
                                <th><s:message code="vendr.membrNm" /></th>
                                <td><input type="text" class="sb-input w100" id="rMembrNm" ng-model="rMembrNM"/></td>
                            </tr>
                        </c:if>
                        </tbody>
                    </table>
                </div>

                <%-- 공통 버튼 영역 --%>
                <div class="btnSet">
                    <%-- 신규등록 --%>
                    <span><a href="#" class="btn_blue" id="btnReg" style="display:none;" ng-click="regVendr()"><s:message code="cmm.new.add" /></a></span>
                    <%-- 저장 --%>
                    <span><a href="#" class="btn_blue" id="btnMod" style="display:none;" ng-click="modVendr()"><s:message code="cmm.save" /></a></span>
                    <%-- 닫기 --%>
                    <span><a href="#" class="btn_gray" id="btnClose" ng-click="close()"><s:message code="cmm.close" /></a></span>
                </div>

            </div>
        </div>
    </div>
</wj-popup>

<script>
    var orgnFg = "${orgnFg}";
    var vendorFgData     = ${ccu.getCommCodeExcpAll("011")};
    <%--var vatIncldYnData   = ${ccu.getCommCodeExcpAll("067")};--%>
    var useYnData        = ${ccu.getCommCodeExcpAll("067")};
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/vendr/regist.js?ver=20250922.01" charset="utf-8"></script>
