import dayjs from 'dayjs/esm';

import { IObservation, NewObservation } from './observation.model';

export const sampleWithRequiredData: IObservation = {
  id: 84073,
};

export const sampleWithPartialData: IObservation = {
  id: 57281,
  bodyHeight: 20458,
  bodyWeight: 22018,
  bodyMass: 94554,
  creatinine: 37250,
  sodium: 44044,
  ureaNitrogen: 67725,
};

export const sampleWithFullData: IObservation = {
  id: 70608,
  bodyHeight: 12503,
  bodyWeight: 29871,
  bodyMass: 61458,
  painseverity: 55488,
  bloodPressure: 44837,
  tobaccosmokingstatusNHIS: 69970,
  creatinine: 68550,
  calcium: 17291,
  sodium: 5425,
  potassium: 35980,
  chloride: 24493,
  carbonDioxide: 13934,
  glucose: 67960,
  ureaNitrogen: 17002,
  date: dayjs('2023-04-11'),
  patientUID: 'Vermont',
};

export const sampleWithNewData: NewObservation = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
