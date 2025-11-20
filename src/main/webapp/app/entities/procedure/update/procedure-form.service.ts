import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IProcedure, NewProcedure } from '../procedure.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IProcedure for edit and NewProcedureFormGroupInput for create.
 */
type ProcedureFormGroupInput = IProcedure | PartialWithRequiredKeyOf<NewProcedure>;

type ProcedureFormDefaults = Pick<NewProcedure, 'id'>;

type ProcedureFormGroupContent = {
  id: FormControl<IProcedure['id'] | NewProcedure['id']>;
  procedureText: FormControl<IProcedure['procedureText']>;
  date: FormControl<IProcedure['date']>;
  patientUID: FormControl<IProcedure['patientUID']>;
};

export type ProcedureFormGroup = FormGroup<ProcedureFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ProcedureFormService {
  createProcedureFormGroup(procedure: ProcedureFormGroupInput = { id: null }): ProcedureFormGroup {
    const procedureRawValue = {
      ...this.getFormDefaults(),
      ...procedure,
    };
    return new FormGroup<ProcedureFormGroupContent>({
      id: new FormControl(
        { value: procedureRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      procedureText: new FormControl(procedureRawValue.procedureText),
      date: new FormControl(procedureRawValue.date),
      patientUID: new FormControl(procedureRawValue.patientUID),
    });
  }

  getProcedure(form: ProcedureFormGroup): IProcedure | NewProcedure {
    return form.getRawValue() as IProcedure | NewProcedure;
  }

  resetForm(form: ProcedureFormGroup, procedure: ProcedureFormGroupInput): void {
    const procedureRawValue = { ...this.getFormDefaults(), ...procedure };
    form.reset(
      {
        ...procedureRawValue,
        id: { value: procedureRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ProcedureFormDefaults {
    return {
      id: null,
    };
  }
}
