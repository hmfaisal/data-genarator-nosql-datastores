import { Component, OnInit, OnDestroy, Inject} from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { Router, ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';

@Component({
  selector: 'app-schema-id',
  templateUrl: './schema-id.component.html',
  styleUrls: ['./schema-id.component.scss']
})
export class SchemaIdComponent implements OnInit, OnDestroy {

  private subscription = new Subscription();

  constructor(
    public dialogRef: MatDialogRef<SchemaIdComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {}

  ngOnInit() {

  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  onDialogClose() {
    this.dialogRef.close();
  }

}
