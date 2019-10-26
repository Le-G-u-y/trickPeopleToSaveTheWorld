import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { ICo2Data, Co2Data } from 'app/shared/model/co-2-data.model';
import { Co2DataService } from './co-2-data.service';

@Component({
  selector: 'jhi-co-2-data-update',
  templateUrl: './co-2-data-update.component.html'
})
export class Co2DataUpdateComponent implements OnInit {
  isSaving: boolean;
  timestampDp: any;

  editForm = this.fb.group({
    id: [],
    timestamp: [null, [Validators.required]],
    co2Value: [null, [Validators.required]],
    temp: [null, [Validators.required]],
    humidity: []
  });

  constructor(protected co2DataService: Co2DataService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ co2Data }) => {
      this.updateForm(co2Data);
    });
  }

  updateForm(co2Data: ICo2Data) {
    this.editForm.patchValue({
      id: co2Data.id,
      timestamp: co2Data.timestamp,
      co2Value: co2Data.co2Value,
      temp: co2Data.temp,
      humidity: co2Data.humidity
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const co2Data = this.createFromForm();
    if (co2Data.id !== undefined) {
      this.subscribeToSaveResponse(this.co2DataService.update(co2Data));
    } else {
      this.subscribeToSaveResponse(this.co2DataService.create(co2Data));
    }
  }

  private createFromForm(): ICo2Data {
    return {
      ...new Co2Data(),
      id: this.editForm.get(['id']).value,
      timestamp: this.editForm.get(['timestamp']).value,
      co2Value: this.editForm.get(['co2Value']).value,
      temp: this.editForm.get(['temp']).value,
      humidity: this.editForm.get(['humidity']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICo2Data>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
