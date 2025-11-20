import dayjs from 'dayjs/esm';

import { IPatient, NewPatient } from './patient.model';

export const sampleWithRequiredData: IPatient = {
  id: 39843,
  patientUID: 'AGP',
};

export const sampleWithPartialData: IPatient = {
  id: 82085,
  patientUID: 'Shoes',
  nameGiven: 'Administrator Home Consultant',
  birthdate: dayjs('2023-04-10'),
  contact: 'Keyboard Denar orchestrate',
  city: 'Cobybury',
  state: 'Steel synthesize Web',
  postalcode: 'Global programming Loan',
};

export const sampleWithFullData: IPatient = {
  id: 55097,
  patientUID: 'bluetooth microchip initiatives',
  nameFamily: 'functionalities Savings',
  nameGiven: 'stable Dynamic Ball',
  birthdate: dayjs('2023-04-10'),
  gender: 'Wooden',
  contact: 'Polarised',
  line: 'coherent',
  city: 'Kylerview',
  country: 'Trinidad and Tobago',
  state: 'Republic Sports',
  postalcode: 'Handmade payment Taiwan',
};

export const sampleWithNewData: NewPatient = {
  patientUID: 'Reactive Concrete',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
