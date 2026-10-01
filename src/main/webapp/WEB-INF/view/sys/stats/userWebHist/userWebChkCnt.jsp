<%--
  ~ userWebChkCnt.jsp
  ~ 사용자웹사용이력 > [조회정보] 팝업 (ETC050 조회건수 탭 이관)
  ~   ① USER_ID 종합 (임계치 OR 조건)
  ~   ② USER_ID + IP별 상세
  ~   ③ 메뉴(URL)별 건수 (웹훅 URLD · HIST · 불일치 · IP목록 · 예외여부)
  ~
  ~    수정일      수정자      수정내용
  ~ ------------  ---------  ----------------------------------------
  ~  2026.09.22    김유승     최초생성 (ETC050_01.JSP chkcnt / chkcnt_ip 이관)
  ~  2026.09.23    김유승     로그인 유형별 · 로그인IP수 · 현재메뉴 불일치 · ③ 메뉴별 건수 추가
  ~  2026.09.28    김유승     ETC050 동기화 · 조회기간 달력 공통모듈(wcombo) · 컬럼명 s:message 적용
  --%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<wj-popup control="userWebChkCntLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:1280px;height:860px;" fade-in="false" fade-out="false">
    <div ng-controller="userWebChkCntCtrl">
        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="userWebChkCnt.title" />
            <a href="#" class="wj-hide btn_close" ng-click="close()"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body" style="height:795px; overflow-y:auto; overflow-x:hidden;">

            <%-- ══════════ ① USER_ID 종합 ══════════ --%>
            <table class="searchTbl">
                <colgroup>
                    <col class="w15" />
                    <col class="w35" />
                    <col class="w15" />
                    <col class="w35" />
                </colgroup>
                <tbody>
                <tr>
                    <%-- 조회기간 --%>
                    <th><s:message code="userWebChkCnt.searchDate" /></th>
                    <td>
                        <div class="sb-select">
                            <span class="txtIn"><input id="chkDay1" name="chkDay1" class="w110px" /></span>
                            <span class="rg">~</span>
                            <span class="txtIn"><input id="chkDay2" name="chkDay2" class="w110px" /></span>
                        </div>
                    </td>
                    <%-- USER_ID 검색 --%>
                    <th><s:message code="userWebChkCnt.userIdSearch" /></th>
                    <td>
                        <input type="text" class="sb-input w100" ng-model="chkUserId" placeholder="<s:message code="userWebChkCnt.userIdSearch.placeholder" />"
                               ng-keyup="$event.keyCode == 13 ? searchChkCnt() : null" />
                    </td>
                </tr>
                <tr>
                    <%-- ① 기간 조회 실건수 / ② 최근5분 실건수 --%>
                    <th><s:message code="userWebChkCnt.thChkToday" /></th>
                    <td>
                        <input type="text" class="sb-input w60px" style="text-align:right;" ng-model="thChkToday" /> <s:message code="userWebChkCnt.over" />
                        <span style="color:#999; margin:0 6px;">|</span>
                        <span style="color:#666;"><s:message code="userWebChkCnt.thChk5m" /></span>
                        <input type="text" class="sb-input w60px" style="text-align:right;" ng-model="thChk5m" /> <s:message code="userWebChkCnt.over" />
                    </td>
                    <%-- ③ 기간 국외IP 접근 / ④ 최근10분 국외IP --%>
                    <th><s:message code="userWebChkCnt.thBlkToday" /></th>
                    <td>
                        <input type="text" class="sb-input w60px" style="text-align:right;" ng-model="thBlkToday" /> <s:message code="userWebChkCnt.over" />
                        <span style="color:#999; margin:0 6px;">|</span>
                        <span style="color:#666;"><s:message code="userWebChkCnt.thBlk10m" /></span>
                        <input type="text" class="sb-input w60px" style="text-align:right;" ng-model="thBlk10m" /> <s:message code="userWebChkCnt.over" />
                    </td>
                </tr>
                <tr>
                    <%-- ⑥ 기간 로그인 시도 / ⑦ 최근5분 로그인 --%>
                    <th><s:message code="userWebChkCnt.thLgnToday" /></th>
                    <td>
                        <input type="text" class="sb-input w60px" style="text-align:right;" ng-model="thLgnToday" /> <s:message code="userWebChkCnt.over" />
                        <span style="color:#999; margin:0 6px;">|</span>
                        <span style="color:#666;"><s:message code="userWebChkCnt.thLgn5m" /></span>
                        <input type="text" class="sb-input w60px" style="text-align:right;" ng-model="thLgn5m" /> <s:message code="userWebChkCnt.over" />
                    </td>
                    <%-- 안내 --%>
                    <th><s:message code="userWebChkCnt.guide" /></th>
                    <td>
                        <span style="color:#777; font-size:11px;"><s:message code="userWebChkCnt.guide.sum" /></span>
                    </td>
                </tr>
                </tbody>
            </table>

            <div class="mt10 oh sb-select dkbr">
                <span class="fl" style="font-weight:bold; color:#1a5276; line-height:26px;"><s:message code="userWebChkCnt.sec.sum" /></span>
                <button class="btn_skyblue ml5 fr" ng-click="excelChkCnt()"><s:message code="cmm.excel.downCurrent" /></button>
                <button class="btn_blue ml5 fr" ng-click="resetChkCnt()"><s:message code="userWebChkCnt.reset" /></button>
                <button class="btn_blue ml5 fr" ng-click="searchChkCnt()"><s:message code="cmm.search" /></button>
            </div>

            <div class="mt5" style="font-size:12px; color:#333;" ng-bind-html="chkCntSummary"></div>

            <div class="w100 mt5">
                <div class="wj-gridWrap" style="height:250px; overflow-y:hidden; overflow-x:hidden;">
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            control="chkCntFlex"
                            initialized="initChkCntGrid(s,e)"
                            sticky-headers="true"
                            selection-mode="Row"
                            items-source="chkCntData"
                            item-formatter="_itemFormatter"
                            is-read-only="true">

                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.userId"/>"          binding="userId"       width="110" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.sum.risk"/>"        binding="risk"         width="140" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.sum.todayReal"/>"   binding="todayReal"    width="85"  is-read-only="true" align="right"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.sum.todayExc"/>"    binding="todayExc"     width="75"  is-read-only="true" align="right"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.sum.todayTot"/>"    binding="todayTot"     width="85"  is-read-only="true" align="right"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.sum.meReal"/>"      binding="meReal"       width="90"  is-read-only="true" align="right"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.sum.meExc"/>"       binding="meExc"        width="80"  is-read-only="true" align="right"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.sum.meTot"/>"       binding="meTot"        width="90"  is-read-only="true" align="right"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.sum.min5Real"/>"    binding="min5Real"     width="80"  is-read-only="true" align="right"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.sum.min5Tot"/>"     binding="min5Tot"      width="80"  is-read-only="true" align="right"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.sum.lgnTodaySucc"/>" binding="lgnTodaySucc" width="60"  is-read-only="true" align="right"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.sum.lgnTodayE1"/>"  binding="lgnTodayE1"   width="70"  is-read-only="true" align="right"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.sum.lgnTodayE2"/>"  binding="lgnTodayE2"   width="100" is-read-only="true" align="right"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.sum.lgnTodayE3"/>"  binding="lgnTodayE3"   width="85"  is-read-only="true" align="right"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.sum.lgnTodayE4"/>"  binding="lgnTodayE4"   width="80"  is-read-only="true" align="right"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.sum.lgnTodayE5"/>"  binding="lgnTodayE5"   width="90"  is-read-only="true" align="right"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.sum.lgnTodayTot"/>" binding="lgnTodayTot"  width="85"  is-read-only="true" align="right"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.sum.lgnIpCnt"/>"    binding="lgnIpCnt"     width="75"  is-read-only="true" align="right"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.sum.lgnMin5Tot"/>"  binding="lgnMin5Tot"   width="80"  is-read-only="true" align="right"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.sum.lgnMin5Err"/>"  binding="lgnMin5Err"   width="70"  is-read-only="true" align="right"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.sum.blkTodayCnt"/>" binding="blkTodayCnt"  width="85"  is-read-only="true" align="right"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.sum.blkMin10Cnt"/>" binding="blkMin10Cnt"  width="85"  is-read-only="true" align="right"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.sum.chkLastIp"/>"   binding="chkLastIp"    width="110" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.sum.chkIpCnt"/>"    binding="chkIpCnt"     width="70"  is-read-only="true" align="right"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.sum.ipHit"/>"       binding="ipHit"        width="75"  is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.sum.min5Ips"/>"     binding="min5Ips"      width="150" is-read-only="true" align="left"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.countryCode"/>"     binding="countryCode"  width="60"  is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.sum.blkLastF"/>"    binding="blkLastF"     width="135" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.sum.blockReason"/>" binding="blockReason"  width="150" is-read-only="true" align="left"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.sum.chkFirstTs"/>"  binding="chkFirstTs"   width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.sum.lgnFirstTs"/>"  binding="lgnFirstTs"   width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.sum.chkLastTs"/>"   binding="chkLastTs"    width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.sum.lgnLastTs"/>"   binding="lgnLastTs"    width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
            </div>

            <%-- ══════════ ② USER_ID + IP별 상세 ══════════ --%>
            <table class="searchTbl mt15">
                <colgroup>
                    <col class="w15" />
                    <col class="w35" />
                    <col class="w15" />
                    <col class="w35" />
                </colgroup>
                <tbody>
                <tr>
                    <%-- USER_ID --%>
                    <th><s:message code="userWebChkCnt.userId" /></th>
                    <td>
                        <input type="text" class="sb-input w100" ng-model="ipUserId"
                               ng-keyup="$event.keyCode == 13 ? searchChkCntIp() : null" />
                    </td>
                    <%-- 안내 --%>
                    <th><s:message code="userWebChkCnt.guide" /></th>
                    <td>
                        <span style="color:#777; font-size:11px;"><s:message code="userWebChkCnt.guide.ip" /></span>
                    </td>
                </tr>
                </tbody>
            </table>

            <div class="mt10 oh sb-select dkbr">
                <span class="fl" style="font-weight:bold; color:#1a5276; line-height:26px;"><s:message code="userWebChkCnt.sec.ip" /></span>
                <button class="btn_skyblue ml5 fr" ng-click="excelChkCntIp()"><s:message code="cmm.excel.downCurrent" /></button>
                <button class="btn_blue ml5 fr" ng-click="searchChkCntIp()"><s:message code="userWebChkCnt.searchIp" /></button>
                <button class="btn_blue ml5 fr" ng-click="openResumePopup()"><s:message code="userWebChkCnt.resume" /></button>
                <button class="btn_blue ml5 fr" ng-click="openPausePopup()"><s:message code="userWebChkCnt.pause" /></button>
            </div>

            <div class="mt5" style="font-size:12px; color:#333;" ng-bind-html="chkCntIpSummary"></div>

            <div class="w100 mt5">
                <div class="wj-gridWrap" style="height:200px; overflow-y:hidden; overflow-x:hidden;">
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            control="chkCntIpFlex"
                            initialized="initChkCntIpGrid(s,e)"
                            sticky-headers="true"
                            selection-mode="Row"
                            items-source="chkCntIpData"
                            item-formatter="_itemFormatter"
                            is-read-only="true">

                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.userId"/>"         binding="userId"       width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.ip.ipv"/>"         binding="ipv"          width="130" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.ip.gubun"/>"       binding="gubun"        width="170" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.ip.lgnTodayTot"/>" binding="lgnTodayTot"  width="80"  is-read-only="true" align="right"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.ip.lgnTodaySucc"/>" binding="lgnTodaySucc" width="60"  is-read-only="true" align="right"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.ip.lgnTodayE1"/>"  binding="lgnTodayE1"   width="70"  is-read-only="true" align="right"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.ip.lgnTodayE2"/>"  binding="lgnTodayE2"   width="100" is-read-only="true" align="right"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.ip.lgnTodayE3"/>"  binding="lgnTodayE3"   width="85"  is-read-only="true" align="right"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.ip.lgnTodayE4"/>"  binding="lgnTodayE4"   width="80"  is-read-only="true" align="right"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.ip.lgnTodayE5"/>"  binding="lgnTodayE5"   width="90"  is-read-only="true" align="right"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.ip.lgnMin5Tot"/>"  binding="lgnMin5Tot"   width="75"  is-read-only="true" align="right"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.ip.lgnMin5Err"/>"  binding="lgnMin5Err"   width="70"  is-read-only="true" align="right"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.ip.todayReal"/>"   binding="todayReal"    width="80"  is-read-only="true" align="right"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.ip.todayExc"/>"    binding="todayExc"     width="70"  is-read-only="true" align="right"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.ip.todayTot"/>"    binding="todayTot"     width="80"  is-read-only="true" align="right"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.ip.meReal"/>"      binding="meReal"       width="90"  is-read-only="true" align="right"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.ip.meExc"/>"       binding="meExc"        width="80"  is-read-only="true" align="right"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.ip.meTot"/>"       binding="meTot"        width="90"  is-read-only="true" align="right"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.ip.min5Real"/>"    binding="min5Real"     width="75"  is-read-only="true" align="right"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.ip.min5Tot"/>"     binding="min5Tot"      width="75"  is-read-only="true" align="right"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.ip.blkTodayCnt"/>" binding="blkTodayCnt"  width="85"  is-read-only="true" align="right"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.ip.blkMin10Cnt"/>" binding="blkMin10Cnt"  width="85"  is-read-only="true" align="right"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.ip.geoCnt"/>"      binding="geoCnt"       width="70"  is-read-only="true" align="right"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.ip.secCnt"/>"      binding="secCnt"       width="75"  is-read-only="true" align="right"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.countryCode"/>"    binding="countryCode"  width="60"  is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.ip.statCd"/>"      binding="statCd"       width="60"  is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.ip.loginOrgn"/>"   binding="loginOrgn"    width="65"  is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.ip.orgnCd"/>"      binding="orgnCd"       width="70"  is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.ip.vUserId"/>"     binding="vUserId"      width="90"  is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.ip.blkFirstF"/>"   binding="blkFirstF"    width="135" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.ip.blkLastF"/>"    binding="blkLastF"     width="135" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.ip.lgnLastTs"/>"   binding="lgnLastTs"    width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.ip.blockReason"/>" binding="blockReason"  width="160" is-read-only="true" align="left"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.ip.brwsrInfo"/>"   binding="brwsrInfo"    width="160" is-read-only="true" align="left"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
            </div>

            <%-- ══════════ ③ 메뉴(URL)별 건수 ══════════ --%>
            <div class="mt15 oh sb-select dkbr">
                <span class="fl" style="font-weight:bold; color:#1a5276; line-height:26px;"><s:message code="userWebChkCnt.sec.menu" /></span>
                <button class="btn_skyblue ml5 fr" ng-click="excelChkCntMenu()"><s:message code="cmm.excel.downCurrent" /></button>
            </div>

            <div class="mt5" style="font-size:12px; color:#333;" ng-bind-html="chkCntMenuSummary"></div>

            <div class="w100 mt5 mb20">
                <div class="wj-gridWrap" style="height:200px; overflow-y:hidden; overflow-x:hidden;">
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            control="chkCntMenuFlex"
                            initialized="initChkCntMenuGrid(s,e)"
                            sticky-headers="true"
                            selection-mode="Row"
                            items-source="chkCntMenuData"
                            item-formatter="_itemFormatter"
                            is-read-only="true">

                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.menu.url"/>"     binding="url"      width="360" is-read-only="true" align="left"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.menu.excRule"/>" binding="excRule"  width="200" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.menu.whTot"/>"   binding="whTot"    width="85"  is-read-only="true" align="right"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.menu.whReal"/>"  binding="whReal"   width="85"  is-read-only="true" align="right"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.menu.chkCnt"/>"  binding="chkCnt"   width="95"  is-read-only="true" align="right"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.menu.meCnt"/>"   binding="meCnt"    width="80"  is-read-only="true" align="right"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.menu.ipCnt"/>"   binding="ipCnt"    width="75"  is-read-only="true" align="right"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.menu.ipList"/>"  binding="ipList"   width="300" is-read-only="true" align="left"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.menu.firstF"/>"  binding="firstF"   width="135" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.menu.lastF"/>"   binding="lastF"    width="135" is-read-only="true" align="center"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
            </div>

            <%-- ══════════ ④ 일시정지 이력 ══════════ --%>
            <div class="mt15 oh sb-select dkbr">
                <span class="fl" style="font-weight:bold; color:#1a5276; line-height:26px;"><s:message code="userWebChkCnt.sec.pauseHist" /></span>
                <button class="btn_blue ml5 fr" ng-click="searchPauseHist()"><s:message code="cmm.search" /></button>
            </div>
            <div class="mt5" style="font-size:12px; color:#333;" ng-bind-html="pauseHistSummary"></div>   <%-- ← 추가 --%>

            <div class="w100 mt5 mb20">
                <div class="wj-gridWrap" style="height:150px; overflow-y:hidden; overflow-x:hidden;">
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            control="pauseHistFlex"
                            initialized="initPauseHistGrid(s,e)"
                            sticky-headers="true"
                            selection-mode="Row"
                            items-source="pauseHistData"
                            item-formatter="_itemFormatter"
                            is-read-only="true">
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.hist.procDt"/>"     binding="procDt"      width="135" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.userId"/>"          binding="userId"      width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.hist.procFgNm"/>"   binding="procFgNm"    width="75"  is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.hist.resultFg"/>"   binding="resultFg"    width="60"  is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.hist.statFg"/>"     binding="statFg"      width="80"  is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.hist.procId"/>"     binding="procId"      width="90"  is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.hist.chkPeriod"/>"  binding="chkPeriod"   width="150" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.sum.risk"/>"        binding="risk"        width="130" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.hist.remark"/>"     binding="remark"      width="260" is-read-only="true" align="left"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.hist.summaryTxt"/>" binding="summaryTxt"  width="600" is-read-only="true" align="left"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
            </div>

            <%-- ══════════ ⑤ 웹사용자정보변경이력 ══════════ --%>
            <div class="mt15 oh sb-select dkbr">
                <span class="fl" style="font-weight:bold; color:#1a5276; line-height:26px;"><s:message code="userWebChkCnt.sec.userInfoLog" /></span>
                <button class="btn_skyblue ml5 fr" ng-click="excelUserInfoLog()"><s:message code="cmm.excel.downCurrent" /></button>
                <button class="btn_blue ml5 fr" ng-click="searchUserInfoLog()"><s:message code="cmm.search" /></button>
            </div>
            <div class="mt5" style="font-size:12px; color:#333;" ng-bind-html="userInfoLogSummary"></div>
            <div class="w100 mt5 mb20">
                <div class="wj-gridWrap" style="height:200px; overflow-y:hidden; overflow-x:hidden;">
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            control="userInfoLogFlex"
                            initialized="initUserInfoLogGrid(s,e)"
                            sticky-headers="true"
                            selection-mode="Row"
                            items-source="userInfoLogData"
                            item-formatter="_itemFormatter"
                            is-read-only="true">
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.log.procDt"/>"        binding="procDt"        width="135" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.userId"/>"            binding="userId"        width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.log.procFgNm"/>"      binding="procFgNm"      width="60"  is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.log.pauseChg"/>"      binding="pauseChg"      width="95"  is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.log.statChg"/>"       binding="statChg"       width="190" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.log.chgItems"/>"      binding="chgItems"      width="220" is-read-only="true" align="left"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.log.modId"/>"         binding="modId"         width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.log.modDt"/>"         binding="modDt"         width="135" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.log.useYnChg"/>"      binding="useYnChg"      width="80"  is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.log.authGrpChg"/>"    binding="authGrpChg"    width="110" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.log.failCntChg"/>"    binding="failCntChg"    width="90"  is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.log.pwdChgYn"/>"      binding="pwdChgYn"      width="80"  is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.log.lastPwdChgDt"/>"  binding="lastPwdChgDt"  width="135" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebChkCnt.log.lastLoginDt"/>"   binding="lastLoginDt"   width="135" is-read-only="true" align="center"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
            </div>

        </div>
    </div>
</wj-popup>

<%-- ════ 일시정지 / 해제 확인 팝업 (비고 + 비밀번호) ════ --%>
<wj-popup control="userWebChkCntPauseLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:640px;" fade-in="false" fade-out="false">
    <div ng-controller="userWebChkCntPauseCtrl">
        <div class="wj-dialog-header wj-dialog-header-font">
            <span ng-show="mode !== 'R'"><s:message code="userWebChkCnt.pause.title" /></span>
            <span ng-show="mode === 'R'"><s:message code="userWebChkCnt.resume.title" /></span>
            <a href="#" class="wj-hide btn_close" ng-click="close()"></a>
        </div>
        <div class="wj-dialog-body">
            <table class="searchTbl">
                <colgroup><col class="w20" /><col class="w80" /></colgroup>
                <tbody>
                <tr>
                    <th><s:message code="userWebChkCnt.userId" /></th>
                    <td><b ng-style="{color: mode === 'R' ? '#1a5276' : '#c0392b'}">{{pauseUserId}}</b>
                        <span ng-show="mode !== 'R'" style="color:#777; font-size:11px; margin-left:8px;"><s:message code="userWebChkCnt.pause.guide" /></span>
                        <span ng-show="mode === 'R'" style="color:#777; font-size:11px; margin-left:8px;"><s:message code="userWebChkCnt.resume.guide" /></span></td>
                </tr>
                <tr>
                    <th><s:message code="userWebChkCnt.pause.summary" /></th>
                    <td><textarea class="sb-input w100" rows="5" readonly style="font-size:11px; color:#555; background:#f7f7f7;">{{pauseSummary}}</textarea></td>
                </tr>
                <tr>
                    <th><s:message code="userWebChkCnt.hist.remark" /></th>
                    <td><textarea class="sb-input w100" rows="3" maxlength="1000" ng-model="pauseRemark" placeholder="<s:message code="userWebChkCnt.pause.remark.placeholder" />"></textarea></td>
                </tr>
                <tr>
                    <th><s:message code="userWebChkCnt.pause.password" /></th>
                    <td><input type="password" class="sb-input w150px" ng-model="pausePassword" autocomplete="off"
                               ng-keyup="$event.keyCode == 13 ? doExec() : null" /></td>
                </tr>
                </tbody>
            </table>

            <div class="mt10 oh" style="text-align:center;">
                <button class="btn_blue" ng-show="mode !== 'R'" ng-click="doExec()"><s:message code="userWebChkCnt.pause.exec" /></button>
                <button class="btn_blue" ng-show="mode === 'R'" ng-click="doExec()"><s:message code="userWebChkCnt.resume.exec" /></button>
                <button class="btn_blue ml5" ng-click="close()"><s:message code="cmm.close" /></button>
            </div>
        </div>
    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/sys/stats/userWebHist/userWebChkCnt.js?ver=20260930.04" charset="utf-8"></script>