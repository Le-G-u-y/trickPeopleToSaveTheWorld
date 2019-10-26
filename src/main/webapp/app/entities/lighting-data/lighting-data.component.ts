import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { ILightingData } from 'app/shared/model/lighting-data.model';
import { AccountService } from 'app/core/auth/account.service';
import { LightingDataService } from './lighting-data.service';

@Component({
  selector: 'jhi-lighting-data',
  templateUrl: './lighting-data.component.html'
})
export class LightingDataComponent implements OnInit, OnDestroy {
  lightingData: ILightingData[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected lightingDataService: LightingDataService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.lightingDataService
      .query()
      .pipe(
        filter((res: HttpResponse<ILightingData[]>) => res.ok),
        map((res: HttpResponse<ILightingData[]>) => res.body)
      )
      .subscribe((res: ILightingData[]) => {
        this.lightingData = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInLightingData();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ILightingData) {
    return item.id;
  }

  registerChangeInLightingData() {
    this.eventSubscriber = this.eventManager.subscribe('lightingDataListModification', response => this.loadAll());
  }
}
