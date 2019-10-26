import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Achievement } from 'app/shared/model/achievement.model';
import { AchievementService } from './achievement.service';
import { AchievementComponent } from './achievement.component';
import { AchievementDetailComponent } from './achievement-detail.component';
import { AchievementUpdateComponent } from './achievement-update.component';
import { AchievementDeletePopupComponent } from './achievement-delete-dialog.component';
import { IAchievement } from 'app/shared/model/achievement.model';

@Injectable({ providedIn: 'root' })
export class AchievementResolve implements Resolve<IAchievement> {
  constructor(private service: AchievementService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAchievement> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Achievement>) => response.ok),
        map((achievement: HttpResponse<Achievement>) => achievement.body)
      );
    }
    return of(new Achievement());
  }
}

export const achievementRoute: Routes = [
  {
    path: '',
    component: AchievementComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'trickPeopleToSaveTheWorldApp.achievement.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AchievementDetailComponent,
    resolve: {
      achievement: AchievementResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'trickPeopleToSaveTheWorldApp.achievement.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AchievementUpdateComponent,
    resolve: {
      achievement: AchievementResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'trickPeopleToSaveTheWorldApp.achievement.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AchievementUpdateComponent,
    resolve: {
      achievement: AchievementResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'trickPeopleToSaveTheWorldApp.achievement.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const achievementPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: AchievementDeletePopupComponent,
    resolve: {
      achievement: AchievementResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'trickPeopleToSaveTheWorldApp.achievement.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
