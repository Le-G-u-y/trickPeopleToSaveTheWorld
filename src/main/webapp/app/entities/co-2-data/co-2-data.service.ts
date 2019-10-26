import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICo2Data } from 'app/shared/model/co-2-data.model';

type EntityResponseType = HttpResponse<ICo2Data>;
type EntityArrayResponseType = HttpResponse<ICo2Data[]>;

@Injectable({ providedIn: 'root' })
export class Co2DataService {
  public resourceUrl = SERVER_API_URL + 'api/co-2-data';

  constructor(protected http: HttpClient) {}

  create(co2Data: ICo2Data): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(co2Data);
    return this.http
      .post<ICo2Data>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(co2Data: ICo2Data): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(co2Data);
    return this.http
      .put<ICo2Data>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICo2Data>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICo2Data[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(co2Data: ICo2Data): ICo2Data {
    const copy: ICo2Data = Object.assign({}, co2Data, {
      timestamp: co2Data.timestamp != null && co2Data.timestamp.isValid() ? co2Data.timestamp.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.timestamp = res.body.timestamp != null ? moment(res.body.timestamp) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((co2Data: ICo2Data) => {
        co2Data.timestamp = co2Data.timestamp != null ? moment(co2Data.timestamp) : null;
      });
    }
    return res;
  }
}
