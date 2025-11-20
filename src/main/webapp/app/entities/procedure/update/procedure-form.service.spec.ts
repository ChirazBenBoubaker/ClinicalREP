import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../procedure.test-samples';

import { ProcedureFormService } from './procedure-form.service';

describe('Procedure Form Service', () => {
  let service: ProcedureFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProcedureFormService);
  });

  describe('Service methods', () => {
    describe('createProcedureFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createProcedureFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            procedureText: expect.any(Object),
            date: expect.any(Object),
            patientUID: expect.any(Object),
          })
        );
      });

      it('passing IProcedure should create a new form with FormGroup', () => {
        const formGroup = service.createProcedureFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            procedureText: expect.any(Object),
            date: expect.any(Object),
            patientUID: expect.any(Object),
          })
        );
      });
    });

    describe('getProcedure', () => {
      it('should return NewProcedure for default Procedure initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createProcedureFormGroup(sampleWithNewData);

        const procedure = service.getProcedure(formGroup) as any;

        expect(procedure).toMatchObject(sampleWithNewData);
      });

      it('should return NewProcedure for empty Procedure initial value', () => {
        const formGroup = service.createProcedureFormGroup();

        const procedure = service.getProcedure(formGroup) as any;

        expect(procedure).toMatchObject({});
      });

      it('should return IProcedure', () => {
        const formGroup = service.createProcedureFormGroup(sampleWithRequiredData);

        const procedure = service.getProcedure(formGroup) as any;

        expect(procedure).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IProcedure should not enable id FormControl', () => {
        const formGroup = service.createProcedureFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewProcedure should disable id FormControl', () => {
        const formGroup = service.createProcedureFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
