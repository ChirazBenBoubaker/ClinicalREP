import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { FactclinicalComponent } from './list/factclinical.component';
import { FactclinicalDetailComponent } from './detail/factclinical-detail.component';
import { FactclinicalUpdateComponent } from './update/factclinical-update.component';
import { FactclinicalDeleteDialogComponent } from './delete/factclinical-delete-dialog.component';
import { FactclinicalRoutingModule } from './route/factclinical-routing.module';

@NgModule({
  imports: [SharedModule, FactclinicalRoutingModule],
  declarations: [FactclinicalComponent, FactclinicalDetailComponent, FactclinicalUpdateComponent, FactclinicalDeleteDialogComponent],
})
export class FactclinicalModule {}
