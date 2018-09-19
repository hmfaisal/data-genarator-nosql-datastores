import { Routes } from '@angular/router';

import { SchemaParseComponent } from './components/schema-parse/schema-parse.component';
import { SchemaShowComponent } from './components/schema-show/schema-show.component';

export const SchemaRoutes: Routes = [
  {
    path: '',
    redirectTo: 'parse', 
    pathMatch: 'full'
  },
  {
    path: 'parse',
    component: SchemaParseComponent,
  },
  {
    path: 'show',
    component: SchemaShowComponent,
  },
];