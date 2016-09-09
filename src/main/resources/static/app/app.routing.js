"use strict";
var router_1 = require("@angular/router");
var invoice_list_component_1 = require("./components/invoice-list.component");
var invoice_form_component_1 = require("./components/invoice-form.component");
var home_page_component_1 = require("./components/home-page.component");
var pagenotfound_component_1 = require("./components/pagenotfound.component");
exports.routes = [
    { path: '', redirectTo: '/home', pathMatch: 'full' },
    { path: 'home', component: home_page_component_1.HomePageComponent },
    // {path: 'about', component: AboutPageComponent},
    // {path: 'contacts', component: ContactsPageComponent},
    // {path: 'features', component: FeaturesPageComponent},
    // {path: 'login', component: LoginPageComponent},
    // {path: 'profile',component: ProfilePageComponent},
    { path: 'create/invoice', component: invoice_form_component_1.InvoiceFormComponent },
    // {path: 'settings', component: SettingsPageComponent},
    { path: 'invoices', component: invoice_list_component_1.InvoiceListComponent },
    // {path: 'logout', component: LogoutComponent}
    { path: '**', component: pagenotfound_component_1.PageNotFoundComponent }
];
exports.appRouterProvider = [router_1.provideRouter(exports.routes)];
//# sourceMappingURL=app.routing.js.map