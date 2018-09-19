import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SharedModule } from '../../shared/shared.module';
import { SharedCompoModule } from '../shared-compo/shared-compo.module';

import { GeneratorRoutes } from './generator.routing';
import { VolumeSelectorComponent } from './components/volume-selector/volume-selector.component';
import { VolumeAnalyzeComponent } from './components/volume-analyze/volume-analyze.component';
import { DatastoreConfigComponent } from './components/datastore-config/datastore-config.component';
import { JsonComponent } from './components/json/json.component';
import { XmlComponent } from './components/xml/xml.component';
import { SqlComponent } from './components/sql/sql.component';
import { CypherComponent } from './components/cypher/cypher.component';
import { GraphsonComponent } from './components/graphson/graphson.component';

import {
  AnalyzeService,
  GeneratorService,
} from '../../services';

@NgModule({
  imports: [
    CommonModule,
    FormsModule, 
    ReactiveFormsModule,
    SharedModule,
    SharedCompoModule,
    RouterModule.forChild(GeneratorRoutes),
  ],
  declarations: [
    VolumeAnalyzeComponent,
    VolumeSelectorComponent,
    DatastoreConfigComponent,
    JsonComponent,
    XmlComponent,
    SqlComponent,
    CypherComponent,
    GraphsonComponent
  ],
  exports:[

  ],
  providers:[
    AnalyzeService,
    GeneratorService
  ]
})
export class GeneratorModule { }