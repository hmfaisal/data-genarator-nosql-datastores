import { Component, OnInit, Input, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';


@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.scss']
})

export class NavComponent implements OnInit {

  @Input() menuitem: any;
  selectedMenu: string;

  constructor(
    private router: Router,
  ) {
    
  }

  ngOnInit() {
    
  }

}