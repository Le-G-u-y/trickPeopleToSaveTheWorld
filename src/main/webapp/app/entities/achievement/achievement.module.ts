import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TrickPeopleToSaveTheWorldSharedModule } from 'app/shared/shared.module';
import { AchievementComponent } from './achievement.component';
import { AchievementDetailComponent } from './achievement-detail.component';
import { AchievementUpdateComponent } from './achievement-update.component';
import { AchievementDeletePopupComponent, AchievementDeleteDialogComponent } from './achievement-delete-dialog.component';
import { achievementRoute, achievementPopupRoute } from './achievement.route';

const ENTITY_STATES = [...achievementRoute, ...achievementPopupRoute];

@NgModule({
  imports: [TrickPeopleToSaveTheWorldSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    AchievementComponent,
    AchievementDetailComponent,
    AchievementUpdateComponent,
    AchievementDeleteDialogComponent,
    AchievementDeletePopupComponent
  ],
  entryComponents: [AchievementDeleteDialogComponent]
})
export class TrickPeopleToSaveTheWorldAchievementModule {}
