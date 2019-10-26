import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILightingData } from 'app/shared/model/lighting-data.model';
import { LightingDataService } from './lighting-data.service';

@Component({
  selector: 'jhi-lighting-data-delete-dialog',
  templateUrl: './lighting-data-delete-dialog.component.html'
})
export class LightingDataDeleteDialogComponent {
  lightingData: ILightingData;

  constructor(
    protected lightingDataService: LightingDataService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.lightingDataService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'lightingDataListModification',
        content: 'Deleted an lightingData'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-lighting-data-delete-popup',
  template: ''
})
export class LightingDataDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ lightingData }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(LightingDataDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.lightingData = lightingData;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/lighting-data', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/lighting-data', { outlets: { popup: null } }]);
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
