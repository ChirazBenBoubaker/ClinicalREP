import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IImmunization } from '../immunization.model';

@Component({
  selector: 'itp-immunization-detail',
  templateUrl: './immunization-detail.component.html',
})
export class ImmunizationDetailComponent implements OnInit {
  immunization: IImmunization | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ immunization }) => {
      this.immunization = immunization;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
