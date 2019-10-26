import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TrickPeopleToSaveTheWorldSharedModule } from 'app/shared/shared.module';
import { Co2DataComponent } from './co-2-data.component';
import { Co2DataDetailComponent } from './co-2-data-detail.component';
import { Co2DataUpdateComponent } from './co-2-data-update.component';
import { Co2DataDeletePopupComponent, Co2DataDeleteDialogComponent } from './co-2-data-delete-dialog.component';
import { co2DataRoute, co2DataPopupRoute } from './co-2-data.route';

const ENTITY_STATES = [...co2DataRoute, ...co2DataPopupRoute];

@NgModule({
  imports: [TrickPeopleToSaveTheWorldSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    Co2DataComponent,
    Co2DataDetailComponent,
    Co2DataUpdateComponent,
    Co2DataDeleteDialogComponent,
    Co2DataDeletePopupComponent
  ],
  entryComponents: [Co2DataDeleteDialogComponent]
})
export class TrickPeopleToSaveTheWorldCo2DataModule {}
