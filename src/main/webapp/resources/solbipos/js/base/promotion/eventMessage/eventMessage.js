/****************************************************************
 *
 * 파일명 : eventMessage.js
 * 설  명 : 이벤트문구출력관리 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.05.03     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 사용여부(전체)
var useYnAllFgData = [
    {"name":"전체","value":""},
    {"name":"사용","value":"Y"},
    {"name":"미사용","value":"N"}
];

// 응모권 출력여부(전체)
var ticketPrintYnAllFgData = [
    {"name":"전체","value":""},
    {"name":"응모권출력","value":"Y"},
    {"name":"응모권미출력","value":"N"}
];

// 재출력여부(전체)
var rePrintYnAllFgData = [
    {"name":"전체","value":""},
    {"name":"재출력가능","value":"Y"},
    {"name":"재출력불가","value":"N"}
];

// 출력조건(전체)
var printCondiAllFgData = [
    {"name":"전체","value":""},
    {"name":"무조건출력","value":"0"},
    {"name":"특정상품 판매시 출력","value":"1"}
];

// 사용여부
var useYnFgData = [
    {"name":"사용","value":"Y"},
    {"name":"미사용","value":"N"}
];

// 응모권 출력여부
var ticketPrintYnFgData = [
    {"name":"응모권출력","value":"Y"},
    {"name":"응모권미출력","value":"N"}
];

// 재출력여부
var rePrintYnFgData = [
    {"name":"재출력가능","value":"Y"},
    {"name":"재출력불가","value":"N"}
];

// 출력조건
var printCondiFgData = [
    {"name":"무조건출력","value":"0"},
    {"name":"특정상품 판매시 출력","value":"1"}
];

// 매장등록구분
var storeSelectExceptFgData=[
    {"name":"선택매장","value":"0"},
    {"name":"제외매장","value":"1"}
];

/**
 * 이벤트문구출력관리 그리드 생성
 */
app.controller('eventMessageCtrl', ['$scope', '$http','$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('eventMessageCtrl', $scope, $http, $timeout, true));

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("useYnAll", useYnAllFgData); // 사용여부
    $scope._setComboData("ticketPrintYnAll", ticketPrintYnAllFgData); // 응모권 출력여부
    $scope._setComboData("rePrintYnAll", rePrintYnAllFgData); // 재출력여부
    $scope._setComboData("printCondiAll", printCondiAllFgData); // 출력조건

    // 일자 달력 셋팅
    $scope.eventMessageDate = wcombo.genDateVal("#eventMessageDate", gvStartDate);
    $scope.eventMessageDate.isReadOnly = true;

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        $scope.useYnFgDataMap = new wijmo.grid.DataMap(useYnFgData, 'value', 'name'); // 사용여부
        $scope.ticketPrintYnFgDataMap = new wijmo.grid.DataMap(ticketPrintYnFgData, 'value', 'name'); // 응모권 출력여부
        $scope.rePrintYnFgDataMap = new wijmo.grid.DataMap(rePrintYnFgData, 'value', 'name'); // 재출력여부
        $scope.printCondiFgDataMap = new wijmo.grid.DataMap(printCondiFgData, 'value', 'name'); // 출력조건

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];

                if (col.binding === "msgCd") { // 출력문구코드
                    wijmo.addClass(e.cell, 'wijLink');
                    wijmo.addClass(e.cell, 'wj-custom-readonly');
                }
            }
        });

        // 그리드 클릭 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function (e) {
            var ht = s.hitTest(e);
            if (ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];
                var selectedRow = s.rows[ht.row].dataItem;
                if (col.binding === "msgCd") { // 출력문구코드 클릭
                    var params    = {};
                    params.msgCd = selectedRow.msgCd;
                    $scope._broadcast('eventMessageRegCtrl', params);
                }
            }
        });
    };

    //
    $scope.$on("eventMessageCtrl", function(event, data) {
        
        // 이벤트문구출력관리 목록 조회
        $scope.getEventMessageList();

        // 셋팅된 정보 초기화 밑 입력폼 숨기기
        $scope.openEventMessageReg();
        $("#eventMessageReg").css("display", "none");

        event.preventDefault();
    });

    // 이벤트문구출력관리 목록 조회
    $scope.getEventMessageList = function () {

        // 파라미터
        var params = {};
        params.printMessage1 = $scope.printMessage1;
        params.useYn = $scope.useYn;
        params.ticketPrintYn = $scope.ticketPrintYn;
        params.rePrintYn = $scope.rePrintYn;
        params.printCondiFg = $scope.printCondiFg;

        if($scope.isChecked) {
            params.eventMessageDate = wijmo.Globalize.format($scope.eventMessageDate.value, 'yyyyMMdd');
        }

        $scope._inquirySub("/base/promotion/eventMessage/list.sb", params, function () {});
    };

    // 일자 검색조건 사용/미사용 체크박스
    $scope.isChkDt = function () {
        if($scope.isChecked){
            $scope.eventMessageDate.isReadOnly = false;
        }else{
            $scope.eventMessageDate.isReadOnly = true;
        }
    };

    // 신규 등록 버튼 클릭 시, 이벤트문구 등록 영역 visible
    $scope.openEventMessageReg = function () {
        $scope._broadcast('eventMessageRegCtrl');
    };

}]);

/**
 * 이벤트문구출력관리 등록
 */
app.controller('eventMessageRegCtrl', ['$scope', '$http','$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('eventMessageRegCtrl', $scope, $http, $timeout, true));

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("useYn", useYnFgData); // 사용여부
    $scope._setComboData("ticketPrintYn", ticketPrintYnFgData); // 응모권 출력여부
    $scope._setComboData("rePrintYn", rePrintYnFgData); // 재출력여부
    $scope._setComboData("printCondiFg", printCondiFgData); // 출력조건
    $scope._setComboData("storeSelectExceptFg", storeSelectExceptFgData); // 매장등록구분

    // 적용조건 - 적용기간 셋팅
    $scope.isCheckedPeriod = false;
    var eventMessageStartDate = wcombo.genDateVal("#eventMessageStartDate", gvStartDate);
    var eventMessageEndDate = wcombo.genDateVal("#eventMessageEndDate", gvEndDate);

    // 매장권한에서는 적용매장 hidden
    if(orgnFg === "STORE") {
        $("#tblEventMessageStore").css("display" , "none");
    }else{
        $("#tblEventMessageStore").css("display" , "");
    }

    //
    $scope.$on("eventMessageRegCtrl", function(event, data) {

        // 등록 영역 Open
        $("#eventMessageReg").css("display", "");

        if(!isEmptyObject(data)){

            // 기존 이벤트문구 상세정보 셋팅
            $scope.setEventMessageDetail(data);

            // 적용상품 목록 조회
            if($scope.printCondiFgCombo.selectedValue === "0") {
                $("#tblEventMessageProd").css("display", "none");
            }else{
                $scope._pageView('selectProdGridCtrl', 1);
                $("#tblEventMessageProd").css("display", "");
            }

            // 매장권한인 경우, 적용매장 등록 불가
            if(orgnFg === "STORE") {
                $("#tblEventMessageStore").css("display", "none");
            }else{
                // 적용매장 목록 조회
                $scope._pageView('selectStoreGridCtrl', 1);
                // 신규 등록 시, 적용 매장 리스트 및 선택 불가(등록 후 가능)
                $("#tblEventMessageStore").css("display", "");
            }

        }else{
            // 초기화
            $scope.reset();

            // 신규 등록 시, 적용 매장 리스트 및 선택 불가(등록 후 가능)
            $("#tblEventMessageProd").css("display", "none");
            $("#tblEventMessageStore").css("display", "none");
        }
    });

    // 기존 이벤트문구출력물 상세정보 셋팅
    $scope.setEventMessageDetail = function(data){

        // hidden에 msgCd 값 가지고 있기(상품, 매장 리스트 저장 시 사용)
        $("#hdMsgCd").val(data.msgCd);

        var params = {};
        params.msgCd = data.msgCd;

        $.postJSON("/base/promotion/eventMessage/detail.sb", params, function(result) {

                var info = result.data;

                if(orgnFg === "STORE") { // 권한에 따른 등록버튼 제어
                    if (info.regFg === 'S') {
                        $("#btnSave").css("display", "");
                        $(".updownSet").css("display", "");
                    }else{
                        $("#btnSave").css("display", "none");
                        $(".updownSet").css("display", "none");
                    }
                }else{
                    $("#btnSave").css("display", "");
                    $(".updownSet").css("display", "");
                }

                // 적용기간
                if(info.startYmd != '00010101' && info.endYmd != '99991231'){
                    $("input:checkbox[id='chkPeriod']").prop("checked", true);
                    eventMessageStartDate.value = new Date(getFormatDate(info.startYmd, "-"));
                    eventMessageEndDate.value = new Date(getFormatDate(info.endYmd, "-"));
                    $scope.isCheckedPeriod = true;
                    $("#divChkPeriod").css("display", "");
                }else{
                    $("input:checkbox[id='chkPeriod']").prop("checked", false);
                    eventMessageStartDate.value = getCurDate('-');
                    eventMessageEndDate.value = getCurDate('-');
                    $scope.isCheckedPeriod = false;
                    $("#divChkPeriod").css("display", "none");
                }

                $scope.ticketPrintYnCombo.selectedValue = info.ticketPrintYn; // 응모권출력여부
                $scope.rePrintYnCombo.selectedValue = info.rePrintYn; // 재출력여부
                $scope.printCondiFgCombo.selectedValue = info.printCondiFg; // 출력조건
                $scope.useYnCombo.selectedValue = info.useYn; // 사용여부
                $("#printMessage1").val(info.printMessage1); // 출력문구

                if(orgnFg === "HQ"){
                    if(info.storeSelectExceptFg != '' && info.storeSelectExceptFg != null) {
                        $scope.storeSelectExceptFgCombo.selectedValue = info.storeSelectExceptFg; // 매장등록구분
                    }else{
                        $scope.storeSelectExceptFgCombo.selectedIndex = 0;
                    }
                }

            },
            function (result) {
                s_alert.pop(result.message);
                return false;
            }
        );
    };

    // 이벤트문구출력물 등록/수정
    $scope.saveEventMessage = function(){

        // 출력문구을(를) 입력하세요.
        if($("#printMessage1").val() === "" || $("#printMessage1").val() === null) {
            $scope._popMsg(messages["eventMessage.printMessage"] +  messages["cmm.require.text"]);
            return;
        }

        var params = {};

        params.msgCd = $("#hdMsgCd").val();
        params.startYmd = $("#chkPeriod").is(":checked") === true ? wijmo.Globalize.format(eventMessageStartDate.value, 'yyyyMMdd') : '00010101'; // 적용기간 시작일
        params.endYmd = $("#chkPeriod").is(":checked") === true ? wijmo.Globalize.format(eventMessageEndDate.value, 'yyyyMMdd') : '99991231'; // 적용기간 종료일
        params.ticketPrintYn = $scope.ticketPrintYnCombo.selectedValue; // 응모권출력여부
        params.rePrintYn = $scope.rePrintYnCombo.selectedValue; // 재출력여부
        params.printCondiFg = $scope.printCondiFgCombo.selectedValue; // 출력조건
        params.useYn = $scope.useYnCombo.selectedValue; // 사용여부
        params.printMessage1 = $("#printMessage1").val(); // 출력문구

        if(orgnFg === "HQ") {
            params.storeSelectExceptFg = $scope.storeSelectExceptFgCombo.selectedValue; // 매장등록구분
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $.postJSONArray("/base/promotion/eventMessage/save.sb", params, function (result) {
            if (result.status === "OK") {

                // 저장되었습니다.
                $scope._popMsg(messages["cmm.saveSucc"]);

                // 입력 폼 초기화
                $scope.reset();

                // 리스트 재조회
                $scope._broadcast('eventMessageCtrl');

                // 상세 재조회
                var params    = {};
                params.msgCd = result.data;
                $scope._broadcast('eventMessageRegCtrl', params);


            } else {
                $scope.$broadcast('loadingPopupInactive');
                $scope._popMsg(result.status);
                return false;
            }
        }, function (err) {
            $scope.$broadcast('loadingPopupInactive');
            $scope._popMsg(err.message);
        });

    };

    // 초기화
    $scope.reset = function(){

        // 이벤트출력물코드 초기화
        $("#hdMsgCd").val("");

        // 저장버튼 다시 보이도록
        $("#btnSave").css("display", "");

        // 적용기간
        $("input:checkbox[id='chkPeriod']").prop("checked", false); // 적용기간
        eventMessageStartDate.value = getCurDate('-');
        eventMessageEndDate.value = getCurDate('-');
        $scope.isCheckedPeriod = false;
        $("#divChkPeriod").css("display", "none");

        $scope.ticketPrintYnCombo.selectedIndex = 0; // 응모권출력여부
        $scope.rePrintYnCombo.selectedIndex = 0; // 재출력여부
        $scope.printCondiFgCombo.selectedIndex = 0; // 출력조건
        $scope.useYnCombo.selectedIndex = 0; // 사용여부
        $("#printMessage1").val(""); // 출력문구

        $scope.storeSelectExceptFgCombo.selectedIndex = 0; // 매장등록구분
    };

    // 적용조건 - 적용기간 입력 사용/미사용 체크박스
    $scope.isChkPeriod = function () {
        if($scope.isCheckedPeriod){
            $("#divChkPeriod").css("display", "");
        }else{
            $("#divChkPeriod").css("display", "none");
        }
    };

    // 적용조건 - 출력조건 선택에 따른 적용상품 disabled 여부
    $scope.setProd = function (s) {
        if($("#hdMsgCd").val() !== ""){
            if(s.selectedValue === "0") {
                $("#tblEventMessageProd").css("display", "none");
            }else{
                $scope._pageView('selectProdGridCtrl', 1);
                $("#tblEventMessageProd").css("display", "");
            }
        }
    };

    // 적용매장 - 매장등록구분 선택에 따른 버튼명 변경
    $scope.setStoreRegBtn = function(s){
        if(s.selectedValue === "0"){
            $("#btnStoreAdd").text(messages["promotion.storeAdd"]);
        }else{
            $("#btnStoreAdd").text(messages["promotion.exceptStoreAdd"]);
        }
    };
}]);

/**
 * 이벤트문구출력관리 적용상품 그리드
 */
app.controller('selectProdGridCtrl', ['$scope', '$http','$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('selectProdGridCtrl', $scope, $http, $timeout, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

    };

    //
    $scope.$on("selectProdGridCtrl", function(event, data) {

        // 적용상품 목록 조회
        $scope.getEventMessageProdList();
        event.preventDefault();
    });

    // 적용상품 목록 조회
    $scope.getEventMessageProdList = function(){

        var params = {};
        params.msgCd = $("#hdMsgCd").val();

        $scope._inquirySub("/base/promotion/eventMessage/getEventMessageProdList.sb", params, function () {});

    };

    // 적용상품 추가 팝업
    $scope.prodAdd = function () {
        $scope.eventMessageProdRegLayer.show(true);
        $scope._broadcast('eventMessageProdRegCtrl');
    };

    // 적용상품 저장
    $scope.prodSave = function () {

        // 파라미터 설정
        var params = new Array();

        // 조건수량이 수정된 내역이 있는지 체크
        if ($scope.flexSelectProdGrid.collectionView.itemsEdited.length <= 0) {
            $scope._popMsg(messages["cmm.not.modify"]);
            return false;
        }

        // 선택한 상품 또는 분류가 있는지 체크
        var chkCount = 0;
        for (var i = 0; i < $scope.flexSelectProdGrid.collectionView.itemsEdited.length; i++) {
            var item = $scope.flexSelectProdGrid.collectionView.itemsEdited[i];
            if(item.gChk === true) chkCount++;
        }

        if(chkCount === 0){
            $scope._popMsg("저장할 " + messages["eventMessage.product"] + "의 체크박스" + messages["eventMessage.chk.item"]); // 저장할 상품의 체크박스을(를) 선택하세요.
            return false;
        }

        for (var i = 0; i < $scope.flexSelectProdGrid.collectionView.itemsEdited.length; i++) {

            var item = $scope.flexSelectProdGrid.collectionView.itemsEdited[i];

            if (item.gChk === true && (item.saleQty === null || item.saleQty === "" || item.saleQty === "0" )) {
                $scope._popMsg(messages["eventMessage.chk.prodQty"]); // 선택한 상품의 조건수량을 반드시 입력하세요.
                return false;
            }

            if(item.gChk === true) {
                var obj = {};
                obj.status = "U";
                obj.msgCd = $("#hdMsgCd").val();
                obj.prodCd = item.prodCd;
                obj.saleQty =  item.saleQty;

                params.push(obj);
            }
        }

        $.postJSONArray("/base/promotion/eventMessage/saveEventMessageProd.sb", params, function (result) {
            if (result.status === "OK") {
                $scope._popMsg(messages["cmm.saveSucc"]); // 저장 되었습니다.

                // 적용상품 목록 재조회
                $scope.getEventMessageProdList();

                $scope.$broadcast('loadingPopupInactive');

            } else {
                $scope.$broadcast('loadingPopupInactive');
                $scope._popMsg(result.status);
                return false;
            }
        }, function (err) {
            $scope.$broadcast('loadingPopupInactive');
            $scope._popMsg(err.message);
        });
    };

    // 적용상품 삭제
    $scope.prodDel = function () {

        // 파라미터 설정
        var params = new Array();

        // 선택한 상품 또는 분류가 있는지 체크
        var chkCount = 0;
        for (var i=0; i< $scope.flexSelectProdGrid.collectionView.items.length; i++) {
            var item =  $scope.flexSelectProdGrid.collectionView.items[i];
            if(item.gChk === true) chkCount++;
        }

        if(chkCount === 0){
            $scope._popMsg("삭제할 " + messages["eventMessage.product"] + "의 체크박스" + messages["eventMessage.chk.item"]); //  삭제할 상품의 체크박스을(를) 선택하세요.
            return false;
        }

        for (var i=0; i< $scope.flexSelectProdGrid.collectionView.items.length; i++) {

            var item =  $scope.flexSelectProdGrid.collectionView.items[i];

            if(item.gChk === true) {
                var obj = {};
                obj.status = "D";
                obj.msgCd = $("#hdMsgCd").val();
                obj.prodCd = item.prodCd;

                params.push(obj);
            }
        }

        $.postJSONArray("/base/promotion/eventMessage/saveEventMessageProd.sb", params, function (result) {
            if (result.status === "OK") {
                $scope._popMsg(messages["cmm.delSucc"]); // 삭제 되었습니다.

                // 적용상품 목록 재조회
                $scope.getEventMessageProdList();

                $scope.$broadcast('loadingPopupInactive');

            } else {
                $scope.$broadcast('loadingPopupInactive');
                $scope._popMsg(result.status);
                return false;
            }
        }, function (err) {
            $scope.$broadcast('loadingPopupInactive');
            $scope._popMsg(err.message);
        });
    }
}]);

/**
 * 이벤트문구출력관리 적용매장 그리드
 */
app.controller('selectStoreGridCtrl', ['$scope', '$http','$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('selectStoreGridCtrl', $scope, $http, $timeout, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

    };

    //
    $scope.$on("selectStoreGridCtrl", function(event, data) {

        // 적용매장 목록 조회
        $scope.getEventMessageStoreList();
        event.preventDefault();
    });

    // 적용매장 목록 조회
    $scope.getEventMessageStoreList = function(){

        var params = {};
        params.msgCd = $("#hdMsgCd").val();

        $scope._inquirySub("/base/promotion/eventMessage/getEventMessageStoreList.sb", params, function () {});

    };

    // 적용매장 추가 팝업
    $scope.storeAdd = function () {
        $scope.eventMessageStoreRegLayer.show(true);
        $scope._broadcast('eventMessageStoreRegCtrl');
    };

    // 적용매장 삭제
    $scope.storeDel = function () {

        // 파라미터 설정
        var params = new Array();

        // 선택한 매장이 있는지 체크
        var chkCount = 0;
        for (var i = 0; i < $scope.flexSelectStoreGrid.collectionView.itemsEdited.length; i++) {
            var item = $scope.flexSelectStoreGrid.collectionView.itemsEdited[i];
            if(item.gChk === true) chkCount++;
        }

        if(chkCount === 0){
            $scope._popMsg("삭제할 " + messages["eventMessage.store"] + "의 체크박스" + messages["eventMessage.chk.item"]); // 삭제할 매장의 체크박스을(를) 선택하세요.
            return false;
        }

        for (var i = 0; i < $scope.flexSelectStoreGrid.collectionView.itemsEdited.length; i++) {

            var item = $scope.flexSelectStoreGrid.collectionView.itemsEdited[i];

            if(item.gChk === true) {
                var obj = {};
                obj.status = "D";
                obj.msgCd = $("#hdMsgCd").val();
                obj.storeCd = item.storeCd;

                params.push(obj);
            }
        }

        $.postJSONArray("/base/promotion/eventMessage/saveEventMessageStore.sb", params, function (result) {
            if (result.status === "OK") {
                $scope._popMsg(messages["cmm.delSucc"]); // 삭제 되었습니다.

                // 적용매장 목록 재조회
                $scope.getEventMessageStoreList();

                $scope.$broadcast('loadingPopupInactive');

            } else {
                $scope.$broadcast('loadingPopupInactive');
                $scope._popMsg(result.status);
                return false;
            }
        }, function (err) {
            $scope.$broadcast('loadingPopupInactive');
            $scope._popMsg(err.message);
        });
    };

}]);