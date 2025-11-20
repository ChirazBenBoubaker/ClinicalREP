import dayjs from 'dayjs/esm';

import { IEncounter, NewEncounter } from './encounter.model';

export const sampleWithRequiredData: IEncounter = {
  id: 64158,
};

export const sampleWithPartialData: IEncounter = {
  id: 49242,
  encountersText: 'Producer iterate cyan',
  encounterLocation: 'Mouse dynamic',
  date: dayjs('2023-04-10'),
  patientUID: 'Iran fuchsia reinvent',
};

export const sampleWithFullData: IEncounter = {
  id: 16290,
  encountersText: 'leverage TCP',
  encounterLocation: 'grey global',
  encounterProvider: 'Profound',
  date: dayjs('2023-04-11'),
  patientUID: 'up Product solutions',
};

export const sampleWithNewData: NewEncounter = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
