import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EncounterDetailComponent } from './encounter-detail.component';

describe('Encounter Management Detail Component', () => {
  let comp: EncounterDetailComponent;
  let fixture: ComponentFixture<EncounterDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EncounterDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ encounter: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(EncounterDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(EncounterDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load encounter on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.encounter).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
