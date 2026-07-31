/****************************************************************
 *
 * 파일명 : smsXrayManage.js
 * 설  명 : SMS Xray 관리 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2026.07.23     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 승인여부
var apprFg = [
    {"name":"전체","value":""},
    {"name":"요청","value":"0"},
    {"name":"승인","value":"1"},
    {"name":"반려","value":"2"}
];

app.controller('smsXrayManageCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('smsXrayManageCtrl', $scope, $http, $timeout, true));

    // 콤보박스 데이터 Set
    $scope._setComboData("apprFg", apprFg); // 승인여부

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정 ("전체" 항목 제외하고 slice(1))
        $scope.apprFgDataMap = new wijmo.grid.DataMap(apprFg.slice(1), 'value', 'name'); // 승인여부

        // 수정 시 체크박스 자동 체크
        s.cellEditEnded.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var item = s.rows[e.row].dataItem;
                $scope.checked(item);
            }
            s.collectionView.commitEdit();
        });
    };

    // 수정 시 체크박스 체크
    $scope.checked = function (item) {
        item.gChk = true;
    };

    $scope.$on("smsXrayManageCtrl", function (event, data) {
        // 조회
        $scope.searchSmsXrayManage();
        event.preventDefault();
    });

    // 조회
    $scope.searchSmsXrayManage = function () {
        var params = {};
        params.orgnCd = $("#orgnCd").val();
        params.orgnNm = $("#orgnNm").val();
        params.reqId = $("#reqId").val();
        params.reqNm = $("#reqNm").val();
        params.apprFg = $scope.apprFgCombo.selectedValue;

        $scope._inquiryMain("/adi/sms/smsXrayManage/smsXrayManage/getSmsXrayManageList.sb", params, function () {}, false);
    };

    // 저장
    $scope.save = function () {

        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["cmm.empty.data"]);
            return false;
        }

        // 수정된 데이터가 있는지 확인
        var params = [];
        for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
            if($scope.flex.collectionView.itemsEdited[i].gChk) {
                $scope.flex.collectionView.itemsEdited[i].status = "U";
                // 입력값 체크
                // 승인/반려 시 사유 입력 필수
                if ($scope.flex.collectionView.itemsEdited[i].apprFg == "1" || $scope.flex.collectionView.itemsEdited[i].apprFg == "2") {
                    if(nvl($scope.flex.collectionView.itemsEdited[i].apprReason, '') == '') {
                        $scope._popMsg(messages["smsXrayManage.msg.apprReasonChk"]);
                        return false;
                    }
                }
                params.push($scope.flex.collectionView.itemsEdited[i]);
            }
        }

        if (params.length <= 0) {
            $scope._popMsg(messages["cmm.not.modify"]);
            return;
        }

        $scope._popConfirm(messages["cmm.choo.save"], function() {
            $scope._postJSONSave.withPopUp("/adi/sms/smsXrayManage/smsXrayManage/saveSmsXrayManage.sb", params, function () {
                // 재조회
                $scope.searchSmsXrayManage();
            });
        });
    };

    // 엑셀 다운로드
    $scope.excelDownload = function () {
        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
            return false;
        }

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
        $timeout(function () {
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
                includeColumnHeaders: true,
                includeCellStyles: true
            }, 'SMS Xray관리_' + getCurDateTime() + '.xlsx', function () {
                $timeout(function () {
                    $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                }, 10);
            });
        }, 10);
    };
}]);
