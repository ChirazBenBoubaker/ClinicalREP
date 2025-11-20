import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { FactclinicalFormService, FactclinicalFormGroup } from './factclinical-form.service';
import { IFactclinical } from '../factclinical.model';
import { FactclinicalService } from '../service/factclinical.service';

@Component({
  selector: 'itp-factclinical-update',
  templateUrl: './factclinical-update.component.html',
})
export class FactclinicalUpdateComponent implements OnInit {
  isSaving = false;
  factclinical: IFactclinical | null = null;

  editForm: FactclinicalFormGroup = this.factclinicalFormService.createFactclinicalFormGroup();

  constructor(
    protected factclinicalService: FactclinicalService,
    protected factclinicalFormService: FactclinicalFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ factclinical }) => {
      this.factclinical = factclinical;
      if (factclinical) {
        this.updateForm(factclinical);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const factclinical = this.factclinicalFormService.getFactclinical(this.editForm);
    if (factclinical.id !== null) {
      this.subscribeToSaveResponse(this.factclinicalService.update(factclinical));
    } else {
      this.subscribeToSaveResponse(this.factclinicalService.create(factclinical));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFactclinical>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(factclinical: IFactclinical): void {
    this.factclinical = factclinical;
    this.factclinicalFormService.resetForm(this.editForm, factclinical);
  }
}
