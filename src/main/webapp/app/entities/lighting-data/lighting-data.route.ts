import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { LightingData } from 'app/shared/model/lighting-data.model';
import { LightingDataService } from './lighting-data.service';
import { LightingDataComponent } from './lighting-data.component';
import { LightingDataDetailComponent } from './lighting-data-detail.component';
import { LightingDataUpdateComponent } from './lighting-data-update.component';
import { LightingDataDeletePopupComponent } from './lighting-data-delete-dialog.component';
import { ILightingData } from 'app/shared/model/lighting-data.model';

@Injectable({ providedIn: 'root' })
export class LightingDataResolve implements Resolve<ILightingData> {
  constructor(private service: LightingDataService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ILightingData> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<LightingData>) => response.ok),
        map((lightingData: HttpResponse<LightingData>) => lightingData.body)
      );
    }
    return of(new LightingData());
  }
}

export const lightingDataRoute: Routes = [
  {
    path: '',
    component: LightingDataComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'trickPeopleToSaveTheWorldApp.lightingData.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: LightingDataDetailComponent,
    resolve: {
      lightingData: LightingDataResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'trickPeopleToSaveTheWorldApp.lightingData.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: LightingDataUpdateComponent,
    resolve: {
      lightingData: LightingDataResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'trickPeopleToSaveTheWorldApp.lightingData.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: LightingDataUpdateComponent,
    resolve: {
      lightingData: LightingDataResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'trickPeopleToSaveTheWorldApp.lightingData.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const lightingDataPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: LightingDataDeletePopupComponent,
    resolve: {
      lightingData: LightingDataResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'trickPeopleToSaveTheWorldApp.lightingData.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
