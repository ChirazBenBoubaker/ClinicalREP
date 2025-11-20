import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../factclinical.test-samples';

import { FactclinicalFormService } from './factclinical-form.service';

describe('Factclinical Form Service', () => {
  let service: FactclinicalFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FactclinicalFormService);
  });

  describe('Service methods', () => {
    describe('createFactclinicalFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createFactclinicalFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            patientUID: expect.any(Object),
            encounterID: expect.any(Object),
            observationID: expect.any(Object),
            procedureID: expect.any(Object),
            immunizationID: expect.any(Object),
            medicationID: expect.any(Object),
            conditionID: expect.any(Object),
            date: expect.any(Object),
          })
        );
      });

      it('passing IFactclinical should create a new form with FormGroup', () => {
        const formGroup = service.createFactclinicalFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            patientUID: expect.any(Object),
            encounterID: expect.any(Object),
            observationID: expect.any(Object),
            procedureID: expect.any(Object),
            immunizationID: expect.any(Object),
            medicationID: expect.any(Object),
            conditionID: expect.any(Object),
            date: expect.any(Object),
          })
        );
      });
    });

    describe('getFactclinical', () => {
      it('should return NewFactclinical for default Factclinical initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createFactclinicalFormGroup(sampleWithNewData);

        const factclinical = service.getFactclinical(formGroup) as any;

        expect(factclinical).toMatchObject(sampleWithNewData);
      });

      it('should return NewFactclinical for empty Factclinical initial value', () => {
        const formGroup = service.createFactclinicalFormGroup();

        const factclinical = service.getFactclinical(formGroup) as any;

        expect(factclinical).toMatchObject({});
      });

      it('should return IFactclinical', () => {
        const formGroup = service.createFactclinicalFormGroup(sampleWithRequiredData);

        const factclinical = service.getFactclinical(formGroup) as any;

        expect(factclinical).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IFactclinical should not enable id FormControl', () => {
        const formGroup = service.createFactclinicalFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewFactclinical should disable id FormControl', () => {
        const formGroup = service.createFactclinicalFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
