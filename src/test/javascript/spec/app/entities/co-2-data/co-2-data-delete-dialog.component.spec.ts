import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { TrickPeopleToSaveTheWorldTestModule } from '../../../test.module';
import { Co2DataDeleteDialogComponent } from 'app/entities/co-2-data/co-2-data-delete-dialog.component';
import { Co2DataService } from 'app/entities/co-2-data/co-2-data.service';

describe('Component Tests', () => {
  describe('Co2Data Management Delete Component', () => {
    let comp: Co2DataDeleteDialogComponent;
    let fixture: ComponentFixture<Co2DataDeleteDialogComponent>;
    let service: Co2DataService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TrickPeopleToSaveTheWorldTestModule],
        declarations: [Co2DataDeleteDialogComponent]
      })
        .overrideTemplate(Co2DataDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(Co2DataDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(Co2DataService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
