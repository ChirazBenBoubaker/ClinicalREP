import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IImmunization } from '../immunization.model';
import { ImmunizationService } from '../service/immunization.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './immunization-delete-dialog.component.html',
})
export class ImmunizationDeleteDialogComponent {
  immunization?: IImmunization;

  constructor(protected immunizationService: ImmunizationService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.immunizationService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
