import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProcedure } from '../procedure.model';

@Component({
  selector: 'itp-procedure-detail',
  templateUrl: './procedure-detail.component.html',
})
export class ProcedureDetailComponent implements OnInit {
  procedure: IProcedure | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ procedure }) => {
      this.procedure = procedure;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
