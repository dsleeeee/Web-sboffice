/**
 * get application
 */
var app = agrid.getApp();

// 분류표시 DropBoxDataMap
var vLevel = [
    {"name":"1레벨","value":"1"},
    {"name":"2레벨","value":"2"},
    {"name":"3레벨","value":"3"}
];
var vLevelNo = [
    {"name":"분류없음","value":"0"}
];

/** 상품분류별 controller */
app.controller('dayProdClassCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('dayProdClassCtrl', $scope, $http, true));

    if(maxLevel == 0) {
        // 조회조건 콤보박스 데이터 Set
        $scope._setComboData("srchLevelCombo", vLevelNo);
    } else {
        // 본사/매장 별 사용하는 최대 분류레벨 값을 파악하여 분류표시 selectBox 선택 값 셋팅
        if(maxLevel == 1){
            vLevel.splice(1,2);
        }else if (maxLevel == 2){
            vLevel.splice(2,1);
        }

        // 조회조건 콤보박스 데이터 Set
        $scope._setComboData("srchLevelCombo", vLevel);
    }

    $scope.srchStartDate = wcombo.genDateVal("#srchProdClassStartDate", gvStartDate);
    $scope.srchEndDate   = wcombo.genDateVal("#srchProdClassEndDate", gvEndDate);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // picker 사용시 호출 : 미사용시 호출안함
        $scope._makePickColumns("dayProdClassCtrl");

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                if (col.format === "date") {
                    e.cell.innerHTML = getFormatDate(e.cell.innerText);
                }
            }
        });

        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');

        // <-- 그리드 헤더2줄 -->
        // 헤더머지
        s.allowMerging = 2;
        s.columnHeaders.rows.push(new wijmo.grid.Row());

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

        //그리드 링크설정
        // ReadOnly 효과설정
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                // 수량합계
                if (col.binding === "totSaleQty") {
                    wijmo.addClass(e.cell, 'wijLink');
                }

                // 수량
                var arr = $("#hdProdClassCd").val().split(",");
                for (var i = 0; i < arr.length; i++) {
                    if (col.binding === ("pay" + (i+1) + "SaleQty")) {
                        var item = s.rows[e.row].dataItem;

                        // 값이 있으면 링크 효과
                        if (nvl(item[("pay" + (i+1) + "SaleQty")], '') !== '') {
                            wijmo.addClass(e.cell, 'wijLink');
                            wijmo.addClass(e.cell, 'wj-custom-readonly');
                        }
                    }
                }
            }
        });

        // 그리드 선택 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function(e) {
            var ht = s.hitTest(e);
            if (ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];

                //  수량합계 클릭시 상세정보 조회
                if (col.binding === "totSaleQty") {
                    var selectedRow = s.rows[ht.row].dataItem;
                    var params = {};
                    params.saleDate = selectedRow.saleDate.replaceAll("-","");
                    params.strProdClassCd = $("#hdProdClassCd").val();
                    params.level = $scope.level;
                    params.prodCd = $scope.prodCd;
                    params.prodNm = $scope.prodNm;
                    params.barCd = $scope.barCd;
                    params.prodClassCd = $scope.prodClassCd;
                    params.storeCd = $("#dayProdClassSelectStoreCd").val();
                    params.gubun = "dayProdClass";

                    $scope._broadcast('prodSaleDtlCtrl', params);
                }

                // 수량 클릭시 상세정보 조회
                var arr = $("#hdProdClassCd").val().split(",");
                for (var i = 0; i < arr.length; i++) {
                    if (col.binding === ("pay" + (i + 1) + "SaleQty")) {

                        var selectedRow = s.rows[ht.row].dataItem;
                        var params = {};
                        params.saleDate = selectedRow.saleDate.replaceAll("-","");
                        // params.strProdClassCd = $("#hdProdClassCd").val();
                        params.strProdClassCd = arr[i];
                        params.level = $scope.level;
                        params.prodCd = $scope.prodCd;
                        params.prodNm = $scope.prodNm;
                        params.barCd = $scope.barCd;
                        // params.prodClassCd = arr[i];
                        params.prodClassCd = $scope.prodClassCd;
                        params.storeCd = $("#dayProdClassSelectStoreCd").val();
                        params.gubun = "dayProdClass";

                        // 값이 있으면 링크
                        if (nvl(selectedRow[("pay" + (i + 1) + "SaleQty")], '') !== '') {
                            $scope._broadcast('prodSaleDtlCtrl', params);
                        }
                    }
                }
            }
        });

    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("dayProdClassCtrl", function (event, data) {
        $scope.searchDayProdClassList();
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 분류 표시 레젤 SelectBox 선택 시
    $scope.setProdClass = function(s){

        // 파라미터
        var params= {};
        params.level = s.selectedValue;

        $.postJSON("/sale/day/day/dayProdClass/level.sb", params, function(result) {

           var data = result.data.list;
           var strCd = "";
           var strNm = "";

           for(var i=0; i<data.length; i++) {
               strCd += data[i].prodClassCd + ",";
               strNm += data[i].prodClassNm + ",";
           }

           $("#hdProdClassCd").val(strCd.substr(0, strCd.length - 1));
           $("#hdProdClassNm").val(strNm.substr(0, strNm.length - 1));
        },
        function(result){
            s_alert.pop(result.message);
        });
    };

    // 상품분류별 리스트 조회
    $scope.searchDayProdClassList = function () {

        $scope.searchedStoreCd = $("#dayProdClassSelectStoreCd").val();
        // 파라미터
        var params= {};
        params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
        params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
        params.storeCd = $("#dayProdClassSelectStoreCd").val();
        params.strProdClassCd = $("#hdProdClassCd").val();
        //params.prodClassCd = "";

        $scope._inquiryMain("/sale/day/day/dayProdClass/list.sb", params, function() {

            var grid = wijmo.Control.getControl("#wjDayProdClassList");
            var columns = grid.columns;
            var arr = $("#hdProdClassNm").val().split(",");

            if($("#hdProdClassNm").val() != "") {
                var start = 4;
                var end = (arr.length * 2) + 3;

                // 분류갯수 파악하여 리스트 컬럼 보이게/안보이게 처리
                for (var i = 4; i <= 199; i++) {
                    if (i >= start && i <= end) {
                        columns[i].visible = true;
                    } else {
                        columns[i].visible = false;
                    }
                }
            }

            //리스트 컬럼에 분류명 셋팅
            var dataItem         = {};
            dataItem.saleDate    = messages["day.prodClass.saleDate"];
            dataItem.yoil        = messages["day.prodClass.yoil"];
            dataItem.totRealSaleAmt  = messages["day.prodClass.totRealSaleAmt"];
            dataItem.totSaleQty    = messages["day.prodClass.totSaleQty"];

            // 결제수단 헤더머지 컬럼 생성
            for (var i = 1; i <= arr.length; i++) {
                dataItem['pay' + i + "RealSaleAmt"] = arr[i-1];
                dataItem['pay' + i + "SaleQty"] = arr[i-1];
            }
            grid.columnHeaders.rows[0].dataItem = dataItem

        }, false);
    };

    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.dayProdClassSelectStoreShow = function () {
        $scope._broadcast('dayProdClassSelectStoreCtrl');
    };

    // 상품분류정보 팝업
    $scope.popUpProdClass = function() {
        var popUp = $scope.prodClassPopUpLayer;
        popUp.show(true, function (s) {
            // 선택 버튼 눌렀을때만
            if (s.dialogResult === "wj-hide-apply") {
                var scope = agrid.getScope('prodClassPopUpCtrl');
                var prodClassCd = scope.getSelectedClass();
                var params = {};
                params.prodClassCd = prodClassCd;
                // 조회 수행 : 조회URL, 파라미터, 콜백함수
                $scope._postJSONQuery.withPopUp("/popup/getProdClassCdNm.sb", params,
                    function(response){
                        $scope.prodClassCd = prodClassCd;
                        $scope.prodClassCdNm = response.data.data;
                    }
                );
            }
        });
    };

    // 상품분류정보 선택취소
    $scope.delProdClass = function(){
        $scope.prodClassCd = "";
        $scope.prodClassCdNm = "";
    }
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
                includeCellStyles: true,
                includeColumns: function (column) {
                    return column.visible;
                }
            },
                messages["day.day"] + '(' + messages["day.prodClass"] + ')_'  + getCurDateTime() +'.xlsx', function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                    }, 10);
                });
        }, 10);
    };
}]);