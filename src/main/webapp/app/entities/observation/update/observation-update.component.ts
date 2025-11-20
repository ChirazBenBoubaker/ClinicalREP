import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ObservationFormService, ObservationFormGroup } from './observation-form.service';
import { IObservation } from '../observation.model';
import { ObservationService } from '../service/observation.service';

@Component({
  selector: 'itp-observation-update',
  templateUrl: './observation-update.component.html',
})
export class ObservationUpdateComponent implements OnInit {
  isSaving = false;
  observation: IObservation | null = null;

  editForm: ObservationFormGroup = this.observationFormService.createObservationFormGroup();

  constructor(
    protected observationService: ObservationService,
    protected observationFormService: ObservationFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ observation }) => {
      this.observation = observation;
      if (observation) {
        this.updateForm(observation);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const observation = this.observationFormService.getObservation(this.editForm);
    if (observation.id !== null) {
      this.subscribeToSaveResponse(this.observationService.update(observation));
    } else {
      this.subscribeToSaveResponse(this.observationService.create(observation));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IObservation>>): void {
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

  protected updateForm(observation: IObservation): void {
    this.observation = observation;
    this.observationFormService.resetForm(this.editForm, observation);
  }
}
