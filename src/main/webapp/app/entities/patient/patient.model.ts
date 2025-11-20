import dayjs from 'dayjs/esm';

export interface IPatient {
  id: number;
  patientUID?: string | null;
  nameFamily?: string | null;
  nameGiven?: string | null;
  birthdate?: dayjs.Dayjs | null;
  gender?: string | null;
  contact?: string | null;
  line?: string | null;
  city?: string | null;
  country?: string | null;
  state?: string | null;
  postalcode?: string | null;
}

export type NewPatient = Omit<IPatient, 'id'> & { id: null };
