import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { ConfigService } from './config.service';

@Injectable()
export class GeneratorService {

    headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');
    params: any;

    constructor(
        private http: HttpClient,
        private config:ConfigService,
    ) { }

    jsonCalculation(wrap:any) {
        return this.http.post(this.config.jsonCalculation_url, wrap, {headers: this.headers});
    }

    jsonGeneration(wrap:any) {
        return this.http.post(this.config.jsonGenerate_url, wrap, {headers: this.headers});
    }

    xmlCalculation(wrap:any) {
        return this.http.post(this.config.xmlCalculation_url, wrap, {headers: this.headers});
    }

    xmlGeneration(wrap:any) {
        return this.http.post(this.config.xmlGenerate_url, wrap, {headers: this.headers});
    }

    sqlCalculation(wrap:any) {
        return this.http.post(this.config.sqlCalculation_url, wrap, {headers: this.headers});
    }

    sqlGeneration(wrap:any) {
        return this.http.post(this.config.sqlGenerate_url, wrap, {headers: this.headers});
    }

    cypherGeneration(wrap:any) {
        return this.http.post(this.config.cypherGenerate_url, wrap, {headers: this.headers});
    }

    cypherCalculation(wrap:any) {
        return this.http.post(this.config.cypherCalculation_url, wrap, {headers: this.headers});
    }

    graphsonGeneration(wrap:any) {
        return this.http.post(this.config.graphsonGenerate_url, wrap, {headers: this.headers});
    }

    graphsonCalculation(wrap:any) {
        return this.http.post(this.config.graphsonCalculation_url, wrap, {headers: this.headers});
    }

}
