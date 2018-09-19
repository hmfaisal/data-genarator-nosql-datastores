import { Routes } from '@angular/router';

import { VolumeSelectorComponent } from './components/volume-selector/volume-selector.component';
import { VolumeAnalyzeComponent } from './components/volume-analyze/volume-analyze.component';
import { DatastoreConfigComponent } from './components/datastore-config/datastore-config.component';
import { JsonComponent } from './components/json/json.component';
import { XmlComponent } from './components/xml/xml.component';
import { SqlComponent } from './components/sql/sql.component';
import { CypherComponent } from './components/cypher/cypher.component';
import { GraphsonComponent } from './components/graphson/graphson.component';

export const GeneratorRoutes: Routes = [
  {
    path: '',
    redirectTo: 'config', 
    pathMatch: 'full'
  },
  {
    path: 'analyze',
    component: VolumeAnalyzeComponent,
  }
  ,
  {
    path: 'config',
    component: DatastoreConfigComponent,
  },
  {
    path: 'selector',
    component: VolumeSelectorComponent,
  },
  {
    path: 'json',
    component: JsonComponent,
  },
  {
    path: 'xml',
    component: XmlComponent,
  },
  {
    path: 'sql',
    component: SqlComponent,
  },
  {
    path: 'cypher',
    component: CypherComponent,
  },
  {
    path: 'graphson',
    component: GraphsonComponent,
  }
];