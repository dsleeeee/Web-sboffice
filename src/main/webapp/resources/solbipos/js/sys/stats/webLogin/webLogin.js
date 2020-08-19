/****************************************************************
 *
 * 파일명 : webLogin.js
 * 설  명 : 웹로그인 현황 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.05.27     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 로그인결과 DropBoxDataMap
var statCdData = [
    {"name":"전체","value":""},
    {"name":"성공","value":"SUCC"},
    {"name":"실패","value":"FAIL"}
];
// 로그인방법 DropBoxDataMap
var loginOrgnData = [
    {"name":"전체","value":""},
    {"name":"웹","value":"WEB"},
    {"name":"가상로그인","value":"VIR"}
];

/**
 *  웹로그인 현황 그리드 생성
 */
app.controller('webLoginCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('webLoginCtrl', $scope, $http, true));

    // 검색조건에 조회기간
    var startDate = wcombo.genDateVal("#startDate", gvStartDate);
    var endDate = wcombo.genDateVal("#endDate", gvEndDate);

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("statCdCombo", statCdData);
    $scope._setComboData("loginOrgnCombo", loginOrgnData);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        //그리드 링크설정
        // ReadOnly 효과설정
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                // 사용자ID
                if (col.binding === "userId") {
                    wijmo.addClass(e.cell, 'wijLink');
                }
                // 성공
                if (col.binding === "statSucc") {
                    wijmo.addClass(e.cell, 'wijLink');
                }
                // 실패
                if (col.binding === "statFail") {
                    wijmo.addClass(e.cell, 'wijLink');
                }
            }
        });

        // 그리드 선택 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function(e) {
            var ht = s.hitTest(e);
            if( ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];

                var selectedRow = s.rows[ht.row].dataItem;
                var params      = {};
                params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd');
                params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd');
                params.userId = selectedRow.userId;
                params.userNm = selectedRow.userNm;
                params.loginOrgn = $scope.loginOrgn;

                // 사용자ID 클릭시 상세정보 조회
                if ( col.binding === "userId") {
                    var storeScope = agrid.getScope('webLoginDayDetailCtrl');
                    storeScope._broadcast('webLoginDayDetailCtrl', params);
                    var storeScope2 = agrid.getScope('webLoginLoginDetailCtrl');
                    storeScope2._broadcast('webLoginLoginDetailCtrl', params);
                    event.preventDefault();
                }

                // 성공 클릭시 상세정보 조회
                if ( col.binding === "statSucc") {
                    params.statCd = "SUCC";

                    var storeScope = agrid.getScope('webLoginDayDetailCtrl');
                    storeScope._broadcast('webLoginDayDetailCtrl', params);
                    var storeScope2 = agrid.getScope('webLoginLoginDetailCtrl');
                    storeScope2._broadcast('webLoginLoginDetailCtrl', params);
                    event.preventDefault();
                }

                // 실패 클릭시 상세정보 조회
                if ( col.binding === "statFail") {
                    params.statCd = "FAIL";

                    var storeScope = agrid.getScope('webLoginDayDetailCtrl');
                    storeScope._broadcast('webLoginDayDetailCtrl', params);
                    var storeScope2 = agrid.getScope('webLoginLoginDetailCtrl');
                    storeScope2._broadcast('webLoginLoginDetailCtrl', params);
                    event.preventDefault();
                }
            }
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("webLoginCtrl", function(event, data) {
        $scope.searchWebLogin();
        event.preventDefault();
    });

    $scope.searchWebLogin = function(){
        var params = {};
        params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd'); //조회기간
        params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd'); //조회기간
        params.statCd = $scope.statCd;
        params.loginOrgn = $scope.loginOrgn;

        $scope._inquiryMain("/sys/stats/webLogin/webLogin/getWebLoginList.sb", params, function() {
            $scope.$apply(function() {
                var storeScope = agrid.getScope('webLoginDayDetailCtrl');
                storeScope._gridDataInit();
                var storeScope2 = agrid.getScope('webLoginLoginDetailCtrl');
                storeScope2._gridDataInit();
            });

        }, false);
    };
    // <-- //검색 호출 -->

}]);

/**
 *  일자별 현황 그리드 생성
 */
app.controller('webLoginDayDetailCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('webLoginDayDetailCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 합계
        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');

        //그리드 링크설정
        // ReadOnly 효과설정
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
            }
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("webLoginDayDetailCtrl", function(event, data) {
        $scope.searchWebLoginDayDetail(data);
        $("#lblWebLoginDayDetail").text(" : " + data.userId + " / " + data.userNm);
        event.preventDefault();
    });

    $scope.searchWebLoginDayDetail = function(data){
        var params = {};
        params.startDate = data.startDate;
        params.endDate = data.endDate;
        params.userId = data.userId;
        params.statCd = data.statCd;
        params.loginOrgn = data.loginOrgn;

        $scope._inquiryMain("/sys/stats/webLogin/webLogin/getWebLoginDayDetailList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

}]);

/**
 *  로그인 현황 그리드 생성
 */
app.controller('webLoginLoginDetailCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('webLoginLoginDetailCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        $scope.loginOrgnDataMap = new wijmo.grid.DataMap(loginOrgnData, 'value', 'name'); //로그인 방법

        //그리드 링크설정
        // ReadOnly 효과설정
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
            }
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("webLoginLoginDetailCtrl", function(event, data) {
        $scope.searchWebLoginLoginDetail(data);
        $("#lblWebLoginLoginDetail").text(" : " + data.userId + " / " + data.userNm);
        event.preventDefault();
    });

    $scope.searchWebLoginLoginDetail = function(data){
        var params = {};
        params.startDate = data.startDate;
        params.endDate = data.endDate;
        params.userId = data.userId;
        params.statCd = data.statCd;
        params.loginOrgn = data.loginOrgn;

        $scope._inquiryMain("/sys/stats/webLogin/webLogin/getWebLoginLoginDetailList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

}]);