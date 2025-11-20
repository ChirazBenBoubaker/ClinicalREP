import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'patient',
        data: { pageTitle: 'clinicalApp.patient.home.title' },
        loadChildren: () => import('./patient/patient.module').then(m => m.PatientModule),
      },
      {
        path: 'encounter',
        data: { pageTitle: 'clinicalApp.encounter.home.title' },
        loadChildren: () => import('./encounter/encounter.module').then(m => m.EncounterModule),
      },
      {
        path: 'procedure',
        data: { pageTitle: 'clinicalApp.procedure.home.title' },
        loadChildren: () => import('./procedure/procedure.module').then(m => m.ProcedureModule),
      },
      {
        path: 'immunization',
        data: { pageTitle: 'clinicalApp.immunization.home.title' },
        loadChildren: () => import('./immunization/immunization.module').then(m => m.ImmunizationModule),
      },
      {
        path: 'medication',
        data: { pageTitle: 'clinicalApp.medication.home.title' },
        loadChildren: () => import('./medication/medication.module').then(m => m.MedicationModule),
      },
      {
        path: 'condition',
        data: { pageTitle: 'clinicalApp.condition.home.title' },
        loadChildren: () => import('./condition/condition.module').then(m => m.ConditionModule),
      },
      {
        path: 'date',
        data: { pageTitle: 'clinicalApp.date.home.title' },
        loadChildren: () => import('./date/date.module').then(m => m.DateModule),
      },
      {
        path: 'observation',
        data: { pageTitle: 'clinicalApp.observation.home.title' },
        loadChildren: () => import('./observation/observation.module').then(m => m.ObservationModule),
      },
      {
        path: 'factclinical',
        data: { pageTitle: 'clinicalApp.factclinical.home.title' },
        loadChildren: () => import('./factclinical/factclinical.module').then(m => m.FactclinicalModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
