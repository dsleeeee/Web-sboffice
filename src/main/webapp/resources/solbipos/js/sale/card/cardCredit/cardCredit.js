/****************************************************************
 *
 * 파일명 : cardCredit.js
 * 설  명 : 신용카드입금관리 (광운대 아이스링크) JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.09.08     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 입금은행
var creditBankData = [
    {"name":"국민은행","value":"국민은행"},
    {"name":"하나은행","value":"하나은행"}
];
// 취소내역포함여부
var rtnSaleFgComboData = [
    {"name":"포함","value":"1"},
    {"name":"미포함","value":"0"}
];
// 매장
var storeCdComboData = [
    {"name":"선택","value":""}
];

/**
 *  신용카드입금관리 그리드 생성
 */
app.controller('cardCreditCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('cardCreditCtrl', $scope, $http, $timeout, false));

    // 검색조건에 조회기간
    var startDate = wcombo.genDateVal("#startDate", gvStartDate);
    var endDate = wcombo.genDateVal("#endDate", gvEndDate);

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("rtnSaleFgCombo", rtnSaleFgComboData); // 취소내역포함여부
    $scope._setComboData("storeCdCombo", storeCdComboData); // 매장

    // 조회 여부
    var srchGubun = false;

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        $scope.creditBankDataMap = new wijmo.grid.DataMap(creditBankData, 'value', 'name'); // 입금은행

        // 합계
        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');

        // 매장 콤보박스
        $scope.storeCdComboList();
    };

    // <-- 검색 호출 -->
    $scope.$on("cardCreditCtrl", function(event, data) {
        $scope.searchCardCredit();
        event.preventDefault();
    });

    $scope.searchCardCredit = function(){
        var params = {};
        params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd'); // 조회기간
        params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd'); // 조회기간
        params.rtnSaleFg = $scope.rtnSaleFgCombo;
        params.storeCd = $scope.storeCdCombo;

        $scope._inquiryMain("/sale/card/cardCredit/cardCredit/getCardCreditList.sb", params, function() {
            // 조회 여부
            srchGubun = true;
            // 조회된 날짜
            $("#lblSrchDate").text("승인일자 : " + params.startDate + " ~ " + params.endDate);
            $("#lblSrchStartDate").text(params.startDate);
            $("#lblSrchEndDate").text(params.endDate);
            // 조회된 매장
            $("#lblSrchStore").text("/ 매장 : " + params.storeCd);
            $("#lblStoreCd").text(params.storeCd);
            // 조회된 취소내역포함여부
            $("#lblRtnSaleFg").text(params.rtnSaleFg);

            // 엑셀다운로드
            var storeScope = agrid.getScope('cardCreditExcelSampleCtrl');
            storeScope.searchCardCreditExcelSample(params);
        }, false);
    };
    // <-- //검색 호출 -->

    // 매장 콤보박스
    $scope.storeCdComboList = function() {
        var params = {};

        $scope._postJSONQuery.withOutPopUp('/sale/card/cardCredit/cardCredit/getStoreCdComboList.sb', params, function (response) {
            if (response.data.data.list.length > 0) {
                var storeCdList = response.data.data.list;
                $scope._setComboData("storeCdCombo", storeCdList); // 매장
            } else {
                $scope._setComboData("storeCdCombo", storeCdComboData); // 매장
            }
        });
    };

    // <-- 그리드 저장 -->
    $scope.save = function() {
        for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
            // 입금일자
            if ($scope.flex.collectionView.itemsEdited[i].creditDate === "" || $scope.flex.collectionView.itemsEdited[i].creditDate === null) {
                $scope._popMsg(messages["cardCredit.creditDateBlank"]); // 입금일자를 입력해주세요.
                return false;
            } else {
                // 길이체크
                if (parseInt(nvl($scope.flex.collectionView.itemsEdited[i].creditDate.replaceAll("-", "").length, 0)) > 8) {
                    $scope._popMsg(messages["cardCredit.creditDateLengthChk"]); // 입금일자가 너무 깁니다. 8자리로 입력해주세요. <br/> (예시 : 2090-09-15)
                    return false;
                }

                // 유효한 날짜인지 체크(윤달 포함)
                var creditDate = $scope.flex.collectionView.itemsEdited[i].creditDate;
                if (parseInt(nvl(creditDate.length, 0)) === 8) {
                    creditDate = creditDate.substr(0, 4) + "-" + creditDate.substr(4, 2) + "-" + creditDate.substr(6, 2);
                }
                var creditDateChk = $scope.checkValidDate(creditDate);
                if (!creditDateChk) {
                    $scope._popMsg(messages["cardCredit.creditDateInChk"]); // 존재하지 않는 날짜입니다.
                    return false;
                }
            }
            // 입금금액
            if ($scope.flex.collectionView.itemsEdited[i].creditAmt === "" || $scope.flex.collectionView.itemsEdited[i].creditAmt === null) {
                $scope._popMsg(messages["cardCredit.creditAmtBlank"]); // 입금금액을 입력해주세요.
                return false;
            } else {
                // 길이체크
                if (parseInt(nvl($scope.flex.collectionView.itemsEdited[i].creditAmt.length, 0)) > 13) {
                    $scope._popMsg(messages["cardCredit.creditAmtLengthChk"]); // 입금금액이 너무 깁니다.
                    return false;
                }
                // 숫자만 입력
                var numChkexp = /[^0-9]/g;
                if (numChkexp.test($scope.flex.collectionView.itemsEdited[i].creditAmt)) {
                    $scope._popMsg(messages["cardCredit.creditAmtInChk"]); // 입금금액는 숫자만 입력해주세요.
                    return false;
                }
            }
            // 수수료
            if ($scope.flex.collectionView.itemsEdited[i].creditFee === "" || $scope.flex.collectionView.itemsEdited[i].creditFee === null) {
                $scope._popMsg(messages["cardCredit.creditFeeBlank"]); // 수수료를 입력해주세요.
                return false;
            } else {
                // 길이체크
                if (parseInt(nvl($scope.flex.collectionView.itemsEdited[i].creditFee.length, 0)) > 13) {
                    $scope._popMsg(messages["cardCredit.creditFeeLengthChk"]); // 수수료가 너무 깁니다.
                    return false;
                }
                // 숫자만 입력
                var numChkexp = /[^0-9]/g;
                if (numChkexp.test($scope.flex.collectionView.itemsEdited[i].creditAmt)) {
                    $scope._popMsg(messages["cardCredit.creditFeeInChk"]); // 수수료는 숫자만 입력해주세요.
                    return false;
                }
            }
            // 입금은행
            if ($scope.flex.collectionView.itemsEdited[i].creditBank === "" || $scope.flex.collectionView.itemsEdited[i].creditBank === null) {
                $scope._popMsg(messages["cardCredit.creditBankBlank"]); // 입금은행을 입력해주세요.
                return false;
            } else {
                // 입력 데이터 체크
                if (!($scope.flex.collectionView.itemsEdited[i].creditBank === "국민은행" || $scope.flex.collectionView.itemsEdited[i].creditBank === "하나은행")) {
                    $scope._popMsg(messages["cardCredit.creditBankInChk"]); // 입금은행은 '국민은행, 하나은행' 만 입력 가능합니다.
                    return false;
                }
            }
        }

        // 파라미터 설정
        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
            $scope.flex.collectionView.itemsEdited[i].saleDate = $scope.flex.collectionView.itemsEdited[i].saleDate.replaceAll("-", "");
            $scope.flex.collectionView.itemsEdited[i].apprDate = $scope.flex.collectionView.itemsEdited[i].apprDate.replaceAll("-", "");
            $scope.flex.collectionView.itemsEdited[i].creditDate = $scope.flex.collectionView.itemsEdited[i].creditDate.replaceAll("-", "");
            $scope.flex.collectionView.itemsEdited[i].status = "U";
            params.push($scope.flex.collectionView.itemsEdited[i]);
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/sale/card/cardCredit/cardCredit/getCardCreditSave.sb", params, function () {
            $scope.allSearch()
        });
    };

    // 재조회
    $scope.allSearch = function () {
        $scope.searchCardCredit();
    };
    // <-- //그리드 저장 -->

    // 유효한 날짜인지 체크(윤달 포함)
    $scope.checkValidDate = function (value) {
        var result = true;
        try {
            var date = value.split("-");
            var y = parseInt(date[0], 10),
                m = parseInt(date[1], 10),
                d = parseInt(date[2], 10);

            var dateRegex = /^(?=\d)(?:(?:31(?!.(?:0?[2469]|11))|(?:30|29)(?!.0?2)|29(?=.0?2.(?:(?:(?:1[6-9]|[2-9]\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00)))(?:\x20|$))|(?:2[0-8]|1\d|0?[1-9]))([-.\/])(?:1[012]|0?[1-9])\1(?:1[6-9]|[2-9]\d)?\d\d(?:(?=\x20\d)\x20|$))?(((0?[1-9]|1[012])(:[0-5]\d){0,2}(\x20[AP]M))|([01]\d|2[0-3])(:[0-5]\d){1,2})?$/;
            result = dateRegex.test(d+'-'+m+'-'+y);
        } catch (err) {
            result = false;
        }
        return result;
    };

    // <-- 엑셀다운로드 -->
    $scope.excelDownload = function(){
        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]);	// 다운로드 할 데이터가 없습니다.
            return false;
        }

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
        $timeout(function()	{
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync(	$scope.flex,
                {
                    includeColumnHeaders: 	true,
                    includeCellStyles	: 	false,
                    includeColumns      :	function (column) {
                        return column.visible;
                    }
                },
                '신용카드입금관리_'+getCurDate()+'.xlsx',
                function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                    }, 10);
                }
            );
        }, 10);
    };
    // <-- //엑셀다운로드 -->

    // <-- 양식다운로드 -->
    $scope.excelSampleDownload = function(){
        // 조회 여부
        if(srchGubun) {
            // 엑셀다운로드
            var storeScope = agrid.getScope('cardCreditExcelSampleCtrl');
            storeScope.excelDownload();
        }
    };
    // <-- //양식다운로드 -->

    // <-- 엑셀업로드 -->
    $scope.excelUpload = function(){
        // 조회 여부
        if(srchGubun) {
            // 신용카드입금관리 엑셀업로드 팝업
            $("#cardCreditExcelUpFile").val('');
            $("#cardCreditExcelUpFile").trigger('click');
        } else {
            $scope._popMsg(messages["cardCredit.srchGubunChk"]); // 조회 후 등록이 가능합니다.
            return false;
        }
    };
    // <-- //엑셀업로드 -->
}]);


/**
 *  신용카드입금관리 엑셀 샘플 양식 그리드 생성
 */
app.controller('cardCreditExcelSampleCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('cardCreditExcelSampleCtrl', $scope, $http, $timeout, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        $scope.creditBankDataMap = new wijmo.grid.DataMap(creditBankData, 'value', 'name'); // 입금은행
    };

    // <-- 검색 호출 -->
    $scope.$on("cardCreditExcelSampleCtrl", function(event, data) {
        $scope.searchCardCreditExcelSample(data);
        event.preventDefault();
    });

    $scope.searchCardCreditExcelSample = function(params){
        $scope._inquiryMain("/sale/card/cardCredit/cardCredit/getCardCreditExcelSampleList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // <-- 엑셀다운로드 -->
    $scope.excelDownload = function(){
        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]);	// 다운로드 할 데이터가 없습니다.
            return false;
        }

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
        $timeout(function()	{
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync(	$scope.flex,
                {
                    includeColumnHeaders: 	true,
                    includeCellStyles	: 	false,
                    includeColumns      :	function (column) {
                        return column.visible;
                    }
                },
                '신용카드입금관리_양식_'+getCurDate()+'.xlsx',
                function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                    }, 10);
                }
            );
        }, 10);
    };
    // <-- //엑셀다운로드 -->
}]);