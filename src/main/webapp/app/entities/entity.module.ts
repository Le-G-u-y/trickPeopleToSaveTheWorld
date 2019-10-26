import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'achievement',
        loadChildren: () => import('./achievement/achievement.module').then(m => m.TrickPeopleToSaveTheWorldAchievementModule)
      },
      {
        path: 'animal',
        loadChildren: () => import('./animal/animal.module').then(m => m.TrickPeopleToSaveTheWorldAnimalModule)
      },
      {
        path: 'planet',
        loadChildren: () => import('./planet/planet.module').then(m => m.TrickPeopleToSaveTheWorldPlanetModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class TrickPeopleToSaveTheWorldEntityModule {}
