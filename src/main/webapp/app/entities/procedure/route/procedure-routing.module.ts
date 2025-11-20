import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ProcedureComponent } from '../list/procedure.component';
import { ProcedureDetailComponent } from '../detail/procedure-detail.component';
import { ProcedureUpdateComponent } from '../update/procedure-update.component';
import { ProcedureRoutingResolveService } from './procedure-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const procedureRoute: Routes = [
  {
    path: '',
    component: ProcedureComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProcedureDetailComponent,
    resolve: {
      procedure: ProcedureRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProcedureUpdateComponent,
    resolve: {
      procedure: ProcedureRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProcedureUpdateComponent,
    resolve: {
      procedure: ProcedureRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(procedureRoute)],
  exports: [RouterModule],
})
export class ProcedureRoutingModule {}
