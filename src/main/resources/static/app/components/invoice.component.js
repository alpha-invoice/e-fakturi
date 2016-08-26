"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
var core_1 = require("@angular/core");
var invoice_1 = require("../models/invoice");
/**
 * Represents a single Invoice component.
 * It gets the invoiceToBeStored to be rendered by an Input variable
 * passed from the {@link InvoiceListComponent} component.
 * @class
 */
var InvoiceComponent = (function () {
    function InvoiceComponent() {
    }
    __decorate([
        core_1.Input(), 
        __metadata('design:type', invoice_1.Invoice)
    ], InvoiceComponent.prototype, "invoice", void 0);
    InvoiceComponent = __decorate([
        core_1.Component({
            selector: 'invoice',
            templateUrl: 'templates/invoice.component.html'
        }), 
        __metadata('design:paramtypes', [])
    ], InvoiceComponent);
    return InvoiceComponent;
}());
exports.InvoiceComponent = InvoiceComponent;
//# sourceMappingURL=invoice.component.js.map