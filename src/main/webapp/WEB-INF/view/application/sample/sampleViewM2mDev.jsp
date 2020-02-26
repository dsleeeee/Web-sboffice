<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ufn" uri="solbipos/function" %>


<style>
.sam-tbl {padding:40px}
.sam-tbl table {width:100%;}
.sam-tbl table th,table td  {font-size:13px; text-align:center; padding:10px; border:1px solid #ddd}
.sam-tbl table th {background-color:#f6f6f6; color:#222}
.sam-tbl table td a {font-size:13px; border-radius:4px; padding:2px 5px;transition:all 0.2s; background:#1e88e5; border:1px solid #1e88e5; color:#fff}
.sam-tbl table td a:hover {background:#174291; transition:all 0.2s;}
</style>

<div class="sam-tbl">

	
	<p class="bk mb10">Tonymory 샘플 page</p>
	<table>
		<colgroup>
			<col style="width:20%">
			<col style="width:20%">
			<col style="width:20%">
			<col style="width:20%">
			<col style="width:20%">
		</colgroup>
		<thead>
			<tr>
				<th>샘플 페이지 명</th>
				<th>파일명</th>
				<th>화면 ID</th>
				<th>url</th>
				<th>링크</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>로그인</td>
				<td>sampleViewTm01.jsp</td>
				<td>-</td>
				<td>/sample40.sb</td>				
				<td><a href="/sample40.sb" target="_blank">바로가기</a></td>
			</tr>
			<tr>
				<td>메인화면</td>
				<td>sampleViewTm02.jsp</td>
				<td>DP_WB_001</td>
				<td>/sample41.sb</td>				
				<td><a href="/sample41.sb" target="_blank">바로가기</a></td>
			</tr>
			<tr>
				<td class="bk">제품등록</td>
				<td>sampleViewTm12.jsp</td>
				<td>DP_WB_37</td>
				<td>/sample51.sb</td>				
				<td><a href="/sample51.sb" target="_blank">바로가기</a></td>
			</tr>			
			<tr>
				<td class="bk">제품별 수주현황</td>
				<td>sampleViewTm03.jsp</td>
				<td>DP_WB_048</td>
				<td>/sample42.sb</td>				
				<td><a href="/sample42.sb" target="_blank">바로가기</a></td>
			</tr>
			<tr>
				<td>주문등록</td>
				<td>sampleViewTm04.jsp</td>
				<td>DP_WB_059</td>
				<td>/sample43.sb</td>				
				<td><a href="/sample43.sb" target="_blank">바로가기</a></td>
			</tr>
			<tr>
				<td>판촉마일리지 관리</td>
				<td>sampleViewTm05.jsp</td>
				<td>DP_WB_066</td>
				<td>/sample44.sb</td>				
				<td><a href="/sample44.sb" target="_blank">바로가기</a></td>
			</tr>
			<tr>
				<td>판촉마일리지 현황</td>
				<td>sampleViewTm06.jsp</td>
				<td>DP_WB_067</td>
				<td>/sample45.sb</td>				
				<td><a href="/sample45.sb" target="_blank">바로가기</a></td>
			</tr>
			<tr>
				<td>판촉마일리지 내역조회</td>
				<td>sampleViewTm07.jsp</td>
				<td>DP_WB_068</td>
				<td>/sample46.sb</td>				
				<td><a href="/sample46.sb" target="_blank">바로가기</a></td>
			</tr>
			<tr>
				<td>당일 매출 현황</td>
				<td>sampleViewTm08.jsp</td>
				<td>DP_WB_078</td>
				<td>/sample47.sb</td>				
				<td><a href="/sample47.sb" target="_blank">바로가기</a></td>
			</tr>
			<tr>
				<td>매출 업로드 등록</td>
				<td>sampleViewTm09.jsp</td>
				<td>DP_WB_142</td>
				<td>/sample48.sb</td>				
				<td><a href="/sample48.sb" target="_blank">바로가기</a></td>
			</tr>
			<tr>
				<td>매출 업로드 확정</td>
				<td>sampleViewTm10.jsp</td>
				<td>DP_WB_143</td>
				<td>/sample49.sb</td>				
				<td><a href="/sample49.sb" target="_blank">바로가기</a></td>
			</tr>
			<tr>
				<td>일별-제품판매실적조회</td>
				<td>sampleViewTm11.jsp</td>
				<td>DP_WB_146</td>
				<td>/sample50.sb</td>				
				<td><a href="/sample50.sb" target="_blank">바로가기</a></td>
			</tr>
		</tbody>
	</table>

	<p class="bk mt30 mb10">샘플 UI 공통요소</p>
	<table>
		<colgroup>
			<col style="width:25%">
			<col style="width:25%">
			<col style="width:25%">
			<col style="width:25%">
		</colgroup>
		<thead>
			<tr>
				<th>샘플 페이지 명</th>
				<th>파일명</th>
				<th>url</th>
				<th>링크</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>CSS class</td>
				<td>sampleViewM2mCss.jsp</td>
				<td>/sample10.sb</td>
				<td><a href="/sample10.sb" target="_blank">바로가기</a></td>
			</tr>
			<tr>
				<td>그리드 영역</td>
				<td>sampleViewM2mLayout.jsp</td>
				<td>/sample11.sb</td>
				<td><a href="/sample11.sb" target="_blank">바로가기</a></td>
			</tr>
			<tr>
				<td>form 요소</td>
				<td>sampleViewM2mForm.jsp</td>
				<td>/sample12.sb</td>
				<td><a href="/sample12.sb" target="_blank">바로가기</a></td>
			</tr>
			<tr>
				<td>button</td>
				<td>sampleViewM2mBtn.jsp</td>
				<td>/sample13.sb</td>
				<td><a href="/sample13.sb" target="_blank">바로가기</a></td>
			</tr>
			<tr>
				<td>layer popup / tab</td>
				<td>sampleViewM2mLyTab.jsp</td>
				<td>/sample14.sb</td>
				<td><a href="/sample14.sb" target="_blank">바로가기</a></td>
			</tr>					
		</tbody>
	</table>
	
	<p class="bk mt30 mb10">샘플 page</p>
	<table>
		<colgroup>
			<col style="width:25%">
			<col style="width:25%">
			<col style="width:25%">
			<col style="width:25%">
		</colgroup>
		<thead>
			<tr>
				<th>샘플 페이지 명</th>
				<th>파일명</th>
				<th>url</th>
				<th>링크</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>월력 판매분석</td>
				<td>sampleViewM2mMonCal.jsp</td>
				<td>/sample20.sb</td>				
				<td><a href="/sample20.sb" target="_blank">바로가기</a></td>
			</tr>			
			<tr>
				<td>집계</td>
				<td>sampleViewM2mAgt.jsp</td>
				<td>/sample21.sb</td>				
				<td><a href="/sample21.sb" target="_blank">바로가기</a></td>
			</tr>
			<tr>
				<td>POS정산내역팝업</td>
				<td>sampleViewM2mPsh.jsp</td>
				<td>/sample22.sb</td>				
				<td><a href="/sample22.sb" target="_blank">바로가기</a></td>
			</tr>
			<tr>
				<td>Touchkey 등록</td>
				<td>sampleViewM2mTouch.jsp</td>
				<td>/sample23.sb</td>				
				<td><a href="/sample23.sb" target="_blank">바로가기</a></td>
			</tr>
		</tbody>
	</table>
	
</div>


