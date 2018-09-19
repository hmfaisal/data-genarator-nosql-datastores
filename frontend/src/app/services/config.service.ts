import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';

@Injectable()
export class ConfigService {

    private _api_url = '/api';
    private _schema_url = this._api_url + '/schema';
    private _schemaParse_url = this._schema_url + '/parse';
    private _analyze_url = this._api_url + '/analyze';
    private _analyzeCalc_url = this._analyze_url + '/calculation';
    private _analyzeVolumeCount_url = this._analyze_url + '/volume/count';
    private _json_url = this._api_url + '/json';
    private _jsonGenerate_url = this._json_url + '/generate';
    private _jsonCalculation_url = this._json_url + '/calculation';
    private _sql_url = this._api_url + '/sql';
    private _sqlGenerate_url = this._sql_url + '/generate';
    private _sqlCalculation_url = this._sql_url + '/calculation';
    private _cypher_url = this._api_url + '/cypher';
    private _cypherGenerate_url = this._cypher_url + '/generate';
    private _cypherCalculation_url = this._cypher_url + '/calculation';
    private _graphson_url = this._api_url + '/graphson';
    private _graphsonGenerate_url = this._graphson_url + '/generate';
    private _graphsonCalculation_url = this._graphson_url + '/calculation';
    private _xml_url = this._api_url + '/xml';
    private _xmlGenerate_url = this._xml_url + '/generate';
    private _xmlCalculation_url = this._xml_url + '/calculation';

    get schema_url(): string {
        return this._schema_url;
    }
    
    get schemaParse_url(): string {
        return this._schemaParse_url;
    }

    get analyze_url(): string {
        return this._analyze_url;
    }
    
    get analyzeCalc_url(): string {
        return this._analyzeCalc_url;
    }

    get analyzeVolumeCount_url(): string {
        return this._analyzeVolumeCount_url;
    }

    get json_url(): string {
        return this._json_url;
    }

    get jsonCalculation_url(): string {
        return this._jsonCalculation_url;
    }
    
    get jsonGenerate_url(): string {
        return this._jsonGenerate_url;
    }

    get xml_url(): string {
        return this._xml_url;
    }

    get xmlCalculation_url(): string {
        return this._xmlCalculation_url;
    }
    
    get xmlGenerate_url(): string {
        return this._xmlGenerate_url;
    }

    get sql_url(): string {
        return this._sql_url;
    }

    get sqlCalculation_url(): string {
        return this._sqlCalculation_url;
    }
    
    get sqlGenerate_url(): string {
        return this._sqlGenerate_url;
    }

    get cypher_url(): string {
        return this._cypher_url;
    }

    get cypherCalculation_url(): string {
        return this._cypherCalculation_url;
    }
    
    get cypherGenerate_url(): string {
        return this._cypherGenerate_url;
    }

    get graphson_url(): string {
        return this._graphson_url;
    }

    get graphsonCalculation_url(): string {
        return this._graphsonCalculation_url;
    }
    
    get graphsonGenerate_url(): string {
        return this._graphsonGenerate_url;
    }

}
