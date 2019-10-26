import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { TrickPeopleToSaveTheWorldTestModule } from '../../../test.module';
import { LightingDataDeleteDialogComponent } from 'app/entities/lighting-data/lighting-data-delete-dialog.component';
import { LightingDataService } from 'app/entities/lighting-data/lighting-data.service';

describe('Component Tests', () => {
  describe('LightingData Management Delete Component', () => {
    let comp: LightingDataDeleteDialogComponent;
    let fixture: ComponentFixture<LightingDataDeleteDialogComponent>;
    let service: LightingDataService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TrickPeopleToSaveTheWorldTestModule],
        declarations: [LightingDataDeleteDialogComponent]
      })
        .overrideTemplate(LightingDataDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LightingDataDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LightingDataService);
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
