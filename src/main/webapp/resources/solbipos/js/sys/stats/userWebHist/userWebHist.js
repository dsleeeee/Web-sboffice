/****************************************************************
 *
 * 파일명 : userWebHist.js
 * 설  명 : 웹로그인 현황 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2024.01.15     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 로그인방법 DropBoxDataMap
var loginOrgnData = [
    {"name":"전체","value":""},
    {"name":"웹","value":"WEB"},
    {"name":"가상로그인","value":"VIR"}
];

/**
 *  웹로그인 현황 그리드 생성
 */
app.controller('userWebHistCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('userWebHistCtrl', $scope, $http, $timeout, false));

    // 검색조건에 조회기간
    var startDate = wcombo.genDateVal("#startDate", gvStartDate);
    var endDate = wcombo.genDateVal("#endDate", gvEndDate);

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("loginOrgnCombo", loginOrgnData);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.loginOrgnDataMap = new wijmo.grid.DataMap(loginOrgnData, 'value', 'name'); //로그인 방법

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];

                if (col.format === "date") {
                    e.cell.innerHTML = getFormatDate(e.cell.innerText);
                } else if (col.format === "dateTime") {
                    e.cell.innerHTML = getFormatDateTime(e.cell.innerText);
                }
                if (col.binding === "sessionId"){
                    var item = s.rows[e.row].dataItem;
                    // 값이 있으면 링크 효과
                    if (item[("sessionId")] !== null) {
                        wijmo.addClass(e.cell, 'wijLink');
                    }
                }
                if(col.binding === "pause" || col.binding === "resume"){
                    wijmo.addClass(e.cell, 'wijLink');
                }
            }
        });

        // 그리드 클릭 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function (e) {
            var ht = s.hitTest(e);
            if (ht.cellType === wijmo.grid.CellType.Cell) {
                var col         = ht.panel.columns[ht.col];
                var selectedRow = s.rows[ht.row].dataItem;
                var params = selectedRow;
                if ((col.binding === "sessionId" && selectedRow.sessionId !== null) || col.binding === "pause" || col.binding === "resume") {
                    $scope.userWebSessionDelPwLayer.show(true);
                    var scope = agrid.getScope('userWebSessionDelPwCtrl');
                    params.procFg = col.binding;
                    scope.setSelectedUser(params);
                }
            }
        });

    };

    // <-- 검색 호출 -->
    $scope.$on("userWebHistCtrl", function(event, data) {
        $scope.searchUserWebHist();
        event.preventDefault();
    });

    $scope.searchUserWebHist = function(){
        var startDt = new Date(wijmo.Globalize.format(startDate.value, 'yyyy-MM-dd'));
        var endDt = new Date(wijmo.Globalize.format(endDate.value, 'yyyy-MM-dd'));
        var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

        // 시작일자가 종료일자보다 빠른지 확인
        if(startDt.getTime() > endDt.getTime()){
            $scope._popMsg(messages['cmm.dateChk.error']);
            return false;
        }

        // 조회일자 최대 한달(31일) 제한
        if (diffDay > 31) {
            $scope._popMsg(messages['cmm.dateOver.1month.error']);
            return false;
        }

        var params = {};
        params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd'); // 조회기간
        params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd'); // 조회기간
        params.userId = $scope.userId;
        params.userNm = $scope.userNm;
        params.hqOfficeCd = $scope.hqOfficeCd;
        params.hqOfficeNm = $scope.hqOfficeNm;
        params.storeCd = $scope.storeCd;
        params.storeNm = $scope.storeNm;
        params.vUserId = $scope.vUserId;
        params.loginIp = $scope.loginIp;

        $scope._inquiryMain("/sys/stats/userWebHist/userWebHist/getUserWebHistList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

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
            }, '사용자웹사용이력' + '_'+ getToday() + '.xlsx', function () {
                $timeout(function () {
                    $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                }, 10);
            });
        }, 10);
    };

}]);