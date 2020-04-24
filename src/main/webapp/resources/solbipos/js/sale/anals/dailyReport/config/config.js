/****************************************************************
 *
 * 파일명 : config.js
 * 설  명 : 영업일보 JavaScript (매출관리 > 매출분석 > 영업일보 > [영업일보 구성] Tab)
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.02.04     조현수      1.0
 *
 * **************************************************************/

var app = agrid.getApp();	//get application

/*
//조회조건 DropBoxDataMap
var useYnFg         =   [
                            {"name":"전체",       "value":""},
                            {"name":"사용",       "value":"Y"},
                            {"name":"사용안함",    "value":"N"}
                        ];
// 사용여부 DropBoxDataMap
var useYnFgDataMap = new wijmo.grid.DataMap([   {id: "",    name: "전체"   },
                                                {id: "Y",   name: "사용"   },
                                                {id: "N",   name: "사용안함"}
                                            ], 'id', 'name');
*/


//configCtrl_1	START		##########################################################################################################################
app.controller('configCtrl_1', ['$scope', '$http', function ($scope, $http) {
    angular.extend(this, new RootController('configCtrl_1', $scope, $http, true));	//상위 객체 상속 : T/F 는 picker

    //$scope._setComboData("srchUseYnFg", useYnFg);									//조회조건 콤보박스 데이터 Set

    var global_maxPayLineCnt = 3;	//최대 결재라인 갯수

    //가맹점인 경우, [결재라인] [영업일보 구성] 조회	START	----------------------------------------------------------------------------------------------
    var scope_reportCtrl = agrid.getScope('reportCtrl');
    //DEBUG	console.log("configCtrl_1 > scope_reportCtrl.orgnFg: " + scope_reportCtrl.orgnFg);

	if(scope_reportCtrl.orgnFg != "H") {	//H:본사, S:가맹점, M:?? -> 매장이 'M'으로 보이는 경우도 있음
        ///*
	    var params = {};
        	params.searchStoreCd   	= $("#reportSelectStoreCd").val();

        //$scope.$broadcast('loadingPopupActive', messages["cmm.progress"]);	//cmm.progress=데이터 처리 중입니다.
	    $scope._postJSONQuery.withOutPopUp	(	"/sale/anals/dailyReport/config/list.sb",	//영업일보 구성 조회
					        					params,
					        					function(response)	{
																		//데이터 setting
															            var configCtrl_1	= agrid.getScope('configCtrl_1');
															            var configCtrl_2 	= agrid.getScope('configCtrl_2');
															            configCtrl_1.flex.itemsSource = response.data.data.payline;	//결재라인
															            configCtrl_2.flex.itemsSource = response.data.data.cfg;		//영업일보 구성

															            //[영업일보 구성] check-box setting
															            for(var i=0; i<configCtrl_2.flex.collectionView.items.length; i++){
															                var item = configCtrl_2.flex.collectionView.items[i];
															            	if(item.cfgSelYn == "Y")	item.gChk = true;
															            }

															            //tracking 가능하게 함
															            $scope.flex.collectionView.trackChanges = true;

															            //$scope.$broadcast('loadingPopupInactive');
																	},	//callBack function
												false);
		//*/
		//$scope.getConfigList();	//왜 오류발생??	TypeError: $scope.getConfigList is not a function

	    event.preventDefault();	//기능수행 종료 (반드시 추가해야 함)

	    //[버튼] 보이기
	    $("#btnAdd"	).show();
	    $("#btnDel"	).show();
	    $("#btnSave").show();
	}
	//가맹점인 경우, [결재라인] [영업일보 구성] 조회	END	----------------------------------------------------------------------------------------------



	//[결재라인] [영업일보 구성] 조회	START	------------------------------------------------------------------------------------------------------------------
	$scope.getConfigList = function(data){
	    var params = {};
        	params.searchStoreCd = $("#reportSelectStoreCd").val();

        //$scope.$broadcast('loadingPopupActive', messages["cmm.progress"]);	//cmm.progress=데이터 처리 중입니다.
	    $scope._postJSONQuery.withOutPopUp	(	"/sale/anals/dailyReport/config/list.sb",	//영업일보 구성 조회
					        					params,
					        					function(response)	{
																		//데이터 setting
															            var configCtrl_1	= agrid.getScope('configCtrl_1');
															            var configCtrl_2 	= agrid.getScope('configCtrl_2');
															            configCtrl_1.flex.itemsSource = response.data.data.payline;	//결재라인
															            configCtrl_2.flex.itemsSource = response.data.data.cfg;		//영업일보 구성

															            //[영업일보 구성] check-box setting
															            for(var i=0; i<configCtrl_2.flex.collectionView.items.length; i++){
															                var item = configCtrl_2.flex.collectionView.items[i];
															            	if(item.cfgSelYn == "Y")	item.gChk = true;
															            }
															            //$scope.$broadcast('loadingPopupInactive');
																	},	//callBack function
												false);
	};
	//[결재라인] [영업일보 구성] 조회	END		------------------------------------------------------------------------------------------------------------------


    //Grid 초기화  START   --------------------------------------------------------------------------------------------------------------------------
    $scope.initGrid = function (s, e) {
        $scope._makePickColumns("configCtrl_1");   	//picker 사용시 호출 : 미사용시 호출안함

        //$scope.useYnFgDataMap = useYnFgDataMap;     //그리드 내 콤보박스 설정

        s.formatItem.addHandler(function (s, e) {   //ReadOnly 효과설정
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                if (col.binding === "nmcodeCd") {
                    var item = s.rows[e.row].dataItem;
                    if (item.status !== "I") {
                        wijmo.addClass(e.cell, 'wijLink');
                        wijmo.addClass(e.cell, 'wj-custom-readonly');
                    } else {
                        wijmo.removeClass(e.cell, 'wj-custom-readonly');
                    }
                }
            }
        });

        /*
        s.beginningEdit.addHandler(function (sender, elements) {     //Grid cell 편집 방지
            var col = sender.columns[elements.col];
            if (col.binding === "cfgPayLineSeq") {
                var dataItem = s.rows[elements.row].dataItem;
                if (nvl(dataItem.status, "") === "" && dataItem.status !== "I") {
                    elements.cancel = true;
                }
            }
        });
        */
		/*
        s.hostElement.addEventListener('mousedown', function(e) {   //대표명칭 그리드 선택 이벤트
            var ht = s.hitTest(e);
            if( ht.cellType === wijmo.grid.CellType.Cell) {
                var selectedRow = s.rows[ht.row].dataItem
                var col = ht.panel.columns[ht.col];
                if( col.binding === "nmcodeCd" && selectedRow.status !== "I") {
                    $scope._broadcast('detailCtrl', selectedRow.nmcodeCd);
                }
            }
        });
        */
    };
    //Grid 초기화 END      --------------------------------------------------------------------------------------------------------------------------



    //Grid 조회   START   --------------------------------------------------------------------------------------------------------------------------
    /*
    $scope.$on("configCtrl_1", function(event, data) {
        var params = {};
            params.nmcodeGrpCd = "000";
            params.useYn = $scope.useYnFg;

        $scope._inquiryMain("/adi/etc/cd/cd/list.sb", params, function() {  //조회 수행 : 조회URL, 파라미터, 콜백함수
            $("#btnAdd"    ).show();   //대표명칭 그리드 버튼 show
            $("#btnDel"    ).show();
            $("#btnSave"   ).show();
        });

        event.preventDefault();	//기능수행 종료 (반드시 추가해야 함)
    });
    */
    //Grid 조회   END     --------------------------------------------------------------------------------------------------------------------------



    //Grid 행 주가 START   --------------------------------------------------------------------------------------------------------------------------
    $scope.addRow = function() {
    	var rowCnt_Total 	= $scope.flex.rows.length;	//전체건수(보이는것 + 삭제된 안보이는 것)
    	var rowCnt_Deleted	= 0;						//삭제건수

        for(var i=0; i<$scope.flex.collectionView.items.length; i++){
            var item = $scope.flex.collectionView.items[i];

            if(item.cfgStatus == 'D')
            	rowCnt_Deleted++;
        }

/*
    	if( (rowCnt_Total - rowCnt_Deleted) == global_maxPayLineCnt){
    		//$scope._popMsg(messages["dailyReport.alert.check.cfgPayLineMaxCnt"]);	//dailyReport.alert.check.cfgPayLineMaxCnt=결재라인은 최대갯수는 {0}입니다.
    		//s_alert.pop("<s:message code='dailyReport.alert.check.cfgPayLineMaxCnt' arguments='" + global_maxPayLineCnt + "'/>");	//dailyReport.alert.check.cfgPayLineMaxCnt=결재라인은 최대갯수는 {0}입니다.
    		$scope._popMsg(messages["dailyReport.alert.check.cfgPayLineMaxCnt"]);

            return false;
    	}
*/

    	//HQ_OFFICE_CD, HQ_BRAND_CD, STORE_CD 구하기 - [영업일보 구성] Grid에서 조회함
    	var configCtrl_2 			= agrid.getScope('configCtrl_2');
        var item 					= configCtrl_2.flex.collectionView.items[0];

        var params 					= {};
	        params.cfgPayLineSeq	= rowCnt_Total - rowCnt_Deleted + 1;
	        params.cfgUseYn			= "Y";
	        params.cfgHqOfficeCd	= item.cfgHqOfficeCd;
	        params.cfgHqBrandCd		= item.cfgHqBrandCd;
	        params.cfgStoreCd		= item.cfgStoreCd;
	        params.cfgStatus		= "I";	//상태 (I:삽입, U:수정, D:삭제)

        $scope._addRow(params);
    };
    //Grid 행 주가 END     --------------------------------------------------------------------------------------------------------------------------



    //Grid 저장   START   --------------------------------------------------------------------------------------------------------------------------
    $scope.save = function() {
    	if($scope.flex.rows.length == 0){
    		$scope._popMsg(messages["dailyReport.alert.require.cfgPayLine"]);	//dailyReport.alert.require.cfgPayLine	=[결재라인]을 입력하십시오.
            return false;
    	}

    	/*
    		'cfgStatus'칼럼을 추가하여 이 값으로 처리구분을 한다 -> I:삽입 & D:삭제 ('I' or 'D' 가 아닌 경우에는 Update)

				[추가]후 저장없이 [삭제]한 row는 -> (Grid에서 기본 제공하는 status)인 status: I 이기에
				                                                        기존 타소스에서 사용하는 방식을 이용하면, 화면에 보이지 않는 데이터임에도 Insert 로직이 호출됨. 문제 발생함.

				여기서 이용하는 방식은 cfgStatus로 구분해서 처리하고
				[추가]후 저장없이 [삭제]한 row는 'cfgStatus=D' & 'cfgPayLineNo'값은 없기에, 해당건은 제외한 후 [save.sb] 호출하면 됨.
    	*/
        var params = new Array();
        for(var i=0; i<$scope.flex.collectionView.items.length; i++){
            var item = $scope.flex.collectionView.items[i];

            //추가행 check > [명칭] 입력 확인
            if(item.cfgStatus == 'I'   &&   (item.cfgPayLineNm === undefined || item.cfgPayLineNm.length === 0) ){
                $scope._popMsg(messages["dailyReport.alert.require.cfgPayLineNm"]); 	//dailyReport.alert.require.cfgPayLineNm	=결재라인 [명칭]을 입력해 주십시오.
                return false;
            }
            //DEBUG	console.log('[' + (i+1) + ']: ' + item.cfgStatus);
            if( item.cfgStatus == 'D'   &&   (item.cfgPayLineNo === undefined || item.cfgPayLineNo.length === 0) )	{}	//[추가]후 저장없이 [삭제]한 row는 제외
            else																									{	params.push(item);	}
        }
        //DEBUG	console.log("params: " + JSON.stringify(params));

      //$scope._save("/sale/anals/dailyReport/report/save.sb", params);	//저장(URL, parameter, callback function)
        $scope._save("/sale/anals/dailyReport/report/save.sb", params, function(){$scope.getConfigList()});	//저장(URL, parameter, callback function)
    }
    /*
    $scope.save_XXX = function() {
    	if($scope.flex.rows.length == 0){
    		$scope._popMsg(messages["dailyReport.alert.require.cfgPayLine"]);	//dailyReport.alert.require.cfgPayLine	=[결재라인]을 입력하십시오.
            return false;
    	}

        //파라미터 설정
        var params = new Array();

        for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
            var item = $scope.flex.collectionView.itemsAdded[i];
            if(item.cfgPayLineSeq === undefined || item.cfgPayLineSeq.length === 0){
                $scope._popMsg(messages["dailyReport.alert.require.cfgPayLineSeq"]);	//dailyReport.alert.require.cfgPayLineSeq	=결재라인 [순번]을 입력해 주십시오.
                return false;
            }
            if(item.cfgPayLineNm === undefined || item.cfgPayLineNm.length === 0){
                $scope._popMsg(messages["dailyReport.alert.require.cfgPayLineNm"]); 	//dailyReport.alert.require.cfgPayLineNm	=결재라인 [명칭]을 입력해 주십시오.
                return false;
            }
            $scope.flex.collectionView.itemsAdded[i].status = "I";
            params.push($scope.flex.collectionView.itemsAdded[i]);
        }

        for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
            $scope.flex.collectionView.itemsEdited[i].status = "U";
            params.push($scope.flex.collectionView.itemsEdited[i]);
        }

        for (var i = 0; i < $scope.flex.collectionView.itemsRemoved.length; i++) {
            $scope.flex.collectionView.itemsRemoved[i].status = "D";
            params.push($scope.flex.collectionView.itemsRemoved[i]);
        }

        console.log("params: " + JSON.stringify(params));

      //$scope._save("/sale/anals/dailyReport/report/save.sb", params);	//저장(URL, parameter, callback function)
        $scope._save("/sale/anals/dailyReport/report/save.sb", params, function(){$scope.getConfigList()});	//저장(URL, parameter, callback function)
    }
    */
    //Grid 저장   END     --------------------------------------------------------------------------------------------------------------------------



    //Grid 행 삭제 START   --------------------------------------------------------------------------------------------------------------------------
    $scope.deleteRow = function() {
        var grid = wijmo.Control.getControl("#grid_payline");

        for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ){
            var item = $scope.flex.collectionView.items[i];
            if(item.gChk){
            	//현재 새로 추가된 행'만 tracking이 가능하다. 조회 후 변경/삭제 하는 것은 tracking이 안됨.
            	//removeAt을 사용하면 tracking이 안된다. tracking 가능한 함수 (editItem/commitEdit, addNew/commitNew, and remove)
            	//	공통에서는 '행추가'인 경우 가능( _addRow > _gridAddRow > flex.collectionView.addNew() )
            	//	https://www.grapecity.com/wijmo/api/classes/wijmo.collectionview.html#trackchanges

            	// --> remove를 사용해도 tracking 되지 않음. [삭제]만 하고 저장시 변경사항이 없는 것으로 인식함 ('변경 사항이 없습니다')
            	//		tracking되게 하려면 remove도 공통함수로 처리해야 하고, 그 함수 내에 'flex.collectionView.trackChanges = true;'를 해주어야 함.
            	// -->
            	//		그래서  삭제된 row는 Grid상에서 실제로 삭제하지 않고, 단지 보여주지 않는(visible=false)형태로 개발함.

            	//$scope.flex.collectionView.remove(item);

            	//DEBUG	console.log("deleteRow: " + item.cfgPayLineSeq);
            	item.cfgUseYn 			= 'N';
            	item.cfgStatus			= "D";	//상태 (I:삽입, U:수정, D:삭제)
            	grid.rows[i].visible 	= false;
            }
        }

        //[순번] 재정렬
        var rowCnt = $scope.flex.rows.length;
        var rowSeq = 1;	//순번
        for(var i=0; i<rowCnt; i++){			//[삭제]되어서 보이지 않는 row 제외하면서 '순번' 재정렬
        	if(grid.rows[i].visible == true){
        		$scope.flex.setCellData(i, 1, rowSeq);
        		rowSeq++;
        	}
        }
    }

    $scope.deleteRow_XXX = function() {
        for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ){
            var item = $scope.flex.collectionView.items[i];
            if(item.gChk){
            	/*
                if(item.cnt > 0){
                    $scope._popMsg("oooo은 삭제할 수 없습니다.");
                    return false;
                }
                */
                $scope.flex.collectionView.removeAt(i);
            }
        }

        //[순번] 재정렬
        var rowCnt = $scope.flex.rows.length;
        for(var i=0; i<rowCnt; i++){
        	$scope.flex.setCellData(i, 1, i + 1);
        }
    }
    //Grid 행 삭제 END     --------------------------------------------------------------------------------------------------------------------------
}]);
//configCtrl_1	END			##########################################################################################################################







//configCtrl_2	START		##########################################################################################################################
app.controller('configCtrl_2', ['$scope', '$http', function ($scope, $http) {
    angular.extend(this, new RootController('configCtrl_2', $scope, $http, true));	//상위 객체 상속 : T/F 는 picker

    //[버튼] 보이기
    var scope_reportCtrl = agrid.getScope('reportCtrl');
    //DEBUG	console.log("configCtrl_2 > scope_reportCtrl.orgnFg: " + scope_reportCtrl.orgnFg);
	if(scope_reportCtrl.orgnFg != "H") {	//H:본사, S:가맹점, M:?? -> 매장이 'M'으로 보이는 경우도 있음
		$("#btnSave2").show();
	}


    //Grid 초기화  START   --------------------------------------------------------------------------------------------------------------------------
    $scope.initGrid = function (s, e) {
        $scope._makePickColumns("configCtrl_2");   //picker 사용시 호출 : 미사용시 호출안함

        //$scope.useYnFgDataMap = useYnFgDataMap;     //그리드 내 콤보박스 설정

        s.formatItem.addHandler(function (s, e) {   //ReadOnly 효과설정
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                if (col.binding === "nmcodeCd") {
                    var item = s.rows[e.row].dataItem;
                    if (item.status !== "I") {
                        wijmo.addClass(e.cell, 'wijLink');
                        wijmo.addClass(e.cell, 'wj-custom-readonly');
                    } else {
                        wijmo.removeClass(e.cell, 'wj-custom-readonly');
                    }
                }
            }
        });

        s.beginningEdit.addHandler(function (sender, elements) {     //대표명칭 그리드 에디팅 방지
            var col = sender.columns[elements.col];
            if (col.binding === "nmcodeCd") {
                var dataItem = s.rows[elements.row].dataItem;
                if (nvl(dataItem.status, "") === "" && dataItem.status !== "I") {
                    elements.cancel = true;
                }
            }
        });
        /*
        s.hostElement.addEventListener('mousedown', function(e) {   //대표명칭 그리드 선택 이벤트
            var ht = s.hitTest(e);
            if( ht.cellType === wijmo.grid.CellType.Cell) {
                var selectedRow = s.rows[ht.row].dataItem
                var col = ht.panel.columns[ht.col];
                if( col.binding === "nmcodeCd" && selectedRow.status !== "I") {
                    $scope._broadcast('detailCtrl', selectedRow.nmcodeCd);
                }
            }
        });
        */
    };
    //Grid 초기화 END      --------------------------------------------------------------------------------------------------------------------------



    //Grid 조회   START   --------------------------------------------------------------------------------------------------------------------------
    /*
    $scope.$on("configCtrl_2", function(event, data) {
        var params = {};
            params.nmcodeGrpCd = "000";
            params.useYn = $scope.useYnFg;

        $scope._inquiryMain("/adi/etc/cd/cd/list.sb", params, function() {  //조회 수행 : 조회URL, 파라미터, 콜백함수
            $("#btnAdd"    ).show();   //대표명칭 그리드 버튼 show
            $("#btnDel"    ).show();
            $("#btnSave"   ).show();
        });

        event.preventDefault();	//기능수행 종료 (반드시 추가해야 함)
    });
    */
    //Grid 조회   END     --------------------------------------------------------------------------------------------------------------------------



    //Grid 저장   START   --------------------------------------------------------------------------------------------------------------------------
    $scope.save = function() {
        var params = new Array();

        for(var i=0; i<$scope.flex.collectionView.items.length; i++){
            var item = $scope.flex.collectionView.items[i];

            //[매출종합]은 무조건 'checked'
            if(item.cfgCd == "SL"   &&   item.gChk == false){
            	$scope._popMsg(messages["dailyReport.alert.require.cfgMandatory"]);	//[매출영업]은 필수항목입니다.
            	return false;
            }

        	if(item.gChk)	item.cfgSelYn = "Y";
        	else			item.cfgSelYn = "N";

        	params.push( $scope.flex.collectionView.items[i] );
        }

      //DEBUG	console.log("params: " + JSON.stringify(params));
        $scope._save("/sale/anals/dailyReport/config/save.sb", params);	//저장(URL, parameter, callback function)
    }
    //Grid 저장   END     --------------------------------------------------------------------------------------------------------------------------


}]);
//configCtrl_2	END			##########################################################################################################################
