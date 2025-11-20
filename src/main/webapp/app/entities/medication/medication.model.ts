import dayjs from 'dayjs/esm';

export interface IMedication {
  id: number;
  medicationText?: string | null;
  date?: dayjs.Dayjs | null;
  patientUID?: string | null;
}

export type NewMedication = Omit<IMedication, 'id'> & { id: null };
