/****************************************************************
 *
 * 파일명 : smsGeneralNoManage2.js
 * 설  명 : 일반번호 인증요청 처리2 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2024.11.15     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 소속구분
var orgnFgComboData = [
    {"name":"전체","value":""},
    {"name":"관리자","value":"M"},
    {"name":"총판/대리점","value":"A"},
    {"name":"본사","value":"H"},
    {"name":"매장","value":"S"}
];
// 처리구분
var addProcFgComboData = [
    {"name":"전체","value":""},
    {"name":"접수","value":"0"},
    {"name":"처리중","value":"1"},
    {"name":"완료","value":"2"},
    {"name":"반려","value":"3"}
];
// 본인인증 여부
var vfYnData = [
    {"name":"완료","value":"Y"},
    {"name":"미완료","value":"N"}
];

/**
 *  일반번호 인증요청 처리2 팝업 조회 그리드 생성
 */
app.controller('smsGeneralNoManage2Ctrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('smsGeneralNoManage2Ctrl', $scope, $http, false));

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("addProcFgCombo", addProcFgComboData); // 처리구분

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.orgnFgDataMap = new wijmo.grid.DataMap(orgnFgComboData, 'value', 'name'); // 소속구분
        $scope.vfYnDataMap = new wijmo.grid.DataMap(vfYnData, 'value', 'name'); // 본인인증 여부
        $scope.addProcFgDataMap = new wijmo.grid.DataMap(addProcFgComboData, 'value', 'name'); // 처리구분

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

                // 다운로드, 미리보기
                if (col.binding === "download1" || col.binding === "preview1") {
                    var item = s.rows[e.row].dataItem;
                    // 값이 있으면 링크 효과
                    if (nvl(item[("fileName1")], '') !== '') {
                        wijmo.addClass(e.cell, 'wijLink');
                    }
                }

                // 다운로드, 미리보기
                if (col.binding === "download2" || col.binding === "preview2") {
                    var item = s.rows[e.row].dataItem;
                    // 값이 있으면 링크 효과
                    if (nvl(item[("fileName2")], '') !== '') {
                        wijmo.addClass(e.cell, 'wijLink');
                    }
                }

                // 다운로드, 미리보기
                if (col.binding === "download3" || col.binding === "preview3") {
                    var item = s.rows[e.row].dataItem;
                    // 값이 있으면 링크 효과
                    if (nvl(item[("fileName3")], '') !== '') {
                        wijmo.addClass(e.cell, 'wijLink');
                    }
                }
            }
        });

        // 그리드 선택 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function(e) {
            var ht = s.hitTest(e);
            if( ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];
                var selectedRow = s.rows[ht.row].dataItem;

                // 다운로드 클릭시 상세정보 조회
                if (col.binding === "download1") {
                    // 값이 있으면 링크
                    if (nvl(selectedRow[("fileName1")], '') !== '') {
                        // 다운로드
                        smsGeneralNo_download2(selectedRow.fileName1);
                    }
                }

                // 다운로드 클릭시 상세정보 조회
                if (col.binding === "download2") {
                    // 값이 있으면 링크
                    if (nvl(selectedRow[("fileName2")], '') !== '') {
                        // 다운로드
                        smsGeneralNo_download2(selectedRow.fileName2);
                    }
                }

                // 다운로드 클릭시 상세정보 조회
                if (col.binding === "download3") {
                    // 값이 있으면 링크
                    if (nvl(selectedRow[("fileName3")], '') !== '') {
                        // 다운로드
                        smsGeneralNo_download2(selectedRow.fileName3);
                    }
                }

                // 미리보기 클릭시 상세정보 조회
                if (col.binding === "preview1") {
                    if (nvl(selectedRow[("fileName1")], '') !== '') {
                        var params = {};
                        params.orgnCd = selectedRow.orgnCd;
                        params.userId = selectedRow.userId;
                        params.certId = selectedRow.certId;
                        params.gubun = "1";
                        $scope._broadcast('smsPreviewCtrl', params);
                        $scope.wjSmsPreviewLayer.show(true);
                        event.preventDefault();
                    }
                }

                // 미리보기 클릭시 상세정보 조회
                if (col.binding === "preview2") {
                    // 값이 있으면 링크
                    if (nvl(selectedRow[("fileName2")], '') !== '') {
                        var params = {};
                        params.orgnCd = selectedRow.orgnCd;
                        params.userId = selectedRow.userId;
                        params.certId = selectedRow.certId;
                        params.gubun = "2";
                        $scope._broadcast('smsPreviewCtrl', params);
                        $scope.wjSmsPreviewLayer.show(true);
                        event.preventDefault();
                    }
                }

                // 미리보기 클릭시 상세정보 조회
                if (col.binding === "preview3") {
                    // 값이 있으면 링크
                    if (nvl(selectedRow[("fileName3")], '') !== '') {
                        var params = {};
                        params.orgnCd = selectedRow.orgnCd;
                        params.userId = selectedRow.userId;
                        params.certId = selectedRow.certId;
                        params.gubun = "3";
                        $scope._broadcast('smsPreviewCtrl', params);
                        $scope.wjSmsPreviewLayer.show(true);
                        event.preventDefault();
                    }
                }
            }
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("smsGeneralNoManage2Ctrl", function(event, data) {
        $scope.searchSmsGeneralNoManage();
        event.preventDefault();
    });

    $scope.searchSmsGeneralNoManage = function(){
        var params = {};

        $scope._inquiryMain("/adi/sms/smsTelNoManage/smsGeneralNoManage2/getSmsGeneralNoManage2List.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // <-- 저장 호출 -->
    // 저장
    $("#funcSaveSmsGeneralNoManage2").click(function(e){
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            var rowCount = i + 1;
            var rowContent = "[" + rowCount + "번째의 " + $scope.flex.collectionView.items[i].orgnCd + "/" + $scope.flex.collectionView.items[i].orgnNm + "] ";

            $scope.flex.collectionView.items[i].status = "I";

            // 처리구분 수정했을때
            if(nvl($scope.flex.collectionView.items[i].addProcFg, "") !== nvl($scope.flex.collectionView.items[i].backAddProcFg, "")) {
                // 처리구분 반려시
                if($scope.flex.collectionView.items[i].addProcFg === "3") {
                    if(nvl($scope.flex.collectionView.items[i].returnRemark, "") === "") {
                        $scope._popMsg(rowContent + messages["smsGeneralNoManage2.returnRemarkBlank"]); // 처리구분 반려시 반려사유를 입력해주세요.
                        return false;
                    }
                }

                // 발신번호 필수입력
                if(nvl($scope.flex.collectionView.items[i].telNo, "") === "") {
                    $scope._popMsg(rowContent + messages["smsGeneralNoManage2.telNoBlank"]); // 발신번호를 입력해주세요.
                    return false;
                } else {
                    // 숫자만 입력
                    var numChkexp = /[^0-9]/g;
                    if (numChkexp.test($scope.flex.collectionView.items[i].telNo)) {
                        $scope._popMsg(rowContent + messages["smsGeneralNoManage2.telNoInChk"]); // 발신번호 숫자만 입력해주세요.
                        return false;
                    }
                }

                // 수정된 내역이 있는지 체크
                $scope.flex.collectionView.items[i].status = "U";
            }

            // 발신번호 수정했을때
            if(nvl($scope.flex.collectionView.items[i].telNo, "") !== nvl($scope.flex.collectionView.items[i].backTelNo, "")) {
                // 발신번호 필수입력
                if(nvl($scope.flex.collectionView.items[i].telNo, "") === "") {
                    $scope._popMsg(rowContent + messages["smsGeneralNoManage2.telNoBlank"]); // 발신번호를 입력해주세요.
                    return false;
                } else {
                    // 숫자만 입력
                    var numChkexp = /[^0-9]/g;
                    if (numChkexp.test($scope.flex.collectionView.items[i].telNo)) {
                        $scope._popMsg(rowContent + messages["smsGeneralNoManage2.telNoInChk"]); // 발신번호 숫자만 입력해주세요.
                        return false;
                    }
                }

                // 수정된 내역이 있는지 체크
                $scope.flex.collectionView.items[i].status = "U";
            }

            // 통화일시 수정했을때
            if(nvl($scope.flex.collectionView.items[i].telDt, "") !== nvl($scope.flex.collectionView.items[i].backTelDt, "")) {
                // 발신번호 필수입력
                if(nvl($scope.flex.collectionView.items[i].telNo, "") === "") {
                    $scope._popMsg(rowContent + messages["smsGeneralNoManage2.telNoBlank"]); // 발신번호를 입력해주세요.
                    return false;
                } else {
                    // 숫자만 입력
                    var numChkexp = /[^0-9]/g;
                    if (numChkexp.test($scope.flex.collectionView.items[i].telNo)) {
                        $scope._popMsg(rowContent + messages["smsGeneralNoManage2.telNoInChk"]); // 발신번호 숫자만 입력해주세요.
                        return false;
                    }
                }

                // 수정된 내역이 있는지 체크
                $scope.flex.collectionView.items[i].status = "U";
            }

            // 반려사유 수정했을때
            if(nvl($scope.flex.collectionView.items[i].returnRemark, "") !== nvl($scope.flex.collectionView.items[i].backReturnRemark, "")) {
                // 발신번호 필수입력
                if(nvl($scope.flex.collectionView.items[i].telNo, "") === "") {
                    $scope._popMsg(rowContent + messages["smsGeneralNoManage2.telNoBlank"]); // 발신번호를 입력해주세요.
                    return false;
                } else {
                    // 숫자만 입력
                    var numChkexp = /[^0-9]/g;
                    if (numChkexp.test($scope.flex.collectionView.items[i].telNo)) {
                        $scope._popMsg(rowContent + messages["smsGeneralNoManage2.telNoInChk"]); // 발신번호 숫자만 입력해주세요.
                        return false;
                    }
                }

                // 수정된 내역이 있는지 체크
                $scope.flex.collectionView.items[i].status = "U";
            }

            // 비고 수정했을때
            if(nvl($scope.flex.collectionView.items[i].remark, "") !== nvl($scope.flex.collectionView.items[i].backRemark, "")) {
                // 발신번호 필수입력
                if(nvl($scope.flex.collectionView.items[i].telNo, "") === "") {
                    $scope._popMsg(rowContent + messages["smsGeneralNoManage2.telNoBlank"]); // 발신번호를 입력해주세요.
                    return false;
                } else {
                    // 숫자만 입력
                    var numChkexp = /[^0-9]/g;
                    if (numChkexp.test($scope.flex.collectionView.items[i].telNo)) {
                        $scope._popMsg(rowContent + messages["smsGeneralNoManage2.telNoInChk"]); // 발신번호 숫자만 입력해주세요.
                        return false;
                    }
                }

                // 수정된 내역이 있는지 체크
                $scope.flex.collectionView.items[i].status = "U";
            }
        }

        $scope._popConfirm(messages["cmm.choo.save"], function() {
            // 파라미터 설정
            var params = new Array();
            for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
                // 수정된 내역이 있을때만
                if(nvl($scope.flex.collectionView.items[i].status, "") === "U") {
                    params.push($scope.flex.collectionView.items[i]);
                }
            }
            if (params.length <= 0) {
                $scope._popMsg(messages["cmm.not.modify"]);
                return;
            }

            // 신규 발신번호 중복체크
            $scope._postJSONSave.withPopUp("/adi/sms/smsTelNoManage/smsGeneralNoManage/getSmsGeneralNoManageCount.sb", params, function (response) {
                var result = response.data.data;
                if(result < 1) {
                    $scope.saveSmsGeneralNoManage(params);
                } else {
                    $scope._popMsg(messages["smsGeneralNoManage2.sameTelNoAlert"]); // 기존에 등록된 전화번호입니다.
                    return false;
                }
            });
        });
    });

    $scope.saveSmsGeneralNoManage = function(params){
        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._postJSONSave.withPopUp("/adi/sms/smsTelNoManage/smsGeneralNoManage2/getSmsGeneralNoManage2Save.sb", params, function(){
            $scope.searchSmsGeneralNoManage();
        });
    };
    // <-- //저장 호출 -->

    // 팝업 닫기
    $scope.close = function() {
        $scope.wjSmsGeneralNoManage2Layer.hide();
        event.preventDefault();
    };
}]);