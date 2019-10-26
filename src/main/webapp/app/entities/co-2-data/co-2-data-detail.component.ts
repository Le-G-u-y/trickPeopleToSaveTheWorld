import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICo2Data } from 'app/shared/model/co-2-data.model';

@Component({
  selector: 'jhi-co-2-data-detail',
  templateUrl: './co-2-data-detail.component.html'
})
export class Co2DataDetailComponent implements OnInit {
  co2Data: ICo2Data;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ co2Data }) => {
      this.co2Data = co2Data;
    });
  }

  previousState() {
    window.history.back();
  }
}
