import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IAchievement, Achievement } from 'app/shared/model/achievement.model';
import { AchievementService } from './achievement.service';

@Component({
  selector: 'jhi-achievement-update',
  templateUrl: './achievement-update.component.html'
})
export class AchievementUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]]
  });

  constructor(protected achievementService: AchievementService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ achievement }) => {
      this.updateForm(achievement);
    });
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
}
