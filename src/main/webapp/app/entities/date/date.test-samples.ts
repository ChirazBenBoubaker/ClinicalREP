import dayjs from 'dayjs/esm';

import { IDate, NewDate } from './date.model';

export const sampleWithRequiredData: IDate = {
  id: 26380,
};

export const sampleWithPartialData: IDate = {
  id: 31552,
  date: dayjs('2023-04-10'),
  year: 12926,
  day: 26885,
};

export const sampleWithFullData: IDate = {
  id: 61853,
  date: dayjs('2023-04-10'),
  year: 12352,
  month: 9532,
  day: 33038,
};

export const sampleWithNewData: NewDate = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
