<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="menuCd">${sessionScope.sessionInfo.currentMenu.resrceCd}</c:set>
<c:set var="menuNm">${sessionScope.sessionInfo.currentMenu.resrceNm}</c:set>


<wj-popup id="wjVendrInfoLayer" control="wjVendrInfoLayer"  show-trigger="Click" hide-trigger="Click" style="display: none;width:800px;">
    <div id="vendrInfoLayer" class="wj-dialog wj-dialog-columns" ng-controller="vendrInfoCtrl">
        <div class="layer_inner">
            <div class="title w800px">
                <%-- 타이틀 --%>
                <p class="tit" id="popInfo"></p>
                <a href="#" class="btn_close" ng-click="close()"></a>
                <div class="con">
                    <%-- 거래처등록, 취급상품 탭 --%>
                    <div class="tabType1">
                        <ul>
                            <%-- 거래처등록 탭 --%>
                            <li><a href="#" id="vendrTab" class="on" ng-click="changeTab('1')"><s:message code="vendr.regst" /></a></li>
                            <c:if test="${urlVendorFg != '2' and urlVendorFg != '1'}">
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
                        <tr>
                            <%-- 거래처코드 --%>
                            <th>
                                 <div class="impWrap"><s:message code="vendr.vendrCd" /> <em class="imp">*</em></div>
                            </th>
                            <td id="vVendrCd"></td>
                            <%-- 거래처명 --%>
                            <th>
                                <div class="impWrap"><s:message code="vendr.vendrNm" /> <em class="imp">*</em></div>
                            </th>
                            <td id="vVendrNm"></td>
                        </tr>
                        <tr>
                            <%-- 대표자명 --%>
                            <th>
                                <div class="impWrap"><s:message code="vendr.ownerNm" /> <em class="imp">*</em></div>
                            </th>
                            <td id="vOwnerNm"></td>
                            <%-- 거래처구분 --%>
                            <th>
                                <div class="impWrap"><s:message code="vendr.vendorFg" /> <em class="imp">*</em></div>
                            </th>
                            <td id="vVendorFg"></td>
                        </tr>
                        <tr>
                            <%-- 부가세 포함여부 --%>
                            <th>
                                <div class="impWrap"><s:message code="vendr.vatIncldYn" /> <em class="imp">*</em></div>
                            </th>
                            <td id="vVatIncldYn"></td>
                            <%-- 사용여부 --%>
                            <th>
                                <div class="impWrap"><s:message code="vendr.useYn" /></div>
                            </th>
                            <td id="vUseYn"></td>
                        </tr>
                        <tr>
                            <%-- 사업자번호 --%>
                            <th>
                                <div class="impWrap"><s:message code="vendr.bizNo" /></div>
                            </th>
                            <td id="vBizNo"></td>
                            <%-- 전화번호 --%>
                            <th>
                                <div class="impWrap"><s:message code="vendr.telNo" /></div>
                            </th>
                            <td id="vTelNo"></td>
                        </tr>
                        <tr>
                            <%-- 이메일주소 --%>
                            <th>
                                <div class="impWrap"><s:message code="vendr.emailAddr" /></div>
                            </th>
                            <td id="vEmailAddr"></td>
                            <%-- 팩스번호 --%>
                            <th>
                                <div class="impWrap"><s:message code="vendr.faxNo" /></div>
                            </th>
                            <td id="vFaxNo"></td>
                        </tr>
                        <tr>
                            <%-- 주소 --%>
                            <th>
                                <div class="impWrap"><s:message code="vendr.addr" /></div>
                            </th>
                            <td colspan="3" id="vAddr"></td>
                        </tr>
                        <tr <c:if test="${orgnFg != 'HQ' or (orgnFg =='HQ' and envst1242 != '2')}">style="display: none;"</c:if>>
                            <%-- 직배송 --%>
                            <th>
                                <div class="impWrap"><s:message code="vendr.shipFg" /><em class="imp">*</em></div>
                            </th>
                            <td id="vShipFg"></td>
                            <th></th>
                            <td></td>
                        </tr>
                        <tr>
                            <th>
                                <div class="impWrap"><s:message code="vendr.remark" /></div>
                            </th>
                            <td colspan="3" id="vRemark"></td>
                        </tr>
                        <tr>
                            <c:if test="${urlVendorFg == '2'}">
                                <%-- 업체구분 --%>
                                <th><s:message code="vendr.companyFg" /></th>
                                <td id="vCompanyFg"></td>
                            </c:if>
                            <c:if test="${urlVendorFg == '1'}">
                                <%-- 매입처구분 --%>
                                <th><s:message code="vendr.acquireCd" /></th>
                                <td id="vAcquireCd"></td>
                            </c:if>
                            <c:if test="${urlVendorFg == '2' or urlVendorFg == '1'}">
                                <%-- 사업자구분 --%>
                                <th><s:message code="vendr.businessFg" /></th>
                                <td id="vBusinessFg"></td>
                            </c:if>
                        </tr>
                        <c:if test="${urlVendorFg == '1'}">
                            <tr>
                                <%-- 매입구분 --%>
                                <th><s:message code="vendr.acquireFg" /></th>
                                <td id="vAcquireFg"></td>
                                <%-- 관리지점 --%>
                                <th><s:message code="vendr.manageSpotCd" /></th>
                                <td id="vManageSpotCd"></td>
                            </tr>
                        </c:if>
                        <c:if test="${urlVendorFg == '2' or urlVendorFg == '1'}">
                            <tr>
                                <%-- 법인번호 --%>
                                <th><s:message code="vendr.corporationNumber" /></th>
                                <td id="vCorporationNumber"></td>
                                <%-- 홈페이지 --%>
                                <th><s:message code="vendr.homepage" /></th>
                                <td id="vHomepage"></td>
                            </tr>
                            <tr>
                                <%-- 업태 --%>
                                <th><s:message code="vendr.businessStatus" /></th>
                                <td id="vBusinessStatus"></td>
                                <%-- 업종 --%>
                                <th><s:message code="vendr.industry" /></th>
                                <td id="vIndustry"></td>
                            </tr>
                            <tr>
                                <%-- 대표자전화 --%>
                                <th><s:message code="vendr.ownerTelNo" /></th>
                                <td id="vOwnerTelNo"></td>
                                <%-- 대표자Email --%>
                                <th><s:message code="vendr.ownerEmail" /></th>
                                <td id="vOwnerEmail"></td>
                            </tr>
                            <tr>
                                <%-- 담당자명 --%>
                                <th><s:message code="vendr.managerNm" /></th>
                                <td id="vManagerNm"></td>
                                <%-- 담당자전화번호 --%>
                                <th><s:message code="vendr.managerTelNo" /></th>
                                <td id="vManagerTelNo"></td>
                            </tr>
                            <tr>
                                <%-- 담당자Email --%>
                                <th><s:message code="vendr.managerEmail" /></th>
                                <td id="vManagerEmail"></td>
                                <%-- 담당자직위 --%>
                                <th><s:message code="vendr.managerSpot" /></th>
                                <td id="vManagerSpot"></td>
                            </tr>
                            <tr>
                                <%-- 담당자휴대전화 --%>
                                <th><s:message code="vendr.managerPhoneNo" /></th>
                                <td id="vManagerPhoneNo"></td>
                                <%-- 은행코드 --%>
                                <th><s:message code="vendr.bankCd" /></th>
                                <td id="vBankCd"></td>
                            </tr>
                            <tr>
                                <%-- 계좌번호 --%>
                                <th><s:message code="vendr.accountNo" /></th>
                                <td id="vAccountNo"></td>
                                <%-- 예금주 --%>
                                <th><s:message code="vendr.depositor" /></th>
                                <td id="vDepositor"></td>
                            </tr>
                            <tr>
                                <%-- 수금 일자/주기 --%>
                                <th><s:message code="vendr.collectFg" /></th>
                                <td id="vCollectFg"></td>
                                <%-- 더존ERP --%>
                                <th><s:message code="vendr.douzoneErp" /></th>
                                <td id="vDouzoneErp"></td>
                            </tr>
                            <tr>
                                <%-- 외상한도액 --%>
                                <th><s:message code="vendr.creditLimit" /></th>
                                <td id="vCreditLimit"></td>
                                <%-- 담보종류 --%>
                                <th><s:message code="vendr.collateralType" /></th>
                                <td id="vCollateralType"></td>
                            </tr>
                            <tr>
                                <%-- 담보금액 --%>
                                <th><s:message code="vendr.collateralAmt" /></th>
                                <td id="vCollateralAmt"></td>
                                <%-- 대조일 --%>
                                <th><s:message code="vendr.contrastDate" /></th>
                                <td id="vContrastDate"></td>
                            </tr>
                            <tr>
                                <%-- 대조자(점포) --%>
                                <th><s:message code="vendr.collatorStore" /></th>
                                <td id="vCollatorStore"></td>
                                <%-- 대조자(업체) --%>
                                <th><s:message code="vendr.collatorCompany" /></th>
                                <td id="vCollatorCompany"></td>
                            </tr>
                            <tr>
                                <%-- 거래시작일 --%>
                                <th><s:message code="vendr.dealStartDate" /></th>
                                <td id="vDealStartDate"></td>
                                <%-- 거래종료일 --%>
                                <th><s:message code="vendr.dealEndDate" /></th>
                                <td id="vDealEndDate"></td>
                            </tr>
                            <tr>
                                <%-- 최종매출일 --%>
                                <th><s:message code="vendr.lastSaleDate" /></th>
                                <td id="vLastSaleDate"></td>
                                <%-- 최종입금일 --%>
                                <th><s:message code="vendr.lastDepositDate" /></th>
                                <td id="vLastDepositDate"></td>
                            </tr>
                        </c:if>
                        <c:if test="${urlVendorFg == '2'}">
                            <tr>
                                <%-- 구매ID --%>
                                <th><s:message code="vendr.purchaseId" /></th>
                                <td id="vPurchaseId"></td>
                                <td></td>
                                <td></td>
                            </tr>
                            <tr>
                                <%-- 회원코드 --%>
                                <th><s:message code="vendr.membrNo" /></th>
                                <td id="vMembrNo"></td>
                                <%-- 회원명 --%>
                                <th style="display: none;"><s:message code="vendr.membrNm" /></th>
                                <td style="display: none;" id="vMembrNm"></td>
                            </tr>
                        </c:if>
                        <c:if test="${urlVendorFg == '1'}">
                            <tr>
                                <%-- 전자계산서 --%>
                                <th><s:message code="vendr.electronicBill" /></th>
                                <td id="vElectronicBill"></td>
                                <%-- 매입상품 --%>
                                <th><s:message code="vendr.acquireProd" /></th>
                                <td id="vAcquireProd"></td>
                            </tr>
                        </c:if>
                        </tbody>
                    </table>
                </div>

                <%-- 공통 버튼 영역 --%>
                <div class="btnSet">
                    <%--수정 --%>
                    <span><a href="#" class="btn_blue" id="btnEdit" ng-click="modifyInfo()"><s:message code="cmm.edit" /></a></span>
                    <%-- 닫기 --%>
                    <span><a href="#" class="btn_gray" id="btnClose" ng-click="close()"><s:message code="cmm.close" /></a></span>
                </div>

            </div>
        </div>
    </div>
</wj-popup>

<script>
    var orgnFg = "${orgnFg}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/vendr/info.js?ver=20251015.01" charset="utf-8"></script>