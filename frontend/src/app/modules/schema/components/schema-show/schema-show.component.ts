import { Component, OnInit, OnDestroy, ElementRef, Renderer2, ViewChild,HostListener } from '@angular/core';
import { ActivatedRoute, Router } from "@angular/router";
import { Subscription } from 'rxjs/Subscription';
import { TranslateService } from '@ngx-translate/core';
import { DialogService } from '../../../../services/dialog.service';
import {
  SchemaService,
  DataService
} from '../../../../services';

@Component({
  selector: 'app-schema-show',
  templateUrl: './schema-show.component.html',
  styleUrls: ['./schema-show.component.scss']
})
export class SchemaShowComponent implements OnInit, OnDestroy {

  endReached:boolean=false;
  result: any;
  private subscription = new Subscription();

  @ViewChild('tree') 
  private tree: ElementRef;

  constructor(
    private renderer: Renderer2,
    private elm: ElementRef
  ) { }

  ngOnInit() {

  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  @HostListener('window:scroll', ['$event'])
  onScroll(event) {
    if (window.scrollY>100) {
      this.endReached = true;
    }else{
      this.endReached = false;
    }
  }
  
  scrollToTop(){
    window.scrollTo(0,0);
  }

}
