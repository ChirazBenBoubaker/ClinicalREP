import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IEncounter, NewEncounter } from '../encounter.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEncounter for edit and NewEncounterFormGroupInput for create.
 */
type EncounterFormGroupInput = IEncounter | PartialWithRequiredKeyOf<NewEncounter>;

type EncounterFormDefaults = Pick<NewEncounter, 'id'>;

type EncounterFormGroupContent = {
  id: FormControl<IEncounter['id'] | NewEncounter['id']>;
  encountersText: FormControl<IEncounter['encountersText']>;
  encounterLocation: FormControl<IEncounter['encounterLocation']>;
  encounterProvider: FormControl<IEncounter['encounterProvider']>;
  date: FormControl<IEncounter['date']>;
  patientUID: FormControl<IEncounter['patientUID']>;
};

export type EncounterFormGroup = FormGroup<EncounterFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EncounterFormService {
  createEncounterFormGroup(encounter: EncounterFormGroupInput = { id: null }): EncounterFormGroup {
    const encounterRawValue = {
      ...this.getFormDefaults(),
      ...encounter,
    };
    return new FormGroup<EncounterFormGroupContent>({
      id: new FormControl(
        { value: encounterRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      encountersText: new FormControl(encounterRawValue.encountersText),
      encounterLocation: new FormControl(encounterRawValue.encounterLocation),
      encounterProvider: new FormControl(encounterRawValue.encounterProvider),
      date: new FormControl(encounterRawValue.date),
      patientUID: new FormControl(encounterRawValue.patientUID),
    });
  }

  getEncounter(form: EncounterFormGroup): IEncounter | NewEncounter {
    return form.getRawValue() as IEncounter | NewEncounter;
  }

  resetForm(form: EncounterFormGroup, encounter: EncounterFormGroupInput): void {
    const encounterRawValue = { ...this.getFormDefaults(), ...encounter };
    form.reset(
      {
        ...encounterRawValue,
        id: { value: encounterRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): EncounterFormDefaults {
    return {
      id: null,
    };
  }
}
