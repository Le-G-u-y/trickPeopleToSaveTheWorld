import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPlanet } from 'app/shared/model/planet.model';

type EntityResponseType = HttpResponse<IPlanet>;
type EntityArrayResponseType = HttpResponse<IPlanet[]>;

@Injectable({ providedIn: 'root' })
export class PlanetService {
  public resourceUrl = SERVER_API_URL + 'api/planets';

  constructor(protected http: HttpClient) {}

  create(planet: IPlanet): Observable<EntityResponseType> {
    return this.http.post<IPlanet>(this.resourceUrl, planet, { observe: 'response' });
  }

  update(planet: IPlanet): Observable<EntityResponseType> {
    return this.http.put<IPlanet>(this.resourceUrl, planet, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPlanet>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPlanet[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
