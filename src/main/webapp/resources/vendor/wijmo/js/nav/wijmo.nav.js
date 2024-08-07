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
var __extends = this && this.__extends || function() {
    var e = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(e, t) {
        e.__proto__ = t
      } || function(e, t) {
        for (var n in t) t.hasOwnProperty(n) && (e[n] = t[n])
      };
    return function(t, n) {
      function i() {
        this.constructor = t
      }
      e(t, n), t.prototype = null === n ? Object.create(n) : (i.prototype = n.prototype, new i)
    }
  }(),
  wijmo;
! function(e) {
  ! function(t) {
    var n = function(n) {
      function i(r, s) {
        var o = n.call(this, r) || this;
        o._itmPath = new t._BindingArray("items"), o._dspPath = new t._BindingArray("header"), o._imgPath = new t._BindingArray, o._html = !1, o._animated = !0, o._xpndOnClick = !0, o._autoColl = !0, o._showChk = !1, o._srch = "", o._isReadOnly = !0, o.itemsSourceChanged = new e.Event, o.loadingItems = new e.Event, o.loadedItems = new e.Event, o.itemClicked = new e.Event, o.selectedItemChanged = new e.Event, o.checkedItemsChanged = new e.Event, o.isCollapsedChanging = new e.Event, o.isCollapsedChanged = new e.Event, o.isCheckedChanging = new e.Event, o.isCheckedChanged = new e.Event, o.formatItem = new e.Event, o.dragStart = new e.Event, o.dragOver = new e.Event, o.drop = new e.Event, o.dragEnd = new e.Event, o.nodeEditStarting = new e.Event, o.nodeEditStarted = new e.Event, o.nodeEditEnding = new e.Event, o.nodeEditEnded = new e.Event;
        var a = o.getTemplate();
        o.applyTemplate("wj-control wj-content wj-treeview", a, {
          _root: "root"
        });
        var l = o.hostElement;
        return e.setAttribute(l, "role", "tree", !0), e.addClass(o._root, i._CNDL), e.setAttribute(o._root, "role", "group", !0), o.addEventListener(l, "mousedown", o._mousedown.bind(o)), o.addEventListener(l, "click", o._click.bind(o)), o.addEventListener(l, "keydown", o._keydown.bind(o)), o.addEventListener(l, "keypress", o._keypress.bind(o)), o.addEventListener(l, "wheel", function(e) {
          l.scrollHeight > l.offsetHeight && (e.deltaY < 0 && 0 == l.scrollTop || e.deltaY > 0 && l.scrollTop + l.offsetHeight >= l.scrollHeight) && e.preventDefault()
        }), o.addEventListener(l, "blur", function(t) {
          o._edtNode && !e.contains(o._edtNode.element, e.getActiveElement()) && o.finishEditing()
        }, !0), o.initialize(s), o.refresh(), o
      }
      return __extends(i, n), Object.defineProperty(i.prototype, "itemsSource", {
        get: function() {
          return this._items
        },
        set: function(t) {
          this._items != t && (this._items = e.asArray(t), this.onItemsSourceChanged(), this._reload())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "childItemsPath", {
        get: function() {
          return this._itmPath.path
        },
        set: function(e) {
          e != this.childItemsPath && (this._itmPath.path = e, this._reload())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "displayMemberPath", {
        get: function() {
          return this._dspPath.path
        },
        set: function(e) {
          e != this.displayMemberPath && (this._dspPath.path = e, this._reload())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "imageMemberPath", {
        get: function() {
          return this._imgPath.path
        },
        set: function(e) {
          e != this.imageMemberPath && (this._imgPath.path = e, this._reload())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "isContentHtml", {
        get: function() {
          return this._html
        },
        set: function(t) {
          t != this._html && (this._html = e.asBoolean(t), this._reload())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "showCheckboxes", {
        get: function() {
          return this._showChk
        },
        set: function(t) {
          t != this._showChk && (this._showChk = e.asBoolean(t), this._reload())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "autoCollapse", {
        get: function() {
          return this._autoColl
        },
        set: function(t) {
          this._autoColl = e.asBoolean(t)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "isAnimated", {
        get: function() {
          return this._animated
        },
        set: function(t) {
          t != this._animated && (this._animated = e.asBoolean(t))
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "isReadOnly", {
        get: function() {
          return this._isReadOnly
        },
        set: function(t) {
          this._isReadOnly = e.asBoolean(t), e.toggleClass(this.hostElement, "wj-state-readonly", this.isReadOnly)
        },
        enumerable: !0,
        configurable: !0
      }), i.prototype.startEditing = function(n) {
        if (this.isReadOnly) return !1;
        if (n || (n = this.selectedNode), !n || n.isDisabled) return !1;
        if (!this.finishEditing()) return !1;
        var r = n.element.querySelector("." + i._CNDT);
        if (!r) return !1;
        var s = new t.TreeNodeEventArgs(n);
        if (!this.onNodeEditStarting(s)) return !1;
        r.tabIndex = 0, r.focus(), r.contentEditable = "true", r.style.cursor = "auto";
        var o = document.createRange();
        o.selectNodeContents(r);
        var a = getSelection();
        return a.removeAllRanges(), a.addRange(o), r.focus(), e.setAttribute(r, "autocomplete", "off"), e.setAttribute(r, "autocorrect", "off"), this._edtNode = n, this.onNodeEditStarted(s), !0
      }, i.prototype.finishEditing = function(e) {
        var n = this._edtNode;
        if (n) {
          var r = n.element.querySelector("." + i._CNDT);
          if (!r) return !1;
          var s = new t.TreeNodeEventArgs(n);
          if (!this.onNodeEditEnding(s)) return !1;
          var o = n.dataItem,
            a = n.level;
          this.isContentHtml ? e ? r.innerHTML = this._dspPath.getValue(o, a) : this._dspPath.setValue(o, a, r.innerHTML) : e ? r.textContent = this._dspPath.getValue(o, a) : this._dspPath.setValue(o, a, r.textContent), document.createRange().selectNodeContents(r), getSelection().removeAllRanges(), r.contentEditable = "false", r.style.cursor = "", this._edtNode = null, this.onNodeEditEnded(s)
        }
        return !0
      }, Object.defineProperty(i.prototype, "allowDragging", {
        get: function() {
          return null != this._dd
        },
        set: function(n) {
          if (n != this.allowDragging) {
            e.asBoolean(n) ? this._dd = new t._TreeDragDropManager(this) : (this._dd.dispose(), this._dd = null);
            for (var r = this.hostElement.querySelectorAll("." + i._CND), s = 0; s < r.length; s++) {
              var o = r[s];
              e.setAttribute(o, "draggable", !!this._dd || null)
            }
          }
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "expandOnClick", {
        get: function() {
          return this._xpndOnClick
        },
        set: function(t) {
          this._xpndOnClick = e.asBoolean(t)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "selectedItem", {
        get: function() {
          return this._selNode ? this._selNode.dataItem : null
        },
        set: function(e) {
          e != this.selectedItem && (this.selectedNode = e ? this.getNode(e) : null)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "selectedNode", {
        get: function() {
          return this._selNode
        },
        set: function(t) {
          if (t != this.selectedNode)
            if (this._prevSel = this._selNode, t) t.select();
            else if (this._selNode) {
              var n = this._selNode.element;
              e.removeClass(n, i._CSEL), e.setAttribute(n, "aria-selected", !1), this._selNode = null, this._updateFocus(this._prevSel), this.onSelectedItemChanged()
            }
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "selectedPath", {
        get: function() {
          for (var e = [], t = this.selectedNode; t; t = t.parentNode) {
            var n = this._dspPath.getValue(t.dataItem, t.level);
            e.splice(0, 0, n)
          }
          return e
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "checkedItems", {
        get: function() {
          if (null == this._chkItems) {
            var e = i,
              t = "." + e._CND + "." + e._CEMP + " > input:checked." + e._CNDC,
              n = this._root.querySelectorAll(t);
            this._chkItems = [];
            for (var r = 0; r < n.length; r++) {
              var s = n[r].parentElement[e._DATAITEM_KEY];
              this._chkItems.push(s)
            }
          }
          return this._chkItems
        },
        set: function(e) {
          if (this.showCheckboxes)
            for (var n = i, r = "." + n._CND + "." + n._CEMP, s = this._root.querySelectorAll(r), o = 0; o < s.length; o++) {
              var a = new t.TreeNode(this, s[o]),
                l = e.indexOf(a.dataItem) > -1;
              a.isChecked != l && (a.isChecked = l)
            }
        },
        enumerable: !0,
        configurable: !0
      }), i.prototype.checkAllItems = function(e) {
        if (this.showCheckboxes)
          for (var n = i, r = "." + n._CND + "." + n._CEMP, s = this._root.querySelectorAll(r), o = 0; o < s.length; o++) {
            var a = new t.TreeNode(this, s[o]);
            a.isChecked != e && (a.isChecked = e)
          }
      }, Object.defineProperty(i.prototype, "totalItemCount", {
        get: function() {
          return this.hostElement.querySelectorAll("." + i._CND).length
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "lazyLoadFunction", {
        get: function() {
          return this._lazyLoad
        },
        set: function(t) {
          t != this._lazyLoad && (this._lazyLoad = e.asFunction(t), this._reload())
        },
        enumerable: !0,
        configurable: !0
      }), i.prototype.getFirstNode = function(e, n) {
        var r = this.hostElement.querySelector("." + i._CND),
          s = r ? new t.TreeNode(this, r) : null;
        return e && s && !s.element.offsetHeight && (s = s.next(e, n)), n && s && s.isDisabled && (s = s.next(e, n)), s
      }, i.prototype.getLastNode = function(e, n) {
        var r = this.hostElement.querySelectorAll("." + i._CND + ":last-child"),
          s = r.length ? new t.TreeNode(this, r[r.length - 1]) : null;
        return e && s && !s.element.offsetHeight && (s = s.previous(e, n)), n && s && s.isDisabled && (s = s.previous(e, n)), s
      }, Object.defineProperty(i.prototype, "nodes", {
        get: function() {
          return t.TreeNode._getChildNodes(this, this._root)
        },
        enumerable: !0,
        configurable: !0
      }), i.prototype.getNode = function(e) {
        this._isDirty && this._loadTree();
        for (var n = this.hostElement.querySelectorAll("." + i._CND), r = 0; r < n.length; r++) {
          var s = n[r];
          if (s[i._DATAITEM_KEY] == e) return new t.TreeNode(this, s)
        }
        return null
      }, i.prototype.addChildNode = function(e, n) {
        var i = this._createNode(n),
          r = this.nodes;
        return r ? e < r.length ? i.move(r[e], t.DropPosition.Before) : i.move(r[r.length - 1], t.DropPosition.After) : i.move(this, t.DropPosition.Into), i
      }, i.prototype.collapseToLevel = function(e) {
        var t = this._animated,
          n = this._autoColl;
        this._animated = this._autoColl = !1, this._collapseToLevel(this.nodes, e, 0), this._animated = t, this._autoColl = n
      }, i.prototype.loadTree = function(e) {
        this._loadTree(e)
      }, i.prototype.onItemsSourceChanged = function(e) {
        this.itemsSourceChanged.raise(this, e)
      }, i.prototype.onLoadingItems = function(e) {
        return this.loadingItems.raise(this, e), !e.cancel
      }, i.prototype.onLoadedItems = function(e) {
        this.loadedItems.raise(this, e)
      }, i.prototype.onItemClicked = function(e) {
        this.itemClicked.raise(this, e)
      }, i.prototype.onSelectedItemChanged = function(e) {
        this.selectedItemChanged.raise(this, e)
      }, i.prototype.onCheckedItemsChanged = function(e) {
        this._chkItems = null, this.checkedItemsChanged.raise(this, e)
      }, i.prototype.onIsCollapsedChanging = function(e) {
        return this.isCollapsedChanging.raise(this, e), !e.cancel
      }, i.prototype.onIsCollapsedChanged = function(e) {
        this.isCollapsedChanged.raise(this, e)
      }, i.prototype.onIsCheckedChanging = function(e) {
        return this.isCheckedChanging.raise(this, e), !e.cancel
      }, i.prototype.onIsCheckedChanged = function(e) {
        this.isCheckedChanged.raise(this, e)
      }, i.prototype.onFormatItem = function(e) {
        this.formatItem.raise(this, e)
      }, i.prototype.onDragStart = function(e) {
        return this.dragStart.raise(this, e), !e.cancel
      }, i.prototype.onDragOver = function(e) {
        return this.dragOver.raise(this, e), !e.cancel
      }, i.prototype.onDrop = function(e) {
        return this.drop.raise(this, e), !e.cancel
      }, i.prototype.onDragEnd = function(e) {
        this.dragEnd.raise(this, e)
      }, i.prototype.onNodeEditStarting = function(e) {
        return this.nodeEditStarting.raise(this, e), !e.cancel
      }, i.prototype.onNodeEditStarted = function(e) {
        this.nodeEditStarted.raise(this, e)
      }, i.prototype.onNodeEditEnding = function(e) {
        return this.nodeEditEnding.raise(this, e), !e.cancel
      }, i.prototype.onNodeEditEnded = function(e) {
        this.nodeEditEnded.raise(this, e)
      }, i.prototype.refresh = function(e) {
        void 0 === e && (e = !0), n.prototype.refresh.call(this, e), !this.isUpdating && this._isDirty && this._loadTree()
      }, i.prototype._updateFocus = function(e) {
        var t = this._selNode;
        t && (t.element.tabIndex = this._orgTabIndex), this.hostElement.tabIndex = t ? -1 : this._orgTabIndex, this.containsFocus() && (t ? t.element.focus() : this.hostElement.focus()), e && (e.element.tabIndex = -1)
      }, i.prototype._raiseCheckedItemsChanged = function() {
        var e = this;
        this._toItemsChanged && clearTimeout(this._toItemsChanged), this._toItemsChanged = setTimeout(function() {
          e._toItemsChanged = null, e.onCheckedItemsChanged()
        }, 10)
      }, i.prototype._reload = function() {
        this._isDirty = !0, this.invalidate()
      }, i.prototype._createNode = function(e) {
        return new i(document.createElement("div"), {
          childItemsPath: this.childItemsPath,
          displayMemberPath: this.displayMemberPath,
          imageMemberPath: this.imageMemberPath,
          isContentHtml: this.isContentHtml,
          showCheckboxes: this.showCheckboxes,
          itemsSource: [e]
        }).getFirstNode()
      }, i.prototype._mousedown = function(n) {
        if (!n.defaultPrevented) {
          var r = e.closest(n.target, "input." + i._CNDC),
            s = e.closestClass(n.target, i._CND),
            o = s ? new t.TreeNode(this, s) : null;
          o && !o.isDisabled && (this.selectedNode = o), this._dnIndet = r && r.indeterminate
        }
      }, i.prototype._click = function(n) {
        if (!n.defaultPrevented) {
          var r = e.closestClass(n.target, i._CND);
          if (r) {
            var s = new t.TreeNode(this, r),
              o = e.closest(n.target, "input." + i._CNDC);
            if (s.isDisabled) return;
            if (!o && s.equals(this._edtNode)) return;
            if (r.focus(), o && (n.preventDefault(), n.stopPropagation(), setTimeout(function() {
              o.indeterminate = !1, s.isChecked = !s.isChecked
            })), !o) {
              var a = n.target,
                l = (n.ctrlKey || n.metaKey) && !s.hasPendingChildren,
                d = r.getBoundingClientRect(),
                h = !1;
              (this.rightToLeft ? d.right - n.clientX : n.clientX - d.left) <= a.offsetHeight ? (l ? this.collapseToLevel(s.isCollapsed ? s.level + 1 : s.level) : s.isCollapsed = !s.isCollapsed, h = !0) : this.expandOnClick && s.isCollapsed && (l ? this.collapseToLevel(s.level) : s.isCollapsed = !1, h = !0), h && l && this.selectedNode && this.selectedNode.ensureVisible(), h || this.isReadOnly || this.selectedNode && this.selectedNode.equals(this._prevSel) && this.startEditing()
            }
            this.selectedItem && this.onItemClicked()
          }
        }
      }, i.prototype._keydown = function(t) {
        if (!t.defaultPrevented) {
          var n = this._getKeyCode(t),
            i = this._selNode,
            r = void 0,
            s = !0;
          if (!i) switch (n) {
            case e.Key.Up:
            case e.Key.Down:
            case e.Key.Left:
            case e.Key.Right:
            case e.Key.Enter:
            case e.Key.Home:
            case e.Key.End:
              if (r = this.getFirstNode(!0, !0)) return this.selectedNode = r, void t.preventDefault()
          }
          if (i && !i.isDisabled) {
            switch (n) {
              case e.Key.F2:
                this.startEditing(), t.preventDefault();
                break;
              case e.Key.Escape:
                this.finishEditing(!0), t.preventDefault();
                break;
              case e.Key.Up:
              case e.Key.Down:
                this.finishEditing();
                break;
              case e.Key.Enter:
                this._edtNode ? (this.finishEditing(), n = e.Key.Down) : (this.startEditing(), t.preventDefault())
            }
            if (this._edtNode) return;
            if (this.rightToLeft) switch (n) {
              case e.Key.Left:
                n = e.Key.Right;
                break;
              case e.Key.Right:
                n = e.Key.Left
            }
            switch (n) {
              case e.Key.Left:
                !i.isCollapsed && i.hasChildren ? i.setCollapsed(!0) : (i = i.parentNode) && i.select();
                break;
              case e.Key.Right:
                i.setCollapsed(!1);
                break;
              case e.Key.Up:
                r = i.previous(!0, !0);
                break;
              case e.Key.Down:
                r = i.next(!0, !0);
                break;
              case e.Key.Home:
                r = this.getFirstNode(!0, !0);
                break;
              case e.Key.End:
                r = this.getLastNode(!0, !0);
                break;
              case e.Key.Space:
                if (this.selectedItem) {
                  var o = i.checkBox;
                  o && (i.isChecked = !o.checked)
                }
                break;
              case e.Key.Enter:
                this.selectedItem && this.onItemClicked();
                break;
              default:
                s = !1
            }
            s && (t.preventDefault(), r && r.select())
          }
        }
      }, i.prototype._keypress = function(t) {
        var n = this;
        if (!t.defaultPrevented) {
          if (t.ctrlKey || t.metaKey || t.altKey) return;
          if (t.target instanceof HTMLInputElement) return;
          if (this._edtNode) return;
          if (t.charCode > 32 && this.startEditing(this.selectedNode)) {
            var r = e.getActiveElement();
            if (e.contains(this._edtNode.element, r)) {
              r.textContent = String.fromCharCode(t.charCode), t.preventDefault();
              var s = document.createRange();
              s.selectNodeContents(r), s.collapse(!1);
              var o = getSelection();
              o.removeAllRanges(), o.addRange(s)
            }
            return
          }
          if (t.charCode > 32 || 32 == t.charCode && this._srch) {
            t.preventDefault(), this._srch += String.fromCharCode(t.charCode).toLowerCase(), this._toSrch && clearTimeout(this._toSrch), this._toSrch = setTimeout(function() {
              n._toSrch = null, n._srch = ""
            }, i._AS_DLY);
            var a = this._findNext();
            null == a && this._srch.length > 1 && (this._srch = this._srch[this._srch.length - 1], a = this._findNext()), null != a && (this.selectedItem = a)
          }
        }
      }, i.prototype._findNext = function() {
        if (this.hostElement && this.selectedItem) {
          var e = this.getNode(this.selectedItem),
            t = e,
            n = !1,
            i = !1;
          for (1 == this._srch.length && (i = !0); t;) {
            if (!t.isDisabled && !i && 0 == t.element.textContent.trim().toLowerCase().indexOf(this._srch)) return t.dataItem;
            var r = t.next(!0, !0);
            if (r == e && n) break;
            r || n || (r = this.getFirstNode(!0, !0), n = !0), t = r, i = !1
          }
        }
        return null
      }, i.prototype._loadTree = function(n) {
        var r = this._root;
        if (r) {
          if (!this.onLoadingItems(new e.CancelEventArgs)) return;
          this._isDirty = !1;
          var s = this.containsFocus(),
            o = this.selectedItem;
          this.selectedItem = null, this._chkItems = null, this._ldLvl = -1;
          var a = void 0;
          if (n && e.isFunction(window.Map)) {
            a = new Map;
            for (var l = this.hostElement.querySelectorAll("." + i._CND), d = 0; d < l.length; d++) {
              h = l[d];
              e.hasClass(h, i._CCLD) && a.set(h[i._DATAITEM_KEY], !0)
            }
          }
          if (r.innerHTML = "", this._items)
            for (d = 0; d < this._items.length; d++) this._addItem(r, 0, this._items[d]);
          if (a)
            for (var l = this.hostElement.querySelectorAll("." + i._CND), d = 0; d < l.length; d++) {
              var h = l[d],
                c = t.TreeNode._isNodeList(h),
                u = a.get(h[i._DATAITEM_KEY]);
              e.toggleClass(h, i._CCLD, 1 == u), e.setAttribute(h, "aria-expanded", c ? (!u).toString() : null)
            }
          s && !this.containsFocus() && this.focus(), this.selectedItem = o, this.onLoadedItems(), this._ldLvl = -1
        }
      }, i.prototype._addItem = function(n, r, s) {
        var o = this._dspPath.getValue(s, r),
          a = this._imgPath.getValue(s, r),
          l = e.asArray(this._itmPath.getValue(s, r), !0),
          d = document.createElement("div");
        e.addClass(d, i._CND), d.tabIndex = -1, e.setAttribute(d, "role", "treeitem", !0), e.setAttribute(d, "aria-selected", !1);
        var h = document.createElement("span");
        if (this.isContentHtml ? h.innerHTML = o : h.textContent = o, e.addClass(h, i._CNDT), d.appendChild(h), a) {
          var c = document.createElement("img");
          c.src = a, d.insertBefore(c, d.firstChild)
        }
        if (this._showChk && !this._lazyLoad) {
          var u = document.createElement("input");
          u.type = "checkbox", u.tabIndex = -1, e.addClass(u, i._CNDC), d.insertBefore(u, d.firstChild)
        }
        if (this._dd && d.setAttribute("draggable", "true"), n.appendChild(d), d[i._DATAITEM_KEY] = s, l && 0 == l.length && !this.lazyLoadFunction && (l = null), l) {
          var p = !0;
          if (r > this._ldLvl ? (this._ldLvl = r, 0 == l.length && (e.addClass(d, i._CCLD), p = !1)) : (e.addClass(d, i._CCLD), p = !1, r < this._ldLvl && (this._ldLvl = 1e4)), l.length > 0) {
            var _ = document.createElement("div");
            _.tabIndex = -1, e.addClass(_, i._CNDL);
            for (var f = 0; f < l.length; f++) this._addItem(_, r + 1, l[f]);
            n.appendChild(_), e.setAttribute(d, "aria-expanded", p.toString(), !0), e.setAttribute(_, "role", "group", !0)
          }
        } else e.addClass(d, i._CEMP);
        this.formatItem.hasHandlers && this.onFormatItem(new t.FormatNodeEventArgs(s, d, r))
      }, i.prototype._collapseToLevel = function(e, t, n) {
        for (var i = 0; i < e.length; i++) {
          var r = e[i];
          r.hasPendingChildren || (r.isCollapsed = n >= t, r.hasChildren && this._collapseToLevel(r.nodes, t, n + 1))
        }
      }, i.prototype._lazyLoadNode = function(t) {
        var n = this.hostElement;
        e.hasClass(n, i._CLDG) || (e.addClass(n, i._CLDG), e.addClass(t.element, i._CLDG), this.lazyLoadFunction(t, this._lazyLoadCallback.bind(t)))
      }, i.prototype._lazyLoadCallback = function(e) {
        var t = this;
        t.treeView._lazyLoadNodeDone(t, e)
      }, i.prototype._lazyLoadNodeDone = function(t, n) {
        var r = i;
        e.removeClass(t.element, r._CLDG), e.removeClass(this.hostElement, r._CLDG);
        var s = t.dataItem,
          o = t.level,
          a = e.asArray(n, !0);
        if (null == a || 0 == a.length) this._itmPath.setValue(s, o, null), e.addClass(t.element, r._CEMP);
        else if (a.length) {
          this._itmPath.setValue(s, o, a);
          var l = document.createElement("div"),
            d = t.element;
          e.addClass(l, r._CNDL), d.parentElement.insertBefore(l, d.nextSibling);
          for (var h = 0; h < a.length; h++) this._addItem(l, o + 1, a[h]);
          t.isCollapsed = !1
        }
      }, i._DATAITEM_KEY = "wj-Data-Item", i._AS_DLY = 600, i._AN_DLY = 200, i._CND = "wj-node", i._CNDL = "wj-nodelist", i._CEMP = "wj-state-empty", i._CNDT = "wj-node-text", i._CNDC = "wj-node-check", i._CSEL = "wj-state-selected", i._CCLD = "wj-state-collapsed", i._CCLG = "wj-state-collapsing", i._CLDG = "wj-state-loading", i.controlTemplate = '<div wj-part="root"></div>', i
    }(e.Control);
    t.TreeView = n
  }(e.nav || (e.nav = {}))
}(wijmo || (wijmo = {}));
var wijmo;
! function(e) {
  ! function(t) {
    var n = function() {
      function n(t, i) {
        e.hasClass(i, "wj-treeview") ? (t = e.Control.getControl(i), i = null) : n._assertNode(i), this._t = t, this._e = i
      }
      return Object.defineProperty(n.prototype, "dataItem", {
        get: function() {
          return this._e[t.TreeView._DATAITEM_KEY]
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "element", {
        get: function() {
          return this._e
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "treeView", {
        get: function() {
          return this._t
        },
        enumerable: !0,
        configurable: !0
      }), n.prototype.ensureVisible = function() {
        for (var e = this.parentNode; e; e = e.parentNode) e.isCollapsed = !1;
        var t = this._t.hostElement,
          n = this.element.getBoundingClientRect(),
          i = t.getBoundingClientRect();
        n.bottom > i.bottom ? t.scrollTop += n.bottom - i.bottom : n.top < i.top && (t.scrollTop -= i.top - n.top)
      }, n.prototype.equals = function(e) {
        return null != e && e.element == this.element
      }, n.prototype.select = function() {
        var n = this._t,
          i = n._selNode;
        this.equals(i) || (i && (e.removeClass(i.element, t.TreeView._CSEL), e.setAttribute(i.element, "aria-selected", !1)), n._selNode = this, e.addClass(this.element, t.TreeView._CSEL), e.setAttribute(this.element, "aria-selected", !0), this.ensureVisible(), n._updateFocus(i), n.onSelectedItemChanged())
      }, Object.defineProperty(n.prototype, "index", {
        get: function() {
          for (var e = 0, t = this._pse(this.element); t; t = this._pse(t)) n._isNode(t) && e++;
          return e
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "parentNode", {
        get: function() {
          var e = null;
          if (this._e) {
            var t = this._e.parentElement;
            n._assertNodeList(t), e = this._pse(t)
          }
          return e ? new n(this._t, e) : null
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "level", {
        get: function() {
          for (var e = -1, t = this; t; t = t.parentNode) e++;
          return e
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "hasChildren", {
        get: function() {
          return n._isNode(this._e) && !n._isEmpty(this._e)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "hasPendingChildren", {
        get: function() {
          return this.isCollapsed && this.hasChildren && !n._isNodeList(this.element.nextElementSibling) && e.isFunction(this._t.lazyLoadFunction)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "nodes", {
        get: function() {
          return this.hasChildren ? n._getChildNodes(this._t, this._e.nextSibling) : null
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "checkBox", {
        get: function() {
          return this._e.querySelector("input." + t.TreeView._CNDC)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "isCollapsed", {
        get: function() {
          return this.hasChildren && e.hasClass(this._e, t.TreeView._CCLD)
        },
        set: function(n) {
          if (n != this.isCollapsed) {
            var i = this._t,
              r = new t.TreeNodeEventArgs(this);
            i.onIsCollapsedChanging(r) && (this.setCollapsed(e.asBoolean(n), i.isAnimated, i.autoCollapse), i.onIsCollapsedChanged(r))
          }
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "isChecked", {
        get: function() {
          var e = this.checkBox;
          return e && !e.indeterminate ? e.checked : null
        },
        set: function(n) {
          if (n != this.isChecked) {
            var i = this._t,
              r = new t.TreeNodeEventArgs(this);
            i.onIsCheckedChanging(r) && (this.setChecked(e.asBoolean(n), !0), i.onIsCheckedChanged(r))
          }
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "isDisabled", {
        get: function() {
          return this._e && null != this._e.getAttribute("disabled")
        },
        set: function(t) {
          (t = e.asBoolean(t, !0)) != this.isDisabled && e.enable(this._e, !t)
        },
        enumerable: !0,
        configurable: !0
      }), n.prototype.previous = function(e, t) {
        var i = this._pse(this._e);
        if (!i && n._isNodeList(this._e.parentElement) && (i = this._pse(this._e.parentElement)), n._isNodeList(i)) {
          for (; n._isNodeList(i) && i.childElementCount;) i = i.lastChild;
          n._isNodeList(i) && (i = this._pse(i))
        }
        var r = n._isNode(i) ? new n(this._t, i) : null;
        return e && r && !r.element.offsetHeight && (r = r.previous(e, t)), t && r && r.isDisabled && (r = r.previous(e, t)), r
      }, n.prototype.next = function(e, t) {
        var i = this._e.nextSibling;
        if (n._isNodeList(i) && (i = i.childElementCount ? i.firstChild : i.nextSibling), !i)
          for (var r = this._e.parentElement; !i && n._isNodeList(r); r = r.parentElement) i = r.nextSibling;
        var s = n._isNode(i) ? new n(this._t, i) : null;
        return e && s && !s.element.offsetHeight && (s = s.next(e, t)), t && s && s.isDisabled && (s = s.next(e, t)), s
      }, n.prototype.previousSibling = function() {
        for (var e = this._pse(this.element); n._isNodeList(e);) e = this._pse(e);
        return e ? new n(this._t, e) : null
      }, n.prototype.nextSibling = function() {
        var e = this.element.nextSibling;
        return n._isNodeList(e) && (e = e.nextSibling), e ? new n(this._t, e) : null
      }, n.prototype.setCollapsed = function(i, r, s) {
        var o = this._t,
          a = this._e,
          l = this._e.nextElementSibling,
          d = n._isNodeList(l);
        if (e.setAttribute(a, "aria-expanded", d ? (!i).toString() : null), i != this.isCollapsed)
          if (i || d || !e.isFunction(o.lazyLoadFunction)) {
            if (null == r && (r = o.isAnimated), null == s && (s = o.autoCollapse), r) {
              if (d) {
                var h = l.offsetHeight,
                  c = l.style,
                  u = o.hostElement,
                  p = u.style;
                u.scrollHeight <= u.clientHeight && (p.overflowY = "hidden"), i ? (e.toggleClass(a, t.TreeView._CCLG, !0), e.animate(function(n) {
                  n < 1 ? (n = 1 - n, c.height = (n * h).toFixed(0) + "px") : (c.height = c.opacity = p.overflowY = "", e.toggleClass(a, t.TreeView._CCLD, !0), e.toggleClass(a, t.TreeView._CCLG, !1))
                }, t.TreeView._AN_DLY)) : (e.toggleClass(a, t.TreeView._CCLD, !1), c.height = c.opacity = "0", e.animate(function(e) {
                  c.height = e >= 1 ? c.opacity = p.overflowY = "" : (e * h).toFixed(0) + "px"
                }, t.TreeView._AN_DLY))
              }
            } else e.toggleClass(a, t.TreeView._CCLD, i);
            if (!i && s) {
              var _ = a.parentElement;
              if (n._isNodeList(_))
                for (var f = 0; f < _.children.length; f++) {
                  var g = _.children[f];
                  g != a && n._isNode(g) && (e.toggleClass(g, t.TreeView._CCLD, !0), g.setAttribute("aria-expanded", "false"))
                }
            }
          } else o._lazyLoadNode(this)
      }, n.prototype.setChecked = function(e, t) {
        var n = this.checkBox,
          i = n.checked != e;
        if (n.checked = e, n.indeterminate = !1, this.hasChildren)
          for (var r = this.nodes, s = 0; s < r.length; s++) r[s].setChecked(e, !1);
        if (t) {
          var o = this.parentNode;
          o && o._updateCheckedState()
        }
        this._t && i && this._t._raiseCheckedItemsChanged()
      }, n.prototype.remove = function() {
        var t = this._t,
          i = this.parentNode,
          r = this._getArray(),
          s = r.indexOf(this.dataItem);
        t.selectedNode == this && (t.selectedNode = this.nextSibling() || this.previousSibling() || i);
        var o = this.element.nextSibling;
        n._isNodeList(o) && e.removeChild(o), e.removeChild(this.element), i && i._updateState(), r.splice(s, 1), this._t = null
      }, n.prototype.addChildNode = function(e, n) {
        var i = this._t._createNode(n),
          r = this.nodes;
        return r ? e < r.length ? i.move(r[e], t.DropPosition.Before) : i.move(r[r.length - 1], t.DropPosition.After) : i.move(this, t.DropPosition.Into), i
      }, n.prototype.refresh = function(t) {
        var n = this._getArray();
        t && (n[this.index] = t), t = n[this.index];
        var i = this._t._createNode(t),
          r = this.hasChildren && !this.hasPendingChildren ? this.element.nextSibling : null;
        r && e.removeChild(r), (r = i.hasChildren && !i.hasPendingChildren ? i.element.nextSibling : null) && this.element.parentElement.insertBefore(r, this.element.nextSibling), this.element.innerHTML = i.element.innerHTML, this._updateState()
      }, n.prototype.move = function(e, t) {
        if (e instanceof n && this._contains(e)) return !1;
        var i = this.parentNode,
          r = this._getArray();
        this._moveElements(e, t);
        var s = this.parentNode,
          o = this._getArray();
        i && i._updateState(), s && s._updateState();
        var a = this.dataItem,
          l = r.indexOf(a);
        return r.splice(l, 1), o.splice(this.index, 0, a), e.treeView && (this._t = e.treeView), !0
      }, Object.defineProperty(n.prototype, "itemsSource", {
        get: function() {
          return this._getArray()
        },
        enumerable: !0,
        configurable: !0
      }), n.prototype._pse = function(e) {
        return e.previousElementSibling
      }, n.prototype._contains = function(e) {
        for (; e; e = e.parentNode)
          if (e.element == this.element) return !0;
        return !1
      }, n.prototype._getArray = function() {
        var e = this._t,
          t = this.parentNode,
          n = e.itemsSource;
        if (t) {
          var i = e._itmPath;
          (n = i.getValue(t.dataItem, this.level)) || (n = [], i.setValue(t.dataItem, this.level, n))
        }
        return n
      }, n.prototype._moveElements = function(i, r) {
        var s = document.createDocumentFragment(),
          o = this.hasChildren && !this.hasPendingChildren ? this.element.nextSibling : null;
        if (s.appendChild(this.element), o && (n._assertNodeList(o), s.appendChild(o)), i instanceof t.TreeView) i._root.insertBefore(s, null);
        else {
          var a = i.element,
            l = a ? a.parentElement : i.treeView._root;
          n._assertNodeList(l);
          var d = t.DropPosition;
          switch (r) {
            case d.Before:
              l.insertBefore(s, a);
              break;
            case d.After:
              a = (i = i.nextSibling()) ? i.element : null, l.insertBefore(s, a);
              break;
            case d.Into:
              i.hasChildren && !i.hasPendingChildren || (o = document.createElement("div"), e.addClass(o, t.TreeView._CNDL), l.insertBefore(o, a.nextSibling)), l = i.element.nextSibling, n._assertNodeList(l), l.insertBefore(s, null)
          }
        }
      }, n.prototype._updateState = function() {
        this._updateEmptyState(), this._updateCheckedState()
      }, n.prototype._updateEmptyState = function() {
        var i = this.element.nextSibling,
          r = !1;
        n._isNodeList(i) && (i.childElementCount ? r = !0 : e.removeChild(i)), e.toggleClass(this.element, t.TreeView._CEMP, !r), r || this.element.removeAttribute("aria-expanded")
      }, n.prototype._updateCheckedState = function() {
        var e = this.checkBox,
          t = this.nodes,
          n = 0,
          i = 0;
        if (e && t) {
          for (var r = 0; r < t.length; r++) switch (t[r].isChecked) {
            case !0:
              n++;
              break;
            case !1:
              i++
          }
          n == t.length ? (e.checked = !0, e.indeterminate = !1) : i == t.length ? (e.checked = !1, e.indeterminate = !1) : (e.checked = !1, e.indeterminate = !0)
        }
        var s = this.parentNode;
        s && s._updateCheckedState()
      }, n._getChildNodes = function(e, t) {
        n._assertNodeList(t);
        var i = [];
        if (n._isNodeList(t))
          for (var r = t.children, s = 0; s < r.length; s++) {
            var o = r[s];
            n._isNode(o) && i.push(new n(e, o))
          }
        return i
      }, n._isNode = function(n) {
        return n && e.hasClass(n, t.TreeView._CND)
      }, n._isNodeList = function(n) {
        return n && e.hasClass(n, t.TreeView._CNDL)
      }, n._isEmpty = function(i) {
        return n._isNode(i) && e.hasClass(i, t.TreeView._CEMP)
      }, n._isCollapsed = function(i) {
        return n._isNode(i) && !n._isEmpty(i) && e.hasClass(i, t.TreeView._CCLD)
      }, n._assertNode = function(t) {
        e.assert(n._isNode(t), "node expected")
      }, n._assertNodeList = function(t) {
        e.assert(n._isNodeList(t), "nodeList expected")
      }, n
    }();
    t.TreeNode = n
  }(e.nav || (e.nav = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var e = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(e, t) {
        e.__proto__ = t
      } || function(e, t) {
        for (var n in t) t.hasOwnProperty(n) && (e[n] = t[n])
      };
    return function(t, n) {
      function i() {
        this.constructor = t
      }
      e(t, n), t.prototype = null === n ? Object.create(n) : (i.prototype = n.prototype, new i)
    }
  }(),
  wijmo;
! function(e) {
  ! function(t) {
    var n = function(t) {
      function n(n, i, r) {
        var s = t.call(this) || this;
        return s._data = n, s._e = e.asType(i, HTMLElement), s._level = r, s
      }
      return __extends(n, t), Object.defineProperty(n.prototype, "dataItem", {
        get: function() {
          return this._data
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "element", {
        get: function() {
          return this._e
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "level", {
        get: function() {
          return this._level
        },
        enumerable: !0,
        configurable: !0
      }), n
    }(e.EventArgs);
    t.FormatNodeEventArgs = n;
    var i = function(e) {
      function t(t) {
        var n = e.call(this) || this;
        return n._node = t, n
      }
      return __extends(t, e), Object.defineProperty(t.prototype, "node", {
        get: function() {
          return this._node
        },
        enumerable: !0,
        configurable: !0
      }), t
    }(e.CancelEventArgs);
    t.TreeNodeEventArgs = i;
    var r = function(n) {
      function i(i, r, s) {
        var o = n.call(this) || this;
        return o._src = e.asType(i, t.TreeNode), o._tgt = e.asType(r, t.TreeNode), o._pos = s, o
      }
      return __extends(i, n), Object.defineProperty(i.prototype, "dragSource", {
        get: function() {
          return this._src
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "dropTarget", {
        get: function() {
          return this._tgt
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "position", {
        get: function() {
          return this._pos
        },
        set: function(t) {
          this._pos = e.asEnum(t, s)
        },
        enumerable: !0,
        configurable: !0
      }), i
    }(e.CancelEventArgs);
    t.TreeNodeDragDropEventArgs = r;
    var s;
    ! function(e) {
      e[e.Before = 0] = "Before", e[e.After = 1] = "After", e[e.Into = 2] = "Into"
    }(s = t.DropPosition || (t.DropPosition = {}))
  }(e.nav || (e.nav = {}))
}(wijmo || (wijmo = {}));
var wijmo;
! function(e) {
  ! function(t) {
    var n = function() {
      function n(n) {
        this._tree = e.asType(n, t.TreeView), this._dragstartBnd = this._dragstart.bind(this), this._dragoverBnd = this._dragover.bind(this), this._dropBnd = this._drop.bind(this), this._dragendBnd = this._dragend.bind(this);
        var i = this._tree,
          r = i.hostElement;
        i.addEventListener(r, "dragstart", this._dragstartBnd), i.addEventListener(r, "dragover", this._dragoverBnd), i.addEventListener(r, "dragleave", this._dragoverBnd), i.addEventListener(r, "drop", this._dropBnd), i.addEventListener(r, "dragend", this._dragendBnd), i.addEventListener(r, "keydown", this._keydown)
      }
      return n.prototype.dispose = function() {
        var e = this._tree,
          t = e.hostElement;
        e.removeEventListener(t, "dragstart", this._dragstartBnd), e.removeEventListener(t, "dragover", this._dragoverBnd), e.removeEventListener(t, "dragleave", this._dragoverBnd), e.removeEventListener(t, "drop", this._dropBnd), e.removeEventListener(t, "dragend", this._dragendBnd), e.removeEventListener(t, "keydown", this._keydown), this._showDragMarker()
      }, n.prototype._dragstart = function(i) {
        if (!i.defaultPrevented) {
          var r = this._tree,
            s = e.closestClass(i.target, t.TreeView._CND),
            o = n;
          if (o._drgSrc = t.TreeNode._isNode(s) ? new t.TreeNode(r, s) : null, o._drgSrc) {
            var a = new t.TreeNodeEventArgs(o._drgSrc);
            r.onDragStart(a) || (o._drgSrc = null)
          }
          o._drgSrc && i.dataTransfer ? (e._startDrag(i.dataTransfer, "copyMove"), i.stopPropagation()) : i.preventDefault()
        }
      }, n.prototype._dragover = function(e) {
        this._handleDragDrop(e, !1)
      }, n.prototype._drop = function(e) {
        this._handleDragDrop(e, !0)
      }, n.prototype._dragend = function(e) {
        n._drgSrc = null, this._showDragMarker(), this._tree.onDragEnd()
      }, n.prototype._keydown = function(t) {
        t.defaultPrevented || t.keyCode == e.Key.Escape && this._dragendBnd(null)
      }, n.prototype._handleDragDrop = function(i, r) {
        var s, o, a = this._tree,
          l = n,
          d = t.DropPosition,
          h = d.Into;
        if (!i.defaultPrevented && l._drgSrc) {
          var c = document.elementFromPoint(i.clientX, i.clientY),
            u = e.closestClass(c, t.TreeView._CND);
          if (null == u) {
            var p = e.Control.getControl(e.closest(c, ".wj-treeview"));
            p instanceof t.TreeView && 0 == p.totalItemCount && (u = p.hostElement)
          }
          if (u == l._drgSrc.element && (u = null), u) {
            o = u.getBoundingClientRect();
            var _ = new t.TreeNode(a, u),
              f = _.hasPendingChildren ? o.height / 2 : o.height / 3;
            null == _.element ? ((o = e.Rect.fromBoundingRect(o)).inflate(-12, -12), h = d.Before) : i.clientY < o.top + f ? h = d.Before : (i.clientY > o.bottom - f || _.hasPendingChildren) && (h = d.After, !_.hasChildren || _.isCollapsed || _.hasPendingChildren || (h = d.Before, o = (u = (_ = _.next(!0, !1)).element).getBoundingClientRect())), l._drgSrc._contains(_) ? u = null : ((s = new t.TreeNodeDragDropEventArgs(l._drgSrc, _, h)).cancel = l._drgSrc.treeView != _.treeView, a.onDragOver(s) || (u = null))
          }
          if (u)
            if ((h = s.position) == d.Before) {
              var g = s.dragSource.next(!0, !1);
              g && g.element == u && (u = null)
            } else if (h == d.After) {
              var m = s.dragSource.previous(!0, !1);
              m && m.element == u && (u = null)
            }
          if (u && !r ? (i.dataTransfer.dropEffect = "move", i.preventDefault(), i.stopPropagation(), this._showDragMarker(o, h)) : this._showDragMarker(), u && r && a.onDrop(s)) {
            a.hostElement.focus();
            var v = s.dragSource;
            v.move(s.dropTarget, s.position), v.select()
          }
        }
      }, n.prototype._showDragMarker = function(i, r) {
        var s = this._tree,
          o = n._dMarker.parentElement;
        if (i) {
          var a = s.hostElement.getBoundingClientRect(),
            l = r == t.DropPosition.After ? i.bottom : i.top,
            d = {
              top: Math.round(l - a.top + s.hostElement.scrollTop - 2),
              width: "75%",
              height: r == t.DropPosition.Into ? i.height : 4,
              opacity: r == t.DropPosition.Into ? "0.15" : ""
            };
          s.rightToLeft ? d.right = Math.round(a.right - i.right) : d.left = Math.round(i.left - a.left), e.setCss(n._dMarker, d), o != s._root && s._root.appendChild(n._dMarker)
        } else o && o.removeChild(n._dMarker)
      }, n._dMarker = e.createElement('<div class="wj-marker">&nbsp;</div>'), n
    }();
    t._TreeDragDropManager = n
  }(e.nav || (e.nav = {}))
}(wijmo || (wijmo = {}));
var wijmo;
! function(e) {
  ! function(t) {
    var n = function() {
      function t(e) {
        this.path = e
      }
      return Object.defineProperty(t.prototype, "path", {
        get: function() {
          return this._path
        },
        set: function(t) {
          if (this._path = t, e.isString(t)) this._bindings = [new e.Binding(t)];
          else if (e.isArray(t)) {
            this._bindings = [];
            for (var n = 0; n < t.length; n++) this._bindings.push(new e.Binding(t[n]))
          } else null != t && e.assert(!1, "Path should be a string or an array of strings.");
          this._maxLevel = this._bindings ? this._bindings.length - 1 : -1
        },
        enumerable: !0,
        configurable: !0
      }), t.prototype.getValue = function(e, t) {
        var n = Math.min(t, this._maxLevel);
        return n > -1 ? this._bindings[n].getValue(e) : null
      }, t.prototype.setValue = function(e, t, n) {
        var i = Math.min(t, this._maxLevel);
        i > -1 && this._bindings[i].setValue(e, n)
      }, t
    }();
    t._BindingArray = n
  }(e.nav || (e.nav = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var e = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(e, t) {
        e.__proto__ = t
      } || function(e, t) {
        for (var n in t) t.hasOwnProperty(n) && (e[n] = t[n])
      };
    return function(t, n) {
      function i() {
        this.constructor = t
      }
      e(t, n), t.prototype = null === n ? Object.create(n) : (i.prototype = n.prototype, new i)
    }
  }(),
  wijmo;
! function(e) {
  ! function(t) {
    "use strict";
    var n = function(t) {
      function n(n, r, s) {
        var o = t.call(this, n, null, !0) || this;
        o._tabs = new e.collections.ObservableArray, o._selectedIndex = -1, o._animated = !0, o._autoSwitch = !0, o.selectedIndexChanged = new e.Event;
        var a = o.hostElement,
          l = [];
        if (!s)
          for (; a.firstElementChild;) {
            var d = a.firstElementChild;
            a.removeChild(d), l.push(d)
          }
        var h = o.getTemplate();
        return o.applyTemplate("wj-control wj-tabpanel", h, {
          _dRoot: "root",
          _dTabHeaders: "tabheaders",
          _dTabPanes: "tabpanes"
        }), a.tabIndex = -1, o._dRoot.tabIndex = o._dTabHeaders.tabIndex = o._dTabPanes.tabIndex = -1, o.addEventListener(a, "click", o._click.bind(o)), o.addEventListener(a, "keydown", o._keydown.bind(o)), o._tabs.collectionChanged.addHandler(o._populateControl.bind(o)), o.tabs.deferUpdate(function() {
          l.forEach(function(t) {
            e.assert(2 == t.childElementCount, "TabPanel children should contain header and pane elements"), o.tabs.push(new i(t.children[0], t.children[1]))
          })
        }), o.initialize(r), o.selectedIndex < 0 && o.tabs.length ? o.selectedIndex = 0 : o.onSelectedIndexChanged(), o
      }
      return __extends(n, t), Object.defineProperty(n.prototype, "tabs", {
        get: function() {
          return this._tabs
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "selectedIndex", {
        get: function() {
          return this._selectedIndex
        },
        set: function(t) {
          (t = e.asInt(t, !1)) != this._selectedIndex ? (this._selectedIndex = t, this._updateContent(), this.onSelectedIndexChanged()) : this._updateContent()
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "selectedTab", {
        get: function() {
          var e = this._selectedIndex;
          return e > -1 ? this._tabs[e] : null
        },
        set: function(e) {
          for (var t = -1, n = 0; n < this._tabs.length && t < 0; n++) this._tabs[n] == e && (t = n);
          this.selectedIndex = t
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "isAnimated", {
        get: function() {
          return this._animated
        },
        set: function(t) {
          this._animated = e.asBoolean(t)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "autoSwitch", {
        get: function() {
          return this._autoSwitch
        },
        set: function(t) {
          t != this._autoSwitch && (this._autoSwitch = e.asBoolean(t), this._updateContent())
        },
        enumerable: !0,
        configurable: !0
      }), n.prototype.getTab = function(e) {
        for (t = 0; t < this._tabs.length; t++)
          if ((n = this._tabs[t]).header.id == e || n.pane.id == e) return n;
        for (var t = 0; t < this._tabs.length; t++) {
          var n = this._tabs[t];
          if (n.header.textContent == e) return n
        }
        return null
      }, n.prototype.onSelectedIndexChanged = function(e) {
        this.selectedIndexChanged.raise(this, e)
      }, n.prototype._populateControl = function() {
        var t = this;
        this._removeChildren(this._dTabHeaders), this._removeChildren(this._dTabPanes);
        var n = -1;
        this._tabs.forEach(function(r, s) {
          e.assert(r instanceof i, "tabs array must contain Tab objects."), r._setPanel(t);
          var o = r.header,
            a = r.pane;
          e.addClass(o, "wj-tabheader"), e.setAttribute(o, "role", "tab"), t._dTabHeaders.appendChild(o), e.addClass(a, "wj-tabpane"), e.setAttribute(a, "role", "tabpanel"), e.setAttribute(a, "aria-labelledby", o.id ? o.id : null), t._dTabPanes.appendChild(a), n < 0 && (e.hasClass(o, "wj-state-active") || "true" == o.getAttribute("aria-selected")) && (n = s)
        }), n < 0 && this._tabs.length > 0 && (n = 0), n > -1 ? this.selectedIndex = n : this.selectedIndex > -1 && this.selectedIndex < this._tabs.length && this._updateContent(), this._validateSelection()
      }, n.prototype._validateSelection = function() {
        var e = this.selectedTab;
        if (e && (e.isDisabled || !e.isVisible)) {
          var t = this._getNextIndex(this.selectedIndex, 1);
          t < 0 && (t = this._getNextIndex(this.selectedIndex, -1)), this.selectedIndex = t
        }
      }, n.prototype._updateContent = function() {
        var t = this,
          n = e.contains(this._dTabHeaders, e.getActiveElement()),
          i = this._dTabHeaders.children,
          r = this._dTabPanes.children,
          s = this._selectedIndex;
        if (s > -1 && s < r.length) {
          var o = (h = r[s]).style;
          this.isAnimated ? (o.opacity = "0", this._toAnim && clearInterval(this._toAnim), this._toAnim = e.animate(function(e) {
            1 == e ? (o.opacity = "", t._toAnim = null) : o.opacity = e.toString()
          })) : o.opacity = ""
        }
        for (var a = 0; a < i.length; a++) {
          var l = a == s,
            d = i[a],
            h = r[a];
          e.setAttribute(d, "aria-selected", l), e.toggleClass(d, "wj-state-active", l), e.toggleClass(h, "wj-state-active", l), d.tabIndex = l || !this._autoSwitch ? this._orgTabIndex : -1, l && (n && d.focus(), e.Control.invalidateAll(h))
        }
      }, n.prototype._removeChildren = function(e) {
        for (; e.firstChild;) e.removeChild(e.firstChild)
      }, n.prototype._click = function(e) {
        var t = this._getTabIndex(e.target);
        if (t > -1) {
          var n = this._tabs[t];
          !n.isDisabled && n.isVisible && (this.selectedIndex = t)
        }
      }, n.prototype._keydown = function(t) {
        if (!t.defaultPrevented) {
          var n = this._getTabIndex(e.getActiveElement());
          if (n > -1) {
            var i = this._getKeyCode(t);
            switch (i) {
              case e.Key.Left:
              case e.Key.Up:
              case e.Key.Right:
              case e.Key.Down:
              case e.Key.Home:
              case e.Key.PageUp:
              case e.Key.End:
              case e.Key.PageDown:
                switch (i) {
                  case e.Key.Left:
                  case e.Key.Up:
                    n = this._getNextIndex(n, -1);
                    break;
                  case e.Key.Right:
                  case e.Key.Down:
                    n = this._getNextIndex(n, 1);
                    break;
                  case e.Key.Home:
                  case e.Key.PageUp:
                    n = this._getNextIndex(-1, 1);
                    break;
                  case e.Key.End:
                  case e.Key.PageDown:
                    n = this._getNextIndex(this._tabs.length, -1)
                }
                n > -1 && (this._autoSwitch ? this.selectedIndex = n : this._tabs[n].header.focus()), t.preventDefault();
                break;
              case e.Key.Enter:
              case e.Key.Space:
                n > -1 && this._tabs[n].header.click(), t.preventDefault()
            }
          }
        }
      }, n.prototype._getTabIndex = function(t) {
        var n = e.closest(t, ".wj-tabheader");
        if (n && e.closest(n, ".wj-tabpanel") == this.hostElement)
          for (var i = 0; i < this._tabs.length; i++)
            if (this._tabs[i].header == n) return i;
        return -1
      }, n.prototype._getNextIndex = function(e, t) {
        for (var n = e + t; n > -1 && n < this._tabs.length; n += t) {
          var i = this._tabs[n];
          if (!i.isDisabled && i.isVisible) return n
        }
        return -1
      }, n.controlTemplate = '<div wj-part="root"><div wj-part="tabheaders" class="wj-tabheaders" role="tablist"></div><div wj-part="tabpanes" class="wj-tabpanes"></div></div>', n
    }(e.Control);
    t.TabPanel = n;
    var i = function() {
      function t(t, n) {
        this._hdr = e.asType(e.getElement(t), HTMLElement), this._pane = e.asType(e.getElement(n), HTMLElement)
      }
      return Object.defineProperty(t.prototype, "tabPanel", {
        get: function() {
          return this._p
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(t.prototype, "header", {
        get: function() {
          return this._hdr
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(t.prototype, "pane", {
        get: function() {
          return this._pane
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(t.prototype, "isDisabled", {
        get: function() {
          return e.hasClass(this._hdr, "wj-state-disabled")
        },
        set: function(t) {
          e.toggleClass(this._hdr, "wj-state-disabled", e.asBoolean(t)), this._p && this._p._validateSelection()
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(t.prototype, "isVisible", {
        get: function() {
          return "none" != this._hdr.style.display
        },
        set: function(t) {
          this._hdr.style.display = e.asBoolean(t) ? "" : "none", this._p && this._p._validateSelection()
        },
        enumerable: !0,
        configurable: !0
      }), t.prototype._setParts = function(t, n) {
        if (t = e.asType(e.getElement(t), HTMLElement), n = e.asType(e.getElement(n), HTMLElement, !1), this._hdr !== t || this._pane !== n) {
          var i = this.isDisabled,
            r = this.isVisible;
          this._hdr = t, this._pane = n, this.isDisabled = i, this.isVisible = r;
          var s = this.tabPanel;
          s && !s.tabs.isUpdating && s._populateControl()
        }
      }, t.prototype._setPanel = function(e) {
        this._p = e
      }, t
    }();
    t.Tab = i
  }(e.nav || (e.nav = {}))
}(wijmo || (wijmo = {}));
