import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../observation.test-samples';

import { ObservationFormService } from './observation-form.service';

describe('Observation Form Service', () => {
  let service: ObservationFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ObservationFormService);
  });

  describe('Service methods', () => {
    describe('createObservationFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createObservationFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            bodyHeight: expect.any(Object),
            bodyWeight: expect.any(Object),
            bodyMass: expect.any(Object),
            painseverity: expect.any(Object),
            bloodPressure: expect.any(Object),
            tobaccosmokingstatusNHIS: expect.any(Object),
            creatinine: expect.any(Object),
            calcium: expect.any(Object),
            sodium: expect.any(Object),
            potassium: expect.any(Object),
            chloride: expect.any(Object),
            carbonDioxide: expect.any(Object),
            glucose: expect.any(Object),
            ureaNitrogen: expect.any(Object),
            date: expect.any(Object),
            patientUID: expect.any(Object),
          })
        );
      });

      it('passing IObservation should create a new form with FormGroup', () => {
        const formGroup = service.createObservationFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            bodyHeight: expect.any(Object),
            bodyWeight: expect.any(Object),
            bodyMass: expect.any(Object),
            painseverity: expect.any(Object),
            bloodPressure: expect.any(Object),
            tobaccosmokingstatusNHIS: expect.any(Object),
            creatinine: expect.any(Object),
            calcium: expect.any(Object),
            sodium: expect.any(Object),
            potassium: expect.any(Object),
            chloride: expect.any(Object),
            carbonDioxide: expect.any(Object),
            glucose: expect.any(Object),
            ureaNitrogen: expect.any(Object),
            date: expect.any(Object),
            patientUID: expect.any(Object),
          })
        );
      });
    });

    describe('getObservation', () => {
      it('should return NewObservation for default Observation initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createObservationFormGroup(sampleWithNewData);

        const observation = service.getObservation(formGroup) as any;

        expect(observation).toMatchObject(sampleWithNewData);
      });

      it('should return NewObservation for empty Observation initial value', () => {
        const formGroup = service.createObservationFormGroup();

        const observation = service.getObservation(formGroup) as any;

        expect(observation).toMatchObject({});
      });

      it('should return IObservation', () => {
        const formGroup = service.createObservationFormGroup(sampleWithRequiredData);

        const observation = service.getObservation(formGroup) as any;

        expect(observation).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IObservation should not enable id FormControl', () => {
        const formGroup = service.createObservationFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewObservation should disable id FormControl', () => {
        const formGroup = service.createObservationFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
