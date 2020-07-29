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
            }, 'excel.xlsx', function () {
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
        console.log($scope.flex1);
    };
    $scope.$on("valChk", function (event, jsonData, mode) {
        $scope.totalRows = jsonData.length;
        var params = [];
        var msg = '';
        for (var i = 0; i < $scope.totalRows; i++) {
            var item = jsonData[i];
            if(!$scope.comboboxChk(item.membrClassCd, "class")) {return false}

            // 필수값 및 길이 체크
            // 회원등급분류
            if (nvl(item.membrClassCd, '') === '') {
                msg = messages["member.excel.membrClassCd"] + messages["excelUpload.require.data"]; // 상품코드/바코드(이)가 없는 데이터가 존재합니다.
                $scope.valueCheckErrPopup(msg);
                return false;
            }

            // 회원명(한글)
            if (nvl(item.membrKrNm, '') === '') {
                msg = messages["member.excel.nm.kr"] + messages["excelUpload.require.data"];
                $scope.valueCheckErrPopup(msg);
                return false;
            }

            // 회원명 최대길이 체크
            if (nvl(item.membrKrNm, '') !== '' && nvl(item.membrKrNm + '', '').getByteLengthForOracle() > 100) {
                msg = messages["member.excel.nm.kr"] + messages["excelUpload.overLength"] + " 100 " + messages["excelUpload.bateLengthInfo"];
                $scope.valueCheckErrPopup(msg);
                return false;
            }

            // 회원명 한글
            var numChkexp =  /[a-z0-9]|[ \[\]{}()<>?|`~!@#$%^&*-_+=,.;:\"'\\]/g;
            if (numChkexp.test(nvl(item.membrKrNm, 0))) {
                msg = messages["member.excel.nm.kr"] + messages["cmm.require.kr"];
                $scope.valueCheckErrPopup(msg);
                return false;
            }

            // 회원명(영문)
            if (nvl(item.membrEnNm, '') === '') {
                msg = messages["member.excel.nm.en"] + messages["excelUpload.require.data"];
                $scope.valueCheckErrPopup(msg);
                return false;
            }

            // 회원명 최대길이 체크
            if (nvl(item.membrEnNm, '') !== '' && nvl(item.membrEnNm + '', '').getByteLengthForOracle() > 100) {
                msg = messages["member.excel.nm.en"] + messages["excelUpload.overLength"] + " 100 " + messages["excelUpload.bateLengthInfo"];
                $scope.valueCheckErrPopup(msg);
                return false;
            }

            // 회원명 영문
            var numChkexp =  /^[A-za-z]/g;
            if (numChkexp.test(nvl(item.membrEnNm, 0))) {
                msg = messages["member.excel.nm.en"] + messages["cmm.require.en"];
                $scope.valueCheckErrPopup(msg);
                return false;
            }

            // 등록매장
            if (nvl(item.membrStore, '') === '') {
                msg = messages["member.excel.store"] + messages["excelUpload.require.data"];
                $scope.valueCheckErrPopup(msg);
                return false;
            }

            // 등록매장 최대길이 체크
            if (nvl(item.membrStore, '') !== '' && nvl(item.membrStore + '', '').getByteLengthForOracle() > 7) {
                msg = messages["member.excel.store"] + messages["excelUpload.overLength"] + " 7 " + messages["excelUpload.bateLengthInfo"];
                $scope.valueCheckErrPopup(msg);
                return false;
            }

            // 성별구분
            if (nvl(item.gendrFg, '') === '') {
                msg = messages["member.excel.gendrFg"] + messages["excelUpload.require.data"];
                $scope.valueCheckErrPopup(msg);
                return false;
            }

            // 성별구분 최대길이 체크
            if (nvl(item.gendrFg, '') !== '' && nvl(item.gendrFg + '', '').getByteLengthForOracle() > 1) {
                msg = messages["member.excel.gendrFg"] + messages["excelUpload.overLength"] + " 1 " + messages["excelUpload.bateLengthInfo"];
                $scope.valueCheckErrPopup(msg);
                return false;
            }
            // 성별구분 값체크
            if (nvl(item.gendrFg, '') === '남자' || nvl(item.gendrFg, '') === '여자' ) {
                msg = messages["member.excel.gendrFg"] + messages["cmm.input.fail"];
                $scope.valueCheckErrPopup(msg);
                return false;
            }

            // 회원카드번호
            if (nvl(item.membrCardNo, '') === '') {
                msg = messages["member.excel.membrCardNo"] + messages["excelUpload.require.data"];
                $scope.valueCheckErrPopup(msg);
                return false;
            }

            // 회원카드번호 최대길이 체크
            if (nvl(item.membrCardNo, '') !== '' && nvl(item.membrCardNo + '', '').getByteLengthForOracle() > 40) {
                msg = messages["member.excel.membrCardNo"] + messages["excelUpload.overLength"] + " 40 " + messages["excelUpload.bateLengthInfo"];
                $scope.valueCheckErrPopup(msg);
                return false;
            }

            // 생년월일
            if (nvl(item.birthday, '') === '') {
                msg = messages["member.excel.birthday"] + messages["excelUpload.require.data"];
                $scope.valueCheckErrPopup(msg);
                return false;
            }

            // 생년월일 최대길이 체크
            if (nvl(item.birthday, '') !== '' && nvl(item.birthday + '', '').getByteLengthForOracle() > 8) {
                msg = messages["member.excel.birthday"] + messages["excelUpload.overLength"] + " 8 " + messages["excelUpload.bateLengthInfo"];
                $scope.valueCheckErrPopup(msg);
                return false;
            }

            // 결혼여부
            if (nvl(item.weddingYn, '') === '') {
                msg = messages["member.excel.weddingYn"] + messages["excelUpload.require.data"];
                $scope.valueCheckErrPopup(msg);
                return false;
            }

            // 결혼여부 최대길이 체크
            if (nvl(item.weddingYn, '') !== '' && nvl(item.weddingYn + '', '').getByteLengthForOracle() > 8) {
                msg = messages["member.excel.weddingYn"] + messages["excelUpload.overLength"] + " 8 " + messages["excelUpload.bateLengthInfo"];
                $scope.valueCheckErrPopup(msg);
                return false;
            }
            // 결혼여부 값체크
            if (nvl(item.weddingYn, '') === '미혼' || nvl(item.weddingYn, '') === '기혼' ) {
                msg = messages["member.excel.weddingYn"] + messages["cmm.input.fail"];
                $scope.valueCheckErrPopup(msg);
                return false;
            }

            // 전화번호 최대길이 체크
            if (nvl(item.telNo, '') !== '' && nvl(item.telNo + '', '').getByteLengthForOracle() > 200) {
                msg = messages["member.excel.telNo"] + messages["excelUpload.overLength"] + " 200 " + messages["excelUpload.bateLengthInfo"];
                $scope.valueCheckErrPopup(msg);
                return false;
            }

            // 단축번호 최대길이 체크
            if (nvl(item.shortNo, '') !== '' && nvl(item.shortNo + '', '').getByteLengthForOracle() > 4) {
                msg = messages["member.excel.shortNo"] + messages["excelUpload.overLength"] + " 4 " + messages["excelUpload.bateLengthInfo"];
                $scope.valueCheckErrPopup(msg);
                return false;
            }

            // E-MAIL 최대길이 체크
            if (nvl(item.email, '') !== '' && nvl(item.email + '', '').getByteLengthForOracle() > 8) {
                msg = messages["member.excel.email"] + messages["excelUpload.overLength"] + " 8 " + messages["excelUpload.bateLengthInfo"];
                $scope.valueCheckErrPopup(msg);
                return false;
            }

            // 우편번호 최대길이 체크
            if (nvl(item.postNo, '') !== '' && nvl(item.postNo + '', '').getByteLengthForOracle() > 5) {
                msg = messages["member.excel.postNo"] + messages["excelUpload.overLength"] + " 5 " + messages["excelUpload.bateLengthInfo"];
                $scope.valueCheckErrPopup(msg);
                return false;
            }

            // 주소 최대길이 체크
            if (nvl(item.addr, '') !== '' && nvl(item.addr + '', '').getByteLengthForOracle() > 200) {
                msg = messages["member.excel.addr"] + messages["excelUpload.overLength"] + " 200 " + messages["excelUpload.bateLengthInfo"];
                $scope.valueCheckErrPopup(msg);
                return false;
            }

            // 상세주소 최대길이 체크
            if (nvl(item.addrDtl, '') !== '' && nvl(item.addrDtl + '', '').getByteLengthForOracle() > 200) {
                msg = messages["member.excel.addrDtl"] + messages["excelUpload.overLength"] + " 200 " + messages["excelUpload.bateLengthInfo"];
                $scope.valueCheckErrPopup(msg);
                return false;
            }
            // 이메일수신
            if (nvl(item.emailRecvYn, '') === '') {
                msg = messages["member.excel.emailRecvYn"] + messages["excelUpload.require.data"];
                $scope.valueCheckErrPopup(msg);
                return false;
            }
            // 이메일수신 값체크
            if (nvl(item.emailRecvYn, '') === 'Y' || nvl(item.emailRecvYn, '') === 'N' ) {
                msg = messages["member.excel.emailRecvYn"] + messages["cmm.input.fail"];
                $scope.valueCheckErrPopup(msg);
                return false;
            }

            // 이메일수신 최대길이 체크
            if (nvl(item.emailRecvYn, '') !== '' && nvl(item.emailRecvYn + '', '').getByteLengthForOracle() > 1) {
                msg = messages["member.excel.emailRecvYn"] + messages["excelUpload.overLength"] + " 8 " + messages["excelUpload.bateLengthInfo"];
                $scope.valueCheckErrPopup(msg);
                return false;
            }
            // SMS수신
            if (nvl(item.smsRecvYn, '') === '') {
                msg = messages["member.excel.smsRecvYn"] + messages["excelUpload.require.data"];
                $scope.valueCheckErrPopup(msg);
                return false;
            }
            // SMS수신 값체크
            if (nvl(item.smsRecvYn, '') === 'Y' || nvl(item.smsRecvYn, '') === 'N' ) {
                msg = messages["member.excel.smsRecvYn"] + messages["cmm.input.fail"];
                $scope.valueCheckErrPopup(msg);
                return false;
            }

            // SMS수신 최대길이 체크
            if (nvl(item.smsRecvYn, '') !== '' && nvl(item.smsRecvYn + '', '').getByteLengthForOracle() > 1) {
                msg = messages["member.excel.weddingYn"] + messages["excelUpload.overLength"] + " 1 " + messages["excelUpload.bateLengthInfo"];
                $scope.valueCheckErrPopup(msg);
                return false;
            }

            // 가용포인트
            if (nvl(item.avablPoint, '') === '') {
                msg = messages["member.excel.avablPoint"] + messages["excelUpload.require.data"];
                $scope.valueCheckErrPopup(msg);
                return false;
            }

            // 가용포인트 숫자가 아닌 값
            var numChkexp = /[^0-9]/g;
            if (numChkexp.test(nvl(item.avablPoint, 0))) {
                msg = messages["excelUpload.avablPoint"] + messages["excelUpload.not.numberData"];
                $scope.valueCheckErrPopup(msg);
                return false;
            }

            // 누적포인트
            if (nvl(item.totPoint, '') === '') {
                msg = messages["member.excel.totPoint"] + messages["excelUpload.require.data"];
                $scope.valueCheckErrPopup(msg);
                return false;
            }

            // 누적포인트 숫자가 아닌 값
            var numChkexp = /[^0-9]/g;
            if (numChkexp.test(nvl(item.totPoint, 0))) {
                msg = messages["excelUpload.totPoint"] + messages["excelUpload.not.numberData"];
                $scope.valueCheckErrPopup(msg);
                return false;
            }

            // 배달구역(대) 최대길이 체크
            if (nvl(item.dlAddrOne, '') !== '' && nvl(item.dlAddrOne + '', '').getByteLengthForOracle() > 3) {
                msg = messages["member.excel.dlAddrOne"] + messages["excelUpload.overLength"] + " 3 " + messages["excelUpload.bateLengthInfo"];
                $scope.valueCheckErrPopup(msg);
                return false;
            }

            // 배달구역(중) 최대길이 체크
            if (nvl(item.dlAddrTwo, '') !== '' && nvl(item.dlAddrTwo + '', '').getByteLengthForOracle() > 5) {
                msg = messages["member.excel.dlAddrTwo"] + messages["excelUpload.overLength"] + " 5 " + messages["excelUpload.bateLengthInfo"];
                $scope.valueCheckErrPopup(msg);
                return false;
            }

            // 배달구역(상세주소)
            if (nvl(item.totPdlAddrDtloint, '') === '') {
                msg = messages["member.excel.dlAddrDtl"] + messages["excelUpload.require.data"];
                $scope.valueCheckErrPopup(msg);
                return false;
            }
            // 배달구역(상세주소) 최대길이 체크
            if (nvl(item.dlAddrDtl, '') !== '' && nvl(item.dlAddrDtl + '', '').getByteLengthForOracle() > 200) {
                msg = messages["member.excel.dlAddrDtl"] + messages["excelUpload.overLength"] + " 200 " + messages["excelUpload.bateLengthInfo"];
                $scope.valueCheckErrPopup(msg);
                return false;
            }
        }
        return true;
    });

    $scope.comboboxChk = function (comboData, fg) {
        var filter = [];
        if(fg === "class") {
            filter = memberClassList.filter(e => {
                e.name === comboData;
            });
            if(filter.length == 0){
                $scope._popMsg(messages["cmm.input.fail"]);
                return false;
            }
        }
        return true;
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
}]);