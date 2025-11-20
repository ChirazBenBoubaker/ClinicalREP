import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFactclinical } from '../factclinical.model';

@Component({
  selector: 'itp-factclinical-detail',
  templateUrl: './factclinical-detail.component.html',
})
export class FactclinicalDetailComponent implements OnInit {
  factclinical: IFactclinical | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ factclinical }) => {
      this.factclinical = factclinical;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
