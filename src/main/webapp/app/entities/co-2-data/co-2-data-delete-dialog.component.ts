import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICo2Data } from 'app/shared/model/co-2-data.model';
import { Co2DataService } from './co-2-data.service';

@Component({
  selector: 'jhi-co-2-data-delete-dialog',
  templateUrl: './co-2-data-delete-dialog.component.html'
})
export class Co2DataDeleteDialogComponent {
  co2Data: ICo2Data;

  constructor(protected co2DataService: Co2DataService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.co2DataService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'co2DataListModification',
        content: 'Deleted an co2Data'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-co-2-data-delete-popup',
  template: ''
})
export class Co2DataDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ co2Data }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(Co2DataDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.co2Data = co2Data;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/co-2-data', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/co-2-data', { outlets: { popup: null } }]);
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
