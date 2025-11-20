import dayjs from 'dayjs/esm';

import { IFactclinical, NewFactclinical } from './factclinical.model';

export const sampleWithRequiredData: IFactclinical = {
  id: 84085,
};

export const sampleWithPartialData: IFactclinical = {
  id: 35667,
  encounterID: 70340,
  medicationID: 92073,
  conditionID: 27685,
  date: dayjs('2023-04-10'),
};

export const sampleWithFullData: IFactclinical = {
  id: 51130,
  patientUID: 'Nigeria Checking',
  encounterID: 31624,
  observationID: 29793,
  procedureID: 88699,
  immunizationID: 62065,
  medicationID: 44775,
  conditionID: 13442,
  date: dayjs('2023-04-10'),
};

export const sampleWithNewData: NewFactclinical = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
