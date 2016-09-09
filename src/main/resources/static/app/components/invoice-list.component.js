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
var invoice_service_1 = require("../services/invoice.service");
var invoice_component_1 = require("./invoice.component");
var router_1 = require("@angular/router");
/**
 * Represents a list of all invoices provided
 * from a service. Uses dependency injection to load
 * our service.
 * @class
 */
var InvoiceListComponent = (function () {
    function InvoiceListComponent(_invoiceService) {
        this._invoiceService = _invoiceService;
    }
    /**
     * Implemented method from {@link OnInit} interface which
     * is called after the constructor of the class. We use the
     * provided service to load all invoices.
     */
    InvoiceListComponent.prototype.ngOnInit = function () {
        var _this = this;
        this._invoiceService.getInvoices()
            .then(function (response) { return _this.invoices = response; })
            .catch(function (error) { return console.error(error); });
    };
    InvoiceListComponent = __decorate([
        core_1.Component({
            selector: 'invoice-list',
            templateUrl: 'templates/invoice-list.component.html',
            providers: [invoice_service_1.InvoiceService],
            directives: [router_1.ROUTER_DIRECTIVES, invoice_component_1.InvoiceComponent]
        }), 
        __metadata('design:paramtypes', [invoice_service_1.InvoiceService])
    ], InvoiceListComponent);
    return InvoiceListComponent;
}());
exports.InvoiceListComponent = InvoiceListComponent;
//# sourceMappingURL=invoice-list.component.js.map