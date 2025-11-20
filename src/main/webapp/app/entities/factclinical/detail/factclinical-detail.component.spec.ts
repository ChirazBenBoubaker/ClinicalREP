import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FactclinicalDetailComponent } from './factclinical-detail.component';

describe('Factclinical Management Detail Component', () => {
  let comp: FactclinicalDetailComponent;
  let fixture: ComponentFixture<FactclinicalDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FactclinicalDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ factclinical: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(FactclinicalDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(FactclinicalDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load factclinical on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.factclinical).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
