/****************************************************************
 *
 * 파일명 : billPopup.js
 * 설  명 : 매출상세정보 팝업
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.08.28     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();
var orderAddFgData = [
    {"name":"오프라인","value":"1"},
    {"name":"온라인","value":"2"}
]
/** 영수증 상세 내역 controller */
app.controller('billPopupCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('billPopupCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        $scope.orderAddFgDataMap = new wijmo.grid.DataMap(orderAddFgData, 'value', 'name');

        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');

        // 헤더머지
        s.allowMerging = 2;
        s.columnHeaders.rows.push(new wijmo.grid.Row());

        // 첫째줄 헤더 생성
        var dataItem         = {};
        dataItem.billDtlNo   = messages["billPopup.billDtlNo"];
        dataItem.prodCd      = messages["billPopup.prodCd"];
        dataItem.prodNm      = messages["billPopup.prodNm"];
        dataItem.orderAddFg  = messages["billPopup.orderAddFg"];
        dataItem.saleQty     = messages["billPopup.saleQty"];
        dataItem.saleUprc    = messages["billPopup.saleUprc"];
        dataItem.saleAmt     = messages["billPopup.saleInfo"];
        dataItem.dcAmt       = messages["billPopup.saleInfo"];
        dataItem.realSaleAmt = messages["billPopup.saleInfo"];
        dataItem.gaAmt       = messages["billPopup.saleInfo"];
        dataItem.vatAmt      = messages["billPopup.saleInfo"];
        // 할인구분 헤더머지 컬럼 생성
        for (var i = 0; i < arrDcCol.length; i++) {
            dataItem['dc' + arrDcCol[i]] = messages["billPopup.dcInfo"];
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
    $scope.$on("billPopupCtrl", function (event, data) {
        $scope.storeCd  = data.storeCd;
        $scope.saleDate = data.saleDate;
        $scope.posNo    = data.posNo;
        $scope.billNo   = data.billNo;

        $scope.wjBillPopupLayer.show(true);

        $scope.getBillInfo();

        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });


    // 영수증 종합내역 조회
    $scope.getBillInfo = function () {
        // 로딩바 show
        $scope.$broadcast('loadingPopupActive');

        var params      = {};
        params.hqOfficeCd = hqOfficeCd;
        params.storeCd  = $scope.storeCd;
        params.saleDate = $scope.saleDate;
        params.posNo    = $scope.posNo;
        params.billNo   = $scope.billNo;

        // ajax 통신 설정
        $http({
            method : 'POST', //방식
            url    : '/dlvr/manage/anals/dlvr/getBillInfo.sb', /* 통신할 URL */
            params : params, /* 파라메터로 보낼 데이터 */
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        }).then(function successCallback(response) {
            if ($scope._httpStatusCheck(response, true)) {
                if (!$.isEmptyObject(response.data.data)) {
                    var data = response.data.data;

                    $("#billSubTitle").html('매장 : '+'['+data.storeCd+']'+ data.storeNm+'  |  매출일자 : '+getFormatDate($scope.saleDate)+'  |  포스번호 : '+$scope.posNo+'  |  영수번호 : '+$scope.billNo);

                    data.totSaleAmt   = addComma(data.totSaleAmt);
                    data.totDcAmt     = addComma(data.totDcAmt);
                    data.realSaleAmt  = addComma(data.realSaleAmt);
                    data.netSaleAmt   = addComma(data.netSaleAmt);
                    data.noTaxSaleAmt = addComma(data.noTaxSaleAmt);
                    data.taxSaleAmt   = addComma(data.taxSaleAmt);
                    data.vatAmt       = addComma(data.vatAmt);
                    data.totTipAmt    = addComma(data.totTipAmt);

                    $scope.billInfo = data; // view 종합내역에 조회한 값 세팅
                    $scope.getBillPayInfo(); // 결제내역 조회
                }
                $scope.$broadcast('loadingPopupInactive');
            }
        }, function errorCallback(response) {
            // called asynchronously if an error occurs
            // or server returns response with an error status.
            // 로딩바 hide
            $scope.$broadcast('loadingPopupInactive');
            if (response.data.message) {
                $scope._popMsg(response.data.message);
            } else {
                $scope._popMsg(messages['cmm.error']);
            }
            return false;
        }).then(function () {
            // "complete" code here
        });
    };


    // 영수증 결제내역 조회
    $scope.getBillPayInfo = function () {

        var params      = {};
        params.hqOfficeCd = hqOfficeCd;
        params.storeCd  = $scope.storeCd;
        params.saleDate = $scope.saleDate;
        params.posNo    = $scope.posNo;
        params.billNo   = $scope.billNo;
        params.payCol   = payCol;

        // ajax 통신 설정
        $http({
            method : 'POST', //방식
            url    : '/dlvr/manage/anals/dlvr/billPayInfo.sb', /* 통신할 URL */
            params : params, /* 파라메터로 보낼 데이터 */
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        }).then(function successCallback(response) {
            if ($scope._httpStatusCheck(response, true)) {
                if (!$.isEmptyObject(response.data.data)) {
                    var data = response.data.data;

                    // 조회한 결제수단 data에 addComma 추가
                    for (var i = 0; i < arrPayCol.length; i++) {
                        data['pay' + arrPayCol[i]] = (nvl(data['pay' + arrPayCol[i]], '') !== '' ? addComma(data['pay' + arrPayCol[i]]) : '');
                    }

                    $scope.billPayInfo = data; // view 결제내역에 조회한 값 세팅
                    $scope.getBillGuestInfo(); // 방문인원 조회
                }
            }
        }, function errorCallback(response) {
            // called asynchronously if an error occurs
            // or server returns response with an error status.
            // 로딩바 hide
            $scope.$broadcast('loadingPopupInactive');
            if (response.data.message) {
                $scope._popMsg(response.data.message);
            } else {
                $scope._popMsg(messages['cmm.error']);
            }
            return false;
        }).then(function () {
            // "complete" code here
        });
    };


    // 영수증 방문인원 조회
    $scope.getBillGuestInfo = function () {

        var params      = {};
        params.hqOfficeCd = hqOfficeCd;
        params.storeCd  = $scope.storeCd;
        params.saleDate = $scope.saleDate;
        params.posNo    = $scope.posNo;
        params.billNo   = $scope.billNo;

        // ajax 통신 설정
        $http({
            method : 'POST', //방식
            url    : '/dlvr/manage/anals/dlvr/billGuestInfo.sb', /* 통신할 URL */
            params : params, /* 파라메터로 보낼 데이터 */
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        }).then(function successCallback(response) {
            if ($scope._httpStatusCheck(response, true)) {
                if (!$.isEmptyObject(response.data.data)) {
                    var data = response.data.data;

                    data.guest01 = (nvl(data.guest01, '') !== '' ? addComma(data.guest01) : '');
                    data.guest02 = (nvl(data.guest02, '') !== '' ? addComma(data.guest02) : '');
                    data.guest03 = (nvl(data.guest03, '') !== '' ? addComma(data.guest03) : '');
                    data.guest04 = (nvl(data.guest04, '') !== '' ? addComma(data.guest04) : '');

                    $scope.billGuestInfo = data;
                    $scope.$broadcast('loadingPopupInactive');
                }

                // 영수증 상품내역 리스트 조회
                $scope.searchBillInfoProdList();

            }
        }, function errorCallback(response) {
            // called asynchronously if an error occurs
            // or server returns response with an error status.
            // 로딩바 hide
            $scope.$broadcast('loadingPopupInactive');
            if (response.data.message) {
                $scope._popMsg(response.data.message);
            } else {
                $scope._popMsg(messages['cmm.error']);
            }
            return false;
        }).then(function () {
            // "complete" code here
        });
    };


    // 영수증 상품내역 리스트 조회
    $scope.searchBillInfoProdList = function () {
        // 파라미터
        var params       = {};
        params.hqOfficeCd = hqOfficeCd;
        params.storeCd   = $scope.storeCd;
        params.saleDate  = $scope.saleDate;
        params.posNo     = $scope.posNo;
        params.billNo    = $scope.billNo;
        params.dcCol     = dcCol;

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/dlvr/manage/anals/dlvr/billProdList.sb", params, function () {
        });
    };


    // 팝업 닫기
    $scope.close = function(){

        // 초기화
        var oScope = agrid.getScope("billPopupCtrl");
        $("#billSubTitle").val("");
        $scope.billPopup = "";
        $scope.billPayInfo = "";
        $scope.billGuestInfo = "";
        oScope._gridDataInit();
        $scope.wjBillPopupLayer.hide();
    };

}]);

