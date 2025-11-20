import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IFactclinical } from '../factclinical.model';
import { FactclinicalService } from '../service/factclinical.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './factclinical-delete-dialog.component.html',
})
export class FactclinicalDeleteDialogComponent {
  factclinical?: IFactclinical;

  constructor(protected factclinicalService: FactclinicalService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.factclinicalService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
