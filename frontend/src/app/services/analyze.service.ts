import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { ConfigService } from './config.service';

@Injectable()
export class AnalyzeService {

    headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');
    params: any;
    seconds:any;
    minutes:any;
    hours:any;
    days:any;

    constructor(
        private http: HttpClient,
        private config: ConfigService,
    ) { }

    calculate(schema: any) {
        return this.http.post(this.config.analyzeCalc_url, schema, { headers: this.headers });
        //return this.http.get<any[]>(this.config.analyzeCalc_url+"?schema="+schema);
    }

    getVolumeCount(wrap: any) {
        return this.http.post(this.config.analyzeVolumeCount_url, wrap, { headers: this.headers });
    }

    mbToByte(volume: number): number {
        return volume * 1000000;
    }

    byteToMb(volume: number): number {
        return volume / 1000000;
    }

    getFormathours(millisec) {
        this.seconds = (millisec / 1000).toFixed(1);
        this.minutes = (millisec / (1000 * 60)).toFixed(1);
        this.hours = (millisec / (1000 * 60 * 60)).toFixed(1);
        this.days = (millisec / (1000 * 60 * 60 * 24)).toFixed(1);
        if (this.seconds < 60) {
            return this.seconds + " Sec";
        } else if (this.minutes < 60) {
            return this.minutes + " Min";
        } else if (this.hours < 24) {
            return this.hours + " Hrs";
        } else {
            return this.days + " Days"
        }
    }

}
