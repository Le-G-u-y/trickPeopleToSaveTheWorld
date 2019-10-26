import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILightingData } from 'app/shared/model/lighting-data.model';

@Component({
  selector: 'jhi-lighting-data-detail',
  templateUrl: './lighting-data-detail.component.html'
})
export class LightingDataDetailComponent implements OnInit {
  lightingData: ILightingData;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ lightingData }) => {
      this.lightingData = lightingData;
    });
  }

  previousState() {
    window.history.back();
  }
}
