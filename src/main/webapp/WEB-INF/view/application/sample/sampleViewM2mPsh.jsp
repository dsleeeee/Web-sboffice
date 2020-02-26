<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>



<div class="subCon" ng-controller="sampleLayerCtrl">

	<div>
		<p class="mb20">정산내역팝업</p>
		<button class="btn_skyblue" id="" ng-click="samLayer()">정산내역팝업</button></td>
	</div>

	<!-- layer popup start -->
	<wj-popup control="sampleLayer" show-trigger="Click" hide-trigger="Click" style="display: none; width:900px;">
	
		<div class="wj-dialog wj-dialog-columns" ng-controller="sampleLyCtrl">

			<div class="wj-dialog-header wj-dialog-header-font">
				<span class="bk">정산내역</span>
				<a href="#" class="wj-hide btn_close"></a>
			</div>
	
			<div class="wj-dialog-body sc2" style="overflow-y: hidden;">
				<h2 class="h2_tit">
					<span class="bk">C001 솔비카페(대림점)</span> / <span class="bk">포스번호 : 01</span>
				</h2>
				<h3 class="h3_tbl brt bg-orange-lite">
					<p class="txtIn bk s12">조회일자 : <span class="bk">2018년 1월 1일</span> / 마감구분 : 일마감</p>
				</h3>
	
				<div style="height: 350px; overflow-y: auto;">
	
					<!-- 매출내역 start -->
					<h3 class="h3_tbl brt">
						<span class="bk s14">매출내역</span>
						<span class="fr">오픈일시 : <span>2018.01.01 09:01:10</span> / 마감일시 : <span>2018.01.01 21:00:15</span></span>
					</h3>
					<table class="tblType01 tbl-pos-sh">
						<colgroup>
							<col class="w12"/>
							<col class="w13"/>
							<col class="w12"/>
							<col class="w13"/>
							<col class="w12"/>
							<col class="w13"/>
							<col class="w12"/>
							<col class="w13"/>
						</colgroup>
						<tbody>
						<tr>
							<th>
								<div class="impWrap">영업준비금</div>
							</th>
							<td>1,000,000,000</td>
							<th>
								<div class="impWrap">총매출액</div>
							</th>
							<td>10,000</td>
							<th>
								<div class="impWrap">할인액</div>
							</th>
							<td>10,000</td>
							<th>
								<div class="impWrap">실매출액</div>
							</th>
							<td>10,000</td>
						</tr>
						<tr>
							<th>
								<div class="impWrap">영수건수</div>
							</th>
							<td>100</td>
							<th>
								<div class="impWrap">영수단가</div>
							</th>
							<td>10,000</td>
							<th>
								<div class="impWrap">방문고객수</div>
							</th>
							<td>1,200</td>
							<th>
								<div class="impWrap">객단가</div>
							</th>
							<td>10,000</td>
						</tr>
						<tr>
							<th>
								<div class="impWrap">봉사료</div>
							</th>
							<td>10,000</td>
							<th>
								<div class="impWrap">기타에누리액</div>
							</th>
							<td>10,000</td>
							<th>
								<div class="impWrap">취소매출건수</div>
							</th>
							<td>1,200</td>
							<th>
								<div class="impWrap">취소매출금액</div>
							</th>
							<td>10,000</td>
						</tr>
						</tbody>
					</table>
					<!-- //매출내역 end -->
					
					<!-- 결제내역 start -->
					<h3 class="h3_tbl brt"><span class="bk s14">결제내역</span></h3>
					<table class="tblType01 tbl-pos-sh">
						<colgroup>
							<col class="w12"/>
							<col class="w13"/>
							<col class="w12"/>
							<col class="w13"/>
							<col class="w12"/>
							<col class="w13"/>
							<col class="w12"/>
							<col class="w13"/>
						</colgroup>
						<tbody>
						<tr>
							<th>
								<div class="impWrap">현금결제액</div>
							</th>
							<td>10,000</td>
							<th>
								<div class="impWrap">신용카드</div>
							</th>
							<td>10,000</td>
							<th>
								<div class="impWrap">외상</div>
							</th>
							<td>10,000</td>
							<th>
								<div class="impWrap">상품권</div>
							</th>
							<td>10,000</td>
						</tr>
						<tr>
							<th>
								<div class="impWrap">식권</div>
							</th>
							<td>100</td>
							<th>
								<div class="impWrap">회원포인트</div>
							</th>
							<td>10,000</td>
							<th>
								<div class="impWrap">제휴카드</div>
							</th>
							<td>1,200</td>
							<th>
								<div class="impWrap">사원카드</div>
							</th>
							<td>10,000</td>
						</tr>
						<tr>
							<th>
								<div class="impWrap">캐시비</div>
							</th>
							<td>10,000</td>
							<th>
								<div class="impWrap">상품권 카드결제</div>
							</th>
							<td>10,000</td>
							<th>
								<div class="impWrap">상품권 현금결제</div>
							</th>
							<td>1,200</td>
							<th>
								<div class="impWrap"></div>
							</th>
							<td></td>
						</tr>
						</tbody>
					</table>
					<!-- //결제내역 end -->
					
					<!-- 할인내역 start -->
					<h3 class="h3_tbl brt"><span class="bk s14">할인내역</span></h3>
					<table class="tblType01 tbl-pos-sh">
						<colgroup>
							<col class="w12"/>
							<col class="w13"/>
							<col class="w12"/>
							<col class="w13"/>
							<col class="w13"/>
							<col class="w13"/>
							<col class="w12"/>
							<col class="w13"/>
						</colgroup>
						<tbody>
						<tr>
							<th>
								<div class="impWrap">일반할인</div>
							</th>
							<td>10,000</td>
							<th>
								<div class="impWrap">서비스할인</div>
							</th>
							<td>10,000</td>
							<th>
								<div class="impWrap">제휴카드할인</div>
							</th>
							<td>10,000</td>
							<th>
								<div class="impWrap">쿠폰할인</div>
							</th>
							<td>10,000</td>
						</tr>
						<tr>
							<th>
								<div class="impWrap">회원할인</div>
							</th>
							<td>100</td>
							<th>
								<div class="impWrap">식권할인</div>
							</th>
							<td>10,000</td>
							<th>
								<div class="impWrap">프로모션할인</div>
							</th>
							<td>1,200</td>
							<th>
								<div class="impWrap">신용카드현장할인</div>
							</th>
							<td>10,000</td>
						</tr>
						<tr>
							<th>
								<div class="impWrap">포장할인</div>
							</th>
							<td>10,000</td>
							<th></th>
							<td></td>
							<th></th>
							<td></td>
							<th></th>
							<td></td>
						</tr>
						</tbody>
					</table>
					<!-- //할인내역 end -->
					
					<!-- 입출금내역 start -->
					<h3 class="h3_tbl brt"><span class="bk s14">입출금내역</span></h3>
					<table class="tblType01 tbl-pos-sh">
						<colgroup>
							<col class="w12"/>
							<col class="w13"/>
							<col class="w12"/>
							<col class="w13"/>
							<col class="w13"/>
							<col class="w13"/>
							<col class="w12"/>
							<col class="w13"/>
						</colgroup>
						<tbody>
						<tr>
						<th>
							<div class="impWrap">시재입금액</div>
						</th>
						<td>10,000</td>
						<th>
								<div class="impWrap">시재출금액</div>
							</th>
							<td>10,000</td>
							<th>
								<div class="impWrap">외상입금-현금</div>
							</th>
							<td>10,000</td>
							<th>
								<div class="impWrap">외상입금-카드</div>
							</th>
							<td>10,000</td>
						</tr>
						</tbody>
					</table>
					<!-- //입출금내역 end -->
					
					<!-- 현금시제내역 start -->
					<h3 class="h3_tbl brt"><span class="bk s14">현금시제내역</span></h3>
					<table class="tblType01 tbl-pos-sh">
						<colgroup>
							<col class="w12"/>
							<col class="w13"/>
							<col class="w12"/>
							<col class="w13"/>
							<col class="w13"/>
							<col class="w13"/>
							<col class="w12"/>
							<col class="w13"/>
						</colgroup>
						<tbody>
						<tr>
							<th>
								<div class="impWrap">수표</div>
							</th>
							<td>10,000</td>
							<th>
								<div class="impWrap">십만원권</div>
							</th>
							<td>10,000</td>
							<th>
								<div class="impWrap">오만원권</div>
							</th>
							<td>10,000</td>
							<th>
								<div class="impWrap">만원권</div>
							</th>
							<td>10,000</td>
						</tr>
						<tr>
							<th>
								<div class="impWrap">오천원권</div>
							</th>
							<td>100</td>
							<th>
								<div class="impWrap">천원권</div>
							</th>
							<td>10,000</td>
							<th>
								<div class="impWrap">오백원권</div>
							</th>
							<td>1,200</td>
							<th>
								<div class="impWrap">백원권</div>
							</th>
							<td>10,000</td>
						</tr>
						<tr>
							<th>
								<div class="impWrap">오십원권</div>
							</th>
							<td>10,000</td>
							<th>
								<div class="impWrap">십원권</div>
							</th>
							<td>10,000</td>
							<th>
								<div class="impWrap">상품권 현금결제</div>
							</th>
							<td>1,200</td>
							<th></th>
							<td></td>
						</tr>
						</tbody>
					</table>
					<!-- //현금시제내역 end -->					
					
					<!-- 마감시재 등록내역 start -->
					<h3 class="h3_tbl brt"><span class="bk s14">현금시제내역</span></h3>
					<table class="tblType01 tbl-pos-sh">
						<colgroup>
							<col class="w12"/>
							<col class="w13"/>
							<col class="w12"/>
							<col class="w13"/>
							<col class="w13"/>
							<col class="w13"/>
							<col class="w12"/>
							<col class="w13"/>
						</colgroup>
						<tbody>
						<tr>
							<th>
								<div class="impWrap">현금시재금액</div>
							</th>
							<td>10,000</td>
							<th>
								<div class="impWrap">상품권시재금액</div>
							</th>
							<td>10,000</td>
							<th>
								<div class="impWrap">식권시재금액</div>
							</th>
							<td>10,000</td>
							<th>
								<div class="impWrap">식권-자투리</div>
							</th>
							<td>10,000</td>
						</tr>
						<tr>
							<th>
								<div class="impWrap">현금과부족</div>
							</th>
							<td>10,000</td>
							<th>
								<div class="impWrap">상품권과부족</div>
							</th>
							<td>10,000</td>
							<th>
								<div class="impWrap">식권과부족</div>
							</th>
							<td>1,200</td>
							<th></th>
							<td></td>
						</tr>
						</tbody>
					</table>
					<!-- //마감시재 등록내역 end -->
				</div>
			</div>
	
		</div>
	
		<div class="wj-dialog-footer">
			<!-- 닫기 -->
			<button class="btn btn_gray" ng-click="close()">닫기</button>
		</div>
	</div>
	
	</wj-popup>	
	<!-- //layer popup end -->

</div>

	<script>
	
	 /**
	  * get application
	  */
	 var app = agrid.getApp();
	
	 /**
	  * 샘플 레이어 팝업
	  */
	 app.controller('sampleLayerCtrl', ['$scope', '$http', function ($scope, $http) {
	   // 상위 객체 상속 : T/F 는 picker
	   angular.extend(this, new RootController('sampleLayerCtrl', $scope, $http, false));
		 
	   $scope.selectedSample;
	   
	   // 신규등록 버튼 클릭
	   $scope.samLayer = function(){
		 $scope.selectedSample = {};
		 
		 $scope.sampleLayer.show(true, function(s){
		   /* $scope.getHqEmpList(); */
		 });
	   };
	   
	   // 화면 ready 된 후 설정
	   angular.element(document).ready(function () {
		   
		 // 사원 등록 팝업 핸들러 추가
		 $scope.sampleLayer.shown.addHandler(function (s) {
		   setTimeout(function() {
			   
			 var params = $scope.selectedSample;
			 $scope._broadcast('sampleLyCtrl', params);
		   }, 50);
		   
		 });
		 
	   });
	
	 }]);
	 
	 </script>


