import dayjs from 'dayjs/esm';

export interface IImmunization {
  id: number;
  immunization?: string | null;
  date?: dayjs.Dayjs | null;
  patientUID?: string | null;
}

export type NewImmunization = Omit<IImmunization, 'id'> & { id: null };
