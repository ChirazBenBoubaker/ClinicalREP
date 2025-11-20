import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDate, NewDate } from '../date.model';

export type PartialUpdateDate = Partial<IDate> & Pick<IDate, 'id'>;

type RestOf<T extends IDate | NewDate> = Omit<T, 'date'> & {
  date?: string | null;
};

export type RestDate = RestOf<IDate>;

export type NewRestDate = RestOf<NewDate>;

export type PartialUpdateRestDate = RestOf<PartialUpdateDate>;

export type EntityResponseType = HttpResponse<IDate>;
export type EntityArrayResponseType = HttpResponse<IDate[]>;

@Injectable({ providedIn: 'root' })
export class DateService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/dates');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(date: NewDate): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(date);
    return this.http.post<RestDate>(this.resourceUrl, copy, { observe: 'response' }).pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(date: IDate): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(date);
    return this.http
      .put<RestDate>(`${this.resourceUrl}/${this.getDateIdentifier(date)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(date: PartialUpdateDate): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(date);
    return this.http
      .patch<RestDate>(`${this.resourceUrl}/${this.getDateIdentifier(date)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestDate>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestDate[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getDateIdentifier(date: Pick<IDate, 'id'>): number {
    return date.id;
  }

  compareDate(o1: Pick<IDate, 'id'> | null, o2: Pick<IDate, 'id'> | null): boolean {
    return o1 && o2 ? this.getDateIdentifier(o1) === this.getDateIdentifier(o2) : o1 === o2;
  }

  addDateToCollectionIfMissing<Type extends Pick<IDate, 'id'>>(
    dateCollection: Type[],
    ...datesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const dates: Type[] = datesToCheck.filter(isPresent);
    if (dates.length > 0) {
      const dateCollectionIdentifiers = dateCollection.map(dateItem => this.getDateIdentifier(dateItem)!);
      const datesToAdd = dates.filter(dateItem => {
        const dateIdentifier = this.getDateIdentifier(dateItem);
        if (dateCollectionIdentifiers.includes(dateIdentifier)) {
          return false;
        }
        dateCollectionIdentifiers.push(dateIdentifier);
        return true;
      });
      return [...datesToAdd, ...dateCollection];
    }
    return dateCollection;
  }

  protected convertDateFromClient<T extends IDate | NewDate | PartialUpdateDate>(date: T): RestOf<T> {
    return {
      ...date,
      date: date.date?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restDate: RestDate): IDate {
    return {
      ...restDate,
      date: restDate.date ? dayjs(restDate.date) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestDate>): HttpResponse<IDate> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestDate[]>): HttpResponse<IDate[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
