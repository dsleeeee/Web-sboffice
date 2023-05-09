/****************************************************************
 *
 * 파일명 : nonSaleCard.js
 * 설  명 : 비매출카드상세 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2023.05.03     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  비매출카드상세 그리드 생성
 */
app.controller('nonSaleCardCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('nonSaleCardCtrl', $scope, $http, false));

    // 검색조건에 조회기간
    var startDate = wcombo.genDateVal("#srchApprNcardStartDate", gvStartDate);
    var endDate = wcombo.genDateVal("#srchApprNcardEndDate", gvEndDate);

    // 조회조건 승인구분 데이터 Set
    $scope._setComboData("srchNcardSaleYnDisplay", [
        {"name": messages["cmm.all"], "value": ""},
        {"name": messages["appr.approve"], "value": "Y"},
        {"name": messages["cmm.cancel"], "value": "N"}
    ]);

    // 조회조건 승인처리 데이터 Set
    $scope._setComboData("srchNcardApprProcFgDisplay", [
        {"name": messages["cmm.all"], "value": ""},
        {"name": messages["card.apprProcFg1"], "value": "1"},
        {"name": messages["card.apprProcFg2"], "value": "2"}
    ]);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');

        // <-- 그리드 헤더2줄 -->
        // 헤더머지
        s.allowMerging = 2;
        s.columnHeaders.rows.push(new wijmo.grid.Row());

        // 첫째줄 헤더 생성
        var dataItem = {};
        dataItem.storeCd = messages["nonSaleCard.storeCd"];
        dataItem.storeNm = messages["nonSaleCard.storeNm"];
        dataItem.saleDate = messages["nonSaleCard.saleDate"];
        dataItem.posNo = messages["nonSaleCard.posNo"];
        dataItem.billNo = messages["nonSaleCard.billNo"];
        dataItem.apprProc = messages["nonSaleCard.appr"];
        dataItem.apprProcFg = messages["nonSaleCard.appr"];
        dataItem.acquireNm = messages["nonSaleCard.acquireNm"];
        dataItem.cardTypeFg = messages["nonSaleCard.cardTypeFg"];
        dataItem.cardNo = messages["nonSaleCard.cardNo"];
        dataItem.saleAmt = messages["nonSaleCard.apprSaleAmt"];
        dataItem.tipAmt = messages["nonSaleCard.apprSaleAmt"];
        dataItem.vatAmt = messages["nonSaleCard.apprSaleAmt"];
        dataItem.instCntNm = messages["nonSaleCard.inst"];
        dataItem.instCnt = messages["nonSaleCard.inst"];
        dataItem.exDate = messages["nonSaleCard.exDate"];
        dataItem.apprDt = messages["nonSaleCard.apprDt"];
        dataItem.apprNo = messages["nonSaleCard.apprNo"];
        dataItem.apprAmt = messages["nonSaleCard.apprAmt"];

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

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel == s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
                if (col.binding === "billNo") {
                    wijmo.addClass(e.cell, 'wijLink');
                    wijmo.addClass(e.cell, 'wj-custom-readonly');
                }
            }
        });

        // 그리드 클릭 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function (e) {
            var ht = s.hitTest(e);
            if (ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];
                var selectedRow = s.rows[ht.row].dataItem;
                if (col.binding === "billNo") {
                    var params = {};
                    params.storeCd = selectedRow.storeCd;
                    params.saleDate = selectedRow.saleDate.replaceAll("-", "");
                    params.posNo = selectedRow.posNo;
                    params.billNo = selectedRow.billNo;
                    params.saleYn = selectedRow.saleYn;

                    $scope._broadcast('billInfoCtrl', params);
                }
            }
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("nonSaleCardCtrl", function(event, data) {
        $scope.searchNonSaleCard();
        event.preventDefault();
    });

    $scope.searchNonSaleCard = function(){
        var startDt = new Date(wijmo.Globalize.format(startDate.value, 'yyyy-MM-dd'));
        var endDt = new Date(wijmo.Globalize.format(endDate.value, 'yyyy-MM-dd'));
        var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

        // 시작일자가 종료일자보다 빠른지 확인
        if(startDt.getTime() > endDt.getTime()){
            $scope._popMsg(messages['cmm.dateChk.error']);
            return false;
        }

        // 조회일자 최대 1달(31일) 제한
        if (diffDay > 31) {
            $scope._popMsg(messages['cmm.dateOver.1month.error']);
            return false;
        }

        // 파라미터
        var params = {};
        params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd');
        params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd');
        params.storeCd = $("#apprNcardSelectStoreCd").val();
        params.posNo = $("#apprNcardSelectPosCd").val();
        params.saleYn = $scope.saleYnModel;
        params.apprProcFg = $scope.apprProcFgModel;

        $scope._inquiryMain("/sale/status/nonSaleCard/nonSaleCard/getNonSaleCardList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    //매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.apprNcardSelectStoreShow = function () {
        $scope._broadcast('apprNcardSelectStoreCtrl');
    };

    //포스선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.apprNcardSelectPosShow = function () {
        $scope._broadcast('apprNcardSelectPosCtrl');
    };

    //매장의 포스(pos) 리스트 조회
    $scope.getPosNmList = function () {
        var url = '/sale/status/pos/pos/posNmList.sb';
        var comboParams = {};
        comboParams.storeCd = $("#posNcardSelectStoreCd").val();
    };

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
                includeCellStyles: false,
                includeColumns: function (column) {
                    return column.visible;
                }
            },  '비매출카드상세_' + getCurDateTime() +'.xlsx', function () {
                $timeout(function () {
                    $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                }, 10);
            });
        }, 10);
    };

}]);