import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ImmunizationFormService } from './immunization-form.service';
import { ImmunizationService } from '../service/immunization.service';
import { IImmunization } from '../immunization.model';

import { ImmunizationUpdateComponent } from './immunization-update.component';

describe('Immunization Management Update Component', () => {
  let comp: ImmunizationUpdateComponent;
  let fixture: ComponentFixture<ImmunizationUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let immunizationFormService: ImmunizationFormService;
  let immunizationService: ImmunizationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ImmunizationUpdateComponent],
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
      .overrideTemplate(ImmunizationUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ImmunizationUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    immunizationFormService = TestBed.inject(ImmunizationFormService);
    immunizationService = TestBed.inject(ImmunizationService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const immunization: IImmunization = { id: 456 };

      activatedRoute.data = of({ immunization });
      comp.ngOnInit();

      expect(comp.immunization).toEqual(immunization);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IImmunization>>();
      const immunization = { id: 123 };
      jest.spyOn(immunizationFormService, 'getImmunization').mockReturnValue(immunization);
      jest.spyOn(immunizationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ immunization });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: immunization }));
      saveSubject.complete();

      // THEN
      expect(immunizationFormService.getImmunization).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(immunizationService.update).toHaveBeenCalledWith(expect.objectContaining(immunization));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IImmunization>>();
      const immunization = { id: 123 };
      jest.spyOn(immunizationFormService, 'getImmunization').mockReturnValue({ id: null });
      jest.spyOn(immunizationService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ immunization: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: immunization }));
      saveSubject.complete();

      // THEN
      expect(immunizationFormService.getImmunization).toHaveBeenCalled();
      expect(immunizationService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IImmunization>>();
      const immunization = { id: 123 };
      jest.spyOn(immunizationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ immunization });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(immunizationService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
