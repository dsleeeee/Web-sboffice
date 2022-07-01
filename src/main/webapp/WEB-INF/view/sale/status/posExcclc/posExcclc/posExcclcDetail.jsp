<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- layer popup start -->
	<wj-popup control="posExcclcDetailLayer" show-trigger="Click" hide-trigger="Click" style="display: none; width:900px;">

		<div class="wj-dialog wj-dialog-columns" ng-controller="posExcclcDetailLayerCtrl">

			<div class="wj-dialog-header wj-dialog-header-font">
				<span class="bk"><s:message code="posExcclc.excclcInfo"/></span>
				<a href="#" id="btnClose" class="wj-hide btn_close"></a>
			</div>

			<div class="wj-dialog-body sc2" style="overflow-y: hidden;">
				<h2 class="h2_tit">
					<span id="rStoreNm" class="bk"></span> / <s:message code="posExcclc.posNo"/> : <span id="rPosNo" class="bk"></span>
				</h2>
				<h3 class="h3_tbl brt bg-orange-lite">
					<p class="txtIn bk s12"><s:message code="posExcclc.srchDate"/> : <span class="bk" id="rSaleDate"></span> / <s:message code="posExcclc.closeFg"/> : <span class="bk" id="rCloseFg"></span></p>
				</h3>

				<div style="height: 350px; overflow-y: auto;">

					<!-- 매출내역 start -->
					<h3 class="h3_tbl brt">
						<span class="bk s14"><s:message code="posExcclc.saleInfo"/></span>
						<span class="fr"><s:message code="posExcclc.openDate"/> : <span id="rOpenDate"></span> / <s:message code="posExcclc.closeDate"/> : <span id="rCloseDate"></span></span>
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
								<div class="impWrap"><s:message code="posExcclc.posFundAmt"/> </div>
							</th>
							<td id="rPosFundAmt"></td>
							<th>
								<div class="impWrap"><s:message code="posExcclc.totSaleAmt"/></div>
							</th>
							<td id="rTotSaleAmt"></td>
							<th>
								<div class="impWrap"><s:message code="posExcclc.dcAmt"/></div>
							</th>
							<td id="rDcAmt"></td>
							<th>
								<div class="impWrap"><s:message code="posExcclc.realSaleAmt"/></div>
							</th>
							<td id="rRealSaleAmt"></td>
						</tr>
						<tr>
							<th>
								<div class="impWrap"><s:message code="posExcclc.billCnt"/></div>
							</th>
							<td id="rBillCnt"></td>
							<th>
								<div class="impWrap"><s:message code="posExcclc.billTran"/></div>
							</th>
							<td id="rBillTran"></td>
							<th>
								<div class="impWrap"><s:message code="posExcclc.guestCnt"/></div>
							</th>
							<td id="rGuestCnt"></td>
							<th>
								<div class="impWrap"><s:message code="posExcclc.guestTran"/></div>
							</th>
							<td id="rGuestTran"></td>
						</tr>
						<tr>
							<th>
								<div class="impWrap"><s:message code="posExcclc.totTipAmt"/></div>
							</th>
							<td id="rTotTipAmt"></td>
							<th>
								<div class="impWrap"><s:message code="posExcclc.totEtcAmt"/></div>
							</th>
							<td id="rTotEtcAmt"></td>
							<th>
								<div class="impWrap"><s:message code="posExcclc.totDepositCnt"/></div>
							</th>
							<td id="rTotDepositCnt"></td>
							<th></th>
							<td></td>
						</tr>
						<tr>
							<th>
								<div class="impWrap"><s:message code="posExcclc.cancelCnt"/></div>
							</th>
							<td id="rCancelCnt"></td>
							<th>
								<div class="impWrap"><s:message code="posExcclc.cancelAmt"/></div>
							</th>
							<td id="rCancelAmt"></td>
							<th></th>
							<td></td>
							<th></th>
							<td></td>
						</tr>
						</tbody>
					</table>
					<!-- //매출내역 end -->

					<!-- 결제내역 start -->
					<h3 class="h3_tbl brt"><span class="bk s14"><s:message code="posExcclc.payInfo"/></span></h3>
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
								<div class="impWrap"><s:message code="posExcclc.payAmtCash"/></div>
							</th>
							<td id="rPayAmtCash"></td>
							<th>
								<div class="impWrap"><s:message code="posExcclc.payAmtCashBill"/></div>
							</th>
							<td id="rPayAmtCashBill"></td>
							<th>
								<div class="impWrap"><s:message code="posExcclc.payAmtCard"/></div>
							</th>
							<td id="rPayAmtCard"></td>
							<th>
								<div class="impWrap"><s:message code="posExcclc.payAmtPayco"/></div>
							</th>
							<td id="rPayAmtPayco"></td>
						</tr>
						<tr>
							<th>
								<div class="impWrap"><s:message code="posExcclc.payAmtVpoint"/></div>
							</th>
							<td id="rPayAmtVpoint"></td>
							<th>
								<div class="impWrap"><s:message code="posExcclc.payAmtVcoupn"/></div>
							</th>
							<td id="rPayAmtVcoupn"></td>
							<th>
								<div class="impWrap"><s:message code="posExcclc.payAmtVcharge"/></div>
							</th>
							<td id="rPayAmtVcharge"></td>
							<th>
								<div class="impWrap"><s:message code="posExcclc.payAmtMpay"/></div>
							</th>
							<td id="rPayAmtMpay"></td>
						</tr>
						<tr>
							<th>
								<div class="impWrap"><s:message code="posExcclc.payAmtMcoupn"/></div>
							</th>
							<td id="rPayAmtMcoupn"></td>
							<th>
								<div class="impWrap"><s:message code="posExcclc.payAmtMembr"/></div>
							</th>
							<td id="rPayAmtMembr"></td>
							<th>
								<div class="impWrap"><s:message code="posExcclc.payAmtPrepaid"/></div>
							</th>
							<td id="rPayAmtPrepaid"></td>
							<th>
								<div class="impWrap"><s:message code="posExcclc.payAmtPostpaid"/></div>
							</th>
							<td id="rPayAmtPostpaid"></td>
						</tr>
						<tr>
							<th>
								<div class="impWrap"><s:message code="posExcclc.payAmtCoupn"/></div>
							</th>
							<td id="rPayAmtCoupn"></td>
							<th>
								<div class="impWrap"><s:message code="posExcclc.payAmtGift"/></div>
							</th>
							<td id="rPayAmtGift"></td>
							<th>
								<div class="impWrap"><s:message code="posExcclc.payAmtFstmp"/></div>
							</th>
							<td id="rPayAmtFstmp"></td>
							<th>
								<div class="impWrap"><s:message code="posExcclc.payAmtPartner"/></div>
							</th>
							<td id="rPayAmtPartner"></td>
						</tr>
						<tr>
							<th>
								<div class="impWrap"><s:message code="posExcclc.payAmtOkcsb"/></div>
							</th>
							<td id="rPayAmtOkcsb"></td>
							<th>
								<div class="impWrap"><s:message code="posExcclc.payAmtEmpCard"/></div>
							</th>
							<td id="rPayAmtEmpCard"></td>
							<th>
								<div class="impWrap"><s:message code="posExcclc.payAmtEmpTemporary"/></div>
							</th>
							<td id="rPayAmtEmpTemporary"></td>
							<th>
								<div class="impWrap"><s:message code="posExcclc.paySmartOrderAmt"/></div>
							</th>
							<td id="rPayAmtPrev"></td>
						</tr>


						</tbody>
					</table>
					<!-- //결제내역 end -->
										
					<!-- 할인내역 start -->
					<h3 class="h3_tbl brt"><span class="bk s14"><s:message code="posExcclc.dcInfo"/></span></h3>
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
								<div class="impWrap"><s:message code="posExcclc.dcAmtGeneral"/></div>
							</th>
							<td id="rDcAmtGeneral"></td>
							<th>
								<div class="impWrap"><s:message code="posExcclc.dcAmtCoupn"/></div>
							</th>
							<td id="rDcAmtCoupn"></td>
							<th>
								<div class="impWrap"><s:message code="posExcclc.dcAmtMembr"/></div>
							</th>
							<td id="rDcAmtMembr"></td>
							<th>
								<div class="impWrap"><s:message code="posExcclc.dcAmtPartner"/></div>
							</th>
							<td id="rDcAmtPartner"></td>
						</tr>
						<tr>
							<th>
								<div class="impWrap"><s:message code="posExcclc.dcAmtService"/></div>
							</th>
							<td id="rDcAmtService"></td>
							<th>
								<div class="impWrap"><s:message code="posExcclc.dcAmtPromtn"/></div>
							</th>
							<td id="rDcAmtPromtn"></td>
							<th>
								<div class="impWrap"><s:message code="posExcclc.dcAmtPack"/></div>
							</th>
							<td id="rDcAmtPack"></td>
							<th>
								<div class="impWrap"><s:message code="posExcclc.dcAmtSite"/></div>
							</th>
							<td id="rDcAmtSite"></td>
						</tr>
						<tr>
							<th>
								<div class="impWrap"><s:message code="posExcclc.dcAmtVcoupn"/></div>
							</th>
							<td id="rDcAmtVcoupn"></td>
							<th>
								<div class="impWrap"></div>
							</th>
							<td></td>
							<th>
								<div class="impWrap"></div>
							</th>
							<td></td>
							<th>
								<div class="impWrap"></div>
							</th>
							<td></td>
						</tr>
						</tbody>
					</table>
					<!-- //할인내역 end -->
					
					<!-- 비매출내역 start -->
					<h3 class="h3_tbl brt"><span class="bk s14"><s:message code="posExcclc.nSaleInfo"/></span></h3>
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
								<div class="impWrap"><s:message code="posExcclc.nonsaleFgCnt1"/></div>
							</th>
							<td id="rNonsaleCnt1"></td>
							<th>
								<div class="impWrap"><s:message code="posExcclc.nonsaleFgAmt1"/></div>
							</th>
							<td id="rNonsaleAmt1"></td>
							<th>
								<div class="impWrap"><s:message code="posExcclc.nonsaleFgCnt2"/></div>
							</th>
							<td id="rNonsaleCnt2"></td>
							<th>
								<div class="impWrap"><s:message code="posExcclc.nonsaleFgAmt2"/></div>
							</th>
							<td id="rNonsaleAmt2"></td>
						</tr>
						<tr>
							<th>
								<div class="impWrap"><s:message code="posExcclc.nonsaleFgCnt3"/></div>
							</th>
							<td id="rNonsaleCnt3"></td>
							<th>
								<div class="impWrap"><s:message code="posExcclc.nonsaleFgAmt3"/></div>
							</th>
							<td id="rNonsaleAmt3"></td>
							<th>
								<div class="impWrap"><s:message code="posExcclc.nonsaleFgCnt4"/></div>
							</th>
							<td id="rNonsaleCnt4"></td>
							<th>
								<div class="impWrap"><s:message code="posExcclc.nonsaleFgAmt4"/></div>
							</th>
							<td id="rNonsaleAmt4"></td>
						</tr>
						<tr>
							<th>
								<div class="impWrap"><s:message code="posExcclc.nonsaleFgCnt5"/></div>
							</th>
							<td id="rNonsaleCnt5"></td>
							<th>
								<div class="impWrap"><s:message code="posExcclc.nonsaleFgAmt5"/></div>
							</th>
							<td id="rNonsaleAmt5"></td>
							<th></th>
							<td></td>
							<th></th>
							<td></td>
						</tr>


						</tbody>
					</table>
					<!-- //비매출내역 end -->
					
					<!-- 입출금내역 start -->
					<h3 class="h3_tbl brt"><span class="bk s14"><s:message code="posExcclc.inOutInfo"/></span></h3>
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
								<div class="impWrap"><s:message code="posExcclc.accntInAmt"/></div>
							</th>
							<td id="rAccntInAmt"></td>
							<th>
								<div class="impWrap"><s:message code="posExcclc.accntOutAmt"/></div>
							</th>
							<td id="rAccntOutAmt"></td>
							<th>
								<div class="impWrap"></div>
							</th>
							<td></td>
							<th>
								<div class="impWrap"></div>
							</th>
							<td></td>
						</tr>
						</tbody>
					</table>
					<!-- //입출금내역 end -->

					<!-- 현금시제내역 start -->
					<h3 class="h3_tbl brt"><span class="bk s14"><s:message code="posExcclc.cashTicketInfo"/></span></h3>
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
								<div class="impWrap"><s:message code="posExcclc.cashTicketAmt10"/></div>
							</th>
							<td id="rCashTicketAmt10"></td>
							<th>
								<div class="impWrap"><s:message code="posExcclc.cashTicketAmt50"/></div>
							</th>
							<td id="rCashTicketAmt50"></td>
							<th>
								<div class="impWrap"><s:message code="posExcclc.cashTicketAmt100"/></div>
							</th>
							<td id="rCashTicketAmt100"></td>
							<th>
								<div class="impWrap"><s:message code="posExcclc.cashTicketAmt500"/></div>
							</th>
							<td id="rCashTicketAmt500"></td>
						</tr>
						<tr>
							<th>
								<div class="impWrap"><s:message code="posExcclc.cashTicketAmt1000"/></div>
							</th>
							<td id="rCashTicketAmt1000"></td>
							<th>
								<div class="impWrap"><s:message code="posExcclc.cashTicketAmt5000"/></div>
							</th>
							<td id="rCashTicketAmt5000"></td>
							<th>
								<div class="impWrap"><s:message code="posExcclc.cashTicketAmt10000"/></div>
							</th>
							<td id="rCashTicketAmt10000"></td>
							<th>
								<div class="impWrap"><s:message code="posExcclc.cashTicketAmt50000"/></div>
							</th>
							<td id="rCashTicketAmt50000"></td>
						</tr>
						<tr>
							<th>
								<div class="impWrap"><s:message code="posExcclc.cashTicketAmt100000"/></div>
							</th>
							<td id="rCashTicketAmt100000"></td>
							<th>
								<div class="impWrap"><s:message code="posExcclc.cashTicketAmtEct"/></div>
							</th>
							<td id="rCashTicketAmtEct"></td>
							<th>
								<div class="impWrap"></div>
							</th>
							<td></td>
							<th>
								<div class="impWrap"></div>
							</th>
							<td></td>
						</tr>
						</tbody>
					</table>
					<!-- //현금시제내역 end -->

					<!-- 마감시재 등록내역 start -->
					<h3 class="h3_tbl brt"><span class="bk s14"><s:message code="posExcclc.closeTicketInfo"/></span></h3>
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
								<div class="impWrap"><s:message code="posExcclc.exactAmtCash"/></div>
							</th>
							<td id="rExactAmtCash"></td>
							<th>
								<div class="impWrap"><s:message code="posExcclc.exactAmtGift"/></div>
							</th>
							<td id="rExactAmtGift"></td>
							<th>
								<div class="impWrap"><s:message code="posExcclc.lostAmtCash"/></div>
							</th>
							<td id="rLostAmtCash"></td>
							<th>
								<div class="impWrap"><s:message code="posExcclc.lostAmtGift"/></div>
							</th>
							<td id="rLostAmtGift"></td>
						</tr>
						</tbody>
					</table>
					<!-- //마감시재 등록내역 end -->
				</div>
			</div>
			<div class="wj-dialog-footer">
				<!-- 닫기 -->
				<button class="btn btn_gray" ng-click="_closeBtn()"><s:message code="cmm.close"/></button>
			</div>
		</div>
	</div>

</wj-popup>
<!-- //layer popup end -->

<script>

	var app = agrid.getApp();

	/**********************************************************************
	 *  버전목록 그리드
	 **********************************************************************/
	app.controller('posExcclcDetailLayerCtrl', ['$scope', '$http', function ($scope, $http) {
		// 상위 객체 상속 : T/F 는 picker
		angular.extend(this, new RootController('posExcclcDetailLayerCtrl', $scope, $http, true));

		// 닫기
		$scope._closeBtn = function(){
			$scope.posExcclcDetailLayer.hide();
		};

	}]);

	<%-- 상세정보 팝업 열기 --%>
	function openDtlLayer(items) {
		getDtlData(items);
		console.log(items);
	}

	function getDtlData(items) {

		var param = items;

		$.postJSON("/sale/status/posExcclc/posExcclc/dtlInfo.sb", param, function(result) {

			var data = result.data;

		    <%-- 상세정보 --%>
		    $("#rStoreNm").text(data.storeCd + " " + data.storeNm);
		    $("#rPosNo").text(param.posNo);
		    $("#rCloseFg").text(param.closeFgNm);
		    $("#rSaleDate").text(param.saleDate);
		    $("#rOpenDate").text(data.openDt);
		    $("#rCloseDate").text(data.closeDt);

		    $("#rPosFundAmt").text(numComma(data.totFundAmt));
		    $("#rTotSaleAmt").text(numComma(data.totSaleAmt));
		    $("#rDcAmt").text(numComma(data.totDcAmt));
		    $("#rRealSaleAmt").text(numComma(data.realSaleAmt));

		    $("#rBillCnt").text(numComma(data.totBillCnt));
		    $("#rBillTran").text(numComma(data.totBillTran));
		    $("#rGuestCnt").text(numComma(data.totGuestCnt));
		    $("#rGuestTran").text(numComma(data.totGuestTran));

		    $("#rTotTipAmt").text(numComma(data.totTipAmt));
		    $("#rTotEtcAmt").text(numComma(data.totEtcAmt));
		    $("#rTotDepositCnt").text(numComma(data.totDepositCnt));

		    $("#rCancelCnt").text(numComma(data.rtnBillCnt));
		    $("#rCancelAmt").text(numComma(data.rtnBillAmt));

		    $("#rPayAmtCash").text(numComma(data.payAmtCash));
		    $("#rPayAmtCashBill").text(numComma(data.payAmtCashBill));
		    $("#rPayAmtCard").text(numComma(data.payAmtCard));
		    $("#rPayAmtPayco").text(numComma(data.payAmtPayco));

		    $("#rPayAmtVpoint").text(numComma(data.payAmtVpoint));
		    $("#rPayAmtVcoupn").text(numComma(data.payAmtVcoupn));
		    $("#rPayAmtVcharge").text(numComma(data.payAmtVcharge));
		    $("#rPayAmtMpay").text(numComma(data.payAmtMpay));

		    $("#rPayAmtMcoupn").text(numComma(data.payAmtMcoupn));
		    $("#rPayAmtMembr").text(numComma(data.payAmtMembr));
		    $("#rPayAmtPrepaid").text(numComma(data.payAmtPrepaid));
		    $("#rPayAmtPostpaid").text(numComma(data.payAmtPostpaid));

		    $("#rPayAmtCoupn").text(numComma(data.payAmtCoupn));
		    $("#rPayAmtGift").text(numComma(data.payAmtGift));
		    $("#rPayAmtFstmp").text(numComma(data.payAmtFstmp));
		    $("#rPayAmtPartner").text(numComma(data.payAmtPartner));

		    $("#rPayAmtOkcsb").text(numComma(data.payAmtOkcsb));
		    $("#rPayAmtEmpCard").text(numComma(data.payAmtEmpCard));
		    $("#rPayAmtEmpTemporary").text(numComma(data.payAmtEmpTemporary));
		    $("#rPayAmtPrev").text(numComma(data.payAmtPrev));
			
		    $("#rNonsaleCnt1").text(numComma(data.nonsaleCnt1));
		    $("#rNonsaleAmt1").text(numComma(data.nonsaleAmt1));
		    $("#rNonsaleCnt2").text(numComma(data.nonsaleCnt2));
		    $("#rNonsaleAmt2").text(numComma(data.nonsaleAmt2));

		    $("#rNonsaleCnt3").text(numComma(data.nonsaleCnt3));
		    $("#rNonsaleAmt3").text(numComma(data.nonsaleAmt3));
		    $("#rNonsaleCnt4").text(numComma(data.nonsaleCnt4));
		    $("#rNonsaleAmt4").text(numComma(data.nonsaleAmt4));
		    
		    $("#rNonsaleCnt5").text(numComma(data.nonsaleCnt5));
		    $("#rNonsaleAmt5").text(numComma(data.nonsaleAmt5));
		    
		    $("#rDcAmtGeneral").text(numComma(data.dcAmtGeneral));
		    $("#rDcAmtCoupn").text(numComma(data.dcAmtCoupn));
		    $("#rDcAmtMembr").text(numComma(data.dcAmtMembr));
		    $("#rDcAmtPartner").text(numComma(data.dcAmtPartner));

		    $("#rDcAmtService").text(numComma(data.dcAmtService));
		    $("#rDcAmtPromtn").text(numComma(data.dcAmtPromtn));
		    $("#rDcAmtPack").text(numComma(data.dcAmtPack));
		    $("#rDcAmtSite").text(numComma(data.dcAmtSite));

		    $("#rDcAmtVcoupn").text(numComma(data.dcAmtVcoupn));

		    $("#rAccntInAmt").text(numComma(data.accntInAmt));
		    $("#rAccntOutAmt").text(numComma(data.accntOutAmt));

		    $("#rCashTicketAmt10").text(numComma(data.cashTicketAmt10));
		    $("#rCashTicketAmt50").text(numComma(data.cashTicketAmt50));
		    $("#rCashTicketAmt100").text(numComma(data.cashTicketAmt100));
		    $("#rCashTicketAmt500").text(numComma(data.cashTicketAmt500));

		    $("#rCashTicketAmt1000").text(numComma(data.cashTicketAmt1000));
		    $("#rCashTicketAmt5000").text(numComma(data.cashTicketAmt5000));
		    $("#rCashTicketAmt10000").text(numComma(data.cashTicketAmt10000));
		    $("#rCashTicketAmt50000").text(numComma(data.cashTicketAmt50000));

		    $("#rCashTicketAmt100000").text(numComma(data.cashTicketAmt100000));
		    $("#rCashTicketAmtEct").text(numComma(data.cashTicketAmtEct));

		    $("#rExactAmtCash").text(numComma(data.exactAmtCash));
		    $("#rExactAmtGift").text(numComma(data.exactAmtGift));
		    $("#rLostAmtCash").text(numComma(data.lostAmtCash));
		    $("#rLostAmtGift").text(numComma(data.lostAmtGift));
		},
			function (result) {
				s_alert.pop(result.message);
			}
		);
	}
	
	function numComma(num) {

		if (num != null && num != undefined && num != "") {
			/*
			var len, point, str;
			
			num = num + "";
			console.log("num :::"+num)
			
			point = num.length % 3 ;
			len = num.length;

			str = num.substring(0, point);
			while (point < len) {
				if (str != "") str += ",";
				str += num.substring(point, point + 3);
			    point += 3;
			}
			*/
			var regexp = /\B(?=(\d{3})+(?!\d))/g;
			return num.toString().replace(regexp, ',');

		} else {
			str = 0
		}

		return str;
	}

</script>

