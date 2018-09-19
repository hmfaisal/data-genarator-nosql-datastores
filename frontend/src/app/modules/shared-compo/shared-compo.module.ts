import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SharedModule } from '../../shared/shared.module';

import { SchemaTreeComponent } from './schema-tree/schema-tree.component';
import { SchemaIdComponent } from './schema-id/schema-id.component';

import { DialogService } from '../../services/dialog.service';
import {
  SchemaService,
} from '../../services';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    SharedModule
  ],
  declarations: [
    SchemaIdComponent,
    SchemaTreeComponent,
],
  exports:[
    SchemaTreeComponent,
    SchemaIdComponent
  ],
  providers: [
    SchemaService,
    DialogService
  ],
  entryComponents: [
    SchemaIdComponent
  ]
})
export class SharedCompoModule { }