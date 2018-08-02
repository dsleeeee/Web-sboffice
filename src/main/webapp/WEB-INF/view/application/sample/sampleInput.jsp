<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- <link rel="stylesheet" href="https://netdna.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css" /> -->
<!-- <link rel="stylesheet" type="text/css" href="http://cdn.wijmo.com/5.latest/styles/wijmo.min.css"> -->
<%-- 
<script type="text/javascript" src="http://cdn.wijmo.com/5.latest/controls/wijmo.min.js"></script>
<script type="text/javascript" src="http://cdn.wijmo.com/5.latest/controls/wijmo.nav.min.js"></script>
<script type="text/javascript" src="http://cdn.wijmo.com/5.latest/controls/wijmo.input.min.js"></script>
 --%>
<%-- <script type="text/javascript" src="//cdnjs.cloudflare.com/ajax/libs/angular.js/1.1.1/angular.min.js"></script> --%>
<%-- <script type="text/javascript" src="/resource/vender/wijmo/js/angular.min.js"></script> --%>
<%-- <script type="text/javascript" src="http://code.jquery.com/jquery-2.1.0.min.js"></script> --%>
<%-- <script type="text/javascript" src="http://cdn.wijmo.com/5.latest/interop/angular/wijmo.angular.min.js"></script> --%>
<%-- <script type="text/javascript" src="/resource/vender/wijmo/js/wijmo.angular.min.js"></script> --%>

<link rel="stylesheet" type="text/css" href="/resource/vender/wijmo/css/wijmo.min.css" />
<script type="text/javascript" src="/resource/vender/wijmo/js/angular.js"></script>
<script type="text/javascript" src="/resource/vender/wijmo/js/jquery-2.1.0.min.js"></script>
<script type="text/javascript" src="/resource/vender/wijmo/js/wijmo.min.js"></script>
<script type="text/javascript" src="/resource/vender/wijmo/js/wijmo.nav.min.js"></script>
<script type="text/javascript" src="/resource/vender/wijmo/js/wijmo.input.js"></script>
<script type="text/javascript" src="/resource/vender/wijmo/js/wijmo.angular.js"></script>
<style>

.wj-dropdown {
  margin-bottom: 12pt;
}

body {
  margin-bottom: 24pt;
}

</style>
<!-- 
  <h1>
    MultiSelect
  </h1>

  <p>
    The MultiSelect control extends the ComboBox and adds 
    checkboxes to each item in the drop-down list.</p>
  <p>
    The control exposes the list of checked items through
    the <b>checkedItems</b> property:</p>    
  <div class="row">
    <div class="col-xs-5">
      <div id="theMultiSelect"></div>
      <label>
        Show "Select All" box
        <input id="selectAll" type="checkbox">
      </label>
    </div>
    <div class="col-xs-7">
      <p>
        <b>Checked Items:</b>
      </p>
      <ul id="checkedItems">
      </ul>
    </div>
  </div>
   -->
  
  <!-- Angular-->
  <div ng-app="app" ng-controller="appCtrl">
  <h4>Choose a country using a <b>ComboBox</b>:</h4>
    <!-- <wj-combo-box 
        ng-hide="false"
        ng-disabled="false"
        required="false"
        text="firstCountry" 
        items-source="countries"
        is-editable="false" 
        placeholder="country">
    </wj-combo-box> -->
    <!-- <wj-combo-box 
        ng-hide="false"
        ng-disabled="false"
        required="false"
        text="firstCountry" 
        items-source="countries"
        is-editable="true" 
        placeholder="country">
    </wj-combo-box> -->

    <wj-multi-select 
        ng-hide="false"
        ng-disabled="false"
        required="false"
        text="firstCountry" 
        items-source="countries"
        is-editable="true" 
        placeholder="country">
    </wj-combo-box>
     
    <!-- <b>{{firstCountry}}</b> -->
  </div>
  
  
<script>

 /*  
onload = function() {
  // MultiSelect
  var theMultiSelect = new wijmo.input.MultiSelect('#theMultiSelect', {
    placeholder: 'Countries',
    headerFormat: '{count:n0} countries',
    displayMemberPath: 'country',
    itemsSource: getData(),
    checkedItemsChanged: function (s, e) {
      var arr = s.checkedItems,
          html = '<li><input type=\'text\' />';
      for (var i = 0; i < arr.length; i++) {
        html += wijmo.format('<li>{country}</li>', arr[i]);
      }
      console.log('html : '+ html);
      
      document.getElementById('checkedItems').innerHTML = html;
    }
  });
  
  // toggle 'select all' checkbox
  document.getElementById('selectAll').addEventListener('click', function(e) {
    theMultiSelect.showSelectAllCheckbox = e.target.checked;
  })
  
  // list of country GDP and populations
  // https://en.wikipedia.org/wiki/List_of_IMF_ranked_countries_by_GDP
  function getData() {
    return [
      { id: 1, country: 'Luxembourg', gdpm: 57825, popk: 563, gdpcap: 102708 },
      { id: 2, country: 'Switzerland', gdpm: 664005, popk: 8238, gdpcap: 80602 },
      { id: 3, country: 'Norway', gdpm: 388315, popk: 5205, gdpcap: 74604 },
      { id: 4, country: 'Macao', gdpm: 46178, popk: 647, gdpcap: 71372 },
      { id: 5, country: 'Qatar', gdpm: 166908, popk: 2421, gdpcap: 68941 },
      { id: 6, country: 'Ireland', gdpm: 283716, popk: 4635, gdpcap: 61211 },
      { id: 7, country: 'United States', gdpm: 18036650, popk: 321601, gdpcap: 56083 },
      { id: 8, country: 'Singapore', gdpm: 292734, popk: 5535, gdpcap: 52887 },
      { id: 9, country: 'Denmark', gdpm: 295091, popk: 5660, gdpcap: 52136 },
      { id: 10, country: 'Australia', gdpm: 1225286, popk: 23940, gdpcap: 51181 },
      { id: 11, country: 'Iceland', gdpm: 16718, popk: 333, gdpcap: 50204 },
      { id: 12, country: 'Sweden', gdpm: 493042, popk: 9851, gdpcap: 50049 },
      { id: 13, country: 'San Marino', gdpm: 1558, popk: 31, gdpcap: 50258 },
      { id: 14, country: 'Netherlands', gdpm: 750696, popk: 16937, gdpcap: 44322 },
      { id: 15, country: 'United Kingdom', gdpm: 2858482, popk: 65110, gdpcap: 43902 },
      { id: 16, country: 'Austria', gdpm: 374261, popk: 8621, gdpcap: 43412 },
      { id: 17, country: 'Canada', gdpm: 1550537, popk: 35825, gdpcap: 43280 },
      { id: 18, country: 'Finland', gdpm: 232077, popk: 5472, gdpcap: 42411 },
      { id: 19, country: 'Germany', gdpm: 3365293, popk: 82176, gdpcap: 40952 },
      { id: 20, country: 'Belgium', gdpm: 454288, popk: 11209, gdpcap: 40528 },
      { id: 21, country: 'United Arab Emirates', gdpm: 370296, popk: 9581, gdpcap: 38648 },
      { id: 22, country: 'France', gdpm: 2420163, popk: 64275, gdpcap: 37653 },
      { id: 23, country: 'New Zealand', gdpm: 172257, popk: 4647, gdpcap: 37068 },
      { id: 24, country: 'Israel', gdpm: 299413, popk: 8377, gdpcap: 35742 },
      { id: 25, country: 'Japan', gdpm: 4124211, popk: 126981, gdpcap: 32478 },
      { id: 26, country: 'Brunei Darussalam', gdpm: 12930, popk: 417, gdpcap: 31007 },
      { id: 27, country: 'Italy', gdpm: 1815759, popk: 60796, gdpcap: 29866 },
      { id: 28, country: 'Puerto Rico', gdpm: 102906, popk: 3474, gdpcap: 29621 },
      { id: 29, country: 'Kuwait', gdpm: 114079, popk: 4110, gdpcap: 27756 },
      { id: 30, country: 'South Korea', gdpm: 1377873, popk: 50617, gdpcap: 27221 },
      { id: 31, country: 'Spain', gdpm: 1199715, popk: 46423, gdpcap: 25843 },
      { id: 32, country: 'The Bahamas', gdpm: 8854, popk: 364, gdpcap: 24324 },
      { id: 33, country: 'Bahrain', gdpm: 31119, popk: 1294, gdpcap: 24048 },
      { id: 34, country: 'Cyprus', gdpm: 19330, popk: 847, gdpcap: 22821 },
      { id: 35, country: 'Malta', gdpm: 9752, popk: 429, gdpcap: 22731 },
      { id: 36, country: 'Taiwan', gdpm: 523006, popk: 23492, gdpcap: 22263 },
      { id: 37, country: 'Slovenia', gdpm: 42798, popk: 2063, gdpcap: 20745 },
      { id: 38, country: 'Saudi Arabia', gdpm: 646002, popk: 31386, gdpcap: 20582 },
      { id: 39, country: 'Portugal', gdpm: 199032, popk: 10411, gdpcap: 19117 },
      { id: 40, country: 'Trinidad and Tobago', gdpm: 24631, popk: 1358, gdpcap: 18137 },
      { id: 41, country: 'Greece', gdpm: 195320, popk: 10858, gdpcap: 17988 },
      { id: 42, country: 'Czech Republic', gdpm: 185156, popk: 10538, gdpcap: 17570 },
      { id: 43, country: 'Estonia', gdpm: 22704, popk: 1313, gdpcap: 17291 },
      { id: 44, country: 'Equatorial Guinea', gdpm: 13819, popk: 799, gdpcap: 17295 },
      { id: 45, country: 'Oman', gdpm: 64121, popk: 3840, gdpcap: 16698 },
      { id: 46, country: 'St. Kitts and Nevis', gdpm: 915, popk: 56, gdpcap: 16339 },
      { id: 47, country: 'Palau', gdpm: 287, popk: 18, gdpcap: 15944 },
      { id: 48, country: 'Slovakia', gdpm: 86629, popk: 5421, gdpcap: 15980 },
      { id: 49, country: 'Barbados', gdpm: 4385, popk: 280, gdpcap: 15660 },
      { id: 50, country: 'Uruguay', gdpm: 53107, popk: 3416, gdpcap: 15546 },
    ];
  }
}
 */
</script>

<script type='text/javascript'>//<![CDATA[

// define app, include Wijmo 5 directives
var app = angular.module('app', ['wj']);

// controller
app.controller('appCtrl', function ($scope) {

    // list of countries to select from
    $scope.countries = [
        'Afghanistan', 'Albania', 'Algeria', 'American Samoa', 'Andorra', 'Angola', 'Anguilla',
        'Antigua', 'Argentina', 'Armenia', 'Aruba', 'Australia', 'Austria', 'Azerbaijan', 
        'Bahamas', 'Bahrain', 'Bangladesh', 'Barbados', 'Belarus', 'Belgium', 'Belize',
        'Benin', 'Bermuda', 'Bhutan', 'Bolivia', 'Bonaire', 'Bosnia', 'Botswana', 'Brazil', 
        'Brunei', 'Bulgaria', 'Burkina Faso', 'Burundi', 'Cambodia', 'Cameroon', 'Canada',
        'Canary Islands', 'Cape Verde', 'Cayman Islands', 'Central African Republic', 'Chad',
        'Channel Islands', 'Chile', 'China', 'Christmas Island', 'Cocos Island', 'Colombia', 
        'Comoros', 'Congo', 'Cook Islands', 'Costa Rica', "Cote D'Ivoire", 'Croatia', 
        'Cuba', 'Curacao', 'Cyprus', 'Czech Republic', 'Denmark', 'Djibouti', 'Dominica', 
        'Dominican Republic', 'East Timor', 'Ecuador', 'Egypt', 'El Salvador', 'Equatorial Guinea',
        'Eritrea', 'Estonia', 'Ethiopia', 'Falkland Islands', 'Faroe Islands', 'Fiji', 'Finland',
        'France', 'French Guiana', 'French Polynesia', 'French Southern Ter', 'Gabon', 'Gambia', 
        'Georgia', 'Germany', 'Ghana', 'Gibraltar', 'Great Britain', 'Greece', 'Greenland', 
        'Grenada', 'Guadeloupe', 'Guam', 'Guatemala', 'Guinea', 'Guyana', 'Haiti', 'Honduras',
        'Hong Kong', 'Hungary', 'Iceland', 'India', 'Indonesia', 'Iran', 'Iraq', 'Ireland',
        'Isle of Man', 'Israel', 'Italy', 'Jamaica', 'Japan', 'Jordan', 'Kazakhstan', 'Kenya', 
        'Kiribati', 'Korea North', 'Korea South', 'Kuwait', 'Kyrgyzstan', 'Laos', 'Latvia', 'Lebanon',
        'Lesotho', 'Liberia', 'Libya', 'Liechtenstein', 'Lithuania', 'Luxembourg', 'Macau', 'Macedonia',
        'Madagascar', 'Malaysia', 'Malawi', 'Maldives', 'Mali', 'Malta', 'Marshall Islands', 'Martinique',
        'Mauritania', 'Mauritius', 'Mayotte', 'Mexico', 'Midway Islands', 'Moldova', 'Monaco',
        'Mongolia', 'Montserrat', 'Morocco', 'Mozambique', 'Myanmar', 'Nambia', 'Nauru', 'Nepal', 
        'Netherland Antilles', 'Netherlands', 'Nevis', 'New Caledonia', 'New Zealand', 'Nicaragua', 
        'Niger', 'Nigeria', 'Niue', 'Norfolk Island', 'Norway', 'Oman', 'Pakistan', 'Palau Island',
        'Palestine', 'Panama', 'Papua New Guinea', 'Paraguay', 'Peru', 'Philippines', 'Pitcairn Island',
        'Poland', 'Portugal', 'Puerto Rico', 'Qatar', 'Republic of Montenegro', 'Republic of Serbia',
        'Reunion', 'Romania', 'Russia', 'Rwanda', 'St Barthelemy', 'St Eustatius', 'St Helena', 
        'St Kitts-Nevis', 'St Lucia', 'St Maarten', 'Saipan', 'Samoa', 'Samoa American', 'San Marino',
        'Saudi Arabia', 'Scotland', 'Senegal', 'Serbia', 'Seychelles', 'Sierra Leone', 'Singapore', 
        'Slovakia', 'Slovenia', 'Solomon Islands', 'Somalia', 'South Africa', 'Spain', 'Sri Lanka',
        'Sudan', 'Suriname', 'Swaziland', 'Sweden', 'Switzerland', 'Syria', 'Tahiti', 'Taiwan',
        'Tajikistan', 'Tanzania', 'Thailand', 'Togo', 'Tokelau', 'Tonga', 'Trinidad Tobago', 
        'Tunisia', 'Turkey', 'Turkmenistan', 'Turks & Caicos Is', 'Tuvalu', 'Uganda', 'Ukraine',
        'United Arab Emirates', 'United Kingdom', 'United States of America', 'Uruguay', 'Uzbekistan',
        'Vanuatu', 'Vatican City State', 'Venezuela', 'Vietnam', 'Virgin Islands (British)', 
        'Virgin Islands (USA)', 'Wake Island', 'Yemen', 'Zaire', 'Zambia', 'Zimbabwe'
    ];
        
});
//]]> 

</script>


