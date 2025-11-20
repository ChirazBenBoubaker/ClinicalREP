import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IFactclinical, NewFactclinical } from '../factclinical.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IFactclinical for edit and NewFactclinicalFormGroupInput for create.
 */
type FactclinicalFormGroupInput = IFactclinical | PartialWithRequiredKeyOf<NewFactclinical>;

type FactclinicalFormDefaults = Pick<NewFactclinical, 'id'>;

type FactclinicalFormGroupContent = {
  id: FormControl<IFactclinical['id'] | NewFactclinical['id']>;
  patientUID: FormControl<IFactclinical['patientUID']>;
  encounterID: FormControl<IFactclinical['encounterID']>;
  observationID: FormControl<IFactclinical['observationID']>;
  procedureID: FormControl<IFactclinical['procedureID']>;
  immunizationID: FormControl<IFactclinical['immunizationID']>;
  medicationID: FormControl<IFactclinical['medicationID']>;
  conditionID: FormControl<IFactclinical['conditionID']>;
  date: FormControl<IFactclinical['date']>;
};

export type FactclinicalFormGroup = FormGroup<FactclinicalFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class FactclinicalFormService {
  createFactclinicalFormGroup(factclinical: FactclinicalFormGroupInput = { id: null }): FactclinicalFormGroup {
    const factclinicalRawValue = {
      ...this.getFormDefaults(),
      ...factclinical,
    };
    return new FormGroup<FactclinicalFormGroupContent>({
      id: new FormControl(
        { value: factclinicalRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      patientUID: new FormControl(factclinicalRawValue.patientUID),
      encounterID: new FormControl(factclinicalRawValue.encounterID),
      observationID: new FormControl(factclinicalRawValue.observationID),
      procedureID: new FormControl(factclinicalRawValue.procedureID),
      immunizationID: new FormControl(factclinicalRawValue.immunizationID),
      medicationID: new FormControl(factclinicalRawValue.medicationID),
      conditionID: new FormControl(factclinicalRawValue.conditionID),
      date: new FormControl(factclinicalRawValue.date),
    });
  }

  getFactclinical(form: FactclinicalFormGroup): IFactclinical | NewFactclinical {
    return form.getRawValue() as IFactclinical | NewFactclinical;
  }

  resetForm(form: FactclinicalFormGroup, factclinical: FactclinicalFormGroupInput): void {
    const factclinicalRawValue = { ...this.getFormDefaults(), ...factclinical };
    form.reset(
      {
        ...factclinicalRawValue,
        id: { value: factclinicalRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): FactclinicalFormDefaults {
    return {
      id: null,
    };
  }
}
