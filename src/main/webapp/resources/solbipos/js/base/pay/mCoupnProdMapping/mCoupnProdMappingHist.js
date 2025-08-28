/****************************************************************
 *
 * 파일명 : mCoupnProdMappingHist.js
 * 설  명 : 모바일쿠폰상품매핑 이력조회 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2028.08.26     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  모바일쿠폰상품매핑 이력조회 팝업 조회 그리드 생성
 */
app.controller('mCoupnProdMappingHistCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mCoupnProdMappingHistCtrl', $scope, $http, false));

    // 처리일자
    $scope.srchStartDate = wcombo.genDateVal("#srchMCoupnProdMappingHistStartDate", gvStartDate);
    $scope.srchEndDate   = wcombo.genDateVal("#srchMCoupnProdMappingHistEndDate", gvEndDate);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // <-- 그리드 합치기 -->
        s.allowMerging = 'Cells';
        s.itemFormatter = function (panel, r, c, cell) {
            if (panel.cellType === wijmo.grid.CellType.Cell) {

                // 컬럼 병합(그리드 합치기)
                if(panel.columns[c].binding == "procDt") {
                    panel.columns[c].allowMerging = true;
                }

                // 합쳐진 컬럼 데이터 가운데 정렬
                wijmo.setCss(cell, {
                    display    : 'table',
                    tableLayout: 'fixed'
                });
                cell.innerHTML = '<div>' + cell.innerHTML + '</div>';
                wijmo.setCss(cell.children[0], {
                    display      : 'table-cell',
                    verticalAlign: 'middle',
                    textAlign    : 'center'
                });

                // readOnly 배경색 표시
                var col = panel.columns[c];
                if (col.isReadOnly) {
                    wijmo.addClass(cell, 'wj-custom-readonly');
                }

                // 오른쪽 정렬
                if (col.binding === "saleUprc") {
                    wijmo.setCss(cell.children[0], {
                        display      : 'table-cell',
                        verticalAlign: 'middle',
                        textAlign    : 'right'
                    });
                }
            }
        }
        // <-- //그리드 합치기 -->
    };

    // <-- 검색 호출 -->
    $scope.$on("mCoupnProdMappingHistCtrl", function(event, data) {
        // 조회구분 (A:가로, B:세로)
        var searchGubun = data;

        $scope.searchMCoupnProdMappingHistCnt(searchGubun);
        event.preventDefault();
    });

    $scope.searchMCoupnProdMappingHistCnt = function(searchGubun){
        var startDt = new Date(wijmo.Globalize.format($scope.srchStartDate.value, 'yyyy-MM-dd'));
        var endDt = new Date(wijmo.Globalize.format($scope.srchEndDate.value, 'yyyy-MM-dd'));

        // 시작일자가 종료일자보다 빠른지 확인
        if(startDt.getTime() > endDt.getTime()){
            $scope._popMsg(messages['cmm.dateChk.error']);
            return false;
        }

        var params = {};
        params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
        params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
        params.prodCd = $scope.prodCd;
        params.prodNm = $scope.prodNm;
        params.prodNm = $scope.prodNm;

        // 조회구분 (A:가로, B:세로)
        params.searchGubun = searchGubun;

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._postJSONQuery.withOutPopUp('/base/pay/mCoupnProdMapping/mCoupnProdMappingHist/getMCoupnProdMappingHistCnt.sb', params, function (response) {
            var mCoupnProdMappingHistCnt = response.data.data.result;
            $scope.mCoupnProdMappingHistCnt = mCoupnProdMappingHistCnt;

            // 모바일쿠폰사-상품코드 최대수
            params.mCoupnProdCnt = $scope.mCoupnProdMappingHistCnt.mcoupnProdCnt;

            // 조회
            $scope.searchMCoupnProdMappingHist(params);
        });
    };

    $scope.searchMCoupnProdMappingHist = function(params){
        $scope._inquiryMain("/base/pay/mCoupnProdMapping/mCoupnProdMappingHist/getMCoupnProdMappingHistList.sb", params, function(){
            // <-- 그리드 생성 -->
            var grid = wijmo.Control.getControl("#wjMCoupnProdMappingHistGridList");

            while(grid.columns.length > 4){
                grid.columns.removeAt(grid.columns.length-1);
            }

            if(params.searchGubun == "A") {
                grid.columns.push(new wijmo.grid.Column({header: messages["mCoupnProdMappingHist.mcoupnNm"], binding: 'mcoupnNm', width: 110, align: "center" , isReadOnly: "true"}));
                for(var j=1; j<params.mCoupnProdCnt+1; j++){
                    grid.columns.push(new wijmo.grid.Column({header: messages["mCoupnProdMappingHist.mappingCd"]+j, binding: 'mcoupnProdCd'+j, width: 100, align: "center" , isReadOnly: "true"}));
                }
            } else {
                grid.columns.push(new wijmo.grid.Column({header: messages["mCoupnProdMappingHist.saleUprc"], binding: 'saleUprc', width: 80, align: "right" , isReadOnly: "true"}));
                grid.columns.push(new wijmo.grid.Column({header: messages["mCoupnProdMappingHist.mcoupnNm"], binding: 'mcoupnNm', width: 110, align: "center" , isReadOnly: "true"}));
                grid.columns.push(new wijmo.grid.Column({header: messages["mCoupnProdMappingHist.mappingCd"], binding: 'mcoupnProdCd', width: 100, align: "center" , isReadOnly: "true"}));
                grid.columns.push(new wijmo.grid.Column({header: messages["mCoupnProdMappingHist.remark"], binding: 'remark', width: 120, align: "center" , isReadOnly: "true"}));
            }
            // <-- //그리드 생성 -->
        });
    };
    // <-- //검색 호출 -->

    // 현재화면 엑셀다운로드
    $scope.excelDownload = function () {
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
            },
                "모바일쿠폰상품매핑 이력조회_" + getCurDateTime()+'.xlsx', function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                    }, 10);
                });
        }, 10);
    };

    // 팝업 닫기
    $scope.close = function() {
        $scope.prodCd = "";
        $scope.prodNm = "";

        $scope.wjMCoupnProdMappingHistLayer.hide();
        event.preventDefault();
    };

}]);