import { Injectable } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA, MatDialogConfig } from '@angular/material';
import { Observable } from 'rxjs/Rx';

import { SchemaIdComponent } from '../modules/shared-compo/schema-id/schema-id.component';


@Injectable()
export class DialogService {

    constructor(
        private dialog: MatDialog
    ) { }

    public schema(schema:any): Observable<boolean> {
        let dialogRef = this.dialog.open(SchemaIdComponent, {
            data: {
                schema:schema,
            },
          });
        return dialogRef.afterClosed();
    }

}