<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- <c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/> --%>

<style>
.sam-tbl {margin-top:50px}
.sam-tbl table {width:100%}
.sam-tbl table thead th,.sam-tbl table tbody th,.sam-tbl table tbody td  {position:relative; font-size:13px; text-align:center; padding:3px; line-height:20px; border:1px solid #ddd}
.sam-tbl table thead th,.sam-tbl table tbody th {background-color:#f6f6f6; color:#222}
.sam-tbl table thead th {padding:5px}
</style>

<div class="subCon" ng-controller="">

	<div id="theGrid"></div>

</div>


<script>

onload = function () {

	 // generate some random data
	 var countries = 'US,Germany,UK,Japan,Italy,Greece'.split(','),
	 	  products = 'Phones,Computers,Cameras,Stereos'.split(','),
	 	  category = 'Desktops,Laptops,Mobiles,Tablets'.split(','),
	 	  etc = 'etc_1,etc_2,etc_3,etc_4'.split(','),
	
	 	 data = [];
	  
		 for (var i = 0; i < 200; i++) {
		   data.push({
		     id: i,
		     country: countries[i % countries.length],
		     product: products[i % products.length],
		     category: category[i % category.length],
		     etc: etc[i % etc.length],
		     dt1: Math.random() * 5000,
		     sales: Math.random() * 10000,
		     expenses: Math.random() * 5000,
		     profit: Math.random() * 5000,
		   });
		}

/* 		// create a group to show the grand totals
		var grandTotalsGroup = new wijmo.collections.PropertyGroupDescription(
			'Grand Total',
			function(item, propName) {
		       return '';
		    }
		); */
		
		// create a group to show the grand totals
		var grpLv1 = new wijmo.collections.PropertyGroupDescription('GrandTotal');
			grpLv2 = new wijmo.collections.PropertyGroupDescription('country');
			grpLv3 = new wijmo.collections.PropertyGroupDescription('product');
			grpLv4 = new wijmo.collections.PropertyGroupDescription('category');
			grpLv5 = new wijmo.collections.PropertyGroupDescription('etc');


		// grid with custom aggregates
	  var theGrid = new wijmo.grid.FlexGrid('#theGrid', {
	  	autoGenerateColumns: false,
		  columns: [ // column definitions with aggregates
	  		{ binding: 'id', header: 'ID', width: 60, isReadOnly: true },
	    	{ binding: 'country', header: 'Country' },
	    	{ binding: 'product', header: 'Product'},
	    	{ binding: 'category', header: 'category'},
	    	{ binding: 'etc', header: 'etc'},
	    	{ binding: 'dt1', header: 'dt1', aggregate: 'Sum' },
	    	{ binding: 'sales', header: 'Sales', aggregate: 'Sum' },
	    	{ binding: 'expenses', header: 'Expenses', aggregate: 'Sum' },
	      	{ binding: 'profit', header: 'Profit', aggregate: 'Sum' }
	  	],
	    itemsSource: new wijmo.collections.CollectionView(data, {
            groupDescriptions: [
            	grpLv1,
            	grpLv2,
            	grpLv3,
            	grpLv4,
            	grpLv5	
            ]
	    })
	    
	  });

	  // start collapsed
	  theGrid.collapseGroupsToLevel(1);
	  
	  // custom cell calculation
	  theGrid.formatItem.addHandler(function(s, e) {
	  	
	   s.rows.forEach(function(row) {
	     if(row instanceof wijmo.grid.GroupRow){
	       var groupProp=row.dataItem.groupDescription.propertyName;
	       var className=null;
	       switch(groupProp){
	           case "GrandTotal":className="grp-lv-1";break;
	           case "country":className="grp-lv-2";break;
	           case "product":className="grp-lv-3";break;
	           case "category":className="grp-lv-4";break;
	           case "etc":className="grp-lv-5";break;
	       }
	       if(className){
	         row.cssClass=className;
	       }
	     }
	     
	   });
	  });
	   
	}
</script>