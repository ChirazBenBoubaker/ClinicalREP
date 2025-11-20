import { Component, OnInit } from '@angular/core';
import {HttpHeaders, HttpResponse} from '@angular/common/http';
import { ActivatedRoute, Data, ParamMap, Router } from '@angular/router';
import { combineLatest, filter, Observable, switchMap, tap } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IFactclinical } from '../factclinical.model';

import { ITEMS_PER_PAGE, PAGE_HEADER, TOTAL_COUNT_RESPONSE_HEADER } from 'app/config/pagination.constants';
import { ASC, DESC, SORT, ITEM_DELETED_EVENT, DEFAULT_SORT_DATA } from 'app/config/navigation.constants';
import { EntityArrayResponseType, FactclinicalService } from '../service/factclinical.service';
import { FactclinicalDeleteDialogComponent } from '../delete/factclinical-delete-dialog.component';
import {IPatient} from "../../patient/patient.model";

@Component({
  selector: 'itp-factclinical',
  templateUrl: './factclinical.component.html',
})
export class FactclinicalComponent implements OnInit {
  factclinicals?: IFactclinical[];
  isLoading = false;

  predicate = 'id';
  ascending = true;

  itemsPerPage = ITEMS_PER_PAGE;
  totalItems = 0;
  page = 1;
  factCriteria: any = {};
  ngbPaginationPage = 1;

  constructor(
    protected factclinicalService: FactclinicalService,
    protected activatedRoute: ActivatedRoute,
    public router: Router,
    protected modalService: NgbModal
  ) {}

  trackId = (_index: number, item: IFactclinical): number => this.factclinicalService.getFactclinicalIdentifier(item);

  ngOnInit(): void {
    this.load();
  }

  onSearch(data: any): void {
    this.loadPage(1, true);
  }

  loadPage(page?: number, dontNavigate?: boolean): void {
    this.isLoading = true;

    const pageToLoad: number = page ?? this.page ?? 1;
    const criteria: any = {
      page: pageToLoad - 1,
      size: this.itemsPerPage,
      sort: this.sort(),
    };
    if (this.factCriteria) {
      if (this.factCriteria.patientUID) {
        criteria['patientUID.contains'] = this.factCriteria.patientUID;
      }
      if (this.factCriteria.dateFact) {
        criteria['date.greaterThan'] = this.factCriteria.dateFact;
      }


      if (this.factCriteria.dateFact) {
        criteria['date.lessThan'] = this.factCriteria.dateFact2;
      }
    }
    this.factclinicalService.query(criteria).subscribe({
      next: (res: HttpResponse<IPatient[]>) => {
        this.isLoading = false;
        this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate);
      },
      error: () => {
        this.isLoading = false;
        this.onError();
      },
    });
  }

  protected onSuccess(data: IFactclinical[] | null, headers: HttpHeaders, page: number, navigate: boolean): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.factclinicals = data ?? [];
    this.ngbPaginationPage = this.page;
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page ?? 1;
  }


  delete(factclinical: IFactclinical): void {
    const modalRef = this.modalService.open(FactclinicalDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.factclinical = factclinical;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed
      .pipe(
        filter(reason => reason === ITEM_DELETED_EVENT),
        switchMap(() => this.loadFromBackendWithRouteInformations())
      )
      .subscribe({
        next: (res: EntityArrayResponseType) => {
          this.onResponseSuccess(res);
        },
      });
  }

  protected sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? ASC : DESC)];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

    load(): void {
    this.loadFromBackendWithRouteInformations().subscribe({
      next: (res: EntityArrayResponseType) => {
        this.onResponseSuccess(res);
      },
    });
  }

  navigateToWithComponentValues(): void {
    this.handleNavigation(this.page, this.predicate, this.ascending);
  }

  navigateToPage(page = this.page): void {
    this.handleNavigation(page, this.predicate, this.ascending);
  }

  protected loadFromBackendWithRouteInformations(): Observable<EntityArrayResponseType> {
    return combineLatest([this.activatedRoute.queryParamMap, this.activatedRoute.data]).pipe(
      tap(([params, data]) => this.fillComponentAttributeFromRoute(params, data)),
      switchMap(() => this.queryBackend(this.page, this.predicate, this.ascending))
    );
  }

  protected fillComponentAttributeFromRoute(params: ParamMap, data: Data): void {
    const page = params.get(PAGE_HEADER);
    this.page = +(page ?? 1);
    const sort = (params.get(SORT) ?? data[DEFAULT_SORT_DATA]).split(',');
    this.predicate = sort[0];
    this.ascending = sort[1] === ASC;
  }

  protected onResponseSuccess(response: EntityArrayResponseType): void {
    this.fillComponentAttributesFromResponseHeader(response.headers);
    const dataFromBody = this.fillComponentAttributesFromResponseBody(response.body);
    this.factclinicals = dataFromBody;
  }

  protected fillComponentAttributesFromResponseBody(data: IFactclinical[] | null): IFactclinical[] {
    return data ?? [];
  }

  protected fillComponentAttributesFromResponseHeader(headers: HttpHeaders): void {
    this.totalItems = Number(headers.get(TOTAL_COUNT_RESPONSE_HEADER));
  }

  protected queryBackend(page?: number, predicate?: string, ascending?: boolean): Observable<EntityArrayResponseType> {
    this.isLoading = true;
    const pageToLoad: number = page ?? 1;
    const queryObject = {
      page: pageToLoad - 1,
      size: this.itemsPerPage,
      sort: this.getSortQueryParam(predicate, ascending),
    };
    return this.factclinicalService.query(queryObject).pipe(tap(() => (this.isLoading = false)));
  }

  protected handleNavigation(page = this.page, predicate?: string, ascending?: boolean): void {
    const queryParamsObj = {
      page,
      size: this.itemsPerPage,
      sort: this.getSortQueryParam(predicate, ascending),
    };

    this.router.navigate(['./'], {
      relativeTo: this.activatedRoute,
      queryParams: queryParamsObj,
    });
  }

  protected getSortQueryParam(predicate = this.predicate, ascending = this.ascending): string[] {
    const ascendingQueryParam = ascending ? ASC : DESC;
    if (predicate === '') {
      return [];
    } else {
      return [predicate + ',' + ascendingQueryParam];
    }
  }

  /*dateTyped(event: any): void {
    if (event.target.value.length === 0) {
      this.factCriteria.dateFact = undefined;
    }
    this.dateToTransform = event.target.value;
    if (this.dateToTransform.length === 10 && this.dateToTransform[2] === '/' && this.dateToTransform[5] === '/') {
      if (/^([0-2][0-9]|(3)[0-1])(\/)(((0)[0-9])|((1)[0-2]))(\/)\d{4}$/.test(event.target.value)) {
        const d = new Date(
          Number(this.dateToTransform.substring(6, 10)),
          Number(this.dateToTransform.substring(3, 5)) - 1,
          Number(this.dateToTransform.substring(0, 2))
        );
        this.factCriteria.DateStart = dayjs(d).format('YYYY-MM-DD');
      }
    }
  }*/
}
