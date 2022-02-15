/****************************************************************
 *
 * 파일명 : smsGeneralNoManage.js
 * 설  명 : 일반번호 인증요청 처리 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.01.27     김설아      1.0
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
// 처리구분 all
var addProcFgAllComboData = [
    {"name":"전체","value":""},
    {"name":"접수","value":"0"},
    {"name":"처리중","value":"1"},
    {"name":"완료","value":"2"},
    {"name":"반려","value":"3"}
];
// 처리구분
var addProcFgComboData = [
    {"name":"접수","value":"0"},
    {"name":"처리중","value":"1"},
    {"name":"완료","value":"2"},
    {"name":"반려","value":"3"}
];
// 자료생성구분
var addFgComboData = [
    {"name":"전체","value":""},
    {"name":"FAX","value":"0"},
    {"name":"FILE","value":"1"}
];

/**
 *  일반번호 인증요청 처리 팝업 조회 그리드 생성
 */
app.controller('smsGeneralNoManageCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('smsGeneralNoManageCtrl', $scope, $http, false));

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("addProcFgCombo", addProcFgAllComboData); // 처리구분

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.orgnFgDataMap = new wijmo.grid.DataMap(orgnFgComboData, 'value', 'name'); // 소속구분
        $scope.addProcFgDataMap = new wijmo.grid.DataMap(addProcFgComboData, 'value', 'name'); // 처리구분
        $scope.addFgDataMap = new wijmo.grid.DataMap(addFgComboData, 'value', 'name'); // 자료생성구분

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

                // 다운로드
                if (col.binding === "download") {
                    var item = s.rows[e.row].dataItem;
                    // 값이 있으면 링크 효과
                    if (nvl(item[("addFg")], '') == '1') {
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

                // 다운로드 클릭시 상세정보 조회
                if ( col.binding === "download") {
                    var selectedRow = s.rows[ht.row].dataItem;
                    // 값이 있으면 링크
                    if (nvl(selectedRow[("addFg")], '') == '1') {
                        // 다운로드
                        smsGeneralNo_download(selectedRow.fileName);
                    }
                }
            }
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("smsGeneralNoManageCtrl", function(event, data) {
        $scope.searchSmsGeneralNoManage();
        event.preventDefault();
    });

    $scope.searchSmsGeneralNoManage = function(){
        var params = {};

        $scope._inquiryMain("/adi/sms/smsTelNoManage/smsGeneralNoManage/getSmsGeneralNoManageList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // <-- 저장 호출 -->
    // 저장
    $("#funcSaveSmsGeneralNoManage").click(function(e){
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            var rowCount = i + 1;
            var rowContent = "[" + rowCount + "번째의 " + $scope.flex.collectionView.items[i].orgnCd + "/" + $scope.flex.collectionView.items[i].orgnNm + "] ";

            $scope.flex.collectionView.items[i].status = "I";

            // 처리구분 수정했을때
            if(nvl($scope.flex.collectionView.items[i].addProcFg, "") !== nvl($scope.flex.collectionView.items[i].backAddProcFg, "")) {
                // 처리구분 반려시
                if($scope.flex.collectionView.items[i].addProcFg === "3") {
                    if(nvl($scope.flex.collectionView.items[i].returnRemark, "") === "") {
                        $scope._popMsg(rowContent + messages["smsGeneralNoManage.returnRemarkBlank"]); // 처리구분 반려시 반려사유를 입력해주세요.
                        return false;
                    }
                }

                // 일반번호 필수입력
                if(nvl($scope.flex.collectionView.items[i].telNo, "") === "") {
                    $scope._popMsg(rowContent + messages["smsGeneralNoManage.telNoBlank"]); // 일반번호를 입력해주세요.
                    return false;
                } else {
                    // 숫자만 입력
                    var numChkexp = /[^0-9]/g;
                    if (numChkexp.test($scope.flex.collectionView.items[i].telNo)) {
                        $scope._popMsg(rowContent + messages["smsGeneralNoManage.telNoInChk"]); // 일반번호 숫자만 입력해주세요.
                        return false;
                    }
                }

                // 수정된 내역이 있는지 체크
                $scope.flex.collectionView.items[i].status = "U";
            }

            // 일반번호 수정했을때
            if(nvl($scope.flex.collectionView.items[i].telNo, "") !== nvl($scope.flex.collectionView.items[i].backTelNo, "")) {
                // 일반번호 필수입력
                if(nvl($scope.flex.collectionView.items[i].telNo, "") === "") {
                    $scope._popMsg(rowContent + messages["smsGeneralNoManage.telNoBlank"]); // 일반번호를 입력해주세요.
                    return false;
                } else {
                    // 숫자만 입력
                    var numChkexp = /[^0-9]/g;
                    if (numChkexp.test($scope.flex.collectionView.items[i].telNo)) {
                        $scope._popMsg(rowContent + messages["smsGeneralNoManage.telNoInChk"]); // 일반번호 숫자만 입력해주세요.
                        return false;
                    }
                }

                // 수정된 내역이 있는지 체크
                $scope.flex.collectionView.items[i].status = "U";
            }

            // 반려사유 수정했을때
            if(nvl($scope.flex.collectionView.items[i].returnRemark, "") !== nvl($scope.flex.collectionView.items[i].backReturnRemark, "")) {
                // 일반번호 필수입력
                if(nvl($scope.flex.collectionView.items[i].telNo, "") === "") {
                    $scope._popMsg(rowContent + messages["smsGeneralNoManage.telNoBlank"]); // 일반번호를 입력해주세요.
                    return false;
                } else {
                    // 숫자만 입력
                    var numChkexp = /[^0-9]/g;
                    if (numChkexp.test($scope.flex.collectionView.items[i].telNo)) {
                        $scope._popMsg(rowContent + messages["smsGeneralNoManage.telNoInChk"]); // 일반번호 숫자만 입력해주세요.
                        return false;
                    }
                }

                // 수정된 내역이 있는지 체크
                $scope.flex.collectionView.items[i].status = "U";
            }

            // 비고 수정했을때
            if(nvl($scope.flex.collectionView.items[i].remark, "") !== nvl($scope.flex.collectionView.items[i].backRemark, "")) {
                // 일반번호 필수입력
                if(nvl($scope.flex.collectionView.items[i].telNo, "") === "") {
                    $scope._popMsg(rowContent + messages["smsGeneralNoManage.telNoBlank"]); // 일반번호를 입력해주세요.
                    return false;
                } else {
                    // 숫자만 입력
                    var numChkexp = /[^0-9]/g;
                    if (numChkexp.test($scope.flex.collectionView.items[i].telNo)) {
                        $scope._popMsg(rowContent + messages["smsGeneralNoManage.telNoInChk"]); // 일반번호 숫자만 입력해주세요.
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
                    $scope._popMsg(messages["smsGeneralNoManage.sameTelNoAlert"]); // 기존에 등록된 전화번호입니다.
                    return false;
                }
            });
        });
    });

    $scope.saveSmsGeneralNoManage = function(params){
        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/adi/sms/smsTelNoManage/smsGeneralNoManage/getSmsGeneralNoManageSave.sb", params, function(){
            $scope.searchSmsGeneralNoManage();
        });
    };
    // <-- //저장 호출 -->

    // 팝업 닫기
    $scope.close = function() {
        $scope.wjSmsGeneralNoManageLayer.hide();
        event.preventDefault();
    };
}]);