import dayjs from 'dayjs/esm';

import { IProcedure, NewProcedure } from './procedure.model';

export const sampleWithRequiredData: IProcedure = {
  id: 45403,
};

export const sampleWithPartialData: IProcedure = {
  id: 3093,
  procedureText: 'bi-directional',
};

export const sampleWithFullData: IProcedure = {
  id: 42121,
  procedureText: 'proactive Frozen',
  date: dayjs('2023-04-10'),
  patientUID: 'Tuna Realigned',
};

export const sampleWithNewData: NewProcedure = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
