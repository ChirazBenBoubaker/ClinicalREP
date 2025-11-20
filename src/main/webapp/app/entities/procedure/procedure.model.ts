import dayjs from 'dayjs/esm';

export interface IProcedure {
  id: number;
  procedureText?: string | null;
  date?: dayjs.Dayjs | null;
  patientUID?: string | null;
}

export type NewProcedure = Omit<IProcedure, 'id'> & { id: null };
