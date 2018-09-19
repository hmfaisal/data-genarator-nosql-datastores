/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { XmlComponent } from './xml.component';

describe('XmlComponent', () => {
  let component: XmlComponent;
  let fixture: ComponentFixture<XmlComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ XmlComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(XmlComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
