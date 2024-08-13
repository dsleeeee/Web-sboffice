/****************************************************************
 *
 * 파일명 : basicSideMenuProdView.js
 * 설  명 : 선택상품 추가팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2024.08.07    이다솜      1.0
 *
 * **************************************************************/

/**
 *  사이드메뉴 상품선택 그리드 생성
 */
app.controller('basicSideMenuProdCtrl', ['$scope', '$http', 'sdselClassCd', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('basicSideMenuProdCtrl', $scope, $http, false))
    // 콤보박스 데이터 Set
    $scope._setComboData("listScaleBox", gvListScaleBoxData);
    $scope._setComboData("srchProdHqBrandCd", userHqBrandCdComboList); // 상품브랜드
    // 상품 선택여부
    $scope.itemChecked = false;

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        $scope.brandDataMap = new wijmo.grid.DataMap(brandList, 'value', 'name'); // 브랜드

        // 그리드 선택 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function (e) {
            var ht = s.hitTest(e);
            if (ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];
                // 체크박스 클릭시
                if (col.binding === 'gChk' && s.rows[ht.row].dataItem.gChk) {
                    $scope.itemChecked = true;
                } else {
                    $scope.itemChecked = false;
                }
            }
        });
    };

    var selSdselClassCd = "";
    var selSdselGrpBrandCd = "";

    // 선택상품 그리드 조회
    $scope.$on("basicSideMenuProdCtrl", function (event, data) {

        if (data !== undefined && !isEmptyObject(data)) {

            // 상품브랜드 콤보박스 disabled 초기화
            $scope.srchProdHqBrandCdCombo.isDisabled = false;

            // 선택그룹 브랜드 코드, 선택분류 코드 셋팅(hidden)
            selSdselClassCd = data.sdselClassCd;
            selSdselGrpBrandCd = data.sdselGrpBrandCd;

            // 선택그룹의 브랜드 유무에 따른 상품브랜드 콤보박스 셋팅
            if (selSdselGrpBrandCd !== "" && selSdselGrpBrandCd !== null && selSdselGrpBrandCd !== undefined) {
                // 선택그룹의 브랜드가 있는 경우, 해당 브랜드로 기본 셋팅
                $scope.srchProdHqBrandCdCombo.selectedValue = selSdselGrpBrandCd;

                // 맘스터치는 선택그룹의 브랜드에 속한 상품만 조회 가능(상품브랜드 변경 불가)
                if (momsEnvstVal === "1") {
                    $scope.srchProdHqBrandCdCombo.isDisabled = true;
                }

            } else {
                // 선택그룹의 브랜드가 없는 경우, '전체'(null)로 기본셋팅
                $scope.srchProdHqBrandCdCombo.selectedValue = null;
            }

            // 상품브랜드 검색조건 show/hidden
            if (brandUseFg === "1" && orgnFg === "HQ") {
                $("#trProdHqBrand").css("display", "");
            } else {
                $("#trProdHqBrand").css("display", "none");
            }

            $scope._setPagingInfo('curr', 1); // 페이지번호 1로 세팅
        }

        // 상품목록 조회
        $scope.srchProdList();
    });

    // 상품목록 조회
    $scope.srchProdList = function () {
        var params = {};
        params.sdselClassCd = selSdselClassCd;
        params.prodClassCd = $scope.prodClassCd;
        if (typeof gubun !== "undefined") {
            params.sideEnvstVal = gubun;
        }
        if (brandUseFg === "1" && orgnFg === "HQ") {
            // 선택한 상품브랜드가 있을 때
            params.prodHqBrandCd = $scope.srchProdHqBrandCdCombo.selectedValue;

            // 선택한 상품브랜드가 없을 때('전체' 일때)
            if (params.prodHqBrandCd === "" || params.prodHqBrandCd === null) {
                var userHqBrandCd = "";
                for (var i = 0; i < userHqBrandCdComboList.length; i++) {
                    if (userHqBrandCdComboList[i].value !== null) {
                        userHqBrandCd += userHqBrandCdComboList[i].value + ","
                    }
                }
                params.userProdBrands = userHqBrandCd; // 사용자별 관리브랜드만 조회(관리브랜드가 따로 없으면, 모든 브랜드 조회)
            }
        }
        // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
        $scope._inquiryMain('/base/prod/basicSideMenu/menuProd/getProdList.sb', params);
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    };

    // 상품선택버튼 클릭
    $scope.selProdConfirm = function () {
        var idx = 0;
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if ($scope.flex.collectionView.items[i].gChk) {
                idx++;
            }
        }
        if (idx < 1) {
            $scope.itemChecked = false;
            $scope._popMsg('상품을 선택해주세요.');
            return false;
        } else {
            $scope.itemChecked = true;
        }
    };

    // 상품분류정보 팝업
    $scope.popUpProdClass = function () {
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
                    function (response) {
                        $scope.prodClassCd = prodClassCd;
                        $scope.prodClassCdNm = response.data.data;
                    }
                );
            }
        });
    };

    // 상품분류정보 선택취소
    $scope.delProdClass = function () {
        $scope.prodClassCd = "";
        $scope.prodClassCdNm = "";
    };
}]);