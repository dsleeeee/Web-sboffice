/****************************************************************
 *
 * 파일명 : mobileDailyIoStockInfo.js
 * 설  명 : (모바일) 재고현황 > (공통) 상품코드 상세 팝업
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2024.07.30     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('mobileProdCodeDtlCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mobileProdCodeDtlCtrl', $scope, $http, true));

    // 접속사용자의 권한(H : 본사, S: 매장)
    //$scope.orgnFg = gvOrgnFg;

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // picker 사용시 호출 : 미사용시 호출안함
        $scope._makePickColumns("mobileProdCodeDtlCtrl");

        // 그리드 클릭 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function (e) {
            var ht = s.hitTest(e);

            // 병합된 그리드 헤더 클릭시 정렬기능 제거
            if (ht.panel == s.columnHeaders && !ht.edgeRight && !e['dataTransfer']) {
                var rng = s.getMergedRange(ht.panel, ht.row, ht.col);
                if (rng && rng.columnSpan > 1) {
                    e.preventDefault();
                }
            }
        }, true);

        // 총매출열에 CSS 추가
        wijmo.addClass(s.columns[2], 'wijLink');
        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');

        // <-- 그리드 헤더2줄 -->
        // 헤더머지
        s.allowMerging = 2;
        s.columnHeaders.rows.push(new wijmo.grid.Row());

        // 첫째줄 헤더 생성
        var dataItem				= {};

        dataItem.ioOccrDt			= messages["periodIostock.date"]; // 날짜

        // 본사
        dataItem.vendrInQty 		= messages["periodIostock.ioOccr01"]; // 본사입고
        dataItem.vendrInTot 		= messages["periodIostock.ioOccr01"];
        dataItem.vendrOutQty 		= messages["periodIostock.ioOccr16"]; // 업체반출
        dataItem.vendrOutTot 		= messages["periodIostock.ioOccr16"];
        dataItem.hqOutQty	 		= messages["periodIostock.ioOccr13"]; // 본사출고
        dataItem.hqOutTot	 		= messages["periodIostock.ioOccr13"];
        dataItem.hqInQty 			= messages["periodIostock.ioOccr02"]; // 본사반입
        dataItem.hqInTot 			= messages["periodIostock.ioOccr02"];

        // 본사
        dataItem.storeMoveInQty 	= messages["periodIostock.ioOccr04"]; // 매장이입
        dataItem.storeMoveInTot 	= messages["periodIostock.ioOccr04"];
        dataItem.storeMoveOutQty 	= messages["periodIostock.ioOccr14"]; // 매장이출
        dataItem.storeMoveOutTot 	= messages["periodIostock.ioOccr14"];
        dataItem.disuseQty 			= messages["periodIostock.ioOccr17"]; // 재고폐기
        dataItem.adjQty 			= messages["periodIostock.ioOccr21"]; // 재고조정
        dataItem.setInQty 			= messages["periodIostock.ioOccr22"]; // 세트생성
        dataItem.saleVendrOrderQty	= messages["periodIostock.ioOccr19"]; // 거래처출고
        dataItem.saleVendrOrderTot	= messages["periodIostock.ioOccr19"];
        dataItem.saleVendrRtnQty	= messages["periodIostock.ioOccr33"]; // 거래처반품
        dataItem.saleVendrRtnTot	= messages["periodIostock.ioOccr33"];

        // 매장
        dataItem.moveInQty 			= messages["periodIostock.ioOccr04"]; // 매장이입
        dataItem.moveInTot 			= messages["periodIostock.ioOccr04"];
        dataItem.moveOutQty 		= messages["periodIostock.ioOccr14"]; // 매장이출
        dataItem.moveOutTot 		= messages["periodIostock.ioOccr14"];
        dataItem.storeInQty 		= messages["periodIostock.ioOccr03"]; // 매장입고
        dataItem.storeInTot 		= messages["periodIostock.ioOccr03"];
        dataItem.storeOutQty 		= messages["periodIostock.ioOccr12"]; // 매장반품
        dataItem.storeOutTot 		= messages["periodIostock.ioOccr12"];
        dataItem.purchsInQty 		= messages["periodIostock.ioOccr06"]; // 사입입고
        dataItem.purchsInTot 		= messages["periodIostock.ioOccr06"];
        dataItem.purchsOutQty		= messages["periodIostock.ioOccr18"]; // 사입반품
        dataItem.purchsOutTot		= messages["periodIostock.ioOccr18"];
        dataItem.storeSaleQty		= messages["periodIostock.ioOccr11"]; // 매장판매
        dataItem.storeSaleTot		= messages["periodIostock.ioOccr11"];

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

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("mobileProdCodeDtlCtrl", function (event, data) {
        $scope.startDate 		= data.startDate;
        $scope.endDate			= data.endDate;
        $scope.prodCd    		= data.prodCd; // 상품코드
        $scope.prodNm    		= data.prodNm; // 상품명
        $scope.orgnFg     		= data.orgnFg;
        $scope.storeCd			= data.storeCd; // 매장코드
        $scope.storeNm			= data.storeNm; // 매장명
        $scope.storeCd  		= data.storeCd; // 매장코드
        $scope.hqOfficeCd		= hqOfficeCd; // 본사코드



        $scope.mobileProdCodeDtlLayer.show(true);

        $scope.searchMobileProdCodeDtlList();

        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    $scope.searchMobileProdCodeDtlList = function(){
        var params			={};
        params.startDate	= $scope.startDate;
        params.endDate	= $scope.endDate;
        params.prodCd		= $scope.prodCd;
        params.storeCd	= $scope.storeCd;
        params.hqOfficeCd = $scope.hqOfficeCd;

        //가상로그인 session 설정
        if(document.getElementsByName('sessionId')[0]){
            params.sid = document.getElementsByName('sessionId')[0].value;
        }

        $scope._inquirySub("/mobile/stock/com/popup/mobileCmmQtyDtl/getCmmProdCodeDtlList.sb", params);
    };

    //엑셀 다운로드
    $scope.excelDownload = function () {
        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
            return false;
        }

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
        $timeout(function () {
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
                includeColumnHeaders: true,
                includeCellStyles   : true,
                includeColumns      : function (column) {
                    return column.visible;
                }
            }, messages["periodIostock.iostockDetail"]+'_'+getToday()+'.xlsx', function () {
                $timeout(function () {
                    $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                }, 10);
            });
        }, 10);
    };
}]);