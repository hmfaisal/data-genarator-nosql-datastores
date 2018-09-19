import { Component, OnInit, OnDestroy, Input } from '@angular/core';
import { Router } from '@angular/router';
import { FormControl, FormGroup, FormArray, FormBuilder, Validators } from '@angular/forms';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CustomValidators } from 'ng2-validation';
import { Subscription } from 'rxjs/Subscription';
import { TranslateService } from '@ngx-translate/core';
import { MatSnackBar } from '@angular/material';

import {
  SchemaService,
  DataService
} from '../../../../services';

@Component({
  selector: 'app-schema-parse',
  templateUrl: './schema-parse.component.html',
  styleUrls: ['./schema-parse.component.scss']
})
export class SchemaParseComponent implements OnInit, OnDestroy {
  
  form: FormGroup;
  submitted = false;
  error: string;
  success: string;
  delete: string;
  private subscription = new Subscription();
  schema:any;

  constructor(
    private translate: TranslateService,
    public snackBar: MatSnackBar,
    private router: Router,
    private schemaService:SchemaService,
    private dataService:DataService,
    private formBuilder: FormBuilder
  ) {
    this.subscription.add(translate.get('WRONG_ERROR').subscribe((res: string) => {
      this.error = res;
    }));
    this.subscription.add(translate.get('SUBMIT_SUCCESSFUL').subscribe((result: string) => {
      this.success = result;
    }));
  }

  ngOnInit() {
    this.form = this.formBuilder.group({
      file: ['', Validators.compose([Validators.required])],
    });
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }


  onSubmit() {
    this.subscription.add(this.schemaService.parse(this.form.value.file)
      .subscribe(data => {
        this.schema=data;
        this.snackBar.open(this.success, 'X', {
            duration: 1000,
          });
      },
      error => {
        this.snackBar.open(this.error, 'X', {
            duration: 1000,
          });
      },
      () => {
        if (this.schema != 'undefined') {
          this.dataService.changeSchema(this.schema);
          this.router.navigate(['/schema/show']);
        }
      }));
  }


}
