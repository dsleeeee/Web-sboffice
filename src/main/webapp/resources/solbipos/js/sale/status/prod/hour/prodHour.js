/**
 * get application
 */
var app = agrid.getApp();

// 시간대 DropBoxDataMap
var vSaleTime = [
    {"name":"전체","value":""},
    {"name":"01시","value":"1"},
    {"name":"02시","value":"2"},
    {"name":"03시","value":"3"},
    {"name":"04시","value":"4"},
    {"name":"05시","value":"5"},
    {"name":"06시","value":"6"},
    {"name":"07시","value":"7"},
    {"name":"08시","value":"8"},
    {"name":"09시","value":"9"},
    {"name":"10시","value":"10"},
    {"name":"11시","value":"11"},
    {"name":"12시","value":"12"},
    {"name":"13시","value":"13"},
    {"name":"14시","value":"14"},
    {"name":"15시","value":"15"},
    {"name":"16시","value":"16"},
    {"name":"17시","value":"17"},
    {"name":"18시","value":"18"},
    {"name":"19시","value":"19"},
    {"name":"20시","value":"20"},
    {"name":"21시","value":"21"},
    {"name":"22시","value":"22"},
    {"name":"23시","value":"23"},
    {"name":"24시","value":"24"}
];

/** 과세면별 controller */
app.controller('prodHourCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('prodHourCtrl', $scope, $http, true));

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("srchSaleTimeCombo", vSaleTime);

    $scope.srchStartDate = wcombo.genDateVal("#srchHourStartDate", gvStartDate);
    $scope.srchEndDate   = wcombo.genDateVal("#srchHourEndDate", gvEndDate);
    $scope.orgnFg        = gvOrgnFg;
    
    // 콤보박스 데이터 Set
    $scope._setComboData('prodHourlistScaleBox', gvListScaleBoxData);
    
    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

       // picker 사용시 호출 : 미사용시 호출안함
       $scope._makePickColumns("prodHourCtrl");

       // add the new GroupRow to the grid's 'columnFooters' panel
       s.columnFooters.rows.push(new wijmo.grid.GroupRow());
       // add a sigma to the header to show that this is a summary row
       s.bottomLeftCells.setCellData(0, 0, '합계');

        // 헤더머지
        s.allowMerging = 2;
        s.columnHeaders.rows.push(new wijmo.grid.Row());
        // 첫째줄 헤더 생성
        var dataItem         = {};
        dataItem.prodCd    = messages["prodhour.prodCd"];
        dataItem.prodNm         = messages["prodhour.prodNm"];

        // 시간대별 컬럼 생성
        for (var i = 1; i < 25; i++) {

            if(i<10){
                dataItem['totSaleQty' + i] = i + "시";
                dataItem['totSaleAmt' + i] = i + "시";

            }else{
                dataItem['totSaleQty' + i] = i + "시";
                dataItem['totSaleAmt' + i] = i + "시";
            }
            j=0;
        }

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

    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("prodHourCtrl", function (event, data) {
        $scope.searchProdHourList(true);
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });
    
    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("prodHourCtrlSrch", function (event, data) {
        $scope.searchProdHourList(false);
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });
    
    // 시간별 리스트 조회
    $scope.searchProdHourList = function (isPageChk) {
        $scope.searchedStoreCd = $("#dayTimeSelectStoreCd").val();
       
        // 파라미터
        var params= {};
        params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
        params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
        
        if(params.startDate > params.endDate){
       	 	$scope._popMsg(messages["prodsale.dateChk"]); // 조회종료일자가 조회시작일자보다 빠릅니다.
       	 	return false;
        }

        params.storeCd = $scope.searchedStoreCd;
        params.saleTime = $scope.saleTime;
        params.listScale = $scope.prodHourlistScale; //-페이지 스케일 갯수
        params.isPageChk = isPageChk;

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/sale/status/prod/hour/list.sb", params, function() {});

        // 선택한 시간대에 따른 리스트 항목 visible
        var grid = wijmo.Control.getControl("#wjGridList");
        var columns = grid.columns;
        var start = 0;
        var end = 0;

        if($scope.saleTime === "1"){ 
            start = 3;
            end = 4;
        }else if($scope.saleTime  === "2"){ 
            start = 5;
            end = 6;
        }else if($scope.saleTime  === "3"){ 
            start = 7;
            end = 8;
        }else if($scope.saleTime  === "4"){ 
            start = 9;
            end = 10;
        }else if($scope.saleTime  === "5"){ 
            start = 11;
            end = 12;
        }else if($scope.saleTime  === "6"){ 
            start = 13;
            end = 14;
        }else if($scope.saleTime  === "7"){ 
            start = 15;
            end = 16;
        }else if($scope.saleTime  === "8"){ 
            start = 17;
            end = 18;
        }else if($scope.saleTime  === "9"){ 
            start = 19;
            end = 20;
        }else if($scope.saleTime  === "10"){ 
            start = 21;
            end = 22;
        }else if($scope.saleTime  === "11"){ 
            start = 23;
            end = 24;
        }else if($scope.saleTime  === "12"){ 
            start = 25;
            end = 26;
        }else if($scope.saleTime  === "13"){ 
            start = 27;
            end = 28;
        }else if($scope.saleTime  === "14"){
            start = 29;
            end = 30;    
        }else if($scope.saleTime  === "15"){ 
            start = 31;
            end = 32;
        }else if($scope.saleTime  === "16"){ 
            start = 33;
            end = 34;
        }else if($scope.saleTime  === "17"){
            start = 35;
            end = 36;
        }else if($scope.saleTime  === "18"){ 
            start = 37;
            end = 38;
        }else if($scope.saleTime  === "19"){ 
            start = 39;
            end = 40;
        }else if($scope.saleTime  === "20"){ 
            start = 41;
            end = 42;
        }else if($scope.saleTime  === "21"){ 
            start = 43;
            end = 44;
        }else if($scope.saleTime  === "22"){
            start = 45;
            end = 46;
        }else if($scope.saleTime  === "23"){
            start = 47;
            end = 48;
        }else if($scope.saleTime  === "24"){
            start = 49;
            end = 50;
        }else if($scope.saleTime === ""){ //전체
            start = 3;
            end = 50;
        }
        
        start++;
        end++;

        for(var i = 3; i <= 74; i++){
            if(i >= start && i <= end){
                columns[i].visible = true;
            }else{
                columns[i].visible = false;
            }
        }
    };

    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.dayTimeSelectStoreShow = function () {
        $scope._broadcast('dayTimeSelectStoreCtrl');
    };
    
    // 엑셀 다운로드
    $scope.excelDownloadHour = function () {
      if ($scope.flex.rows.length <= 0) {
        $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
        return false;
      }

      $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
      $timeout(function () {
        wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
          includeColumnHeaders: true,
          includeCellStyles   : false,
          includeColumns      : function (column) {
            return column.visible;
          }
        }, 'excel.xlsx', function () {
          $timeout(function () {
            $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
          }, 10);
        });
      }, 10);
    };
}]);