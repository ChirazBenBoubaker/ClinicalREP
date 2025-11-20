import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IImmunization } from '../immunization.model';
import { ImmunizationService } from '../service/immunization.service';

@Injectable({ providedIn: 'root' })
export class ImmunizationRoutingResolveService implements Resolve<IImmunization | null> {
  constructor(protected service: ImmunizationService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IImmunization | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((immunization: HttpResponse<IImmunization>) => {
          if (immunization.body) {
            return of(immunization.body);
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
