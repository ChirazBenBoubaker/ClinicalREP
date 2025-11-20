import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../immunization.test-samples';

import { ImmunizationFormService } from './immunization-form.service';

describe('Immunization Form Service', () => {
  let service: ImmunizationFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ImmunizationFormService);
  });

  describe('Service methods', () => {
    describe('createImmunizationFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createImmunizationFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            immunization: expect.any(Object),
            date: expect.any(Object),
            patientUID: expect.any(Object),
          })
        );
      });

      it('passing IImmunization should create a new form with FormGroup', () => {
        const formGroup = service.createImmunizationFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            immunization: expect.any(Object),
            date: expect.any(Object),
            patientUID: expect.any(Object),
          })
        );
      });
    });

    describe('getImmunization', () => {
      it('should return NewImmunization for default Immunization initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createImmunizationFormGroup(sampleWithNewData);

        const immunization = service.getImmunization(formGroup) as any;

        expect(immunization).toMatchObject(sampleWithNewData);
      });

      it('should return NewImmunization for empty Immunization initial value', () => {
        const formGroup = service.createImmunizationFormGroup();

        const immunization = service.getImmunization(formGroup) as any;

        expect(immunization).toMatchObject({});
      });

      it('should return IImmunization', () => {
        const formGroup = service.createImmunizationFormGroup(sampleWithRequiredData);

        const immunization = service.getImmunization(formGroup) as any;

        expect(immunization).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IImmunization should not enable id FormControl', () => {
        const formGroup = service.createImmunizationFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewImmunization should disable id FormControl', () => {
        const formGroup = service.createImmunizationFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
