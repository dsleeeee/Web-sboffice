/****************************************************************
 *
 * 파일명 : vendr.js
 * 설  명 : 거래처관리목록 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.04.23     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  거래처관리목록 생성
 */
app.controller('vendrCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('vendrCtrl', $scope, $http, false));

    // comboBox 초기화
    $scope._setComboData("listScaleBox", gvListScaleBoxData);

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("vendorFg", vendorFg); // 조회구분

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        $scope.vendorFgDataMap = new wijmo.grid.DataMap(vendorFgNm, 'value', 'name');
        $scope.useYnDataMap    = new wijmo.grid.DataMap(useYn, 'value', 'name');

        // 그리드 포맷
        s.formatItem.addHandler(function(s, e) {
            if (e.panel == s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
                if( col.binding == "vendrCd" ) {
                    wijmo.addClass(e.cell, 'wijLink');
                }
            }
        });

        // 그리드 선택 이벤트
        s.addEventListener(s.hostElement, 'click', function(e) {
            var ht = s.hitTest(e);
            if( ht.cellType == wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];
                // 거래처코드
                if( col.binding == "vendrCd" ) {
                    $scope.setVendrInfo(s.rows[ht.row].dataItem);
                    $scope.wjVendrInfoLayer.show(true);
                    event.preventDefault();
                }
            }
        });

        // 거래처목록 조회
        $scope.searchVendrList();
    }

    // 거래처 상세 정보
    $scope.vendrInfo = {};
    $scope.setVendrInfo = function(data){
        $scope.vendrInfo = data;
    };
    $scope.getVendrInfo = function(){
        return $scope.vendrInfo;
    };

    // <-- 검색 호출 -->
    $scope.$on("vendrCtrl", function(event, data) {
        $scope.searchVendrList();
        event.preventDefault();
    });
    
    // 거래처목록 조회
    $scope.searchVendrList = function(){
        var param = {};
        param.vendrCd = $scope.vendrCd;
        param.vendrNm = $scope.vendrNm;
        if(urlVendorFg == "2" || urlVendorFg == "1") {
            param.vendorFg = urlVendorFg;
        } else {
            param.vendorFg = $scope.vendorFg;
        }
        param.listScale = listScaleBox.selectedValue;

        $scope._inquiryMain("/base/prod/vendr/vendr/getVendrList.sb", param, function() {}, false);
    };

    // 거래처등록 팝업 오픈
    $scope.regVendr = function(){

        var data = {};
        data.type = "reg";
        data.vendrCd = "";

        $scope.wjVendrRegistLayer.show(true);
        $scope.setVendrInfo(data);
        event.preventDefault();
    };

    // 화면 ready 된 후 설정
    angular.element(document).ready(function () {

        // 거래처관리 신규등록,수정 팝업 핸들러 추가
        $scope.wjVendrRegistLayer.shown.addHandler(function (s) {
            setTimeout(function() {
                $scope._broadcast('vendrRegistCtrl', $scope.getVendrInfo());
            }, 50)
        });

        // 거래처관리 상세 팝업 핸들러 추가
        $scope.wjVendrInfoLayer.shown.addHandler(function (s) {
            setTimeout(function() {
                $scope._broadcast('vendrInfoCtrl', $scope.getVendrInfo());
            }, 50)
        });
    });

}]);