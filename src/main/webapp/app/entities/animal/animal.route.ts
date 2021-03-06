import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Animal } from 'app/shared/model/animal.model';
import { AnimalService } from './animal.service';
import { AnimalComponent } from './animal.component';
import { AnimalDetailComponent } from './animal-detail.component';
import { AnimalUpdateComponent } from './animal-update.component';
import { AnimalDeletePopupComponent } from './animal-delete-dialog.component';
import { IAnimal } from 'app/shared/model/animal.model';

@Injectable({ providedIn: 'root' })
export class AnimalResolve implements Resolve<IAnimal> {
  constructor(private service: AnimalService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAnimal> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Animal>) => response.ok),
        map((animal: HttpResponse<Animal>) => animal.body)
      );
    }
    return of(new Animal());
  }
}

export const animalRoute: Routes = [
  {
    path: '',
    component: AnimalComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'trickPeopleToSaveTheWorldApp.animal.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AnimalDetailComponent,
    resolve: {
      animal: AnimalResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'trickPeopleToSaveTheWorldApp.animal.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AnimalUpdateComponent,
    resolve: {
      animal: AnimalResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'trickPeopleToSaveTheWorldApp.animal.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AnimalUpdateComponent,
    resolve: {
      animal: AnimalResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'trickPeopleToSaveTheWorldApp.animal.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const animalPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: AnimalDeletePopupComponent,
    resolve: {
      animal: AnimalResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'trickPeopleToSaveTheWorldApp.animal.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
