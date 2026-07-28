/****************************************************************
 *
 * 파일명 : prodPlatform.js
 * 설  명 : 다국어관리(상품) - 플랫폼 상품명 탭 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2026.07.24     이다솜      1.0
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

app.controller('prodPlatformCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('prodPlatformCtrl', $scope, $http, true));

    // 콤보박스 셋팅
    $scope._setComboData("useYn3", useYn);                               // 사용여부
    $scope._setComboData("platformFg3", platformFgData);                 // 플랫폼

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        s.cellEditEnded.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
                // 값 변경시 체크박스 체크
                if (col.binding === "prodEnNmDisp1" || col.binding === "prodEnNmDisp2" || col.binding === "prodCnNmDisp1" || col.binding === "prodCnNmDisp2"
                    || col.binding === "prodJpNmDisp1" || col.binding === "prodJpNmDisp2"
                    || col.binding === "prodEnNmDispKiosk1" || col.binding === "prodEnNmDispKiosk2" || col.binding === "prodCnNmDispKiosk1" || col.binding === "prodCnNmDispKiosk2"
                    || col.binding === "prodJpNmDispKiosk1" || col.binding === "prodJpNmDispKiosk2") {
                    $scope.checked(item);
                }
            }
            s.collectionView.commitEdit();
        });
    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("prodPlatformCtrl", function (event, data) {
        // 조회
        $scope.getProdPlatformList();
        event.preventDefault();
    });

    // 조회
    $scope.getProdPlatformList = function () {

        // 파라미터
        var params = {};
        params.prodCd = $("#srchProdCd3").val();
        params.prodNm = $("#srchProdNm3").val();
        params.prodClassCd = $scope.prodClassCd;
        params.useYn = $scope.srchUseYnCombo3.selectedValue;
        params.platformFg = $scope.srchPlatformFgCombo3.selectedValue;

        if(brandUseFg === "1" && orgnFg === "HQ"){
            var userHqBrandCd = "";
            for(var i=0; i < userHqBrandCdComboList.length; i++){
                if(userHqBrandCdComboList[i].value !== null) {
                    userHqBrandCd += userHqBrandCdComboList[i].value + ","
                }
            }
            params.userProdBrands = userHqBrandCd; // 사용자별 관리브랜드만 조회(관리브랜드가 따로 없으면, 모든 브랜드 조회)
        }

        // 플랫폼 상품명 탭 리스트 조회
        $scope._inquiryMain("/base/multilingual/prodLang/getProdPlatformList.sb", params, function () {
            // 플랫폼구분에 따라 POS/KIOSK 상품명 컬럼 노출/숨김
            var showPos = (params.platformFg === "" || params.platformFg === null || params.platformFg === "1");
            var showKiosk = (params.platformFg === "" || params.platformFg === null || params.platformFg === "2");

            var posBindings = ["prodKoNmDisp1", "prodKoNmDisp2", "prodEnNmDisp1", "prodEnNmDisp2",
                "prodCnNmDisp1", "prodCnNmDisp2", "prodJpNmDisp1", "prodJpNmDisp2"];
            var kioskBindings = ["prodKoNmDispKiosk1", "prodKoNmDispKiosk2", "prodEnNmDispKiosk1", "prodEnNmDispKiosk2",
                "prodCnNmDispKiosk1", "prodCnNmDispKiosk2", "prodJpNmDispKiosk1", "prodJpNmDispKiosk2"];

            $scope.flex.columns.forEach(function (col) {
                if (posBindings.indexOf(col.binding) >= 0) {
                    col.visible = showPos;
                } else if (kioskBindings.indexOf(col.binding) >= 0) {
                    col.visible = showKiosk;
                }
            });
        });
    };

    // 저장
    $scope.saveRow = function(){
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
                item.prodEnNmDisp1 = nvl(item.prodEnNmDisp1, '').trim().removeEnter();
                item.prodEnNmDisp2 = nvl(item.prodEnNmDisp2, '').trim().removeEnter();
                item.prodCnNmDisp1 = nvl(item.prodCnNmDisp1, '').trim().removeEnter();
                item.prodCnNmDisp2 = nvl(item.prodCnNmDisp2, '').trim().removeEnter();
                item.prodJpNmDisp1 = nvl(item.prodJpNmDisp1, '').trim().removeEnter();
                item.prodJpNmDisp2 = nvl(item.prodJpNmDisp2, '').trim().removeEnter();
                item.prodEnNmDispKiosk1 = nvl(item.prodEnNmDispKiosk1, '').trim().removeEnter();
                item.prodEnNmDispKiosk2 = nvl(item.prodEnNmDispKiosk2, '').trim().removeEnter();
                item.prodCnNmDispKiosk1 = nvl(item.prodCnNmDispKiosk1, '').trim().removeEnter();
                item.prodCnNmDispKiosk2 = nvl(item.prodCnNmDispKiosk2, '').trim().removeEnter();
                item.prodJpNmDispKiosk1 = nvl(item.prodJpNmDispKiosk1, '').trim().removeEnter();
                item.prodJpNmDispKiosk2 = nvl(item.prodJpNmDispKiosk2, '').trim().removeEnter();

                // POS/키오스크 상품명 큰따옴표(") 입력 불가
                if (nvl(item.prodEnNmDisp1, '').indexOf("\"") >= 0 || nvl(item.prodEnNmDisp2, '').indexOf("\"") >= 0) {
                    // POS상품명(영문)에 큰따옴표(")를 입력할 수 없습니다.
                    $scope._popMsg(messages["prodLang.pos"] + messages["prodLang.prodNm"] + "(" + messages["prodLang.en"] + ") " + messages["prodLang.prodNmTextChk.msg"]);
                    return false;
                }

                if (nvl(item.prodCnNmDisp1, '').indexOf("\"") >= 0 || nvl(item.prodCnNmDisp2, '').indexOf("\"") >= 0) {
                    // POS상품명(중문)에 큰따옴표(")를 입력할 수 없습니다.
                    $scope._popMsg(messages["prodLang.pos"] + messages["prodLang.prodNm"] + "(" + messages["prodLang.cn"] + ") " + messages["prodLang.prodNmTextChk.msg"]);
                    return false;
                }

                if (nvl(item.prodJpNmDisp1, '').indexOf("\"") >= 0 || nvl(item.prodJpNmDisp2, '').indexOf("\"") >= 0) {
                    // POS상품명(일문)에 큰따옴표(")를 입력할 수 없습니다.
                    $scope._popMsg(messages["prodLang.pos"] + messages["prodLang.prodNm"] + "(" + messages["prodLang.jp"] + ") " + messages["prodLang.prodNmTextChk.msg"]);
                    return false;
                }

                if (nvl(item.prodEnNmDispKiosk1, '').indexOf("\"") >= 0 || nvl(item.prodEnNmDispKiosk2, '').indexOf("\"") >= 0) {
                    // 키오스크상품명(영문)에 큰따옴표(")를 입력할 수 없습니다.
                    $scope._popMsg(messages["prodLang.kiosk"] + messages["prodLang.prodNm"] + "(" + messages["prodLang.en"] + ") " + messages["prodLang.prodNmTextChk.msg"]);
                    return false;
                }

                if (nvl(item.prodCnNmDispKiosk1, '').indexOf("\"") >= 0 || nvl(item.prodCnNmDispKiosk2, '').indexOf("\"") >= 0) {
                    // 키오스크상품명(중문)에 큰따옴표(")를 입력할 수 없습니다.
                    $scope._popMsg(messages["prodLang.kiosk"] + messages["prodLang.prodNm"] + "(" + messages["prodLang.cn"] + ") " + messages["prodLang.prodNmTextChk.msg"]);
                    return false;
                }

                if (nvl(item.prodJpNmDispKiosk1, '').indexOf("\"") >= 0 || nvl(item.prodJpNmDispKiosk2, '').indexOf("\"") >= 0) {
                    // 키오스크상품명(일문)에 큰따옴표(")를 입력할 수 없습니다.
                    $scope._popMsg(messages["prodLang.kiosk"] + messages["prodLang.prodNm"] + "(" + messages["prodLang.jp"] + ") " + messages["prodLang.prodNmTextChk.msg"]);
                    return false;
                }

                // POS/키오스크 상품명 컬럼(VARCHAR2(100))에 [명1 + CHR(10) + 명2] 형태로 합쳐서 저장하므로 합친 길이 기준으로 체크
                var posEnCombined = nvl(item.prodEnNmDisp2, '') !== '' ? item.prodEnNmDisp1 + '\n' + item.prodEnNmDisp2 : item.prodEnNmDisp1;
                if (nvl(posEnCombined, '').getByteLengthForOracle() > 100) {
                    // POS상품명(영문)의 데이터 중 문자열의 길이가 너무 긴 데이터가 있습니다. 최대 : 100, 현재 : 120(영문:1byte, 한글:3byte)
                    $scope._popMsg(messages["prodLang.pos"] + messages["prodLang.prodNm"] + "(" + messages["prodLang.en"] + ")" + messages["cmm.overLength"] + "100" +
                        ", 현재 : " + posEnCombined.getByteLengthForOracle() + messages["cmm.bateLengthInfo"]);
                    return false;
                }

                var posCnCombined = nvl(item.prodCnNmDisp2, '') !== '' ? item.prodCnNmDisp1 + '\n' + item.prodCnNmDisp2 : item.prodCnNmDisp1;
                if (nvl(posCnCombined, '').getByteLengthForOracle() > 100) {
                    // POS상품명(중문)의 데이터 중 문자열의 길이가 너무 긴 데이터가 있습니다. 최대 : 100, 현재 : 120(영문:1byte, 한글:3byte)
                    $scope._popMsg(messages["prodLang.pos"] + messages["prodLang.prodNm"] + "(" + messages["prodLang.cn"] + ")" + messages["cmm.overLength"] + "100" +
                        ", 현재 : " + posCnCombined.getByteLengthForOracle() + messages["cmm.bateLengthInfo"]);
                    return false;
                }

                var posJpCombined = nvl(item.prodJpNmDisp2, '') !== '' ? item.prodJpNmDisp1 + '\n' + item.prodJpNmDisp2 : item.prodJpNmDisp1;
                if (nvl(posJpCombined, '').getByteLengthForOracle() > 100) {
                    // POS상품명(일문)의 데이터 중 문자열의 길이가 너무 긴 데이터가 있습니다. 최대 : 100, 현재 : 120(영문:1byte, 한글:3byte)
                    $scope._popMsg(messages["prodLang.pos"] + messages["prodLang.prodNm"] + "(" + messages["prodLang.jp"] + ")" + messages["cmm.overLength"] + "100" +
                        ", 현재 : " + posJpCombined.getByteLengthForOracle() + messages["cmm.bateLengthInfo"]);
                    return false;
                }

                var kioskEnCombined = nvl(item.prodEnNmDispKiosk2, '') !== '' ? item.prodEnNmDispKiosk1 + '\n' + item.prodEnNmDispKiosk2 : item.prodEnNmDispKiosk1;
                if (nvl(kioskEnCombined, '').getByteLengthForOracle() > 100) {
                    // 키오스크상품명(영문)의 데이터 중 문자열의 길이가 너무 긴 데이터가 있습니다. 최대 : 100, 현재 : 120(영문:1byte, 한글:3byte)
                    $scope._popMsg(messages["prodLang.kiosk"] + messages["prodLang.prodNm"] + "(" + messages["prodLang.en"] + ")" + messages["cmm.overLength"] + "100" +
                        ", 현재 : " + kioskEnCombined.getByteLengthForOracle() + messages["cmm.bateLengthInfo"]);
                    return false;
                }

                var kioskCnCombined = nvl(item.prodCnNmDispKiosk2, '') !== '' ? item.prodCnNmDispKiosk1 + '\n' + item.prodCnNmDispKiosk2 : item.prodCnNmDispKiosk1;
                if (nvl(kioskCnCombined, '').getByteLengthForOracle() > 100) {
                    // 키오스크상품명(중문)의 데이터 중 문자열의 길이가 너무 긴 데이터가 있습니다. 최대 : 100, 현재 : 120(영문:1byte, 한글:3byte)
                    $scope._popMsg(messages["prodLang.kiosk"] + messages["prodLang.prodNm"] + "(" + messages["prodLang.cn"] + ")" + messages["cmm.overLength"] + "100" +
                        ", 현재 : " + kioskCnCombined.getByteLengthForOracle() + messages["cmm.bateLengthInfo"]);
                    return false;
                }

                var kioskJpCombined = nvl(item.prodJpNmDispKiosk2, '') !== '' ? item.prodJpNmDispKiosk1 + '\n' + item.prodJpNmDispKiosk2 : item.prodJpNmDispKiosk1;
                if (nvl(kioskJpCombined, '').getByteLengthForOracle() > 100) {
                    // 키오스크상품명(일문)의 데이터 중 문자열의 길이가 너무 긴 데이터가 있습니다. 최대 : 100, 현재 : 120(영문:1byte, 한글:3byte)
                    $scope._popMsg(messages["prodLang.kiosk"] + messages["prodLang.prodNm"] + "(" + messages["prodLang.jp"] + ")" + messages["cmm.overLength"] + "100" +
                        ", 현재 : " + kioskJpCombined.getByteLengthForOracle() + messages["cmm.bateLengthInfo"]);
                    return false;
                }
            }

            $scope._save("/base/multilingual/prodLang/saveProdPlatform.sb", params, function(result) {
                // 재조회
                $scope.getProdPlatformList();
            });
        });
    };

    // 그리드 입력값 변경시, 자동체크
    $scope.checked = function(item){
        item.gChk = true;
    };

    // 엑셀다운로드
    $scope.excelDownload = function(){

        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
            return false;
        }

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 열기
        $timeout(function () {
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
                includeColumnHeaders: true,
                includeCellStyles   : true,
                includeColumns      : function (column) {
                    //return column.visible;
                    return column.binding != 'gChk';
                }
            }, messages["prodLang.platform"]  + '_' +  getCurDateTime() + '.xlsx', function () {
                $timeout(function () {
                    $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                }, 10);
            });
        }, 10);
    };

    // 양식다운로드
    $scope.sampleDownload = function () {

        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
            return false;
        }

        var vScope = agrid.getScope('prodPlatformExcelCtrl');

        // 파라미터
        var params = {};
        params.prodCd = $("#srchProdCd3").val();
        params.prodNm = $("#srchProdNm3").val();
        params.prodClassCd = $scope.prodClassCd;
        params.useYn = $scope.srchUseYnCombo3.selectedValue;
        params.platformFg = $scope.srchPlatformFgCombo3.selectedValue;

        vScope.sampleDownload(params);
    };

    // 엑셀업로드
    $scope.excelUpload = function () {
        var msg = messages["prodLang.excelUpload.confmMsg"];  // 정상업로드 된 데이터는 자동저장됩니다. 업로드 하시겠습니까?

        $scope._popConfirm(msg, function() {

            $("#prodPlatformExcelUpFile").val('');
            $("#prodPlatformExcelUpFile").trigger('click');

        });
    };

    // 상품분류정보 팝업
    $scope.popUpProdClass = function () {
        var popUp = $scope.prodClassPopUpLayer;
        popUp.show(true, function (s) {
            // 선택 버튼 눌렀을때만
            if (s.dialogResult === "wj-hide-apply") {
                var scope          = agrid.getScope('prodClassPopUpCtrl');
                var prodClassCd    = scope.getSelectedClass();
                var params         = {};
                params.prodClassCd = prodClassCd;
                // 조회 수행 : 조회URL, 파라미터, 콜백함수
                $scope._postJSONQuery.withPopUp("/popup/getProdClassCdNm.sb", params,
                    function (response) {
                        $scope.prodClassCd   = prodClassCd;
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

/**
 * 플랫폼 상품명 양식다운로드 그리드 생성
 */
app.controller('prodPlatformExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('prodPlatformExcelCtrl', $scope, $http, false));

    //
    $scope.initGrid = function (s, e) {

    };

    // 양식 다운로드
    $scope.sampleDownload = function (data) {

        var params = {};
        params.prodCd = data.prodCd;
        params.prodNm = data.prodNm;
        params.prodClassCd = data.prodClassCd;
        params.useYn = data.useYn;
        params.platformFg = data.platformFg;

        $scope._inquiryMain("/base/multilingual/prodLang/getProdPlatformList.sb", params, function (){

            if ($scope.flex.rows.length <= 0) {
                $scope._popMsg(messages["excelUpload.not.downloadData"]);	//다운로드 할 데이터가 없습니다.
                return false;
            }

            $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 열기
            $timeout(function()	{
                wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync(	$scope.flex,
                    {
                        includeColumnHeaders: 	true,
                        includeCellStyles	: 	true,
                        includeColumns      :	function (column) {
                            return column.visible;
                        }
                    },
                    messages["prodLang.platform"] + '_엑셀업로드_양식_' + getCurDateTime() + '.xlsx',
                    function () {
                        $timeout(function () {
                            $scope.$broadcast('loadingPopupInactive'); //데이터 처리중 메시지 팝업 닫기
                        }, 10);
                    }
                );
            }, 10);
        });
    };

    // 엑셀파일이 변경된 경우
    $scope.excelFileChanged = function () {
        if ($('#prodPlatformExcelUpFile')[0].files[0]) {
            // 엑셀업로드 호출
            $scope.excelUpload();
        }
    };

    // 엑셀 업로드
    $scope.excelUpload = function () {

        $scope.stepCnt = 100; // 한번에 DB에 저장할 숫자 세팅
        $scope.progressCnt = 0; // 처리된 숫자

        // 선택한 파일이 있으면
        if ($('#prodPlatformExcelUpFile')[0].files[0]) {
            var file = $('#prodPlatformExcelUpFile')[0].files[0];
            var fileName = file.name;
            var fileExtension = fileName.substring(fileName.lastIndexOf('.'));

            // 확장자가 xlsx, xlsm 인 경우에만 업로드 실행
            if (fileExtension.toLowerCase() === '.xlsx' || fileExtension.toLowerCase() === '.xlsm') {
                $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
                /*$timeout(function () {
                    var flex = $scope.flex;
                    wijmo.grid.xlsx.FlexGridXlsxConverter.loadAsync(flex, $('#prodNmExcelUpFile')[0].files[0], {includeColumnHeaders: true}
                        , function () {
                            $timeout(function () {
                                $scope.excelUploadToJsonConvert();
                            }, 10);
                        }
                    );
                }, 10);*/

                // excel file read
                var reader = new FileReader();
                var arr = [];
                reader.onload = function(){
                    var fileData = reader.result;
                    var wb = XLSX.read(fileData, {type : 'binary'});
                    wb.SheetNames.forEach(function(sheetName) {
                        arr = XLSX.utils.sheet_to_json(wb.Sheets[sheetName]);

                        // key명 변경
                        arr.forEach(function(item){
                            // 엑셀 헤더 key 공백 제거
                            Object.keys(item).forEach(function(key){
                                var trimmedKey = key.trim();
                                if (trimmedKey !== key) {
                                    item[trimmedKey] = item[key];
                                    delete item[key];
                                }
                            });
                            
                            renameKey(item, '브랜드명', 'hqBrandNm');
                            renameKey(item, '상품분류코드', 'prodClassCd');
                            renameKey(item, '상품분류명', 'prodClassNm');
                            renameKey(item, '상품코드', 'prodCd');
                            renameKey(item, '상품명', 'prodNm');
                            renameKey(item, 'POS상품명1', 'prodKoNmDisp1');
                            renameKey(item, 'POS상품명2', 'prodKoNmDisp2');
                            renameKey(item, 'POS상품명1(영문)', 'prodEnNmDisp1');
                            renameKey(item, 'POS상품명2(영문)', 'prodEnNmDisp2');
                            renameKey(item, 'POS상품명1(중문)', 'prodCnNmDisp1');
                            renameKey(item, 'POS상품명2(중문)', 'prodCnNmDisp2');
                            renameKey(item, 'POS상품명1(일문)', 'prodJpNmDisp1');
                            renameKey(item, 'POS상품명2(일문)', 'prodJpNmDisp2');
                            renameKey(item, '키오스크상품명1', 'prodKoNmDispKiosk1');
                            renameKey(item, '키오스크상품명2', 'prodKoNmDispKiosk1');
                            renameKey(item, '키오스크상품명1(영문)', 'prodEnNmDispKiosk1');
                            renameKey(item, '키오스크상품명2(영문)', 'prodEnNmDispKiosk2');
                            renameKey(item, '키오스크상품명1(중문)', 'prodCnNmDispKiosk1');
                            renameKey(item, '키오스크상품명2(중문)', 'prodCnNmDispKiosk2');
                            renameKey(item, '키오스크상품명1(일문)', 'prodJpNmDispKiosk1');
                            renameKey(item, '키오스크상품명2(일문)', 'prodJpNmDispKiosk2');
                        });

                        // 엔터값 제거
                        arr.forEach(function(item){
                            if (item.prodEnNmDisp1 !== null && item.prodEnNmDisp1 !== undefined && item.prodEnNmDisp1 !== "") {
                                item.prodEnNmDisp1 = item.prodEnNmDisp1.toString().replace(/\r\n|\r|\n/g, ' ');
                            }

                            if (item.prodEnNmDisp2 !== null && item.prodEnNmDisp2 !== undefined && item.prodEnNmDisp2 !== "") {
                                item.prodEnNmDisp2 = item.prodEnNmDisp2.toString().replace(/\r\n|\r|\n/g, ' ');
                            }

                            if (item.prodCnNmDisp1 !== null && item.prodCnNmDisp1 !== undefined && item.prodCnNmDisp1 !== "") {
                                item.prodCnNmDisp1 = item.prodCnNmDisp1.toString().replace(/\r\n|\r|\n/g, ' ');
                            }

                            if (item.prodCnNmDisp2 !== null && item.prodCnNmDisp2 !== undefined && item.prodCnNmDisp2 !== "") {
                                item.prodCnNmDisp2 = item.prodCnNmDisp2.toString().replace(/\r\n|\r|\n/g, ' ');
                            }

                            if (item.prodJpNmDisp1 !== null && item.prodJpNmDisp1 !== undefined && item.prodJpNmDisp1 !== "") {
                                item.prodJpNmDisp1 = item.prodJpNmDisp1.toString().replace(/\r\n|\r|\n/g, ' ');
                            }

                            if (item.prodJpNmDisp2 !== null && item.prodJpNmDisp2 !== undefined && item.prodJpNmDisp2 !== "") {
                                item.prodJpNmDisp2 = item.prodJpNmDisp2.toString().replace(/\r\n|\r|\n/g, ' ');
                            }

                            if (item.prodEnNmDispKiosk1 !== null && item.prodEnNmDispKiosk1 !== undefined && item.prodEnNmDispKiosk1 !== "") {
                                item.prodEnNmDispKiosk1 = item.prodEnNmDispKiosk1.toString().replace(/\r\n|\r|\n/g, ' ');
                            }

                            if (item.prodEnNmDispKiosk2 !== null && item.prodEnNmDispKiosk2 !== undefined && item.prodEnNmDispKiosk2 !== "") {
                                item.prodEnNmDispKiosk2 = item.prodEnNmDispKiosk2.toString().replace(/\r\n|\r|\n/g, ' ');
                            }

                            if (item.prodCnNmDispKiosk1 !== null && item.prodCnNmDispKiosk1 !== undefined && item.prodCnNmDispKiosk1 !== "") {
                                item.prodCnNmDispKiosk1 = item.prodCnNmDispKiosk1.toString().replace(/\r\n|\r|\n/g, ' ');
                            }

                            if (item.prodCnNmDispKiosk2 !== null && item.prodCnNmDispKiosk2 !== undefined && item.prodCnNmDispKiosk2 !== "") {
                                item.prodCnNmDispKiosk2 = item.prodCnNmDispKiosk2.toString().replace(/\r\n|\r|\n/g, ' ');
                            }

                            if (item.prodJpNmDispKiosk1 !== null && item.prodJpNmDispKiosk1 !== undefined && item.prodJpNmDispKiosk1 !== "") {
                                item.prodJpNmDispKiosk1 = item.prodJpNmDispKiosk1.toString().replace(/\r\n|\r|\n/g, ' ');
                            }

                            if (item.prodJpNmDispKiosk2 !== null && item.prodJpNmDispKiosk2 !== undefined && item.prodJpNmDispKiosk2 !== "") {
                                item.prodJpNmDispKiosk2 = item.prodJpNmDispKiosk2.toString().replace(/\r\n|\r|\n/g, ' ');
                            }
                        });
                        console.log(arr);
                        //console.log(JSON.stringify(arr, null, 2));
                    })
                };
                reader.readAsBinaryString(file);

                $timeout(function () {
                    setTimeout(function() {
                        // 저장 전 입력값 체크
                        $scope.saveRow(arr);
                    }, 500);

                }, 10);

            } else {
                $("#prodPlatformExcelUpFile").val('');
                $scope._popMsg(messages['prodLang.not.excelFile']); // 엑셀 파일만 업로드 됩니다.(*.xlsx, *.xlsm)
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
            $scope._popMsg(messages['prodLang.not.excelUploadData']); // 엑셀업로드 된 데이터가 없습니다.
            return false;
        }

        // 업로드 된 데이터 JSON 형태로 생성
        for (var r = 0; r < rowLength; r++) {
            item = {};
            for (var c = 0; c < $scope.flex.columns.length; c++) {
                if ($scope.flex.columns[c].header !== null && $scope.flex.getCellData(r, c, false) !== null) {
                    var colBinding = $scope.colHeaderBind[$scope.flex.columns[c].header.replaceAll('\'', '')];
                    var cellValue  = $scope.flex.getCellData(r, c, false) + '';
                    item[colBinding] = cellValue;
                }
            }

            jsonData.push(item);
        }
        $timeout(function () {
            setTimeout(function() {
                // 저장 전 입력값 체크
                $scope.saveRow(jsonData);
            }, 500);

        }, 10);
    };

    // 저장 전 입력값 체크
    $scope.saveRow = function (jsonData) {
        $scope.totalRows = jsonData.length;

        for (var i = 0; i < $scope.totalRows; i++) {
            var item = jsonData[i];

            // POS/키오스크 상품명 앞뒤 공백 및 엔터값 제거
            item.prodEnNmDisp1 = nvl(item.prodEnNmDisp1, '').trim().removeEnter();
            item.prodEnNmDisp2 = nvl(item.prodEnNmDisp2, '').trim().removeEnter();
            item.prodCnNmDisp1 = nvl(item.prodCnNmDisp1, '').trim().removeEnter();
            item.prodCnNmDisp2 = nvl(item.prodCnNmDisp2, '').trim().removeEnter();
            item.prodJpNmDisp1 = nvl(item.prodJpNmDisp1, '').trim().removeEnter();
            item.prodJpNmDisp2 = nvl(item.prodJpNmDisp2, '').trim().removeEnter();
            item.prodEnNmDispKiosk1 = nvl(item.prodEnNmDispKiosk1, '').trim().removeEnter();
            item.prodEnNmDispKiosk2 = nvl(item.prodEnNmDispKiosk2, '').trim().removeEnter();
            item.prodCnNmDispKiosk1 = nvl(item.prodCnNmDispKiosk1, '').trim().removeEnter();
            item.prodCnNmDispKiosk2 = nvl(item.prodCnNmDispKiosk2, '').trim().removeEnter();
            item.prodJpNmDispKiosk1 = nvl(item.prodJpNmDispKiosk1, '').trim().removeEnter();
            item.prodJpNmDispKiosk2 = nvl(item.prodJpNmDispKiosk2, '').trim().removeEnter();

            // POS/키오스크 상품명 큰따옴표(") 입력 불가
            if (nvl(item.prodEnNmDisp1, '').indexOf("\"") >= 0 || nvl(item.prodEnNmDisp2, '').indexOf("\"") >= 0) {
                $scope.excelUploadingPopup(false); // 작업내역 로딩 팝업 닫기
                // POS상품명(영문)에 큰따옴표(")를 입력할 수 없습니다.
                $scope._popMsg(messages["prodLang.pos"] + messages["prodLang.prodNm"] + "(" + messages["prodLang.en"] + ") " + messages["prodLang.prodNmTextChk.msg"]);
                return false;
            }

            if (nvl(item.prodCnNmDisp1, '').indexOf("\"") >= 0 || nvl(item.prodCnNmDisp2, '').indexOf("\"") >= 0) {
                $scope.excelUploadingPopup(false); // 작업내역 로딩 팝업 닫기
                // POS상품명(중문)에 큰따옴표(")를 입력할 수 없습니다.
                $scope._popMsg(messages["prodLang.pos"] + messages["prodLang.prodNm"] + "(" + messages["prodLang.cn"] + ") " + messages["prodLang.prodNmTextChk.msg"]);
                return false;
            }

            if (nvl(item.prodJpNmDisp1, '').indexOf("\"") >= 0 || nvl(item.prodJpNmDisp2, '').indexOf("\"") >= 0) {
                $scope.excelUploadingPopup(false); // 작업내역 로딩 팝업 닫기
                // POS상품명(일문)에 큰따옴표(")를 입력할 수 없습니다.
                $scope._popMsg(messages["prodLang.pos"] + messages["prodLang.prodNm"] + "(" + messages["prodLang.jp"] + ") " + messages["prodLang.prodNmTextChk.msg"]);
                return false;
            }

            if (nvl(item.prodEnNmDispKiosk1, '').indexOf("\"") >= 0 || nvl(item.prodEnNmDispKiosk2, '').indexOf("\"") >= 0) {
                $scope.excelUploadingPopup(false); // 작업내역 로딩 팝업 닫기
                // 키오스크상품명(영문)에 큰따옴표(")를 입력할 수 없습니다.
                $scope._popMsg(messages["prodLang.kiosk"] + messages["prodLang.prodNm"] + "(" + messages["prodLang.en"] + ") " + messages["prodLang.prodNmTextChk.msg"]);
                return false;
            }

            if (nvl(item.prodCnNmDispKiosk1, '').indexOf("\"") >= 0 || nvl(item.prodCnNmDispKiosk2, '').indexOf("\"") >= 0) {
                $scope.excelUploadingPopup(false); // 작업내역 로딩 팝업 닫기
                // 키오스크상품명(중문)에 큰따옴표(")를 입력할 수 없습니다.
                $scope._popMsg(messages["prodLang.kiosk"] + messages["prodLang.prodNm"] + "(" + messages["prodLang.cn"] + ") " + messages["prodLang.prodNmTextChk.msg"]);
                return false;
            }

            if (nvl(item.prodJpNmDispKiosk1, '').indexOf("\"") >= 0 || nvl(item.prodJpNmDispKiosk2, '').indexOf("\"") >= 0) {
                $scope.excelUploadingPopup(false); // 작업내역 로딩 팝업 닫기
                // 키오스크상품명(일문)에 큰따옴표(")를 입력할 수 없습니다.
                $scope._popMsg(messages["prodLang.kiosk"] + messages["prodLang.prodNm"] + "(" + messages["prodLang.jp"] + ") " + messages["prodLang.prodNmTextChk.msg"]);
                return false;
            }

            // POS/키오스크 상품명 컬럼(VARCHAR2(100))에 [명1 + CHR(10) + 명2] 형태로 합쳐서 저장하므로 합친 길이 기준으로 체크
            var posEnCombined = nvl(item.prodEnNmDisp2, '') !== '' ? item.prodEnNmDisp1 + '\n' + item.prodEnNmDisp2 : item.prodEnNmDisp1;
            if (nvl(posEnCombined, '').getByteLengthForOracle() > 100) {
                $scope.excelUploadingPopup(false); // 작업내역 로딩 팝업 닫기
                // POS상품명(영문)의 데이터 중 문자열의 길이가 너무 긴 데이터가 있습니다. 최대 : 100, 현재 : 120(영문:1byte, 한글:3byte)
                $scope._popMsg(messages["prodLang.pos"] + messages["prodLang.prodNm"] + "(" + messages["prodLang.en"] + ")" + messages["cmm.overLength"] + "100" +
                    ", 현재 : " + posEnCombined.getByteLengthForOracle() + messages["cmm.bateLengthInfo"]);
                return false;
            }

            var posCnCombined = nvl(item.prodCnNmDisp2, '') !== '' ? item.prodCnNmDisp1 + '\n' + item.prodCnNmDisp2 : item.prodCnNmDisp1;
            if (nvl(posCnCombined, '').getByteLengthForOracle() > 100) {
                $scope.excelUploadingPopup(false); // 작업내역 로딩 팝업 닫기
                // POS상품명(중문)의 데이터 중 문자열의 길이가 너무 긴 데이터가 있습니다. 최대 : 100, 현재 : 120(영문:1byte, 한글:3byte)
                $scope._popMsg(messages["prodLang.pos"] + messages["prodLang.prodNm"] + "(" + messages["prodLang.cn"] + ")" + messages["cmm.overLength"] + "100" +
                    ", 현재 : " + posCnCombined.getByteLengthForOracle() + messages["cmm.bateLengthInfo"]);
                return false;
            }

            var posJpCombined = nvl(item.prodJpNmDisp2, '') !== '' ? item.prodJpNmDisp1 + '\n' + item.prodJpNmDisp2 : item.prodJpNmDisp1;
            if (nvl(posJpCombined, '').getByteLengthForOracle() > 100) {
                $scope.excelUploadingPopup(false); // 작업내역 로딩 팝업 닫기
                // POS상품명(일문)의 데이터 중 문자열의 길이가 너무 긴 데이터가 있습니다. 최대 : 100, 현재 : 120(영문:1byte, 한글:3byte)
                $scope._popMsg(messages["prodLang.pos"] + messages["prodLang.prodNm"] + "(" + messages["prodLang.jp"] + ")" + messages["cmm.overLength"] + "100" +
                    ", 현재 : " + posJpCombined.getByteLengthForOracle() + messages["cmm.bateLengthInfo"]);
                return false;
            }

            var kioskEnCombined = nvl(item.prodEnNmDispKiosk2, '') !== '' ? item.prodEnNmDispKiosk1 + '\n' + item.prodEnNmDispKiosk2 : item.prodEnNmDispKiosk1;
            if (nvl(kioskEnCombined, '').getByteLengthForOracle() > 100) {
                $scope.excelUploadingPopup(false); // 작업내역 로딩 팝업 닫기
                // 키오스크상품명(영문)의 데이터 중 문자열의 길이가 너무 긴 데이터가 있습니다. 최대 : 100, 현재 : 120(영문:1byte, 한글:3byte)
                $scope._popMsg(messages["prodLang.kiosk"] + messages["prodLang.prodNm"] + "(" + messages["prodLang.en"] + ")" + messages["cmm.overLength"] + "100" +
                    ", 현재 : " + kioskEnCombined.getByteLengthForOracle() + messages["cmm.bateLengthInfo"]);
                return false;
            }

            var kioskCnCombined = nvl(item.prodCnNmDispKiosk2, '') !== '' ? item.prodCnNmDispKiosk1 + '\n' + item.prodCnNmDispKiosk2 : item.prodCnNmDispKiosk1;
            if (nvl(kioskCnCombined, '').getByteLengthForOracle() > 100) {
                $scope.excelUploadingPopup(false); // 작업내역 로딩 팝업 닫기
                // 키오스크상품명(중문)의 데이터 중 문자열의 길이가 너무 긴 데이터가 있습니다. 최대 : 100, 현재 : 120(영문:1byte, 한글:3byte)
                $scope._popMsg(messages["prodLang.kiosk"] + messages["prodLang.prodNm"] + "(" + messages["prodLang.cn"] + ")" + messages["cmm.overLength"] + "100" +
                    ", 현재 : " + kioskCnCombined.getByteLengthForOracle() + messages["cmm.bateLengthInfo"]);
                return false;
            }

            var kioskJpCombined = nvl(item.prodJpNmDispKiosk2, '') !== '' ? item.prodJpNmDispKiosk1 + '\n' + item.prodJpNmDispKiosk2 : item.prodJpNmDispKiosk1;
            if (nvl(kioskJpCombined, '').getByteLengthForOracle() > 100) {
                $scope.excelUploadingPopup(false); // 작업내역 로딩 팝업 닫기
                // 키오스크상품명(일문)의 데이터 중 문자열의 길이가 너무 긴 데이터가 있습니다. 최대 : 100, 현재 : 120(영문:1byte, 한글:3byte)
                $scope._popMsg(messages["prodLang.kiosk"] + messages["prodLang.prodNm"] + "(" + messages["prodLang.jp"] + ")" + messages["cmm.overLength"] + "100" +
                    ", 현재 : " + kioskJpCombined.getByteLengthForOracle() + messages["cmm.bateLengthInfo"]);
                return false;
            }
        }

        // 데이터 저장
        $timeout(function () {
            $scope.saveData(jsonData);
        }, 10);
    };

    // 데이터 저장
    $scope.saveData = function (jsonData) {
        $scope.totalRows = jsonData.length;
        var params = [];

        // 저장 시작이면 업로드 중 팝업 오픈
        if ($scope.progressCnt === 0) {
            $timeout(function () {
                $scope.excelUploadingPopup(true);
                $("#progressCnt").html($scope.progressCnt);
                $("#totalRows").html($scope.totalRows);
            }, 10);
        }

        // stepCnt 만큼 데이터 DB에 저장
        var loopCnt = (parseInt($scope.progressCnt) + parseInt($scope.stepCnt) > parseInt($scope.totalRows) ? parseInt($scope.totalRows) : parseInt($scope.progressCnt) + parseInt($scope.stepCnt));
        for (var i = $scope.progressCnt; i < loopCnt; i++) {
            var item = jsonData[i];

            item.progressCnt = $scope.progressCnt;

            params.push(item);
        }

        //가상로그인 session 설정
        var sParam = {};
        if(document.getElementsByName('sessionId')[0]){
            sParam['sid'] = document.getElementsByName('sessionId')[0].value;
        }

        // ajax 통신 설정
        $http({
            method : 'POST', //방식
            url    : '/base/multilingual/prodLang/saveProdPlatform.sb', /* 통신할 URL */
            data   : params, /* 파라메터로 보낼 데이터 : @requestBody */
            params : sParam,
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        }).then(function successCallback(response) {
            if ($scope._httpStatusCheck(response, true)) {
                if (parseInt($scope.progressCnt) >= parseInt($scope.totalRows)) {
                    // 재조회
                    agrid.getScope('prodPlatformCtrl').getProdPlatformList();
                }
            }
        }, function errorCallback(response) {
            $scope.excelUploadingPopup(false); // 업로딩 팝업 닫기
            if (response.data.message) {
                $scope._popMsg(response.data.message);
            } else {
                $scope._popMsg(messages['cmm.saveFail']);
            }
            return false;
        }).then(function () {
            // 'complete' code here
            // 처리 된 숫자가 총 업로드할 수보다 작은 경우 다시 save 함수 호출
            if (parseInt($scope.progressCnt) < parseInt($scope.totalRows)) {
                // 처리된 숫자 변경
                $scope.progressCnt = loopCnt;
                // 팝업의 progressCnt 값 변경
                $("#progressCnt").html($scope.progressCnt);
                $scope.saveData(jsonData);
            }
        });

    };

    // 업로딩 팝업 열기
    $scope.excelUploadingPopup = function (showFg) {
        if (showFg) {
            // 팝업내용 동적 생성
            var innerHtml = '<div class=\"wj-popup-loading\"><p class=\"bk\">' + messages['empCardInfo.excelUploading'] + '</p>';
            innerHtml += '<div class="mt5 txtIn"><span class="bk" id="progressCnt">0</span>/<span class="bk" id="totalRows">0</span> 개 적용 중...</div>';
            innerHtml += '<p><img src=\"/resource/solbipos/css/img/loading.gif\" alt=\"\" /></p></div>';
            // html 적용
            $scope._loadingPopup.content.innerHTML = innerHtml;
            // 팝업 show
            $scope._loadingPopup.show(true);
        } else {
            $scope._loadingPopup.hide(true);
        }
    };

}]);
