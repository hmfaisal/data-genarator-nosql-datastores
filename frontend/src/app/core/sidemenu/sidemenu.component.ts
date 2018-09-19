import { Component, OnInit, Output, EventEmitter, Input} from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-sidemenu',
  templateUrl: './sidemenu.component.html',
  styleUrls: ['./sidemenu.component.scss']
})
export class SidemenuComponent implements OnInit {

  @Input() menuitem: any;
  @Output() showEvent = new EventEmitter<boolean>();
  @Output() langChange = new EventEmitter<string>();
  selectedMenu: string;

  constructor(
    private router: Router,
  ) { }

  ngOnInit() {
  }

  sidemenuClose(){
    this.showEvent.emit();
  }

  changeLanguage(language: string){
    this.langChange.emit(language);
  }

}
