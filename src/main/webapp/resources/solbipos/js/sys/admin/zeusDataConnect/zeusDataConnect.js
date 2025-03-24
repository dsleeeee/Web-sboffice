/****************************************************************
 *
 * 파일명 : zeusDataConnect.js
 * 설  명 : 제우스데이터연동 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.03.19     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 매핑여부
var mappingFgData = [
    {"name":"전체","value":""},
    {"name":"등록","value":"0"},
    {"name":"연동중","value":"1"},
    {"name":"해지","value":"9"},
    {"name":"ERROR 관리자문의","value":"E"},
    {"name":"삭제","value":"D"}
];

/**
 *  제우스데이터연동 그리드 생성
 */
app.controller('zeusDataConnectCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('zeusDataConnectCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        $scope.mappingFgDataMap = new wijmo.grid.DataMap(mappingFgData, 'value', 'name'); // 연동상태

        // <-- 그리드 헤더2줄 -->
        // 헤더머지
        s.allowMerging = 2;
        s.columnHeaders.rows.push(new wijmo.grid.Row());
        // 첫째줄 헤더 생성
        var dataItem        = {};
        dataItem.cocd       = messages["zeusDataConnect.zeusDataMapping"];
        dataItem.buut       = messages["zeusDataConnect.zeusDataMapping"];
        dataItem.buname1    = messages["zeusDataConnect.zeusDataMapping"];
        dataItem.hqOfficeCd = messages["zeusDataConnect.solbi"];
        dataItem.storeCd    = messages["zeusDataConnect.solbi"];
        dataItem.storeNm    = messages["zeusDataConnect.solbi"];
        dataItem.mappingFg  = messages["zeusDataConnect.mappingFg"];
        dataItem.mappingDt  = messages["zeusDataConnect.mappingTime"];
        dataItem.regDt      = messages["zeusDataConnect.lastMappingTime"];
        dataItem.remark     = messages["zeusDataConnect.remark"];

        s.columnHeaders.rows[0].dataItem = dataItem;

        s.itemFormatter = function (panel, r, c, cell) {
            if (panel.cellType === wijmo.grid.CellType.ColumnHeader) {
                //align in center horizontally and vertically
                panel.rows[r].allowMerging    = true;
                panel.columns[c].allowMerging = true;
                wijmo.setCss(cell, {
                    display    : 'table',
                    tableLayout: 'fixed'
                });
                cell.innerHTML = '<div class=\"wj-header\">' + cell.innerHTML + '</div>';
                wijmo.setCss(cell.children[0], {
                    display      : 'table-cell',
                    verticalAlign: 'middle',
                    textAlign    : 'center'
                });
            }
            // 로우헤더 의 RowNum 표시 ( 페이징/비페이징 구분 )
            else if (panel.cellType === wijmo.grid.CellType.RowHeader) {
                // GroupRow 인 경우에는 표시하지 않는다.
                if (panel.rows[r] instanceof wijmo.grid.GroupRow) {
                    cell.textContent = '';
                } else {
                    if (!isEmpty(panel._rows[r]._data.rnum)) {
                        cell.textContent = (panel._rows[r]._data.rnum).toString();
                    } else {
                        cell.textContent = (r + 1).toString();
                    }
                }
            }
            // readOnly 배경색 표시
            else if (panel.cellType === wijmo.grid.CellType.Cell) {
                var col = panel.columns[c];
                if (col.isReadOnly) {
                    wijmo.addClass(cell, 'wj-custom-readonly');
                }
            }
        }
        // <-- //그리드 헤더2줄 -->
    };

    // <-- 검색 호출 -->
    $scope.$on("zeusDataConnectCtrl", function(event, data) {
        $scope.searchZeusDataConnect();
        event.preventDefault();
    });

    $scope.searchZeusDataConnect = function(){
        var params = {};

        $scope._inquiryMain("/sys/admin/zeusDataConnect/zeusDataConnect/getZeusDataConnectList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // 제우스 PKG 호출 01
    $scope.zeusPkg01 = function() {
        if($scope.systemPw === "" || $scope.systemPw === undefined) {
            $scope._popMsg("시스템패스워드를 입력해주세요.");
            return false;

        } else {
            if($scope.systemPw !== "2025") {
                $scope._popMsg("시스템패스워드를 틀렸습니다. 다시확인해주세요.");
                return false;
            }
        }

        var params = {};

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._postJSONSave.withPopUp("/sys/admin/zeusDataConnect/zeusDataConnect/getZeusPkg01.sb", params, function(response){
            $scope._popMsg(response.data.data);
        });
    };

    // 제우스 PKG 호출 02
    $scope.zeusPkg02 = function() {
        if($scope.systemPw === "" || $scope.systemPw === undefined) {
            $scope._popMsg("시스템패스워드를 입력해주세요.");
            return false;

        } else {
            if($scope.systemPw !== "2025") {
                $scope._popMsg("시스템패스워드를 틀렸습니다. 다시확인해주세요.");
                return false;
            }
        }

        var params = {};

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._postJSONSave.withPopUp("/sys/admin/zeusDataConnect/zeusDataConnect/getZeusPkg02.sb", params, function(response){
            $scope._popMsg(response.data.data);
        });
    };

    // 제우스->링크 데이터연동
    $scope.zeusPkg01Call = function() {
        if($scope.systemPwCall === "" || $scope.systemPwCall === undefined) {
            $scope._popMsg("시스템패스워드를 입력해주세요.");
            return false;

        } else {
            if($scope.systemPwCall !== "0000") {
                $scope._popMsg("시스템패스워드를 틀렸습니다. 다시확인해주세요.");
                return false;
            }
        }

        var params = {};

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._postJSONSave.withPopUp("/sys/admin/zeusDataConnect/zeusDataConnect/getZeusPkg01Call.sb", params, function(response){
            $scope._popMsg(response.data.data);
        });
    };

    // 연동신청처리
    $scope.zeusPkg02Call = function() {
        if($scope.systemPwCall === "" || $scope.systemPwCall === undefined) {
            $scope._popMsg("시스템패스워드를 입력해주세요.");
            return false;

        } else {
            if($scope.systemPwCall !== "0000") {
                $scope._popMsg("시스템패스워드를 틀렸습니다. 다시확인해주세요.");
                return false;
            }
        }

        var params = {};

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._postJSONSave.withPopUp("/sys/admin/zeusDataConnect/zeusDataConnect/getZeusPkg02Call.sb", params, function(response){
            $scope._popMsg(response.data.data);
        });
    };

}]);