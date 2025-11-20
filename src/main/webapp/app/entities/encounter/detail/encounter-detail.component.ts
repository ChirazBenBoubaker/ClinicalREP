import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEncounter } from '../encounter.model';

@Component({
  selector: 'itp-encounter-detail',
  templateUrl: './encounter-detail.component.html',
})
export class EncounterDetailComponent implements OnInit {
  encounter: IEncounter | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ encounter }) => {
      this.encounter = encounter;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
