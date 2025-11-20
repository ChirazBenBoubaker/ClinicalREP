import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IEncounter } from '../encounter.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../encounter.test-samples';

import { EncounterService, RestEncounter } from './encounter.service';

const requireRestSample: RestEncounter = {
  ...sampleWithRequiredData,
  date: sampleWithRequiredData.date?.format(DATE_FORMAT),
};

describe('Encounter Service', () => {
  let service: EncounterService;
  let httpMock: HttpTestingController;
  let expectedResult: IEncounter | IEncounter[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(EncounterService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a Encounter', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const encounter = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(encounter).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Encounter', () => {
      const encounter = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(encounter).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Encounter', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Encounter', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Encounter', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addEncounterToCollectionIfMissing', () => {
      it('should add a Encounter to an empty array', () => {
        const encounter: IEncounter = sampleWithRequiredData;
        expectedResult = service.addEncounterToCollectionIfMissing([], encounter);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(encounter);
      });

      it('should not add a Encounter to an array that contains it', () => {
        const encounter: IEncounter = sampleWithRequiredData;
        const encounterCollection: IEncounter[] = [
          {
            ...encounter,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addEncounterToCollectionIfMissing(encounterCollection, encounter);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Encounter to an array that doesn't contain it", () => {
        const encounter: IEncounter = sampleWithRequiredData;
        const encounterCollection: IEncounter[] = [sampleWithPartialData];
        expectedResult = service.addEncounterToCollectionIfMissing(encounterCollection, encounter);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(encounter);
      });

      it('should add only unique Encounter to an array', () => {
        const encounterArray: IEncounter[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const encounterCollection: IEncounter[] = [sampleWithRequiredData];
        expectedResult = service.addEncounterToCollectionIfMissing(encounterCollection, ...encounterArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const encounter: IEncounter = sampleWithRequiredData;
        const encounter2: IEncounter = sampleWithPartialData;
        expectedResult = service.addEncounterToCollectionIfMissing([], encounter, encounter2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(encounter);
        expect(expectedResult).toContain(encounter2);
      });

      it('should accept null and undefined values', () => {
        const encounter: IEncounter = sampleWithRequiredData;
        expectedResult = service.addEncounterToCollectionIfMissing([], null, encounter, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(encounter);
      });

      it('should return initial array if no Encounter is added', () => {
        const encounterCollection: IEncounter[] = [sampleWithRequiredData];
        expectedResult = service.addEncounterToCollectionIfMissing(encounterCollection, undefined, null);
        expect(expectedResult).toEqual(encounterCollection);
      });
    });

    describe('compareEncounter', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareEncounter(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareEncounter(entity1, entity2);
        const compareResult2 = service.compareEncounter(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareEncounter(entity1, entity2);
        const compareResult2 = service.compareEncounter(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareEncounter(entity1, entity2);
        const compareResult2 = service.compareEncounter(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
