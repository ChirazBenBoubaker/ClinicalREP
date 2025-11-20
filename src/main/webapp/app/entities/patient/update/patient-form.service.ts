import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPatient, NewPatient } from '../patient.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPatient for edit and NewPatientFormGroupInput for create.
 */
type PatientFormGroupInput = IPatient | PartialWithRequiredKeyOf<NewPatient>;

type PatientFormDefaults = Pick<NewPatient, 'id'>;

type PatientFormGroupContent = {
  id: FormControl<IPatient['id'] | NewPatient['id']>;
  patientUID: FormControl<IPatient['patientUID']>;
  nameFamily: FormControl<IPatient['nameFamily']>;
  nameGiven: FormControl<IPatient['nameGiven']>;
  birthdate: FormControl<IPatient['birthdate']>;
  gender: FormControl<IPatient['gender']>;
  contact: FormControl<IPatient['contact']>;
  line: FormControl<IPatient['line']>;
  city: FormControl<IPatient['city']>;
  country: FormControl<IPatient['country']>;
  state: FormControl<IPatient['state']>;
  postalcode: FormControl<IPatient['postalcode']>;
};

export type PatientFormGroup = FormGroup<PatientFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PatientFormService {
  createPatientFormGroup(patient: PatientFormGroupInput = { id: null }): PatientFormGroup {
    const patientRawValue = {
      ...this.getFormDefaults(),
      ...patient,
    };
    return new FormGroup<PatientFormGroupContent>({
      id: new FormControl(
        { value: patientRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      patientUID: new FormControl(patientRawValue.patientUID, {
        validators: [Validators.required],
      }),
      nameFamily: new FormControl(patientRawValue.nameFamily),
      nameGiven: new FormControl(patientRawValue.nameGiven),
      birthdate: new FormControl(patientRawValue.birthdate),
      gender: new FormControl(patientRawValue.gender),
      contact: new FormControl(patientRawValue.contact),
      line: new FormControl(patientRawValue.line),
      city: new FormControl(patientRawValue.city),
      country: new FormControl(patientRawValue.country),
      state: new FormControl(patientRawValue.state),
      postalcode: new FormControl(patientRawValue.postalcode),
    });
  }

  getPatient(form: PatientFormGroup): IPatient | NewPatient {
    return form.getRawValue() as IPatient | NewPatient;
  }

  resetForm(form: PatientFormGroup, patient: PatientFormGroupInput): void {
    const patientRawValue = { ...this.getFormDefaults(), ...patient };
    form.reset(
      {
        ...patientRawValue,
        id: { value: patientRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): PatientFormDefaults {
    return {
      id: null,
    };
  }
}
