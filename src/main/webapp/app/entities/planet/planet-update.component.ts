import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IPlanet, Planet } from 'app/shared/model/planet.model';
import { PlanetService } from './planet.service';
import { IAchievement } from 'app/shared/model/achievement.model';
import { AchievementService } from 'app/entities/achievement/achievement.service';

@Component({
  selector: 'jhi-planet-update',
  templateUrl: './planet-update.component.html'
})
export class PlanetUpdateComponent implements OnInit {
  isSaving: boolean;

  achievements: IAchievement[];

  editForm = this.fb.group({
    id: [],
    forestPoints: [null, [Validators.required]],
    waterPoints: [null, [Validators.required]],
    smogPoints: [null, [Validators.required]],
    achievements: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected planetService: PlanetService,
    protected achievementService: AchievementService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ planet }) => {
      this.updateForm(planet);
    });
    this.achievementService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAchievement[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAchievement[]>) => response.body)
      )
      .subscribe((res: IAchievement[]) => (this.achievements = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(planet: IPlanet) {
    this.editForm.patchValue({
      id: planet.id,
      forestPoints: planet.forestPoints,
      waterPoints: planet.waterPoints,
      smogPoints: planet.smogPoints,
      achievements: planet.achievements
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const planet = this.createFromForm();
    if (planet.id !== undefined) {
      this.subscribeToSaveResponse(this.planetService.update(planet));
    } else {
      this.subscribeToSaveResponse(this.planetService.create(planet));
    }
  }

  private createFromForm(): IPlanet {
    return {
      ...new Planet(),
      id: this.editForm.get(['id']).value,
      forestPoints: this.editForm.get(['forestPoints']).value,
      waterPoints: this.editForm.get(['waterPoints']).value,
      smogPoints: this.editForm.get(['smogPoints']).value,
      achievements: this.editForm.get(['achievements']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPlanet>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackAchievementById(index: number, item: IAchievement) {
    return item.id;
  }

  getSelected(selectedVals: any[], option: any) {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
