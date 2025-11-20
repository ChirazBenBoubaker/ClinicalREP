import dayjs from 'dayjs/esm';

export interface IObservation {
  id: number;
  bodyHeight?: number | null;
  bodyWeight?: number | null;
  bodyMass?: number | null;
  painseverity?: number | null;
  bloodPressure?: number | null;
  tobaccosmokingstatusNHIS?: number | null;
  creatinine?: number | null;
  calcium?: number | null;
  sodium?: number | null;
  potassium?: number | null;
  chloride?: number | null;
  carbonDioxide?: number | null;
  glucose?: number | null;
  ureaNitrogen?: number | null;
  date?: dayjs.Dayjs | null;
  patientUID?: string | null;
}

export type NewObservation = Omit<IObservation, 'id'> & { id: null };
