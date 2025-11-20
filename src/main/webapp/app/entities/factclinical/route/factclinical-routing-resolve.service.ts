import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFactclinical } from '../factclinical.model';
import { FactclinicalService } from '../service/factclinical.service';

@Injectable({ providedIn: 'root' })
export class FactclinicalRoutingResolveService implements Resolve<IFactclinical | null> {
  constructor(protected service: FactclinicalService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFactclinical | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((factclinical: HttpResponse<IFactclinical>) => {
          if (factclinical.body) {
            return of(factclinical.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
