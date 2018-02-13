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
    var input;
    (function (input) {
        'use strict';
        /**
         * DropDown control (abstract).
         *
         * Contains an input element and a button used to show or hide the drop-down.
         *
         * Derived classes must override the _createDropDown method to create whatever
         * editor they want to show in the drop down area (a list of items, a calendar,
         * a color editor, etc).
         */
        var DropDown = /** @class */ (function (_super) {
            __extends(DropDown, _super);
            /**
             * Initializes a new instance of the @see:DropDown class.
             *
             * @param element The DOM element that hosts the control, or a selector for the host element (e.g. '#theCtrl').
             * @param options The JavaScript object containing initialization data for the control.
             */
            function DropDown(element, options) {
                var _this = _super.call(this, element, null, true) || this;
                // property storage
                _this._showBtn = true;
                _this._autoExpand = true;
                _this._animate = false;
                /**
                 * Occurs when the value of the @see:text property changes.
                 */
                _this.textChanged = new wijmo.Event();
                /**
                 * Occurs before the drop down is shown or hidden.
                 */
                _this.isDroppedDownChanging = new wijmo.Event();
                /**
                 * Occurs after the drop down is shown or hidden.
                 */
                _this.isDroppedDownChanged = new wijmo.Event();
                // instantiate and apply template
                var tpl = _this.getTemplate();
                _this.applyTemplate('wj-control wj-dropdown wj-content', tpl, {
                    _tbx: 'input',
                    _btn: 'btn',
                    _dropDown: 'dropdown'
                }, 'input');
                // set reference element (used for positioning the drop-down)
                var tbx = _this._tbx;
                _this._elRef = tbx;
                // disable autocomplete/correct/capitalize
                // (important for mobile browsers including Chrome/ Android)
                // https://davidwalsh.name/disable-autocorrect
                'autocomplete,autocorrect,autocapitalize,spellcheck'.split(',').forEach(function (att) {
                    if (!tbx.hasAttribute(att)) {
                        tbx.setAttribute(att, att == 'spellcheck' ? 'false' : 'off');
                    }
                });
                // create drop-down element, update button display
                _this._createDropDown();
                _this._updateBtn();
                // remove drop-down from DOM (so IE/Edge can print properly)
                // NOTE: this causes some accessibility warnings
                wijmo.removeChild(_this._dropDown);
                // we start collapsed
                wijmo.addClass(_this.hostElement, 'wj-state-collapsed');
                // update focus state when the drop-down gets or loses focus
                var fs = _this._updateFocusState.bind(_this); // TFS 153367
                _this.addEventListener(_this.dropDown, 'blur', fs, true);
                _this.addEventListener(_this.dropDown, 'focus', fs);
                // keyboard events (the same handlers are used for the control and for the drop-down)
                var kd = _this._keydown.bind(_this);
                _this.addEventListener(_this.hostElement, 'keydown', kd);
                _this.addEventListener(_this.dropDown, 'keydown', kd);
                // prevent smiley that appears when the user presses alt-down
                _this.addEventListener(tbx, 'keypress', function (e) {
                    if (e.keyCode == 9787 && _this._altDown) {
                        e.preventDefault();
                    }
                });
                // textbox events
                _this.addEventListener(tbx, 'input', function () {
                    _this._setText(_this.text, false);
                });
                _this.addEventListener(tbx, 'click', function () {
                    if (_this._autoExpand) {
                        _this._expandSelection(); // expand the selection to the whole number/word that was clicked
                    }
                });
                // IE 9 does not fire an input event when the user removes characters from input 
                // filled by keyboard, cut, or drag operations.
                // https://developer.mozilla.org/en-US/docs/Web/Events/input
                // so subscribe to keyup and set the text just in case (TFS 111189)
                if (wijmo.isIE9()) {
                    _this.addEventListener(tbx, 'keyup', function () {
                        _this._setText(_this.text, false);
                    });
                }
                // toggle the drop-down on mousedown (used to be on click)
                _this.addEventListener(_this._btn, 'mousedown', function (e) {
                    setTimeout(function () {
                        _this._btnclick(e);
                    });
                });
                //this.addEventListener(this._btn, 'click', (e) => {
                //    if (e.clientX <= 0 && e.clientX <= 0) { // automation click
                //        this._btnclick(e);
                //    }
                //});
                // stop propagation of click events on the drop-down element
                // they are not children of the hostElement, which can confuse Bootstrap popups
                _this.addEventListener(_this._dropDown, 'click', function (e) {
                    e.stopPropagation();
                });
                return _this;
            }
            Object.defineProperty(DropDown.prototype, "text", {
                //--------------------------------------------------------------------------
                //#region ** object model
                /**
                 * Gets or sets the text shown on the control.
                 */
                get: function () {
                    return this._tbx.value;
                },
                set: function (value) {
                    if (value != this.text) {
                        this._setText(value, true);
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(DropDown.prototype, "inputElement", {
                /**
                 * Gets the HTML input element hosted by the control.
                 *
                 * Use this property in situations where you want to customize the
                 * attributes of the input element.
                 */
                get: function () {
                    return this._tbx;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(DropDown.prototype, "isReadOnly", {
                /**
                 * Gets or sets a value that indicates whether the user can modify
                 * the control value using the mouse and keyboard.
                 */
                get: function () {
                    return this._tbx.readOnly;
                },
                set: function (value) {
                    this._tbx.readOnly = wijmo.asBoolean(value);
                    wijmo.toggleClass(this.hostElement, 'wj-state-readonly', this.isReadOnly);
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(DropDown.prototype, "isRequired", {
                /**
                 * Gets or sets a value that determines whether the control value must be set to
                 * a non-null value or whether it can be set to null
                 * (by deleting the content of the control).
                 */
                get: function () {
                    return this._tbx.required;
                },
                set: function (value) {
                    this._tbx.required = wijmo.asBoolean(value);
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(DropDown.prototype, "placeholder", {
                /**
                 * Gets or sets the string shown as a hint when the control is empty.
                 */
                get: function () {
                    return this._tbx.placeholder;
                },
                set: function (value) {
                    this._tbx.placeholder = value;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(DropDown.prototype, "isDroppedDown", {
                /**
                 * Gets or sets a value that indicates whether the drop down is currently visible.
                 */
                get: function () {
                    var dd = this._dropDown;
                    return dd && dd.style.display != 'none';
                },
                set: function (value) {
                    value = wijmo.asBoolean(value) && !this.isDisabled && !this.isReadOnly;
                    if (value != this.isDroppedDown && this.onIsDroppedDownChanging(new wijmo.CancelEventArgs())) {
                        var host = this.hostElement, dd = this._dropDown;
                        if (value) {
                            if (!dd.style.minWidth) {
                                dd.style.minWidth = host.getBoundingClientRect().width + 'px';
                            }
                            dd.style.display = 'block';
                            this._updateDropDown();
                        }
                        else {
                            var focus_1 = this.containsFocus();
                            wijmo.hidePopup(dd);
                            if (focus_1) {
                                if (!this.isTouching || !this.showDropDownButton) {
                                    this.selectAll();
                                }
                                else {
                                    this.focus();
                                }
                            }
                        }
                        this._updateFocusState();
                        wijmo.toggleClass(host, 'wj-state-collapsed', !this.isDroppedDown);
                        this.onIsDroppedDownChanged();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(DropDown.prototype, "dropDown", {
                /**
                 * Gets the drop down element shown when the @see:isDroppedDown
                 * property is set to true.
                 */
                get: function () {
                    return this._dropDown;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(DropDown.prototype, "dropDownCssClass", {
                /**
                 * Gets or sets a CSS class name to add to the control's drop-down element.
                 *
                 * This property is useful when styling the drop-down element, because it is
                 * shown as a child of the document body rather than as a child of the control
                 * itself, which prevents using CSS selectors based on the parent control.
                 */
                get: function () {
                    return this._cssClass;
                },
                set: function (value) {
                    if (value != this._cssClass) {
                        wijmo.removeClass(this._dropDown, this._cssClass);
                        this._cssClass = wijmo.asString(value);
                        wijmo.addClass(this._dropDown, this._cssClass);
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(DropDown.prototype, "showDropDownButton", {
                /**
                 * Gets or sets a value that indicates whether the control should display a drop-down button.
                 */
                get: function () {
                    return this._showBtn;
                },
                set: function (value) {
                    this._showBtn = wijmo.asBoolean(value);
                    this._updateBtn();
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(DropDown.prototype, "autoExpandSelection", {
                /**
                 * Gets or sets a value that indicates whether the control should automatically expand the
                 * selection to whole words/numbers when the control is clicked.
                 */
                get: function () {
                    return this._autoExpand;
                },
                set: function (value) {
                    this._autoExpand = wijmo.asBoolean(value);
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(DropDown.prototype, "isAnimated", {
                /**
                 * Gets or sets a value that indicates whether the control should use a fade-in animation
                 * when displaying the drop-down.
                 */
                get: function () {
                    return this._animate;
                },
                set: function (value) {
                    this._animate = wijmo.asBoolean(value);
                },
                enumerable: true,
                configurable: true
            });
            /**
             * Sets the focus to the control and selects all its content.
             */
            DropDown.prototype.selectAll = function () {
                if (this._elRef == this._tbx) {
                    wijmo.setSelectionRange(this._tbx, 0, this.text.length);
                }
                else if (!this.containsFocus()) {
                    this.focus();
                }
            };
            /**
             * Raises the @see:textChanged event.
             */
            DropDown.prototype.onTextChanged = function (e) {
                this.textChanged.raise(this, e);
                this._updateState();
            };
            /**
             * Raises the @see:isDroppedDownChanging event.
             */
            DropDown.prototype.onIsDroppedDownChanging = function (e) {
                this.isDroppedDownChanging.raise(this, e);
                return !e.cancel;
            };
            /**
             * Raises the @see:isDroppedDownChanged event.
             */
            DropDown.prototype.onIsDroppedDownChanged = function (e) {
                this.isDroppedDownChanged.raise(this, e);
            };
            //#endregion
            //--------------------------------------------------------------------------
            //#region ** overrides
            // transfer focus from control to textbox
            // (but don't show the soft keyboard when the user touches the drop-down button)
            DropDown.prototype.onGotFocus = function (e) {
                if (!this.isTouching && !wijmo.contains(this._dropDown, wijmo.getActiveElement())) {
                    this.selectAll();
                }
                _super.prototype.onGotFocus.call(this, e);
            };
            // close the drop-down when losing focus
            DropDown.prototype.onLostFocus = function (e) {
                this._commitText();
                if (!this.containsFocus()) {
                    this.isDroppedDown = false;
                }
                _super.prototype.onLostFocus.call(this, e);
            };
            // check whether this control or its drop-down contain the focused element.
            // this is needed mostly for context menus, where the drop-down's owner
            // is not a child of the control (TFS 268503).
            DropDown.prototype.containsFocus = function () {
                return _super.prototype.containsFocus.call(this) ||
                    (this.isDroppedDown && wijmo.contains(this._dropDown, wijmo.getActiveElement()));
            };
            // close and dispose of drop-down when disposing the control
            DropDown.prototype.dispose = function () {
                this.isDroppedDown = false;
                var dd = this.dropDown;
                if (dd) {
                    var ctl = wijmo.Control.getControl(dd);
                    if (ctl) {
                        ctl.dispose();
                    }
                    wijmo.removeChild(dd);
                }
                _super.prototype.dispose.call(this);
            };
            // reposition dropdown when refreshing
            DropDown.prototype.refresh = function (fullUpdate) {
                if (fullUpdate === void 0) { fullUpdate = true; }
                _super.prototype.refresh.call(this, fullUpdate);
                // update popup/focus
                if (this.isDroppedDown) {
                    if (getComputedStyle(this.hostElement).display != 'none') {
                        var ae = wijmo.getActiveElement();
                        wijmo.showPopup(this._dropDown, this.hostElement, false, false, this.dropDownCssClass == null);
                        if (ae instanceof HTMLElement && ae != wijmo.getActiveElement()) {
                            ae.focus();
                        }
                    }
                }
            };
            // reposition dropdown when window size changes
            DropDown.prototype._handleResize = function () {
                if (this.isDroppedDown) {
                    this.refresh();
                }
            };
            //#endregion
            //--------------------------------------------------------------------------
            //#region ** implementation
            // expand the current selection to the entire number/string that was clicked
            DropDown.prototype._expandSelection = function () {
                var tbx = this._tbx, val = tbx.value, start = tbx.selectionStart, end = tbx.selectionEnd;
                if (val && start == end) {
                    var ct = this._getCharType(val, start);
                    if (ct > -1) {
                        for (; end < val.length; end++) {
                            if (this._getCharType(val, end) != ct) {
                                break;
                            }
                        }
                        for (; start > 0; start--) {
                            if (this._getCharType(val, start - 1) != ct) {
                                break;
                            }
                        }
                        if (start != end) {
                            wijmo.setSelectionRange(tbx, start, end);
                        }
                    }
                }
            };
            // get the type of character (digit, letter, other) at a given position
            DropDown.prototype._getCharType = function (text, pos) {
                var chr = text[pos];
                if (chr >= '0' && chr <= '9')
                    return 0;
                if ((chr >= 'a' && chr <= 'z') || (chr >= 'A' && chr <= 'Z'))
                    return 1;
                return -1;
            };
            // handle keyboard events
            DropDown.prototype._keydown = function (e) {
                // remember alt key for preventing smiley
                this._altDown = e.altKey;
                // ignore if default prevented
                if (e.defaultPrevented)
                    return;
                // handle key
                switch (e.keyCode) {
                    // close dropdown on tab, escape, enter
                    case wijmo.Key.Tab:
                    case wijmo.Key.Escape:
                    case wijmo.Key.Enter:
                        if (this.isDroppedDown) {
                            this.isDroppedDown = false;
                            if (e.keyCode != wijmo.Key.Tab && !this.containsFocus()) {
                                this.focus();
                            }
                            e.preventDefault();
                        }
                        break;
                    // toggle drop-down on F4, alt up/down
                    case wijmo.Key.F4:
                    case wijmo.Key.Up:
                    case wijmo.Key.Down:
                        if (e.keyCode == wijmo.Key.F4 || e.altKey) {
                            if (wijmo.contains(document.body, this.hostElement)) {
                                var dd = this.isDroppedDown;
                                this.isDroppedDown = !dd; // sets focus to input element (TFS 242752)
                                if (this.isDroppedDown == !dd) {
                                    e.preventDefault();
                                }
                            }
                        }
                        break;
                }
            };
            // handle clicks on the drop-down button
            DropDown.prototype._btnclick = function (e) {
                if (!e.defaultPrevented) {
                    this.isDroppedDown = !this.isDroppedDown;
                }
            };
            // update text in textbox
            DropDown.prototype._setText = function (text, fullMatch) {
                // make sure we have a string
                if (text == null)
                    text = '';
                text = text.toString();
                // update element
                if (text != this._tbx.value) {
                    this._tbx.value = text;
                }
                // fire change event
                if (text != this._oldText) {
                    this._oldText = text;
                    this.onTextChanged();
                }
            };
            // update drop-down button visibility
            DropDown.prototype._updateBtn = function () {
                this._btn.tabIndex = -1;
                this._btn.style.display = this._showBtn ? '' : 'none';
            };
            // create the drop-down element
            DropDown.prototype._createDropDown = function () {
                // override in derived classes
            };
            // commit the text in the value element
            DropDown.prototype._commitText = function () {
                // override in derived classes
            };
            // update drop down content before showing it
            DropDown.prototype._updateDropDown = function () {
                if (this.isDroppedDown) {
                    this._commitText();
                    wijmo.showPopup(this._dropDown, this.hostElement, false, this._animate, this.dropDownCssClass == null);
                }
            };
            /**
             * Gets or sets the template used to instantiate @see:DropDown controls.
             */
            DropDown.controlTemplate = '<div style="position:relative" class="wj-template">' +
                '<div class="wj-input">' +
                '<div class="wj-input-group wj-input-btn-visible">' +
                '<input wj-part="input" type="text" class="wj-form-control" />' +
                '<span wj-part="btn" class="wj-input-group-btn" tabindex="-1">' +
                '<button class="wj-btn wj-btn-default" type="button" tabindex="-1">' +
                '<span class="wj-glyph-down"></span>' +
                '</button>' +
                '</span>' +
                '</div>' +
                '</div>' +
                '<div wj-part="dropdown" class="wj-dropdown-panel wj-content" style="display:none">' +
                '</div>' +
                '</div>';
            return DropDown;
        }(wijmo.Control));
        input.DropDown = DropDown;
    })(input = wijmo.input || (wijmo.input = {}));
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
    var input;
    (function (input) {
        'use strict';
        /**
         * Specifies constants that define the date selection behavior.
         */
        var DateSelectionMode;
        (function (DateSelectionMode) {
            /** The user cannot change the current value using the mouse or keyboard. */
            DateSelectionMode[DateSelectionMode["None"] = 0] = "None";
            /** The user can select days. */
            DateSelectionMode[DateSelectionMode["Day"] = 1] = "Day";
            /** The user can select months. */
            DateSelectionMode[DateSelectionMode["Month"] = 2] = "Month";
        })(DateSelectionMode = input.DateSelectionMode || (input.DateSelectionMode = {}));
        /**
         * The @see:Calendar control displays a one-month calendar and allows users
         * to select a date.
         *
         * You may use the @see:min and @see:max properties to restrict the range
         * of dates that the user can select.
         *
         * For details about using the @see:min and @see:max properties, please see the
         * <a href="static/minMax.html">Using the min and max properties</a> topic.
         *
         * Use the @see:value property to get or set the currently selected date.
         *
         * Use the @see:selectionMode property to determine whether users should be
         * allowed to select days, months, or no values at all.
         *
         * The @see:Calendar control supports the following keyboard commands:
         *
         * <table>
         *   <thead>
         *     <tr><th>Key Combination</th><th>Moves Selection To</th></tr>
         *   </thead>
         *   <tbody>
         *     <tr><td>Left</td><td>Previous day</td></tr>
         *     <tr><td>Right</td><td>Next day</td></tr>
         *     <tr><td>Up</td><td>Previous week</td></tr>
         *     <tr><td>Down</td><td>Next week</td></tr>
         *     <tr><td>PgUp</td><td>Previous month</td></tr>
         *     <tr><td>PgDn</td><td>Next month</td></tr>
         *     <tr><td>Alt + PgUp</td><td>Previous year</td></tr>
         *     <tr><td>Alt + PgDn</td><td>Next year</td></tr>
         *     <tr><td>Home</td><td>@see:min value (if defined) or first of the month</td></tr>
         *     <tr><td>End</td><td>@see:max value (if defined) or last of the month</td></tr>
         *     <tr><td>Alt + End</td><td>Today's date</td></tr>
         *   </tbody>
         * </table>
         *
         * The example below shows a <b>Date</b> value with date and time information
         * using an @see:InputDate and an @see:InputTime control. Notice how both
         * controls are bound to the same controller variable, and each edits the
         * appropriate information (either date or time). The example also shows a
         * @see:Calendar control that allows users to select the date with a
         * single click.
         *
         * @fiddle:vgc3Y
         */
        var Calendar = /** @class */ (function (_super) {
            __extends(Calendar, _super);
            /**
             * Initializes a new instance of the @see:Calendar class.
             *
             * @param element The DOM element that hosts the control, or a selector for the host element (e.g. '#theCtrl').
             * @param options The JavaScript object containing initialization data for the control.
             */
            function Calendar(element, options) {
                var _this = _super.call(this, element) || this;
                _this._readOnly = false;
                _this._selMode = DateSelectionMode.Day;
                // formats used to display calendar elements
                _this._fmtYrMo = 'y';
                _this._fmtYr = 'yyyy';
                _this._fmtDayHdr = 'ddd';
                _this._fmtDay = 'd ';
                _this._fmtMonths = 'MMM';
                /**
                 * Occurs when the value of the @see:value property changes, either
                 * as a result of user actions or by assignment in code.
                 */
                _this.valueChanged = new wijmo.Event();
                /**
                 * Occurs after the @see:displayMonth property changes.
                 */
                _this.displayMonthChanged = new wijmo.Event();
                /**
                 * Occurs when an element representing a day in the calendar has been created.
                 *
                 * This event can be used to format calendar items for display. It is similar
                 * in purpose to the @see:itemFormatter property, but has the advantage
                 * of allowing multiple independent handlers.
                 *
                 * For example, the code below uses the @see:formatItem event to disable weekends
                 * so they appear dimmed in the calendar:
                 *
                 * <pre>// disable Sundays and Saturdays
                 * calendar.formatItem.addHandler(function (s, e) {
                 *   var day = e.data.getDay();
                 *   if (day == 0 || day == 6) {
                 *     wijmo.addClass(e.item, 'wj-state-disabled');
                 *   }
                 * });</pre>
                 */
                _this.formatItem = new wijmo.Event();
                // initialize value (current date)
                _this._value = wijmo.DateTime.newDate();
                _this._currMonth = _this._getMonth(_this._value);
                // create child elements
                _this._createChildren();
                // update the control
                _this.refresh(true);
                // handle mouse and keyboard
                // The 'click' event may not be triggered on iOS Safari if focus changed
                // during previous tap, so use 'mouseup' instead.
                //this.addEventListener(this.hostElement, 'click', this._click.bind(this));
                _this.addEventListener(_this.hostElement, 'mouseup', _this._click.bind(_this));
                _this.addEventListener(_this.hostElement, 'keydown', _this._keydown.bind(_this));
                // initialize control options
                _this.initialize(options);
                return _this;
            }
            Object.defineProperty(Calendar.prototype, "value", {
                //--------------------------------------------------------------------------
                //#region ** object model
                /**
                 * Gets or sets the currently selected date.
                 */
                get: function () {
                    return this._value;
                },
                set: function (value) {
                    value = wijmo.asDate(value, true);
                    // honor ranges (but keep the time)
                    // REVIEW: should not clamp this...
                    value = this._clamp(value);
                    // update control
                    if (this._valid(value)) {
                        this.displayMonth = this._getMonth(value);
                        if (!wijmo.DateTime.equals(this._value, value)) {
                            this._value = value;
                            this.invalidate(false);
                            this.onValueChanged();
                        }
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(Calendar.prototype, "min", {
                /**
                 * Gets or sets the earliest date that the user can select in the calendar.
                 *
                 * For details about using the @see:min and @see:max properties, please see the
                 * <a href="static/minMax.html">Using the min and max properties</a> topic.
                 */
                get: function () {
                    return this._min;
                },
                set: function (value) {
                    if (value != this.min) {
                        this._min = wijmo.asDate(value, true);
                        this.refresh();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(Calendar.prototype, "max", {
                /**
                 * Gets or sets the latest date that the user can select in the calendar.
                 *
                 * For details about using the @see:min and @see:max properties, please see the
                 * <a href="static/minMax.html">Using the min and max properties</a> topic.
                 */
                get: function () {
                    return this._max;
                },
                set: function (value) {
                    if (value != this.max) {
                        this._max = wijmo.asDate(value, true);
                        this.refresh();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(Calendar.prototype, "selectionMode", {
                /**
                 * Gets or sets a value that indicates whether users can select
                 * days, months, or no values at all.
                 */
                get: function () {
                    return this._selMode;
                },
                set: function (value) {
                    if (value != this._selMode) {
                        // apply new setting
                        this._selMode = wijmo.asEnum(value, DateSelectionMode);
                        // update monthView
                        var mthMode = this._monthMode();
                        if (mthMode)
                            this.monthView = false;
                        // update month glyph
                        var mthGlyph = this._btnMth.querySelector('.wj-glyph-down');
                        if (mthGlyph)
                            mthGlyph.style.display = mthMode ? 'none' : '';
                        // refresh to show/hide the selection
                        this.refresh();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(Calendar.prototype, "isReadOnly", {
                /**
                 * Gets or sets a value that indicates whether the user can modify
                 * the control value using the mouse and keyboard.
                 */
                get: function () {
                    return this._readOnly;
                },
                set: function (value) {
                    this._readOnly = wijmo.asBoolean(value);
                    wijmo.toggleClass(this.hostElement, 'wj-state-readonly', this.isReadOnly);
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(Calendar.prototype, "firstDayOfWeek", {
                /**
                 * Gets or sets a value that represents the first day of the week,
                 * the one displayed in the first column of the calendar.
                 *
                 * Setting this property to null causes the calendar to use the default
                 * for the current culture. In the English culture, the first day of the
                 * week is Sunday (0); in most European cultures, the first day of the
                 * week is Monday (1).
                 */
                get: function () {
                    return this._fdw;
                },
                set: function (value) {
                    if (value != this._fdw) {
                        value = wijmo.asNumber(value, true);
                        if (value && (value > 6 || value < 0)) {
                            throw 'firstDayOfWeek must be between 0 and 6 (Sunday to Saturday).';
                        }
                        this._fdw = value;
                        this.refresh();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(Calendar.prototype, "displayMonth", {
                /**
                 * Gets or sets the month displayed in the calendar.
                 */
                get: function () {
                    return this._currMonth;
                },
                set: function (value) {
                    if (!wijmo.DateTime.equals(this.displayMonth, value)) {
                        value = wijmo.asDate(value);
                        var valid = this.monthView // TFS 208757
                            ? this._monthInValidRange(value)
                            : this._yearInValidRange(value);
                        if (valid) {
                            this._currMonth = this._getMonth(this._clamp(value)); // TFS 208757
                            this.invalidate(true);
                            this.onDisplayMonthChanged();
                        }
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(Calendar.prototype, "formatYearMonth", {
                /**
                 * Gets or sets the format used to display the month and year
                 * above the calendar in month view.
                 *
                 * The default value for this property is 'y'.
                 */
                get: function () {
                    return this._fmtYrMo;
                },
                set: function (value) {
                    if (value != this._fmtYrMo) {
                        this._fmtYrMo = wijmo.asString(value);
                        this.invalidate();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(Calendar.prototype, "formatDayHeaders", {
                /**
                 * Gets or sets the format used to display the headers
                 * above the days in month view.
                 *
                 * The default value for this property is 'ddd'.
                 */
                get: function () {
                    return this._fmtDayHdr;
                },
                set: function (value) {
                    if (value != this._fmtDayHdr) {
                        this._fmtDayHdr = wijmo.asString(value);
                        this.invalidate();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(Calendar.prototype, "formatDays", {
                /**
                 * Gets or sets the format used to display the days
                 * in month view.
                 *
                 * The default value for this property is 'd ' (the space after the 'd'
                 * prevents the format from being interpreted as 'd', the standard format
                 * used to represent the short date pattern).
                 */
                get: function () {
                    return this._fmtDay;
                },
                set: function (value) {
                    if (value != this._fmtDay) {
                        this._fmtDay = wijmo.asString(value);
                        this.invalidate();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(Calendar.prototype, "formatYear", {
                /**
                 * Gets or sets the format used to display the year
                 * above the months in year view.
                 *
                 * The default value for this property is 'yyyy'.
                 */
                get: function () {
                    return this._fmtYr;
                },
                set: function (value) {
                    if (value != this._fmtYr) {
                        this._fmtYr = wijmo.asString(value);
                        this.invalidate();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(Calendar.prototype, "formatMonths", {
                /**
                 * Gets or sets the format used to display the months
                 * in year view.
                 *
                 * The default value for this property is 'MMM'.
                 */
                get: function () {
                    return this._fmtMonths;
                },
                set: function (value) {
                    if (value != this._fmtMonths) {
                        this._fmtMonths = wijmo.asString(value);
                        this.invalidate();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(Calendar.prototype, "showHeader", {
                /**
                 * Gets or sets a value indicating whether the control displays the header
                 * area with the current month and navigation buttons.
                 */
                get: function () {
                    return this._tbHdr.style.display != 'none';
                },
                set: function (value) {
                    this._tbHdr.style.display = wijmo.asBoolean(value) ? '' : 'none';
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(Calendar.prototype, "monthView", {
                /**
                 * Gets or sets a value indicating whether the calendar displays a month or a year.
                 */
                get: function () {
                    return this._tbMth.style.display != 'none';
                },
                set: function (value) {
                    if (value != this.monthView) {
                        this._tbMth.style.display = value ? '' : 'none';
                        this._tbYr.style.display = value ? 'none' : '';
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(Calendar.prototype, "itemFormatter", {
                /**
                 * Gets or sets a formatter function to customize dates in the calendar.
                 *
                 * The formatter function can add any content to any date. It allows
                 * complete customization of the appearance and behavior of the calendar.
                 *
                 * If specified, the function takes two parameters:
                 * <ul>
                 *     <li>the date being formatted </li>
                 *     <li>the HTML element that represents the date</li>
                 * </ul>
                 *
                 * For example, the code below shows weekends with a yellow background:
                 * <pre>
                 * calendar.itemFormatter = function(date, element) {
                 *   var day = date.getDay();
                 *   element.style.backgroundColor = day == 0 || day == 6 ? 'yellow' : '';
                 * }
                 * </pre>
                 */
                get: function () {
                    return this._itemFormatter;
                },
                set: function (value) {
                    if (value != this._itemFormatter) {
                        this._itemFormatter = wijmo.asFunction(value);
                        this.invalidate();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(Calendar.prototype, "itemValidator", {
                /**
                 * Gets or sets a validator function to determine whether dates are valid for selection.
                 *
                 * If specified, the validator function should take one parameter representing the
                 * date to be tested, and should return false if the date is invalid and should not
                 * be selectable.
                 *
                 * For example, the code below shows weekends in a disabled state and prevents users
                 * from selecting those dates:
                 * <pre>
                 * calendar.itemValidator = function(date) {
                 *   var weekday = date.getDay();
                 *   return weekday != 0 && weekday != 6;
                 * }
                 * </pre>
                 */
                get: function () {
                    return this._itemValidator;
                },
                set: function (value) {
                    if (value != this._itemValidator) {
                        this._itemValidator = wijmo.asFunction(value);
                        this.invalidate();
                    }
                },
                enumerable: true,
                configurable: true
            });
            /**
             * Raises the @see:valueChanged event.
             */
            Calendar.prototype.onValueChanged = function (e) {
                this.valueChanged.raise(this, e);
            };
            /**
             * Raises the @see:displayMonthChanged event.
             */
            Calendar.prototype.onDisplayMonthChanged = function (e) {
                this.displayMonthChanged.raise(this, e);
            };
            /**
             * Raises the @see:formatItem event.
             *
             * @param e @see:FormatItemEventArgs that contains the event data.
             */
            Calendar.prototype.onFormatItem = function (e) {
                this.formatItem.raise(this, e);
            };
            /**
             * Refreshes the control.
             *
             * @param fullUpdate Indicates whether to update the control layout as well as the content.
             */
            Calendar.prototype.refresh = function (fullUpdate) {
                if (fullUpdate === void 0) { fullUpdate = true; }
                var cells, cell, day, month = this.displayMonth, fdw = this.firstDayOfWeek != null ? this.firstDayOfWeek : wijmo.Globalize.getFirstDayOfWeek();
                // call base class to suppress any pending invalidations
                _super.prototype.refresh.call(this, fullUpdate);
                // calculate first day of the calendar
                this._firstDay = wijmo.DateTime.addDays(month, -(month.getDay() - fdw + 7) % 7);
                // update current display month (e.g. January 2014)
                wijmo.setText(this._spMth, wijmo.Globalize.format(month, this._fmtYrMo));
                // update week day headers (localizable)
                cells = this._tbMth.querySelectorAll('td');
                for (var i = 0; i < 7 && i < cells.length; i++) {
                    day = wijmo.DateTime.addDays(this._firstDay, i);
                    wijmo.setText(cells[i], wijmo.Globalize.format(day, this._fmtDayHdr));
                }
                // update month days
                for (var i = 7; i < cells.length; i++) {
                    cell = cells[i];
                    day = wijmo.DateTime.addDays(this._firstDay, i - 7);
                    wijmo.setText(cell, wijmo.Globalize.format(day, this._fmtDay));
                    var invalid = !this._valid(day);
                    wijmo.toggleClass(cell, 'wj-state-invalid', invalid);
                    wijmo.toggleClass(cell, 'wj-state-selected', this._selMode && wijmo.DateTime.sameDate(day, this.value));
                    wijmo.toggleClass(cell, 'wj-day-today', wijmo.DateTime.sameDate(day, wijmo.DateTime.newDate()));
                    wijmo.toggleClass(cell, 'wj-day-othermonth', invalid || day.getMonth() != month.getMonth() || !this._inValidRange(day));
                    // customize the display
                    if (this.itemFormatter) {
                        this.itemFormatter(day, cell);
                    }
                    if (this.formatItem.hasHandlers) {
                        var e = new input.FormatItemEventArgs(i, day, cell);
                        this.onFormatItem(e);
                    }
                }
                // hide rows that belong to the next month
                var rows = this._tbMth.querySelectorAll('tr');
                if (rows.length) {
                    day = wijmo.DateTime.addDays(this._firstDay, 28);
                    rows[rows.length - 2].style.display = (day.getMonth() == month.getMonth()) ? '' : 'none';
                    day = wijmo.DateTime.addDays(this._firstDay, 35);
                    rows[rows.length - 1].style.display = (day.getMonth() == month.getMonth()) ? '' : 'none';
                }
                // update current year 
                cells = this._tbYr.querySelectorAll('td');
                if (cells.length) {
                    wijmo.setText(cells[0], wijmo.Globalize.format(month, this._fmtYr));
                }
                // update month names
                for (var i = 1; i < cells.length; i++) {
                    cell = cells[i];
                    day = wijmo.DateTime.newDate(month.getFullYear(), i - 1, 1);
                    wijmo.setText(cell, wijmo.Globalize.format(day, this._fmtMonths));
                    cell.className = '';
                    wijmo.toggleClass(cell, 'wj-state-disabled', !this._monthInValidRange(day));
                    wijmo.toggleClass(cell, 'wj-state-selected', this._sameMonth(day, this.value));
                }
            };
            //#endregion
            //--------------------------------------------------------------------------
            //#region ** implementation
            // checks whether the control can change the current value
            Calendar.prototype._canChangeValue = function () {
                return !this._readOnly && this._selMode != DateSelectionMode.None;
            };
            // check whether a date should be selectable by the user
            Calendar.prototype._valid = function (date) {
                return this.itemValidator && date
                    ? this.itemValidator(date)
                    : true;
            };
            // check whether a day is within the valid range
            Calendar.prototype._inValidRange = function (date) {
                if (this.min && date < wijmo.DateTime.fromDateTime(this.min, date))
                    return false;
                if (this.max && date > wijmo.DateTime.fromDateTime(this.max, date))
                    return false;
                return true;
            };
            // check whether a month contains days in the valid range
            // get the month's first and last days, then test whether
            // the min is after the last or the max is before the first
            // to detect invalid months (TFS 221061)
            Calendar.prototype._monthInValidRange = function (month) {
                if (this.min || this.max) {
                    var y = month.getFullYear(), m = month.getMonth(), first = wijmo.DateTime.newDate(y, m, 1), last = wijmo.DateTime.addDays(wijmo.DateTime.newDate(y, m + 1, 1), -1); // TFS 276518
                    if (this.min && this.min > last)
                        return false;
                    if (this.max && this.max < first)
                        return false;
                }
                return true;
            };
            // check whether a year contains days in the valid range
            Calendar.prototype._yearInValidRange = function (year) {
                if (this.min || this.max) {
                    var y = year.getFullYear(), first = wijmo.DateTime.newDate(y, 0), last = wijmo.DateTime.newDate(y, 11, 31);
                    if (this.min && this.min > last)
                        return false;
                    if (this.max && this.max < first)
                        return false;
                }
                return true;
            };
            // checks whether a date is in the current month
            Calendar.prototype._sameMonth = function (date, month) {
                return wijmo.isDate(date) && wijmo.isDate(month) &&
                    date.getMonth() == month.getMonth() &&
                    date.getFullYear() == month.getFullYear();
            };
            // honor min/max range (keeping the time)
            Calendar.prototype._clamp = function (value) {
                if (value) {
                    if (this.min) {
                        var min = wijmo.DateTime.fromDateTime(this.min, value);
                        if (value < min) {
                            value = min;
                        }
                    }
                    if (this.max) {
                        var max = wijmo.DateTime.fromDateTime(this.max, value);
                        if (value > max) {
                            value = max;
                        }
                    }
                }
                return value;
            };
            // create child elements
            Calendar.prototype._createChildren = function () {
                // instantiate and apply template
                var tpl = this.getTemplate();
                this.applyTemplate('wj-control wj-calendar', tpl, {
                    _tbHdr: 'tbl-header',
                    _btnMth: 'btn-month',
                    _spMth: 'span-month',
                    _btnPrv: 'btn-prev',
                    _btnTdy: 'btn-today',
                    _btnNxt: 'btn-next',
                    _tbMth: 'tbl-month',
                    _tbYr: 'tbl-year'
                });
                // populate month calendar
                var tr = this._createElement('tr', this._tbMth, 'wj-header');
                for (var d = 0; d < 7; d++) {
                    this._createElement('td', tr);
                }
                for (var w = 0; w < 6; w++) {
                    tr = this._createElement('tr', this._tbMth);
                    for (var d = 0; d < 7; d++) {
                        this._createElement('td', tr);
                    }
                }
                // populate year calendar
                tr = this._createElement('tr', this._tbYr, 'wj-header');
                this._createElement('td', tr).setAttribute('colspan', '4');
                for (var i = 0; i < 3; i++) {
                    tr = this._createElement('tr', this._tbYr);
                    for (var j = 0; j < 4; j++) {
                        this._createElement('td', tr);
                    }
                }
            };
            // create an element, append it to another element, and set its class name
            Calendar.prototype._createElement = function (tag, parent, className) {
                var el = document.createElement(tag);
                if (parent)
                    parent.appendChild(el);
                if (className)
                    wijmo.addClass(el, className);
                return el;
            };
            // handle clicks on the calendar
            Calendar.prototype._click = function (e) {
                var handled = false;
                // get element that was clicked
                var elem = e.target;
                // switch month/year view
                if (wijmo.contains(this._btnMth, elem) && !this._monthMode()) {
                    this.monthView = !this.monthView;
                    handled = true;
                }
                else if (wijmo.contains(this._btnPrv, elem)) {
                    this._navigate(-1);
                    handled = true;
                }
                else if (wijmo.contains(this._btnNxt, elem)) {
                    this._navigate(+1);
                    handled = true;
                }
                else if (wijmo.contains(this._btnTdy, elem)) {
                    this._navigate(0);
                    handled = true;
                }
                // select day/month
                if (elem && !handled) {
                    elem = wijmo.closest(elem, 'TD');
                    if (elem) {
                        if (this.monthView) {
                            var index = this._getCellIndex(this._tbMth, elem);
                            if (index > 6 && this._canChangeValue()) {
                                var value = wijmo.DateTime.fromDateTime(wijmo.DateTime.addDays(this._firstDay, index - 7), this.value);
                                if (this._inValidRange(value) && this._valid(value)) {
                                    this.value = value;
                                }
                                handled = true;
                            }
                        }
                        else {
                            var index = this._getCellIndex(this._tbYr, elem);
                            if (index > 0) {
                                this.displayMonth = wijmo.DateTime.newDate(this.displayMonth.getFullYear(), index - 1, 1);
                                if (this._monthMode()) {
                                    if (this._canChangeValue()) {
                                        var value = wijmo.DateTime.fromDateTime(this.displayMonth, this.value);
                                        if (this._inValidRange(value)) {
                                            this.value = value;
                                        }
                                    }
                                }
                                else {
                                    this.monthView = true;
                                }
                                handled = true;
                            }
                        }
                    }
                }
                // if we handled the mouse, prevent browser from seeing it
                if (handled) {
                    e.preventDefault();
                    this.focus();
                }
            };
            // gets the index of a cell in a table
            Calendar.prototype._getCellIndex = function (tbl, cell) {
                var cells = tbl.querySelectorAll('TD');
                for (var i = 0; i < cells.length; i++) {
                    if (cells[i] == cell)
                        return i;
                }
                return -1;
            };
            // handle keyboard events
            Calendar.prototype._keydown = function (e) {
                // honor defaultPrevented
                if (e.defaultPrevented)
                    return;
                // alt up/down: open/close popup
                if (e.altKey) {
                    switch (e.keyCode) {
                        case wijmo.Key.Up:
                        case wijmo.Key.Down:
                            return;
                        case wijmo.Key.End:// alt End: today's date
                            this._navigate(0);
                            e.preventDefault();
                            return;
                    }
                }
                // not interested in meta keys
                if (e.ctrlKey || e.metaKey || e.shiftKey) {
                    return;
                }
                // perform date navigation
                var addDays = 0, addMonths = 0, handled = true;
                if (this.monthView) {
                    switch (e.keyCode) {
                        case wijmo.Key.Left:
                            addDays = -1;
                            break;
                        case wijmo.Key.Right:
                            addDays = +1;
                            break;
                        case wijmo.Key.Up:
                            addDays = -7;
                            break;
                        case wijmo.Key.Down:
                            addDays = +7;
                            break;
                        case wijmo.Key.PageDown:
                            addMonths = e.altKey ? +12 : +1; // year/month
                            break;
                        case wijmo.Key.PageUp:
                            addMonths = e.altKey ? -12 : -1; // year/month
                            break;
                        case wijmo.Key.Home:// min/first of the month
                            if (this._canChangeValue()) {
                                if (this.min) {
                                    this.value = wijmo.DateTime.fromDateTime(this.min, this.value);
                                }
                                else if (this.value) {
                                    var dt = this.value;
                                    dt = wijmo.DateTime.newDate(dt.getFullYear(), dt.getMonth(), 1);
                                    this.value = wijmo.DateTime.fromDateTime(dt, this.value);
                                }
                            }
                            break;
                        case wijmo.Key.End:// max/last of the month
                            if (this._canChangeValue()) {
                                if (this.max) {
                                    this.value = wijmo.DateTime.fromDateTime(this.max, this.value);
                                }
                                else if (this.value) {
                                    var dt = this.value;
                                    dt = wijmo.DateTime.newDate(dt.getFullYear(), dt.getMonth() + 1, 0);
                                    this.value = wijmo.DateTime.fromDateTime(dt, this.value);
                                }
                            }
                            break;
                        default:
                            handled = false;
                            break;
                    }
                }
                else {
                    switch (e.keyCode) {
                        case wijmo.Key.Left:
                            addMonths = -1;
                            break;
                        case wijmo.Key.Right:
                            addMonths = +1;
                            break;
                        case wijmo.Key.Up:
                            addMonths = -4;
                            break;
                        case wijmo.Key.Down:
                            addMonths = +4;
                            break;
                        case wijmo.Key.PageDown:
                            addMonths = e.altKey ? +120 : +12; // decade/year
                            break;
                        case wijmo.Key.PageUp:
                            addMonths = e.altKey ? -120 : -12; // decade/year
                            break;
                        case wijmo.Key.Home:// jan
                            addMonths = this.value ? -this.value.getMonth() : 0;
                            break;
                        case wijmo.Key.End:// dec
                            addMonths = this.value ? 11 - this.value.getMonth() : 0;
                            break;
                        case wijmo.Key.Enter:// back to month view
                            if (!this._monthMode()) {
                                this.monthView = true;
                            }
                            else {
                                handled = false;
                            }
                            break;
                        default:
                            handled = false;
                            break;
                    }
                }
                // apply the change
                if (this.value && this._canChangeValue() && (addDays || addMonths)) {
                    // add days/months
                    var dt = this.value;
                    dt = wijmo.DateTime.addDays(dt, addDays);
                    dt = wijmo.DateTime.addMonths(dt, addMonths);
                    // skip over invalid dates (TFS 223913)
                    for (var cnt = 0; !this._valid(dt) && cnt < 31; cnt++) {
                        dt = wijmo.DateTime.addDays(dt, addDays > 0 || addMonths > 0 ? +1 : -1);
                    }
                    // set the new value
                    this.value = dt;
                }
                // if we handled the key, prevent browser from seeing it
                if (handled) {
                    e.preventDefault();
                }
            };
            // gets the month being displayed in the calendar
            Calendar.prototype._getMonth = function (date) {
                if (!date)
                    date = wijmo.DateTime.newDate();
                return wijmo.DateTime.newDate(date.getFullYear(), date.getMonth(), 1);
            };
            // returns true in month selection mode
            Calendar.prototype._monthMode = function () {
                return this.selectionMode == DateSelectionMode.Month;
            };
            // change display month by a month or a year, or skip to the current
            Calendar.prototype._navigate = function (skip) {
                var monthView = this.monthView;
                switch (skip) {
                    // today/this month
                    case 0:
                        var today = wijmo.DateTime.newDate();
                        if (monthView) {
                            if (this._canChangeValue()) {
                                this.value = wijmo.DateTime.fromDateTime(today, this.value); // select today's date
                            }
                        }
                        else {
                            if (this._canChangeValue()) {
                                this.value = this._getMonth(today); // select today's month
                            }
                        }
                        this._setDisplayMonth(this._getMonth(today)); // show today's month
                        break;
                    // show next month/year (keeping current value)
                    case +1:
                        this._setDisplayMonth(wijmo.DateTime.addMonths(this.displayMonth, monthView ? +1 : +12));
                        break;
                    // show previous month/year (keeping current value)
                    case -1:
                        this._setDisplayMonth(wijmo.DateTime.addMonths(this.displayMonth, monthView ? -1 : -12));
                        break;
                }
            };
            // set the display month if it is within the valid range
            Calendar.prototype._setDisplayMonth = function (value) {
                if (this._yearInValidRange(value)) {
                    this.displayMonth = value;
                }
            };
            /**
             * Gets or sets the template used to instantiate @see:Calendar controls.
             */
            Calendar.controlTemplate = '<div class="wj-calendar-outer wj-content">' +
                '<div wj-part="tbl-header" class="wj-calendar-header">' +
                '<div wj-part="btn-month" class="wj-month-select">' +
                '<span wj-part="span-month"></span> <span class="wj-glyph-down"></span>' +
                '</div>' +
                '<div class="wj-btn-group">' +
                '<button type="button" tabindex="-1" wj-part="btn-prev" class="wj-btn wj-btn-default"><span class="wj-glyph-left"></span></button>' +
                '<button type="button" tabindex="-1" wj-part="btn-today" class="wj-btn wj-btn-default"><span class="wj-glyph-circle"></span></button>' +
                '<button type="button" tabindex="-1" wj-part="btn-next" class="wj-btn wj-btn-default"><span class="wj-glyph-right"></span></button>' +
                '</div>' +
                '</div>' +
                '<table wj-part="tbl-month" class="wj-calendar-month"/>' +
                '<table wj-part="tbl-year" class="wj-calendar-year" style="display:none"/>' +
                '</div>';
            return Calendar;
        }(wijmo.Control));
        input.Calendar = Calendar;
    })(input = wijmo.input || (wijmo.input = {}));
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
    var input;
    (function (input) {
        'use strict';
        /**
         * The @see:ColorPicker control allows users to select a color by clicking
         * on panels to adjust color channels (hue, saturation, brightness, alpha).
         *
         * Use the @see:value property to get or set the currently selected color.
         *
         * The control is used as a drop-down for the @see:InputColor control.
         *
         * @fiddle:84xvsz90
         */
        var ColorPicker = /** @class */ (function (_super) {
            __extends(ColorPicker, _super);
            /**
             * Initializes a new instance of the @see:ColorPicker class.
             *
             * @param element The DOM element that hosts the control, or a selector for the host element (e.g. '#theCtrl').
             * @param options The JavaScript object containing initialization data for the control.
             */
            function ColorPicker(element, options) {
                var _this = _super.call(this, element) || this;
                _this._hsb = [.5, 1, 1];
                _this._alpha = 1;
                /**
                 * Occurs when the value of the @see:value property changes, either
                 * as a result of user actions or by assignment in code.
                 */
                _this.valueChanged = new wijmo.Event();
                // instantiate and apply template
                var tpl = _this.getTemplate();
                _this.applyTemplate('wj-control wj-colorpicker wj-content', tpl, {
                    _eSB: 'div-sb',
                    _eHue: 'div-hue',
                    _eAlpha: 'div-alpha',
                    _ePreview: 'div-pv',
                    _ePal: 'div-pal',
                    _eText: 'div-text'
                });
                // build palette
                _this._palette = '#FFF,#000, #F00,#FFC000,#FFFF00,#92D050,#00B050,#00B0F0,#0070C0,#7030A0'.split(',');
                _this._updatePalette();
                // build hue gradient 
                // (use an image since IE9 doesn't support multi-stop gradients)
                _this._eHue.style.backgroundImage = 'url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAD4CAIAAACi6hsPAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAAAYdEVYdFNvZnR3YXJlAHBhaW50Lm5ldCA0LjAuM4zml1AAAAGvSURBVDhPXdBPaM9xHMfxz4pWaxcmtoOhpdXSVpiyHWxqmVpDjaU5rK34XfypjTJ/p+ZPay6jhsOsRrKwaJElf9IQq03WIkv4FeMwMq221tfje1ByeFzfvd7PEKWGEKWTQRZLySWfVRRTQjmVbKWGOhLsZT+HaeY0bbTTQSfdXOcWffTzmAFeMcwoYyT5ygS/mA5hNgphip98J8kHRnnNSwZ4yH1uc4OrdHGR87RximYO0cgedlLLdqqoYAPrWMtKVrCcJSxiPmnMJUQp/Bsyk2xyyKOAQooopYwKtlDNDur5G7SBJo7RQiv/B+2hl3s84CkvGGKEOOYnxolj/mYmhBmDJ5ngCx95xxsGecYj4pB3iENeoZMO2mmlhaMcpIE4ZII6aqhmM3HMMkooopB88sghm0wySCeVlCjMCVFIYx4LWUwOeRSwhmLWU84mqqihll3sppEmjnOSs5zjEl1c4yZ99POE5wwxwns+840fTDFLFKaZZIJxkozxlmEGGSC+GF++Sy89dHOZC8Rr4lVnOMERDrCPBPXEX22jko2UEn+/mnxyWUYWC0gnNUQh/AEc0HJs6cex0gAAAABJRU5ErkJggg==)';
                _this._eHue.style.backgroundSize = 'contain';
                // add filter gradients to IE 9
                if (navigator.appVersion.indexOf('MSIE 9') > -1) {
                    _this._eSB.children[0].style.filter = 'progid:DXImageTransform.Microsoft.gradient(startColorstr=#ffffffff,endColorstr=#00ffffff,GradientType=1)';
                    _this._eSB.children[1].style.filter = 'progid:DXImageTransform.Microsoft.gradient(startColorstr=#00000000,endColorstr=#ff000000,GradientType=0)';
                }
                // add cursors to panels
                tpl = ColorPicker._tplCursor;
                _this._cSB = wijmo.createElement(tpl);
                _this._cHue = wijmo.createElement(tpl);
                _this._cHue.style.width = '100%';
                _this._cAlpha = wijmo.createElement(tpl);
                _this._cAlpha.style.height = '100%';
                _this._eSB.appendChild(_this._cSB);
                _this._eHue.appendChild(_this._cHue);
                _this._eAlpha.appendChild(_this._cAlpha);
                // handle mouse
                _this.addEventListener(_this.hostElement, 'mousedown', function (e) {
                    document.addEventListener('mousemove', mouseMove);
                    document.addEventListener('mouseup', mouseUp);
                    _this._mouseDown(e);
                });
                var mouseMove = function (e) {
                    _this._mouseMove(e);
                };
                var mouseUp = function (e) {
                    document.removeEventListener('mousemove', mouseMove);
                    document.removeEventListener('mouseup', mouseUp);
                    _this._mouseUp(e);
                };
                // handle clicks on the palette
                _this.addEventListener(_this.hostElement, 'click', function (e) {
                    var el = e.target;
                    if (el && el.tagName == 'DIV' && wijmo.contains(_this._ePal, el)) {
                        var color = el.style.backgroundColor;
                        if (color) {
                            _this.value = new wijmo.Color(color).toString();
                        }
                    }
                });
                // initialize value to white
                _this.value = '#ffffff';
                // initialize control options
                _this.initialize(options);
                // initialize control
                _this._updatePanels();
                return _this;
            }
            Object.defineProperty(ColorPicker.prototype, "showAlphaChannel", {
                /**
                 * Gets or sets a value indicating whether the @see:ColorPicker allows users
                 * to edit the color's alpha channel (transparency).
                 */
                get: function () {
                    return this._eAlpha.parentElement.style.display != 'none';
                },
                set: function (value) {
                    this._eAlpha.parentElement.style.display = wijmo.asBoolean(value) ? '' : 'none';
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(ColorPicker.prototype, "showColorString", {
                /**
                 * Gets or sets a value indicating whether the @see:ColorPicker shows a string representation
                 * of the current color.
                 */
                get: function () {
                    return this._eText.style.display != 'none';
                },
                set: function (value) {
                    this._eText.style.display = wijmo.asBoolean(value) ? '' : 'none';
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(ColorPicker.prototype, "value", {
                /**
                 * Gets or sets the currently selected color.
                 */
                get: function () {
                    return this._value;
                },
                set: function (value) {
                    if (value != this.value) {
                        // save new value
                        this._value = wijmo.asString(value);
                        this._eText.innerText = this._value;
                        // parse new color, convert to hsb values
                        var c = new wijmo.Color(this._value), hsb = c.getHsb();
                        // check whether the color really changed
                        if (this._hsb[0] != hsb[0] || this._hsb[1] != hsb[1] ||
                            this._hsb[2] != hsb[2] || this._alpha != c.a) {
                            // update hsb channels (but keep hue when s/b go to zero)
                            if (hsb[2] == 0) {
                                hsb[0] = this._hsb[0];
                                hsb[1] = this._hsb[1];
                            }
                            else if (hsb[1] == 0) {
                                hsb[0] = this._hsb[0];
                            }
                            this._hsb = hsb;
                            this._alpha = c.a;
                            // raise valueChanged event
                            this.onValueChanged();
                        }
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(ColorPicker.prototype, "palette", {
                /**
                 * Gets or sets an array that contains the colors in the palette.
                 *
                 * The palette contains ten colors, represented by an array with
                 * ten strings. The first two colors are usually white and black.
                 */
                get: function () {
                    return this._palette;
                },
                set: function (value) {
                    value = wijmo.asArray(value);
                    for (var i = 0; i < value.length && i < this._palette.length; i++) {
                        var entry = wijmo.asString(value[i]);
                        this._palette[i] = entry;
                    }
                    this._updatePalette();
                },
                enumerable: true,
                configurable: true
            });
            /**
             * Raises the @see:valueChanged event.
             */
            ColorPicker.prototype.onValueChanged = function (e) {
                this._updatePanels();
                this.valueChanged.raise(this, e);
            };
            // ** event handlers
            ColorPicker.prototype._mouseDown = function (e) {
                this._htDown = this._getTargetPanel(e);
                if (this._htDown) {
                    e.preventDefault();
                    this.focus();
                    this._mouseMove(e);
                }
            };
            ColorPicker.prototype._mouseMove = function (e) {
                if (this._htDown) {
                    var rc = this._htDown.getBoundingClientRect();
                    if (this._htDown == this._eHue) {
                        this._hsb[0] = wijmo.clamp((e.clientY - rc.top) / rc.height, 0, .99);
                        this._updateColor();
                    }
                    else if (this._htDown == this._eSB) {
                        this._hsb[1] = wijmo.clamp((e.clientX - rc.left) / rc.width, 0, 1);
                        this._hsb[2] = wijmo.clamp(1 - (e.clientY - rc.top) / rc.height, 0, 1);
                        this._updateColor();
                    }
                    else if (this._htDown == this._eAlpha) {
                        this._alpha = wijmo.clamp((e.clientX - rc.left) / rc.width, 0, 1);
                        this._updateColor();
                    }
                }
            };
            ColorPicker.prototype._mouseUp = function (e) {
                this._htDown = null;
            };
            // update color value to reflect new hsb values
            ColorPicker.prototype._updateColor = function () {
                var c = wijmo.Color.fromHsb(this._hsb[0], this._hsb[1], this._hsb[2], this._alpha);
                this.value = c.toString();
                this._updatePanels();
            };
            // updates the color elements in the palette
            ColorPicker.prototype._updatePalette = function () {
                var white = new wijmo.Color('#fff'), black = new wijmo.Color('#000');
                // clear the current palette
                this._ePal.innerHTML = '';
                // add one column per palette color
                for (var i = 0; i < this._palette.length; i++) {
                    var div = wijmo.createElement('<div style="float:left;width:10%;box-sizing:border-box;padding:1px">'), clr = new wijmo.Color(this._palette[i]), hsb = clr.getHsb();
                    // add palette color
                    div.appendChild(this._makePalEntry(clr, 4));
                    // add six shades for this color
                    for (var r = 0; r < 5; r++) {
                        if (hsb[1] == 0) {
                            var pct = r * .1 + (hsb[2] > .5 ? .05 : .55);
                            clr = wijmo.Color.interpolate(white, black, pct);
                        }
                        else {
                            clr = wijmo.Color.fromHsb(hsb[0], 0.1 + r * 0.2, 1 - r * 0.1);
                        }
                        div.appendChild(this._makePalEntry(clr, 0));
                    }
                    // add color and shades to palette
                    this._ePal.appendChild(div);
                }
            };
            // creates a palette entry with the given color
            ColorPicker.prototype._makePalEntry = function (color, margin) {
                var e = document.createElement('div');
                wijmo.setCss(e, {
                    cursor: 'pointer',
                    backgroundColor: color.toString(),
                    marginBottom: margin ? margin : ''
                });
                e.innerHTML = '&nbsp';
                return e;
            };
            // update color and cursor on all panels
            ColorPicker.prototype._updatePanels = function () {
                var clrHue = wijmo.Color.fromHsb(this._hsb[0], 1, 1, 1), clrSolid = wijmo.Color.fromHsb(this._hsb[0], this._hsb[1], this._hsb[2], 1);
                this._eSB.style.backgroundColor = clrHue.toString();
                this._eAlpha.style.background = 'linear-gradient(to right, transparent 0%, ' + clrSolid.toString() + ' 100%)';
                if (navigator.appVersion.indexOf('MSIE 9') > -1) {
                    this._eAlpha.style.filter = 'progid:DXImageTransform.Microsoft.gradient(startColorstr=#00000000,endColorstr=' + clrSolid.toString() + ', GradientType = 1)';
                }
                this._ePreview.style.backgroundColor = this.value;
                this._cHue.style.top = (this._hsb[0] * 100).toFixed(0) + '%';
                this._cSB.style.left = (this._hsb[1] * 100).toFixed(0) + '%';
                this._cSB.style.top = (100 - this._hsb[2] * 100).toFixed(0) + '%';
                this._cAlpha.style.left = (this._alpha * 100).toFixed(0) + '%';
            };
            // gets the design panel that contains the mouse target
            ColorPicker.prototype._getTargetPanel = function (e) {
                var target = e.target;
                if (wijmo.contains(this._eSB, target))
                    return this._eSB;
                if (wijmo.contains(this._eHue, target))
                    return this._eHue;
                if (wijmo.contains(this._eAlpha, target))
                    return this._eAlpha;
                return null;
            };
            /**
             * Gets or sets the template used to instantiate @see:ColorPicker controls.
             */
            ColorPicker.controlTemplate = '<div style="position:relative;width:100%;height:100%">' +
                '<div style="float:left;width:50%;height:100%;box-sizing:border-box;padding:2px">' +
                '<div wj-part="div-pal">' +
                '<div style="float:left;width:10%;box-sizing:border-box;padding:2px">' +
                '<div style="background-color:black;width:100%">&nbsp;</div>' +
                '<div style="height:6px"></div>' +
                '</div>' +
                '</div>' +
                '<div wj-part="div-text" style="position:absolute;bottom:0px;display:none"></div>' +
                '</div>' +
                '<div style="float:left;width:50%;height:100%;box-sizing:border-box;padding:2px">' +
                '<div wj-part="div-sb" class="wj-colorbox" style="float:left;width:89%;height:89%">' +
                '<div style="position:absolute;width:100%;height:100%;background:linear-gradient(to right, white 0%,transparent 100%)"></div>' +
                '<div style="position:absolute;width:100%;height:100%;background:linear-gradient(to top, black 0%,transparent 100%)"></div>' +
                '</div>' +
                '<div style="float:left;width:1%;height:89%"></div>' +
                '<div style="float:left;width:10%;height:89%">' +
                '<div wj-part="div-hue" class="wj-colorbox"></div>' +
                '</div>' +
                '<div style="float:left;width:89%;height:1%"></div>' +
                '<div style="float:left;width:89%;height:10%">' +
                '<div style="width:100%;height:100%;background-image:url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAgAAAAICAIAAABLbSncAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAAAYdEVYdFNvZnR3YXJlAHBhaW50Lm5ldCA0LjAuM4zml1AAAAAcSURBVBhXY/iPBBYgAWpKQGkwgMqDAdUk/v8HAM7Mm6GatDUYAAAAAElFTkSuQmCC)">' +
                '<div wj-part="div-alpha" class="wj-colorbox"></div>' +
                '</div>' +
                '</div>' +
                '<div style="float:left;width:1%;height:10%"></div>' +
                '<div style="float:left;width:10%;height:10%">' +
                '<div wj-part="div-pv" class="wj-colorbox" style="position:static"></div>' +
                '</div>' +
                '</div>' +
                '</div>';
            ColorPicker._tplCursor = '<div style="position:absolute;left:50%;top:50%;width:7px;height:7px;transform:translate(-50%,-50%);border:2px solid #f0f0f0;border-radius:50px;box-shadow:0px 0px 4px 2px #0f0f0f"></div>';
            return ColorPicker;
        }(wijmo.Control));
        input.ColorPicker = ColorPicker;
    })(input = wijmo.input || (wijmo.input = {}));
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
    var input;
    (function (input) {
        'use strict';
        /**
         * The @see:ListBox control displays a list of items which may contain
         * plain text or HTML, and allows users to select items with the mouse or
         * the keyboard.
         *
         * Use the @see:ListBox.selectedIndex property to determine which item is
         * currently selected.
         *
         * You can populate a @see:ListBox using an array of strings or you can use
         * an array of objects, in which case the @see:ListBox.displayMemberPath
         * property determines which object property is displayed on the list.
         *
         * To display items that contain HTML rather than plain text, set the
         * @see:ListBox.isContentHtml property to true.
         *
         * The example below creates a @see:ListBox control and populates it using
         * a 'countries' array. The control updates its @see:ListBox.selectedIndex
         * and @see:ListBox.selectedItem properties as the user moves the selection.
         *
         * @fiddle:8HnLx
         */
        var ListBox = /** @class */ (function (_super) {
            __extends(ListBox, _super);
            /**
             * Initializes a new instance of the @see:ListBox class.
             *
             * @param element The DOM element that hosts the control, or a selector for the host element (e.g. '#theCtrl').
             * @param options The JavaScript object containing initialization data for the control.
             */
            function ListBox(element, options) {
                var _this = _super.call(this, element) || this;
                _this._pathDisplay = new wijmo.Binding(null);
                _this._pathValue = new wijmo.Binding(null);
                _this._pathChecked = new wijmo.Binding(null);
                _this._html = false;
                _this._checkedItems = [];
                // work variables
                _this._itemRole = 'option';
                _this._search = '';
                /**
                 * Occurs when the value of the @see:selectedIndex property changes.
                 */
                _this.selectedIndexChanged = new wijmo.Event();
                /**
                 * Occurs when the list of items changes.
                 */
                _this.itemsChanged = new wijmo.Event();
                /**
                 * Occurs before the list items are generated.
                 */
                _this.loadingItems = new wijmo.Event();
                /**
                 * Occurs after the list items have been generated.
                 */
                _this.loadedItems = new wijmo.Event();
                /**
                 * Occurs when the current item is checked or unchecked by the user.
                 *
                 * This event is raised when the @see:checkedMemberPath is set to the name of a
                 * property to add CheckBoxes to each item in the control.
                 *
                 * Use the @see:selectedItem property to retrieve the item that was checked or
                 * unchecked.
                 */
                _this.itemChecked = new wijmo.Event();
                /**
                 * Occurs when the value of the @see:checkedItems property changes.
                 */
                _this.checkedItemsChanged = new wijmo.Event();
                /**
                 * Occurs when an element representing a list item has been created.
                 *
                 * This event can be used to format list items for display. It is similar
                 * in purpose to the @see:itemFormatter property, but has the advantage
                 * of allowing multiple independent handlers.
                 */
                _this.formatItem = new wijmo.Event();
                // instantiate and apply template
                _this.applyTemplate('wj-control wj-listbox wj-content', null, null);
                // accessibility: https://www.w3.org/TR/wai-aria-1.1/#listbox
                var host = _this.hostElement;
                wijmo.setAttribute(host, 'role', 'listbox', true);
                // initializing from <select> tag
                if (_this._orgTag == 'SELECT') {
                    _this._initFromSelect(_this.hostElement);
                }
                // handle mouse and keyboard
                _this.addEventListener(host, 'click', _this._click.bind(_this));
                _this.addEventListener(host, 'keydown', _this._keydown.bind(_this));
                _this.addEventListener(host, 'keypress', _this._keypress.bind(_this));
                // prevent wheel from propagating to parent elements
                _this.addEventListener(host, 'wheel', function (e) {
                    if (host.scrollHeight > host.offsetHeight) {
                        if ((e.deltaY < 0 && host.scrollTop == 0) ||
                            (e.deltaY > 0 && host.scrollTop + host.offsetHeight >= host.scrollHeight)) {
                            e.preventDefault();
                            e.stopPropagation();
                        }
                    }
                });
                // save tabIndex to apply to items later
                _this._tabIndex = host.tabIndex;
                // initialize control options
                _this.initialize(options);
                return _this;
            }
            //--------------------------------------------------------------------------
            //#region ** overrides
            /**
             * Refreshes the list.
             */
            ListBox.prototype.refresh = function () {
                _super.prototype.refresh.call(this);
                // re-populate list unless we're binding directly to values and 
                // are showing CheckBoxes (in which case re-populating would 
                // reset the checked state of the items).
                if (this.displayMemberPath || !this.checkedMemberPath) {
                    this._populateList();
                }
            };
            Object.defineProperty(ListBox.prototype, "itemsSource", {
                //#endregion
                //--------------------------------------------------------------------------
                //#region ** object model
                /**
                 * Gets or sets the array or @see:ICollectionView object that contains the list items.
                 */
                get: function () {
                    return this._items;
                },
                set: function (value) {
                    if (this._items != value) {
                        // unbind current collection view
                        if (this._cv) {
                            this._cv.currentChanged.removeHandler(this._cvCurrentChanged, this);
                            this._cv.collectionChanged.removeHandler(this._cvCollectionChanged, this);
                            this._cv = null;
                        }
                        // save new data source and collection view
                        this._items = value;
                        this._cv = wijmo.asCollectionView(value);
                        // bind new collection view
                        if (this._cv != null) {
                            this._cv.currentChanged.addHandler(this._cvCurrentChanged, this);
                            this._cv.collectionChanged.addHandler(this._cvCollectionChanged, this);
                        }
                        // update the list
                        this._populateList();
                        this.onItemsChanged();
                        this.onSelectedIndexChanged();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(ListBox.prototype, "collectionView", {
                /**
                 * Gets the @see:ICollectionView object used as the item source.
                 */
                get: function () {
                    return this._cv;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(ListBox.prototype, "isContentHtml", {
                /**
                 * Gets or sets a value indicating whether items contain plain text or HTML.
                 */
                get: function () {
                    return this._html;
                },
                set: function (value) {
                    if (value != this._html) {
                        this._html = wijmo.asBoolean(value);
                        this._populateList();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(ListBox.prototype, "itemFormatter", {
                /**
                 * Gets or sets a function used to customize the values shown on the list.
                 * The function takes two arguments, the item index and the default text or html, and
                 * returns the new text or html to display.
                 *
                 * If the formatting function needs a scope (i.e. a meaningful 'this'
                 * value), then remember to set the filter using the 'bind' function to
                 * specify the 'this' object. For example:
                 *
                 * <pre>
                 *   listBox.itemFormatter = customItemFormatter.bind(this);
                 *   function customItemFormatter(index, content) {
                 *     if (this.makeItemBold(index)) {
                 *       content = '&lt;b&gt;' + content + '&lt;/b&gt;';
                 *     }
                 *     return content;
                 *   }
                 * </pre>
                 */
                get: function () {
                    return this._itemFormatter;
                },
                set: function (value) {
                    if (value != this._itemFormatter) {
                        this._itemFormatter = wijmo.asFunction(value);
                        this._populateList();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(ListBox.prototype, "displayMemberPath", {
                /**
                 * Gets or sets the name of the property to use as the visual representation of the items.
                 */
                get: function () {
                    return this._pathDisplay.path;
                },
                set: function (value) {
                    if (value != this.displayMemberPath) {
                        this._pathDisplay.path = wijmo.asString(value);
                        this._populateList();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(ListBox.prototype, "selectedValuePath", {
                /**
                 * Gets or sets the name of the property used to get the @see:selectedValue
                 * from the @see:selectedItem.
                 */
                get: function () {
                    return this._pathValue.path;
                },
                set: function (value) {
                    this._pathValue.path = wijmo.asString(value);
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(ListBox.prototype, "checkedMemberPath", {
                /**
                 * Gets or sets the name of the property used to control the CheckBoxes
                 * placed next to each item.
                 *
                 * Use this property to create multi-select LisBoxes.
                 * When an item is checked or unchecked, the control raises the @see:itemChecked event.
                 * Use the @see:selectedItem property to retrieve the item that was checked or unchecked,
                 * or use the @see:checkedItems property to retrieve the list of items that are currently
                 * checked.
                 */
                get: function () {
                    return this._pathChecked.path;
                },
                set: function (value) {
                    if (value != this.checkedMemberPath) {
                        this._pathChecked.path = wijmo.asString(value);
                        this._populateList();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(ListBox.prototype, "itemRole", {
                /**
                 * Gets or sets the value or the "role" attribute added to the
                 * list items. The default value for this property is "option".
                 */
                get: function () {
                    return this._itemRole;
                },
                set: function (value) {
                    if (value != this.itemRole) {
                        this._itemRole = wijmo.asString(value);
                        this._populateList();
                    }
                },
                enumerable: true,
                configurable: true
            });
            /**
             * Gets the string displayed for the item at a given index.
             *
             * The string may be plain text or HTML, depending on the setting
             * of the @see:isContentHtml property.
             *
             * @param index The index of the item.
             */
            ListBox.prototype.getDisplayValue = function (index) {
                // get the text or html
                var item = null;
                if (index > -1 && wijmo.hasItems(this._cv)) {
                    item = this._cv.items[index];
                    if (this.displayMemberPath) {
                        item = this._pathDisplay.getValue(item);
                    }
                }
                var text = item != null ? item.toString() : '';
                // allow caller to override/modify the text or html
                if (this.itemFormatter) {
                    text = this.itemFormatter(index, text);
                }
                // return the result
                return text;
            };
            /**
             * Gets the text displayed for the item at a given index (as plain text).
             *
             * @param index The index of the item.
             */
            ListBox.prototype.getDisplayText = function (index) {
                var children = this.hostElement.children, item = index > -1 && index < children.length
                    ? children[index]
                    : null;
                return item != null ? item.textContent : '';
            };
            /**
             * Gets a value that determines whether the item at a given index is enabled.
             *
             * @param index The index of the item.
             */
            ListBox.prototype.isItemEnabled = function (index) {
                // skip empty items
                if (!this.getDisplayText(index)) {
                    return false;
                }
                // skip disabled items
                var item = this.hostElement.children[index];
                if (!item ||
                    item.hasAttribute('disabled') ||
                    wijmo.hasClass(item, 'wj-state-disabled')) {
                    return false;
                }
                // seems OK
                return true;
            };
            Object.defineProperty(ListBox.prototype, "selectedIndex", {
                /**
                 * Gets or sets the index of the currently selected item.
                 */
                get: function () {
                    return this._cv ? this._cv.currentPosition : -1;
                },
                set: function (value) {
                    if (this._cv) {
                        this._cv.moveCurrentToPosition(wijmo.asNumber(value));
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(ListBox.prototype, "selectedItem", {
                /**
                 * Gets or sets the item that is currently selected.
                 */
                get: function () {
                    return this._cv ? this._cv.currentItem : null;
                },
                set: function (value) {
                    if (this._cv) {
                        this._cv.moveCurrentTo(value);
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(ListBox.prototype, "selectedValue", {
                /**
                 * Gets or sets the value of the @see:selectedItem obtained using the @see:selectedValuePath.
                 */
                get: function () {
                    var item = this.selectedItem;
                    if (item && this.selectedValuePath) {
                        item = this._pathValue.getValue(item);
                    }
                    return item;
                },
                set: function (value) {
                    var path = this.selectedValuePath, index = -1;
                    if (this._cv) {
                        for (var i = 0; i < this._cv.items.length; i++) {
                            var item = this._cv.items[i], itemValue = path ? this._pathValue.getValue(item) : item;
                            if (itemValue === value) {
                                index = i;
                                break;
                            }
                        }
                        this.selectedIndex = index;
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(ListBox.prototype, "maxHeight", {
                /**
                 * Gets or sets the maximum height of the list.
                 */
                get: function () {
                    var host = this.hostElement;
                    return host ? parseFloat(host.style.maxHeight) : null;
                },
                set: function (value) {
                    var host = this.hostElement;
                    if (host) {
                        host.style.maxHeight = wijmo.asNumber(value) + 'px';
                    }
                },
                enumerable: true,
                configurable: true
            });
            /**
             * Highlights the selected item and scrolls it into view.
             */
            ListBox.prototype.showSelection = function () {
                var index = this.selectedIndex, host = this.hostElement, children = host.children, e;
                // highlight
                for (var i = 0; i < children.length; i++) {
                    e = children[i];
                    wijmo.toggleClass(e, 'wj-state-selected', i == index);
                    wijmo.setAttribute(e, 'tabindex', i == index ? this._tabIndex : -1);
                }
                // scroll into view
                if (index > -1 && index < children.length) {
                    e = children[index];
                    var rco = e.getBoundingClientRect(), rcc = host.getBoundingClientRect();
                    if (rco.bottom > rcc.bottom) {
                        host.scrollTop += rco.bottom - rcc.bottom;
                    }
                    else if (rco.top < rcc.top) {
                        host.scrollTop -= rcc.top - rco.top;
                    }
                }
                // make sure the focus is within the selected element (TFS 135278)
                if (index > -1 && this.containsFocus()) {
                    e = children[index];
                    if (e instanceof HTMLElement && !wijmo.contains(e, wijmo.getActiveElement())) {
                        e.focus();
                    }
                }
                // update control's tabindex as well
                wijmo.setAttribute(host, 'tabindex', index < 0 ? this._tabIndex : -1);
            };
            /**
             * Gets the checked state of an item on the list.
             *
             * This method is applicable only on multi-select ListBoxes
             * (see the @see:checkedMemberPath property).
             *
             * @param index Item index.
             */
            ListBox.prototype.getItemChecked = function (index) {
                var item = this._cv.items[index];
                if (wijmo.isObject(item) && this.checkedMemberPath) {
                    return this._pathChecked.getValue(item);
                }
                var cb = this._getCheckbox(index);
                return cb ? cb.checked : false;
            };
            /**
             * Sets the checked state of an item on the list.
             *
             * This method is applicable only on multi-select ListBoxes
             * (see the @see:checkedMemberPath property).
             *
             * @param index Item index.
             * @param checked Item's new checked state.
             */
            ListBox.prototype.setItemChecked = function (index, checked) {
                this._setItemChecked(index, checked, true);
            };
            /**
             * Toggles the checked state of an item on the list.
             * This method is applicable only to multi-select ListBoxes
             * (see the @see:checkedMemberPath property).
             *
             * @param index Item index.
             */
            ListBox.prototype.toggleItemChecked = function (index) {
                this.setItemChecked(index, !this.getItemChecked(index));
            };
            Object.defineProperty(ListBox.prototype, "checkedItems", {
                /**
                 * Gets or sets an array containing the items that are currently checked.
                 */
                get: function () {
                    this._checkedItems.splice(0, this._checkedItems.length);
                    if (this._cv) {
                        for (var i = 0; i < this._cv.items.length; i++) {
                            if (this.getItemChecked(i)) {
                                this._checkedItems.push(this._cv.items[i]);
                            }
                        }
                    }
                    return this._checkedItems;
                },
                set: function (value) {
                    var cv = this._cv, host = this.hostElement, arr = wijmo.asArray(value, false);
                    if (cv && arr) {
                        var pos = cv.currentPosition, top_1 = host.scrollTop;
                        for (var i = 0; i < cv.items.length; i++) {
                            var item = cv.items[i];
                            this._setItemChecked(i, arr.indexOf(item) > -1, false);
                        }
                        cv.moveCurrentToPosition(pos);
                        host.scrollTop = top_1;
                        this.onCheckedItemsChanged();
                    }
                },
                enumerable: true,
                configurable: true
            });
            /**
             * Raises the @see:selectedIndexChanged event.
             */
            ListBox.prototype.onSelectedIndexChanged = function (e) {
                this.selectedIndexChanged.raise(this, e);
            };
            /**
             * Raises the @see:itemsChanged event.
             */
            ListBox.prototype.onItemsChanged = function (e) {
                this.itemsChanged.raise(this, e);
            };
            /**
             * Raises the @see:loadingItems event.
             */
            ListBox.prototype.onLoadingItems = function (e) {
                this.loadingItems.raise(this, e);
            };
            /**
             * Raises the @see:loadedItems event.
             */
            ListBox.prototype.onLoadedItems = function (e) {
                this.loadedItems.raise(this, e);
            };
            /**
             * Raises the @see:itemChecked event.
             */
            ListBox.prototype.onItemChecked = function (e) {
                this.itemChecked.raise(this, e);
            };
            /**
             * Raises the @see:checkedItemsChanged event.
             */
            ListBox.prototype.onCheckedItemsChanged = function (e) {
                this.checkedItemsChanged.raise(this, e);
            };
            /**
             * Raises the @see:formatItem event.
             *
             * @param e @see:FormatItemEventArgs that contains the event data.
             */
            ListBox.prototype.onFormatItem = function (e) {
                this.formatItem.raise(this, e);
            };
            //#endregion
            //--------------------------------------------------------------------------
            //#region ** implementation
            // sets the checked state of an item on the list
            ListBox.prototype._setItemChecked = function (index, checked, notify) {
                if (notify === void 0) { notify = true; }
                // update data item
                var item = this._cv.items[index];
                if (wijmo.isObject(item)) {
                    var ecv = wijmo.tryCast(this._cv, 'IEditableCollectionView');
                    if (this._pathChecked.getValue(item) != checked) {
                        this._checking = true;
                        if (ecv) {
                            ecv.editItem(item);
                            this._pathChecked.setValue(item, checked);
                            ecv.commitEdit();
                        }
                        else {
                            this._pathChecked.setValue(item, checked);
                            this._cv.refresh();
                        }
                        this._checking = false;
                    }
                }
                // update checkbox value and checked pseudo-class
                var cb = this._getCheckbox(index);
                if (cb) {
                    cb.checked = checked;
                    var e = wijmo.closest(cb, '.wj-listbox-item');
                    if (e) {
                        wijmo.toggleClass(e, 'wj-state-checked', checked);
                    }
                }
                // fire events
                if (notify) {
                    this.onItemChecked();
                    this.onCheckedItemsChanged();
                }
            };
            // handle changes to the data source
            ListBox.prototype._cvCollectionChanged = function (sender, e) {
                if (!this._checking) {
                    this._populateList();
                    this.onItemsChanged();
                }
            };
            ListBox.prototype._cvCurrentChanged = function (sender, e) {
                if (!this._checking) {
                    this.showSelection();
                    this.onSelectedIndexChanged();
                }
            };
            // populate the list from the current itemsSource
            ListBox.prototype._populateList = function () {
                // get ready to populate
                var host = this.hostElement;
                if (host) {
                    // remember if we have focus
                    var focus_1 = this.containsFocus();
                    // fire event so user can clean up any current items
                    this.onLoadingItems();
                    // populate list
                    host.innerHTML = '';
                    if (this._cv) {
                        // add items to document fragment
                        var frag = document.createDocumentFragment();
                        for (var i = 0; i < this._cv.items.length; i++) {
                            // get item text
                            var text = this.getDisplayValue(i);
                            if (this._html != true) {
                                text = wijmo.escapeHtml(text);
                            }
                            // add checkbox (with tabindex -1 for accessibility: TFS 135857?)
                            var isChecked = false;
                            if (this.checkedMemberPath) {
                                isChecked = this._pathChecked.getValue(this._cv.items[i]);
                                text = '<label><input tabindex="-1" type="checkbox"' +
                                    (isChecked ? ' checked' : '') + '> ' + text + '</label>';
                            }
                            // build item
                            var item = document.createElement('div'), clsName = 'wj-listbox-item';
                            item.innerHTML = text;
                            if (wijmo.hasClass(item.firstChild, 'wj-separator')) {
                                clsName += ' wj-separator';
                            }
                            if (isChecked) {
                                clsName += ' wj-state-checked';
                            }
                            item.className = clsName;
                            // set the item role
                            wijmo.setAttribute(item, 'role', this.itemRole ? this.itemRole : null);
                            // allow custom formatting
                            if (this.formatItem.hasHandlers) {
                                var e = new FormatItemEventArgs(i, this._cv.items[i], item);
                                this.onFormatItem(e);
                            }
                            // add item to list
                            frag.appendChild(item);
                        }
                        // move elements to host all at once (doesn't seem to be faster, but...)
                        host.appendChild(frag);
                    }
                    // make sure the list is not totally empty
                    // or min-height/max-height won't work properly in IE/Edge
                    if (host.children.length == 0) {
                        host.appendChild(document.createElement('div'));
                    }
                    // restore focus
                    if (focus_1 && !this.containsFocus()) {
                        this.focus();
                    }
                    // scroll selection into view
                    this.showSelection();
                    // fire event so user can hook up to items
                    this.onLoadedItems();
                }
            };
            // click to select elements
            ListBox.prototype._click = function (e) {
                if (!e.defaultPrevented) {
                    // select the item that was clicked
                    var children = this.hostElement.children;
                    for (var index_1 = 0; index_1 < children.length; index_1++) {
                        if (wijmo.contains(children[index_1], e.target)) {
                            this.selectedIndex = index_1;
                            break;
                        }
                    }
                    // handle checkboxes
                    var index = this.selectedIndex;
                    if (this.checkedMemberPath && index > -1) {
                        var cb = this._getCheckbox(index);
                        if (cb == e.target) {
                            var item = children[index];
                            item.focus(); // take focus from the checkbox (Firefox, TFS 135857)
                            this.setItemChecked(index, cb.checked);
                        }
                    }
                }
            };
            // handle keydown (cursor keys)
            ListBox.prototype._keydown = function (e) {
                var index = this.selectedIndex, host = this.hostElement, children = host.children;
                // honor defaultPrevented
                if (e.defaultPrevented)
                    return;
                // ctrl+A toggles checkboxes
                if (e.keyCode == 65 && (e.ctrlKey || e.metaKey)) {
                    if (this.checkedMemberPath && wijmo.hasItems(this.collectionView)) {
                        this.checkedItems = this.getItemChecked(0) ? [] : this.collectionView.items;
                        e.preventDefault();
                        return;
                    }
                }
                // not interested in other meta keys
                if (e.ctrlKey || e.shiftKey || e.altKey || e.metaKey)
                    return;
                // handle the event
                switch (e.keyCode) {
                    case wijmo.Key.Down:
                        e.preventDefault();
                        this._selectNext();
                        break;
                    case wijmo.Key.Up:
                        e.preventDefault();
                        this._selectPrev();
                        break;
                    case wijmo.Key.Home:
                        e.preventDefault();
                        this._selectFirst();
                        break;
                    case wijmo.Key.End:
                        e.preventDefault();
                        this._selectLast();
                        break;
                    case wijmo.Key.PageDown:
                        e.preventDefault();
                        this._selectNextPage();
                        break;
                    case wijmo.Key.PageUp:
                        e.preventDefault();
                        this._selectPrevPage();
                        break;
                    case wijmo.Key.Space:
                        if (this.checkedMemberPath && index > -1) {
                            var cb = this._getCheckbox(index);
                            if (cb && this.isItemEnabled(index)) {
                                this.setItemChecked(index, !cb.checked);
                                e.preventDefault();
                            }
                        }
                        break;
                }
            };
            // handle keypress (select/search)
            ListBox.prototype._keypress = function (e) {
                var _this = this;
                // honor defaultPrevented
                if (e.defaultPrevented)
                    return;
                // don't interfere with inner input elements (TFS 132081)
                if (e.target instanceof HTMLInputElement)
                    return;
                // auto search
                if (e.charCode > 32 || (e.charCode == 32 && this._search)) {
                    e.preventDefault();
                    // update search string
                    this._search += String.fromCharCode(e.charCode).toLowerCase();
                    //console.log('looking for ' + this._search);
                    if (this._toSearch) {
                        clearTimeout(this._toSearch);
                    }
                    this._toSearch = setTimeout(function () {
                        _this._toSearch = null;
                        _this._search = '';
                    }, ListBox._AUTOSEARCH_DELAY);
                    // perform search
                    var index = this._findNext(); // multi-char search
                    if (index < 0 && this._search.length > 1) {
                        this._search = this._search[this._search.length - 1];
                        index = this._findNext(); // single-char search
                    }
                    if (index > -1) {
                        this.selectedIndex = index;
                    }
                }
            };
            // move the selection to the next enabled item
            ListBox.prototype._selectNext = function () {
                var len = this.hostElement.children.length;
                for (var i = this.selectedIndex + 1; i < len; i++) {
                    if (this.isItemEnabled(i)) {
                        this.selectedIndex = i;
                        return true;
                    }
                }
                return false;
            };
            // move the selection to the previous enabled item
            ListBox.prototype._selectPrev = function () {
                for (var i = this.selectedIndex - 1; i >= 0; i--) {
                    if (this.isItemEnabled(i)) {
                        this.selectedIndex = i;
                        return true;
                    }
                }
                return false;
            };
            // select the first enabled item
            ListBox.prototype._selectFirst = function () {
                var len = this.hostElement.children.length;
                for (var i = 0; i < len; i++) {
                    if (this.isItemEnabled(i)) {
                        this.selectedIndex = i;
                        return true;
                    }
                }
                return false;
            };
            // select the last enabled item
            ListBox.prototype._selectLast = function () {
                var len = this.hostElement.children.length;
                for (var i = len - 1; i >= 0; i--) {
                    if (this.isItemEnabled(i)) {
                        this.selectedIndex = i;
                        return true;
                    }
                }
                return false;
            };
            // select the first valid item in the next page
            ListBox.prototype._selectNextPage = function () {
                var host = this.hostElement, height = host.offsetHeight, children = host.children, offset = 0;
                for (var i = this.selectedIndex + 1; i < this._cv.items.length; i++) {
                    var itemHeight = children[i].scrollHeight;
                    if (offset + itemHeight > height && this.isItemEnabled(i)) {
                        this.selectedIndex = i;
                        return true;
                    }
                    offset += itemHeight;
                }
                return this._selectLast();
            };
            // select the first valid item in the previous page
            ListBox.prototype._selectPrevPage = function () {
                var host = this.hostElement, height = host.offsetHeight, children = host.children, offset = 0;
                for (var i = this.selectedIndex - 1; i > 0; i--) {
                    var itemHeight = children[i].scrollHeight;
                    if (offset + itemHeight > height && this.isItemEnabled(i)) {
                        this.selectedIndex = i;
                        return true;
                    }
                    offset += itemHeight;
                }
                return this._selectFirst();
            };
            // look for the '_search' string from the current position
            ListBox.prototype._findNext = function () {
                if (this.hostElement) {
                    var cnt = this.hostElement.childElementCount, start = this.selectedIndex;
                    // start searching from current or next item
                    if (start < 0 || this._search.length == 1) {
                        start++;
                    }
                    // search through the items (with wrapping)
                    for (var off = 0; off < cnt; off++) {
                        var index = (start + off) % cnt, txt = this.getDisplayText(index).trim().toLowerCase();
                        if (txt.indexOf(this._search) == 0 && this.isItemEnabled(index)) {
                            //console.log('match at ' + index);
                            return index;
                        }
                    }
                }
                // not found
                return -1;
            };
            // gets the checkbox element in a given ListBox item
            ListBox.prototype._getCheckbox = function (index) {
                var host = this.hostElement;
                return (host && index > -1 && index < host.children.length)
                    ? host.children[index].querySelector('input[type=checkbox]')
                    : null;
            };
            // build collectionView from OPTION elements items in a SELECT element
            // this is used by the ComboBox
            ListBox.prototype._initFromSelect = function (hostElement) {
                var children = hostElement.children, items = [], selIndex = -1;
                for (var i = 0; i < children.length; i++) {
                    var child = children[i];
                    if (child.tagName == 'OPTION') {
                        // keep track of selected item
                        if (child.hasAttribute('selected')) {
                            selIndex = items.length;
                        }
                        // add option to collectionView
                        if (child.innerHTML) {
                            items.push({
                                hdr: child.innerHTML,
                                val: child.getAttribute('value'),
                                cmdParam: child.getAttribute('cmd-param')
                            });
                        }
                        else {
                            items.push({
                                hdr: '<div class="wj-separator"/>'
                            });
                        }
                        // remove child from host
                        hostElement.removeChild(child);
                        i--;
                    }
                }
                // apply items to control
                if (items) {
                    this.displayMemberPath = 'hdr';
                    this.selectedValuePath = 'val';
                    this.itemsSource = items;
                    this.selectedIndex = selIndex;
                }
            };
            ListBox._AUTOSEARCH_DELAY = 600;
            return ListBox;
        }(wijmo.Control));
        input.ListBox = ListBox;
        /**
         * Provides arguments for the @see:ListBox.formatItem event.
         */
        var FormatItemEventArgs = /** @class */ (function (_super) {
            __extends(FormatItemEventArgs, _super);
            /**
             * Initializes a new instance of the @see:FormatItemEventArgs class.
             *
             * @param index Index of the item being formatted.
             * @param data Data item being formatted.
             * @param item Element that represents the list item to be formatted.
             */
            function FormatItemEventArgs(index, data, item) {
                var _this = _super.call(this) || this;
                _this._index = wijmo.asNumber(index);
                _this._data = data;
                _this._item = wijmo.asType(item, HTMLElement);
                return _this;
            }
            Object.defineProperty(FormatItemEventArgs.prototype, "index", {
                /**
                 * Gets the index of the data item in the list.
                 */
                get: function () {
                    return this._index;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(FormatItemEventArgs.prototype, "data", {
                /**
                 * Gets the data item being formatted.
                 */
                get: function () {
                    return this._data;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(FormatItemEventArgs.prototype, "item", {
                /**
                 * Gets a reference to the element that represents the list item to be formatted.
                 */
                get: function () {
                    return this._item;
                },
                enumerable: true,
                configurable: true
            });
            return FormatItemEventArgs;
        }(wijmo.EventArgs));
        input.FormatItemEventArgs = FormatItemEventArgs;
    })(input = wijmo.input || (wijmo.input = {}));
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
    var input;
    (function (input) {
        'use strict';
        /**
         * The @see:ComboBox control allows users to pick strings from lists.
         *
         * The control automatically completes entries as the user types, and allows users
         * to show a drop-down list with the items available.
         *
         * Use the @see:ComboBox.itemsSource property to populate the list of options.
         * The items may be strings or objects. If the items are objects, use the
         * @see:ComboBox.displayMemberPath to define which property of the items will be
         * displayed in the list and use the @see:ComboBox.selectedValuePath property to
         * define which property of the items will be used to set the combo's
         * @see:ComboBox.selectedValue property.
         *
         * Use the @see:ComboBox.selectedIndex or the @see:ComboBox.text properties to
         * determine which item is currently selected.
         *
         * The @see:ComboBox.isEditable property determines whether users can enter values
         * that are not present in the list.
         *
         * The example below creates a @see:ComboBox control and populates it with a list
         * of countries. The @see:ComboBox searches for the country as the user types.
         * The @see:ComboBox.isEditable property is set to false, so the user is forced to
         * select one of the items in the list.
         *
         * The example also shows how to create and populate a @see:ComboBox using
         * an HTML <b>&lt;select&gt;</b> element with <b>&lt;option&gt;</b> child
         * elements.
         *
         * @fiddle:8HnLx
         */
        var ComboBox = /** @class */ (function (_super) {
            __extends(ComboBox, _super);
            /**
             * Initializes a new instance of the @see:ComboBox class.
             *
             * @param element The DOM element that hosts the control, or a selector for the host element (e.g. '#theCtrl').
             * @param options The JavaScript object containing initialization data for the control.
             */
            function ComboBox(element, options) {
                var _this = _super.call(this, element) || this;
                // property storage
                _this._editable = false;
                // private stuff
                _this._delKey = 0;
                _this._composing = false;
                _this._pathHdr = new wijmo.Binding(null);
                _this._bsCollapse = true;
                /**
                 * Occurs when the value of the @see:itemsSource property changes.
                 */
                _this.itemsSourceChanged = new wijmo.Event();
                /**
                 * Occurs when the value of the @see:selectedIndex property changes.
                 */
                _this.selectedIndexChanged = new wijmo.Event();
                // add wj-combobox class to host element
                var host = _this.hostElement;
                wijmo.addClass(host, 'wj-combobox');
                // accessibility: 
                // https://www.w3.org/TR/wai-aria-1.1/#combobox
                // http://oaa-accessibility.org/examples/role/77/
                var tbx = _this._tbx, ddId = wijmo.getUniqueId(host.id + '_dropdown');
                _this.dropDown.id = ddId;
                wijmo.setAttribute(tbx, 'role', 'combobox');
                wijmo.setAttribute(tbx, 'aria-autocomplete', 'both');
                wijmo.setAttribute(tbx, 'aria-owns', ddId);
                wijmo.setAttribute(_this._btn, 'aria-controls', ddId);
                wijmo.setAttribute(_this.dropDown, 'aria-expanded', false);
                // disable auto-expand by default
                _this.autoExpandSelection = false;
                // handle IME
                _this.addEventListener(_this._tbx, 'compositionstart', function () {
                    _this._composing = true;
                });
                _this.addEventListener(_this._tbx, 'compositionend', function () {
                    _this._composing = false;
                    setTimeout(function () {
                        _this._setText(_this.text, true);
                    });
                });
                // use wheel to scroll through the items
                _this.addEventListener(host, 'wheel', function (e) {
                    if (!e.defaultPrevented && !_this.isDroppedDown && !_this.isReadOnly && _this.containsFocus()) {
                        if (_this.selectedIndex > -1) {
                            var step = wijmo.clamp(-e.deltaY, -1, +1);
                            _this.selectedIndex = wijmo.clamp(_this.selectedIndex - step, 0, _this.collectionView.items.length - 1);
                            e.preventDefault();
                        }
                    }
                });
                // initializing from <select> tag
                if (_this._orgTag == 'SELECT') {
                    _this._lbx._initFromSelect(host);
                }
                // refresh text after CollectionView updates
                _this._lbx.loadedItems.addHandler(function () {
                    if (_this.selectedIndex > -1) {
                        _this.selectedIndex = _this._lbx.selectedIndex;
                    }
                });
                // initialize control options
                _this.isRequired = true;
                _this.initialize(options);
                return _this;
            }
            Object.defineProperty(ComboBox.prototype, "itemsSource", {
                //--------------------------------------------------------------------------
                //#region ** object model
                /**
                 * Gets or sets the array or @see:ICollectionView object that contains the items to select from.
                 */
                get: function () {
                    return this._lbx.itemsSource;
                },
                set: function (value) {
                    if (this._lbx.itemsSource != value) {
                        this._lbx.itemsSource = value;
                        this.onItemsSourceChanged();
                    }
                    this._updateBtn();
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(ComboBox.prototype, "collectionView", {
                /**
                 * Gets the @see:ICollectionView object used as the item source.
                 */
                get: function () {
                    return this._lbx.collectionView;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(ComboBox.prototype, "displayMemberPath", {
                /**
                 * Gets or sets the name of the property to use as the visual representation of the items.
                 */
                get: function () {
                    return this._lbx.displayMemberPath;
                },
                set: function (value) {
                    this._lbx.displayMemberPath = value;
                    var text = this.getDisplayText();
                    if (this.text != text) {
                        this._setText(text, true);
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(ComboBox.prototype, "headerPath", {
                /**
                 * Gets or sets the name of a property to use for getting the value displayed in the
                 * control's input element.
                 *
                 * The default value for this property is null, which causes the control to display
                 * the same content in the input element as in the selected item of the drop-down list.
                 *
                 * Use this property if you want to de-couple the value shown in the input element
                 * from the values shown in the drop-down list. For example, the input element could
                 * show an item's name and the drop-down list could show additional detail.
                 */
                get: function () {
                    return this._pathHdr.path;
                },
                set: function (value) {
                    this._pathHdr.path = wijmo.asString(value);
                    var text = this.getDisplayText();
                    if (this.text != text) {
                        this._setText(text, true);
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(ComboBox.prototype, "selectedValuePath", {
                /**
                 * Gets or sets the name of the property used to get the
                 * @see:selectedValue from the @see:selectedItem.
                 */
                get: function () {
                    return this._lbx.selectedValuePath;
                },
                set: function (value) {
                    this._lbx.selectedValuePath = value;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(ComboBox.prototype, "isContentHtml", {
                /**
                 * Gets or sets a value indicating whether the drop-down list displays
                 * items as plain text or as HTML.
                 */
                get: function () {
                    return this._lbx.isContentHtml;
                },
                set: function (value) {
                    if (value != this.isContentHtml) {
                        this._lbx.isContentHtml = wijmo.asBoolean(value);
                        var text = this.getDisplayText();
                        if (this.text != text) {
                            this._setText(text, true);
                        }
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(ComboBox.prototype, "itemFormatter", {
                /**
                 * Gets or sets a function used to customize the values shown in the
                 * drop-down list.
                 * The function takes two arguments, the item index and the default
                 * text or html, and returns the new text or html to display.
                 *
                 * If the formatting function needs a scope (i.e. a meaningful 'this'
                 * value), then remember to set the filter using the 'bind' function to
                 * specify the 'this' object. For example:
                 *
                 * <pre>
                 *   comboBox.itemFormatter = customItemFormatter.bind(this);
                 *   function customItemFormatter(index, content) {
                 *     if (this.makeItemBold(index)) {
                 *       content = '&lt;b&gt;' + content + '&lt;/b&gt;';
                 *     }
                 *     return content;
                 *   }
                 * </pre>
                 */
                get: function () {
                    return this._lbx.itemFormatter;
                },
                set: function (value) {
                    this._lbx.itemFormatter = wijmo.asFunction(value); // update drop-down
                    this.selectedIndex = this._lbx.selectedIndex; // update control
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(ComboBox.prototype, "formatItem", {
                /**
                 * Event that fires when items in the drop-down list are created.
                 *
                 * You can use this event to modify the HTML in the list items.
                 * For details, see the @see:ListBox.formatItem event.
                 */
                get: function () {
                    return this.listBox.formatItem;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(ComboBox.prototype, "selectedIndex", {
                /**
                 * Gets or sets the index of the currently selected item in the drop-down list.
                 */
                get: function () {
                    return this._lbx.selectedIndex;
                },
                set: function (value) {
                    if (value != this.selectedIndex) {
                        this._lbx.selectedIndex = value;
                    }
                    value = this.selectedIndex; // TFS 214555
                    var text = this.getDisplayText(value);
                    if (this.text != text) {
                        this._setText(text, true);
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(ComboBox.prototype, "selectedItem", {
                /**
                 * Gets or sets the item that is currently selected in the drop-down list.
                 */
                get: function () {
                    return this._lbx.selectedItem;
                },
                set: function (value) {
                    this._lbx.selectedItem = value;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(ComboBox.prototype, "selectedValue", {
                /**
                 * Gets or sets the value of the @see:selectedItem, obtained using the @see:selectedValuePath.
                 */
                get: function () {
                    return this._lbx.selectedValue;
                },
                set: function (value) {
                    this._lbx.selectedValue = value;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(ComboBox.prototype, "isEditable", {
                /**
                 * Gets or sets a value that determines whether the content of the
                 * input element should be restricted to items in the @see:itemsSource
                 * collection.
                 */
                get: function () {
                    return this._editable;
                },
                set: function (value) {
                    this._editable = wijmo.asBoolean(value);
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(ComboBox.prototype, "maxDropDownHeight", {
                /**
                 * Gets or sets the maximum height of the drop-down list.
                 */
                get: function () {
                    return this._lbx.maxHeight;
                },
                set: function (value) {
                    this._lbx.maxHeight = wijmo.asNumber(value);
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(ComboBox.prototype, "maxDropDownWidth", {
                /**
                 * Gets or sets the maximum width of the drop-down list.
                 *
                 * The width of the drop-down list is also limited by the width of
                 * the control itself (that value represents the drop-down's minimum width).
                 */
                get: function () {
                    var lbx = this._dropDown;
                    return parseInt(lbx.style.maxWidth);
                },
                set: function (value) {
                    var lbx = this._dropDown;
                    lbx.style.maxWidth = wijmo.asNumber(value) + 'px';
                },
                enumerable: true,
                configurable: true
            });
            /**
             * Gets the string displayed in the input element for the item at a
             * given index (always plain text).
             *
             * @param index The index of the item to retrieve the text for.
             */
            ComboBox.prototype.getDisplayText = function (index) {
                if (index === void 0) { index = this.selectedIndex; }
                // get display text directly from the headerPath if that was specified
                if (this.headerPath && index > -1 && wijmo.hasItems(this.collectionView)) {
                    var item = this.collectionView.items[index], text = item ? this._pathHdr.getValue(item) : null;
                    text = text != null ? text.toString() : '';
                    if (this.isContentHtml) {
                        if (!this._cvt) {
                            this._cvt = document.createElement('div');
                        }
                        this._cvt.innerHTML = text;
                        text = this._cvt.textContent;
                    }
                    return text.trim();
                }
                // headerPath not specified, get text straight from the ListBox
                return this._lbx.getDisplayText(index).trim();
            };
            /**
             * Gets the index of the first item that matches a given string.
             *
             * @param text The text to search for.
             * @param fullMatch Whether to look for a full match or just the start of the string.
             * @return The index of the item, or -1 if not found.
             */
            ComboBox.prototype.indexOf = function (text, fullMatch) {
                var cv = this.collectionView;
                if (wijmo.hasItems(cv) && text != null) {
                    // preserve the current selection if possible 
                    // http://wijmo.com/topic/wj-combo-box-bug/#post-76154
                    var index = this.selectedIndex;
                    if (fullMatch && text == this.getDisplayText(index)) {
                        return index;
                    }
                    // scan the list from the start
                    text = text.toString().toLowerCase();
                    for (var i = 0; i < cv.items.length; i++) {
                        if (this.listBox.isItemEnabled(i)) {
                            var t = this.getDisplayText(i).toLowerCase();
                            if (fullMatch) {
                                if (t == text) {
                                    return i;
                                }
                            }
                            else {
                                if (text && t.indexOf(text) == 0) {
                                    return i;
                                }
                            }
                        }
                    }
                }
                // not found
                return -1;
            };
            Object.defineProperty(ComboBox.prototype, "listBox", {
                /**
                 * Gets the @see:ListBox control shown in the drop-down.
                 */
                get: function () {
                    return this._lbx;
                },
                enumerable: true,
                configurable: true
            });
            /**
             * Raises the @see:itemsSourceChanged event.
             */
            ComboBox.prototype.onItemsSourceChanged = function (e) {
                this.itemsSourceChanged.raise(this, e);
            };
            /**
             * Raises the @see:selectedIndexChanged event.
             */
            ComboBox.prototype.onSelectedIndexChanged = function (e) {
                this._updateBtn();
                this.selectedIndexChanged.raise(this, e);
            };
            //#endregion ** object model
            //--------------------------------------------------------------------------
            //#region ** overrides
            // update the content when refreshing
            ComboBox.prototype.refresh = function (fullUpdate) {
                _super.prototype.refresh.call(this, fullUpdate);
                if (wijmo.hasItems(this.collectionView)) {
                    this._lbx.refresh();
                    if (this.selectedIndex > -1) {
                        this.selectedIndex = this._lbx.selectedIndex;
                    }
                }
            };
            // prevent empty values if editable and required
            ComboBox.prototype.onLostFocus = function (e) {
                // Safari does not finish composition on blur (TFS 236810)
                if (this._composing) {
                    this._composing = false;
                    this._setText(this.text, true);
                }
                // prevent empty values if editable and required (TFS 138025)
                if (this.isEditable && this.isRequired && !this.text) {
                    if (wijmo.hasItems(this.collectionView)) {
                        this.selectedIndex = 0;
                    }
                }
                // raise event as usual
                _super.prototype.onLostFocus.call(this, e);
            };
            // prevent dropping down with no items
            ComboBox.prototype.onIsDroppedDownChanging = function (e) {
                if (!this.isDroppedDown && !wijmo.hasItems(this.collectionView)) {
                    e.cancel = true;
                    return false; // TFS 252531
                }
                return _super.prototype.onIsDroppedDownChanging.call(this, e);
            };
            // show current selection when dropping down
            ComboBox.prototype.onIsDroppedDownChanged = function (e) {
                _super.prototype.onIsDroppedDownChanged.call(this, e);
                if (this.isDroppedDown) {
                    this._lbx.showSelection();
                    if (!this.isTouching) {
                        this.selectAll();
                    }
                }
                wijmo.setAttribute(this.dropDown, 'aria-expanded', this.isDroppedDown);
            };
            // update button visibility and state
            ComboBox.prototype._updateBtn = function () {
                var cv = this.collectionView;
                // show button if the 'showButton' property is true and we have an itemsSource
                this._btn.style.display = (this._showBtn && cv != null) ? '' : 'none';
                // enable the button if the itemsSource is not empty
                wijmo.enable(this._btn, wijmo.hasItems(cv));
            };
            // select all text (and focus on the input element) when user clicks the button
            ComboBox.prototype._btnclick = function (e) {
                _super.prototype._btnclick.call(this, e);
                if (!this.isTouching && this._elRef == this._tbx) {
                    this.selectAll();
                }
            };
            // create the drop-down element
            ComboBox.prototype._createDropDown = function () {
                var _this = this;
                // create the drop-down element
                if (!this._lbx) {
                    this._lbx = new input.ListBox(this._dropDown);
                }
                // limit the size of the drop-down
                this._lbx.maxHeight = 200;
                // update our selection when user picks an item from the ListBox
                // or when the selected index changes because the list changed
                this._lbx.selectedIndexChanged.addHandler(function () {
                    _this._updateBtn();
                    _this.selectedIndex = _this._lbx.selectedIndex;
                    _this.onSelectedIndexChanged();
                });
                // update button display when item list changes
                this._lbx.itemsChanged.addHandler(function () {
                    _this._updateBtn();
                });
                // close the drop-down when the user clicks to select an item
                this.addEventListener(this._dropDown, 'click', this._dropDownClick.bind(this));
            };
            //#endregion ** overrides
            //--------------------------------------------------------------------------
            //#region ** implementation
            // close the drop-down when the user clicks to select an item
            ComboBox.prototype._dropDownClick = function (e) {
                if (!e.defaultPrevented) {
                    if (e.target != this._dropDown) {
                        this.isDroppedDown = false;
                    }
                }
            };
            // update text in textbox
            ComboBox.prototype._setText = function (text, fullMatch) {
                // not while composing IME text...
                if (this._composing)
                    return;
                // prevent reentrant calls while moving CollectionView cursor
                if (this._settingText)
                    return;
                this._settingText = true;
                // make sure we have a string
                if (text == null)
                    text = '';
                text = text.toString();
                // get variables we need
                var index = this.selectedIndex, cv = this.collectionView, start = this._getSelStart(), len = -1, autoComplete = true;
                // handle cases where user presses delete on editable boxes
                if (this._delKey && this.isEditable) {
                    fullMatch = true;
                    autoComplete = false;
                }
                // search for the index
                index = this.indexOf(text, fullMatch);
                if (autoComplete) {
                    if (index < 0 && fullMatch) {
                        index = this.indexOf(text, false);
                    }
                    if (index < 0 && start > 0) {
                        index = this.indexOf(text.substr(0, start), false);
                    }
                }
                // not found and not editable? restore old text and move cursor to matching part
                if (index < 0 && !this.isEditable && wijmo.hasItems(cv)) {
                    if (this.isRequired || text) {
                        var oldText = this._oldText || ''; // TFS 233094
                        index = Math.max(0, this.indexOf(oldText, false));
                        for (var i = 0; i < text.length && i < oldText.length; i++) {
                            if (text[i] != oldText[i]) {
                                start = i;
                                break;
                            }
                        }
                    }
                }
                if (index > -1) {
                    len = start;
                    text = this.getDisplayText(index);
                }
                // update element
                if (text != this._tbx.value) {
                    this._tbx.value = text;
                }
                // update text selection
                if (len > -1 && this.containsFocus() && !this.isTouching) {
                    this._updateInputSelection(len);
                }
                // update collectionView
                if (cv) {
                    cv.moveCurrentToPosition(index);
                }
                // call base class to fire textChanged event
                _super.prototype._setText.call(this, text, fullMatch);
                // clear flags
                this._delKey = 0;
                this._settingText = false;
            };
            // skip to the next/previous item that starts with a given string, wrapping
            ComboBox.prototype._findNext = function (text, step) {
                if (this.collectionView) {
                    text = text.toLowerCase();
                    var len = this.collectionView.items.length, index = void 0, t = void 0;
                    for (var i = 1; i <= len; i++) {
                        index = (this.selectedIndex + i * step + len) % len;
                        t = this.getDisplayText(index).toLowerCase();
                        if (t && t.indexOf(text) == 0) {
                            var item = this.dropDown.children[index]; // skip disabled items
                            if (!item || this.listBox.isItemEnabled(index)) {
                                return index;
                            }
                        }
                    }
                }
                return this.selectedIndex;
            };
            // override to select items with the keyboard
            ComboBox.prototype._keydown = function (e) {
                // allow base class
                _super.prototype._keydown.call(this, e);
                // done if default prevented or read-only
                if (e.defaultPrevented || this.isReadOnly) {
                    return;
                }
                // not if the alt key is pressed (TFS 273476/272449)
                if (e.altKey) {
                    return;
                }
                // not if we have no items
                if (!wijmo.hasItems(this.collectionView)) {
                    return;
                }
                // if the input element is not visible, we're done (e.g. menu)
                if (this._elRef != this._tbx) {
                    return;
                }
                // special handling for Back/Delete/Up/Down keys (TFS 153089, 200212, 279218)
                this._delKey = 0;
                var start = this._getSelStart();
                switch (e.keyCode) {
                    // remember Back/Delete for use later in _setText
                    case wijmo.Key.Back:
                        if (this._bsCollapse && !this.isEditable) {
                            var end = this._getSelEnd();
                            if (start > 0 && end == this._tbx.value.length && wijmo.hasItems(this.collectionView)) {
                                this._setSelRange(start - 1, end);
                            }
                        }
                        this._delKey = e.keyCode;
                        break;
                    case wijmo.Key.Delete:
                        this._delKey = e.keyCode;
                        break;
                    // move up/down the list
                    case wijmo.Key.Up:
                    case wijmo.Key.Down:
                        if (start == this.text.length) {
                            start = 0;
                        }
                        ;
                        this.selectedIndex = this._findNext(this.text.substr(0, start), e.keyCode == wijmo.Key.Up ? -1 : +1);
                        this._setSelRange(start, this.text.length);
                        e.preventDefault();
                        break;
                }
            };
            // set selection range in input element (if it is visible)
            ComboBox.prototype._updateInputSelection = function (start) {
                if (this._elRef == this._tbx) {
                    this._setSelRange(start, this._tbx.value.length);
                }
            };
            // get selection start in an extra-safe way (TFS 82372)
            ComboBox.prototype._getSelStart = function () {
                return this._tbx && this._tbx.value
                    ? this._tbx.selectionStart
                    : 0;
            };
            // get selection end in an extra-safe way
            ComboBox.prototype._getSelEnd = function () {
                return this._tbx && this._tbx.value
                    ? this._tbx.selectionEnd
                    : 0;
            };
            // set selection range if the input element is visible and not read-only
            ComboBox.prototype._setSelRange = function (start, end) {
                var tbx = this._tbx;
                if (this._elRef == tbx) {
                    wijmo.setSelectionRange(tbx, start, end);
                }
            };
            return ComboBox;
        }(input.DropDown));
        input.ComboBox = ComboBox;
    })(input = wijmo.input || (wijmo.input = {}));
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
/**
 * Defines input controls for strings, numbers, dates, times, and colors.
 */
var wijmo;
(function (wijmo) {
    var input;
    (function (input) {
        'use strict';
        /**
         * The @see:AutoComplete control is an input control that allows callers
         * to customize the item list as the user types.
         *
         * The control is similar to the @see:ComboBox, except the item source is a
         * function (@see:itemsSourceFunction) rather than a static list. For example,
         * you can look up items on remote databases as the user types.
         *
         * The example below creates an @see:AutoComplete control and populates it using
         * a 'countries' array. The @see:AutoComplete searches for the country as the user
         * types, and narrows down the list of countries that match the current input.
         *
         * @fiddle:8HnLx
         */
        var AutoComplete = /** @class */ (function (_super) {
            __extends(AutoComplete, _super);
            /**
             * Initializes a new instance of the @see:AutoComplete class.
             *
             * @param element The DOM element that hosts the control, or a selector for the host element (e.g. '#theCtrl').
             * @param options The JavaScript object containing initialization data for the control.
             */
            function AutoComplete(element, options) {
                var _this = _super.call(this, element) || this;
                // property storage
                _this._cssMatch = 'wj-autocomplete-match';
                _this._minLength = 2;
                _this._maxItems = 6;
                _this._itemCount = 0;
                _this._delay = 500;
                _this._query = '';
                _this._inCallback = false;
                _this._srchProps = [];
                wijmo.addClass(_this.hostElement, 'wj-autocomplete');
                _this._bsCollapse = false; // do not collapse selection on Backspace
                _this.isEditable = true;
                _this.isRequired = false; // TFS 142492
                _this.isContentHtml = true;
                _this.listBox.formatItem.addHandler(_this._formatListItem, _this);
                _this._itemsSourceFnCallBackBnd = _this._itemSourceFunctionCallback.bind(_this);
                _this.initialize(options);
                return _this;
            }
            Object.defineProperty(AutoComplete.prototype, "minLength", {
                //--------------------------------------------------------------------------
                //#region ** object model
                /**
                 * Gets or sets the minimum input length to trigger auto-complete suggestions.
                 */
                get: function () {
                    return this._minLength;
                },
                set: function (value) {
                    this._minLength = wijmo.asNumber(value, false, true);
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(AutoComplete.prototype, "maxItems", {
                /**
                 * Gets or sets the maximum number of items to display in the drop-down list.
                 */
                get: function () {
                    return this._maxItems;
                },
                set: function (value) {
                    this._maxItems = wijmo.asNumber(value, false, true);
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(AutoComplete.prototype, "delay", {
                /**
                 * Gets or sets the delay, in milliseconds, between when a keystroke occurs
                 * and when the search is performed.
                 */
                get: function () {
                    return this._delay;
                },
                set: function (value) {
                    this._delay = wijmo.asNumber(value, false, true);
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(AutoComplete.prototype, "searchMemberPath", {
                /**
                 * Gets or sets a string containing a comma-separated list of properties to use
                 * when searching for items.
                 *
                 * By default, the @see:AutoComplete control searches for matches against the
                 * property specified by the @see:displayMemberPath property. The @see:searchMemberPath
                 * property allows you to search using additional properties.
                 *
                 * For example, the code below would cause the control to display the company name
                 * and search by company name, symbol, and country:
                 *
                 * <pre>var ac = new wijmo.input.AutoComplete('#autoComplete', {
                 *   itemsSource: companies,
                 *   displayMemberPath: 'name',
                 *   searchMemberPath: 'symbol,country'
                 * });</pre>
                 */
                get: function () {
                    return this._srchProp;
                },
                set: function (value) {
                    this._srchProp = wijmo.asString(value);
                    this._srchProps = value ? value.trim().split(/\s*,\s*/) : [];
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(AutoComplete.prototype, "itemsSourceFunction", {
                /**
                 * Gets or sets a function that provides list items dynamically as the user types.
                 *
                 * The function takes three parameters:
                 * <ul>
                 *     <li>the query string typed by the user</li>
                 *     <li>the maximum number of items to return</li>
                 *     <li>the callback function to call when the results become available</li>
                 * </ul>
                 *
                 * For example:
                 * <pre>autoComplete.itemsSourceFunction = function (query, max, callback) {
                 *   // get results from the server
                 *   var params = { query: query, max: max };
                 *   $.getJSON('companycatalog.ashx', params, function (response) {
                 *     // return results to the control
                 *     callback(response);
                 *   });
                 * };</pre>
                 */
                get: function () {
                    return this._itemsSourceFn;
                },
                set: function (value) {
                    this._itemsSourceFn = wijmo.asFunction(value);
                    if (wijmo.isFunction(this._itemsSourceFn)) {
                        this.itemsSourceFunction(this.text, this.maxItems, this._itemsSourceFnCallBackBnd);
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(AutoComplete.prototype, "cssMatch", {
                /**
                 * Gets or sets the name of the CSS class used to highlight any parts
                 * of the content that match the search terms.
                 */
                get: function () {
                    return this._cssMatch;
                },
                set: function (value) {
                    this._cssMatch = wijmo.asString(value);
                },
                enumerable: true,
                configurable: true
            });
            //#endregion ** object model
            //--------------------------------------------------------------------------
            //#region ** overrides
            // override to make up/down keys work properly
            AutoComplete.prototype._keydown = function (e) {
                if (!e.defaultPrevented && this.isDroppedDown) {
                    switch (e.keyCode) {
                        case wijmo.Key.Up:
                        case wijmo.Key.Down:
                            this.selectAll();
                            break;
                    }
                }
                _super.prototype._keydown.call(this, e);
            };
            // update text in textbox
            AutoComplete.prototype._setText = function (text) {
                // don't call base class (to avoid autocomplete)
                var _this = this;
                // don't do this while handling the itemsSourcefunction callback
                if (this._inCallback) {
                    return;
                }
                // resetting...
                if (!text && this.selectedIndex > -1) {
                    this.selectedIndex = -1;
                }
                // raise textChanged
                if (text != this._oldText) {
                    // assign only if necessary to prevent occasionally swapping chars (Android 4.4.2)
                    if (this._tbx.value != text) {
                        this._tbx.value = text;
                    }
                    this._oldText = text;
                    this.onTextChanged();
                    // no text? no filter...
                    if (!text && this.collectionView) {
                        this._rxHighlight = null; // TFS 278848
                        this.collectionView.filter = this._query = null;
                        this.isDroppedDown = false;
                        return;
                    }
                }
                // update list when user types in some text
                if (this._toSearch) {
                    clearTimeout(this._toSearch);
                }
                if (text != this.getDisplayText()) {
                    // get new search terms on a timeOut (so the control doesn't update too often)
                    this._toSearch = setTimeout(function () {
                        _this._toSearch = null;
                        // get search terms
                        var terms = _this.text.trim().toLowerCase();
                        if (terms.length >= _this._minLength && terms != _this._query) {
                            // save new search terms
                            _this._query = terms;
                            // escape RegEx characters in the terms string
                            terms = terms.replace(/([.?*+^$[\]\\(){}|-])/g, "\\$1");
                            // escape HTML characters in the terms string
                            var termsEscaped = wijmo.escapeHtml(terms);
                            // build regular expressions for searching and highlighting the items
                            // when searching, match *all* terms on the string
                            // when highlighting, match *any* terms on the string
                            // if the content is html, use negative lookahead to highlight only outside HTML tags
                            // http://stackoverflow.com/questions/18621568/regex-replace-text-outside-html-tags
                            _this._rxMatch = new RegExp('(?=.*' + terms.replace(/ /g, ')(?=.*') + ')', 'ig');
                            _this._rxHighlight = _this.isContentHtml
                                ? new RegExp('(' + termsEscaped.replace(/ /g, '|') + ')(?![^<]*>|[^<>]* </)', 'ig')
                                : new RegExp('(' + termsEscaped.replace(/ /g, '|') + ')', 'ig');
                            // update list
                            //this.isDroppedDown = false;
                            if (_this.itemsSourceFunction) {
                                _this.itemsSourceFunction(terms, _this.maxItems, _this._itemsSourceFnCallBackBnd);
                            }
                            else {
                                _this._updateItems();
                            }
                        }
                    }, this._delay);
                }
            };
            // populate list with results from itemSourceFunction
            AutoComplete.prototype._itemSourceFunctionCallback = function (result) {
                // update list
                this._inCallback = true;
                var cv = wijmo.asCollectionView(result);
                if (cv) {
                    cv.moveCurrentToPosition(-1);
                }
                this.itemsSource = cv;
                this._inCallback = false;
                // show list at the proper place if we have the focus 
                if (this.containsFocus()) {
                    this.isDroppedDown = true;
                    this.refresh();
                }
            };
            // closing the drop-down: commit the change
            AutoComplete.prototype.onIsDroppedDownChanged = function (e) {
                // do not call super because it selects the whole text, 
                // and we don't want that while the user is typing
                //super.onIsDroppedDownChanged(e);
                this.isDroppedDownChanged.raise(this, e);
                // keep cursor at the end of the string if the list closed because
                // the user just typed something that didn't match any items
                if (this.containsFocus()) {
                    if (!this.isDroppedDown && !this.isTouching) {
                        if (this.selectedItem == null) {
                            var len = this.text.length;
                            wijmo.setSelectionRange(this._tbx, len);
                        }
                    }
                }
                // clear query string
                this._query = '';
                // remove the filter if the dropdown is closed (or not: TFS 284543)
                //if (!this.isDroppedDown && this.collectionView) {
                //    this.collectionView.filter = null;
                //}
            };
            //#endregion ** overrides
            //--------------------------------------------------------------------------
            //#region ** implementation
            // apply the filter to show only the matches
            AutoComplete.prototype._updateItems = function () {
                var cv = this.collectionView;
                if (cv) {
                    // apply the filter
                    this._inCallback = true;
                    cv.beginUpdate();
                    this._itemCount = 0;
                    cv.filter = this._filter.bind(this);
                    cv.moveCurrentToPosition(-1);
                    cv.endUpdate();
                    this._inCallback = false;
                    // show/hide the drop-down
                    var cnt = cv.items.length;
                    this.isDroppedDown = cnt > 0 && this.containsFocus();
                    if (cnt == 0 && !this.isEditable) {
                        this.selectedIndex = -1;
                    }
                    // refresh to update the drop-down position
                    this.refresh();
                }
            };
            // filter the items and show only the matches
            AutoComplete.prototype._filter = function (item) {
                // honor maxItems
                if (this._itemCount >= this._maxItems) {
                    return false;
                }
                // apply filter to item
                var text = this._getItemText(item, false);
                if (this._srchProps) {
                    for (var i = 0; i < this._srchProps.length; i++) {
                        text += '\0' + item[this._srchProps[i]];
                    }
                }
                // remove html tags for matching
                if (this.isContentHtml) {
                    text = text.replace(/<[^>]*>/g, '');
                }
                // count matches
                if (text.match(this._rxMatch)) {
                    this._itemCount++;
                    return true;
                }
                // no pass
                return false;
            };
            // gets the text to display for a given item (TFS 253890)
            AutoComplete.prototype._getItemText = function (item, header) {
                var text = item ? item.toString() : '', binding = header && this.headerPath
                    ? this._pathHdr
                    : this._lbx._pathDisplay;
                if (binding) {
                    text = binding.getValue(item);
                    text = text != null ? text.toString() : ''; // TFS 268268
                }
                return text;
            };
            // ListBox item formatter: show matches in bold
            AutoComplete.prototype._formatListItem = function (sender, e) {
                if (this._cssMatch && this._rxHighlight) {
                    var highlight = '<span class="' + this._cssMatch + '">$1</span>';
                    e.item.innerHTML = e.item.innerHTML.replace(this._rxHighlight, highlight);
                }
            };
            return AutoComplete;
        }(input.ComboBox));
        input.AutoComplete = AutoComplete;
    })(input = wijmo.input || (wijmo.input = {}));
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
    var input;
    (function (input) {
        'use strict';
        /**
         * The @see:Menu control shows a text element with a drop-down list of commands that
         * the user can invoke by click or touch.
         *
         * The @see:Menu control inherits from @see:ComboBox, so you populate and style it
         * in the same way that you do the @see:ComboBox (see the @see:Menu.itemsSource
         * property).
         *
         * The @see:Menu control adds an @see:Menu.itemClicked event that fires when the user
         * selects an item from the menu. The event handler can inspect the @see:Menu control
         * to determine which item was clicked. For example:
         *
         * <pre>
         * var menu = new wijmo.input.Menu(hostElement);
         * menu.header = 'Main Menu';
         * menu.itemsSource = ['option 1', 'option 2', 'option 3'];
         * menu.itemClicked.addHandler(function(sender, args) {
         * var menu = sender;
         *   alert('Thanks for selecting item ' + menu.selectedIndex + ' from menu ' + menu.header + '!');
         * });
         * </pre>
         *
         * The example below illustrates how you can create value pickers, command-based menus, and
         * menus that respond to the @see:Menu.itemClicked event. The menus in this example are based
         * on HTML <b>&lt;select;&gt</b> and <b>&lt;option;&gt</b> elements.
         *
         * @fiddle:BX853
         */
        var Menu = /** @class */ (function (_super) {
            __extends(Menu, _super);
            /**
             * Initializes a new instance of the @see:Menu class.
             *
             * @param element The DOM element that hosts the control, or a selector for the host element (e.g. '#theCtrl').
             * @param options The JavaScript object containing initialization data for the control.
             */
            function Menu(element, options) {
                var _this = _super.call(this, element) || this;
                /**
                 * Occurs when the user picks an item from the menu.
                 *
                 * The handler can determine which item was picked by reading the event sender's
                 * @see:selectedIndex property.
                 */
                _this.itemClicked = new wijmo.Event();
                // add css class name
                var host = _this.hostElement, tbx = _this._tbx;
                wijmo.addClass(host, 'wj-menu');
                // replace textbox with header div
                tbx.style.display = 'none';
                var tpl = '<div wj-part="header" class="wj-form-control" style="cursor:default"/>';
                _this._hdr = _this._elRef = wijmo.createElement(tpl);
                tbx.parentElement.insertBefore(_this._hdr, _this._tbx);
                // restore original tabIndex since applyTemplate set it to -1
                var rxIdx = _this._orgOuter.match(/tabindex="?(-?\d+)"?/i);
                if (rxIdx) {
                    host.tabIndex = parseInt(rxIdx[1]);
                }
                // this is not required
                _this.isRequired = false;
                // accessibility:
                // https://www.w3.org/TR/wai-aria-1.1/#menu
                // http://oaa-accessibility.org/examples/role/85/
                wijmo.setAttribute(host, 'role', 'menubar', true);
                wijmo.setAttribute(tbx, 'role', null);
                wijmo.setAttribute(tbx, 'aria-autocomplete', null);
                wijmo.setAttribute(tbx, 'aria-owns', null);
                wijmo.setAttribute(_this.dropDown, 'role', 'menu');
                _this.listBox.itemRole = 'menuitem';
                // initializing from <select> tag
                if (_this._orgTag == 'SELECT') {
                    _this.header = host.getAttribute('header');
                    if (_this._lbx.itemsSource) {
                        _this.commandParameterPath = 'cmdParam';
                    }
                }
                // change some defaults
                _this.isContentHtml = true;
                _this.maxDropDownHeight = 500;
                // toggle drop-down when clicking on the header
                // or fire the click event if this menu is a split-button
                _this.addEventListener(_this._hdr, 'click', function (e) {
                    if (!e.defaultPrevented) {
                        if (_this._isButton) {
                            _this.isDroppedDown = false;
                            _this._raiseCommand();
                        }
                        else {
                            _this.isDroppedDown = !_this.isDroppedDown;
                        }
                    }
                });
                // close menu when dropdown loses focus (TFS 302760)
                _this.listBox.lostFocus.addHandler(function () {
                    if (!_this.containsFocus()) {
                        _this.isDroppedDown = false;
                    }
                });
                // initialize control options
                _this.initialize(options);
                return _this;
            }
            Object.defineProperty(Menu.prototype, "header", {
                /**
                 * Gets or sets the HTML text shown in the @see:Menu element.
                 */
                get: function () {
                    return this._hdr.innerHTML;
                },
                set: function (value) {
                    this._hdr.innerHTML = wijmo.asString(value);
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(Menu.prototype, "command", {
                /**
                 * Gets or sets the command to execute when an item is clicked.
                 *
                 * Commands are objects that implement two methods:
                 * <ul>
                 *  <li><b>executeCommand(parameter)</b> This method executes the command.</li>
                 *  <li><b>canExecuteCommand(parameter)</b> This method returns a Boolean value
                 *      that determines whether the controller can execute the command.
                 *      If this method returns false, the menu option is disabled.</li>
                 * </ul>
                 *
                 * You can also set commands on individual items using the @see:commandPath
                 * property.
                 */
                get: function () {
                    return this._command;
                },
                set: function (value) {
                    this._command = value;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(Menu.prototype, "commandPath", {
                /**
                 * Gets or sets the name of the property that contains the command to
                 * execute when the user clicks an item.
                 *
                 * Commands are objects that implement two methods:
                 * <ul>
                 *  <li><b>executeCommand(parameter)</b> This method executes the command.</li>
                 *  <li><b>canExecuteCommand(parameter)</b> This method returns a Boolean value
                 *      that determines whether the controller can execute the command.
                 *      If this method returns false, the menu option is disabled.</li>
                 * </ul>
                 */
                get: function () {
                    return this._cmdPath;
                },
                set: function (value) {
                    this._cmdPath = wijmo.asString(value);
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(Menu.prototype, "commandParameterPath", {
                /**
                 * Gets or sets the name of the property that contains a parameter to use with
                 * the command specified by the @see:commandPath property.
                 */
                get: function () {
                    return this._cmdParamPath;
                },
                set: function (value) {
                    this._cmdParamPath = wijmo.asString(value);
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(Menu.prototype, "isButton", {
                /**
                 * Gets or sets a value that determines whether this @see:Menu should act
                 * as a split button instead of a regular menu.
                 *
                 * The difference between regular menus and split buttons is what happens
                 * when the user clicks the menu header.
                 * In regular menus, clicking the header shows or hides the menu options.
                 * In split buttons, clicking the header raises the @see:Menu.itemClicked
                 * event and/or invokes the command associated with the last option selected by
                 * the user as if the user had picked the item from the drop-down list.
                 *
                 * If you want to differentiate between clicks on menu items and the button
                 * part of a split button, check the value of the @see:Menu.isDroppedDown property
                 * of the event sender. If that is true, then a menu item was clicked; if it
                 * is false, then the button was clicked.
                 *
                 * For example, the code below implements a split button that uses the drop-down
                 * list only to change the default item/command, and triggers actions only when
                 * the button is clicked:
                 *
                 * <pre>&lt;-- view --&gt;
                 * &lt;wj-menu is-button="true" header="Run" value="browser"
                 *   item-clicked="itemClicked(s, e)"&gt;
                 *   &lt;wj-menu-item value="'Internet Explorer'"&gt;Internet Explorer&lt;/wj-menu-item&gt;
                 *   &lt;wj-menu-item value="'Chrome'"&gt;Chrome&lt;/wj-menu-item&gt;
                 *   &lt;wj-menu-item value="'Firefox'"&gt;Firefox&lt;/wj-menu-item&gt;
                 *   &lt;wj-menu-item value="'Safari'"&gt;Safari&lt;/wj-menu-item&gt;
                 *   &lt;wj-menu-item value="'Opera'"&gt;Opera&lt;/wj-menu-item&gt;
                 * &lt;/wj-menu&gt;
                 *
                 * // controller
                 * $scope.browser = 'Internet Explorer';
                 * $scope.itemClicked = function (s, e) {
                 *   // if not dropped down, click was on the button
                 *   if (!s.isDroppedDown) {
                 *     alert('running ' + $scope.browser);
                 *   }
                 *}</pre>
                 */
                get: function () {
                    return this._isButton;
                },
                set: function (value) {
                    this._isButton = wijmo.asBoolean(value);
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(Menu.prototype, "owner", {
                /**
                 * Gets or sets the element that owns this @see:Menu.
                 *
                 * This variable is set by the wj-context-menu directive in case a single
                 * menu is used as a context menu for several different elements.
                 */
                get: function () {
                    return this._owner;
                },
                set: function (value) {
                    this._owner = wijmo.asType(value, HTMLElement, true);
                    this._enableDisableItems(); // TFS 122978
                },
                enumerable: true,
                configurable: true
            });
            /**
             * Shows the menu at a given location.
             *
             * This method is useful if you want to use the menu as a context
             * menu, attached to one or more elements on the page. For example:
             *
             * <pre>// create menu
             * var div = document.createElement('div');
             * var menu = new wijmo.input.Menu(div, {
             *     itemsSource: 'New,Open,Save,Exit'.split(','),
             *     itemClicked: function (s, e) {
             *         alert('thanks for picking ' + menu.selectedIndex);
             *     }
             * });
             *
             * // use it as a context menu for one or more elements
             * var element = document.getElementById('btn');
             * element.addEventListener('contextmenu', function (e) {
             *     e.preventDefault();
             *     menu.show(e);
             * });</pre>
             *
             * @param position An optional <b>MouseEvent</b> or reference element
             * that determines the position where the menu should be displayed.
             * If not provided, the menu is displayed at the center of the screen.
             */
            Menu.prototype.show = function (position) {
                if (!this.isDroppedDown) {
                    var dd = this.dropDown;
                    this.selectedIndex = -1;
                    if (this.onIsDroppedDownChanging(new wijmo.CancelEventArgs())) {
                        if (this.owner) {
                            dd[wijmo.Control._OWNR_KEY] = this.owner;
                        }
                        wijmo.showPopup(dd, position);
                        this.onIsDroppedDownChanged();
                        dd.focus();
                    }
                }
            };
            /**
             * Hides the menu.
             *
             * This method is useful if you want to hide a context menu displayed
             * with the @see:show method.
             */
            Menu.prototype.hide = function () {
                if (this.isDroppedDown) {
                    if (this.onIsDroppedDownChanging(new wijmo.CancelEventArgs())) {
                        wijmo.hidePopup(this.dropDown);
                        this.onIsDroppedDownChanged();
                    }
                }
            };
            /**
             * Raises the @see:itemClicked event.
             */
            Menu.prototype.onItemClicked = function (e) {
                this.itemClicked.raise(this, e);
            };
            // ** overrides
            // override refresh to enable/disable items (TFS 302993)
            Menu.prototype.refresh = function (fullUpdate) {
                _super.prototype.refresh.call(this, fullUpdate);
                this._enableDisableItems();
            };
            // override onIsDroppedDownChanged to clear the selection when showing the menu
            Menu.prototype.onIsDroppedDownChanged = function (e) {
                _super.prototype.onIsDroppedDownChanged.call(this, e);
                if (this.isDroppedDown) {
                    // suspend events
                    this._closing = true;
                    // save current item in case the user presses the split button
                    // while the drop-down is open (TFS 119513)
                    this._defaultItem = this.selectedItem;
                    // reset menu
                    this.isRequired = false;
                    this.selectedIndex = -1;
                    // enable/disable items
                    this._enableDisableItems();
                    // restore events
                    this._closing = false;
                    // move focus to the list so users can select with the keyboard
                    this.dropDown.focus();
                }
                else {
                    // closed the drop-down, make sure we have a selected item (TFS 122720)
                    if (!this.selectedItem) {
                        this.selectedItem = this._defaultItem;
                    }
                }
            };
            // ** implementation
            // override to raise itemClicked on Enter (when open) or 
            // to open the drop-down (when closed) TFS 206344
            Menu.prototype._keydown = function (e) {
                if (!e.defaultPrevented) {
                    if (e.keyCode == wijmo.Key.Enter) {
                        if (this.isDroppedDown) {
                            if (this.getDisplayText(this.selectedIndex)) {
                                this._raiseCommand();
                            }
                        }
                        else {
                            this.isDroppedDown = true;
                            e.preventDefault();
                        }
                    }
                }
                _super.prototype._keydown.call(this, e);
            };
            // raise command and close drop-down when an item is clicked
            Menu.prototype._dropDownClick = function (e) {
                if (!e.defaultPrevented && e.target != this.dropDown) {
                    if (this.getDisplayText(this.selectedIndex)) {
                        this._raiseCommand();
                    }
                }
                _super.prototype._dropDownClick.call(this, e); // allow base class
            };
            // raise itemClicked and/or invoke the current command
            Menu.prototype._raiseCommand = function (e) {
                // execute command if available
                var item = this.selectedItem, cmd = this._getCommand(item);
                if (cmd) {
                    var parm = this._cmdParamPath ? item[this._cmdParamPath] : null;
                    if (!this._canExecuteCommand(cmd, parm)) {
                        return; // command not currently available
                    }
                    this._executeCommand(cmd, parm);
                }
                // raise itemClicked
                this.onItemClicked(e);
            };
            // gets the command to be executed when an item is clicked
            Menu.prototype._getCommand = function (item) {
                var cmd = item && this.commandPath ? item[this.commandPath] : null;
                return cmd ? cmd : this.command;
            };
            // execute a command
            // cmd may be an object that implements the ICommand interface or it may be just a function
            // parm is an optional parameter passed to the command.
            Menu.prototype._executeCommand = function (cmd, parm) {
                if (cmd && !wijmo.isFunction(cmd)) {
                    cmd = cmd['executeCommand'];
                }
                if (wijmo.isFunction(cmd)) {
                    cmd(parm);
                }
            };
            // checks whether a command can be executed
            Menu.prototype._canExecuteCommand = function (cmd, parm) {
                if (cmd) {
                    var x = cmd['canExecuteCommand'];
                    if (wijmo.isFunction(x)) {
                        return x(parm);
                    }
                }
                return true;
            };
            // enable/disable the menu options
            Menu.prototype._enableDisableItems = function () {
                if (this.collectionView && (this.command || this.commandPath)) {
                    var items = this.collectionView.items;
                    for (var i = 0; i < items.length; i++) {
                        var cmd = this._getCommand(items[i]), parm = this.commandParameterPath ? items[i][this.commandParameterPath] : null;
                        if (cmd) {
                            var el = this._lbx.hostElement.children[i];
                            wijmo.toggleClass(el, 'wj-state-disabled', !this._canExecuteCommand(cmd, parm));
                        }
                    }
                }
            };
            return Menu;
        }(input.ComboBox));
        input.Menu = Menu;
    })(input = wijmo.input || (wijmo.input = {}));
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
// initialize header format
wijmo.culture.MultiSelect = window['wijmo'].culture.MultiSelect || {
    itemsSelected: '{count:n0} items selected',
    selectAll: 'Select All'
};
var wijmo;
(function (wijmo) {
    var input;
    (function (input) {
        'use strict';
        /**
         * The @see:MultiSelect control allows users to select multiple items from
         * drop-down lists that contain custom objects or simple strings.
         *
         * The @see:MultiSelect control extends @see:ComboBox, with all the usual
         * properties, including @see:MultiSelect.itemsSource and
         * @see:MultiSelect.displayMemberPath.
         *
         * Like the @see:ListBox control, it has a @see:MultiSelect.checkedMemberPath
         * property that defines the name of the property that determines whether an
         * item is checked or not.
         *
         * The items currently checked (selected) can be obtained using the
         * @see:MultiSelect.checkedItems property.
         *
         * The control header is fully customizable. By default, it shows up to two items
         * selected and the item count after that. You can change the maximum number of
         * items to display (@see:MultiSelect.maxHeaderItems), the message shown when no
         * items are selected (@see:MultiSelect.placeholder), and the format string used to
         * show the item count (@see:MultiSelect.headerFormat).
         *
         * Alternatively, you can provide a function to generate the header content based
         * on whatever criteria your application requires (@see:MultiSelect.headerFormatter).
         */
        var MultiSelect = /** @class */ (function (_super) {
            __extends(MultiSelect, _super);
            /**
             * Initializes a new instance of the @see:MultiSelect class.
             *
             * @param element The DOM element that hosts the control, or a selector for the host element (e.g. '#theCtrl').
             * @param options The JavaScript object containing initialization data for the control.
             */
            function MultiSelect(element, options) {
                var _this = _super.call(this, element) || this;
                _this._maxHdrItems = 2;
                _this._readOnly = false;
                _this._hdrFmt = wijmo.culture.MultiSelect.itemsSelected;
                /**
                 * Occurs when the value of the @see:checkedItems property changes.
                 */
                _this.checkedItemsChanged = new wijmo.Event();
                wijmo.addClass(_this.hostElement, 'wj-multiselect');
                // make header element read-only, ListBox a multi-select
                _this._tbx.readOnly = true;
                _this.checkedMemberPath = null;
                // toggle drop-down when clicking on the header
                _this.addEventListener(_this.inputElement, 'click', function () {
                    _this.isDroppedDown = !_this.isDroppedDown;
                });
                // handle clicks on the select all button
                _this.addEventListener(_this._selectAll, 'click', function (e) {
                    if (wijmo.hasItems(_this.collectionView) && e.target == _this._selectAllCheckbox) {
                        _this.checkedItems = e.target.checked ? _this.collectionView.items : [];
                    }
                });
                // do NOT close the drop-down when the user clicks to select an item
                _this.removeEventListener(_this.dropDown, 'click');
                // update header now, when the itemsSource changes, and when items are selected
                _this._updateHeader();
                _this.listBox.itemsChanged.addHandler(function () {
                    _this._updateHeader();
                });
                _this.listBox.checkedItemsChanged.addHandler(function () {
                    _this._updateHeader();
                    _this.onCheckedItemsChanged();
                });
                // initialize control options
                _this.initialize(options);
                return _this;
            }
            Object.defineProperty(MultiSelect.prototype, "showSelectAllCheckbox", {
                //** object model
                /**
                 * Gets or sets whether the control should display a "Select All" checkbox
                 * above the items to select or de-select all items.
                 */
                get: function () {
                    return this._selectAll.style.display == '';
                },
                set: function (value) {
                    this._selectAll.style.display = wijmo.asBoolean(value) ? '' : 'none';
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(MultiSelect.prototype, "selectAllLabel", {
                /**
                 * Gets or sets the string to be used as a label for the "Select All"
                 * checkbox that is displayed when the @see:showSelectAllCheckbox
                 * property is set to true.
                 *
                 * This property is set to null by default, which causes the control
                 * to show a localized version of the string "Select All".
                 */
                get: function () {
                    return this._selectAllLabel;
                },
                set: function (value) {
                    if (value != this._selectAllLabel) {
                        this._selectAllLabel = wijmo.asString(value);
                        this.refresh();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(MultiSelect.prototype, "checkedMemberPath", {
                /**
                 * Gets or sets the name of the property used to control the checkboxes
                 * placed next to each item.
                 */
                get: function () {
                    var p = this.listBox.checkedMemberPath;
                    return p != MultiSelect._DEF_CHECKED_PATH ? p : null;
                },
                set: function (value) {
                    value = wijmo.asString(value);
                    this.listBox.checkedMemberPath = value ? value : MultiSelect._DEF_CHECKED_PATH;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(MultiSelect.prototype, "maxHeaderItems", {
                /**
                 * Gets or sets the maximum number of items to display on the control header.
                 *
                 * If no items are selected, the header displays the text specified by the
                 * @see:placeholder property.
                 *
                 * If the number of selected items is smaller than or equal to the value of the
                 * @see:maxHeaderItems property, the selected items are shown in the header.
                 *
                 * If the number of selected items is greater than @see:maxHeaderItems, the
                 * header displays the selected item count instead.
                 */
                get: function () {
                    return this._maxHdrItems;
                },
                set: function (value) {
                    if (this._maxHdrItems != value) {
                        this._maxHdrItems = wijmo.asNumber(value);
                        this._updateHeader();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(MultiSelect.prototype, "headerFormat", {
                /**
                 * Gets or sets the format string used to create the header content
                 * when the control has more than @see:maxHeaderItems items checked.
                 *
                 * The format string may contain the '{count}' replacement string
                 * which gets replaced with the number of items currently checked.
                 * The default value for this property in the English culture is
                 * '{count:n0} items selected'.
                 */
                get: function () {
                    return this._hdrFmt;
                },
                set: function (value) {
                    if (value != this._hdrFmt) {
                        this._hdrFmt = wijmo.asString(value);
                        this._updateHeader();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(MultiSelect.prototype, "headerFormatter", {
                /**
                 * Gets or sets a function that gets the HTML in the control header.
                 *
                 * By default, the control header content is determined based on the
                 * @see:placeholder, @see:maxHeaderItems, and on the current selection.
                 *
                 * You may customize the header content by specifying a function that
                 * returns a custom string based on whatever criteria your application
                 * requires.
                 */
                get: function () {
                    return this._hdrFormatter;
                },
                set: function (value) {
                    if (value != this._hdrFormatter) {
                        this._hdrFormatter = wijmo.asFunction(value);
                        this._updateHeader();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(MultiSelect.prototype, "checkedItems", {
                /**
                 * Gets or sets an array containing the items that are currently checked.
                 */
                get: function () {
                    return this.listBox.checkedItems;
                },
                set: function (value) {
                    this.listBox.checkedItems = wijmo.asArray(value);
                },
                enumerable: true,
                configurable: true
            });
            /**
             * Raises the @see:checkedItemsChanged event.
             */
            MultiSelect.prototype.onCheckedItemsChanged = function (e) {
                this.checkedItemsChanged.raise(this, e);
            };
            //** overrides
            // create the drop-down element
            MultiSelect.prototype._createDropDown = function () {
                // create selectAll element
                this._selectAll = wijmo.createElement('<div class="wj-listbox-item wj-header wj-select-all" tabindex="0" style="display:none"><label><input type="checkbox"> <span></span></label></div>', this._dropDown);
                this._selectAllCheckbox = this._selectAll.querySelector('input[type=checkbox]');
                this._selectAllSpan = this._selectAll.querySelector('label>span');
                wijmo.setText(this._selectAllSpan, wijmo.culture.MultiSelect.selectAll);
                // create child ListBox control
                var lbHost = wijmo.createElement('<div style="width:100%;border:none"></div>', this._dropDown);
                this._lbx = new wijmo.input.ListBox(lbHost);
                // let base class do its thing
                _super.prototype._createDropDown.call(this);
            };
            Object.defineProperty(MultiSelect.prototype, "isReadOnly", {
                // override since our input is always read-only
                get: function () {
                    return this._readOnly;
                },
                set: function (value) {
                    this._readOnly = wijmo.asBoolean(value);
                    wijmo.toggleClass(this.hostElement, 'wj-state-readonly', this.isReadOnly);
                },
                enumerable: true,
                configurable: true
            });
            // update header when refreshing
            MultiSelect.prototype.refresh = function (fullUpdate) {
                if (fullUpdate === void 0) { fullUpdate = true; }
                _super.prototype.refresh.call(this, fullUpdate);
                this._updateHeader();
                if (this._selectAllSpan) {
                    wijmo.setText(this._selectAllSpan, this._selectAllLabel || wijmo.culture.MultiSelect.selectAll);
                }
            };
            // give focus to list when dropping down
            MultiSelect.prototype.onIsDroppedDownChanged = function (e) {
                var _this = this;
                _super.prototype.onIsDroppedDownChanged.call(this, e);
                if (this.isDroppedDown && this.containsFocus()) {
                    setTimeout(function () {
                        //this.dropDown.focus();
                        _this.listBox.focus();
                    }, 200);
                }
            };
            // textbox is read-only!
            MultiSelect.prototype._setText = function (text, fullMatch) {
                // keep existing text
            };
            // override to show drop-down and start selecting
            MultiSelect.prototype._keydown = function (e) {
                _super.prototype._keydown.call(this, e);
                if (!e.defaultPrevented && wijmo.hasItems(this.collectionView) && e.keyCode > 32) {
                    this.isDroppedDown = true;
                }
            };
            //** implementation
            // update the value of the control header
            MultiSelect.prototype._updateHeader = function () {
                // get selected items
                var items = this.checkedItems;
                // update the header
                if (wijmo.isFunction(this._hdrFormatter)) {
                    this.inputElement.value = this._hdrFormatter();
                }
                else {
                    var hdr = '';
                    if (items.length > 0) {
                        if (items.length <= this._maxHdrItems) {
                            if (this.displayMemberPath) {
                                for (var i = 0; i < items.length; i++) {
                                    items[i] = items[i][this.displayMemberPath];
                                }
                            }
                            hdr = items.join(', ');
                        }
                        else {
                            hdr = wijmo.format(this.headerFormat, {
                                count: items.length
                            });
                        }
                    }
                    this.inputElement.value = hdr;
                }
                // update the selectAll element state
                var checked = null, view = this.collectionView;
                if (wijmo.hasItems(view)) {
                    if (items.length == 0) {
                        checked = false;
                    }
                    else if (items.length == view.items.length) {
                        checked = true;
                    }
                }
                this._selectAllCheckbox.indeterminate = checked == null;
                if (checked != null) {
                    this._selectAllCheckbox.checked = checked;
                }
                // update wj-state attributes
                this._updateState();
            };
            MultiSelect._DEF_CHECKED_PATH = '$checked';
            return MultiSelect;
        }(input.ComboBox));
        input.MultiSelect = MultiSelect;
    })(input = wijmo.input || (wijmo.input = {}));
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
    var input;
    (function (input_1) {
        'use strict';
        /**
         * The @see:MultiAutoComplete control allows users to pick items from lists
         * that contain custom objects or simple strings.
         */
        var MultiAutoComplete = /** @class */ (function (_super) {
            __extends(MultiAutoComplete, _super);
            /**
             * Initializes a new instance of the @see:MultiAutoComplete class.
             *
             * @param element The DOM element that hosts the control, or a selector for the host element (e.g. '#theCtrl').
             * @param options The JavaScript object containing initialization data for the control.
             */
            function MultiAutoComplete(element, options) {
                var _this = _super.call(this, element) || this;
                _this._selItems = [];
                _this._lastInputValue = '';
                _this._selPath = new wijmo.Binding(null);
                _this._notAddItm = false;
                // ** events
                /**
                 * Occurs when the value of the @see:selectedItems property changes.
                 */
                _this.selectedItemsChanged = new wijmo.Event();
                wijmo.addClass(_this.hostElement, 'wj-multi-autocomplete');
                _this.showDropDownButton = false;
                // initialize control options
                _this.initialize(options);
                _this._wjTpl = _this.hostElement.querySelector('.wj-template');
                _this._wjInput = _this.hostElement.querySelector('.wj-input');
                _this.addEventListener(_this.hostElement, 'keyup', _this._keyup.bind(_this), true);
                _this.addEventListener(window, 'resize', _this._adjustInputWidth.bind(_this));
                // deactivate the token field when input gets the focus
                _this.addEventListener(_this._tbx, 'focus', function () {
                    _this._itemOff();
                });
                // add helper input element to handle focus
                _this._addHelperInput();
                // refresh header now, when items are selected
                _this._initSeltems();
                // when loading the first item will show in the header, so clear it.
                //if (this._selItems.length === 0) {
                //    setTimeout(() => {
                //        this._clearSelIndex();
                //    }, 0);
                //}
                _this.listBox.itemsChanged.addHandler(function () { return _this.selectedIndex = -1; });
                _this._refreshHeader();
                return _this;
            }
            Object.defineProperty(MultiAutoComplete.prototype, "showDropDownButton", {
                //** object model
                /**
                 * Override the value for indicating control should not display a drop-down button.
                 */
                set: function (value) {
                    this._showBtn = false;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(MultiAutoComplete.prototype, "maxSelectedItems", {
                /**
                 * Gets or sets the maximum number of items that can be selected.
                 *
                 * Setting this property to null (the default value) allows users
                 * to pick any number of items.
                 */
                get: function () {
                    return this._maxtems;
                },
                set: function (value) {
                    if (this._maxtems != value) {
                        this._maxtems = wijmo.asNumber(value, true);
                        this._updateMaxItems();
                        this._refreshHeader();
                        this._clearSelIndex();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(MultiAutoComplete.prototype, "selectedMemberPath", {
                /**
                 * Gets or sets the name of the property used to control which
                 * item will be selected.
                 */
                get: function () {
                    return this._selPath.path;
                },
                set: function (value) {
                    value = wijmo.asString(value);
                    if (value !== this.selectedMemberPath) {
                        this._selPath.path = value;
                        this._initSeltems();
                        this._refreshHeader();
                        this.onSelectedItemsChanged();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(MultiAutoComplete.prototype, "selectedItems", {
                /**
                 * Gets or sets an array containing the items that are currently
                 * selected.
                 */
                get: function () {
                    return this._selItems;
                },
                set: function (value) {
                    // save the new value
                    this._selItems = wijmo.asArray(value);
                    // update the data source
                    if (this.selectedMemberPath && this.selectedMemberPath !== '') {
                        if (this._selItems) {
                            for (var i = 0; i < this._selItems.length; i++) {
                                var item = this._selItems[i];
                                this._setSelItem(item, false);
                            }
                        }
                    }
                    // update everything else
                    this._updateMaxItems();
                    this.onSelectedItemsChanged();
                    this._refreshHeader();
                    this._clearSelIndex();
                },
                enumerable: true,
                configurable: true
            });
            /**
             * Raises the @see:selectedItemsChanged event.
             */
            MultiAutoComplete.prototype.onSelectedItemsChanged = function (e) {
                this.selectedItemsChanged.raise(this, e);
            };
            //** overrides
            // give focus to list when dropping down
            MultiAutoComplete.prototype.onIsDroppedDownChanged = function (e) {
                if (!this.isDroppedDown && this.selectedIndex > -1
                    && !this._notAddItm) {
                    this._addItem(true);
                }
                this._notAddItm = false;
                _super.prototype.onIsDroppedDownChanged.call(this, e);
            };
            // update the header when refreshing
            MultiAutoComplete.prototype.refresh = function (fullUpdate) {
                _super.prototype.refresh.call(this, fullUpdate);
                this._initSeltems();
                // _itemSourceFunctionCallback call the refresh method,
                // so when dropdown list is close state, then refresh the header
                if (!this.isDroppedDown) {
                    this._refreshHeader();
                }
            };
            // override keydown handle: BackSpace, Up, Down etc
            MultiAutoComplete.prototype._keydown = function (e) {
                if (this.isReadOnly) {
                    return;
                }
                if (!e.defaultPrevented) {
                    switch (e.keyCode) {
                        // remember last text value
                        case wijmo.Key.Back:
                            this._lastInputValue = this._tbx.value;
                            break;
                        // add selected item and close dropdown
                        case wijmo.Key.Enter:
                            this._itemOff();
                            this._addItem(true);
                            if (wijmo.isIE) {
                                wijmo.setSelectionRange(this._tbx, 0, 0);
                            }
                            break;
                        // add item and keep dropdown open
                        case wijmo.Key.Tab:
                            if (this.isDroppedDown) {
                                this._addItem(false);
                                this._tbx.value = '';
                                this._lbx.selectedIndex = -1;
                                e.preventDefault();
                            }
                            else {
                                this._updateFocus();
                            }
                            break;
                        // open dropdown list
                        case wijmo.Key.Space:
                            if (this._tbx.value !== '') {
                                return;
                            }
                            if (!this.isDroppedDown && !this._tbx.disabled) {
                                this.isDroppedDown = true;
                                this._clearSelIndex();
                            }
                            break;
                        // don't add item and close dropdown 
                        case wijmo.Key.Escape:
                            if (this.isDroppedDown) {
                                this._notAddItm = true;
                            }
                            break;
                        // activate previous item
                        case wijmo.Key.Left:
                            this._itemOn(this.rightToLeft ? false : true);
                            break;
                        // activate next item
                        case wijmo.Key.Right:
                            this._itemOn(this.rightToLeft ? true : false);
                            break;
                        // return if input element is not active element
                        case wijmo.Key.Up:
                        case wijmo.Key.Down:
                            var ae = wijmo.getActiveElement();
                            if (e.altKey) {
                                if (this._tbx == ae) {
                                    this.isDroppedDown = !this.isDroppedDown;
                                    if (!this.isDroppedDown) {
                                        this._tbx.focus();
                                    }
                                    e.preventDefault();
                                    return;
                                }
                            }
                            else if (this._tbx !== ae) {
                                return;
                            }
                        // all other keys
                        default:
                            if (e.keyCode === wijmo.Key.Back || e.keyCode === wijmo.Key.Delete) {
                                return;
                            }
                            this._itemOff();
                            if (this._maxtems != null &&
                                this._selItems.length >= this._maxtems) {
                                e.preventDefault();
                            }
                            break;
                    }
                }
                // allow base class if the input element is not disabled: TFS 286036
                if (!this._tbx.disabled) {
                    _super.prototype._keydown.call(this, e);
                }
            };
            // override to deactivate the item
            MultiAutoComplete.prototype._updateState = function () {
                _super.prototype._updateState.call(this);
                // deactivate the item
                if (!this._wjTpl) {
                    return;
                }
                if (!wijmo.hasClass(this.hostElement, 'wj-state-focused')) {
                    this._itemOff();
                }
            };
            // handle the key up event: Back & Delete
            MultiAutoComplete.prototype._keyup = function (e) {
                if (this.isReadOnly) {
                    return;
                }
                if (!e.defaultPrevented) {
                    switch (e.keyCode) {
                        case wijmo.Key.Back:
                            if (this._tbx.value.length === 0 &&
                                this._lastInputValue.length === 0) {
                                this._delItem(false);
                            }
                            break;
                        case wijmo.Key.Delete:
                            this._delItem(true);
                            break;
                    }
                }
            };
            // add helper input element to handle focus
            MultiAutoComplete.prototype._addHelperInput = function () {
                var input = document.createElement("input");
                input.type = 'text';
                input.tabIndex = -1;
                input.className = 'wj-token-helper';
                input.readOnly = true;
                this._wjTpl.insertBefore(input, this._wjInput);
                this._helperInput = input;
            };
            // refresh the header to display the selected items
            MultiAutoComplete.prototype._refreshHeader = function () {
                // clear the token fields
                var tokenFields = this.hostElement.querySelectorAll('.wj-token');
                for (var i = 0; i < tokenFields.length; i++) {
                    this._wjTpl.removeChild(tokenFields[i]);
                }
                // when loading the first item will show in the header, so clear it.
                var items = this.selectedItems;
                if (!items || items.length === 0) {
                    return;
                }
                // add items to wj-template part
                for (var i = 0; i < items.length; i++) {
                    this._insertToken(items[i]);
                }
                this._wjInput.style.cssFloat = this.rightToLeft ? 'right' : 'left';
                // adjust input width and be done
                this._adjustInputWidth();
            };
            // insert token into template
            MultiAutoComplete.prototype._insertToken = function (item) {
                var tokenTxt = this._getItemText(item, true); // TFS 253890
                if (this.isContentHtml) {
                    if (!this._cvt) {
                        this._cvt = document.createElement('div');
                    }
                    this._cvt.innerHTML = tokenTxt;
                    tokenTxt = this._cvt.textContent.trim();
                }
                else {
                    tokenTxt = wijmo.escapeHtml(tokenTxt);
                }
                this._wjTpl.insertBefore(this._createItem(tokenTxt), this._wjInput);
            };
            // enforce maximum number of selected items
            MultiAutoComplete.prototype._updateMaxItems = function () {
                if (this._maxtems == null || !this._selItems) {
                    return;
                }
                if (this._selItems.length > this._maxtems) {
                    this._selItems = this._selItems.slice(0, this._maxtems);
                }
            };
            // update the control focus state
            MultiAutoComplete.prototype._updateFocus = function () {
                var _this = this;
                var activeToken = this._wjTpl.querySelector('.' + MultiAutoComplete._clsActive);
                if (activeToken) {
                    wijmo.removeClass(activeToken, MultiAutoComplete._clsActive);
                    setTimeout(function () {
                        _this._tbx.focus();
                    });
                }
                else {
                    this._clearSelIndex();
                    wijmo.removeClass(this.hostElement, 'wj-state-focused');
                }
            };
            // add an item
            MultiAutoComplete.prototype._addItem = function (clearSelected) {
                // filter duplicate items
                if (this.selectedItems.indexOf(this.selectedItem) > -1) {
                    this._clearSelIndex();
                    return;
                }
                if (this.selectedIndex > -1) {
                    this._updateSelItems(this.selectedItem, true);
                    this._refreshHeader();
                    if (clearSelected) {
                        this._clearSelIndex();
                    }
                    this._disableInput(true);
                }
            };
            // delete an item
            MultiAutoComplete.prototype._delItem = function (isDelKey) {
                // get active token
                var activeToken = this._wjTpl.querySelector('.' + MultiAutoComplete._clsActive), delItem, curIdx, selectedItmsChanged = false;
                // sanity
                if (isDelKey && !activeToken) {
                    return;
                }
                if (activeToken) {
                    curIdx = this._getItemIndex(activeToken);
                    if (curIdx > -1) {
                        delItem = this._selItems[curIdx];
                        selectedItmsChanged = true;
                    }
                }
                else {
                    if (this._selItems.length > 0) {
                        delItem = this._selItems[this._selItems.length - 1];
                        selectedItmsChanged = true;
                    }
                }
                // update selectedItems and refresh header
                if (selectedItmsChanged) {
                    this._updateSelItems(delItem, false);
                    this._refreshHeader();
                    this._clearSelIndex();
                    this._disableInput(false);
                }
                // focus back to input element
                this._tbx.focus();
            };
            // update the selected items
            MultiAutoComplete.prototype._updateSelItems = function (itm, isAdd) {
                if (isAdd) {
                    if (!this._selItems || this._selItems.length === 0) {
                        this._selItems = [];
                    }
                    if (this._maxtems != null &&
                        this._selItems.length >= this._maxtems) {
                        return;
                    }
                    this._selItems.push(itm);
                }
                else {
                    var idx = this._selItems.indexOf(itm);
                    this._selItems.splice(idx, 1);
                }
                if (this._hasSelectedMemeberPath()) {
                    this._setSelItem(itm, isAdd);
                }
                this.onSelectedItemsChanged();
            };
            // create a single item
            MultiAutoComplete.prototype._createItem = function (tokenTxt) {
                var _this = this;
                var container = document.createElement("div"), tSpan = document.createElement("span"), closeBtn = document.createElement("a");
                container.appendChild(tSpan);
                container.appendChild(closeBtn);
                container.className = 'wj-token';
                tSpan.className = 'wj-token-label';
                tSpan.innerHTML = tokenTxt;
                closeBtn.className = 'wj-token-close';
                closeBtn.href = '#';
                closeBtn.tabIndex = -1;
                closeBtn.text = '×';
                container.style.cssFloat = this.rightToLeft ? 'right' : 'left';
                this.addEventListener(container, 'click', function (e) {
                    _this._helperInput.focus();
                    var activeToken = _this._wjTpl.querySelector('.' + MultiAutoComplete._clsActive);
                    if (activeToken) {
                        wijmo.removeClass(activeToken, MultiAutoComplete._clsActive);
                    }
                    wijmo.addClass(container, MultiAutoComplete._clsActive);
                    e.stopPropagation();
                    e.preventDefault();
                });
                this.addEventListener(closeBtn, 'click', function (e) {
                    if (_this.isReadOnly) {
                        return;
                    }
                    var idx = _this._getItemIndex(container);
                    if (idx > -1) {
                        var delItem = _this._selItems[idx];
                        _this._updateSelItems(delItem, false);
                    }
                    _this._wjTpl.removeChild(container);
                    _this._adjustInputWidth();
                    _this._disableInput(false);
                    _this._tbx.focus();
                    e.stopPropagation();
                    e.preventDefault();
                });
                return container;
            };
            // activate the item
            MultiAutoComplete.prototype._itemOn = function (isPrev) {
                var ae = wijmo.getActiveElement(), tokes, activeToken, activeTokenIdx;
                if (this._tbx == ae &&
                    this._tbx.value.length !== 0) {
                    return;
                }
                // get all tokens
                tokes = this._wjTpl.querySelectorAll('.wj-token');
                if (tokes.length === 0) {
                    return;
                }
                // get active tokens
                activeToken = this._wjTpl.querySelector('.' + MultiAutoComplete._clsActive);
                activeTokenIdx = this._getItemIndex(activeToken);
                if (isPrev) {
                    if (activeTokenIdx === 0) {
                        return;
                    }
                    if (activeTokenIdx === -1) {
                        wijmo.addClass(tokes[tokes.length - 1], MultiAutoComplete._clsActive);
                        this._helperInput.focus();
                    }
                    else {
                        wijmo.removeClass(activeToken, MultiAutoComplete._clsActive);
                        wijmo.addClass(tokes[activeTokenIdx - 1], MultiAutoComplete._clsActive);
                        this._helperInput.focus();
                    }
                }
                else if (!isPrev) {
                    if (activeTokenIdx === -1) {
                        return;
                    }
                    if (activeTokenIdx !== tokes.length - 1) {
                        wijmo.removeClass(activeToken, MultiAutoComplete._clsActive);
                        wijmo.addClass(tokes[activeTokenIdx + 1], MultiAutoComplete._clsActive);
                        this._helperInput.focus();
                    }
                    else {
                        wijmo.removeClass(activeToken, MultiAutoComplete._clsActive);
                        this._tbx.focus();
                    }
                }
            };
            // deactivate the currently active item
            MultiAutoComplete.prototype._itemOff = function () {
                var token = this._wjTpl.querySelector('.' + MultiAutoComplete._clsActive);
                if (token) {
                    wijmo.removeClass(token, MultiAutoComplete._clsActive);
                }
            };
            // initialize the selectedItems when control initializes
            MultiAutoComplete.prototype._initSeltems = function () {
                if (this.selectedMemberPath && this.selectedMemberPath !== '') {
                    var cv = this.itemsSource;
                    this._selItems.splice(0, this._selItems.length);
                    if (cv) {
                        for (var i = 0; i < cv.sourceCollection.length; i++) {
                            if (this._getSelItem(i)) {
                                this._selItems.push(cv.sourceCollection[i]);
                            }
                        }
                    }
                }
            };
            // get selected item
            MultiAutoComplete.prototype._getSelItem = function (index) {
                var cv = this.itemsSource.sourceCollection, item = cv[index];
                if (wijmo.isObject(item) && this.selectedMemberPath) {
                    return this._selPath.getValue(item);
                }
                return false;
            };
            // set selected item
            MultiAutoComplete.prototype._setSelItem = function (item, selected) {
                var cv = this.itemsSource;
                if (wijmo.isObject(item)) {
                    if (this._selPath.getValue(item) != selected) {
                        this._selPath.setValue(item, selected);
                        //cv.refresh();
                    }
                }
            };
            // clear the selected index
            MultiAutoComplete.prototype._clearSelIndex = function () {
                this.selectedIndex = -1;
            };
            // check the SelectedMemeberPath
            MultiAutoComplete.prototype._hasSelectedMemeberPath = function () {
                return this.selectedMemberPath && this.selectedMemberPath !== '';
            };
            // disable the input field
            MultiAutoComplete.prototype._disableInput = function (disabled) {
                if (this._maxtems != null) {
                    if (this._selItems.length < this._maxtems) {
                        this._tbx.disabled = false;
                        this._tbx.focus();
                    }
                    else {
                        this._tbx.disabled = true;
                        this.hostElement.focus();
                    }
                }
            };
            // adjust the input width
            MultiAutoComplete.prototype._adjustInputWidth = function () {
                // first set the input width to min width
                this._tbx.style.width = '60px';
                var width, offsetHost = wijmo.getElementRect(this.hostElement), offsetInput = wijmo.getElementRect(this._tbx), inputCss = getComputedStyle(this._tbx), inputPaddingLeft = parseInt(inputCss.paddingLeft, 10), inputPaddingRight = parseInt(inputCss.paddingRight, 10);
                if (this.rightToLeft) {
                    width = offsetInput.left + offsetInput.width - offsetHost.left -
                        inputPaddingLeft - inputPaddingRight - 8;
                }
                else {
                    width = offsetHost.left + offsetHost.width - offsetInput.left -
                        inputPaddingLeft - inputPaddingRight - 8;
                }
                this._tbx.style.width = width + 'px';
            };
            // get the index of an item
            MultiAutoComplete.prototype._getItemIndex = function (token) {
                var items = this.hostElement.querySelectorAll('.wj-token');
                for (var i = 0; i < items.length; i++) {
                    if (token === items[i]) {
                        return i;
                    }
                }
                return -1;
            };
            MultiAutoComplete._clsActive = 'wj-token-active';
            return MultiAutoComplete;
        }(input_1.AutoComplete));
        input_1.MultiAutoComplete = MultiAutoComplete;
    })(input = wijmo.input || (wijmo.input = {}));
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
    var input;
    (function (input) {
        'use strict';
        /**
         * Specifies actions that trigger showing and hiding @see:Popup controls.
         */
        var PopupTrigger;
        (function (PopupTrigger) {
            /** No triggers; popups must be shown and hidden using code. */
            PopupTrigger[PopupTrigger["None"] = 0] = "None";
            /** Show or hide the popup when the owner element is clicked. */
            PopupTrigger[PopupTrigger["Click"] = 1] = "Click";
            /** Hide the popup when it loses focus. */
            PopupTrigger[PopupTrigger["Blur"] = 2] = "Blur";
            /** Show or hide the popup when the owner element is clicked, hide when it loses focus. */
            PopupTrigger[PopupTrigger["ClickOrBlur"] = 3] = "ClickOrBlur";
        })(PopupTrigger = input.PopupTrigger || (input.PopupTrigger = {}));
        /**
         * Class that shows an element as a popup.
         *
         * Popups may be have @see:owner elements, in which case they behave
         * as rich tooltips that may be shown or hidden based on actions
         * specified by the @see:Popup.showTrigger and @see:Popup.hideTrigger
         * properties.
         *
         * Popups with no owner elements behave like dialogs. They are centered
         * on the screen and displayed using the @see:show method.
         *
         * To close a @see:Popup, call the @see:Popup.hide method.
         *
         * Alternatively, any clickable elements within a @see:Popup that have
         * the classes starting with the 'wj-hide' string will hide the @see:Popup
         * when clicked and will set the @see:Popup.dialogResult property to the
         * class name so the caller may take appropriate action.
         *
         * For example, the @see:Popup below will be hidden when the user presses
         * the OK or Cancel buttons, and the @see:Popup.dialogResult property will
         * be set to either 'wj-hide-cancel' or 'wj-hide-ok':
         *
         * <pre>&lt;button id="btnPopup"&gt;Show Popup&lt;/button&gt;
         * &lt;wj-popup owner="#btnPopup" style="padding:12px"&gt;
         *   &lt;p&gt;Press one of the buttons below to hide the Popup.&lt;/p&gt;
         *   &lt;hr/&gt;
         *   &lt;button class="wj-hide-ok" ng-click="handleOK()"&gt;OK&lt;/button&gt;
         *   &lt;button class="wj-hide-cancel"&gt;Cancel&lt;/button&gt;
         * &lt;/wj-popup&gt;</pre>
         */
        var Popup = /** @class */ (function (_super) {
            __extends(Popup, _super);
            /**
             * Initializes a new instance of the @see:Popup class.
             *
             * @param element The DOM element that will host the control, or a selector for the host element (e.g. '#theCtrl').
             * @param options JavaScript object containing initialization data for the control.
             */
            function Popup(element, options) {
                var _this = _super.call(this, element, null, true) || this;
                _this._showTrigger = PopupTrigger.Click;
                _this._hideTrigger = PopupTrigger.Blur;
                _this._fadeIn = true;
                _this._fadeOut = true;
                _this._removeOnHide = true;
                _this._click = _this._handleClick.bind(_this);
                _this._mousedown = _this._handleMouseDown.bind(_this);
                _this._visible = false; // to report correctly while fading out
                /**
                 * Occurs before the @see:Popup is shown.
                 */
                _this.showing = new wijmo.Event();
                /**
                 * Occurs after the @see:Popup has been shown.
                 */
                _this.shown = new wijmo.Event();
                /**
                 * Occurs before the @see:Popup is hidden.
                 */
                _this.hiding = new wijmo.Event();
                /**
                 * Occurs after the @see:Popup has been hidden.
                 */
                _this.hidden = new wijmo.Event();
                var host = _this.hostElement;
                // add classes
                wijmo.addClass(host, 'wj-control wj-content wj-popup');
                // ensure the host element can get the focus (TFS 199312)
                if (!host.getAttribute('tabindex')) {
                    host.tabIndex = 0;
                }
                // start hidden
                wijmo.hidePopup(host, false);
                // keep track of IME composition status
                _this.addEventListener(host, 'compositionstart', function (e) {
                    _this._composing = true;
                });
                _this.addEventListener(host, 'compositionend', function (e) {
                    _this._composing = false;
                });
                // hide Popup when user presses Escape or Enter keys
                _this.addEventListener(host, 'keydown', function (e) {
                    if (!e.defaultPrevented) {
                        // Escape: hide the popup with no dialogResult
                        // (if not composing: TFS 286794)
                        if (e.keyCode == wijmo.Key.Escape && !_this._composing) {
                            e.preventDefault();
                            _this.hide();
                        }
                        // Enter: hide the popup and provide a dialogResult
                        if (e.keyCode == wijmo.Key.Enter) {
                            var result = _this.dialogResultEnter;
                            if (result) {
                                e.preventDefault();
                                _this._validateAndHide(result);
                            }
                        }
                        // Tab: keep focus within modal popups (TFS 148651)
                        if (e.keyCode == wijmo.Key.Tab && _this.modal) {
                            e.preventDefault();
                            wijmo.moveFocus(host, e.shiftKey ? -1 : +1);
                        }
                    }
                });
                // hide Popup when user clicks an element with the 'wj-hide' class
                _this.addEventListener(host, 'click', function (e) {
                    if (e.target instanceof HTMLElement) {
                        var target = e.target, match = target.className.match(/\bwj-hide[\S]*\b/);
                        if (match && match.length > 0) {
                            e.preventDefault(); // cancel any navigation
                            e.stopPropagation();
                            _this.hide(match[0]); // hide and pass the attribute as the dialogResult
                        }
                    }
                });
                // limit wheel propagation while modals are open
                _this.addEventListener(document, 'wheel', function (e) {
                    if (_this.isVisible && _this._modal) {
                        for (var t = e.target; t && t != document.body; t = t.parentElement) {
                            if (t.scrollHeight > t.clientHeight) {
                                return;
                            }
                        }
                        e.preventDefault();
                        e.stopPropagation();
                    }
                });
                // apply options after control is fully initialized
                _this.initialize(options);
                return _this;
            }
            Object.defineProperty(Popup.prototype, "owner", {
                // ** object model
                /**
                 * Gets or sets the element that owns this @see:Popup.
                 *
                 * If the @see:owner is null, the @see:Popup behaves like a dialog.
                 * It is centered on the screen and must be shown using the
                 * @see:show method.
                 */
                get: function () {
                    return this._owner;
                },
                set: function (value) {
                    // disconnect previous owner
                    if (this._owner) {
                        this.removeEventListener(this._owner, 'mousedown');
                        this.removeEventListener(this._owner, 'click');
                    }
                    // set new owner
                    this._owner = value != null ? wijmo.getElement(value) : null;
                    // connect new owner
                    if (this._owner) {
                        this.addEventListener(this._owner, 'mousedown', this._mousedown, true);
                        this.addEventListener(this._owner, 'click', this._click, true);
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(Popup.prototype, "content", {
                /**
                 * Gets or sets the HTML element contained in this @see:Popup.
                 */
                get: function () {
                    return this.hostElement.firstElementChild;
                },
                set: function (value) {
                    if (value != this.content) {
                        this.hostElement.innerHTML = '';
                        if (value instanceof HTMLElement) {
                            this.hostElement.appendChild(value);
                        }
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(Popup.prototype, "showTrigger", {
                /**
                 * Gets or sets the actions that show the @see:Popup.
                 *
                 * By default, the @see:showTrigger property is set to @see:PopupTrigger.Click,
                 * which causes the popup to appear when the user clicks the owner element.
                 *
                 * If you set the @see:showTrigger property to @see:PopupTrigger.None, the popup
                 * will be shown only when the @see:show method is called.
                 */
                get: function () {
                    return this._showTrigger;
                },
                set: function (value) {
                    this._showTrigger = wijmo.asEnum(value, PopupTrigger);
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(Popup.prototype, "hideTrigger", {
                /**
                 * Gets or sets the actions that hide the @see:Popup.
                 *
                 * By default, the @see:hideTrigger property is set to @see:PopupTrigger.Blur,
                 * which hides the popup when it loses focus.
                 *
                 * If you set the @see:hideTrigger property to @see:PopupTrigger.Click, the popup
                 * will be hidden only when the owner element is clicked.
                 *
                 * If you set the @see:hideTrigger property to @see:PopupTrigger.None, the popup
                 * will be hidden only when the @see:hide method is called.
                 */
                get: function () {
                    return this._hideTrigger;
                },
                set: function (value) {
                    this._hideTrigger = wijmo.asEnum(value, PopupTrigger);
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(Popup.prototype, "fadeIn", {
                /**
                 * Gets or sets a value that determines whether the @see:Popup should
                 * use a fade-out animation when it is shown.
                 */
                get: function () {
                    return this._fadeIn;
                },
                set: function (value) {
                    this._fadeIn = wijmo.asBoolean(value);
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(Popup.prototype, "fadeOut", {
                /**
                 * Gets or sets a value that determines whether the @see:Popup should
                 * use a fade-out animation when it is hidden.
                 */
                get: function () {
                    return this._fadeOut;
                },
                set: function (value) {
                    this._fadeOut = wijmo.asBoolean(value);
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(Popup.prototype, "removeOnHide", {
                /**
                 * Gets or sets a value that determines whether the @see:Popup element
                 * should be removed from the DOM when the @see:Popup is hidden, as
                 * opposed to being hidden.
                 *
                 * This property is set to true by default.
                 */
                get: function () {
                    return this._removeOnHide;
                },
                set: function (value) {
                    this._removeOnHide = wijmo.asBoolean(value);
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(Popup.prototype, "modal", {
                /**
                 * Gets or sets a value that determines whether the @see:Popup should
                 * be displayed as a modal dialog.
                 *
                 * Modal dialogs show a dark backdrop that makes the @see:Popup stand
                 * out from other content on the page.
                 *
                 * If you want to make a dialog truly modal, also set the @see:Popup.hideTrigger
                 * property to @see:PopupTrigger.None, so users won't be able to click the
                 * backdrop to dismiss the dialog. In this case, the dialog will close only
                 * if the @see:Popup.hide method is called or if the user presses the Escape
                 * key.
                 */
                get: function () {
                    return this._modal;
                },
                set: function (value) {
                    this._modal = wijmo.asBoolean(value);
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(Popup.prototype, "isDraggable", {
                /**
                 * Gets or sets a value that determines whether the popup can be dragged
                 * with the mouse by its header.
                 *
                 * The header is identified by the '.wj-dialog-header' CSS selector.
                 * If the dialog does not contain any elements with the 'wj-dialog-header'
                 * class, user will not be able to drag the popup.
                 *
                 * When making popups draggable, you may want to set the cursor property
                 * of the '.wj-dialog-header' CSS selector. For example:
                 *
                 * <pre>
                 * &lt;style&gt;
                 *   .wj-popup {
                 *     width: 30%;
                 *   }
                 *   .wj-dialog-header {
                 *     cursor: move;
                 *   }
                 * &lt;/style&gt;
                 * </pre>
                 */
                get: function () {
                    return this._draggable;
                },
                set: function (value) {
                    this._draggable = wijmo.asBoolean(value);
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(Popup.prototype, "dialogResult", {
                /**
                 * Gets or sets a value that can be used for handling the content of the @see:Popup
                 * after it is hidden.
                 *
                 * This property is set to null when the @see:Popup is displayed, and it can be
                 * set in response to button click events or in the call to the @see:hide method.
                 */
                get: function () {
                    return this._result;
                },
                set: function (value) {
                    this._result = value;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(Popup.prototype, "dialogResultEnter", {
                /**
                 * Gets or sets a value to be used as a @see:dialogResult when the user presses
                 * the Enter key while the @see:Popup is visible.
                 *
                 * If the user presses Enter and the @see:dialogResultEnter property is not null,
                 * the popup checks whether all its child elements are in a valid state.
                 * If so, the popup is closed and the @see:dialogResult property is set to
                 * the value of the @see:dialogResultEnter property.
                 */
                get: function () {
                    return this._resultEnter;
                },
                set: function (value) {
                    this._resultEnter = value;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(Popup.prototype, "isVisible", {
                /**
                 * Gets a value that determines whether the @see:Popup is currently visible.
                 */
                get: function () {
                    var host = this.hostElement;
                    return this._visible && host != null && host.offsetHeight > 0;
                    //return this._visible && host && host.parentElement && host.style.display != 'none';
                },
                enumerable: true,
                configurable: true
            });
            /**
             * Shows the @see:Popup.
             *
             * @param modal Whether to show the popup as a modal dialog. If provided, this
             * sets the value of the @see:modal property.
             * @param handleResult Callback invoked when the popup is hidden. If provided,
             * this should be a function that receives the popup as a parameter.
             *
             * The <b>handleResult</b> callback allows callers to handle the result of modal
             * dialogs without attaching handlers to the @see:hidden event. For example,
             * the code below shows a dialog used to edit the current item in a
             * @see:CollectionView. The edits are committed or canceled depending on the
             * @see:Popup.dialogResult value. For example:
             *
             * <pre>$scope.editCurrentItem = function () {
             *   $scope.data.editItem($scope.data.currentItem);
             *   $scope.itemEditor.show(true, function (e) {
             *     if (e.dialogResult == 'wj-hide-ok') {
             *       $scope.data.commitEdit();
             *     } else {
             *       $scope.data.cancelEdit();
             *     }
             *   });
             * }</pre>
             */
            Popup.prototype.show = function (modal, handleResult) {
                var _this = this;
                if (!this.isVisible) {
                    var host_1 = this.hostElement;
                    // reset dialog result/callback
                    this.dialogResult = null;
                    this._callback = null;
                    // suspend any pending hide animations (TFS 294608)
                    if (this._hideAnim) {
                        clearInterval(this._hideAnim);
                        this._hideAnim = null;
                    }
                    // raise the event
                    var e = new wijmo.CancelEventArgs();
                    if (this.onShowing(e)) {
                        // honor parameters
                        if (modal != null) {
                            this.modal = wijmo.asBoolean(modal);
                        }
                        if (handleResult != null) {
                            this._callback = wijmo.asFunction(handleResult);
                        }
                        // show the popup
                        wijmo.showPopup(host_1, this._owner, false, this._fadeIn, false);
                        // show modal backdrop behind the popup
                        if (this._modal) {
                            this._showBackdrop();
                        }
                        // raise shown event
                        this._composing = false;
                        this._visible = true;
                        this.onShown(e);
                        // if modal, get the focus when the window does (TFS 267199, 267199)
                        if (this.modal) {
                            this.addEventListener(window, 'focus', function () {
                                if (!_this.containsFocus()) {
                                    wijmo.moveFocus(host_1, 0);
                                }
                            });
                        }
                        // make dialog draggable
                        this._makeDraggable(this._draggable);
                        // and get the focus now
                        setTimeout(function () {
                            // if this is not a touch event, set the focus to the 'autofocus' element 
                            // or to the first focusable element on the popup
                            if (!_this.isTouching) {
                                var el = host_1.querySelector('input[autofocus]');
                                if (el && el.clientHeight > 0 && // ignore disabled, unfocusable, hidden
                                    !el.disabled && el.tabIndex > -1 &&
                                    !wijmo.closest(el, '[disabled],.wj-state-disabled')) {
                                    el.focus();
                                    el.select(); // TFS 190336
                                }
                                else {
                                    wijmo.moveFocus(host_1, 0);
                                }
                            }
                            // make sure the popup has the focus (no input elements/touch: TFS 143114)
                            if (!_this.containsFocus()) {
                                host_1.tabIndex = 0;
                                host_1.focus();
                            }
                        }, 200);
                    }
                }
            };
            /**
             * Hides the @see:Popup.
             * @param dialogResult Optional value assigned to the @see:dialogResult property
             * before closing the @see:Popup.
             */
            Popup.prototype.hide = function (dialogResult) {
                var _this = this;
                // no longer draggable
                this._makeDraggable(false);
                // hide if visible
                if (this.isVisible) {
                    if (!wijmo.isUndefined(dialogResult)) {
                        this.dialogResult = dialogResult;
                    }
                    var e_1 = new wijmo.CancelEventArgs();
                    if (this.onHiding(e_1)) {
                        // close any open drop-downs (just in case, TFS 152950)
                        var ddh = this.hostElement.querySelectorAll('.wj-control.wj-dropdown');
                        for (var i = 0; i < ddh.length; i++) {
                            var dd = wijmo.Control.getControl(ddh[i]);
                            if (dd instanceof input.DropDown) {
                                dd.isDroppedDown = false;
                            }
                        }
                        // hide the popup
                        if (this._modal) {
                            wijmo.hidePopup(this._bkdrop, this.removeOnHide, this.fadeOut);
                        }
                        this._hideAnim = wijmo.hidePopup(this.hostElement, this.removeOnHide, this.fadeOut);
                        this._visible = false;
                        // clean up/update state (TFS 269434)
                        this.removeEventListener(window, 'focus');
                        if (this.containsFocus()) {
                            document.activeElement.blur();
                        }
                        setTimeout(function () {
                            _this._updateState();
                            _this.onHidden(e_1);
                            if (_this._callback) {
                                _this._callback(_this);
                            }
                        });
                    }
                }
            };
            /**
             * Raises the @see:showing event.
             */
            Popup.prototype.onShowing = function (e) {
                this.showing.raise(this, e);
                return !e.cancel;
            };
            /**
             * Raises the @see:shown event.
             */
            Popup.prototype.onShown = function (e) {
                this.shown.raise(this, e);
            };
            /**
             * Raises the @see:hiding event.
             */
            Popup.prototype.onHiding = function (e) {
                this.hiding.raise(this, e);
                return !e.cancel;
            };
            /**
             * Raises the @see:hidden event.
             */
            Popup.prototype.onHidden = function (e) {
                this.hidden.raise(this, e);
            };
            // ** overrides
            // release owner when disposing
            Popup.prototype.dispose = function () {
                this._owner = null;
                _super.prototype.dispose.call(this);
            };
            // hide popup when popup loses focus
            Popup.prototype.onLostFocus = function (e) {
                if (this.isVisible && (this._hideTrigger & PopupTrigger.Blur)) {
                    if (!this.containsFocus()) {
                        this.hide();
                    }
                }
                _super.prototype.onLostFocus.call(this, e);
            };
            // reposition Popup when refreshing
            Popup.prototype.refresh = function (fullUpdate) {
                if (fullUpdate === void 0) { fullUpdate = true; }
                _super.prototype.refresh.call(this, fullUpdate);
                if (this.isVisible && !this._refreshing) {
                    this._refreshing = true;
                    var ae = wijmo.getActiveElement(), ref = this._owner ? this._owner.getBoundingClientRect() : null;
                    wijmo.showPopup(this.hostElement, ref);
                    if (this._modal && ae instanceof HTMLElement && ae != wijmo.getActiveElement()) {
                        ae.focus();
                    }
                    this._refreshing = false;
                }
            };
            // ** implementation
            // make dialog draggable or fixed
            Popup.prototype._makeDraggable = function (draggable) {
                var _this = this;
                var host = this.hostElement, hdr = host ? host.querySelector('.wj-dialog-header') : null, ptStart, ptDown, md = 'mousedown', mm = 'mousemove', mu = 'mouseup';
                // draggable event handlers
                var mouseDown = function (e) {
                    if (host && e.target == hdr) {
                        ptStart = new wijmo.Point(host.offsetLeft, host.offsetTop);
                        ptDown = new wijmo.Point(e.pageX, e.pageY);
                        _this.removeEventListener(document, mm);
                        _this.removeEventListener(document, mu);
                        _this.addEventListener(document, mm, mouseMove);
                        _this.addEventListener(document, mu, mouseUp);
                    }
                };
                var mouseMove = function (e) {
                    var x = Math.max(ptStart.x + (e.pageX - ptDown.x), 50 - host.offsetWidth), y = Math.max(ptStart.y + (e.pageY - ptDown.y), 0);
                    wijmo.setCss(host, {
                        left: x,
                        top: y,
                    });
                    _this._dragged = true;
                };
                var mouseUp = function (e) {
                    _this.removeEventListener(document, mm);
                    _this.removeEventListener(document, mu);
                };
                // add/remove the mousedown event listener
                this._dragged = false;
                this.removeEventListener(host, md, mouseDown);
                if (draggable && hdr) {
                    this.addEventListener(host, md, mouseDown);
                }
            };
            // reposition Popup when window size changes, 
            // unless the user has dragged the dialog
            Popup.prototype._handleResize = function () {
                if (this.isVisible && !this._dragged) {
                    this.refresh();
                }
            };
            // toggle Popup when user clicks the owner element
            Popup.prototype._handleClick = function (e) {
                if (this.isVisible) {
                    if (this._hideTrigger & PopupTrigger.Click) {
                        this.hide();
                    }
                }
                else {
                    if (this._showTrigger & PopupTrigger.Click) {
                        // don't show while fading out (in this case, visible is false 
                        // but host element is still visible on the page)
                        if (!this._wasVisible) {
                            this.show();
                        }
                    }
                }
            };
            // remember visible state on mouse down to avoid hiding and showing again on click
            // (mousedown loses focus, hides, mouseup triggers click, shows again)
            Popup.prototype._handleMouseDown = function (e) {
                this._wasVisible = this.isVisible;
            };
            // show/hide modal popup backdrop
            Popup.prototype._showBackdrop = function () {
                var _this = this;
                if (!this._bkdrop) {
                    // create backdrop element
                    this._bkdrop = document.createElement('div');
                    this._bkdrop.tabIndex = -1;
                    wijmo.addClass(this._bkdrop, 'wj-popup-backdrop');
                    // background is not clickable
                    this.addEventListener(this._bkdrop, 'mousedown', function (e) {
                        e.preventDefault();
                        e.stopPropagation();
                        _this.hostElement.focus(); // close any open menus/popups TFS 152950
                        if (_this.hideTrigger & PopupTrigger.Blur) {
                            _this.hide(); // hide if trigger has blur: TFS 245415, 245953
                        }
                    });
                }
                this._bkdrop.style.display = '';
                // insert background behind the popup (TFS 205400)
                var host = this.hostElement;
                host.parentElement.insertBefore(this._bkdrop, host);
            };
            // validate the dialog and hide it if there are no errors
            Popup.prototype._validateAndHide = function (result) {
                var invalid = this.hostElement.querySelector(':invalid');
                if (invalid) {
                    invalid.focus(); // focus to invalid field
                }
                else {
                    this.hide(result); // no errors
                }
            };
            return Popup;
        }(wijmo.Control));
        input.Popup = Popup;
    })(input = wijmo.input || (wijmo.input = {}));
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
    var input;
    (function (input) {
        'use strict';
        /**
         * The @see:InputDate control allows users to type in dates using any format
         * supported by the @see:Globalize class, or to pick dates from a drop-down
         * that contains a @see:Calendar control.
         *
         * Use the @see:min and @see:max properties to restrict the range of
         * values that the user can enter.
         *
         * For details about using the @see:min and @see:max properties, please see the
         * <a href="static/minMax.html">Using the min and max properties</a> topic.
         *
         * Use the @see:value property to gets or set the currently selected date.
         *
         * The example below shows a <b>Date</b> value (that includes date and time information)
         * using an @see:InputDate and an an @see:InputTime control. Notice how both controls
         * are bound to the same controller variable, and each edits the appropriate information
         * (either date or time). The example also shows a @see:Calendar control that you can
         * use to select the date with a single click.
         *
         * @fiddle:vgc3Y
         */
        var InputDate = /** @class */ (function (_super) {
            __extends(InputDate, _super);
            // private stuff
            /**
             * Initializes a new instance of the @see:InputDate class.
             *
             * @param element The DOM element that hosts the control, or a selector for the host element (e.g. '#theCtrl').
             * @param options The JavaScript object containing initialization data for the control.
             */
            function InputDate(element, options) {
                var _this = _super.call(this, element) || this;
                _this._format = 'd';
                /**
                 * Occurs when the value of the @see:value property changes, either
                 * as a result of user actions or by assignment in code.
                 */
                _this.valueChanged = new wijmo.Event();
                wijmo.addClass(_this.hostElement, 'wj-inputdate');
                // initialize mask provider
                _this._msk = new wijmo._MaskProvider(_this._tbx);
                // default to numeric keyboard (like InputNumber), unless this is IE9...
                if (!wijmo.isIE9()) {
                    _this.inputType = 'tel';
                }
                // don't use 'date' input type (TFS 84901)
                if (_this._tbx.type.match(/date/i)) {
                    _this.inputType = '';
                }
                // use wheel to increase/decrease the date
                _this.addEventListener(_this.hostElement, 'wheel', function (e) {
                    if (!e.defaultPrevented && !_this.isDroppedDown && _this.containsFocus()) {
                        if (_this.value != null && _this._canChangeValue()) {
                            var step = wijmo.clamp(-e.deltaY, -1, +1);
                            _this.value = _this.selectionMode == input.DateSelectionMode.Month
                                ? wijmo.DateTime.addMonths(_this.value, step)
                                : wijmo.DateTime.addDays(_this.value, step);
                            _this.selectAll();
                            e.preventDefault();
                        }
                    }
                });
                // initialize value (current date) TFS 193848
                _this.value = wijmo.DateTime.newDate();
                // initializing from <input> tag
                if (_this._orgTag == 'INPUT') {
                    var value = _this._tbx.getAttribute('value');
                    if (value) {
                        _this.value = wijmo.Globalize.parseDate(value, 'yyyy-MM-dd');
                    }
                }
                // initialize control options
                _this.isRequired = true;
                _this.initialize(options);
                return _this;
            }
            Object.defineProperty(InputDate.prototype, "value", {
                //--------------------------------------------------------------------------
                //#region ** object model
                /**
                 * Gets or sets the current date.
                 */
                get: function () {
                    return this._value;
                },
                set: function (value) {
                    if (wijmo.DateTime.equals(this._value, value)) {
                        this._tbx.value = wijmo.Globalize.format(value, this.format);
                    }
                    else {
                        // check type
                        value = wijmo.asDate(value, !this.isRequired || (value == null && this._value == null));
                        // honor min/max range
                        // REVIEW: should not clamp this...
                        value = this._clamp(value);
                        // update control text and value
                        if (this._isValidDate(value)) {
                            this._tbx.value = value ? wijmo.Globalize.format(value, this.format) : '';
                            if (value != this._value && !wijmo.DateTime.equals(this._value, value)) {
                                this._value = value;
                                this.onValueChanged();
                            }
                        }
                        else {
                            this._tbx.value = value ? wijmo.Globalize.format(this.value, this.format) : '';
                        }
                        // raise textChanged event
                        if (this.text != this._oldText) {
                            this._oldText = this.text; // TFS 228044
                            this.onTextChanged();
                        }
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(InputDate.prototype, "text", {
                /**
                 * Gets or sets the text shown on the control.
                 */
                get: function () {
                    return this._tbx.value;
                },
                set: function (value) {
                    if (value != this.text) {
                        this._setText(value, true);
                        this._commitText();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(InputDate.prototype, "selectionMode", {
                /**
                 * Gets or sets a value that indicates whether users can select
                 * days, months, or no values at all.
                 *
                 * This property affects the behavior of the drop-down calendar,
                 * but not the format used to display dates.
                 * If you set @see:selectionMode to 'Month', you should normally
                 * set the @see:format property to 'MMM yyyy' or some format that
                 * does not include the day. For example:
                 *
                 * <pre>var inputDate = new wijmo.input.InputDate('#el, {
                 *   selectionMode: 'Month',
                 *   format: 'MMM yyyy'
                 * });</pre>
                 */
                get: function () {
                    return this.calendar.selectionMode;
                },
                set: function (value) {
                    this.calendar.selectionMode = value;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(InputDate.prototype, "min", {
                /**
                 * Gets or sets the earliest date that the user can enter.
                 *
                 * For details about using the @see:min and @see:max properties, please see the
                 * <a href="static/minMax.html">Using the min and max properties</a> topic.
                 */
                get: function () {
                    return this._calendar.min;
                },
                set: function (value) {
                    this._calendar.min = wijmo.asDate(value, true);
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(InputDate.prototype, "max", {
                /**
                 * Gets or sets the latest date that the user can enter.
                 *
                 * For details about using the @see:min and @see:max properties, please see the
                 * <a href="static/minMax.html">Using the min and max properties</a> topic.
                 */
                get: function () {
                    return this._calendar.max;
                },
                set: function (value) {
                    this._calendar.max = wijmo.asDate(value, true);
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(InputDate.prototype, "format", {
                /**
                 * Gets or sets the format used to display the selected date.
                 *
                 * The format string is expressed as a .NET-style
                 * <a href="http://msdn.microsoft.com/en-us/library/8kb3ddd4(v=vs.110).aspx" target="_blank">
                 * Date format string</a>.
                 */
                get: function () {
                    return this._format;
                },
                set: function (value) {
                    if (value != this.format) {
                        this._format = wijmo.asString(value);
                        this._tbx.value = wijmo.Globalize.format(this.value, this.format);
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(InputDate.prototype, "mask", {
                /**
                 * Gets or sets a mask to use while editing.
                 *
                 * The mask format is the same one that the @see:wijmo.input.InputMask
                 * control uses.
                 *
                 * If specified, the mask must be compatible with the value of
                 * the @see:format property. For example, the mask '99/99/9999' can
                 * be used for entering dates formatted as 'MM/dd/yyyy'.
                 */
                get: function () {
                    return this._msk.mask;
                },
                set: function (value) {
                    this._msk.mask = wijmo.asString(value);
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(InputDate.prototype, "calendar", {
                /**
                 * Gets a reference to the @see:Calendar control shown in the drop-down box.
                 */
                get: function () {
                    return this._calendar;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(InputDate.prototype, "inputElement", {
                /**
                 * Gets the HTML input element hosted by the control.
                 *
                 * Use this property in situations where you want to customize the
                 * attributes of the input element.
                 */
                get: function () {
                    return this._tbx;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(InputDate.prototype, "inputType", {
                /**
                 * Gets or sets the "type" attribute of the HTML input element hosted by the control.
                 *
                 * By default, this property is set to "tel", a value that causes mobile devices to
                 * show a numeric keypad that includes a negative sign and a decimal separator.
                 *
                 * Use this property to change the default setting if the default does not work well
                 * for the current culture, device, or application. In those cases, try changing
                 * the value to "number" or "text."
                 *
                 * Note that input elements with type "number" prevent selection in Chrome and therefore
                 * is not recommended. For more details, see this link:
                 * http://stackoverflow.com/questions/21177489/selectionstart-selectionend-on-input-type-number-no-longer-allowed-in-chrome
                 */
                get: function () {
                    return this._tbx.type;
                },
                set: function (value) {
                    this._tbx.type = wijmo.asString(value);
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(InputDate.prototype, "itemValidator", {
                /**
                 * Gets or sets a validator function to determine whether dates are valid for selection.
                 *
                 * If specified, the validator function should take one parameter representing the
                 * date to be tested, and should return false if the date is invalid and should not
                 * be selectable.
                 *
                 * For example, the code below prevents users from selecting dates that fall on
                 * weekends:
                 * <pre>
                 * inputDate.itemValidator = function(date) {
                 *   var weekday = date.getDay();
                 *   return weekday != 0 && weekday != 6;
                 * }
                 * </pre>
                 */
                get: function () {
                    return this._calendar.itemValidator;
                },
                set: function (value) {
                    if (value != this.itemValidator) {
                        this._calendar.itemValidator = wijmo.asFunction(value);
                        this.invalidate();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(InputDate.prototype, "itemFormatter", {
                /**
                 * Gets or sets a formatter function to customize dates in the drop-down calendar.
                 *
                 * The formatter function can add any content to any date. It allows
                 * complete customization of the appearance and behavior of the calendar.
                 *
                 * If specified, the function takes two parameters:
                 * <ul>
                 *     <li>the date being formatted </li>
                 *     <li>the HTML element that represents the date</li>
                 * </ul>
                 *
                 * For example, the code below shows weekends with a yellow background:
                 * <pre>
                 * inputDate.itemFormatter = function(date, element) {
                 *   var day = date.getDay();
                 *   element.style.backgroundColor = day == 0 || day == 6 ? 'yellow' : '';
                 * }
                 * </pre>
                 */
                get: function () {
                    return this.calendar.itemFormatter;
                },
                set: function (value) {
                    if (value != this.itemFormatter) {
                        this.calendar.itemFormatter = wijmo.asFunction(value);
                    }
                },
                enumerable: true,
                configurable: true
            });
            /**
             * Raises the @see:valueChanged event.
             */
            InputDate.prototype.onValueChanged = function (e) {
                this.valueChanged.raise(this, e);
            };
            //#endregion ** object model
            //--------------------------------------------------------------------------
            //#region ** overrides
            // update value display in case culture changed
            InputDate.prototype.refresh = function () {
                this.isDroppedDown = false;
                if (this._msk) {
                    this._msk.refresh();
                }
                if (this._calendar) {
                    this._calendar.refresh();
                }
                this._tbx.value = wijmo.Globalize.format(this.value, this.format);
            };
            // overridden to update calendar when dropping down
            InputDate.prototype.onIsDroppedDownChanged = function (e) {
                _super.prototype.onIsDroppedDownChanged.call(this, e);
                if (this.isDroppedDown) {
                    this._calChanged = false;
                    this.dropDown.focus();
                }
            };
            // create the drop-down element
            InputDate.prototype._createDropDown = function () {
                var _this = this;
                // create the drop-down element
                this._calendar = new input.Calendar(this._dropDown);
                //this._dropDown.tabIndex = -1;
                // update our value to match calendar's
                this._calendar.valueChanged.addHandler(function () {
                    _this.value = wijmo.DateTime.fromDateTime(_this._calendar.value, _this.value);
                    _this._calChanged = true; // remember change to close drop-down on click
                });
                // close the drop-down when the user changes the date with the mouse
                // the 'click' event may not be triggered on iOS Safari if focus change
                // happens during previous tap, so use 'mouseup' instead.
                //this.addEventListener(this._dropDown, 'click', () => {
                this.addEventListener(this._dropDown, 'mouseup', function (e) {
                    if (_this._calChanged && !wijmo.closest(e.target, '.wj-calendar-header')) {
                        _this.isDroppedDown = false;
                    }
                    else {
                        if (e.target.getAttribute('wj-part') == 'btn-today') {
                            _this.isDroppedDown = false;
                        }
                    }
                });
            };
            // update drop down content and position before showing it
            InputDate.prototype._updateDropDown = function () {
                // update value
                this._commitText();
                // update selected date, range
                var cal = this._calendar;
                cal.value = this.value;
                cal.min = this.min;
                cal.max = this.max;
                // update view
                if (this.selectionMode != input.DateSelectionMode.Month) {
                    cal.monthView = true;
                }
                // update size
                var cs = getComputedStyle(this.hostElement);
                this._dropDown.style.minWidth = parseFloat(cs.fontSize) * 18 + 'px';
                this._calendar.refresh(); // update layout/size now
                // let base class update position
                _super.prototype._updateDropDown.call(this);
            };
            // override to commit text on Enter and cancel on Escape
            InputDate.prototype._keydown = function (e) {
                if (!e.defaultPrevented && !e.altKey && !e.ctrlKey && !e.metaKey) {
                    switch (e.keyCode) {
                        case wijmo.Key.Enter:
                            this._commitText();
                            this.selectAll();
                            break;
                        case wijmo.Key.Escape:
                            this.text = wijmo.Globalize.format(this.value, this.format);
                            this.selectAll();
                            break;
                        case wijmo.Key.Up:
                        case wijmo.Key.Down:
                            if (!this.isDroppedDown && this.value && this._canChangeValue()) {
                                var step = e.keyCode == wijmo.Key.Up ? +1 : -1, value = this.selectionMode == input.DateSelectionMode.Month
                                    ? wijmo.DateTime.addMonths(this.value, step)
                                    : wijmo.DateTime.addDays(this.value, step);
                                this.value = wijmo.DateTime.fromDateTime(value, this.value); // set date, keep time
                                this.selectAll();
                                e.preventDefault();
                            }
                            break;
                    }
                }
                _super.prototype._keydown.call(this, e);
            };
            //#endregion ** overrides
            //--------------------------------------------------------------------------
            //#region ** implementation
            // checks whether the control can change the current value
            InputDate.prototype._canChangeValue = function () {
                return !this.isReadOnly && this.selectionMode != input.DateSelectionMode.None;
            };
            // honor min/max range
            InputDate.prototype._clamp = function (value) {
                return this.calendar._clamp(value);
            };
            // parse date, commit date part (no time) if successful or revert
            InputDate.prototype._commitText = function () {
                var txt = this._tbx.value;
                if (!txt && !this.isRequired) {
                    this.value = null;
                }
                else {
                    var dt = wijmo.Globalize.parseDate(txt, this.format);
                    if (dt) {
                        this.value = wijmo.DateTime.fromDateTime(dt, this.value);
                    }
                    else {
                        this._tbx.value = wijmo.Globalize.format(this.value, this.format);
                    }
                }
            };
            // check whether a date should be selectable by the user
            InputDate.prototype._isValidDate = function (value) {
                if (value) {
                    if (this._clamp(value) != value) {
                        return false;
                    }
                    if (this.itemValidator && !this.itemValidator(value)) {
                        return false;
                    }
                }
                return true;
            };
            return InputDate;
        }(input.DropDown));
        input.InputDate = InputDate;
    })(input = wijmo.input || (wijmo.input = {}));
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
    var input;
    (function (input) {
        'use strict';
        /**
         * The @see:InputTime control allows users to enter times using any format
         * supported by the @see:Globalize class, or to pick times from a drop-down
         * list.
         *
         * The @see:min, @see:max, and @see:step properties determine the values shown
         * in the list.
         *
         * For details about using the @see:min and @see:max properties, please see the
         * <a href="static/minMax.html">Using the min and max properties</a> topic.
         *
         * The @see:value property gets or sets a @see:Date object that represents the time
         * selected by the user.
         *
         * The example below shows a <b>Date</b> value (that includes date and time information)
         * using an @see:InputDate and an @see:InputTime control. Notice how both controls
         * are bound to the same controller variable, and each edits the appropriate information
         * (either date or time). The example also shows a @see:Calendar control that can be
         * used to select the date with a single click.
         *
         * @fiddle:vgc3Y
         */
        var InputTime = /** @class */ (function (_super) {
            __extends(InputTime, _super);
            /**
             * Initializes a new instance of the @see:InputTime class.
             *
             * @param element The DOM element that hosts the control, or a selector for the host element (e.g. '#theCtrl').
             * @param options The JavaScript object containing initialization data for the control.
             */
            function InputTime(element, options) {
                var _this = _super.call(this, element) || this;
                _this._format = 't';
                /**
                 * Occurs when the value of the @see:value property changes, either
                 * as a result of user actions or by assignment in code.
                 */
                _this.valueChanged = new wijmo.Event();
                wijmo.addClass(_this.hostElement, 'wj-inputtime');
                // initialize value (current date)
                _this._value = wijmo.DateTime.newDate();
                // initialize mask provider
                _this._msk = new wijmo._MaskProvider(_this._tbx);
                // default to numeric keyboard (like InputNumber), unless this is IE9...
                if (!wijmo.isIE9()) {
                    _this._tbx.type = 'tel';
                }
                // initializing from <input> tag
                if (_this._orgTag == 'INPUT') {
                    var value = _this._tbx.getAttribute('value');
                    if (value) {
                        _this.value = wijmo.Globalize.parseDate(value, 'HH:mm:ss');
                    }
                }
                // friendly defaults
                _this.step = 15;
                _this.autoExpandSelection = true;
                // initialize control options
                _this.initialize(options);
                return _this;
            }
            Object.defineProperty(InputTime.prototype, "inputElement", {
                //--------------------------------------------------------------------------
                //#region ** object model
                /**
                 * Gets the HTML input element hosted by the control.
                 *
                 * Use this property in situations where you want to customize the
                 * attributes of the input element.
                 */
                get: function () {
                    return this._tbx;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(InputTime.prototype, "inputType", {
                /**
                 * Gets or sets the "type" attribute of the HTML input element hosted by the control.
                 *
                 * By default, this property is set to "tel", a value that causes mobile devices to
                 * show a numeric keypad that includes a negative sign and a decimal separator.
                 *
                 * Use this property to change the default setting if the default does not work well
                 * for the current culture, device, or application. In those cases, try changing
                 * the value to "number" or "text."
                 *
                 * Note that input elements with type "number" prevent selection in Chrome and therefore
                 * is not recommended. For more details, see this link:
                 * http://stackoverflow.com/questions/21177489/selectionstart-selectionend-on-input-type-number-no-longer-allowed-in-chrome
                 */
                get: function () {
                    return this._tbx.type;
                },
                set: function (value) {
                    this._tbx.type = wijmo.asString(value);
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(InputTime.prototype, "value", {
                /**
                 * Gets or sets the current input time.
                 */
                get: function () {
                    return this._value;
                },
                set: function (value) {
                    // check type
                    value = wijmo.asDate(value, !this.isRequired);
                    // honor ranges (but keep the dates)
                    if (value) {
                        if (this._min != null && this._getTime(value) < this._getTime(this._min)) {
                            value = wijmo.DateTime.fromDateTime(value, this._min);
                        }
                        if (this._max != null && this._getTime(value) > this._getTime(this._max)) {
                            value = wijmo.DateTime.fromDateTime(value, this._max);
                        }
                    }
                    // update control
                    this._setText(value ? wijmo.Globalize.format(value, this.format) : '', true);
                    if (this.selectedItem && this.selectedItem.value) {
                        value = wijmo.DateTime.fromDateTime(value, this.selectedItem.value);
                    }
                    if (value != this._value && !wijmo.DateTime.equals(value, this._value)) {
                        this._value = value;
                        this.onValueChanged();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(InputTime.prototype, "text", {
                /**
                 * Gets or sets the text shown in the control.
                 */
                get: function () {
                    return this._tbx.value;
                },
                set: function (value) {
                    if (value != this.text) {
                        this._setText(value, true);
                        this._commitText();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(InputTime.prototype, "min", {
                /**
                 * Gets or sets the earliest time that the user can enter.
                 *
                 * For details about using the @see:min and @see:max properties, please see the
                 * <a href="static/minMax.html">Using the min and max properties</a> topic.
                 */
                get: function () {
                    return this._min;
                },
                set: function (value) {
                    this._min = wijmo.asDate(value, true);
                    this.isDroppedDown = false;
                    this._updateItems();
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(InputTime.prototype, "max", {
                /**
                 * Gets or sets the latest time that the user can enter.
                 *
                 * For details about using the @see:min and @see:max properties, please see the
                 * <a href="static/minMax.html">Using the min and max properties</a> topic.
                 */
                get: function () {
                    return this._max;
                },
                set: function (value) {
                    this._max = wijmo.asDate(value, true);
                    this.isDroppedDown = false;
                    this._updateItems();
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(InputTime.prototype, "step", {
                /**
                 * Gets or sets the number of minutes between entries in the drop-down list.
                 *
                 * The default value for this property is 15 minutes.
                 * Setting it to null, zero, or any negative value disables the drop-down.
                 */
                get: function () {
                    return this._step;
                },
                set: function (value) {
                    this._step = wijmo.asNumber(value, true);
                    this.isDroppedDown = false;
                    this._updateItems();
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(InputTime.prototype, "format", {
                /**
                 * Gets or sets the format used to display the selected time (see @see:Globalize).
                 *
                 * The format string is expressed as a .NET-style
                 * <a href="http://msdn.microsoft.com/en-us/library/8kb3ddd4(v=vs.110).aspx" target="_blank">
                 * time format string</a>.
                 */
                get: function () {
                    return this._format;
                },
                set: function (value) {
                    if (value != this.format) {
                        this._format = wijmo.asString(value);
                        this._tbx.value = wijmo.Globalize.format(this.value, this.format);
                        if (wijmo.hasItems(this.collectionView)) {
                            this._updateItems();
                        }
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(InputTime.prototype, "mask", {
                /**
                 * Gets or sets a mask to use while the user is editing.
                 *
                 * The mask format is the same used by the @see:wijmo.input.InputMask
                 * control.
                 *
                 * If specified, the mask must be compatible with the value of
                 * the @see:format property. For example, you can use the mask '99:99 >LL'
                 * for entering short times (format 't').
                 */
                get: function () {
                    return this._msk.mask;
                },
                set: function (value) {
                    this._msk.mask = wijmo.asString(value);
                },
                enumerable: true,
                configurable: true
            });
            /**
             * Raises the @see:valueChanged event.
             */
            InputTime.prototype.onValueChanged = function (e) {
                this.valueChanged.raise(this, e);
            };
            //#endregion ** object model
            //--------------------------------------------------------------------------
            //#region ** overrides
            // remember whether we have custom items on the list
            InputTime.prototype.onItemsSourceChanged = function (e) {
                _super.prototype.onItemsSourceChanged.call(this, e);
                this._hasCustomItems = this.itemsSource != null;
            };
            // set selection range in input element (if it is visible)
            InputTime.prototype._updateInputSelection = function (start) {
                if (this._delKey) {
                    _super.prototype._updateInputSelection.call(this, start);
                }
                else {
                    var val = this._tbx.value;
                    while (start < val.length && !val[start].match(/[a-z0-9]/i)) {
                        start++;
                    }
                    wijmo.setSelectionRange(this._tbx, start, this._tbx.value.length);
                }
            };
            // update value display in case culture changed
            InputTime.prototype.refresh = function () {
                this.isDroppedDown = false;
                this._msk.refresh();
                this._tbx.value = wijmo.Globalize.format(this.value, this.format);
                this._updateItems();
            };
            // commit changes when the user picks a value from the list
            InputTime.prototype.onSelectedIndexChanged = function (e) {
                if (this.selectedIndex > -1 && !this._settingText) {
                    var value = this.value ? this.value : wijmo.DateTime.newDate(), selValue = this.selectedItem.value != null
                        ? this.selectedItem.value
                        : wijmo.Globalize.parseDate(this.text, this.format);
                    this.value = wijmo.DateTime.fromDateTime(value, selValue);
                }
                _super.prototype.onSelectedIndexChanged.call(this, e);
            };
            // update items in drop-down list
            InputTime.prototype._updateItems = function () {
                // not if we have custom items
                if (this._hasCustomItems) {
                    return;
                }
                // populate the list
                var items = [], min = wijmo.DateTime.newDate(0, 0, 0, 0, 0), max = wijmo.DateTime.newDate(0, 0, 0, 23, 59, 59);
                if (this.min) {
                    min.setHours(this.min.getHours(), this.min.getMinutes(), this.min.getSeconds());
                }
                if (this.max) {
                    max.setHours(this.max.getHours(), this.max.getMinutes(), this.max.getSeconds());
                }
                if (wijmo.isNumber(this.step) && this.step > 0) {
                    for (var dt = min; dt <= max; dt = wijmo.DateTime.addMinutes(dt, this.step)) {
                        items.push({ value: dt, text: wijmo.Globalize.format(dt, this.format) });
                        //items.push(Globalize.format(dt, this.format));
                    }
                }
                // save current value
                var value = this.value;
                this._settingText = true;
                // update item source
                this.displayMemberPath = 'text';
                this.selectedValuePath = 'text';
                this.itemsSource = items;
                this._hasCustomItems = false;
                // restore value
                this._settingText = false;
                this.value = value;
            };
            //#endregion ** overrides
            //--------------------------------------------------------------------------
            //#region ** implementation
            // gets the time of day in seconds
            InputTime.prototype._getTime = function (value) {
                return value.getHours() * 3600 + value.getMinutes() * 60 + value.getSeconds();
            };
            // override to commit text on Enter and cancel on Escape
            InputTime.prototype._keydown = function (e) {
                _super.prototype._keydown.call(this, e);
                if (!e.defaultPrevented) {
                    switch (e.keyCode) {
                        case wijmo.Key.Enter:
                            if (!this.isDroppedDown) {
                                this._commitText();
                                this.selectAll();
                            }
                            break;
                        case wijmo.Key.Escape:
                            this.text = wijmo.Globalize.format(this.value, this.format);
                            this.selectAll();
                            break;
                    }
                }
            };
            // parse time, commit if successful or revert
            InputTime.prototype._commitText = function () {
                if (!this.text && !this.isRequired) {
                    this.value = null;
                }
                else {
                    var text = this.value ? wijmo.Globalize.format(this.value, this.format) : null;
                    if (this.text != text) {
                        var value = this.selectedItem && this.selectedItem.value
                            ? this.selectedItem.value // TFS 290187
                            : wijmo.Globalize.parseDate(this.text, this.format);
                        if (value) {
                            this.value = wijmo.DateTime.fromDateTime(this.value, value);
                        }
                        else {
                            this._tbx.value = text;
                        }
                    }
                }
            };
            return InputTime;
        }(input.ComboBox));
        input.InputTime = InputTime;
    })(input = wijmo.input || (wijmo.input = {}));
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
    var input;
    (function (input) {
        'use strict';
        /**
         * The @see:InputDateTime control extends the @see:InputDate control to allows users
         * to input dates and times, either by typing complete date/time values in any format
         * supported by the @see:Globalize class, or by picking dates from a drop-down calendar
         * and times from a drop-down list.
         *
         * Use the @see:InputDateTime.min and @see:InputDateTime.max properties to restrict
         * the range of dates that the user can enter.
         *
         * Use the @see:InputDateTime.timeMin and @see:InputDateTime.timeMax properties to
         * restrict the range of times that the user can enter.
         *
         * Use the @see:InputDateTime.value property to gets or set the currently selected
         * date/time.
         */
        var InputDateTime = /** @class */ (function (_super) {
            __extends(InputDateTime, _super);
            //--------------------------------------------------------------------------
            //#region ** ctor
            /**
             * Initializes a new instance of the @see:InputDateTime class.
             *
             * @param element The DOM element that hosts the control, or a selector for the host element (e.g. '#theCtrl').
             * @param options The JavaScript object containing initialization data for the control.
             */
            function InputDateTime(element, options) {
                var _this = _super.call(this, element) || this;
                wijmo.addClass(_this.hostElement, 'wj-inputdatetime');
                // get reference to drop-down button for time part
                _this._btnTm = _this.hostElement.querySelector('[wj-part="btn-tm"]');
                // change default format to show date and time
                _this._format = 'g';
                // create InputTime control (with additional drop-down)
                _this._inputTime = new input.InputTime(document.createElement('div'));
                // update time when user selects a new value from time drop-down
                _this._inputTime.valueChanged.addHandler(function () {
                    // update value
                    _this.value = wijmo.DateTime.fromDateTime(_this.value, _this._inputTime.value);
                    // switch focus to input element
                    if (_this.containsFocus()) {
                        if (!_this.isTouching || !_this.showDropDownButton) {
                            _this.selectAll();
                        }
                    }
                });
                // create time picker drop-down
                var tmDropdown = _this._inputTime.dropDown;
                // attach keyboard to time picker drop-down (open/close/commit, F4/Enter/Escape etc)
                var kd = _this._keydown.bind(_this);
                _this.addEventListener(tmDropdown, 'keydown', kd, true);
                // handle focus (we have an extra drop-down)
                _this.addEventListener(tmDropdown, 'blur', function () {
                    _this._updateFocusState();
                }, true);
                // handle clicks on the drop-down button (show drop-down, manage focus)
                _this.addEventListener(_this._btnTm, 'click', _this._btnclick.bind(_this));
                // switch editors on mousedown
                _this.addEventListener(_this._btn, 'mousedown', function () {
                    _this._setDropdown(_this.calendar.hostElement);
                });
                _this.addEventListener(_this._btnTm, 'mousedown', function (e) {
                    // if we're showing the time drop-down, the mousedown will cause
                    // the input time to lose focus and close the drop-down; 
                    // so prevent the default action to avoid having the click event 
                    // re-open the drop-down.
                    if (_this.isDroppedDown && _this.dropDown == tmDropdown) {
                        e.preventDefault();
                    }
                    _this._inputTime.dropDownCssClass = _this.dropDownCssClass;
                    _this._setDropdown(tmDropdown);
                });
                // initialize control options
                _this.initialize(options);
                return _this;
            }
            Object.defineProperty(InputDateTime.prototype, "timeMin", {
                //#endregion
                //--------------------------------------------------------------------------
                //#region ** object model
                /**
                 * Gets or sets the earliest time that the user can enter.
                 */
                get: function () {
                    return this._inputTime.min;
                },
                set: function (value) {
                    this._inputTime.min = value;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(InputDateTime.prototype, "timeMax", {
                /**
                 * Gets or sets the latest time that the user can enter.
                 */
                get: function () {
                    return this._inputTime.max;
                },
                set: function (value) {
                    this._inputTime.max = value;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(InputDateTime.prototype, "timeFormat", {
                /**
                 * Gets or sets the format used to display times in the drop-down list.
                 *
                 * This property does not affect the value shown in the control's input element.
                 * That value is formatted using the @see:format property.
                 *
                 * The format string is expressed as a .NET-style
                 * <a href="http://msdn.microsoft.com/en-us/library/8kb3ddd4(v=vs.110).aspx" target="_blank">
                 * time format string</a>.
                 */
                get: function () {
                    return this._inputTime.format;
                },
                set: function (value) {
                    this._inputTime.format = value;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(InputDateTime.prototype, "timeStep", {
                /**
                 * Gets or sets the number of minutes between entries in the drop-down list of times.
                 */
                get: function () {
                    return this._inputTime.step;
                },
                set: function (value) {
                    this._inputTime.step = value;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(InputDateTime.prototype, "inputTime", {
                /**
                 * Gets a reference to the inner @see:InputTime control so you can access its
                 * full object model.
                 */
                get: function () {
                    return this._inputTime;
                },
                enumerable: true,
                configurable: true
            });
            //#endregion
            //--------------------------------------------------------------------------
            //#region ** overrides
            // dispose of InputTime and Calendar controls
            InputDateTime.prototype.dispose = function () {
                this._setDropdown(this.calendar.hostElement);
                _super.prototype.dispose.call(this); // Date
                this._inputTime.dispose(); // Time
            };
            // update value display in case culture changed
            InputDateTime.prototype.refresh = function () {
                this._setDropdown(this.calendar.hostElement);
                _super.prototype.refresh.call(this); // Date
                this._inputTime.refresh(); // Time
            };
            // update drop-down button visibility
            InputDateTime.prototype._updateBtn = function () {
                _super.prototype._updateBtn.call(this);
                if (this._btnTm) {
                    this._btnTm.tabIndex = this._btn.tabIndex;
                    this._btnTm.parentElement.style.display = this._btn.style.display;
                }
            };
            // honor min/max range (date and time)
            InputDateTime.prototype._clamp = function (value) {
                if (value) {
                    if (this.min && value < this.min) {
                        value = this.min;
                    }
                    if (this.max && value > this.max) {
                        value = this.max;
                    }
                }
                return value;
            };
            // parse date, commit date and time parts if successful or revert
            InputDateTime.prototype._commitText = function () {
                var txt = this._tbx.value;
                if (!txt && !this.isRequired) {
                    this.value = null;
                }
                else {
                    var dt = wijmo.Globalize.parseDate(txt, this.format);
                    if (dt) {
                        this.value = dt;
                    }
                    else {
                        this._tbx.value = wijmo.Globalize.format(this.value, this.format);
                    }
                }
            };
            //#endregion
            //--------------------------------------------------------------------------
            //#region ** implementation
            // selects a drop-down element (date/time)
            InputDateTime.prototype._setDropdown = function (e) {
                if (this._dropDown != e) {
                    if (this.isDroppedDown) {
                        this.isDroppedDown = false;
                    }
                    this._dropDown = e;
                }
            };
            // update drop down content before showing it
            InputDateTime.prototype._updateDropDown = function () {
                var tm = this._inputTime;
                if (this._dropDown == tm.dropDown) {
                    this._commitText();
                    _super.prototype._updateDropDown.call(this);
                    tm.isRequired = this.isRequired;
                    tm.value = this.value;
                    if (this.isDroppedDown) {
                        tm.listBox.showSelection();
                    }
                }
                else {
                    _super.prototype._updateDropDown.call(this);
                }
            };
            /**
             * Gets or sets the template used to instantiate @see:InputDateTime controls.
             */
            InputDateTime.controlTemplate = '<div style="position:relative" class="wj-template">' +
                '<div class="wj-input">' +
                '<div class="wj-input-group wj-input-btn-visible">' +
                '<input wj-part="input" type="text" class="wj-form-control" />' +
                '<span class="wj-input-group-btn" tabindex="-1">' +
                '<button wj-part="btn" class="wj-btn wj-btn-default" type="button" tabindex="-1">' +
                '<span class="wj-glyph-calendar"></span>' +
                '</button>' +
                '<button wj-part="btn-tm" class="wj-btn wj-btn-default" type="button" tabindex="-1">' +
                '<span class="wj-glyph-clock"></span>' +
                '</button>' +
                '</span>' +
                '</div>' +
                '</div>' +
                '<div wj-part="dropdown" class="wj-content wj-dropdown-panel" ' +
                'style="display:none;position:absolute;z-index:100;width:auto">' +
                '</div>' +
                '</div>';
            return InputDateTime;
        }(input.InputDate));
        input.InputDateTime = InputDateTime;
    })(input = wijmo.input || (wijmo.input = {}));
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
    var input;
    (function (input) {
        'use strict';
        /**
         * The @see:InputNumber control allows users to enter numbers.
         *
         * The control prevents users from accidentally entering invalid data and
         * formats the number as it is edited.
         *
         * Pressing the minus key reverses the sign of the value being edited,
         * regardless of cursor position.
         *
         * You may use the @see:min and @see:max properties to limit the range of
         * acceptable values, and the @see:step property to provide spinner buttons
         * that increase or decrease the value with a click.
         *
         * For details about using the @see:min and @see:max properties, please see
         * the <a href="static/minMax.html">Using the min and max properties</a> topic.
         *
         * Use the @see:value property to get or set the currently selected number.
         *
         * The example below creates several @see:InputNumber controls and shows
         * the effect of using different formats, ranges, and step values.
         *
         * @fiddle:Cf9L9
         */
        var InputNumber = /** @class */ (function (_super) {
            __extends(InputNumber, _super);
            /**
             * Initializes a new instance of the @see:InputNumber class.
             *
             * @param element The DOM element that hosts the control, or a selector for the host element (e.g. '#theCtrl').
             * @param options The JavaScript object containing initialization data for the control.
             */
            function InputNumber(element, options) {
                var _this = _super.call(this, element) || this;
                _this._showBtn = true;
                _this._readOnly = false;
                /**
                 * Occurs when the value of the @see:text property changes.
                 */
                _this.textChanged = new wijmo.Event();
                /**
                 * Occurs when the value of the @see:value property changes, either
                 * as a result of user actions or by assignment in code.
                 */
                _this.valueChanged = new wijmo.Event();
                // accessibility: 
                // https://www.w3.org/TR/wai-aria-1.1/#spinbutton
                // http://oaa-accessibility.org/example/33/
                var host = _this.hostElement;
                wijmo.setAttribute(host, 'role', 'spinbutton', true);
                // instantiate and apply template
                var tpl = _this.getTemplate();
                _this.applyTemplate('wj-control wj-inputnumber wj-content', tpl, {
                    _tbx: 'input',
                    _btnUp: 'btn-inc',
                    _btnDn: 'btn-dec'
                }, 'input');
                // don't use 'number' input type (TFS 84900)
                if (_this._tbx.type.match(/number/i)) {
                    _this.inputType = '';
                }
                // disable autocomplete/spellcheck (important for mobile browsers including Chrome/Android)
                var tb = _this._tbx;
                tb.autocomplete = 'off';
                tb.spellcheck = false;
                // update localized decimal and currency symbols
                _this._updateSymbols();
                // handle IME
                _this.addEventListener(_this._tbx, 'compositionstart', function () {
                    _this._composing = true;
                });
                _this.addEventListener(_this._tbx, 'compositionend', function () {
                    _this._composing = false;
                    setTimeout(function () {
                        _this._setText(_this.text);
                    });
                });
                // textbox events
                _this.addEventListener(tb, 'keypress', _this._keypress.bind(_this));
                _this.addEventListener(tb, 'keydown', _this._keydown.bind(_this));
                _this.addEventListener(tb, 'input', _this._input.bind(_this));
                // inc/dec buttons: change value
                // if this was a tap, keep focus on button; OW transfer to textbox
                var cs = _this._clickSpinner.bind(_this);
                _this.addEventListener(_this._btnUp, 'click', cs);
                _this.addEventListener(_this._btnDn, 'click', cs);
                // inc/dec buttons: repeatButtons
                _this._rptUp = new wijmo._ClickRepeater(_this._btnUp.querySelector('button'));
                _this._rptDn = new wijmo._ClickRepeater(_this._btnDn.querySelector('button'));
                // use wheel to increase/decrease the value
                _this.addEventListener(host, 'wheel', function (e) {
                    if (!e.defaultPrevented && !_this.isReadOnly && _this.containsFocus()) {
                        var step = wijmo.clamp(-e.deltaY, -1, +1);
                        _this._increment((_this.step || 1) * step);
                        setTimeout(function () { return _this.selectAll(); });
                        e.preventDefault();
                    }
                });
                // initialize value
                _this.value = 0;
                // initialize control options
                _this.isRequired = true;
                _this.initialize(options);
                return _this;
            }
            Object.defineProperty(InputNumber.prototype, "inputElement", {
                //--------------------------------------------------------------------------
                //#region ** object model
                /**
                 * Gets the HTML input element hosted by the control.
                 *
                 * Use this property in situations where you want to customize the
                 * attributes of the input element.
                 */
                get: function () {
                    return this._tbx;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(InputNumber.prototype, "inputType", {
                /**
                 * Gets or sets the "type" attribute of the HTML input element hosted by the control.
                 *
                 * By default, this property is set to "tel", a value that causes mobile devices to
                 * show a numeric keypad that includes a negative sign and a decimal separator.
                 *
                 * Use this property to change the default setting if the default does not work well
                 * for the current culture, device, or application. In those cases, try changing
                 * the value to "number" or "text."
                 *
                 * Note that input elements with type "number" prevent selection in Chrome and therefore
                 * is not recommended. For more details, see this link:
                 * http://stackoverflow.com/questions/21177489/selectionstart-selectionend-on-input-type-number-no-longer-allowed-in-chrome
                 */
                get: function () {
                    return this._tbx.type;
                },
                set: function (value) {
                    this._tbx.type = wijmo.asString(value);
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(InputNumber.prototype, "value", {
                /**
                 * Gets or sets the current value of the control.
                 */
                get: function () {
                    return this._value;
                },
                set: function (value) {
                    if (value != this._value) {
                        value = wijmo.asNumber(value, !this.isRequired || (value == null && this._value == null));
                        if (value == null) {
                            this._setText('');
                        }
                        else if (!isNaN(value)) {
                            var text = wijmo.Globalize.format(value, this.format);
                            this._setText(text);
                        }
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(InputNumber.prototype, "isRequired", {
                /**
                 * Gets or sets a value indicating whether the control value must be a number or whether it
                 * can be set to null (by deleting the content of the control).
                 */
                get: function () {
                    return this._tbx.required;
                },
                set: function (value) {
                    this._tbx.required = wijmo.asBoolean(value);
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(InputNumber.prototype, "isReadOnly", {
                /**
                 * Gets or sets a value that indicates whether the user can modify
                 * the control value using the mouse and keyboard.
                 */
                get: function () {
                    return this._readOnly;
                },
                set: function (value) {
                    this._readOnly = wijmo.asBoolean(value);
                    this.inputElement.readOnly = this._readOnly;
                    wijmo.toggleClass(this.hostElement, 'wj-state-readonly', this.isReadOnly);
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(InputNumber.prototype, "min", {
                /**
                 * Gets or sets the smallest number that the user can enter.
                 *
                 * For details about using the @see:min and @see:max properties, please see the
                 * <a href="static/minMax.html">Using the min and max properties</a> topic.
                 */
                get: function () {
                    return this._min;
                },
                set: function (value) {
                    this._min = wijmo.asNumber(value, true);
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(InputNumber.prototype, "max", {
                /**
                 * Gets or sets the largest number that the user can enter.
                 *
                 * For details about using the @see:min and @see:max properties, please see the
                 * <a href="static/minMax.html">Using the min and max properties</a> topic.
                 */
                get: function () {
                    return this._max;
                },
                set: function (value) {
                    this._max = wijmo.asNumber(value, true);
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(InputNumber.prototype, "step", {
                /**
                 * Gets or sets the amount to add or subtract to the @see:value property
                 * when the user clicks the spinner buttons.
                 */
                get: function () {
                    return this._step;
                },
                set: function (value) {
                    this._step = wijmo.asNumber(value, true);
                    this._updateBtn();
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(InputNumber.prototype, "format", {
                /**
                 * Gets or sets the format used to display the number being edited (see @see:Globalize).
                 *
                 * The format string is expressed as a .NET-style
                 * <a href="http://msdn.microsoft.com/en-us/library/dwhawy9k(v=vs.110).aspx" target="_blank">
                 * standard numeric format string</a>.
                 */
                get: function () {
                    return this._format;
                },
                set: function (value) {
                    if (value != this.format) {
                        this._format = wijmo.asString(value);
                        this.refresh();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(InputNumber.prototype, "text", {
                /**
                 * Gets or sets the text shown in the control.
                 */
                get: function () {
                    return this._tbx.value;
                },
                set: function (value) {
                    if (value != this.text) {
                        this._oldText = null;
                        this._setText(value);
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(InputNumber.prototype, "placeholder", {
                /**
                 * Gets or sets the string shown as a hint when the control is empty.
                 */
                get: function () {
                    return this._tbx.placeholder;
                },
                set: function (value) {
                    this._tbx.placeholder = value;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(InputNumber.prototype, "showSpinner", {
                /**
                 * Gets or sets a value indicating whether the control displays spinner buttons
                 * to increment or decrement the value (the step property must be set to a
                 * value other than zero).
                 */
                get: function () {
                    return this._showBtn;
                },
                set: function (value) {
                    this._showBtn = wijmo.asBoolean(value);
                    this._updateBtn();
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(InputNumber.prototype, "repeatButtons", {
                /**
                 * Gets or sets a value that determines whether the spinner buttons
                 * should act as repeat buttons, firing repeatedly as the button
                 * remains pressed.
                 */
                get: function () {
                    return !this._rptUp.disabled;
                },
                set: function (value) {
                    this._rptUp.disabled = this._rptDn.disabled = !wijmo.asBoolean(value);
                },
                enumerable: true,
                configurable: true
            });
            /**
             * Sets the focus to the control and selects all its content.
             */
            InputNumber.prototype.selectAll = function () {
                var tbx = this._tbx;
                wijmo.setSelectionRange(tbx, 0, tbx.value.length); // TFS 295426
            };
            /**
             * Raises the @see:textChanged event.
             */
            InputNumber.prototype.onTextChanged = function (e) {
                this.textChanged.raise(this, e);
                this._updateState();
            };
            /**
             * Raises the @see:valueChanged event.
             */
            InputNumber.prototype.onValueChanged = function (e) {
                this._updateAria();
                this.valueChanged.raise(this, e);
            };
            //#endregion
            //--------------------------------------------------------------------------
            //#region ** overrides
            // disconnect button repeaters
            InputNumber.prototype.dispose = function () {
                this._rptUp.element = null;
                this._rptDn.element = null;
                _super.prototype.dispose.call(this);
            };
            // give focus to textbox unless touching
            InputNumber.prototype.onGotFocus = function (e) {
                if (!this.isTouching) {
                    this._tbx.focus();
                    this.selectAll();
                }
                _super.prototype.onGotFocus.call(this, e);
            };
            // enforce min/max when losing focus
            InputNumber.prototype.onLostFocus = function (e) {
                // Safari does not finish composition on blur (TFS 236810)
                if (this._composing) {
                    this._composing = false;
                    this._setText(this.text);
                }
                // enforce min/max
                var value = this._clamp(this.value), text = wijmo.Globalize.format(value, this.format);
                this._setText(text);
                // allow base class
                _super.prototype.onLostFocus.call(this, e);
            };
            // update culture symbols and display text when refreshing
            InputNumber.prototype.refresh = function (fullUpdate) {
                this._updateSymbols();
                var text = wijmo.Globalize.format(this.value, this.format);
                this._setText(text);
            };
            //#endregion
            //--------------------------------------------------------------------------
            //#region ** implementation
            // update culture/format symbols
            InputNumber.prototype._updateSymbols = function () {
                var nf = wijmo.culture.Globalize.numberFormat, fmt = this.format ? this.format.match(/([a-z])(\d*)(,*)(.*)/i) : null;
                this._chrDec = nf['.'] || '.';
                this._chrCur = (fmt && fmt[4]) ? fmt[4] : (nf.currency.symbol || '$');
                this._fmtSpc = (fmt && fmt[1]) ? fmt[1].toLowerCase() : 'n';
                this._fmtPrc = (fmt && fmt[2]) ? parseInt(fmt[2]) : null;
                this._rxSym = new RegExp('^[%+\\-() ' + this._chrDec + this._chrCur + ']*$');
                this._rxNeg = new RegExp('(\\-|\\()');
            };
            // make sure a value is between min and max
            InputNumber.prototype._clamp = function (value) {
                return wijmo.clamp(value, this.min, this.max);
            };
            // checks whether a character is a digit, sign, or decimal point
            InputNumber.prototype._isNumeric = function (chr, digitsOnly) {
                var isNum = (chr == this._chrDec) || (chr >= '0' && chr <= '9'), hex = this._fmtSpc == 'x';
                if (!isNum && hex) {
                    isNum = (chr >= 'a' && chr <= 'f') || (chr >= 'A' && chr <= 'F');
                }
                if (!isNum && !digitsOnly && !hex) {
                    isNum = '+-()'.indexOf(chr) > -1;
                }
                return isNum;
            };
            // get the range of numeric characters within the current text
            InputNumber.prototype._getInputRange = function (digitsOnly) {
                var rng = [0, 0], text = this.text, hasStart = false;
                for (var i = 0; i < text.length; i++) {
                    if (this._isNumeric(text[i], digitsOnly)) {
                        if (!hasStart) {
                            rng[0] = i;
                            hasStart = true;
                        }
                        rng[1] = i + 1;
                    }
                }
                return rng;
            };
            // flip the current value between positive and negative
            // keeping the cursor position
            InputNumber.prototype._flipSign = function () {
                var start = this._getSelStartDigits();
                this.value *= -1;
                this._setSelStartDigits(start);
            };
            // get selection start counting digits and decimals only
            InputNumber.prototype._getSelStartDigits = function () {
                var start = 0, selStart = this._tbx.selectionStart, str = this._tbx.value;
                for (var i = 0; i < str.length && i < selStart; i++) {
                    if (this._isNumeric(str[i], true)) {
                        start++;
                    }
                }
                return start;
            };
            // set selection start counting digits and decimals only
            InputNumber.prototype._setSelStartDigits = function (start) {
                var str = this._tbx.value;
                for (var i = 0; i < str.length && start >= 0; i++) {
                    if (this._isNumeric(str[i], true)) {
                        if (!start) {
                            wijmo.setSelectionRange(this._tbx, i);
                            break;
                        }
                        start--;
                    }
                    else if (!start) {
                        wijmo.setSelectionRange(this._tbx, i);
                        break;
                    }
                }
            };
            // apply increment with rounding (not truncating): TFS 142618, 145814, 153300
            InputNumber.prototype._increment = function (step) {
                if (step) {
                    var value = this._clamp(wijmo.isNumber(this.value) ? this.value + step : 0), text = wijmo.Globalize.format(value, this.format, false, false);
                    this._setText(text);
                }
            };
            // update spinner button visibility
            InputNumber.prototype._updateBtn = function () {
                var showBtn = this.showSpinner && !!this.step, enableBtn = showBtn;
                wijmo.setCss([this._btnUp, this._btnDn], {
                    display: showBtn ? '' : 'none'
                });
                wijmo.toggleClass(this.hostElement, 'wj-input-show-spinner', showBtn);
                wijmo.enable(this._btnUp, enableBtn);
                wijmo.enable(this._btnDn, enableBtn);
                this._updateAria();
            };
            // update text in textbox
            InputNumber.prototype._setText = function (text) {
                // not while composing IME text...
                if (this._composing)
                    return;
                // save state
                var tbx = this._tbx, isNegative = this._rxNeg.test(text), delKey = this._delKey;
                // handle strings composed only of non-digit chars (TFS 143559, 141501)
                if (text && this._rxSym.test(text)) {
                    text = (this.isRequired || !delKey) ? '0' : '';
                }
                // delete/backspace keys clear non-required zeros to null
                this._delKey = false;
                if (delKey && this.value == 0 && !this.isRequired) {
                    text = '';
                }
                // handle nulls
                if (!text) {
                    // if value is not required, setting to null is OK
                    if (!this.isRequired) {
                        tbx.value = '';
                        if (this._value != null) {
                            this._value = null;
                            this.onValueChanged();
                        }
                        if (this._oldText) {
                            this._oldText = text;
                            this.onTextChanged();
                        }
                        this._updateBtn();
                        return;
                    }
                    // value is required, so change text to zero
                    text = '0';
                }
                // parse input
                var fmt = this._format || (text.indexOf(this._chrDec) > -1 ? 'n2' : 'n0'), value = wijmo.Globalize.parseFloat(text, fmt);
                // handle invalid input
                if (isNaN(value)) {
                    tbx.value = this._oldText;
                    return;
                }
                // get formatted value
                var fval = wijmo.Globalize.format(value, fmt, false);
                // allow for '-0.00'
                if (isNegative && value >= 0) {
                    fval = '-' + fval;
                }
                // allow trailing decimal/zeros when precision is not set
                // TFS 140302, 145764, 295990, 304407
                if (this._fmtPrc == null || this._fmtSpc == 'g') {
                    if (!delKey && tbx.value.match(/\.0?$/)) {
                        fval = text + (text == '0' ? this._chrDec : '');
                    }
                }
                // update text with formatted value
                if (tbx.value != fval) {
                    tbx.value = fval;
                    value = wijmo.Globalize.parseFloat(fval, this.format);
                }
                // update value, raise valueChanged
                if (value != this._value) {
                    this._value = value;
                    this.onValueChanged();
                }
                // raise textChanged
                if (this.text != this._oldText) {
                    this._oldText = this.text;
                    this.onTextChanged();
                }
                // update spinner button visibility
                this._updateBtn();
            };
            // handle the keypress events
            InputNumber.prototype._keypress = function (e) {
                // ignore the key if handled, composing, or if the control is read-only (TFS 199438)
                if (e.defaultPrevented || this._composing || this.isReadOnly) {
                    return;
                }
                // if char pressed, not ctrl/command key // TFS 193087, 234934
                if (e.charCode && !e.ctrlKey && !e.metaKey) {
                    // prevent invalid chars/validate cursor position (TFS 80733)
                    var tbx = this._tbx, chr = String.fromCharCode(e.charCode);
                    if (!this._isNumeric(chr, false)) {
                        e.preventDefault();
                    }
                    else {
                        // validate cursor position
                        var rng = this._getInputRange(true), start = tbx.selectionStart, end = tbx.selectionEnd;
                        if (start < rng[0] && end < tbx.value.length) {
                            wijmo.setSelectionRange(tbx, rng[0], end);
                        }
                        // ignore input after the end to prevent rounding (TFS 205653, 270431, 276538)
                        if (start >= rng[1]) {
                            var prec = this._fmtPrc != null ? this._fmtPrc : 2, idx = tbx.value.indexOf(this._chrDec);
                            if (idx > -1 && start - idx > prec) {
                                e.preventDefault();
                            }
                        }
                    }
                    // handle special characters
                    switch (chr) {
                        // flip sign
                        case '-':
                            if (this.min >= 0) {
                                if (this.value < 0) {
                                    this._flipSign();
                                }
                            }
                            else {
                                if (this.value && tbx.selectionStart == tbx.selectionEnd) {
                                    this._flipSign();
                                }
                                else {
                                    // just show the minus sign (TFS 299899)
                                    if (this._clamp(-1) < 0) {
                                        tbx.value = '-';
                                        wijmo.setSelectionRange(tbx, 1);
                                    }
                                    //// start with negative value
                                    //this._setText('-');
                                    //// position cursor before the decimal or after the last digit
                                    //let index = this.text.indexOf(this._chrDec);
                                    //if (index < 0) {
                                    //    index = this._getInputRange(true)[1];
                                    //}
                                    //setSelectionRange(tbx, index);
                                }
                            }
                            e.preventDefault();
                            break;
                        // make positive
                        case '+':
                            if (this.value < 0) {
                                this._flipSign();
                            }
                            e.preventDefault();
                            break;
                        // prevent decimal points altogether, or multiple instances
                        case this._chrDec:
                            if (this._fmtPrc == 0) {
                                e.preventDefault();
                            }
                            else {
                                var dec = tbx.value.indexOf(chr);
                                if (dec > -1) {
                                    if (tbx.selectionStart <= dec) {
                                        dec++;
                                    }
                                    wijmo.setSelectionRange(tbx, dec);
                                    e.preventDefault();
                                }
                            }
                            break;
                    }
                }
            };
            // handle the keydown event
            InputNumber.prototype._keydown = function (e) {
                var _this = this;
                this._delKey = false;
                // ignore the key if handled or composing
                if (e.defaultPrevented || this._composing) {
                    return;
                }
                // handle the key
                var tbx = this._tbx, text = tbx.value, selStart = tbx.selectionStart, selEnd = tbx.selectionEnd;
                switch (e.keyCode) {
                    // customize select all behavior
                    case 65:// A
                        if (e.ctrlKey) {
                            setTimeout(function () {
                                _this.selectAll();
                            });
                            e.preventDefault();
                        }
                        break;
                    // apply increment when user presses up/down
                    case wijmo.Key.Up:
                    case wijmo.Key.Down:
                        if (this.step && !this.isReadOnly) {
                            this._increment(this.step * (e.keyCode == wijmo.Key.Up ? +1 : -1));
                            setTimeout(function () {
                                _this.selectAll();
                            });
                            e.preventDefault();
                        }
                        break;
                    // Back skips over decimal points, '%', and ')' signs (TFS 80472, 267528, 281341)
                    case wijmo.Key.Back:
                        this._delKey = true;
                        if (selEnd - selStart < 2 && !this.isReadOnly) {
                            var chr_1 = text[selEnd - 1];
                            if (chr_1 == this._chrDec || chr_1 == '%' || chr_1 == ')') {
                                setTimeout(function () {
                                    selEnd = chr_1 == '%'
                                        ? _this._getInputRange(true)[1] // after the percentage
                                        : selEnd - 1; // before the decimal/parenthesis (TFS 283792)
                                    wijmo.setSelectionRange(tbx, selEnd);
                                });
                                e.preventDefault();
                            }
                        }
                        break;
                    // Delete skips over decimal points, and '%' signs (TFS 80472, 267528, 281341)
                    case wijmo.Key.Delete:
                        this._delKey = true;
                        if (selEnd - selStart < 2 && !this.isReadOnly) {
                            if (text == '0' && selStart == 1) {
                                wijmo.setSelectionRange(tbx, 0);
                            }
                            else {
                                var chr = text[selStart];
                                if (chr == this._chrDec || chr == '%') {
                                    setTimeout(function () {
                                        wijmo.setSelectionRange(tbx, selStart + 1);
                                    });
                                    e.preventDefault();
                                }
                            }
                        }
                        break;
                }
            };
            // handle user input (keypress or paste)
            InputNumber.prototype._input = function (e) {
                var _this = this;
                // not while composing IME text...
                if (this._composing)
                    return;
                // this timeOut is **important** for Windows Phone/Android/Safari
                setTimeout(function () {
                    // remember cursor position
                    var tbx = _this._tbx, text = tbx.value, dec = text.indexOf(_this._chrDec), sel = tbx.selectionStart, pos = _this._getSelStartDigits();
                    // preserve percentage sign for percentage formats
                    if (_this._fmtSpc == 'p' && text.length && text.indexOf('%') < 0) {
                        text += '%';
                    }
                    // set the text
                    _this._setText(text);
                    // update cursor position if we have the focus (TFS 136134)
                    if (_this.containsFocus()) {
                        // get updated values
                        var newText = tbx.value, newDec = newText.indexOf(_this._chrDec), rng = _this._getInputRange(true);
                        // handle case where the user types '-.'
                        if (text == '-.' && newDec > -1) {
                            wijmo.setSelectionRange(tbx, newDec + 1);
                            return;
                        }
                        // handle case where user types "-*" and the control switches to
                        // parenthesized values
                        if (text[0] == '-' && newText[0] != '-') {
                            _this._setSelStartDigits(pos);
                            return;
                        }
                        // try to keep cursor offset from the right (TFS 136392, 143553)
                        if (text) {
                            if (text == _this._chrDec && newDec > -1) {
                                sel = newDec + 1; // user just typed a decimal point (TFS 236650)
                            }
                            else if ((sel <= dec && newDec > -1) || (dec < 0 && newDec < 0)) {
                                sel += newText.length - text.length; // cursor was on the left of the decimal
                            }
                            else if (dec < 0 && newDec > -1) {
                                sel = newDec; // there was no decimal, but now there is
                            }
                        }
                        else {
                            sel = newDec > -1 ? newDec : rng[1];
                        }
                        // make sure it's within the valid range
                        sel = wijmo.clamp(sel, rng[0], rng[1]);
                        // set cursor position
                        wijmo.setSelectionRange(tbx, sel);
                    }
                });
            };
            // handle clicks on the spinner buttons
            InputNumber.prototype._clickSpinner = function (e) {
                var _this = this;
                if (!e.defaultPrevented && !this.isReadOnly && this.step) {
                    this._increment(this.step * (wijmo.contains(this._btnUp, e.target) ? +1 : -1));
                    if (!this.isTouching) {
                        setTimeout(function () { return _this.selectAll(); });
                    }
                }
            };
            // update ARIA attributes for this control
            InputNumber.prototype._updateAria = function () {
                var host = this.hostElement;
                if (host) {
                    wijmo.setAttribute(host, 'aria-valuemin', this.min);
                    wijmo.setAttribute(host, 'aria-valuemax', this.max);
                    wijmo.setAttribute(host, 'aria-valuenow', this.value);
                }
            };
            /**
             * Gets or sets the template used to instantiate @see:InputNumber controls.
             */
            InputNumber.controlTemplate = '<div class="wj-input">' +
                '<div class="wj-input-group">' +
                '<span wj-part="btn-dec" class="wj-input-group-btn" tabindex="-1">' +
                '<button class="wj-btn wj-btn-default" type="button" tabindex="-1">-</button>' +
                '</span>' +
                '<input type="tel" wj-part="input" class="wj-form-control wj-numeric"/>' +
                '<span wj-part="btn-inc" class="wj-input-group-btn" tabindex="-1">' +
                '<button class="wj-btn wj-btn-default" type="button" tabindex="-1">+</button>' +
                '</span>' +
                '</div>' +
                '</div>';
            return InputNumber;
        }(wijmo.Control));
        input.InputNumber = InputNumber;
    })(input = wijmo.input || (wijmo.input = {}));
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
    var input;
    (function (input) {
        'use strict';
        /**
         * The @see:InputMask control provides a way to govern what a user is allowed to input.
         *
         * The control prevents users from accidentally entering invalid data and
         * saves time by skipping over literals (such as slashes in dates) as the user types.
         *
         * The mask used to validate the input is defined by the @see:InputMask.mask property,
         * which may contain one or more of the following special characters:
         *
         *  <dl class="dl-horizontal">
         *      <dt>0</dt>      <dd>Digit.</dd>
         *      <dt>9</dt>      <dd>Digit or space.</dd>
         *      <dt>#</dt>      <dd>Digit, sign, or space.</dd>
         *      <dt>L</dt>      <dd>Letter.</dd>
         *      <dt>l</dt>      <dd>Letter or space.</dd>
         *      <dt>A</dt>      <dd>Alphanumeric.</dd>
         *      <dt>a</dt>      <dd>Alphanumeric or space.</dd>
         *      <dt>.</dt>      <dd>Localized decimal point.</dd>
         *      <dt>,</dt>      <dd>Localized thousand separator.</dd>
         *      <dt>:</dt>      <dd>Localized time separator.</dd>
         *      <dt>/</dt>      <dd>Localized date separator.</dd>
         *      <dt>$</dt>      <dd>Localized currency symbol.</dd>
         *      <dt>&lt;</dt>   <dd>Converts characters that follow to lowercase.</dd>
         *      <dt>&gt;</dt>   <dd>Converts characters that follow to uppercase.</dd>
         *      <dt>|</dt>      <dd>Disables case conversion.</dd>
         *      <dt>\</dt>      <dd>Escapes any character, turning it into a literal.</dd>
         *      <dt>９</dt>      <dd>DBCS Digit.</dd>
         *      <dt>Ｊ</dt>      <dd>DBCS Hiragana.</dd>
         *      <dt>Ｇ</dt>      <dd>DBCS big Hiragana.</dd>
         *      <dt>Ｋ</dt>      <dd>DBCS Katakana. </dd>
         *      <dt>Ｎ</dt>      <dd>DBCS big Katakana.</dd>
         *      <dt>K</dt>      <dd>SBCS Katakana.</dd>
         *      <dt>N</dt>      <dd>SBCS big Katakana.</dd>
         *      <dt>Ｚ</dt>      <dd>Any DBCS character.</dd>
         *      <dt>H</dt>      <dd>Any SBCS character.</dd>
         *      <dt>All others</dt><dd>Literals.</dd>
         *  </dl>
         */
        var InputMask = /** @class */ (function (_super) {
            __extends(InputMask, _super);
            /**
             * Initializes a new instance of the @see:InputMask class.
             *
             * @param element The DOM element that hosts the control, or a selector for the host element (e.g. '#theCtrl').
             * @param options The JavaScript object containing initialization data for the control.
             */
            function InputMask(element, options) {
                var _this = _super.call(this, element) || this;
                /**
                 * Occurs when the value of the @see:value property changes, either
                 * as a result of user actions or by assignment in code.
                 */
                _this.valueChanged = new wijmo.Event();
                // instantiate and apply template
                var tpl = _this.getTemplate();
                _this.applyTemplate('wj-control wj-inputmask wj-content', tpl, {
                    _tbx: 'input'
                }, 'input');
                // initialize value from <input> tag
                if (_this._orgTag == 'INPUT') {
                    var value = _this._tbx.getAttribute('value');
                    if (value) {
                        _this.value = value;
                    }
                }
                // create mask provider
                _this._msk = new wijmo._MaskProvider(_this._tbx);
                // initialize control options
                _this.isRequired = true;
                _this.initialize(options);
                // update mask on input
                _this.addEventListener(_this._tbx, 'input', function () {
                    setTimeout(function () {
                        _this.onValueChanged();
                    });
                });
                return _this;
            }
            Object.defineProperty(InputMask.prototype, "inputElement", {
                //--------------------------------------------------------------------------
                //#region ** object model
                /**
                 * Gets the HTML input element hosted by the control.
                 *
                 * Use this property in situations where you want to customize the
                 * attributes of the input element.
                 */
                get: function () {
                    return this._tbx;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(InputMask.prototype, "value", {
                /**
                 * Gets or sets the text currently shown in the control.
                 */
                get: function () {
                    return this._tbx.value;
                },
                set: function (value) {
                    if (value != this.value) {
                        // assign unmasked value to input element
                        this._tbx.value = wijmo.asString(value);
                        // move selection to end without disturbing the focus (TFS 152756)
                        // (for IE consistency with typing, important for vague literal handling)
                        var ae = wijmo.getActiveElement();
                        this._tbx.selectionStart = this._tbx.value.length;
                        if (ae && ae != wijmo.getActiveElement()) {
                            ae.focus();
                        }
                        // update masked value
                        value = this._msk._applyMask();
                        // update input element
                        this._tbx.value = value;
                        this.onValueChanged();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(InputMask.prototype, "rawValue", {
                /**
                 * Gets or sets the raw value of the control (excluding mask literals).
                 *
                 * The raw value of the control excludes prompt and literal characters.
                 * For example, if the @see:mask property is set to "AA-9999" and the
                 * user enters the value "AB-1234", the @see:rawValue property will
                 * return "AB1234", excluding the hyphen that is part of the mask.
                 */
                get: function () {
                    return this._msk.getRawValue();
                },
                set: function (value) {
                    if (value != this.rawValue) {
                        this.value = wijmo.asString(value);
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(InputMask.prototype, "mask", {
                /**
                 * Gets or sets the mask used to validate the input as the user types.
                 *
                 * The mask is defined as a string with one or more of the masking
                 * characters listed in the @see:InputMask topic.
                 */
                get: function () {
                    return this._msk.mask;
                },
                set: function (value) {
                    var oldValue = this.value;
                    this._msk.mask = wijmo.asString(value);
                    if (this.value != oldValue) {
                        this.onValueChanged();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(InputMask.prototype, "promptChar", {
                /**
                 * Gets or sets the symbol used to show input positions in the control.
                 */
                get: function () {
                    return this._msk.promptChar;
                },
                set: function (value) {
                    var oldValue = this.value;
                    this._msk.promptChar = value;
                    if (this.value != oldValue) {
                        this.onValueChanged();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(InputMask.prototype, "placeholder", {
                /**
                 * Gets or sets the string shown as a hint when the control is empty.
                 */
                get: function () {
                    return this._tbx.placeholder;
                },
                set: function (value) {
                    this._tbx.placeholder = value;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(InputMask.prototype, "maskFull", {
                /**
                 * Gets a value that indicates whether the mask has been completely filled.
                 */
                get: function () {
                    return this._msk.maskFull;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(InputMask.prototype, "isRequired", {
                /**
                 * Gets or sets a value indicating whether the control value
                 * must be a non-empty string.
                 */
                get: function () {
                    return this._tbx.required;
                },
                set: function (value) {
                    this._tbx.required = wijmo.asBoolean(value);
                },
                enumerable: true,
                configurable: true
            });
            /**
             * Sets the focus to the control and selects all its content.
             */
            InputMask.prototype.selectAll = function () {
                var rng = this._msk.getMaskRange();
                wijmo.setSelectionRange(this._tbx, rng[0], rng[1] + 1);
            };
            /**
             * Raises the @see:valueChanged event.
             */
            InputMask.prototype.onValueChanged = function (e) {
                if (this.value != this._oldValue) {
                    this._oldValue = this.value;
                    this.valueChanged.raise(this, e);
                }
                this._updateState();
            };
            //#endregion
            //--------------------------------------------------------------------------
            //#region ** overrides
            // disconnect mask provider
            InputMask.prototype.dispose = function () {
                this._msk.input = null;
                _super.prototype.dispose.call(this);
            };
            // apply mask when refreshing
            InputMask.prototype.refresh = function (fullUpdate) {
                _super.prototype.refresh.call(this, fullUpdate);
                this._msk.refresh();
            };
            // select all when getting the focus
            InputMask.prototype.onGotFocus = function (e) {
                _super.prototype.onGotFocus.call(this, e);
                this.selectAll();
            };
            /**
             * Gets or sets the template used to instantiate @see:InputMask controls.
             */
            InputMask.controlTemplate = '<div class="wj-input">' +
                '<div class="wj-input-group">' +
                '<input wj-part="input" class="wj-form-control"/>' +
                '</div>' +
                '</div>';
            return InputMask;
        }(wijmo.Control));
        input.InputMask = InputMask;
    })(input = wijmo.input || (wijmo.input = {}));
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
    var input;
    (function (input) {
        'use strict';
        /**
         * The @see:InputColor control allows users to select colors by typing in
         * HTML-supported color strings, or to pick colors from a drop-down
         * that shows a @see:ColorPicker control.
         *
         * Use the @see:value property to get or set the currently selected color.
         *
         * @fiddle:84xvsz90
         */
        var InputColor = /** @class */ (function (_super) {
            __extends(InputColor, _super);
            /**
             * Initializes a new instance of the @see:InputColor class.
             *
             * @param element The DOM element that hosts the control, or a selector for the host element (e.g. '#theCtrl').
             * @param options The JavaScript object containing initialization data for the control.
             */
            function InputColor(element, options) {
                var _this = _super.call(this, element) || this;
                /**
                 * Occurs when the value of the @see:value property changes, either
                 * as a result of user actions or by assignment in code.
                 */
                _this.valueChanged = new wijmo.Event();
                wijmo.addClass(_this.hostElement, 'wj-inputcolor');
                // create preview element
                _this._tbx.style.paddingLeft = '24px';
                _this._ePreview = wijmo.createElement('<div class="wj-inputcolorbox" style="position:absolute;left:6px;top:6px;width:12px;bottom:6px;border:1px solid black"></div>');
                _this.hostElement.style.position = 'relative';
                _this.hostElement.appendChild(_this._ePreview);
                // initializing from <input> tag
                if (_this._orgTag == 'INPUT') {
                    _this._tbx.type = '';
                    _this._commitText();
                }
                // initialize value to white
                _this.value = '#ffffff';
                // initialize control options
                _this.isRequired = true;
                _this.initialize(options);
                // close drop-down when user clicks a palette entry or the preview element
                _this.addEventListener(_this._colorPicker.hostElement, 'click', function (e) {
                    var el = e.target;
                    if (el && el.tagName == 'DIV') {
                        if (wijmo.closest(el, '[wj-part="div-pal"]') || wijmo.closest(el, '[wj-part="div-pv"]')) {
                            var color = el.style.backgroundColor;
                            if (color) {
                                _this.isDroppedDown = false;
                            }
                        }
                    }
                });
                return _this;
            }
            Object.defineProperty(InputColor.prototype, "value", {
                //--------------------------------------------------------------------------
                //#region ** object model
                /**
                 * Gets or sets the current color.
                 */
                get: function () {
                    return this._value;
                },
                set: function (value) {
                    if (value != this.value) {
                        if (value || !this.isRequired) {
                            this.text = wijmo.asString(value);
                        }
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(InputColor.prototype, "text", {
                /**
                 * Gets or sets the text shown on the control.
                 */
                get: function () {
                    return this._tbx.value;
                },
                set: function (value) {
                    if (value != this.text) {
                        this._setText(wijmo.asString(value), true);
                        this._commitText();
                    }
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(InputColor.prototype, "showAlphaChannel", {
                /**
                 * Gets or sets a value indicating whether the @see:ColorPicker allows users
                 * to edit the color's alpha channel (transparency).
                 */
                get: function () {
                    return this._colorPicker.showAlphaChannel;
                },
                set: function (value) {
                    this._colorPicker.showAlphaChannel = value;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(InputColor.prototype, "palette", {
                /**
                 * Gets or sets an array that contains the colors in the palette.
                 *
                 * The palette contains ten colors, represented by an array with
                 * ten strings. The first two colors are usually white and black.
                 */
                get: function () {
                    return this._colorPicker.palette;
                },
                set: function (value) {
                    this._colorPicker.palette = value;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(InputColor.prototype, "colorPicker", {
                /**
                 * Gets a reference to the @see:ColorPicker control shown in the drop-down.
                 */
                get: function () {
                    return this._colorPicker;
                },
                enumerable: true,
                configurable: true
            });
            /**
             * Raises the @see:valueChanged event.
             */
            InputColor.prototype.onValueChanged = function (e) {
                this.valueChanged.raise(this, e);
            };
            //#endregion ** object model
            //--------------------------------------------------------------------------
            //#region ** overrides
            // create the drop-down element
            InputColor.prototype._createDropDown = function () {
                var _this = this;
                // create the drop-down element
                this._colorPicker = new input.ColorPicker(this._dropDown);
                wijmo.setCss(this._dropDown, {
                    minWidth: 420,
                    minHeight: 200
                });
                // update our value to match colorPicker's
                this._colorPicker.valueChanged.addHandler(function () {
                    _this.value = _this._colorPicker.value;
                });
            };
            // override to commit/cancel edits
            InputColor.prototype._keydown = function (e) {
                if (!e.defaultPrevented) {
                    switch (e.keyCode) {
                        case wijmo.Key.Enter:
                            this._commitText();
                            this.selectAll();
                            break;
                        case wijmo.Key.Escape:
                            this.text = this.value;
                            this.selectAll();
                            break;
                    }
                }
                _super.prototype._keydown.call(this, e);
            };
            //#endregion ** overrides
            //--------------------------------------------------------------------------
            //#region ** implementation
            // assign new color to ColorPicker
            InputColor.prototype._commitText = function () {
                if (this.value != this.text) {
                    // allow empty values
                    if (!this.isRequired && !this.text) {
                        this._value = this.text;
                        this._ePreview.style.backgroundColor = '';
                        return;
                    }
                    // parse and assign color to control
                    var c = wijmo.Color.fromString(this.text);
                    if (c) {
                        this._colorPicker.value = this.text;
                        this._value = this._colorPicker.value;
                        this._ePreview.style.backgroundColor = this.value;
                        this.onValueChanged();
                    }
                    else {
                        this.text = this._value ? this._value : '';
                    }
                }
            };
            return InputColor;
        }(input.DropDown));
        input.InputColor = InputColor;
    })(input = wijmo.input || (wijmo.input = {}));
})(wijmo || (wijmo = {}));

