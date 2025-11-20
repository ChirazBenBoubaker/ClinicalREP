import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ProcedureDetailComponent } from './procedure-detail.component';

describe('Procedure Management Detail Component', () => {
  let comp: ProcedureDetailComponent;
  let fixture: ComponentFixture<ProcedureDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProcedureDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ procedure: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ProcedureDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ProcedureDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load procedure on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.procedure).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
