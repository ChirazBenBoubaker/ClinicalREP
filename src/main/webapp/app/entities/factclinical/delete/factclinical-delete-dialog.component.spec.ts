jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { FactclinicalService } from '../service/factclinical.service';

import { FactclinicalDeleteDialogComponent } from './factclinical-delete-dialog.component';

describe('Factclinical Management Delete Component', () => {
  let comp: FactclinicalDeleteDialogComponent;
  let fixture: ComponentFixture<FactclinicalDeleteDialogComponent>;
  let service: FactclinicalService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [FactclinicalDeleteDialogComponent],
      providers: [NgbActiveModal],
    })
      .overrideTemplate(FactclinicalDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(FactclinicalDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(FactclinicalService);
    mockActiveModal = TestBed.inject(NgbActiveModal);
  });

  describe('confirmDelete', () => {
    it('Should call delete service on confirmDelete', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse({ body: {} })));

        // WHEN
        comp.confirmDelete(123);
        tick();

        // THEN
        expect(service.delete).toHaveBeenCalledWith(123);
        expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
      })
    ));

    it('Should not call delete service on clear', () => {
      // GIVEN
      jest.spyOn(service, 'delete');

      // WHEN
      comp.cancel();

      // THEN
      expect(service.delete).not.toHaveBeenCalled();
      expect(mockActiveModal.close).not.toHaveBeenCalled();
      expect(mockActiveModal.dismiss).toHaveBeenCalled();
    });
  });
});
