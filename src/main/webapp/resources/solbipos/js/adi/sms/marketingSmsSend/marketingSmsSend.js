/****************************************************************
 *
 * 파일명 : marketingSmsSend.js
 * 설  명 : 마케팅용 SMS전송 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.08.10     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 전송자번호
var telNoComboData = [
    {"name":"선택","value":""}
];

// 검색기간 콤보박스
var periodData = [
    {"name":"기간 미사용","value":"all"},
    {"name":"가입일","value":"reg"},
    {"name":"최종방문일","value":"last"}
];

// 광고성 SMS전송 DropBoxDataMap
var marketingSmsGubunComboData = [
    {"name": "전체", "value": ""},
    {"name": "1개월전", "value": "1"},
    {"name": "2개월전", "value": "2"},
    {"name": "3개월전", "value": "3"},
    {"name": "4개월전", "value": "4"},
    {"name": "5개월전", "value": "5"},
    {"name": "6개월전", "value": "6"}
];

// 메세지관리 목록 내용 삽입
function msgShow(title, message) {
    var scope = agrid.getScope('marketingSmsSendCtrl');
    var params = {};
    params.title = title;
    params.message = message;
    scope.msgShow(params);
}

/**
 *  마케팅용 SMS전송 조회 그리드 생성
 */
app.controller('marketingSmsSendCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('marketingSmsSendCtrl', $scope, $http, false));

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("telNoCombo", telNoComboData); // 전송자번호
    $scope._setComboData("marketingSmsGubunCombo", marketingSmsGubunComboData); // 광고성 SMS전송
    $scope._setComboData("rMemberClass", memberClassList); // 회원등급
    $scope._setComboData("periodType", periodData); // 검색기간 콤보박스
    $scope._getComboDataQuery('299', 'membrCardFg', 'A');
    $scope._getComboDataQuery('072', 'emailRecvYn', 'A');
    $scope._getComboDataQuery('072', 'smsRecvYn', 'A');
    $scope._getComboDataQuery('055', 'gendrFg', 'A');
    $scope._getComboDataQuery('067', 'useYnAll', 'A');
    $scope._getComboDataQuery('076', 'weddingYn', 'A');
    $scope._getComboDataQuery('032', 'anvType', 'A');

    $scope.memberSaleList = [
        {value: "0", name: messages["marketingSmsSend.saveSale"]},
        {value: "1", name: messages["marketingSmsSend.saveSaleAmount"]}
    ];
    $scope.memberPointList = [
        {value: "0", name: messages["marketingSmsSend.membrPoint.add"] + messages["marketingSmsSend.membrPoint"]},
        {value: "1", name: messages["marketingSmsSend.membrPoint.use"] + messages["marketingSmsSend.membrPoint"]},
        {value: "2", name: messages["marketingSmsSend.membrPoint.adj"] + messages["marketingSmsSend.membrPoint"]},
        {value: "3", name: messages["marketingSmsSend.membrPoint.ava"] + messages["marketingSmsSend.membrPoint"]}
    ];

    $("#lblStoreNmInfo").text("(광고)" +  "");
    $("#lblMemoInfo").text("(무료수신거부)" +  "080-000-0000");
    $("#lblTxtByte").text("0");
    $("#lblTxtByteGubun").text("SMS");
    $("#lblSmsQty").text("0");

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];

                if (col.format === "date") {
                    e.cell.innerHTML = getFormatDate(e.cell.innerText);
                } else if (col.format === "dateTime") {
                    e.cell.innerHTML = getFormatDateTime(e.cell.innerText);
                } else if (col.format === "time") {
                    e.cell.innerHTML = getFormatTime(e.cell.innerText, 'hms');
                }

                // 수신자, 수신번호
                if (col.binding === "telNm" || col.binding === "telNo") {
                    var item = s.rows[e.row].dataItem;

                    // 값이 있으면 링크 효과
                    // C:회원조회, X:추가
                    // M:시스템, A:대리점, H:본사, S:매장
                    if (item[("rOgnFg")] !== 'X') {
                        wijmo.addClass(e.cell, 'wj-custom-readonly');
                        wijmo.setAttribute(e.cell, 'aria-readonly', true);

                        // Attribute 의 변경사항을 적용.
                        e.cell.outerHTML = e.cell.outerHTML;
                    }
                }

                // 주소, 생일, 결혼기념일
                if (col.binding === "addr" || col.binding === "birthday" || col.binding === "weddingDay") {
                    wijmo.addClass(e.cell, 'wj-custom-readonly');
                    wijmo.setAttribute(e.cell, 'aria-readonly', true);

                    // Attribute 의 변경사항을 적용.
                    e.cell.outerHTML = e.cell.outerHTML;
                }
            }
        });

        // 페이지 로드시 호출
        $scope.initPageSmsSend();
    };

    // <-- 검색 호출 -->
    $scope.$on("marketingSmsSendCtrl", function(event, data) {
        $scope.searchMarketingSmsSend();
        event.preventDefault();
    });

    $scope.searchMarketingSmsSend = function(){
        var params = {};

        $scope._inquiryMain("/adi/sms/marketingSmsSend/marketingSmsSend/getMarketingSmsSendList.sb", params, function() {
            // 회원은 조회 후 체크박스 체크
            for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
                $scope.flex.collectionView.items[i].gChk = true;
            }
            $scope.flex.refresh();
        }, false);
    };
    // <-- //검색 호출 -->

    // 페이지 로드시 호출
    $scope.initPageSmsSend = function() {
        // 발신번호 유무 체크
        $scope.tellNumChk();

        // 관리자/총판/본사/매장 명칭
        $scope.storeNmInfo();

        // 잔여수량
        $scope.restSmsQty();

        // 메세지그룹
        if(msgGrpColList == "") {
            $("#divMsgGrpPage").css("display", "none");
            $("#divMsgGrpPageAuth").css("display", "");
        } else {
            $("#divMsgGrpPage").css("display", "");
            $("#divMsgGrpPageAuth").css("display", "none");

            // 메세지그룹 탭
            $scope.msgGrpShow("00");
        }
    };

    // 발신번호 유무 체크
    $scope.tellNumChk = function() {
        var params = {};

        $scope._postJSONQuery.withOutPopUp('/adi/sms/smsSend/smsSend/getSmsTelNoComboList.sb', params, function (response) {
            if (response.data.data.list.length > 0) {
                var telNoList = response.data.data.list;
                $scope._setComboData("telNoCombo", telNoList); // 전송자번호

            } else {
                $scope._setComboData("telNoCombo", telNoComboData); // 전송자번호

                // 등록된 발신번호가 없습니다. <br/> 문자메세지 발송을 위해서는 <br/> 발신번호를 사전 등록하셔야 합니다. <br/> 등록을 진행하시겠습니까?
                if (confirm(messages["marketingSmsSend.telNoConfirm"])) {
                    // 발신번호 사전등록 팝업
                    $scope.wjSmsTelNoRegisterLayer.show(true);
                    event.preventDefault();

                } else {
                    // 화면
                    $("#divSmsSendPage").css("display", "none");
                    $("#divSmsSendPageAuth").css("display", "");
                }
            }
        });
    };

    // 발신번호 유무 체크 (발신번호 사전등록 팝업 닫을때 다시 체크)
    $scope.tellNumChkPop = function() {
        var params = {};

        $scope._postJSONQuery.withOutPopUp('/adi/sms/smsSend/smsSend/getSmsTelNoComboList.sb', params, function (response) {
            if (response.data.data.list.length > 0) {
                var telNoList = response.data.data.list;
                $scope._setComboData("telNoCombo", telNoList); // 전송자번호

            } else {
                $scope._setComboData("telNoCombo", telNoComboData); // 전송자번호

                // 화면
                $("#divSmsSendPage").css("display", "none");
                $("#divSmsSendPageAuth").css("display", "");
            }
        });
    };

    // 관리자/총판/본사/매장 명칭
    $scope.storeNmInfo = function() {
        var params = {};

        $scope._postJSONQuery.withOutPopUp('/adi/sms/smsSend/smsSend/getStoreNmList.sb', params, function (response) {
            var storeNmList = response.data.data.result;
            $scope.storeNmList = storeNmList;

            $("#lblStoreNmInfo").text("(광고)" +  storeNmList.storeNm);
        });
    };

    // 잔여수량
    $scope.restSmsQty = function() {
        var params = {};

        $scope._postJSONQuery.withOutPopUp('/adi/sms/smsSend/smsSend/getSmsQtyList.sb', params, function (response) {
            var smsQtyList = response.data.data.result;
            $scope.smsQtyList = smsQtyList;

            $("#lblSmsQty").text($scope.smsQtyList.smsQty);
        });
    };

    // 자동변환
    $scope.addMsg = function(str) {
        var msgContent = $("#messageContent").val();
        $("#messageContent").val(msgContent + str);

        // 바이트
        $scope.showByte();
    };

    // 바이트
    $scope.showByte = function() {
        $("#lblTxtByte").text($("#messageContent").val().getByteLength());

        if($("#messageContent").val().getByteLength() > 80) {
            $("#lblTxtByteGubun").text("LMS");
        } else {
            $("#lblTxtByteGubun").text("SMS");
        }
    };

    // <-- 그리드 행 추가 -->
    $scope.addRow = function() {
        // 파라미터 설정
        var params = {};
        params.status = "I";
        params.gChk = true;
        params.membrNo = "";
        params.telNm = "";
        params.telNo = "";
        params.addr = "";
        params.birthday = "";
        params.weddingDay = "";
        params.memo = "";
        params.rOgnFg = "X";
        params.rOgnCd = "";
        params.rUserId = "";

        // 추가기능 수행 : 파라미터
        $scope._addRow(params);
    };
    // <-- //그리드 행 추가 -->

    // <-- 그리드 행 삭제 -->
    $scope.del = function(){
        for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ){
            var item = $scope.flex.collectionView.items[i];

            if(item.gChk) {
                $scope.flex.collectionView.removeAt(i);
            }
        }
    };
    // <-- //그리드 행 삭제 -->

    // <-- 전송, 예약 -->
    // 전송, 예약
    $scope.smsSendReserve = function(reserveYn) {
        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if($scope.flex.collectionView.items[i].gChk) {
                params.push($scope.flex.collectionView.items[i]);
            }
        }
        if(params.length <= 0) {
            s_alert.pop(messages["cmm.not.select"]);
            return;
        }

        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if($scope.flex.collectionView.items[i].gChk) {
                if ($scope.flex.collectionView.items[i].telNo === "") {
                    $scope._popMsg(messages["marketingSmsSend.telNoBlank"]); // 수신번호를 입력해주세요.
                    return false;
                }
            }
        }

        // 잔여 수량
        var smsQty = $("#lblSmsQty").text();
        if(smsQty < 1) {
            $scope._popMsg(messages["marketingSmsSend.smsQtyAlert"]); // 전송가능한 수량이 없습니다.
            return;
        }
        if(smsQty < params.length) {
            $scope._popMsg(messages["marketingSmsSend.smsQtyOverAlert"]); // 수신자가 전송가능한 수량보다 많습니다.
            return;
        }

        // 0:전송, 1:예약
        if(reserveYn == "1") {
            var param = {};
            param.reserveYn = reserveYn;
            param.gubun = "marketingSmsSend";

            $scope.setSelectedSmsSend(param);
            $scope.wjSmsReserveLayer.show(true);
            event.preventDefault();
        } else {
            // 전송 저장
            $scope.smsSendSave(reserveYn, "");
        }
    };

    // 전송 저장
    $scope.smsSendSave = function(reserveYn, reserveDate) {
        // 바이트
        var msgType = "1"; // 메세지타입 1:SMS 2:LMS 3:MMS
        var byte = $("#lblTxtByte").text();
        if(byte > 80) {
            msgType = "2";
        }

        // SMS 전송수량은 5건 입니다. 전송하시겠습니까?
        var msg = messages["marketingSmsSend.smsSendConfirm"]  + " " + $("#lblSmsQty").text() + messages["marketingSmsSend.smsSendConfirm2"];
        if (confirm(msg)) {
            // 파라미터 설정
            var params = new Array();
            for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
                if($scope.flex.collectionView.items[i].gChk) {
                    // 내용
                    var messageContent = $("#messageContent").val();
                    if (messageContent == undefined) {
                        messageContent = "";
                    }
                    messageContent = messageContent.replaceAll("#이름#", $scope.flex.collectionView.items[i].telNm);
                    messageContent = messageContent.replaceAll("#추가사항#", $scope.flex.collectionView.items[i].memo);
                    var content = $("#lblStoreNmInfo").text() + messageContent + $("#lblMemoInfo").text();

                    $scope.flex.collectionView.items[i].reserveYn = reserveYn; // 0:전송, 1:예약
                    if (reserveYn == "1") {
                        $scope.flex.collectionView.items[i].sendDate = reserveDate; // 전송일시
                    }
                    $scope.flex.collectionView.items[i].title = $("#srchTitle").val(); // 제목
                    $scope.flex.collectionView.items[i].content = content; // 내용
                    $scope.flex.collectionView.items[i].msgType = msgType; // 메세지타입
                    $scope.flex.collectionView.items[i].cstNo = $scope.flex.collectionView.items[i].membrNo; // 회원번호
                    $scope.flex.collectionView.items[i].callback = $scope.telNoCombo; // 보내는사람 번호
                    $scope.flex.collectionView.items[i].phoneNumber = $scope.flex.collectionView.items[i].telNo.replaceAll("-", ""); // 받는사람 번호
                    $scope.flex.collectionView.items[i].rrOrgnFg = $scope.flex.collectionView.items[i].rOgnFg; // 받는사람 소속구분
                    $scope.flex.collectionView.items[i].rrOrgnCd = $scope.flex.collectionView.items[i].rOgnCd; // 받는사람 소속코드
                    $scope.flex.collectionView.items[i].rrUserId = $scope.flex.collectionView.items[i].rUserId; // 받는사람ID

                    params.push($scope.flex.collectionView.items[i]);
                }
            }

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._postJSONSave.withPopUp("/adi/sms/smsSend/smsSend/getSmsSendReserveSave.sb", params, function(){ $scope.allSearch() });
        }
    };

    // 재조회
    $scope.allSearch = function () {
        $("#srchTitle").val("");
        $("#messageContent").val("");
        $("#lblTxtByte").text("0");
        $("#lblTxtByteGubun").text("SMS");

        $scope._gridDataInit();

        // 잔여수량
        $scope.restSmsQty();
    };
    // <-- //전송, 예약 -->

    // 메세지그룹 탭
    $scope.msgGrpShow = function(msgGrpCd) {
        $("#divMsgComment").html("");

        // 탭 색상변경
        for(var i=0; i < msgGrpColList.length; i++) {
            if(msgGrpColList[i].msgGrpCd == msgGrpCd) {
                $("#msgGrpTab"+ msgGrpColList[i].msgGrpCd).addClass("on");
            } else {
                $("#msgGrpTab" + msgGrpColList[i].msgGrpCd).removeClass("on");
            }
        }

        // 메세지 관리
        var urlMsgManageDtl = "/adi/sms/msgManage/msgManage/getMsgManageDtlList.sb";
        // 최근이력
        if(msgGrpCd === "00") {
            urlMsgManageDtl = "/adi/sms/marketingSmsSend/marketingSmsSend/getMarketingSmsSendMsgManageDtlList.sb";
        }

        var params = {};
        params.msgGrpCd = msgGrpCd;

        $scope._postJSONQuery.withOutPopUp(urlMsgManageDtl, params, function(response) {
            var list = response.data.data.list;
            var innerHtml = "";

            if(list.length > 0) {
                for(var i=0; i < list.length; i++) {
                    innerHtml += "<div style=\"float:left; text-align:center; width:125px; height:140px; padding-top:10px; padding-right:10px;\">";
                    innerHtml += "<table>";
                    innerHtml += "<colgroup>";
                    innerHtml += "<col class=\"w100\" />";
                    innerHtml += "</colgroup>";
                    innerHtml += "<tbody>";
                    innerHtml += "<table>";
                    innerHtml += "<tr><td><input type=\"text\" class=\"sb-input-msg w100\" value=\""+ list[i].title +"\" readonly/></td></tr>";
                    innerHtml += "<tr style=\"height: 10px\"></tr>";
                    innerHtml += "<tr><td><textarea style=\"width:100%; height:90px; overflow-x:hidden; background-color: #EAF7FF\" onclick=\"msgShow(\'"+ list[i].title + "\', \'"+ list[i].message + "\')\" readonly>" + list[i].message + "</textarea></td></tr>";
                    innerHtml += "</table>";
                    innerHtml += "</div>";
                }
                $("#divMsgComment").html(innerHtml);
            }
        }, false);
    };

    // 메세지관리 목록 내용 삽입
    $scope.msgShow = function (data) {
        $("#srchTitle").val(data.title);
        $("#messageContent").val(data.message);

        // 바이트
        $scope.showByte();
    };

    // 선택
    $scope.selectedSmsSend;
    $scope.setSelectedSmsSend = function(store) {
        $scope.selectedSmsSend = store;
    };
    $scope.getSelectedSmsSend = function() {
        return $scope.selectedSmsSend;
    };

    // 발신번호추가
    $scope.telNoAdd = function() {
        $scope.wjSmsTelNoRegisterLayer.show(true);
        event.preventDefault();
    };

    // 화면 ready 된 후 설정
    angular.element(document).ready(function () {

        // 발신번호 사전등록 팝업 핸들러 추가
        $scope.wjSmsTelNoRegisterLayer.shown.addHandler(function (s) {
            setTimeout(function() {
                $scope._broadcast('smsTelNoRegisterCtrl', null);
            }, 50)
        });

        // SMS예약 팝업 핸들러 추가
        $scope.wjSmsReserveLayer.shown.addHandler(function (s) {
            setTimeout(function() {
                $scope._broadcast('smsReserveCtrl', $scope.getSelectedSmsSend());
            }, 50)
        });
    });

    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.regStoreShow = function () {
        $scope._broadcast('regStoreCtrl');
    };
    $scope.regUseStoreShow = function () {
        $scope._broadcast('regUseStoreCtrl');
    };

    // 확장조회 숨김/보임
    $scope.searchAddShowChange = function(){
        if( $("#tblSearchAddShow").css("display") === 'none') {
            $("#tblSearchAddShow").show();
        } else {
            $("#tblSearchAddShow").hide();
        }
    };
}]);


/**
 *  팝업 조회 그리드 생성
 */
app.controller('smsPopupCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('smsPopupCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };

    // <-- 검색 호출 -->
    $scope.$on("smsPopupCtrl", function(event, data) {
        // 닫았다 다시 호출하면 안떠서
        // 날짜 비교하여 팝업 띄우기
        if(Number(now) >= Number(startDate)){
            if(Number(endDate) >= Number(now)){
                if(getCookie("notSmsNotice")!="Y") {
                    $("#divDimmed").css('display', 'block');
                    $("#divPopup").css('display', 'block');
                }
            }
        }
        event.preventDefault();
    });
    // <-- //검색 호출 -->

    // 팝업 게시 기간
    var startDate = '20210201';
    var endDate = '29991231';

    // 오늘 날짜
    var date = new Date();
    var year = new String(date.getFullYear());
    var month = new String(date.getMonth()+1);
    var day = new String(date.getDate());

    // 한자리수일 경우 0을 채워준다.
    if(month.length == 1){
        month = "0" + month;
    }
    if(day.length == 1){
        day = "0" + day;
    }
    var now = year + "" + month + "" + day;

    // 1. 쿠키 만들기
    function setCookie(name, value, expiredays) {
        var today = new Date();
        today.setDate(today.getDate() + expiredays);
        document.cookie = name + '=' + escape(value) + '; path=/; expires=' + today.toGMTString() + ';'
    }

    //2. 쿠키 가져오기
    function getCookie(name){
        var cName = name + "=";
        var x = 0;
        while ( x <= document.cookie.length )
        {
            var y = (x+cName.length);
            if ( document.cookie.substring( x, y ) == cName )
            {
                if ( (endOfCookie=document.cookie.indexOf( ";", y )) == -1 )
                    endOfCookie = document.cookie.length;
                return unescape( document.cookie.substring( y, endOfCookie ) );
            }
            x = document.cookie.indexOf( " ", x ) + 1;
            if ( x == 0 )
                break;
        }
        return "";
    }

    //alert(getCookie("notSmsNotice"));

    // 날짜 비교하여 팝업 띄우기
    if(Number(now) >= Number(startDate)){
        if(Number(endDate) >= Number(now)){
            if(getCookie("notSmsNotice")!="Y") {
                $("#divDimmed").css('display', 'block');
                $("#divPopup").css('display', 'block');
            }
        }
    }

    // 오늘하루 열지않기
    $scope.closeToday = function() {
        setCookie('notSmsNotice','Y', 1);
        $("#divDimmed").css('display', 'none');
        $("#divPopup").css('display', 'none');
    };

    // 닫기
    $scope.close = function() {
        $("#divDimmed").css('display', 'none');
        $("#divPopup").css('display', 'none');
    };
}]);