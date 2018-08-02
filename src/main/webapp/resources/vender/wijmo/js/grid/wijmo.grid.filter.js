/*
    *
    * Wijmo Library 5.20173.405
    * http://wijmo.com/
    *
    * Copyright(c) GrapeCity, Inc.  All rights reserved.
    *
    * Licensed under the GrapeCity Commercial License.
    * sales@wijmo.com
    * wijmo.com/products/wijmo-5/license/
    *
    */
var wijmo;
(function (wijmo) {
    var grid;
    (function (grid) {
        var filter;
        (function (filter) {
            'use strict';
        })(filter = grid.filter || (grid.filter = {}));
    })(grid = wijmo.grid || (wijmo.grid = {}));
})(wijmo || (wijmo = {}));

var wijmo;
(function (wijmo) {
    var grid;
    (function (grid) {
        var filter;
        (function (filter) {
            'use strict';
            /**
             * Defines a value filter for a column on a @see:FlexGrid control.
             *
             * Value filters contain an explicit list of values that should be
             * displayed by the grid.
             */
            var ValueFilter = /** @class */ (function () {
                /**
                 * Initializes a new instance of the @see:ValueFilter class.
                 *
                 * @param column The column to filter.
                 */
                function ValueFilter(column) {
                    this._maxValues = 250;
                    this._sortValues = true;
                    this._col = column;
                    this._bnd = column.binding ? new wijmo.Binding(column.binding) : null;
                }
                Object.defineProperty(ValueFilter.prototype, "showValues", {
                    /**
                     * Gets or sets an object with all the formatted values that should be
                     * shown on the value list.
                     */
                    get: function () {
                        return this._values;
                    },
                    set: function (value) {
                        this._values = value;
                    },
                    enumerable: true,
                    configurable: true
                });
                Object.defineProperty(ValueFilter.prototype, "filterText", {
                    /**
                     * Gets or sets a string used to filter the list of display values.
                     */
                    get: function () {
                        return this._filterText;
                    },
                    set: function (value) {
                        this._filterText = wijmo.asString(value);
                    },
                    enumerable: true,
                    configurable: true
                });
                Object.defineProperty(ValueFilter.prototype, "maxValues", {
                    /**
                     * Gets or sets the maximum number of elements on the list of display values.
                     *
                     * Adding too many items to the list makes searching difficult and hurts
                     * performance. This property limits the number of items displayed at any time,
                     * but users can still use the search box to filter the items they are
                     * interested in.
                     *
                     * This property is set to 250 by default.
                     *
                     * This code changes the value to 1,000,000, effectively listing all unique
                     * values for the field:
                     *
                     * <pre>// change the maxItems property for the 'id' column:
                     * var f = new wijmo.grid.filter.FlexGridFilter(s);
                     * f.getColumnFilter('id').valueFilter.maxValues = 1000000;</pre>
                     */
                    get: function () {
                        return this._maxValues;
                    },
                    set: function (value) {
                        this._maxValues = wijmo.asNumber(value, false, true);
                    },
                    enumerable: true,
                    configurable: true
                });
                Object.defineProperty(ValueFilter.prototype, "uniqueValues", {
                    /**
                     * Gets or sets an array containing the unique values to be displayed on the list.
                     *
                     * If this property is set to null, the list will be filled based on the grid data.
                     *
                     * Explicitly assigning the list of unique values is more efficient than building
                     * the list from the data, and is required for value filters to work properly when
                     * the data is filtered on the server (because in this case some values might not
                     * be present on the client so the list will be incomplete).
                     *
                     * By default, the filter editor will sort the unique values when displaying them
                     * to the user. If you want to prevent that and show the values in the order you
                     * provided, set the @see:sortValues property to false.
                     *
                     * For example, the code below provides a list of countries to be used in the
                     * @see:ValueFilter for the column bound to the 'country' field:
                     *
                     * <pre>// create filter for a FlexGrid
                     * var filter = new wijmo.grid.filter.FlexGridFilter(grid);
                     * // assign list of unique values to country filter
                     * var cf = filter.getColumnFilter('country');
                     * cf.valueFilter.uniqueValues = countries;</pre>
                     */
                    get: function () {
                        return this._uniqueValues;
                    },
                    set: function (value) {
                        this._uniqueValues = wijmo.asArray(value);
                    },
                    enumerable: true,
                    configurable: true
                });
                Object.defineProperty(ValueFilter.prototype, "sortValues", {
                    /**
                     * Gets or sets a value that determines whether the values should be sorted
                     * when displayed in the editor.
                     *
                     * This property is especially useful when you are using the @see:uniqueValues
                     * to provide a custom list of values property and you would like to preserve
                     * the order of the values.
                     */
                    get: function () {
                        return this._sortValues;
                    },
                    set: function (value) {
                        this._sortValues = wijmo.asBoolean(value);
                    },
                    enumerable: true,
                    configurable: true
                });
                Object.defineProperty(ValueFilter.prototype, "dataMap", {
                    /**
                     * Gets or sets the @see:DataMap used to convert raw values into display
                     * values shown when editing this filter.
                     */
                    get: function () {
                        return this._map;
                    },
                    set: function (value) {
                        this._map = wijmo.asType(value, grid.DataMap, true);
                    },
                    enumerable: true,
                    configurable: true
                });
                Object.defineProperty(ValueFilter.prototype, "column", {
                    // ** IColumnFilter
                    /**
                     * Gets the @see:Column to filter.
                     */
                    get: function () {
                        return this._col;
                    },
                    enumerable: true,
                    configurable: true
                });
                Object.defineProperty(ValueFilter.prototype, "isActive", {
                    /**
                     * Gets a value that indicates whether the filter is active.
                     *
                     * The filter is active if there is at least one value is selected.
                     */
                    get: function () {
                        return this._values != null && Object.keys(this._values).length > 0;
                    },
                    enumerable: true,
                    configurable: true
                });
                /**
                 * Gets a value that indicates whether a value passes the filter.
                 *
                 * @param value The value to test.
                 */
                ValueFilter.prototype.apply = function (value) {
                    var col = this.column;
                    // no binding or no values? accept everything
                    if (!this._bnd || !this._values || !Object.keys(this._values).length) {
                        return true;
                    }
                    // retrieve the formatted value
                    value = this._bnd.getValue(value);
                    value =
                        this.dataMap ? this.dataMap.getDisplayValue(value) :
                            col.dataMap ? col.dataMap.getDisplayValue(value) :
                                wijmo.Globalize.format(value, col.format);
                    // apply conditions
                    return this._values[value] != undefined;
                };
                /**
                 * Clears the filter.
                 */
                ValueFilter.prototype.clear = function () {
                    this.showValues = null;
                    this.filterText = null;
                };
                // ** IQueryInterface
                /**
                 * Returns true if the caller queries for a supported interface.
                 *
                 * @param interfaceName Name of the interface to look for.
                 */
                ValueFilter.prototype.implementsInterface = function (interfaceName) {
                    return interfaceName == 'IColumnFilter';
                };
                // ** implementation
                // get a list of unique values
                ValueFilter.prototype._getUniqueValues = function (col, filtered) {
                    var values = [];
                    // explicit list provided
                    if (this.uniqueValues) {
                        var uvalues = this.uniqueValues;
                        for (var i = 0; i < uvalues.length; i++) {
                            var value = uvalues[i];
                            values.push({ value: value, text: value.toString() });
                        }
                        return values;
                    }
                    // list not provided, get from data
                    var keys = {}, view = col.collectionView, src = view ? view.sourceCollection : [];
                    // apply all filters but this one (Excel-style filtering, TFS 133354)
                    if (filtered && view && view.sourceCollection && view.filter) {
                        // disable this filter
                        var sv = this.showValues;
                        this.showValues = null;
                        // apply all other filters
                        var nsrc = [];
                        for (var i = 0; i < src.length; i++) {
                            if (view.filter(src[i])) {
                                nsrc.push(src[i]);
                            }
                        }
                        src = nsrc;
                        // restore this filter
                        this.showValues = sv;
                    }
                    // format and add unique values to the 'values' array
                    for (var i = 0; i < src.length; i++) {
                        var value = col._binding.getValue(src[i]), text = this.dataMap ? this.dataMap.getDisplayValue(value) :
                            col.dataMap ? col.dataMap.getDisplayValue(value) :
                                wijmo.Globalize.format(value, col.format);
                        if (!keys[text]) {
                            keys[text] = true;
                            values.push({ value: value, text: text });
                        }
                    }
                    // done 
                    return values;
                };
                return ValueFilter;
            }());
            filter.ValueFilter = ValueFilter;
        })(filter = grid.filter || (grid.filter = {}));
    })(grid = wijmo.grid || (wijmo.grid = {}));
})(wijmo || (wijmo = {}));

var __extends = (this && this.__extends) || (function () {
    var extendStatics = Object.setPrototypeOf ||
        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
    return function (d, b) {
        extendStatics(d, b);
        function __() { this.constructor = d; }
        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
    };
})();
var wijmo;
(function (wijmo) {
    var grid;
    (function (grid) {
        var filter;
        (function (filter_1) {
            'use strict';
            /**
             * The editor used to inspect and modify @see:ValueFilter objects.
             *
             * This class is used by the @see:FlexGridFilter class; you
             * rarely use it directly.
             */
            var ValueFilterEditor = /** @class */ (function (_super) {
                __extends(ValueFilterEditor, _super);
                /**
                 * Initializes a new instance of the @see:ValueFilterEditor class.
                 *
                 * @param element The DOM element that hosts the control, or a selector
                 * for the host element (e.g. '#theCtrl').
                 * @param filter The @see:ValueFilter to edit.
                 */
                function ValueFilterEditor(element, filter) {
                    var _this = _super.call(this, element) || this;
                    // save reference to filter
                    _this._filter = wijmo.asType(filter, filter_1.ValueFilter, false);
                    // instantiate and apply template
                    var tpl = _this.getTemplate();
                    _this.applyTemplate('wj-control', tpl, {
                        _divFilter: 'div-filter',
                        _cbSelectAll: 'cb-select-all',
                        _spSelectAll: 'sp-select-all',
                        _divValues: 'div-values'
                    });
                    // localization
                    wijmo.setText(_this._spSelectAll, wijmo.culture.FlexGridFilter.selectAll);
                    // create sorted/filtered collection view with the values
                    _this._view = new wijmo.collections.CollectionView();
                    if (filter.sortValues) {
                        var sortBinding = filter.column.dataMap || filter.dataMap ? 'text' : 'value', asc = filter.column.dataType != wijmo.DataType.Boolean; // TFS 229224
                        _this._view.sortDescriptions.push(new wijmo.collections.SortDescription(sortBinding, asc));
                    }
                    _this._view.filter = _this._filterValues.bind(_this);
                    _this._view.collectionChanged.addHandler(_this._updateSelectAllCheck, _this);
                    // create search combo and value list
                    _this._filterText = '';
                    _this._cmbFilter = new wijmo.input.ComboBox(_this._divFilter, {
                        placeholder: wijmo.culture.FlexGridFilter.search
                    });
                    _this._lbValues = new wijmo.input.ListBox(_this._divValues, {
                        displayMemberPath: 'text',
                        checkedMemberPath: 'show',
                        itemsSource: _this._view,
                        itemFormatter: function (index, item) {
                            return item ? item : wijmo.culture.FlexGridFilter.null;
                        }
                    });
                    // add event listeners
                    _this._cmbFilter.textChanged.addHandler(_this._filterTextChanged, _this);
                    _this._cbSelectAll.addEventListener('click', _this._cbSelectAllClicked.bind(_this));
                    // initialize all values
                    _this.updateEditor();
                    return _this;
                }
                Object.defineProperty(ValueFilterEditor.prototype, "filter", {
                    /**
                     * Gets a reference to the @see:ValueFilter being edited.
                     */
                    get: function () {
                        return this._filter;
                    },
                    enumerable: true,
                    configurable: true
                });
                /**
                 * Updates editor with current filter settings.
                 */
                ValueFilterEditor.prototype.updateEditor = function () {
                    var _this = this;
                    setTimeout(function () {
                        _this._updateEditor(); // this may take a while...
                    });
                };
                ValueFilterEditor.prototype._updateEditor = function () {
                    var _this = this;
                    var col = this._filter.column, values = this._filter._getUniqueValues(col, true);
                    // honor isContentHtml property
                    this._lbValues.isContentHtml = col.isContentHtml;
                    // check the items that are currently selected
                    var showValues = this._filter.showValues;
                    if (!showValues || Object.keys(showValues).length == 0) {
                        for (var i = 0; i < values.length; i++) {
                            values[i].show = true;
                        }
                    }
                    else {
                        for (var key in showValues) {
                            for (var i = 0; i < values.length; i++) {
                                if (values[i].text == key) {
                                    values[i].show = true;
                                    break;
                                }
                            }
                        }
                    }
                    // populate list quickly and show it right away (TFS 261358)
                    this._view.pageSize = 20;
                    this._view.sourceCollection = values;
                    this._view.moveCurrentTo(null);
                    // finish populating the list after the initial values have become visible
                    setTimeout(function () {
                        // show all values
                        _this._view.pageSize = _this._filter.maxValues;
                        // load and apply filter
                        _this._cmbFilter.text = _this._filter.filterText;
                        _this._filterText = _this._cmbFilter.text.toLowerCase();
                    });
                };
                /**
                 * Clears the editor without applying changes to the filter.
                 */
                ValueFilterEditor.prototype.clearEditor = function () {
                    this._cmbFilter.text = '';
                    this._filterText = '';
                    this._view.pageSize = 0; // TFS 288369
                    this._view.refresh();
                    var values = this._view.items;
                    for (var i = 0; i < values.length; i++) {
                        values[i].show = false;
                    }
                    this._view.pageSize = this._filter.maxValues;
                };
                /**
                 * Updates filter to reflect the current editor values.
                 */
                ValueFilterEditor.prototype.updateFilter = function () {
                    // build list of values to show
                    // (clear filter if all values are selected)
                    var showValues = null, items = this._view.items;
                    if (this._filterText || this._cbSelectAll.indeterminate) {
                        showValues = {};
                        for (var i = 0; i < items.length; i++) {
                            var item = items[i];
                            if (item.show) {
                                showValues[item.text] = true;
                            }
                        }
                    }
                    // save to filter
                    this._filter.showValues = showValues;
                    this._filter.filterText = this._filterText;
                };
                // ** implementation
                // filter items on the list
                ValueFilterEditor.prototype._filterTextChanged = function () {
                    var _this = this;
                    if (this._toText) {
                        clearTimeout(this._toText);
                    }
                    this._toText = setTimeout(function () {
                        // apply the filter
                        var filter = _this._cmbFilter.text.toLowerCase();
                        if (filter != _this._filterText) {
                            _this._filterText = filter;
                            _this._view.refresh();
                            // select all items that pass the filter (Excel behavior)
                            _this._cbSelectAll.checked = true;
                            _this._cbSelectAllClicked();
                        }
                    }, 500);
                };
                // filter values for display
                ValueFilterEditor.prototype._filterValues = function (value) {
                    if (this._filterText) {
                        return value && value.text
                            ? value.text.toLowerCase().indexOf(this._filterText) > -1
                            : false;
                    }
                    return true;
                };
                // handle clicks on 'Select All' checkbox
                ValueFilterEditor.prototype._cbSelectAllClicked = function () {
                    var checked = this._cbSelectAll.checked, values = this._view.items;
                    for (var i = 0; i < values.length; i++) {
                        values[i].show = checked;
                    }
                    this._view.refresh();
                };
                // update state of 'Select All' checkbox when values are checked/unchecked
                ValueFilterEditor.prototype._updateSelectAllCheck = function () {
                    // count checked items
                    var checked = 0, values = this._view.items;
                    for (var i = 0; i < values.length; i++) {
                        if (values[i].show)
                            checked++;
                    }
                    // update checkbox
                    if (checked == 0) {
                        this._cbSelectAll.checked = false;
                        this._cbSelectAll.indeterminate = false;
                    }
                    else if (checked == values.length) {
                        this._cbSelectAll.checked = true;
                        this._cbSelectAll.indeterminate = false;
                    }
                    else {
                        this._cbSelectAll.indeterminate = true;
                    }
                    // REVIEW: disable Apply button if nothing is selected
                    //toggleClass(this._btnApply, 'wj-state-disabled', checked == 0);
                    //this._btnApply.style.cursor = (checked == 0) ? 'default' : '';
                };
                /**
                 * Gets or sets the template used to instantiate @see:ColumnFilterEditor controls.
                 */
                ValueFilterEditor.controlTemplate = '<div>' +
                    '<div wj-part="div-filter"></div>' +
                    '<div class="wj-listbox-item">' +
                    '<label>' +
                    '<input wj-part="cb-select-all" type="checkbox"> ' +
                    '<span wj-part="sp-select-all"></span>' +
                    '</label>' +
                    '</div>' +
                    '<div wj-part="div-values" style="height:150px"></div>' +
                    '</div>';
                return ValueFilterEditor;
            }(wijmo.Control));
            filter_1.ValueFilterEditor = ValueFilterEditor;
        })(filter = grid.filter || (grid.filter = {}));
    })(grid = wijmo.grid || (wijmo.grid = {}));
})(wijmo || (wijmo = {}));

var wijmo;
(function (wijmo) {
    var grid;
    (function (grid) {
        var filter;
        (function (filter) {
            'use strict';
            /**
             * Defines a condition filter for a column on a @see:FlexGrid control.
             *
             * Condition filters contain two conditions that may be combined
             * using an 'and' or an 'or' operator.
             *
             * This class is used by the @see:FlexGridFilter class; you will
             * rarely use it directly.
             */
            var ConditionFilter = /** @class */ (function () {
                /**
                 * Initializes a new instance of the @see:ConditionFilter class.
                 *
                 * @param column The column to filter.
                 */
                function ConditionFilter(column) {
                    this._c1 = new filter.FilterCondition();
                    this._c2 = new filter.FilterCondition();
                    this._and = true;
                    this._col = column;
                    this._bnd = column.binding ? new wijmo.Binding(column.binding) : null;
                }
                Object.defineProperty(ConditionFilter.prototype, "condition1", {
                    /**
                     * Gets the first condition in the filter.
                     */
                    get: function () {
                        return this._c1;
                    },
                    enumerable: true,
                    configurable: true
                });
                Object.defineProperty(ConditionFilter.prototype, "condition2", {
                    /**
                     * Gets the second condition in the filter.
                     */
                    get: function () {
                        return this._c2;
                    },
                    enumerable: true,
                    configurable: true
                });
                Object.defineProperty(ConditionFilter.prototype, "and", {
                    /**
                     * Gets a value that indicates whether to combine the two conditions
                     * with an AND or an OR operator.
                     */
                    get: function () {
                        return this._and;
                    },
                    set: function (value) {
                        this._and = wijmo.asBoolean(value);
                        this._bnd = this._col && this._col.binding // REVIEW: why is this needed?
                            ? new wijmo.Binding(this._col.binding)
                            : null;
                    },
                    enumerable: true,
                    configurable: true
                });
                Object.defineProperty(ConditionFilter.prototype, "dataMap", {
                    /**
                     * Gets or sets the @see:DataMap used to convert raw values into display
                     * values shown when editing this filter.
                     */
                    get: function () {
                        return this._map;
                    },
                    set: function (value) {
                        this._map = wijmo.asType(value, grid.DataMap, true);
                    },
                    enumerable: true,
                    configurable: true
                });
                Object.defineProperty(ConditionFilter.prototype, "column", {
                    // ** IColumnFilter
                    /**
                     * Gets the @see:Column to filter.
                     */
                    get: function () {
                        return this._col;
                    },
                    enumerable: true,
                    configurable: true
                });
                Object.defineProperty(ConditionFilter.prototype, "isActive", {
                    /**
                     * Gets a value that indicates whether the filter is active.
                     *
                     * The filter is active if at least one of the two conditions
                     * has its operator and value set to a valid combination.
                     */
                    get: function () {
                        return this._c1.isActive || this._c2.isActive;
                    },
                    enumerable: true,
                    configurable: true
                });
                /**
                 * Returns a value indicating whether a value passes this filter.
                 *
                 * @param value The value to test.
                 */
                ConditionFilter.prototype.apply = function (value) {
                    var col = this._col, c1 = this._c1, c2 = this._c2;
                    // no binding or not active? accept everything
                    if (!this._bnd || !this.isActive) {
                        return true;
                    }
                    // retrieve the value
                    value = this._bnd.getValue(value);
                    if (col.dataMap) {
                        value = col.dataMap.getDisplayValue(value);
                    }
                    else if (wijmo.isDate(value)) {
                        if (wijmo.isString(c1.value) || wijmo.isString(c2.value)) {
                            value = wijmo.Globalize.format(value, col.format);
                        }
                    }
                    else if (wijmo.isNumber(value)) {
                        value = wijmo.Globalize.parseFloat(wijmo.Globalize.format(value, col.format));
                    }
                    // apply conditions
                    var rv1 = c1.apply(value), rv2 = c2.apply(value);
                    // combine results
                    if (c1.isActive && c2.isActive) {
                        return this._and ? rv1 && rv2 : rv1 || rv2;
                    }
                    else {
                        return c1.isActive ? rv1 : c2.isActive ? rv2 : true;
                    }
                };
                /**
                 * Clears the filter.
                 */
                ConditionFilter.prototype.clear = function () {
                    this._c1.clear();
                    this._c2.clear();
                    this.and = true;
                };
                // ** IQueryInterface
                /**
                 * Returns true if the caller queries for a supported interface.
                 *
                 * @param interfaceName Name of the interface to look for.
                 */
                ConditionFilter.prototype.implementsInterface = function (interfaceName) {
                    return interfaceName == 'IColumnFilter';
                };
                return ConditionFilter;
            }());
            filter.ConditionFilter = ConditionFilter;
        })(filter = grid.filter || (grid.filter = {}));
    })(grid = wijmo.grid || (wijmo.grid = {}));
})(wijmo || (wijmo = {}));

var __extends = (this && this.__extends) || (function () {
    var extendStatics = Object.setPrototypeOf ||
        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
    return function (d, b) {
        extendStatics(d, b);
        function __() { this.constructor = d; }
        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
    };
})();
var wijmo;
(function (wijmo) {
    var grid;
    (function (grid) {
        var filter;
        (function (filter_1) {
            'use strict';
            /**
             * The editor used to inspect and modify @see:ConditionFilter objects.
             *
             * This class is used by the @see:FlexGridFilter class; you
             * rarely use it directly.
             */
            var ConditionFilterEditor = /** @class */ (function (_super) {
                __extends(ConditionFilterEditor, _super);
                /**
                 * Initializes a new instance of the @see:ConditionFilterEditor class.
                 *
                 * @param element The DOM element that hosts the control, or a selector
                 * for the host element (e.g. '#theCtrl').
                 * @param filter The @see:ConditionFilter to edit.
                 */
                function ConditionFilterEditor(element, filter) {
                    var _this = _super.call(this, element) || this;
                    // save reference to filter
                    _this._filter = wijmo.asType(filter, filter_1.ConditionFilter, false);
                    // instantiate and apply template
                    var tpl = _this.getTemplate();
                    _this.applyTemplate('wj-control', tpl, {
                        _divHdr: 'div-hdr',
                        _divCmb1: 'div-cmb1',
                        _divVal1: 'div-val1',
                        _btnAnd: 'btn-and',
                        _btnOr: 'btn-or',
                        _spAnd: 'sp-and',
                        _spOr: 'sp-or',
                        _divCmb2: 'div-cmb2',
                        _divVal2: 'div-val2',
                    });
                    // localization
                    var strings = wijmo.culture.FlexGridFilter;
                    wijmo.setText(_this._divHdr, strings.header);
                    wijmo.setText(_this._spAnd, strings.and);
                    wijmo.setText(_this._spOr, strings.or);
                    // create combos and value editors
                    _this._cmb1 = _this._createOperatorCombo(_this._divCmb1);
                    _this._cmb2 = _this._createOperatorCombo(_this._divCmb2);
                    _this._val1 = _this._createValueInput(_this._divVal1);
                    _this._val2 = _this._createValueInput(_this._divVal2);
                    // add event listeners
                    var andOr = _this._btnAndOrChanged.bind(_this);
                    _this._btnAnd.addEventListener('change', andOr);
                    _this._btnOr.addEventListener('change', andOr);
                    // initialize all values
                    _this.updateEditor();
                    return _this;
                }
                Object.defineProperty(ConditionFilterEditor.prototype, "filter", {
                    /**
                     * Gets a reference to the @see:ConditionFilter being edited.
                     */
                    get: function () {
                        return this._filter;
                    },
                    enumerable: true,
                    configurable: true
                });
                /**
                 * Updates editor with current filter settings.
                 */
                ConditionFilterEditor.prototype.updateEditor = function () {
                    // initialize conditions
                    var c1 = this._filter.condition1, c2 = this._filter.condition2;
                    this._cmb1.selectedValue = c1.operator;
                    this._cmb2.selectedValue = c2.operator;
                    if (this._val1 instanceof wijmo.input.ComboBox) {
                        this._val1.text = wijmo.changeType(c1.value, wijmo.DataType.String, null);
                        this._val2.text = wijmo.changeType(c2.value, wijmo.DataType.String, null);
                    }
                    else {
                        this._val1.value = c1.value;
                        this._val2.value = c2.value;
                    }
                    // initialize and/or buttons
                    this._btnAnd.checked = this._filter.and;
                    this._btnOr.checked = !this._filter.and;
                };
                /**
                 * Clears the editor without applying changes to the filter.
                 */
                ConditionFilterEditor.prototype.clearEditor = function () {
                    this._cmb1.selectedValue = this._cmb2.selectedValue = null;
                    this._val1.text = this._val2.text = null;
                    this._btnAnd.checked = true;
                    this._btnOr.checked = false;
                };
                /**
                 * Updates filter to reflect the current editor values.
                 */
                ConditionFilterEditor.prototype.updateFilter = function () {
                    // initialize conditions
                    var col = this._filter.column, c1 = this._filter.condition1, c2 = this._filter.condition2;
                    c1.operator = this._cmb1.selectedValue;
                    c2.operator = this._cmb2.selectedValue;
                    if (this._val1 instanceof wijmo.input.ComboBox) {
                        // store condition values to the types specified by the column, except for 
                        // time values, which are dates but must be stored as strings (TFS 123969)
                        var dt = col.dataType == wijmo.DataType.Date ? wijmo.DataType.String : col.dataType;
                        c1.value = wijmo.changeType(this._val1.text, dt, col.format);
                        c2.value = wijmo.changeType(this._val2.text, dt, col.format);
                    }
                    else {
                        c1.value = this._val1.value;
                        c2.value = this._val2.value;
                    }
                    // initialize and/or operator
                    this._filter.and = this._btnAnd.checked;
                };
                // ** implementation
                // create operator combo
                ConditionFilterEditor.prototype._createOperatorCombo = function (element) {
                    // get operator list based on column data type
                    var col = this._filter.column, list = wijmo.culture.FlexGridFilter.stringOperators;
                    if (col.dataType == wijmo.DataType.Date && !this._isTimeFormat(col.format)) {
                        list = wijmo.culture.FlexGridFilter.dateOperators;
                    }
                    else if (col.dataType == wijmo.DataType.Number && !col.dataMap) {
                        list = wijmo.culture.FlexGridFilter.numberOperators;
                    }
                    else if (col.dataType == wijmo.DataType.Boolean && !col.dataMap) {
                        list = wijmo.culture.FlexGridFilter.booleanOperators;
                    }
                    // create and initialize the combo
                    var cmb = new wijmo.input.ComboBox(element);
                    cmb.itemsSource = list;
                    cmb.displayMemberPath = 'name';
                    cmb.selectedValuePath = 'op';
                    // return combo
                    return cmb;
                };
                // create operator input
                ConditionFilterEditor.prototype._createValueInput = function (e) {
                    var col = this._filter.column, ctl = null;
                    if (col.dataType == wijmo.DataType.Date && !this._isTimeFormat(col.format)) {
                        ctl = new wijmo.input.InputDate(e);
                        ctl.format = col.format;
                    }
                    else if (col.dataType == wijmo.DataType.Number && !col.dataMap) {
                        ctl = new wijmo.input.InputNumber(e);
                        ctl.format = col.format;
                    }
                    else {
                        ctl = new wijmo.input.ComboBox(e);
                        ctl.itemsSource =
                            this._filter.dataMap ? this._filter.dataMap.getDisplayValues() :
                                col.dataMap ? col.dataMap.getDisplayValues() :
                                    col.dataType == wijmo.DataType.Boolean ? [true, false] :
                                        null;
                    }
                    ctl.isRequired = false;
                    return ctl;
                };
                // checks whether a format represents a time (and not just a date)
                ConditionFilterEditor.prototype._isTimeFormat = function (fmt) {
                    if (!fmt)
                        return false;
                    fmt = wijmo.culture.Globalize.calendar.patterns[fmt] || fmt;
                    return /[Hmst]+/.test(fmt); // TFS 109409
                };
                // update and/or buttons
                ConditionFilterEditor.prototype._btnAndOrChanged = function (e) {
                    this._btnAnd.checked = e.target == this._btnAnd;
                    this._btnOr.checked = e.target == this._btnOr;
                };
                /**
                 * Gets or sets the template used to instantiate @see:ConditionFilterEditor controls.
                 */
                ConditionFilterEditor.controlTemplate = '<div>' +
                    '<div wj-part="div-hdr"></div>' +
                    '<div wj-part="div-cmb1"></div><br/>' +
                    '<div wj-part="div-val1"></div><br/>' +
                    '<div style="text-align:center">' +
                    '<label><input wj-part="btn-and" type="radio"> <span wj-part="sp-and"></span> </label>&nbsp;&nbsp;&nbsp;' +
                    '<label><input wj-part="btn-or" type="radio"> <span wj-part="sp-or"></span> </label>' +
                    '</div>' +
                    '<div wj-part="div-cmb2"></div><br/>' +
                    '<div wj-part="div-val2"></div><br/>' +
                    '</div>';
                return ConditionFilterEditor;
            }(wijmo.Control));
            filter_1.ConditionFilterEditor = ConditionFilterEditor;
        })(filter = grid.filter || (grid.filter = {}));
    })(grid = wijmo.grid || (wijmo.grid = {}));
})(wijmo || (wijmo = {}));

var wijmo;
(function (wijmo) {
    var grid;
    (function (grid) {
        var filter;
        (function (filter) {
            'use strict';
            /**
             * Defines a filter condition.
             *
             * This class is used by the @see:FlexGridFilter class; you will rarely have to use it directly.
             */
            var FilterCondition = /** @class */ (function () {
                function FilterCondition() {
                    this._op = null;
                }
                Object.defineProperty(FilterCondition.prototype, "operator", {
                    /**
                     * Gets or sets the operator used by this @see:FilterCondition.
                     */
                    get: function () {
                        return this._op;
                    },
                    set: function (value) {
                        this._op = wijmo.asEnum(value, Operator, true);
                    },
                    enumerable: true,
                    configurable: true
                });
                Object.defineProperty(FilterCondition.prototype, "value", {
                    /**
                     * Gets or sets the value used by this @see:FilterCondition.
                     */
                    get: function () {
                        return this._val;
                    },
                    set: function (value) {
                        this._val = value;
                        this._strVal = wijmo.isString(value) ? value.toString().toLowerCase() : null;
                    },
                    enumerable: true,
                    configurable: true
                });
                Object.defineProperty(FilterCondition.prototype, "isActive", {
                    /**
                     * Gets a value that indicates whether the condition is active.
                     */
                    get: function () {
                        switch (this._op) {
                            // no operator
                            case null:
                                return false;
                            // equals/does not equal do not require a value (can compare to null)
                            case Operator.EQ:
                            case Operator.NE:
                                return true;
                            // other operators require a value
                            default:
                                return this._val != null || this._strVal != null;
                        }
                    },
                    enumerable: true,
                    configurable: true
                });
                /**
                 * Clears the condition.
                 */
                FilterCondition.prototype.clear = function () {
                    this.operator = null;
                    this.value = null;
                };
                /**
                 * Returns a value that determines whether the given value passes this
                 * @see:FilterCondition.
                 *
                 * @param value The value to test.
                 */
                FilterCondition.prototype.apply = function (value) {
                    // use lower-case strings for all operations
                    var val = this._strVal || this._val;
                    if (wijmo.isString(value)) {
                        value = value.toLowerCase();
                    }
                    // treat null values as empty strings (TFS 247101)
                    if (wijmo.isString(val) && value == null) {
                        value = '';
                    }
                    // apply operator
                    switch (this._op) {
                        case null:
                            return true;
                        case Operator.EQ:
                            return wijmo.isDate(value) && wijmo.isDate(val)
                                ? wijmo.DateTime.sameDate(value, val)
                                : value == val;
                        case Operator.NE:
                            return value != val;
                        case Operator.GT:
                            return value > val;
                        case Operator.GE:
                            return value >= val;
                        case Operator.LT:
                            return value < val;
                        case Operator.LE:
                            return value <= val;
                        case Operator.BW:
                            return this._strVal && wijmo.isString(value)
                                ? value.indexOf(this._strVal) == 0
                                : false;
                        case Operator.EW:
                            return this._strVal && wijmo.isString(value) && value.length >= this._strVal.length
                                ? value.substr(value.length - this._strVal.length) == val
                                : false;
                        case Operator.CT:
                            return this._strVal && wijmo.isString(value)
                                ? value.indexOf(this._strVal) > -1
                                : false;
                        case Operator.NC:
                            return this._strVal && wijmo.isString(value)
                                ? value.indexOf(this._strVal) < 0
                                : false;
                    }
                    throw 'Unknown operator';
                };
                return FilterCondition;
            }());
            filter.FilterCondition = FilterCondition;
            /**
             * Specifies filter condition operators.
             */
            var Operator;
            (function (Operator) {
                /** Equals. */
                Operator[Operator["EQ"] = 0] = "EQ";
                /** Does not equal. */
                Operator[Operator["NE"] = 1] = "NE";
                /** Greater than. */
                Operator[Operator["GT"] = 2] = "GT";
                /** Greater than or equal to. */
                Operator[Operator["GE"] = 3] = "GE";
                /** Less than. */
                Operator[Operator["LT"] = 4] = "LT";
                /** Less than or equal to. */
                Operator[Operator["LE"] = 5] = "LE";
                /** Begins with. */
                Operator[Operator["BW"] = 6] = "BW";
                /** Ends with. */
                Operator[Operator["EW"] = 7] = "EW";
                /** Contains. */
                Operator[Operator["CT"] = 8] = "CT";
                /** Does not contain. */
                Operator[Operator["NC"] = 9] = "NC";
            })(Operator = filter.Operator || (filter.Operator = {}));
        })(filter = grid.filter || (grid.filter = {}));
    })(grid = wijmo.grid || (wijmo.grid = {}));
})(wijmo || (wijmo = {}));

var wijmo;
(function (wijmo) {
    var grid;
    (function (grid) {
        var filter;
        (function (filter) {
            'use strict';
            /**
             * Defines a filter for a column on a @see:FlexGrid control.
             *
             * The @see:ColumnFilter contains a @see:ConditionFilter and a
             * @see:ValueFilter; only one of them may be active at a time.
             *
             * This class is used by the @see:FlexGridFilter class; you
             * rarely use it directly.
             */
            var ColumnFilter = /** @class */ (function () {
                /**
                 * Initializes a new instance of the @see:ColumnFilter class.
                 *
                 * @param owner The @see:FlexGridFilter that owns this column filter.
                 * @param column The @see:Column to filter.
                 */
                function ColumnFilter(owner, column) {
                    this._owner = owner;
                    this._col = column;
                    this._valueFilter = new filter.ValueFilter(column);
                    this._conditionFilter = new filter.ConditionFilter(column);
                }
                Object.defineProperty(ColumnFilter.prototype, "filterType", {
                    /**
                     * Gets or sets the types of filtering provided by this filter.
                     *
                     * Setting this property to null causes the filter to use the value
                     * defined by the owner filter's @see:FlexGridFilter.defaultFilterType
                     * property.
                     */
                    get: function () {
                        return this._filterType != null ? this._filterType : this._owner.defaultFilterType;
                    },
                    set: function (value) {
                        if (value != this._filterType) {
                            var wasActive = this.isActive;
                            this.clear();
                            this._filterType = wijmo.asEnum(value, filter.FilterType, true);
                            if (wasActive) {
                                this._owner.apply();
                            }
                            else if (this._col.grid) {
                                this._col.grid.invalidate();
                            }
                        }
                    },
                    enumerable: true,
                    configurable: true
                });
                Object.defineProperty(ColumnFilter.prototype, "dataMap", {
                    /**
                     * Gets or sets the @see:DataMap used to convert raw values into display
                     * values shown when editing this filter.
                     *
                     * The example below assigns a @see:DataMap to Boolean column filters
                     * so the filter editor displays 'Yes' and 'No' instead of 'true' and 'false':
                     *
                     * <pre>var filter = new wijmo.grid.filter.FlexGridFilter(grid),
                     *     map = new wijmo.grid.DataMap([
                     *             { value: true, caption: 'Yes' },
                     *             { value: false, caption: 'No' },
                     *         ], 'value', 'caption');
                     * for (var c = 0; c &lt; grid.columns.length; c++) {
                     *     if (grid.columns[c].dataType == wijmo.DataType.Boolean) {
                     *         filter.getColumnFilter(c).dataMap = map;
                     *     }
                     * }</pre>
                     */
                    get: function () {
                        return this.conditionFilter.dataMap || this.valueFilter.dataMap;
                    },
                    set: function (value) {
                        this.conditionFilter.dataMap = value;
                        this.valueFilter.dataMap = value;
                    },
                    enumerable: true,
                    configurable: true
                });
                Object.defineProperty(ColumnFilter.prototype, "valueFilter", {
                    /**
                     * Gets the @see:ValueFilter in this @see:ColumnFilter.
                     */
                    get: function () {
                        return this._valueFilter;
                    },
                    enumerable: true,
                    configurable: true
                });
                Object.defineProperty(ColumnFilter.prototype, "conditionFilter", {
                    /**
                     * Gets the @see:ConditionFilter in this @see:ColumnFilter.
                     */
                    get: function () {
                        return this._conditionFilter;
                    },
                    enumerable: true,
                    configurable: true
                });
                Object.defineProperty(ColumnFilter.prototype, "column", {
                    // ** IColumnFilter
                    /**
                     * Gets the @see:Column being filtered.
                     */
                    get: function () {
                        return this._col;
                    },
                    enumerable: true,
                    configurable: true
                });
                Object.defineProperty(ColumnFilter.prototype, "isActive", {
                    /**
                     * Gets a value that indicates whether the filter is active.
                     */
                    get: function () {
                        return this._conditionFilter.isActive || this._valueFilter.isActive;
                    },
                    enumerable: true,
                    configurable: true
                });
                /**
                 * Gets a value that indicates whether a value passes the filter.
                 *
                 * @param value The value to test.
                 */
                ColumnFilter.prototype.apply = function (value) {
                    return this._conditionFilter.apply(value) && this._valueFilter.apply(value);
                };
                /**
                 * Clears the filter.
                 */
                ColumnFilter.prototype.clear = function () {
                    this._valueFilter.clear();
                    this._conditionFilter.clear();
                };
                // ** IQueryInterface
                /**
                 * Returns true if the caller queries for a supported interface.
                 *
                 * @param interfaceName Name of the interface to look for.
                 */
                ColumnFilter.prototype.implementsInterface = function (interfaceName) {
                    return interfaceName == 'IColumnFilter';
                };
                return ColumnFilter;
            }());
            filter.ColumnFilter = ColumnFilter;
        })(filter = grid.filter || (grid.filter = {}));
    })(grid = wijmo.grid || (wijmo.grid = {}));
})(wijmo || (wijmo = {}));

var __extends = (this && this.__extends) || (function () {
    var extendStatics = Object.setPrototypeOf ||
        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
    return function (d, b) {
        extendStatics(d, b);
        function __() { this.constructor = d; }
        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
    };
})();
var wijmo;
(function (wijmo) {
    var grid;
    (function (grid) {
        var filter;
        (function (filter_1) {
            'use strict';
            // globalization info
            wijmo.culture.FlexGridFilter = window['wijmo'].culture.FlexGridFilter || {
                // filter
                ascending: '\u2191 Ascending',
                descending: '\u2193 Descending',
                apply: 'Apply',
                cancel: 'Cancel',
                clear: 'Clear',
                conditions: 'Filter by Condition',
                values: 'Filter by Value',
                // value filter
                search: 'Search',
                selectAll: 'Select All',
                null: '(nothing)',
                // condition filter
                header: 'Show items where the value',
                and: 'And',
                or: 'Or',
                stringOperators: [
                    { name: '(not set)', op: null },
                    { name: 'Equals', op: filter_1.Operator.EQ },
                    { name: 'Does not equal', op: filter_1.Operator.NE },
                    { name: 'Begins with', op: filter_1.Operator.BW },
                    { name: 'Ends with', op: filter_1.Operator.EW },
                    { name: 'Contains', op: filter_1.Operator.CT },
                    { name: 'Does not contain', op: filter_1.Operator.NC }
                ],
                numberOperators: [
                    { name: '(not set)', op: null },
                    { name: 'Equals', op: filter_1.Operator.EQ },
                    { name: 'Does not equal', op: filter_1.Operator.NE },
                    { name: 'Is Greater than', op: filter_1.Operator.GT },
                    { name: 'Is Greater than or equal to', op: filter_1.Operator.GE },
                    { name: 'Is Less than', op: filter_1.Operator.LT },
                    { name: 'Is Less than or equal to', op: filter_1.Operator.LE }
                ],
                dateOperators: [
                    { name: '(not set)', op: null },
                    { name: 'Equals', op: filter_1.Operator.EQ },
                    { name: 'Is Before', op: filter_1.Operator.LT },
                    { name: 'Is After', op: filter_1.Operator.GT }
                ],
                booleanOperators: [
                    { name: '(not set)', op: null },
                    { name: 'Equals', op: filter_1.Operator.EQ },
                    { name: 'Does not equal', op: filter_1.Operator.NE }
                ]
            };
            /**
             * The editor used to inspect and modify column filters.
             *
             * This class is used by the @see:FlexGridFilter class; you
             * rarely use it directly.
             */
            var ColumnFilterEditor = /** @class */ (function (_super) {
                __extends(ColumnFilterEditor, _super);
                /**
                 * Initializes a new instance of the @see:ColumnFilterEditor class.
                 *
                 * @param element The DOM element that hosts the control, or a selector
                 * for the host element (e.g. '#theCtrl').
                 * @param filter The @see:ColumnFilter to edit.
                 * @param sortButtons Whether to show sort buttons in the editor.
                 */
                function ColumnFilterEditor(element, filter, sortButtons) {
                    if (sortButtons === void 0) { sortButtons = true; }
                    var _this = _super.call(this, element, null, true) || this;
                    /**
                     * Occurs after the filter is modified.
                     */
                    _this.filterChanged = new wijmo.Event();
                    /**
                     * Occurs when one of the editor buttons is clicked.
                     */
                    _this.buttonClicked = new wijmo.Event();
                    // save reference to filter being edited
                    _this._filter = wijmo.asType(filter, filter_1.ColumnFilter);
                    // instantiate and apply template
                    var tpl = _this.getTemplate();
                    _this.applyTemplate('wj-control wj-columnfiltereditor wj-content', tpl, {
                        _divSort: 'div-sort',
                        _btnAsc: 'btn-asc',
                        _btnDsc: 'btn-dsc',
                        _divType: 'div-type',
                        _aVal: 'a-val',
                        _aCnd: 'a-cnd',
                        _divEdtVal: 'div-edt-val',
                        _divEdtCnd: 'div-edt-cnd',
                        _btnApply: 'btn-apply',
                        _btnCancel: 'btn-cancel',
                        _btnClear: 'btn-clear'
                    });
                    // localization
                    var strings = wijmo.culture.FlexGridFilter;
                    wijmo.setText(_this._btnAsc, strings.ascending);
                    wijmo.setText(_this._btnDsc, strings.descending);
                    wijmo.setText(_this._aVal, strings.values);
                    wijmo.setText(_this._aCnd, strings.conditions);
                    wijmo.setText(_this._btnApply, strings.apply);
                    wijmo.setText(_this._btnCancel, strings.cancel);
                    wijmo.setText(_this._btnClear, strings.clear);
                    // show the filter that is active
                    var ft = (_this.filter.conditionFilter.isActive || (filter.filterType & filter_1.FilterType.Value) == 0)
                        ? filter_1.FilterType.Condition
                        : filter_1.FilterType.Value;
                    _this._showFilter(ft);
                    // hide sort buttons if the collection view is not sortable
                    // or if the user doesn't want them
                    var col = _this.filter.column, view = col.grid.collectionView;
                    if (!sortButtons || !view || !view.canSort) {
                        _this._divSort.style.display = 'none';
                    }
                    // handle button clicks
                    var bnd = _this._btnClicked.bind(_this);
                    _this._btnApply.addEventListener('click', bnd);
                    _this._btnCancel.addEventListener('click', bnd);
                    _this._btnClear.addEventListener('click', bnd);
                    _this._btnAsc.addEventListener('click', bnd);
                    _this._btnDsc.addEventListener('click', bnd);
                    _this._aVal.addEventListener('click', bnd);
                    _this._aCnd.addEventListener('click', bnd);
                    // commit/dismiss on Enter/Esc
                    _this.hostElement.addEventListener('keydown', function (e) {
                        if (!e.defaultPrevented) {
                            switch (e.keyCode) {
                                case wijmo.Key.Enter:
                                    switch (e.target.tagName) {
                                        case 'A':
                                        case 'BUTTON':
                                            _this._btnClicked(e); // TFS 123049
                                            break;
                                        default:
                                            _this.updateFilter();
                                            _this.onFilterChanged();
                                            _this.onButtonClicked();
                                            break;
                                    }
                                    e.preventDefault();
                                    break;
                                case wijmo.Key.Escape:
                                    _this.onButtonClicked();
                                    e.preventDefault();
                                    break;
                            }
                        }
                    });
                    return _this;
                }
                Object.defineProperty(ColumnFilterEditor.prototype, "filter", {
                    /**
                     * Gets a reference to the @see:ColumnFilter being edited.
                     */
                    get: function () {
                        return this._filter;
                    },
                    enumerable: true,
                    configurable: true
                });
                /**
                 * Updates editor with current filter settings.
                 */
                ColumnFilterEditor.prototype.updateEditor = function () {
                    if (this._edtVal) {
                        this._edtVal.updateEditor();
                    }
                    if (this._edtCnd) {
                        this._edtCnd.updateEditor();
                    }
                };
                /**
                 * Updates filter with current editor settings.
                 */
                ColumnFilterEditor.prototype.updateFilter = function () {
                    switch (this._getFilterType()) {
                        case filter_1.FilterType.Value:
                            this._edtVal.updateFilter();
                            this.filter.conditionFilter.clear();
                            break;
                        case filter_1.FilterType.Condition:
                            this._edtCnd.updateFilter();
                            this.filter.valueFilter.clear();
                            break;
                    }
                };
                /**
                 * Raises the @see:filterChanged event.
                 */
                ColumnFilterEditor.prototype.onFilterChanged = function (e) {
                    this.filterChanged.raise(this, e);
                };
                /**
                 * Raises the @see:buttonClicked event.
                 */
                ColumnFilterEditor.prototype.onButtonClicked = function (e) {
                    this.buttonClicked.raise(this, e);
                };
                // ** implementation
                // close editor when the browser is resized
                // (or it will be in the wrong position...)
                ColumnFilterEditor.prototype._handleResize = function () {
                    if (!this.isTouching && !this._wasTouching) {
                        this.onButtonClicked();
                    }
                };
                // shows the value or condition filter editor
                ColumnFilterEditor.prototype._showFilter = function (filterType) {
                    // save isTouching value to keep the editor up
                    this._wasTouching = this.isTouching;
                    // create editor if we have to
                    if (filterType == filter_1.FilterType.Value && this._edtVal == null) {
                        this._edtVal = new filter_1.ValueFilterEditor(this._divEdtVal, this.filter.valueFilter);
                    }
                    if (filterType == filter_1.FilterType.Condition && this._edtCnd == null) {
                        this._edtCnd = new filter_1.ConditionFilterEditor(this._divEdtCnd, this.filter.conditionFilter);
                    }
                    // show selected editor
                    if ((filterType & this.filter.filterType) != 0) {
                        if (filterType == filter_1.FilterType.Value) {
                            this._divEdtVal.style.display = '';
                            this._divEdtCnd.style.display = 'none';
                            this._enableLink(this._aVal, false);
                            this._enableLink(this._aCnd, true);
                            this._edtVal.focus();
                        }
                        else {
                            this._divEdtVal.style.display = 'none';
                            this._divEdtCnd.style.display = '';
                            this._enableLink(this._aVal, true);
                            this._enableLink(this._aCnd, false);
                            this._edtCnd.focus();
                        }
                    }
                    // hide switch button if only one filter type is supported
                    switch (this.filter.filterType) {
                        case filter_1.FilterType.None:
                        case filter_1.FilterType.Condition:
                        case filter_1.FilterType.Value:
                            this._divType.style.display = 'none';
                            break;
                        default:
                            this._divType.style.display = '';
                            break;
                    }
                };
                // enable/disable filter switch links
                ColumnFilterEditor.prototype._enableLink = function (a, enable) {
                    a.style.textDecoration = enable ? '' : 'none';
                    a.style.fontWeight = enable ? '' : 'bold';
                    wijmo.setAttribute(a, 'href', enable ? '' : null);
                };
                // gets the type of filter currently being edited
                ColumnFilterEditor.prototype._getFilterType = function () {
                    return this._divEdtVal.style.display != 'none'
                        ? filter_1.FilterType.Value
                        : filter_1.FilterType.Condition;
                };
                // handle buttons
                ColumnFilterEditor.prototype._btnClicked = function (e) {
                    e.preventDefault();
                    e.stopPropagation();
                    // ignore disabled elements
                    if (wijmo.hasClass(e.target, 'wj-state-disabled')) {
                        return;
                    }
                    // switch filters
                    if (e.target == this._aVal) {
                        this._showFilter(filter_1.FilterType.Value);
                        return;
                    }
                    if (e.target == this._aCnd) {
                        this._showFilter(filter_1.FilterType.Condition);
                        return;
                    }
                    // apply sort
                    if (e.target == this._btnAsc || e.target == this._btnDsc) {
                        var col = this.filter.column, binding = col.sortMemberPath ? col.sortMemberPath : col.binding, view_1 = col.grid.collectionView, sortDesc_1 = new wijmo.collections.SortDescription(binding, e.target == this._btnAsc);
                        view_1.sortDescriptions.deferUpdate(function () {
                            view_1.sortDescriptions.clear();
                            view_1.sortDescriptions.push(sortDesc_1);
                        });
                    }
                    // apply/clear filter
                    if (e.target == this._btnApply) {
                        this.updateFilter();
                        this.onFilterChanged();
                    }
                    else if (e.target == this._btnClear) {
                        if (this.filter.isActive) {
                            this.filter.clear();
                            this.onFilterChanged();
                        }
                    }
                    else {
                        this.updateEditor(); // show current filter state
                    }
                    // raise event so caller can close the editor and apply the new filter
                    this.onButtonClicked();
                };
                /**
                 * Gets or sets the template used to instantiate @see:ColumnFilterEditor controls.
                 */
                ColumnFilterEditor.controlTemplate = '<div>' +
                    '<div wj-part="div-sort">' +
                    '<a wj-part="btn-asc" href="" style="min-width:95px" draggable="false"></a>&nbsp;&nbsp;&nbsp;' +
                    '<a wj-part="btn-dsc" href="" style="min-width:95px" draggable="false"></a>' +
                    '</div>' +
                    '<div style="text-align:right;margin:10px 0px;font-size:80%">' +
                    '<div wj-part="div-type">' +
                    '<a wj-part="a-cnd" href="" draggable="false"></a>' +
                    '&nbsp;|&nbsp;' +
                    '<a wj-part="a-val" href="" draggable="false"></a>' +
                    '</div>' +
                    '</div>' +
                    '<div wj-part="div-edt-val"></div>' +
                    '<div wj-part="div-edt-cnd"></div>' +
                    '<div style="text-align:right;margin-top:10px">' +
                    '<a wj-part="btn-apply" href="" draggable="false"></a>&nbsp;&nbsp;' +
                    '<a wj-part="btn-cancel" href="" draggable="false"></a>&nbsp;&nbsp;' +
                    '<a wj-part="btn-clear" href="" draggable="false"></a>' +
                    '</div>';
                return ColumnFilterEditor;
            }(wijmo.Control));
            filter_1.ColumnFilterEditor = ColumnFilterEditor;
        })(filter = grid.filter || (grid.filter = {}));
    })(grid = wijmo.grid || (wijmo.grid = {}));
})(wijmo || (wijmo = {}));

/**
 * Extension that provides an Excel-style filtering UI for @see:FlexGrid controls.
 */
var wijmo;
(function (wijmo) {
    var grid;
    (function (grid_1) {
        var filter;
        (function (filter) {
            'use strict';
            /**
             * Specifies types of column filter.
             */
            var FilterType;
            (function (FilterType) {
                /** No filter. */
                FilterType[FilterType["None"] = 0] = "None";
                /** A filter based on two conditions. */
                FilterType[FilterType["Condition"] = 1] = "Condition";
                /** A filter based on a set of values. */
                FilterType[FilterType["Value"] = 2] = "Value";
                /** A filter that combines condition and value filters. */
                FilterType[FilterType["Both"] = 3] = "Both";
            })(FilterType = filter.FilterType || (filter.FilterType = {}));
            /**
             * Implements an Excel-style filter for @see:FlexGrid controls.
             *
             * To enable filtering on a @see:FlexGrid control, create an instance
             * of the @see:FlexGridFilter and pass the grid as a parameter to the
             * constructor. For example:
             *
             * <pre>
             * // create FlexGrid
             * var flex = new wijmo.grid.FlexGrid('#gridElement');
             * // enable filtering on the FlexGrid
             * var filter = new wijmo.grid.filter.FlexGridFilter(flex);
             * </pre>
             *
             * Once this is done, a filter icon is added to the grid's column headers.
             * Clicking the icon shows an editor where the user can edit the filter
             * conditions for that column.
             *
             * The @see:FlexGridFilter class depends on the <b>wijmo.grid</b> and
             * <b>wijmo.input</b> modules.
             */
            var FlexGridFilter = /** @class */ (function () {
                /**
                 * Initializes a new instance of the @see:FlexGridFilter class.
                 *
                 * @param grid The @see:FlexGrid to filter.
                 * @param options Initialization options for the @see:FlexGridFilter.
                 */
                function FlexGridFilter(grid, options) {
                    this._showIcons = true;
                    this._showSort = true;
                    this._defFilterType = FilterType.Both;
                    /**
                     * Occurs after the filter is applied.
                     */
                    this.filterApplied = new wijmo.Event();
                    /**
                     * Occurs when a column filter is about to be edited by the user.
                     *
                     * Use this event to customize the column filter if you want to
                     * override the default settings for the filter.
                     *
                     * For example, the code below sets the operator used by the filter
                     * conditions to 'contains' if they are null:
                     *
                     * <pre>filter.filterChanging.addHandler(function (s, e) {
                     *   var cf = filter.getColumnFilter(e.col);
                     *   if (!cf.valueFilter.isActive && cf.conditionFilter.condition1.operator == null) {
                     *     cf.filterType = wijmo.grid.filter.FilterType.Condition;
                     *     cf.conditionFilter.condition1.operator = wijmo.grid.filter.Operator.CT;
                     *   }
                     * });</pre>
                     */
                    this.filterChanging = new wijmo.Event();
                    /**
                     * Occurs after a column filter has been edited by the user.
                     *
                     * Use the event parameters to determine the column that owns
                     * the filter and whether changes were applied or canceled.
                     */
                    this.filterChanged = new wijmo.Event();
                    // check dependencies
                    var depErr = 'Missing dependency: FlexGridFilter requires ';
                    wijmo.assert(wijmo.grid != null, depErr + 'wijmo.grid.');
                    wijmo.assert(wijmo.input != null, depErr + 'wijmo.input.');
                    // initialize filter
                    this._filters = [];
                    this._g = wijmo.asType(grid, grid_1.FlexGrid, false);
                    this._g.formatItem.addHandler(this._formatItem.bind(this));
                    this._g.itemsSourceChanged.addHandler(this.clear.bind(this));
                    var host = this._g.hostElement;
                    grid.addEventListener(host, 'mousedown', this._mousedown.bind(this), true);
                    grid.addEventListener(host, 'click', this._click.bind(this), true);
                    grid.addEventListener(host, 'keydown', this._keydown.bind(this), true);
                    // initialize column filters
                    this._g.invalidate();
                    // apply options
                    if (options) {
                        wijmo.copy(this, options);
                    }
                }
                Object.defineProperty(FlexGridFilter.prototype, "grid", {
                    /**
                     * Gets a reference to the @see:FlexGrid that owns this filter.
                     */
                    get: function () {
                        return this._g;
                    },
                    enumerable: true,
                    configurable: true
                });
                Object.defineProperty(FlexGridFilter.prototype, "filterColumns", {
                    /**
                     * Gets or sets an array containing the names or bindings of the columns
                     * that have filters.
                     *
                     * Setting this property to null or to an empty array adds filters to
                     * all columns.
                     */
                    get: function () {
                        return this._filterColumns;
                    },
                    set: function (value) {
                        this._filterColumns = wijmo.asArray(value);
                        this.clear();
                    },
                    enumerable: true,
                    configurable: true
                });
                Object.defineProperty(FlexGridFilter.prototype, "showFilterIcons", {
                    /**
                     * Gets or sets a value indicating whether the @see:FlexGridFilter adds filter
                     * editing buttons to the grid's column headers.
                     *
                     * If you set this property to false, then you are responsible for providing
                     * a way for users to edit, clear, and apply the filters.
                     */
                    get: function () {
                        return this._showIcons;
                    },
                    set: function (value) {
                        if (value != this.showFilterIcons) {
                            this._showIcons = wijmo.asBoolean(value);
                            if (this._g) {
                                this._g.invalidate();
                            }
                        }
                    },
                    enumerable: true,
                    configurable: true
                });
                Object.defineProperty(FlexGridFilter.prototype, "showSortButtons", {
                    /**
                     * Gets or sets a value indicating whether the filter editor should include
                     * sort buttons.
                     *
                     * By default, the editor shows sort buttons like Excel does. But since users
                     * can sort columns by clicking their headers, sort buttons in the filter editor
                     * may not be desirable in some circumstances.
                     */
                    get: function () {
                        return this._showSort;
                    },
                    set: function (value) {
                        this._showSort = wijmo.asBoolean(value);
                    },
                    enumerable: true,
                    configurable: true
                });
                /**
                 * Gets the filter for the given column.
                 *
                 * @param col The @see:Column that the filter applies to (or column name or index).
                 * @param create Whether to create the filter if it does not exist.
                 */
                FlexGridFilter.prototype.getColumnFilter = function (col, create) {
                    if (create === void 0) { create = true; }
                    // get the column by name or index, check type
                    col = this._asColumn(col);
                    // look for the filter
                    for (var i = 0; i < this._filters.length; i++) {
                        if (this._filters[i].column == col) {
                            return this._filters[i];
                        }
                    }
                    // not found, create one now
                    if (create && col.binding) {
                        var cf = new filter.ColumnFilter(this, col);
                        this._filters.push(cf);
                        return cf;
                    }
                    // not found, not created
                    return null;
                };
                Object.defineProperty(FlexGridFilter.prototype, "defaultFilterType", {
                    /**
                     * Gets or sets the default filter type to use.
                     *
                     * This value can be overridden in filters for specific columns.
                     * For example, the code below creates a filter that filters by
                     * conditions on all columns except the "ByValue" column:
                     *
                     * <pre>
                     * var f = new wijmo.grid.filter.FlexGridFilter(flex);
                     * f.defaultFilterType = wijmo.grid.filter.FilterType.Condition;
                     * var col = flex.columns.getColumn('ByValue'),
                     *     cf = f.getColumnFilter(col);
                     * cf.filterType = wijmo.grid.filter.FilterType.Value;
                     * </pre>
                     */
                    get: function () {
                        return this._defFilterType;
                    },
                    set: function (value) {
                        if (value != this.defaultFilterType) {
                            this._defFilterType = wijmo.asEnum(value, FilterType, false);
                            this._g.invalidate();
                            this.clear();
                        }
                    },
                    enumerable: true,
                    configurable: true
                });
                Object.defineProperty(FlexGridFilter.prototype, "filterDefinition", {
                    /**
                     * Gets or sets the current filter definition as a JSON string.
                     */
                    get: function () {
                        var def = {
                            defaultFilterType: this.defaultFilterType,
                            filters: []
                        };
                        for (var i = 0; i < this._filters.length; i++) {
                            var cf = this._filters[i];
                            if (cf && cf.column && cf.column.binding) {
                                if (cf.conditionFilter.isActive) {
                                    var cfc = cf.conditionFilter;
                                    def.filters.push({
                                        binding: cf.column.binding,
                                        type: 'condition',
                                        condition1: { operator: cfc.condition1.operator, value: cfc.condition1.value },
                                        and: cfc.and,
                                        condition2: { operator: cfc.condition2.operator, value: cfc.condition2.value }
                                    });
                                }
                                else if (cf.valueFilter.isActive) {
                                    var cfv = cf.valueFilter;
                                    def.filters.push({
                                        binding: cf.column.binding,
                                        type: 'value',
                                        filterText: cfv.filterText,
                                        showValues: cfv.showValues
                                    });
                                }
                            }
                        }
                        return JSON.stringify(def);
                    },
                    set: function (value) {
                        // make sure the value is a string
                        value = wijmo.asString(value);
                        // empty/null clears filter
                        this.clear();
                        // if a value was provided, parse it
                        if (value) {
                            var def = JSON.parse(value);
                            this.defaultFilterType = def.defaultFilterType;
                            for (var i = 0; i < def.filters.length; i++) {
                                var cfs = def.filters[i], col = this._g.getColumn(cfs.binding), cf = this.getColumnFilter(col, true);
                                if (cf) {
                                    switch (cfs.type) {
                                        case 'condition':
                                            var cfc = cf.conditionFilter;
                                            cfc.condition1.value = col.dataType == wijmo.DataType.Date // handle times/times: TFS 125144, 143453
                                                ? wijmo.changeType(cfs.condition1.value, col.dataType, null)
                                                : cfs.condition1.value;
                                            cfc.condition1.operator = cfs.condition1.operator;
                                            cfc.and = cfs.and;
                                            cfc.condition2.value = col.dataType == wijmo.DataType.Date
                                                ? wijmo.changeType(cfs.condition2.value, col.dataType, null)
                                                : cfs.condition2.value;
                                            cfc.condition2.operator = cfs.condition2.operator;
                                            break;
                                        case 'value':
                                            var cfv = cf.valueFilter;
                                            cfv.filterText = cfs.filterText;
                                            cfv.showValues = cfs.showValues;
                                            break;
                                    }
                                }
                            }
                        }
                        // done, apply new filter
                        this.apply();
                    },
                    enumerable: true,
                    configurable: true
                });
                /**
                 * Shows the filter editor for the given grid column.
                 *
                 * @param col The @see:Column that contains the filter to edit.
                 * @param ht A @see:wijmo.grid.HitTestInfo object containing the range of the cell
                 * that triggered the filter display.
                 */
                FlexGridFilter.prototype.editColumnFilter = function (col, ht) {
                    var _this = this;
                    // remove current editor
                    this.closeEditor();
                    // get column (by name, index, or reference)
                    col = this._asColumn(col);
                    // raise filterChanging event
                    var e = new grid_1.CellRangeEventArgs(this._g.cells, new grid_1.CellRange(-1, col.index));
                    if (!this.onFilterChanging(e)) {
                        return;
                    }
                    e.cancel = true; // assume the changes will be canceled
                    // get the filter and the editor
                    var div = document.createElement('div'), flt = this.getColumnFilter(col), edt = new filter.ColumnFilterEditor(div, flt, this.showSortButtons);
                    wijmo.addClass(div, 'wj-dropdown-panel');
                    // handle RTL
                    if (this._g.rightToLeft) {
                        div.dir = 'rtl';
                    }
                    // apply filter when it changes
                    edt.filterChanged.addHandler(function () {
                        e.cancel = false; // the changes were not canceled
                        setTimeout(function () {
                            if (!e.cancel) {
                                _this.apply();
                            }
                        });
                    });
                    // close editor when editor button is clicked
                    edt.buttonClicked.addHandler(function () {
                        _this.closeEditor();
                        _this._g.focus();
                        _this.onFilterChanged(e);
                    });
                    // close editor when it loses focus (changes are not applied)
                    edt.lostFocus.addHandler(function () {
                        setTimeout(function () {
                            var ctl = wijmo.Control.getControl(_this._divEdt);
                            if (ctl && !ctl.containsFocus()) {
                                _this.closeEditor();
                            }
                        }, 10); //200); // let others handle it first
                    });
                    // scroll the column being filtered into view
                    this._g.scrollIntoView(-1, col.index, true);
                    // get the header cell to position editor
                    var ch = this._g.columnHeaders, r = ht ? ht.row : ch.rows.length - 1, c = ht ? ht.col : col.index, rc = ch.getCellBoundingRect(r, c), hdrCell = ch.getCellElement(r, c);
                    // show editor and give it focus
                    if (hdrCell) {
                        wijmo.showPopup(div, hdrCell, false, false, false);
                    }
                    else {
                        wijmo.showPopup(div, rc);
                    }
                    edt.focus();
                    // save reference to editor
                    this._divEdt = div;
                    this._edtCol = col;
                };
                /**
                 * Closes the filter editor.
                 */
                FlexGridFilter.prototype.closeEditor = function () {
                    if (this._divEdt) {
                        var edt_1 = wijmo.Control.getControl(this._divEdt);
                        if (edt_1) {
                            wijmo.hidePopup(edt_1.hostElement, function () {
                                edt_1.dispose(); // dispose of editor to avoid memory leaks
                            });
                        }
                        this._divEdt = null;
                        this._edtCol = null;
                    }
                };
                /**
                 * Applies the current column filters to the grid.
                 */
                FlexGridFilter.prototype.apply = function () {
                    var view = this._g.collectionView;
                    if (view) {
                        // commit any pending edits (TFS 271476)
                        var ecv = this._g.editableCollectionView;
                        if (ecv) {
                            ecv.commitEdit();
                            ecv.commitNew();
                        }
                        // apply new filter
                        if (view.filter) {
                            view.refresh();
                        }
                        else {
                            view.filter = this._filter.bind(this);
                        }
                    }
                    // apply filter definition if the collectionView supports that
                    var updateFilterDefinition = view ? view['updateFilterDefinition'] : null;
                    if (wijmo.isFunction(updateFilterDefinition)) {
                        updateFilterDefinition.call(view, this);
                    }
                    // and fire the event
                    this.onFilterApplied();
                };
                /**
                 * Clears all column filters.
                 */
                FlexGridFilter.prototype.clear = function () {
                    if (this._filters.length) {
                        this._filters = [];
                        this.apply();
                    }
                };
                /**
                 * Raises the @see:filterApplied event.
                 */
                FlexGridFilter.prototype.onFilterApplied = function (e) {
                    this.filterApplied.raise(this, e);
                };
                /**
                 * Raises the @see:filterChanging event.
                 *
                 * @param e @see:CellRangeEventArgs that contains the event data.
                 * @return True if the event was not canceled.
                 */
                FlexGridFilter.prototype.onFilterChanging = function (e) {
                    this.filterChanging.raise(this, e);
                    return !e.cancel;
                };
                /**
                 * Raises the @see:filterChanged event.
                 */
                FlexGridFilter.prototype.onFilterChanged = function (e) {
                    this.filterChanged.raise(this, e);
                };
                // ** implementation
                // get a column by name, index, or reference
                FlexGridFilter.prototype._asColumn = function (col) {
                    return wijmo.isString(col) ? this._g.getColumn(col) :
                        wijmo.isNumber(col) ? this._g.columns[col] :
                            wijmo.asType(col, grid_1.Column, false);
                };
                // predicate function used to filter the CollectionView
                FlexGridFilter.prototype._filter = function (item) {
                    for (var i = 0; i < this._filters.length; i++) {
                        if (!this._filters[i].apply(item)) {
                            return false;
                        }
                    }
                    return true;
                };
                // handle the formatItem event to add filter icons to the column header cells
                FlexGridFilter.prototype._formatItem = function (sender, e) {
                    // format only ColumnHeader elements
                    if (e.panel.cellType == grid_1.CellType.ColumnHeader) {
                        // get column, binding column
                        var g = this._g, rng = g.getMergedRange(e.panel, e.row, e.col) || new grid_1.CellRange(e.row, e.col), col = g.columns[rng.col], bcol = g._getBindingColumn(e.panel, e.row, col);
                        // check that the row is valid for the filter icon
                        if (rng.row2 == e.panel.rows.length - 1 || col != bcol) {
                            // get the filter for this column
                            var cf = this.getColumnFilter(bcol, this.defaultFilterType != FilterType.None);
                            // honor filterColumns property
                            if (this._filterColumns && this._filterColumns.indexOf(bcol.binding) < 0) {
                                cf = null;
                            }
                            // if we have a filter, show the icon
                            if (cf && cf.filterType != FilterType.None) {
                                // show filter glyph for this column
                                if (this._showIcons) {
                                    if (!FlexGridFilter._filterGlyph) {
                                        FlexGridFilter._filterGlyph = wijmo.createElement('<div role="button" class="' + FlexGridFilter._WJC_FILTER + '">' +
                                            '<span class="wj-glyph-filter"></span>' +
                                            '</div> ');
                                    }
                                    var cell = (e.cell.querySelector('div') || e.cell), existingGlyph = cell.querySelector('.wj-glyph-filter');
                                    if (!existingGlyph) {
                                        cell.insertBefore(FlexGridFilter._filterGlyph.cloneNode(true), cell.firstChild);
                                    }
                                }
                                // update filter classes if there is a filter
                                wijmo.toggleClass(e.cell, 'wj-filter-on', cf.isActive);
                                wijmo.toggleClass(e.cell, 'wj-filter-off', !cf.isActive);
                            }
                            else {
                                // remove filter classes if there is no filter
                                wijmo.removeClass(e.cell, 'wj-filter-on');
                                wijmo.removeClass(e.cell, 'wj-filter-off');
                            }
                        }
                    }
                };
                // handle mousedown to show/hide the filter editor
                FlexGridFilter.prototype._mousedown = function (e) {
                    if (this._toggleEditor(e)) {
                        this._tmd = true; // remember we used this mouse down
                        e.stopPropagation();
                        e.preventDefault();
                    }
                };
                // handle clicks to show/hide the filter editor
                FlexGridFilter.prototype._click = function (e) {
                    if (this._tmd || this._toggleEditor(e)) {
                        e.stopPropagation();
                        e.preventDefault();
                    }
                    this._tmd = false;
                };
                // toggle filter editor on mousedown/click
                FlexGridFilter.prototype._toggleEditor = function (e) {
                    var _this = this;
                    this._tmd = false;
                    if (!e.defaultPrevented && e.button == 0) {
                        if (wijmo.closestClass(e.target, FlexGridFilter._WJC_FILTER)) {
                            var g = this._g, ht_1 = new grid_1.HitTestInfo(e.target, null);
                            if (!ht_1.panel) {
                                ht_1 = g.hitTest(e, true);
                            }
                            if (ht_1.panel == g.columnHeaders) {
                                var col = g.columns[ht_1.col], bcol_1 = g._getBindingColumn(ht_1.panel, ht_1.row, col);
                                if (this._divEdt && this._edtCol == bcol_1) {
                                    this.closeEditor();
                                    g.focus(); // TFS 275275
                                }
                                else {
                                    setTimeout(function () {
                                        _this.editColumnFilter(bcol_1, ht_1);
                                    }, this._divEdt ? 100 : 0); // allow some time to close editors (TFS 117746)
                                }
                                return true;
                            }
                        }
                        else {
                            this.closeEditor(); // TFS 271847
                        }
                    }
                    return false;
                };
                // show filter editor on alt+Down or alt+Up keys (like Excel)
                FlexGridFilter.prototype._keydown = function (e) {
                    if (!e.defaultPrevented && !e.ctrlKey && e.altKey) {
                        if (e.keyCode == wijmo.Key.Down || e.keyCode == wijmo.Key.Up) {
                            var sel = this.grid.selection, col = sel.col > -1 ? this.grid.columns[sel.col] : null;
                            if (col && !col.dataMap && this.getColumnFilter(col, true)) {
                                this.editColumnFilter(col);
                                e.preventDefault();
                                e.stopPropagation();
                            }
                        }
                    }
                };
                FlexGridFilter._WJC_FILTER = 'wj-elem-filter';
                return FlexGridFilter;
            }());
            filter.FlexGridFilter = FlexGridFilter;
        })(filter = grid_1.filter || (grid_1.filter = {}));
    })(grid = wijmo.grid || (wijmo.grid = {}));
})(wijmo || (wijmo = {}));

