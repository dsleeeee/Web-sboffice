/****************************************************************
 *
 * 파일명 : menuRankDisplay.js
 * 설  명 : 메뉴 순위 표시 관리 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.08.06     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 사용여부 콤보는 별도 조회하지 않고 고정적으로 사용
var useYnAllComboData = [
    {"name": "전체", "value": ""},
    {"name": "사용", "value": "Y"},
    {"name": "미사용", "value": "N"}
];
var useYnComboData = [
    {"name": "사용", "value": "Y"},
    {"name": "미사용", "value": "N"}
];

app.controller('prodImgCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('prodImgCtrl', $scope, $http, true));

    // 등록일자 셋팅
    $scope.srchStartDate = wcombo.genDateVal("#srchTimeStartDate", gvStartDate);
    $scope.srchEndDate   = wcombo.genDateVal("#srchTimeEndDate", gvEndDate);

    // 전체기간 체크박스
    $scope.isChecked = true;

    // 전체기간 체크박스 선택에 따른 날짜선택 초기화
    $scope.srchStartDate.isReadOnly = $scope.isChecked;
    $scope.srchEndDate.isReadOnly = $scope.isChecked;

    // 콤보박스 데이터 Set
    $scope._setComboData('listScaleBox', gvListScaleBoxData);

    // 사용여부를 쓰는 콤보박스의 데이터 (조회용)
    $scope._setComboData('useYnAllComboData', useYnAllComboData);

    $scope.initGrid = function (s, e) {

        // 그리드에서 사용하는 dataMap 초기화
        $scope.useYnComboDataMap = new wijmo.grid.DataMap(useYnComboData, 'value', 'name');

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel == s.cells) {
                var col = s.columns[e.col];
                if (col.binding === "prodCd") {
                    var item = s.rows[e.row].dataItem;
                    wijmo.addClass(e.cell, 'wijLink');
                    wijmo.addClass(e.cell, 'wj-custom-readonly');
                }
            }
        });

        // 그리드 클릭 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function (e) {
            var ht = s.hitTest(e);
            if (ht.cellType === wijmo.grid.CellType.Cell) {
                var col         = ht.panel.columns[ht.col];
                var selectedRow = s.rows[ht.row].dataItem;
                if (col.binding === "prodCd") {
                    $scope.getProdImg(selectedRow.prodCd, selectedRow.prodNm);
                }
            }
        });


    };

    $scope.$on("prodImgCtrl", function(event, data) {
        $scope.searchProdList();
        event.preventDefault();
    });

    // 상품 목록 조회
    $scope.searchProdList = function(){
        // 파라미터
        var params = {};
        params.listScale = 15;

        // 등록일자 '전체기간' 선택에 따른 params
        if(!$scope.isChecked){
            params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
            params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
        }

        // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
        $scope._inquiryMain("/base/prod/prodImg/prodImg/list.sb", params, function(){
        });

    };

    // 전체기간 체크박스 클릭이벤트
    $scope.isChkDt = function() {
        $scope.srchStartDate.isReadOnly = $scope.isChecked;
        $scope.srchEndDate.isReadOnly = $scope.isChecked;
    };

    // 상품 이미지 조회
    $scope.getProdImg = function(prodCd, prodNm){

        // 상품을 클릭하면 상품이미지등록이 보이도록
        $("#imgTbl").css("display", "");

        //상품코드, 이름 셋팅
        $("#prodInfo").text("[" + prodCd + "] " + prodNm);
        $("#hdProdCd").val(prodCd);

        // 이미지 초기화
        $("#imgProd").html("No Image");
        $("#imgKiosk").html("No Image");
        $("#imgDid").html("No Image");
        
        var params = {};
        params.prodCd = prodCd;

        $scope._postJSONQuery.withOutPopUp("/base/prod/prodImg/prodImg/getProdImg.sb", params, function (response) {

            if (response.data.data.list.length > 0) {
                var list = response.data.data.list;

                for (var i = 0; i < list.length; i++) {

                    if(list[i].imgFg === "001"){
                        $("#imgProd").html("<img src='" + list[i].imgUrl + "/" + list[i].imgFileNm + "' class='imgPic'>");
                    }

                    if(list[i].imgFg === "002"){
                        $("#imgKiosk").html("<img src='" + list[i].imgUrl + "/" + list[i].imgFileNm + "' class='imgPic'>");
                    }

                    if(list[i].imgFg === "003"){
                        $("#imgDid").html("<img src='" + list[i].imgUrl + "/" + list[i].imgFileNm + "' class='imgPic'>");
                    }
                }
            }
        });
    }

    // 이미지 등록
    $scope.regImg = function(imgFg){
        alert(imgFg);
    }

    // 이미지 삭제
    $scope.delImg = function(imgFg){
        alert(imgFg);
    }

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

}]);