/****************************************************************
 *
 * 파일명 : mobileStoreDaySale.js
 * 설  명 : (모바일) 매장매출 > 일별매출 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.09.01     권지현      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  그리드 생성
 */
app.controller('mobileStoreDaySaleCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mobileStoreDaySaleCtrl', $scope, $http, false));

    // 검색조건에 조회기간
    var startDate = wcombo.genDateVal("#startDate", gvStartDate);
    var endDate = wcombo.genDateVal("#endDate", gvEndDate);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {};

    // <-- 검색 호출 -->
    $scope.$on("mobileStoreDaySaleCtrl", function(event, data) {
        gridOpen("mobileStoreDaySaleDtl");

        var startDt = new Date(wijmo.Globalize.format(startDate.value, 'yyyy-MM-dd'));
        var endDt = new Date(wijmo.Globalize.format(endDate.value, 'yyyy-MM-dd'));
        var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

        // 시작일자가 종료일자보다 빠른지 확인
        if(startDt.getTime() > endDt.getTime()){
            $scope._popMsg(messages['mobile.cmm.dateChk.error']);
            return false;
        }
        // 조회일자 최대 한달(31일) 제한
        if (diffDay > 31) {
            $scope._popMsg(messages['mobile.cmm.dateOver.1month.error']);
            return false;
        }

        var params = {};
        params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd'); // 조회기간
        params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd'); // 조회기간
        params.srchStoreCd = $("#mobileDaySaleStoreCd").val();
        params.diffDay = diffDay; // 조회기간 차이(차트 높이 때문에)

        // 할인내역
        $scope._broadcast("mobileStoreDaySaleDtlCtrl", params);

        event.preventDefault();
    });
    // <-- //검색 호출 -->


    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.mobileDaySaleStoreShow = function () {
        $scope._broadcast('mobileDaySaleStoreCtrl');
    };

}]);



/**
 *  일자별 매출현황 그리드 생성
 */
app.controller('mobileStoreDaySaleDtlCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mobileStoreDaySaleDtlCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');

        // 그리드 링크 효과
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

        // <-- 그리드 헤더2줄 -->
        // 헤더머지
        s.allowMerging = 2;
        s.columnHeaders.rows.push(new wijmo.grid.Row());

        // 첫째줄 헤더 생성
        var dataItem = {};
        dataItem.storeCd = messages["mobile.storeDaySale.storeCd"];
        dataItem.storeNm = messages["mobile.storeDaySale.storeNm"];
        dataItem.saleDate = messages["mobile.storeDaySale.saleDate"];
        dataItem.totSaleAmt = messages["mobile.storeDaySale.totSaleAmt"];
        dataItem.totDcAmt = messages["mobile.storeDaySale.totDcAmt"];
        dataItem.totRealSaleAmt = messages["mobile.storeDaySale.totRealSaleAmt"];
        dataItem.shopRealSaleAmt = messages["mobile.storeDaySale.shop"];
        dataItem.shopBillCnt = messages["mobile.storeDaySale.shop"];
        dataItem.shopBillUprc = messages["mobile.storeDaySale.shop"];
        dataItem.dlvrRealSaleAmt = messages["mobile.storeDaySale.dlvr"];
        dataItem.dlvrBillCnt = messages["mobile.storeDaySale.dlvr"];
        dataItem.dlvrBillUprc = messages["mobile.storeDaySale.dlvr"];
        dataItem.packRealSaleAmt = messages["mobile.storeDaySale.pack"];
        dataItem.packBillCnt = messages["mobile.storeDaySale.pack"];
        dataItem.packBillUprc = messages["mobile.storeDaySale.pack"];
        dataItem.totGuestCnt = messages["mobile.storeDaySale.totGuestCnt"];
        dataItem.guestUprc = messages["mobile.storeDaySale.guestUprc"];
        dataItem.cardAmt = messages["mobile.storeDaySale.pay"];
        dataItem.cashAmt = messages["mobile.storeDaySale.pay"];
        dataItem.etcAmt = messages["mobile.storeDaySale.pay"];
        dataItem.dcAmt = messages["mobile.storeDaySale.dc"];
        dataItem.coupnDcAmt = messages["mobile.storeDaySale.dc"];
        dataItem.etcDcAmt = messages["mobile.storeDaySale.dc"];

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


        // 그리드 클릭 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function (e) {
            var ht = s.hitTest(e);

            /* 머지된 헤더 셀 클릭시 정렬 비활성화
             * 헤더 cellType: 2 && 머지된 row 인덱스: 0 && 머지된 column 인덱스 4 초과
             * 머지영역 클릭시 소트 비활성화, 다른 영역 클릭시 소트 활성화
             */
            if(ht.cellType == 2 && ht.row < 1 && ht.col > 4) {
                s.allowSorting = false;
            } else {
                s.allowSorting = true;
            }
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("mobileStoreDaySaleDtlCtrl", function(event, data) {
        $scope.searchMobileStoreDaySaleDtl(data);
        event.preventDefault();
    });

    // 매출조회
    $scope.searchMobileStoreDaySaleDtl = function(data){
        var params = {};
        params.startDate = data.startDate;
        params.endDate = data.endDate;
        params.srchStoreCd = data.srchStoreCd;

        $scope._inquirySub("/mobile/sale/status/storeDaySale/mobileStoreDaySale/getMobileStoreDaySaleDtlList.sb", params, function() {
            // 조회 결과가 없으면 grid에'조회 결과 없음' Msg 띄우기
            gridShowMsgNoData("mobileStoreDaySaleDtl", $scope.flexMobileStoreDaySaleDtl, "Y");
        }, false);

        //create a group to show the grand totals
        // var grpLv1 = new wijmo.collections.PropertyGroupDescription('전체');
        var grpLv2 = new wijmo.collections.PropertyGroupDescription('store');

        var theGrid = new wijmo.Control.getControl('#mobileStoreDaySaleDtlGrid');

        theGrid.itemsSource = new wijmo.collections.CollectionView();

        // custom cell calculation
        theGrid.formatItem.addHandler(function(s, e) {

            var lengthTemp = s.collectionView.groupDescriptions.length;

            if (lengthTemp < 1) {
                // s.collectionView.groupDescriptions.push(grpLv1);
                s.collectionView.groupDescriptions.push(grpLv2);
            }

            s.rows.forEach(function(row) {
                if(row instanceof wijmo.grid.GroupRow){
                    var groupProp=row.dataItem.groupDescription.propertyName;
                    var className=null;
                    switch(groupProp){
                        case "전체":className="grp-lv-1";break;
                        case "clsFg":className="grp-lv-2";break;
                    }

                    if(className){
                        row.cssClass=className;
                    }

                    if(row.level == 0) {
                        if(!$scope.setCollapsed){
                            row.isCollapsed = true;
                        }
                    }
                }
            });


        });

        // 그리드 클릭 이벤트-------------------------------------------------------------------------------------------------
        theGrid.addEventListener(theGrid.hostElement, 'mousedown', function (e) {
            var ht = theGrid.hitTest(e);
            if (ht.cellType === wijmo.grid.CellType.Cell) {
                if (theGrid.rows[ht.row].level == 0) { // 1단계 분류
                    $scope.setCollapsed = true;
                    var isCollapsed = theGrid.rows[ht.row].isCollapsed;
                    theGrid.rows[ht.row].isCollapsed ? false : true;
                }
            }
        });

        // start collapsed
        theGrid.collapseGroupsToLevel(1);
        theGrid.collectionView.refresh();
    };
    // <-- //검색 호출 -->
}]);