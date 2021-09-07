/****************************************************************
 *
 * 파일명 : empCardInfo.js
 * 설  명 : 사원카드정보관리 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.08.13     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('empCardInfoCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('empCardInfoCtrl', $scope, $http, true));

    // 콤보박스 데이터 Set
    $scope._setComboData("listScaleBox", gvListScaleBoxData);

    $scope.initGrid = function (s, e) {

        // ReadOnly 효과설정
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                if (col.binding === "employeeCardNo") {
                    var item = s.rows[e.row].dataItem;
                    if (item.status !== "I") {
                        wijmo.addClass(e.cell, 'wj-custom-readonly');
                        wijmo.setAttribute(e.cell, 'aria-readonly', true);

                        // Attribute 의 변경사항을 적용.
                        e.cell.outerHTML = e.cell.outerHTML;
                    }
                }
            }
        });
    };

    $scope.$on("empCardInfoCtrl", function(event, data) {
        
        // 조회
        $scope.searchEmpCardInfo();
        event.preventDefault();
    });
    
    // 조회
    $scope.searchEmpCardInfo = function () {

        // 파라미터
        var params = {};
        params.listScale = $scope.listScale;
        // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
        $scope._inquiryMain("/base/store/emp/cardInfo/getEmpCardInfo.sb", params, function(){});
    };

    // 추가
    $scope.addRow = function() {
        // 파라미터 설정
        var params = {};
        params.employeeCardNo = '';
        params.employeeNo = '';
        params.employeeNm = '';
        params.divNm = '';
        params.deptNm = '';
        params.positionNm = '';
        // 추가기능 수행 : 파라미터
        $scope._addRow(params, 1);
    };

    // 삭제
    $scope.delRow = function() {

        $scope._popConfirm(messages["cmm.choo.delete"], function() {

            for (var i = $scope.flex.collectionView.items.length - 1; i >= 0; i--) {
                var item = $scope.flex.collectionView.items[i];
                if (item.gChk) {
                    $scope.flex.collectionView.removeAt(i);
                }
            }

            var params = [];
            for (var i = 0; i < $scope.flex.collectionView.itemsRemoved.length; i++) {
                $scope.flex.collectionView.itemsRemoved[i].status = 'D';
                params.push($scope.flex.collectionView.itemsRemoved[i]);
            }

            // 삭제기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save('/base/store/emp/cardInfo/saveEmpCardInfo.sb', params, function() {
                $scope._broadcast('empCardInfoCtrl');
            });
        });
    };

    // 저장 전 값 체크
    $scope.saveChkRow = function() {

        // 사원카드번호 필수입력 체크
        for (var i = 0; i < $scope.flex.collectionView.itemCount; i++) {
            if( $scope.flex.collectionView.items[i].employeeCardNo === null  || $scope.flex.collectionView.items[i].employeeCardNo === '') {
                $scope._popMsg(messages['empCardInfo.saveEmpCard.chk.msg']); // 사원카드번호를 입력해주세요.
                return;
            }
        }

        var strEmpCardNo = ""; // 사원카드번호 중복체크를 위함.

        // 데이터 자릿수 체크
        for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
            if(nvl($scope.flex.collectionView.itemsEdited[i].employeeCardNo, '').getByteLengthForOracle() > 30){
                // 사원카드번호는 최대 30byte까지 입력 가능합니다. (한글 3byte 그외 1byte)
                $scope._popMsg(messages['empCardInfo.empCardNo'] + "는 " + messages["cmm.max30Chk"] + "<br>(사원카드번호 : " + $scope.flex.collectionView.itemsEdited[i].employeeCardNo + ")");
                return;
            }

            if(nvl($scope.flex.collectionView.itemsEdited[i].employeeNo, '').getByteLengthForOracle() > 50){
                // 사원번호는 최대 50byte까지 입력 가능합니다. (한글 3byte 그외 1byte)
                $scope._popMsg(messages['empCardInfo.empNo'] + "는 " + messages["cmm.max50Chk"] + "<br>(사원번호 : " + $scope.flex.collectionView.itemsEdited[i].employeeNo + ")");
                return;
            }

            if(nvl($scope.flex.collectionView.itemsEdited[i].employeeNm, '').getByteLengthForOracle() > 100){
                // 사원이름은 최대 100byte까지 입력 가능합니다. (한글 3byte 그외 1byte)
                $scope._popMsg(messages['empCardInfo.empNm'] + "은 " + messages["cmm.max100Chk"] + "<br>(사원이름 : " + $scope.flex.collectionView.itemsEdited[i].employeeNm + ")");
                return;
            }

            if(nvl($scope.flex.collectionView.itemsEdited[i].divNm, '').getByteLengthForOracle() > 60){
                // 소속명은 최대 60byte까지 입력 가능합니다. (한글 3byte 그외 1byte)
                $scope._popMsg(messages['empCardInfo.divNm'] + "은 " + messages["cmm.max60Chk"] + "<br>(소속명 : " + $scope.flex.collectionView.itemsEdited[i].divNm + ")");
                return;
            }

            if(nvl($scope.flex.collectionView.itemsEdited[i].deptNm, '').getByteLengthForOracle() > 60){
                // 부서명은 최대 60byte까지 입력 가능합니다. (한글 3byte 그외 1byte)
                $scope._popMsg(messages['empCardInfo.deptNm'] + "은 " + messages["cmm.max60Chk"] + "<br>(부서명 : " + $scope.flex.collectionView.itemsEdited[i].deptNm + ")");
                return;
            }

            if(nvl($scope.flex.collectionView.itemsEdited[i].positionNm, '').getByteLengthForOracle() > 30){
                // 직위명은 최대 30byte까지 입력 가능합니다. (한글 3byte 그외 1byte)
                $scope._popMsg(messages['empCardInfo.positionNm'] + "은 " + messages["cmm.max30Chk"] + "<br>(직위명 : " + $scope.flex.collectionView.itemsEdited[i].positionNm + ")");
                return;
            }
        }
        for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
            if(nvl($scope.flex.collectionView.itemsAdded[i].employeeCardNo, '').getByteLengthForOracle() > 30){
                // 사원카드번호는 최대 30byte까지 입력 가능합니다. (한글 3byte 그외 1byte)
                $scope._popMsg(messages['empCardInfo.empCardNo'] + "는 " + messages["cmm.max30Chk"] + "<br>(사원카드번호 : " + $scope.flex.collectionView.itemsAdded[i].employeeCardNo + ")");
                return;
            }

            if(nvl($scope.flex.collectionView.itemsAdded[i].employeeNo, '').getByteLengthForOracle() > 50){
                // 사원번호는 최대 50byte까지 입력 가능합니다. (한글 3byte 그외 1byte)
                $scope._popMsg(messages['empCardInfo.empNo'] + "는 " + messages["cmm.max50Chk"] + "<br>(사원번호 : " + $scope.flex.collectionView.itemsAdded[i].employeeNo + ")");
                return;
            }

            if(nvl($scope.flex.collectionView.itemsAdded[i].employeeNm, '').getByteLengthForOracle() > 100){
                // 사원이름은 최대 100byte까지 입력 가능합니다. (한글 3byte 그외 1byte)
                $scope._popMsg(messages['empCardInfo.empNm'] + "은 " + messages["cmm.max100Chk"] + "<br>(사원이름 : " + $scope.flex.collectionView.itemsAdded[i].employeeNm + ")");
                return;
            }

            if(nvl($scope.flex.collectionView.itemsAdded[i].divNm, '').getByteLengthForOracle() > 60){
                // 소속명은 최대 60byte까지 입력 가능합니다. (한글 3byte 그외 1byte)
                $scope._popMsg(messages['empCardInfo.divNm'] + "은 " + messages["cmm.max60Chk"] + "<br>(소속명 : " + $scope.flex.collectionView.itemsAdded[i].divNm + ")");
                return;
            }

            if(nvl($scope.flex.collectionView.itemsAdded[i].deptNm, '').getByteLengthForOracle() > 60){
                // 부서명은 최대 60byte까지 입력 가능합니다. (한글 3byte 그외 1byte)
                $scope._popMsg(messages['empCardInfo.deptNm'] + "은 " + messages["cmm.max60Chk"] + "<br>(부서명 : " + $scope.flex.collectionView.itemsAdded[i].deptNm + ")");
                return;
            }

            if(nvl($scope.flex.collectionView.itemsAdded[i].positionNm, '').getByteLengthForOracle() > 30){
                // 직위명은 최대 30byte까지 입력 가능합니다. (한글 3byte 그외 1byte)
                $scope._popMsg(messages['empCardInfo.positionNm'] + "은 " + messages["cmm.max30Chk"] + "<br>(직위명 : " + $scope.flex.collectionView.itemsAdded[i].positionNm + ")");
                return;
            }

            // 사원카드번호 중복체크를 위함.
            strEmpCardNo += $scope.flex.collectionView.itemsAdded[i].employeeCardNo + ",";
        }

        // 사원카드정보 수정만 했을때
        if(strEmpCardNo === ""){
            
            // 저장
            $scope.saveRow();

        } else {

            // 사원카드번호 중복체크
            var params = {};
            params.employeeCardNo = strEmpCardNo.substr(0, strEmpCardNo.length - 1);

            $scope._postJSONQuery.withPopUp( "/base/store/emp/cardInfo/getChkEmpCardNo.sb", params, function(response){
                var list = response.data.data.list;

                if(list.length > 0) { //  중복

                    var duplicateCardNo = "";
                    for(var i=0; i< list.length; i++){
                        duplicateCardNo += list[i].employeeCardNo + ", ";
                    }

                    $scope._popMsg(messages["empCardInfo.empCardNoDuplicate.msg"] + "<br>(" + duplicateCardNo.substr(0, duplicateCardNo.length - 2) + ")"); // 중복된 사원카드번호 입니다.
                    return;

                }else{

                    // 저장
                    $scope.saveRow();
                }
            });
        }
    };

    // 저장
    $scope.saveRow = function (){

        var params = [];

        for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
            $scope.flex.collectionView.itemsEdited[i].status = "U";
            params.push($scope.flex.collectionView.itemsEdited[i]);
        }

        for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
            $scope.flex.collectionView.itemsAdded[i].status = "I";
            params.push($scope.flex.collectionView.itemsAdded[i]);
        }

        // 삭제기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save('/base/store/emp/cardInfo/saveEmpCardInfo.sb', params, function() {
            $scope._broadcast('empCardInfoCtrl');
        });
    };

    // 엑셀업로드
    $scope.excelUpload = function () {

        var vScope = agrid.getScope('excelUploadEmpCardInfoCtrl');
        var msg = messages["empCardInfo.confmMsg"];  // 정상업로드 된 데이터는 자동저장됩니다. 업로드 하시겠습니까?

        s_alert.popConf(msg, function () {

            /* 부모컨트롤러 값을 넣으면 업로드가 완료된 후 uploadCallBack 이라는 함수를 호출해준다. */
            vScope.parentCtrl = 'empCardInfoCtrl';

            $("#excelUpFile").val('');
            $("#excelUpFile").trigger('click');

        });
    };

    /** 업로드 완료 후 callback 함수. 업로드 이후 로직 작성. */
    $scope.uploadCallBack = function () {
        $scope._pageView('empCardInfoCtrl', 1);
    };

    // 양식 다운로드
    $scope.excelDownload = function () {
        var vScope = agrid.getScope('excelUploadEmpCardInfoCtrl');
        vScope.excelFormDownload();
    };

    // 엑셀 데이터 다운로드
    $scope.excelDataDownload = function () {
        if($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
            return false;
        }
        
        // 엑셀 데이터에 체크박스 컬럼은 안나오도록 숨김
        $scope.flex.columns[0].visible = false;

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
        $timeout(function () {
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
                includeColumnHeaders: true,
                includeCellStyles   : true,
                includeColumns      : function (column) {
                    return column.visible;
                }
            }, '사원카드정보_' + getToday() + '.xlsx', function () {
                $timeout(function () {
                    $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                    // 체크박스 컬럼 다시 보이게 처리
                    $scope.flex.columns[0].visible = true;
                }, 10);
            });
        }, 10);

    };
    
}]);