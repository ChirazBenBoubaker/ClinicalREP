import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ImmunizationDetailComponent } from './immunization-detail.component';

describe('Immunization Management Detail Component', () => {
  let comp: ImmunizationDetailComponent;
  let fixture: ComponentFixture<ImmunizationDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ImmunizationDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ immunization: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ImmunizationDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ImmunizationDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load immunization on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.immunization).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
