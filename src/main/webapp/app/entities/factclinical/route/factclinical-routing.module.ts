import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { FactclinicalComponent } from '../list/factclinical.component';
import { FactclinicalDetailComponent } from '../detail/factclinical-detail.component';
import { FactclinicalUpdateComponent } from '../update/factclinical-update.component';
import { FactclinicalRoutingResolveService } from './factclinical-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const factclinicalRoute: Routes = [
  {
    path: '',
    component: FactclinicalComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FactclinicalDetailComponent,
    resolve: {
      factclinical: FactclinicalRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FactclinicalUpdateComponent,
    resolve: {
      factclinical: FactclinicalRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FactclinicalUpdateComponent,
    resolve: {
      factclinical: FactclinicalRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(factclinicalRoute)],
  exports: [RouterModule],
})
export class FactclinicalRoutingModule {}
