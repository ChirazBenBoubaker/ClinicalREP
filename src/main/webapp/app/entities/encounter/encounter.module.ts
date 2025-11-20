import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { EncounterComponent } from './list/encounter.component';
import { EncounterDetailComponent } from './detail/encounter-detail.component';
import { EncounterUpdateComponent } from './update/encounter-update.component';
import { EncounterDeleteDialogComponent } from './delete/encounter-delete-dialog.component';
import { EncounterRoutingModule } from './route/encounter-routing.module';

@NgModule({
  imports: [SharedModule, EncounterRoutingModule],
  declarations: [EncounterComponent, EncounterDetailComponent, EncounterUpdateComponent, EncounterDeleteDialogComponent],
})
export class EncounterModule {}
