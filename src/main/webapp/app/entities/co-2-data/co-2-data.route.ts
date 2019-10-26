import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Co2Data } from 'app/shared/model/co-2-data.model';
import { Co2DataService } from './co-2-data.service';
import { Co2DataComponent } from './co-2-data.component';
import { Co2DataDetailComponent } from './co-2-data-detail.component';
import { Co2DataUpdateComponent } from './co-2-data-update.component';
import { Co2DataDeletePopupComponent } from './co-2-data-delete-dialog.component';
import { ICo2Data } from 'app/shared/model/co-2-data.model';

@Injectable({ providedIn: 'root' })
export class Co2DataResolve implements Resolve<ICo2Data> {
  constructor(private service: Co2DataService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICo2Data> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Co2Data>) => response.ok),
        map((co2Data: HttpResponse<Co2Data>) => co2Data.body)
      );
    }
    return of(new Co2Data());
  }
}

export const co2DataRoute: Routes = [
  {
    path: '',
    component: Co2DataComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'trickPeopleToSaveTheWorldApp.co2Data.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: Co2DataDetailComponent,
    resolve: {
      co2Data: Co2DataResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'trickPeopleToSaveTheWorldApp.co2Data.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: Co2DataUpdateComponent,
    resolve: {
      co2Data: Co2DataResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'trickPeopleToSaveTheWorldApp.co2Data.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: Co2DataUpdateComponent,
    resolve: {
      co2Data: Co2DataResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'trickPeopleToSaveTheWorldApp.co2Data.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const co2DataPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: Co2DataDeletePopupComponent,
    resolve: {
      co2Data: Co2DataResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'trickPeopleToSaveTheWorldApp.co2Data.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
