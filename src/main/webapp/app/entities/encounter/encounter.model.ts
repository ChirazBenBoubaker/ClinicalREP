import dayjs from 'dayjs/esm';

export interface IEncounter {
  id: number;
  encountersText?: string | null;
  encounterLocation?: string | null;
  encounterProvider?: string | null;
  date?: dayjs.Dayjs | null;
  patientUID?: string | null;
}

export type NewEncounter = Omit<IEncounter, 'id'> & { id: null };
