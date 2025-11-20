import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IProcedure } from '../procedure.model';
import { ProcedureService } from '../service/procedure.service';

@Injectable({ providedIn: 'root' })
export class ProcedureRoutingResolveService implements Resolve<IProcedure | null> {
  constructor(protected service: ProcedureService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProcedure | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((procedure: HttpResponse<IProcedure>) => {
          if (procedure.body) {
            return of(procedure.body);
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
