/****************************************************************
 *
 * 파일명 : memberExcelUpload.js
 * 설  명 : 회원포인트조정 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.06.23    Daniel      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  세금계산서 요청목록 그리드 생성
 */
app.controller('memberExcelUploadCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('memberExcelUploadCtrl', $scope, $http, '$timeout', true));
    // 성공내역, 실페내역
    $scope.statusList = [
        {value: '1', name: '전체'},
        {value: '2', name: '성공내역'},
        {value: '3', name: '오류내역'}
    ]
    $scope.statu = $scope.statusList[0];
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
        $scope.useYnDataMap = new wijmo.grid.DataMap(useDataMap, 'value', 'name');
        $scope.recvDataMap = new wijmo.grid.DataMap(recvDataMap, 'value', 'name');
        $scope.regstrStoreList = new wijmo.grid.DataMap(regstrStoreList, 'value', 'name');
        $scope.memberClassList = new wijmo.grid.DataMap(memberClassList, 'value', 'name');
        $scope.genderDataMap = new wijmo.grid.DataMap(genderDataMap, 'value', 'name');
        $scope.weddingDataMap = new wijmo.grid.DataMap(weddingDataMap, 'value', 'name');
        $scope.dlvrLzoneList = new wijmo.grid.DataMap(dlvrLzoneList, 'value', 'name');
    };

    $scope.initGrid1 = function (s, e) {
        $scope.useYnDataMap = new wijmo.grid.DataMap(useDataMap, 'value', 'name');
        $scope.recvDataMap = new wijmo.grid.DataMap(recvDataMap, 'value', 'name');
        $scope.regstrStoreList = new wijmo.grid.DataMap(regstrStoreList, 'value', 'name');
        $scope.memberClassList = new wijmo.grid.DataMap(memberClassList, 'value', 'name');
        $scope.genderDataMap = new wijmo.grid.DataMap(genderDataMap, 'value', 'name');
        $scope.weddingDataMap = new wijmo.grid.DataMap(weddingDataMap, 'value', 'name');
        $scope.dlvrLzoneList = new wijmo.grid.DataMap(dlvrLzoneList, 'value', 'name');
        $scope.rMembrcardList = new wijmo.grid.DataMap(rMembrcardList, 'value', 'name');


        // row 추가
        var flex1 = $scope.flex1;
        if (!flex1.collectionView) {
            flex1.itemsSource = new wijmo.collections.CollectionView();
        }
        var newRow = flex1.collectionView.addNew();
        // 파라미터 설정
        var params = {};
        params.comboClass = '001';
        params.comboStore = '';
        params.comboGendr = 'N';
        params.comboWedding = 'N';
        params.comboCardUse = '1';
        params.comboEmail = 'N';
        params.comboSms = 'N';
        params.comboLzone = '001';
        params.comboMzone = '';

        // $scope._addRow(params);

        newRow.status = 'I';
        newRow.gChk = true;
        for (var prop in params) {
            newRow[prop] = params[prop];
        }
        $scope.flex1.collectionView.trackChanges = true;
        $scope.flex1.collectionView.commitNew();
    };


    $scope.$on("memberExcelUploadCtrl", function (event, data) {
        $scope.searchMemberExcelList();
        event.preventDefault();
    });

    $scope.searchMemberExcelList = function () {
        var params = {};
        $scope._inquiryMain("/membr/info/upload/excel/getMemberExcelList.sb", params, function () {
        }, false);
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
            }, '회원엑셀업로드_' + getToday() + '.xlsx', function () {
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
        var uploadFg = 'memberExcel';

        // 엑셀 양식다운로드
        if (prcsFg === 'excelFormDown') {
            excelUploadScope.excelFormDownload(uploadFg);
        } else {
            excelUploadScope.uploadFg = uploadFg;
            // /** 부모컨트롤러 값을 넣으면 업로드가 완료된 후 uploadCallBack 이라는 함수를 호출해준다. */
            // excelUploadScope.parentCtrl = 'memberExcelUploadCtrl';
            // 엑셀 업로드
            $("#memberExcelUpload").val('');
            $("#memberExcelUpload").trigger('click')

        }
    };

    // 양식검증
    $scope.formChk = function () {
        var scope = agrid.getScope('excelUploadCtrl');
        var jsonData = $scope.flex.collectionView.items;
        scope.valChk(jsonData);
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
            if (item.result !== "검증성공" || item.result === null) {
                $scope._popMsg(messages["cmm.excel.result"]);
                return false;
            }
            memberClassList.find(e => {
                if(e.name === $scope.flex.collectionView.items[i].membrClassCd) {
                    $scope.flex.collectionView.items[i].membrClassCd = e.value;
                }
            });
            regstrStoreList.find(e => {
                if(e.name === $scope.flex.collectionView.items[i].membrStore) {
                    $scope.flex.collectionView.items[i].membrStore = e.value;
                    $scope.flex.collectionView.items[i].regStoreCd = e.value;
                }
            });
            genderDataMap.find(e => {
                if(e.name === $scope.flex.collectionView.items[i].gendrFg) {
                    $scope.flex.collectionView.items[i].gendrFg = e.value;
                }
            });
            weddingDataMap.find(e => {
                if(e.name === $scope.flex.collectionView.items[i].weddingYn) {
                    $scope.flex.collectionView.items[i].weddingYn = e.value;
                }
            });
            recvDataMap.find(e => {
                if(e.name === $scope.flex.collectionView.items[i].smsRecvYn) {
                    $scope.flex.collectionView.items[i].smsRecvYn = e.value;
                }
            });
            recvDataMap.find(e => {
                if(e.name === $scope.flex.collectionView.items[i].emailRecvYn) {
                    $scope.flex.collectionView.items[i].emailRecvYn = e.value;
                }
            });
            $scope.flex.collectionView.items[i].status = "I";
            params.push($scope.flex.collectionView.items[i]);
        }
        try {
            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $.postJSONArray("/membr/info/upload/excel/memberExcelSave.sb", params, function (result) {
            }, function (err) {
                s_alert.pop(err.message);
            });
        } catch (err) {

        }
    };

}])
;