<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- 우편번호 찾기 팝업 --%>
<%-- 선택한 주소를 부모창에 바인딩 하기 위해, 각 화면마다 구분자를 지정하여 element id명을 파악한다. --%>
<%-- jsp:param 방식은 API 호출 시, 파라미터 사용을 불허하기 때문에 호출이 거부됨. --%>
<input type="hidden" id="pageNm" value="storeInfo" />
<%@ include file="/WEB-INF/view/application/layer/searchAddr.jsp" %>

<wj-popup id="storeInfoLayer" control="storeInfoLayer" show-trigger="Click" hide-trigger="Click" style="width:800px;height:600px;display:none;">
  <div class="wj-dialog wj-dialog-columns title" ng-controller="storeInfoCtrl">

    <%-- header --%>
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="storeManage.storeInfo" />
      <span id="storeInfoTitle" class="ml20"></span>
      <a href="#" class="wj-hide btn_close" ng-click="closeStoreInfo()"></a>
    </div>

    <%-- body --%>
    <div class="wj-dialog-body">

      <%-- 탭 --%>
      <ul class="subTab">
        <%-- 매장정보 --%>
        <li><a id="storeInfo" href="#" class="on"><s:message code="storeManage.storeInfo" /></a></li>
        <%-- 매장환경 --%>
        <li><a id="storeEnv" href="#" ng-click="changeEnvTab();"><s:message code="storeManage.storeEnv" /></a></li>
        <%-- 메뉴권한 --%>
        <li><a id="storeAuth" href="#" ng-click="changeAuthTab();"><s:message code="storeManage.auth" /></a></li>
      </ul>

      <div style="height:400px; overflow-y: auto;">
        <f:form id="viewForm">
          <h3 class="h3_tbl" style="height: 36px;">
            <div class="fl" style="padding-top: 7px;"><s:message code="storeManage.basicInfo" /></div>
            <div class="fr"><a id="btnErpStoreSet" href="#" class="btn_grayS ml5" ng-click="erpStoreSet()" style="display: none;"><label id="lblErpStoreSet"></label></a></div>
          </h3>
          <table class="searchTbl">
            <colgroup>
              <col class="w15" />
              <col class="w35" />
              <col class="w15" />
              <col class="w35" />
            </colgroup>
            <tbody>
            <tr>
              <%-- 본사코드 --%>
              <th><s:message code="storeManage.hqOfficeCd" /><em class="imp">*</em></th>
              <td>
                <input type="text" id="hqOfficeCd" class="sb-input w100" ng-model="store.hqOfficeCd" readonly="readonly" ng-click="searchHq()"/>
              </td>
              <%-- 본사명 --%>
              <th><s:message code="storeManage.hqOfficeNm" /><em class="imp">*</em></th>
              <td>
                <input type="text" id="hqOfficeNm" class="sb-input w100" ng-model="store.hqOfficeNm" readonly="readonly" ng-click="searchHq()"/>
              </td>
            </tr>
            <tr>
              <%-- 매장코드 --%>
              <th><s:message code="storeManage.storeCd" /><em class="imp">*</em></th>
              <td>
                <input type="text" id="storeCd" class="sb-input w100" ng-model="store.storeCd" readonly="readonly" maxlength="12" onkeyup="this.value=this.value.replace(/[^A-Z0-9]/g,'');"/>
                <input type="hidden" id="storeCdChkFg" ng-model="store.storeCdChkFg"/>
                <input type="hidden" id="storeCdInputType" ng-model="store.storeCdInputType"/>
                <input type="hidden" id="envst0043" ng-model="store.envst0043"/>
                <input type="hidden" id="hdDigit8Store"/>
                <input type="hidden" id="hdBbqStoreCd" />
                <input type="hidden" id="hdMomsEnvstVal" /> <%-- [1250 맘스터치] --%>
                <a id="btnChkStoreCd" href="#" class="btn_grayS ml5" ng-click="chkStoreCd()" style="display: none;"><s:message code="storeManage.chk.duplicate" /></a><Br />
              </td>
              <%-- 매장명 --%>
              <th><s:message code="storeManage.storeNm" /><em class="imp">*</em></th>
              <td>
                <input type="text" id="storeNm" class="sb-input w100" ng-model="store.storeNm" maxlength="15"/>
              </td>
            </tr>
            <tr>
              <%-- 상호명 --%>
              <th><s:message code="storeManage.bizStoreNm" /><em class="imp">*</em></th>
              <td>
                <input type="text" id="bizStoreNm" class="sb-input w100" ng-model="store.bizStoreNm" maxlength="15"/>
              </td>
              <%-- 대표자명 --%>
              <th><s:message code="storeManage.onwerNm" /><em class="imp">*</em></th>
              <td>
                <input type="text" id="ownerNm" class="sb-input w100" ng-model="store.ownerNm" maxlength="10"/>
              </td>
            </tr>
            <tr id="trUser" style="display: none;">
              <%-- 웹사용자아이디 --%>
              <th><s:message code="storeManage.userId" /><em class="imp">*</em></th>
              <td>
                <input type="text" id="userId" class="sb-input w60" ng-model="store.userId" maxlength="12" placeholder="소문자, 숫자 입력가능" onkeyup="this.value=this.value.replace(/[^a-z0-9]/g,'');" />
                <input type="hidden" id="userIdChkFg" ng-model="store.userIdChkFg"/>
                <a id="btnChkUserId" href="#" class="btn_grayS ml5" ng-click="chkUserId()"><s:message code="storeManage.chk.duplicate" /></a><Br />
              </td>
              <%-- 비밀번호 --%>
              <th><s:message code="storeManage.pwd" /><em class="imp">*</em></th>
              <td>
                <input type="password" class="sb-input w45" id="userPwd" ng-model="store.userPwd" maxlength="16" placeholder="<s:message code="storeManage.pwd" />"/>
                <input type="password" class="sb-input w45" id="userPwdConf" ng-model="store.userPwdConf" maxlength="16" placeholder="<s:message code="storeManage.pwdConf" />"/>
              </td>
            </tr>
            <tr>
              <%-- 시스템 오픈일자 / 포스개점일자 --%>
              <th><s:message code="storeManage.posOpenDate" /></th>
              <td>
                <div class="sb-select">
                    <span class="txtIn"><input id="sysOpenDate" ng-model="store.sysOpenDate" style="width: 245px;"></span>
                </div>
              </td>
              <%-- 매장상태구분 --%>
              <th><s:message code="storeManage.sysStatFg" /><em class="imp">*</em></th>
              <td>
                <div class="sb-select">
                  <wj-combo-box
                    id="sysStatFg"
                    ng-model="store.sysStatFg"
                    control="sysStatFgCombo"
                    items-source="_getComboData('sysStatFg')"
                    display-member-path="name"
                    selected-value-path="value"
                    is-editable="false"
                    initialized="_initComboBox(s)"
                    selected-index-changed="setSysStatFgVal(s,e)">
                  </wj-combo-box>
                </div>
                <input type="hidden" id="hdSysStatFg" />
              </td>
            </tr>
            <%-- [1250 맘스터치] --%>
            <tr id="trMomsEnvst" style="display: none;">
              <%-- 브랜드 --%>
              <th><s:message code="storeManage.moms.hqBrandCd" /></th>
              <td>
                <div class="sb-select">
                  <wj-combo-box
                          id="srchHqBrandCdCombo"
                          ng-model="store.hqBrandCd"
                          items-source="_getComboData('hqBrandCdCombo')"
                          display-member-path="name"
                          selected-value-path="value"
                          is-editable="false"
                          initialized="_initComboBox(s)"
                          control="srchHqBrandCdCombo">
                  </wj-combo-box>
                </div>
              </td>
              <th></th>
              <td></td>
            </tr>
            <tr>
              <%-- 용도 --%>
              <th><s:message code="storeManage.cls" /><em class="imp">*</em></th>
              <td>
                <div class="sb-select">
                  <wj-combo-box
                    id="clsFg"
                    ng-model="store.clsFg"
                    control="clsFgCombo"
                    items-source="_getComboData('clsFg')"
                    display-member-path="name"
                    selected-value-path="value"
                    is-editable="false"
                    initialized="_initComboBox(s)"
                    selected-index-changed="setClsFgVal(s,e)"
                    is-read-only="true">
                  </wj-combo-box>
                </div>
              </td>
              <%-- 날씨표시지역 --%>
              <th><s:message code="storeManage.weatherArea" /><em class="imp">*</em></th>
              <td>
                <div class="sb-select">
                  <wj-combo-box
                    id="areaCd"
                    ng-model="store.areaCd"
                    control="areaCdCombo"
                    items-source="_getComboData('areaCd')"
                    display-member-path="name"
                    selected-value-path="value"
                    is-editable="false"
                    initialized="_initComboBox(s)"
                    selected-index-changed="setAreaCdVal(s,e)">
                  </wj-combo-box>
                </div>
              </td>
            </tr>
            <tr>
              <%-- 직영구분 --%>
              <th><s:message code="storeManage.directManage" /><em class="imp">*</em></th>
              <td>
                <div class="sb-input">
                  <input type="radio" ng-model="store.directManageYn" value="Y" checked="checked"><label class="mr5"><s:message code="storeManage.directManageStore" /></label>
                  <input type="radio" ng-model="store.directManageYn" value="N"><label><s:message code="storeManage.merchantStore" /></label>
                </div>
              </td>
              <%-- 설치포스수 --%>
              <th><s:message code="storeManage.installPosCnt" /><em class="imp">*</em></th>
              <td>
                <input type="text" id="installPosCnt" ng-model="store.installPosCnt" class="sb-input w100" ng-click="addPos()"/>
              </td>
            </tr>
            <tr>
              <%-- 사업자번호 --%>
              <th><s:message code="storeManage.bizNo" /><em class="imp">*</em></th>
              <td colspan="3">
                <input type="text" id="bizNo1" ng-model="store.bizNo1" class="sb-input w10" maxlength="3" onkeyup="this.value=this.value.replace(/[^0-9]/g,'');"/>-
                <input type="text" id="bizNo2" ng-model="store.bizNo2" class="sb-input w10" maxlength="2" onkeyup="this.value=this.value.replace(/[^0-9]/g,'');"/>-
                <input type="text" id="bizNo3" ng-model="store.bizNo3" class="sb-input w15" maxlength="5" onkeyup="this.value=this.value.replace(/[^0-9]/g,'');"/>
                <input type="hidden" ng-model="store.beforeBizNo" />
                <%-- 중복체크 --%>
                <a id="btnChkBizNo" href="#" class="btn_grayS ml5" ng-click="chkBizNo()"><s:message code="storeManage.chk.duplicate" /></a><Br />
              </td>
            </tr>
            <tr>
              <%-- 전화번호 --%>
              <th><s:message code="storeManage.telNo" /><em class="imp">*</em></th>
              <td>
                <input type="text" id="telNo" ng-model="store.telNo" class="sb-input w100" maxlength="11" placeholder="<s:message code='storeManage.bizNo.comment' />" />
              </td>
              <%-- 팩스번호 --%>
              <th><s:message code="storeManage.faxNo" /></th>
              <td>
                <input type="text" id="faxNo" ng-model="store.faxNo" class="sb-input w100" maxlength="11" placeholder="<s:message code='storeManage.bizNo.comment' />" />
              </td>
            </tr>
            <tr>
              <%-- 이메일 --%>
              <th><s:message code="storeManage.emailAddr" /></th>
              <td colspan="3">
                <input type="text" id="emailAddr" ng-model="store.emailAddr" class="sb-input w100" maxlength="50"/>
              </td>
            </tr>
            <tr>
              <%-- 홈페이지 --%>
              <th><s:message code="storeManage.hmpgAddr" /></th>
              <td colspan="3">
                <input type="text" id="hmpgAddr" ng-model="store.hmpgAddr" class="sb-input w100" maxlength="30"/>
              </td>
            </tr>
            <tr>
              <%-- 주소 --%>
              <th><s:message code="storeManage.addr" /><em class="imp">*</em></th>
              <td colspan="3">
                <input type="text" id="postNo" ng-model="store.postNo" class="sb-input w80px" maxlength="5" placeholder="우편번호" onkeyup="this.value=this.value.replace(/[^0-9]/g,'');" readonly/>
                <a id="btnSrchAddr" href="#" class="btn_grayS" onclick="searchAddr()">
                  <s:message code="storeManage.srchAddr" />
                </a>
                <input type="text" id="latitude" ng-model="store.latitude" class="sb-input w130px" placeholder="위도" style="margin:4px 0px;" readonly/>
                <input type="text" id="longitude" ng-model="store.longitude" class="sb-input w130px" placeholder="경도" style="margin:4px 0px;" readonly/>
                <a id="btnOpenMap" href="#" class="btn_grayS" ng-click="openMap()">
                  <s:message code="storeManage.storeLocation" />
                </a>
                <br>
                <input type="text" id="addr" ng-model="store.addr" class="sb-input w100" maxlength="60" placeholder="주소1" style="margin:4px 0px;" readonly/>
                <input type="text" id="addrDtl" ng-model="store.addrDtl" class="sb-input w100" maxlength="60" placeholder="주소2"/>
              </td>
            </tr>
            <tr>
              <%-- 관리업체 --%>
              <th><s:message code="storeManage.manageVan" /><em class="imp">*</em></th>
              <td>
                <input type="text" name="manageVanNm" id="manageVanNm" ng-model="store.vanNm" class="sb-input w100" readonly="readonly" ng-click="searchManageVan()">
                <input type="hidden" name="manageVanCd" id="manageVanCd" ng-model="store.vanCd">
              </td>
                <%-- 대리점 --%>
              <th><s:message code="storeManage.agency" /><em class="imp">*</em></th>
              <td>
                <input type="text" name="agencyNm" id="agencyNm" ng-model="store.agencyNm" class="sb-input w100" readonly="readonly" ng-click="searchAgency()">
                <input type="hidden" name="agencyCd" id="agencyCd" ng-model="store.agencyCd">
              </td>
            </tr>
            <tr>
              <%-- 지사 --%>
              <th><s:message code="storeManage.branch" /></th>
              <td>
                <div class="sb-select">
                  <wj-combo-box
                    id="branchCd"
                    ng-model="store.branchCd"
                    control="branchCdCombo"
                    items-source="_getComboData('branchCd')"
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
            </tbody>
          </table>
          <%-- [1250 맘스터치] --%>
          <div id="divMomsEnvst" style="display: none;">
            <%-- 추가정보 --%>
            <h3 class="h3_tbl"><s:message code="storeManage.moms.member" /></h3>
              <table class="searchTbl">
                  <colgroup>
                      <col class="w15"/>
                      <col class="w35"/>
                      <col class="w15"/>
                      <col class="w35"/>
                  </colgroup>
                  <tbody>
                  <tr>
                      <%-- 팀별 --%>
                      <th><s:message code="storeManage.moms.momsTeam"/></th>
                      <td>
                          <div class="sb-select">
                              <wj-combo-box
                                      id="srchMomsTeamCombo"
                                      ng-model="store.momsTeam"
                                      items-source="_getComboData('momsTeamCombo')"
                                      display-member-path="name"
                                      selected-value-path="value"
                                      is-editable="false"
                                      initialized="_initComboBox(s)"
                                      control="srchMomsTeamCombo">
                              </wj-combo-box>
                          </div>
                      </td>
                      <%-- AC점포별 --%>
                      <th><s:message code="storeManage.moms.momsAcShop"/></th>
                      <td>
                          <div class="sb-select">
                              <wj-combo-box
                                      id="srchMomsAcShopCombo"
                                      ng-model="store.momsAcShop"
                                      items-source="_getComboData('momsAcShopCombo')"
                                      display-member-path="name"
                                      selected-value-path="value"
                                      is-editable="false"
                                      initialized="_initComboBox(s)"
                                      control="srchMomsAcShopCombo">
                              </wj-combo-box>
                          </div>
                      </td>
                  </tr>
                  <tr>
                    <%-- 지역구분 --%>
                    <th><s:message code="storeManage.moms.momsAreaFg"/></th>
                    <td>
                      <div class="sb-select">
                        <wj-combo-box
                                id="srchMomsAreaFgCombo"
                                ng-model="store.momsAreaFg"
                                items-source="_getComboData('momsAreaFgCombo')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)"
                                control="srchMomsAreaFgCombo">
                        </wj-combo-box>
                      </div>
                    </td>
                    <%-- 상권 --%>
                    <th><s:message code="storeManage.moms.momsCommercial"/></th>
                    <td>
                      <div class="sb-select">
                        <wj-combo-box
                                id="srchMomsCommercialCombo"
                                ng-model="store.momsCommercial"
                                items-source="_getComboData('momsCommercialCombo')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)"
                                control="srchMomsCommercialCombo">
                        </wj-combo-box>
                      </div>
                    </td>
                  </tr>
                  <tr>
                    <%-- 점포유형 --%>
                    <th><s:message code="storeManage.moms.momsShopType"/></th>
                    <td>
                      <div class="sb-select">
                        <wj-combo-box
                                id="srchMomsShopTypeCombo"
                                ng-model="store.momsShopType"
                                items-source="_getComboData('momsShopTypeCombo')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)"
                                control="srchMomsShopTypeCombo">
                        </wj-combo-box>
                      </div>
                    </td>
                    <%-- 매장관리타입 --%>
                    <th><s:message code="storeManage.moms.momsStoreManageType"/></th>
                    <td>
                      <div class="sb-select">
                        <wj-combo-box
                                id="srchMomsStoreManageTypeCombo"
                                ng-model="store.momsStoreManageType"
                                items-source="_getComboData('momsStoreManageTypeCombo')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)"
                                control="srchMomsStoreManageTypeCombo">
                        </wj-combo-box>
                      </div>
                    </td>
                  </tr>
                  </tbody>
              </table>
          </div>
          <%-- 비고 --%>
          <h3 class="h3_tbl"><s:message code="storeManage.remark" /></h3>
          <table class="searchTbl">
            <colgroup>
              <col class="w15" />
              <col class="w35" />
              <col class="w15" />
              <col class="w35" />
            </colgroup>
            <tbody>
            <tr>
              <%-- 시스템비고 --%>
              <th><s:message code="storeManage.systemRemark" /></th>
              <td colspan="3">
                <input id="sysRemark" ng-model="store.sysRemark" type="text" class="sb-input w100" maxlength="100"/>
              </td>
            </tr>
            <tr>
              <%-- 본사비고 --%>
              <th><s:message code="storeManage.hdRemark" /></th>
              <td colspan="3">
                <input id="hdRemark" ng-model="store.hdRemark" type="text" class="sb-input w100" maxlength="100"/>
              </td>
            </tr>
            <tr>
              <%-- 특이사항 --%>
              <th><s:message code="storeManage.uniqueRemark" /></th>
              <td colspan="3">
                <input id="remark" ng-model="store.remark" type="text" class="sb-input w100" maxlength="100"/>
              </td>
            </tr>
            </tbody>
          </table>

          <%-- 기타정보 --%>
          <h3 class="h3_tbl"><s:message code="storeManage.etcInfo" /></h3>
          <table class="searchTbl">
            <colgroup>
              <col class="w15" />
              <col class="w35" />
              <col class="w15" />
              <col class="w35" />
            </colgroup>
            <tbody>
            <tr>
                <%-- 매핑매장코드 --%>
              <th><s:message code="storeManage.mapStoreCd" /></th>
              <td>
                <input id="mapStoreCd" ng-model="store.mapStoreCd" type="text" class="sb-input w100" maxlength="20" onkeyup="this.value=this.value.replace(/[^A-Za-z0-9]/g,'');"/>
              </td>
              <td></td>
              <td></td>
            </tr>
            </tbody>
          </table>

          <%-- 추가설정 (매장등록시에만 보여줌) --%>
          <div id="additionalArea"> <!--todo 왜 조회했다가 신규등록하려면 안보이냐 이자식아 -->
            <h3 class="h3_tbl"><s:message code="storeManage.additionalSetting" /></h3>
            <table class="searchTbl">
              <colgroup>
                <col class="w15" />
                <col class="w35" />
                <col class="w15" />
                <col class="w35" />
              </colgroup>
              <tbody>
              <tr>
                <%-- 매장환경복사 --%>
                <th><s:message code="storeManage.copyStoreSetting" /></th>
                <td colspan="3" class="oh">
                  <div class="mr5 w50">
                    <%-- 본사 선택 --%>
                    <div class="sb-select">
                      <wj-combo-box
                        id="envHqOfficeCd"
                        ng-model="store.copyHqOfficeCd"
                        control="envHqOfficeCdCombo"
                        items-source="_getComboData('envHqOfficeCd')"
                        display-member-path="name"
                        selected-value-path="value"
                        is-editable="false"
                        initialized="_initComboBox(s)"
                        selected-index-changed="setEnvHqOfficeCdVal(s,e)">
                      </wj-combo-box>
                    </div>
                    <%-- 매장선택 --%>
                    <div class="sb-select">
<%--                      <wj-combo-box--%>
<%--                              id="envStoreCd"--%>
<%--                              ng-model="store.copyStoreCd"--%>
<%--                              control="envStoreCdCombo"--%>
<%--                              items-source="_getComboData('envStoreCd')"--%>
<%--                              display-member-path="name"--%>
<%--                              selected-value-path="value"--%>
<%--                              is-editable="false"--%>
<%--                              initialized="_initComboBox(s)"--%>
<%--                              selected-index-changed="setEnvStoreCdVal(s,e)">--%>
<%--                      </wj-combo-box>--%>
                    </div>
                      <input type="text" class="sb-input w100" ng-model="store.copyStoreInfo" readonly="readonly" ng-click="searchStore()"/>
                      <input type="text" id="envStoreCd" class="sb-input w100" ng-model="store.copyStoreCd" readonly="readonly" style="display: none"/>
                  </div>
                  <%-- 매장환경조회 버튼 --%>
                  <%--<a id="btnStoreSetting" href="#" class="btn_grayS mt35 mb15"><s:message code="storeManage.srchStoreSetting" /></a><br />--%>
                  <%-- 매장환경 체크박스  --%>
                  <div class="mr5 mt10">
                  <span class="chk mr10 pdb5 txtIn"><input type="checkbox" name="copyChk" id="storeEnvChk" value="storeEnv"/><%-- 매장환경 --%>
                    <label for="storeEnvChk" ><s:message code="storeManage.storeEnv" /></label>
                  </span>
                    <span class="chk mr10 pdb5 txtIn"><input type="checkbox" name="copyChk" id="posEnvChk" value="posEnv"/>  <%-- 포스환경 --%>
                    <label for="posEnvChk" ><s:message code="storeManage.posEnv" /></label>
                  </span>
                    <span class="chk mr10 pdb5 txtIn"><input type="checkbox" name="copyChk" id="foodEnvChk" value="foodEnv"/> <%-- 외식환경 --%>
                    <label for="foodEnvChk" ><s:message code="storeManage.foodEnv" /></label>
                  </span>
                    <span class="chk mr10 pdb5 txtIn"><input type="checkbox" name="copyChk" id="kitchenPrintChk" value="kitchenPrint"/> <%-- 주방프린터 --%>
                    <label for="kitchenPrintChk" ><s:message code="storeManage.kitchenPrint" /></label>
                  </span>
                    <span class="chk mr10 pdb5 txtIn" style="display: none;"><input type="checkbox" name="copyChk" id="productChk" value="product"/><%-- 상품 --%>
                    <label for="productChk" ><s:message code="storeManage.product" /></label>
                  </span>
                    <span class="chk mr10 pdb5 txtIn"><input type="checkbox" name="copyChk" id="salePriceChk" value="salePrice"/><%-- 판매가격 --%>
                    <label for="salePriceChk" ><s:message code="storeManage.salePrice" /></label>
                  </span>
                    <span class="chk mr10 pdb5 txtIn"><input type="checkbox" name="copyChk" id="supplyPriceChk" value="supplyPrice"/><%-- 공급가격 --%>
                    <label for="supplyPriceChk" ><s:message code="storeManage.supplyPrice" /></label>
                  </span>
                  <span class="chk mr10 pdb5 txtIn"><input type="checkbox" name="copyChk" id="posFnkeyChk" value="posFnkey"/><%-- 포스기능키 --%>
                    <label for="posFnkeyChk" ><s:message code="storeManage.posFnkey" /></label>
                  </span>
                  <span class="chk mr10 pdb5 txtIn"><input type="checkbox" name="copyChk" id="touchKeyChk" value="touchKey"/><%-- 터치키(판매) --%>
                    <label for="touchKeyChk" ><s:message code="storeManage.touchKey" /></label>
                  </span>
                  </div>
                </td>
              </tr>
              </tbody>
            </table>
          </div>

        </f:form>
      </div>

      <div class="btnSet">
        <%-- 저장 --%>
        <span><a href="#" class="btn_blue pd20" id="btnSave" ng-click="save()"><s:message code="cmm.save" /></a></span>
      </div>
    </div>

  </div>
</wj-popup>

<script>
  var startDate = "${sessionScope.sessionInfo.startDate}";
  var hqList = ${ccu.getHqOfficeList()};
</script>

<script type="text/javascript" src="/resource/solbipos/js/store/manage/storeManage/storeInfo.js?ver=20221104.01" charset="utf-8"></script>

<%-- 사업자번호 조회 --%>
<c:import url="/WEB-INF/view/application/layer/checkBizNo.jsp">
</c:import>

<%-- 본사 조회 --%>
<c:import url="/WEB-INF/view/application/layer/searchHq.jsp">
</c:import>

<%-- 관리업체 조회 --%>
<c:import url="/WEB-INF/view/application/layer/searchVan.jsp">
</c:import>

<%-- 대리점 조회 --%>
<c:import url="/WEB-INF/view/application/layer/searchAgency.jsp">
</c:import>

<%-- 설치포스수 추가 --%>
<c:import url="/WEB-INF/view/store/manage/storeManage/storePosAdd.jsp">
</c:import>

<%-- 매장 조회 --%>
<c:import url="/WEB-INF/view/application/layer/searchStore.jsp">
</c:import>

<%-- ERP 연동 매장 셋팅 팝업 --%>
<c:import url="/WEB-INF/view/store/manage/storeManage/erpStoreSet.jsp">
</c:import>

<%-- 매장환경설정코드 설명 팝업 --%>
<c:import url="/WEB-INF/view/store/manage/storeManage/envRemarkPop.jsp">
</c:import>