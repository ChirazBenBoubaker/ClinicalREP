import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IImmunization, NewImmunization } from '../immunization.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IImmunization for edit and NewImmunizationFormGroupInput for create.
 */
type ImmunizationFormGroupInput = IImmunization | PartialWithRequiredKeyOf<NewImmunization>;

type ImmunizationFormDefaults = Pick<NewImmunization, 'id'>;

type ImmunizationFormGroupContent = {
  id: FormControl<IImmunization['id'] | NewImmunization['id']>;
  immunization: FormControl<IImmunization['immunization']>;
  date: FormControl<IImmunization['date']>;
  patientUID: FormControl<IImmunization['patientUID']>;
};

export type ImmunizationFormGroup = FormGroup<ImmunizationFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ImmunizationFormService {
  createImmunizationFormGroup(immunization: ImmunizationFormGroupInput = { id: null }): ImmunizationFormGroup {
    const immunizationRawValue = {
      ...this.getFormDefaults(),
      ...immunization,
    };
    return new FormGroup<ImmunizationFormGroupContent>({
      id: new FormControl(
        { value: immunizationRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      immunization: new FormControl(immunizationRawValue.immunization),
      date: new FormControl(immunizationRawValue.date),
      patientUID: new FormControl(immunizationRawValue.patientUID),
    });
  }

  getImmunization(form: ImmunizationFormGroup): IImmunization | NewImmunization {
    return form.getRawValue() as IImmunization | NewImmunization;
  }

  resetForm(form: ImmunizationFormGroup, immunization: ImmunizationFormGroupInput): void {
    const immunizationRawValue = { ...this.getFormDefaults(), ...immunization };
    form.reset(
      {
        ...immunizationRawValue,
        id: { value: immunizationRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ImmunizationFormDefaults {
    return {
      id: null,
    };
  }
}
