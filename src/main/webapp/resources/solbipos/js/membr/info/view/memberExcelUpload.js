/****************************************************************
 *
 * 파일명 : memberExcelUpload.js
 * 설  명 : 회원엑셀업로드 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.06.23    Daniel      1.0
 * 2020.10.14    tyoh        1.1
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  회원엑셀업로드
 */
app.controller('memberExcelUploadCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('memberExcelUploadCtrl', $scope, $http, '$timeout', true));
    // 성공내역, 실페내역
    $scope.statusList = [
        {value: '1', name: '전체'},
        {value: '2', name: '성공내역'},
        {value: '3', name: '오류내역'}
    ];
    $scope.status = $scope.statusList[0];
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
    // 중복체크(회원명, 전화번호, 카드번호) 여부
    $scope.isCheckedMembr = false;

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.useYnDataMap = new wijmo.grid.DataMap(useDataMap, 'value', 'name');
        $scope.recvDataMap = new wijmo.grid.DataMap(recvDataMap, 'value', 'name');
        $scope.regstrStoreList = new wijmo.grid.DataMap(regstrStoreList, 'value', 'name');
        $scope.memberClassList = new wijmo.grid.DataMap(memberClassList, 'value', 'name');
        $scope.genderDataMap = new wijmo.grid.DataMap(genderDataMap, 'value', 'name');
        $scope.weddingDataMap = new wijmo.grid.DataMap(weddingDataMap, 'value', 'name');

        $scope.isEdited = false;
        s.cellEditEnded.addHandler(function (s, e) {
            if (e.panel === s.cells) {
              var col = s.columns[e.col];
              var dataItem = s.rows[e.row].dataItem;
              if (col.binding !== "gChk") {
                  $scope.isEdited = true;
                  dataItem.result = "양식검증 필요";
              }
            }
        });

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
                // 검증결과
                if (col.binding === "result") {
                    // 값이 있으면 링크 효과
                    if (item[("result")] !== '검증성공') {
                        wijmo.addClass(e.cell, 'wij_gridText-red');
                        wijmo.addClass(e.cell, 'wj-custom-readonly');
                    }
                } /*else if (col.binding === "memberTelNo") {
                    if(item.memberTelNo === undefined || item.memberTelNo === ""){
                        item.memberTelNo = '01000000000';
                    }
                }*/
            }
        });
    };

    $scope.initGrid1 = function (s, e) {
        $scope.useYnDataMap = new wijmo.grid.DataMap(useDataMap, 'value', 'name');
        $scope.recvDataMap = new wijmo.grid.DataMap(recvDataMap, 'value', 'name');
        $scope.regstrStoreList = new wijmo.grid.DataMap(regstrStoreList, 'value', 'name');
        $scope.memberClassList = new wijmo.grid.DataMap(memberClassList, 'value', 'name');
        $scope.genderDataMap = new wijmo.grid.DataMap(genderDataMap, 'value', 'name');
        $scope.weddingDataMap = new wijmo.grid.DataMap(weddingDataMap, 'value', 'name');
        $scope.rMembrcardList = new wijmo.grid.DataMap(rMembrcardList, 'value', 'name');

        // row 추가
        var flex1 = $scope.flex1;
        if (!flex1.collectionView) {
            flex1.itemsSource = new wijmo.collections.CollectionView();
        }
        var newRow = flex1.collectionView.addNew();

        // 파라미터 설정
        var params = {};
        // 단독매장
        if (hqOfficeCd === "00000") {
            for (var j in regstrStoreList) {
                if (regstrStoreList[j].value === storeCd) {
                    regstrStoreList = [{name: regstrStoreList[j].name, value: regstrStoreList[j].value}]
                    break;
                }
            }
        } else {
            // 매장
            if (orgnFg !== "HQ") {
                for (var j in regstrStoreList) {
                    if (regstrStoreList[j].value === storeCd) {
                        regstrStoreList = [{name: regstrStoreList[j].name, value: regstrStoreList[j].value}]
                        break;
                    }
                }
            }
        }
        params.comboClass = memberClassList[0].name; // 회원등급분류 첫번째 값
        params.comboStore = regstrStoreList[0].name; // 등록매장 첫번째 값
        params.comboGendr = 'M'; // 남
        params.comboWedding = 'N'; // 미혼
        params.comboCardUse = rMembrcardList[0].name; // 카드사용구분 첫번째 값
        params.comboEmail = 'N'; // 미수신
        params.comboSms = 'N'; // 미수신
        // $scope._addRow(params);

        newRow.status = 'I';
        newRow.gChk = true;
        for (var prop in params) {
            newRow[prop] = params[prop];
        }
        $scope.flex1.collectionView.trackChanges = true;
        $scope.flex1.collectionView.commitNew();

        document.getElementById('btnSearch').addEventListener('click', function (e) {
            if ($scope.flex.collectionView === undefined) {
                $scope._popMsg(messages["cmm.empty.data"]); // 조회 데이터가 없습니다.
                return false;
            }
            $scope.flex.collectionView.filter = function (item) {
                if ($scope.status === "2") {
                    return item.result === "검증성공"
                } else if ($scope.status === "3") {
                    return item.result !== "검증성공"
                } else {
                    return item
                }
            };
            if ($scope.flex.collectionView.items.length === 0) {
                $scope._popMsg(messages["cmm.empty.data"]); // 조회 데이터가 없습니다.
            }
            if ($scope.flex.collectionView._src.length === 0) {
                $scope._popMsg(messages["cmm.empty.data"]); // 조회 데이터가 없습니다.
                return false;
            }
        });
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

                    // 사용자 행위 기록
                    var actParams = {};
                    actParams.resrceCd = menuCd;
                    actParams.pathNm = "회원관리-회원정보-회원엑셀업로드";
                    actParams.contents = "[편집화면 엑셀 다운로드] 버튼 클릭 시";
                    
                    $scope._postJSONSave.withOutPopUp("/common/method/saveUserAct.sb", actParams, function(response){});

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
            if ($scope.flex.rows.length > 0) {
                var msg = messages["member.excel.upload.confirm.msg"]; // 편집중인 자료가 모두 삭제됩니다. 계속하시겠습니까?
                $scope._popConfirm(msg, function () {
                    excelUploadScope.uploadFg = uploadFg;
                    // /** 부모컨트롤러 값을 넣으면 업로드가 완료된 후 uploadCallBack 이라는 함수를 호출해준다. */
                    // excelUploadScope.parentCtrl = 'memberExcelUploadCtrl';
                    // 엑셀 업로드
                    $("#memberExcelUpload").val('');
                    $("#memberExcelUpload").trigger('click');
                });
            } else {
                $("#memberExcelUpload").val('');
                $("#memberExcelUpload").trigger('click');
            }
        }
    };

    // 양식검증
    $scope.formChk = function () {
        var scope = agrid.getScope('excelUploadCtrl');
        if ($scope.flex.rows.length <= 0) {
            var msg = messages["member.excel.upload.no.validate.data"]; // 검증할 데이터가 없습니다.
            $scope._popMsg(msg);
            return false;
        }
        var jsonData = $scope.flex.collectionView.items;
        scope.valChk(jsonData, $scope.isCheckedMembr);
        $scope.flex.collectionView.commitEdit();
        $scope.flex.collectionView.refresh();
        $scope.isEdited = false;
    };

    // 체크박스 체크된 항목 삭제
    $scope.deleteUpload = function () {
        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["cmm.empty.data"]); // 조회 데이터가 없습니다.
            return false;
        } else {
            var chkCnt = 0;
            var msg = messages["cmm.choo.delete"]; // 삭제 하시겠습니까?
            $scope._popConfirm(msg, function () {
                for (var i = $scope.flex.collectionView.items.length - 1; i >= 0; i--) {
                    var item = $scope.flex.collectionView.items[i];
                    if (item.gChk) {
                        chkCnt++;
                        $scope.flex.collectionView.removeAt(i);
                    }
                }
                if (chkCnt === 0) {
                    $scope._popMsg(messages["cmm.not.select"]); // 선택된 데이터가 없습니다.
                } else {
                    $scope._popMsg(messages["cmm.delSucc"]); // 삭제 되었습니다.
                }
            });
        }
    };

    // 저장
    $scope.save = function () {
        $scope.stepCnt = 10;    // 한번에 DB에 저장할 숫자 세팅
        $scope.progressCnt = 0; // 처리된 숫자

        $scope.flex.collectionView.commitEdit();

        if ($scope.flex.rows.length <= 0) {
            var msg = messages["outstockReqDate.not.save"]; // 저장할 내용이 없습니다.
            $scope._popMsg(msg);
            return false;
        }
        if ($scope.isEdited) {
            $scope._popMsg(messages["cmm.excel.edit.chk"]); // 수정한 내역이 있습니다.<br>양식 검증 처리후 저장하십시오.
            return false;
        }
        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            var item = $scope.flex.collectionView.items[i];
            item.membrClassCd = selectValueFromName(memberClassList, item.membrClassCd);
            getStoreLoop:
            for (var j in regstrStoreList) {
                if (regstrStoreList[j].name === item.membrStore) {
                    item.membrStore = regstrStoreList[j].value;
                    item.regStoreCd = regstrStoreList[j].value;
                    break getStoreLoop;
                }
                if (regstrStoreList[j].value === item.membrStore) {
                    item.membrStore = regstrStoreList[j].value;
                    item.regStoreCd = regstrStoreList[j].value;
                    break getStoreLoop;
                }
            }
            item.gendrFg = selectValueFromName(genderDataMap, item.gendrFg);
            item.weddingYn = selectValueFromName(weddingDataMap, item.weddingYn);
            item.emailRecvYn = nvl(selectValueFromName(recvDataMap, item.emailRecvYn), 'N');
            item.smsRecvYn = nvl(selectValueFromName(recvDataMap, item.smsRecvYn), 'N');
            item.totAdjPoint = nvl(item.totSavePoint, 0);

            $scope.flex.collectionView.items[i].status = "I";

            if (item.result === "검증성공") {
                params.push($scope.flex.collectionView.items[i]);
            }
        }
        if (params.length < 1) {
            var msg = messages["outstockReqDate.not.save"]; // 저장할 내용이 없습니다.
            $scope._popMsg(msg);
            return false;
        }

        $timeout(function () {
            setTimeout(function () {
                // 저장
                $scope.save2(params);
            }, 500);
        }, 10);
    };

    // 저장
    $scope.save2 = function(orgParams) {
        $scope.totalRows = orgParams.length;    // 체크수
        var params = [];

        // 저장 시작이면 작업내역 로딩 팝업 오픈
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
            params.push(orgParams[i]);
        }

        console.log("총 갯수 :" + $scope.totalRows);
        console.log("진행 갯수 :" + $scope.progressCnt + "~" + (loopCnt - 1));
        console.log("---------------------------------------------------------------------");

        //가상로그인 session 설정
        var sParam = {};
        if(document.getElementsByName('sessionId')[0]){
            sParam['sid'] = document.getElementsByName('sessionId')[0].value;
        }

        // ajax 통신 설정
        $http({
            method : 'POST', //방식
            url    : '/membr/info/upload/excel/memberExcelSave.sb', /* 통신할 URL */
            data   : params, /* 파라메터로 보낼 데이터 : @requestBody */
            params : sParam,
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        }).then(function successCallback(response) {
            if ($scope._httpStatusCheck(response, true)) {
                if (parseInt($scope.progressCnt) >= parseInt($scope.totalRows)) {
                    // 작업내역 로딩 팝업 닫기
                    $scope.excelUploadingPopup(false);
                    // $scope._gridDataInit();
                    // 저장되었습니다.
                    $scope._popMsg(messages["cmm.saveSucc"]);
                    for ( var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ) {
                        var item = $scope.flex.collectionView.items[i];
                        if (item.result === "검증성공") {
                            $scope.flex.collectionView.removeAt(i);
                        }
                    }

                    // 사용자 행위 기록
                    var actParams = {};
                    actParams.resrceCd = menuCd;
                    actParams.pathNm = "회원관리-회원정보-회원엑셀업로드";
                    actParams.contents = "양식검증 완료 후 [저장] 버튼 클릭 시";

                    $scope._postJSONSave.withOutPopUp("/common/method/saveUserAct.sb", actParams, function(response){});
                }
            }
        }, function errorCallback(response) {
            $scope.excelUploadingPopup(false); // 작업내역 로딩 팝업 닫기
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
                $scope.save2(orgParams);
            }
        });
    };

    // 작업내역 로딩 팝업
    $scope.excelUploadingPopup = function (showFg) {
        if (showFg) {
            // 팝업내용 동적 생성
            var innerHtml = '<div class=\"wj-popup-loading\"><p class=\"bk\">' + messages['touchKey.loading.msg'] + '</p>';
            innerHtml += '<div class="mt5 txtIn"><span class="bk" id="progressCnt">0</span>/<span class="bk" id="totalRows">0</span> 개 진행 중...</div>';
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

function selectValueFromName(wijmoGridDataMapList, itemValue) {
    var value = itemValue
    for (var i in wijmoGridDataMapList) {
        if (wijmoGridDataMapList[i].name === itemValue) {
            value = wijmoGridDataMapList[i].value;
            break;
        }
    }
    return value;
}