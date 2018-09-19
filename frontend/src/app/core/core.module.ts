import {NgModule, Optional, SkipSelf} from '@angular/core';
import {CommonModule} from '@angular/common';

import {throwIfAlreadyLoaded} from './module-import-guard';

import { NavComponent } from './nav/nav.component';
import { FooterComponent } from './footer/footer.component';
import { SharedModule } from '../shared/shared.module';
import { RouterModule } from '@angular/router';
import { Error404Component } from './error404/error-404.component';
import { SidemenuComponent } from './sidemenu/sidemenu.component';


@NgModule({
  imports: [
    CommonModule,
    RouterModule,
    SharedModule
  ],
  exports: [
    NavComponent,
    FooterComponent,
    Error404Component,
    SidemenuComponent
  ],
  declarations: [
    NavComponent,
    FooterComponent,
    Error404Component,
    SidemenuComponent
],
  providers: [ 

  ]
})

export class CoreModule {
  constructor(@Optional() @SkipSelf() parentModule: CoreModule) {
    throwIfAlreadyLoaded(parentModule, 'CoreModule');
  }
}