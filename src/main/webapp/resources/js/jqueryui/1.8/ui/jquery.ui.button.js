/*
 * jQuery UI Button 1.8.17
 *
 * Copyright 2011, AUTHORS.txt (http://jqueryui.com/about)
 * Dual licensed under the MIT or GPL Version 2 licenses.
 * http://jquery.org/license
 *
 * http://docs.jquery.com/UI/Button
 *
 * Depends:
 *	jquery.ui.core.js
 *	jquery.ui.widget.js
 */
(function (a, b) {
    var c, d, e, f, g = "ui-button ui-widget ui-state-default ui-corner-all", h = "ui-state-hover ui-state-active ", i = "ui-button-icons-only ui-button-icon-only ui-button-text-icons ui-button-text-icon-primary ui-button-text-icon-secondary ui-button-text-only", j = function () {
        var b = a(this).find(":ui-button");
        setTimeout(function () {
            b.button("refresh")
        }, 1)
    }, k = function (b) {
        var c = b.name, d = b.form, e = a([]);
        c && (d ? e = a(d).find("[name='" + c + "']") : e = a("[name='" + c + "']", b.ownerDocument).filter(function () {
            return!this.form
        }));
        return e
    };
    a.widget("ui.button", {options:{disabled:null, text:!0, label:null, icons:{primary:null, secondary:null}}, _create:function () {
        this.element.closest("form").unbind("reset.button").bind("reset.button", j), typeof this.options.disabled != "boolean" && (this.options.disabled = this.element.propAttr("disabled")), this._determineButtonType(), this.hasTitle = !!this.buttonElement.attr("title");
        var b = this, h = this.options, i = this.type === "checkbox" || this.type === "radio", l = "ui-state-hover" + (i ? "" : " ui-state-active"), m = "ui-state-focus";
        h.label === null && (h.label = this.buttonElement.html()), this.element.is(":disabled") && (h.disabled = !0), this.buttonElement.addClass(g).attr("role", "button").bind("mouseenter.button",
            function () {
                h.disabled || (a(this).addClass("ui-state-hover"), this === c && a(this).addClass("ui-state-active"))
            }).bind("mouseleave.button",
            function () {
                h.disabled || a(this).removeClass(l)
            }).bind("click.button", function (a) {
                h.disabled && (a.preventDefault(), a.stopImmediatePropagation())
            }), this.element.bind("focus.button",
            function () {
                b.buttonElement.addClass(m)
            }).bind("blur.button", function () {
                b.buttonElement.removeClass(m)
            }), i && (this.element.bind("change.button", function () {
            f || b.refresh()
        }), this.buttonElement.bind("mousedown.button",
            function (a) {
                h.disabled || (f = !1, d = a.pageX, e = a.pageY)
            }).bind("mouseup.button", function (a) {
                !h.disabled && (d !== a.pageX || e !== a.pageY) && (f = !0)
            })), this.type === "checkbox" ? this.buttonElement.bind("click.button", function () {
            if (h.disabled || f)return!1;
            a(this).toggleClass("ui-state-active"), b.buttonElement.attr("aria-pressed", b.element[0].checked)
        }) : this.type === "radio" ? this.buttonElement.bind("click.button", function () {
            if (h.disabled || f)return!1;
            a(this).addClass("ui-state-active"), b.buttonElement.attr("aria-pressed", "true");
            var c = b.element[0];
            k(c).not(c).map(
                function () {
                    return a(this).button("widget")[0]
                }).removeClass("ui-state-active").attr("aria-pressed", "false")
        }) : (this.buttonElement.bind("mousedown.button",
            function () {
                if (h.disabled)return!1;
                a(this).addClass("ui-state-active"), c = this, a(document).one("mouseup", function () {
                    c = null
                })
            }).bind("mouseup.button",
            function () {
                if (h.disabled)return!1;
                a(this).removeClass("ui-state-active")
            }).bind("keydown.button",
            function (b) {
                if (h.disabled)return!1;
                (b.keyCode == a.ui.keyCode.SPACE || b.keyCode == a.ui.keyCode.ENTER) && a(this).addClass("ui-state-active")
            }).bind("keyup.button", function () {
                a(this).removeClass("ui-state-active")
            }), this.buttonElement.is("a") && this.buttonElement.keyup(function (b) {
            b.keyCode === a.ui.keyCode.SPACE && a(this).click()
        })), this._setOption("disabled", h.disabled), this._resetButton()
    }, _determineButtonType:function () {
        this.element.is(":checkbox") ? this.type = "checkbox" : this.element.is(":radio") ? this.type = "radio" : this.element.is("input") ? this.type = "input" : this.type = "button";
        if (this.type === "checkbox" || this.type === "radio") {
            var a = this.element.parents().filter(":last"), b = "label[for='" + this.element.attr("id") + "']";
            this.buttonElement = a.find(b), this.buttonElement.length || (a = a.length ? a.siblings() : this.element.siblings(), this.buttonElement = a.filter(b), this.buttonElement.length || (this.buttonElement = a.find(b))), this.element.addClass("ui-helper-hidden-accessible");
            var c = this.element.is(":checked");
            c && this.buttonElement.addClass("ui-state-active"), this.buttonElement.attr("aria-pressed", c)
        } else this.buttonElement = this.element
    }, widget:function () {
        return this.buttonElement
    }, destroy:function () {
        this.element.removeClass("ui-helper-hidden-accessible"), this.buttonElement.removeClass(g + " " + h + " " + i).removeAttr("role").removeAttr("aria-pressed").html(this.buttonElement.find(".ui-button-text").html()), this.hasTitle || this.buttonElement.removeAttr("title"), a.Widget.prototype.destroy.call(this)
    }, _setOption:function (b, c) {
        a.Widget.prototype._setOption.apply(this, arguments);
        b === "disabled" ? c ? this.element.propAttr("disabled", !0) : this.element.propAttr("disabled", !1) : this._resetButton()
    }, refresh:function () {
        var b = this.element.is(":disabled");
        b !== this.options.disabled && this._setOption("disabled", b), this.type === "radio" ? k(this.element[0]).each(function () {
            a(this).is(":checked") ? a(this).button("widget").addClass("ui-state-active").attr("aria-pressed", "true") : a(this).button("widget").removeClass("ui-state-active").attr("aria-pressed", "false")
        }) : this.type === "checkbox" && (this.element.is(":checked") ? this.buttonElement.addClass("ui-state-active").attr("aria-pressed", "true") : this.buttonElement.removeClass("ui-state-active").attr("aria-pressed", "false"))
    }, _resetButton:function () {
        if (this.type === "input")this.options.label && this.element.val(this.options.label); else {
            var b = this.buttonElement.removeClass(i), c = a("<span></span>", this.element[0].ownerDocument).addClass("ui-button-text").html(this.options.label).appendTo(b.empty()).text(), d = this.options.icons, e = d.primary && d.secondary, f = [];
            d.primary || d.secondary ? (this.options.text && f.push("ui-button-text-icon" + (e ? "s" : d.primary ? "-primary" : "-secondary")), d.primary && b.prepend("<span class='ui-button-icon-primary ui-icon " + d.primary + "'></span>"), d.secondary && b.append("<span class='ui-button-icon-secondary ui-icon " + d.secondary + "'></span>"), this.options.text || (f.push(e ? "ui-button-icons-only" : "ui-button-icon-only"), this.hasTitle || b.attr("title", c))) : f.push("ui-button-text-only"), b.addClass(f.join(" "))
        }
    }}), a.widget("ui.buttonset", {options:{items:":button, :submit, :reset, :checkbox, :radio, a, :data(button)"}, _create:function () {
        this.element.addClass("ui-buttonset")
    }, _init:function () {
        this.refresh()
    }, _setOption:function (b, c) {
        b === "disabled" && this.buttons.button("option", b, c), a.Widget.prototype._setOption.apply(this, arguments)
    }, refresh:function () {
        var b = this.element.css("direction") === "rtl";
        this.buttons = this.element.find(this.options.items).filter(":ui-button").button("refresh").end().not(":ui-button").button().end().map(
            function () {
                return a(this).button("widget")[0]
            }).removeClass("ui-corner-all ui-corner-left ui-corner-right").filter(":first").addClass(b ? "ui-corner-right" : "ui-corner-left").end().filter(":last").addClass(b ? "ui-corner-left" : "ui-corner-right").end().end()
    }, destroy:function () {
        this.element.removeClass("ui-buttonset"), this.buttons.map(
            function () {
                return a(this).button("widget")[0]
            }).removeClass("ui-corner-left ui-corner-right").end().button("destroy"), a.Widget.prototype.destroy.call(this)
    }})
})(jQuery)