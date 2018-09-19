import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Subject } from 'rxjs/Subject';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';

@Injectable()
export class DataService {

    constructor() { }

    private schemaSource = new BehaviorSubject<any>("empty");
    currentSchema = this.schemaSource.asObservable();
    private techniqueSource = new BehaviorSubject<any>("empty");
    currentTechnique = this.techniqueSource.asObservable();
    private volumeSource = new BehaviorSubject<any>("empty");
    currentVolume = this.volumeSource.asObservable();
    private methodSource = new BehaviorSubject<any>("empty");
    currentMethod = this.methodSource.asObservable();
    private choiceSource = new BehaviorSubject<any>("empty");
    currentChoice = this.choiceSource.asObservable();
    private formatSource = new BehaviorSubject<any>("empty");
    currentFormat = this.formatSource.asObservable();

    changeSchema(schema: any) {
        this.schemaSource.next(schema);
    }

    clearSchema() {
        this.schemaSource.next("empty");
    }

    changeTechnique(technique: string) {
        this.techniqueSource.next(technique);
    }

    clearTechnique() {
        this.techniqueSource.next("empty");
    }

    changeVolume(volume: number) {
        this.volumeSource.next(volume);
    }

    clearVolume() {
        this.volumeSource.next("empty");
    }

    changeMethod(method: any) {
        this.methodSource.next(method);
    }

    clearMethod() {
        this.methodSource.next("empty");
    }

    changeChoice(choice: any) {
        this.choiceSource.next(choice);
    }

    clearChoice() {
        this.choiceSource.next("empty");
    }

    changeFormat(format: any) {
        this.formatSource.next(format);
    }

    clearFormat() {
        this.formatSource.next("empty");
    }

    clear(){
        this.clearSchema();
    }
}