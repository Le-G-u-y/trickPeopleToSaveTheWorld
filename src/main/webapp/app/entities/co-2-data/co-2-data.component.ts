import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { ICo2Data } from 'app/shared/model/co-2-data.model';
import { AccountService } from 'app/core/auth/account.service';
import { Co2DataService } from './co-2-data.service';

@Component({
  selector: 'jhi-co-2-data',
  templateUrl: './co-2-data.component.html'
})
export class Co2DataComponent implements OnInit, OnDestroy {
  co2Data: ICo2Data[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected co2DataService: Co2DataService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.co2DataService
      .query()
      .pipe(
        filter((res: HttpResponse<ICo2Data[]>) => res.ok),
        map((res: HttpResponse<ICo2Data[]>) => res.body)
      )
      .subscribe((res: ICo2Data[]) => {
        this.co2Data = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInCo2Data();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ICo2Data) {
    return item.id;
  }

  registerChangeInCo2Data() {
    this.eventSubscriber = this.eventManager.subscribe('co2DataListModification', response => this.loadAll());
  }
}
