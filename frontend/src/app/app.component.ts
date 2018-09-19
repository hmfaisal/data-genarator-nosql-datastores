import { Component, Inject, ViewChild, OnInit, OnDestroy, Input,Renderer2 } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { TranslateService } from '@ngx-translate/core';
import { MenuItems } from './shared/menu-items/menu-items';
import { CdkScrollable } from '@angular/cdk/scrolling';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit, OnDestroy {

  @Input() currentview: any;
  @ViewChild('sidemenu') sidemenu;
  dir = 'ltr';
  url: string;
  toggled:boolean=false;
  private subscription = new Subscription();
  private _router: Subscription;

  constructor(
    private translate: TranslateService,
    public menuItems: MenuItems,
    private router: Router,
    private renderer: Renderer2,
  ) {
    this.translate = translate;
    translate.addLangs(['en', 'de']);
    translate.setDefaultLang('en');
    const browserLang: string = translate.getBrowserLang();
    translate.use(browserLang.match(/en|de/) ? browserLang : 'en');
  }

  ngOnInit() {
    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        this.currentview = event.url;
      }
    });  
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  changeLanguage(language: string): void {
    this.translate.use(language);
  }

  sideClose($event) {
    this.renderer.removeClass(document.body, 'dialog-open');
    this.toggled = false;
    this.sidemenu.close();
  }

  toggle() {
    this.toggled = !this.toggled;
    if(this.toggled==true){
      this.renderer.addClass(document.body, 'dialog-open');
    }else{
      this.renderer.removeClass(document.body, 'dialog-open');
    }
    this.sidemenu.toggle();
  }
}
