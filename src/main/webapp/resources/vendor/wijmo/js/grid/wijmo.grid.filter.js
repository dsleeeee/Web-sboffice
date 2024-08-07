﻿/*
 *
 * Wijmo Library 5.20183.550
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
! function(t) {
  ! function(t) {
    ! function(t) {
      "use strict"
    }(t.filter || (t.filter = {}))
  }(t.grid || (t.grid = {}))
}(wijmo || (wijmo = {}));
var wijmo;
! function(t) {
  ! function(e) {
    ! function(i) {
      "use strict";
      var n = function() {
        function i(e) {
          this._maxValues = 250, this._sortValues = !0, this._col = e, this._bnd = e.binding ? new t.Binding(e.binding) : null
        }
        return Object.defineProperty(i.prototype, "showValues", {
          get: function() {
            return this._values
          },
          set: function(t) {
            this._values = t
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(i.prototype, "filterText", {
          get: function() {
            return this._filterText
          },
          set: function(e) {
            this._filterText = t.asString(e)
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(i.prototype, "maxValues", {
          get: function() {
            return this._maxValues
          },
          set: function(e) {
            this._maxValues = t.asNumber(e, !1, !0)
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(i.prototype, "uniqueValues", {
          get: function() {
            return this._uniqueValues
          },
          set: function(e) {
            this._uniqueValues = t.asArray(e)
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(i.prototype, "sortValues", {
          get: function() {
            return this._sortValues
          },
          set: function(e) {
            this._sortValues = t.asBoolean(e)
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(i.prototype, "dataMap", {
          get: function() {
            return this._map
          },
          set: function(i) {
            this._map = t.asType(i, e.DataMap, !0)
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(i.prototype, "column", {
          get: function() {
            return this._col
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(i.prototype, "isActive", {
          get: function() {
            return null != this._values && Object.keys(this._values).length > 0
          },
          enumerable: !0,
          configurable: !0
        }), i.prototype.apply = function(e) {
          var i = this.column;
          return !(this._bnd && this._values && Object.keys(this._values).length) || (e = this._bnd.getValue(e), e = this.dataMap ? this.dataMap.getDisplayValue(e) : i.dataMap ? i.dataMap.getDisplayValue(e) : t.Globalize.format(e, i.format), void 0 != this._values[e])
        }, i.prototype.clear = function() {
          this.showValues = null, this.filterText = null
        }, i.prototype.implementsInterface = function(t) {
          return "IColumnFilter" == t
        }, i.prototype._getUniqueValues = function(e, i) {
          var n = [];
          if (this.uniqueValues) {
            for (var r = this.uniqueValues, o = 0; o < r.length; o++) {
              d = r[o];
              n.push({
                value: d,
                text: d.toString()
              })
            }
            return n
          }
          var l = {},
            a = e.collectionView,
            s = a ? a.sourceCollection : [];
          if (i && a && a.sourceCollection && a.filter) {
            var u = this.showValues;
            this.showValues = null;
            for (var c = [], o = 0; o < s.length; o++) a.filter(s[o]) && c.push(s[o]);
            s = c, this.showValues = u
          }
          for (o = 0; o < s.length; o++) {
            var d = e._binding.getValue(s[o]),
              p = this.dataMap ? this.dataMap.getDisplayValue(d) : e.dataMap ? e.dataMap.getDisplayValue(d) : t.Globalize.format(d, e.format);
            l[p] || (l[p] = !0, n.push({
              value: d,
              text: p
            }))
          }
          return n
        }, i
      }();
      i.ValueFilter = n
    }(e.filter || (e.filter = {}))
  }(t.grid || (t.grid = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var t = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(t, e) {
        t.__proto__ = e
      } || function(t, e) {
        for (var i in e) e.hasOwnProperty(i) && (t[i] = e[i])
      };
    return function(e, i) {
      function n() {
        this.constructor = e
      }
      t(e, i), e.prototype = null === i ? Object.create(i) : (n.prototype = i.prototype, new n)
    }
  }(),
  wijmo;
! function(t) {
  ! function(e) {
    ! function(e) {
      "use strict";
      var i = function(i) {
        function n(n, r) {
          var o = i.call(this, n) || this;
          o._filter = t.asType(r, e.ValueFilter, !1);
          var l = o.getTemplate();
          o.applyTemplate("wj-control wj-valuefilter-editor", l, {
            _divFilter: "div-filter",
            _cbSelectAll: "cb-select-all",
            _spSelectAll: "sp-select-all",
            _divValues: "div-values"
          }), o._cbSelectAll.tabIndex = 0;
          var a = t.culture.FlexGridFilter;
          t.setText(o._spSelectAll, a.selectAll);
          var s = o._view = new t.collections.CollectionView;
          if (s.sortNullsFirst = !0, r.sortValues) {
            var u = r.column.dataMap || r.dataMap ? "text" : "value",
              c = r.column.dataType != t.DataType.Boolean;
            s.sortDescriptions.push(new t.collections.SortDescription(u, c))
          }
          return s.filter = o._filterValues.bind(o), s.collectionChanged.addHandler(o._updateSelectAllCheck, o), o._filterText = "", o._cmbFilter = new t.input.ComboBox(o._divFilter, {
            placeholder: a.search
          }), o._lbValues = new t.input.ListBox(o._divValues, {
            displayMemberPath: "text",
            checkedMemberPath: "show",
            itemsSource: o._view,
            itemFormatter: function(t, e) {
              return e || a.null
            }
          }), t.setAriaLabel(o._cmbFilter.inputElement, a.ariaLabels.search), o._cmbFilter.textChanged.addHandler(o._filterTextChanged, o), o._cbSelectAll.addEventListener("click", o._cbSelectAllClicked.bind(o)), o.updateEditor(), o
        }
        return __extends(n, i), Object.defineProperty(n.prototype, "filter", {
          get: function() {
            return this._filter
          },
          enumerable: !0,
          configurable: !0
        }), n.prototype.updateEditor = function() {
          var t = this,
            e = this._filter.column,
            i = this._filter._getUniqueValues(e, !0);
          this._lbValues.isContentHtml = e.isContentHtml;
          var n = this._filter.showValues;
          if (n && 0 != Object.keys(n).length) {
            for (var r in n)
              for (o = 0; o < i.length; o++)
                if (i[o].text == r) {
                  i[o].show = !0;
                  break
                }
          } else
            for (var o = 0; o < i.length; o++) i[o].show = !0;
          this._view.pageSize = 20, this._view.sourceCollection = i, setTimeout(function() {
            t._view.pageSize = t._filter.maxValues, t._cmbFilter.text = t._filter.filterText || "", t._filterText = t._cmbFilter.text.toLowerCase()
          })
        }, n.prototype.clearEditor = function() {
          this._cmbFilter.text = "", this._filterText = "", this._view.pageSize = 0, this._view.refresh();
          for (var t = this._view.items, e = 0; e < t.length; e++) t[e].show = !1;
          this._view.pageSize = this._filter.maxValues
        }, n.prototype.updateFilter = function() {
          var t = null,
            e = this._view.items;
          if (this._filterText || this._cbSelectAll.indeterminate) {
            t = {};
            for (var i = 0; i < e.length; i++) {
              var n = e[i];
              n.show && (t[n.text] = !0)
            }
          }
          this._filter.showValues = t, this._filter.filterText = ""
        }, n.prototype._filterTextChanged = function() {
          var t = this;
          this._toText && clearTimeout(this._toText), this._toText = setTimeout(function() {
            var e = t._cmbFilter.text.toLowerCase(),
              i = e != t._filterText;
            t._filterText = e, t._view.refresh(), i && (t._cbSelectAll.checked = !0, t._cbSelectAllClicked())
          }, 300)
        }, n.prototype._filterValues = function(t) {
          return !this._filterText || !(!t || !t.text) && t.text.toLowerCase().indexOf(this._filterText) > -1
        }, n.prototype._cbSelectAllClicked = function() {
          for (var t = this._cbSelectAll.checked, e = this._view.items, i = this._divValues.scrollTop, n = 0; n < e.length; n++) e[n].show = t;
          this._view.refresh(), this._divValues.scrollTop = i
        }, n.prototype._updateSelectAllCheck = function() {
          for (var t = 0, e = this._view.items, i = 0; i < e.length; i++) e[i].show && t++;
          0 == t ? (this._cbSelectAll.checked = !1, this._cbSelectAll.indeterminate = !1) : t == e.length ? (this._cbSelectAll.checked = !0, this._cbSelectAll.indeterminate = !1) : this._cbSelectAll.indeterminate = !0
        }, n.controlTemplate = '<div><div wj-part="div-filter"></div><div class="wj-listbox-item"><label><input wj-part="cb-select-all" type="checkbox"> <span wj-part="sp-select-all"></span></label></div><div wj-part="div-values"></div></div>', n
      }(t.Control);
      e.ValueFilterEditor = i
    }(e.filter || (e.filter = {}))
  }(t.grid || (t.grid = {}))
}(wijmo || (wijmo = {}));
var wijmo;
! function(t) {
  ! function(e) {
    ! function(i) {
      "use strict";
      var n = function() {
        function n(e) {
          this._c1 = new i.FilterCondition(this), this._c2 = new i.FilterCondition(this), this._and = !0, this._col = e, this._bnd = e.binding ? new t.Binding(e.binding) : null
        }
        return Object.defineProperty(n.prototype, "condition1", {
          get: function() {
            return this._c1
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(n.prototype, "condition2", {
          get: function() {
            return this._c2
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(n.prototype, "and", {
          get: function() {
            return this._and
          },
          set: function(e) {
            this._and = t.asBoolean(e), this._bnd = this._col && this._col.binding ? new t.Binding(this._col.binding) : null
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(n.prototype, "dataMap", {
          get: function() {
            return this._map
          },
          set: function(i) {
            this._map = t.asType(i, e.DataMap, !0)
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(n.prototype, "column", {
          get: function() {
            return this._col
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(n.prototype, "isActive", {
          get: function() {
            return this._c1.isActive || this._c2.isActive
          },
          enumerable: !0,
          configurable: !0
        }), n.prototype.apply = function(e) {
          var i = this._col,
            n = this._c1,
            r = this._c2;
          if (!this._bnd || !this.isActive) return !0;
          e = this._bnd.getValue(e), i.dataMap ? e = i.dataMap.getDisplayValue(e) : t.isDate(e) ? (t.isString(n.value) || t.isString(r.value)) && (e = t.Globalize.format(e, i.format)) : t.isNumber(e) && (e = t.Globalize.parseFloat(t.Globalize.format(e, i.format)));
          var o = n.apply(e),
            l = r.apply(e);
          return n.isActive && r.isActive ? this._and ? o && l : o || l : n.isActive ? o : !r.isActive || l
        }, n.prototype.clear = function() {
          this._c1.clear(), this._c2.clear(), this.and = !0
        }, n.prototype.implementsInterface = function(t) {
          return "IColumnFilter" == t
        }, n
      }();
      i.ConditionFilter = n
    }(e.filter || (e.filter = {}))
  }(t.grid || (t.grid = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var t = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(t, e) {
        t.__proto__ = e
      } || function(t, e) {
        for (var i in e) e.hasOwnProperty(i) && (t[i] = e[i])
      };
    return function(e, i) {
      function n() {
        this.constructor = e
      }
      t(e, i), e.prototype = null === i ? Object.create(i) : (n.prototype = i.prototype, new n)
    }
  }(),
  wijmo;
! function(t) {
  ! function(e) {
    ! function(e) {
      "use strict";
      var i = function(i) {
        function n(n, r) {
          var o = i.call(this, n) || this;
          o._filter = t.asType(r, e.ConditionFilter, !1);
          var l = o.getTemplate();
          o.applyTemplate("wj-control wj-conditionfilter-editor", l, {
            _divHdr: "div-hdr",
            _divCmb1: "div-cmb1",
            _divVal1: "div-val1",
            _btnAnd: "btn-and",
            _btnOr: "btn-or",
            _spAnd: "sp-and",
            _spOr: "sp-or",
            _divCmb2: "div-cmb2",
            _divVal2: "div-val2"
          });
          var a = t.culture.FlexGridFilter,
            s = a.ariaLabels;
          t.setAriaLabel(o._btnAnd, s.and), t.setAriaLabel(o._btnOr, s.or), t.setText(o._divHdr, a.header), t.setText(o._spAnd, a.and), t.setText(o._spOr, a.or), o._cmb1 = o._createOperatorCombo(o._divCmb1, s.op1), o._cmb2 = o._createOperatorCombo(o._divCmb2, s.op2), o._val1 = o._createValueInput(o._divVal1, s.val1), o._val2 = o._createValueInput(o._divVal2, s.val2);
          var u = o.hostElement;
          return o.addEventListener(u, "change", o._btnAndOrChanged.bind(o)), o.addEventListener(u, "keydown", o._keydown.bind(o)), o.updateEditor(), o
        }
        return __extends(n, i), Object.defineProperty(n.prototype, "filter", {
          get: function() {
            return this._filter
          },
          enumerable: !0,
          configurable: !0
        }), n.prototype.updateEditor = function() {
          var e = this._filter.condition1,
            i = this._filter.condition2;
          this._cmb1.selectedValue = e.operator, this._cmb2.selectedValue = i.operator, this._val1 instanceof t.input.ComboBox ? (this._val1.text = t.changeType(e.value, t.DataType.String, null), this._val2.text = t.changeType(i.value, t.DataType.String, null)) : (this._val1.value = e.value, this._val2.value = i.value);
          var n = this._filter.and;
          this._checkRadio(this._btnAnd, n), this._checkRadio(this._btnOr, !n)
        }, n.prototype.clearEditor = function() {
          this._cmb1.selectedValue = this._cmb2.selectedValue = null, this._val1.text = this._val2.text = null, this._checkRadio(this._btnAnd, !0), this._checkRadio(this._btnOr, !1)
        }, n.prototype.updateFilter = function() {
          var e = this._filter.column,
            i = this._filter.condition1,
            n = this._filter.condition2;
          if (i.operator = this._cmb1.selectedValue, n.operator = this._cmb2.selectedValue, this._val1 instanceof t.input.ComboBox) {
            var r = e.dataType == t.DataType.Date ? t.DataType.String : e.dataType;
            i.value = t.changeType(this._val1.text, r, e.format), n.value = t.changeType(this._val2.text, r, e.format)
          } else i.value = this._val1.value, n.value = this._val2.value;
          this._filter.and = this._btnAnd.checked
        }, n.prototype._createOperatorCombo = function(e, i) {
          var n = this._filter.column,
            r = t.culture.FlexGridFilter,
            o = r.stringOperators,
            l = this._filter.dataMap || n.dataMap,
            a = t.DataType;
          n.dataType == a.Date && this._hasDatePart(n.format) ? o = r.dateOperators : n.dataType != a.Number || l ? n.dataType != a.Boolean || l || (o = r.booleanOperators) : o = r.numberOperators;
          var s = new t.input.ComboBox(e, {
            itemsSource: o,
            displayMemberPath: "name",
            selectedValuePath: "op"
          });
          return t.setAriaLabel(s.inputElement, i), s
        }, n.prototype._createValueInput = function(e, i) {
          var n = this._filter.column,
            r = this._filter.dataMap || n.dataMap,
            o = null,
            l = t.DataType;
          return n.dataType == l.Date && this._hasDatePart(n.format) ? (o = this._hasTimePart(n.format) ? new t.input.InputDateTime(e) : new t.input.InputDate(e)).format = n.format : n.dataType != l.Number || r ? (o = new t.input.ComboBox(e), r ? (o.itemsSource = r.getDisplayValues(), o.isEditable = !0) : n.dataType == l.Boolean && (o.itemsSource = [!0, !1])) : (o = new t.input.InputNumber(e)).format = n.format, o.isRequired = !1, t.setAriaLabel(o.inputElement, i), o
        }, n.prototype._hasDatePart = function(e) {
          return !e || (e = t.culture.Globalize.calendar.patterns[e] || e, /[yMd]+/.test(e))
        }, n.prototype._hasTimePart = function(e) {
          return !!e && (e = t.culture.Globalize.calendar.patterns[e] || e, /[Hmst]+/.test(e))
        }, n.prototype._btnAndOrChanged = function(t) {
          var e = t.target == this._btnAnd,
            i = t.target == this._btnOr;
          (e || i) && (this._checkRadio(this._btnAnd, e), this._checkRadio(this._btnOr, i))
        }, n.prototype._checkRadio = function(t, e) {
          t.checked = e, t.setAttribute("aria-checked", e.toString()), t.setAttribute("tabindex", e ? "1" : "-1")
        }, n.prototype._keydown = function(e) {
          var i = e.target == this._btnAnd,
            n = e.target == this._btnOr;
          if (i || n) switch (e.keyCode) {
            case t.Key.Left:
            case t.Key.Right:
            case t.Key.Up:
            case t.Key.Down:
              var r = i ? this._btnOr : this._btnAnd;
              r.click(), r.focus(), e.preventDefault()
          }
        }, n.controlTemplate = '<div><div wj-part="div-hdr"></div><div wj-part="div-cmb1"></div><br/><div wj-part="div-val1"></div><br/><div role="radiogroup" style="text-align:center"><label><input wj-part="btn-and" type="radio" role="radio"> <span wj-part="sp-and"></span> </label>&nbsp;&nbsp;&nbsp;<label><input wj-part="btn-or" type="radio" role="radio"> <span wj-part="sp-or"></span> </label></div><div wj-part="div-cmb2"></div><br/><div wj-part="div-val2"></div><br/></div>', n
      }(t.Control);
      e.ConditionFilterEditor = i
    }(e.filter || (e.filter = {}))
  }(t.grid || (t.grid = {}))
}(wijmo || (wijmo = {}));
var wijmo;
! function(t) {
  ! function(e) {
    ! function(e) {
      "use strict";
      var i = function() {
        function e(t) {
          this._op = null, this._filter = t
        }
        return Object.defineProperty(e.prototype, "operator", {
          get: function() {
            return this._op
          },
          set: function(e) {
            this._op = t.asEnum(e, n, !0)
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(e.prototype, "value", {
          get: function() {
            return this._val
          },
          set: function(e) {
            if (this._val = e, this._strVal = t.isString(e) ? e.toString().toLowerCase() : null, this._mapVal = null, this._filter) {
              var i = this._filter.dataMap || this._filter.column.dataMap;
              i && (this._mapVal = i.getDisplayValue(e), t.isString(this._mapVal) && (this._strVal = this._mapVal = this._mapVal.toLowerCase()))
            }
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(e.prototype, "isActive", {
          get: function() {
            switch (this._op) {
              case null:
                return !1;
              case n.EQ:
              case n.NE:
                return !0;
              default:
                return null != this._val || null != this._strVal
            }
          },
          enumerable: !0,
          configurable: !0
        }), e.prototype.clear = function() {
          this.operator = null, this.value = null
        }, e.prototype.apply = function(e) {
          var i = this._mapVal || this._strVal || this._val;
          t.isString(e) && (e = e.toLowerCase()), t.isString(i) && null == e && (e = "");
          var r = n;
          switch (this._op) {
            case null:
              return !0;
            case r.EQ:
              return null != e && null != i ? e.valueOf() == i.valueOf() : e == i;
            case r.NE:
              return null != e && null != i ? e.valueOf() != i.valueOf() : e != i;
            case r.GT:
              return e > i;
            case r.GE:
              return e >= i;
            case r.LT:
              return e < i;
            case r.LE:
              return e <= i;
            case r.BW:
              return !(null == this._strVal || !t.isString(e)) && 0 == e.indexOf(this._strVal);
            case r.EW:
              return !!(null != this._strVal && t.isString(e) && e.length >= this._strVal.length) && e.substr(e.length - this._strVal.length) == i;
            case r.CT:
              return !(null == this._strVal || !t.isString(e)) && e.indexOf(this._strVal) > -1;
            case r.NC:
              return !(null == this._strVal || !t.isString(e)) && e.indexOf(this._strVal) < 0
          }
          throw "Unknown operator"
        }, e
      }();
      e.FilterCondition = i;
      var n;
      ! function(t) {
        t[t.EQ = 0] = "EQ", t[t.NE = 1] = "NE", t[t.GT = 2] = "GT", t[t.GE = 3] = "GE", t[t.LT = 4] = "LT", t[t.LE = 5] = "LE", t[t.BW = 6] = "BW", t[t.EW = 7] = "EW", t[t.CT = 8] = "CT", t[t.NC = 9] = "NC"
      }(n = e.Operator || (e.Operator = {}))
    }(e.filter || (e.filter = {}))
  }(t.grid || (t.grid = {}))
}(wijmo || (wijmo = {}));
var wijmo;
! function(t) {
  ! function(e) {
    ! function(e) {
      "use strict";
      var i = function() {
        function i(t, i) {
          this._owner = t, this._col = i, this._valueFilter = new e.ValueFilter(i), this._conditionFilter = new e.ConditionFilter(i)
        }
        return Object.defineProperty(i.prototype, "filterType", {
          get: function() {
            return null != this._filterType ? this._filterType : this._owner.defaultFilterType
          },
          set: function(i) {
            if ((i = t.asEnum(i, e.FilterType, !0)) != this._filterType) {
              var n = this.isActive;
              this.clear(), this._filterType = i, n ? this._owner.apply() : this._col.grid && this._col.grid.invalidate()
            }
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(i.prototype, "dataMap", {
          get: function() {
            return this.conditionFilter.dataMap || this.valueFilter.dataMap
          },
          set: function(t) {
            this.conditionFilter.dataMap = t, this.valueFilter.dataMap = t
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(i.prototype, "valueFilter", {
          get: function() {
            return this._valueFilter
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(i.prototype, "conditionFilter", {
          get: function() {
            return this._conditionFilter
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(i.prototype, "column", {
          get: function() {
            return this._col
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(i.prototype, "isActive", {
          get: function() {
            return this._conditionFilter.isActive || this._valueFilter.isActive
          },
          enumerable: !0,
          configurable: !0
        }), i.prototype.apply = function(t) {
          return this._conditionFilter.apply(t) && this._valueFilter.apply(t)
        }, i.prototype.clear = function() {
          this._valueFilter.clear(), this._conditionFilter.clear()
        }, i.prototype.implementsInterface = function(t) {
          return "IColumnFilter" == t
        }, i
      }();
      e.ColumnFilter = i
    }(e.filter || (e.filter = {}))
  }(t.grid || (t.grid = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var t = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(t, e) {
        t.__proto__ = e
      } || function(t, e) {
        for (var i in e) e.hasOwnProperty(i) && (t[i] = e[i])
      };
    return function(e, i) {
      function n() {
        this.constructor = e
      }
      t(e, i), e.prototype = null === i ? Object.create(i) : (n.prototype = i.prototype, new n)
    }
  }(),
  wijmo;
! function(t) {
  ! function(e) {
    ! function(i) {
      "use strict";
      t._addCultureInfo("FlexGridFilter", {
        ariaLabels: {
          edit: "Edit Filter for Column",
          dialog: "Filter Editor for Column",
          asc: "Sort Column in Ascending Order",
          dsc: "Sort Column in Descending Order",
          search: "Search Item List",
          op1: "First Condition Operator",
          val1: "First Condition Value",
          and: "Require both Conditions",
          or: "Require either Condition",
          op2: "Second Condition Operator",
          val2: "Second Condition Value"
        },
        ascending: "↑ Ascending",
        descending: "↓ Descending",
        apply: "Apply",
        cancel: "Cancel",
        clear: "Clear",
        conditions: "Filter by Condition",
        values: "Filter by Value",
        search: "Search",
        selectAll: "Select All",
        null: "(nothing)",
        header: "Show items where the value",
        and: "And",
        or: "Or",
        stringOperators: [{
          name: "(not set)",
          op: null
        }, {
          name: "Equals",
          op: i.Operator.EQ
        }, {
          name: "Does not equal",
          op: i.Operator.NE
        }, {
          name: "Begins with",
          op: i.Operator.BW
        }, {
          name: "Ends with",
          op: i.Operator.EW
        }, {
          name: "Contains",
          op: i.Operator.CT
        }, {
          name: "Does not contain",
          op: i.Operator.NC
        }],
        numberOperators: [{
          name: "(not set)",
          op: null
        }, {
          name: "Equals",
          op: i.Operator.EQ
        }, {
          name: "Does not equal",
          op: i.Operator.NE
        }, {
          name: "Is Greater than",
          op: i.Operator.GT
        }, {
          name: "Is Greater than or equal to",
          op: i.Operator.GE
        }, {
          name: "Is Less than",
          op: i.Operator.LT
        }, {
          name: "Is Less than or equal to",
          op: i.Operator.LE
        }],
        dateOperators: [{
          name: "(not set)",
          op: null
        }, {
          name: "Equals",
          op: i.Operator.EQ
        }, {
          name: "Is Before",
          op: i.Operator.LT
        }, {
          name: "Is After",
          op: i.Operator.GT
        }],
        booleanOperators: [{
          name: "(not set)",
          op: null
        }, {
          name: "Equals",
          op: i.Operator.EQ
        }, {
          name: "Does not equal",
          op: i.Operator.NE
        }]
      });
      var n = function(n) {
        function r(e, r, o) {
          void 0 === o && (o = !0);
          var l = n.call(this, e, null, !0) || this;
          l.filterChanged = new t.Event, l.buttonClicked = new t.Event, l._filter = t.asType(r, i.ColumnFilter);
          var a = l.getTemplate();
          l.applyTemplate("wj-control wj-content wj-columnfiltereditor", a, {
            _divSort: "div-sort",
            _btnAsc: "btn-asc",
            _btnDsc: "btn-dsc",
            _divType: "div-type",
            _aVal: "a-val",
            _aCnd: "a-cnd",
            _divEdtVal: "div-edt-val",
            _divEdtCnd: "div-edt-cnd",
            _btnApply: "btn-apply",
            _btnCancel: "btn-cancel",
            _btnClear: "btn-clear"
          });
          var s = t.culture.FlexGridFilter,
            u = s.ariaLabels,
            c = l.hostElement,
            d = l.filter.column,
            p = d.grid.collectionView;
          t.setAttribute(c, "role", "dialog"), t.setAriaLabel(c, u.dialog + " " + d.header), t.setAriaLabel(l._btnAsc, u.asc), t.setAriaLabel(l._btnDsc, u.dsc), t.setText(l._btnAsc, s.ascending), t.setText(l._btnDsc, s.descending), t.setText(l._aVal, s.values), t.setText(l._aCnd, s.conditions), t.setText(l._btnApply, s.apply), t.setText(l._btnCancel, s.cancel), t.setText(l._btnClear, s.clear);
          var h = l.filter.conditionFilter.isActive || 0 == (r.filterType & i.FilterType.Value) ? i.FilterType.Condition : i.FilterType.Value;
          l._showFilter(h), o && p && p.canSort || (l._divSort.style.display = "none");
          var f = l._btnClicked.bind(l);
          return l._btnApply.addEventListener("click", f), l._btnCancel.addEventListener("click", f), l._btnClear.addEventListener("click", f), l._btnAsc.addEventListener("click", f), l._btnDsc.addEventListener("click", f), l._aVal.addEventListener("click", f), l._aCnd.addEventListener("click", f), c.addEventListener("keydown", function(e) {
            if (!e.defaultPrevented) {
              var i = e.target.tagName.match(/^(a|button)$/i);
              switch (e.keyCode) {
                case t.Key.Space:
                  i && (l._btnClicked(e), e.preventDefault());
                  break;
                case t.Key.Enter:
                  i ? l._btnClicked(e) : (l.updateFilter(), l.onFilterChanged(), l.onButtonClicked()), e.preventDefault();
                  break;
                case t.Key.Escape:
                  l.onButtonClicked(), e.preventDefault();
                  break;
                case t.Key.Tab:
                  t.moveFocus(l.hostElement, e.shiftKey ? -1 : 1), e.preventDefault()
              }
            }
          }), l
        }
        return __extends(r, n), Object.defineProperty(r.prototype, "filter", {
          get: function() {
            return this._filter
          },
          enumerable: !0,
          configurable: !0
        }), r.prototype.updateEditor = function() {
          this._edtVal && this._edtVal.updateEditor(), this._edtCnd && this._edtCnd.updateEditor()
        }, r.prototype.updateFilter = function() {
          switch (this._getFilterType()) {
            case i.FilterType.Value:
              this._edtVal.updateFilter(), this.filter.conditionFilter.clear();
              break;
            case i.FilterType.Condition:
              this._edtCnd.updateFilter(), this.filter.valueFilter.clear()
          }
        }, r.prototype.onFilterChanged = function(t) {
          this.filterChanged.raise(this, t)
        }, r.prototype.onButtonClicked = function(t) {
          this.buttonClicked.raise(this, t)
        }, r.prototype._handleResize = function() {
          this.isTouching || this._wasTouching || this.onButtonClicked()
        }, r.prototype._showFilter = function(t) {
          this._wasTouching = this.isTouching, t == i.FilterType.Value && null == this._edtVal && (this._edtVal = new i.ValueFilterEditor(this._divEdtVal, this.filter.valueFilter)), t == i.FilterType.Condition && null == this._edtCnd && (this._edtCnd = new i.ConditionFilterEditor(this._divEdtCnd, this.filter.conditionFilter)), 0 != (t & this.filter.filterType) && (t == i.FilterType.Value ? (this._divEdtVal.style.display = "", this._divEdtCnd.style.display = "none", this._enableLink(this._aVal, !1), this._enableLink(this._aCnd, !0), this._edtVal.focus()) : (this._divEdtVal.style.display = "none", this._divEdtCnd.style.display = "", this._enableLink(this._aVal, !0), this._enableLink(this._aCnd, !1), this._edtCnd.focus()));
          var e = this._divType.style;
          switch (this.filter.filterType) {
            case i.FilterType.None:
            case i.FilterType.Condition:
            case i.FilterType.Value:
              e.display = "none";
              break;
            default:
              e.display = ""
          }
        }, r.prototype._enableLink = function(e, i) {
          t.toggleClass(e, "wj-state-disabled", !i), t.setAttribute(e, "href", i ? "" : null), t.setAttribute(e, "disabled", i ? null : "disabled")
        }, r.prototype._getFilterType = function() {
          var t = e.filter.FilterType;
          return "none" != this._divEdtVal.style.display ? t.Value : t.Condition
        }, r.prototype._btnClicked = function(e) {
          var n = e.target;
          if (e.preventDefault(), e.stopPropagation(), !t.hasClass(n, "wj-state-disabled")) {
            if (n == this._aVal) return this._showFilter(i.FilterType.Value), void t.moveFocus(this._edtVal.hostElement, 0);
            if (n == this._aCnd) return this._showFilter(i.FilterType.Condition), void t.moveFocus(this._edtCnd.hostElement, 0);
            if (n == this._btnAsc || n == this._btnDsc) {
              var r = this.filter.column,
                o = r.sortMemberPath ? r.sortMemberPath : r.binding,
                l = r.grid.collectionView,
                a = new t.collections.SortDescription(o, e.target == this._btnAsc);
              l.sortDescriptions.deferUpdate(function() {
                l.sortDescriptions.clear(), l.sortDescriptions.push(a)
              })
            }
            n == this._btnApply ? (this.updateFilter(), this.onFilterChanged()) : n == this._btnClear ? this.filter.isActive && (this.filter.clear(), this.onFilterChanged()) : this.updateEditor(), this.onButtonClicked()
          }
        }, r.controlTemplate = '<div><div wj-part="div-sort"><button wj-part="btn-asc" class="wj-btn" style="min-width:95px"></button>&nbsp;&nbsp;&nbsp;<button wj-part="btn-dsc" class="wj-btn" style="min-width:95px"></button></div><div wj-part="div-type" class="wj-filtertype"><a wj-part="a-cnd" href="" draggable="false"></a>&nbsp;|&nbsp;<a wj-part="a-val" href="" draggable="false"></a></div><div wj-part="div-edt-val" tabindex="-1"></div><div wj-part="div-edt-cnd" tabindex="-1"></div><div style="text-align:right;margin-top:10px"><button wj-part="btn-apply" class="wj-btn"></button>&nbsp;&nbsp;<button wj-part="btn-cancel" class="wj-btn"></button>&nbsp;&nbsp;<button wj-part="btn-clear" class="wj-btn"></button></div>', r
      }(t.Control);
      i.ColumnFilterEditor = n
    }(e.filter || (e.filter = {}))
  }(t.grid || (t.grid = {}))
}(wijmo || (wijmo = {}));
var wijmo;
! function(t) {
  ! function(e) {
    ! function(i) {
      "use strict";
      var n;
      ! function(t) {
        t[t.None = 0] = "None", t[t.Condition = 1] = "Condition", t[t.Value = 2] = "Value", t[t.Both = 3] = "Both"
      }(n = i.FilterType || (i.FilterType = {}));
      var r = function() {
        function r(i, r) {
          this._showIcons = !0, this._showSort = !0, this._defFilterType = n.Both, this.filterApplied = new t.Event, this.filterChanging = new t.Event, this.filterChanged = new t.Event;
          var o = "Missing dependency: FlexGridFilter requires ";
          t.assert(null != t.grid, o + "wijmo.grid."), t.assert(null != t.input, o + "wijmo.input."), this._filters = [], this._g = t.asType(i, e.FlexGrid, !1), this._g.formatItem.addHandler(this._formatItem.bind(this)), this._g.itemsSourceChanged.addHandler(this.clear.bind(this));
          var l = this._g.hostElement;
          i.addEventListener(l, "mousedown", this._mousedown.bind(this), !0), i.addEventListener(l, "click", this._click.bind(this), !0), i.addEventListener(l, "keydown", this._keydown.bind(this), !0), this._g.invalidate(), r && t.copy(this, r)
        }
        return Object.defineProperty(r.prototype, "grid", {
          get: function() {
            return this._g
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(r.prototype, "filterColumns", {
          get: function() {
            return this._filterColumns
          },
          set: function(e) {
            this._filterColumns = t.asArray(e), this.clear()
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(r.prototype, "showFilterIcons", {
          get: function() {
            return this._showIcons
          },
          set: function(e) {
            e != this.showFilterIcons && (this._showIcons = t.asBoolean(e), this._g && this._g.invalidate())
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(r.prototype, "showSortButtons", {
          get: function() {
            return this._showSort
          },
          set: function(e) {
            this._showSort = t.asBoolean(e)
          },
          enumerable: !0,
          configurable: !0
        }), r.prototype.getColumnFilter = function(t, e) {
          void 0 === e && (e = !0), t = this._asColumn(t);
          for (var n = 0; n < this._filters.length; n++)
            if (this._filters[n].column == t) return this._filters[n];
          if (e && t.binding) {
            var r = new i.ColumnFilter(this, t);
            return this._filters.push(r), r
          }
          return null
        }, Object.defineProperty(r.prototype, "defaultFilterType", {
          get: function() {
            return this._defFilterType
          },
          set: function(e) {
            (e = t.asEnum(e, n, !1)) != this.defaultFilterType && (this._defFilterType = e, this._g.invalidate(), this.clear())
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(r.prototype, "filterDefinition", {
          get: function() {
            for (var t = {
              defaultFilterType: this.defaultFilterType,
              filters: []
            }, e = 0; e < this._filters.length; e++) {
              var i = this._filters[e];
              if (i && i.column && i.column.binding && (i.isActive || i.filterType != this.defaultFilterType)) {
                var n = {
                  binding: i.column.binding
                };
                if (i.conditionFilter.isActive) {
                  var r = i.conditionFilter;
                  n = {
                    binding: i.column.binding,
                    type: "condition",
                    condition1: {
                      operator: r.condition1.operator,
                      value: r.condition1.value
                    },
                    and: r.and,
                    condition2: {
                      operator: r.condition2.operator,
                      value: r.condition2.value
                    }
                  }
                } else if (i.valueFilter.isActive) {
                  var o = i.valueFilter;
                  n = {
                    binding: i.column.binding,
                    type: "value",
                    filterText: o.filterText,
                    showValues: o.showValues
                  }
                }
                i.filterType != this.defaultFilterType && (n.filterType = i.filterType), t.filters.push(n)
              }
            }
            return JSON.stringify(t)
          },
          set: function(e) {
            if (e = t.asString(e), this.clear(), e) {
              var i = JSON.parse(e);
              this.defaultFilterType = i.defaultFilterType;
              for (var r = 0; r < i.filters.length; r++) {
                var o = i.filters[r],
                  l = this._g.getColumn(o.binding),
                  a = this.getColumnFilter(l, !0);
                if (a) switch (null != o.filterType && (a.filterType = t.asEnum(o.filterType, n)), o.type) {
                  case "condition":
                    var s = a.conditionFilter;
                    s.condition1.value = l.dataType == t.DataType.Date ? t.changeType(o.condition1.value, l.dataType, null) : o.condition1.value, s.condition1.operator = o.condition1.operator, s.and = o.and, s.condition2.value = l.dataType == t.DataType.Date ? t.changeType(o.condition2.value, l.dataType, null) : o.condition2.value, s.condition2.operator = o.condition2.operator;
                    break;
                  case "value":
                    var u = a.valueFilter;
                    u.filterText = o.filterText, u.showValues = o.showValues
                }
              }
            }
            this.apply()
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(r.prototype, "activeEditor", {
          get: function() {
            return t.Control.getControl(this._divEdt)
          },
          enumerable: !0,
          configurable: !0
        }), r.prototype.editColumnFilter = function(n, r, o) {
          var l = this;
          this.closeEditor(), n = this._asColumn(n);
          var a = this._g,
            s = new e.CellRangeEventArgs(a.cells, new e.CellRange(-1, n.index));
          if (this.onFilterChanging(s)) {
            s.cancel = !0;
            var u = t.createElement('<div class="wj-dropdown-panel"></div>'),
              c = this.getColumnFilter(n),
              d = new i.ColumnFilterEditor(u, c, this.showSortButtons);
            this._divEdt = u, this._edtCol = n, a.rightToLeft && (u.dir = "rtl"), d.filterChanged.addHandler(function() {
              s.cancel = !1, setTimeout(function() {
                s.cancel || l.apply()
              })
            }), d.buttonClicked.addHandler(function() {
              l.closeEditor(), a.focus(), l.onFilterChanged(s)
            }), d.lostFocus.addHandler(function() {
              setTimeout(function() {
                var e = t.Control.getControl(l._divEdt);
                e && !e.containsFocus() && l.closeEditor()
              }, 10)
            });
            var p = r ? r.col : n.index;
            r || a.columns[p].binding == n.binding || (p = a.selection.leftCol), a._edtHdl._commitRowEdits(), a.scrollIntoView(-1, p, !0);
            var h = a.columnHeaders,
              f = r && r.panel == h ? r.row : h.rows.length - 1,
              _ = p,
              b = h.getCellBoundingRect(f, _),
              v = o || h.getCellElement(f, _);
            v ? t.showPopup(u, v, !1, !1, !1) : t.showPopup(u, b), this._setAriaExpanded(v, !0), this._setAriaExpanded(a.cells.getCellElement(-1, _), !0);
            for (var y = d.hostElement.querySelectorAll("input"), g = 0; g < y.length; g++) {
              var m = y[g];
              if (m.offsetHeight > 0 && m.tabIndex > -1 && !m.disabled) {
                m.focus();
                break
              }
            }
            d.containsFocus() || d.focus()
          } else this._divEdt = this._edtCol = null
        }, r.prototype._setAriaExpanded = function(e, i) {
          if (e) {
            var n = e.querySelector("." + r._WJC_FILTER);
            t.setAttribute(n, "aria-expanded", i)
          }
        }, r.prototype.closeEditor = function() {
          var e = this._g,
            i = t.Control.getControl(this._divEdt),
            n = this._edtCol;
          if (i && t.hidePopup(i.hostElement, function() {
            i.dispose()
          }), n) {
            var r = e.columnHeaders,
              o = r.rows.length ? r.getCellElement(r.rows.length - 1, n.index) : null;
            this._setAriaExpanded(o, !1), o = e.cells.getCellElement(-1, n.index), this._setAriaExpanded(o, !1)
          }
          this._divEdt = null, this._edtCol = null
        }, r.prototype.apply = function() {
          var e = this._g.collectionView;
          if (e) {
            var i = this._g.editableCollectionView;
            i && (i.commitEdit(), i.commitNew()), e.filter = this._filter.bind(this)
          }
          var n = e ? e.updateFilterDefinition : null;
          t.isFunction(n) && n.call(e, this), this.onFilterApplied()
        }, r.prototype.clear = function() {
          this._filters.length && (this._filters = [], this.apply())
        }, r.prototype.onFilterApplied = function(t) {
          this.filterApplied.raise(this, t)
        }, r.prototype.onFilterChanging = function(t) {
          return this.filterChanging.raise(this, t), !t.cancel
        }, r.prototype.onFilterChanged = function(t) {
          this.filterChanged.raise(this, t)
        }, r.prototype._asColumn = function(i) {
          return t.isString(i) ? this._g.getColumn(i) : t.isNumber(i) ? this._g.columns[i] : t.asType(i, e.Column, !1)
        }, r.prototype._filter = function(t) {
          for (var e = 0; e < this._filters.length; e++)
            if (!this._filters[e].apply(t)) return !1;
          return !0
        }, r.prototype._formatItem = function(i, r) {
          if (r.panel == i.columnHeaders) {
            var o = this._g,
              l = o.getMergedRange(r.panel, r.row, r.col) || new e.CellRange(r.row, r.col),
              a = o.columns[l.col],
              s = o._getBindingColumn(r.panel, r.row, a),
              u = r.cell;
            if (l.row2 == r.panel.rows.length - 1 || a != s) {
              var c = this.getColumnFilter(s, this.defaultFilterType != n.None);
              this._filterColumns && this._filterColumns.indexOf(s.binding) < 0 && (c = null), c ? (t.toggleClass(u, "wj-filter-on", c.isActive), t.toggleClass(u, "wj-filter-off", !c.isActive)) : (t.removeClass(u, "wj-filter-on"), t.removeClass(u, "wj-filter-off")), c && c.filterType != n.None && (this._showIcons && this._addFilterButton(s, c, u), 0 == r.row && (u = o.cells.getCellElement(-1, r.col)) && this._addFilterButton(a, c, u))
            }
          }
        }, r.prototype._addFilterButton = function(e, i, n) {
          var o = r._WJC_FILTER,
            l = t.createElement('<button class="wj-btn wj-btn-glyph wj-right ' + o + '" type="button" tabindex="-1"><span class="wj-glyph-filter"></span></button>');
          t.setAriaLabel(l, t.culture.FlexGridFilter.ariaLabels.edit + " " + e.header), t.setAttribute(l, "aria-haspopup", "dialog"), t.setAttribute(l, "aria-expanded", !1), t.setAttribute(l, "aria-describedby", e.describedById), t.setAttribute(l, "aria-pressed", i.isActive), n.querySelector("." + o) || (1 == n.children.length && (n = n.querySelector("div") || n), n.appendChild(l))
        }, r.prototype._mousedown = function(t) {
          this._toggleEditor(t) && (this._tmd = !0, t.stopPropagation(), t.preventDefault())
        }, r.prototype._click = function(t) {
          (this._tmd || this._toggleEditor(t)) && (t.stopPropagation(), t.preventDefault()), this._tmd = !1
        }, r.prototype._toggleEditor = function(e) {
          var i = this;
          if (this._tmd = !1, !e.defaultPrevented && 0 == e.button)
            if (t.closestClass(e.target, r._WJC_FILTER)) {
              var n = this._g,
                o = n.hitTest(e.target);
              if (o.panel || (o = n.hitTest(e)), o.panel == n.columnHeaders || o.panel == n.cells && -1 == o.row) {
                var l = n.columns[o.col],
                  a = n._getBindingColumn(o.panel, o.row, l);
                return this._divEdt && this._edtCol == a ? (this.closeEditor(), n.focus()) : setTimeout(function() {
                  i.editColumnFilter(a, o)
                }, this._divEdt ? 100 : 0), !0
              }
            } else this.closeEditor();
          return !1
        }, r.prototype._keydown = function(e) {
          if (!e.defaultPrevented && !e.ctrlKey && e.altKey && (e.keyCode == t.Key.Down || e.keyCode == t.Key.Up)) {
            var i = this.grid,
              n = i.selection,
              r = n.col > -1 ? i.columns[n.col] : null,
              o = r ? i._getBindingColumn(i.cells, n.row, r) : null;
            o && !o.dataMap && this.getColumnFilter(o, !0) && (this.editColumnFilter(o), e.preventDefault(), e.stopPropagation())
          }
        }, r._WJC_FILTER = "wj-elem-filter", r
      }();
      i.FlexGridFilter = r
    }(e.filter || (e.filter = {}))
  }(t.grid || (t.grid = {}))
}(wijmo || (wijmo = {}));
