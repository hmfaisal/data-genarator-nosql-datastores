import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { ConfigService } from './config.service';

@Injectable()
export class SchemaService {

    headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');
    params: any;

    constructor(
        private http: HttpClient,
        private config:ConfigService,
    ) { }

    getAll(): Observable<any[]> {
        return this.http.get<any[]>(this.config.schema_url) 
    }
    
    parse(schema:any) {
        //return this.http.post(this.config.schemaParse_url, schema, {headers: this.headers});
        return this.http.get<any[]>(this.config.schemaParse_url+"?file="+schema);
    }

}