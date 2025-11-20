import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { EncounterFormService } from './encounter-form.service';
import { EncounterService } from '../service/encounter.service';
import { IEncounter } from '../encounter.model';

import { EncounterUpdateComponent } from './encounter-update.component';

describe('Encounter Management Update Component', () => {
  let comp: EncounterUpdateComponent;
  let fixture: ComponentFixture<EncounterUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let encounterFormService: EncounterFormService;
  let encounterService: EncounterService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [EncounterUpdateComponent],
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
      .overrideTemplate(EncounterUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EncounterUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    encounterFormService = TestBed.inject(EncounterFormService);
    encounterService = TestBed.inject(EncounterService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const encounter: IEncounter = { id: 456 };

      activatedRoute.data = of({ encounter });
      comp.ngOnInit();

      expect(comp.encounter).toEqual(encounter);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEncounter>>();
      const encounter = { id: 123 };
      jest.spyOn(encounterFormService, 'getEncounter').mockReturnValue(encounter);
      jest.spyOn(encounterService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ encounter });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: encounter }));
      saveSubject.complete();

      // THEN
      expect(encounterFormService.getEncounter).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(encounterService.update).toHaveBeenCalledWith(expect.objectContaining(encounter));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEncounter>>();
      const encounter = { id: 123 };
      jest.spyOn(encounterFormService, 'getEncounter').mockReturnValue({ id: null });
      jest.spyOn(encounterService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ encounter: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: encounter }));
      saveSubject.complete();

      // THEN
      expect(encounterFormService.getEncounter).toHaveBeenCalled();
      expect(encounterService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEncounter>>();
      const encounter = { id: 123 };
      jest.spyOn(encounterService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ encounter });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(encounterService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
