import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEncounter } from '../encounter.model';
import { EncounterService } from '../service/encounter.service';

@Injectable({ providedIn: 'root' })
export class EncounterRoutingResolveService implements Resolve<IEncounter | null> {
  constructor(protected service: EncounterService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEncounter | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((encounter: HttpResponse<IEncounter>) => {
          if (encounter.body) {
            return of(encounter.body);
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
