import dayjs from 'dayjs/esm';

import { IImmunization, NewImmunization } from './immunization.model';

export const sampleWithRequiredData: IImmunization = {
  id: 35192,
};

export const sampleWithPartialData: IImmunization = {
  id: 76004,
};

export const sampleWithFullData: IImmunization = {
  id: 23811,
  immunization: 'partnerships architectures',
  date: dayjs('2023-04-10'),
  patientUID: 'calculating',
};

export const sampleWithNewData: NewImmunization = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
