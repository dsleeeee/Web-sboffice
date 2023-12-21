/****************************************************************
 *
 * 파일명 : agencyAuthList.js
 * 설  명 : 운영현황 대리점인증현황탭 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2019.10.30     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 업체구분 DropBoxDataMap
var agencyFgData = [
    {"name":"전체","value":""},
    {"name":"자사","value":"1"},
    {"name":"대리점","value":"2"}
];

/**
 *  대리점인증현황 그리드 생성
 */
app.controller('agencyAuthListCtrl', ['$scope', '$http','$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('agencyAuthListCtrl', $scope, $http, true));

    // 검색조건에 조회기간
    var startDate = wcombo.genDateVal("#startDate", gvStartDate);
    var endDate = wcombo.genDateVal("#endDate", gvEndDate);

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("agencyFg", agencyFgData); //업체구분

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.useYnFgDataMap = new wijmo.grid.DataMap(useYnFgData, 'value', 'name'); //사용여부

        // 합계
        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    // <-- 검색 호출 -->
    $scope.$on("agencyAuthListCtrl", function(event, data) {
        $scope.searchAgencyAuthList();
        event.preventDefault();
    });

    // 대리점인증현황 그리드 조회
    $scope.searchAgencyAuthList = function() {
        var params = {};
        params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd'); //조회기간
        params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd'); //조회기간
        params.srchAgencyCd = $("#srchAgencyCd").val();
        params.srchAgencyNm = $("#srchAgencyNm").val();
        params.orgnCd = orgnCd;
        params.orgnFg = orgnFg;
        params.pAgencyCd = pAgencyCd;
        params.manageVanCd = $("#ssl_srchManageVanCdAuth").val();

        $scope._inquiryMain("/pos/license/oper/oper/getAgencyAuthList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    /*********************************************************
     * 관리밴사 조회
     * *******************************************************/
    $scope.searchManageVanAuth = function(){
        var popup = $scope.vanLayer;

        // 팝업 닫을때
        popup.show(true, function (s) {
            var vanScope = agrid.getScope('searchVanCtrl');
            vanScope.$apply(function () {
                vanScope._gridDataInit();
                if (!$.isEmptyObject(vanScope.getVan())) {
                    $("#ssl_srchManageVanCdAuth").val(vanScope.getVan().vanCd);
                    $("#manageVanNmAuth").val(vanScope.getVan().vanNm);
                }
            });
        });
    };

    // 엑셀 다운로드
    $scope.excelDownloadInfo = function () {

        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
            return false;
        }

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
        $timeout(function () {
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
                includeColumnHeaders: true,
                includeCellStyles: true,  //20220427 false 엑섹속도 cell 스타일 확인중
                includeColumns: function (column) {
                    return column.visible;
                }
            }, '대리점인증현황_매출일자(' + wijmo.Globalize.format(startDate.value, 'yyyy-MM-dd') +'_' + wijmo.Globalize.format(endDate.value, 'yyyy-MM-dd') + ')'+'_'+ getCurDateTime() +'.xlsx', function () {
                $timeout(function () {
                    console.log('시작시간'+ startDate.value)
                    console.log('종료시간'+ endDate.value)
                    $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                }, 10);
            });
        }, 10);
    };

}]);