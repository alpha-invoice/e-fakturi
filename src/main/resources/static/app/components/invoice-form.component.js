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
var company_1 = require("../models/company");
var item_1 = require("../models/item");
var invoice_service_1 = require("../services/invoice.service");
var ng2_file_upload_1 = require('ng2-file-upload');
// URL for uploading a template
var UPLOAD_TEMPLATE_URL = 'http://localhost:8080/api/upload';
// 3 MB
var MAX_FILE_SIZE = 3 * 1024 * 1024;
var DOCX_FILE_MIME_TYPE = 'application/vnd.openxmlformats-officedocument.wordprocessingml.document';
/**
 * Represents a form which submits new invoices
 * to the service. Uses dependency injection to load
 * our service.
 * @class
 */
var InvoiceFormComponent = (function () {
    function InvoiceFormComponent(_invoiceService) {
        this._invoiceService = _invoiceService;
    }
    /**
     * Implemented method from {@link OnInit} interface which
     * is called after the constructor of the class. We instantiate
     * our invoiceToBeStored by an empty Invoice object which we
     * fill from the form.
     */
    InvoiceFormComponent.prototype.ngOnInit = function () {
        this.invoiceToBeStored = invoice_1.Invoice.createEmptyInvoice();
        this.isFileSizeTooLarge = false;
        this.isFileTypeInvalid = false;
        this.initFileUploader();
    };
    /**
     * Initialize the FileUploader instance (this.uploader) with basic configurations
     */
    InvoiceFormComponent.prototype.initFileUploader = function () {
        var _this = this;
        // Instantiate a file uploader using an upload URL
        // TODO: Add an authToken to the file uploader when authentication is implemented
        this.uploader = new ng2_file_upload_1.FileUploader({ url: UPLOAD_TEMPLATE_URL });
        // Set constraints for file size (max 3MB) and file extension (.docx)
        this.uploader.setOptions({
            allowedMimeType: [DOCX_FILE_MIME_TYPE],
            maxFileSize: MAX_FILE_SIZE
        });
        // Hook: Set the method type for uploading an item to 'POST'                          
        this.uploader.onBeforeUploadItem = function (fileItem) {
            fileItem.method = 'POST';
        };
        // Hook: When the user links a file, upload immediately
        this.uploader.onAfterAddingFile = function (fileItem) {
            fileItem.upload();
            _this.isFileSizeTooLarge = false;
            _this.isFileTypeInvalid = false;
        };
        /**
         * Hook: Give feedback to the user if the file he wants to upload is invalid and doesn't meet the constraints.
         * Based on the isFileSizeTooLarge and isFileTypeInvalid values different error messages are displayed in the HTML.
         */
        this.uploader.onWhenAddingFileFailed = function (item, filter, options) {
            _this.isFileSizeTooLarge = !_this.uploader._fileSizeFilter(item);
            _this.isFileTypeInvalid = !_this.uploader._mimeTypeFilter(item);
        };
    };
    /**
     * EventHandler method which is called when the form Add button
     * is clicked. It stores the updated invoiceToBeStored object by
     * passing it to the invoice service.
     * @param invoiceNumber string passed from the form input.
     * @param sender anonymous object passed from the form input.
     * @param recipient anonymous object passed from the form input.
     * @param item anonymous object passed from the form input.
     */
    InvoiceFormComponent.prototype.addNewInvoice = function (invoiceNumber, sender, recipient, item) {
        this.updateInvoiceFromForm(invoiceNumber, sender, recipient, item);
        this._invoiceService.addInvoice(this.invoiceToBeStored);
    };
    /**
     * Updates the invoiceToBeStored object by storing the parsed form values
     * to the appropriate fields of the Invoice object.
     * @param invoiceNumber string
     * @param sender anonymous object which needs to be mapped to a Company instance
     * @param recipient anonymous object which needs to be mapped to a Company instance
     * @param item anonymous object which needs to be mapped to an Item instance
     */
    InvoiceFormComponent.prototype.updateInvoiceFromForm = function (invoiceNumber, sender, recipient, item) {
        this.invoiceToBeStored.invoiceNumber = invoiceNumber;
        this.invoiceToBeStored.sender = company_1.Company.parseOutputObjectToCompany(sender);
        this.invoiceToBeStored.recipient = company_1.Company.parseOutputObjectToCompany(recipient);
        this.invoiceToBeStored.items.push(item_1.Item.parseOutputObjectToItem(item));
    };
    InvoiceFormComponent = __decorate([
        core_1.Component({
            selector: 'invoice-form',
            templateUrl: 'templates/invoice-form.component.html',
            providers: [invoice_service_1.InvoiceService],
            directives: [ng2_file_upload_1.FILE_UPLOAD_DIRECTIVES]
        }), 
        __metadata('design:paramtypes', [invoice_service_1.InvoiceService])
    ], InvoiceFormComponent);
    return InvoiceFormComponent;
}());
exports.InvoiceFormComponent = InvoiceFormComponent;
//# sourceMappingURL=invoice-form.component.js.map