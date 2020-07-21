/****************************************************************
 *
 * 파일명 : migDataMapping.js
 * 설  명 : OKPOS-KCP 데이터 이관 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.07.16     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 이관여부
var migYnData = [
    {"name":"완료","value":"Y"},
    {"name":"진행대기","value":"N"}
];

/**
 *  OKPOS-KCP 데이터 이관 그리드 생성
 */
app.controller('migDataMappingCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('migDataMappingCtrl', $scope, $http, true));

    // 검색조건에 조회기간
    var startDate = wcombo.genDateVal("#startDate", gvStartDate);
    var endDate = wcombo.genDateVal("#endDate", gvEndDate);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        $scope.migYnDataMap = new wijmo.grid.DataMap(migYnData, 'value', 'name'); //이관여부

        // <-- 그리드 헤더2줄 -->
        // 헤더머지
        s.allowMerging = 2;
        s.columnHeaders.rows.push(new wijmo.grid.Row());

        // 첫째줄 헤더 생성
        var dataItem         = {};
        dataItem.okposStoreCd    = messages["migDataMapping.okpos"];
        dataItem.okposStoreNm    = messages["migDataMapping.okpos"];
        dataItem.solbiStoreCd    = messages["migDataMapping.solbi"];
        dataItem.solbiStoreNm    = messages["migDataMapping.solbi"];
        dataItem.migYn    = messages["migDataMapping.migData"];
        dataItem.migPosMstCnt    = messages["migDataMapping.migData"];
        dataItem.migProdClsCnt   = messages["migDataMapping.migData"];
        dataItem.migProdCnt   = messages["migDataMapping.migData"];
        dataItem.migProdPriceCnt   = messages["migDataMapping.migData"];
        dataItem.migSaleCnt   = messages["migDataMapping.migData"];

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

        // 조회
        $scope.searchMigDataMapping();
    };

    // <-- 검색 호출 -->
    $scope.$on("migDataMappingCtrl", function(event, data) {
        $scope.searchMigDataMapping();
        event.preventDefault();
    });

    $scope.searchMigDataMapping = function(){
        var params = {};
        params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd'); //조회기간
        params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd'); //조회기간

        $scope._inquiryMain("/store/manage/migDataMapping/migDataMapping/getMigDataMappingList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // 신규이관요청
    $scope.addInfo = function(){
        $scope.wjMigDataMappingInfoLayer.show(true);
        event.preventDefault();
    };

    // 화면 ready 된 후 설정
    angular.element(document).ready(function () {

        // OKPOS-KCP 데이터 이관 신규등록 팝업 핸들러 추가
        $scope.wjMigDataMappingInfoLayer.shown.addHandler(function (s) {
            setTimeout(function() {
                // $scope._broadcast('migDataMappingInfoCtrl', null);
            }, 50)
        });
    });
}]);