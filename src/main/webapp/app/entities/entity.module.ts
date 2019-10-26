import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'achievement',
        loadChildren: () => import('./achievement/achievement.module').then(m => m.TrickPeopleToSaveTheWorldAchievementModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class TrickPeopleToSaveTheWorldEntityModule {}
