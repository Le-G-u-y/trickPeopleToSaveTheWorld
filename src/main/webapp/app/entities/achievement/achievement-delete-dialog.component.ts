import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAchievement } from 'app/shared/model/achievement.model';
import { AchievementService } from './achievement.service';

@Component({
  selector: 'jhi-achievement-delete-dialog',
  templateUrl: './achievement-delete-dialog.component.html'
})
export class AchievementDeleteDialogComponent {
  achievement: IAchievement;

  constructor(
    protected achievementService: AchievementService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.achievementService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'achievementListModification',
        content: 'Deleted an achievement'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-achievement-delete-popup',
  template: ''
})
export class AchievementDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ achievement }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AchievementDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.achievement = achievement;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/achievement', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/achievement', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
