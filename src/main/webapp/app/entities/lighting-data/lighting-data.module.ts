import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TrickPeopleToSaveTheWorldSharedModule } from 'app/shared/shared.module';
import { LightingDataComponent } from './lighting-data.component';
import { LightingDataDetailComponent } from './lighting-data-detail.component';
import { LightingDataUpdateComponent } from './lighting-data-update.component';
import { LightingDataDeletePopupComponent, LightingDataDeleteDialogComponent } from './lighting-data-delete-dialog.component';
import { lightingDataRoute, lightingDataPopupRoute } from './lighting-data.route';

const ENTITY_STATES = [...lightingDataRoute, ...lightingDataPopupRoute];

@NgModule({
  imports: [TrickPeopleToSaveTheWorldSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    LightingDataComponent,
    LightingDataDetailComponent,
    LightingDataUpdateComponent,
    LightingDataDeleteDialogComponent,
    LightingDataDeletePopupComponent
  ],
  entryComponents: [LightingDataDeleteDialogComponent]
})
export class TrickPeopleToSaveTheWorldLightingDataModule {}
