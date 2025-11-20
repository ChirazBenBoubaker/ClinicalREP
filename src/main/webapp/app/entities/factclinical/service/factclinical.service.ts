import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFactclinical, NewFactclinical } from '../factclinical.model';

export type PartialUpdateFactclinical = Partial<IFactclinical> & Pick<IFactclinical, 'id'>;

type RestOf<T extends IFactclinical | NewFactclinical> = Omit<T, 'date'> & {
  date?: string | null;
};

export type RestFactclinical = RestOf<IFactclinical>;

export type NewRestFactclinical = RestOf<NewFactclinical>;

export type PartialUpdateRestFactclinical = RestOf<PartialUpdateFactclinical>;

export type EntityResponseType = HttpResponse<IFactclinical>;
export type EntityArrayResponseType = HttpResponse<IFactclinical[]>;

@Injectable({ providedIn: 'root' })
export class FactclinicalService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/factclinicals');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(factclinical: NewFactclinical): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(factclinical);
    return this.http
      .post<RestFactclinical>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(factclinical: IFactclinical): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(factclinical);
    return this.http
      .put<RestFactclinical>(`${this.resourceUrl}/${this.getFactclinicalIdentifier(factclinical)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(factclinical: PartialUpdateFactclinical): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(factclinical);
    return this.http
      .patch<RestFactclinical>(`${this.resourceUrl}/${this.getFactclinicalIdentifier(factclinical)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestFactclinical>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestFactclinical[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getFactclinicalIdentifier(factclinical: Pick<IFactclinical, 'id'>): number {
    return factclinical.id;
  }

  compareFactclinical(o1: Pick<IFactclinical, 'id'> | null, o2: Pick<IFactclinical, 'id'> | null): boolean {
    return o1 && o2 ? this.getFactclinicalIdentifier(o1) === this.getFactclinicalIdentifier(o2) : o1 === o2;
  }

  addFactclinicalToCollectionIfMissing<Type extends Pick<IFactclinical, 'id'>>(
    factclinicalCollection: Type[],
    ...factclinicalsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const factclinicals: Type[] = factclinicalsToCheck.filter(isPresent);
    if (factclinicals.length > 0) {
      const factclinicalCollectionIdentifiers = factclinicalCollection.map(
        factclinicalItem => this.getFactclinicalIdentifier(factclinicalItem)!
      );
      const factclinicalsToAdd = factclinicals.filter(factclinicalItem => {
        const factclinicalIdentifier = this.getFactclinicalIdentifier(factclinicalItem);
        if (factclinicalCollectionIdentifiers.includes(factclinicalIdentifier)) {
          return false;
        }
        factclinicalCollectionIdentifiers.push(factclinicalIdentifier);
        return true;
      });
      return [...factclinicalsToAdd, ...factclinicalCollection];
    }
    return factclinicalCollection;
  }

  protected convertDateFromClient<T extends IFactclinical | NewFactclinical | PartialUpdateFactclinical>(factclinical: T): RestOf<T> {
    return {
      ...factclinical,
      date: factclinical.date?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restFactclinical: RestFactclinical): IFactclinical {
    return {
      ...restFactclinical,
      date: restFactclinical.date ? dayjs(restFactclinical.date) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestFactclinical>): HttpResponse<IFactclinical> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestFactclinical[]>): HttpResponse<IFactclinical[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
