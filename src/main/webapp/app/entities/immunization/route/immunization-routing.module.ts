import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ImmunizationComponent } from '../list/immunization.component';
import { ImmunizationDetailComponent } from '../detail/immunization-detail.component';
import { ImmunizationUpdateComponent } from '../update/immunization-update.component';
import { ImmunizationRoutingResolveService } from './immunization-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const immunizationRoute: Routes = [
  {
    path: '',
    component: ImmunizationComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ImmunizationDetailComponent,
    resolve: {
      immunization: ImmunizationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ImmunizationUpdateComponent,
    resolve: {
      immunization: ImmunizationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ImmunizationUpdateComponent,
    resolve: {
      immunization: ImmunizationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(immunizationRoute)],
  exports: [RouterModule],
})
export class ImmunizationRoutingModule {}
