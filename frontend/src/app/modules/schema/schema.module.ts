import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SharedModule } from '../../shared/shared.module';
import { SharedCompoModule } from '../shared-compo/shared-compo.module';

import { SchemaRoutes } from './schema.routing';
import { SchemaParseComponent } from './components/schema-parse/schema-parse.component';
import { SchemaShowComponent } from './components/schema-show/schema-show.component';
import { SchemaAnalyzeButtonComponent } from './components/schema-analyze-button/schema-analyze-button.component';

import { DialogService } from '../../services/dialog.service';
import {
  SchemaService,
} from '../../services';

@NgModule({
  imports: [
    CommonModule,
    FormsModule, 
    ReactiveFormsModule,
    SharedModule,
    SharedCompoModule,
    RouterModule.forChild(SchemaRoutes),
  ],
  declarations: [
    SchemaParseComponent,
    SchemaShowComponent,
    SchemaAnalyzeButtonComponent
  ],
  exports:[
  ],
  providers:[
    SchemaService,
    DialogService
  ],
})
export class SchemaModule { }