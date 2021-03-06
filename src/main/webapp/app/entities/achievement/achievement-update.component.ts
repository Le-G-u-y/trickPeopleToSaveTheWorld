import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IAchievement, Achievement } from 'app/shared/model/achievement.model';
import { AchievementService } from './achievement.service';
import { IPlanet } from 'app/shared/model/planet.model';
import { PlanetService } from 'app/entities/planet/planet.service';

@Component({
  selector: 'jhi-achievement-update',
  templateUrl: './achievement-update.component.html'
})
export class AchievementUpdateComponent implements OnInit {
  isSaving: boolean;

  planets: IPlanet[];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected achievementService: AchievementService,
    protected planetService: PlanetService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ achievement }) => {
      this.updateForm(achievement);
    });
    this.planetService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPlanet[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPlanet[]>) => response.body)
      )
      .subscribe((res: IPlanet[]) => (this.planets = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(achievement: IAchievement) {
    this.editForm.patchValue({
      id: achievement.id,
      name: achievement.name
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const achievement = this.createFromForm();
    if (achievement.id !== undefined) {
      this.subscribeToSaveResponse(this.achievementService.update(achievement));
    } else {
      this.subscribeToSaveResponse(this.achievementService.create(achievement));
    }
  }

  private createFromForm(): IAchievement {
    return {
      ...new Achievement(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAchievement>>) {
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

  trackPlanetById(index: number, item: IPlanet) {
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
