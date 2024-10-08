<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<style>
</style>

exInput

<br>

<div class="container">
  <div class="page-header">
    <h3>Create Wijmo Controls</h3>
  </div>

  <br>

  <div id="theComboBox"></div>
  
  <br>
  <div id="theInputDate"></div>
  
  <br>
  <div id="theAutoComplete"></div>
  
  <br>
  <input id="theInputTime">
  
</div>

<script>
onload = function() {
	
	function clickCombo(s, e) {
		alert(s["selectedItem"]["name"] + s["selectedItem"]["value"]);		
	}
	
	
	var comboData = ${ccu.getCommCode('005')};
	
	// create two DropDowns
	var theComboBox = new wijmo.input.ComboBox('#theComboBox', {
		displayMemberPath: 'name',
	    selectedValuePath: 'value',
		itemsSource: comboData,
	    selectedIndexChanged: function(s, e) {
	    	clickCombo(s, e);
	    }
  	});
	
	var theInputDate = new wijmo.input.InputDate('#theInputDate', {
		
	});
	
	
	var theAutoComplete = new wijmo.input.AutoComplete('#theAutoComplete', {
		displayMemberPath: 'country',
	    searchMemberPath: 'country,continent',
	    itemsSource: getData()
	});

	
	// list of country GDP and populations
	// https://en.wikipedia.org/wiki/List_of_IMF_ranked_countries_by_GDP
	function getData() {
		return [
			{ id: 1, country: 'Luxembourg', continent: 'Europe', gdpm: 57825, popk: 563, gdpcap: 102708 },
			{ id: 2, country: 'Switzerland', continent: 'Europe', gdpm: 664005, popk: 8238, gdpcap: 80602 },
			{ id: 3, country: 'Norway', continent: 'Europe', gdpm: 388315, popk: 5205, gdpcap: 74604 },
			{ id: 4, country: 'Macao', continent: 'Asia', gdpm: 46178, popk: 647, gdpcap: 71372 },
			{ id: 5, country: 'Qatar', continent: 'Africa', gdpm: 166908, popk: 2421, gdpcap: 68941 },
			{ id: 6, country: 'Ireland', continent: 'Europe', gdpm: 283716, popk: 4635, gdpcap: 61211 },
			{ id: 7, country: 'United States', continent: 'America', gdpm: 18036650, popk: 321601, gdpcap: 56083 },
			{ id: 8, country: 'Singapore', continent: 'Asia', gdpm: 292734, popk: 5535, gdpcap: 52887 },
			{ id: 9, country: 'Denmark', continent: 'Europe', gdpm: 295091, popk: 5660, gdpcap: 52136 },
			{ id: 10, country: 'Australia', continent: 'Oceania', gdpm: 1225286, popk: 23940, gdpcap: 51181 },
			{ id: 12, country: 'Sweden', continent: 'Europe', gdpm: 493042, popk: 9851, gdpcap: 50049 },
			{ id: 13, country: 'San Marino', continent: 'Europe', gdpm: 1558, popk: 31, gdpcap: 50258 },
			{ id: 14, country: 'Netherlands', continent: 'Europe', gdpm: 750696, popk: 16937, gdpcap: 44322 },
			{ id: 15, country: 'United Kingdom', continent: 'Europe', gdpm: 2858482, popk: 65110, gdpcap: 43902 },
			{ id: 16, country: 'Austria', continent: 'Europe', gdpm: 374261, popk: 8621, gdpcap: 43412 },
			{ id: 17, country: 'Canada', continent: 'America', gdpm: 1550537, popk: 35825, gdpcap: 43280 },
			{ id: 18, country: 'Finland', continent: 'Europe', gdpm: 232077, popk: 5472, gdpcap: 42411 },
			{ id: 19, country: 'Germany', continent: 'Europe', gdpm: 3365293, popk: 82176, gdpcap: 40952 },
			{ id: 20, country: 'Belgium', continent: 'Europe', gdpm: 454288, popk: 11209, gdpcap: 40528 },
			{ id: 21, country: 'United Arab Emirates', continent: 'Africa', gdpm: 370296, popk: 9581, gdpcap: 38648 },
			{ id: 22, country: 'France', continent: 'Europe', gdpm: 2420163, popk: 64275, gdpcap: 37653 },
			{ id: 23, country: 'New Zealand', continent: 'Oceania', gdpm: 172257, popk: 4647, gdpcap: 37068 },
			{ id: 24, country: 'Israel', continent: 'Africa', gdpm: 299413, popk: 8377, gdpcap: 35742 },
			{ id: 25, country: 'Japan', continent: 'Asia', gdpm: 4124211, popk: 126981, gdpcap: 32478 },
			{ id: 26, country: 'Brunei Darussalam', continent: 'Africa', gdpm: 12930, popk: 417, gdpcap: 31007 },
			{ id: 27, country: 'Italy', continent: 'Europe', gdpm: 1815759, popk: 60796, gdpcap: 29866 },
			{ id: 28, country: 'Puerto Rico', continent: 'America', gdpm: 102906, popk: 3474, gdpcap: 29621 },
			{ id: 29, country: 'Kuwait', continent: 'Africa', gdpm: 114079, popk: 4110, gdpcap: 27756 },
			{ id: 30, country: 'South Korea', continent: 'Asia', gdpm: 1377873, popk: 50617, gdpcap: 27221 },
			{ id: 31, country: 'Spain', continent: 'Europe', gdpm: 1199715, popk: 46423, gdpcap: 25843 },
			{ id: 32, country: 'The Bahamas', continent: 'America', gdpm: 8854, popk: 364, gdpcap: 24324 },
			{ id: 33, country: 'Bahrain', continent: 'Africa', gdpm: 31119, popk: 1294, gdpcap: 24048 },
			{ id: 34, country: 'Cyprus', continent: 'Europe', gdpm: 19330, popk: 847, gdpcap: 22821 },
			{ id: 35, country: 'Malta', continent: 'Europe', gdpm: 9752, popk: 429, gdpcap: 22731 },
			{ id: 36, country: 'Taiwan', continent: 'Asia', gdpm: 523006, popk: 23492, gdpcap: 22263 },
			{ id: 37, country: 'Slovenia', continent: 'Europe', gdpm: 42798, popk: 2063, gdpcap: 20745 },
			{ id: 38, country: 'Saudi Arabia', continent: 'Africa', gdpm: 646002, popk: 31386, gdpcap: 20582 },
			{ id: 39, country: 'Portugal', continent: 'Europe', gdpm: 199032, popk: 10411, gdpcap: 19117 },
			{ id: 40, country: 'Trinidad and Tobago', continent: 'America', gdpm: 24631, popk: 1358, gdpcap: 18137 },
			{ id: 41, country: 'Greece', continent: 'Europe', gdpm: 195320, popk: 10858, gdpcap: 17988 },
			{ id: 42, country: 'Czech Republic', continent: 'Europe', gdpm: 185156, popk: 10538, gdpcap: 17570 },
			{ id: 43, country: 'Estonia', continent: 'Europe', gdpm: 22704, popk: 1313, gdpcap: 17291 },
			{ id: 44, country: 'Equatorial Guinea', continent: 'Africa', gdpm: 13819, popk: 799, gdpcap: 17295 },
			{ id: 45, country: 'Oman', continent: 'Africa', gdpm: 64121, popk: 3840, gdpcap: 16698 },
			{ id: 46, country: 'St. Kitts and Nevis', continent: 'America', gdpm: 915, popk: 56, gdpcap: 16339 },
			{ id: 47, country: 'Palau', continent: 'Asia', gdpm: 287, popk: 18, gdpcap: 15944 },
			{ id: 48, country: 'Slovakia', continent: 'Europe', gdpm: 86629, popk: 5421, gdpcap: 15980 },
			{ id: 49, country: 'Barbados', continent: 'America', gdpm: 4385, popk: 280, gdpcap: 15660 },
			{ id: 50, country: 'Uruguay', continent: 'America', gdpm: 53107, popk: 3416, gdpcap: 15546 },
			];
	}

	var theInputTime = new wijmo.input.InputTime('#theInputTime', {
		format: 'h:mm tt', // default format, with AM/PM
		step: 30, // every 30 minutes
		isEditable: true, // user can enter values not on the list
	});

}
</script>


























