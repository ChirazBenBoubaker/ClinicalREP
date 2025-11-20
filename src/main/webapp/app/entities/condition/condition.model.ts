import dayjs from 'dayjs/esm';

export interface ICondition {
  id: number;
  conditionText?: string | null;
  conditionOnsetDates?: dayjs.Dayjs | null;
  patientUID?: string | null;
}

export type NewCondition = Omit<ICondition, 'id'> & { id: null };
