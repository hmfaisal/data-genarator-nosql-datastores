import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from "@angular/router";
import { CustomValidators } from 'ng2-validation';
import { Subscription } from 'rxjs/Subscription';

import {
  AnalyzeService,
  GeneratorService,
  DataService
} from '../../../../services';

@Component({
  selector: 'app-datastore-config',
  templateUrl: './datastore-config.component.html',
  styleUrls: ['./datastore-config.component.scss']
})
export class DatastoreConfigComponent implements OnInit, OnDestroy {

  submitted = false;
  error: string;
  success: string;
  config:string;
  private subscription = new Subscription();

  constructor(
    private dataService: DataService,
    private router: Router
  ) {
    
   }

  ngOnInit() {
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  onSelect(event){
    this.dataService.changeChoice(event.value);
    this.router.navigate(['/generator/selector']);
  }

}
