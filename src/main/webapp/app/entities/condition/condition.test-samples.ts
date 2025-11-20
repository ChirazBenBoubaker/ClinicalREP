import dayjs from 'dayjs/esm';

import { ICondition, NewCondition } from './condition.model';

export const sampleWithRequiredData: ICondition = {
  id: 6098,
};

export const sampleWithPartialData: ICondition = {
  id: 35744,
};

export const sampleWithFullData: ICondition = {
  id: 71076,
  conditionText: 'cutting-edge Fiji',
  conditionOnsetDates: dayjs('2023-04-10'),
  patientUID: 'Berkshire e-business back',
};

export const sampleWithNewData: NewCondition = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
