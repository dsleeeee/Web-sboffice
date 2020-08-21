/****************************************************************
 *
 * 파일명 : memberPoint.js
 * 설  명 : 회원포인트조정 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.06.15    Daniel      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  세금계산서 요청목록 그리드 생성
 */
app.controller('memberPointCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 성공내역, 실페내역
    $scope.statusList = [
        {value: '1', name: '전체'},
        {value: '2', name: '성공내역'},
        {value: '3', name: '오류내역'}
    ]
    $scope.status = $scope.statusList[0]

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('memberPointCtrl', $scope, $http, '$timeout', true));

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("listScaleBox", gvListScaleBoxData);
    $scope._getComboDataQuery('072', 'emailRecvYn', '');
    $scope._getComboDataQuery('072', 'smsRecvYn', '');
    $scope._getComboDataQuery('032', 'anvType', '');
    $scope._getComboDataQuery('077', 'periodType', '');
    $scope._getComboDataQuery('076', 'weddingYn', '');
    $scope._getComboDataQuery('055', 'gendrFg', '');
    $scope._getComboDataQuery('067', 'useYn', '');

    // 선택 회원
    $scope.selectedMember;
    $scope.setSelectedMember = function (member) {
        $scope.selectedMember = member;
    };
    $scope.getSelectedMember = function () {
        return $scope.selectedMember;
    };

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        // $scope.statusFgDataMap = new wijmo.grid.DataMap(statusDataFg, 'value', 'name');
        $scope.memberClassList = new wijmo.grid.DataMap(memberClassList, 'value', 'name');
        // 합계
        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    // 엑셀 다운로드
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
            }, '회원포인트조정_' + getToday() + '.xlsx', function () {
                $timeout(function () {
                    $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                }, 10);
            });
        }, 10);
    };


    /** 엑셀업로드 관련 공통 함수 */
    $scope.excelTextUpload = function (prcsFg) {
        var excelUploadScope = agrid.getScope('excelUploadCtrl');
        /** 업로드 구분. 해당값에 따라 엑셀 양식이 달라짐. */
        var uploadFg = 'memberPoint';
        // 엑셀 양식다운로드
        if (prcsFg === 'excelFormDown') {
            excelUploadScope.excelFormDownload(uploadFg);
        } else {
            excelUploadScope.uploadFg = uploadFg;
            // /** 부모컨트롤러 값을 넣으면 업로드가 완료된 후 uploadCallBack 이라는 함수를 호출해준다. */
            // excelUploadScope.parentCtrl = 'memberExcelUploadCtrl';
            // 엑셀 업로드
            $("#memberPointUpload").val('');
            $("#memberPointUpload").trigger('click')

        }
    };


    // 양식검증
    $scope.formChk = function () {
        var scope = agrid.getScope('excelUploadCtrl');

        var jsonData = [];
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            var items = $scope.flex.collectionView.items[i];
            jsonData.push({
                membrCardNo: items.membrCardNo,
                membrNm: items.membrNm,
                membrNo: items.membrNo,
                totAdjPoint: items.totAdjPoint
            });
        }
        scope.ajaxChk(jsonData);
        $scope.flex.collectionView.refresh();
    };


    // 삭제
    $scope.deleteUpload = function () {
        for (var i = $scope.flex.collectionView.items.length - 1; i >= 0; i--) {
            var item = $scope.flex.collectionView.items[i];
            if (item.gChk) {
                $scope.flex.collectionView.removeAt(i);
            }
        }
    };

    // 저장
    $scope.save = function () {
        var params = new Array();

        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            var item = $scope.flex.collectionView.items[i];
            if (item.memberResult !== "검증성공" || item.result === null) {
                $scope._popMsg(messages["cmm.excel.result"]);
                return false;
            }
            memberClassList.find(e => {
                if (e.name === $scope.flex.collectionView.items[i].membrClassCd) {
                    $scope.flex.collectionView.items[i].membrClassCd = e.value;
                }
            });
            // item.totSaleCnt = 0;
            // item.totSaleAmt = 0;
            // item.totDcAmt = 0;
            // item.totSavePoint = 0;
            // item.totUsePoint = 0;
            $scope.flex.collectionView.items[i].status = "U";
            params.push({
                status: "U",
                membrClassCd: item.membrClassCd,
                membrNo: item.membrNo,
                totAdjPoint: item.totAdjPoint,
                avablPoint: (item.avablPoint = item.totAdjPointAfter)
            });
        }
        try {
            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $.postJSONArray("/membr/info/point/point/memberPointSave.sb", params, function (result) {
            }, function (err) {
                s_alert.pop(err.message);
            });
        } catch (err) {

        }
    };

    $scope.adjustAll = function () {
        let param = $scope.changeAll
        param.totAjdPoint = param.totAjdPoint * 1
        $http({
            method: 'POST', //방식
            url: "/membr/info/point/point/adjustAll.sb", /* 통신할 URL */
            params: param, /* 파라메터로 보낼 데이터 */
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        }).then(function successCallback(response) {
        }, function errorCallback(response) {
            // 로딩바 hide
            $scope.$broadcast('loadingPopupInactive');
            // called asynchronously if an error occurs
            // or server returns response with an error status.
            if (response.data.message) {
                $scope._popMsg(response.data.message);
            } else {
                $scope._popMsg(messages['cmm.error']);
            }
            return false;
        }).then(function () {
            // 'complete' code here
            if (typeof callback === 'function') {
                setTimeout(function () {
                    callback();
                }, 10);
            }
        });
    }

// $scope.$on("memberPointCtrl", function (event, data) {
//   $scope.searchMemberPointList();
//   event.preventDefault();
// });

// // 후불회원 그리드 조회
// $scope.searchMemberPointList = function () {
//   var params = {};
//   $scope._inquiryMain("membr/info/point/point/getMemberPointList.sb", params, function () {
//   }, false);
// };
}])
;