import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../encounter.test-samples';

import { EncounterFormService } from './encounter-form.service';

describe('Encounter Form Service', () => {
  let service: EncounterFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EncounterFormService);
  });

  describe('Service methods', () => {
    describe('createEncounterFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createEncounterFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            encountersText: expect.any(Object),
            encounterLocation: expect.any(Object),
            encounterProvider: expect.any(Object),
            date: expect.any(Object),
            patientUID: expect.any(Object),
          })
        );
      });

      it('passing IEncounter should create a new form with FormGroup', () => {
        const formGroup = service.createEncounterFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            encountersText: expect.any(Object),
            encounterLocation: expect.any(Object),
            encounterProvider: expect.any(Object),
            date: expect.any(Object),
            patientUID: expect.any(Object),
          })
        );
      });
    });

    describe('getEncounter', () => {
      it('should return NewEncounter for default Encounter initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createEncounterFormGroup(sampleWithNewData);

        const encounter = service.getEncounter(formGroup) as any;

        expect(encounter).toMatchObject(sampleWithNewData);
      });

      it('should return NewEncounter for empty Encounter initial value', () => {
        const formGroup = service.createEncounterFormGroup();

        const encounter = service.getEncounter(formGroup) as any;

        expect(encounter).toMatchObject({});
      });

      it('should return IEncounter', () => {
        const formGroup = service.createEncounterFormGroup(sampleWithRequiredData);

        const encounter = service.getEncounter(formGroup) as any;

        expect(encounter).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IEncounter should not enable id FormControl', () => {
        const formGroup = service.createEncounterFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewEncounter should disable id FormControl', () => {
        const formGroup = service.createEncounterFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
