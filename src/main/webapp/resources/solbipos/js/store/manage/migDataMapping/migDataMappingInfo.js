/****************************************************************
 *
 * 파일명 : migDataMappingInfo.js
 * 설  명 : OKPOS-KCP 데이터 이관 신규등록 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.07.16     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  OKPOS-KCP 매장 그리드 생성
 */
app.controller('migDataMappingInfoCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('migDataMappingInfoCtrl', $scope, $http, true));

    // 관리자의 경우, 모든 본사(데모까지) 나오고, 총판의 경우, 자기가 관리하는 본사만 나오도록
    if(orgnFg === "AGENCY") {
        $scope._setComboData("envHqOfficeCd", authHqList);
    } else {
        $scope._setComboData("envHqOfficeCd", hqList);
    }

    // 매장선택
    $scope.envStoreCdVal;
    $scope.setEnvStoreCdVal = function(s){
        $scope.envStoreCdVal = s.selectedValue;
    };
    $scope.getEnvStoreCdVal = function(){
        return $scope.envStoreCdVal;
    };

    // [추가설정] 복사할 본사 클릭시, 해당 본사의 매장 목록 조회
    $scope.envHqOfficeCdVal;
    $scope.setEnvHqOfficeCdVal = function(s,e){

        $scope.envHqOfficeCdVal = s.selectedValue;

        if( isNull(s.selectedValue) ) {
            return false;
        }

        var params = {};
        params.hqOfficeCd = s.selectedValue;

        $scope._postJSONQuery.withOutPopUp( "/store/manage/storeManage/storeManage/getStoreComboList.sb", params,
            function(response){
                $scope._setComboData("envStoreCd", response.data.data.list);
            }
        );
    };

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
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
    $scope.$on("migDataMappingInfoCtrl", function(event, data) {
        if(data === "clear") {
            $scope.$apply(function() {
                var dtlScope = agrid.getScope('migDataMappingInfoCtrl');
                dtlScope._gridDataInit();
            });
            return false;
        }

        // OKPOS-KCP 사용자정보 조회
        $scope.searchMigDataMappingInfoCheck();
        event.preventDefault();
    });

    // OKPOS-KCP 사용자정보 조회
    $scope.searchMigDataMappingInfoCheck = function(){
        if (isNull($scope.userId)) {
            $scope._popMsg(messages["migDataMappingInfo.userIdBlank"]);
            return false;
        }
        if (isNull($scope.userPwd)) {
            $scope._popMsg(messages["migDataMappingInfo.userPwdBlank"]);
            return false;
        }

        var params = {};
        params.userId = $scope.userId;
        params.userPwd = $scope.userPwd;
        params.corpCd = "";

        $scope._postJSONQuery.withOutPopUp( "/store/manage/migDataMapping/migDataMappingInfo/getOkposUserInfoList.sb", params, function(response){
            if($.isEmptyObject(response.data.data.result)) {
                $scope._popMsg(messages["migDataMappingInfo.userFail"]);
                return false;
            }

            var okposUserInfo = response.data.data.result;
            $scope.okposUserInfo = okposUserInfo;

            params.corpCd = $scope.okposUserInfo.corpCd;

            // OKPOS-KCP 매장 조회
            $scope.searchMigDataMappingInfo(params);
        });
    };

    // OKPOS-KCP 매장 조회
    $scope.searchMigDataMappingInfo = function(data){
        var params = {};
        params.corpCd = data.corpCd;

        $scope._inquiryMain("/store/manage/migDataMapping/migDataMappingInfo/getMigDataMappingInfoList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // <-- 저장 -->
    // 저장
    $("#funcSave").click(function(e){
        if (isNull($scope.userId)) {
            $scope._popMsg(messages["migDataMappingInfo.userIdBlank"]);
            return false;
        }
        if (isNull($scope.userPwd)) {
            $scope._popMsg(messages["migDataMappingInfo.userPwdBlank"]);
            return false;
        }

        // 매장환경복사 체크값이 있을때
        if($("input:checkbox[name='copyChk']:checked").length > 0) {
            // 매장환경 복사할 본사를 선택해주세요.
            var msg = messages["migDataMappingInfo.copyHqOfficeCdBlank"] + messages["cmm.require.select"];
            if( isNull( $scope.envHqOfficeCdVal) ) {
                $scope._popMsg(msg);
                return false;
            }
            // 매장환경 복사할 매장을 선택해주세요.
            var msg = messages["migDataMappingInfo.copyStoreCdBlank"] + messages["cmm.require.select"];
            if( isNull( $scope.envStoreCdVal) ) {
                $scope._popMsg(msg);
                return false;
            }
        }

        var params = {};
        // 매장코드(자동채번)
        $scope._postJSONQuery.withOutPopUp( "/store/manage/migDataMapping/migDataMappingInfo/getMigDataMappingSolbiStoreCdList.sb", params, function(response){
            if($.isEmptyObject(response.data.data.result)) {
                $scope._popMsg(messages["migDataMappingInfo.userFail"]);
                return false;
            }

            var migDataMappingSolbiStoreCd = response.data.data.result;
            $scope.migDataMappingSolbiStoreCd = migDataMappingSolbiStoreCd;

            params.storeCd = $scope.migDataMappingSolbiStoreCd.storeCd;

            // 매장 신규 등록
            $scope.saveSolbiStore(params);
        });
    });

    // 매장 신규 등록
    $scope.saveSolbiStore = function(data){

        function lpad(s, padLength, padString) {
            s = '' + s;
            while (s.length < padLength)
                s = padString + s;
            return s;
        }

        $scope._popConfirm(messages["migDataMappingInfo.saveConfirm"], function() {
            var j = 0;
            for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
                // 파라미터 설정
                var params = {};
                if($scope.flex.collectionView.items[i].gChk) {

                    // OKPOS-KCP 데이터 이관시 매장코드는 F로 시작하게 순차적으로 채번한다. (F000001, F000002...)
                    params.storeCd = "F" + lpad(parseInt(data.storeCd) + j, 6, "0");
                    j++;
                    // 매장 신규 등록을 호출시 매장을 자동채번하기 때문에 수동으로 넣는다고 정의
                    params.storeCdInputType = "1";

                    params.orgnFg = "S";
                    params.pAgencyCd = pAgencyCd;
                    params.storeNm = nvl($scope.flex.collectionView.items[i].storeNm, " ");
                    params.ownerNm = nvl($scope.flex.collectionView.items[i].ownerNm, " ");
                    params.hqOfficeCd = "00000";
                    params.hqOfficeNm = "단독매장";
                    params.hqBrandCd = "0000000";
                    var bizNo = nvl($scope.flex.collectionView.items[i].bizNo, "0000000000");
                    params.bizNo = bizNo;
                    params.bizNo1 = bizNo.substr(0,3);
                    params.bizNo2 = bizNo.substr(3,2);
                    params.bizNo3 = bizNo.substr(5,5);
                    params.bizStoreNm = nvl($scope.flex.collectionView.items[i].storeNm, " ");
                    params.telNo = nvl($scope.flex.collectionView.items[i].telNo, " ");
                    params.postNo = nvl($scope.flex.collectionView.items[i].postNo, " ");
                    params.addr = nvl($scope.flex.collectionView.items[i].addr, " ");
                    params.addrDtl = nvl($scope.flex.collectionView.items[i].addrDtl, " ");
                    params.areaCd = nvl($scope.flex.collectionView.items[i].areaCd, " ");
                    params.clsFg = nvl($scope.flex.collectionView.items[i].clsFg, " ");
                    params.sysStatFg = nvl($scope.flex.collectionView.items[i].sysStatFg, " ");
                    params.sysOpenDate = nvl($scope.flex.collectionView.items[i].sysOpenDate, " ");
                    params.sysClosureDate = "99991231";
                    params.vanCd = "001"; // KCP
                    params.agencyCd = orgnCd;
                    params.sysRemark = "OKPOS 매장 데이터 이관";
                    params.installPosCnt = $scope.flex.collectionView.items[i].installPosCnt;
                    params.copyHqOfficeCd = $scope.copyHqOfficeCd;
                    params.copyStoreCd = $scope.copyStoreCd;
                    params.inFg = "S";
                    var copyChkVal = "";
                    $("input[name=copyChk]:checked").each(function() {
                        copyChkVal += ($(this).val() + "|");
                    });
                    params.copyChkVal = copyChkVal;
                    params.posNo = $scope.flex.collectionView.items[i].posNo;
                    params.userPwd =  "123456";
                    params.posEmpNo =  "0001";
                    params.posUserPwd =  "1234";
                    params.cornerUseYn =  "N";
                    params.storeType =  "02";
                    params.directManageYn =  "Y";

                    $scope.flex.collectionView.items[i].newSoibiStoreCd = params.storeCd;

                    $scope._postJSONSave.withPopUp("/store/manage/storeManage/storeManage/saveStoreInfo.sb", params, function (response) {
                        var result = response.data.data;
                        // params.solbiStoreCd = result;

                        var params_mapping = {};
                        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
                            if($scope.flex.collectionView.items[i].newSoibiStoreCd === result) {
                                params_mapping.solbiStoreCd = $scope.flex.collectionView.items[i].newSoibiStoreCd;
                                params_mapping.okposHqOfficeCd = $scope.flex.collectionView.items[i].hqOfficeCd;
                                params_mapping.okposHqOfficeNm = $scope.flex.collectionView.items[i].hqOfficeNm;
                                params_mapping.okposStoreCd = $scope.flex.collectionView.items[i].storeCd;
                                params_mapping.okposStoreNm = $scope.flex.collectionView.items[i].storeNm;
                                break;
                            }
                        }
                        // TB_MIG_DATA_MAPPING 저장
                        $scope.saveMigDataMapping(params_mapping);

                        $scope._popMsg("저장되었습니다.");
                        $scope.close();
                    });
                }
            }
        });
    };

    // TB_MIG_DATA_MAPPING 저장
    $scope.saveMigDataMapping = function(data){
        var params = {};
        params.solbiStoreCd = data.solbiStoreCd;
        params.okposHqOfficeCd = data.okposHqOfficeCd;
        params.okposHqOfficeNm = data.okposHqOfficeNm;
        params.okposStoreCd = data.okposStoreCd;
        params.okposStoreNm = data.okposStoreNm;

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/store/manage/migDataMapping/migDataMappingInfo/getMigDataMappingInfoSave.sb", params, function(){ });
    };
    // <-- //저장 -->

    // 팝업 닫기
    $scope.close = function(){
        $scope.wjMigDataMappingInfoLayer.hide();

        $scope.userId = "";
        $scope.userPwd = "";
        $scope.okposStoreCd = "";
        $scope.okposStoreNm = "";
        $scope.envHqOfficeCdCombo.selectedIndex = 0;
        $scope.envStoreCdCombo.selectedIndex = 0;
        $("input:checkbox[name='copyChk']:checked").prop("checked", false);

        // var storeScope = agrid.getScope('migDataMappingInfoCtrl');
        // storeScope._gridDataInit();

        // 저장기능 수행후 재조회
        $scope._broadcast('migDataMappingCtrl');
    };
}]);