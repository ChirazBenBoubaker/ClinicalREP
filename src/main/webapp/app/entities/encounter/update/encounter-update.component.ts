import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { EncounterFormService, EncounterFormGroup } from './encounter-form.service';
import { IEncounter } from '../encounter.model';
import { EncounterService } from '../service/encounter.service';

@Component({
  selector: 'itp-encounter-update',
  templateUrl: './encounter-update.component.html',
})
export class EncounterUpdateComponent implements OnInit {
  isSaving = false;
  encounter: IEncounter | null = null;

  editForm: EncounterFormGroup = this.encounterFormService.createEncounterFormGroup();

  constructor(
    protected encounterService: EncounterService,
    protected encounterFormService: EncounterFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ encounter }) => {
      this.encounter = encounter;
      if (encounter) {
        this.updateForm(encounter);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const encounter = this.encounterFormService.getEncounter(this.editForm);
    if (encounter.id !== null) {
      this.subscribeToSaveResponse(this.encounterService.update(encounter));
    } else {
      this.subscribeToSaveResponse(this.encounterService.create(encounter));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEncounter>>): void {
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

  protected updateForm(encounter: IEncounter): void {
    this.encounter = encounter;
    this.encounterFormService.resetForm(this.editForm, encounter);
  }
}
