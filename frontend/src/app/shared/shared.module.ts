import { NgModule } from '@angular/core';

import { MaterialModule } from './material/material.module';
import { TranslateModule } from '@ngx-translate/core';
import { FlexLayoutModule } from '@angular/flex-layout';
import { ClickOutsideModule } from 'ng4-click-outside';
import { ScrollDirective } from './scroll/scroll.directive';

import { MenuItems } from './menu-items/menu-items';

@NgModule({
  imports: [
    FlexLayoutModule,
    MaterialModule,
    TranslateModule,
    ClickOutsideModule
  ],
  declarations: [
    ScrollDirective,
  ],
  exports: [
    FlexLayoutModule,
    MaterialModule,
    TranslateModule,
    ClickOutsideModule,
    ScrollDirective
   ],
  providers: [
    MenuItems
  ]
})
export class SharedModule { }
