import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from "@angular/router";
import { FormControl, FormGroup, FormArray, FormBuilder, Validators } from '@angular/forms';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CustomValidators } from 'ng2-validation';
import { Subscription } from 'rxjs/Subscription';
import { TranslateService } from '@ngx-translate/core';
import { MatSnackBar } from '@angular/material';

import { Method } from '../../../../model/method';
import { Volume } from '../../../../model/volume';

import {
  AnalyzeService,
  GeneratorService,
  DataService
} from '../../../../services';

@Component({
  selector: 'app-volume-analyze',
  templateUrl: './volume-analyze.component.html',
  styleUrls: ['./volume-analyze.component.scss']
})
export class VolumeAnalyzeComponent implements OnInit, OnDestroy {

  form: FormGroup;
  submitted = false;
  error: string;
  success: string;
  schemas: any;
  tempVolume:any;
  totalVolume: number;
  methods = [];
  nodes = [];
  schemaTree: boolean = false;
  choice:string;
  format:string;
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
    this.subscription.add(this.dataService.currentChoice.subscribe(value => {
      if (value != "empty") {
        this.choice = value;
      }
    }));
    this.subscription.add(this.dataService.currentFormat.subscribe(value => {
      if (value != "empty") {
        this.format = value;
      }
    }));
    this.subscription.add(this.dataService.currentSchema.subscribe(value => {
      if (value != "empty" && this.totalVolume) {
        this.schemas = value;
        this.makeNodes(value);
      }
    }));
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  toggleSchemaTree() {
    this.schemaTree = !this.schemaTree;
  }

  makeNodes(schemas: any) {
    for (let data of schemas) {
      for (let value of data.value) {
        const method: any = {
          id: value.id,
          method: "MIMICKDUPLICATE",
          total: this.totalVolume,
        };
        this.methods.push(method);
        if (value.valueType == "OBJECT" || value.valueType == "ARRAY") {
          
        }else{
          this.nodes.push(value);
        }
      }
    }
  }

  onChange(id, event) {
    for (let method of this.methods) {
      if (method.id == id) {
        method.method = event.value;
      }
    }
  }

  onOther(id) {
    this.setVolume(id, this.totalVolume);
  }

  onUnique(id) {
    const wrap: any = {
      schema: this.schemas,
      choice:this.choice,
      volume:this.totalVolume,
      id: id
    };
    this.subscription.add(this.analyzeService.getVolumeCount(wrap)
      .subscribe(data => {
        this.tempVolume=data;
      },
      error => {

      },
      () => {
        if(this.tempVolume){
          this.setVolume(id, this.tempVolume);
        }
      }));
  }

  setVolume(id, volume) {
    for (let met of this.methods) {
      if (met.id == id) {
        met.total = volume;
      }
    }
  }

  setTotalVolume() {
    for (let met of this.methods) {
      let volume = met.total;
      if(volume<this.totalVolume){
        this.totalVolume = volume;
      }
    }
  }

  onSubmit(){
    //this.setTotalVolume();
    this.dataService.changeVolume(this.totalVolume);
    this.dataService.changeMethod(this.methods);
    if(this.choice=="JSON"){
      this.router.navigate(['/generator/json']);
    }else if(this.choice=="SQL"){
      this.router.navigate(['/generator/sql']);
    }else if(this.choice=="CQL"){
      this.router.navigate(['/generator/cypher']);
    }else if(this.choice=="GRAPHSON"){
      this.router.navigate(['/generator/graphson']);
    }else if(this.choice=="XML"){
      this.router.navigate(['/generator/xml']);
    }
  }

}
