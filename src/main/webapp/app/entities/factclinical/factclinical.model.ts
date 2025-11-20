import dayjs from 'dayjs/esm';

export interface IFactclinical {
  id: number;
  patientUID?: string | null;
  encounterID?: number | null;
  observationID?: number | null;
  procedureID?: number | null;
  immunizationID?: number | null;
  medicationID?: number | null;
  conditionID?: number | null;
  date?: dayjs.Dayjs | null;
}

export type NewFactclinical = Omit<IFactclinical, 'id'> & { id: null };
