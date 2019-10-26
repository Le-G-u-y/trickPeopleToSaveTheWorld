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
      },
      {
        path: 'lighting-data',
        loadChildren: () => import('./lighting-data/lighting-data.module').then(m => m.TrickPeopleToSaveTheWorldLightingDataModule)
      },
      {
        path: 'co-2-data',
        loadChildren: () => import('./co-2-data/co-2-data.module').then(m => m.TrickPeopleToSaveTheWorldCo2DataModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class TrickPeopleToSaveTheWorldEntityModule {}
