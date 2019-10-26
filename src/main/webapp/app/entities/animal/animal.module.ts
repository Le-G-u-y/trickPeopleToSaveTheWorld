import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TrickPeopleToSaveTheWorldSharedModule } from 'app/shared/shared.module';
import { AnimalComponent } from './animal.component';
import { AnimalDetailComponent } from './animal-detail.component';
import { AnimalUpdateComponent } from './animal-update.component';
import { AnimalDeletePopupComponent, AnimalDeleteDialogComponent } from './animal-delete-dialog.component';
import { animalRoute, animalPopupRoute } from './animal.route';

const ENTITY_STATES = [...animalRoute, ...animalPopupRoute];

@NgModule({
  imports: [TrickPeopleToSaveTheWorldSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [AnimalComponent, AnimalDetailComponent, AnimalUpdateComponent, AnimalDeleteDialogComponent, AnimalDeletePopupComponent],
  entryComponents: [AnimalDeleteDialogComponent]
})
export class TrickPeopleToSaveTheWorldAnimalModule {}
