/****************************************************************
 *
 * 파일명 : runCopyCnt.js
 * 설  명 : 런닝매장현황 런닝COPY수 탭 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2024.04.11     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 사용여부
var shopMigFgComboMap = [
    {"name": "전체", "value": ""},
    {"name": "신규", "value": "0"},
    {"name": "전환", "value": "1"}
];

// 하위대리점 포함여부
var includeFgTypeComboData = [
    {"name":"포함","value":"Y"},
    {"name":"미포함","value":"N"}
];

/**
 *  매출매장현황 그리드 생성
 */
app.controller('runCopyCntCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('runCopyCntCtrl', $scope, $http, true));

    // 조회일자
    var startMonth = new wijmo.input.InputDate('#startMonth2', {
        format       : "yyyy-MM",
        selectionMode: "2" // 달력 선택 모드(1:day 2:month)
    });

    $scope._setComboData("includeFgCombo", includeFgTypeComboData); // 하위대리점 포함여부

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.sysStatFgDataMap = new wijmo.grid.DataMap(sysStatFgData, 'value', 'name'); //상태구분

        // 합계
        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel == s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
                if (col.binding === "runFg") {
                    if(item.runFg === "폐점") {
                        wijmo.addClass(e.cell, 'red');
                    } else if(item.runFg === "신규") {
                        wijmo.addClass(e.cell, 'blue');
                    }
                }
            }
        });

    };

    // <-- 검색 호출 -->
    $scope.$on("runCopyCntCtrl", function(event, data) {
        $scope.searchRunCopyList();
        $scope.searchRunCopyCnt();
        event.preventDefault();

    });

    // 매출매장현황 그리드 조회
    $scope.searchRunCopyList = function() {
        if (!$scope.valueCheck()) {
            return false;
        }

        // 한달 전 날짜 구하기
        var endDt = new Date(startMonth.value);
        var endDiffDay = new Date(endDt.setMonth(endDt.getMonth()-1));
        var lastMonth = wijmo.Globalize.format(endDiffDay, 'yyyyMM');

        var params = {};
        params.startMonth = wijmo.Globalize.format(startMonth.value, 'yyyyMM');
        params.hqOfficeCd = $scope.hqOfficeCd;
        params.hqOfficeNm = $scope.hqOfficeNm
        params.storeCd = $scope.storeCd
        params.storeNm = $scope.storeNm
        // params.orgnFg = orgnFg;
        // params.pAgencyCd = pAgencyCd;
        params.manageVanCd = $("#ssl_srchManageVanCdCopy").val();
        params.includeFg = $scope.srchIncludeFgCombo.selectedValue;
        params.lastMonth = lastMonth;

        if(orgnFg === "MASTER"){
            params.agencyCd = $("#ssl_srchAgencyCdCopy").val();
        }else{
            params.agencyCd = orgnCd;
        }

        $scope._inquiryMain("/pos/license/runStore/runStore/getRunCopyList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // 런닝/신규/폐점 매장, 포스 수 조회
    $scope.searchRunCopyCnt = function() {

        // 한달 전 날짜 구하기
        var endDt       = new Date(startMonth.value);
        var endDiffDay  = new Date(endDt.setMonth(endDt.getMonth()-1));
        var lastMonth   = wijmo.Globalize.format(endDiffDay, 'yyyyMM');

        var params          = {};
        params.startMonth   = wijmo.Globalize.format(startMonth.value, 'yyyyMM');
        params.lastMonth    = lastMonth;
        params.includeFg = $scope.srchIncludeFgCombo.selectedValue;
        params.hqOfficeCd = $("#copyHqOfficeCd").val();
        params.hqOfficeNm = $("#copyHqOfficeNm").val();
        params.storeCd = $("#copyStoreCd").val();
        params.storeNm = $("#copyStoreNm").val();
        params.manageVanCd = $("#ssl_srchManageVanCdCopy").val();

        if(orgnFg === "MASTER"){
            params.agencyCd = $("#ssl_srchAgencyCdCopy").val();
        }else{
            params.agencyCd = orgnCd;
        }


        //가상로그인 session 설정
        if(document.getElementsByName('sessionId')[0]){
            params['sid'] = document.getElementsByName('sessionId')[0].value;
        }

        // ajax 통신 설정
        $http({
            method : 'POST', //방식
            url    : '/pos/license/runStore/runStore/getRunCopyCnt.sb', /* 통신할 URL */
            params : params, /* 파라메터로 보낼 데이터 */
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        }).then(function successCallback(response) {
            if ($scope._httpStatusCheck(response, true)) {

                var month = params.startMonth.substring(4,6);

                $scope.runStoreCnt      = response.data.data.runStoreCnt;    // 런닝매장수
                $scope.runPosCnt        = response.data.data.runPosCnt;      // 런닝포스수
                $scope.openStoreCnt     = response.data.data.openStoreCnt;   // 신규매장수
                $scope.openPosCnt       = response.data.data.openPosCnt;     // 신규포스수
                $scope.closeStoreCnt    = response.data.data.closeStoreCnt;  // 폐점매장수
                $scope.closePosCnt      = response.data.data.closePosCnt;    // 폐점포스수

                $("#storePosCnt").html(month + "월 런닝매장수: " + addComma($scope.runStoreCnt) + " 런닝포스수: " + addComma($scope.runPosCnt) + " (" + month + "월 신규매장수: " + addComma($scope.openStoreCnt) + " 신규포스수: " + addComma($scope.openPosCnt)
                    + " - 폐점매장수: " + addComma($scope.closeStoreCnt) + " 폐점포스수: " + addComma($scope.closePosCnt) + ")");

            }
        }, function errorCallback(response) {
            // called asynchronously if an error occurs
            // or server returns response with an error status.
            if (response.data.message) {
                $scope._popMsg(response.data.message);
            } else {
                $scope._popMsg(messages['cmm.error']);
            }
            return false;
        }).then(function () {
        });
    }

// 체크
    $scope.valueCheck = function () {
        // var msg = messages['kds.date.error'];
        // var date1 = new Date(wijmo.Globalize.format($scope.startDate, 'yyyy-MM-dd'));
        // var date2 = new Date(wijmo.Globalize.format($scope.endDate, 'yyyy-MM-dd'));
        //
        // var diffDay = (date2.getTime() - date1.getTime()) / (1000 * 60 * 60 * 24);
        // if (diffDay > 31) {
        //     $scope._popMsg(msg);
        //     return false;
        // }
        return true;
    };

    /*********************************************************
     * 관리밴사 조회
     * *******************************************************/
    $scope.searchManageVanCopy = function(){
        var popup = $scope.vanLayer;

        // 팝업 닫을때
        popup.show(true, function (s) {
            var vanScope = agrid.getScope('searchVanCtrl');
            vanScope.$apply(function () {
                vanScope._gridDataInit();
                if (!$.isEmptyObject(vanScope.getVan())) {
                    $("#ssl_srchManageVanCdCopy").val(vanScope.getVan().vanCd);
                    $("#manageVanNmCopy").val(vanScope.getVan().vanNm);
                }
            });
        });
    };

    // 관리밴사 선택취소
    $scope.delManageVanCdCopy = function(){
        $("#ssl_srchManageVanCdCopy").val("");
        $("#manageVanNmCopy").val(messages["cmm.all"]);
    }

    /*********************************************************
     * 대리점(관리업체) 조회
     * *******************************************************/
    $scope.searchAgencyCopy = function(){
        if(orgnFg === "MASTER") {
            var popup = $scope.agencyLayer;

            // 팝업 닫을때
            popup.show(true, function (s) {
                var agencyScope = agrid.getScope('searchAgencyCtrl');
                agencyScope.$apply(function () {
                    agencyScope._gridDataInit();
                    if (!$.isEmptyObject(agencyScope.getAgency())) {
                        $("#ssl_srchAgencyCdCopy").val(agencyScope.getAgency().agencyCd);
                        $("#agencyNmCopy").val(agencyScope.getAgency().agencyNm);
                    }
                });
            });
        }
    };

    // 대리점(관리업체) 선택취소
    $scope.delAgencyCdCopy = function(){
        $("#ssl_srchAgencyCdCopy").val("");
        $("#agencyNmCopy").val(messages["cmm.all"]);
    }

    // 엑셀 다운로드
    $scope.excelDownloadInfo = function () {

        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
            return false;
        }

        var startDt = wijmo.Globalize.format(startMonth.value, 'yyyy-MM');
        var endDt   = wijmo.Globalize.format(startMonth.value, 'yyyy-MM');


        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
        $timeout(function () {
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
                includeColumnHeaders: true,
                includeCellStyles: true,  //20220427 false 엑섹속도 cell 스타일 확인중
                includeColumns: function (column) {
                    return column.visible;
                }
            }, '런닝매장현황_런닝COPY수(' + startDt +'_' + endDt + ')'+'_'+ getCurDateTime() +'.xlsx', function () {
                $timeout(function () {
                    $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                }, 10);
            });
        }, 10);
    };

}]);