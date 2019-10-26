import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ILightingData } from 'app/shared/model/lighting-data.model';

type EntityResponseType = HttpResponse<ILightingData>;
type EntityArrayResponseType = HttpResponse<ILightingData[]>;

@Injectable({ providedIn: 'root' })
export class LightingDataService {
  public resourceUrl = SERVER_API_URL + 'api/lighting-data';

  constructor(protected http: HttpClient) {}

  create(lightingData: ILightingData): Observable<EntityResponseType> {
    return this.http.post<ILightingData>(this.resourceUrl, lightingData, { observe: 'response' });
  }

  update(lightingData: ILightingData): Observable<EntityResponseType> {
    return this.http.put<ILightingData>(this.resourceUrl, lightingData, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ILightingData>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILightingData[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
