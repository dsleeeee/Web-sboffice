<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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
                            <%-- 취급상품 탭 --%>
                            <li><a href="#" id="trtMntTab" ng-click="changeTab('2')"><s:message code="vendr.trtMnt" /></a></li>
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
                            <tr>
                                <th>
                                    <div class="impWrap"><s:message code="vendr.remark" /></div>
                                </th>
                                <td colspan="3" id="vRemark"></td>
                            </tr>
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
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/vendr/info.js?ver=20200506.13" charset="utf-8"></script>


