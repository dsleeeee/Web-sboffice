<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="userId" value="${sessionScope.sessionInfo.userId}"/>

<div class="subCon" ng-controller="naverPlacePlusLinkCtrl">
    <div class="searchBar">
        <a href="#" class="open fl"><s:message code="naverPlacePlusLink.naverPlacePlusLink" /></a>
    </div>

    <div id="divLinkN">
        <div class="mt10" style="text-align:center;">
            <img src="/resource/solbipos/css/img/orderkit/banner_260123@2x.png" alt="" style="width:100%; height: 100%;"/>
            <button class="action-btn1 mt30" id="btn1" ng-click="btn1();"><s:message code="naverPlacePlusLink.naverPlacePlusLink" /></button>
        </div>
    </div>

    <div id="divLinkY">
        <div class="mt40" style="text-align:center;">
            <h3 class="title_box">
                연동완료 <img src="/resource/solbipos/css/img/check.png" alt="" style="width: 25px;"></br></br>
                네이버 스마트 플레이스+ 를</br>
                정상적으로 연동 완료 하였습니다.
            </h3>
            <div class="bottom-container">
                <div class="card-item">
                    <p class="description"><s:message code="naverPlacePlusLink.linkInfo" /> <img src="/resource/solbipos/css/img/info.png" alt="" style="width: 15px;"></p>
                    <table class="w100 mt30">
                        <colgroup>
                            <col class="w30"/>
                            <col class=""/>
                        </colgroup>
                        <tbody>
                        <%--매장 명--%>
                        <tr class="pdt10">
                            <td class="description" style="text-align: right"><s:message code="naverPlacePlusLink.storeNm" /></td>
                            <td class="pd5">
                                <input type="text" class="inSty2 w80" style="border-radius: 0.6em;" readonly="readonly" id="txtStoreNm">
                            </td>
                        </tr>
                        <%--플레이스 아이디--%>
                        <tr class="pd10">
                            <td class="description" style="text-align: right"><s:message code="naverPlacePlusLink.placeId" /></td>
                            <td class="pd5">
                                <input type="text" class="inSty2 w80" style="border-radius: 0.6em;" readonly="readonly" id="txtPlaceId">
                            </td>
                        </tr>
                        <%--연동 일시--%>
                        <tr class="pd10">
                            <td class="description" style="text-align: right"><s:message code="naverPlacePlusLink.linkDt" /></td>
                            <td class="pd5">
                                <input type="text" class="inSty2 w80" style="border-radius: 0.6em;" readonly="readonly" id="txtLinkDt">
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
            <button class="action-btn2" id="btn2" ng-click="btn2();"><s:message code="naverPlacePlusLink.withdraw" /></button>
        </div>
    </div>

</div>

<script type="text/javascript">

    // 사용자 ID
    var userId = "${userId}";
    sessionStorage.setItem("userId", userId);

    // 네.아.로 uniqueId
    var uniqueId = "${uniqueId}";
    // 약관동의 여부
    var agreeYn = ${agreeYn};
    // 네이버 플레이스 연동여부
    var linkYn = ${linkYn};
    // 연동 단계
    var linkStep = 0;

    // 연동 단계 파악
    // 0 : 네.아.로 로그인 미완료
    // 1 : 네.아.로 로그인 완료, 동의 미완료
    // 2 : 동의 완료, 매장연동 미완료
    // 3 : 매장연동 완료
    if (uniqueId != "") {
        linkStep = 1;

        if (agreeYn != null && agreeYn != undefined) {
            if (agreeYn.ownerMemberStatus == "REGULAR" && agreeYn.isJoinedMember == true) {
                var arr = agreeYn.agreedPlacePrivacyAgreementTypes;
                for (var i = 0; i < arr.length; i++) {
                    if (arr[i] === "SMARTPLACE_INTEGRATED_TERMS") {
                        linkStep = 2;

                        if (linkYn != null && linkYn != undefined) {
                            if (linkYn.placeId != null && linkYn.placeId != undefined && linkYn.placeId != "") {
                                linkStep = 3;
                            }
                        }
                    }
                }
            }
        }
    }

    // 화면 셋팅
    if (3 > linkStep) { // 매장연동 미완료 시
        $("#divLinkN").css("display", "");
        $("#divLinkY").css("display", "none");
    } else { // 매장연동 완료 시
        $("#divLinkN").css("display", "none");
        $("#divLinkY").css("display", "");
        // 정보 셋팅
        $("#txtStoreNm").val(linkYn.storeNm);
        $("#txtPlaceId").val(linkYn.placeId);
        $("#txtLinkDt").val((linkYn.regDateTime).replace('T', ' ').split('.')[0]);
    }

    // state 값 생성 (네이버로그인후 기존세션 확인을 위한 임의값)
    function generateState() {
        const bytes = new Uint8Array(16);
        crypto.getRandomValues(bytes);

        const base64 = btoa(String.fromCharCode(...bytes));
        return base64.replace(/\+/g, "-").replace(/\//g, "_").replace(/=+$/, "");
    }

    // 연동 후 재조회
    const bc = new BroadcastChannel('refresh_channel');
    bc.onmessage = (event) => {
        if (event.data === 'refresh') location.reload();
    };

    /*agreeYn = {
        "ownerMemberStatus": "REGULAR",
        "isJoinedMember": true,
        "isWithdrawing": false,
        "agreedPlacePrivacyAgreementTypes": [
            "PROMOTION_RECEIVE",
            "SMARTPLACE_BUSINESS_TERMS",
            "SMARTPLACE_INTEGRATED_TERMS",
            "AGENCY_BUSINESS_DATA_PROVISION"
        ],
        "isMyBizAgreed": true
    };*/

    /*{"ownerMemberStatus":"NONMEMBER","isJoinedMember":false,"isWithdrawing":false}*/

    //alert("연동단계 :" + linkStep + " / 네.아.로 아이디 :" + uniqueId + "/ 동의 :" + agreeYn.agreedPlacePrivacyAgreementTypes + " / 매장연동 :" + linkYn.placeId);

</script>

<script type="text/javascript" src="/resource/solbipos/js/naverPlace/naverPlace/naverPlacePlusLink/naverPlacePlusLink.js?ver=20260309.01" charset="utf-8"></script>

<style>
    /* 텍스트 스타일 */
    .title_box {
        font-size: 1.25rem;
        color: #212529;
        margin-bottom: 12px;
        font-weight: 700;
    }

    /* 전체 컨테이너: 유연한 배치를 위해 Flexbox 사용 */
    .bottom-container {
        display: flex;
        gap: 20px;
        padding: 40px;
        justify-content: center;
    }

    /* 박스 */
    .card-item {
        background: #ffffff;
        border: 1px solid #e0e0e0;
        border-radius: 12px;
        padding: 24px;
        width: 500px;
        transition: transform 0.2s ease;
    }

    /* 박스 안 텍스트 스타일 */
    .description {
        font-size: 1.0rem;
        color: #212529;
        margin-bottom: 12px;
        font-weight: 700;
    }

    /* 버튼 스타일: 블루 */
    .action-btn1 {
        background-color: #007bff;
        color: white;
        border: none;
        padding: 10px 20px;
        border-radius: 6px;
        cursor: pointer;
        font-size: 1rem;
        font-weight: 600;
        padding: 1.2em 2.8em;
        width: 50%;

    }

    /* 버튼 스타일: 블랙 */
    .action-btn2 {
        background-color: #000000;
        color: white;
        border: none;
        padding: 10px 20px;
        border-radius: 6px;
        cursor: pointer;
        font-size: 1rem;
        font-weight: 600;
        padding: 1.2em 2.8em;
        width: 50%;
    }
</style>