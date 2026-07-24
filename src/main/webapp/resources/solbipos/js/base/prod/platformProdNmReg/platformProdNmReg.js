/****************************************************************
 *
 * 파일명 : platformProdNmReg.js
 * 설  명 : 기초관리 > 상품관리2 > 플랫폼 상품명 등록
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2026.07.21     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 플랫폼 구분
var platformFgData=[
    {"name":"전체","value":""},
    {"name":"POS","value":"1"},
    {"name":"키오스크","value":"2"}
];

app.controller('platformProdNmRegCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('platformProdNmRegCtrl', $scope, $http, true));

    // 콤보박스 셋팅
    $scope._setComboData("useYn", useYn);                               // 사용여부
    $scope._setComboData("platformFg", platformFgData);               // 플랫폼
    $scope._setComboData("prodHqBrandCdCombo", momsHqBrandCdComboList); // 상품브랜드


    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.useYnDataMap = new wijmo.grid.DataMap(useYn, 'value', 'name'); // 사용여부
    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("platformProdNmRegCtrl", function (event, data) {

        // 플랫폼 상품명 리스트 조회
        $scope.getPlatformProdNmList();
        event.preventDefault();
    });

    // 플랫폼 상품명 리스트 조회
    $scope.getPlatformProdNmList = function () {

        // 파라미터
        var params = {};
        params.prodCd = $scope.prodCd;
        params.prodNm = $scope.prodNm;
        params.prodClassCd = $scope.prodClassCd;
        params.useYn = $scope.useYn;
        params.platformFg = $scope.platformFg;
        params.prodHqBrandCd = $scope.prodHqBrandCd;
        // '전체' 일때
        if(params.prodHqBrandCd === "" || params.prodHqBrandCd === null) {
            var momsHqBrandCd = "";
            for(var i=0; i < momsHqBrandCdComboList.length; i++){
                if(momsHqBrandCdComboList[i].value !== null) {
                    momsHqBrandCd += momsHqBrandCdComboList[i].value + ","
                }
            }
            params.userBrands = momsHqBrandCd;
        }

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/base/prod/platformProdNmReg/getPlatformProdNmRegList.sb", params, function () {
            // 플랫폼구분에 따라 POS/KIOSK 상품명 컬럼 노출/숨김
            var showPos = (params.platformFg === "" || params.platformFg === null || params.platformFg === "1");
            var showKiosk = (params.platformFg === "" || params.platformFg === null || params.platformFg === "2");

            $scope.flex.columns.forEach(function (col) {
                if (col.binding === "posProdNm1" || col.binding === "posProdNm2") {
                    col.visible = showPos;
                } else if (col.binding === "kioskProdNm1" || col.binding === "kioskProdNm2") {
                    col.visible = showKiosk;
                }
            });
        });
    };

    // 초기화
    $scope.resetRow = function () {
        $scope._popConfirm(messages["platformProdNmReg.reset.msg"], function() {
            $scope.flex.collectionView.items.forEach(function (item) {
                // 원래 값이 있던 행만 초기화 대상으로 처리
                if (nvl(item.posProdNm1, '') !== '' || nvl(item.posProdNm2, '') !== '' ||
                    nvl(item.kioskProdNm1, '') !== '' || nvl(item.kioskProdNm2, '') !== '') {
                    $scope.flex.collectionView.editItem(item);
                    item.posProdNm1 = "";
                    item.posProdNm2 = "";
                    item.kioskProdNm1 = "";
                    item.kioskProdNm2 = "";
                    $scope.flex.collectionView.commitEdit();
                }
            });
            $scope.flex.collectionView.refresh();
        });
    };

    // 저장
    $scope.saveRow = function () {
        $scope._popConfirm(messages["cmm.choo.save"], function() {
            $scope.flex.collectionView.commitEdit();

            // 파라미터 설정
            var params = [];

            for (var u = 0; u < $scope.flex.collectionView.itemsEdited.length; u++) {
                $scope.flex.collectionView.itemsEdited[u].status = 'U';
                params.push($scope.flex.collectionView.itemsEdited[u]);
            }

            for (var i = 0; i < params.length; i++) {
                var item = params[i];

                // POS/키오스크 상품명 앞뒤 공백 및 엔터값 제거
                item.posProdNm1 = nvl(item.posProdNm1, '').trim().removeEnter();
                item.posProdNm2 = nvl(item.posProdNm2, '').trim().removeEnter();
                item.kioskProdNm1 = nvl(item.kioskProdNm1, '').trim().removeEnter();
                item.kioskProdNm2 = nvl(item.kioskProdNm2, '').trim().removeEnter();

                // POS/키오스크 상품명 큰따옴표(") 입력 불가
                if (nvl(item.posProdNm1, '').indexOf("\"") >= 0 || nvl(item.posProdNm2, '').indexOf("\"") >= 0) {
                    $scope._popMsg(messages["platformProdNmReg.pos"] + " " + messages["platformProdNmReg.prodNmTextChk.msg"]); // POS 상품명에 큰따옴표(")를 입력할 수 없습니다.
                    return false;
                }

                if (nvl(item.kioskProdNm1, '').indexOf("\"") >= 0 || nvl(item.kioskProdNm2, '').indexOf("\"") >= 0) {
                    $scope._popMsg(messages["platformProdNmReg.kiosk"] + " " + messages["platformProdNmReg.prodNmTextChk.msg"]); // 키오스크 상품명에 큰따옴표(")를 입력할 수 없습니다.
                    return false;
                }

                // POS/키오스크 상품명 컬럼(VARCHAR2(100))에 [명1 + CHR(10) + 명2] 형태로 합쳐서 저장하므로 합친 길이 기준으로 체크
                var posCombined = nvl(item.posProdNm2, '') !== '' ? item.posProdNm1 + '\n' + item.posProdNm2 : item.posProdNm1;
                if (nvl(posCombined, '').getByteLengthForOracle() > 100) {
                    // POS 상품명의 데이터 중 문자열의 길이가 너무 긴 데이터가 있습니다. 최대 : 100, 현재 : 120(영문:1byte, 한글:3byte)
                    $scope._popMsg(messages["platformProdNmReg.pos"] + messages["cmm.prodNm"] + messages["cmm.overLength"] + "100" +
                        ", 현재 : " + posCombined.getByteLengthForOracle() + messages["cmm.bateLengthInfo"]);
                    return false;
                }

                var kioskCombined = nvl(item.kioskProdNm2, '') !== '' ? item.kioskProdNm1 + '\n' + item.kioskProdNm2 : item.kioskProdNm1;
                if (nvl(kioskCombined, '').getByteLengthForOracle() > 100) {
                    // 키오스크 상품명의 데이터 중 문자열의 길이가 너무 긴 데이터가 있습니다. 최대 : 100, 현재 : 120(영문:1byte, 한글:3byte)
                    $scope._popMsg(messages["platformProdNmReg.kiosk"] + messages["cmm.prodNm"] + messages["cmm.overLength"] + "100" +
                        ", 현재 : " + kioskCombined.getByteLengthForOracle() + messages["cmm.bateLengthInfo"]);
                    return false;
                }
            }

            $scope._save("/base/prod/platformProdNmReg/savePlatformProdNm.sb", params, function(result) {
                // 재조회
                $scope.getPlatformProdNmList();
            });

        });
    };

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
    };

}]);