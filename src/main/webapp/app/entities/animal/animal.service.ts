import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAnimal } from 'app/shared/model/animal.model';

type EntityResponseType = HttpResponse<IAnimal>;
type EntityArrayResponseType = HttpResponse<IAnimal[]>;

@Injectable({ providedIn: 'root' })
export class AnimalService {
  public resourceUrl = SERVER_API_URL + 'api/animals';

  constructor(protected http: HttpClient) {}

  create(animal: IAnimal): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(animal);
    return this.http
      .post<IAnimal>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(animal: IAnimal): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(animal);
    return this.http
      .put<IAnimal>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAnimal>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAnimal[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(animal: IAnimal): IAnimal {
    const copy: IAnimal = Object.assign({}, animal, {
      creationDate: animal.creationDate != null && animal.creationDate.isValid() ? animal.creationDate.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.creationDate = res.body.creationDate != null ? moment(res.body.creationDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((animal: IAnimal) => {
        animal.creationDate = animal.creationDate != null ? moment(animal.creationDate) : null;
      });
    }
    return res;
  }
}
