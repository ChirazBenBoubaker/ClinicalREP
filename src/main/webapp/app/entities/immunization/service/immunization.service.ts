import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IImmunization, NewImmunization } from '../immunization.model';
import { RestEncounter } from '../../encounter/service/encounter.service';

export type PartialUpdateImmunization = Partial<IImmunization> & Pick<IImmunization, 'id'>;

type RestOf<T extends IImmunization | NewImmunization> = Omit<T, 'date'> & {
  date?: string | null;
};

export type RestImmunization = RestOf<IImmunization>;

export type NewRestImmunization = RestOf<NewImmunization>;

export type PartialUpdateRestImmunization = RestOf<PartialUpdateImmunization>;

export type EntityResponseType = HttpResponse<IImmunization>;
export type EntityArrayResponseType = HttpResponse<IImmunization[]>;

@Injectable({ providedIn: 'root' })
export class ImmunizationService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/immunizations');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(immunization: NewImmunization): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(immunization);
    return this.http
      .post<RestImmunization>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(immunization: IImmunization): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(immunization);
    return this.http
      .put<RestImmunization>(`${this.resourceUrl}/${this.getImmunizationIdentifier(immunization)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(immunization: PartialUpdateImmunization): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(immunization);
    return this.http
      .patch<RestImmunization>(`${this.resourceUrl}/${this.getImmunizationIdentifier(immunization)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestImmunization>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestImmunization[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }
  count(req?: any): any {
    const options = createRequestOption(req);
    return this.http.get<RestImmunization[]>(`${this.resourceUrl}/count`, { params: options, observe: 'response' });
  }
  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  queryCountByDate2(month: number, year: number, req?: any): Observable<HttpResponse<any>> {
    const options = createRequestOption(req);
    return this.http.get<any>('api/immunizations/CountMonthYear?month=' + `${month}` + '&year=' + `${year}`, {
      params: options,
      observe: 'response',
    });
  }

  getImmunizationIdentifier(immunization: Pick<IImmunization, 'id'>): number {
    return immunization.id;
  }

  compareImmunization(o1: Pick<IImmunization, 'id'> | null, o2: Pick<IImmunization, 'id'> | null): boolean {
    return o1 && o2 ? this.getImmunizationIdentifier(o1) === this.getImmunizationIdentifier(o2) : o1 === o2;
  }

  addImmunizationToCollectionIfMissing<Type extends Pick<IImmunization, 'id'>>(
    immunizationCollection: Type[],
    ...immunizationsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const immunizations: Type[] = immunizationsToCheck.filter(isPresent);
    if (immunizations.length > 0) {
      const immunizationCollectionIdentifiers = immunizationCollection.map(
        immunizationItem => this.getImmunizationIdentifier(immunizationItem)!
      );
      const immunizationsToAdd = immunizations.filter(immunizationItem => {
        const immunizationIdentifier = this.getImmunizationIdentifier(immunizationItem);
        if (immunizationCollectionIdentifiers.includes(immunizationIdentifier)) {
          return false;
        }
        immunizationCollectionIdentifiers.push(immunizationIdentifier);
        return true;
      });
      return [...immunizationsToAdd, ...immunizationCollection];
    }
    return immunizationCollection;
  }

  protected convertDateFromClient<T extends IImmunization | NewImmunization | PartialUpdateImmunization>(immunization: T): RestOf<T> {
    return {
      ...immunization,
      date: immunization.date?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restImmunization: RestImmunization): IImmunization {
    return {
      ...restImmunization,
      date: restImmunization.date ? dayjs(restImmunization.date) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestImmunization>): HttpResponse<IImmunization> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestImmunization[]>): HttpResponse<IImmunization[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }

}
