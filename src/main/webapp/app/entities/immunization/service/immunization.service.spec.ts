import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IImmunization } from '../immunization.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../immunization.test-samples';

import { ImmunizationService, RestImmunization } from './immunization.service';

const requireRestSample: RestImmunization = {
  ...sampleWithRequiredData,
  date: sampleWithRequiredData.date?.format(DATE_FORMAT),
};

describe('Immunization Service', () => {
  let service: ImmunizationService;
  let httpMock: HttpTestingController;
  let expectedResult: IImmunization | IImmunization[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ImmunizationService);
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

    it('should create a Immunization', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const immunization = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(immunization).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Immunization', () => {
      const immunization = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(immunization).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Immunization', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Immunization', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Immunization', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addImmunizationToCollectionIfMissing', () => {
      it('should add a Immunization to an empty array', () => {
        const immunization: IImmunization = sampleWithRequiredData;
        expectedResult = service.addImmunizationToCollectionIfMissing([], immunization);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(immunization);
      });

      it('should not add a Immunization to an array that contains it', () => {
        const immunization: IImmunization = sampleWithRequiredData;
        const immunizationCollection: IImmunization[] = [
          {
            ...immunization,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addImmunizationToCollectionIfMissing(immunizationCollection, immunization);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Immunization to an array that doesn't contain it", () => {
        const immunization: IImmunization = sampleWithRequiredData;
        const immunizationCollection: IImmunization[] = [sampleWithPartialData];
        expectedResult = service.addImmunizationToCollectionIfMissing(immunizationCollection, immunization);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(immunization);
      });

      it('should add only unique Immunization to an array', () => {
        const immunizationArray: IImmunization[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const immunizationCollection: IImmunization[] = [sampleWithRequiredData];
        expectedResult = service.addImmunizationToCollectionIfMissing(immunizationCollection, ...immunizationArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const immunization: IImmunization = sampleWithRequiredData;
        const immunization2: IImmunization = sampleWithPartialData;
        expectedResult = service.addImmunizationToCollectionIfMissing([], immunization, immunization2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(immunization);
        expect(expectedResult).toContain(immunization2);
      });

      it('should accept null and undefined values', () => {
        const immunization: IImmunization = sampleWithRequiredData;
        expectedResult = service.addImmunizationToCollectionIfMissing([], null, immunization, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(immunization);
      });

      it('should return initial array if no Immunization is added', () => {
        const immunizationCollection: IImmunization[] = [sampleWithRequiredData];
        expectedResult = service.addImmunizationToCollectionIfMissing(immunizationCollection, undefined, null);
        expect(expectedResult).toEqual(immunizationCollection);
      });
    });

    describe('compareImmunization', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareImmunization(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareImmunization(entity1, entity2);
        const compareResult2 = service.compareImmunization(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareImmunization(entity1, entity2);
        const compareResult2 = service.compareImmunization(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareImmunization(entity1, entity2);
        const compareResult2 = service.compareImmunization(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
