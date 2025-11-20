import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ImmunizationComponent } from './list/immunization.component';
import { ImmunizationDetailComponent } from './detail/immunization-detail.component';
import { ImmunizationUpdateComponent } from './update/immunization-update.component';
import { ImmunizationDeleteDialogComponent } from './delete/immunization-delete-dialog.component';
import { ImmunizationRoutingModule } from './route/immunization-routing.module';

@NgModule({
  imports: [SharedModule, ImmunizationRoutingModule],
  declarations: [ImmunizationComponent, ImmunizationDetailComponent, ImmunizationUpdateComponent, ImmunizationDeleteDialogComponent],
})
export class ImmunizationModule {}
