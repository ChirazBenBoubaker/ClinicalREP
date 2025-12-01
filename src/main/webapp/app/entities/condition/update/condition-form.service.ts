import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICondition, NewCondition } from '../condition.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICondition for edit and NewConditionFormGroupInput for create.
 */
type ConditionFormGroupInput = ICondition | PartialWithRequiredKeyOf<NewCondition>;

type ConditionFormDefaults = Pick<NewCondition, 'id'>;

type ConditionFormGroupContent = {
  id: FormControl<ICondition['id'] | NewCondition['id']>;
  conditionText: FormControl<ICondition['conditionText']>;
  conditionOnsetDates: FormControl<ICondition['conditionOnsetDates']>;
  patientUID: FormControl<ICondition['patientUID']>;
};

export type ConditionFormGroup = FormGroup<ConditionFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ConditionFormService {
  createConditionFormGroup(condition?: ConditionFormGroupInput): ConditionFormGroup {
    // Destructuration avec valeurs par défaut individuelles → règle Sonar respectée
    const {
      id = null,
      conditionText = null,
      conditionOnsetDates = null,
      patientUID = null,
    } = condition ?? {};

    return new FormGroup<ConditionFormGroupContent>({
      id: new FormControl(
        { value: id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      conditionText: new FormControl(conditionText),
      conditionOnsetDates: new FormControl(conditionOnsetDates),
      patientUID: new FormControl(patientUID),
    });
  }

  getCondition(form: ConditionFormGroup): ICondition | NewCondition {
    return form.getRawValue() as ICondition | NewCondition;
  }

  resetForm(form: ConditionFormGroup, condition?: ConditionFormGroupInput): void {
    const {
      id = null,
      conditionText = null,
      conditionOnsetDates = null,
      patientUID = null,
    } = condition ?? {};

    form.reset({
      id: { value: id, disabled: true },
      conditionText,
      conditionOnsetDates,
      patientUID,
    } as any); // cast conservé pour workaround Angular connu
  }

  // Plus besoin de getFormDefaults() → tout est géré par la destructuration
}
