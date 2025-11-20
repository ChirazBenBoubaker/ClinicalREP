import dayjs from 'dayjs/esm';

export interface IDate {
  id: number;
  date?: dayjs.Dayjs | null;
  year?: number | null;
  month?: number | null;
  day?: number | null;
}

export type NewDate = Omit<IDate, 'id'> & { id: null };
