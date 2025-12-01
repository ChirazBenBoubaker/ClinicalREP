import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IDate, NewDate } from '../date.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

type DateFormGroupInput = IDate | PartialWithRequiredKeyOf<NewDate>;

type DateFormGroupContent = {
  {
  id: FormControl<IDate['id'] | NewDate['id']>;
  date: FormControl<IDate['date']>;        // Dayjs | null
  year: FormControl<IDate['year']>;
  month: FormControl<IDate['month']>;
  day: FormControl<IDate['day']>;
};

export type DateFormGroup = FormGroup<DateFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DateFormService {

  createDateFormGroup(dateData?: DateFormGroupInput): DateFormGroup {
    // On renomme le paramètre en "dateData" pour éviter le conflit avec la propriété "date"
    const {
      id = null,
      date = null,
      year = null,
      month = null,
      day = null,
    } = dateData ?? {};

    return new FormGroup<DateFormGroupContent>({
      id: new FormControl(
        { value: id, disabled: true },
        { nonNullable: true, validators: [Validators.required] }
      ),
      date: new FormControl(date as IDate['date']),   // cast safe : date est bien un Dayjs | null
      year: new FormControl(year),
      month: new FormControl(month),
      day: new FormControl(day),
    });
  }

  getDate(form: DateFormGroup): IDate | NewDate {
    return form.getRawValue() as IDate | NewDate;
  }

  resetForm(form: DateFormGroup, dateData?: DateFormGroupInput): void {
    const {
      id = null,
      date = null,
      year = null,
      month = null,
      day = null,
    } = dateData ?? {};

    form.reset({
      id: { value: id, disabled: true },
      date,
      year,
      month,
      day,
    } as any);
  }
}
