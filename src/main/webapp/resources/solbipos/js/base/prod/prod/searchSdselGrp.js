/****************************************************************
 *
 * 파일명 : searchSdselGrp.js
 * 설  명 : 선택메뉴 조회 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.09.13     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

var sdselTypeFgData = [
    {"name": "세트", "value": "C"},
    {"name": "싱글세트", "value": "S"}
];

/**
 *  선택메뉴 조회 그리드 생성
 */
app.controller('searchSdselGrpCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('searchSdselGrpCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // 그리드 DataMap 설정
        $scope.sdselTypeFgDataMap = new wijmo.grid.DataMap(sdselTypeFgData, 'value', 'name'); // 세트구분

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                if (col.binding === "sdselGrpCd"){ // 선택메뉴코드
                    wijmo.addClass(e.cell, 'wijLink');
                }
            }
        });

        // 그리드 클릭 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function (e) {

            var ht = s.hitTest(e);
            if (ht.cellType === wijmo.grid.CellType.Cell) {
                var col         = ht.panel.columns[ht.col];
                var selectedRow = s.rows[ht.row].dataItem;
                if (col.binding === "sdselGrpCd") { // 선택메뉴코드 클릭

                    $("#_sdselGrpNm").val(selectedRow.sdselGrpNm);
                    $("#_sdselGrpCd").val(selectedRow.sdselGrpCd);
                    $("#_sdselGrpNmCd").val("[" + selectedRow.sdselGrpCd + "] " + selectedRow.sdselGrpNm);

                    var scope = agrid.getScope("prodModifyCtrl")
                    scope.prodModifyInfo.sdselGrpCd     = selectedRow.sdselGrpCd;
                    scope.prodModifyInfo.sdselGrpNm     = selectedRow.sdselGrpNm;
                    scope.prodModifyInfo.sdselGrpNmCd   = "[" + selectedRow.sdselGrpCd + "] " + selectedRow.sdselGrpNm;

                    $scope.close();

                }
            }
        });
        
    };

    $scope.$on("searchSdselGrpCtrl", function(event, data) {
        
        // 선택메뉴 리스트 조회
        $scope.searchSdselGrp();
        event.preventDefault();
    });

    // 선택메뉴 리스트 조회
    $scope.searchSdselGrp = function () {

        var params = {};
        params.sdselGrpCd = $("#searchSdselGrpCd").val();
        params.sdselGrpNm = $("#searchSdselGrpNm").val();

        $scope._inquirySub("/base/prod/prod/prod/getSearchSdselGrpList.sb", params, function() {}, false);
    }

    // 신규선택메뉴생성
    $scope.newSdsel = function (){
        var scope = agrid.getScope('prodModifyCtrl');

        //선택한 그룹이 없으면 신규생성
        if(scope.prodModifyInfo.sdselGrpCd === "" && (scope.getNewSdselGrpCd() === "" || scope.getNewSdselGrpCd() === undefined)){
            // 선택그룹을 생성
            // 파라미터 설정
            var params = [];
            var param = {};
            param.status = "I";
            var prodInfoScope = agrid.getScope('prodModifyCtrl');
            if(prodInfoScope.prodModifyInfo.prodNm === ""){
                param.sdselGrpNm = "신상품";
            } else {
                param.sdselGrpNm = prodInfoScope.prodModifyInfo.prodNm;
            }
            param.fixProdFg = "1";
            param.sdselTypeFg = "C";
            params.push(param);

            $scope._save('/base/prod/sideMenu/menuGrp/save.sb', params, function() {
                $scope.wjNewSdselLayer.show();
                var grpScope = agrid.getScope('newSdselGrpCtrl');
                grpScope.searchSdselGrp();
                setTimeout(function() {
                    grpScope.selectNewGrp();
                }, 300);
            }, false);
        } else {
            $scope.wjNewSdselLayer.show();
            var scope = agrid.getScope('newSdselGrpCtrl');
            scope.searchSdselGrp();
            scope.flex.fixed();
        }

    };

    $scope.close = function (){
        $("#searchSdselGrpCd").val("");
        $("#searchSdselGrpNm").val("");
        $scope.wjSearchSdselGrpLayer.hide();
    }

    // 화면 ready 된 후 설정
    angular.element(document).ready(function () {
        // 신규선택메뉴생성 팝업 핸들러 추가
        $scope.wjNewSdselLayer.shown.addHandler(function (s) {
            $("#sdselGrpTitle").html("");
            var classScope = agrid.getScope('newSdselClassCtrl');
            classScope._gridDataInit();   // 그리드 초기화

            $("#sdselClassTitle").html("");
            var prodScope = agrid.getScope('newSdselProdCtrl');
            prodScope._gridDataInit();   // 그리드 초기화
            var prodScope2 = agrid.getScope('prodSelectCtrl');
            prodScope2._gridDataInit();   // 그리드 초기화
        });
        // 신규선택메뉴생성 팝업 핸들러 추가
        $scope.wjNewSdselLayer.hidden.addHandler(function (s) {
            setTimeout(function() {
                $scope.searchSdselGrp();
                var scope = agrid.getScope('prodModifyCtrl');
                $("#searchSdselGrpCd").val(scope.getNewSdselGrpCd());
            }, 50);
        });
    });
}]);