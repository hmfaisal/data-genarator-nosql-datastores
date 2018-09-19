import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from "@angular/router";

@Component({
  selector: 'app-schema-analyze-button',
  templateUrl: './schema-analyze-button.component.html',
  styleUrls: ['./schema-analyze-button.component.scss']
})
export class SchemaAnalyzeButtonComponent implements OnInit {

  constructor(
    private router: Router,
  ) { }

  ngOnInit() {
  }

  onSelect(){
    this.router.navigate(['/generator']);
  }

}
