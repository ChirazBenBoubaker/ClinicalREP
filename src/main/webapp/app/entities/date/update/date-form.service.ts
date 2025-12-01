import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IDate, NewDate } from '../date.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDate for edit and NewDateFormGroupInput for create.
 */
type DateFormGroupInput = IDate | PartialWithRequiredKeyOf<NewDate>;

type DateFormDefaults = Pick<NewDate, 'id'>;

type DateFormGroupContent = {
  id: FormControl<IDate['id'] | NewDate['id']>;
  date: FormControl<IDate['date']>;
  year: FormControl<IDate['year']>;
  month: FormControl<IDate['month']>;
  day: FormControl<IDate['day']>;
};

export type DateFormGroup = FormGroup<DateFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DateFormService {
  createDateFormGroup(date: DateFormGroupInput = { id: null }): DateFormGroup {
    const dateRawValue = {
      ...this.getFormDefaults(),
      ...date,
    };
    return new FormGroup<DateFormGroupContent>({
      id: new FormControl(
        { value: dateRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      date: new FormControl(dateRawValue.date),
      year: new FormControl(dateRawValue.year),
      month: new FormControl(dateRawValue.month),
      day: new FormControl(dateRawValue.day),
    });
  }

  getDate(form: DateFormGroup): IDate | NewDate {
    return form.getRawValue() as IDate | NewDate;
  }

  resetForm(form: DateFormGroup, date: DateFormGroupInput): void {
    const dateRawValue = { ...this.getFormDefaults(), ...date };
    form.reset(
      {
        ...dateRawValue,
        id: { value: dateRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): DateFormDefaults {
    return {
      id: null,
    };
  }
}
