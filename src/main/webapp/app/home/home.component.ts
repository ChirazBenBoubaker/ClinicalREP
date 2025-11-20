import {Component, OnInit, OnDestroy, ElementRef, ViewChild} from '@angular/core';
import { Router } from '@angular/router';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { Chart} from "chart.js";
import { ChartConfiguration, ChartOptions, ChartType } from 'chart.js';

import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';
import { PatientService } from '../entities/patient/service/patient.service';
import { HttpHeaders } from '@angular/common/http';
import { EncounterService } from '../entities/encounter/service/encounter.service';
import { ImmunizationService } from '../entities/immunization/service/immunization.service';
import { ConditionService } from "../entities/condition/service/condition.service";

@Component({
  selector: 'itp-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})

export class HomeComponent implements OnInit, OnDestroy {
  account: Account | null = null;
  title = 'ng2-charts-demo';
  totalPatients = 0;
  totalEncounters = 0;
  totalCheckup = 0;
  totalImmunizations = 0;
  isDataLoaded = false;
  isDataLoaded2 = false;
  ispieDataLoaded = false;
  ispieDataLoaded2 = false;
  data: any[] = [];
  data2: any[] = [];
  pieChartData: number[] = [];
  pieChartData2: number[] = [];
  //---------------------------------
  conditions: any[] = [];
  chartData: any[] = [];

  barChartOptions: any = {
    responsive: true
  };
  barChartLabels: string[] = [];
  barChartType: string = 'bar';
  barChartLegend: boolean = true;
  barChartData: any[] = [];
  //---------------------------------
  public lineChartData: ChartConfiguration<'line'>['data'] = {
    labels: ['Janvier', 'février', 'mars', 'avril', 'mai', 'juin', 'juillet', 'août', 'septembre', 'octobre', 'novembre', 'décembre'],
    datasets: [
      {
        data: [],
        label: 'Encounters',
        fill: true,
        tension: 0.5,
        borderColor: 'black',
      },
    ],
  };
  public lineChartOptions: ChartOptions<'line'> = {
    responsive: false,
  };
  public lineChartLegend = true;
  //-------------------------------------


  public barChartData1: ChartConfiguration<'bar'>['data'] = {
    labels: ['1 ML Epoetin Alfa', 'Simvistatin 10 MG', 'Cisplatin 50 MG Injection', 'PACLitaxel 100 MG Injection', 'Acetaminophen 325 MG Oral Tablet'],
    datasets: [
      {
        data: [4133, 2316, 880, 820, 631], label: 'Les médicaments les plus pris',
        backgroundColor: [
          'rgb(189,138,182)',
          'rgb(255,161,181)',
          'rgb(255,226,154)',
          'rgb(134,199,243)',
          'rgba(75,192,192,0.2)',
          'rgba(153, 102, 255, 0.2)',
          'rgba(213,161,100,0.87)'
        ],

      },
    ],
  };
  public barChartData2: ChartConfiguration<'bar'>['data'] = {
    labels: ['acute bronchitis', 'Otitis media', 'Viral Sinusitis', 'hypertension', 'Coronary Heart Diasease', 'Streptococcal sore throat'],
    datasets: [
      {
        data: [541, 342, 241, 197, 193, 180], label: 'Les conditions fréquentes',
        backgroundColor: [
          'rgb(189,138,182)',
          'rgb(255,161,181)',
          'rgb(255,226,154)',
          'rgb(134,199,243)',
          'rgba(75,192,192,0.2)',
          'rgba(153, 102, 255, 0.2)',
          'rgba(213,161,100,0.87)'
        ],

      },
    ],
  };
  public barChartOptions2: ChartOptions<'bar'> = {
    responsive: false,
  };
  //----------------------
  public lineChartData2: ChartConfiguration<'line'>['data'] = {
    labels: ['Janvier', 'février', 'mars', 'avril', 'mai', 'juin', 'juillet', 'août', 'septembre', 'octobre', 'novembre', 'décembre'],
    datasets: [
      {
        data: [],
        label: 'immunization',
        fill: true,
        tension: 0.5,
        borderColor: 'grey',
        backgroundColor: [
          'rgb(134,199,243)',
        ],
      },
    ],
  };
  public lineChartOptions2: ChartOptions<'line'> = {
    responsive: false,
  };
  public lineChartLegend2 = true;
  //---------------------------------------
  private readonly destroy$ = new Subject<void>();
  public pieChartOptions: ChartOptions<'pie'> = {
    responsive: false,
  };
  public pieChartOptions2: ChartOptions<'pie'> = {
    responsive: false,
  };

  public pieChartLabels = ['Femme', 'Homme'];
  public pieChartLabels2 = ['0-14', '15-24', '25-64','65-80'];
  public pieChartDatasets = [
    {
      data: [0, 0],
    },
  ];
  public pieChartDatasets2 = [
    {
      data: [0, 0,0,0],
    },

  ];
  public pieChartLegend = true;
  public pieChartLegend2 = true;
  public pieChartPlugins = [];
  public pieChartPlugins2 = [];
  constructor(
    private accountService: AccountService,
    private patientService: PatientService,
    private encounterService: EncounterService,
    private immunizationService: ImmunizationService,
    private conditionService : ConditionService ,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.data = [
      {
        Janvier: 1,
        février: 0,
        mars: 0,
        avril: 0,
        mai: 10,
        juin: 0,
        juillet: 0,
        août: 0,
        septembre: 0,
        octobre: 0,
        novembre: 0,
        décembre: 0,
      },
    ];

    this.data2 = [
      {
        Janvier: 1,
        février: 0,
        mars: 0,
        avril: 0,
        mai: 10,
        juin: 0,
        juillet: 0,
        août: 0,
        septembre: 0,
        octobre: 0,
        novembre: 0,
        décembre: 0,
      },
    ];


    this.pieChartData = [0, 0];
    this.pieChartData2 = [0, 0,0,0];
    this.accountService
      .getAuthenticationState()
      .pipe(takeUntil(this.destroy$))
      .subscribe(account => (this.account = account));

    this.patientService.count().subscribe({
      next: (res: any) => {
        this.onSuccessPatientCount(res.body, res.headers);
      },
      error() {
        console.log('error'); //eslint-disable-line no-console
      },
    });

    this.changement(2023);
    this.changement2(2023);

    const criteria0: any = {};
    criteria0['encountersText.equals'] = 'Encounter for \'check-up\'' ;
    this.encounterService.count(criteria0).subscribe({
      next: (res: any) => {
        this.onSuccessEncounterCheckCount(res.body, res.headers);
      },
      error() {
        console.log('error'); //eslint-disable-line no-console
      },
    });

    const criteria: any = {};
    criteria['gender.equals'] = 'male';
    this.patientService.count(criteria).subscribe({
      next: (res: any) => {
        this.onSuccessPatientMaleCount(res.body, res.headers);
      },
      error() {
        console.log('error'); //eslint-disable-line no-console
      },
    });
    const criteria2: any = {};
    criteria2['gender.equals'] = 'female';
    this.patientService.count(criteria2).subscribe({
      next: (res: any) => {
        this.onSuccessPatientFemaleCount(res.body, res.headers);
      },
      error() {
        console.log('error'); //eslint-disable-line no-console
      },
    });
    const criteria3: any = {};

    criteria3['birthdate.greaterThan'] = '2009-01-01';
    this.patientService.count(criteria3).subscribe({
      next: (res: any) => {
        this.onSuccessPatientAgeOne(res.body, res.headers);
      },
      error() {
        console.log('error'); //eslint-disable-line no-console
      },
    });

    const criteria4: any = {};
    criteria4['birthdate.lessThan'] = '1958-12-31';
    this.patientService.count(criteria4).subscribe({
      next: (res: any) => {
        this.onSuccessPatientAgeFour(res.body, res.headers);
      },
      error() {
        console.log('error'); //eslint-disable-line no-console
      },
    });

    const criteria5: any = {};
    criteria5['birthdate.in'] = ['1958-01-01','1998-01-01'] ;
    this.patientService.count(criteria4).subscribe({
      next: (res: any) => {
        this.onSuccessPatientthree(res.body, res.headers);
      },
      error() {
        console.log('error'); //eslint-disable-line no-console
      },
    });

    const criteria6: any = {};
    criteria6['birthdate.in'] = '1998-01-01' && '2009-01-01';
    this.patientService.count(criteria4).subscribe({
      next: (res: any) => {
        this.onSuccessPatientAgetwo(res.body, res.headers);
      },
      error() {
        console.log('error'); //eslint-disable-line no-console
      },
    });

    setTimeout(() => {
      console.log(this.pieChartData); //eslint-disable-line no-console
      this.pieChartDatasets[0].data = this.pieChartData;
      this.ispieDataLoaded = true;
    }, 1000);
    setTimeout(() => {
      console.log(this.pieChartData2); //eslint-disable-line no-console
      this.pieChartDatasets2[0].data = this.pieChartData2;
      this.ispieDataLoaded2 = true;
    }, 1000);
    this.encounterService.count().subscribe({
      next: (res: any) => {
        this.onSuccessEncounterCount(res.body, res.headers);
      },
      error() {
        console.log('error'); //eslint-disable-line no-console
      },
    });
    this.immunizationService.count().subscribe({
      next: (res: any) => {
        this.onSuccessImmunizationCount(res.body, res.headers);
      },
      error() {
        console.log('error'); //eslint-disable-line no-console
      },
    });

  }

  login(): void {
    this.router.navigate(['/login']);
  }
  changement(data:any){
    this.isDataLoaded = false;
    let year=2017;
    if(data!==2023){
      console.log(data.target.value)
      year = data.target.value;
    }
    for (let i = 1; i < 13; i++) {
      this.encounterService.queryCountByDate(i, year).subscribe({
        next: (res: any) => {
          this.onSuccessLoadMonth(res.body, res.headers, i);
        },
        error() {
          console.log('error'); //eslint-disable-line no-console
        },
      });
    }
    setTimeout(() => {
      this.lineChartData.datasets[0].data = this.data;
      this.isDataLoaded = true;
    }, 1000);
  }
  //--------------------
  changement2(data:any){
    this.isDataLoaded2 = false;
    let year2=2023;
    if(data!==2023){
      console.log(data.target.value)
      year2 = data.target.value;
    }
    for (let i = 1; i < 13; i++) {
      this.immunizationService.queryCountByDate2(i, year2).subscribe({
        next: (res: any) => {
          this.onSuccessLoadMonth2(res.body, res.headers, i);
        },
        error() {
          console.log('error'); //eslint-disable-line no-console
        },
      });
    }
    setTimeout(() => {
      this.lineChartData2.datasets[0].data = this.data2;
      this.isDataLoaded2 = true;
    }, 1000);
  }
  //----------------------
  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
  protected onSuccessPatientCount(data: any | null, headers: HttpHeaders): void {
    this.totalPatients = data;
  }
  protected onSuccessEncounterCount(data: any | null, headers: HttpHeaders): void {
    this.totalEncounters = data;
  }
  protected onSuccessEncounterCheckCount(data: any | null, headers: HttpHeaders): void {
    this.totalCheckup = data;
  }
  protected onSuccessImmunizationCount(data: any | null, headers: HttpHeaders): void {
    this.totalImmunizations = data;
  }
  protected onSuccessPatientMaleCount(data: any | null, headers: HttpHeaders): void {
    this.pieChartData[1] = data;
  }
  protected onSuccessPatientFemaleCount(data: any | null, headers: HttpHeaders): void {
    this.pieChartData[0] = data;
  }
  protected onSuccessPatientAgeOne(data: any | null, headers: HttpHeaders): void {
    this.pieChartData2[0] = data;
  }
  protected onSuccessPatientAgetwo(data: any | null, headers: HttpHeaders): void {
    this.pieChartData2[1] = data;
  }
  protected onSuccessPatientthree(data: any | null, headers: HttpHeaders): void {
    this.pieChartData2[2] = data;
  }
  protected onSuccessPatientAgeFour(data: any | null, headers: HttpHeaders): void {
    this.pieChartData2[3] = data;
  }
  protected onSuccessLoadMonth(data: any | null, headers: HttpHeaders, index: any): void {
    switch (index) {
      case 1:
        this.data[0] = data;
        break;
      case 2:
        this.data[1] = data;
        break;
      case 3:
        this.data[2] = data;
        break;
      case 4:
        this.data[3] = data;
        break;
      case 5:
        this.data[4] = data;
        break;
      case 6:
        this.data[5] = data;

        break;
      case 7:
        this.data[6] = data;
        break;
      case 8:
        this.data[7] = data;
        break;
      case 9:
        this.data[8] = data;
        break;
      case 10:
        this.data[9] = data;
        break;
      case 11:
        this.data[10] = data;
        break;
      case 12:
        this.data[11] = data;
        break;
    }
  }

  protected onSuccessLoadMonth2(data: any | null, headers: HttpHeaders, index: any): void {
    switch (index) {
      case 1:
        this.data2[0] = data;
        break;
      case 2:
        this.data2[1] = data;
        break;
      case 3:
        this.data2[2] = data;
        break;
      case 4:
        this.data2[3] = data;
        break;
      case 5:
        this.data2[4] = data;
        break;
      case 6:
        this.data2[5] = data;

        break;
      case 7:
        this.data2[6] = data;
        break;
      case 8:
        this.data2[7] = data;
        break;
      case 9:
        this.data2[8] = data;
        break;
      case 10:
        this.data2[9] = data;
        break;
      case 11:
        this.data2[10] = data;
        break;
      case 12:
        this.data2[11] = data;
        break;
    }
  }


  private processChartData() {
    // Triez les conditions par le nombre de fois pris (descendant)
    this.conditions.sort((a, b) => b.medicationCount - a.medicationCount);

    // Sélectionnez les 20 premières conditions avec les médicaments les plus fréquents
    const top20Conditions = this.conditions.slice(0, 20);

    // Créez les tableaux de données pour l'histogramme
    // eslint-disable-next-line @typescript-eslint/no-unsafe-return
    this.barChartLabels = top20Conditions.map(condition => condition.conditionText);
    this.barChartData = [{
      // eslint-disable-next-line @typescript-eslint/no-unsafe-return
      data: top20Conditions.map(condition => condition.medicationCount),
      label: 'Nombre de fois pris'
    }];
  }
}
