import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ProcedureComponent } from './list/procedure.component';
import { ProcedureDetailComponent } from './detail/procedure-detail.component';
import { ProcedureUpdateComponent } from './update/procedure-update.component';
import { ProcedureDeleteDialogComponent } from './delete/procedure-delete-dialog.component';
import { ProcedureRoutingModule } from './route/procedure-routing.module';

@NgModule({
  imports: [SharedModule, ProcedureRoutingModule],
  declarations: [ProcedureComponent, ProcedureDetailComponent, ProcedureUpdateComponent, ProcedureDeleteDialogComponent],
})
export class ProcedureModule {}
