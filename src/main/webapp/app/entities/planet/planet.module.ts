import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TrickPeopleToSaveTheWorldSharedModule } from 'app/shared/shared.module';
import { PlanetComponent } from './planet.component';
import { PlanetDetailComponent } from './planet-detail.component';
import { PlanetUpdateComponent } from './planet-update.component';
import { PlanetDeletePopupComponent, PlanetDeleteDialogComponent } from './planet-delete-dialog.component';
import { planetRoute, planetPopupRoute } from './planet.route';

const ENTITY_STATES = [...planetRoute, ...planetPopupRoute];

@NgModule({
  imports: [TrickPeopleToSaveTheWorldSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [PlanetComponent, PlanetDetailComponent, PlanetUpdateComponent, PlanetDeleteDialogComponent, PlanetDeletePopupComponent],
  entryComponents: [PlanetDeleteDialogComponent]
})
export class TrickPeopleToSaveTheWorldPlanetModule {}
