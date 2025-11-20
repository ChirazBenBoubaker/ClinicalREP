import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { FactclinicalFormService } from './factclinical-form.service';
import { FactclinicalService } from '../service/factclinical.service';
import { IFactclinical } from '../factclinical.model';

import { FactclinicalUpdateComponent } from './factclinical-update.component';

describe('Factclinical Management Update Component', () => {
  let comp: FactclinicalUpdateComponent;
  let fixture: ComponentFixture<FactclinicalUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let factclinicalFormService: FactclinicalFormService;
  let factclinicalService: FactclinicalService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [FactclinicalUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(FactclinicalUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FactclinicalUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    factclinicalFormService = TestBed.inject(FactclinicalFormService);
    factclinicalService = TestBed.inject(FactclinicalService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const factclinical: IFactclinical = { id: 456 };

      activatedRoute.data = of({ factclinical });
      comp.ngOnInit();

      expect(comp.factclinical).toEqual(factclinical);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFactclinical>>();
      const factclinical = { id: 123 };
      jest.spyOn(factclinicalFormService, 'getFactclinical').mockReturnValue(factclinical);
      jest.spyOn(factclinicalService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ factclinical });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: factclinical }));
      saveSubject.complete();

      // THEN
      expect(factclinicalFormService.getFactclinical).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(factclinicalService.update).toHaveBeenCalledWith(expect.objectContaining(factclinical));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFactclinical>>();
      const factclinical = { id: 123 };
      jest.spyOn(factclinicalFormService, 'getFactclinical').mockReturnValue({ id: null });
      jest.spyOn(factclinicalService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ factclinical: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: factclinical }));
      saveSubject.complete();

      // THEN
      expect(factclinicalFormService.getFactclinical).toHaveBeenCalled();
      expect(factclinicalService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFactclinical>>();
      const factclinical = { id: 123 };
      jest.spyOn(factclinicalService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ factclinical });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(factclinicalService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
