/****************************************************************
 *
 * 파일명 : prodExcelUploadAdd.js
 * 설  명 : 상품엑셀업로드 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.10.14     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  상품엑셀업로드 팝업 조회 그리드 생성
 */
app.controller('prodExcelUploadAddCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('prodExcelUploadAddCtrl', $scope, $http, false));

    // 상품코드 채번방식
    $scope.prodNoEnvFg = prodNoEnvFg;

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 컬럼헤더:바인딩명 형태의 JSON 데이터 생성.
        $scope.colHeaderBind = {};
        for (var i = 0; i < $scope.flex.columns.length; i++) {
            var col = $scope.flex.columns[i];
            $scope.colHeaderBind[col.header] = col.binding;
        }
    };

    // <-- 검색 호출 -->
    $scope.$on("prodExcelUploadAddCtrl", function(event, data) {
        event.preventDefault();
    });
    // <-- //검색 호출 -->

    // 엑셀파일이 변경된 경우
    $scope.excelFileChanged = function () {
        // 엑셀업로드 전 현재 세션ID 와 동일한 자료를 삭제한다.
        $scope.deleteExl();
    };

    // 현재 세션ID 와 동일한 데이터 삭제
    $scope.deleteExl = function () {
        var params = {};

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._postJSONSave.withOutPopUp("/base/prod/prodExcelUpload/prodExcelUpload/getProdExcelUploadCheckDeleteAll.sb", params, function(){
            // 엑셀 업로드
            $scope.excelUpload();
        });
    };

    // 엑셀 업로드
    $scope.excelUpload = function () {
        // 선택한 파일이 있으면
        if ($('#prodExcelUpFile')[0].files[0]) {
            var file          = $('#prodExcelUpFile')[0].files[0];
            var fileName      = file.name;
            var fileExtension = fileName.substring(fileName.lastIndexOf('.'));

            // 확장자가 xlsx, xlsm 인 경우에만 업로드 실행
            if (fileExtension.toLowerCase() === '.xlsx' || fileExtension.toLowerCase() === '.xlsm') {
                $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈

                $timeout(function () {
                    var flex = $scope.flex;
                    wijmo.grid.xlsx.FlexGridXlsxConverter.loadAsync(flex, $('#prodExcelUpFile')[0].files[0], {includeColumnHeaders: true}
                        , function () {
                            $timeout(function () {
                                // 엑셀업로드 한 데이터를 JSON 형태로 변경한다.
                                $scope.excelUploadToJsonConvert();
                            }, 10);
                        }
                    );
                }, 10);
            } else {
                $("#prodExcelUpFile").val('');
                $scope._popMsg(messages['prodExcelUpload.not.excelFile']); // 엑셀 파일만 업로드 됩니다.(*.xlsx, *.xlsm)
                return false;
            }
        }
    };

    // 엑셀업로드 한 데이터를 JSON 형태로 변경한다.
    $scope.excelUploadToJsonConvert = function () {
        var jsonData  = [];
        var item      = {};
        var rowLength = $scope.flex.rows.length;

        if (rowLength === 0) {
            $scope._popMsg(messages['prodExcelUpload.not.excelUploadData']); // 엑셀업로드 된 데이터가 없습니다.
            return false;
        }

        // 업로드 된 데이터 JSON 형태로 생성
        for (var r = 0; r < rowLength; r++) {
            item = {};

            //  브랜드 '미사용'인 경우에도 브랜드코드는 들어감
            if(brandUseFg === "0") { item["hqBrandCd"] = brandList[0].name; }

            for (var c = 0; c < $scope.flex.columns.length; c++) {
                // alert($scope.flex.columns[c].header); // 상품코드
                // alert($scope.flex.getCellData(r, c, false)); // 00001
                // alert($scope.colHeaderBind[$scope.flex.columns[c].header]); // prodCd
                if ($scope.flex.columns[c].header !== null && $scope.flex.getCellData(r, c, false) !== null) {
                    var colBinding = $scope.colHeaderBind[$scope.flex.columns[c].header];
                    var cellValue  = $scope.flex.getCellData(r, c, false) + '';
                    item[colBinding] = cellValue;
                }
            }

            jsonData.push(item);
        }

        // $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
        $timeout(function () {
            $scope.save(jsonData);
        }, 10);
    };

    // DB에 저장
    $scope.save = function (jsonData) {
        for (var i = 0; i < jsonData.length; i++) {
            // 숫자만 입력
            var numChkexp = /[^0-9]/g;
            // 공급단가
            if (numChkexp.test(jsonData[i].splyUprc)) {
                jsonData[i].splyUprc = "";
            }
            // 내점가
            if (numChkexp.test(jsonData[i].stinSaleUprc)) {
                jsonData[i].stinSaleUprc = "";
            }
            // 배달가
            if (numChkexp.test(jsonData[i].dlvrSaleUprc)) {
                jsonData[i].dlvrSaleUprc = "";
            }
            // 포장가
            if (numChkexp.test(jsonData[i].packSaleUprc)) {
                jsonData[i].packSaleUprc = "";
            }
            // 원가단가
            if (numChkexp.test(jsonData[i].costUprc)) {
                jsonData[i].costUprc = "";
            }
            // 최종원가단가
            if (numChkexp.test(jsonData[i].lastCostUprc)) {
                jsonData[i].lastCostUprc = "";
            }
            // 발주단위수량
            if (numChkexp.test(jsonData[i].poUnitQty)) {
                jsonData[i].poUnitQty = "";
            }
            // 발주최소수량
            if (numChkexp.test(jsonData[i].poMinQty)) {
                jsonData[i].poMinQty = "";
            }
            // 안전재고수량
            if (numChkexp.test(jsonData[i].safeStockQty)) {
                jsonData[i].safeStockQty = "";
            }
            // 초기재고
            if (numChkexp.test(jsonData[i].startStockQty)) {
                jsonData[i].startStockQty = "";
            }
        }

        // 업로드시 임시테이블 저장
        $scope._postJSONSave.withOutPopUp("/base/prod/prodExcelUpload/prodExcelUpload/getProdExcelUploadCheckSave.sb", jsonData, function () {
            $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
            // 저장기능 수행후 재조회
            $scope._broadcast('prodExcelUploadProdCtrl');
        });
    };

}]);