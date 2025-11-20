import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { EncounterComponent } from '../list/encounter.component';
import { EncounterDetailComponent } from '../detail/encounter-detail.component';
import { EncounterUpdateComponent } from '../update/encounter-update.component';
import { EncounterRoutingResolveService } from './encounter-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const encounterRoute: Routes = [
  {
    path: '',
    component: EncounterComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EncounterDetailComponent,
    resolve: {
      encounter: EncounterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EncounterUpdateComponent,
    resolve: {
      encounter: EncounterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EncounterUpdateComponent,
    resolve: {
      encounter: EncounterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(encounterRoute)],
  exports: [RouterModule],
})
export class EncounterRoutingModule {}
