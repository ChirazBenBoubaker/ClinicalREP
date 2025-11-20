import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IObservation, NewObservation } from '../observation.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IObservation for edit and NewObservationFormGroupInput for create.
 */
type ObservationFormGroupInput = IObservation | PartialWithRequiredKeyOf<NewObservation>;

type ObservationFormDefaults = Pick<NewObservation, 'id'>;

type ObservationFormGroupContent = {
  id: FormControl<IObservation['id'] | NewObservation['id']>;
  bodyHeight: FormControl<IObservation['bodyHeight']>;
  bodyWeight: FormControl<IObservation['bodyWeight']>;
  bodyMass: FormControl<IObservation['bodyMass']>;
  painseverity: FormControl<IObservation['painseverity']>;
  bloodPressure: FormControl<IObservation['bloodPressure']>;
  tobaccosmokingstatusNHIS: FormControl<IObservation['tobaccosmokingstatusNHIS']>;
  creatinine: FormControl<IObservation['creatinine']>;
  calcium: FormControl<IObservation['calcium']>;
  sodium: FormControl<IObservation['sodium']>;
  potassium: FormControl<IObservation['potassium']>;
  chloride: FormControl<IObservation['chloride']>;
  carbonDioxide: FormControl<IObservation['carbonDioxide']>;
  glucose: FormControl<IObservation['glucose']>;
  ureaNitrogen: FormControl<IObservation['ureaNitrogen']>;
  date: FormControl<IObservation['date']>;
  patientUID: FormControl<IObservation['patientUID']>;
};

export type ObservationFormGroup = FormGroup<ObservationFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ObservationFormService {
  createObservationFormGroup(observation: ObservationFormGroupInput = { id: null }): ObservationFormGroup {
    const observationRawValue = {
      ...this.getFormDefaults(),
      ...observation,
    };
    return new FormGroup<ObservationFormGroupContent>({
      id: new FormControl(
        { value: observationRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      bodyHeight: new FormControl(observationRawValue.bodyHeight),
      bodyWeight: new FormControl(observationRawValue.bodyWeight),
      bodyMass: new FormControl(observationRawValue.bodyMass),
      painseverity: new FormControl(observationRawValue.painseverity),
      bloodPressure: new FormControl(observationRawValue.bloodPressure),
      tobaccosmokingstatusNHIS: new FormControl(observationRawValue.tobaccosmokingstatusNHIS),
      creatinine: new FormControl(observationRawValue.creatinine),
      calcium: new FormControl(observationRawValue.calcium),
      sodium: new FormControl(observationRawValue.sodium),
      potassium: new FormControl(observationRawValue.potassium),
      chloride: new FormControl(observationRawValue.chloride),
      carbonDioxide: new FormControl(observationRawValue.carbonDioxide),
      glucose: new FormControl(observationRawValue.glucose),
      ureaNitrogen: new FormControl(observationRawValue.ureaNitrogen),
      date: new FormControl(observationRawValue.date),
      patientUID: new FormControl(observationRawValue.patientUID),
    });
  }

  getObservation(form: ObservationFormGroup): IObservation | NewObservation {
    return form.getRawValue() as IObservation | NewObservation;
  }

  resetForm(form: ObservationFormGroup, observation: ObservationFormGroupInput): void {
    const observationRawValue = { ...this.getFormDefaults(), ...observation };
    form.reset(
      {
        ...observationRawValue,
        id: { value: observationRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ObservationFormDefaults {
    return {
      id: null,
    };
  }
}
