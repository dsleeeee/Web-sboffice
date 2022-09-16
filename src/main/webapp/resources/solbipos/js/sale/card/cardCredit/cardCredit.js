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

/**
 *  신용카드입금관리 그리드 생성
 */
app.controller('cardCreditCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('cardCreditCtrl', $scope, $http, $timeout, false));

    // 검색조건에 조회기간
    var startDate = wcombo.genDateVal("#startDate", gvStartDate);
    var endDate = wcombo.genDateVal("#endDate", gvEndDate);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        $scope.creditBankDataMap = new wijmo.grid.DataMap(creditBankData, 'value', 'name'); // 입금은행

        // 합계
        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];

                if (col.format === "date") {
                    e.cell.innerHTML = getFormatDate(e.cell.innerText);
                } else if (col.format === "dateTime") {
                    e.cell.innerHTML = getFormatDateTime(e.cell.innerText);
                } else if (col.format === "time") {
                    e.cell.innerHTML = getFormatTime(e.cell.innerText, 'hms');
                }
            }
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("cardCreditCtrl", function(event, data) {
        $scope.searchCardCredit();
        event.preventDefault();
    });

    $scope.searchCardCredit = function(){
        var params = {};
        params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd'); //조회기간
        params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd'); //조회기간

        $scope._inquiryMain("/sale/card/cardCredit/cardCredit/getCardCreditList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // <-- 그리드 저장 -->
    $scope.save = function() {
        for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
            // 입금일자
            if($scope.flex.collectionView.itemsEdited[i].creditDate === "" || $scope.flex.collectionView.itemsEdited[i].creditDate === null) {
                $scope._popMsg(messages["cardCredit.creditDateBlank"]); // 입금일자를 입력해주세요.
                return false;
            } else {
                // 길이체크
                if(parseInt(nvl($scope.flex.collectionView.itemsEdited[i].creditDate.length, 0)) > 8) {
                    $scope._popMsg(messages["cardCredit.creditDateLengthChk"]); // 입금일자가 너무 깁니다. 8자리로 입력해주세요.
                    return false;
                }
            }
            // 입금금액
            if($scope.flex.collectionView.itemsEdited[i].creditAmt === "" || $scope.flex.collectionView.itemsEdited[i].creditAmt === null) {
                $scope._popMsg(messages["cardCredit.creditAmtBlank"]); // 입금금액을 입력해주세요.
                return false;
            } else {
                // 길이체크
                if(parseInt(nvl($scope.flex.collectionView.itemsEdited[i].creditAmt.length, 0)) > 13) {
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
            if($scope.flex.collectionView.itemsEdited[i].creditFee === "" || $scope.flex.collectionView.itemsEdited[i].creditFee === null) {
                $scope._popMsg(messages["cardCredit.creditFeeBlank"]); // 수수료를 입력해주세요.
                return false;
            } else {
                // 길이체크
                if(parseInt(nvl($scope.flex.collectionView.itemsEdited[i].creditFee.length, 0)) > 13) {
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
            if($scope.flex.collectionView.itemsEdited[i].creditBank === "" || $scope.flex.collectionView.itemsEdited[i].creditBank === null) {
                $scope._popMsg(messages["cardCredit.creditBankBlank"]); // 입금은행을 입력해주세요.
                return false;
            } else{
                if(! ($scope.flex.collectionView.itemsEdited[i].creditBank === "국민은행" || $scope.flex.collectionView.itemsEdited[i].creditBank === "하나은행") ) {
                    $scope._popMsg(messages["cardCredit.creditBankInChk"]); // 입금은행은 '국민은행, 하나은행' 만 입력 가능합니다.
                    return false;
                }
            }
        }

        // 파라미터 설정
        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
            $scope.flex.collectionView.itemsEdited[i].status = "U";
            params.push($scope.flex.collectionView.itemsEdited[i]);
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/sale/card/cardCredit/cardCredit/getCardCreditSave.sb", params, function(){ $scope.allSearch() });
    };

    // 재조회
    $scope.allSearch = function () {
        $scope.searchCardCredit();
    };
    // <-- //그리드 저장 -->

    // <-- 엑셀다운로드 -->
    $scope.excelDownload = function(){
        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]);	//다운로드 할 데이터가 없습니다.
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
                        $scope.$broadcast('loadingPopupInactive'); //데이터 처리중 메시지 팝업 닫기
                    }, 10);
                }
            );
        }, 10);
    };
    // <-- //엑셀다운로드 -->

}]);