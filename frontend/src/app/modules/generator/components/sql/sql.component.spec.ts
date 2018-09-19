/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { SqlComponent } from './sql.component';

describe('SqlComponent', () => {
  let component: SqlComponent;
  let fixture: ComponentFixture<SqlComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SqlComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SqlComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
