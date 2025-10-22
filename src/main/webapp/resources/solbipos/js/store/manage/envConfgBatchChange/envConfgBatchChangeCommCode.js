/****************************************************************
 *
 * 파일명 : envConfgBatchChangeCommCode.js
 * 설  명 : 환경변수일괄변경 > 공통코드관리 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2024.11.14     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 사용여부 DataMap
var useYnFg = [
    {"name":"","value":""},
    {"name":"사용","value":"Y"},
    {"name":"미사용","value":"N"}
];

/**
 * 공통코드관리 탭 그리드 생성
 */
app.controller('envConfgBatchChangeCommCodeCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('envConfgBatchChangeCommCodeCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        $scope.useYnDataMap = new wijmo.grid.DataMap(useYnFg, 'value', 'name');
    };

    $scope.$on("envConfgBatchChangeCommCodeCtrl", function(event, data) {
        // 조회
        $scope.searchEnvConfgBatchChangeCommCode();
        event.preventDefault();
    });

    // 조회
    $scope.searchEnvConfgBatchChangeCommCode = function(){

        // 시스템패스워드 비우기
        $scope.systemPw = "";

        if($scope.nmcodeCd === "" || $scope.nmcodeCd === undefined) {
            $scope._popMsg(messages["envConfgBatchChange.commCode.nmcodeBlank"]); // 공통코드를 선택해주세요.
            return false;
        }

        var params = {};
        // 공통코드 팝업에서 선택해 가져온 정보
        params.selHqOfficeCd = $scope.selHqOfficeCd;
        params.useTargetFg = $scope.useTargetFg;

        // 검색조건
        params.hqOfficeCd = $("#srchHqOfficeCd").val();
        params.hqOfficeNm = $("#srchHqOfficeNm").val();
        params.storeCd = $("#srchStoreCd").val();
        params.storeNm = $("#srchStoreNm").val();
        params.nmcodeGrpCd = $scope.nmcodeGrpCd;
        params.nmcodeCd = $scope.nmcodeCd;

        $scope._inquiryMain("/store/manage/envConfgBatchChange/envConfgBatchChangeCommCode/getEnvConfgBatchChangeCommCodeList.sb", params, function() {

            // 공통코드 사용대상에 따른 리스트 항목 visible
            var grid = wijmo.Control.getControl("#wjGridEnvConfgBatchChangeCommCodeList");
            var columns = grid.columns;

            var columnsCnt = 5;
            // 공통코드 사용대상에 따라 매장정보 숨기기
            for (var j = 0; j < columnsCnt; j++) {
                if($scope.useTargetFg === "H"){
                    if(columns[j].binding == "storeCd" || columns[j].binding == "storeNm") {
                        columns[j].visible = false;
                    } else {
                        columns[j].visible = true;
                    }
                } else {
                    columns[j].visible = true;
                }
            }
        }, false);
    };

    //
    $scope.popUpCommCode = function() {

        // 공통코드 선택취소
        $scope.delCommCode();

        var popup = $scope.wjSearchCommCodeLayer;
        popup.show(true, function (s) {
            var popScope = agrid.getScope('searchCommCodeCtrl');
            popScope.$apply(function () {
                popScope._gridDataInit();
                if (!$.isEmptyObject(popScope.getSelectedCommCode())) {
                    $scope.selHqOfficeCd = popScope.getSelectedCommCode().hqOfficeCd;
                    $scope.useTargetFg = popScope.getSelectedCommCode().useTargetFg;
                    $scope.nmcodeCd = popScope.getSelectedCommCode().nmcodeCd;
                    $scope.nmcodeNm = "[" + popScope.getSelectedCommCode().nmcodeCd + "]" + popScope.getSelectedCommCode().nmcodeNm;
                    $scope.nmcodeGrpCd = popScope.getSelectedCommCode().nmcodeGrpCd;
                    $scope.parentCd = popScope.getSelectedCommCode().parentCd;

                    // 시스템패스워드 비우기
                    $scope.systemPw = "";

                    // 그리드 지우기
                    var agencyScope1 = agrid.getScope('envConfgBatchChangeCommCodeCtrl');
                    agencyScope1._gridDataInit();
                }
            });
        });
    };

    // 공통코드 선택취소
    $scope.delCommCode = function(){

        $scope.selHqOfficeCd = "";
        $scope.useTargetFg = "";
        $scope.nmcodeCd = "";
        $scope.nmcodeNm = "";
        $scope.nmcodeGrpCd = "";
        $scope.parentCd = "";

        // 시스템패스워드 비우기
        $scope.systemPw = "";

        // 그리드 지우기
        var agencyScope1 = agrid.getScope('envConfgBatchChangeCommCodeCtrl');
        agencyScope1._gridDataInit();
    };

    // 저장
    $scope.saveCommCode = function(){

        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["cmm.empty.data"]); // 조회 데이터가 없습니다.
            return false;
        }

        if($scope.systemPw === "" || $scope.systemPw === undefined) {
            $scope._popMsg(messages["envConfgBatchChange.commCode.systemPwBlank"]); // 시스템패스워드를 입력해주세요.
            return false;

        } else {
            var date = new Date();
            var year = new String(date.getFullYear());
            var month = new String(date.getMonth() + 1);
            month = month.length <= 1 ? "0" + month : month;
            var day = new String(date.getDate());
            day = day.length <= 1 ? "0" + day : day;
            var hh = new String(date.getHours());
            hh = hh < 10 ? "0" + hh : hh;
            var pw = userId + year + month + day + hh;

            if ($scope.systemPw !== pw) {
                $scope._popMsg(messages["envConfgBatchChange.commCode.systemPwError"]); // 시스템패스워드를 틀렸습니다. 다시확인해주세요.
                return false;
            }
        }

        // 저장 하시겠습니까?
        var msg = messages["cmm.choo.save"];
        $scope._popConfirm(msg, function() {

            // 파라미터 설정
            var params = new Array();
            for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
                if ($scope.flex.collectionView.itemsEdited[i].gChk === true){
                    $scope.flex.collectionView.itemsEdited[i].status = "U";
                    $scope.flex.collectionView.itemsEdited[i].useTargetFg = $scope.useTargetFg;
                    $scope.flex.collectionView.itemsEdited[i].nmcodeCd = $scope.nmcodeCd;
                    $scope.flex.collectionView.itemsEdited[i].nmcodeGrpCd = $scope.nmcodeGrpCd;
                    $scope.flex.collectionView.itemsEdited[i].parentCd = $scope.parentCd;
                    params.push($scope.flex.collectionView.itemsEdited[i]);
                }
            }

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save("/store/manage/envConfgBatchChange/envConfgBatchChangeCommCode/getEnvConfgBatchChangeCommCodeSave.sb", params, function(){ $scope.allSearch(); });
        });
    };

    // 화면 ready 된 후 설정
    angular.element(document).ready(function () {
        // 기능키 조회 팝업 핸들러 추가
        $scope.wjSearchCommCodeLayer.shown.addHandler(function (s) {
            setTimeout(function() {
                $scope._broadcast('searchCommCodeCtrl');
            }, 50)
        });
    });

    $scope.excelDownload = function () {
        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
            return false;
        }

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
        $timeout(function () {
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
                includeColumnHeaders: true,
                includeCellStyles: false,
                includeColumns: function (column) {
                    //return column.visible;
                    return column.binding != 'gChk'; //선택
                }
            },
                "환경변수일괄변경_" + messages['envConfgBatchChange.commCode'] + '.xlsx', function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                    }, 10);
                });
        }, 10);
    };

}]);