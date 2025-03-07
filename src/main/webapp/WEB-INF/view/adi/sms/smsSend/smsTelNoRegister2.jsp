<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<wj-popup id="wjSmsTelNoRegister2Layer" control="wjSmsTelNoRegister2Layer" show-trigger="Click" hide-trigger="Click" style="display:none;width:830px;height:690px;" fade-in="false" fade-out="false">
    <div ng-controller="smsTelNoRegister2Ctrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="smsTelNoRegister2.info"/>
            <a href="#" id="btn_close" class="wj-hide btn_close" ng-click="close()"></a>
        </div>

        <div style="width: 830px; height: 640px; overflow-x: auto; overflow-y: auto; border: 1px solid #e8e8e8;">
            <div class="subCon">
                <div class="w100 pd10">
                    <div class="oh">
                        <p class="tl s14 mt5 lh15 blue">통신서비스 이용증명원이란, 이용자 본인이 사용하는 발신번호임을 증명하는 서류로 발신번호가 가입된 통신사에 문의하여 증명서 발급해주세요.</br>
                        - 승인 처리 가능 기간 ( 평일 09:00 ~ 18:00 )</p>
                    </div>
                </div>
                <div class="w100 pd10">
                    <div class="oh">
                        <p class="tl s14 mt5 lh15">1) 스팸 규제 및 개인정보 처리 동의 안내</p>
                    </div>
                    <table class="tblType01 mt10">
                        <colgroup>
                            <col class="w30" />
                            <col class="w70" />
                        </colgroup>
                        <tbody>
                        <tr>
                            <td class="bl br">
                                개인정보취급방침
                            </td>
                            <td class="br">
                                <%-- 내용보기 --%>
                                <button class="btn_skyblue ml5 fl" id="btnContentPop1" ng-click="contentPop1()">
                                    <s:message code="smsTelNoRegister2.content" />
                                </button>
                            </td>
                        </tr>
                        <tr>
                            <td class="bl br">
                                광고 및 스팸 문자 정책
                            </td>
                            <td class="br">
                                <%-- 내용보기 --%>
                                <button class="btn_skyblue ml5 fl" id="btnContentPop2" ng-click="contentPop2()">
                                    <s:message code="smsTelNoRegister2.content" />
                                </button>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <div class="w100 pd10">
                    <div class="sb-select tr">
                        <span class="chk ml10">
                            <input type="checkbox" id="contentYn" name="contentYnChk" ng-model="contentYn">
                            <label for="contentYn">위 내용에 동의합니다.</label>
                        </span>
                    </div>
                </div>
                <div class="w100 pd10">
                    <div class="oh">
                        <p class="tl s14 mt5 lh15">2) 본인 인증 및 서류 첨부</p>
                    </div>
                    <table class="tblType01 mt10">
                        <colgroup>
                            <col class="w30" />
                            <col class="w70" />
                        </colgroup>
                        <tbody>
                        <tr>
                            <td class="bl br">
                                <%--<div class="impWrap">--%>
                                <div style="position:relative;">
                                    <%-- 발신번호 유형 --%>
                                    <em class="imp">*</em>&nbsp;&nbsp;&nbsp;<s:message code="smsTelNoRegister2.telFg" />
                                </div>
                            </td>
                            <td class="br">
                                <span class="sb-radio">
                                    <%-- 휴대폰번호 --%>
                                    <input type="radio" id="telNo" name="radioTelFg" value="0" ng-click="radioTelNoChange()" checked style="width:17px; height:17px; margin-right: 1px"/>
                                    <label><s:message code="smsTelNoRegister2.telNo"/></label>
                                    &nbsp;&nbsp;&nbsp;&nbsp;
                                    <%-- 유선번호 --%>
                                    <input type="radio" id="generalNo" name="radioTelFg" value="1" ng-click="radioTelNoChange()" style="width:17px; height:17px; margin-right: 1px;"/>
                                    <label><s:message code="smsTelNoRegister2.generalNo"/></label>
                                </span>
                            </td>
                        </tr>
                        <tr>
                            <td class="bl br">
                                <div style="position:relative;">
                                    <%-- 발신번호 명의자 --%>
                                    <em class="imp">*</em>&nbsp;&nbsp;&nbsp;<s:message code="smsTelNoRegister2.addSmsFg" />
                                </div>
                            </td>
                            <td class="br">
                                <span class="sb-radio">
                                    <%-- 대표자 본인 --%>
                                    <input type="radio" id="onwer" name="radioAddSmsFg" value="0" ng-click="radioTelNoChange()" checked style="width:17px; height:17px; margin-right: 1px;"/>
                                    <label><s:message code="smsTelNoRegister2.onwer"/></label>
                                    &nbsp;&nbsp;&nbsp;
                                    <%-- 기업명의 --%>
                                    <input type="radio" id="company" name="radioAddSmsFg" value="1" ng-click="radioTelNoChange()" style="width:17px; height:17px; margin-right: 1px;"/>
                                    <label><s:message code="smsTelNoRegister2.company"/></label>
                                </span>
                            </td>
                        </tr>
                        <tr>
                            <td class="bl br">
                                <div style="position:relative;">
                                    <%-- 휴대폰 본인인증 --%>
                                    <em class="imp">*</em>&nbsp;&nbsp;&nbsp;<s:message code="smsTelNoRegister2.vfTelNo" />
                                </div>
                            </td>
                            <td class="br">
                                <%-- 휴대폰 본인인증 --%>
                                <button class="btn_skyblue ml5 fl" id="btnYfTelNo" ng-click="vfTelNo()">
                                    <s:message code="smsTelNoRegister2.vfTelNo" />
                                </button>
                                <div style="display: none;">
                                    <input type="text" class="sb-input w100" id="srchCertId" ng-model="certId" readonly />
                                </div>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                    <f:form id="smsTelNoFileTelForm" name="smsTelNoFileTelForm" method="post" enctype="multipart/form-data">
                        <table class="tblType01">
                            <%-- 첨부파일 --%>
                            <tr id="trFile1" style="display: none;">
                                <td class="bl br" style="width:225px;">
                                    <div style="position:relative;">
                                        <%-- 서류 첨부 ① --%>
                                        <em class="imp">*</em>&nbsp;&nbsp;&nbsp;서류 첨부 ①
                                        <br>&nbsp;&nbsp;&nbsp;(png,jpg,gif 가능)
                                    </div>
                                </td>
                                <td class="br" style="width:525px;">
                                    <input class="form-control" type="file" id="smsTelNoFileTel1" name="smsTelNoFileTel1" accept="image/x-png, .jpg, .gif"/>
                                </td>
                            </tr>
                            <tr id="trFile2" style="display: none;">
                                <td class="bl br" style="width:225px;">
                                    <div style="position:relative;">
                                        <%-- 서류 첨부 ② --%>
                                        <em class="imp">*</em>&nbsp;&nbsp;&nbsp;서류 첨부 ②
                                        <br>&nbsp;&nbsp;&nbsp;(png,jpg,gif 가능)
                                    </div>
                                </td>
                                <td class="br" style="width:525px;">
                                    <input class="form-control" type="file" id="smsTelNoFileTel2" name="smsTelNoFileTel2" accept="image/x-png, .jpg, .gif"/>
                                </td>
                            </tr>
                            <tr id="trFile3" style="display: none;">
                                <td class="bl br" style="width:225px;">
                                    <div style="position:relative;">
                                        <%-- 서류 첨부 ③ --%>
                                        <em class="imp">*</em>&nbsp;&nbsp;&nbsp;서류 첨부 ③
                                        <br>&nbsp;&nbsp;&nbsp;(png,jpg,gif 가능)
                                    </div>
                                </td>
                                <td class="br" style="width:525px;">
                                    <input class="form-control" type="file" id="smsTelNoFileTel3" name="smsTelNoFileTel3" accept="image/x-png, .jpg, .gif"/>
                                </td>
                            </tr>
                        </table>
                    </f:form>
                    <table class="tblType01">
                        <colgroup>
                            <col class="w30" />
                            <col class="w70" />
                        </colgroup>
                        <tbody>
                        <tr>
                            <td class="bl br">
                                <div style="position:relative;">
                                    <%-- 신청자 이름 --%>
                                    <em class="imp">*</em>&nbsp;&nbsp;&nbsp;<s:message code="smsTelNoRegister2.addSmsUserNm" />
                                </div>
                            </td>
                            <td class="br">
                                <input type="text" class="sb-input w100" ng-model="addSmsUserNm" placeholder="상호가 아닌 신청자 명으로 기재해주세요." />
                            </td>
                        </tr>
                        <tr>
                            <td class="bl br">
                                <div style="position:relative;">
                                    <%-- 신청자 연락처 --%>
                                    <em class="imp">*</em>&nbsp;&nbsp;&nbsp;<s:message code="smsTelNoRegister2.addSmsTelNo" />
                                </div>
                            </td>
                            <td class="br">
                                <input type="text" class="sb-input w100" ng-model="addSmsTelNo" placeholder="- 없이 연락 받을 전화번호를 입력해주세요." />
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <div class="w100 pd10">
                    <div class="oh">
                        <p class="tl s14 mt5 lh15">- 등록하신 번호로 고객센터에서 연락드리며 통화 후 발신번호가 등록됩니다.</p>
                        <p class="tl s14 mt5 lh15">- 요청하신 서류는 근무일 1~5일 이내로 심사가 완료됩니다.</p>
                        <p class="tl s14 mt5 lh15">- 인증 요청하신 내역은 '부가서비스 > SMS관리 > SMS전송 > 발신번호관리 탭' 에서 확인 가능합니다.</p>
                    </div>
                </div>
                <div class="w100 pd10">
                    <div class="oh">
                        <p class="tl s14 mt5 lh15">3) 유형별 구비서류 안내</p>
                    </div>
                    <table class="tblType01 mt10">
                        <colgroup>
                            <col class="w20" />
                            <col class="w20" />
                            <col class="w60" />
                        </colgroup>
                        <tbody>
                        <tr>
                            <td class="bl br tc">
                               번호유형
                            </td>
                            <td class="br tc">
                               소유자
                            </td>
                            <td class="br tc">
                                <br>
                                구비 서류
                                <br>&nbsp;
                            </td>
                        </tr>
                        <tr>
                            <td class="bl br tc" rowspan="2">
                                휴대폰번호
                            </td>
                            <td class="br tc">
                                본인명의
                            </td>
                            <td class="br">
                                <br>
                                핸드폰 본인인증 ( 제출서류 없음 )
                                <br>&nbsp;
                            </td>
                        </tr>
                        <tr>
                            <td class="br tc">
                                가입명의
                            </td>
                            <td class="br">
                                <br>
                                본인인증 + 통신서비스 이용증명원+사업자등록증+재직증명서
                                <br>&nbsp;
                            </td>
                        </tr>
                        <tr>
                            <td class="bl br tc" rowspan="2">
                                유선 번호
                            </td>
                            <td class="br tc">
                                본인명의
                            </td>
                            <td class="br">
                                <br>
                                본인인증 + 통신서비스 이용증명원
                                <br>&nbsp;
                            </td>
                        </tr>
                        <tr>
                            <td class="br tc">
                                가입명의
                            </td>
                            <td class="br">
                                <br>
                                본인인증 + 통신서비스 이용증명원+사업자등록증+재직증명서
                                <br>&nbsp;
                            </td>
                        </tr>
                        <tr>
                            <td class="bl br tc">
                                통신사 별
                                <br>고객센터 안내
                            </td>
                            <td class="br" colspan="2">
                                <br>SK텔레콤 : 모바일서비스 (114,080-011-6000) 유선서비스 (080-816-2000)
                                <br><br>KT : 모바일서비스 (114,080-000-1618) 유선서비스(1588-0010)
                                <br><br>LG U+ : 모바일서비스 (1544-0010) 유선서비스 (101)
                                <br><br>세종 텔레콤 : 1688-1000
                                <br><br>SK 브로드밴드 : 080-8282-106
                                <br><br>SK 텔링크 : 114, 1599-0999
                                <br>&nbsp;
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>

            <%-- 저장 버튼 --%>
            <div class="tc mt10 mb20">
                <button id="funcSaveSmsTelNoRegister2" class="btn_blue">
                    <%-- 서류인증신청 --%>
                    <s:message code="smsTelNoRegister2.save" />
                </button>
            </div>
        </div>

    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/adi/sms/smsSend/smsTelNoRegister2.js?ver=20241120.01" charset="utf-8"></script>

<%-- SMS 개인정보취급방침 팝업 --%>
<c:import url="/WEB-INF/view/adi/sms/smsSend/contentPop1.jsp">
</c:import>

<%-- SMS 광고 및 스팸 문자 정책 팝업 --%>
<c:import url="/WEB-INF/view/adi/sms/smsSend/contentPop2.jsp">
</c:import>