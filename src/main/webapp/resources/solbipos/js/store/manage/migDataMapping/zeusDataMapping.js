/****************************************************************
 *
 * 파일명 : zeusDataMapping.js
 * 설  명 : 제우스 매장 연동 화면 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.02.04     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 매핑여부
var mapFgData = [
    {"name":"전체","value":""},
    {"name":"등록","value":"0"},
    {"name":"연동중","value":"1"},
    {"name":"해지","value":"9"},
    {"name":"ERROR 관리자문의","value":"E"},
    {"name":"삭제","value":"D"}
];

/**
 *  제우스 매장 연동 그리드 생성
 */
app.controller('zeusDataMappingCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('zeusDataMappingCtrl', $scope, $http, true));

    // 디폴트 일주일
    var today 	= new Date();
    var week = new Date(today.setDate(today.getDate() - 7));
    var dd 		= week.getDate();
    var mm 		= week.getMonth()+1; //January is 0!
    var yyyy 	= week.getFullYear();
    if (dd < 10) { dd = '0' + dd;}
    if (mm < 10) { mm = '0' + mm;}
    week 	= yyyy.toString()  + mm.toString()  + dd.toString();

    $scope.srchStartDate = wcombo.genDateVal("#zeusStartDate", week);
    $scope.srchEndDate = wcombo.genDateVal("#zeusEndDate", gvEndDate);

    $scope._setComboData("mapFgCombo", mapFgData); // 연동상태

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        $scope.mapFgDataMap = new wijmo.grid.DataMap(mapFgData, 'value', 'name'); // 연동상태

        // 헤더머지
        s.allowMerging = 2;
        s.columnHeaders.rows.push(new wijmo.grid.Row());
        // 첫째줄 헤더 생성
        var dataItem        = {};
        dataItem.cocd       = messages["migDataMapping.zeusDataMapping"];
        dataItem.buut       = messages["migDataMapping.zeusDataMapping"];
        dataItem.buname1    = messages["migDataMapping.zeusDataMapping"];
        dataItem.hqOfficeCd = messages["migDataMapping.solbi"];
        dataItem.storeCd    = messages["migDataMapping.solbi"];
        dataItem.storeNm    = messages["migDataMapping.solbi"];
        dataItem.mappingFg  = messages["migDataMapping.mappingFg"];
        dataItem.mappingDt  = messages["migDataMapping.mappingTime"];
        dataItem.regDt      = messages["migDataMapping.lastMappingTime"];
        dataItem.delDt      = messages["migDataMapping.delMapping"];
        dataItem.remark     = messages["migDataMapping.remark"];

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

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
                if (col.binding === "delDt") {
                    if(item[("delDt")] === '연동삭제') {
                        wijmo.addClass(e.cell, 'wijLink');
                    }else{
                        wijmo.addClass(e.cell, 'red');
                    }
                }
            }
        });

        // 그리드 선택 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function(e) {
            var ht = s.hitTest(e);
            if( ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];
                var selectedRow = s.rows[ht.row].dataItem;
                // 연동삭제 클릭 시 삭제
                if (col.binding === "delDt") {
                    if(selectedRow.delDt === '연동삭제') {
                        $scope._popConfirm(messages["migDataMapping.zeus.delConfirm"], function () {
                            $scope.deleteMapping(selectedRow);
                        });
                    }
                }
            }
        });

    };

    // <-- 검색 호출 -->
    $scope.$on("zeusDataMappingCtrl", function(event, data) {
        $scope.searchZeusDataMapping();
        event.preventDefault();
    });

    $scope.searchZeusDataMapping = function() {
        var params = {};
        if(!$scope.srchStartDate.isReadOnly){
            params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
            params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
        }
        params.storeCd      = $scope.zeusStoreCd;
        params.storeNm      = $scope.zeusStoreNm;
        params.mappingFg    = $scope.srchMapFgCombo.selectedValue;

        $scope._inquiryMain("/store/manage/migDataMapping/migDataMapping/getZeusDataMappingList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // 날짜 변경
    $scope.setDt = function (dayFg) {

        var today 	= new Date();
        var startDt;

        if(dayFg !== '4') {

            $scope.srchStartDate.isReadOnly = false;
            $scope.srchEndDate.isReadOnly = false;

            if (dayFg === '0') {
                $("#oneWeek").addClass("on");
                $("#oneMonth").removeClass("on");
                $("#threeMonth").removeClass("on");
                $("#sixMonth").removeClass("on");
                $("#all").removeClass("on");
                startDt = new Date(today.setDate(today.getDate() - 7));
            } else if (dayFg === '1') {
                $("#oneWeek").removeClass("on");
                $("#oneMonth").addClass("on");
                $("#threeMonth").removeClass("on");
                $("#sixMonth").removeClass("on");
                $("#all").removeClass("on");
                startDt = new Date(today.setDate(today.getDate() - 31));
            } else if (dayFg === '2') {
                $("#oneWeek").removeClass("on");
                $("#oneMonth").removeClass("on");
                $("#threeMonth").addClass("on");
                $("#sixMonth").removeClass("on");
                $("#all").removeClass("on");
                startDt = new Date(today.setDate(today.getDate() - 93));
            } else if (dayFg === '3') {
                $("#oneWeek").removeClass("on");
                $("#oneMonth").removeClass("on");
                $("#threeMonth").removeClass("on");
                $("#sixMonth").addClass("on");
                $("#all").removeClass("on");
                startDt = new Date(today.setDate(today.getDate() - 186));
            }

            //오늘 날짜 세팅
            var endDt = new Date();

            $scope.srchStartDate.value = startDt;
            $scope.srchEndDate.value = endDt;

        }else if(dayFg === '4'){
            $("#oneWeek").removeClass("on");
            $("#oneMonth").removeClass("on");
            $("#threeMonth").removeClass("on");
            $("#sixMonth").removeClass("on");
            $("#all").addClass("on");

            $scope.srchStartDate.isReadOnly = true;
            $scope.srchEndDate.isReadOnly = true;
        }
    };

    // 연동삭제
    $scope.deleteMapping = function (data){

        var params = {};
        params.cocd = data.cocd;
        params.buut = data.buut;

        $scope._save("/store/manage/migDataMapping/migDataMapping/getDeleteStoreMapping.sb", params, function(){
            $scope.searchZeusDataMapping();
        });

    };

    // 신규이관요청
    $scope.addZeusMapping = function() {
        $scope.zeusDataMappingStoreLayer.show(true);
        event.preventDefault();
    };

    // 화면 ready 된 후 설정
    angular.element(document).ready(function () {

        // OKPOS-KCP 데이터 이관 신규등록 팝업 핸들러 추가
        $scope.zeusDataMappingStoreLayer.shown.addHandler(function (s) {
            setTimeout(function() {
                $scope._broadcast('zeusDataMappingStoreCtrl', "clear");
            }, 50)
        });
    });

}]);