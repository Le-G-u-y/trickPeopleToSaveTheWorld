import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { TrickPeopleToSaveTheWorldTestModule } from '../../../test.module';
import { LightingDataUpdateComponent } from 'app/entities/lighting-data/lighting-data-update.component';
import { LightingDataService } from 'app/entities/lighting-data/lighting-data.service';
import { LightingData } from 'app/shared/model/lighting-data.model';

describe('Component Tests', () => {
  describe('LightingData Management Update Component', () => {
    let comp: LightingDataUpdateComponent;
    let fixture: ComponentFixture<LightingDataUpdateComponent>;
    let service: LightingDataService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TrickPeopleToSaveTheWorldTestModule],
        declarations: [LightingDataUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(LightingDataUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LightingDataUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LightingDataService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new LightingData(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new LightingData();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
