/****************************************************************
 *
 * 파일명 : saleAnalsMonthlyMoms.js
 * 설  명 : 월력판매분석 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2023.04.13     권지현      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/** 세트재고조정 그리드 controller */
app.controller('saleAnalsMonthlyMomsCtrl', ['$scope', '$http', '$sce', function ($scope, $http, $sce) {

	// 상위 객체 상속 : T/F 는 picker
	angular.extend(this, new RootController('saleAnalsMonthlyMomsCtrl', $scope, $http, false));

	//$scope.reqYearMonth = wcombo.genDateVal("#reqYearMonth", gvStartDate);

	//검색조건에 조회기간
	var startMonth = new wijmo.input.InputDate('#reqYearMonth', {
		format       : "yyyy-MM",
		selectionMode: "2" // 달력 선택 모드(1:day 2:month)
	});

	$scope._setComboData("storeHqBrandCdCombo", momsHqBrandCdComboList); // 매장브랜드
	$scope._setComboData("momsTeamCombo", momsTeamComboList); // 팀별
	$scope._setComboData("momsAcShopCombo", momsAcShopComboList); // AC점포별
	$scope._setComboData("momsAreaFgCombo", momsAreaFgComboList); // 지역구분
	$scope._setComboData("momsCommercialCombo", momsCommercialComboList); // 상권
	$scope._setComboData("momsShopTypeCombo", momsShopTypeComboList); // 점포유형
	$scope._setComboData("momsStoreManageTypeCombo", momsStoreManageTypeComboList); // 매장관리타입
	$scope._setComboData("branchCdCombo", branchCdComboList); // 그룹
	$scope._setComboData("momsStoreFg01Combo", momsStoreFg01ComboList); // 매장그룹
	$scope._setComboData("momsStoreFg02Combo", momsStoreFg02ComboList); // 매장그룹2
	$scope._setComboData("momsStoreFg03Combo", momsStoreFg03ComboList); // 매장그룹3
	$scope._setComboData("momsStoreFg04Combo", momsStoreFg04ComboList); // 매장그룹4
	$scope._setComboData("momsStoreFg05Combo", momsStoreFg05ComboList); // 매장그룹5

	//초기화
	$scope.init = function () {
		$scope.searchSaleAnalsMonthlyMomsList();
		// 기능수행 종료 : 반드시 추가
		event.preventDefault();
	};

	// 다른 컨트롤러의 broadcast 받기
	$scope.$on("saleAnalsMonthlyMomsCtrl", function (event, data) {
		$scope.searchSaleAnalsMonthlyMomsList();
		// 기능수행 종료 : 반드시 추가
		event.preventDefault();
	});

	// 매장선택 모듈 팝업 사용시 정의
	// 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
	// _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
	$scope.saleAnalsMonthlyMomsSelectStoreShow = function () {
		$scope._broadcast('saleAnalsMonthlyMomsSelectStoreCtrl');
	};

	// 확장조회 숨김/보임
	$scope.searchAddShowChange = function(){
		if( $("#tblSearchAddShow").css("display") === 'none') {
			$("#tblSearchAddShow").show();
		} else {
			$("#tblSearchAddShow").hide();
		}
	};

	// 리스트 조회
	$scope.searchSaleAnalsMonthlyMomsList = function () {

		Number.prototype.format = function(){
			if(this==0) return 0;
			var reg = /(^[+-]?\d+)(\d{3})/;
			var n = (this + '');
			while (reg.test(n)) n = n.replace(reg, '$1' + ',' + '$2');
			return n;
		};

		String.prototype.format = function(){
			var num = parseFloat(this);
			if( isNaN(num) ) return "0";
			return num.format();
		};

		$scope.changeDate = function changeDate(day) {
			if (day.substr(0,1) == "0") {
				day = day.substr(1,1);
			}
			return day;
		};
		

		// 파라미터
		var params = {};
		params.reqYearMonth = wijmo.Globalize.format(startMonth.value, 'yyyyMM');
		params.storeCd = $("#saleAnalsMonthlyMomsSelectStoreCd").val();
		params.hqOfficeCd = gvHqOfficeCd;
		params.empNo = empNo;

		params.momsTeam = $scope.momsTeam;
		params.momsAcShop = $scope.momsAcShop;
		params.momsAreaFg = $scope.momsAreaFg;
		params.momsCommercial = $scope.momsCommercial;
		params.momsShopType = $scope.momsShopType;
		params.momsStoreManageType = $scope.momsStoreManageType;
		params.branchCd = $scope.branchCd;
		params.storeHqBrandCd = $scope.storeHqBrandCd;
		// '전체' 일때
		if(params.storeHqBrandCd === "" || params.storeHqBrandCd === null) {
			var momsHqBrandCd = "";
			for(var i=0; i < momsHqBrandCdComboList.length; i++){
				if(momsHqBrandCdComboList[i].value !== null) {
					momsHqBrandCd += momsHqBrandCdComboList[i].value + ","
				}
			}
			params.userBrands = momsHqBrandCd;
		}
		params.momsStoreFg01 = $scope.momsStoreFg01;
		params.momsStoreFg02 = $scope.momsStoreFg02;
		params.momsStoreFg03 = $scope.momsStoreFg03;
		params.momsStoreFg04 = $scope.momsStoreFg04;
		params.momsStoreFg05 = $scope.momsStoreFg05;

		// $scope.init() 호출때문에 오류
		if($scope.storeHqBrandCd === undefined) {
			params.storeHqBrandCd = "";
			if($scope.userBrands === undefined) {
				params.userBrands = "";
			}
		}

		var url = "/sale/anals/monthlyMoms/SaleAnalsMonthlyMoms/list.sb";

		$http({
			method: 'POST', //방식
			url: url, /* 통신할 URL */
			params: params, /* 파라메터로 보낼 데이터 */
			headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
		}).then(function successCallback(response) {
			// 로딩바 hide
			$scope.$broadcast('loadingPopupInactive');
			if ($scope._httpStatusCheck(response, true)) {
				// this callback will be called asynchronously
				// when the response is available
				var list = response.data.data.list;
				if (list.length === undefined || list.length === 0) {
					$scope.data = new wijmo.collections.CollectionView([]);
					if (isView && response.data.message) {
						$scope._popMsg(response.data.message);
					}

					return false;
				}

				var selectedYearMonth = wijmo.Globalize.format(startMonth.value, 'yyyyMM');

				$scope._selectedYear = selectedYearMonth.substr(0,4);
				$scope._selectedMonth = $scope.changeDate(selectedYearMonth.substr(4,2));

				var sunSum = 0;
				var monSum = 0;
				var tueSum = 0;
				var wedSum = 0;
				var thuSum = 0;
				var friSum = 0;
				var satSum = 0;
				var totalSaleSum = 0;
				var htmlTxt = '';

				for (var i = 0; i < list.length; i++) {

					var sunData = list[i].sun;
					var monData = list[i].mon;
					var tueData = list[i].tue;
					var wedData = list[i].wed;
					var thuData = list[i].thu;
					var friData = list[i].fri;
					var satData = list[i].sat;

					if (sunData != null && sunData != "") {
						var sunSplit = sunData.split('||');
						totalSaleSum += parseInt(sunSplit[1]);
					}

					if (monData != null && monData != "") {
						var monSplit = monData.split('||');
						totalSaleSum += parseInt(monSplit[1]);
					}

					if (tueData != null && tueData != "") {
						var tueSplit = tueData.split('||');
						totalSaleSum += parseInt(tueSplit[1]);
					}

					if (wedData != null && wedData != "") {
						var wedSplit = wedData.split('||');
						totalSaleSum += parseInt(wedSplit[1]);
					}

					if (thuData != null && thuData != "") {
						var thuSplit = thuData.split('||');
						totalSaleSum += parseInt(thuSplit[1]);
					}

					if (friData != null && friData != "") {
						var friSplit = friData.split('||');
						totalSaleSum += parseInt(friSplit[1]);
					}

					if (satData != null && satData != "") {
						var satSplit = satData.split('||');
						totalSaleSum += parseInt(satSplit[1]);
					}
				}

				for (i = 0; i < list.length; i++) {

					cnt = i * 7;
					cnt2 = i + 1;

					htmlTxt += '<tr class="cal-date">';

					sunData = list[i].sun;
					monData = list[i].mon;
					tueData = list[i].tue;
					wedData = list[i].wed;
					thuData = list[i].thu;
					friData = list[i].fri;
					satData = list[i].sat;
					var weekSum = 0;

					if (sunData != null && sunData != "") {
						var sunSplit = sunData.split('||');
						htmlTxt += '	<td style="background-color: #d5d5d5; color:red;">' + $scope.changeDate(sunSplit[0].substr(6,2)) + '</td>';
						sunSum += parseInt(sunSplit[1]);
						weekSum += parseInt(sunSplit[1]);
					} else {
						htmlTxt += '	<td></td>';
					}

					if (monData != null && monData != "") {
						var monSplit = monData.split('||');
						if (monSplit[2] == "Y") {
							htmlTxt += '	<td style="background-color: #d5d5d5; color:red;">' + $scope.changeDate(monSplit[0].substr(6,2)) + '</td>';
						} else {
							htmlTxt += '	<td style="background-color: #d5d5d5;">' + $scope.changeDate(monSplit[0].substr(6,2)) + '</td>';
						}
						monSum += parseInt(monSplit[1]);
						weekSum += parseInt(monSplit[1]);
					} else {
						htmlTxt += '	<td></td>';
					}

					if (tueData != null && tueData != "") {
						var tueSplit = tueData.split('||');
						if (tueSplit[2] == "Y") {
							htmlTxt += '	<td style="background-color: #d5d5d5; color:red;">' + $scope.changeDate(tueSplit[0].substr(6,2)) + '</td>';
						} else {
							htmlTxt += '	<td style="background-color: #d5d5d5;">' + $scope.changeDate(tueSplit[0].substr(6,2)) + '</td>';
						}
						tueSum += parseInt(tueSplit[1]);
						weekSum += parseInt(tueSplit[1]);
					} else {
						htmlTxt += '	<td></td>';
					}

					if (wedData != null && wedData != "") {
						var wedSplit = wedData.split('||');
						if (wedSplit[2] == "Y") {
							htmlTxt += '	<td style="background-color: #d5d5d5; color:red;">' + $scope.changeDate(wedSplit[0].substr(6,2)) + '</td>';
						} else {
							htmlTxt += '	<td style="background-color: #d5d5d5;">' + $scope.changeDate(wedSplit[0].substr(6,2)) + '</td>';
						}
						wedSum += parseInt(wedSplit[1]);
						weekSum += parseInt(wedSplit[1]);
					} else {
						htmlTxt += '	<td></td>';
					}

					if (thuData != null && thuData != "") {
						var thuSplit = thuData.split('||');
						if (thuSplit[2] == "Y") {
							htmlTxt += '	<td style="background-color: #d5d5d5; color:red;">' + $scope.changeDate(thuSplit[0].substr(6,2)) + '</td>';
						} else {
							htmlTxt += '	<td style="background-color: #d5d5d5;">' + $scope.changeDate(thuSplit[0].substr(6,2)) + '</td>';
						}
						thuSum += parseInt(thuSplit[1]);
						weekSum += parseInt(thuSplit[1]);
					} else {
						htmlTxt += '	<td></td>';
					}

					if (friData != null && friData != "") {
						var friSplit = friData.split('||');
						if (friSplit[2] == "Y") {
							htmlTxt += '	<td style="background-color: #d5d5d5; color:red;">' + $scope.changeDate(friSplit[0].substr(6,2)) + '</td>';
						} else {
							htmlTxt += '	<td style="background-color: #d5d5d5;">' + $scope.changeDate(friSplit[0].substr(6,2)) + '</td>';
						}
						friSum += parseInt(friSplit[1]);
						weekSum += parseInt(friSplit[1]);
					} else {
						htmlTxt += '	<td></td>';
					}

					if (satData != null && satData != "") {
						var satSplit = satData.split('||');
						if (satSplit[2] == "Y") {
							htmlTxt += '	<td style="background-color: #d5d5d5; color:blue;">' + $scope.changeDate(satSplit[0].substr(6,2)) + '</td>';
						} else {
							htmlTxt += '	<td style="background-color: #d5d5d5; color:blue;">' + $scope.changeDate(satSplit[0].substr(6,2)) + '</td>';
						}
						satSum += parseInt(satSplit[1]);
						weekSum += parseInt(satSplit[1]);
					} else {
						htmlTxt += '	<td></td>';
					}

					htmlTxt += '	<td style="background-color: #FDD69D;" class="bg-total">주간합계</td>'
							+ '	<td  style="background-color: #FDD69D;" class="bg-total">비율</td>'
							+ '</tr>'
							+ '<tr class="cal-txt">';

					if (sunData != null && sunData != "") {
						var sunSplit = sunData.split('||');
						if(sunSplit[1] > 0 || sunSplit[1] < 0){
							htmlTxt += '	<td class="cal-txt"><p class="tl">' + sunSplit[3].format() + '</p><a class="wijLink" id=\"payFgId\" onClick=\"saleAnalsMonthlyMomsPop(' + sunSplit[0] + ')\"> ' + sunSplit[1].format() + '</a></td>';
						}else{
							htmlTxt += '	<td class="cal-txt">' + sunSplit[1].format() + '</td>';
						}
					} else {
						htmlTxt += '	<td></td>';
					}

					if (monData != null && monData != "") {
						var monSplit = monData.split('||');
						if(monSplit[1] > 0 || monSplit[1] < 0){
							htmlTxt += '	<td class="cal-txt"><p class="tl">' + monSplit[3].format() + '</p><a class="wijLink" id=\"payFgId\" onClick=\"saleAnalsMonthlyMomsPop(' + monSplit[0] + ')\"> ' + monSplit[1].format() + '</a></td>';
						}else{
							htmlTxt += '	<td class="cal-txt">' + monSplit[1].format() + '</td>';
						}
					} else {
						htmlTxt += '	<td></td>';
					}

					if (tueData != null && tueData != "") {
						var tueSplit = tueData.split('||');
						if(tueSplit[1] > 0 || tueSplit[1] < 0){
							htmlTxt += '	<td class="cal-txt"><p class="tl">' + tueSplit[3].format() + '</p><a class="wijLink" id=\"payFgId\" onClick=\"saleAnalsMonthlyMomsPop(' + tueSplit[0] + ')\"> ' + tueSplit[1].format() + ' </a></td>';
						}else{
							htmlTxt += '	<td class="cal-txt">' + tueSplit[1].format() + '</td>';
						}
					} else {
						htmlTxt += '	<td></td>';
					}

					if (wedData != null && wedData != "") {
						var wedSplit = wedData.split('||');
						if(wedSplit[1] > 0 || wedSplit[1] < 0){
							htmlTxt += '	<td class="cal-txt"><p class="tl">' + wedSplit[3].format() + '</p><a class="wijLink" id=\"payFgId\" onClick=\"saleAnalsMonthlyMomsPop(' + wedSplit[0] + ')\"> ' + wedSplit[1].format() + '</a></td>';
						}else{
							htmlTxt += '	<td class="cal-txt">' + wedSplit[1].format() + '</td>';
						}
					} else {
						htmlTxt += '	<td></td>';
					}

					if (thuData != null && thuData != "") {
						var thuSplit = thuData.split('||');
						if(thuSplit[1] > 0 || thuSplit[1] < 0){
							htmlTxt += '	<td class="cal-txt"><p class="tl">' + thuSplit[3].format() + '</p><a class="wijLink" id=\"payFgId\" onClick=\"saleAnalsMonthlyMomsPop(' + thuSplit[0] + ')\"> ' + thuSplit[1].format() + '</a></td>';
						}else{
							htmlTxt += '	<td class="cal-txt">' + thuSplit[1].format() + '</td>';
						}
					} else {
						htmlTxt += '	<td></td>';
					}

					if (friData != null && friData != "") {
						var friSplit = friData.split('||');
						if(friSplit[1] > 0 || friSplit[1] < 0){
							htmlTxt += '	<td class="cal-txt"><p class="tl">' + friSplit[3].format() + '</p><a class="wijLink" id=\"payFgId\" onClick=\"saleAnalsMonthlyMomsPop(' + friSplit[0] + ')\"> ' + friSplit[1].format() + '</a></td>';
						}else{
							htmlTxt += '	<td class="cal-txt">' + friSplit[1].format() + '</td>';
						}
					} else {
						htmlTxt += '	<td></td>';
					}

					if (satData != null && satData != "") {
						var satSplit = satData.split('||');
						if(satSplit[1] > 0 || satSplit[1] < 0){
							htmlTxt += '	<td class="cal-txt"><p class="tl">' + satSplit[3].format() + '</p><a class="wijLink" id=\"payFgId\" onClick=\"saleAnalsMonthlyMomsPop(' + satSplit[0] + ')\"> ' + satSplit[1].format() + '</a></td>';
						}else{
							htmlTxt += '	<td class="cal-txt">' + satSplit[1].format() + '</td>';
						}
					} else {
						htmlTxt += '	<td></td>';
					}

					var ratio = (weekSum / totalSaleSum) * 100;

					if (isNaN(ratio)) {
						ratio = 0;
					}

					htmlTxt += '	<td>' + weekSum.format() + '</td>'
							+'	<td>' + String(ratio.toFixed(2)) + '%' + '</td>'
							+ '</tr>'
				}

				//요일별 비율
				var sunRatio = (sunSum / totalSaleSum) * 100;
				var monRatio = (monSum / totalSaleSum) * 100;
				var tueRatio = (tueSum / totalSaleSum) * 100;
				var wedRatio = (wedSum / totalSaleSum) * 100;
				var thuRatio = (thuSum / totalSaleSum) * 100;
				var friRatio = (friSum / totalSaleSum) * 100;
				var satRatio = (satSum / totalSaleSum) * 100;

				if (isNaN(sunRatio)) {
					sunRatio = 0;
				}

				if (isNaN(monRatio)) {
					monRatio = 0;
				}

				if (isNaN(tueRatio)) {
					tueRatio = 0;
				}

				if (isNaN(wedRatio)) {
					wedRatio = 0;
				}

				if (isNaN(thuRatio)) {
					thuRatio = 0;
				}

				if (isNaN(friRatio)) {
					friRatio = 0;
				}

				if (isNaN(satRatio)) {
					satRatio = 0;
				}


				htmlTxt += '<tr class="bg-total">'
						+ '  <td style="background-color: #FDD69D; color:red;">일요일합계</td>'
						+ '  <td style="background-color: #FDD69D;">월요일합계</td>'
						+ '  <td style="background-color: #FDD69D;">화요일합계</td>'
						+ '  <td style="background-color: #FDD69D;">수요일합계</td>'
						+ '  <td style="background-color: #FDD69D;">목요일합계</td>'
						+ '  <td style="background-color: #FDD69D;">금요일합계</td>'
						+ '  <td style="background-color: #FDD69D; color:blue;">토요일합계</th>'
						+ '  <td style="background-color: #FDD69D;" colspan="2">총매출합계</td>'
						+ '</tr>'
						+ '<tr class="cal-txt">'
						+ '  <td>' + sunSum.format() + '</td>'
						+ '  <td>' + monSum.format() + '</td>'
						+ '  <td>' + tueSum.format() + '</td>'
						+ '  <td>' + wedSum.format() + '</td>'
						+ '  <td>' + thuSum.format() + '</td>'
						+ '  <td>' + friSum.format() + '</td>'
						+ '  <td>' + satSum.format() + '</td>'
						+ '  <td rowspan="3" colspan="2" style="color:red;" class="tr s20">' + totalSaleSum.format() + '</td>'
						+ '</tr>'
						+ '<tr class="bg-total">'
						+ '  <td style="background-color: #FDD69D;">비율</td>'
						+ '  <td style="background-color: #FDD69D;">비율</td>'
						+ '  <td style="background-color: #FDD69D;">비율</td>'
						+ '  <td style="background-color: #FDD69D;">비율</td>'
						+ '  <td style="background-color: #FDD69D;">비율</td>'
						+ '  <td style="background-color: #FDD69D;">비율</td>'
						+ '  <td style="background-color: #FDD69D;">비율</td>'
						+ '</tr>'
						+ '<tr class="cal-txt">'
						+ '  <td>' + String(sunRatio.toFixed(2)) + '%' + '</td>'
						+ '  <td>' + String(monRatio.toFixed(2)) + '%' + '</td>'
						+ '  <td>' + String(tueRatio.toFixed(2)) + '%' + '</td>'
						+ '  <td>' + String(wedRatio.toFixed(2)) + '%' + '</td>'
						+ '  <td>' + String(thuRatio.toFixed(2)) + '%' + '</td>'
						+ '  <td>' + String(friRatio.toFixed(2)) + '%' + '</td>'
						+ '  <td>' + String(satRatio.toFixed(2)) + '%' + '</td>'
						+ '</tr>';

				$scope.saleAnalsMonthlyMomsBody = $sce.trustAsHtml(htmlTxt);
			}

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
	};
}]);

function saleAnalsMonthlyMomsPop(saleDate) {
	var scope = angular.element(document.getElementById('payFgId')).scope();
	scope.$apply(function() {
		var params       = {};
		params.saleDate	= saleDate;
		params.hqOfficeCd = gvHqOfficeCd;
		params.storeCd	= $("#saleAnalsMonthlyMomsSelectStoreCd").val();
		scope._broadcast('saleAnalsMonthlyMomsStoreCtrl', params);
	});
};