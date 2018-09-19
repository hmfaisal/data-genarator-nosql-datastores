import { Component, OnInit, OnDestroy, Input } from '@angular/core';
import { Router } from '@angular/router';
import { FormControl, FormGroup, FormArray, FormBuilder, Validators } from '@angular/forms';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CustomValidators } from 'ng2-validation';
import { Subscription } from 'rxjs/Subscription';
import { TranslateService } from '@ngx-translate/core';
import { MatSnackBar } from '@angular/material';

import {
  AnalyzeService,
  GeneratorService,
  DataService
} from '../../../../services';

@Component({
  selector: 'app-volume-selector',
  templateUrl: './volume-selector.component.html',
  styleUrls: ['./volume-selector.component.scss']
})
export class VolumeSelectorComponent implements OnInit, OnDestroy {

  form: FormGroup;
  submitted = false;
  error: string;
  success: string;
  delete: string;
  technique:string;
  choice:string;
  format:string;
  techniqueShow:boolean=false;
  formShow:boolean=false;
  totalVol:number;
  private subscription = new Subscription();

  constructor(
    private translate: TranslateService,
    public snackBar: MatSnackBar,
    private router: Router,
    private analyzeService: AnalyzeService,
    private generatorService: GeneratorService,
    private dataService: DataService,
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
    this.subscription.add(this.dataService.currentChoice.subscribe(value => {
      if (value != "empty") {
        this.choice = value;
      }
    }));
    this.form = this.formBuilder.group({
      volume: ['', Validators.compose([Validators.required])],
    });
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  onSelectFormat(event){
    this.format= event.value;
    this.techniqueShow=true;
  }

  onSelectTechnique(event){
    this.technique= event.value;
    this.formShow=true;
  }

  onSubmit() {
    if(this.technique=="BYVOLUME"){
      this.totalVol=this.analyzeService.mbToByte(this.form.value.volume);
    }else{
      this.totalVol=this.form.value.volume;
    }
    
    this.dataService.changeVolume(this.totalVol);
    this.dataService.changeTechnique(this.technique);
    this.dataService.changeFormat(this.format);
    this.router.navigate(['/generator/analyze']);
  }

}
