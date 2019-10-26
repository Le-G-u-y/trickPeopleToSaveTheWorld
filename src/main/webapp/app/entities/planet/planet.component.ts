import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IPlanet } from 'app/shared/model/planet.model';
import { AccountService } from 'app/core/auth/account.service';
import { PlanetService } from './planet.service';

@Component({
  selector: 'jhi-planet',
  templateUrl: './planet.component.html'
})
export class PlanetComponent implements OnInit, OnDestroy {
  planets: IPlanet[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(protected planetService: PlanetService, protected eventManager: JhiEventManager, protected accountService: AccountService) {}

  loadAll() {
    this.planetService
      .query()
      .pipe(
        filter((res: HttpResponse<IPlanet[]>) => res.ok),
        map((res: HttpResponse<IPlanet[]>) => res.body)
      )
      .subscribe((res: IPlanet[]) => {
        this.planets = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInPlanets();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPlanet) {
    return item.id;
  }

  registerChangeInPlanets() {
    this.eventSubscriber = this.eventManager.subscribe('planetListModification', response => this.loadAll());
  }
}
