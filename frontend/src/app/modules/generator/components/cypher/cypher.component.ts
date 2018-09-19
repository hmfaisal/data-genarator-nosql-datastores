import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from "@angular/router";
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
  selector: 'app-cypher',
  templateUrl: './cypher.component.html',
  styleUrls: ['./cypher.component.scss']
})
export class CypherComponent implements OnInit, OnDestroy {

  form: FormGroup;
  submitted = false;
  error: string;
  success: string;
  schemas: any;
  totalVolume: number;
  singleVolume:number;
  methods = [];
  technique:string;
  format:string;
  calculated:any;
  final:boolean =false;
  totalVol:number;
  totalTime:any;
  private subscription = new Subscription();

  constructor(
    private translate: TranslateService,
    public snackBar: MatSnackBar,
    private router: Router,
    private route: ActivatedRoute,
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
    this.subscription.add(this.dataService.currentVolume.subscribe(value => {
      if (value != "empty") {
        this.totalVolume = value;
      }
    }));
    this.subscription.add(this.dataService.currentSchema.subscribe(value => {
      if (value != "empty") {
        this.schemas = value;
      }
    }));
    this.subscription.add(this.dataService.currentMethod.subscribe(value => {
      if (value != "empty") {
        this.methods = value;
      }
    }));
    this.subscription.add(this.dataService.currentTechnique.subscribe(value => {
      if (value != "empty") {
        this.technique = value;
      }
    }));
    this.subscription.add(this.dataService.currentFormat.subscribe(value => {
      if (value != "empty") {
        this.format = value;
      }
    }));
    this.form = this.formBuilder.group({
      path: ['', Validators.compose([Validators.required])],
    });
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  toggle() {
    this.final = true;
  }

  onSubmit() {
    const config:any ={
      fileLocation:this.form.value.path
    };
    const wrap: any = {
      schema: this.schemas,
      singleVolume:0,
      volume: this.totalVolume,
      config: config,
      methods: this.methods,
      technique:this.technique,
      format:this.format
    };
    this.toggle();
    this.subscription.add(this.generatorService.cypherCalculation(wrap)
      .subscribe(data => {
        this.calculated=data;
      },
      error => {
      },
      () => {
        this.totalVol=this.analyzeService.byteToMb(this.calculated.volume);
        this.totalTime=this.analyzeService.getFormathours(this.calculated.time);
      }));
  }

  onGenerate() {
    const config:any ={
      fileLocation:this.form.value.path
    };
    const wrap: any = {
      schema: this.schemas,
      singleVolume:this.calculated.singleVolume,
      volume: this.calculated.volume,
      config: config,
      methods: this.methods,
      technique:this.technique,
      format:this.format
    };
    this.subscription.add(this.generatorService.cypherGeneration(wrap)
      .subscribe(data => {
        
      },
      error => {
      },
      () => {
      }));
  }

}
