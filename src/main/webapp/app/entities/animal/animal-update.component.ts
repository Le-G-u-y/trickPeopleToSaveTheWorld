import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IAnimal, Animal } from 'app/shared/model/animal.model';
import { AnimalService } from './animal.service';
import { IPlanet } from 'app/shared/model/planet.model';
import { PlanetService } from 'app/entities/planet/planet.service';

@Component({
  selector: 'jhi-animal-update',
  templateUrl: './animal-update.component.html'
})
export class AnimalUpdateComponent implements OnInit {
  isSaving: boolean;

  planets: IPlanet[];
  creationDateDp: any;

  editForm = this.fb.group({
    id: [],
    name: [],
    animalType: [null, [Validators.required]],
    maxHealth: [null, [Validators.required]],
    currentHealth: [null, [Validators.required]],
    creationDate: [],
    happiness: [null, [Validators.required]],
    deathNotified: [],
    planet: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected animalService: AnimalService,
    protected planetService: PlanetService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ animal }) => {
      this.updateForm(animal);
    });
    this.planetService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPlanet[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPlanet[]>) => response.body)
      )
      .subscribe((res: IPlanet[]) => (this.planets = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(animal: IAnimal) {
    this.editForm.patchValue({
      id: animal.id,
      name: animal.name,
      animalType: animal.animalType,
      maxHealth: animal.maxHealth,
      currentHealth: animal.currentHealth,
      creationDate: animal.creationDate,
      happiness: animal.happiness,
      deathNotified: animal.deathNotified,
      planet: animal.planet
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const animal = this.createFromForm();
    if (animal.id !== undefined) {
      this.subscribeToSaveResponse(this.animalService.update(animal));
    } else {
      this.subscribeToSaveResponse(this.animalService.create(animal));
    }
  }

  private createFromForm(): IAnimal {
    return {
      ...new Animal(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      animalType: this.editForm.get(['animalType']).value,
      maxHealth: this.editForm.get(['maxHealth']).value,
      currentHealth: this.editForm.get(['currentHealth']).value,
      creationDate: this.editForm.get(['creationDate']).value,
      happiness: this.editForm.get(['happiness']).value,
      deathNotified: this.editForm.get(['deathNotified']).value,
      planet: this.editForm.get(['planet']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAnimal>>) {
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
}
