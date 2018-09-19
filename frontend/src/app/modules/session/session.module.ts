import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import {SharedModule} from '../../shared/shared.module';

import { SessionRoutes } from './session.routing';
import { NotFoundComponent } from './not-found/not-found.component';
import { ErrorComponent } from './error/error.component';

@NgModule({
  imports: [
    CommonModule,
    SharedModule,
    RouterModule.forChild(SessionRoutes),
    FormsModule,
    ReactiveFormsModule
  ],
  declarations: [
    NotFoundComponent,
    ErrorComponent
  ],
  providers:[
  ]
})

export class SessionModule {}
