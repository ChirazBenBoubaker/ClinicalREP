import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IFactclinical } from '../factclinical.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../factclinical.test-samples';

import { FactclinicalService, RestFactclinical } from './factclinical.service';

const requireRestSample: RestFactclinical = {
  ...sampleWithRequiredData,
  date: sampleWithRequiredData.date?.format(DATE_FORMAT),
};

describe('Factclinical Service', () => {
  let service: FactclinicalService;
  let httpMock: HttpTestingController;
  let expectedResult: IFactclinical | IFactclinical[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(FactclinicalService);
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

    it('should create a Factclinical', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const factclinical = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(factclinical).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Factclinical', () => {
      const factclinical = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(factclinical).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Factclinical', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Factclinical', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Factclinical', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addFactclinicalToCollectionIfMissing', () => {
      it('should add a Factclinical to an empty array', () => {
        const factclinical: IFactclinical = sampleWithRequiredData;
        expectedResult = service.addFactclinicalToCollectionIfMissing([], factclinical);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(factclinical);
      });

      it('should not add a Factclinical to an array that contains it', () => {
        const factclinical: IFactclinical = sampleWithRequiredData;
        const factclinicalCollection: IFactclinical[] = [
          {
            ...factclinical,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addFactclinicalToCollectionIfMissing(factclinicalCollection, factclinical);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Factclinical to an array that doesn't contain it", () => {
        const factclinical: IFactclinical = sampleWithRequiredData;
        const factclinicalCollection: IFactclinical[] = [sampleWithPartialData];
        expectedResult = service.addFactclinicalToCollectionIfMissing(factclinicalCollection, factclinical);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(factclinical);
      });

      it('should add only unique Factclinical to an array', () => {
        const factclinicalArray: IFactclinical[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const factclinicalCollection: IFactclinical[] = [sampleWithRequiredData];
        expectedResult = service.addFactclinicalToCollectionIfMissing(factclinicalCollection, ...factclinicalArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const factclinical: IFactclinical = sampleWithRequiredData;
        const factclinical2: IFactclinical = sampleWithPartialData;
        expectedResult = service.addFactclinicalToCollectionIfMissing([], factclinical, factclinical2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(factclinical);
        expect(expectedResult).toContain(factclinical2);
      });

      it('should accept null and undefined values', () => {
        const factclinical: IFactclinical = sampleWithRequiredData;
        expectedResult = service.addFactclinicalToCollectionIfMissing([], null, factclinical, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(factclinical);
      });

      it('should return initial array if no Factclinical is added', () => {
        const factclinicalCollection: IFactclinical[] = [sampleWithRequiredData];
        expectedResult = service.addFactclinicalToCollectionIfMissing(factclinicalCollection, undefined, null);
        expect(expectedResult).toEqual(factclinicalCollection);
      });
    });

    describe('compareFactclinical', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareFactclinical(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareFactclinical(entity1, entity2);
        const compareResult2 = service.compareFactclinical(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareFactclinical(entity1, entity2);
        const compareResult2 = service.compareFactclinical(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareFactclinical(entity1, entity2);
        const compareResult2 = service.compareFactclinical(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
