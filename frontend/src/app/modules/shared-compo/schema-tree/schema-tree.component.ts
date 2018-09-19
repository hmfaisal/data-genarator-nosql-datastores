import { Component, OnInit, OnDestroy, ElementRef, Renderer2, ViewChild,HostListener } from '@angular/core';
import { ActivatedRoute, Router } from "@angular/router";
import { Subscription } from 'rxjs/Subscription';
import { TranslateService } from '@ngx-translate/core';
import { DialogService } from '../../../services/dialog.service';
import {
  SchemaService,
  DataService
} from '../../../services';

@Component({
  selector: 'app-schema-tree',
  templateUrl: './schema-tree.component.html',
  styleUrls: ['./schema-tree.component.scss']
})
export class SchemaTreeComponent implements OnInit, OnDestroy {

  schemas: any;
  keys = [];
  nodes = [];
  visitedKeys = [];
  endReached:boolean=false;
  result: any;
  private subscription = new Subscription();

  @ViewChild('tree') 
  private tree: ElementRef;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private schemaService: SchemaService,
    private dataService: DataService,
    private dialogService: DialogService,
    private renderer: Renderer2,
    private elm: ElementRef
  ) { }

  ngOnInit() {
    this.subscription.add(this.dataService.currentSchema.subscribe(value => {
      if (value != "empty") {
        this.schemas = value;
        this.makeKeys(value);
      }
    }));
    if(this.keys.length>0 && this.schemas){
      this.getNodes();
    }
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  onSelect(value){
    this.subscription.add(this.dialogService
    .schema(value)
    .subscribe(res => this.result = res));
  }

  makeKeys(schemas) {
    for (let data of schemas) {
      this.keys.push(data.key);
    }
  }

  getNodes() {
    for (let key of this.keys) {
      if(!this.isVisited(key)){
        const pdiv = this.renderer.createElement('ul');
        const ptext = this.renderer.createText('');
        this.renderer.appendChild(pdiv,ptext);
        this.renderer.appendChild(this.tree.nativeElement, pdiv);
        this.renderer.setAttribute(pdiv, 'id', key);

        /*
        const cdiv = this.renderer.createElement('li');
        const text = this.renderer.createText(key);
        this.renderer.appendChild(cdiv, text);
        this.renderer.appendChild(pdiv, cdiv);
        */
        
        this.getChildren(key);
      }
    }
  }

  getChildren(key: number) {
    this.visitedKeys.push(key);
    for (let data of this.schemas) {
      if (data.key === key) {
        for (let value of data.value) {
          let id = value.id;
          if (this.isKey(id)) {
            this.createChild(value,"parent");
            this.createParent(value);
            this.nodes.push(value);
            this.getChildren(id);         
          }else{
            this.createChild(value,"child");
            this.nodes.push(value);
          }
        }
      }
    }
  }

  createParent(value:any){
    const parent= document.getElementById(value.parentId);
    const div = this.renderer.createElement('ul');
    const text = this.renderer.createText('');
    this.renderer.appendChild(div,text);
    this.renderer.appendChild(parent, div);
    this.renderer.setAttribute(div, 'id', value.id);
  }

  createChild(value:any,source:string){
    const parent= document.getElementById(value.parentId);
    const div = this.renderer.createElement('li');
    const text = this.renderer.createText(''); 
    this.renderer.appendChild(div, text);
    this.renderer.appendChild(parent, div);
    if(source=="child"){
      this.renderer.setAttribute(div, 'id', value.id);
    }
    
    const a = this.renderer.createElement('a');
    const atext = this.renderer.createText(value.nodeName);     
    this.renderer.appendChild(a, atext);
    this.renderer.appendChild(div, a);
    this.renderer.listen(a, 'click', (evt) => {
      this.onSelect(value);
    });
  }

  isKey(id: number): boolean {
    for (let key of this.keys) {
      if (key === id) {
        return true;
      }
    }
  }

  isVisited(key):boolean{
    if(this.visitedKeys.length>0){
      for (let vkey of this.visitedKeys) {
        if(key===vkey){
          return true;
        }
      }
    }   
  }

}
