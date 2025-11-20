import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ImmunizationFormService, ImmunizationFormGroup } from './immunization-form.service';
import { IImmunization } from '../immunization.model';
import { ImmunizationService } from '../service/immunization.service';

@Component({
  selector: 'itp-immunization-update',
  templateUrl: './immunization-update.component.html',
})
export class ImmunizationUpdateComponent implements OnInit {
  isSaving = false;
  immunization: IImmunization | null = null;

  editForm: ImmunizationFormGroup = this.immunizationFormService.createImmunizationFormGroup();

  constructor(
    protected immunizationService: ImmunizationService,
    protected immunizationFormService: ImmunizationFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ immunization }) => {
      this.immunization = immunization;
      if (immunization) {
        this.updateForm(immunization);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const immunization = this.immunizationFormService.getImmunization(this.editForm);
    if (immunization.id !== null) {
      this.subscribeToSaveResponse(this.immunizationService.update(immunization));
    } else {
      this.subscribeToSaveResponse(this.immunizationService.create(immunization));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IImmunization>>): void {
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

  protected updateForm(immunization: IImmunization): void {
    this.immunization = immunization;
    this.immunizationFormService.resetForm(this.editForm, immunization);
  }
}
