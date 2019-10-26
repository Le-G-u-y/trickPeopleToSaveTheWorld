import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ILightingData, LightingData } from 'app/shared/model/lighting-data.model';
import { LightingDataService } from './lighting-data.service';

@Component({
  selector: 'jhi-lighting-data-update',
  templateUrl: './lighting-data-update.component.html'
})
export class LightingDataUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    onSeconds: [],
    offSeconds: []
  });

  constructor(protected lightingDataService: LightingDataService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ lightingData }) => {
      this.updateForm(lightingData);
    });
  }

  updateForm(lightingData: ILightingData) {
    this.editForm.patchValue({
      id: lightingData.id,
      onSeconds: lightingData.onSeconds,
      offSeconds: lightingData.offSeconds
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const lightingData = this.createFromForm();
    if (lightingData.id !== undefined) {
      this.subscribeToSaveResponse(this.lightingDataService.update(lightingData));
    } else {
      this.subscribeToSaveResponse(this.lightingDataService.create(lightingData));
    }
  }

  private createFromForm(): ILightingData {
    return {
      ...new LightingData(),
      id: this.editForm.get(['id']).value,
      onSeconds: this.editForm.get(['onSeconds']).value,
      offSeconds: this.editForm.get(['offSeconds']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILightingData>>) {
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
