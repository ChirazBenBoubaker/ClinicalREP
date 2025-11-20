import dayjs from 'dayjs/esm';

import { IMedication, NewMedication } from './medication.model';

export const sampleWithRequiredData: IMedication = {
  id: 73796,
};

export const sampleWithPartialData: IMedication = {
  id: 6291,
  date: dayjs('2023-04-10'),
};

export const sampleWithFullData: IMedication = {
  id: 9863,
  medicationText: 'infomediaries withdrawal motivating',
  date: dayjs('2023-04-10'),
  patientUID: 'Customer scalable Accountability',
};

export const sampleWithNewData: NewMedication = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
