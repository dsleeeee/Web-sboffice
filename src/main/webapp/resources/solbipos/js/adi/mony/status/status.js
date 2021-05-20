/****************************************************************
 *
 * 파일명 : status.js
 * 설  명 : 금전현황 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.05.17     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 입출구분
var accntFgData=[
    {"name":"전체","value":""},
    {"name":"입금","value":"1"},
    {"name":"출금","value":"2"}
];

// 콤보박스 아무것도 선택하지 않았을 때 사용
var defaultComboData = [
    {"name": "전체", "value": ""}
];

/**
 * 금전현황 그리드 생성
 */
app.controller('monyStatusCtrl', ['$scope', '$http','$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('monyStatusCtrl', $scope, $http, $timeout, true));

    // 입출구분 콤보박스 셋팅
    $scope._setComboData("accntFg", accntFgData);

    // 일자 달력 셋팅
    var srchStartDate = wcombo.genDateVal("#srchStartDate", gvStartDate);
    var srchEndDate   = wcombo.genDateVal("#srchEndDate", gvEndDate);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        $scope.accntFgDataMap = new wijmo.grid.DataMap(accntFgData, 'value', 'name'); // 입출구분

        // 합계
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    //
    $scope.$on("monyStatusCtrl", function(event, data) {

        // 금전현황 조회
        $scope.getList();
        event.preventDefault();
    });

    // 금전현황 조회
    $scope.getList = function () {

        /*if(orgnFg === 'HQ') {
            // 조회매장을(를) 입력하세요.
            if ($("#monyStatusStoreCd").val() === "" || $("#monyStatusStoreCd").val() === null) {
                $scope._popMsg("조회" + messages["status.store"] + messages["cmm.require.text"]);
                return;
            }
        }*/

        // 파라미터
        var params = {};
        params.startDate = wijmo.Globalize.format(srchStartDate.value, 'yyyyMMdd');
        params.endDate = wijmo.Globalize.format(srchEndDate.value, 'yyyyMMdd');

        if(orgnFg === 'HQ') {
            params.storeCd = $("#monyStatusStoreCd").val();
            params.accntFg = $scope.accntFgCombo.selectedValue;

        }else{
            params.accntFg = $scope.accntFgStoreCombo.selectedValue;
            params.accntCd = $scope.accntCdCombo.selectedValue;
        }

        $scope._inquirySub("/adi/mony/status/status/list.sb", params, function () {});
    };

    // 검색조건 입금/출금에 따른 계정코드 조회
    $scope.setAccntFg = function (s) {

        var params = {};

        if(s.selectedValue === "") {
            $scope._setComboData("accntCd", defaultComboData);

        }else {

            params.accntFg = s.selectedValue;

            // 계정조회
            $scope._postJSONQuery.withOutPopUp('/adi/mony/status/status/accnt.sb', params, function (response) {
                if (response.data.data.list.length > 0) {
                    var accntCdList = response.data.data.list;
                    $scope._setComboData("accntCd", accntCdList);
                } else {
                    $scope._setComboData("accntCd", defaultComboData);
                }
            });
        }
    };

    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.monyStatusStoreShow = function () {
        $scope._broadcast('monyStatusStoreCtrl');
    };
    
    // 엑셀 다운로드
    $scope.excelDownload = function () {

        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
            return false;
        }

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 열기
        $timeout(function () {
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
                includeColumnHeaders: true,
                includeCellStyles   : true,
                includeColumns      : function (column) {
                    return column.visible;
                }
            },  messages["status.monyStatus"] + '_'+ getToday() + '.xlsx', function () {
                $timeout(function () {
                    $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                }, 10);
            });
        }, 10);
    };

}]);