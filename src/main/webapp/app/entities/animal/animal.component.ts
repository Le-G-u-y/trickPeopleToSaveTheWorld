import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IAnimal } from 'app/shared/model/animal.model';
import { AccountService } from 'app/core/auth/account.service';
import { AnimalService } from './animal.service';

@Component({
  selector: 'jhi-animal',
  templateUrl: './animal.component.html'
})
export class AnimalComponent implements OnInit, OnDestroy {
  animals: IAnimal[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(protected animalService: AnimalService, protected eventManager: JhiEventManager, protected accountService: AccountService) {}

  loadAll() {
    this.animalService
      .query()
      .pipe(
        filter((res: HttpResponse<IAnimal[]>) => res.ok),
        map((res: HttpResponse<IAnimal[]>) => res.body)
      )
      .subscribe((res: IAnimal[]) => {
        this.animals = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInAnimals();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IAnimal) {
    return item.id;
  }

  registerChangeInAnimals() {
    this.eventSubscriber = this.eventManager.subscribe('animalListModification', response => this.loadAll());
  }
}
