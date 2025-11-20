import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEncounter, NewEncounter } from '../encounter.model';
import { RestPatient } from '../../patient/service/patient.service';

export type PartialUpdateEncounter = Partial<IEncounter> & Pick<IEncounter, 'id'>;

type RestOf<T extends IEncounter | NewEncounter> = Omit<T, 'date'> & {
  date?: string | null;
};

export type RestEncounter = RestOf<IEncounter>;

export type NewRestEncounter = RestOf<NewEncounter>;

export type PartialUpdateRestEncounter = RestOf<PartialUpdateEncounter>;

export type EntityResponseType = HttpResponse<IEncounter>;
export type EntityArrayResponseType = HttpResponse<IEncounter[]>;

@Injectable({ providedIn: 'root' })
export class EncounterService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/encounters');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(encounter: NewEncounter): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(encounter);
    return this.http
      .post<RestEncounter>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(encounter: IEncounter): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(encounter);
    return this.http
      .put<RestEncounter>(`${this.resourceUrl}/${this.getEncounterIdentifier(encounter)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(encounter: PartialUpdateEncounter): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(encounter);
    return this.http
      .patch<RestEncounter>(`${this.resourceUrl}/${this.getEncounterIdentifier(encounter)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestEncounter>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestEncounter[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
  queryCountByDate(month: number, year: number, req?: any): Observable<HttpResponse<any>> {
    const options = createRequestOption(req);
    return this.http.get<any>('api/encounters/CountMonthYear?month=' + `${month}` + '&year=' + `${year}`, {
      params: options,
      observe: 'response',
    });
  }
  count(req?: any): any {
    const options = createRequestOption(req);
    return this.http.get<RestEncounter[]>(`${this.resourceUrl}/count`, { params: options, observe: 'response' });
  }
  getEncounterIdentifier(encounter: Pick<IEncounter, 'id'>): number {
    return encounter.id;
  }

  compareEncounter(o1: Pick<IEncounter, 'id'> | null, o2: Pick<IEncounter, 'id'> | null): boolean {
    return o1 && o2 ? this.getEncounterIdentifier(o1) === this.getEncounterIdentifier(o2) : o1 === o2;
  }

  addEncounterToCollectionIfMissing<Type extends Pick<IEncounter, 'id'>>(
    encounterCollection: Type[],
    ...encountersToCheck: (Type | null | undefined)[]
  ): Type[] {
    const encounters: Type[] = encountersToCheck.filter(isPresent);
    if (encounters.length > 0) {
      const encounterCollectionIdentifiers = encounterCollection.map(encounterItem => this.getEncounterIdentifier(encounterItem)!);
      const encountersToAdd = encounters.filter(encounterItem => {
        const encounterIdentifier = this.getEncounterIdentifier(encounterItem);
        if (encounterCollectionIdentifiers.includes(encounterIdentifier)) {
          return false;
        }
        encounterCollectionIdentifiers.push(encounterIdentifier);
        return true;
      });
      return [...encountersToAdd, ...encounterCollection];
    }
    return encounterCollection;
  }

  protected convertDateFromClient<T extends IEncounter | NewEncounter | PartialUpdateEncounter>(encounter: T): RestOf<T> {
    return {
      ...encounter,
      date: encounter.date?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restEncounter: RestEncounter): IEncounter {
    return {
      ...restEncounter,
      date: restEncounter.date ? dayjs(restEncounter.date) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestEncounter>): HttpResponse<IEncounter> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestEncounter[]>): HttpResponse<IEncounter[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
